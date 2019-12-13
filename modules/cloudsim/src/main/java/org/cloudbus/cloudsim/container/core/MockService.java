package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the Mock Services provided by Siemens. The response time/processing
 * time is not influenced by other services. Normally, the response time is a constant value.
 * While in different services, the response time can be varied.
 * @author Minxian
 *
 */
public class MockService {
    int responseTime; //It can be defined as a constant value
    String name;
    String serviceId;

    /**
     * The construct method will be called by Siemens NFR instances.
     * @param name
     * @param serviceId
     * @param responseTime
     */
    public MockService(String name, String serviceId, int responseTime,SiemensList siemensList){
        this.name = name;
        this.serviceId = serviceId;
        this.responseTime = responseTime;
        String label = "Mockservice";
        int time = 200;
        double qpsbase = 6.5;
        List<Double> qpslist = new ArrayList<>();
        for(int i=0;i<siemensList.getLoadnumber().size();i++){
            qpslist.add(qpsbase*(double)siemensList.getLoadnumber().get(i));
        }
        Plotpictures.plotpicture(time,qpslist,label+"qps随时间的关系","qps");
    }

    int getResponeTime(){
        return responseTime;
    }

    void setResponseTime(int responseTime){
        this.responseTime = responseTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }


}

