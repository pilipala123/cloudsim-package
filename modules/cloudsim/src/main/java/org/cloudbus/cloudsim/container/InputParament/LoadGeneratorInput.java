package org.cloudbus.cloudsim.container.InputParament;

public class LoadGeneratorInput {
    private int Loadnumbers;
    private int Loadduration;
    private int Concurrentrequest;
    private int Requestinterval;
    private int Ramp_up;
    private int Ramp_down;

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