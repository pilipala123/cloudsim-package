package org.cloudbus.cloudsim.container.core.Siemens;

import org.cloudbus.cloudsim.container.core.ContainerVm;

public class SiemensContainerresource extends ContainerVm {
    private int pepool;
    private int memorypool;
    private int [][] memoryarraypool;
    private int [][] cpuarraypool;
    private int [][] bwarraypool;
    private int remaincpuresource;
    private int remainbwresource;
    private int id;
    private int SiemensVmid;
    private int state;

    public int[][] getBwarraypool() {
        return bwarraypool;
    }

    public void setBwarraypool(int[][] bwarraypool) {
        this.bwarraypool = bwarraypool;
    }

    public int getRemainbwresource() {
        return remainbwresource;
    }

    public void setRemainbwresource(int remainbwresource) {
        this.remainbwresource = remainbwresource;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSiemensVmid() {
        return SiemensVmid;
    }

    public void setSiemensVmid(int siemensVmid) {
        SiemensVmid = siemensVmid;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int remainmemoryresource;

    public void initCpuarraypool(int cpuresources,int maxtimelength){
        this.cpuarraypool = new int[cpuresources][maxtimelength];
        for(int i=0;i<this.cpuarraypool.length;i++){
            for(int j=0;j<this.cpuarraypool[0].length;j++){
                this.cpuarraypool[i][j]=0;
            }
        }

    }

    public void initMemoryarraypool(int memoryresources, int maxtimelength){
        this.memoryarraypool = new int[memoryresources][maxtimelength];
        for(int i = 0; i<this.memoryarraypool.length; i++){
            for(int j = 0; j<this.memoryarraypool[0].length; j++){
                this.memoryarraypool[i][j]=0;
            }
        }

    }

    public void initBwarraypool(int bwresources,int maxtimelength){
        this.bwarraypool = new int[bwresources][maxtimelength];
        for(int i=0;i<this.bwarraypool.length;i++){
            for(int j=0;j<this.bwarraypool[0].length;j++){
                this.bwarraypool[i][j]=0;
            }
        }

    }

    public void changeCpuarraypool(int remainperesource, int cloudletcpurequest,int presenttime, int cloudletconsumetime){

        int arraystart= this.cpuarraypool.length-remainperesource;
        for(int i=arraystart;i<arraystart+cloudletcpurequest;i++) {
            for (int j = presenttime; j < presenttime + cloudletconsumetime; j++) {
                this.cpuarraypool[i][j] = 1;
            }
        }
    }
    public void changeBwarraypool(int remainbwresource, int cloudletbwrequest,int presenttime, int cloudletconsumetime){

        int arraystart= this.bwarraypool.length-remainbwresource;
        for(int i=arraystart;i<arraystart+cloudletbwrequest;i++) {
            for (int j = presenttime; j < presenttime + cloudletconsumetime; j++) {
                this.bwarraypool[i][j] = 1;
            }
        }
    }

    public void changeMemoryarraypool(int remainmemoryresource, int cloudletbwrequest, int presenttime, int cloudletconsumetime){

        int arraystart= this.memoryarraypool.length-remainmemoryresource;
        for(int i=arraystart;i<arraystart+cloudletbwrequest;i++) {
            for (int j = presenttime; j < presenttime + cloudletconsumetime; j++) {
                this.memoryarraypool[i][j] = 1;
            }
        }
    }


//    public SiemensContainerresource(int id,
//                      int userId,
//                      double mips,
//                      float ram,
//                      long bw,
//                      long size,
//                      String vmm,
//                      ContainerScheduler containerScheduler,
//                      ContainerRamProvisioner containerRamProvisioner,
//                      ContainerBwProvisioner containerBwProvisioner,
//                      List<? extends ContainerPe> peList){
//        super(id,userId,mips,ram,bw,size,vmm, containerScheduler,containerRamProvisioner,containerBwProvisioner,peList);
//        setBwpool(0);
//
//        setMemorypool(0);
//        setPepool(0);
//    }


    public SiemensContainerresource(){
//        super(id, userId, mips, numberOfPes, ram, bw, cloudletList);
        setMemorypool(0);
        setPepool(0);
        setState(0);
    }


    public int getRemaincpuresource() {
        return remaincpuresource;
    }

    public void setRemaincpuresource(int time) {
        int remaincpuresource=0;
        for(int i=0;i<cpuarraypool.length;i++) {
            if(cpuarraypool[i][time]==0){
                remaincpuresource++;
            }
        }
        for(int j=0;j<cpuarraypool.length-remaincpuresource;j++){
            cpuarraypool[j][time]=1;
        }
        for(int z=cpuarraypool.length-remaincpuresource;z<cpuarraypool.length;z++){
            cpuarraypool[z][time]=0;
        }
        this.remaincpuresource = remaincpuresource;
    }

    public int getRemainmemoryresource() {
        return remainmemoryresource;
    }

    public void setRemainmemoryresource(int time) {
        int remainbwresource=0;
        for(int i = 0; i< memoryarraypool.length; i++) {
            if(memoryarraypool[i][time]==0){
                remainbwresource++;
            }
        }
        for(int j = 0; j< memoryarraypool.length-remainbwresource; j++){
            memoryarraypool[j][time]=1;
        }
        for(int z = memoryarraypool.length-remainbwresource; z< memoryarraypool.length; z++){
            memoryarraypool[z][time]=0;
        }
        this.remainmemoryresource = remainbwresource;
    }

    public int[][] getCpuarraypool() {
        return cpuarraypool;
    }

    public void setCpuarraypool(int[][] cpuarraypool) {
        this.cpuarraypool = cpuarraypool;
    }
    public int[][] getMemoryarraypool() {
        return memoryarraypool;
    }

    public void setMemoryarraypool(int[][] memoryarraypool) {
        this.memoryarraypool = memoryarraypool;
    }


    public int getPepool() {
        return pepool;
    }

    public void setPepool(int pepool) {
        this.pepool = pepool;
    }

    public int getMemorypool() {
        return memorypool;
    }

    public void setMemorypool(int memorypool) {
        this.memorypool = memorypool;
    }



}
