package org.cloudbus.cloudsim.container.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class WriteProperties {
    public void writeProperties() {
        Properties properties = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("/dev/xlx/cloudsim31/modules/cloudsim/src/main/resources/config.properties");
            setEcs(properties);
            setRedis(properties);
            setContainers(properties);
            setK8sdevelopmentpolicy(properties);
            setLoadGenerator(properties);
            setSlb(properties);
            setTablestore(properties);
            System.out.println("Properties write success!");
            properties.store(output, "xlx2019 modify" + new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(output!=null) {
                try {
                    output.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void setEcs(Properties properties){
        properties.setProperty("EcsCPUQuota", "1");
        properties.setProperty("EcsMemoryQuota", "512");
        properties.setProperty("EcsDiskIO", "10");
        properties.setProperty("EcsDiskSize", "10");
        properties.setProperty("EcsInboundbandwidth", "1000");
        properties.setProperty("EcsOutboundbandwidth", "1000");
        properties.setProperty("EcsMIPSpercore", "1000");
        properties.setProperty("EcsServiceType", "1");
    }
    public void setContainers(Properties properties){
        properties.setProperty("ContainerCPUQuota", "1");
        properties.setProperty("ContainerMemoryQuota", "512");
        properties.setProperty("ContainerMaxConnections", "1000");
        properties.setProperty("ContainerMIPSpercore", "1000");
        properties.setProperty("ContainerInboundbandwidth", "1000");
        properties.setProperty("ContainerOutboundbandwidth", "1000");
        properties.setProperty("ContainerDiskIO", "1000");
        properties.setProperty("ContainerDiskSize", "1000");

    }

    public void setK8sdevelopmentpolicy(Properties properties){
        properties.setProperty("ECSNumbers", "3");
        properties.setProperty("PodNumbersineachECS", "1000");
        properties.setProperty("k8smoney","19000");
    }




    public void setLoadGenerator(Properties properties){
        properties.setProperty("Loadnumbers", "1000");
        properties.setProperty("Loadduration", "10");
        properties.setProperty("Concurrentrequest", "3");
        properties.setProperty("Requestinterval", "1");
        properties.setProperty("Ramp_up", "10");
        properties.setProperty("Ramp_down", "10");

    }
    public void setSlb(Properties properties){
        properties.setProperty("SlbMaxconnections","10");
        properties.setProperty("SlbMaxQPS","10");
        properties.setProperty("SlbMaxinboundbandwidth ","1000");
        properties.setProperty("SlbMaxoutboundbandwidth ","1000");
        properties.setProperty("SlbLatency","10");
        properties.setProperty("SlbLoaddistributionstrategy","10");
        properties.setProperty("SlbCPUQuota","1");
        properties.setProperty("SlbMemoryQuota","512");
        properties.setProperty("SlbLoadbalancertype","1");
        properties.setProperty("Slbmoney","19000");
    }

    public void setRedis(Properties properties){
        properties.setProperty("RedisMemoryQuota", "12");
        properties.setProperty("RedisNodes", "8");
        properties.setProperty("RedisConnectionPerNumber", "50000");
        properties.setProperty("RedisMaxConnections", "80000");
        properties.setProperty("RedismaxBw", "768");
        properties.setProperty("RedisCPUQuota", "8");
        properties.setProperty("RedisServiceType", "8");
        properties.setProperty("RedisIOnumber", "0");
        properties.setProperty("RedisReadNode", "0");
        properties.setProperty("RedisMaxinboundbandwidth", "0");
        properties.setProperty("RedisMaxoutboundbandwidth", "0");
    }

    public void setTablestore(Properties properties){
        properties.setProperty("tablestorepartitioncount", "10");
        properties.setProperty("tablestorethreadcount", "10");
        properties.setProperty("tablestorerunnercount", "10");
        properties.setProperty("tablestoreTablestoretype", "10");

    }


    public static void main(String[] args) {
        WriteProperties t = new WriteProperties();
        t.writeProperties();
    }
}