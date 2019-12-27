package org.cloudbus.cloudsim.container.core.util;

import org.cloudbus.cloudsim.container.core.Siemens.SiemensList;
import org.cloudbus.cloudsim.container.core.plotpicture.Plotpictures;

import java.util.ArrayList;
import java.util.List;

public class CalculateSumResponsetime {

    public static void calculateresultresponsetime(List<SiemensList> siemensLists,List<SiemensList>  siemensListList,int ramp_down){
        List<Double> responsetime_list = new ArrayList<>();
        double response_time = 0,sumresponse_time = 0,average_response_time = 0;
        int finishnumber= 0;
        int i=0;
        int size = 0;

        while (true){
            sumresponse_time =0;
            finishnumber = 0;
//            average_response_time=0;
            response_time=0;
            for(SiemensList siemensList:siemensLists){
                sumresponse_time = sumresponse_time + siemensList.getAvgperresponsetimelist().get(i) * siemensList.getPresentfinishcloudletnumberlist().get(i);
                finishnumber = finishnumber + siemensList.getPresentfinishcloudletnumberlist().get(i);

            }
            finishnumber = finishnumber/siemensLists.size();

            if(finishnumber ==0){
                response_time =0;
                average_response_time=0;
            }
            else {
                average_response_time=sumresponse_time/finishnumber;
                for(SiemensList siemensList2:siemensListList) {
                    response_time = response_time + siemensList2.getAverageresponsetimelist().get(i);
                }
            }
            response_time=response_time+average_response_time;
            responsetime_list.add(response_time);
            i++;
            if(i==ramp_down-1){
                break;
            }
        }
        Plotpictures.plotpicture(i,responsetime_list,"总的response time","rt");
    }

}
