package org.cloudbus.cloudsim.container.core.Siemens;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SiemensList {
    private List<Double> hostcpuusagelist;
    private List<Double> hostmemoryusagelist;
    private List<Double> averageresponsetimelist;
    private List<Double> loadnumber;
    private List<Double> hostbwusagelist;


    private List<Double> inputFlow;
    private List<Double> qps;
    private List<Pair<Integer, Integer>> load2qps;

    private List<Double> qpslist;
    private int state;    //qps是否进入阈值

    private List<Integer> runningcloudletnumberlist;

    private List<Integer> startcloudletnumberList;


    private int finishtime;

    private List<Integer> finishcloudletnumber;

    public List<Integer> getStartcloudletnumberList() {
        return startcloudletnumberList;
    }

    public void setStartcloudletnumberList(List<Integer> startcloudletnumberList) {
        this.startcloudletnumberList = startcloudletnumberList;
    }
    public List<Integer> getRunningcloudletnumberlist() {
        return runningcloudletnumberlist;
    }

    public void setRunningcloudletnumberlist(List<Integer> runningcloudletnumberlist) {
        this.runningcloudletnumberlist = runningcloudletnumberlist;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public List<Double> getQpslist() {
        return qpslist;
    }

    public void setQpslist(List<Double> qpslist) {
        this.qpslist = qpslist;
    }
    public List<Integer> getFinishcloudletnumber() {
        return finishcloudletnumber;
    }

    public void setFinishcloudletnumber(List<Integer> finishcloudletnumber) {
        this.finishcloudletnumber = finishcloudletnumber;
    }

    public int getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(int finishtime) {
        this.finishtime = finishtime;
    }
    private List<Double> hostregressioncpuusagelist;
    private int status;    //是否已经完成

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Double> getHostregressioncpuusagelist() {
        return hostregressioncpuusagelist;
    }

    public void setHostregressioncpuusagelist(List<Double> hostregressioncpuusagelist) {
        this.hostregressioncpuusagelist = hostregressioncpuusagelist;
    }
    public List<Double> getHostcpuusagelist() {
        return hostcpuusagelist;
    }

    public void setHostcpuusagelist(List<Double> hostcpuusagelist) {
        this.hostcpuusagelist = hostcpuusagelist;
    }

    public List<Double> getHostmemoryusagelist() {
        return hostmemoryusagelist;
    }

    public void setHostmemoryusagelist(List<Double> hostmemoryusagelist) {
        this.hostmemoryusagelist = hostmemoryusagelist;
    }

    public List<Double> getAverageresponsetimelist() {
        return averageresponsetimelist;
    }

    public void setAverageresponsetimelist(List<Double> averageresponsetimelist) {
        this.averageresponsetimelist = averageresponsetimelist;
    }

    public List<Double> getLoadnumber() {
        return loadnumber;
    }

    public void setLoadnumber(List<Double> loadnumber) {
        this.loadnumber = loadnumber;
    }






    public List<Double> getHostbwusagelist() {
        return hostbwusagelist;
    }

    public void setHostbwusagelist(List<Double> hostbwusagelist) {
        this.hostbwusagelist = hostbwusagelist;
    }


    public List<Double> getInputFlow() {
        return inputFlow;
    }

    public void setInputFlow(List<Double> inputFlow) {
        this.inputFlow = inputFlow;
    }

    public List<Double> getQps() {
        return qps;
    }

    public void setQps(List<Double> qps) {
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
        setHostmemoryusagelist(new ArrayList<>());
        setHostcpuusagelist(new ArrayList<>());
        setLoadnumber(new ArrayList<>());
        setHostregressioncpuusagelist(new ArrayList<>());
        setQpslist(new ArrayList<>());
        setRunningcloudletnumberlist(new ArrayList<>());
        setStartcloudletnumberList(new ArrayList<>());
        setFinishcloudletnumber(new ArrayList<>());
        setStatus(0);
        setState(0);
        setHostbwusagelist(new ArrayList<>());
        setInputFlow(new ArrayList<>());
        setQps(new ArrayList<>());
        setLoad2qps(new ArrayList<>());
    }
}
