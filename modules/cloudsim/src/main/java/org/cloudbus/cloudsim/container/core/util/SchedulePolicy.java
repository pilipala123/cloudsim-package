package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.core.Siemens.*;

import java.util.ArrayList;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.*;

public class SchedulePolicy {


    public static SiemensList schedulepolicy(List<BindContainer> bindCloudletlist,
                                             int containernumber,
                                             int vmnumber,
                                             int cpuresources,
                                             int bwresources,
                                             SiemensList siemensList,
                                             int responsetimeparment,
                                             int time,double qps,
                                             double qpsthreshold,
                                             double qpsratio,
                                             double responsetimethreshold,
                                             double responsetimeratio,
                                             int bandwidth) {
        int startcloudletnumber=0;
        int runningcloudletnumber =0;
        int presentstarttimecloudletnumber, lasttimestartcloudletnumber = 0;
        int roundrobinorder = 0,roundrobinnumber;
        int flag = 0;
        double responsetime=0,sumresponsetime = 0;
        double sumqps = 0;
        int presentfinishcloudletnumber = 0;
        int laststartcloudletnumber =0,overloadcontainernumber = 0;
        int finishloadnumber = siemensList.getFinishloadnumber();
        List<SiemensVmresources>  siemensVmresourcesList = siemensList.getSiemensVmresourcesList();
        List<SiemensContainerresource> siemensContainerresourceList = siemensList.getSiemensContainerresourceList();
        double packetsize = bindCloudletlist.get(0).getPacketsize();
        List<BindContainer> lastdeferbindcloudCloudletlist = siemensList.getDeferedbindContainerslist();
        List<BindContainer> lastprocessbindcloudCloudletlist = siemensList.getProcessbindContainerslist();
        int size1 = lastdeferbindcloudCloudletlist.size();
        int size2 = lastprocessbindcloudCloudletlist.size();
        int addnumber = 0;
        double persumresponsetime =0;
        startcloudletnumber=0;
        while (size1+size2+addnumber<bindCloudletlist.size()) {

            lastdeferbindcloudCloudletlist.add(bindCloudletlist.get(addnumber));
            addnumber++;
        }
//        System.out.println("time"+ time + "s   "+(size1+size2+addnumber));
        List<BindContainer> presentdefbindcloudCloudletlist= new ArrayList<>();
        List<BindContainer> presentprocessbindcloudletlist = new ArrayList<>();
        presentdefbindcloudCloudletlist.addAll(lastdeferbindcloudCloudletlist);
        presentprocessbindcloudletlist.addAll(lastprocessbindcloudCloudletlist);


        if (time !=0){
            sumresponsetime = finishloadnumber*siemensList.getAverageresponsetimelist().get(time-1);
            startcloudletnumber = siemensList.getStartcloudletnumberList().get(time-1);
            laststartcloudletnumber = startcloudletnumber;
            for(BindContainer bindContainer1:bindCloudletlist){
                if(bindContainer1.getState()==1){
                    startcloudletnumber ++;
                }
            }
            siemensList.getStartcloudletnumberList().add(startcloudletnumber);
        }

        persumresponsetime=0;
        for(BindContainer processbindContainer: lastprocessbindcloudCloudletlist){
            runningcloudletnumber++;
            if(processbindContainer.getFinishtime() == time){
                sumresponsetime = sumresponsetime+responsetimeparment*processbindContainer.getEveryresponsetime();
                presentfinishcloudletnumber++;
                persumresponsetime = persumresponsetime+responsetimeparment*processbindContainer.getEveryresponsetime();
                presentprocessbindcloudletlist.remove(processbindContainer);
            }
        }
        siemensList.getPresentfinishcloudletnumberlist().add(presentfinishcloudletnumber);

        siemensList.getAvgperresponsetimelist().add(persumresponsetime/presentfinishcloudletnumber);
        if(time == 0){
            siemensList.getAverageresponsetimelist().add(0.0);
            siemensList.getStartcloudletnumberList().add(0);
        }
        else {
            siemensList.getFinishcloudletnumber().add(presentfinishcloudletnumber);
            finishloadnumber = finishloadnumber + presentfinishcloudletnumber;
            if (finishloadnumber==0){
                siemensList.getAverageresponsetimelist().add(0.0);
            }
            else {
                siemensList.getAverageresponsetimelist().add(sumresponsetime / finishloadnumber);
            }
            siemensList.setFinishloadnumber(finishloadnumber);
        }

//        System.out.println("processcloudletnumber" + siemensList.getProcessbindContainerslist().get(time-1));
//
        System.out.println("time: "+time+ " s"+ siemensList.getName()+": average response time is "+ siemensList.getAverageresponsetimelist().get(time));

        for (BindContainer bindContainer : lastdeferbindcloudCloudletlist) {
            roundrobinnumber=0;
            overloadcontainernumber = 0;
            if (roundrobinorder ==containernumber){
                roundrobinorder=0;
            }
            flag = schedule(siemensContainerresourceList,bindContainer,roundrobinorder,time,siemensList);
            roundrobinorder++;
            if(flag==0){
                while (true) {
                    roundrobinnumber++;
                    if (roundrobinorder==containernumber) {
                        roundrobinorder = 0;
                    }
                    flag = schedule(siemensContainerresourceList,bindContainer,roundrobinorder,time,siemensList);
                    roundrobinorder++;
                    if(flag==1){
                        presentprocessbindcloudletlist.add(bindContainer);
                        presentdefbindcloudCloudletlist.remove(bindContainer);
                        break;
                    }

                    if(roundrobinnumber ==containernumber){
                        roundrobinnumber = 0;
                        presentdefbindcloudCloudletlist.add(bindContainer);
                        break;
                    }
//                    if(flag == 2){
//                        overloadcontainernumber++;
//                    }
//                    if(overloadcontainernumber==containernumber){
//                        break;
//                    }
                }
            }
            else if(flag ==1){
                presentdefbindcloudCloudletlist.remove(bindContainer);
                presentprocessbindcloudletlist.add(bindContainer);
            }
            else{
                break;
            }



        }
        siemensList.setProcessbindContainerslist(presentprocessbindcloudletlist);
        siemensList.setDeferedbindContainerslist(presentdefbindcloudCloudletlist);

        siemensList.getRunningcloudletnumberlist().add(runningcloudletnumber);
        siemensList.getLoadnumber().add((double) bindCloudletlist.stream().mapToInt(BindContainer::getOperations).sum());


        //计算cpu和带宽利用率
        siemensList = calculateusage(siemensVmresourcesList, lastprocessbindcloudCloudletlist,
                siemensContainerresourceList, siemensList, time, containernumber,vmnumber,cpuresources,
                bwresources,responsetimethreshold,responsetimeratio,packetsize,bandwidth);
        sumqps = qps * (double) runningcloudletnumber*qpsratio/(2+(Math.pow((siemensList.getHostcpuusagelist().get(time)/100),3))*qpsthreshold);
        siemensList.getQpslist().add(sumqps);




        return siemensList;
    }

