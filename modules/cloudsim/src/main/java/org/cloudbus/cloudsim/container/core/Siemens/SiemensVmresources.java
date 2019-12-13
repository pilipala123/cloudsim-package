package org.cloudbus.cloudsim.container.core.Siemens;


import org.cloudbus.cloudsim.container.containerProvisioners.ContainerBwProvisioner;
import org.cloudbus.cloudsim.container.containerProvisioners.ContainerPe;
import org.cloudbus.cloudsim.container.containerProvisioners.ContainerRamProvisioner;
import org.cloudbus.cloudsim.container.containerVmProvisioners.ContainerVmBwProvisioner;
import org.cloudbus.cloudsim.container.containerVmProvisioners.ContainerVmPe;
import org.cloudbus.cloudsim.container.containerVmProvisioners.ContainerVmRamProvisioner;
import org.cloudbus.cloudsim.container.core.ContainerHost;
import org.cloudbus.cloudsim.container.core.ContainerVm;
import org.cloudbus.cloudsim.container.schedulers.ContainerScheduler;
import org.cloudbus.cloudsim.container.schedulers.ContainerVmScheduler;

import java.util.List;

public class SiemensVmresources extends ContainerHost {

    private int cpuusage;
    private int bwusage;


    private int memoryusage;

    /**
     * Instantiates a new host.
     *
     * @param id                        the id
     * @param containerVmRamProvisioner the ram provisioner
     * @param containerVmBwProvisioner  the bw provisioner
     * @param storage                   the storage
     * @param peList                    the pe list
     * @param containerVmScheduler      the vm scheduler
     */
    public SiemensVmresources(int id, ContainerVmRamProvisioner containerVmRamProvisioner, ContainerVmBwProvisioner containerVmBwProvisioner, long storage, List<? extends ContainerVmPe> peList, ContainerVmScheduler containerVmScheduler) {
        super(id, containerVmRamProvisioner, containerVmBwProvisioner, storage, peList, containerVmScheduler);
    }

    public SiemensVmresources(){

    }

    public int getCpuusage() {
        return cpuusage;
    }

    public void setCpuusage(int cpuusage) {
        this.cpuusage = cpuusage;
    }
    public void addCpuusage(int cpuusage){
        this.cpuusage = this.cpuusage+cpuusage;
    }

    public int getBwusage() {
        return bwusage;
    }

    public void setBwusage(int bwusage) {
        this.bwusage = bwusage;
    }

    public void addBwusage(int bwusage){
        this.bwusage = this.bwusage+bwusage;
    }

    public void addMemoryusage(int memoryusage){
        this.memoryusage = this.memoryusage+memoryusage;
    }

    public int getMemoryusage() {
        return memoryusage;
    }

    public void setMemoryusage(int memoryusage) {
        this.memoryusage = memoryusage;
    }
}
