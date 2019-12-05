package org.cloudbus.cloudsim.container.load.load;

import org.cloudbus.cloudsim.Cloudlet;

import java.util.List;

/**
 * @author weirenjie
 * @date 2019/10/22
 */

/**
 * 并发任务，包含若干个普通任务
 */
public class ConcurrencyLoads {

    /**
     * 并发任务id
     */
    public int conLoadsId;
    /**
     * 包含的普通任务
     */
    private List<Cloudlet> cloudlets;

    /**
     * 并发数
     */
    private int concurrencyNumber = 1;

    /**
     * 包含的普通任务数量
     */
    private int cloudletNumbers;

    /**
     * 最大响应时间。
     * 可以设置为单独load中响应时间的最大值。
     */
    public int responseTime = 1000;

    /**
     * 任务创建时间
     */
    protected double createdTime;

    /**
     * 请求地址
     */
    public String targetURL;

    /**
     * 平均响应时间
     */
    public double averageResponseTime;

    /**
     * p95响应时间
     */
    public double p95ResponseTime;

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

    public ConcurrencyLoads(List<Cloudlet> cloudlets, int concurrencyNumber) {
        this.cloudlets = cloudlets;
        this.concurrencyNumber = concurrencyNumber;
        this.cloudletNumbers = cloudlets.size();
    }


    public int getConLoadsId() {
        return conLoadsId;
    }

    public void setConLoadsId(int conLoadsId) {
        this.conLoadsId = conLoadsId;
    }


    public int getConcurrencyNumber() {
        return concurrencyNumber;
    }

    public void setConcurrencyNumber(int concurrencyNumber) {
        this.concurrencyNumber = concurrencyNumber;
    }

    public int getCloudletNumbers() {
        return cloudletNumbers;
    }

    public void setCloudletNumbers(int cloudletNumbers) {
        this.cloudletNumbers = cloudletNumbers;
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

    public List<Cloudlet> getCloudlets() {
        return cloudlets;
    }

    public void setCloudlets(List<Cloudlet> cloudlets) {
        this.cloudlets = cloudlets;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(double averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public double getP95ResponseTime() {
        return p95ResponseTime;
    }

    public void setP95ResponseTime(double p95ResponseTime) {
        this.p95ResponseTime = p95ResponseTime;
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

    public void setMedianTime(double medianTime) {
        this.medianTime = medianTime;
    }

    public double getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(double loadDuration) {
        this.loadDuration = loadDuration;
    }
}
