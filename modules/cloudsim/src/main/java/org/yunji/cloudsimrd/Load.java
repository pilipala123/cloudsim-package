package org.yunji.cloudsimrd;

import org.cloudbus.cloudsim.Cloudlet;

import java.util.List;

/**
 * @author weirenjie
 * @date 2019/10/22
 */

/**
 * 普通任务。
 * 包含了一个Cloudlet和一个请求地址。
 */
public class Load {
    public Cloudlet singleTask;

    public String targetURL;

    public Load(Cloudlet cloudlet, String targetURL){
        this.singleTask = cloudlet;
        this.targetURL = targetURL;
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
}
