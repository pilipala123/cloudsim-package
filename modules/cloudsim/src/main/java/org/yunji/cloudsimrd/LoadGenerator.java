package org.yunji.cloudsimrd;

import org.cloudbus.cloudsim.Cloudlet;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.util.WorkloadFileReader;

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

    // public List<Cloudlet>
}
