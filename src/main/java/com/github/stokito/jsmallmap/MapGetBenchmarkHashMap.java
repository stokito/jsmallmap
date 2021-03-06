package com.github.stokito.jsmallmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class MapGetBenchmarkHashMap {
    private static final Long KEY1 = 1L;
    private static final Long KEY2 = 2L;
    private static final Long KEY3 = 3L;
    private static final Long KEY4 = 4L;
    private static final Long KEY5 = 5L;

    private HashMap<Long, Long> map = new HashMap<Long, Long>();
    private HashMap<Long, Long> mapFiled = new HashMap<>();

    @Setup(Level.Trial)
    public void setup() {
        mapFiled.put(KEY1, KEY1);
        mapFiled.put(KEY2, KEY2);
        mapFiled.put(KEY3, KEY3);
        mapFiled.put(KEY4, KEY4);
        mapFiled.put(KEY5, KEY5);
    }

    /*
        private long key;

        @Setup(Level.Iteration)
        public void setup() {
            key = 0;
            for (long i = 0; i < 5; i++) {
                map.put(i, i);
            }
        }

        @Benchmark
        @BenchmarkMode(Mode.Throughput)
        @OutputTimeUnit(TimeUnit.MILLISECONDS)
        public Long testGetExistingKey() {
            if (key >= 5) key = 0;
            return map.get(key++);
        }
    */

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testPutGetRemove(Blackhole bh) {
        bh.consume(map.put(KEY1, KEY1));
        bh.consume(map.put(KEY2, KEY2));
        bh.consume(map.put(KEY3, KEY3));
        bh.consume(map.put(KEY4, KEY4));
        bh.consume(map.put(KEY5, KEY5));
        //get
        bh.consume(map.get(KEY1));
        bh.consume(map.get(KEY2));
        bh.consume(map.get(KEY3));
        bh.consume(map.get(KEY4));
        bh.consume(map.get(KEY5));
        // remove
        bh.consume(map.remove(KEY1));
        bh.consume(map.remove(KEY2));
        bh.consume(map.remove(KEY3));
        bh.consume(map.remove(KEY4));
        bh.consume(map.remove(KEY5));
        //get
        bh.consume(map.get(KEY1));
        bh.consume(map.get(KEY2));
        bh.consume(map.get(KEY3));
        bh.consume(map.get(KEY4));
        bh.consume(map.get(KEY5));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testSize() {
        return mapFiled.size();
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + MapGetBenchmarkHashMap.class.getSimpleName() + ".*")
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
