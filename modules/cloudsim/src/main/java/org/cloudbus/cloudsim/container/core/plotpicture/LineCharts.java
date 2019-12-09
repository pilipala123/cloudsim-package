package org.cloudbus.cloudsim.container.core.plotpicture;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LineCharts extends ApplicationFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public LineCharts(String s, int time, List<Integer> numberlist,String title,String ylabel) {
        super(s);
        setContentPane(createDemoLine(time,numberlist,title,ylabel));
    }
//    public static void main(String[] args) {
//        List<Integer> numberlist= new ArrayList<>();
//        for (int i=0;i<100;i++){
//            numberlist.add(i);
//        }
//        LineCharts fjc = new LineCharts("折线图",100,numberlist);
//        fjc.pack();
//        RefineryUtilities.centerFrameOnScreen(fjc);
//        fjc.setVisible(true);
//    }
    // 生成显示图表的面板
    public static JPanel createDemoLine(int time, List<Integer> numberlist,String title,String ylabel) {
        JFreeChart jfreechart = createChart(createDataset(time,numberlist,ylabel),title,ylabel);
        return new ChartPanel(jfreechart);
    }
    // 生成图表主对象JFreeChart
    public static JFreeChart createChart(DefaultCategoryDataset linedataset,String title,String ylabel) {
        // 定义图表对象
        JFreeChart chart = ChartFactory.createLineChart(title, //折线图名称
                "time", // 横坐标名称
                ylabel, // 纵坐标名称
                linedataset, // 数据
                PlotOrientation.VERTICAL, // 水平显示图像
                true, // include legend
                true, // tooltips
                false // urls
        );
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(false); //是否显示格子线
        plot.setBackgroundAlpha(0.3f); //设置背景透明度
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.20);
        rangeAxis.setLabelAngle(Math.PI / 2.0);
        return chart;
    }
    // 生成数据
    public static DefaultCategoryDataset createDataset(int time, List<Integer> numberlist,String ylabel) {
        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        // 各曲线名称
        String series1 = "x:time;y:"+ylabel;
        String series2 = "彩电";
        String series3 = "洗衣机";
        // 横轴名称(列名称)
        String type1 = "1月";
        String type2 = "2月";
        String type3 = "3月";

        for (int i=0;i<time;i++){
            linedataset.addValue(numberlist.get(i), series1, Integer.toString(i));
        }

//        linedataset.addValue(1.0, series2, type1);
//        linedataset.addValue(5.2, series2, type2);
//        linedataset.addValue(7.9, series2, type3);
//        linedataset.addValue(2.0, series3, type1);
//        linedataset.addValue(9.2, series3, type2);
//        linedataset.addValue(8.9, series3, type3);
        return linedataset;
    }
}