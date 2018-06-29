package com.github.stokito.jsmallmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class MapPutAllBenchmarkSmallMap {
    private SmallMap<Long, Long> map = new SmallMap<Long, Long>();
    private SmallMap<Long, Long> otherMap = new SmallMap<Long, Long>();
    private TreeMap<Long, Long> otherMapClass = new TreeMap<Long, Long>();

    @Setup(Level.Iteration)
    public void setup() {
        for (long i = 0; i < 5; i++) {
            otherMap.put(i, i);
            otherMapClass.put(i, i);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public SmallMap testPutAll() {
        map.putAll(otherMap);
        return map;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public SmallMap testPutAllFromOtherMapClass() {
        map.putAll(otherMapClass);
        return map;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + MapPutAllBenchmarkSmallMap.class.getSimpleName() + ".*")
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
