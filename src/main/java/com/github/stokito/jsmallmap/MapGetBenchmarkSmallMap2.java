package com.github.stokito.jsmallmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/*
PutGetRemove 6807.701 against 4913.280 of HM i.e. only 1.385571553 times faster
get size from field: 316332.373 ops/ms while calculate it  232730.051 i.e. in 1.359224439 times slowly than get size field
 */
@State(Scope.Thread)
public class MapGetBenchmarkSmallMap2 {
    private static final Long KEY1 = 1L;
    private static final Long KEY2 = 2L;
    private static final Long KEY3 = 3L;
    private static final Long KEY4 = 4L;
    private static final Long KEY5 = 5L;
    private SmallMap2<Long, Long> map = new SmallMap2<>();
    private SmallMap2<Long, Long> mapFiled = new SmallMap2<>();

    @Setup(Level.Trial)
    public void setup() {
        mapFiled.put(KEY1, KEY1);
        mapFiled.put(KEY2, KEY2);
        mapFiled.put(KEY3, KEY3);
        mapFiled.put(KEY4, KEY4);
        mapFiled.put(KEY5, KEY5);
    }

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
                .include(".*" + MapGetBenchmarkSmallMap2.class.getSimpleName() + ".*")
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
