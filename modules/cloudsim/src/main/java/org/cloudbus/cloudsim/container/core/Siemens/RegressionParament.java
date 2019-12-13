package org.cloudbus.cloudsim.container.core.Siemens;

public class RegressionParament {
    private SingleRegreesionParament slb_k8s_s;
    private SingleRegreesionParament k8s_slb_s;
    private SingleRegreesionParament k8s_nfr_s;
    private SingleRegreesionParament nfr_k8s_s;
    private SingleRegreesionParament redis_k8s_s;
    private SingleRegreesionParament k8s_redis_s;
    private SingleRegreesionParament nfr_gwtma_s;
    private SingleRegreesionParament gwtma_nfr_s;

    private MultiRegressionParament slb_k8s_m;
    private MultiRegressionParament k8s_slb_m;
    private MultiRegressionParament k8s_nfr_m;
    private MultiRegressionParament nfr_k8s_m;
    private MultiRegressionParament redis_k8s_m;
    private MultiRegressionParament k8s_redis_m;
    private MultiRegressionParament nfr_gwtma_m;
    private MultiRegressionParament gwtma_nfr_m;


    private CpuRegression slb_cpu;
    private CpuRegression k8s_cpu;

    public RegressionParament(){
        setSlb_cpu(new CpuRegression());
        setSlb_k8s_m(new MultiRegressionParament());
        setSlb_k8s_s(new SingleRegreesionParament());
        setGwtma_nfr_m(new MultiRegressionParament());
        setGwtma_nfr_s(new SingleRegreesionParament());
        setK8s_cpu(new CpuRegression());
        setK8s_nfr_m(new MultiRegressionParament());
        setK8s_nfr_s(new SingleRegreesionParament());
        setK8s_redis_m(new MultiRegressionParament());
        setK8s_redis_s(new SingleRegreesionParament());
        setNfr_gwtma_m(new MultiRegressionParament());
        setNfr_gwtma_s(new SingleRegreesionParament());
        setNfr_k8s_m(new MultiRegressionParament());
        setNfr_k8s_s(new SingleRegreesionParament());
        setRedis_k8s_m(new MultiRegressionParament());
        setRedis_k8s_s(new SingleRegreesionParament());
        setK8s_slb_m(new MultiRegressionParament());
        setK8s_slb_s(new SingleRegreesionParament());
    }
    public SingleRegreesionParament getSlb_k8s_s() {
        return slb_k8s_s;
    }

    public void setSlb_k8s_s(SingleRegreesionParament slb_k8s_s) {
        this.slb_k8s_s = slb_k8s_s;
    }

    public MultiRegressionParament getSlb_k8s_m() {
        return slb_k8s_m;
    }

    public void setSlb_k8s_m(MultiRegressionParament slb_k8s_m) {
        this.slb_k8s_m = slb_k8s_m;
    }

    public CpuRegression getSlb_cpu() {
        return slb_cpu;
    }

    public void setSlb_cpu(CpuRegression slb_cpu) {
        this.slb_cpu = slb_cpu;
    }

    public SingleRegreesionParament getK8s_slb_s() {
        return k8s_slb_s;
    }

    public void setK8s_slb_s(SingleRegreesionParament k8s_slb_s) {
        this.k8s_slb_s = k8s_slb_s;
    }

    public SingleRegreesionParament getK8s_nfr_s() {
        return k8s_nfr_s;
    }

    public void setK8s_nfr_s(SingleRegreesionParament k8s_nfr_s) {
        this.k8s_nfr_s = k8s_nfr_s;
    }

    public SingleRegreesionParament getNfr_k8s_s() {
        return nfr_k8s_s;
    }

    public void setNfr_k8s_s(SingleRegreesionParament nfr_k8s_s) {
        this.nfr_k8s_s = nfr_k8s_s;
    }

    public SingleRegreesionParament getRedis_k8s_s() {
        return redis_k8s_s;
    }

    public void setRedis_k8s_s(SingleRegreesionParament redis_k8s_s) {
        this.redis_k8s_s = redis_k8s_s;
    }

    public SingleRegreesionParament getK8s_redis_s() {
        return k8s_redis_s;
    }

    public void setK8s_redis_s(SingleRegreesionParament k8s_redis_s) {
        this.k8s_redis_s = k8s_redis_s;
    }

    public SingleRegreesionParament getNfr_gwtma_s() {
        return nfr_gwtma_s;
    }

    public void setNfr_gwtma_s(SingleRegreesionParament nfr_gwtma_s) {
        this.nfr_gwtma_s = nfr_gwtma_s;
    }

    public SingleRegreesionParament getGwtma_nfr_s() {
        return gwtma_nfr_s;
    }

    public void setGwtma_nfr_s(SingleRegreesionParament gwtma_nfr_s) {
        this.gwtma_nfr_s = gwtma_nfr_s;
    }

    public MultiRegressionParament getK8s_slb_m() {
        return k8s_slb_m;
    }

    public void setK8s_slb_m(MultiRegressionParament k8s_slb_m) {
        this.k8s_slb_m = k8s_slb_m;
    }

    public MultiRegressionParament getK8s_nfr_m() {
        return k8s_nfr_m;
    }

    public void setK8s_nfr_m(MultiRegressionParament k8s_nfr_m) {
        this.k8s_nfr_m = k8s_nfr_m;
    }

    public MultiRegressionParament getNfr_k8s_m() {
        return nfr_k8s_m;
    }

    public void setNfr_k8s_m(MultiRegressionParament nfr_k8s_m) {
        this.nfr_k8s_m = nfr_k8s_m;
    }

    public MultiRegressionParament getRedis_k8s_m() {
        return redis_k8s_m;
    }

    public void setRedis_k8s_m(MultiRegressionParament redis_k8s_m) {
        this.redis_k8s_m = redis_k8s_m;
    }

    public MultiRegressionParament getK8s_redis_m() {
        return k8s_redis_m;
    }

    public void setK8s_redis_m(MultiRegressionParament k8s_redis_m) {
        this.k8s_redis_m = k8s_redis_m;
    }

    public MultiRegressionParament getNfr_gwtma_m() {
        return nfr_gwtma_m;
    }

    public void setNfr_gwtma_m(MultiRegressionParament nfr_gwtma_m) {
        this.nfr_gwtma_m = nfr_gwtma_m;
    }

    public MultiRegressionParament getGwtma_nfr_m() {
        return gwtma_nfr_m;
    }

    public void setGwtma_nfr_m(MultiRegressionParament gwtma_nfr_m) {
        this.gwtma_nfr_m = gwtma_nfr_m;
    }

    public CpuRegression getK8s_cpu() {
        return k8s_cpu;
    }

    public void setK8s_cpu(CpuRegression k8s_cpu) {
        this.k8s_cpu = k8s_cpu;
    }
}
