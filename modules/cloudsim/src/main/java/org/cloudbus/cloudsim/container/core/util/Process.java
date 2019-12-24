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

    /**
     *
     * @param cloudletList the input loads
     * @param cpuresources the container cpuresources
     * @param bwresources the container bwresources
     * @param name the part name
     * @param containernumber the container numbers
     * @param mips the mips
     * @param responsetimeparament
     * @param time
     * @param siemensList
     * @param qps
     * @param qpsratio
     * @param qpsthreshold
     * @param responsetimethreshold
     * @param responsetimeratio
     * @return
     * @throws FileNotFoundException
     */

    public static SiemensList  processRequests(List<ContainerCloudlet> cloudletList,
                                               int cpuresources,
                                               int bwresources,
                                               String name,
                                               SiemensList siemensList,
                                               int containernumber,
                                               int vmnumber,
                                               double mips,
                                               int responsetimeparament,
                                               int time,
                                               double qps,
                                               double qpsratio,
                                               double qpsthreshold,
                                               double responsetimethreshold,
                                               double responsetimeratio,
                                               int bandwidth) throws FileNotFoundException {
        List<BindContainer> bindCloudletlist =createbindCloudlet(cloudletList,mips);
        siemensList.setName(name);
        siemensList = SchedulePolicy.schedulepolicy(bindCloudletlist,
                containernumber,vmnumber,cpuresources,bwresources,siemensList,responsetimeparament,time,
                qps,qpsthreshold,qpsratio,responsetimethreshold,responsetimeratio,bandwidth);
        return siemensList;
    }
}
