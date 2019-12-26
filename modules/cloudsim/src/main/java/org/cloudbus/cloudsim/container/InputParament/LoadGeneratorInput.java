package org.cloudbus.cloudsim.container.InputParament;

public class LoadGeneratorInput {
    private int Loadnumbers;
    private int Loadduration;
    private int Concurrentrequest;
    private int Requestinterval;
    private int Ramp_up;
    private int Ramp_down;
    private double timerange;
    private double precision;
    private int timenumber;
    private int cloudletcpurequest;
    private int cloudletmemoryrequest;
    private int cloudletlength;
    private double packetsizerandom;
    private double basepacketsize;
    private int cpurandomnumber;
    private int lengthrandomnumber;
    private int bwrandomnumber;
    private int slbresponsetime;
    private int nfrresponsetime;
    private int mockserviceresponsetime;

    public int getSlbresponsetime() {
        return slbresponsetime;
    }

    public void setSlbresponsetime(int slbresponsetime) {
        this.slbresponsetime = slbresponsetime;
    }

    public int getNfrresponsetime() {
        return nfrresponsetime;
    }

    public void setNfrresponsetime(int nfrresponsetime) {
        this.nfrresponsetime = nfrresponsetime;
    }

    public int getMockserviceresponsetime() {
        return mockserviceresponsetime;
    }

    public void setMockserviceresponsetime(int mockserviceresponsetime) {
        this.mockserviceresponsetime = mockserviceresponsetime;
    }

    public int getCloudletcpurequest() {
        return cloudletcpurequest;
    }

    public void setCloudletcpurequest(int cloudletcpurequest) {
        this.cloudletcpurequest = cloudletcpurequest;
    }

    public int getCloudletmemoryrequest() {
        return cloudletmemoryrequest;
    }

    public void setCloudletmemoryrequest(int cloudletmemoryrequest) {
        this.cloudletmemoryrequest = cloudletmemoryrequest;
    }

    public int getCloudletlength() {
        return cloudletlength;
    }

    public void setCloudletlength(int cloudletlength) {
        this.cloudletlength = cloudletlength;
    }

    public double getPacketsizerandom() {
        return packetsizerandom;
    }

    public void setPacketsizerandom(double packetsizerandom) {
        this.packetsizerandom = packetsizerandom;
    }

    public double getBasepacketsize() {
        return basepacketsize;
    }

    public void setBasepacketsize(double basepacketsize) {
        this.basepacketsize = basepacketsize;
    }

    public int getCpurandomnumber() {
        return cpurandomnumber;
    }

    public void setCpurandomnumber(int cpurandomnumber) {
        this.cpurandomnumber = cpurandomnumber;
    }

    public int getLengthrandomnumber() {
        return lengthrandomnumber;
    }

    public void setLengthrandomnumber(int lengthrandomnumber) {
        this.lengthrandomnumber = lengthrandomnumber;
    }

    public int getBwrandomnumber() {
        return bwrandomnumber;
    }

    public void setBwrandomnumber(int bwrandomnumber) {
        this.bwrandomnumber = bwrandomnumber;
    }

    public int getTimenumber() {
        return timenumber;
    }

    public void setTimenumber(int timenumber) {
        this.timenumber = timenumber;
    }

    public double getTimerange() {
        return timerange;
    }

    public void setTimerange(double timerange) {
        this.timerange = timerange;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public int getLoadnumbers() {
        return Loadnumbers;
    }

    public void setLoadnumbers(int loadnumbers) {
        Loadnumbers = loadnumbers;
    }

    public int getLoadduration() {
        return Loadduration;
    }

    public void setLoadduration(int loadduration) {
        Loadduration = loadduration;
    }

    public int getConcurrentrequest() {
        return Concurrentrequest;
    }

    public void setConcurrentrequest(int concurrentrequest) {
        Concurrentrequest = concurrentrequest;
    }

    public int getRequestinterval() {
        return Requestinterval;
    }

    public void setRequestinterval(int requestinterval) {
        Requestinterval = requestinterval;
    }

    public int getRamp_up() {
        return Ramp_up;
    }

    public void setRamp_up(int ramp_up) {
        Ramp_up = ramp_up;
    }

    public int getRamp_down() {
        return Ramp_down;
    }

    public void setRamp_down(int ramp_down) {
        Ramp_down = ramp_down;
    }
}
