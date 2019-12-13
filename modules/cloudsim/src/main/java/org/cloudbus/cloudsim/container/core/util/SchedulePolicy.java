package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.core.Siemens.*;

import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.calculateaverageresponsetime;
import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.calculateusage;

public class SchedulePolicy {
    public static SiemensList schedulepolicy(List<BindContainer> bindCloudletlist,
                                             List<SiemensContainerresource> siemensContainerresourceList,
                                             CloudletMinParament cloudletMinParament,
                                             List<SiemensVmresources> siemensVmresourcesList,
                                             int containernumber,
                                             int cpuresources,
                                             int memoryresources,
                                             SiemensList siemensList) {
        int startcloudletnumber, time = 0;
        int remaincpuresources, remainmemoryresources;
        int containercpurequest, containermemoryrequest, containerhandletime;
        int presentstarttimecloudletnumber, lasttimestartcloudletnumber = 0;
        int finishcloudletnumber = 0;
        int roundrobinorder = 0,roundrobinnumber =0;
        while (true) {
            startcloudletnumber = 0;
            for (BindContainer bindContainer : bindCloudletlist) {

                if ((bindContainer.getState() == 3)&&(bindContainer.getFinishtime() == time)){
                    bindContainer.setState(4);
                }
                if ((bindContainer.getState() == 4)||(bindContainer.getState()==3)) {
                    continue;
                }
                if (bindContainer.getStarttime() > time) {
                    break;
                }
                for (SiemensContainerresource siemensContainerresource : siemensContainerresourceList) {
                    siemensContainerresource.setRemaincpuresource(time);
                    siemensContainerresource.setRemainmemoryresource(time);
                    remaincpuresources = siemensContainerresource.getRemaincpuresource();
                    remainmemoryresources = siemensContainerresource.getRemainmemoryresource();


                    if (bindContainer.getState() == 0 && bindContainer.getStarttime() == time) {
                        bindContainer.setState(1);
                    }
                    if (bindContainer.getState() == 1 || bindContainer.getState() == 2) {

                        containercpurequest = bindContainer.getCpuusage();
                        containermemoryrequest = bindContainer.getMemoryusage();
                        if((remaincpuresources>=containercpurequest)
                            && (remainmemoryresources >= containermemoryrequest)) {

                            containerhandletime = (int) bindContainer.getHandletime();
                            siemensContainerresource.changeCpuarraypool(remaincpuresources, containercpurequest, time, containerhandletime);
                            siemensContainerresource.changeMemoryarraypool(remainmemoryresources, containermemoryrequest, time, containerhandletime);
                            bindContainer.setState(3);
                            bindContainer.setFinishtime(time + containerhandletime);
                            bindContainer.setEveryresponsetime(bindContainer.getFinishtime() - bindContainer.getStarttime());
                            finishcloudletnumber++;
                            break;
                        }

                        if ((remaincpuresources < cloudletMinParament.getMincpurequest()
                                || remainmemoryresources < cloudletMinParament.getMinmemoryrequest())
                                && (siemensContainerresource.getId() == containernumber - 1)) {
                            bindContainer.setState(2);
                            break;
                        }

                    }

                }
            }
            for (BindContainer bindContainer : bindCloudletlist) {
                if (bindContainer.getState() != 0) {
                    startcloudletnumber++;
                }
            }
            presentstarttimecloudletnumber = startcloudletnumber - lasttimestartcloudletnumber;
            siemensList.getLoadnumber().add((double) presentstarttimecloudletnumber);
            lasttimestartcloudletnumber = startcloudletnumber;

            //计算cpu和带宽利用率
            siemensList = calculateusage(siemensVmresourcesList, bindCloudletlist,siemensContainerresourceList, siemensList, time, containernumber, cpuresources, memoryresources);
            //计算当前时间的平均响应时间
            siemensList = calculateaverageresponsetime(siemensList, bindCloudletlist, time);
//              try {
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
            if (siemensList.getStatus() == 1) {
                break;
            }
            time++;

        }
        siemensList.setFinishtime(time);
//        siemensList.setFinishcloudletnumber(finishcloudletnumber);
        return siemensList;
    }



