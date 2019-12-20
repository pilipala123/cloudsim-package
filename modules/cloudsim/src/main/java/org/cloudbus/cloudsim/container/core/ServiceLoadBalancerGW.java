package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.AdjustParament;
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
    private double qps;
    private int cpuresources;

    private int memoryresources;

    private int containernumber;

    private int vmnumber;

    private int mipsability;

    public int getMipsability() {
        return mipsability;
    }

    public void setMipsability(int mipsability) {
        this.mipsability = mipsability;
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
    public ServiceLoadBalancerGW(int id, List<ContainerCloudlet> cloudletList, int loadnumber, int ramp_down,
                                 AdjustParament adjustParament,EcsInput ecsInput,SlbInput slbInput){
        super(id);
        setQps(8.8);
        int containernumber = slbInput.getSlbcontainernumber();
        int vmnumber =slbInput.getSlbECSnumber();
        int maxqps = slbInput.getSlbmaxqps();
        int networkbandwidth = slbInput.getSlbnetworkbandwidth();
        int ecscore = ecsInput.getEcsCPUQuota();
        int ecspermips = ecsInput.getEcsMIPSpercore();
        double cpucore = slbInput.getSlbcpucore();
        double cpunumber = 125;
        System.out.println(containernumber+" "+vmnumber+" "+maxqps+" "+networkbandwidth+" "+cpucore);

        int cpuresources= (int)(ecscore*cpunumber);
        int memoryresources = 500;
        int mipsablility  =100;
        setMipsability(mipsablility);
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

    public double getQps(){

        return qps;
    }


    public void process(List<ContainerCloudlet> cloudletList,
                               int flag,
                               RegressionParament regressionParament,
                               LoadGeneratorInput loadGeneratorInput,
                        AdjustParament adjustParament,
                        int time){
        try {
            int mipsparament = adjustParament.getSlbmipsparament();
            int mipsability = getMipsability();
            double mips = (double)mipsability/(double)mipsparament;
            int responsetimeparament = adjustParament.getSlbresponsetimeparament();
            this.slbsiemensList = processRequests(cloudletList,cpuresources,memoryresources,
                    "SLB",loadGeneratorInput,containernumber,vmnumber,
                    mips,responsetimeparament,time,this.slbsiemensList,this.qps);
//            Calculatebw.calculateregressionbw("slb","k8s",flag,regressionParament,this.slbsiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.slbsiemensList.getFinishtime());
    }




}
