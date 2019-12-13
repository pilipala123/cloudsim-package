package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.EcsInput;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.InputParament.SlbInput;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;

import java.io.*;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;

public class ServiceLoadBalancerGW extends Host {

    private int responseTime;
    private int qps;

    private SiemensList slbsiemensList;
    public SiemensList getSlbsiemensList() {
        return slbsiemensList;
    }

    public void setSlbsiemensList(SiemensList slbsiemensList) {
        this.slbsiemensList = slbsiemensList;
    }

    /**
     * Creates a new Container-based Redis object.
     *
     * @param id
     * @param userId
     * @param mips
     * @param numberOfPes
     * @param ram
     * @param bw
     */
    public ServiceLoadBalancerGW(int id,
                                 int userId,
                                 double mips,
                                 int numberOfPes,
                                 int ram,
                                 long bw,
                                 List<ContainerCloudlet> cloudletList,
                                 SlbInput slbInput,
                                 EcsInput ecsInput,
                                 LoadGeneratorInput loadGeneratorInput,
                                 RegressionParament regressionParament,
                                 int flag){
        super(id);

        /**
         * Functions to calculate response time and qps will be added here
         */
        this.slbsiemensList=null;
        try {

            this.slbsiemensList = processRequests(cloudletList,1000,1000,"SLB",loadGeneratorInput,18,9);
            Calculatebw.calculateregressionbw("slb","k8s",flag,regressionParament,this.slbsiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.slbsiemensList.getFinishtime());

        System.out.println("The GWSLB QPS is "+qps);
    }
    public int getResponseTime(){
        return responseTime;
    }

    public void setResponseTime(int responseTime){
        this.responseTime = responseTime;
    }

    public int getQps(){

        return qps;
    }

    public void setQps(int money){
        if (money ==0){
            this.qps = 1000;
        }
        else {
            double qpscaculate = Double.valueOf(money)*0.263;
            this.qps =(int)(qpscaculate) ;
        }
    }




}
