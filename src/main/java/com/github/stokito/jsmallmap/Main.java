package com.github.stokito.jsmallmap;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/*
hash map
2000000
Spent time: 3249.609999ms
Consumed mem: 503_633.734375kb

simple map
2000000
Spent time: 206.163396ms
Consumed mem: 125_952.5078125kb

simple map consumed 4 less memory and worked 16 times fast
*/
public class Main {
    private static final int CAPACITY = 2000000;

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000); // pause to connect via VisualVM
        ArrayList<Map> holder = new ArrayList<>(CAPACITY);
        Integer I1 = 1;
        Integer I2 = 2;
        Integer I3 = 3;
        Integer I4 = 4;
        Integer I5 = 5;
        System.gc();
        Runtime rt = Runtime.getRuntime();
        long freeMemoryBefore = rt.freeMemory();
        long totalMemoryBefore = rt.totalMemory();
        long consumedBefore = totalMemoryBefore - freeMemoryBefore;
        long start = System.nanoTime();
        for (int i = 0; i < CAPACITY; i++) {
//            HashMap<Integer, Integer> map = new HashMap<>(5, 1.0f);
            SmallMap<Integer, Integer> map = new SmallMap<>();
            holder.add(map);
            Objects.nonNull(map.put(I1, I1));
            Objects.nonNull(map.put(I2, I2));
            Objects.nonNull(map.put(I3, I3));
            Objects.nonNull(map.put(I4, I4));
            Objects.nonNull(map.put(I5, I5));
            Objects.nonNull(map.get(I1));
            Objects.nonNull(map.get(I2));
            Objects.nonNull(map.get(I3));
            Objects.nonNull(map.get(I4));
            Objects.nonNull(map.get(I5));
        }
        long end = System.nanoTime();
        long freeMemoryAfter = rt.freeMemory();
        long totalMemoryAfter = rt.totalMemory();
        long consumedAfter = totalMemoryAfter - freeMemoryAfter;
        Thread.sleep(5000);
        System.out.println(holder.size());
        long spentInNs = end - start;
        System.out.println("Spent time: " + spentInNs / 1000_000.0 + "ms");
        long totalConsumed = consumedAfter - consumedBefore;
        System.out.println("Consumed mem: " + totalConsumed / 1024.0 + "kb");
    }
}
