package org.cloudbus.cloudsim.container.load;

import java.util.Map;

/**
 * @author weirenjie
 * @date 2019/11/15
 */

/**
 * 读取load.properties文件的属性
 */
public class LoadPropertiesMDSP {
    public String propertiesPath =LoadGeneratorMDSP.root+ "\\modules\\cloudsim\\src\\main\\java\\org\\cloudbus\\cloudsim\\container\\load\\load.properties";


    PropertiesUtil propertiesUtil = new PropertiesUtil();
    Map<String, String> propertiesMap = propertiesUtil.loadProperties(propertiesPath);


    public double ramp_up = Double.valueOf(propertiesMap.get("ramp_up"));
    public double ramp_down = Double.valueOf(propertiesMap.get("ramp_down"));
    public int totalNumberOfRequest = Integer.valueOf(propertiesMap.get("loadNumbers"));
    public int maxConcurrent = Integer.valueOf(propertiesMap.get("concurrent"));
    public int CPUMax = Integer.valueOf(propertiesMap.get("CPU_max"));
    public int interval = Integer.valueOf(propertiesMap.get("request_interval"));

    public int qps = Integer.valueOf(propertiesMap.get("qps"));
    public double load_duration = Double.valueOf(propertiesMap.get("load_duration"));
    public double average_response_time = Double.valueOf(propertiesMap.get("average_response_time"));
    public double median_response_time = Double.valueOf(propertiesMap.get("median_response_time"));
    public double p95_response_time = Double.valueOf(propertiesMap.get("p95_response_time"));
    public int rating = 1;

}
