package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.container.core.Siemens.BindContianer;
import org.cloudbus.cloudsim.container.core.Siemens.CloudletMinParament;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensContainerresource;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensVmresources;

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


    public List<SiemensContainerresource> createVmResource(int containernumber, int vmnumber, int cpuresources, int bwresources, int maxlength){
        List<SiemensContainerresource> siemensContainerresourceList =new ArrayList<> ();
        int containereveryvm = (int)containernumber/vmnumber;
        for(Integer i=0;i<containernumber;i++){
            SiemensContainerresource siemensContainerresource = new SiemensContainerresource();
            siemensContainerresource.setId(i);
            siemensContainerresource.initCpuarraypool(cpuresources,maxlength);
            siemensContainerresource.initBwarraypool(bwresources,maxlength);
            siemensContainerresource.setSiemensVmid((int)(i/containereveryvm));
            siemensContainerresourceList.add(siemensContainerresource);

        }
        return siemensContainerresourceList;
    }


    /**
     * Process the requests and generate response time
     * @param cloudletList
     */
    public List<BindContianer> bindCloudlet(List<ContainerCloudlet> cloudletList){
        List<BindContianer> bindContianerList = new ArrayList<>();
        int containerid = 0;

        for (ContainerCloudlet cloudlet:cloudletList){
            BindContianer bindContianer = new BindContianer(cloudlet.getCpurequest(),
                    cloudlet.getBwrequest(),cloudlet.getCloudletId(),containerid,
                    cloudlet.getStarttime(),(int)cloudlet.getCloudletLength());
            bindContianerList.add(bindContianer);
            cloudlet.setContainerId(bindContianer.getId());
            containerid++;
        }
        return bindContianerList;
    }

    public List<SiemensVmresources> createsiemnesVmresources(int vmnumber){
        List<SiemensVmresources> siemensVmresourcesList = new ArrayList<>();
        for(int i=0;i<vmnumber;i++){
            SiemensVmresources siemensVmresources = new SiemensVmresources();
            siemensVmresources.setId(i);
            siemensVmresourcesList.add(siemensVmresources);
        }
        return siemensVmresourcesList;

    }


    public int processRequests(List<ContainerCloudlet> cloudletList,int cpuresources,int bwresources){
        int response_time=0;
        int containernumber=18;
        int vmnumber = 6;
        int containerhandletime;
        int time = 0;
        int containercpurequest=0,remaincpuresources=0;
        int containerbwrequest=0,remainbwresources=0;
        int vmfreenumber = 0,vmcpuusage=0,vmbwusage=0;
        int containercpuusage = 0,containerbwusage = 0;
        int finishcloudletnumber=0;
        CloudletMinParament cloudletMinParament = new CloudletMinParament();
        cloudletMinParament.setcloudletMinParament(cloudletList);
        List<SiemensVmresources> siemensVmresourcesList = createsiemnesVmresources(vmnumber);
        List<SiemensContainerresource> siemensContainerresourceList = createVmResource(containernumber,vmnumber,cpuresources,bwresources,cloudletMinParament.getMaxtimelength());

        List<BindContianer> bindCloudletlist =bindCloudlet(cloudletList);

        while (true) {

            vmfreenumber = 0;
            for (BindContianer bindContianer : bindCloudletlist) {
                if (bindContianer.getState() == 3) {
                    continue;
                }
                if (bindContianer.getStarttime()>time){
                    break;
                }
                for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                    siemensContainerresource.setRemaincpuresource(time);
                    siemensContainerresource.setRemainbwresource(time);
                    remaincpuresources = siemensContainerresource.getRemaincpuresource();
                    remainbwresources = siemensContainerresource.getRemainbwresource();

                    if (bindContianer.getState() == 0 && bindContianer.getStarttime() == time) {
                        bindContianer.setState(1);
                    }
                    if (bindContianer.getState() == 1 || bindContianer.getState() == 2) {

                        containercpurequest = bindContianer.getCpuusage();
                        containerbwrequest = bindContianer.getBwusage();
                        if ((remaincpuresources >= containercpurequest)
                                && (remainbwresources >= containerbwrequest)) {
                            containerhandletime = (int) bindContianer.getHandletime();
                            siemensContainerresource.changeCpuarraypool(remaincpuresources, containercpurequest, time, containerhandletime);
                            siemensContainerresource.changeBwarraypool(remainbwresources, containerbwrequest, time, containerhandletime);
                            bindContianer.setState(3);
                            bindContianer.setFinishtime(time);
                            finishcloudletnumber++;
                            break;
                        }

                        if ((remaincpuresources < cloudletMinParament.getMincpurequest()
                                || remainbwresources < cloudletMinParament.getMinbwrequest())
                                && (siemensContainerresource.getId()==containernumber-1)) {
                            bindContianer.setState(2);
                            break;
                        }
                    }

                }
            }
            for(SiemensVmresources siemensVmresources : siemensVmresourcesList) {
                siemensVmresources.setCpuusage(0);
                siemensVmresources.setBwusage(0);
                for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                    containercpuusage = 0;
                    containerbwusage = 0;
                    if(siemensContainerresource.getSiemensVmid()!= siemensVmresources.getId()){
                        continue;
                    }
                    for (int i = 0; i < cpuresources; i++) {
                        if (siemensContainerresource.getCpuarraypool()[i][time] == 1) {
                            containercpuusage++;
                        }
                        if (siemensContainerresource.getBwarraypool()[i][time] == 1) {
                            containerbwusage++;
                        }
                    }

                    siemensVmresources.addCpuusage(containercpuusage);
                    siemensVmresources.addBwusage(containerbwusage);


                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Cpu usage is " + containercpuusage);
                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Bw usage is " + containerbwusage);

                }
                vmcpuusage = siemensVmresources.getCpuusage();
                vmbwusage = siemensVmresources.getBwusage();
                if (vmcpuusage == 0 && vmbwusage == 0) {
                    vmfreenumber++;
                }
                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Cpu usage is " + vmcpuusage);
                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Bw usage is " + vmbwusage);

            }
            if (vmfreenumber == vmnumber) {
                break;
            }
            time++;

        }
        for(BindContianer bindContianer : bindCloudletlist){
            int cloudletstarttime = bindContianer.getStarttime();
            int cloudletfinishtime = bindContianer.getFinishtime();
        }

        System.out.println("finish cloudlet numbers is "+finishcloudletnumber);
        System.out.println("The response time of Siemens is "+time+"ms");
//        System.out.println("The Response Time of GWslb is "+ response_time+"ms");
        return time;
    }





}
