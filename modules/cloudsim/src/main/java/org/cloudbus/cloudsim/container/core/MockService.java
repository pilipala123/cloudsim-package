package org.cloudbus.cloudsim.container.core;

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
     * The construct method will be called by SLB NFR instances.
     * @param name
     * @param serviceId
     * @param responseTime
     */
    public MockService(String name, String serviceId, int responseTime){
        this.name = name;
        this.serviceId = serviceId;
        this.responseTime = responseTime;
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

