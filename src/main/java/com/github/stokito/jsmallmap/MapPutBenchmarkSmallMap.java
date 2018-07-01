package com.github.stokito.jsmallmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class MapPutBenchmarkSmallMap {
    private SmallMap<Long, Long> map = new SmallMap<Long, Long>();
    private long key;

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Long testPutNonExistingKeyKey() {
        if (key >= 5) {
            key = 0;
            map.clear();
        }
        Long oldVal = map.put(key, key);
        key++;
        return oldVal;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + MapPutBenchmarkSmallMap.class.getSimpleName() + ".*")
                .warmupIterations(1)
                .measurementIterations(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
