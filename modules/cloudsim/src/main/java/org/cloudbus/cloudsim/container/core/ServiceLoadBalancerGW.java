package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.EcsInput;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.InputParament.SlbInput;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;
import org.yunji.cloudsimrd.load.LoadGenerator;

import java.io.*;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;

public class ServiceLoadBalancerGW extends Host {

    private int responseTime;
    private int qps;
    private int cpuresources;

    private int memoryresources;

    private int containernumber;

    private int vmnumber;

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

     */
    public ServiceLoadBalancerGW(int id,List<ContainerCloudlet> cloudletList,int loadnumber,int ramp_down){
        super(id);
        int containernumber = 18;
        int vmnumber =9;
        int cpuresources= 1000;
        int memoryresources = 1000;
        setCpuresources(cpuresources);
        setMemoryresources(memoryresources);
        setContainernumber(containernumber);
        setVmnumber(vmnumber);

        /**
         * Functions to calculate response time and qps will be added here
         */
        setSlbsiemensList(new SiemensList(cloudletList,containernumber,vmnumber,
                cpuresources,memoryresources,loadnumber,ramp_down));

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

//    public void setQps(int money){
//        if (money ==0){
//            this.qps = 1000;
//        }
//        else {
//            double qpscaculate = Double.valueOf(money)*0.263;
//            this.qps =(int)(qpscaculate) ;
//        }
//    }

    public void process(List<ContainerCloudlet> cloudletList,
                               int flag,
                               RegressionParament regressionParament,
                               LoadGeneratorInput loadGeneratorInput,
                        int time){
        try {

            this.slbsiemensList = processRequests(cloudletList,cpuresources,memoryresources,
                    "SLB",loadGeneratorInput,containernumber,vmnumber,
                    100,100,time,this.slbsiemensList);
//            Calculatebw.calculateregressionbw("slb","k8s",flag,regressionParament,this.slbsiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.slbsiemensList.getFinishtime());
    }




}
