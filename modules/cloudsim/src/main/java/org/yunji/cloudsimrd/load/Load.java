package org.yunji.cloudsimrd.load;

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
     * @default 1000
     */
    public int responseTime = 1000;

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
}
