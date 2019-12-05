package org.cloudbus.cloudsim.container.load;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weirenjie
 * @date 2019/11/22
 */
public class Axis extends JPanel {
    int hight;
    int width;
    Map<Double, Integer> pointMap = new HashMap();


    JFrame jFrame;

    public Axis() {
    }

    public Axis(Map<Double, Integer> map) {
        this.pointMap = map;
    }


    public void go() {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(hight, width);
        jFrame.add(this);

        jFrame.setTitle("任务与时间的坐标系");
        jFrame.setVisible(true);
    }

    /**
     * 四个象限
     *
     * @param graphics
     */
    // @Override
    // public void paintComponent(Graphics graphics) {
    //     graphics.setColor(Color.black);
    //     int height = this.getHeight();
    //     int width = this.getWidth();
    //     int x_axis = height/2;
    //     int y_axis = width/2;
    //     graphics.drawLine(0, x_axis, width, x_axis);
    //     graphics.drawLine(y_axis, 0, y_axis, height);
    //     //在屏幕中间构建平面直角坐标系
    //     double x, y;
    //     graphics.setColor(Color.blue);
    //     for (int i = 0; i < width; i++) {
    //         for (int j = 0; j < height; j++) {
    //             //遍历平面直角坐标系,如果一个点满足要求就绘制这个点
    //             x = (i - y_axis - 1) / 100.0;
    //             y = (x_axis - j - 1) / 100.0;
    //             if (isRight(y, x * x * x, 0.02)) {
    //                 graphics.fillOval(i, j, 2, 2);
    //             }
    //         }
    //     }
    // }
    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.black);
        int height = this.hight;
        int width = this.width;
        System.out.println("height" + height);
        System.out.println("width" + width);
        int x_axis = height / 2;
        int y_axis = width / 2;
        graphics.drawLine(0, x_axis, width, x_axis);
        graphics.drawLine(y_axis, 0, y_axis, height);
        graphics.setColor(Color.blue);
        // double x, y;

        // for (int i = 0; i < width; i++) {
        //     for (int j = 0; j < height; j++) {
        //         x = (i - y_axis) / 100.0;
        //         y = (x_axis - j) / 100.0;
        //         if (isRight(y, x * x, 0.02)) {
        //             graphics.fillOval(i, j, 5, 5);
        //         }
        //     }
        // }

        int x, y;
        System.out.println("Map.size = " + pointMap.size());
        for (Double i : pointMap.keySet()) {
            x = i.intValue() - y_axis;
            y = x_axis - pointMap.get(i);
            graphics.fillOval(x, y, 5, 5);
            System.out.println(x + "|" + y);
        }
    }

    boolean isRight(double num1, double num2, double error) {
        //判断两个数字是否在误差允许范围
        return (Math.abs(num1 - num2) <= error);
    }

    public static void main(String[] args) {
        MathUtil mathUtil = new MathUtil();
        Axis axis = new Axis(mathUtil.linearF(10, 0, 600, 800, 1));
        axis.setWidth(590);
        axis.setHight(800);
        axis.go();
    }


    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public Map<Double, Integer> getPointMap() {
        return pointMap;
    }

    public void setPointMap(Map<Double, Integer> pointMap) {
        this.pointMap = pointMap;
    }
}
