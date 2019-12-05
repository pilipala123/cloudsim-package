package org.cloudbus.cloudsim.container.InputParament;

public class EcsInput {
    private int EcsCPUQuota;
    private int EcsMemoryQuota;
    private int EcsDiskIO;
    private int EcsDiskSize;
    private int EcsInboundbandwidth;
    private int EcsOutboundbandwidth;
    private int EcsMIPSpercore;
    private int EcsServiceType;

    public int getEcsCPUQuota() {
        return EcsCPUQuota;
    }

    public void setEcsCPUQuota(int ecsCPUQuota) {
        EcsCPUQuota = ecsCPUQuota;
    }

    public int getEcsMemoryQuota() {
        return EcsMemoryQuota;
    }

    public void setEcsMemoryQuota(int ecsMemoryQuota) {
        EcsMemoryQuota = ecsMemoryQuota;
    }

    public int getEcsDiskIO() {
        return EcsDiskIO;
    }

    public void setEcsDiskIO(int ecsDiskIO) {
        EcsDiskIO = ecsDiskIO;
    }

    public int getEcsDiskSize() {
        return EcsDiskSize;
    }

    public void setEcsDiskSize(int ecsDiskSize) {
        EcsDiskSize = ecsDiskSize;
    }

    public int getEcsInboundbandwidth() {
        return EcsInboundbandwidth;
    }

    public void setEcsInboundbandwidth(int ecsInboundbandwidth) {
        EcsInboundbandwidth = ecsInboundbandwidth;
    }

    public int getEcsOutboundbandwidth() {
        return EcsOutboundbandwidth;
    }

    public void setEcsOutboundbandwidth(int ecsOutboundbandwidth) {
        EcsOutboundbandwidth = ecsOutboundbandwidth;
    }

    public int getEcsMIPSpercore() {
        return EcsMIPSpercore;
    }

    public void setEcsMIPSpercore(int ecsMIPSpercore) {
        EcsMIPSpercore = ecsMIPSpercore;
    }

    public int getEcsServiceType() {
        return EcsServiceType;
    }

    public void setEcsServiceType(int ecsServiceType) {
        EcsServiceType = ecsServiceType;
    }
}
