package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Cloudlet;

import java.util.ArrayList;
import java.util.List;



public class GW_K8S extends Container{

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
    public GW_K8S(int id, int userId, double mips, int numberOfPes, int ram, int k8smoneycost, long bw, List<ContainerCloudlet> cloudletList) {
        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setResponseTime(processRequests(cloudletList,bw));
        setQps(k8smoneycost);
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
    public int processRequests(List<ContainerCloudlet> cloudletList,long bw){
        int response_time=0;
        int base_response_time=10;
        long bwResource = bw;
        int time = 0;
        int id = 0;
        ArrayList<Integer> delaycloudlistid = new ArrayList<>();

        for(Cloudlet cloudlet:cloudletList){
//            while(true) {
//                if (bwResource < 0.1*cloudlet.getCloudletLength()) {
//                    delaycloudlistid.add(cloudlet.getCloudletId());
//                    break;
//                }
//            }
//            for (int i : delaycloudlistid) {
//
//            }
            bwResource = bwResource-(long)0.1*cloudlet.getCloudletLength();
            response_time = (int)(response_time + 0.5*cloudlet.getCloudletLength()/base_response_time);

        }
        System.out.println("The Response Time of k8sGw is " + response_time + "ms");
        return response_time;
    }

}
