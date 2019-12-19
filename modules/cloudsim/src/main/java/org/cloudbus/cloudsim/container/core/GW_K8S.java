package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.EcsInput;
import org.cloudbus.cloudsim.container.InputParament.K8sInput;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.core.Siemens.*;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;
import org.cloudbus.cloudsim.container.core.util.SiemensUtils;

import java.io.FileNotFoundException;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;


public class GW_K8S extends Host {

    private int responseTime;

    private int qps;


    private SiemensList k8ssiemensList;


    public SiemensList getK8ssiemensList() {
        return k8ssiemensList;
    }

    public void setK8ssiemensList(SiemensList k8ssiemensList) {
        this.k8ssiemensList = k8ssiemensList;
    }
    /**
     * Creates a new Container-based Redis object.
     *
     * @param id
     * @param userId
     * @param mips
     * @param numberOfPes
     * @param ram
     * @param bw
     */
    public GW_K8S(int id,
                  int userId,
                  double mips,
                  int numberOfPes,
                  int ram,
                  long bw,
                  List<ContainerCloudlet> cloudletList,
                  K8sInput k8sInput,
                  EcsInput ecsInput,
                  LoadGeneratorInput loadGeneratorInput,
                  RegressionParament regressionParament,
                  int flag) {
        super(id);
        /**
         * Functions to calculate response time and qps will be added here
         */
        setK8ssiemensList(null);
        try {
            this.k8ssiemensList = processRequests(cloudletList,300,300,"K8s",loadGeneratorInput,18,9);
            Calculatebw.calculateregressionbw("k8s","slb",flag,regressionParament,this.k8ssiemensList);
            Calculatebw.calculateregressionbw("k8s","redis",flag,regressionParament,this.k8ssiemensList);
            Calculatebw.calculateregressionbw("k8s","nfr",flag,regressionParament,this.k8ssiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.k8ssiemensList.getFinishtime());
//        setQps(k8smoneycost);
        System.out.println("The GWK8s QPS is "+qps);
    }

    public int getResponseTime(){
        return responseTime;
    }

    public void setResponseTime(int responseTime){
        this.responseTime = responseTime;
    }

    public int getQps(){

        return qps;
    }

    public void setQps(int money){
        if (money ==0){
            this.qps = 1000;
        }
        else {
            double qpscaculate = Double.valueOf(money)*0.263;
            this.qps =(int)(qpscaculate) ;
        }
    }

    /**
     * Process the requests and generate response time
     * @param cloudletList
     */
//    public int processRequests(List<ContainerCloudlet> cloudletList,int cpuresources,int memoryresources){
//
//    }

}
