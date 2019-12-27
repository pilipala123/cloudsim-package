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


    /**
     * Creates a new Container-based Redis object.
     *
     * @param id
     */
    public GW_K8S(int id, List<ContainerCloudlet> cloudletList, int loadnumber, int ramp_down,
                 K8sInput k8sInput) {
        super(id);
        double qps = k8sInput.getK8sqpsperload();
        this.qpsthreshold = k8sInput.getK8sqpsthreshold();
        this.qpsratio = k8sInput.getK8sqpsratio();
        this.responsetimethreshold = k8sInput.getK8sresponsetimethreshold();
        this.responsetimeratio = k8sInput.getK8sresponsetimeratio();
//        System.out.println(qps+" "+qpsthreshold+" "+qpsratio+" "+responsetimeratio+" "+responsetimethreshold);
        setQps(qps);
        int containernumber = k8sInput.getK8scontainernumber();
        int vmnumber =k8sInput.getECSNumbers();
        double ecscpucore = k8sInput.getK8scpucore();
        int maxqps = k8sInput.getK8smaxqps();
        this.networkbandwidth = k8sInput.getK8snetworkbandwidth();
        double cpunumber = k8sInput.getK8scpuparament();
        double bwnubmer = k8sInput.getK8sbwparamnet();
//        System.out.println(containernumber+" "+vmnumber+" "+maxqps+" "+networkbandwidth+" "+ecscpucore);
        int cpuresources= (int)(ecscpucore*cpunumber*(double)vmnumber/(double)containernumber);
        int bwresources = (int)(networkbandwidth*bwnubmer/(double)containernumber);
        int mipsability = (int)k8sInput.getK8smipsability();
        setMipsability(mipsability);
        setCpuresources(cpuresources);
        setBwresources(bwresources);
        setContainernumber(containernumber);
        setVmnumber(vmnumber);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setSiemensList(new SiemensList(cloudletList,containernumber,vmnumber,
                cpuresources,bwresources,loadnumber,ramp_down));
        getSiemensList().setState(1);

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
    public void processEvent(List<ContainerCloudlet> cloudletList,
                             K8sInput k8sInput,
                             int time){
        super.processEvent(cloudletList,k8sInput,time);
        try {
            int mipsparament = k8sInput.getK8smipsparament();
            int mipsability = getMipsability();
            String name = k8sInput.getName();
            double mips = (double)mipsability/(double)mipsparament;
            double responsetimeratio2 = k8sInput.getResponsetimeratio2();
            double responsetimeparament = k8sInput.getK8sresponsetimeparament();
            setSiemensList(processRequests(cloudletList,cpuresources, bwresources,
                    name,getSiemensList(),containernumber,vmnumber,mips,
                    responsetimeparament,time,this.qps,this.qpsratio,this.qpsthreshold,
                    this.responsetimethreshold,this.responsetimeratio,responsetimeratio2,this.networkbandwidth)
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(getSiemensList().getFinishtime());
    }


}
