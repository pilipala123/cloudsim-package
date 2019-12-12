package org.cloudbus.cloudsim.container.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.math3.util.Pair;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;
import org.cloudbus.cloudsim.container.core.redis.Configuration;
import org.cloudbus.cloudsim.container.core.redis.ERedisModel;
import org.cloudbus.cloudsim.container.core.redis.ERedisOperationType;
import org.cloudbus.cloudsim.container.core.redis.QpsCaculate;
import org.cloudbus.cloudsim.container.schedulers.ContainerCloudletScheduler;
import org.cloudbus.cloudsim.container.utils.MapOperator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Redis extends Container {

        private int responseTime;
        private double qps;

        private int max_connection;
        private int max_inbound_bandwidth;
        private int max_outbound_bandwidth;
        private int max_qps;

        private ERedisModel model;

        private int current_connection = 0;

        private int max_storage;
        private Map<Integer, Long> cache;
        private Map<Integer, Integer> cache_request_count;

        /**
         * Creates a new Container-based Redis object.
         *
         * @param id
         * @param userId
         * @param mips
         * @param numberOfPes
         * @param ram
         * @param bw
         * @param size
         * @param containerManager
         * @param containerCloudletScheduler
         * @param schedulingInterval
         */
        public Redis(int id, int userId, double mips, int numberOfPes, int ram, long bw, long size, String containerManager, ContainerCloudletScheduler containerCloudletScheduler, double schedulingInterval) {
        super(id, userId, mips, numberOfPes, ram, bw, size, containerManager, containerCloudletScheduler, schedulingInterval);
    }

        public Redis(int id, int userId, double mips, int numberOfPes, int ram, long bw, List<ContainerCloudlet> cloudletList) {
        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
        init();
        try {
            setResponseTime(processRequests(cloudletList, 100, 100));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setQps(100);
    }

    private void init() {
        this.max_connection = Configuration.getProperty("redis.MAX_ACTIVE", 1024);
        this.max_inbound_bandwidth = Configuration.getProperty("redis.max.inbound.bandwidth", 1000);
        this.max_outbound_bandwidth = Configuration.getProperty("redis.max.outbound.bandwidth", 1000);
        this.max_qps = Configuration.getProperty("redis.max.qps", 1000);

        this.model = ERedisModel.valueOf(Configuration.getProperty("redis.model", "STANDARDDOUBLE"));

        this.max_storage = Configuration.getProperty("redis.max.qps", 1000);

        this.cache = Maps.newHashMapWithExpectedSize(10);
        this.cache_request_count = Maps.newHashMapWithExpectedSize(10);
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public double getQps() {
        return qps;
    }

    public void setQps(int qps) {
        this.qps = qps;
    }

    /**
     * 根据当前配置和参数，基于线性规划模型计算qps
     *
     * @return qps
     */
    private int qps() {
        return QpsCaculate.QpsCaculate(getId(), 0, current_connection, max_connection, getBw()
                , getMips(), model.getStatus(), max_inbound_bandwidth, max_outbound_bandwidth);
    }

    /**
     * 根据不同操作返回响应时间
     *
     * @param type Redis不同操作
     * @return qps
     */
    private double qps(ERedisOperationType type) {
        int qps = qps();
        switch (type) {
            case QueryMinimumRequest:
            case QueryNotRequestRecently:
                return 1.2 * qps;
            case Query:
            default:
                return qps;
        }
    }

    /**
     * 执行任务队列，假设 containerCloudlet.getStatus() == 1 代表查询，否则代表插入
     *
     * @param cloudletList 任务队列
     */
    public void processRequests(List<ContainerCloudlet> cloudletList) {
        for (ContainerCloudlet containerCloudlet : cloudletList) {
            if (1 == containerCloudlet.getStatus()) {
                this.qps += query(containerCloudlet);
            } else {
                this.qps += insert(containerCloudlet);
            }
        }
    }

    /**
     * 执行从缓存中查询任务
     * 1. 若缓存中存在，则更新时间为当前时间，访问次数+1
     * 2. 否则，跳出
     *
     * @param cloudlet 任务
     * @return 响应时间
     */
    public double query(ContainerCloudlet cloudlet) {
        int id = cloudlet.containerId;
        if (cache.containsKey(id)) {
            cache.put(id, System.currentTimeMillis());
            cache_request_count.put(id, cache_request_count.getOrDefault(id, 0) + 1);
        }
        return qps(ERedisOperationType.Query);
    }

    /**
     * 执行向缓存中插入任务
     * 1. 若缓存中存在，则返回查询的响应时间
     * 2. 否则，执行如下
     * 2.1 若当前缓存量超过最大存储量{@link}，执行更新缓存
     * 2.2 插入任务到缓存中，并设置该任务的频率=1
     *
     * @param cloudlet 任务
     * @return 响应时间
     */
    public double insert(ContainerCloudlet cloudlet) {
        double qps = 0;
        int id = cloudlet.containerId;
        if (cache.containsKey(id)) {
            qps += qps(ERedisOperationType.Query);
        } else {
            if (cache.size() >= max_storage) {
                qps += update();
            }
            cache.put(id, System.currentTimeMillis());
            cache_request_count.put(id, 1);
        }

        return qps;
    }

    /**
     * 执行更新缓存操作
     * 1. 将缓存任务按请求频率升序排序
     * 2. 选择请求频率最低的一组任务（可能为多个）
     * 3. 若请求频率最低的有多个，则执行4，否则执行5
     * 4. 从请求频率最低的一组任务中，按最近最久未请求排序筛选
     * 5. 从缓存中删除过滤的任务列表
     *
     * @return 响应时间
     */
    private double update() {
        double qps = 0;
        List<Integer> ids;
        List<Integer> minimumRequest = findMinimumRequest(cache_request_count);
        qps += qps(ERedisOperationType.QueryMinimumRequest);

        if (1 != minimumRequest.size()) {
            ids = findNotRequestRecently(cache.entrySet().stream()
                    .filter(entry -> minimumRequest.contains(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            qps += qps(ERedisOperationType.QueryNotRequestRecently);
        } else {
            ids = minimumRequest;
        }
        for (Integer id : ids) {
            cache.remove(id);
            cache_request_count.remove(id);
        }
        return qps;
    }

    /**
     * 从缓存请求频率表中选择频率最低的任务列表
     *
     * @param map 缓存请求频率表
     * @return 请求频率最低的任务列表
     */
    private List<Integer> findMinimumRequest(Map<Integer, Integer> map) {
        map = MapOperator.sortMapByValue(map);

        List<Integer> ids = Lists.newArrayListWithExpectedSize(1);

        int min_count = -1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (-1 == min_count) {
                min_count = entry.getValue();
            }
            if (min_count != entry.getValue()) {
                break;
            }
            ids.add(entry.getKey());
        }
        return ids;
    }

    /**
     * 从请求频率最低的任务列表中筛选最近最久未访问的任务列表
     *
     * @param map 请求频率最低的任务列表
     * @return 最近最久未访问的任务列表
     */
    private List<Integer> findNotRequestRecently(Map<Integer, Long> map) {
        map = MapOperator.sortMapByValue(map);
        List<Integer> ids = Lists.newArrayListWithExpectedSize(1);

        long min_count = -1;
        for (Map.Entry<Integer, Long> entry : map.entrySet()) {
            if (-1 == min_count) {
                min_count = entry.getValue();
            }
            if (min_count != entry.getValue()) {
                break;
            }
            ids.add(entry.getKey());
        }
        return ids;
    }

    public int processRequests(List<ContainerCloudlet> cloudletList, int cpuresources, int bwresources) throws FileNotFoundException {
        int containernumber = 12;
        int vmnumber = 6;
        int containerhandletime;
        int time = 0;
        int containercpurequest = 0, remaincpuresources = 0;
        int containerbwrequest = 0, remainbwresources = 0;
        int vmfreenumber = 0, vmcpuusage = 0, vmbwusage = 0;
        int containercpuusage = 0, containerbwusage = 0;
        int finishcloudletnumber = 0, startcloudletnumber = 0, lasttimestartcloudletnumber = 0, presentstarttimecloudletnumber = 0;
        int hostcpuusage = 0, hostbwusage = 0;

        SiemensList siemensList = new SiemensList();
        CloudletMinParament cloudletMinParament = new CloudletMinParament();

        cloudletList.forEach(cloudlet->cloudlet.setCloudletLength(25));

        cloudletMinParament.setcloudletMinParament(cloudletList, containernumber);
        List<SiemensVmresources> siemensVmresourcesList = createsiemnesVmresources(vmnumber);
        List<SiemensContainerresource> siemensContainerresourceList = createVmResource(containernumber, vmnumber, cpuresources, bwresources, cloudletMinParament.getMaxtimelength());

        List<BindContainer> bindCloudletlist = bindCloudlet(cloudletList);
        bindCloudletlist.sort(Comparator.comparingInt(BindContainer::getStarttime));

        int current_connection = 0, pre_connection = 0;
        int currentResponseTime = 0, preResponseTime = 0;
        float per_qps = 6.5f, coefficient = 1.0f;

        while (true) {
            startcloudletnumber = 0;
            vmfreenumber = 0;
            final int current_time = time;

            current_connection = Long.valueOf(bindCloudletlist.stream()
                    .filter(bindContainer -> bindContainer.getStarttime() == current_time).count()).intValue();
            int current_qps = Float.valueOf(current_connection * per_qps * coefficient).intValue();
            siemensList.getQps().add(current_qps);
            siemensList.getLoad2qps().add(Pair.create(current_connection, current_qps));

            containerhandletime = calculateResponseTime(current_connection);

            for (BindContainer bindContainer : bindCloudletlist) {
                if (bindContainer.getState() == 3) {
                    continue;
                }
                if (bindContainer.getStarttime() > time) {
                    break;
                }
                for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                    siemensContainerresource.setRemaincpuresource(time);
                    siemensContainerresource.setRemainbwresource(time);
                    remaincpuresources = siemensContainerresource.getRemaincpuresource();
                    remainbwresources = siemensContainerresource.getRemainbwresource();

                    if (bindContainer.getState() == 0 && bindContainer.getStarttime() == time) {
                        bindContainer.setState(1);
                    }
                    if (bindContainer.getState() == 1 || bindContainer.getState() == 2) {

                        containercpurequest = bindContainer.getCpuusage();
                        containerbwrequest = bindContainer.getBwusage();
                        if ((remaincpuresources >= containercpurequest) && (remainbwresources >= containerbwrequest)) {
                            siemensContainerresource.changeCpuarraypool(remaincpuresources, containercpurequest, time, containerhandletime);
                            siemensContainerresource.changeBwarraypool(remainbwresources, containerbwrequest, time, containerhandletime);
                            bindContainer.setState(3);
                            bindContainer.setFinishtime(time + containerhandletime);
                            bindContainer.setEveryresponsetime(bindContainer.getFinishtime() - bindContainer.getStarttime());
                            finishcloudletnumber++;
                            break;
                        }

                        if ((remaincpuresources < cloudletMinParament.getMincpurequest()
                                || remainbwresources < cloudletMinParament.getMinbwrequest())
                                && (siemensContainerresource.getId() == containernumber - 1)) {
                            bindContainer.setState(2);
                            break;
                        }
                    }

                }
            }
            startcloudletnumber = Long.valueOf(bindCloudletlist.stream().filter(bindContainer -> 0 != bindContainer.getState()).count()).intValue();
            //当time时，当前CloudLet数，也是connection，但是不是执行个数
            presentstarttimecloudletnumber = startcloudletnumber - lasttimestartcloudletnumber;

            siemensList.getLoadnumber().add(presentstarttimecloudletnumber);

            siemensList.getInputFlow().add(calculateInputFlow(current_connection));
            lasttimestartcloudletnumber = startcloudletnumber;

            hostbwusage = hostcpuusage = 0;
            //计算cpu和带宽利用率
            siemensList = calculateusage(siemensVmresourcesList, siemensContainerresourceList, siemensList, time, containernumber);
            //计算当前时间的平均响应时间
            currentResponseTime = calculateaverageresponsetime(siemensList, bindCloudletlist, time);
            if (0 == current_connection || 0 == pre_connection
                    || currentResponseTime / current_connection <= preResponseTime / pre_connection) {
                coefficient = 1.0f;
            } else {
                //ToDo
                coefficient = 0.9f;
            }
            pre_connection = current_connection;
            preResponseTime = currentResponseTime;
            if (siemensList.getStatus() == 1) {
                break;
            }
            time++;

        }
//        Plotpictures.plotpicture(time,hostbwusagelist);
//        List<Integer> numberlist= new ArrayList<>();
//        for (int i=0;i<100;i++){
//            numberlist.add(i);
//        }
        Plotpictures.plotpicture(time, siemensList.getLoadnumber(), "负载产生数量随时间的关系", "load");
        Plotpictures.plotpicture(time, siemensList.getInputFlow(), "InputFlow随时间的关系", "input flow");
        Plotpictures.plotpicture(time, siemensList.getQps(), "qps随时间的关系", "qps");
        Plotpictures.plotpicture(time, siemensList.getHostcpuusagelist(), "CPU利用率随时间的关系", "CPU");
        Plotpictures.plotpicture(time, siemensList.getHostbwusagelist(), "带宽利用率随时间的关系", "bw");
        Plotpictures.plotpicture(time, siemensList.getAverageresponsetimelist(), "平均响应时间随时间的关系", "response time");
        Plotpictures.plotpictureFromMap(time, siemensList.getLoad2qps(), "qps与负载的关系", "load2qps");

        System.out.println("finish cloudlet numbers is " + finishcloudletnumber);
        System.out.println("The finish time of Siemens is " + time + "ms");
//        System.out.println("The Response Time of GWslb is "+ response_time+"ms");
        return time;
    }

    private int qps(int current_connection) {
        return max_connection / Math.max(max_connection, current_connection) * Math.min(current_connection * 5, max_qps);
    }

    private int calculateResponseTime(int current_connection) {
//        int current_operation = current_connection * 5;
//        float per_time = (float) max_connection / max_qps;
//        double max_operation = Math.ceil((double) current_operation / max_connection);
//
//        double time = max_operation * per_time * 1000;
//
//        return Double.valueOf(Math.max(25.0d, time)).intValue();
        if (current_connection < max_connection * 0.9) {
            return 25;
        } else {
            return Float.valueOf((1 + Math.abs(current_connection - 0.9f * max_connection) / max_connection) * 25).intValue();
        }
    }

    private int calculateInputFlow(int current_connection) {
        return Double.valueOf(-2.695652 - 0.00026087 * current_connection + 0.00087 * current_connection * 5).intValue();
    }

    public SiemensList calculateusage(List<SiemensVmresources> siemensVmresourcesList, List<SiemensContainerresource> siemensContainerresourceList, SiemensList siemensList, int time, int containernumber) {
        int containercpuusage, containerbwusage;
        int vmcpuusage, vmbwusage;
        int hostcpuusage = 0, hostbwusage = 0;
        for (SiemensVmresources siemensVmresources : siemensVmresourcesList) {
            siemensVmresources.setCpuusage(0);
            siemensVmresources.setBwusage(0);
            for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                containercpuusage = 0;
                containerbwusage = 0;
                if (siemensContainerresource.getSiemensVmid() != siemensVmresources.getId()) {
                    continue;
                }
                for (int i = 0; i < siemensContainerresource.getCpuarraypool().length; i++) {
                    if (siemensContainerresource.getCpuarraypool()[i][time] == 1) {
                        containercpuusage++;
                    }
                }
                for (int j = 0; j < siemensContainerresource.getBwarraypool().length; j++) {
                    if (siemensContainerresource.getBwarraypool()[j][time] == 1) {
                        containerbwusage++;
                    }
                }

                siemensVmresources.addCpuusage(containercpuusage);
                siemensVmresources.addBwusage(containerbwusage);


//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Cpu usage is " + containercpuusage);
//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Bw usage is " + containerbwusage);

            }
            vmcpuusage = siemensVmresources.getCpuusage();
            vmbwusage = siemensVmresources.getBwusage();

//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Cpu usage is " + vmcpuusage);
//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Bw usage is " + vmbwusage);
            hostcpuusage = hostcpuusage + vmcpuusage;
            hostbwusage = hostbwusage + vmbwusage;

        }
        hostcpuusage = hostcpuusage / containernumber;
        hostbwusage = hostbwusage / containernumber;
        if (hostbwusage == 0 && hostcpuusage == 0 && time != 0) {
            siemensList.setStatus(1);
        }
        siemensList.getHostcpuusagelist().add(hostcpuusage);
        siemensList.getHostbwusagelist().add(hostbwusage);
        return siemensList;
    }

    public int calculateaverageresponsetime(SiemensList siemensList, List<BindContainer> bindCloudletlist, int time) {
        int sumreponsetime = 0, presentfinishcloudletnumber = 0, averagereponsetime = 0;
        for (BindContainer bindContainer : bindCloudletlist) {
            if (bindContainer.getFinishtime() <= time && bindContainer.getFinishtime() != 0) {
                int perresponsetime = bindContainer.getEveryresponsetime();
                sumreponsetime = sumreponsetime + perresponsetime;
                presentfinishcloudletnumber++;
            } else if (bindContainer.getState() == 0) {
                break;
            }
        }
        if (presentfinishcloudletnumber == 0) {
            System.out.println("At time:" + time + "ms averageresponsetime:" + averagereponsetime + "ms");
        } else {
            averagereponsetime = sumreponsetime / presentfinishcloudletnumber;
            System.out.println("At time:" + time + "ms averageresponsetime:" + averagereponsetime + "ms");
        }

        siemensList.getAverageresponsetimelist().add(averagereponsetime);

        return sumreponsetime;
    }

    public List<SiemensContainerresource> createVmResource(int containernumber, int vmnumber, int cpuresources, int bwresources, int maxlength) {
        List<SiemensContainerresource> siemensContainerresourceList = new ArrayList<>();
        int containereveryvm = (int) containernumber / vmnumber;
        for (Integer i = 0; i < containernumber; i++) {
            SiemensContainerresource siemensContainerresource = new SiemensContainerresource();
            siemensContainerresource.setId(i);
            siemensContainerresource.initCpuarraypool(cpuresources, maxlength);
            siemensContainerresource.initBwarraypool(bwresources, maxlength);
            siemensContainerresource.setSiemensVmid((int) (i / containereveryvm));
            siemensContainerresourceList.add(siemensContainerresource);

        }
        return siemensContainerresourceList;
    }


    /**
     * Process the requests and generate response time
     *
     * @param cloudletList
     */
    public List<BindContainer> bindCloudlet(List<ContainerCloudlet> cloudletList) {
        List<BindContainer> bindContainerList = new ArrayList<>();
        int containerid = 0;

        for (ContainerCloudlet cloudlet : cloudletList) {
            BindContainer bindContainer = new BindContainer(cloudlet.getCpurequest(),
                    cloudlet.getBwrequest(), cloudlet.getCloudletId(), containerid,
                    cloudlet.getStarttime(), (int) cloudlet.getCloudletLength());
            bindContainerList.add(bindContainer);
            cloudlet.setContainerId(bindContainer.getId());
            containerid++;
        }
        return bindContainerList;
    }

    public List<SiemensVmresources> createsiemnesVmresources(int vmnumber) {
        List<SiemensVmresources> siemensVmresourcesList = new ArrayList<>();
        for (int i = 0; i < vmnumber; i++) {
            SiemensVmresources siemensVmresources = new SiemensVmresources();
            siemensVmresources.setId(i);
            siemensVmresourcesList.add(siemensVmresources);
        }
        return siemensVmresourcesList;

    }
}