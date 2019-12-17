package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.EcsInput;
import org.cloudbus.cloudsim.container.InputParament.K8sInput;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;
import org.cloudbus.cloudsim.container.core.util.SiemensUtils;

import java.io.FileNotFoundException;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;


public class GW_K8S extends Host {

    private int responseTime;

    private int qps;

    private SiemensList k8ssiemensList;

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
    public GW_K8S(int id,List<ContainerCloudlet> cloudletList,int loadnumber,int ramp_down) {
        super(id);
        int containernumber = 18;
        int vmnumber =9;
        int cpuresources= 550;
        int memoryresources = 550;
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
        System.out.println("The GWK8s QPS is "+qps);
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

    /**
     * Process the requests and generate response time
     * @param cloudletList
     */
    public void process(List<ContainerCloudlet> cloudletList,
                     int flag,
                     RegressionParament regressionParament,
                     LoadGeneratorInput loadGeneratorInput,
                        int time){
        try {

            this.k8ssiemensList = processRequests(cloudletList,cpuresources,memoryresources,
                    "K8s",loadGeneratorInput,containernumber,vmnumber,100,
                    100,time,this.k8ssiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.k8ssiemensList.getFinishtime());
    }


}
