package org.cloudbus.cloudsim.container.core.redis;

public enum ERedisModel {
    STANDARDDOUBLE(1, "标准版-双副本", 10000, 100, 80000),
    STANDARDENHANCE(2, "标准版-增强性能", 10000, 100, 80000),
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
    private int max_connection;
    private int cpu;
    private int max_qps;

    ERedisModel(int status, String name) {
        this.name = name;
        this.status = status;
        this.max_connection = 1000;
        this.cpu = 100;
        this.max_qps = 80000;
    }

    ERedisModel(int status, String name, int max_connection, int cpu, int max_qps) {
        this.name = name;
        this.status = status;
        this.max_connection = max_connection;
        this.cpu = cpu;
        this.max_qps = max_qps;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public int getMax_connection() {
        return max_connection;
    }

    public int getCpu() {
        return cpu;
    }

    public int getMax_qps() {
        return max_qps;
    }
}
