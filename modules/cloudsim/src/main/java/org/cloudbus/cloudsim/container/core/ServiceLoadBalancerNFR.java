package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
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

    private int qps;

    private SiemensList nfrsiemensList;
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
    public ServiceLoadBalancerNFR(int id,List<ContainerCloudlet> cloudletList,int loadnumber,int ramp_down) {
        super(id);
        int containernumber = 18;
        int vmnumber =9;
        int cpuresources= 700;
        int memoryresources = 600;
        setCpuresources(cpuresources);
        setMemoryresources(memoryresources);
        setContainernumber(containernumber);
        setVmnumber(vmnumber);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setNfrsiemensList(new SiemensList(cloudletList,containernumber,vmnumber,
                cpuresources,memoryresources,loadnumber,ramp_down));

        setQps(100);
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

    public void setQps(int qps){
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
                        int time){
        try {

            this.nfrsiemensList = processRequests(cloudletList,cpuresources,
                    memoryresources,"NFR",loadGeneratorInput,containernumber,
                    vmnumber,100,100,time,this.nfrsiemensList);
//            Calculatebw.calculateregressionbw("slb","k8s",flag,regressionParament,this.nfrsiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.nfrsiemensList.getFinishtime());
    }

}
