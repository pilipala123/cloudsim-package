package org.cloudbus.cloudsim.container.core.Siemens;

import java.util.ArrayList;
import java.util.List;

public class SiemensList {
    private List<Integer> hostcpuusagelist;
    private List<Integer> hostbwusagelist;
    private List<Integer> averageresponsetimelist;
    private List<Integer> loadnumber;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public List<Integer> getHostcpuusagelist() {
        return hostcpuusagelist;
    }

    public void setHostcpuusagelist(List<Integer> hostcpuusagelist) {
        this.hostcpuusagelist = hostcpuusagelist;
    }

    public List<Integer> getHostbwusagelist() {
        return hostbwusagelist;
    }

    public void setHostbwusagelist(List<Integer> hostbwusagelist) {
        this.hostbwusagelist = hostbwusagelist;
    }

    public List<Integer> getAverageresponsetimelist() {
        return averageresponsetimelist;
    }

    public void setAverageresponsetimelist(List<Integer> averageresponsetimelist) {
        this.averageresponsetimelist = averageresponsetimelist;
    }

    public List<Integer> getLoadnumber() {
        return loadnumber;
    }

    public void setLoadnumber(List<Integer> loadnumber) {
        this.loadnumber = loadnumber;
    }

    public SiemensList() {
        setAverageresponsetimelist(new ArrayList<>());
        setHostbwusagelist(new ArrayList<>());
        setHostcpuusagelist(new ArrayList<>());
        setLoadnumber(new ArrayList<>());
        setStatus(0);
    }
}
