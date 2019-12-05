package org.cloudbus.cloudsim.container.core.redis;

public class QpsCaculate {
    /**
     * Instantiates a new Pe object.
     *
     * @param specification            the Pe ID
     * @param nodes the numbers of nodes
     * @param connectionpernumber the number of the connection per second
     * @param maxconnection the max number of the connection
     * @param maxBw the max Bandwidth
     * @param cpuability the number of cores every cpu
     * @param status which type of service you choose
     * @param IOnumber the number of IO threads
     * @param readnode the number of nodes only allowed read
     * @pre specification >= 0
     * @pre nodes >= 0
     * @pre connectionpernumber >= 0
     * @pre maxconnection >= 0
     * @pre maxBw >= 0
     * @pre cpuability >= 0
     * @pre status >= 0
     * @pre IOnumber >= 0
     * @pre readnode >= 0
     * @pre connectionpernumber <= maxconnection
     */
    private final static int STANDARDDOUBLE = 1;
    private final static int STANDARDENHANCE = 2;
    private final static int STANDARDHYBRID = 3;
    private final static int DISASTERTOLERANCE = 4;
    private final static int STANDARDSINGLE = 5;
    private final static int CLUSTERDOUBLE = 6;
    private final static int CLUSTERENHANCE = 7;
    private final static int CLUSTERDISASTERTOLERANCE = 8;
    private final static int ClUSTERSINGLE = 9;
    private final static int READANDWRITE = 10;
    private final static int READANDWRITEENHANCE = 11;
    private final static int CLUSTERREADANDWRITE = 12;
    private final static int standardqps = 80000;

    public static int QpsCaculate(int specification, int nodes, int connectionpernumber, int maxconnection
            , long maxBw, double cpuability, int status, int IOnumbers, int readnode) {
        int qps = 0;
        switch (status) {
            case STANDARDDOUBLE:
                qps = 80000;
                break;
            case STANDARDENHANCE:
                qps = 240000;
                break;
            case STANDARDHYBRID:
                qps = 100000;
                break;
            case DISASTERTOLERANCE:
                qps = 80000;
                break;
            case STANDARDSINGLE:
                qps = 80000;
                break;
            case CLUSTERDOUBLE:
                qps = getClusterdoubleQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability);
                break;
            case CLUSTERENHANCE:
                qps = getClusterenhanceQps(IOnumbers, specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability);
                break;
            case CLUSTERDISASTERTOLERANCE:
                qps = getClusterdisastertoleranceQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability);
                break;
            case ClUSTERSINGLE:
                qps = getClusterSingleQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability);
                break;
            case READANDWRITE:
                qps = getReadandwriteQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability);
                break;
            case READANDWRITEENHANCE:
                qps = getReadandwriteenhanceQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability);
                break;
            case CLUSTERREADANDWRITE:
                qps = getClusterreadandwriteQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability);
                break;
        }
        return qps;
    }

    private static int getClusterdoubleQps(int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
        int qps = 0;
        if (checkQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability)) {
            qps = standardqps * nodes;
        }
        return qps;

    }

    private static int getClusterenhanceQps(int IOnumbers, int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
        int qps = 0;
        if (checkQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability)) {
            qps = standardqps * 3 * nodes;
        }
        return qps;
    }

    private static int getClusterdisastertoleranceQps(int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
        int qps = 0;
        if (checkQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability)) {
            qps = 100000 * nodes;
        }
        return qps;
    }

    private static int getClusterSingleQps(int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
        int qps = 0;
        if (checkQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability)) {
            qps = standardqps * nodes;
        }
        return qps;

    }

    private static int getReadandwriteQps(int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
        int qps = 0;
        if (checkQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability)) {
            qps = 10 * maxconnection;
        }
        return qps;
    }

    private static int getReadandwriteenhanceQps(int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
        int qps = 0;
        if (checkQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability)) {
            qps = 10 * maxconnection;
        }
        return qps;
    }

    private static int getClusterreadandwriteQps(int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
        int qps = 0;
        if (checkQps(specification, nodes, connectionpernumber, maxconnection, maxBw, cpuability)) {
            qps = nodes * 200000;
        }
        return qps;
    }

    private static boolean checkQps(int specification, int nodes, int connectionpernumber, int maxconnection, long maxBw, double cpuability) {
//        if((specification>=8)&&(connectionpernumber/nodes>=10000)&&(maxBw/nodes>=24)&&(cpuability/nodes>=1)){
//            return true;
//        }
        if (connectionpernumber <= maxconnection) {
            return true;
        } else {
            return false;
        }
    }


}
