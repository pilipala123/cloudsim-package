package org.cloudbus.cloudsim.container.core;

import com.google.common.collect.Lists;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.*;
import org.cloudbus.cloudsim.container.core.Siemens.RegressionParament;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.redis.Configuration;
import org.cloudbus.cloudsim.container.core.redis.ERedisModel;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;

public class Redis extends Host {
    private int cpu_core;
    private int vm_number;
    private int container_number;

    private int cpu_parament;
    private double bw_parament;

    private double qps;
    private double qps_ratio;
    private double qps_threshold;

    private double response_time_threshold;
    private double response_time_ratio;
    private int response_time_parament;
    private double response_time_ratio2;

    private int network_bandwidth;

    private int cpu_resources;
    private int bw_resources;

    private int mips_ability;
    private int mips_parament;


    //以下暂时没用
    private int max_connection;
    private int max_inbound_bandwidth;
    private int max_outbound_bandwidth;
    private int max_qps;
    private ERedisModel model;

    private int avg_cpu;
    private int avg_ram;

    private int responseTime;


    private int memory_resources;


    private SiemensList siemensList;

    double pre_response_time = 0;
    float per_qps = 80f, coefficient = 1.0f;
    int pre_operation, current_operation = 0;

    public Redis(int id, List<ContainerCloudlet> cloudletList, int loadnumber, int ramp_down) {
        super(id);
        init();

        setSiemensList(new SiemensList(cloudletList, container_number, vm_number
                , cpu_resources, bw_resources, loadnumber, ramp_down));
        this.siemensList.setState(1);
        this.siemensList.setName("redis");
    }

