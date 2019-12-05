/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.container.load;

/**
 * The UtilizationModel interface needs to be implemented in order to provide a fine-grained control
 * over resource usage by a Cloudlet.
 * 需要实现UtilizationModel接口，以便对Cloudlet的资源使用提供细粒度的控制。
 *
 * @author Anton Beloglazov
 * @todo It has to be seen if the utilization models are only for cloudlets. If yes,
 * the name of the interface and implementing classes would include the word "Cloudlet"
 * to make clear their for what kind of entity they are related.
 * 接口和实现类的名称将包含“Cloudlet”一词，以明确它们与哪种实体相关。
 * @since CloudSim Toolkit 2.0
 */
public interface UtilizationModel {

    /**
     * Gets the utilization percentage of a given resource.
     * 获取给定资源的利用率百分比。
     *
     * @param time the time to get the resource usage.
     *             获取资源使用情况的时间
     * @return utilization percentage, from [0 to 1]
     */
    double getUtilization(double time);

}
