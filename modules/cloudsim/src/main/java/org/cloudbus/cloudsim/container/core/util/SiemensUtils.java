package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.InputParament.K8sInput;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.InputParament.PartInput;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.util.ArrayList;
import java.util.List;

public class SiemensUtils {
    public static List<SiemensContainerresource> createVmResource(int containernumber, int vmnumber, int cpuresources, int bwresources, int maxlength){
        List<SiemensContainerresource> siemensContainerresourceList =new ArrayList<>();
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

    public static List<SiemensVmresources> createsiemnesVmresources(int vmnumber){
        List<SiemensVmresources> siemensVmresourcesList = new ArrayList<>();
        for(int i=0;i<vmnumber;i++){
            SiemensVmresources siemensVmresources = new SiemensVmresources();
            siemensVmresources.setId(i);
            siemensVmresourcesList.add(siemensVmresources);
        }
        return siemensVmresourcesList;

    }

    public static List<BindContainer> createbindCloudlet(List<ContainerCloudlet> cloudletList,double mips){
        List<BindContainer> bindContainerList = new ArrayList<>();
        int containerid = 0;

        for (ContainerCloudlet cloudlet:cloudletList){
            if(cloudlet.getState()==1) {
                int cloudletlength =(int) ((double)cloudlet.getCloudletLength()/mips);
                BindContainer bindContainer = new BindContainer(cloudlet.getCpurequest(),
                        cloudlet.getBwrequest(), cloudlet.getCloudletId(), containerid,
                        cloudlet.getState(), cloudlet.getStarttime(), cloudletlength,cloudlet.getPacketsize());
                bindContainerList.add(bindContainer);
                cloudlet.setContainerId(bindContainer.getId());
                bindContainer.setOperations(cloudlet.getOperation());
                containerid++;
            }
        }
        return bindContainerList;
    }

    public static SiemensList calculateusage(List<SiemensVmresources> siemensVmresourcesList,
                                             List<BindContainer> processbindContainerList,
                                             List<SiemensContainerresource> siemensContainerresourceList,
                                             SiemensList siemensList,
                                             int time,
                                             int containernumber,
                                             int vmnumber,
                                             int cpuresource,
                                             int bwresource,
                                             double responsetimethreshold,
                                             double responsetimeratio,
                                             double responsetimeratio2,
                                             double packetsize,
                                             int bandwidth){
        int containercpuusage,containerbwusage;
        int vmcpuusage,vmbwusage;
        int hostcpuusage=0,hostbwusage=0;
        double responsetime=0;
        double hostcpu,hostbw;
        int containerflag =0;
        int vmflag=0;
        double outputhostbwusage = 0;
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
                for (int j = 0; j<siemensContainerresource.getBwarraypool().length; j++){
                    if (siemensContainerresource.getBwarraypool()[j][time] == 1) {
                        containerbwusage++;
                    }
                }
                if(siemensContainerresource.getId()==containerflag) {
                    siemensList.getContainercpuusagelist().add(100*containercpuusage / (double) cpuresource);
                }
                siemensVmresources.addCpuusage(containercpuusage);
                siemensVmresources.addBwusage(containerbwusage);


//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Cpu usage is " + containercpuusage);
//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Bw usage is " + containerbwusage);

            }
            vmcpuusage = siemensVmresources.getCpuusage();
            if(siemensVmresources.getId()==vmflag) {
                siemensList.getVmcpuusagelist().add((double) vmcpuusage*100*vmnumber/containernumber/(double)cpuresource);
            }
            vmbwusage = siemensVmresources.getBwusage();

//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Cpu usage is " + vmcpuusage);
//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Bw usage is " + vmbwusage);
            hostcpuusage = hostcpuusage + vmcpuusage;
            hostbwusage =hostbwusage +vmbwusage;

        }

        hostcpu=(double)hostcpuusage*100.0/(double)containernumber/(double)cpuresource;
        if(time==0){
            outputhostbwusage = 0;
        }
        else {
            outputhostbwusage = hostbwusage - packetsize * siemensList.getFinishcloudletnumber().get(time-1);
        }
        hostbw = (double)hostbwusage*bandwidth/(double)containernumber/(double)bwresource;
        outputhostbwusage = (double)outputhostbwusage*bandwidth/(double)containernumber/(double)bwresource;

        if(hostcpu>=responsetimethreshold){
            for(BindContainer bindContainer:processbindContainerList){

                responsetime = bindContainer.getEveryresponsetime();
                bindContainer.setEveryresponsetime(responsetime*responsetimeratio*(Math.pow((hostcpu-responsetimethreshold)/100,2)+1)*(Math.pow(siemensList.getDeferedbindContainerslist().size()/siemensList.getLoadnumber().get(time),2)+responsetimeratio2));

            }
        }
        siemensList.getHostoutputbwusage().add(outputhostbwusage);
        siemensList.getHostcpuusagelist().add(hostcpu);
        siemensList.getHostbwusagelist().add(hostbw);
        return siemensList;
    }


    public static SiemensList calculateregressionusage(SiemensList siemensList, LoadGeneratorInput loadGeneratorInput, int percloudletnumber, double cpu_a, double cpu_b){
        double cpuusage = 0;
        int bwusage = 0;
        int endtime = loadGeneratorInput.getRamp_down();
        double timerange = loadGeneratorInput.getTimerange();
        int middletime =loadGeneratorInput.getRamp_up();
        for (int i=0;i<endtime;i++){
            if(i<middletime) {
                cpuusage = cpu_a * (double)i*timerange + cpu_b;
            }
            else{
                cpuusage = cpu_a*(double)middletime*timerange+cpu_b;
            }
            siemensList.getHostregressioncpuusagelist().add(cpuusage);
        }
        Plotpictures.plotpicture(endtime,siemensList.getHostregressioncpuusagelist(),"线性回归下CPU利用率随时间的关系","CPU");


//        bwusage =
        return siemensList;
    }
//
//    public static SiemensList calculateaverageresponsetime(SiemensList siemensList,List<BindContainer> bindCloudletlist,int time,int responsetimeparaments){
//        double sumreponsetime = 0;
//        int presentfinishcloudletnumber = 0;
//        double averagereponsetime =0;
//        for(BindContainer bindContainer : bindCloudletlist){
//            if (bindContainer.getState()==4) {
//                double perresponsetime = bindContainer.getEveryresponsetime();
//                sumreponsetime= sumreponsetime+perresponsetime;
//                presentfinishcloudletnumber++;
//            }
//            else if(bindContainer.getState()==0){
//                break;
//            }
//        }
//        siemensList.getFinishcloudletnumber().add(presentfinishcloudletnumber);
//        if(presentfinishcloudletnumber==0){
//            System.out.println("At time:"+ time +"ms averageresponsetime:"+ averagereponsetime+"ms");
//        }
//        else {
//            averagereponsetime = sumreponsetime*responsetimeparaments / (double)presentfinishcloudletnumber;
//            System.out.println("At time:" + time + "ms averageresponsetime:" + averagereponsetime + "ms");
//        }
//
//        siemensList.getAverageresponsetimelist().add(averagereponsetime);
//        return siemensList;
//    }




}
