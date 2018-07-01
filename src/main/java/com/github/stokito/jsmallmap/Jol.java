package com.github.stokito.jsmallmap;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.out;

public class Jol {
    private static final Integer I_1 = 1;
    private static final Integer I_2 = 2;
    private static final Integer I_3 = 3;
    private static final Integer I_4 = 4;
    private static final Integer I_5 = 5;

    public static void main(String[] args) throws Exception {
        out.println(VM.current().details());

//JDK10         Map mapN = Map.of(I_1, I_1, I_2, I_2, I_3, I_3, I_4, I_4, I_5, I_5);
        Map mapN = new MapN(I_1, I_1, I_2, I_2, I_3, I_3, I_4, I_4, I_5, I_5);
        out.println(ClassLayout.parseInstance(mapN).toPrintable());
        out.println(GraphLayout.parseInstance(mapN).toFootprint());

        HashMap hashMap = new HashMap(5, 1.0F);
        put5Enries(hashMap);
        out.println(ClassLayout.parseInstance(hashMap).toPrintable());
        out.println(GraphLayout.parseInstance(hashMap).toFootprint());

        SmallMap smallMap = new SmallMap(mapN);
        put5Enries(hashMap);
        out.println(ClassLayout.parseInstance(smallMap).toPrintable());
        out.println(GraphLayout.parseInstance(smallMap).toFootprint());

        SmallMap2 smallMap2 = SmallMap2.of(I_1, I_1, I_2, I_2, I_3, I_3, I_4, I_4, I_5, I_5);
        out.println(ClassLayout.parseInstance(smallMap2).toPrintable());
        out.println(GraphLayout.parseInstance(smallMap2).toFootprint());

        LinkedHashMap linkedHashMap = new  LinkedHashMap(5, 1.0F);
        put5Enries(linkedHashMap);
        out.println(ClassLayout.parseInstance(linkedHashMap).toPrintable());
        out.println(GraphLayout.parseInstance(linkedHashMap).toFootprint());

        /*
         * In this example, we see that under collisions, HashMap
         * degrades to the linked list. With JDK 8, we can also see
         * it further "degrades" to the tree.
         */
       PrintWriter pw = new PrintWriter(System.out, true);

        Map<Dummy, Void> map = new HashMap<Dummy, Void>();

        map.put(new Dummy(1), null);
        map.put(new Dummy(2), null);

        System.gc();
        pw.println(GraphLayout.parseInstance(map).toPrintable());

        map.put(new Dummy(2), null);
        map.put(new Dummy(2), null);
        map.put(new Dummy(2), null);
        map.put(new Dummy(2), null);

        System.gc();
        pw.println(GraphLayout.parseInstance(map).toPrintable());

        for (int c = 0; c < 6; c++) {
            map.put(new Dummy(2), null);
        }
        pw.println(map.size());//=>12
        System.gc();
        pw.println(GraphLayout.parseInstance(map).toPrintable());

        pw.close();
    }

    private static void put5Enries(Map map) {
        map.put(I_1, I_1);
        map.put(I_2, I_2);
        map.put(I_3, I_3);
        map.put(I_4, I_4);
        map.put(I_5, I_5);
    }


    /**
     * Dummy class which controls the hashcode and is decently Comparable.
     */
    public static class Dummy implements Comparable<Dummy> {
        static int ID;
        final int id = ID++;
        final int hc;

        public Dummy(int hc) {
            this.hc = hc;
        }

        @Override
        public boolean equals(Object o) {
            return (this == o);
        }

        @Override
        public int hashCode() {
            return hc;
        }

        @Override
        public int compareTo(Dummy o) {
            return Integer.compare(id, o.id);
        }
    }
}