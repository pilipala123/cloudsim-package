package org.cloudbus.cloudsim.container.load.load;

//import org.yunji.cloudsimrd.load.Load;

import com.alibaba.fastjson.JSON;
import org.cloudbus.cloudsim.container.load.Constants;
import org.cloudbus.cloudsim.container.load.Load;

import java.io.*;
import java.util.*;

/**
 * @author weirenjie
 * @date 2019/11/14
 */
public class PropertiesUtil {
    /**
     * 将Map中的属性写入properties
     *
     * @param fileName
     * @param propertiesMap
     */
    public void writePropertiesFromMap(String fileName, Map propertiesMap) {
        Properties properties = new Properties();
        // properties.putAll(propertiesMap);
        OutputStream output = null;
        String filePath = new StringBuilder(Constants.PROJECT_PATH).append(fileName).toString();
        try {
            Iterator<Map.Entry<Double, List<Load>>> entries = propertiesMap.entrySet().iterator();

            while (entries.hasNext()) {
                Map.Entry<Double, List<Load>> entry = entries.next();
                String key = JSON.toJSONString(entry.getKey());
                String value = JSON.toJSONString(entry.getValue());
                properties.setProperty(key, value);
                System.out.println("已写入属性：" + key + "=" + value);
            }
            properties.store(new FileOutputStream(filePath), new Date().toString());
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将properties文件中的属性读入Map中方便处理
     *
     * @return
     */
    public Map<String, String> loadProperties(String propertiesPath) {
        Map<String, String> resultMap = new HashMap<>();

        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(propertiesPath);
            properties.load(input);
            for (String key : properties.stringPropertyNames()) {
                resultMap.put(key, properties.get(key).toString());
            }
        } catch (IOException io) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }

}
