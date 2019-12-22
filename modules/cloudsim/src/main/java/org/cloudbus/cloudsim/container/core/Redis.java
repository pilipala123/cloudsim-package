package org.cloudbus.cloudsim.container.core;

import com.google.common.collect.Lists;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.AdjustParament;
import org.cloudbus.cloudsim.container.InputParament.EcsInput;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.InputParament.SlbInput;
import org.cloudbus.cloudsim.container.core.Siemens.RegressionParament;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.redis.Configuration;
import org.cloudbus.cloudsim.container.core.redis.ERedisModel;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;

public class Redis extends Host {

    private int max_connection;
    private int max_inbound_bandwidth;
    private int max_outbound_bandwidth;
    private int max_qps;
    private int max_ram;
    private ERedisModel model;

    private int avg_cpu;
    private int avg_ram;

    private int responseTime;
    private double qps;
    private int cpuresources;

    private int memoryresources;

    private int containernumber;

    private int vmnumber;

    private SiemensList siemensList;

    double pre_response_time = 0;
    float per_qps = 8f, coefficient = 1.0f;
    int pre_operation, current_operation = 0;

    public Redis(int id, List<ContainerCloudlet> cloudletList, int loadnumber, int ramp_down
            , AdjustParament adjustParament, EcsInput ecsInput, SlbInput slbInput) {
        super(id);
        setQps(8.8);
        int containernumber = 2;
        int vmnumber = 1;
        int cpuresources = 100;
        int memoryresources = 100;
        setCpuresources(cpuresources);
        setMemoryresources(memoryresources);
        setContainernumber(containernumber);
        setVmnumber(vmnumber);

        setSiemensList(new SiemensList(cloudletList, containernumber, vmnumber
                , cpuresources, memoryresources, loadnumber, ramp_down *2));
        init();
    }

    private void init() {
        this.max_inbound_bandwidth = Configuration.getProperty("redis.max.inbound.bandwidth", 1000);
        this.max_outbound_bandwidth = Configuration.getProperty("redis.max.outbound.bandwidth", 1000);

        this.model = ERedisModel.valueOf(Configuration.getProperty("redis.model", "STANDARDDOUBLE"));
        this.max_connection = this.model.getMax_connection();
        this.max_qps = this.model.getMax_qps();
        this.max_ram = Configuration.getProperty("redis.max.ram", 1024);

        this.avg_cpu = Math.max(1, 100 / this.max_connection);
        this.avg_ram = Math.max(1, this.max_ram / this.max_connection);
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

    public int getCpuresources() {
        return cpuresources;
    }

    public void setCpuresources(int cpuresources) {
        this.cpuresources = cpuresources;
    }

    public int getMemoryresources() {
        return memoryresources;
    }

    public void setMemoryresources(int memoryresources) {
        this.memoryresources = memoryresources;
    }

    public int getContainernumber() {
        return containernumber;
    }

    public void setContainernumber(int containernumber) {
        this.containernumber = containernumber;
    }

    public int getVmnumber() {
        return vmnumber;
    }

    public void setVmnumber(int vmnumber) {
        this.vmnumber = vmnumber;
    }

    public SiemensList getSiemensList() {
        return siemensList;
    }

    public void setSiemensList(SiemensList siemensList) {
        this.siemensList = siemensList;
    }

    public void process(List<ContainerCloudlet> cloudletList,
                        int flag,
                        RegressionParament regressionParament,
                        LoadGeneratorInput loadGeneratorInput,
                        AdjustParament adjustParament,
                        int time) {

        List<ContainerCloudlet> current_operation = loadOperation(cloudletList, time);
        try {
            siemensList = processRequests(current_operation, cpuresources, memoryresources, "redis"
                    , loadGeneratorInput, containernumber, vmnumber, 1, 1, time, siemensList, qps,0.8,1.05,65,1.18);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        updateCoefficient(time);

        setResponseTime(this.siemensList.getFinishtime());
    }

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