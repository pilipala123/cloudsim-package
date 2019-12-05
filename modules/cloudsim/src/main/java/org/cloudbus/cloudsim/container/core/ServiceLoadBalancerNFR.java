package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.container.schedulers.ContainerCloudletScheduler;

import java.util.List;

public class ServiceLoadBalancerNFR extends Container{

    private int responseTime;

    private int qps;
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
    public ServiceLoadBalancerNFR(int id, int userId, double mips, int numberOfPes, int ram, long bw, List<ContainerCloudlet> cloudletList) {
        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
        /**
         * Functions to calculate response time and qps will be added here
         */
        processRequests(cloudletList);
        setResponseTime(100);
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
    public void processRequests(List<ContainerCloudlet> cloudletList){

    }

}
