package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.InputParament.LoadGeneratorInput;
import org.cloudbus.cloudsim.container.core.Siemens.RegressionParament;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;
import org.cloudbus.cloudsim.container.schedulers.ContainerCloudletScheduler;
import org.yunji.cloudsimrd.load.LoadGenerator;

import java.io.FileNotFoundException;
import java.util.List;

import static org.cloudbus.cloudsim.container.core.util.Process.processRequests;

public class ServiceLoadBalancerNFR extends Host {

    private int responseTime;

    private int qps;

    private SiemensList nfrsiemensList;

    public SiemensList getNfrsiemensList() {
        return nfrsiemensList;
    }

    public void setNfrsiemensList(SiemensList nfrsiemensList) {
        this.nfrsiemensList = nfrsiemensList;
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
    public ServiceLoadBalancerNFR(int id, int userId, double mips, int numberOfPes, int ram, long bw,
                                  List<ContainerCloudlet> cloudletList,
                                  LoadGeneratorInput loadGeneratorInput,
                                  RegressionParament regressionParament,

                                  int flag) {
        super(id);
        /**
         * Functions to calculate response time and qps will be added here
         */
        this.nfrsiemensList= null;
        try {
            this.nfrsiemensList = processRequests(cloudletList,1000,1000,"NfR",loadGeneratorInput,18,9);
            Calculatebw.calculateregressionbw("nfr","gwtma",flag,regressionParament,this.nfrsiemensList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setResponseTime(this.nfrsiemensList.getFinishtime());
//        setResponseTime(100);
        setQps(100);
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

    public void setQps(int qps){
        this.qps = qps;
    }

    /**
     * Process the requests and generate response time
     * @param cloudletList
     */
//    public void processRequests(List<ContainerCloudlet> cloudletList){
//
//    }

}
