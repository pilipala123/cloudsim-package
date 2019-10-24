package org.yunji.cloudsimrd.load;

import org.cloudbus.cloudsim.Cloudlet;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.util.WorkloadFileReader;
import org.yunji.cloudsimrd.load.ConcurrencyLoads;
import org.yunji.cloudsimrd.load.Load;

/**
 * @author weirenjie
 * @date 2019/10/22
 */

/**
 * 负载生成器，仿真负载。
 */
public class LoadGenerator {
    private int rating = 1;

    private String defaultUrl = "www.baidu.com";

    /**
     * 从文件中读取任务
     * @param fileName
     * @return
     */
    public List<Load> generateLoadsFromFile(String fileName) {
        List<Load> loads = new ArrayList<>();
        try {
            WorkloadFileReader workloadFileReader = new WorkloadFileReader(fileName, this.rating);
            ArrayList<Cloudlet> cloudlets = workloadFileReader.generateWorkload();


            for (Cloudlet cloudlet : cloudlets) {
                Load load = new Load(cloudlet, defaultUrl);
                loads.add(load);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return loads;
        }
    }

    /**
     * 根据默认参数生成Load
     * @param brokerId
     * @param vmid
     * @return
     */
    public Load generateLoad(int brokerId, int vmid) {
        int id = 0;
        long length = 400000;
        long fileSize = 300;
        long outputSize = 300;
        // number of cpus
        int pesNumber = 1;
        UtilizationModel utilizationModel = new UtilizationModelFull();

        // 用刚才定义的参数创建云任务
        Cloudlet cloudlet =
                new Cloudlet(id, length, pesNumber, fileSize,
                        outputSize, utilizationModel, utilizationModel,
                        utilizationModel);
        cloudlet.setUserId(brokerId);
        cloudlet.setVmId(vmid);
        Load load = new Load(cloudlet, this.defaultUrl);
        return load;
    }

    /**
     * 生成并发任务
     * @param loads 普通任务
     * @param concurrencyNumber 并发数
     * @return
     */
    public ConcurrencyLoads getConcurrencyLoads(List<Load> loads, Integer concurrencyNumber) {

        if (null == concurrencyNumber) {
            concurrencyNumber = 1;
        }
        ConcurrencyLoads concurrencyLoads = new ConcurrencyLoads(loads, concurrencyNumber);
        return concurrencyLoads;
    }


}
