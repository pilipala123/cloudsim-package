package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.io.FileNotFoundException;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.*;
import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.calculateregressionusage;

public class Process {
    public static SiemensList processRequests(List<ContainerCloudlet> cloudletList, int cpuresources, int memoryresources,
                                              String label, LoadGeneratorInput loadGeneratorInput,
                                              int containernumber,int vmnumber) throws FileNotFoundException {

        int time;
        int finishcloudletnumber,presentstarttimecloudletnumber=0;

        SiemensList siemensList = new SiemensList();
        CloudletMinParament cloudletMinParament = new CloudletMinParament();
        cloudletMinParament.setcloudletMinParament(cloudletList,containernumber);
        List<SiemensVmresources> slbsiemensVmresourcesList = createsiemnesVmresources(vmnumber);
        List<SiemensContainerresource> slbsiemensContainerresourceList = createVmResource(containernumber,vmnumber,cpuresources,memoryresources,cloudletMinParament.getMaxtimelength());

        List<BindContainer> bindCloudletlist =bindCloudlet(cloudletList);
        siemensList = SchedulePolicy.schedulepolicy2(bindCloudletlist,slbsiemensContainerresourceList,cloudletMinParament,slbsiemensVmresourcesList,containernumber,cpuresources,memoryresources,siemensList);

        siemensList = calculateregressionusage(siemensList,loadGeneratorInput,presentstarttimecloudletnumber,0.026174,-0.07892);
        time = siemensList.getFinishtime();
        Plotpictures.plotpicture(time,siemensList.getQpslist(),label+"qps随时间的关系","qps");
        Plotpictures.plotpicture(time,siemensList.getLoadnumber(),label+"负载产生数量随时间的关系","load");
        Plotpictures.plotpicture(time,siemensList.getHostcpuusagelist(),label+"CPU利用率随时间的关系","CPU");
        Plotpictures.plotpicture(time,siemensList.getHostmemoryusagelist(),label+"内存利用率随时间的关系","memory");
        Plotpictures.plotpicture(time,siemensList.getAverageresponsetimelist(),label+"平均响应时间随时间的关系","response time");

//        System.out.println("finish cloudlet numbers is "+finishcloudletnumber);
//        System.out.println("The finish time of Siemens is "+time+"ms");
//        System.out.println("The Response Time of GWslb is "+ response_time+"ms");
        return siemensList;
    }
}
