package org.cloudbus.cloudsim.container.load;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;
import org.cloudbus.cloudsim.container.utils.IDs;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
//import org.yunji.cloudsimrd.Constants;
//import org.yunji.cloudsimrd.PropertiesUtil;
//import sun.plugin2.gluegen.runtime.CPU;


/**
 * @author weirenjie
 * @date 2019/10/22
 */


/**
 * 负载生成器，仿真负载。
 */
public class LoadGeneratorMDSP {

    public static final String root = "E:\\GitHub\\cloudsim-package";

    private String propertiesPath = "D:/cloudsim-5.0/cloudsim-5.0/modules/cloudsim/src/main/java/org/cloudbus/cloudsim/container/load/load.properties";

    private int brokeId = 1;
    private int vmId = 0;

    private int rating = 1;

    private String defaultUrl = "www.baidu.com";

    /**
     * 读取trace文件中的数据，生成List
     *
     * @param inputPath
     * @param size
     * @return
     */
    public List<Integer> readListFromFile(String inputPath, Integer size) {
        List<Integer> resultList = new ArrayList<>();

        try {
            BufferedReader input = new BufferedReader(new FileReader(inputPath));
            for (int i = 0; i < size - 1; i++) {
                resultList.add(Integer.valueOf(input.readLine()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    /**
     * 根据List每秒生成不同数量的任务
     *
     * @param numList 不同时间生成任务数量的list
     * @return
     */
    public List<Cloudlet> generateCloudletsFromList(List<Integer> numList) {
        System.out.println("Load Generator: generating loads......\n");
        List<Cloudlet> resultList = new ArrayList<>();
        for (Integer num : numList) {
            resultList.addAll(generateCloudlets(num));
        }
        return resultList;
    }

    public List<ContainerCloudlet> generateContainerCloudletsFromList(int number) {
        System.out.println("Load Generator: generating loads......\n");
        List<ContainerCloudlet> resultList = new ArrayList<>();
//        for (Integer num : numList) {
//            resultList.addAll(generateContainerCloudlets(num));
//        }
        resultList.addAll(generateContainerCloudlets(number));
        return resultList;
    }

    public List<ContainerCloudlet> generateContainerCloudletsFromList(List<Integer> number) {
        System.out.println("Load Generator: generating loads......\n");
        List<ContainerCloudlet> resultList = new ArrayList<>();
        for (Integer num : number) {
            resultList.addAll(generateContainerCloudlets(num));
        }
//        resultList.addAll(generateContainerCloudlets(number));
        return resultList;
    }

    /**
     * 生成一定数量的cloudlet
     *
     * @param number
     * @return
     */
    public List<Cloudlet> generateCloudlets(Integer number) {
        long length = 40;
        long fileSize = 300;
        long outputSize = 300;
        // number of cpus
        int pesNumber = 1;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        List<Cloudlet> cloudlets = new ArrayList<>();
        for (Integer i = 0; i < number; i++) {
            Cloudlet cloudlet =
                    new Cloudlet(i, length, pesNumber, fileSize,
                            outputSize, utilizationModel, utilizationModel,
                            utilizationModel);
            cloudlet.setUserId(brokeId);
            cloudlet.setVmId(vmId);
            cloudlets.add(cloudlet);
        }
        return cloudlets;
    }

    public List<ContainerCloudlet> generateContainerCloudlets(Integer number) {
            //单个任务需要处理的时间
        long fileSize = 300;
        long outputSize = 300;
        int basetime = 1;
        // number of cpus
        int pesNumber = 1;
        int cloudleteverytimenumber = 0;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        List<ContainerCloudlet> containerCloudlets = new ArrayList<>();
        int clocktime =  0;
        for (Integer i = 0; i < number; i++) {
            long length =(5+(int) (Math.random() * 10))*basetime;
            ContainerCloudlet cloudlet =
                    new ContainerCloudlet(i, length, pesNumber, fileSize,
                            outputSize, utilizationModel, utilizationModel,
                            utilizationModel);
            cloudlet.setUserId(brokeId);
            cloudlet.setVmId(vmId);
            int cpurequest = (int) (Math.random() * 10)+1;     //单个任务的cpu需求
            int bwrequest = (int)(Math.random() * 10)+1;      //单个任务的带宽需求
            int cloudlethandle = (int)(Math.random() * 10)+5;
            if (clocktime >= 150){
                cloudleteverytimenumber++;
                cloudlet.setStarttime(clocktime);
                if (cloudleteverytimenumber==150){
                    cloudleteverytimenumber=0;
                    clocktime++;
                }
            }

            if (i <= (clocktime * (clocktime + 1) / 2) && (i >= ((clocktime * (clocktime + 1) / 2) - clocktime))) {
                cloudlet.setStarttime(clocktime);
            }
            if (i == (clocktime * (clocktime + 1) / 2)) {
                clocktime++;                                          //画出一个随时间增加,任务数呈现直线的图像
            }


            cloudlet.setCpurequest(cpurequest);
            cloudlet.setBwrequest(bwrequest);
            cloudlet.setState(0);   //waiting state
            containerCloudlets.add(cloudlet);
        }
        return containerCloudlets;
    }


    /**
     * 根据默认参数生成Load
     *
     * @param brokerId
     * @param vmid
     * @return
     */
    public Load generateSingleLoad(int brokerId, int vmid) {
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
     *
     * @param concurrencyNumber 并发数
     * @return
     */
    public ConcurrencyLoads generateConcurrentLoad(Integer cloudletNumbers, Integer concurrencyNumber) {

        if (null == concurrencyNumber) {
            concurrencyNumber = 1;
        }
        ConcurrencyLoads concurrencyLoads = new ConcurrencyLoads(generateCloudlets(cloudletNumbers), concurrencyNumber);
        return concurrencyLoads;
    }

    // /**
    //  * Creating the cloudlet list that are going to run on containers
    //  *
    //  * @param brokerId
    //  * @param numberOfCloudlets
    //  * @return
    //  * @throws FileNotFoundException
    //  */
    // public static List<ContainerCloudlet> createContainerCloudletList(int brokerId, int numberOfCloudlets)
    //         throws FileNotFoundException {
    //     String inputFolderName = LoadGenerator.class.getClassLoader().getResource("workload/planetlab").getPath();
    //     ArrayList<ContainerCloudlet> cloudletList = new ArrayList<ContainerCloudlet>();
    //     long fileSize = 300L;
    //     long outputSize = 300L;
    //     UtilizationModelNull utilizationModelNull = new UtilizationModelNull();
    //     java.io.File inputFolder1 = new java.io.File(inputFolderName);
    //     java.io.File[] files1 = inputFolder1.listFiles();
    //     int createdCloudlets = 0;
    //     for (java.io.File aFiles1 : files1) {
    //         java.io.File inputFolder = new java.io.File(aFiles1.toString());
    //         java.io.File[] files = inputFolder.listFiles();
    //         for (int i = 0; i < files.length; ++i) {
    //             if (createdCloudlets < numberOfCloudlets) {
    //                 ContainerCloudlet cloudlet = null;
    //
    //                 try {
    //                     cloudlet = new ContainerCloudlet(IDs.pollId(ContainerCloudlet.class), Constants.CLOUDLET_LENGTH, 1,
    //                             fileSize, outputSize,
    //                             new UtilizationModelPlanetLabInMemoryExtended(files[i].getAbsolutePath(), 300.0D),
    //                             utilizationModelNull, utilizationModelNull);
    //                 } catch (Exception var13) {
    //                     var13.printStackTrace();
    //                     System.exit(0);
    //                 }
    //
    //                 cloudlet.setUserId(brokerId);
    //                 cloudletList.add(cloudlet);
    //                 createdCloudlets += 1;
    //             } else {
    //
    //                 return cloudletList;
    //             }
    //         }
    //
    //     }
    //     return cloudletList;
    // }

    /**
     * 通过参数生成任务列表生成任务列表
     *
     * @param brokeId
     * @param Urls
     * @param utilizationModelCpu
     * @param utilizationModelRam
     * @param utilizationModelBw
     * @return
     * @throws FileNotFoundException
     */

    public static List<Load> createLoadList(int brokeId, List<String> Urls, UtilizationModel utilizationModelCpu, UtilizationModel utilizationModelRam, UtilizationModel utilizationModelBw) throws FileNotFoundException {

        long fileSize = 300L;
        long outputSize = 300L;
        String inputFolderName = LoadGeneratorMDSP.class.getClassLoader().getResource("workload/planetlab").getPath();
        ArrayList<Load> loadList = new ArrayList<Load>();
        File inputFolder1 = new File(inputFolderName);
        File[] files1 = inputFolder1.listFiles();
        int createdCloudlets = 0;

        for (File aFiles1 : files1) {
            File inputFolder = new File(aFiles1.toString());
            File[] files = inputFolder.listFiles();
            for (int i = 0; i < files.length; ++i) {
                if (createdCloudlets < Urls.size()) {
                    ContainerCloudlet cloudlet = null;
                    try {
                        cloudlet = new ContainerCloudlet(IDs.pollId(ContainerCloudlet.class), Constants.CLOUDLET_LENGTH, 1,
                                fileSize, outputSize,
                                new UtilizationModelPlanetLabInMemoryExtended(files[i].getAbsolutePath(), 300.0D),
                                utilizationModelRam, utilizationModelBw);
                    } catch (Exception var13) {
                        var13.printStackTrace();
                        System.exit(0);
                    }

                    cloudlet.setUserId(brokeId);
                    Load load = new Load(cloudlet, Urls.get(i));
                    loadList.add(load);
                    createdCloudlets += 1;
                } else {
                    return loadList;
                }
            }
        }
        return loadList;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public double currentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 打印时间
     */
    public void printSimpleTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss a");
        // 输出已经格式化的现在时间（24小时制）
        System.out.println("Current Time：" + simpleDateFormat.format(date));
    }

    /**
     * 打印日期的时间
     *
     * @param date
     */
    public void convertToSimpleTime(double date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss a");
        // 输出已经格式化的现在时间（24小时制）
        System.out.println("Current Time：" + simpleDateFormat.format(date));
    }

    /**
     * 按照planetLab的格式保存每秒占有率和每秒任务数
     *
     * @param filePathOfNum
     * @param filePathOfRate
     * @param ramp_up
     * @param ramp_down
     * @param total_number
     * @param maxConcurrent
     */
    public void saveLoadConfig(String filePathOfNum, String filePathOfRate, double ramp_up, double ramp_down, int total_number, int maxConcurrent, int CPUMax) {
        List<Integer> numList = new ArrayList<>();
        List<Integer> rateList = new ArrayList<>();

        Integer up_acceleration = new Double(maxConcurrent / (ramp_up / 1000)).intValue();
        Integer up_number = 0;


        for (Integer speed = up_acceleration; speed <= maxConcurrent; speed += up_acceleration) {
            numList.add(speed);
            rateList.add(speed * 100 / CPUMax);
            up_number = up_number + speed;
        }

        Integer down_acceleration = new Double(maxConcurrent / ramp_down * 1000).intValue();
        Integer down_number = 0;
        for (Integer speed = maxConcurrent; speed > 0; speed -= down_acceleration) {
            down_number = down_number + speed;
        }

        for (Integer currentNum = up_number; currentNum < total_number - down_number; currentNum += maxConcurrent) {
            numList.add(maxConcurrent);
            rateList.add(maxConcurrent * 100 / CPUMax);
        }

        for (Integer speed = maxConcurrent; speed > 0; speed -= down_acceleration) {
            numList.add(speed);
            rateList.add(speed * 100 / CPUMax);
            down_number = down_number + speed;
        }

        try {
            // String currentPath = this.getClass().getResource("").getPath();
            //
            // filePathOfNum = new StringBuilder(currentPath).append(filePathOfNum).toString();
            // filePathOfRate = new StringBuilder(currentPath).append(filePathOfRate).toString();

            BufferedWriter bf1 = new BufferedWriter(new FileWriter(filePathOfNum + "," + numList.size()));
            System.out.println("Generating Number of Requests......");
            for (Integer i : numList) {
                bf1.write(i.toString());
                System.out.println(i);
                bf1.newLine();
            }
            bf1.close();
            System.out.println("Trace: number of requests is stored......\n");

            BufferedWriter bf2 = new BufferedWriter(new FileWriter(filePathOfRate + "," + rateList.size()));
            for (Integer i : rateList) {
                bf2.write(i.toString());
                bf2.newLine();
            }
            bf2.close();
            System.out.println("Trace: CPU utilization is stored......\n");
        } catch (Exception e) {
            System.out.println("Written Exception!");
        }

    }

    public void saveLoadConfig(String filePathOfNum, String filePathOfRate, Map<Double, Integer> map, int CPUMax) {

        try {
            BufferedWriter bf1 = new BufferedWriter(new FileWriter(filePathOfNum + "," + map.size()));
            System.out.println("Generating Number of Requests......");

            for (Integer i : map.values()) {
                bf1.write(i.toString());
                System.out.println(i);
                bf1.newLine();
            }
            // for (Integer i = 0; i < map.size(); i++) {
            //     bf1.write(map.get(i).toString());
            //     bf1.newLine();
            // }
                bf1.close();
            System.out.println("Trace: number of requests is stored......\n");

            BufferedWriter bf2 = new BufferedWriter(new FileWriter(filePathOfRate + "," + map.size()));
            for (Integer i : map.values()) {
                Integer rate = new Double(i * 100 / CPUMax).intValue();
                bf2.write(rate.toString());
                bf2.newLine();
            }
            bf2.close();
            System.out.println("Trace: CPU utilization is stored......\n");
        } catch (Exception e) {
            System.out.println("Written Exception!");
        }
    }

    /**
     * 生成带有时间的普通任务
     *
     * @param brokeId
     * @param vmId
     * @param ramp_up
     * @param ramp_down
     * @param total_number
     * @param maxConcurrent
     * @param interval
     * @return
     */
    public Map<Double, List<Load>> createLoadsWithTime(int brokeId, int vmId, double ramp_up, double ramp_down, int total_number, int maxConcurrent, int interval) {
        Map<Double, List<Load>> resultMap = new HashMap<>();
        int numOfCloudlet = 0;

        Integer up_acceleration = new Double(maxConcurrent / (ramp_up / 1000)).intValue();
        Integer up_number = 0;
        for (Integer speed = up_acceleration; speed <= maxConcurrent; speed += up_acceleration) {
            up_number = up_number + speed;
        }

        Integer down_acceleration = new Double(maxConcurrent / ramp_down * 1000).intValue();
        Integer down_number = 0;
        for (Integer speed = maxConcurrent; speed > 0; speed -= down_acceleration) {
            down_number = down_number + speed;
        }

        Integer stable_number = total_number - up_number - down_number;
        Double stable_time = new Double(stable_number / (maxConcurrent / Constants.INTERVAL));

        System.out.println("总时长：" + (ramp_up + stable_time + ramp_down) / 1000 + "s");
        System.out.println("\n启动|持续时间：" + ramp_up / 1000 + "s");
        System.out.println("启动|加速度：" + up_acceleration + "load/s^2");
        System.out.println("启动|需要生成的任务数量：" + up_number);

        System.out.println("\n稳定|持续时间：" + stable_time / 1000 + "s");
        System.out.println("稳定|需要生成的任务总量：" + stable_number);

        System.out.println("\n停止|持续时间：" + ramp_down / 1000 + "s");
        System.out.println("停止|加速度：" + down_acceleration + "load/s^2");
        System.out.println("停止|需要生成的任务数量：" + down_number + "\n");

        Integer currentSpeed;
        for (currentSpeed = up_acceleration; currentSpeed <= maxConcurrent; currentSpeed += up_acceleration) {

            Double changeTime = currentTime();

            List<Load> loads = new ArrayList<>();
            for (int i = 0; i < currentSpeed; i++) {
                loads.add(generateSingleLoad(brokeId, vmId));
            }
            resultMap.put(currentTime(), loads);
            numOfCloudlet = numOfCloudlet + currentSpeed;

            System.out.println("启动阶段，当前生成速度" + currentSpeed + "  当前已生成任务数量" + numOfCloudlet);

            changeTime = changeTime + interval;
            while (currentTime() < changeTime) {
            }
        }

        if (stable_time > 0) {
            do {
                Double changeTime = currentTime();

                List<Load> loads = new ArrayList<>();
                for (int i = 0; i < maxConcurrent; i++) {
                    loads.add(generateSingleLoad(brokeId, vmId));
                }
                resultMap.put(currentTime(), loads);
                numOfCloudlet = numOfCloudlet + maxConcurrent;

                System.out.println("稳定阶段，当前生成速度" + maxConcurrent + "  当前已生成任务数量" + numOfCloudlet);

                changeTime = changeTime + interval;
                while (currentTime() < changeTime) {
                }
            } while (numOfCloudlet < up_number + stable_number);
        }

        for (currentSpeed = maxConcurrent; currentSpeed > 0; currentSpeed -= down_acceleration) {
            Double changeTime = currentTime();

            List<Load> loads = new ArrayList<>();
            for (int i = 0; i < currentSpeed; i++) {
                loads.add(generateSingleLoad(brokeId, vmId));
            }
            resultMap.put(currentTime(), loads);
            numOfCloudlet = numOfCloudlet + currentSpeed;

            System.out.println("终止阶段，当前生成任务速度" + currentSpeed + "  当前已生成任务数量" + numOfCloudlet);

            changeTime = changeTime + interval;
            while (currentTime() < changeTime) {
            }
        }
        return resultMap;
    }

    /**
     * 生成带有时间的并发任务
     *
     * @param brokeId
     * @param vmId
     * @param ramp_up
     * @param ramp_down
     * @param total_number
     * @param maxConcurrent
     * @param interval
     * @return
     */
    public Map<Double, ConcurrencyLoads> createConcurrentLoadsWithTime(int brokeId, int vmId, double ramp_up, double ramp_down, int total_number, int maxConcurrent, int interval) {
        Map<Double, ConcurrencyLoads> resultMap = new HashMap<>();
        int numOfCloudlet = 0;

        Integer up_acceleration = new Double(maxConcurrent / (ramp_up / 1000)).intValue();
        Integer up_number = 0;
        for (Integer speed = up_acceleration; speed <= maxConcurrent; speed += up_acceleration) {
            up_number = up_number + speed;
        }

        Integer down_acceleration = new Double(maxConcurrent / ramp_down * 1000).intValue();
        Integer down_number = 0;
        for (Integer speed = maxConcurrent; speed > 0; speed -= down_acceleration) {
            down_number = down_number + speed;
        }

        Integer stable_number = total_number - up_number - down_number;
        Double stable_time = new Double(stable_number / (maxConcurrent / Constants.INTERVAL));

        System.out.println("总时长：" + (ramp_up + stable_time + ramp_down) / 1000 + "s");
        System.out.println("\n启动|持续时间：" + ramp_up / 1000 + "s");
        System.out.println("启动|加速度：" + up_acceleration + "load/s^2");
        System.out.println("启动|需要生成的任务数量：" + up_number);

        System.out.println("\n稳定|持续时间：" + stable_time / 1000 + "s");
        System.out.println("稳定|需要生成的任务总量：" + stable_number);

        System.out.println("\n停止|持续时间：" + ramp_down / 1000 + "s");
        System.out.println("停止|加速度：" + down_acceleration + "load/s^2");
        System.out.println("停止|需要生成的任务数量：" + down_number + "\n");

        Integer currentSpeed;
        for (currentSpeed = up_acceleration; currentSpeed <= maxConcurrent; currentSpeed += up_acceleration) {

            Double changeTime = currentTime();


            resultMap.put(currentTime(), generateConcurrentLoad(currentSpeed, 1));
            numOfCloudlet = numOfCloudlet + currentSpeed;

            System.out.println("启动阶段，当前生成速度" + currentSpeed + "  当前已生成任务数量" + numOfCloudlet);

            changeTime = changeTime + interval;
            while (currentTime() < changeTime) {
            }
        }

        if (stable_time > 0) {
            do {
                Double changeTime = currentTime();

                resultMap.put(currentTime(), generateConcurrentLoad(maxConcurrent, 1));
                numOfCloudlet = numOfCloudlet + maxConcurrent;

                System.out.println("稳定阶段，当前生成速度" + maxConcurrent + "  当前已生成任务数量" + numOfCloudlet);

                changeTime = changeTime + interval;
                while (currentTime() < changeTime) {
                }
            } while (numOfCloudlet < up_number + stable_number);
        }

        for (currentSpeed = maxConcurrent; currentSpeed > 0; currentSpeed -= down_acceleration) {
            Double changeTime = currentTime();

            resultMap.put(currentTime(), generateConcurrentLoad(currentSpeed, 1));
            numOfCloudlet = numOfCloudlet + currentSpeed;

            System.out.println("终止阶段，当前生成任务速度" + currentSpeed + "  当前已生成任务数量" + numOfCloudlet);

            changeTime = changeTime + interval;
            while (currentTime() < changeTime) {
            }
        }
        return resultMap;
    }

    /**
     * 从时间和并发任务的Map中合并出Cloudlet的List
     *
     * @param map
     * @return
     */
    public List<Cloudlet> getCloudletsFromTimeTable(Map<Double, ConcurrencyLoads> map) {
        List<Cloudlet> resultList = new ArrayList<>();
        Iterator<Map.Entry<Double, ConcurrencyLoads>> entryIterator = map.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<Double, ConcurrencyLoads> entry = entryIterator.next();
            resultList.addAll(entry.getValue().getCloudlets());
        }
        return resultList;
    }

}
