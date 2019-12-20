package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.core.Siemens.*;

import java.util.ArrayList;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.*;

public class SchedulePolicy {


    public static SiemensList schedulepolicy(List<BindContainer> bindCloudletlist,
                                             CloudletMinParament cloudletMinParament,
                                             int containernumber,
                                             int cpuresources,
                                             int memoryresources,
                                             SiemensList siemensList,
                                             int responsetimeparment,
                                             int time) {
        int startcloudletnumber=0;
        int runningcloudletnumber =0;
        int presentstarttimecloudletnumber, lasttimestartcloudletnumber = 0;
        int roundrobinorder = 0,roundrobinnumber;
        int flag = 0;
        double responsetime=0,sumresponsetime = 0;
        double qps = 6.5,qpsthreshold = 0.9;
        double sumqps = 0;
        double qpsmaxthreshold = 1.1;
        int presentfinishcloudletnumber = 0;
        int laststartcloudletnumber =0,overloadcontainernumber = 0;
        int finishloadnumber = siemensList.getFinishloadnumber();
        List<SiemensVmresources>  siemensVmresourcesList = siemensList.getSiemensVmresourcesList();
        List<SiemensContainerresource> siemensContainerresourceList = siemensList.getSiemensContainerresourceList();

        List<BindContainer> lastdeferbindcloudCloudletlist = siemensList.getDeferedbindContainerslist();
        List<BindContainer> lastprocessbindcloudCloudletlist = siemensList.getProcessbindContainerslist();
        int size1 = lastdeferbindcloudCloudletlist.size();
        int size2 = lastprocessbindcloudCloudletlist.size();
        int addnumber = 0;
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


        for(BindContainer processbindContainer: lastprocessbindcloudCloudletlist){
            runningcloudletnumber++;
            if(processbindContainer.getFinishtime() == time){
                sumresponsetime = sumresponsetime+responsetimeparment*processbindContainer.getEveryresponsetime();
                presentfinishcloudletnumber++;
                presentprocessbindcloudletlist.remove(processbindContainer);
            }
        }
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
        System.out.println("time:"+time+ "s"+ siemensList.getName()+": average response time is "+ siemensList.getAverageresponsetimelist().get(time));

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

//        presentstarttimecloudletnumber = startcloudletnumber - laststartcloudletnumber;
        siemensList.getLoadnumber().add((double) bindCloudletlist.size());


        //计算cpu和带宽利用率
        siemensList = calculateusage(siemensVmresourcesList, lastprocessbindcloudCloudletlist,siemensContainerresourceList, siemensList, time, containernumber, cpuresources, memoryresources);
        //计算当前时间的平均响应时间
//        siemensList = calculateaverageresponsetime(siemensList, bindCloudletlist, time,responsetimeparment);

        if(time>10) {
            if (siemensList.getAverageresponsetimelist().get(time) / siemensList.getStartcloudletnumberList().get(time)
                    > qpsmaxthreshold * siemensList.getAverageresponsetimelist().get(time - 1) / siemensList.getStartcloudletnumberList().get(time - 1)) {
                siemensList.setState(1);
            }
        }
        if (siemensList.getState() ==0) {
            sumqps = qps * (double) runningcloudletnumber;
            siemensList.getQpslist().add(sumqps);
        }
        else{
            sumqps = qps * (double) runningcloudletnumber* qpsthreshold;
            siemensList.getQpslist().add(sumqps);
        }



        return siemensList;
    }

    public static int schedule(List<SiemensContainerresource> siemensContainerresourceList,
                               BindContainer bindContainer,
                               int roundrobinorder,
                               int time,
                               SiemensList siemensList){
        int remaincpuresources,remainmemoryresources,containercpurequest=0,containermemoryrequest=0,containerhandletime;
        int flag = 0;

        SiemensContainerresource siemensContainerresource=siemensContainerresourceList.get(roundrobinorder);
        siemensContainerresource.setRemaincpuresource(time);
        siemensContainerresource.setRemainmemoryresource(time);
        remaincpuresources = siemensContainerresource.getRemaincpuresource();
        remainmemoryresources = siemensContainerresource.getRemainmemoryresource();

        containercpurequest = bindContainer.getCpuusage();
        containermemoryrequest = bindContainer.getMemoryusage();
        if ((remaincpuresources >= containercpurequest)
                && (remainmemoryresources >= containermemoryrequest)) {
            flag= 1;
            containerhandletime = (int)(bindContainer.getHandletime());
            siemensContainerresource.changeCpuarraypool(remaincpuresources, containercpurequest, time, containerhandletime);
            siemensContainerresource.changeMemoryarraypool(remainmemoryresources, containermemoryrequest, time, containerhandletime);
            bindContainer.setFinishtime(time + containerhandletime);
            bindContainer.setEveryresponsetime(bindContainer.getFinishtime() - bindContainer.getStarttime());

        }
        if ((remaincpuresources <= siemensList.getCloudletMinParament().getMincpurequest()
                || remainmemoryresources < siemensList.getCloudletMinParament().getMinmemoryrequest())) {
            flag = 2;
        }


        return  flag;
    }
}
