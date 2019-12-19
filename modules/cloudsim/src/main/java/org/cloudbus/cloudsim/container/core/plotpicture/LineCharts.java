package org.cloudbus.cloudsim.container.core.plotpicture;

import org.apache.commons.math3.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LineCharts extends ApplicationFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static List<Double> staticNumberList = null;

    public LineCharts(String s, int time, List<Double> numberlist, String title, String ylabel) {
        super(s);
        staticNumberList = numberlist;
        setContentPane(createDemoLine(time,numberlist,title,ylabel));
    }

    public LineCharts(String s, int time, String title, String ylabel, List<Pair<Integer, Integer>> numberlist) {
        super(s);
        setContentPane(createDemoLineFromMap(time, numberlist, title, ylabel));
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


    public static JPanel createDemoLine(int time, List<Double> numberlist, String title, String ylabel) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        //统计数据定义
        XYSeries xyseries = new XYSeries("x:time;y:"+ylabel);
        int index=0;
        for (int i=0;i<time;i++){
            xyseries.add(i, numberlist.get(i));
        }

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        xyseriescollection.addSeries(xyseries);

        //JFreeChart jfreechart = createChart(createDataset(time,numberlist,ylabel),title,ylabel);
        JFreeChart jfreechart = createChart(xyseriescollection,title,ylabel);

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
        CategoryAxis domainAxis = plot.getDomainAxis();
        //x轴显示设置，显示10个刻度
        setDomainAxis(domainAxis,staticNumberList.size());

        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.20);
        rangeAxis.setLabelAngle(Math.PI / 2.0);
        return chart;
    }



    // 生成图表主对象JFreeChart
    public static JFreeChart createChart(XYDataset xyDataset, String title, String ylabel) {

        // 定义图表对象
        JFreeChart chart = ChartFactory.createTimeSeriesChart(title, //折线图名称
                "time(s)", // 横坐标名称
                ylabel, // 纵坐标名称
                xyDataset, // 数据
                true, // include legend
                false, // tooltips
                false // urls
        );
        XYPlot plot = (XYPlot) chart.getXYPlot();
        plot.setDomainGridlinesVisible(true);
        DateAxis dateaxis =(DateAxis)plot.getDomainAxis();
        SimpleDateFormat format = new SimpleDateFormat("sSSS");
        dateaxis.setDateFormatOverride(format);
        plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
//        chart.setBackgroundPaint(Color.white);
//        CategoryPlot plot = chart.getCategoryPlot();
//        plot.setRangeGridlinesVisible(true); //是否显示格子线
//        plot.setBackgroundAlpha(0.3f); //设置背景透明度
//        CategoryAxis domainAxis = plot.getDomainAxis();
//        //x轴显示设置，显示10个刻度
//        setDomainAxis(domainAxis,staticNumberList.size());
//
//        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //rangeAxis.setAutoRangeIncludesZero(true);
        //rangeAxis.setUpperMargin(0.20);
        // rangeAxis.setLabelAngle(Math.PI / 2.0);


        return chart;
    }

    public static void setDomainAxis(CategoryAxis domainAxis,int max){
        domainAxis.setTickLabelFont(new Font("宋体", Font.ITALIC, 20));  //解决x轴坐标上中文乱码
        domainAxis.setLabelFont(new Font("宋体", Font.ITALIC, 20));  //解决x轴标题中文乱码
        domainAxis.setTickMarksVisible(true);  //用于显示X轴标尺
        domainAxis.setTickLabelsVisible(true); //用于显示X轴标尺值
        for(int i = 0; i<max; i++)
        {
            //设置显示10个刻度，可自选
            if(i%(max/10) ==0)
            {
                domainAxis.setTickLabelPaint(Integer.toString(i), Color.black);
            }else{
                domainAxis.setTickLabelPaint(Integer.toString(i), Color.white);
            }
        }
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);  //设置X轴45度
    }
    // 生成数据
    public static JPanel createDemoLineFromMap(int time, List<Pair<Integer, Integer>> numberlist, String title, String ylabel) {
        JFreeChart jfreechart = createChart(createDatasetFromMap(time, numberlist, ylabel), title, ylabel);
        return new ChartPanel(jfreechart);
    }


    public static DefaultCategoryDataset createDataset(int time, List<Double> numberlist, String ylabel) {
        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        // 各曲线名称
        String series1 = "x:time;y:"+ylabel;


        for (int i=0;i<time;i++){
            linedataset.addValue(numberlist.get(i), series1, Integer.toString(i));
        }
        return linedataset;
    }

    public static DefaultCategoryDataset createDatasetFromMap(int time, List<Pair<Integer, Integer>> numberlist, String ylabel) {
        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        // 各曲线名称
        String series1 = "x:time;y:" + ylabel;
        for (int i = 0; i < time; i++) {
            Pair<Integer, Integer> pair = numberlist.get(i);
            linedataset.addValue(pair.getValue(), series1, pair.getKey());
        }

        return linedataset;
    }
}