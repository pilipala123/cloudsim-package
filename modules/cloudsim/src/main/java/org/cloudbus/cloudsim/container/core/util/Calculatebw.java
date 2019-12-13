package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.core.Siemens.RegressionParament;
import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.util.ArrayList;
import java.util.List;

public class Calculatebw {

    public static double calculateregressionbw(String label1, String label2, int flag, RegressionParament regressionParament, SiemensList siemensList){
        double a,b,c;
        double bw=0.0;
        int time = siemensList.getFinishtime();
        List<Double> bwlist = new ArrayList<>();
        switch (label1 + "_" + label2) {
            case "slb_k8s": {
                if (flag == 0) {
                    a = regressionParament.getSlb_k8s_s().getBw_a_s();
                    b = regressionParament.getSlb_k8s_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getSlb_k8s_m().getBw_a_m();
                    b = regressionParament.getSlb_k8s_m().getBw_b_m();
                    c = regressionParament.getSlb_k8s_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }

            case "k8s_slb":{
                if (flag == 0) {
                    a = regressionParament.getK8s_slb_s().getBw_a_s();
                    b = regressionParament.getK8s_slb_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getK8s_slb_m().getBw_a_m();
                    b = regressionParament.getK8s_slb_m().getBw_b_m();
                    c = regressionParament.getK8s_slb_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }

            case "k8s_nfr":{
                if (flag == 0) {
                    a = regressionParament.getK8s_nfr_s().getBw_a_s();
                    b = regressionParament.getK8s_nfr_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getK8s_nfr_m().getBw_a_m();
                    b = regressionParament.getK8s_nfr_m().getBw_b_m();
                    c = regressionParament.getK8s_nfr_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }


            case "k8s_redis":{
                if (flag == 0) {
                    a = regressionParament.getK8s_redis_s().getBw_a_s();
                    b = regressionParament.getK8s_redis_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getK8s_redis_m().getBw_a_m();
                    b = regressionParament.getK8s_redis_m().getBw_b_m();
                    c = regressionParament.getK8s_redis_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }

            case "gwtma_nfr":{
                if (flag == 0) {
                    a = regressionParament.getGwtma_nfr_s().getBw_a_s();
                    b = regressionParament.getGwtma_nfr_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getGwtma_nfr_m().getBw_a_m();
                    b = regressionParament.getGwtma_nfr_m().getBw_b_m();
                    c = regressionParament.getGwtma_nfr_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }

            case "nfr_gwtma":{
                if (flag == 0) {
                    a = regressionParament.getNfr_gwtma_s().getBw_a_s();
                    b = regressionParament.getNfr_gwtma_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getNfr_gwtma_m().getBw_a_m();
                    b = regressionParament.getNfr_gwtma_m().getBw_b_m();
                    c = regressionParament.getNfr_gwtma_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }

            case "redis_k8s":{
                if (flag == 0) {
                    a = regressionParament.getRedis_k8s_s().getBw_a_s();
                    b = regressionParament.getRedis_k8s_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getRedis_k8s_m().getBw_a_m();
                    b = regressionParament.getRedis_k8s_m().getBw_b_m();
                    c = regressionParament.getRedis_k8s_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }
            case "nfr_k8s":{
                if (flag == 0) {
                    a = regressionParament.getNfr_k8s_s().getBw_a_s();
                    b = regressionParament.getNfr_k8s_s().getBw_b_s();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b;
                        bwlist.add(bw);
                    }
                    break;

                }
                else {
                    a = regressionParament.getNfr_k8s_m().getBw_a_m();
                    b = regressionParament.getNfr_k8s_m().getBw_b_m();
                    c = regressionParament.getNfr_k8s_m().getBw_c_m();
                    for(int i=0;i<time;i++) {
                        bw = a * (double) siemensList.getLoadnumber().get(i) + b * siemensList.getQpslist().get(i) + c;
                        bwlist.add(bw);

                    }
                    break;
                }
            }


        }
        Plotpictures.plotpicture(time,bwlist,label1+"_"+label2+"带宽","bw");
        return bw;

    }

}
