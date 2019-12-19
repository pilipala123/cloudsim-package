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
    int serviceId;
    private SiemensList mockservicesiemensList;

    public SiemensList getMockservicesiemensList() {
        return mockservicesiemensList;
    }

    public void setMockservicesiemensList(SiemensList mockservicesiemensList) {
        this.mockservicesiemensList = mockservicesiemensList;
    }

    public int getResponseTime() {
        return responseTime;
    }

    /**
     * The construct method will be called by Siemens NFR instances.
     * @param serviceId
     */
    public MockService(int serviceId){
        this.serviceId = serviceId;
        String label = "Mockservice";
        int time = 200;

        setMockservicesiemensList(new SiemensList());
        getMockservicesiemensList().setAverageresponsetimelist(new ArrayList<>());
        getMockservicesiemensList().setQpslist(new ArrayList<>());
//        Plotpictures.plotpicture(time,qpslist,label+"qps随时间的关系","qps");
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

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void processEvent(int loadpernumber,double responsetime){
        String label = "Mockservice";
        double qpsbase = 6.5;
        getMockservicesiemensList().setName(label);
        getMockservicesiemensList().getQpslist().add(qpsbase*(double)loadpernumber);
        getMockservicesiemensList().getAverageresponsetimelist().add(responsetime);

    }


}

