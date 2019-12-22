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
                                              SiemensList siemensList,double qps,
                                               double qpsratio,double qpsthreshold,
                                               double responsetimethreshold,double responsetimeratio) throws FileNotFoundException {

        int finishcloudletnumber,presentstarttimecloudletnumber=0;

        CloudletMinParament cloudletMinParament = siemensList.getCloudletMinParament();
//        cloudletMinParament.setcloudletMinParament(cloudletList,containernumber);
        List<BindContainer> bindCloudletlist =createbindCloudlet(cloudletList,mips);
        siemensList.setName(name);
        siemensList.setMaxloadnumber(cloudletList.size());
        siemensList = SchedulePolicy.schedulepolicy(bindCloudletlist,
                containernumber,cpuresources,memoryresources,siemensList,responsetimeparament,time,
                qps,qpsthreshold,qpsratio,responsetimethreshold,responsetimeratio);

        return siemensList;
    }
}
