package org.cloudbus.cloudsim.container.core.redis;

public enum ERedisModel {
    STANDARDDOUBLE(1, "标准版-双副本"),
    STANDARDENHANCE(2, "标准版-增强性能"),
    STANDARDHYBRID(3, "标准版-混合存储"),
    DISASTERTOLERANCE(4, "标准版-同城容灾"),
    STANDARDSINGLE(5, "标准版-单副本"),
    CLUSTERDOUBLE(6, "集群版-双副本"),
    CLUSTERENHANCE(7, "集群版-性能增强"),
    CLUSTERDISASTERTOLERANCE(8, "集群版-同城容灾"),
    ClUSTERSINGLE(9, "集群版-单副本"),
    READANDWRITE(10, "读写分离版"),
    READANDWRITEENHANCE(11, "读写分离版-增强性能"),
    CLUSTERREADANDWRITE(12, "集群版读写分离"),
    ;

    private String name;
    private int status;

    ERedisModel(int status, String name) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }
}
