package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;
import org.yunji.cloudsimrd.load.LoadGenerator;

import java.util.ArrayList;
import java.util.List;

public class SiemensUtils {
    public static List<SiemensContainerresource> createVmResource(int containernumber, int vmnumber, int cpuresources, int memoryresources, int maxlength){
        List<SiemensContainerresource> siemensContainerresourceList =new ArrayList<>();
        int containereveryvm = (int)containernumber/vmnumber;
        for(Integer i=0;i<containernumber;i++){
            SiemensContainerresource siemensContainerresource = new SiemensContainerresource();
            siemensContainerresource.setId(i);
            siemensContainerresource.initCpuarraypool(cpuresources,maxlength);
            siemensContainerresource.initMemoryarraypool(memoryresources,maxlength);
            siemensContainerresource.initBwarraypool(memoryresources,maxlength);
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
                        cloudlet.getMemoryrequest(), cloudlet.getCloudletId(), containerid,
                        cloudlet.getState(), cloudlet.getStarttime(), cloudletlength);
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
                                             int cpuresource,
                                             int memoryresource,
                                             double responsetimethreshold,
                                             double responsetimeratio){
        int containercpuusage,containerbwusage;
        int vmcpuusage,vmmemoryusage;
        int hostcpuusage=0,hostmemoryusage=0;
        double responsetime=0;
        int finishtime=0;
        double hostcpu,hostmemory;
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
                for (int j = 0; j<siemensContainerresource.getMemoryarraypool().length; j++){
                    if (siemensContainerresource.getMemoryarraypool()[j][time] == 1) {
                        containerbwusage++;
                    }
                }

                siemensVmresources.addCpuusage(containercpuusage);
                siemensVmresources.addBwusage(containerbwusage);


//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Cpu usage is " + containercpuusage);
//                    System.out.println("At time:" + time + "ms Container:" + siemensContainerresource.getId() + " Bw usage is " + containerbwusage);

            }
            vmcpuusage = siemensVmresources.getCpuusage();
            vmmemoryusage = siemensVmresources.getBwusage();

//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Cpu usage is " + vmcpuusage);
//                System.out.println("At time:" + time + "ms VM:" + siemensVmresources.getId() + " Bw usage is " + vmbwusage);
            hostcpuusage = hostcpuusage + vmcpuusage;
            hostmemoryusage =hostmemoryusage +vmmemoryusage;

        }
//        hostcpuusage= hostcpuusage/containernumber;
//        hostbwusage=hostbwusage/containernumber;
        hostcpu=(double)hostcpuusage*100.0/(double)containernumber/(double)cpuresource;
        hostmemory = (double)hostmemoryusage*100.0/(double)containernumber/(double)memoryresource;
//        System.out.println("At time:" + time + "ms " + " Cpu usage is " + hostcpu);
//        System.out.println("At time:" + time + "ms " + " Bw usage is " + hostbw);

        if(hostmemory>=responsetimethreshold||hostcpu>=responsetimethreshold){
            for(BindContainer bindContainer:processbindContainerList){

                responsetime = bindContainer.getEveryresponsetime();
                bindContainer.setEveryresponsetime(responsetime*responsetimeratio*((hostcpu-responsetimethreshold)*(hostcpu-responsetimethreshold)/10000+1));

            }
        }
        siemensList.getHostcpuusagelist().add(hostcpu);
        siemensList.getHostmemoryusagelist().add(hostmemory);
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

    public static SiemensList calculateaverageresponsetime(SiemensList siemensList,List<BindContainer> bindCloudletlist,int time,int responsetimeparaments){
        double sumreponsetime = 0;
        int presentfinishcloudletnumber = 0;
        double averagereponsetime =0;
        for(BindContainer bindContainer : bindCloudletlist){
            if (bindContainer.getState()==4) {
                double perresponsetime = bindContainer.getEveryresponsetime();
                sumreponsetime= sumreponsetime+perresponsetime;
                presentfinishcloudletnumber++;
            }
            else if(bindContainer.getState()==0){
                break;
            }
        }
        siemensList.getFinishcloudletnumber().add(presentfinishcloudletnumber);
        if(presentfinishcloudletnumber==0){
            System.out.println("At time:"+ time +"ms averageresponsetime:"+ averagereponsetime+"ms");
        }
        else {
            averagereponsetime = sumreponsetime*responsetimeparaments / (double)presentfinishcloudletnumber;
            System.out.println("At time:" + time + "ms averageresponsetime:" + averagereponsetime + "ms");
        }

        siemensList.getAverageresponsetimelist().add(averagereponsetime);
        return siemensList;
    }




}
