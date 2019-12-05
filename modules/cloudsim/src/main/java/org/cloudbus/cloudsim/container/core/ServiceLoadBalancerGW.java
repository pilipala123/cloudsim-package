package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.container.core.SLB.CloudletMinParament;
import org.cloudbus.cloudsim.container.core.SLB.SiemensContainerresource;

import java.util.ArrayList;
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
        setResponseTime(processRequests(cloudletList,100,100));
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


    public List<SiemensContainerresource> createVmResource(int vmnumber, int cpuresources, int bwresources, int maxlength){
        List<SiemensContainerresource> siemensContainerresourceList =new ArrayList<> ();
        for(Integer i=0;i<vmnumber;i++){
            SiemensContainerresource siemensContainerresource = new SiemensContainerresource();
            siemensContainerresource.setId(i);
            siemensContainerresource.initCpuarraypool(cpuresources,maxlength);
            siemensContainerresource.initBwarraypool(bwresources,maxlength);
            siemensContainerresourceList.add(siemensContainerresource);
        }
        return siemensContainerresourceList;
    }


    /**
     * Process the requests and generate response time
     * @param cloudletList
     */


    public int processRequests(List<ContainerCloudlet> cloudletList,int cpuresources,int bwresources){
        int response_time=0;
        int vmnumber=3;
        int cloudlethandletime;
        int time = 0;
        int cloudletcpurequest=0,remaincpuresources=0;
        int cloudletbwrequest=0,remainbwresources=0;
        int vmfreenumber = 0;
        int cpuusage = 0,bwusage = 0;
        int finishcloudletnumber=0;
        CloudletMinParament cloudletMinParament = new CloudletMinParament();
        cloudletMinParament.setcloudletMinParament(cloudletList);
        List<SiemensContainerresource> siemensContainerresourceList = createVmResource(vmnumber,cpuresources,bwresources,cloudletMinParament.getMaxtimelength());

        while (true) {

            vmfreenumber = 0;
            for (ContainerCloudlet cloudlet : cloudletList) {
                if (cloudlet.getState() == 3) {
                    continue;
                }
                if (cloudlet.getStarttime()>time){
                    break;
                }
                for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                    siemensContainerresource.setRemaincpuresource(time);
                    siemensContainerresource.setRemainbwresource(time);
                    remaincpuresources = siemensContainerresource.getRemaincpuresource();
                    remainbwresources = siemensContainerresource.getRemainbwresource();

                    if (cloudlet.getState() == 0 && cloudlet.getStarttime() == time) {
                        cloudlet.setState(1);
                    }
                    if (cloudlet.getState() == 1 || cloudlet.getState() == 2) {

                        cloudletcpurequest = cloudlet.getCpurequest();
                        cloudletbwrequest = cloudlet.getBwrequest();
                        if ((remaincpuresources >= cloudletcpurequest)
                                && (remainbwresources >= cloudletbwrequest)) {
                            cloudlethandletime = (int) cloudlet.getCloudletLength();
                            siemensContainerresource.changeCpuarraypool(remaincpuresources, cloudletcpurequest, time, cloudlethandletime);
                            siemensContainerresource.changeBwarraypool(remainbwresources, cloudletbwrequest, time, cloudlethandletime);
                            cloudlet.setState(3);
                            cloudlet.setFinishtime(time);
                            finishcloudletnumber++;
                            break;
                        }

                        if ((remaincpuresources < cloudletMinParament.getMincpurequest()
                                || remainbwresources < cloudletMinParament.getMinbwrequest())
                                && (siemensContainerresource.getId()==vmnumber-1)) {
                            cloudlet.setState(2);
                            break;
                        }
                    }

                }
            }
            for(SiemensContainerresource siemensContainerresource : siemensContainerresourceList){
                cpuusage = 0;
                bwusage = 0;
                for (int i = 0; i < cpuresources; i++) {
                    if (siemensContainerresource.getCpuarraypool()[i][time] == 1) {
                        cpuusage++;
                    }
                    if (siemensContainerresource.getBwarraypool()[i][time] == 1) {
                        bwusage++;
                    }
                }
                if (cpuusage==0&&bwusage==0){
                    vmfreenumber++;
                }
                System.out.println("At time:"+ time +"ms VM:"+ siemensContainerresource.getId()+" Cpu usage is "+cpuusage);
                System.out.println("At time:"+ time +"ms VM:"+ siemensContainerresource.getId()+ " Bw usage is "+bwusage);
            }
            if(vmfreenumber ==vmnumber){
                break;
            }

            time++;

        }
        for(Cloudlet cloudlet:cloudletList){
            int cloudletstarttime = cloudlet.getStarttime();
            int cloudletfinishtime = cloudlet.getFinishtime();
        }

        System.out.println("finish cloudlet numbers is "+finishcloudletnumber);
        System.out.println("The response time of SLB is "+time+"ms");
//        System.out.println("The Response Time of GWslb is "+ response_time+"ms");
        return time;
    }





}
