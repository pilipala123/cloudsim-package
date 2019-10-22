package org.yunji.cloudsimrd;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;
import java.util.*;




/**
 * @author weirenjie
 * @date 2019/10/14
 */
public class LoadBalance {
    public static int CLOUDLET = 1;
    public static int VM = 2;
    public static int CONTAINER = 3;

    public Map<Integer, ArrayList> allocateCould(Map<Integer, ArrayList> sourceMap) {
        ArrayList<Cloudlet> cloudlets = sourceMap.get(CLOUDLET);
        ArrayList<Vm> vms = sourceMap.get(VM);



        Map resultMap = new HashMap<Integer, ArrayList>();
        resultMap.put(CLOUDLET, cloudlets);
        resultMap.put(VM, vms);
        return resultMap;
    }
}