    public static SiemensList schedulepolicy2(List<BindContainer> bindCloudletlist,
                                             List<SiemensContainerresource> siemensContainerresourceList,
                                             CloudletMinParament cloudletMinParament,
                                             List<SiemensVmresources> siemensVmresourcesList,
                                             int containernumber,
                                             int cpuresources,
                                             int memoryresources,
                                             SiemensList siemensList) {
        int startcloudletnumber, time = 0;
        int runningcloudletnumber =0;
        int presentstarttimecloudletnumber, lasttimestartcloudletnumber = 0;

        int roundrobinorder = 0,roundrobinnumber;
        int flag = 0;
        double qps = 6.5,qpsthreshold = 0.9;
        double sumqps = 0;

        while (true) {
            startcloudletnumber = 0;

            for (BindContainer bindContainer : bindCloudletlist) {
                roundrobinnumber=0;
                if ((bindContainer.getState() == 3)&&(bindContainer.getFinishtime() == time)){
                    bindContainer.setState(4);
                }
                if ((bindContainer.getState() == 4)||(bindContainer.getState()==3)) {
                    continue;
                }
                if (bindContainer.getStarttime() > time) {
                    break;
                }
                if (roundrobinorder ==containernumber){
                    roundrobinorder=0;
                }
                flag = schedule(siemensContainerresourceList,bindContainer,roundrobinorder,time);
                roundrobinorder++;
                if(flag==0){
                    while (true) {
                        roundrobinnumber++;
                        if (roundrobinorder==containernumber) {
                            roundrobinorder = 0;
                        }
                        flag = schedule(siemensContainerresourceList,bindContainer,roundrobinorder,time);
                        roundrobinorder++;
                        if(flag==1){
                            break;
                        }

                        if(roundrobinnumber ==containernumber){
                            roundrobinnumber = 0;
                            bindContainer.setState(2);
                            break;
                        }
                    }


//                        if ((remaincpuresources < cloudletMinParament.getMincpurequest()
////                                || remainmemoryresources < cloudletMinParament.getMinmemoryrequest())
////                                && (siemensContainerresource.getId() == containernumber - 1)) {
////                            bindContainer.setState(2);
////                            break;
////                        }

                }


            }
            for (BindContainer bindContainer : bindCloudletlist) {
                if (bindContainer.getState() != 0) {
                    startcloudletnumber++;
                }
                if(bindContainer.getState()==3){
                    runningcloudletnumber++;
                }

            }
            siemensList.getRunningcloudletnumberlist().add(runningcloudletnumber);
            siemensList.getStartcloudletnumberList().add(startcloudletnumber);
            presentstarttimecloudletnumber = startcloudletnumber - lasttimestartcloudletnumber;
            siemensList.getLoadnumber().add((double) presentstarttimecloudletnumber);
            lasttimestartcloudletnumber = startcloudletnumber;

            //计算cpu和带宽利用率
            siemensList = calculateusage(siemensVmresourcesList, bindCloudletlist,siemensContainerresourceList, siemensList, time, containernumber, cpuresources, memoryresources);
            //计算当前时间的平均响应时间
            siemensList = calculateaverageresponsetime(siemensList, bindCloudletlist, time);

            if(time>10) {
                if (siemensList.getAverageresponsetimelist().get(time) / siemensList.getStartcloudletnumberList().get(time)
                        > 1.1 * siemensList.getAverageresponsetimelist().get(time - 1) / siemensList.getStartcloudletnumberList().get(time - 1)) {
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
            if (siemensList.getStatus() == 1) {
                break;
            }
            runningcloudletnumber = 0;
            time++;

        }
        siemensList.setFinishtime(time);
        return siemensList;
    }

    public static int schedule(List<SiemensContainerresource> siemensContainerresourceList,
                               BindContainer bindContainer,
                               int roundrobinorder,
                               int time){
        int remaincpuresources,remainmemoryresources,containercpurequest=0,containermemoryrequest=0,containerhandletime;
        int flag = 0;

        SiemensContainerresource siemensContainerresource=siemensContainerresourceList.get(roundrobinorder);
        siemensContainerresource.setRemaincpuresource(time);
        siemensContainerresource.setRemainmemoryresource(time);
        remaincpuresources = siemensContainerresource.getRemaincpuresource();
        remainmemoryresources = siemensContainerresource.getRemainmemoryresource();


        if (bindContainer.getState() == 0 && bindContainer.getStarttime() == time) {
            bindContainer.setState(1);
        }
        if (bindContainer.getState() == 1 || bindContainer.getState() == 2) {

            containercpurequest = bindContainer.getCpuusage();
            containermemoryrequest = bindContainer.getMemoryusage();
            if ((remaincpuresources >= containercpurequest)
                    && (remainmemoryresources >= containermemoryrequest)) {
                flag= 1;
                containerhandletime = (int) bindContainer.getHandletime();
                siemensContainerresource.changeCpuarraypool(remaincpuresources, containercpurequest, time, containerhandletime);
                siemensContainerresource.changeMemoryarraypool(remainmemoryresources, containermemoryrequest, time, containerhandletime);
                bindContainer.setState(3);
                bindContainer.setFinishtime(time + containerhandletime);
                bindContainer.setEveryresponsetime(bindContainer.getFinishtime() - bindContainer.getStarttime());

            }
        }


        return  flag;
    }
}
