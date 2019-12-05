package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.container.InputParament.*;
import org.yunji.cloudsimrd.load.Load;
import org.yunji.cloudsimrd.load.LoadGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
    public static void main(String[] args){
        LoadProperties l = new LoadProperties();
        l.loadProperties();

    }

    public void loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("/dev/xlx/cloudsim31/modules/cloudsim/src/main/resources/config.properties");
            properties.load(inputStream);
            EcsInput ecsInput = setEcsInput(properties);
            printEcsInput(ecsInput);
            ContainerInput containerInput = setContainerInput(properties);
            printContainerInput(containerInput);
            K8sInput k8sInput = setK8sInput(properties);
            printK8sInput(k8sInput);
            LoadGeneratorInput loadGeneratorInput = setLoadGeneratorInput(properties);
            printLoadGeneratorInput(loadGeneratorInput);
            SlbInput slbInput = setslbInput(properties);
            printSlbInput(slbInput);
            RedisInput redisInput =setRedisInput(properties);
            printRedisInput(redisInput);
            TablestoreInput tablestoreInput = settablestoreInput(properties);
            printTablestoreInput(tablestoreInput);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream !=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static EcsInput setEcsInput(Properties properties){
        EcsInput ecsInput = new EcsInput();
        ecsInput.setEcsCPUQuota(Integer.parseInt(properties.getProperty("EcsCPUQuota")));
        ecsInput.setEcsDiskIO(Integer.parseInt(properties.getProperty("EcsDiskIO")));
        ecsInput.setEcsDiskSize(Integer.parseInt(properties.getProperty("EcsDiskSize")));
        ecsInput.setEcsInboundbandwidth(Integer.parseInt(properties.getProperty("EcsInboundbandwidth")));
        ecsInput.setEcsOutboundbandwidth(Integer.parseInt(properties.getProperty("EcsOutboundbandwidth")));
        ecsInput.setEcsMemoryQuota(Integer.parseInt(properties.getProperty("EcsMemoryQuota")));
        ecsInput.setEcsMIPSpercore(Integer.parseInt(properties.getProperty("EcsMIPSpercore")));
        ecsInput.setEcsServiceType(Integer.parseInt(properties.getProperty("EcsServiceType")));
        return ecsInput;
    }

    public void printEcsInput(EcsInput ecsInput){
        System.out.println("EcsCPUQuota:"+ecsInput.getEcsCPUQuota());
        System.out.println("EcsMemoryQuota:"+ecsInput.getEcsMemoryQuota());
        System.out.println("EcsDiskIO:"+ecsInput.getEcsDiskIO());
        System.out.println("EcsDiskSize:"+ecsInput.getEcsDiskSize());
        System.out.println("EcsInboundbandwidth:"+ecsInput.getEcsInboundbandwidth());
        System.out.println("EcsOutboundbandwidth:"+ecsInput.getEcsOutboundbandwidth());
        System.out.println("EcsMIPSpercore:"+ecsInput.getEcsMIPSpercore());
        System.out.println("EcsServiceType:"+ecsInput.getEcsServiceType());

    }

    public static ContainerInput setContainerInput(Properties properties){
        ContainerInput containerInput = new ContainerInput();
        containerInput.setContainerCPUQuota(Integer.parseInt(properties.getProperty("ContainerCPUQuota")));
        containerInput.setContainerDiskIO(Integer.parseInt(properties.getProperty("ContainerDiskIO")));
        containerInput.setContainerDiskSize(Integer.parseInt(properties.getProperty("ContainerDiskSize")));
        containerInput.setContainerInboundbandwidth(Integer.parseInt(properties.getProperty("ContainerInboundbandwidth")));
        containerInput.setContainerOutboundbandwidth(Integer.parseInt(properties.getProperty("ContainerOutboundbandwidth")));
        containerInput.setContainerMaxConnections(Integer.parseInt(properties.getProperty("ContainerMaxConnections")));
        containerInput.setContainerMemoryQuota(Integer.parseInt(properties.getProperty("ContainerMemoryQuota")));
        containerInput.setContainerMIPSpercore(Integer.parseInt(properties.getProperty("ContainerMIPSpercore")));
        return containerInput;
    }

    public void printContainerInput(ContainerInput containerInput){
        System.out.println("ContainerCPUQuota:"+containerInput.getContainerCPUQuota());
        System.out.println("ContainerMemoryQuota:"+containerInput.getContainerMemoryQuota());
        System.out.println("ContainerMaxConnections:"+containerInput.getContainerMaxConnections());
        System.out.println("ContainerMIPSpercore:"+containerInput.getContainerMIPSpercore());
        System.out.println("ContainerInboundbandwidth:"+containerInput.getContainerInboundbandwidth());
        System.out.println("ContainerOutboundbandwidth:"+containerInput.getContainerOutboundbandwidth());
        System.out.println("ContainerDiskIO:"+containerInput.getContainerDiskIO());
        System.out.println("ContainerDiskSize:"+containerInput.getContainerDiskSize());
    }

    public static K8sInput setK8sInput(Properties properties){
        K8sInput k8sInput = new K8sInput();
        k8sInput.setECSNumbers(Integer.parseInt(properties.getProperty("ECSNumbers")));
        k8sInput.setK8smoney(Integer.parseInt(properties.getProperty("k8smoney")));
        k8sInput.setPodNumbersineachECS(Integer.parseInt(properties.getProperty("PodNumbersineachECS")));
        return k8sInput;
    }

    public void printK8sInput(K8sInput k8sInput){
        System.out.println("ECSNumbers:"+k8sInput.getECSNumbers());
        System.out.println("K8smoney:"+k8sInput.getK8smoney());
        System.out.println("PodNumbersineachECS:"+k8sInput.getPodNumbersineachECS());
    }

    public static LoadGeneratorInput setLoadGeneratorInput(Properties properties){
        LoadGeneratorInput loadGeneratorInput = new LoadGeneratorInput();
        loadGeneratorInput.setLoadnumbers(Integer.parseInt(properties.getProperty("Loadnumbers")));
        loadGeneratorInput.setLoadduration(Integer.parseInt(properties.getProperty("Loadduration")));
        loadGeneratorInput.setConcurrentrequest(Integer.parseInt(properties.getProperty("Concurrentrequest")));
        loadGeneratorInput.setRequestinterval(Integer.parseInt(properties.getProperty("Requestinterval")));
        loadGeneratorInput.setRamp_up(Integer.parseInt(properties.getProperty("Ramp_up")));
        loadGeneratorInput.setRamp_down(Integer.parseInt(properties.getProperty("Ramp_down")));
        return loadGeneratorInput;
    }

    public void printLoadGeneratorInput(LoadGeneratorInput loadGeneratorInput){
        System.out.println("Loadnumbers:"+loadGeneratorInput.getLoadnumbers());
        System.out.println("Loadduration:"+loadGeneratorInput.getLoadduration());
        System.out.println("Concurrentrequest:"+loadGeneratorInput.getConcurrentrequest());
        System.out.println("Requestinterval:"+loadGeneratorInput.getRequestinterval());
        System.out.println("Ramp_up:"+loadGeneratorInput.getRamp_up());
        System.out.println("Ramp_down:"+loadGeneratorInput.getRamp_down());
    }

    public static SlbInput setslbInput(Properties properties){
        SlbInput slbInput = new SlbInput();
        slbInput.setSlbCPUQuota(Integer.parseInt(properties.getProperty("SlbCPUQuota")));
        slbInput.setSlbmoney(Integer.parseInt(properties.getProperty("Slbmoney")));
        slbInput.setSlbLatency(Integer.parseInt(properties.getProperty("SlbLatency")));
        slbInput.setSlbLoadbalancertype(Integer.parseInt(properties.getProperty("Loadnumbers")));
        slbInput.setSlbLoaddistributionstrategy(Integer.parseInt(properties.getProperty("Loadnumbers")));
        slbInput.setSlbMaxconnections(Integer.parseInt(properties.getProperty("Loadnumbers")));
        slbInput.setSlbMaxinboundbandwidth(Integer.parseInt(properties.getProperty("Loadnumbers")));
        slbInput.setSlbMaxoutboundbandwidth(Integer.parseInt(properties.getProperty("Loadnumbers")));
        slbInput.setSlbMaxQPS(Integer.parseInt(properties.getProperty("Loadnumbers")));
        slbInput.setSlbMemoryQuota(Integer.parseInt(properties.getProperty("Loadnumbers")));
        return slbInput;
    }

    public void printSlbInput(SlbInput slbInput){
        System.out.println("SlbMaxconnections:"+slbInput.getSlbMaxconnections());
        System.out.println("SlbMaxQPS:"+slbInput.getSlbMaxQPS());
        System.out.println("Slbmoney:"+slbInput.getSlbmoney());
        System.out.println("SlbMaxinboundbandwidth:"+slbInput.getSlbMaxinboundbandwidth());
        System.out.println("SlbMaxoutboundbandwidth:"+slbInput.getSlbMaxoutboundbandwidth());
        System.out.println("SlbLatency:"+slbInput.getSlbLatency());
        System.out.println("SlbLoaddistributionstrategy:"+slbInput.getSlbLoaddistributionstrategy());
        System.out.println("SlbLoadbalancertype:"+slbInput.getSlbLoadbalancertype());
        System.out.println("SlbCPUQuota:"+slbInput.getSlbCPUQuota());
        System.out.println("SlbMemoryQuota:"+slbInput.getSlbMemoryQuota());





    }

    public static RedisInput setRedisInput(Properties properties){
        RedisInput redisInput = new RedisInput();
        redisInput.setRedisMemoryQuota(Integer.parseInt(properties.getProperty("RedisMemoryQuota")));
        redisInput.setRedisNodes(Integer.parseInt(properties.getProperty("RedisNodes")));
        redisInput.setRedisConnectionPerNumber(Integer.parseInt(properties.getProperty("RedisConnectionPerNumber")));
        redisInput.setRedisMaxConnections(Integer.parseInt(properties.getProperty("RedisMaxConnections")));
        redisInput.setRedismaxBw(Integer.parseInt(properties.getProperty("RedismaxBw")));
        redisInput.setRedisCPUQuota(Integer.parseInt(properties.getProperty("RedisCPUQuota")));
        redisInput.setRedisServiceType(Integer.parseInt(properties.getProperty("RedisServiceType")));
        redisInput.setRedisIOnumber(Integer.parseInt(properties.getProperty("RedisIOnumber")));
        redisInput.setRedisReadNode(Integer.parseInt(properties.getProperty("RedisReadNode")));
        redisInput.setRedisMaxinboundbandwidth(Integer.parseInt(properties.getProperty("RedisMaxinboundbandwidth")));
        redisInput.setRedisMaxoutboundbandwidth(Integer.parseInt(properties.getProperty("RedisMaxoutboundbandwidth")));

        return redisInput;
    }

    public void printRedisInput(RedisInput redisInput){
        System.out.println("MemoryQuota:" + redisInput.getRedisMemoryQuota());
        System.out.println("Nodes:" + redisInput.getRedisNodes());
        System.out.println("redisConnectionPerNumber:" + redisInput.getRedisConnectionPerNumber());
        System.out.println("maxconnection:" + redisInput.getRedisMaxConnections());
        System.out.println("redismaxBw:" + redisInput.getRedismaxBw());
        System.out.println("redisCPUQuota:" + redisInput.getRedisCPUQuota());
        System.out.println("serviceType:" + redisInput.getRedisServiceType());
        System.out.println("iOnumber:" + redisInput.getRedisIOnumber());
        System.out.println("readnode:" + redisInput.getRedisReadNode());
        System.out.println("maxinboundbandwidth:" + redisInput.getRedisMaxinboundbandwidth());
        System.out.println("maxoutboundbandwidth:" + redisInput.getRedisMaxoutboundbandwidth());
    }

    public static TablestoreInput settablestoreInput(Properties properties){
        TablestoreInput tablestoreInput = new TablestoreInput();
        tablestoreInput.setTablestorepartitioncount(Integer.parseInt(properties.getProperty("tablestorepartitioncount")));
        tablestoreInput.setTablestorerunnercount(Integer.parseInt(properties.getProperty("tablestorerunnercount")));
        tablestoreInput.setTablestoreTablestoretype(Integer.parseInt(properties.getProperty("tablestoreTablestoretype")));
        tablestoreInput.setTablestorethreadcount(Integer.parseInt(properties.getProperty("tablestorethreadcount")));
        return tablestoreInput;
    }

    public void printTablestoreInput(TablestoreInput tablestoreInput){
        System.out.println("Tablestorepartitioncount:"+tablestoreInput.getTablestorepartitioncount());
        System.out.println("TablestoreTablestoretype:"+tablestoreInput.getTablestoreTablestoretype());
        System.out.println("Tablestorerunnercount:"+tablestoreInput.getTablestorerunnercount());
        System.out.println("Tablestorethreadcount:"+tablestoreInput.getTablestorethreadcount());
    }

}
