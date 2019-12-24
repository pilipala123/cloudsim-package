package org.cloudbus.cloudsim.examples.container;


/*
 * Title:        ContainerCloudSimExample1 Toolkit
 * Description:  ContainerCloudSimExample1 (containerized cloud simulation) Toolkit for Modeling and Simulation
 *               of Containerized Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModelNull;
import org.cloudbus.cloudsim.container.InputParament.*;
import org.cloudbus.cloudsim.container.containerProvisioners.ContainerBwProvisionerSimple;
import org.cloudbus.cloudsim.container.containerProvisioners.ContainerPe;
import org.cloudbus.cloudsim.container.containerProvisioners.ContainerRamProvisionerSimple;
import org.cloudbus.cloudsim.container.containerProvisioners.CotainerPeProvisionerSimple;
import org.cloudbus.cloudsim.container.containerVmProvisioners.ContainerVmBwProvisionerSimple;
import org.cloudbus.cloudsim.container.containerVmProvisioners.ContainerVmPe;
import org.cloudbus.cloudsim.container.containerVmProvisioners.ContainerVmPeProvisionerSimple;
import org.cloudbus.cloudsim.container.containerVmProvisioners.ContainerVmRamProvisionerSimple;
import org.cloudbus.cloudsim.container.core.*;
import org.cloudbus.cloudsim.container.core.Siemens.RegressionParament;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;
import org.cloudbus.cloudsim.container.core.util.CalculateSumResponsetime;
import org.cloudbus.cloudsim.container.core.util.Calculatebw;
import org.cloudbus.cloudsim.container.hostSelectionPolicies.HostSelectionPolicy;
import org.cloudbus.cloudsim.container.hostSelectionPolicies.HostSelectionPolicyFirstFit;
import org.cloudbus.cloudsim.container.load.ConcurrencyLoads;
import org.cloudbus.cloudsim.container.load.LoadGeneratorMDSP;
import org.cloudbus.cloudsim.container.load.LoadPropertiesMDSP;
import org.cloudbus.cloudsim.container.resourceAllocatorMigrationEnabled.PowerContainerVmAllocationPolicyMigrationAbstractHostSelection;
import org.cloudbus.cloudsim.container.resourceAllocators.ContainerAllocationPolicy;
import org.cloudbus.cloudsim.container.resourceAllocators.ContainerVmAllocationPolicy;
import org.cloudbus.cloudsim.container.resourceAllocators.PowerContainerAllocationPolicySimple;
import org.cloudbus.cloudsim.container.schedulers.ContainerCloudletSchedulerDynamicWorkload;
import org.cloudbus.cloudsim.container.schedulers.ContainerSchedulerTimeSharedOverSubscription;
import org.cloudbus.cloudsim.container.schedulers.ContainerVmSchedulerTimeSharedOverSubscription;
import org.cloudbus.cloudsim.container.utils.IDs;
import org.cloudbus.cloudsim.container.vmSelectionPolicies.PowerContainerVmSelectionPolicy;
import org.cloudbus.cloudsim.container.vmSelectionPolicies.PowerContainerVmSelectionPolicyMaximumUsage;
import org.cloudbus.cloudsim.core.CloudSim;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

import static org.cloudbus.cloudsim.container.core.LoadProperties.*;

/**
 * A simple example showing how to create a data center with one host, one VM, one container and run one cloudlet on it.
 */
public class ContainerCloudSimExample1 {

    /**
     * The cloudlet list.
     */
    private static List<ContainerCloudlet> cloudletList;

    /**
     * The vmlist.
     */
    private static List<ContainerVm> vmList;

    /**
     * The vmlist.
     */

    private static List<Container> containerList;

    /**
     * The hostList.
     */

    private static List<ContainerHost> hostList;

    /**
     * Creates main() to run this example.
     *
     * @param args the args
     */