    private void init() {
        this.cpu_core = Configuration.getProperty("redis.cpu.core", 3);
        this.vm_number = Configuration.getProperty("redis.vm.number", 1);
        this.container_number = Configuration.getProperty("redis.container.number", 2);

        this.cpu_parament = Configuration.getProperty("redis.cpu.parament", 300);
        this.bw_parament = Configuration.getProperty("redis.bw.parament", 37.5);


        this.qps = Configuration.getProperty("redis.qps", 8.8);
        this.qps_ratio = Configuration.getProperty("redis.qps.ratio", 0.8);
        this.qps_threshold = Configuration.getProperty("redis.qps.threshold", 1.05);

        this.network_bandwidth = Configuration.getProperty("redis.network.bandwidth", 200);

        this.response_time_threshold = Configuration.getProperty("redis.response.time.threshold", 65);
        this.response_time_ratio = Configuration.getProperty("redis.response.time.ratio", 1.18);
        this.response_time_ratio2 = Configuration.getProperty("redis.response.time.ratio2", 1.4);
        this.response_time_parament = Configuration.getProperty("redis.response.time.parament", 12);


        this.cpu_resources= (int)(cpu_core*cpu_parament*(double)vm_number/(double)container_number);
        this.bw_resources = (int)(network_bandwidth*bw_parament/(double)container_number);

        this.mips_ability = Configuration.getProperty("redis.mips.ability", 1);
        this.mips_parament = Configuration.getProperty("redis.mips.parament", 290);
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

    public void setQps(double qps) {
        this.qps = qps;
    }

    public int getCpu_resources() {
        return cpu_resources;
    }

    public void setCpu_resources(int cpu_resources) {
        this.cpu_resources = cpu_resources;
    }

    public int getMemory_resources() {
        return memory_resources;
    }

    public void setMemory_resources(int memory_resources) {
        this.memory_resources = memory_resources;
    }

    public int getContainer_number() {
        return container_number;
    }

    public void setContainer_number(int container_number) {
        this.container_number = container_number;
    }

    public int getVm_number() {
        return vm_number;
    }

    public void setVm_number(int vm_number) {
        this.vm_number = vm_number;
    }

    public SiemensList getSiemensList() {
        return siemensList;
    }

    public void setSiemensList(SiemensList siemensList) {
        this.siemensList = siemensList;
    }

    public void processEvent(List<ContainerCloudlet> cloudletList,
                             int time){
        try {
            double mips = (double)mips_ability/(double)mips_parament;

//            List<ContainerCloudlet> current_operation = loadOperation(cloudletList, time);
            setSiemensList(processRequests(cloudletList,cpu_resources, bw_resources,
                    "redis",getSiemensList(),container_number,vm_number,mips,response_time_parament
                    ,time,this.qps,this.qps_ratio,this.qps_threshold,
                    this.response_time_threshold,this.response_time_ratio,response_time_ratio2,network_bandwidth*2)
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(getSiemensList().getFinishtime());
    }

//    public void process(List<ContainerCloudlet> cloudletList,
//                        int time) {
//
//        List<ContainerCloudlet> current_operation = loadOperation(cloudletList, time);
//        try {
//            siemensList = processRequests(current_operation, cpu_resources, memory_resources, "redis"
//                    , siemensList, container_number, vm_number, 1, 1, time, qps, qps_ratio
//                    , qps_threshold, response_time_threshold, response_time_ratio, 2, network_bandwidth);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
////        updateCoefficient(time);
//
//        setResponseTime(this.siemensList.getFinishtime());
//    }

    private List<ContainerCloudlet> loadOperation(List<ContainerCloudlet> cloudletList, int time) {
        //选出当前产生的负载
        List<ContainerCloudlet> current_load = cloudletList.stream()
                .filter(cloudlet -> cloudlet.getStarttime() == time && cloudlet.getState() == 1)
                .collect(Collectors.toList());

        int connection = current_load.size();

        if (0 == connection) {
            return current_load;
        }

        ContainerCloudlet first = current_load.get(0);


        //每个操作执行时间：1000/model.getMax_qps()
        //每个连接占用资源：100/model.getMax_connection()

        //按占用1个单位资源重新分配，则每个请求是model.getMax_connection()/100个请求
        List<ContainerCloudlet> loads = Lists.newArrayListWithExpectedSize(10);

        //Double.valueOf(model.getMax_qps() / (per_qps * 1000)).intValue();//
        int re_load_size = this.model.getMax_connection() / 100;
        double per_length = (double) 1000 / model.getMax_qps();
        int id = 0;

        while (0 != connection) {
            int current_load_size = connection <= re_load_size ? connection : re_load_size;
            connection -= current_load_size;

            int cloudletLength = Math.max(1, Double.valueOf(Math.ceil(per_length * current_load_size * per_qps * coefficient)).intValue());

            ContainerCloudlet containerCloudlet = new ContainerCloudlet(id++, cloudletLength, first.getNumberOfPes()
                    , first.getCloudletFileSize(), first.getCloudletOutputSize(), first.getUtilizationModelCpu()
                    , first.getUtilizationModelRam(), first.getUtilizationModelBw());
            containerCloudlet.setUserId(first.getUserId());
            containerCloudlet.setVmId(first.getVmId());
            containerCloudlet.setCpurequest(1);
            containerCloudlet.setBwrequest(first.getBwrequest());
            containerCloudlet.setMemoryrequest(1);
            containerCloudlet.setQps(6.5);
            containerCloudlet.setState(1);
            containerCloudlet.setOperation(Float.valueOf(current_load_size * per_qps * coefficient).intValue());
            containerCloudlet.setStarttime(time);

            loads.add(containerCloudlet);
        }

//        System.err.println(String.format("connection: %d\tloads: %d", current_load.size(), loads.size()));
        return loads;
    }

//    private List<ContainerCloudlet> loadOperation(List<ContainerCloudlet> cloudletList, int time) {
//        //选出当前产生的负载
//        List<ContainerCloudlet> current_load = cloudletList.stream()
//                .filter(cloudlet -> cloudlet.getStarttime() == time && cloudlet.getState() == 1)
//                .collect(Collectors.toList());
//
//        int connection = current_load.size();
//
//        if (0 == connection) {
//            return current_load;
//        }
//
//        ContainerCloudlet first = current_load.get(0);
//
//
//        //每个操作执行时间：1000/model.getMax_qps()
//        //每个连接占用资源：100/model.getMax_connection()
//
//        //按占用1个单位资源重新分配，则每个请求是model.getMax_connection()/100个请求
//        List<ContainerCloudlet> loads = Lists.newArrayListWithExpectedSize(10);
//
//        //Double.valueOf(model.getMax_qps() / (per_qps * 1000)).intValue();//
//        int re_load_size = this.model.getMax_connection() / 100;
//        double per_length = (double) 1000 / model.getMax_qps();
//        int per_operation = Double.valueOf((double)3/per_length).intValue();
//        int total_operation = Float.valueOf(connection * per_qps).intValue();
//        int id = 0;
//
//        Random random = new Random();
//        int start  = 1;
//        while (total_operation > 0) {
//            int left_length = Double.valueOf(Math.ceil((double)total_operation * per_length)).intValue();
//            int cloudletLength = start++;
//            if (cloudletLength > 5) {
//                cloudletLength = 1;
//                start= 1;
//            }
//            if (cloudletLength > left_length) {
//                cloudletLength = left_length;
//            }
//
//            int current_operation = Double.valueOf((double)cloudletLength/per_length).intValue();
//            total_operation -= current_operation;
//
//            int cpu = cloudletLength+3;//random.nextInt(8) + 1;
//
//            ContainerCloudlet containerCloudlet = new ContainerCloudlet(id++, cloudletLength, first.getNumberOfPes()
//                    , first.getCloudletFileSize(), first.getCloudletOutputSize(), first.getUtilizationModelCpu()
//                    , first.getUtilizationModelRam(), first.getUtilizationModelBw());
//            containerCloudlet.setUserId(first.getUserId());
//            containerCloudlet.setVmId(first.getVmId());
//            containerCloudlet.setCpurequest(cpu);
//            containerCloudlet.setBwrequest(first.getBwrequest());
//            containerCloudlet.setMemoryrequest(cpu);
//            containerCloudlet.setQps(per_qps);
//            containerCloudlet.setState(1);
//            containerCloudlet.setOperation(current_operation);
//            containerCloudlet.setStarttime(time);
//
//            loads.add(containerCloudlet);
//        }
//
////        System.err.println(String.format("connection: %d\tloads: %d", current_load.size(), loads.size()));
//        return loads;
//    }

//    private List<ContainerCloudlet> loadOperation(List<ContainerCloudlet> cloudletList, int time) {
//        //选出当前产生的负载
//       return cloudletList.stream().map(cloudlet->{
//           long cloudletLength = cloudlet.getCloudletLength();
//           cloudletLength = cloudletLength/2 < 1? 1:cloudletLength/2;
//           int cpu = cloudlet.getCpurequest();
////           cpu = Math.max(cpu / 2, 1);
//           ContainerCloudlet containerCloudlet = new ContainerCloudlet(cloudlet.getCloudletId(), cloudletLength, cloudlet.getNumberOfPes()
//                   , cloudlet.getCloudletFileSize(), cloudlet.getCloudletOutputSize(), cloudlet.getUtilizationModelCpu()
//                   , cloudlet.getUtilizationModelRam(), cloudlet.getUtilizationModelBw());
//           containerCloudlet.setUserId(cloudlet.getUserId());
//           containerCloudlet.setVmId(cloudlet.getVmId());
//           containerCloudlet.setCpurequest(cpu);
//           containerCloudlet.setBwrequest(cloudlet.getBwrequest());
//           containerCloudlet.setMemoryrequest(cpu);
//           containerCloudlet.setQps(per_qps);
//           containerCloudlet.setState(cloudlet.getState());
//           containerCloudlet.setOperation(80);
//           containerCloudlet.setStarttime(cloudlet.getStarttime());
//           return containerCloudlet;
//       }).collect(Collectors.toList());
//    }

//    private List<ContainerCloudlet> loadOperation(List<ContainerCloudlet> cloudletList, int time) {
//        //选出当前产生的负载
//        List<ContainerCloudlet> current_load = cloudletList.stream()
//                .filter(cloudlet -> cloudlet.getStarttime() == time && cloudlet.getState() == 1)
//                .collect(Collectors.toList());
//
//        int connection = current_load.size();
//
//        if (0 == connection) {
//            return current_load;
//        }
//
//        ContainerCloudlet first = current_load.get(0);
//
//
//        //每个操作执行时间：1000/model.getMax_qps()
//        //每个连接占用资源：100/model.getMax_connection()
//
//        //按占用1个单位资源重新分配，则每个请求是model.getMax_connection()/100个请求
//        List<ContainerCloudlet> loads = Lists.newArrayListWithExpectedSize(10);
//
//        //Double.valueOf(model.getMax_qps() / (per_qps * 1000)).intValue();//
//        int re_load_size = this.model.getMax_connection() / 100;
//        double per_length = (double) 1000 / model.getMax_qps();
//        int per_operation = Double.valueOf((double)3/per_length).intValue();
//        int total_operation = Float.valueOf(connection * per_qps).intValue();
//        int id = 0;
//
//        Random random = new Random();
//        while (total_operation > 0) {
//            int left_length = Double.valueOf(Math.ceil((double)total_operation * per_length)).intValue();
//            int cloudletLength = random.nextInt(5)+1;
//            if (cloudletLength > left_length) {
//                cloudletLength = left_length;
//            }
//
//            int current_operation = Double.valueOf((double)cloudletLength/per_length).intValue();
//            total_operation -= current_operation;
//
//            int cpu = cloudletLength+3;//random.nextInt(8) + 1;
//
//            ContainerCloudlet containerCloudlet = new ContainerCloudlet(id++, cloudletLength, first.getNumberOfPes()
//                    , first.getCloudletFileSize(), first.getCloudletOutputSize(), first.getUtilizationModelCpu()
//                    , first.getUtilizationModelRam(), first.getUtilizationModelBw());
//            containerCloudlet.setUserId(first.getUserId());
//            containerCloudlet.setVmId(first.getVmId());
//            containerCloudlet.setCpurequest(cpu);
//            containerCloudlet.setBwrequest(first.getBwrequest());
//            containerCloudlet.setMemoryrequest(cpu);
//            containerCloudlet.setQps(per_qps);
//            containerCloudlet.setState(1);
//            containerCloudlet.setOperation(current_operation);
//            containerCloudlet.setStarttime(time);
//
//            loads.add(containerCloudlet);
//        }
//
////        System.err.println(String.format("connection: %d\tloads: %d", current_load.size(), loads.size()));
//        return loads;
//    }

    private void updateCoefficient(int time) {
        double current_response_time = siemensList.getAverageresponsetimelist().get(time);

        if (0 == current_operation || 0 == pre_operation
                || current_response_time / current_operation <= pre_response_time / pre_operation) {
            coefficient = 1.0f;
        } else {
            coefficient = 0.9f;
        }

        this.pre_operation = current_operation;
        this.pre_response_time = current_response_time;
    }

    private int getResource(int connection) {
        return Math.max(1, Double.valueOf((double) 100 / model.getMax_connection() * connection).intValue());
    }


}