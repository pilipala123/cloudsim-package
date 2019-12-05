/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.container.load.load;

import org.cloudbus.cloudsim.UtilizationModel;

/**
 * The UtilizationModelFull class is a simple model, according to which a Cloudlet always utilizes
 * a given allocated resource at 100%, all the time.
 * UtilizationModelFull类是一个简单的模型，根据该模型，Cloudlet始终以100%的速率使用给定的分配资源。
 *
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 2.0
 */
public class UtilizationModelFull implements UtilizationModel {

    /**
     * Gets the utilization percentage of a given resource
     * in relation to the total capacity of that resource allocated
     * to the cloudlet.
     *
     * @param time the time to get the resource usage, that isn't considered
     *             for this UtilizationModel.
     *             获取给定资源相对于分配给cloudlet的资源总容量的利用率百分比@param time获取资源使用情况的时间，此利用率模型不考虑此时间。
     * @return Always return 1 (100% of utilization), independent of the time.
     */
    @Override
    public double getUtilization(double time) {
        return 1;
    }

}
