package org.cloudbus.cloudsim.container.core.Siemens;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;

import java.util.List;

public class CloudletMinParament {
    private int mincpurequest;
    private int minbwrequest;
    private int minmemoryrequest;
    private int maxtimelength;

    public int getMaxtimelength() {
        return maxtimelength;
    }

    public void setMaxtimelength(int maxtimelength) {
        this.maxtimelength = maxtimelength;
    }



    public void setcloudletMinParament(List<ContainerCloudlet> cloudletList,int containernumber){
        int presentcloudletcpu,presentcloudletbw,maxcpucost=0,maxbwcost=0;
        for (Cloudlet cloudlet: cloudletList) {
            this.maxtimelength = (int)cloudlet.getCloudletLength()+this.maxtimelength;
            presentcloudletcpu = cloudlet.getCpurequest();
            presentcloudletbw = cloudlet.getBwrequest();

            if (cloudlet.getCloudletId() == 1) {
                this.mincpurequest = presentcloudletcpu;
                this.minbwrequest = presentcloudletbw;
                maxcpucost = cloudlet.getCpurequest();
                maxbwcost = cloudlet.getBwrequest();
            }
            if (this.mincpurequest > presentcloudletcpu) {
                this.mincpurequest = presentcloudletcpu;
            }
            if (this.minbwrequest>presentcloudletbw){
                this.minbwrequest = presentcloudletbw;
            }
            if(maxcpucost<cloudlet.getCpurequest()){
                maxcpucost= cloudlet.getCpurequest();
            }
            if(maxbwcost <cloudlet.getBwrequest()){
                maxbwcost = cloudlet.getBwrequest();
            }
        }
        if(maxbwcost<=maxcpucost){
            this.maxtimelength=this.maxtimelength/containernumber/maxcpucost;
        }
        else{
            this.maxtimelength=this.maxtimelength/containernumber/maxbwcost;
        }

    }
    public CloudletMinParament(int pe,int memory, int bw){
        setMinbwrequest(bw);
        setMinmemoryrequest(memory);
        setMincpurequest(pe);
    }
    public CloudletMinParament(){
        setMinbwrequest(10);
        setMinmemoryrequest(0);
        setMincpurequest(10);
        setMaxtimelength(0);

    }
    public int getMincpurequest() {
        return mincpurequest;
    }

    public void setMincpurequest(int mincpurequest) {
        this.mincpurequest = mincpurequest;
    }

    public int getMinbwrequest() {
        return minbwrequest;
    }

    public void setMinbwrequest(int minbwrequest) {
        this.minbwrequest = minbwrequest;
    }

    public int getMinmemoryrequest() {
        return minmemoryrequest;
    }

    public void setMinmemoryrequest(int minmemoryrequest) {
        this.minmemoryrequest = minmemoryrequest;
    }
}
