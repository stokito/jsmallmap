package com.github.stokito.jsmallmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class MapGetBenchmarkSmallMap {
    private SmallMap<Long, Long> map = new SmallMap<>();
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
    public Long testGetExistingKey() throws InterruptedException {
        if (key >= 5) key = 0;
        return map.get(key++);
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
