package org.cloudbus.cloudsim.container.load;

import org.cloudbus.cloudsim.container.core.ContainerCloudlet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * LoadGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11月 14, 2019</pre>
 */
public class LoadGeneratorTest {

    public static void main(String[] args) {

        LoadGeneratorMDSP loadGeneratorMDSP = new LoadGeneratorMDSP();
        MathUtil mathUtil = new MathUtil();
        LoadPropertiesMDSP loadPropertiesMDSP = new LoadPropertiesMDSP();

        //根据起始点和结束点生成幂函数形状的负载
        LinkedHashMap<Double, Integer> map1 = mathUtil.powerF(10, 0, 600, 8000, 1);
        //根据起始点和结束点生成直线形状的负载
        LinkedHashMap<Double, Integer> map2 = mathUtil.linearF(600, 8000, 1200, 9000, 1);
        LinkedHashMap<Double, Integer> map3 = mathUtil.linearF(1200, 9000, 1300, 0, 1);
        //合并不同阶段的负载
        Map<Double, Integer> map = mathUtil.mergeMap(mathUtil.mergeMap(map1, map2), map3);
        //将负载保存到配置文件
        loadGeneratorMDSP.saveLoadConfig("loadTrace", "rateTrace", map, loadPropertiesMDSP.CPUMax);
        //读取配置文件生成任务
//        List<ContainerCloudlet> cloudlets = loadGeneratorMDSP.generateContainerCloudletsFromList(loadGeneratorMDSP.readListFromFile("/dev/xlx/cloudsim31/modules/cloudsim/src/main/java/org/cloudbus/cloudsim/container/load/trace-29", map.size()));
//        System.out.println(cloudlets.size());
    }


}

