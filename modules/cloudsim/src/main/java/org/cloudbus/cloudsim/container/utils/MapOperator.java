package org.cloudbus.cloudsim.container.utils;

import com.google.common.collect.Maps;

import java.util.*;

public class MapOperator {

    /**
     * 使用 Map按value进行排序
     *
     * @param oriMap
     * @return
     */
    public static <T, F extends Comparable> Map<T, F> sortMapByValue(Map<T, F> oriMap) {
        Map<T, F> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<T, F>> entryList = new ArrayList<>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<T, F>> iter = entryList.iterator();
        Map.Entry<T, F> tmpEntry;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    static class MapValueComparator<T, F extends Comparable> implements Comparator<Map.Entry<T, F>> {

        @Override
        public int compare(Map.Entry<T, F> me1, Map.Entry<T, F> me2) {

            return me1.getValue().compareTo(me2.getValue());
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(2);
        map.put("test1", 1);
        map.put("test2", 2);
        map.put("test3", 1);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        map = sortMapByValue(map);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
