package org.cloudbus.cloudsim.container.load.load;

import org.cloudbus.cloudsim.container.core.ContainerCloudlet;
import org.cloudbus.cloudsim.container.load.LoadGeneratorMDSP;
import org.cloudbus.cloudsim.container.load.LoadPropertiesMDSP;
import org.cloudbus.cloudsim.container.load.MathUtil;

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
        int brokerId = 0;

        LoadGeneratorMDSP loadGeneratorMDSP = new LoadGeneratorMDSP();
        MathUtil mathUtil = new MathUtil();
        LoadPropertiesMDSP loadPropertiesMDSP = new LoadPropertiesMDSP();

        //根据起始点和结束点生成幂函数形状的负载
        LinkedHashMap<Double, Integer> map1 = mathUtil.powerF(10, 0, 600, 800, 1);
        //根据起始点和结束点生成直线形状的负载
        LinkedHashMap<Double, Integer> map2 = mathUtil.linearF(600, 800, 1200, 800, 1);
        LinkedHashMap<Double, Integer> map3 = mathUtil.linearF(1200, 800, 1300, 0, 1);
        //合并不同阶段的负载
        Map<Double, Integer> map = mathUtil.mergeMap(mathUtil.mergeMap(map1, map2), map3);
        //将负载保存到配置文件
//        loadGeneratorMDSP.saveLoadConfig("loadTrace", "rateTrace", map, loadPropertiesMDSP.CPUMax);
        //读取配置文件生成任务
//        List<ContainerCloudlet> cloudletList = loadGeneratorMDSP.generateContainerCloudletsFromList(loadGeneratorMDSP.readListFromFile("/dev/xlx/CloudSimNFR-master/cloudsim-5.0/loadTrace,1290", map.size()));
//        System.out.println(cloudletList.size());
//        for (ContainerCloudlet containerCloudlet: cloudletList) {
//            containerCloudlet.setUserId(brokerId);
//        }
    }


}

