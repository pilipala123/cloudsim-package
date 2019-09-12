package org.cloudbus.cloudsim.examples;

/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * A simple example showing how to create a data center with one host and run one cloudlet on it.
 */
public class CloudSimExample1 {
    /**
     * The cloudlet list.
     */
    private static List<Cloudlet> cloudletList;
    /**
     * The vmlist.
     */
    private static List<Vm> vmlist;

    /**
     * Creates main() to run this example.
     *
     * @param args the args
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Log.printLine("Starting CloudSimExample1...");

        try {
            // First step: Initialize the CloudSim package. It should be called before creating any entities.
			// 第一步：初始化工具包
            // 云用户数量
            int numUser = 1;
            // 用当前日期和时间初始化字段的日历
            Calendar calendar = Calendar.getInstance();
            // 时间追踪
            boolean trace_flag = false;

			/* Comment Start - Dinesh Bhagwat
             * Initialize the CloudSim library.
             * init() invokes initCommonVariable() which in turn calls initialize() (all these 3 methods are defined in CloudSim.java).
             * initialize() creates two collections - an ArrayList of SimEntity Objects (named entities which denote the simulation entities) and
             * a LinkedHashMap (named entitiesByName which denote the LinkedHashMap of the same simulation entities), with name of every SimEntity as the key.
             * initialize() creates two queues - a Queue of SimEvents (future) and another Queue of SimEvents (deferred).
             * initialize() creates a HashMap of of Predicates (with integers as keys) - these predicates are used to select a particular event from the deferred queue.
             * initialize() sets the simulation clock to 0 and running (a boolean flag) to false.
             * Once initialize() returns (note that we are in method initCommonVariable() now), a CloudSimShutDown (which is derived from SimEntity) instance is created
             * (with numuser as 1, its name as CloudSimShutDown, id as -1, and state as RUNNABLE). Then this new entity is added to the simulation
             * While being added to the simulation, its id changes to 0 (from the earlier -1). The two collections - entities and entitiesByName are updated with this SimEntity.
             * the shutdownId (whose default value was -1) is 0
             * Once initCommonVariable() returns (note that we are in method init() now), a CloudInformationService (which is also derived from SimEntity) instance is created
             * (with its name as CloudInformatinService, id as -1, and state as RUNNABLE). Then this new entity is also added to the simulation.
             * While being added to the simulation, the id of the SimEntitiy is changed to 1 (which is the next id) from its earlier value of -1.
             * The two collections - entities and entitiesByName are updated with this SimEntity.
             * the cisId(whose default value is -1) is 1
             * Comment End - Dinesh Bhagwat
             *************************************************************************************************
             *  Comment Start
			 * Initialize the CloudSim library.
			 * init()调用 initCommonVariable(), initCommonVariable()反过来调用initialize()
					   (all these 3 methods are defined in CloudSim.java).
			 * initialize() 创建了两个集合 - an 数组列表 of SimEntity 数组对象 (表示模拟实体的named entities) and
			 * a LinkedHashMap 链表哈希映射 (named entitiesByName 表示相同模拟实体的LinkedHashMap),
					   以每个SimEntity的名称作为键.
			 * initialize() 创建两个队列 - a Queue of SimEvents (future) and another Queue of SimEvents (deferred).
			 * initialize() creates a 哈希表 of 预测 (以整数为键) - these predicates are used
					   to select a particular event from the deferred queue.
			 * initialize() sets the simulation clock to 0 and running (a boolean flag) to false.
			 * 一旦initialize()返回(请注意，我们现在处于方法initCommonVariable()中),
					   就会创建一个CloudSimShutDown(从SimEntity派生而来)实例
			 * (with numuser as 1, its name as CloudSimShutDown, id as -1, and state as RUNNABLE).
					   然后将这个新实体添加到模拟中
			 * 当添加到模拟中时，它的id变为0(从前面的-1). 两个集合 - entities 和 entitiesByName 使用此SimEntity更新.
			 * the shutdownId (whose default value was -1) is 0
			 * 一旦initCommonVariable()返回(请注意，我们现在是在方法init()中), 就会创建一个
					   CloudInformationService(它也派生自SimEntity)实例
			 * (with its name as CloudInformatinService, id as -1, and state as RUNNABLE).
					   然后这个新实体也被添加到模拟中.
			 * While being added to the simulation, the id of the SimEntitiy is changed to 1
					   (which is the next id) from its earlier value of -1.
			 * 两个集合 - entities 和 entitiesByName are updated with this SimEntity.
			 * the cisId(whose default value is -1) is 1
			 * Comment End - Dinesh Bhagwat
             */


            CloudSim.init(numUser, calendar, trace_flag);

            // Second step: Create Datacenters
            // Datacenters are the resource providers in CloudSim. We need at
            // list one of them to run a CloudSim simulation
			// 第二步 创建数据中心
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // Third step: Create Broker
			// 第三步 创建代理
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            // Fourth step: Create one virtual machine
			// 第四步 创建虚拟机
            vmlist = new ArrayList<Vm>();

