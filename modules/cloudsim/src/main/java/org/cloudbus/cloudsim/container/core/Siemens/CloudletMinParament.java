package org.cloudbus.cloudsim.container.core.Siemens;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.container.core.ContainerCloudlet;

import java.util.List;

public class CloudletMinParament {
    private int mincpurequest;
    private int minmemoryrequest;
    private int memoryrequest;
    private int maxtimelength;
    private int minbwrequest;



    public int getMaxtimelength() {
        return maxtimelength;
    }

    public void setMaxtimelength(int maxtimelength) {
        this.maxtimelength = maxtimelength;
    }



    public void setcloudletMinParament(List<ContainerCloudlet> cloudletList,int containernumber,int loadnumber){
        int presentcloudletcpu,presentcloudletmemory,presentcloudletbw,maxcpucost=0,maxmemorycost=0,maxbwcost=0;
        int maxlength = 0;
        for (Cloudlet cloudlet: cloudletList) {
            if(maxlength<(int)cloudlet.getCloudletLength()){
                maxlength = (int)cloudlet.getCloudletLength();
            }

            this.maxtimelength = (int)cloudlet.getCloudletLength()+this.maxtimelength;
            presentcloudletcpu = cloudlet.getCpurequest();
            presentcloudletmemory = cloudlet.getMemoryrequest();
            presentcloudletbw = cloudlet.getBwrequest();


            if (cloudlet.getCloudletId() == 1) {
                this.mincpurequest = presentcloudletcpu;
                this.minmemoryrequest = presentcloudletmemory;
                this.minbwrequest = presentcloudletbw;
                maxcpucost = cloudlet.getCpurequest();
                maxmemorycost = cloudlet.getMemoryrequest();
                maxbwcost = cloudlet.getBwrequest();
            }
            if (this.mincpurequest > presentcloudletcpu) {
                this.mincpurequest = presentcloudletcpu;
            }
            if (this.minmemoryrequest >presentcloudletmemory){
                this.minmemoryrequest = presentcloudletmemory;
            }
            if(this.minbwrequest>presentcloudletbw){
                this.minbwrequest = presentcloudletbw;
            }
            if(maxcpucost<cloudlet.getCpurequest()){
                maxcpucost= cloudlet.getCpurequest();
            }
            if(maxmemorycost <cloudlet.getMemoryrequest()){
                maxmemorycost = cloudlet.getMemoryrequest();
            }
            if(maxbwcost<cloudlet.getBwrequest()){
                maxbwcost = cloudlet.getBwrequest();
            }
        }
        this.maxtimelength = maxlength;

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
        setMinbwrequest(10);

    }

    public int getMinbwrequest() {
        return minbwrequest;
    }

    public void setMinbwrequest(int minbwrequest) {
        this.minbwrequest = minbwrequest;
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