    public static int schedule(List<SiemensContainerresource> siemensContainerresourceList,
                               BindContainer bindContainer,
                               int roundrobinorder,
                               int time,
                               SiemensList siemensList){
        int remaincpuresources,remainbwresources,containercpurequest=0,containerbwrequest=0,containerhandletime;
        int flag = 0;

        SiemensContainerresource siemensContainerresource=siemensContainerresourceList.get(roundrobinorder);
        siemensContainerresource.setRemaincpuresource(time);
        siemensContainerresource.setRemainbwresource(time);
        remaincpuresources = siemensContainerresource.getRemaincpuresource();
        remainbwresources = siemensContainerresource.getRemainbwresource();

        containercpurequest = bindContainer.getCpuusage();
        containerbwrequest = bindContainer.getBwusage();
        if ((remaincpuresources >= containercpurequest)
                && (remainbwresources >= containerbwrequest)) {
            flag= 1;
            containerhandletime = (int)(bindContainer.getHandletime());
            siemensContainerresource.changeCpuarraypool(remaincpuresources, containercpurequest, time, containerhandletime);
            siemensContainerresource.changeBwarraypool(remainbwresources, containerbwrequest, time, containerhandletime);
            bindContainer.setFinishtime(time + containerhandletime);
            bindContainer.setEveryresponsetime(bindContainer.getFinishtime() - bindContainer.getStarttime());

        }
        if ((remaincpuresources <= siemensList.getCloudletMinParament().getMincpurequest()
                || remainbwresources < siemensList.getCloudletMinParament().getMinbwrequest())) {
            flag = 2;
        }


        return  flag;
    }
}
