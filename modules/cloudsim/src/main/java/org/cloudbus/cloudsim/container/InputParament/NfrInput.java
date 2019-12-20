package org.cloudbus.cloudsim.container.InputParament;

public class NfrInput {
    private int NfrECSnumber;
    private int Nfrcontainernumber;
    private int Nfrmaxqps;
    private int Nfrnetworkbandwidth;
    private double cpucore;

    public double getCpucore() {
        return cpucore;
    }

    public void setCpucore(double cpucore) {
        this.cpucore = cpucore;
    }

    public int getNfrECSnumber() {
        return NfrECSnumber;
    }

    public void setNfrECSnumber(int nfrECSnumber) {
        NfrECSnumber = nfrECSnumber;
    }

    public int getNfrcontainernumber() {
        return Nfrcontainernumber;
    }

    public void setNfrcontainernumber(int nfrcontainernumber) {
        Nfrcontainernumber = nfrcontainernumber;
    }

    public int getNfrmaxqps() {
        return Nfrmaxqps;
    }

    public void setNfrmaxqps(int nfrmaxqps) {
        Nfrmaxqps = nfrmaxqps;
    }

    public int getNfrnetworkbandwidth() {
        return Nfrnetworkbandwidth;
    }

    public void setNfrnetworkbandwidth(int nfrnetworkbandwidth) {
        Nfrnetworkbandwidth = nfrnetworkbandwidth;
    }
}