    public static void main(String[] args) {
        Log.printLine("Starting ContainerCloudSimExample1...");

        try {
            /**
             * S1: Use properties input the paraments
             */
            Properties properties = new Properties();
            Properties properties1 = new Properties();
            InputStream inputStream = null;
            InputStream inputStream2 = null;
            LoadGeneratorInput loadGeneratorInput = new LoadGeneratorInput();
            EcsInput ecsInput = new EcsInput();
            K8sInput k8sInput = new K8sInput();
            SlbInput slbInput = new SlbInput();
            RegressionParament regressionParament = new RegressionParament();
            AdjustParament adjustParament = new AdjustParament();
            NfrInput nfrInput = new NfrInput();
            try {
                inputStream = new FileInputStream("modules/cloudsim/src/main/resources/config.properties");
                properties.load(inputStream);
                inputStream2 = new FileInputStream("modules/cloudsim/src/main/resources/parament.properties");
                properties1.load(inputStream2);
                //ecs init
                regressionParament = setregressionParament(properties);
                ecsInput = setEcsInput(properties);
                k8sInput = setk8sInput(properties);
                slbInput = setslbInput(properties);
                nfrInput = setnfrInput(properties);
                adjustParament = setAdjustParament(properties1);
                loadGeneratorInput = setLoadGeneratorInput(properties);


            } catch (IOException error) {
                error.printStackTrace();
            }finally {
                if(inputStream !=null) {
                    try {
                        inputStream.close();
                    } catch (IOException error2) {
                        error2.printStackTrace();
                    }
                }
            }

            LoadGeneratorMDSP loadGeneratorMDSP = new LoadGeneratorMDSP();
//            MathUtil mathUtil = new MathUtil();
            LoadPropertiesMDSP loadPropertiesMDSP = new LoadPropertiesMDSP();
            int loadnumber = loadGeneratorInput.getLoadnumbers();
            int ramp_up = loadGeneratorInput.getRamp_up();
            int ramp_down = loadGeneratorInput.getRamp_down();
            int duration = loadGeneratorInput.getLoadduration();
            /**
             * S2: Use Load generator generate loads
             */
            List<ContainerCloudlet> cloudlets = loadGeneratorMDSP.generateContainerCloudletsFromList(loadGeneratorInput,adjustParament);
            /**
             * S3:init
             */
            ServiceLoadBalancerGW slb_gw = new ServiceLoadBalancerGW(0,cloudlets,loadnumber,ramp_down,adjustParament,ecsInput,slbInput);
            Redis redis = new Redis(0,cloudlets,loadnumber,ramp_down,adjustParament,ecsInput,slbInput);
            GW_K8S gw_k8S = new GW_K8S(0,cloudlets,loadnumber,ramp_down,adjustParament,ecsInput,k8sInput);
            ServiceLoadBalancerNFR nfr = new ServiceLoadBalancerNFR(0,cloudlets,loadnumber,ramp_down,adjustParament,ecsInput,nfrInput);
            MockService mockService = new MockService(0);
            int flag = 1;    //flag为0时计算带宽时采用单元回归，为1时采用多元回归
            /**
            * S4: init SiemensList,which is used to store the results
            */
            SiemensList slbsiemensList=null;
            SiemensList redisSiementsList;
            SiemensList k8ssiemensList= null;
            SiemensList nfrsiemensList=null;
            SiemensList mockservicesiemenslist = null;
            List<SiemensList> siemensListList = new ArrayList<>();
            List<SiemensList> siemensListList2 = new ArrayList<>();
            /**
             * S5:generator the loads which is close to the real environment
             */
            int time =0;
            int loadnumberpertime = 0;
            for(time=0;time<ramp_down;time++) {
                loadnumberpertime = 0;
                for(ContainerCloudlet cloudlet1:cloudlets){
                    cloudlet1.setState(0);
                }
                for (ContainerCloudlet cloudlet:cloudlets){
                    cloudlet.setState(1);   //submitting state
                    cloudlet.setStarttime(time);
                    loadnumberpertime ++;
                    //上升部分的直线
                    if(loadnumberpertime >= ((double)time*(double)loadnumber/(double)ramp_up)){
                        break;
                    }
                    //下降部分的直线
                    if(loadnumberpertime>=((double)(ramp_down-time)*(double)loadnumber/(double)(ramp_down-duration-ramp_up))){
                        break;
                    }
                    //峰值部分的直线
                    if(loadnumberpertime >= loadnumber){
                        break;
                    }
                }
                /**
                 * S6:run the simulation
                 */
                slb_gw.processEvent(loadnumberpertime,3);
//                slb_gw.process(cloudlets,adjustParament,time);
//                redis.process(cloudlets,flag,regressionParament,loadGeneratorInput,adjustParament,time);
                gw_k8S.process(cloudlets,adjustParament,time);
                nfr.processEvent(loadnumberpertime,3);
//                nfr.process(cloudlets,adjustParament,time);
                mockService.processEvent(loadnumberpertime,100);
            }
            /**
             * S7:print the results
             */
            slbsiemensList = slb_gw.getSlbsiemensList();
//            redisSiementsList = redis.getSiemensList();
            k8ssiemensList = gw_k8S.getK8ssiemensList();
            nfrsiemensList = nfr.getNfrsiemensList();
            mockservicesiemenslist = mockService.getMockservicesiemensList();
//            Calculatebw.calculateregressionbw("slb","k8s",flag,regressionParament,slbsiemensList,ramp_down);
//            Calculatebw.calculateregressionbw("k8s","slb",flag,regressionParament,k8ssiemensList,ramp_down);
//            Calculatebw.calculateregressionbw("k8s","redis",flag,regressionParament,k8ssiemensList,ramp_down);
//            Calculatebw.calculateregressionbw("k8s","nfr",flag,regressionParament,k8ssiemensList,ramp_down);
//            Calculatebw.calculateregressionbw("nfr","k8s",flag,regressionParament,nfrsiemensList,ramp_down);
//            Calculatebw.calculateregressionbw("nfr","gwtma",flag,regressionParament,k8ssiemensList,ramp_down);
            //将获取到的结果类做成列表
            siemensListList2.add(slbsiemensList);
//            siemensListList.add(redisSiementsList);
            siemensListList.add(k8ssiemensList);
            siemensListList2.add(nfrsiemensList);
            siemensListList2.add(mockservicesiemenslist);
            for (SiemensList siemensList:siemensListList){

                //绘制图形
                if(siemensList.getState()==1) {
                    Plotpictures.plotpicture(ramp_down, siemensList.getAvgperresponsetimelist(), siemensList.getName() + "每个时刻的平均响应时间", "per response time(ms)");
                    Plotpictures.plotpicture(ramp_down, siemensList.getQpslist(), siemensList.getName() + " qps随时间的关系", "qps(/sec)");
                    Plotpictures.plotpicture(ramp_down, siemensList.getLoadnumber(), siemensList.getName() + " 负载产生数量随时间的关系", "load(vu)");
                    Plotpictures.plotpicture(ramp_down, siemensList.getHostcpuusagelist(), siemensList.getName() + " CPU利用率随时间的关系", "CPU(%)");
                    Plotpictures.plotpicture(ramp_down, siemensList.getHostbwusagelist(), siemensList.getName() + " 带宽利用率随时间的关系", "bw(%)");
                    Plotpictures.plotpicture(ramp_down, siemensList.getAverageresponsetimelist(), siemensList.getName() + " 平均响应时间随时间的关系", "response time(ms)");
                    Plotpictures.plotpicture(ramp_down,siemensList.getVmcpuusagelist(),siemensList.getName()+" vm中 cpu利用率随时间的关系","CPU(%)");
                    Plotpictures.plotpicture(ramp_down,siemensList.getContainercpuusagelist(),siemensList.getName()+" container中 cpu利用率随时间的关系","CPU(%)");
                    Plotpictures.plotpicture(ramp_down,siemensList.getHostoutputbwusage(),siemensList.getName()+" output bw","bw");

                }
            }
//            Plotpictures.plotpicture(ramp_down,mockService.getMockservicesiemensList().getAverageresponsetimelist(),mockService.getName()+"qps随时间的关系","qps");
            CalculateSumResponsetime.calculateresultresponsetime(siemensListList,siemensListList2,ramp_down);
//            CalculateSumResponsetime.calculateresultresponsetime(siemensListList,ramp_down);
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }
    }


    public static ContainerDatacenter createDatacenter(String name, Class<? extends ContainerDatacenter> datacenterClass,
                                                       List<ContainerHost> hostList,
                                                       ContainerVmAllocationPolicy vmAllocationPolicy,
                                                       ContainerAllocationPolicy containerAllocationPolicy,
                                                       String experimentName, double schedulingInterval, String logAddress, double VMStartupDelay,
                                                       double ContainerStartupDelay) throws Exception {
        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double time_zone = 10.0D;
        double cost = 3.0D;
        double costPerMem = 0.05D;
        double costPerStorage = 0.001D;
        double costPerBw = 0.0D;
        ContainerDatacenterCharacteristics characteristics = new
                ContainerDatacenterCharacteristics(arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage,
                costPerBw);
        ContainerDatacenter datacenter = new PowerContainerDatacenterCM(name, characteristics, vmAllocationPolicy,
                containerAllocationPolicy, new LinkedList<Storage>(), schedulingInterval, experimentName, logAddress,
                VMStartupDelay, ContainerStartupDelay);

        return datacenter;
    }

    /**
     * create the containers for hosting the cloudlets and binding them together.
     *
     * @param brokerId
     * @param containersNumber
     * @return
     */


}
