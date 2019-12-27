package org.cloudbus.cloudsim.container.InputParament;

public class MockserviceInput {
    private int mockserviceresponsetime;
    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMockserviceresponsetime() {
        return mockserviceresponsetime;
    }

    public void setMockserviceresponsetime(int mockserviceresponsetime) {
        this.mockserviceresponsetime = mockserviceresponsetime;
    }
}
