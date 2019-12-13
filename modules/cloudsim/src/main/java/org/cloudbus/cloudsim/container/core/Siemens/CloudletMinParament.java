package org.cloudbus.cloudsim.container.core.Siemens;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;

import java.util.List;

public class CloudletMinParament {
    private int mincpurequest;
    private int minmemoryrequest;
    private int memoryrequest;
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
            presentcloudletbw = cloudlet.getMemoryrequest();

            if (cloudlet.getCloudletId() == 1) {
                this.mincpurequest = presentcloudletcpu;
                this.minmemoryrequest = presentcloudletbw;
                maxcpucost = cloudlet.getCpurequest();
                maxbwcost = cloudlet.getMemoryrequest();
            }
            if (this.mincpurequest > presentcloudletcpu) {
                this.mincpurequest = presentcloudletcpu;
            }
            if (this.minmemoryrequest >presentcloudletbw){
                this.minmemoryrequest = presentcloudletbw;
            }
            if(maxcpucost<cloudlet.getCpurequest()){
                maxcpucost= cloudlet.getCpurequest();
            }
            if(maxbwcost <cloudlet.getMemoryrequest()){
                maxbwcost = cloudlet.getMemoryrequest();
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
        setMinmemoryrequest(bw);
        setMemoryrequest(memory);
        setMincpurequest(pe);
    }
    public CloudletMinParament(){
        setMinmemoryrequest(10);
        setMemoryrequest(0);
        setMincpurequest(10);
        setMaxtimelength(0);

    }
    public int getMincpurequest() {
        return mincpurequest;
    }

    public void setMincpurequest(int mincpurequest) {
        this.mincpurequest = mincpurequest;
    }

    public int getMinmemoryrequest() {
        return minmemoryrequest;
    }

    public void setMinmemoryrequest(int minmemoryrequest) {
        this.minmemoryrequest = minmemoryrequest;
    }

    public int getMemoryrequest() {
        return memoryrequest;
    }

    public void setMemoryrequest(int memoryrequest) {
        this.memoryrequest = memoryrequest;
    }
}
