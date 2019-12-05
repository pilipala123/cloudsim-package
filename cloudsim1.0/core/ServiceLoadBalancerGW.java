package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.lists.CloudletList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceLoadBalancerGW extends Container{

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
    public ServiceLoadBalancerGW(int id, int userId, double mips, int numberOfPes, int ram, int money, long bw, List<ContainerCloudlet> cloudletList) {
        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
        /**
         * Functions to calculate response time and qps will be added here
         */
        ;
        setResponseTime(processRequests(cloudletList,numberOfPes));
        setQps(money);
        System.out.println("The GWSLB QPS is "+qps);
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
    public int processRequests(List<ContainerCloudlet> cloudletList,int numberOfPes){
        int response_time=0;
        int base_response_time=10;
        int peResources = numberOfPes;
        int time = 0;
        int id = 0;
        ArrayList<Integer> delaycloudlistid = new ArrayList<>();

        for(Cloudlet cloudlet:cloudletList){
//            while(true) {
//                if (peResources < cloudlet.getNumberOfPes()) {
//                    delaycloudlistid.add(cloudlet.getCloudletId());
//                    break;
//                }
//            }
//            for (int i : delaycloudlistid) {
//
//            }
            peResources = peResources-cloudlet.getNumberOfPes();
            response_time = (int)(response_time + cloudlet.getCloudletLength()/base_response_time);

        }
        System.out.println("The Response Time of GWslb is "+ response_time+"ms");
        return response_time;
    }



}