            // 虚拟机参数
            // VM description
            int vmid = 0;
            int mips = 1000;
            // image size (MB)
            long size = 10000;
            // vm memory (MB)
            int ram = 512;
            long bw = 1000;
            // number of cpus
            int pesNumber = 1;
            // VMM name
            String vmm = "Xen";

            // create VM
			// 用刚才定义的参数创建VM
            Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

            // add the VM to the vmList
			// 键入虚拟机列表
            vmlist.add(vm);

            // submit vm list to the broker
			// 将虚拟机列表提交给代理
            broker.submitVmList(vmlist);

            // Fifth step: Create one Cloudlet
			// 第五步 创建一个云任务
            cloudletList = new ArrayList<Cloudlet>();

            // Cloudlet properties
			// 云任务参数
            int id = 0;
            long length = 400000;
            long fileSize = 300;
            long outputSize = 300;
            UtilizationModel utilizationModel = new UtilizationModelFull();

            // 用刚才定义的参数创建云任务
            Cloudlet cloudlet =
                    new Cloudlet(id, length, pesNumber, fileSize,
                            outputSize, utilizationModel, utilizationModel,
                            utilizationModel);
            cloudlet.setUserId(brokerId);
            cloudlet.setVmId(vmid);

            // add the cloudlet to the list
			// 将云任务加入列表
            cloudletList.add(cloudlet);

            // submit cloudlet list to the broker
            // 云任务列表提交给代理
			broker.submitCloudletList(cloudletList);

            // Sixth step: Starts the simulation
			// 第六步 开始仿真
            CloudSim.startSimulation();

            CloudSim.stopSimulation();

            //Final step: Print results when simulation is over
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

            Log.printLine("CloudSimExample1 finished!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }
    }

    /**
     * Creates the datacenter.
     *
     * @param name the name
     * @return the datacenter
     */
    private static Datacenter 	createDatacenter(String name) {

        // Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store our machine
        // 1.创建列表储存机器
        List<Host> hostList = new ArrayList<Host>();

        // 2. A Machine contains one or more PEs or CPUs/Cores.
        // In this example, it will have only one core.
		// 2.创建处理器列表
        List<Pe> peList = new ArrayList<Pe>();

        int mips = 1000;

        // 3. Create PEs and add these into a list.
		// 3. 创建处理器，并添加到Pe列表。需要保存处理器的id和MIPS
		// need to store Pe id and MIPS Rating
        peList.add(new Pe(0, new PeProvisionerSimple(mips)));

        // 4. Create Host with its id and list of PEs and add them to the list of machines
		// 4.通过id创建主机，并添加到机器列表
        int hostId = 0;
		// host memory (MB)
        int ram = 2048;
		// host storage
        long storage = 1000000;
        // bandwidth
        int bw = 10000;

        hostList.add(
                new Host(
                        hostId,
                        new RamProvisionerSimple(ram),
                        new BwProvisionerSimple(bw),
                        storage,
                        peList,
                        new VmSchedulerTimeShared(peList)
                )
        ); // This is our machine

        // 5. Create a DatacenterCharacteristics object that stores the properties of a data center: architecture, OS, list of
        // Machines, allocation policy: time- or space-shared, time zone and its price (G$/Pe time unit).
		// 5.创建一个数据中心的特征对象
		// 该对象储存数据中心的特性：体系结构、操作系统、机器列表
		// 分配策略：
		// 时间或空间共享、时区及其价格(G$/Pe时间单位)

		// 系统架构
        String arch = "x86";
		// 操作系统
        String os = "Linux";
        String vmm = "Xen";
		// time zone this resource located
        double time_zone = 10.0;
		// the cost of using processing in this resource
        double cost = 3.0;
		// the cost of using memory in this resource
        double costPerMem = 0.05;
		// the cost of using storage in this
        double costPerStorage = 0.001;
        // resource
		// the cost of using bw in this resource
        double costPerBw = 0.0;
		// we are not adding SAN
        LinkedList<Storage> storageList = new LinkedList<Storage>();

        // 特征对象属性
        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);

        // 6. Finally, we need to create a PowerDatacenter object.
		// 6.最后创建数据中心对象
        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

    // We strongly encourage users to develop their own broker policies, to
    // submit vms and cloudlets according
    // to the specific rules of the simulated scenario


    /**
     * Creates the broker.
     * 创建代理，可以根据特定需求发展自己的代理协议来提交虚拟机和云任务
     * @return the datacenter broker
     */
    private static DatacenterBroker createBroker() {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return broker;
    }

    /**
     * Prints the Cloudlet objects.
     * 打印云任务参数
     * @param list list of Cloudlets
     */
    private static void printCloudletList(List<Cloudlet> list) {
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

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
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
}
