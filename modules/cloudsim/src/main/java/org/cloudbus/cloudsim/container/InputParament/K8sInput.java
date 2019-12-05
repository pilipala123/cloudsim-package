package org.cloudbus.cloudsim.container.InputParament;

public class K8sInput {
    private int ECSNumbers;

    private int K8smoney;

    public int getK8smoney() {
        return K8smoney;
    }

    public void setK8smoney(int k8smoney) {
        K8smoney = k8smoney;
    }

    public int getECSNumbers() {
        return ECSNumbers;
    }

    public void setECSNumbers(int ECSNumbers) {
        this.ECSNumbers = ECSNumbers;
    }

    public int getPodNumbersineachECS() {
        return PodNumbersineachECS;
    }

    public void setPodNumbersineachECS(int podNumbersineachECS) {
        PodNumbersineachECS = podNumbersineachECS;
    }

    private int PodNumbersineachECS;
}
