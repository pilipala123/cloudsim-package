package org.cloudbus.cloudsim.container.load;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author weirenjie
 * @date 2019/11/21
 */
public class MathUtil {
    public Map logF(double beginTime, Integer beginAmount, double endTime, Integer endAmount) {
        Map<Double, Integer> resultMap = new HashMap<>();
        return resultMap;
    }

    /**
     * 根据参数
     * 生成直线
     * 生成任务
     * y = kx + b
     *
     * @param slope     斜率
     * @param increment 截距
     * @param beginTime 起始时间
     * @param endTime   结束时间
     * @param interval  生成任务间隔
     * @return
     */
    public Map linearFStandard(double slope, double increment, double beginTime, double endTime, double interval) {
        Map<Double, Integer> resultMap = new HashMap<>();
        Integer amount;
        for (double i = beginTime; i < endTime; i += interval) {
            amount = new Double(slope * i + increment).intValue();
            resultMap.put(i, amount);
        }
        return resultMap;
    }

    /**
     * 根据开始和结束的时间和请求数量
     * 生成直线
     * 生成任务
     * y = kx + b
     *
     * @param beginTime   起始时间
     * @param beginAmount 起始时间生成的任务数
     * @param endAmount   结束时间
     * @param endTime     结束时间生成的任务数
     * @param interval    生成任务间隔
     * @return
     */
    public LinkedHashMap linearF(double beginTime, Integer beginAmount, double endTime, Integer endAmount, double interval) {
        LinkedHashMap<Double, Integer> resultMap = new LinkedHashMap<>();
        double slope = (endAmount - beginAmount) / (endTime - beginTime);
        double b = beginAmount - slope * beginTime;
        System.out.println("斜率：" + slope);
        System.out.println("截距："+ b);
        Integer amount;
        for (double i = beginTime; i < endTime; i += interval) {
            // amount = new Double(slope * (i - beginTime) + beginAmount).intValue();
            amount = new Double(slope * i + b).intValue();
            System.out.println(i + "" + amount);
            resultMap.put(i, amount);
        }
        return resultMap;
    }

    /**
     * 根据参数
     * 生成幂函数
     * 生成任务
     * y = a * x^n
     *
     * @param a
     * @param n
     * @param beginTime
     * @param endTime
     * @param interval
     * @return
     */
    public Map powerFStandard(double a, double n, double beginTime, double endTime, double interval) {
        Map<Double, Integer> resultMap = new HashMap<>();
        Integer amount;
        for (double i = beginTime; i < endTime; i += interval) {
            amount = new Double(a * Math.pow(i, n)).intValue();
            resultMap.put(i, amount);
        }
        return resultMap;
    }

    /**
     * 根据开始和结束的时间和请求数量
     * 生成幂函数
     * 生成任务
     * y = a * x^n
     *
     * @param beginTime   起始时间
     * @param beginAmount 起始时间生成的任务数
     * @param endTime     结束时间
     * @param endAmount   结束时间生成的任务数
     * @param interval    生成任务间隔
     * @return
     */
    public LinkedHashMap powerF(double beginTime, Integer beginAmount, double endTime, Integer endAmount, double interval) {
        if (beginAmount == 0) {
            beginAmount = beginAmount + 1;
        }
        if (beginTime == 0) {
            beginTime = beginAmount + 1;
        }
        LinkedHashMap<Double, Integer> resultMap = new LinkedHashMap<>();
        double s = Math.log(endAmount / (beginAmount));
        double t = Math.log(endTime / beginTime);
        double n = s / t;
        double a = beginAmount / Math.pow(beginTime, n);
        Integer amount;
        for (double i = beginTime; i < endTime; i += interval) {
            amount = new Double(a * Math.pow(i, n)).intValue();
            resultMap.put(i, amount);
        }
        return resultMap;
    }

    /**
     * 根据参数
     * 生成指数函数
     * 生成任务
     * y = a * e ^ x + b
     *
     * @param a
     * @param b
     * @param beginTime
     * @param endTime
     * @param interval
     * @return
     */
    public Map exponentialFStandard(double a, double b, double beginTime, double endTime, double interval) {
        Map<Double, Integer> resultMap = new LinkedHashMap<>();
        Integer amount;
        for (double i = beginTime; i < endTime; i += interval) {
            amount = new Double(a * Math.exp(i) + b).intValue();
            resultMap.put(i, amount);
        }
        return resultMap;
    }

    /**
     * 根据开始和结束的时间和请求数量
     * 生成指数函数
     * 生成任务
     * y = a * e ^ x + b
     *
     * @param beginTime   起始时间
     * @param beginAmount 起始时间生成的任务数
     * @param endTime     结束时间
     * @param endAmount   结束时间生成的任务数
     * @param interval    生成任务间隔
     * @return
     */
    public Map exponentialF(double beginTime, Integer beginAmount, double endTime, Integer endAmount,
                            double interval) {
        if (beginAmount == 0) {
            beginAmount = beginAmount + 1;
        }
        if (beginTime == 0) {
            beginTime = beginAmount + 1;
        }
        Map<Double, Integer> resultMap = new LinkedHashMap<>();
        double s = new Double(endAmount - beginAmount);
        Double t = Math.exp(endTime) - Math.exp(beginTime);
        double a = s / t;
        double b = endAmount - a * Math.exp(endTime);
        Integer amount;
        for (double i = beginTime; i < endTime; i += interval) {
            amount = new Double(a * Math.exp(i) + b).intValue();
            resultMap.put(i, amount);
        }
        return resultMap;
    }


    /**
     * 根据参数
     * 生成对数函数
     * 生成任务
     * y = ln(x + a) + b
     *
     * @param a
     * @param b
     * @param beginTime
     * @param endTime
     * @param interval
     * @return
     */
    public Map logarithmFStandard(double a, double b, double beginTime, double endTime, double interval) {
        Map<Double, Integer> resultMap = new LinkedHashMap<>();
        Integer amount;
        for (double i = beginTime; i < endTime; i += interval) {
            amount = new Double(Math.log(i + a) + b).intValue();
            resultMap.put(i, amount);
        }
        return resultMap;
    }

    /**
     * 根据开始和结束的时间和请求数量
     * 生成对数函数
     * 生成任务
     * y = e ^ (x + a) + b
     *
     * @param beginTime   起始时间
     * @param beginAmount 起始时间生成的任务数
     * @param endTime     结束时间
     * @param endAmount   结束时间生成的任务数
     * @param interval    生成任务间隔
     * @return
     */
    public Map logarithmF(double beginTime, Integer beginAmount, double endTime, Integer endAmount, double interval) {
        Map<Double, Integer> resultMap = new LinkedHashMap<>();
        //to_do
        return resultMap;
    }

    /**
     * 合并Map
     *
     * @param map1
     * @param map2
     * @return
     */
    public LinkedHashMap<Double, Integer> mergeMap(LinkedHashMap<Double, Integer> map1, LinkedHashMap<Double, Integer> map2) {
        Iterator<Map.Entry<Double, Integer>> entries = map2.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<Double, Integer> entry = entries.next();
            map1.put(entry.getKey(), entry.getValue());
        }
        return map1;
    }

}


