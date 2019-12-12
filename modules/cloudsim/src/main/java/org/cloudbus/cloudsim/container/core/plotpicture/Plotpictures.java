package org.cloudbus.cloudsim.container.core.plotpicture;

import org.apache.commons.math3.util.Pair;
import org.jfree.ui.RefineryUtilities;

import java.util.List;

public class Plotpictures {
    public static void plotpicture(int time, List<Integer> numberlist,String title,String ylabel){
        LineCharts fjc = new LineCharts("折线图",time,numberlist,title,ylabel);
        fjc.pack();
        RefineryUtilities.centerFrameOnScreen(fjc);
        fjc.setVisible(true);
    }

    public static void plotpictureFromMap(int time, List<Pair<Integer, Integer>> numberlist, String title, String ylabel) {
        LineCharts fjc = new LineCharts("折线图", time, title, ylabel, numberlist);
        fjc.pack();
        RefineryUtilities.centerFrameOnScreen(fjc);
        fjc.setVisible(true);
    }
}
