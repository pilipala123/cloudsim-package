package org.cloudbus.cloudsim.container.load;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.container.InputParament.AdjustParament;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;
import org.cloudbus.cloudsim.container.utils.IDs;
import org.yunji.cloudsimrd.load.LoadGenerator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
//import org.yunji.cloudsimrd.Constants;
//import org.yunji.cloudsimrd.PropertiesUtil;
//import sun.plugin2.gluegen.runtime.CPU;


/**
 * @author weirenjie
 * @date 2019/10/22
 */


/**
 * 负载生成器，仿真负载。
 */
public class LoadGeneratorMDSP {
    private String propertiesPath = "D:/cloudsim-5.0/cloudsim-5.0/modules/cloudsim/src/main/java/org/cloudbus/cloudsim/container/load/load.properties";

    private int brokeId = 1;
    private int vmId = 0;

    private int rating = 1;

    private String defaultUrl = "www.baidu.com";



    public List<ContainerCloudlet> generateContainerCloudletsFromList(LoadGeneratorInput loadGeneratorInput) {
        System.out.println("Load Generator: generating loads......\n");
        List<ContainerCloudlet> resultList = new ArrayList<>();
        int number = loadGeneratorInput.getLoadnumbers();
        int duration = loadGeneratorInput.getLoadduration();
        int ramp_up = loadGeneratorInput.getRamp_up();
        int endtime = loadGeneratorInput.getRamp_down();
        double timerange = (double)number/(double)ramp_up;
        loadGeneratorInput.setTimerange(timerange);
        resultList.addAll(generateContainerCloudlets(number,endtime,timerange,loadGeneratorInput));
        return resultList;
    }


    public List<ContainerCloudlet> generateContainerCloudlets(Integer number,int endtime,double timerange,LoadGeneratorInput loadGeneratorInput) {
        long fileSize = 300;
        long outputSize = 300;
        int basetime = 1;
        int id = 0;
        // number of cpus
        int pesNumber = 1;

        int cloudletcpurequest = loadGeneratorInput.getCloudletcpurequest();
        int cloudletbwrequest = loadGeneratorInput.getCloudletmemoryrequest();
        int cloudletcpurandom = loadGeneratorInput.getCpurandomnumber();
        int cloudletbwrandom = loadGeneratorInput.getBwrandomnumber();
        int cloudletlength = loadGeneratorInput.getCloudletlength();
        int cloudletlengthrandom = loadGeneratorInput.getLengthrandomnumber();
        double packetsizerandom = loadGeneratorInput.getPacketsizerandom();
        double basepacketsize = loadGeneratorInput.getBasepacketsize();
        UtilizationModel utilizationModel = new UtilizationModelFull();
        List<ContainerCloudlet> containerCloudlets = new ArrayList<>();
        int clocktime = 1;
        for(int i=0;i<number;i++){

            long length = (cloudletlength + (int) (Math.random() * cloudletlengthrandom)) * basetime;
            ContainerCloudlet cloudlet =
                    new ContainerCloudlet(id, length, pesNumber, fileSize,
                            outputSize, utilizationModel, utilizationModel,
                            utilizationModel);
            cloudlet.setUserId(brokeId);
            cloudlet.setVmId(vmId);
            cloudlet.setCloudletLength(length);
            int cpurequest = (int) (Math.random() * cloudletcpurandom) + cloudletcpurequest;     //单个任务的cpu需求
            int bwrequest = (int) (Math.random() * cloudletbwrandom) + cloudletbwrequest;//单个任务的带宽需求
            double packetsize =basepacketsize;
            cloudlet.setCpurequest(cpurequest);
            cloudlet.setBwrequest(bwrequest);
            cloudlet.setPacketsize(packetsize);
            cloudlet.setQps(6.5);
            cloudlet.setState(0);   //waiting state
            containerCloudlets.add(cloudlet);
        }
        return containerCloudlets;

    }
}
