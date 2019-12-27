package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.AdjustParament;
import org.cloudbus.cloudsim.container.InputParament.EcsInput;
import org.cloudbus.cloudsim.container.InputParament.NfrInput;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

public class ServiceLoadBalancerNFR extends Host {

    private int responseTime;
    private String name;
    private double qps;

    private int cpuresources;

    private int memoryresources;

    private int containernumber;

    private double qpsthreshold;
    private double qpsratio;
    private double responsetimethreshold;
    private double responsetimeratio;

    private int vmnumber;
    private int mipsability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    /**
     * Creates a new Container-based Redis object.
     *
     * @param id
     */
    public ServiceLoadBalancerNFR(int id, List<ContainerCloudlet> cloudletList, int loadnumber, int ramp_down,
                                  AdjustParament adjustParament, EcsInput ecsInput, NfrInput nfrInput ) {
        super(id);
        double qps = nfrInput.getNfrqpsperload();
        this.qpsthreshold = nfrInput.getNfrqpsthreshold();
        this.qpsratio = nfrInput.getNfrqpsratio();
        this.responsetimethreshold = nfrInput.getNfrresponsetimethreshold();
        this.responsetimeratio = nfrInput.getNfrresponsetimeratio();
        System.out.println(qps+" "+qpsthreshold+" "+qpsratio+" "+responsetimeratio+" "+responsetimethreshold);
        setQps(qps);
        int containernumber = nfrInput.getNfrcontainernumber();
        int vmnumber =nfrInput.getNfrECSnumber();
        int maxqps = nfrInput.getNfrmaxqps();
        int networkbandwidth = nfrInput.getNfrnetworkbandwidth();
        double cpucore = nfrInput.getCpucore();
        int ecscore = ecsInput.getEcsCPUQuota();
        double ecsmips = ecsInput.getEcsMIPSpercore();
        System.out.println(containernumber+" "+vmnumber+" "+maxqps+" "+networkbandwidth+" "+cpucore);
        double cpunumber=adjustParament.getNfrcpuparament();
        int cpuresources= (int)(cpunumber*ecscore*(double)vmnumber/(double)containernumber);
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
        setSiemensList(new SiemensList(cloudletList,containernumber,vmnumber,
                cpuresources,memoryresources,loadnumber,ramp_down));

//        setQps(100);
    }
    public ServiceLoadBalancerNFR(int id,String name,int responseTime){
        super(id);
        setResponseTime(responseTime);
        setSiemensList(new SiemensList());
        getSiemensList().setAverageresponsetimelist(new ArrayList<>());
        getSiemensList().setQpslist(new ArrayList<>());
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

    public void processEvent(int loadpernumber,int time) {
        super.processEvent(loadpernumber,time);
        String label = "Slb_NFR";
        double qpsbase = this.qps;
        getSiemensList().setName(this.name);
        getSiemensList().getQpslist().add(qpsbase * (double) loadpernumber);
        getSiemensList().getAverageresponsetimelist().add((double)this.responseTime);
        System.out.println("time: "+time+ "s "+ getSiemensList().getName()+": average response time is "+ this.responseTime+"ms");

//    }
//    /**
//     * Process the requests and generate response time
//     * @param cloudletList
//     */
//    public void processEvent(List<ContainerCloudlet> cloudletList,
//                        AdjustParament adjustParament,
//                        int time){
//        try {
//            int mipsparament = adjustParament.getNfrmipsparament();
//            int mipsability = getMipsability();
//            double mips = (double)mipsability/(double)mipsparament;
//            int responsetimeparament = adjustParament.getNfrresponsetimeparament();
//            this.siemensList = processRequests(cloudletList,cpuresources,
//                    memoryresources,"NFR",this.siemensList,containernumber,
//                    vmnumber,mips,responsetimeparament, time,this.qps,this.qpsratio,
//                    this.qpsthreshold, this.responsetimethreshold,this.responsetimeratio,2,200);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        setResponseTime(this.siemensList.getFinishtime());
//    }
    }
}
