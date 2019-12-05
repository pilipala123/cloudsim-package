package org.yunji.Redis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
    public static void main(String[] args){
        LoadProperties l = new LoadProperties();
        l.loadProperties();

//        System.out.println("input is ");
//        System.out.print("specification:"+specification);
//        System.out.print("  ");
//        System.out.print("nodes:"+nodes);
//        System.out.print("  ");
//        System.out.print("connectionpernumber:"+connectionpernumber);
//        System.out.print("  ");
//        System.out.print("maxconnection:"+maxconnection);
//        System.out.print("  ");
//        System.out.print("maxBw:"+maxBw);
//        System.out.print("  ");
//        System.out.print("cpuability:"+cpuability);
//        System.out.print("  ");
//        System.out.print("status:"+status);
//        System.out.print("  ");
//        System.out.print("IOnumber:"+IOnumber);
//        System.out.print("  ");
//        System.out.println("readnode:"+readnode);


    }
    public void loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            int specification =Integer.parseInt(properties.getProperty("specification"));
            int nodes =Integer.parseInt(properties.getProperty("nodes"));
            int connectionpernumber =Integer.parseInt(properties.getProperty("connectionpernumber"));
            int maxconnection =Integer.parseInt(properties.getProperty("maxconnection"));
            int maxBw =Integer.parseInt(properties.getProperty("maxBw"));
            int cpuability =Integer.parseInt(properties.getProperty("cpuability"));
            int status =Integer.parseInt(properties.getProperty("status"));
            int IOnumber =Integer.parseInt(properties.getProperty("IOnumber"));
            int readnode =Integer.parseInt(properties.getProperty("readnode"));
            System.out.println("specification:" + properties.getProperty("specification"));
            System.out.println("nodes:" + properties.getProperty("nodes"));
            System.out.println("connectionpernumber:" + properties.getProperty("connectionpernumber"));
            System.out.println("maxconnection:" + properties.getProperty("maxconnection"));
            System.out.println("maxBw:" + properties.getProperty("maxBw"));
            System.out.println("cpuability:" + properties.getProperty("cpuability"));
            System.out.println("status:" + properties.getProperty("status"));
            System.out.println("IOnumber:" + properties.getProperty("IOnumber"));
            System.out.println("readnode:" + properties.getProperty("readnode"));
            int result;
            result = (int) QpsCaculate.QpsCaculated(specification,nodes,connectionpernumber,maxconnection,maxBw,cpuability,status,IOnumber,readnode);
            System.out.println("outputQPS:"+result);
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

}
