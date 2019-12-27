package org.cloudbus.cloudsim.examples.container;


/*
 * Title:        ContainerCloudSimExample1 Toolkit
 * Description:  ContainerCloudSimExample1 (containerized cloud simulation) Toolkit for Modeling and Simulation
 *               of Containerized Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.container.InputParament.*;
import org.cloudbus.cloudsim.container.core.*;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;
import org.cloudbus.cloudsim.container.core.util.CalculateSumResponsetime;
import org.cloudbus.cloudsim.container.load.LoadGeneratorMDSP;
import org.cloudbus.cloudsim.container.resourceAllocators.ContainerAllocationPolicy;
import org.cloudbus.cloudsim.container.resourceAllocators.ContainerVmAllocationPolicy;

import java.io.*;
import java.io.File;
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
            LoadGeneratorInput loadGeneratorInput = new LoadGeneratorInput();
            K8sInput k8sInput = new K8sInput();
            PartInput partInput = new PartInput();
            List<K8sInput> k8sInputList = new ArrayList<>();
            List<PartInput> partInputList= new ArrayList<>();
            List<GW_K8S> gw_k8SList = new ArrayList<>();
            List<Host> hostList = new ArrayList<>();
            List<Integer> idlist = new ArrayList<>();
            ArrayList<String> listFileName = new ArrayList<String>();
            List<Properties> propertiesList = new ArrayList<>();
            List<InputStream> inputStreamList = new ArrayList<>();
            String path = "/dev/xlx/cloudsim_online/cloudsim31/modules/cloudsim/src/main/resources";
            File file = new File(path);
            File[] files = file.listFiles();
            String [] names = file.list();
            int loadnumber=0;
            int ramp_up = 0;
            int ramp_down = 0;
            int duration = 0;
            int gwpartnumber =0;
            if(names != null){
                String [] completNames = new String[names.length];
                for(int i=0;i<names.length;i++){
                    completNames[i]=path+"/"+names[i];
                    propertiesList.add(new Properties());
                    inputStreamList.add(new FileInputStream(completNames[i]));
                    propertiesList.get(i).load(inputStreamList.get(i));
                }
                listFileName.addAll(Arrays.asList(completNames));
            }
            for(Properties properties:propertiesList) {
                int id = Integer.parseInt(properties.getProperty("id"));

                String type = properties.getProperty("type");
                if(type==null){
                    continue;
                }
                switch (type){
                    case "input":
                        loadGeneratorInput = setLoadGeneratorInput(properties);
                        loadnumber = loadGeneratorInput.getLoadnumbers();
                        ramp_up = loadGeneratorInput.getRamp_up();
                        ramp_down = loadGeneratorInput.getRamp_down();
                        duration = loadGeneratorInput.getLoadduration();
                        break;
                    case "GW":
                        gwpartnumber++;
                        k8sInput = setk8sInput(properties);
                        k8sInput.setId(id);
                        k8sInput.setType("GW");
                        k8sInputList.add(k8sInput);
                        break;
                    case "part":
                        partInput = setpartInput(properties);
                        partInput.setId(id);
                        partInputList.add(partInput);
                    default:
                        break;

                }
                System.out.println(id);
            }
            partInputList.addAll(k8sInputList);
            Collections.sort(k8sInputList);
            Collections.sort(partInputList);
            Collections.sort(idlist);

            LoadGeneratorMDSP loadGeneratorMDSP = new LoadGeneratorMDSP();


            /**
             * S2: Use Load generator generate loads
             */
            List<ContainerCloudlet> cloudlets = loadGeneratorMDSP.generateContainerCloudletsFromList(loadGeneratorInput);

            /**
             * S3:init
             */
            int gwnumber= 0;
            ServiceLoadBalancerGW slb_gw = null;
            ServiceLoadBalancerNFR nfr=null;
            MockService mockService=null;
            for(PartInput partInput1:partInputList){
                String type = partInput1.getType();
                String name = partInput1.getName();
                int id = partInput.getId();
                idlist.add(id);
                int responsetime = partInput1.getResponsetime();
                switch (type) {
                    case "GW":
                        GW_K8S gw_k8S = new GW_K8S(partInput.getId(), cloudlets, loadnumber, ramp_down, k8sInputList.get(gwnumber));
                        gw_k8SList.add(gw_k8S);
                        hostList.add(gw_k8S);
                        gwnumber++;
                        break;
                    case "part":
                        switch (name){
                            case "slb":
                                slb_gw = new ServiceLoadBalancerGW(id,name,responsetime);
                                hostList.add(slb_gw);
                                break;
                            case "nfr":
                                nfr = new ServiceLoadBalancerNFR(id,name,responsetime);
                                hostList.add(nfr);
                                break;
                            case "mockservice":
                                mockService = new MockService(id,name,responsetime);
                                hostList.add(mockService);
                                break;
                            default:
//                                ServiceLoadBalancerGW slb = new ServiceLoadBalancerGW(id,name,responsetime);
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }

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

                int hostnumber=0;
                gwnumber=0;
                System.out.print("order:");
                for(PartInput runpartInput:partInputList){

                    String type = runpartInput.getType();
                    String name = runpartInput.getName();
                    System.out.print(name+"__");
                    switch (type) {
                        case "GW":
                            hostList.get(hostnumber).processEvent(cloudlets, k8sInputList.get(gwnumber), time);
                            gwnumber++;
                            hostnumber++;
                            break;
                        case "part":
                            switch (name){
                                case "slb":
                                    hostList.get(hostnumber).processEvent(loadnumberpertime);
                                    hostnumber++;
                                    break;
                                case "nfr":
                                    hostList.get(hostnumber).processEvent(loadnumberpertime);
                                    hostnumber++;
                                    break;
                                case "mockservice":
                                    hostList.get(hostnumber).processEvent(loadnumberpertime);
                                    hostnumber++;
                                    break;
                                default:
//                                ServiceLoadBalancerGW slb = new ServiceLoadBalancerGW(id,name,responsetime);
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                }
                System.out.println();
            }
            /**
             * S7:print the results
             */
            int hostnumber=0;
            gwnumber=0;
            for(PartInput outputpartInput:partInputList){
                String type = outputpartInput.getType();
                String name = outputpartInput.getName();
                switch (type) {
                    case "GW":
                        k8ssiemensList=hostList.get(hostnumber).getSiemensList();
                        siemensListList.add(k8ssiemensList);
                        gwnumber++;
                        hostnumber++;
                        break;
                    case "part":
                        switch (name){
                            case "slb":
                                slbsiemensList = hostList.get(hostnumber).getSiemensList();
                                siemensListList2.add(slbsiemensList);
                                hostnumber++;
                                break;
                            case "nfr":
                                nfrsiemensList = hostList.get(hostnumber).getSiemensList();
                                siemensListList2.add(nfrsiemensList);
                                hostnumber++;
                                break;
                            case "mockservice":
                                mockservicesiemenslist = hostList.get(hostnumber).getSiemensList();
                                siemensListList2.add(mockservicesiemenslist);
                                hostnumber++;
                                break;
                            default:
//                                ServiceLoadBalancerGW slb = new ServiceLoadBalancerGW(id,name,responsetime);
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }


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
//            Plotpictures.plotpicture(ramp_down,mockService.getSiemensList().getAverageresponsetimelist(),mockService.getName()+"qps随时间的关系","qps");
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
