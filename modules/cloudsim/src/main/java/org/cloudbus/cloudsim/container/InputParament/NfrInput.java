package org.cloudbus.cloudsim.container.InputParament;

public class NfrInput {
    private int NfrECSnumber;
    private int Nfrcontainernumber;
    private int Nfrmaxqps;
    private int Nfrnetworkbandwidth;
    private double cpucore;
    private double Nfrqpsperload;
    private double Nfrqpsthreshold;
    private double Nfrqpsratio;
    private double Nfrresponsetimethreshold;
    private double Nfrresponsetimeratio;

    public double getNfrqpsthreshold() {
        return Nfrqpsthreshold;
    }

    public void setNfrqpsthreshold(double nfrqpsthreshold) {
        Nfrqpsthreshold = nfrqpsthreshold;
    }

    public double getNfrqpsratio() {
        return Nfrqpsratio;
    }

    public void setNfrqpsratio(double nfrqpsratio) {
        Nfrqpsratio = nfrqpsratio;
    }

    public double getNfrresponsetimethreshold() {
        return Nfrresponsetimethreshold;
    }

    public void setNfrresponsetimethreshold(double nfrresponsetimethreshold) {
        Nfrresponsetimethreshold = nfrresponsetimethreshold;
    }

    public double getNfrresponsetimeratio() {
        return Nfrresponsetimeratio;
    }

    public void setNfrresponsetimeratio(double nfrresponsetimeratio) {
        Nfrresponsetimeratio = nfrresponsetimeratio;
    }

    public double getNfrqpsperload() {
        return Nfrqpsperload;
    }

    public void setNfrqpsperload(double nfrqpsperload) {
        Nfrqpsperload = nfrqpsperload;
    }

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
