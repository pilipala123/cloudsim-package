package org.yunji.cloudsimGWslb;

import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.provisioners.PeProvisioner;

public class GWPe extends Pe {
        /**
         * Instantiates a new Pe object.
         *
         * @param id            the Pe ID
         * @param peProvisioner the pe provisioner
         * @pre id >= 0
         * @pre peProvisioner != null
         * @post $none
         */
    public GWPe(int id, PeProvisioner peProvisioner) {
        super(id, peProvisioner);
    }
}
