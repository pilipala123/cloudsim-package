package org.cloudbus.cloudsim.container.core.Siemens;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SiemensList {
    private List<Integer> hostcpuusagelist;
    private List<Integer> hostbwusagelist;
    private List<Integer> averageresponsetimelist;
    private List<Integer> loadnumber;

    private List<Integer> inputFlow;
    private List<Integer> qps;
    private List<Pair<Integer, Integer>> load2qps;

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

    public List<Integer> getInputFlow() {
        return inputFlow;
    }

    public void setInputFlow(List<Integer> inputFlow) {
        this.inputFlow = inputFlow;
    }

    public List<Integer> getQps() {
        return qps;
    }

    public void setQps(List<Integer> qps) {
        this.qps = qps;
    }

    public List<Pair<Integer, Integer>> getLoad2qps() {
        return load2qps;
    }

    public void setLoad2qps(List<Pair<Integer, Integer>> load2qps) {
        this.load2qps = load2qps;
    }

    public SiemensList() {
        setAverageresponsetimelist(new ArrayList<>());
        setHostbwusagelist(new ArrayList<>());
        setHostcpuusagelist(new ArrayList<>());
        setLoadnumber(new ArrayList<>());
        setInputFlow(new ArrayList<>());
        setQps(new ArrayList<>());
        setLoad2qps(new ArrayList<>());
        setStatus(0);
    }
}
