package org.yunji.cloudsimrd.redis;

/**
 * @author weirenjie
 * @date 2019/10/24
 */

/**
 * 单个redis节点
 */
public class RedisNode {
    /**
     * redis节点id
     */
    public int redisId;

    /**
     * 节点类型。
     * 0代表主节点，1代表子节点
     */
    public int nodeType;

    /**
     * 节点在集群中的编号
     */
    public int clusterIndex = 0;


    public int getClusterIndex() {
        return clusterIndex;
    }

    public void setClusterIndex(int clusterIndex) {
        this.clusterIndex = clusterIndex;
    }


    public int getRedisId() {
        return redisId;
    }

    public void setRedisId(int redisId) {
        this.redisId = redisId;
    }


    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }
}
