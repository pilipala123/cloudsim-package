package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.*;
import org.cloudbus.cloudsim.container.core.Siemens.*;

import java.io.FileNotFoundException;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;


public class GW_K8S extends Host {

    private int responseTime;

    public void setQps(double qps) {
        this.qps = qps;
    }

    private double qps;

    private SiemensList k8ssiemensList;

    private int cpuresources;

    private int bwresources;

    private int containernumber;
    private double qpsthreshold;
    private double qpsratio;
    private double responsetimethreshold;
    private double responsetimeratio;

    private int vmnumber;
    private int mipsability;
    private int networkbandwidth;

    public int getNetworkbandwidth() {
        return networkbandwidth;
    }

    public void setNetworkbandwidth(int networkbandwidth) {
        this.networkbandwidth = networkbandwidth;
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

    public int getBwresources() {
        return bwresources;
    }

    public void setBwresources(int bwresources) {
        this.bwresources = bwresources;
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

    public SiemensList getK8ssiemensList() {
        return k8ssiemensList;
    }

    public void setK8ssiemensList(SiemensList k8ssiemensList) {
        this.k8ssiemensList = k8ssiemensList;
    }
    /**
     * Creates a new Container-based Redis object.
     *
     * @param id
     */
    public GW_K8S(int id, List<ContainerCloudlet> cloudletList, int loadnumber, int ramp_down,
                  AdjustParament adjustParament, EcsInput ecsInput,K8sInput k8sInput) {
        super(id);
        double qps = k8sInput.getK8sqpsperload();
        this.qpsthreshold = k8sInput.getK8sqpsthreshold();
        this.qpsratio = k8sInput.getK8sqpsratio();
        this.responsetimethreshold = k8sInput.getK8sresponsetimethreshold();
        this.responsetimeratio = k8sInput.getK8sresponsetimeratio();
        System.out.println(qps+" "+qpsthreshold+" "+qpsratio+" "+responsetimeratio+" "+responsetimethreshold);
        setQps(qps);
        int containernumber = k8sInput.getK8scontainernumber();
        int vmnumber =k8sInput.getECSNumbers();
        double cpucore = k8sInput.getK8scpucore();
        int maxqps = k8sInput.getK8smaxqps();
        this.networkbandwidth = k8sInput.getK8snetworkbandwidth();
        int ecscore = ecsInput.getEcsCPUQuota();
        int ecspermips = ecsInput.getEcsMIPSpercore();
        double cpunumber = adjustParament.getK8scpuparament();
        double bwnubmer = adjustParament.getK8sbwparament();
        System.out.println(containernumber+" "+vmnumber+" "+maxqps+" "+networkbandwidth+" "+cpucore);
        int cpuresources= (int)(ecscore*cpunumber*(double)vmnumber/(double)containernumber);
        int bwresources = (int)(networkbandwidth*bwnubmer/(double)containernumber);
        int mipsability = 100;
        setMipsability(mipsability);
        setCpuresources(cpuresources);
        setBwresources(bwresources);
        setContainernumber(containernumber);
        setVmnumber(vmnumber);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setK8ssiemensList(new SiemensList(cloudletList,containernumber,vmnumber,
                cpuresources,bwresources,loadnumber,ramp_down));
        this.k8ssiemensList.setState(1);

//        setQps(k8smoneycost);
//        System.out.println("The GWK8s QPS is "+qps);
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

//    public void setQps(int money){
//        if (money ==0){
//            this.qps = 1000;
//        }
//        else {
//            double qpscaculate = Double.valueOf(money)*0.263;
//            this.qps =(int)(qpscaculate) ;
//        }
//    }

    /**
     * Process the requests and generate response time
     * @param cloudletList
     */
    public void process(List<ContainerCloudlet> cloudletList,
                        AdjustParament adjustParament,
                        int time){
        try {
            int mipsparament = adjustParament.getK8smipsparament();
            int mipsability = getMipsability();
            double mips = (double)mipsability/(double)mipsparament;
            int responsetimeparament = adjustParament.getK8sresponsetimeparament();
            this.k8ssiemensList = processRequests(cloudletList,cpuresources, bwresources,
                    "Gw_K8s",this.k8ssiemensList,containernumber,vmnumber,mips,
                    responsetimeparament,time,this.qps,this.qpsratio,this.qpsthreshold,
                    this.responsetimethreshold,this.responsetimeratio,this.networkbandwidth);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.k8ssiemensList.getFinishtime());
    }


}
