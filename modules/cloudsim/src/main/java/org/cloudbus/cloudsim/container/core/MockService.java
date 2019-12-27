package org.cloudbus.cloudsim.container.core;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;

import java.util.ArrayList;

import static java.lang.Double.NaN;

/**
 * This class defines the Mock Services provided by Siemens. The response time/processing
 * time is not influenced by other services. Normally, the response time is a constant value.
 * While in different services, the response time can be varied.
 * @author Minxian
 *
 */
public class MockService extends Host {
    int responseTime; //It can be defined as a constant value
    String name;
    int serviceId;

    public int getResponseTime() {
        return responseTime;
    }

    /**
     * The construct method will be called by Siemens NFR instances.
     * @param id
     */
    public MockService(int id,String name,int responseTime){
        super(id);
        this.serviceId = id;
        setName(name);
        setResponseTime(responseTime);
        setSiemensList(new SiemensList());
        getSiemensList().setAverageresponsetimelist(new ArrayList<>());
        getSiemensList().setQpslist(new ArrayList<>());
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

    public void processEvent(int loadpernumber,int time){
        super.processEvent(loadpernumber,time);
        double qpsbase = 8.8;
        getSiemensList().setName(this.name);
        getSiemensList().getQpslist().add(qpsbase*(double)loadpernumber);
        getSiemensList().getAverageresponsetimelist().add((double)this.responseTime);
        System.out.println("time: "+time+ "s "+ getSiemensList().getName()+": average response time is "+ this.responseTime+"ms");

    }


}

