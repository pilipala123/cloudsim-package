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
import org.cloudbus.cloudsim.container.hostSelectionPolicies.HostSelectionPolicy;
import org.cloudbus.cloudsim.container.hostSelectionPolicies.HostSelectionPolicyFirstFit;
import org.cloudbus.cloudsim.container.load.LoadGeneratorMDSP;
import org.cloudbus.cloudsim.container.load.LoadPropertiesMDSP;
import org.cloudbus.cloudsim.container.load.MathUtil;
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
public class ContainerRedisCloudSimExample1 {

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

    public static void main(String[] args)
    {
        Log.printLine("Starting ContainerCloudSimExample1...");

        try {
            /**
             * number of cloud Users
             */
            int num_user = 1;
            /**
             *  The fields of calender have been initialized with the current date and time.
             */
            Calendar calendar = Calendar.getInstance();
            /**
             * Deactivating the event tracing
             */
            boolean trace_flag = false;
            /**
             * 1- Like CloudSim the first step is initializing the CloudSim Package before creating any entities.
             *
             */


            CloudSim.init(num_user, calendar, trace_flag);
            /**
             * 2-  Defining the container allocation Policy. This policy determines how Containers are
             * allocated to VMs in the data center.
             *
             */

            LoadGeneratorMDSP loadGeneratorMDSP = new LoadGeneratorMDSP();
            MathUtil mathUtil = new MathUtil();
            LoadPropertiesMDSP loadPropertiesMDSP = new LoadPropertiesMDSP();

            //根据起始点和结束点生成幂函数形状的负载
            LinkedHashMap<Double, Integer> map1 = mathUtil.powerF(10, 100, 600, 15000, 1);
            //根据起始点和结束点生成直线形状的负载
            LinkedHashMap<Double, Integer> map2 = mathUtil.linearF(600, 15000, 1800, 20000, 1);
            LinkedHashMap<Double, Integer> map3 = mathUtil.linearF(1800, 20000, 2500, 0, 1);
            //合并不同阶段的负载
            Map<Double, Integer> map = mathUtil.mergeMap(mathUtil.mergeMap(map1, map2), map3);
            //将负载保存到配置文件
            loadGeneratorMDSP.saveLoadConfig("loadTrace", "rateTrace", map, loadPropertiesMDSP.CPUMax);
            //读取配置文件生成任务
            List<ContainerCloudlet> cloudlets = loadGeneratorMDSP.generateContainerCloudletsFromList(loadGeneratorMDSP.readListFromFile(LoadGeneratorMDSP.root + "\\modules\\cloudsim\\src\\main\\java\\org\\cloudbus\\cloudsim\\container\\load\\trace-29", map.size()));

            ContainerAllocationPolicy containerAllocationPolicy = new PowerContainerAllocationPolicySimple();

            /**
             * 3-  Defining the VM selection Policy. This policy determines which VMs should be selected for migration
             * when a host is identified as over-loaded.
             *
             */

            PowerContainerVmSelectionPolicy vmSelectionPolicy = new PowerContainerVmSelectionPolicyMaximumUsage();


            /**
             * 4-  Defining the host selection Policy. This policy determines which hosts should be selected as
             * migration destination.
             *
             */
            HostSelectionPolicy hostSelectionPolicy = new HostSelectionPolicyFirstFit();
            /**
             * 5- Defining the thresholds for selecting the under-utilized and over-utilized hosts.
             */

            double overUtilizationThreshold = 0.80;
            double underUtilizationThreshold = 0.70;
            /**
             * 6- The host list is created considering the number of hosts, and host types which are specified
             * in the {@link ConstantsExamples}.
             */
            hostList = new ArrayList<ContainerHost>();
            hostList = createHostList(ConstantsExamples.NUMBER_HOSTS);
            cloudletList = new ArrayList<ContainerCloudlet>();
            vmList = new ArrayList<ContainerVm>();
            /**
             * 7- The container allocation policy  which defines the allocation of VMs to containers.
             */
            ContainerVmAllocationPolicy vmAllocationPolicy = new
                    PowerContainerVmAllocationPolicyMigrationAbstractHostSelection(hostList, vmSelectionPolicy,
                    hostSelectionPolicy, overUtilizationThreshold, underUtilizationThreshold);
            /**
             * 8- The overbooking factor for allocating containers to VMs. This factor is used by the broker for the
             * allocation process.
             */
            int overBookingFactor = 80;
            ContainerDatacenterBroker broker = createBroker(overBookingFactor);
            int brokerId = broker.getId();
            /**
             * 9- Creating the cloudlet, container and VM lists for submitting to the broker.
             */
//            cloudletList =cloudlets;
            cloudletList = createContainerCloudletList(brokerId, ConstantsExamples.NUMBER_CLOUDLETS);
            containerList = createContainerList(brokerId, ConstantsExamples.NUMBER_CLOUDLETS);
            vmList = createVmList(brokerId, ConstantsExamples.NUMBER_VMS);
            /**
             * 10- The address for logging the statistics of the VMs, containers in the data center.
             */
            String logAddress = "~/Results";

            @SuppressWarnings("unused")
            PowerContainerDatacenter e = (PowerContainerDatacenter) createDatacenter("datacenter",
                    PowerContainerDatacenterCM.class, hostList, vmAllocationPolicy, containerAllocationPolicy,
                    getExperimentName("ContainerCloudSimExample-1", String.valueOf(overBookingFactor)),
                    ConstantsExamples.SCHEDULING_INTERVAL, logAddress,
                    ConstantsExamples.VM_STARTTUP_DELAY, ConstantsExamples.CONTAINER_STARTTUP_DELAY);


            /**
             * 11- Submitting the cloudlet's , container's , and VM's lists to the broker.
             */
            broker.submitCloudletList(cloudletList.subList(0, containerList.size()));
            broker.submitContainerList(containerList);
            broker.submitVmList(vmList);


            /**
             * 12- Determining the simulation termination time according to the cloudlet's workload.
             */


//            CloudSim.terminateSimulation(86400.00);
//            /**
//             * 13- Starting the simualtion.
//             */
//            CloudSim.startSimulation();
//            /**
//             * 14- Stopping the simualtion.
//             */
//            CloudSim.stopSimulation();
            int i = 0;

            /**
             * 15- Printing the results when the simulation is finished.
             */
            /**
             * S1: define some paraments
             */
            int bw = 0, ecsmipspercore = 0, ecsmemory = 0, ecscpuquote = 0, ecsbw = 0, k8secsnumber = 0, k8smoney = 0,
                    slbMaxoutboundbandwidth = 0, slbcpuquota = 0, slbmemoryquota = 0, slbmoney = 0;

            /**
             * S2: Use properties input the paraments
             */
            Properties properties = new Properties();
            InputStream inputStream = null;

            try {
                inputStream = new FileInputStream(LoadGeneratorMDSP.root + "/modules/cloudsim/src/main/resources/config.properties");
                properties.load(inputStream);
                EcsInput ecsInput = setEcsInput(properties);
                ecsmipspercore = ecsInput.getEcsMIPSpercore();
                ecsmemory = ecsInput.getEcsMemoryQuota();
                ecscpuquote = ecsInput.getEcsCPUQuota();
                ecsbw = ecsInput.getEcsOutboundbandwidth();
                ContainerInput containerInput = setContainerInput(properties);
                K8sInput k8sInput = setK8sInput(properties);
                k8secsnumber = k8sInput.getECSNumbers();
                k8smoney = k8sInput.getK8smoney();
                LoadGeneratorInput loadGeneratorInput = setLoadGeneratorInput(properties);
                SlbInput slbInput = setslbInput(properties);
                slbMaxoutboundbandwidth = slbInput.getSlbMaxoutboundbandwidth();
                slbcpuquota = slbInput.getSlbCPUQuota();
                slbmemoryquota = slbInput.getSlbMemoryQuota();
                slbmoney = slbInput.getSlbmoney();
                RedisInput redisInput = setRedisInput(properties);
                TablestoreInput tablestoreInput = settablestoreInput(properties);
            } catch (IOException error) {
                error.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException error2) {
                        error2.printStackTrace();
                    }
                }
            }
            /**
             * S2: Requests are going through SLB_GW
             */
//            ServiceLoadBalancerGW slb_gw = new ServiceLoadBalancerGW(0, 0, ecsmipspercore * slbcpuquota
//                    , slbcpuquota, slbmemoryquota, slbMaxoutboundbandwidth, cloudlets);
//            broker.connectWithSLB_GW(slb_gw);
            /**
             * S3: Requests processed by GW K8S
             * Capacity of SLB GW can be configured here
             */

//            GW_K8S gw_k8s = new GW_K8S(0, 0, k8secsnumber * ecsmipspercore, k8secsnumber * ecscpuquote, k8secsnumber * ecsmemory, k8smoney, k8secsnumber * ecsbw, cloudletList);
//            broker.connectWithGW_K8S(gw_k8s);

