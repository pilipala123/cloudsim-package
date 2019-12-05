package org.yunji.Redis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class TestProperties {
    public void writeProperties() {
        Properties properties = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("config.properties");
            properties.setProperty("specification", "16");
            properties.setProperty("nodes", "8");
            properties.setProperty("connectionpernumber", "50000");
            properties.setProperty("maxconnection", "80000");
            properties.setProperty("maxBw", "768");
            properties.setProperty("cpuability", "8");
            properties.setProperty("status", "8");
            properties.setProperty("IOnumber", "0");
            properties.setProperty("readnode", "0");
            System.out.println("1");
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
    public static void main(String[] args) {
        TestProperties t = new TestProperties();
        t.writeProperties();
    }
}