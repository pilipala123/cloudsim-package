package org.yunji.cloudsimrd.redis;

import java.util.List;

/**
 * @author weirenjie
 * @date 2019/10/24
 */

/**
 * redis集群
 */
public class RedisCluster {
    /**
     * 集群编号
     */
    public int clusterId;
    /**
     * 主节点
     */
    private RedisNode master;
    /**
     * 子节点
     */
    private List<RedisNode> slave;

    /**
     * 添加子节点
     * @param redisNode
     * @return
     */
    public boolean addSlaveNode(RedisNode redisNode) {
        return true;
    }

    /**
     * 添加多个子节点
     * @param nodes
     * @return
     */
    public boolean addSlaveNodes(List<RedisNode> nodes) {
        return true;
    }

    /**
     * 通过id删除子节点
     * @param nodeId
     * @return
     */
    public boolean deleteSlaveNode(int nodeId) {
        return true;
    }

    /**
     * 更改子节点
     * @param redisNode
     * @return
     */
    public boolean changeMasterNode(RedisNode redisNode) {
        this.master = redisNode;
        return true;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public RedisNode getMaster() {
        return master;
    }

    public void setMaster(RedisNode master) {
        this.master = master;
    }

    public List<RedisNode> getSlave() {
        return slave;
    }

    public void setSlave(List<RedisNode> slave) {
        this.slave = slave;
    }
}
