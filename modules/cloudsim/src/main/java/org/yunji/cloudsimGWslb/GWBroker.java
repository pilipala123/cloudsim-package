package org.yunji.cloudsimGWslb;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.core.SimEntity;
import org.yunji.cloudsimrd.load.Load;

import java.util.List;

public class GWBroker extends DatacenterBroker {
    /**
     * Created a new DatacenterBroker object.
     *
     * @param name name to be associated with this entity (as required by {@link SimEntity} class)
     * @throws Exception the exception
     * @pre name != null
     * @post $none
     */
    private double responseTime;

    protected List<? extends Load> LoadList;

    protected List<Cloudlet> cloudletList;

    public GWBroker(String name, double responseTime) throws Exception {
        super(name);

        this.responseTime = responseTime;
    }
    public List<Cloudlet> readLoad(List<Load> loads){
        for (Load load : loads) {
            Cloudlet cloudlet = load.getSingleTask();

            cloudletList.add(cloudlet);


        }
        return cloudletList;
    }

    public void filterRequests(){

    }
    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }
}
