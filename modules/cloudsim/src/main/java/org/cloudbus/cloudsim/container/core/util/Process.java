package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.*;
import static org.cloudbus.cloudsim.container.core.util.SiemensUtils.calculateregressionusage;

public class Process {
    public static SiemensList  processRequests(List<ContainerCloudlet> cloudletList, int cpuresources, int memoryresources,
                                              String name, LoadGeneratorInput loadGeneratorInput,
                                              int containernumber,int vmnumber,double mips,
                                              int responsetimeparament,int time,
                                              SiemensList siemensList,double qps) throws FileNotFoundException {

        int finishcloudletnumber,presentstarttimecloudletnumber=0;

        CloudletMinParament cloudletMinParament = siemensList.getCloudletMinParament();
//        cloudletMinParament.setcloudletMinParament(cloudletList,containernumber);
        List<BindContainer> bindCloudletlist =createbindCloudlet(cloudletList,mips);
        siemensList.setName(name);
        siemensList = SchedulePolicy.schedulepolicy(bindCloudletlist,cloudletMinParament,
                containernumber,cpuresources,memoryresources,siemensList,responsetimeparament,time,qps);


//        siemensList = calculateregressionusage(siemensList,loadGeneratorInput,presentstarttimecloudletnumber,0.026174,-0.07892);
//        time = siemensList.getFinishtime();

//        System.out.println("finish cloudlet numbers is "+finishcloudletnumber);
//        System.out.println("The finish time of Siemens is "+time+"ms");
//        System.out.println("The Response Time of GWslb is "+ response_time+"ms");
        return siemensList;
    }
}
