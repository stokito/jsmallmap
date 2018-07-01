# jsmallmap - POC of simplest as possible Java Map
Proof of Concept (POC, experiment) of implementing of fastest Map class for small amount of entries.
You can call it "SimpleMap" or "SmallMap" or "TupleMap" or "Vals" (see bellow why).

Java's standard `HashMap` is just great: it is fast and memory efficient.
It's so smart that on big elements number it internally switches to `TreeMap`. 
It's genius but not as this `SmallMap`. 

It works quite stupid: each key and each value are stored in... fields `key1`, `val1`, `key2`, `val2`... `key5`, `val5`:
```java
public final class SmallMap<K, V> implements Map<K, V> {
    private K key1;
    private V val1;
    private K key2;
    private V val2;
    private K key3;
    private V val3;
    private K key4;
    private V val4;
    private K key5;
    private V val5;

    public boolean containsKey(Object key) {
        return key.equals(key1) || key.equals(key2) || key.equals(key3) || key.equals(key4) || key.equals(key5);
    }
   
    ...
}    
```
Yes, it is possible to store only five entries in ten fields. And if you try to put more entries you'll get an exception (sic!).

Not so much to be honest, but in practice this is enough for most cases:
* When map is created to pass or return multiple values into a method i.e. the map is used as [tuple](https://docs.microsoft.com/en-us/dotnet/api/system.tuple?view=netframework-4.7.1).
* As imitation of object when you don't want to create a separate class. 
* To pass placeholder variables to formatters
* it's enough to store params of most HTTP requests
* Almost all unit tests uses few entries. 

The SmallMap is intended to be fast (but not *fastest*) but to have smallest memory consumption (about 56 bytes per instance).
You can say: "Who cares? HashMap is already fast and with reasonably small memory footprint. Today we have gibibytes of memory".
And you are partially right: we do have gibibytes of RAM but we have only about 256kb or processor cache. E.g. on my computer:  

    $ lscpu | grep "Cache"
    L1d Cache:                     32K
    L1i Cache:                     32K
    L2 Cache:                      256K
    L3 Cache:                      6144K

To understand how this is very important see [Latency Numbers Every Programmer Should Know](https://gist.github.com/jboner/2841832).
Today enterprise applications contains thousands of instances of hash map and it becomes a problem because it takes more time to collect a garbage. 
So a main idea is to make so small map so it can fit into CPU cache and all it's entries are tied together to be [prefetched](https://en.wikipedia.org/wiki/Cache_prefetching).
I expect that this can be especially useful in conjunction with [compact objects](http://objectlayout.github.io/ObjectLayout/) that are still not added to JDK :(

# Design principles
Here is some trade-off which method should be fast and which can be slow. Since the class will be used as tuple I chose the following priority: 
1. `put()` and `get()` should be fastest methods because are used the most: this is achieved.
2. `putAll()` and copy constructor: current implementation can be faster
3. iteration by entry set: current implementation is dramatically slow.
4. `toString()` but current implementation is too slow.
5. `equals()` and `hashCode()`
6. `size()` and `isEmpty()` current implementation is fastest as possible.
7. `remove()` is fast enough and `clean()` makes immediately (just set size = 0) but can cause memory leaks because it don't nulls fields and references are alive.
8. get keys set and values list: current implementation is too slow and returns inline class (can cause memory leak). Also I think to return just usual ArrayList.

# Benchmark result
It's hard to benchmark SimpleMap because it's overhead is too small to measure. Also it is hard to compare it with HashMap.
Usually maps are benchmarked on large amounts of entries while here we should do opposite.
Especially it's hard to imitate the main usage of SimpleMap: as tuple returned from methods.
I made a synthetic test to roughly estimate allocation of 2000000 maps in **old memory** with putting and getting 5 entries:

    hash map
    Spent time: 3249.609999ms
    Consumed mem: 503_633.734375kb

![Screenshot of VisualVM for HashMap](hashmap.png "Screenshot of VisualVM for HashMap")
    
    simple map
    Spent time: 119.958241ms
    Consumed mem: 115_456.5703125kb

![Screenshot of VisualVM for SmallMap](smallmap.png "Screenshot of VisualVM for SmallMap")

Simple map consumed 4 less memory and worked 16 times fast. And when I tried 4000000 entries HashMap test just died with OOM.
To reproduce it run Main class with -Xms2G -Xmx2G vm options.

Looks impressive but don't trust this results it doesn't count a lot of things. 
So here is a [JMH](http://openjdk.java.net/projects/code-tools/jmh/) benchmark. To compile and run it use:

    mvn clean isntall
    java -jar target/benchmarks.jar -gc true

Example on my machine:
```
$ java -jar target/benchmarks.jar -wi 1 -wf 1 -tu ms -i 1 -foe true -gc true

Benchmark                                               Mode   Cnt       Score      Error   Units
MapGetBenchmarkHashMap.testGetExistingKey               thrpt    5   96545.632 ± 4223.053  ops/ms
MapGetBenchmarkSmallMap.testGetExistingKey              thrpt    5  102397.779 ± 3472.132  ops/ms
MapPutBenchmarkHashMap.testPutNonExistingKey            thrpt    5   47219.787 ±  713.935  ops/ms
MapPutBenchmarkSmallMap.testPutNonExistingKey           thrpt    5   94985.959 ± 3306.885  ops/ms
```

`get()` works twice faster while `put()` is just slightly faster. Here maps was created in **eden (new) memory**.

Also due to some weird bug inside JMH when `instanceof` doesn't work the test for `putAll()` when it justs copies fields is not working properly.
Even if other map is SmallMap it will iterate and execute `put()` for each entry and this is much heavier.
But with some hacks I got some near to real data: 
```
Benchmark                     Mode   Cnt      Score       Error   Units
SmallMap.putAll() from other SimpleMap thrpt    5  98251.151 ±  4336.348  ops/ms
SmallMap.putAll() from other HashMap   thrpt    5  89048.174 ± 74724.720  ops/ms
```

# Memory footprint
The instance of `SmallMap` takes 56 bytes see [jol](http://openjdk.java.net/projects/code-tools/jol/) output: 
```
# Running 64-bit HotSpot VM.
# Using compressed oop with 0-bit shift.
# Using compressed klass with 3-bit shift.
# Objects are 8 bytes aligned.
# Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]

com.github.stokito.jsmallmap.SmallMap object internals:
 OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
      0    12                    (object header)                           N/A
     12     1               byte SmallMap.size                             N/A
     13     3                    (alignment/padding gap)                  
     16     4   java.lang.Object SmallMap.key1                             N/A
     20     4   java.lang.Object SmallMap.val1                             N/A
     24     4   java.lang.Object SmallMap.key2                             N/A
     28     4   java.lang.Object SmallMap.val2                             N/A
     32     4   java.lang.Object SmallMap.key3                             N/A
     36     4   java.lang.Object SmallMap.val3                             N/A
     40     4   java.lang.Object SmallMap.key4                             N/A
     44     4   java.lang.Object SmallMap.val4                             N/A
     48     4   java.lang.Object SmallMap.key5                             N/A
     52     4   java.lang.Object SmallMap.val5                             N/A
Instance size: 56 bytes
Space losses: 3 bytes internal + 0 bytes external = 3 bytes total
```
Note that `byte size` was padded to int but I still wan't to use byte just for feature test with compact objects.

From heap dump the size of instance SmallMap<Integer,Integer> is 97. 
 
The empty HashMap is smaller and consumes only 48 bytes but with five values it becomes much bigger.
From heap dump the size is 64 and retained: 372 i.e. more in 3 times than SmallMap.

MapN retained 228.
2000000
Spent time: 706.55321ms
Consumed mem: 277506.0kb  
 
# Vals: simple and universal collection
But what is more interesting for me is to try to create an universal class which can be used as Tuple as Map, as List and Set and call it Vals.
The similar conception is used in dynamic languages where object is just a map and you can use it as array.
Thus you can easily write everywhere something like: 
```java
// use as list
List<String> usernames = Vals.listOf("admin", "user", "guest"));
assert usernames.val1 == "admin"; 
assert usernames.val2 == "user"; 
assert usernames.val3 == "guest"; 
// use as set: duplicated removed on creation 
Set<String> goods = Vals.setOf("beer", "vodka", "beer"));
assert goods.val1 == "beer"; 
assert goods.val2 == "vodka"; 
assert goods.val3 == null;
// try to add duplicate
goods.add("beer");
assert goods.size() == 2; // still no dups
``` 

  
# Related works and discussions
* https://www.nayuki.io/page/compact-hash-map-java
* https://github.com/OpenHFT/SmoothieMap
* See [Which implementation of Map<K,V> should I use if my map needs to be small more than fast?](https://stackoverflow.com/questions/8835928/which-implementation-of-mapk-v-should-i-use-if-my-map-needs-to-be-small-more-t)
* https://github.com/Zteve/SmallCollections
* https://developer.android.com/reference/android/support/v4/util/ArrayMap
* https://github.com/austinv11/Long-Map-Benchmarks/blob/master/src/jmh/java/com/austinv11/bench/MapTests.java