package org.cloudbus.cloudsim.container.load.load;

import org.cloudbus.cloudsim.Cloudlet;

/**
 * @author weirenjie
 * @date 2019/10/22
 */

/**
 * 普通任务。
 * 包含了一个Cloudlet和一个请求地址。
 */
public class Load {
    /**
     * 任务id
     */
    public int loadId;
    /**
     * 一个普通的云任务
     */
    public Cloudlet singleTask;

    /**
     * 请求地址
     */
    public String targetURL;

    /**
     * 任务生成时间
     */
    protected double createdTime;

    /**
     * 最大响应时间
     *
     * @default 1000
     */
    public int responseTime = 1000;

    /**
     * 平均响应时间
     */
    public double averageResponseTime;

    /**
     * Calls per second
     */
    public int qpt;

    /**
     * 错误率
     */
    public int errorRate;

    /**
     * 中值响应时间
     */
    public double medianTime;

    /**
     * 加载时间
     */
    public double loadDuration;

    public Load(Cloudlet cloudlet, String targetURL) {
        this.singleTask = cloudlet;
        this.targetURL = targetURL;
        this.createdTime = System.currentTimeMillis();
    }

    public int getLoadId() {
        return loadId;
    }

    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }

    public Cloudlet getSingleTask() {
        return singleTask;
    }

    public void setSingleTask(Cloudlet singleTask) {
        this.singleTask = singleTask;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public double getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(double createdTime) {
        this.createdTime = createdTime;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(int averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public int getQpt() {
        return qpt;
    }

    public void setQpt(int qpt) {
        this.qpt = qpt;
    }

    public int getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(int errorRate) {
        this.errorRate = errorRate;
    }

    public double getMedianTime() {
        return medianTime;
    }

    public void setMedianTime(int medianTime) {
        this.medianTime = medianTime;
    }

    public double getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(int loadDuration) {
        this.loadDuration = loadDuration;
    }
}
