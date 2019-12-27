package org.cloudbus.cloudsim.container.InputParament;

public class PartInput implements Comparable<PartInput>{
    private int id;
    private String type;
    private String name;
    private int responsetime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(int responsetime) {
        this.responsetime = responsetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PartInput(){
        setName("null");
    }


    @Override
    public int compareTo(PartInput partInput) {

        return this.id - partInput.getId();
    }



}

