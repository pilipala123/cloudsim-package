package org.cloudbus.cloudsim.container.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.cloudbus.cloudsim.container.core.redis.Configuration;
import org.cloudbus.cloudsim.container.core.redis.ERedisModel;
import org.cloudbus.cloudsim.container.core.redis.ERedisOperationType;
import org.cloudbus.cloudsim.container.core.redis.QpsCaculate;
import org.cloudbus.cloudsim.container.schedulers.ContainerCloudletScheduler;
import org.cloudbus.cloudsim.container.utils.MapOperator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Redis extends Container {

    private int responseTime;
    private double qps;

    private int max_connection;
    private int max_inbound_bandwidth;
    private int max_outbound_bandwidth;
    private int max_qps;

    private ERedisModel model;

    private int current_connection = 0;

    private int max_storage;
    private Map<Integer, Long> cache;
    private Map<Integer, Integer> cache_request_count;

    /**
     * Creates a new Container-based Redis object.
     *
     * @param id
     * @param userId
     * @param mips
     * @param numberOfPes
     * @param ram
     * @param bw
     * @param size
     * @param containerManager
     * @param containerCloudletScheduler
     * @param schedulingInterval
     */
    public Redis(int id, int userId, double mips, int numberOfPes, int ram, long bw, long size, String containerManager, ContainerCloudletScheduler containerCloudletScheduler, double schedulingInterval) {
        super(id, userId, mips, numberOfPes, ram, bw, size, containerManager, containerCloudletScheduler, schedulingInterval);
    }

    public Redis(int id, int userId, double mips, int numberOfPes, int ram, long bw, List<ContainerCloudlet> cloudletList) {
        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setResponseTime(100);
        setQps(100);

        init();
        processRequests(cloudletList);
    }

    private void init() {
        this.max_connection = Configuration.getProperty("redis.MAX_ACTIVE", 1024);
        this.max_inbound_bandwidth = Configuration.getProperty("redis.max.inbound.bandwidth", 1000);
        this.max_outbound_bandwidth = Configuration.getProperty("redis.max.outbound.bandwidth", 1000);
        this.max_qps = Configuration.getProperty("redis.max.qps", 1000);

        this.model = ERedisModel.valueOf(Configuration.getProperty("redis.model", "STANDARDDOUBLE"));

        this.max_storage = Configuration.getProperty("redis.max.qps", 1000);

        this.cache = Maps.newHashMapWithExpectedSize(10);
        this.cache_request_count = Maps.newHashMapWithExpectedSize(10);
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public double getQps() {
        return qps;
    }

    public void setQps(int qps) {
        this.qps = qps;
    }

    /**
     * 根据当前配置和参数，基于线性规划模型计算qps
     *
     * @return qps
     */
    private int qps() {
        return QpsCaculate.QpsCaculate(getId(), 0, current_connection, max_connection, getBw()
                , getMips(), model.getStatus(), max_inbound_bandwidth, max_outbound_bandwidth);
    }

    /**
     * 根据不同操作返回响应时间
     *
     * @param type Redis不同操作
     * @return qps
     */
    private double qps(ERedisOperationType type) {
        int qps = qps();
        switch (type) {
            case QueryMinimumRequest:
            case QueryNotRequestRecently:
                return 1.2 * qps;
            case Query:
            default:
                return qps;
        }
    }

    /**
     * 执行任务队列，假设 containerCloudlet.getStatus() == 1 代表查询，否则代表插入
     *
     * @param cloudletList 任务队列
     */
    public void processRequests(List<ContainerCloudlet> cloudletList) {
        for (ContainerCloudlet containerCloudlet : cloudletList) {
            if (1 == containerCloudlet.getStatus()) {
                this.qps += query(containerCloudlet);
            } else {
                this.qps += insert(containerCloudlet);
            }
        }
    }

    /**
     * 执行从缓存中查询任务
     * 1. 若缓存中存在，则更新时间为当前时间，访问次数+1
     * 2. 否则，跳出
     *
     * @param cloudlet 任务
     * @return 响应时间
     */
    public double query(ContainerCloudlet cloudlet) {
        int id = cloudlet.containerId;
        if (cache.containsKey(id)) {
            cache.put(id, System.currentTimeMillis());
            cache_request_count.put(id, cache_request_count.getOrDefault(id, 0) + 1);
        }
        return qps(ERedisOperationType.Query);
    }

    /**
     * 执行向缓存中插入任务
     * 1. 若缓存中存在，则返回查询的响应时间
     * 2. 否则，执行如下
     * 2.1 若当前缓存量超过最大存储量{@link max_storage}，执行更新缓存
     * 2.2 插入任务到缓存中，并设置该任务的频率=1
     *
     * @param cloudlet 任务
     * @return 响应时间
     */
    public double insert(ContainerCloudlet cloudlet) {
        double qps = 0;
        int id = cloudlet.containerId;
        if (cache.containsKey(id)) {
            qps += qps(ERedisOperationType.Query);
        } else {
            if (cache.size() >= max_storage) {
                qps += update();
            }
            cache.put(id, System.currentTimeMillis());
            cache_request_count.put(id, 1);
        }

        return qps;
    }

    /**
     * 执行更新缓存操作
     * 1. 将缓存任务按请求频率升序排序
     * 2. 选择请求频率最低的一组任务（可能为多个）
     * 3. 若请求频率最低的有多个，则执行4，否则执行5
     * 4. 从请求频率最低的一组任务中，按最近最久未请求排序筛选
     * 5. 从缓存中删除过滤的任务列表
     *
     * @return 响应时间
     */
    private double update() {
        double qps = 0;
        List<Integer> ids;
        List<Integer> minimumRequest = findMinimumRequest(cache_request_count);
        qps += qps(ERedisOperationType.QueryMinimumRequest);

        if (1 != minimumRequest.size()) {
            ids = findNotRequestRecently(cache.entrySet().stream()
                    .filter(entry -> minimumRequest.contains(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            qps += qps(ERedisOperationType.QueryNotRequestRecently);
        } else {
            ids = minimumRequest;
        }
        for (Integer id : ids) {
            cache.remove(id);
            cache_request_count.remove(id);
        }
        return qps;
    }

    /**
     * 从缓存请求频率表中选择频率最低的任务列表
     *
     * @param map 缓存请求频率表
     * @return 请求频率最低的任务列表
     */
    private List<Integer> findMinimumRequest(Map<Integer, Integer> map) {
        map = MapOperator.sortMapByValue(map);

        List<Integer> ids = Lists.newArrayListWithExpectedSize(1);

        int min_count = -1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (-1 == min_count) {
                min_count = entry.getValue();
            }
            if (min_count != entry.getValue()) {
                break;
            }
            ids.add(entry.getKey());
        }
        return ids;
    }

    /**
     * 从请求频率最低的任务列表中筛选最近最久未访问的任务列表
     *
     * @param map 请求频率最低的任务列表
     * @return 最近最久未访问的任务列表
     */
    private List<Integer> findNotRequestRecently(Map<Integer, Long> map) {
        map = MapOperator.sortMapByValue(map);
        List<Integer> ids = Lists.newArrayListWithExpectedSize(1);

        long min_count = -1;
        for (Map.Entry<Integer, Long> entry : map.entrySet()) {
            if (-1 == min_count) {
                min_count = entry.getValue();
            }
            if (min_count != entry.getValue()) {
                break;
            }
            ids.add(entry.getKey());
        }
        return ids;
    }
}
