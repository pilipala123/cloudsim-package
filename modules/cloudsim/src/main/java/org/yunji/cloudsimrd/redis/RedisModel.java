package org.yunji.cloudsimrd.redis;

/**
 * @author weirenjie
 * @date 2019/10/23
 */
public interface RedisModel {
    /**
     * 通过单个节点读取数据
     * @param redisNode
     * @param length
     * @return AccessTime
     */
    int getDataFromNode(RedisNode redisNode, Byte length);

    /**
     * 通过集群读取数据
     * @param redisCluster
     * @param length
     * @return AccessTime
     */
    int getDataFromCluster(RedisCluster redisCluster, Byte length);

    /**
     * 读取RDB持久化的数据
     * @param length
     * @return AccessTime
     */
    int getDataFromRDB(Byte length);

    /**
     * 读取AOF持久化的数据
     * @param length
     * @return AccessTime
     */
    int getDataFromAOF(Byte length);

    /**
     * 保存数据
     * @param length
     * @return AccessTime
     */
    int saveData(Byte length);
}
