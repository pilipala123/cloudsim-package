package org.cloudbus.cloudsim.container.InputParament;

public class K8sInput {
    private int ECSNumbers;

    private int K8smoney;

    private int K8sECSnumber;
    private int K8scontainernumber;
    private int K8smaxqps;
    private int K8snetworkbandwidth;
    private double K8scpucore;
    private double K8sqpsperload;
    private double K8sqpsthreshold;
    private double K8sqpsratio;
    private double K8sresponsetimethreshold;
    private double K8sresponsetimeratio;

    public double getK8sqpsthreshold() {
        return K8sqpsthreshold;
    }

    public void setK8sqpsthreshold(double k8sqpsthreshold) {
        K8sqpsthreshold = k8sqpsthreshold;
    }

    public double getK8sqpsratio() {
        return K8sqpsratio;
    }

    public void setK8sqpsratio(double k8sqpsratio) {
        K8sqpsratio = k8sqpsratio;
    }

    public double getK8sresponsetimethreshold() {
        return K8sresponsetimethreshold;
    }

    public void setK8sresponsetimethreshold(double k8sresponsetimethreshold) {
        K8sresponsetimethreshold = k8sresponsetimethreshold;
    }

    public double getK8sresponsetimeratio() {
        return K8sresponsetimeratio;
    }

    public void setK8sresponsetimeratio(double k8sresponsetimeratio) {
        K8sresponsetimeratio = k8sresponsetimeratio;
    }

    public double getK8sqpsperload() {
        return K8sqpsperload;
    }

    public void setK8sqpsperload(double k8sqpsperload) {
        K8sqpsperload = k8sqpsperload;
    }

    public double getK8scpucore() {
        return K8scpucore;
    }

    public void setK8scpucore(double k8scpucore) {
        K8scpucore = k8scpucore;
    }

    public int getK8sECSnumber() {
        return K8sECSnumber;
    }

    public void setK8sECSnumber(int k8sECSnumber) {
        K8sECSnumber = k8sECSnumber;
    }

    public int getK8scontainernumber() {
        return K8scontainernumber;
    }

    public void setK8scontainernumber(int k8scontainernumber) {
        K8scontainernumber = k8scontainernumber;
    }

    public int getK8smaxqps() {
        return K8smaxqps;
    }

    public void setK8smaxqps(int k8smaxqps) {
        K8smaxqps = k8smaxqps;
    }

    public int getK8snetworkbandwidth() {
        return K8snetworkbandwidth;
    }

    public void setK8snetworkbandwidth(int k8snetworkbandwidth) {
        K8snetworkbandwidth = k8snetworkbandwidth;
    }

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
