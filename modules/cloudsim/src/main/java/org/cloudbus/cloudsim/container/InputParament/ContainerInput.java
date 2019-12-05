package org.cloudbus.cloudsim.container.InputParament;

public class ContainerInput {
    private int ContainerCPUQuota;
    private int ContainerMemoryQuota;
    private int ContainerMaxConnections;
    private int ContainerMIPSpercore;
    private int ContainerInboundbandwidth;
    private int ContainerOutboundbandwidth;
    private int ContainerDiskIO;
    private int ContainerDiskSize;

    public int getContainerCPUQuota() {
        return ContainerCPUQuota;
    }

    public void setContainerCPUQuota(int containerCPUQuota) {
        ContainerCPUQuota = containerCPUQuota;
    }

    public int getContainerMemoryQuota() {
        return ContainerMemoryQuota;
    }

    public void setContainerMemoryQuota(int containerMemoryQuota) {
        ContainerMemoryQuota = containerMemoryQuota;
    }

    public int getContainerMaxConnections() {
        return ContainerMaxConnections;
    }

    public void setContainerMaxConnections(int containerMaxConnections) {
        ContainerMaxConnections = containerMaxConnections;
    }

    public int getContainerMIPSpercore() {
        return ContainerMIPSpercore;
    }

    public void setContainerMIPSpercore(int containerMIPSpercore) {
        ContainerMIPSpercore = containerMIPSpercore;
    }

    public int getContainerInboundbandwidth() {
        return ContainerInboundbandwidth;
    }

    public void setContainerInboundbandwidth(int containerInboundbandwidth) {
        ContainerInboundbandwidth = containerInboundbandwidth;
    }

    public int getContainerOutboundbandwidth() {
        return ContainerOutboundbandwidth;
    }

    public void setContainerOutboundbandwidth(int containerOutboundbandwidth) {
        ContainerOutboundbandwidth = containerOutboundbandwidth;
    }

    public int getContainerDiskIO() {
        return ContainerDiskIO;
    }

    public void setContainerDiskIO(int containerDiskIO) {
        ContainerDiskIO = containerDiskIO;
    }

    public int getContainerDiskSize() {
        return ContainerDiskSize;
    }

    public void setContainerDiskSize(int containerDiskSize) {
        ContainerDiskSize = containerDiskSize;
    }
}
