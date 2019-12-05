package org.cloudbus.cloudsim.container.load;

import java.util.ArrayList;
import java.util.List;


/**
 * @author weirenjie
 * @date 2019/10/14
 */
public class LoadReceive {
    public static int CLOUDLET = 1;
    public static int VM = 2;
    public static int CONTAINER = 3;

    // public Map<Integer, ArrayList> allocateCould(Map<Integer, ArrayList> sourceMap) {
    //     ArrayList<Cloudlet> cloudlets = sourceMap.get(CLOUDLET);
    //     ArrayList<Vm> vms = sourceMap.get(VM);
    //
    //
    //
    //     Map resultMap = new HashMap<Integer, ArrayList>();
    //     resultMap.put(CLOUDLET, cloudlets);
    //     resultMap.put(VM, vms);
    //     return resultMap;
    // }

    /**
     * 计算任务的响应时间
     *
     * @param loads
     * @return
     */
    public List<Double> getResponseTime(List<Load> loads) {

        List<Double> responseTimeList = new ArrayList<>();
        return responseTimeList;
    }

    /**
     * 计算任务的错误率
     *
     * @param loads
     * @return
     */
    public List<Double> getErrorRate(List<Load> loads) {
        List<Double> errorRateList = new ArrayList<>();
        return errorRateList;
    }
}



