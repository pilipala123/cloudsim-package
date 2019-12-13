package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.util.ArrayList;
import java.util.List;

public class CalculateSumResponsetime {

    public static void calculateresultresponsetime(List<SiemensList> siemensLists){
        List<Double> responsetime_list = new ArrayList<>();
        double response_time = 0,sumresponse_time = 0;
        int finishnumber= 0;
        int i=0;
        int size = 0;

        while (true){
            sumresponse_time =0;
            finishnumber = 0;
            for(SiemensList siemensList:siemensLists){
                if(i>=siemensList.getFinishcloudletnumber().size()){
                    finishnumber = finishnumber + siemensList.getFinishcloudletnumber().get(siemensList.getFinishcloudletnumber().size()-1);
                }
                else {
                    sumresponse_time = sumresponse_time + siemensList.getAverageresponsetimelist().get(i) * siemensList.getFinishcloudletnumber().get(i);
                    finishnumber = finishnumber + siemensList.getFinishcloudletnumber().get(i);
                }
            }
            finishnumber = finishnumber/3;
            if(finishnumber ==0){
                response_time =0;
            }
            else {
                response_time = sumresponse_time / finishnumber;
            }
            responsetime_list.add(response_time);
            i++;
            if(sumresponse_time ==0&&finishnumber!=0){
                break;
            }
        }
        Plotpictures.plotpicture(i,responsetime_list,"总的response time","rt");
    }

}
