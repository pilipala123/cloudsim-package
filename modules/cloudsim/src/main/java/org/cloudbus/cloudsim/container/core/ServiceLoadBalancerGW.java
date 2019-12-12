package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.io.FileNotFoundException;
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
    public ServiceLoadBalancerGW(int id, int userId, double mips, int numberOfPes, int ram, long bw, List<ContainerCloudlet> cloudletList) throws FileNotFoundException {
        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setResponseTime(processRequests(cloudletList,100,100));
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
    public List<BindContainer> bindCloudlet(List<ContainerCloudlet> cloudletList){
        List<BindContainer> bindContainerList = new ArrayList<>();
        int containerid = 0;

        for (ContainerCloudlet cloudlet:cloudletList){
            BindContainer bindContainer = new BindContainer(cloudlet.getCpurequest(),
                    cloudlet.getBwrequest(),cloudlet.getCloudletId(),containerid,
                    cloudlet.getStarttime(),(int)cloudlet.getCloudletLength());
            bindContainerList.add(bindContainer);
            cloudlet.setContainerId(bindContainer.getId());
            containerid++;
        }
        return bindContainerList;
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


    public int processRequests(List<ContainerCloudlet> cloudletList,int cpuresources,int bwresources) throws FileNotFoundException {
        int containernumber=12;
        int vmnumber = 6;
        int containerhandletime;
        int time = 0;
        int containercpurequest=0,remaincpuresources=0;
        int containerbwrequest=0,remainbwresources=0;
        int vmfreenumber = 0,vmcpuusage=0,vmbwusage=0;
        int containercpuusage = 0,containerbwusage = 0;
        int finishcloudletnumber=0,startcloudletnumber=0,lasttimestartcloudletnumber=0,presentstarttimecloudletnumber=0;
        int hostcpuusage=0,hostbwusage=0;

        SiemensList siemensList = new SiemensList();
        CloudletMinParament cloudletMinParament = new CloudletMinParament();
        cloudletMinParament.setcloudletMinParament(cloudletList,containernumber);
        List<SiemensVmresources> siemensVmresourcesList = createsiemnesVmresources(vmnumber);
        List<SiemensContainerresource> siemensContainerresourceList = createVmResource(containernumber,vmnumber,cpuresources,bwresources,cloudletMinParament.getMaxtimelength());

        List<BindContainer> bindCloudletlist =bindCloudlet(cloudletList);

        while (true) {
            startcloudletnumber=0;
            vmfreenumber = 0;
            for (BindContainer bindContainer : bindCloudletlist) {
                if (bindContainer.getState() == 3) {
                    continue;
                }
                if (bindContainer.getStarttime()>time){
                    break;
                }
                for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                    siemensContainerresource.setRemaincpuresource(time);
                    siemensContainerresource.setRemainbwresource(time);
                    remaincpuresources = siemensContainerresource.getRemaincpuresource();
                    remainbwresources = siemensContainerresource.getRemainbwresource();

                    if (bindContainer.getState() == 0 && bindContainer.getStarttime() == time) {
                        bindContainer.setState(1);
                    }
                    if (bindContainer.getState() == 1 || bindContainer.getState() == 2) {

                        containercpurequest = bindContainer.getCpuusage();
                        containerbwrequest = bindContainer.getBwusage();
                        if ((remaincpuresources >= containercpurequest)
                                && (remainbwresources >= containerbwrequest)) {
                            containerhandletime = (int) bindContainer.getHandletime();
                            siemensContainerresource.changeCpuarraypool(remaincpuresources, containercpurequest, time, containerhandletime);
                            siemensContainerresource.changeBwarraypool(remainbwresources, containerbwrequest, time, containerhandletime);
                            bindContainer.setState(3);
                            bindContainer.setFinishtime(time+containerhandletime);
                            bindContainer.setEveryresponsetime(bindContainer.getFinishtime()- bindContainer.getStarttime());
                            finishcloudletnumber++;
                            break;
                        }

                        if ((remaincpuresources < cloudletMinParament.getMincpurequest()
                                || remainbwresources < cloudletMinParament.getMinbwrequest())
                                && (siemensContainerresource.getId()==containernumber-1)) {
                            bindContainer.setState(2);
                            break;
                        }
                    }

                }
            }
            for (BindContainer bindContainer : bindCloudletlist) {
                if(bindContainer.getState()!=0){
                    startcloudletnumber++;
                }
            }
            presentstarttimecloudletnumber = startcloudletnumber-lasttimestartcloudletnumber;
            siemensList.getLoadnumber().add(presentstarttimecloudletnumber);
            lasttimestartcloudletnumber = startcloudletnumber;

            hostbwusage=hostcpuusage=0;
            //计算cpu和带宽利用率
            siemensList = calculateusage(siemensVmresourcesList,siemensContainerresourceList,siemensList,time,containernumber);
            //计算当前时间的平均响应时间
            siemensList = calculateaverageresponsetime(siemensList,bindCloudletlist,time);
//            try {
//                RandomAccessFile accessFile = new RandomAccessFile("/dev/xlx/cloudsim31/modules/cloudsim/src/main/java/org/cloudbus/cloudsim/container/core/Siemens/responsetimecontent.txt", "rw");
//                //获取文件长度
//                long length = accessFile.length();
//                //设置文件指针移动到文件末尾
//                accessFile.seek(length);
//                String responsetimecontent = time+","+averagereponsetime+" ";
//                String cpuusage = time+","+hostcpuusage+" ";
//                String bwusage = time+","+hostbwusage+" ";
//                String load = time+","+presentstarttimecloudletnumber+" ";
//                accessFile.write(responsetimecontent.getBytes());
//                accessFile.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            if (siemensList.getStatus()==1) {
                break;
            }
            time++;

        }
//        Plotpictures.plotpicture(time,hostbwusagelist);
//        List<Integer> numberlist= new ArrayList<>();
//        for (int i=0;i<100;i++){
//            numberlist.add(i);
//        }
        Plotpictures.plotpicture(time,siemensList.getLoadnumber(),"负载产生数量随时间的关系","load");
        Plotpictures.plotpicture(time,siemensList.getHostcpuusagelist(),"CPU利用率随时间的关系","CPU");
        Plotpictures.plotpicture(time,siemensList.getHostbwusagelist(),"带宽利用率随时间的关系","bw");
        Plotpictures.plotpicture(time,siemensList.getAverageresponsetimelist(),"平均响应时间随时间的关系","response time");
        System.out.println("finish cloudlet numbers is "+finishcloudletnumber);
        System.out.println("The finish time of Siemens is "+time+"ms");
//        System.out.println("The Response Time of GWslb is "+ response_time+"ms");
        return time;
    }


    public SiemensList calculateusage(List<SiemensVmresources> siemensVmresourcesList,List<SiemensContainerresource> siemensContainerresourceList,SiemensList siemensList,int time,int containernumber){
        int containercpuusage,containerbwusage;
        int vmcpuusage,vmbwusage;
        int hostcpuusage=0,hostbwusage=0;
        for(SiemensVmresources siemensVmresources : siemensVmresourcesList) {
            siemensVmresources.setCpuusage(0);
            siemensVmresources.setBwusage(0);
            for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                containercpuusage = 0;
                containerbwusage = 0;
                if(siemensContainerresource.getSiemensVmid()!= siemensVmresources.getId()){
                    continue;
                }
                for (int i = 0; i < siemensContainerresource.getCpuarraypool().length; i++) {
                    if (siemensContainerresource.getCpuarraypool()[i][time] == 1) {
                        containercpuusage++;
                    }
                }
                for (int j = 0;j<siemensContainerresource.getBwarraypool().length;j++){
                    if (siemensContainerresource.getBwarraypool()[j][time] == 1) {
                        containerbwusage++;
                    }
                }

                siemensVmresources.addCpuusage(containercpuusage);
                siemensVmresources.addBwusage(containerbwusage);


//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Cpu usage is " + containercpuusage);
//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Bw usage is " + containerbwusage);

            }
            vmcpuusage = siemensVmresources.getCpuusage();
            vmbwusage = siemensVmresources.getBwusage();

//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Cpu usage is " + vmcpuusage);
//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Bw usage is " + vmbwusage);
            hostcpuusage = hostcpuusage + vmcpuusage;
            hostbwusage =hostbwusage +vmbwusage;

        }
        hostcpuusage= hostcpuusage/containernumber;
        hostbwusage=hostbwusage/containernumber;
        if(hostbwusage==0&&hostcpuusage ==0&&time!=0){
            siemensList.setStatus(1);
        }
        siemensList.getHostcpuusagelist().add(hostcpuusage);
        siemensList.getHostbwusagelist().add(hostbwusage);
        return siemensList;
    }

    public SiemensList calculateaverageresponsetime(SiemensList siemensList,List<BindContainer> bindCloudletlist,int time){
        int sumreponsetime = 0,presentfinishcloudletnumber = 0,averagereponsetime =0;
        for(BindContainer bindContainer : bindCloudletlist){
            if (bindContainer.getFinishtime()<=time&&bindContainer.getFinishtime()!=0) {
                int perresponsetime = bindContainer.getEveryresponsetime();
                sumreponsetime= sumreponsetime+perresponsetime;
                presentfinishcloudletnumber++;
            }
            else if(bindContainer.getState()==0){
                break;
            }
        }
        if(presentfinishcloudletnumber==0){
            System.out.println("At time:"+ time +"ms averageresponsetime:"+ averagereponsetime+"ms");
        }
        else {
            averagereponsetime = sumreponsetime / presentfinishcloudletnumber;
            System.out.println("At time:" + time + "ms averageresponsetime:" + averagereponsetime + "ms");
        }

        siemensList.getAverageresponsetimelist().add(averagereponsetime);
        return siemensList;
    }

}