            /**
             * S4: Connect with Redis, update response time
             * Redis configurations can be configured here
             */
            Redis redis = new Redis(0, 0, 1000, 1, 512, 1000, cloudlets);
            broker.connectWithRedis(redis);

            /**
             * S5: Requests going through SLB NFR
             * SLB specification can be defined here
             */
            ServiceLoadBalancerNFR slb_nfr = new ServiceLoadBalancerNFR(0, 0, 2000, 1, 512, 1000, cloudletList);
            broker.connectWithSLB_NFR(slb_nfr);

            /**
             * S6: Connect with MockService();
             * Mock service (constant response time configured here). The response time should be updated
             */
            broker.connectWithMockService(new MockService("Service1", "MS1", 300));

            List<ContainerCloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);
            Log.printConcatLine("Total Response Time is ", broker.getResponeTime() + "ms");

            Log.printLine("ContainerCloudSimExample1 finished!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }
    }


    /**
     * It creates a specific name for the experiment which is used for creating the Log address folder.
     */

    private static String getExperimentName(String... args) {
        StringBuilder experimentName = new StringBuilder();

        for (int i = 0; i < args.length; ++i) {
            if (!args[i].isEmpty()) {
                if (i != 0) {
                    experimentName.append("_");
                }

                experimentName.append(args[i]);
            }
        }

        return experimentName.toString();
    }

    /**
     * Creates the broker.
     *
     * @param overBookingFactor
     * @return the datacenter broker
     */
    private static ContainerDatacenterBroker createBroker(int overBookingFactor) {

        ContainerDatacenterBroker broker = null;

        try {
            broker = new ContainerDatacenterBroker("Broker", overBookingFactor);
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(0);
        }

        return broker;
    }

    /**
     * Prints the Cloudlet objects.
     *
     * @param list list of Cloudlets
     */
    private static void printCloudletList(List<ContainerCloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
                + "Data center ID" + indent + "VM ID" + indent + "Time" + indent
                + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

            if (cloudlet.getCloudletStatusString() == "Success") {
                Log.print("SUCCESS");

                Log.printLine(indent + indent + cloudlet.getResourceId()
                        + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent
                        + dft.format(cloudlet.getActualCPUTime()) + indent
                        + indent + dft.format(cloudlet.getExecStartTime())
                        + indent + indent
                        + dft.format(cloudlet.getFinishTime()));
            }
        }
    }

    /**
     * Create the Virtual machines and add them to the list
     *
     * @param brokerId
     * @param containerVmsNumber
     */
    private static ArrayList<ContainerVm> createVmList(int brokerId, int containerVmsNumber) {
        ArrayList<ContainerVm> containerVms = new ArrayList<ContainerVm>();

        for (int i = 0; i < containerVmsNumber; ++i) {
            ArrayList<ContainerPe> peList = new ArrayList<ContainerPe>();
            int vmType = i / (int) Math.ceil((double) containerVmsNumber / 4.0D);
            for (int j = 0; j < ConstantsExamples.VM_PES[vmType]; ++j) {
                peList.add(new ContainerPe(j,
                        new CotainerPeProvisionerSimple((double) ConstantsExamples.VM_MIPS[vmType])));
            }
            containerVms.add(new PowerContainerVm(IDs.pollId(ContainerVm.class), brokerId,
                    (double) ConstantsExamples.VM_MIPS[vmType], (float) ConstantsExamples.VM_RAM[vmType],
                    ConstantsExamples.VM_BW, ConstantsExamples.VM_SIZE, "Xen",
                    new ContainerSchedulerTimeSharedOverSubscription(peList),
                    new ContainerRamProvisionerSimple(ConstantsExamples.VM_RAM[vmType]),
                    new ContainerBwProvisionerSimple(ConstantsExamples.VM_BW),
                    peList, ConstantsExamples.SCHEDULING_INTERVAL));


        }

        return containerVms;
    }

    /**
     * Create the host list considering the specs listed in the {@link ConstantsExamples}.
     *
     * @param hostsNumber
     * @return
     */


    public static List<ContainerHost> createHostList(int hostsNumber) {
        ArrayList<ContainerHost> hostList = new ArrayList<ContainerHost>();
        for (int i = 0; i < hostsNumber; ++i) {
            int hostType = i / (int) Math.ceil((double) hostsNumber / 3.0D);
            ArrayList<ContainerVmPe> peList = new ArrayList<ContainerVmPe>();
            for (int j = 0; j < ConstantsExamples.HOST_PES[hostType]; ++j) {
                peList.add(new ContainerVmPe(j,
                        new ContainerVmPeProvisionerSimple((double) ConstantsExamples.HOST_MIPS[hostType])));
            }

            hostList.add(new PowerContainerHostUtilizationHistory(IDs.pollId(ContainerHost.class),
                    new ContainerVmRamProvisionerSimple(ConstantsExamples.HOST_RAM[hostType]),
                    new ContainerVmBwProvisionerSimple(1000000L), 1000000L, peList,
                    new ContainerVmSchedulerTimeSharedOverSubscription(peList),
                    ConstantsExamples.HOST_POWER[hostType]));
        }

        return hostList;
    }


    /**
     * Create the data center
     *
     * @param name
     * @param datacenterClass
     * @param hostList
     * @param vmAllocationPolicy
     * @param containerAllocationPolicy
     * @param experimentName
     * @param logAddress
     * @return
     * @throws Exception
     */

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

    public static List<Container> createContainerList(int brokerId, int containersNumber) {
        ArrayList<Container> containers = new ArrayList<Container>();

        for (int i = 0; i < containersNumber; ++i) {
            int containerType = i / (int) Math.ceil((double) containersNumber / 3.0D);

            containers.add(new PowerContainer(IDs.pollId(Container.class), brokerId, (double) ConstantsExamples.CONTAINER_MIPS[containerType], ConstantsExamples.
                    CONTAINER_PES[containerType], ConstantsExamples.CONTAINER_RAM[containerType], ConstantsExamples.CONTAINER_BW, 0L, "Xen",
                    new ContainerCloudletSchedulerDynamicWorkload(ConstantsExamples.CONTAINER_MIPS[containerType], ConstantsExamples.CONTAINER_PES[containerType]), ConstantsExamples.SCHEDULING_INTERVAL));
        }

        return containers;
    }

    /**
     * Creating the cloudlet list that are going to run on containers
     *
     * @param brokerId
     * @param numberOfCloudlets
     * @return
     * @throws FileNotFoundException
     */
    public static List<ContainerCloudlet> createContainerCloudletList(int brokerId, int numberOfCloudlets)
            throws FileNotFoundException {
        String inputFolderName = ContainerRedisCloudSimExample1.class.getClassLoader().getResource("workload/planetlab").getPath();


       // System.out.println(inputFolderName);

    //    java.io.File inputFolder1 = new java.io.File("D:/EclipseWorkSpace/cloudsim-5.0/cloudsim-5.0/modules/cloudsim-examples/src/main/resources/workload/planetlab/");
        java.io.File inputFolder1 = new java.io.File(inputFolderName+ "/");

        ArrayList<ContainerCloudlet> cloudletList = new ArrayList<ContainerCloudlet>();
        long fileSize = 300L;
        long outputSize = 300L;
        UtilizationModelNull utilizationModelNull = new UtilizationModelNull();
        java.io.File[] files1 = inputFolder1.listFiles();
        int createdCloudlets = 0;
        for (java.io.File aFiles1 : files1) {
            java.io.File inputFolder = new java.io.File(aFiles1.toString());
            java.io.File[] files = inputFolder.listFiles();
            for (int i = 0; i < files.length; ++i) {
                if (createdCloudlets < numberOfCloudlets) {
                    ContainerCloudlet cloudlet = null;

                    try {
                        cloudlet = new ContainerCloudlet(IDs.pollId(ContainerCloudlet.class), ConstantsExamples.CLOUDLET_LENGTH, 1,
                                fileSize, outputSize,
                                new UtilizationModelPlanetLabInMemoryExtended(files[i].getAbsolutePath(), 300.0D),
                                utilizationModelNull, utilizationModelNull);
                    } catch (Exception var13) {
                        var13.printStackTrace();
                        System.exit(0);
                    }

                    cloudlet.setUserId(brokerId);
                    cloudletList.add(cloudlet);
                    createdCloudlets += 1;
                } else {

                    return cloudletList;
                }
            }

        }
        return cloudletList;
    }

}
