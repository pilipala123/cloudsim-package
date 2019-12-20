package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.*;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;
import org.cloudbus.cloudsim.container.core.util.SiemensUtils;

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
        setQps(8.8);
        int containernumber = k8sInput.getK8scontainernumber();
        int vmnumber =k8sInput.getECSNumbers();
        double cpucore = k8sInput.getK8scpucore();
        int maxqps = k8sInput.getK8smaxqps();
        int networkbandwidth = k8sInput.getK8snetworkbandwidth();
        int ecscore = ecsInput.getEcsCPUQuota();
        int ecspermips = ecsInput.getEcsMIPSpercore();
        double cpunumber = 125.5;
        System.out.println(containernumber+" "+vmnumber+" "+maxqps+" "+networkbandwidth+" "+cpucore);
        int cpuresources= (int)(ecscore*cpunumber);
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
        setK8ssiemensList(new SiemensList(cloudletList,containernumber,vmnumber,
                cpuresources,memoryresources,loadnumber,ramp_down));

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
                     int flag,
                     RegressionParament regressionParament,
                     LoadGeneratorInput loadGeneratorInput,
                        AdjustParament adjustParament,
                        int time){
        try {
            int mipsparament = adjustParament.getK8smipsparament();
            int mipsability = getMipsability();
            double mips = (double)mipsability/(double)mipsparament;
            int responsetimeparament = adjustParament.getK8sresponsetimeparament();
            this.k8ssiemensList = processRequests(cloudletList,cpuresources,memoryresources,
                    "K8s",loadGeneratorInput,containernumber,vmnumber,mips,
                    responsetimeparament,time,this.k8ssiemensList,this.qps);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.k8ssiemensList.getFinishtime());
    }


}
