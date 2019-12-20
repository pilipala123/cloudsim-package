package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.AdjustParament;
import org.cloudbus.cloudsim.container.InputParament.EcsInput;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.InputParament.NfrInput;
import org.cloudbus.cloudsim.container.core.Siemens.RegressionParament;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;
import org.cloudbus.cloudsim.container.schedulers.ContainerCloudletScheduler;
import org.yunji.cloudsimrd.load.LoadGenerator;

import java.io.FileNotFoundException;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;

public class ServiceLoadBalancerNFR extends Host {

    private int responseTime;

    private double qps;

    private SiemensList nfrsiemensList;
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

    public SiemensList getNfrsiemensList() {
        return nfrsiemensList;
    }

    public void setNfrsiemensList(SiemensList nfrsiemensList) {
        this.nfrsiemensList = nfrsiemensList;
    }

    /**
     * Creates a new Container-based Redis object.
     *
     * @param id
     */
    public ServiceLoadBalancerNFR(int id, List<ContainerCloudlet> cloudletList, int loadnumber, int ramp_down,
                                  AdjustParament adjustParament, EcsInput ecsInput, NfrInput nfrInput ) {
        super(id);
        setQps(8.8);
        int containernumber = nfrInput.getNfrcontainernumber();
        int vmnumber =nfrInput.getNfrECSnumber();
        int maxqps = nfrInput.getNfrmaxqps();
        int networkbandwidth = nfrInput.getNfrnetworkbandwidth();
        double cpucore = nfrInput.getCpucore();
        int ecscore = ecsInput.getEcsCPUQuota();
        double ecsmips = ecsInput.getEcsMIPSpercore();
        System.out.println(containernumber+" "+vmnumber+" "+maxqps+" "+networkbandwidth+" "+cpucore);
        double cpunumber=125.4;
        int cpuresources= (int)cpunumber*ecscore;
        int memoryresources = 500;
        int mipsability = 100;
        setMipsability(mipsability);
        setCpuresources(cpuresources);
        setMemoryresources(memoryresources);
        setContainernumber(containernumber);
        setVmnumber(vmnumber);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setNfrsiemensList(new SiemensList(cloudletList,containernumber,vmnumber,
                cpuresources,memoryresources,loadnumber,ramp_down));

//        setQps(100);
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

    public void setQps(double qps){
        this.qps = qps;
    }

    /**
     * Process the requests and generate response time
     * @param cloudletList
     */
    public void process(List<ContainerCloudlet> cloudletList,
                        int flag,
                        RegressionParament regressionParament,
                        LoadGeneratorInput loadGeneratorInput,
                        AdjustParament adjustParament,
                        int time){
        try {
            int mipsparament = adjustParament.getNfrmipsparament();
            int mipsability = getMipsability();
            double mips = (double)mipsability/(double)mipsparament;
            int responsetimeparament = adjustParament.getNfrresponsetimeparament();
            this.nfrsiemensList = processRequests(cloudletList,cpuresources,
                    memoryresources,"NFR",loadGeneratorInput,containernumber,
                    vmnumber,mips,responsetimeparament,time,this.nfrsiemensList,this.qps);
//            Calculatebw.calculateregressionbw("slb","k8s",flag,regressionParament,this.nfrsiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.nfrsiemensList.getFinishtime());
    }

}
