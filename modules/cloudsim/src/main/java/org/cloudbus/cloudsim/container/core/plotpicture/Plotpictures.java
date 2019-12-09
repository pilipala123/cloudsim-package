package org.cloudbus.cloudsim.container.core.plotpicture;

import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

public class Plotpictures {
    public static void plotpicture(int time, List<Integer> numberlist,String title,String ylabel){
        LineCharts fjc = new LineCharts("折线图",time,numberlist,title,ylabel);
        fjc.pack();
        RefineryUtilities.centerFrameOnScreen(fjc);
        fjc.setVisible(true);
    }
}
