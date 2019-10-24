package org.yunji.cloudsimrd.load;

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
    private List<Load> loads;

    /**
     * 并发数
     */
    private int concurrencyNumber = 1;

    /**
     * 包含的普通任务数量
     */
    private int loadNumbers;

    /**
     * 最大响应时间。
     * 可以设置为单独load中响应时间的最大值。
     */
    public int responseTime = 1000;

    /**
     * 任务创建时间
     */
    protected double createdTime;

    public ConcurrencyLoads(List<Load> loads, int concurrencyNumber) {
        this.loads = loads;
        this.concurrencyNumber = concurrencyNumber;
        this.loadNumbers = loads.size();
    }


    public int getConLoadsId() {
        return conLoadsId;
    }

    public void setConLoadsId(int conLoadsId) {
        this.conLoadsId = conLoadsId;
    }

    public List<Load> getLoads() {
        return loads;
    }

    public void setLoads(List<Load> loads) {
        this.loads = loads;
    }

    public int getConcurrencyNumber() {
        return concurrencyNumber;
    }

    public void setConcurrencyNumber(int concurrencyNumber) {
        this.concurrencyNumber = concurrencyNumber;
    }

    public int getLoadNumbers() {
        return loadNumbers;
    }

    public void setLoadNumbers(int loadNumbers) {
        this.loadNumbers = loadNumbers;
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
}
