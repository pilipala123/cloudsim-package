package org.cloudbus.cloudsim.container.InputParament;

public class K8sInput extends PartInput implements Comparable<K8sInput>{
    private int id;
    private int ECSNumbers;
    private int EcsCPUQuota;
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
    private double k8scpuparament;
    private double k8sbwparamnet;
    private int k8smipsparament;
    private double k8sresponsetimeparament;
    private double k8smipsability;
    private String name;
    private double responsetimeratio2;

    public double getResponsetimeratio2() {
        return responsetimeratio2;
    }

    public void setResponsetimeratio2(double responsetimeratio2) {
        this.responsetimeratio2 = responsetimeratio2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getK8smipsability() {
        return k8smipsability;
    }

    public void setK8smipsability(double k8smipsability) {
        this.k8smipsability = k8smipsability;
    }

    public double getK8scpuparament() {
        return k8scpuparament;
    }

    public void setK8scpuparament(double k8scpuparament) {
        this.k8scpuparament = k8scpuparament;
    }

    public double getK8sbwparamnet() {
        return k8sbwparamnet;
    }

    public void setK8sbwparamnet(double k8sbwparamnet) {
        this.k8sbwparamnet = k8sbwparamnet;
    }

    public int getK8smipsparament() {
        return k8smipsparament;
    }

    public void setK8smipsparament(int k8smipsparament) {
        this.k8smipsparament = k8smipsparament;
    }

    public double getK8sresponsetimeparament() {
        return k8sresponsetimeparament;
    }

    public void setK8sresponsetimeparament(double k8sresponsetimeparament) {
        this.k8sresponsetimeparament = k8sresponsetimeparament;
    }

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

    @Override
    public int compareTo(K8sInput k8sInput) {
        return this.id - k8sInput.getId();
    }
}
