package org.cloudbus.cloudsim.container.core.Siemens;

import org.cloudbus.cloudsim.container.core.Container;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;

import java.util.List;

public class BindContainer extends Container {
    private int state;

    private int starttime;
    private int finishtime;
    private double everyresponsetime;


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

    public double getEveryresponsetime() {
        return everyresponsetime;
    }

    public void setEveryresponsetime(double everyresponsetime) {
        this.everyresponsetime = everyresponsetime;
    }
    private int cpuusage;
    private int memoryusage;
    private int Cloudletid;
    private int bwusage;

    public int getBwusage() {
        return bwusage;
    }

    public void setBwusage(int bwusage) {
        this.bwusage = bwusage;
    }

    public int getState() {
        return state;
    }
    public int getCpuusage() {
        return cpuusage;
    }

    public void setCpuusage(int cpuusage) {
        this.cpuusage = cpuusage;
    }

    public int getMemoryusage() {
        return memoryusage;
    }

    public void setMemoryusage(int memoryusage) {
        this.memoryusage = memoryusage;
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
    public BindContainer(int cpuusage, int memoryusage, int cloudletid, int containerid, int nowstate,int starttime, int handletime){
        setCpuusage(cpuusage);
        setMemoryusage(memoryusage);
        setCloudletid(cloudletid);
        setId(containerid);
        setState(nowstate);
        setStarttime(starttime);
        setHandletime(handletime);
    }
}
