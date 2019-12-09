package org.cloudbus.cloudsim.container.core.Siemens;

import org.cloudbus.cloudsim.container.core.Container;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;

import java.util.List;

public class BindContainer extends Container {
    private int state;

    private int starttime;
    private int finishtime;
    private int everyresponsetime;


    public int getHandletime() {
        return handletime;
    }

    public void setHandletime(int handletime) {
        this.handletime = handletime;
    }

    private int handletime;

    public void setState(int state) {
        this.state = state;
    }

    public int getEveryresponsetime() {
        return everyresponsetime;
    }

    public void setEveryresponsetime(int everyresponsetime) {
        this.everyresponsetime = everyresponsetime;
    }
    private int cpuusage;
    private int bwusage;
    private int Cloudletid;

    public int getState() {
        return state;
    }
    public int getCpuusage() {
        return cpuusage;
    }

    public void setCpuusage(int cpuusage) {
        this.cpuusage = cpuusage;
    }

    public int getBwusage() {
        return bwusage;
    }

    public void setBwusage(int bwusage) {
        this.bwusage = bwusage;
    }

    public int getCloudletid() {
        return Cloudletid;
    }

    public void setCloudletid(int cloudletid) {
        Cloudletid = cloudletid;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(int finishtime) {
        this.finishtime = finishtime;
    }

    public BindContainer(int id, int userId, double mips, int numberOfPes, int ram, long bw, List<ContainerCloudlet> cloudletList) {
        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
    }
    public BindContainer(int cpuusage, int bwusage, int cloudletid, int containerid, int starttime, int handletime){
        setCpuusage(cpuusage);
        setBwusage(bwusage);
        setCloudletid(cloudletid);
        setId(containerid);
        setState(0);
        setStarttime(starttime);
        setHandletime(handletime);
    }
}
