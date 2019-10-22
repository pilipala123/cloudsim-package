package org.yunji.cloudsimrd;

import java.util.List;

/**
 * @author weirenjie
 * @date 2019/10/22
 */

/**
 * 并发任务，包含若干个普通任务
 */
public class ConcurrencyLoads {
    /**
     * 包含的普通任务
     */
    private List<Load> loads;

    /**
     * 并发数
     */
    private int concurrencyNumber = 1;

    /**
     * 包含的普通任务数量
     */
    private int loadNumbers;

    public ConcurrencyLoads(List<Load> loads, int concurrencyNumber) {
        this.loads = loads;
        this.concurrencyNumber = concurrencyNumber;
        this.loadNumbers = loads.size();
    }

    public List<Load> getLoads() {
        return loads;
    }

    public void setLoads(List<Load> loads) {
        this.loads = loads;
    }

    public int getConcurrencyNumber() {
        return concurrencyNumber;
    }

    public void setConcurrencyNumber(int concurrencyNumber) {
        this.concurrencyNumber = concurrencyNumber;
    }

    public int getLoadNumbers() {
        return loadNumbers;
    }

    public void setLoadNumbers(int loadNumbers) {
        this.loadNumbers = loadNumbers;
    }
}
