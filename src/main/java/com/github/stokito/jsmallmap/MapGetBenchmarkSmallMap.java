package com.github.stokito.jsmallmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/*
Benchmark                                          Mode  Cnt       Score       Error   Units
MapGetBenchmarkSmallMap.testGetExistingKey        thrpt    5  102225.117 ± 17440.059  ops/ms
MapGetBenchmarkSmallMap.testGetIfElseExistingKey  thrpt    5   99728.603 ± 17004.486  ops/ms

MapGetBenchmarkSmallMap.testGetExistingKey        thrpt    5  103430.681 ± 11367.171  ops/ms
MapGetBenchmarkSmallMap.testGetIfElseExistingKey  thrpt    5  104078.974 ±  3145.631  ops/ms

Benchmark                                             Mode  Cnt      Score      Error   Units
MapGetBenchmarkSmallMap.testGetExistingKey           thrpt   25  40525.213 ± 1699.387  ops/ms
MapGetBenchmarkSmallMap.testGetIfElseExistingKeyAsc  thrpt   25  55765.715 ± 5859.984  ops/ms

Benchmark                                             Mode  Cnt      Score     Error   Units
MapGetBenchmarkSmallMap.testGetExistingKey           thrpt   25  41319.052 ± 438.658  ops/ms
MapGetBenchmarkSmallMap.testGetIfElseExistingKeyAsc  thrpt   25  59484.808 ± 477.865  ops/ms

MapGetBenchmarkSmallMap.testGetExistingKey           thrpt   25  13981.236 ± 2808.944  ops/ms
MapGetBenchmarkSmallMap.testGetExistingKeyNull       thrpt   25  14607.789 ± 2503.173  ops/ms
MapGetBenchmarkSmallMap.testGetIfElseExistingKeyAsc  thrpt   25  14394.323 ± 3108.622  ops/ms


 */
@State(Scope.Thread)
public class MapGetBenchmarkSmallMap {
    private static final Long KEY1 = 1L;
    private static final Long KEY2 = 2L;
    private static final Long KEY3 = 3L;
    private static final Long KEY4 = 4L;
    private static final Long KEY5 = 5L;
    private SmallMap<Long, Long> map = new SmallMap<>();
    private SmallMap<Long, Long> mapFiled = new SmallMap<>();

    @Setup(Level.Trial)
    public void setup() {
        mapFiled.put(KEY1, KEY1);
        mapFiled.put(KEY2, KEY2);
        mapFiled.put(KEY3, KEY3);
        mapFiled.put(KEY4, KEY4);
        mapFiled.put(KEY5, KEY5);
    }

/*    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testGetIfElseExistingKeyAsc(Blackhole bh) {
        bh.consume(mapFiled.getIfElseAsceding(KEY1));
        bh.consume(mapFiled.getIfElseAsceding(KEY2));
        bh.consume(mapFiled.getIfElseAsceding(KEY3));
        bh.consume(mapFiled.getIfElseAsceding(KEY4));
        bh.consume(mapFiled.getIfElseAsceding(KEY5));
        //repeat in desc
        bh.consume(mapFiled.getIfElseAsceding(KEY5));
        bh.consume(mapFiled.getIfElseAsceding(KEY4));
        bh.consume(mapFiled.getIfElseAsceding(KEY3));
        bh.consume(mapFiled.getIfElseAsceding(KEY2));
        bh.consume(mapFiled.getIfElseAsceding(KEY1));
    }*/

/*    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testGetExistingKeyNull(Blackhole bh) {
        bh.consume(mapFiled.getIfNull(KEY1));
        bh.consume(mapFiled.getIfNull(KEY2));
        bh.consume(mapFiled.getIfNull(KEY3));
        bh.consume(mapFiled.getIfNull(KEY4));
        bh.consume(mapFiled.getIfNull(KEY5));
        //repeat in desc
        bh.consume(mapFiled.getIfNull(KEY5));
        bh.consume(mapFiled.getIfNull(KEY4));
        bh.consume(mapFiled.getIfNull(KEY3));
        bh.consume(mapFiled.getIfNull(KEY2));
        bh.consume(mapFiled.getIfNull(KEY1));
    }*/

/*    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testGetExistingKey(Blackhole bh) {
        bh.consume(mapFiled.get(KEY1));
        bh.consume(mapFiled.get(KEY2));
        bh.consume(mapFiled.get(KEY3));
        bh.consume(mapFiled.get(KEY4));
        bh.consume(mapFiled.get(KEY5));
        //repeat in desc
        bh.consume(mapFiled.get(KEY5));
        bh.consume(mapFiled.get(KEY4));
        bh.consume(mapFiled.get(KEY3));
        bh.consume(mapFiled.get(KEY2));
        bh.consume(mapFiled.get(KEY1));
    }*/

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
                .include(".*" + MapGetBenchmarkSmallMap.class.getSimpleName() + ".*")
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
