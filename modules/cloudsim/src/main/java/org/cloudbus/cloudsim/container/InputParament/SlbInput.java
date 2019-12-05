package org.cloudbus.cloudsim.container.InputParament;

public class SlbInput {
    private int SlbMaxconnections;
    private int SlbMaxQPS;
    private int SlbMaxinboundbandwidth;
    private int SlbMaxoutboundbandwidth;
    private int SlbLatency;
    private int SlbLoaddistributionstrategy;
    private int SlbCPUQuota;
    private int SlbMemoryQuota;
    private int Slbmoney;

    public int getSlbmoney() {
        return Slbmoney;
    }

    public void setSlbmoney(int slbmoney) {
        Slbmoney = slbmoney;
    }

    public int getSlbMaxconnections() {
        return SlbMaxconnections;
    }

    public void setSlbMaxconnections(int slbMaxconnections) {
        SlbMaxconnections = slbMaxconnections;
    }

    public int getSlbMaxQPS() {
        return SlbMaxQPS;
    }

    public void setSlbMaxQPS(int slbMaxQPS) {
        SlbMaxQPS = slbMaxQPS;
    }

    public int getSlbMaxinboundbandwidth() {
        return SlbMaxinboundbandwidth;
    }

    public void setSlbMaxinboundbandwidth(int slbMaxinboundbandwidth) {
        SlbMaxinboundbandwidth = slbMaxinboundbandwidth;
    }

    public int getSlbMaxoutboundbandwidth() {
        return SlbMaxoutboundbandwidth;
    }

    public void setSlbMaxoutboundbandwidth(int slbMaxoutboundbandwidth) {
        SlbMaxoutboundbandwidth = slbMaxoutboundbandwidth;
    }

    public int getSlbLatency() {
        return SlbLatency;
    }

    public void setSlbLatency(int slbLatency) {
        SlbLatency = slbLatency;
    }

    public int getSlbLoaddistributionstrategy() {
        return SlbLoaddistributionstrategy;
    }

    public void setSlbLoaddistributionstrategy(int slbLoaddistributionstrategy) {
        SlbLoaddistributionstrategy = slbLoaddistributionstrategy;
    }

    public int getSlbCPUQuota() {
        return SlbCPUQuota;
    }

    public void setSlbCPUQuota(int slbCPUQuota) {
        SlbCPUQuota = slbCPUQuota;
    }

    public int getSlbMemoryQuota() {
        return SlbMemoryQuota;
    }

    public void setSlbMemoryQuota(int slbMemoryQuota) {
        SlbMemoryQuota = slbMemoryQuota;
    }

    public int getSlbLoadbalancertype() {
        return SlbLoadbalancertype;
    }

    public void setSlbLoadbalancertype(int slbLoadbalancertype) {
        SlbLoadbalancertype = slbLoadbalancertype;
    }

    private int SlbLoadbalancertype;
}

