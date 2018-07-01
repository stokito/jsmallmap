package com.github.stokito.jsmallmap;

import java.util.*;

import static java.util.Objects.requireNonNull;

public final class SmallMap2<K, V> implements Map<K, V> {
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

    public SmallMap2() {
    }

    public SmallMap2(Map<K, V> otherMap) {
        putAll(otherMap);
    }

    public static <K, V> SmallMap2<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        SmallMap2<K, V> map = new SmallMap2<>();
        map.key1 = requireNonNull(k1);
        map.val1 = requireNonNull(v1);
        map.key2 = requireNonNull(k2);
        map.val2 = requireNonNull(v2);
        map.key3 = requireNonNull(k3);
        map.val3 = requireNonNull(v3);
        map.key4 = requireNonNull(k4);
        map.val4 = requireNonNull(v4);
        map.key5 = requireNonNull(k5);
        map.val5 = requireNonNull(v5);
        return map;
    }

    public static <K, V> SmallMap2<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        SmallMap2<K, V> map = new SmallMap2<>();
        map.key1 = requireNonNull(k1);
        map.val1 = requireNonNull(v1);
        map.key2 = requireNonNull(k2);
        map.val2 = requireNonNull(v2);
        map.key3 = requireNonNull(k3);
        map.val3 = requireNonNull(v3);
        map.key4 = requireNonNull(k4);
        map.val4 = requireNonNull(v4);
        return map;
    }

    public static <K, V> SmallMap2<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        SmallMap2<K, V> map = new SmallMap2<>();
        map.key1 = requireNonNull(k1);
        map.val1 = requireNonNull(v1);
        map.key2 = requireNonNull(k2);
        map.val2 = requireNonNull(v2);
        map.key3 = requireNonNull(k3);
        map.val3 = requireNonNull(v3);
        return map;
    }

    public static <K, V> SmallMap2<K, V> of(K k1, V v1, K k2, V v2) {
        SmallMap2<K, V> map = new SmallMap2<>();
        map.key1 = requireNonNull(k1);
        map.val1 = requireNonNull(v1);
        map.key2 = requireNonNull(k2);
        map.val2 = requireNonNull(v2);
        return map;
    }

    // use singletonMap() or Map1 instead
    public static <K, V> SmallMap2<K, V> of(K k1, V v1) {
        SmallMap2<K, V> map = new SmallMap2<>();
        map.key1 = requireNonNull(k1);
        map.val1 = requireNonNull(v1);
        return map;
    }

    @Override
    public int size() {
        int size = 0;
        if (key1 == null) return size; //=>0
        size++;
        if (key2 == null) return size; //=>1
        size++;
        if (key3 == null) return size; //=>2
        size++;
        if (key4 == null) return size; //=>3
        size++;
        if (key5 == null) return size; //=>4
        size++;
        return size; //=>5
    }

    @Override
    public boolean isEmpty() {
        return key1 == null;
    }

    @Override
    public boolean containsKey(Object key) {
        return key != null && (key.equals(key1) || key.equals(key2) || key.equals(key3) || key.equals(key4) || key.equals(key5));
    }

    @Override
    public boolean containsValue(Object val) {
        return val != null && (val.equals(val1) || val.equals(val2) || val.equals(val3) || val.equals(val4) || val.equals(val5));
    }

    @Override
    public V get(Object key) {
        requireNonNull(key);
        if (key1 == null) return null;
        if (key.equals(key1)) return val1;
        if (key2 == null) return null;
        if (key.equals(key2)) return val2;
        if (key3 == null) return null;
        if (key.equals(key3)) return val3;
        if (key4 == null) return null;
        if (key.equals(key4)) return val4;
        if (key5 == null) return null;
        if (key.equals(key5)) return val5;
        return null;
    }

    @Override
    public V put(final K key, final V newVal) {
        requireNonNull((Object) key);
        if (newVal == null) return remove(key);
        if (key1 == null) {
            key1 = key;
            val1 = newVal;
            return null;
        }
        if (key.equals(key1)) {
            V oldVal = val1;
            val1 = newVal;
            return oldVal;
        }
        if (key2 == null) {
            key2 = key;
            val2 = newVal;
            return null;
        }
        if (key.equals(key2)) {
            V oldVal = val2;
            val2 = newVal;
            return oldVal;
        }
        if (key3 == null) {
            key3 = key;
            val3 = newVal;
            return null;
        }
        if (key.equals(key3)) {
            V oldVal = val3;
            val3 = newVal;
            return oldVal;
        }
        if (key4 == null) {
            key4 = key;
            val4 = newVal;
            return null;
        }
        if (key.equals(key4)) {
            V oldVal = val4;
            val4 = newVal;
            return oldVal;
        }
        if (key5 == null) {
            key5 = key;
            val5 = newVal;
            return null;
        }
        if (key.equals(key5)) {
            V oldVal = val5;
            val5 = newVal;
            return oldVal;
        }
        throw new IndexOutOfBoundsException("Too many entries");
    }

    @Override
    public V remove(Object key) {
        requireNonNull(key);
        if (key1 == null) return null;
        if (key.equals(key1)) {
            V oldVal = val1;
            deleteByIdx((byte) 0);
            return oldVal;
        }
        if (key2 == null) return null;
        if (key.equals(key2)) {
            V oldVal = val2;
            deleteByIdx((byte) 1);
            return oldVal;
        }
        if (key3 == null) return null;
        if (key.equals(key3)) {
            V oldVal = val3;
            deleteByIdx((byte) 2);
            return oldVal;
        }
        if (key4 == null) return null;
        if (key.equals(key4)) {
            V oldVal = val4;
            deleteByIdx((byte) 3);
            return oldVal;
        }
        if (key5 == null) return null;
        if (key.equals(key5)) {
            V oldVal = val5;
            deleteByIdx((byte) 4);
            return oldVal;
        }
        return null;

    }

    @Override
    public void putAll(Map<? extends K, ? extends V> otherMap) {
        if (otherMap == null) {
            throw new NullPointerException("otherMap shouldn't be null");
        }
        if (otherMap instanceof SmallMap2) {
            SmallMap2<? extends K, ? extends V> otherSmallMap = (SmallMap2<? extends K, ? extends V>) otherMap;
            this.key1 = otherSmallMap.key1;
            this.val1 = otherSmallMap.val1;
            this.key2 = otherSmallMap.key2;
            this.val2 = otherSmallMap.val2;
            this.key3 = otherSmallMap.key3;
            this.val3 = otherSmallMap.val3;
            this.key4 = otherSmallMap.key4;
            this.val4 = otherSmallMap.val4;
            this.key5 = otherSmallMap.key5;
            this.val5 = otherSmallMap.val5;
        } else {
            for (Entry<? extends K, ? extends V> entry: otherMap.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void clear() {
        this.key1 = null;
        this.val1 = null;
        this.key2 = null;
        this.val2 = null;
        this.key3 = null;
        this.val3 = null;
        this.key4 = null;
        this.val4 = null;
        this.key5 = null;
        this.val5 = null;
    }

    @Override
    public Set<K> keySet() {
        return new Keys();
    }

    @Override
    public Collection<V> values() {
        return new Values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new EntriesSet();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof Map))
            return false;
        Map<?, ?> m = (Map<?, ?>) other;
        if (m.size() != size())
            return false;
        // ok let's just use equals from HashMap :)
        return new HashMap<K, V>(this).equals(other);
    }

    @Override
    public int hashCode() {
        return new HashMap<K, V>(this).hashCode();
    }

    @Override
    public String toString() {
        //TODO: optimize
        StringBuilder mapAsStr = new StringBuilder(64);
        mapAsStr.append('[');
        for (Entry entry: entrySet()) {
            mapAsStr.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        int length = mapAsStr.length();
        mapAsStr.setCharAt(length - 2, ']');
        mapAsStr.setLength(length - 1);
        return mapAsStr.toString();
    }

    public V removeByIdx(byte idx) {
        V oldVal = valByIdx(idx);
        deleteByIdx(idx);
        return oldVal;
    }

    public void deleteByIdx(byte idx) {
        switch (idx) {
            case 0:
                val1 = val2;
            case 1:
                val2 = val3;
            case 2:
                val3 = val4;
            case 3:
                val4 = val5;
            case 4:
                val5 = null;
        }
    }

    public V valByIdx(byte idx) {
        switch (idx) {
            case 0:
                return val1;
            case 1:
                return val2;
            case 2:
                return val3;
            case 3:
                return val4;
            case 4:
                return val5;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    public K keyByIdx(byte idx) {
        switch (idx) {
            case 0:
                return key1;
            case 1:
                return key2;
            case 2:
                return key3;
            case 3:
                return key4;
            case 4:
                return key5;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    public ValsEntry<K, V> entryByIdx(byte idx) {
        switch (idx) {
            case 0:
                return new ValsEntry<>(key1, val1);
            case 1:
                return new ValsEntry<>(key2, val2);
            case 2:
                return new ValsEntry<>(key3, val3);
            case 3:
                return new ValsEntry<>(key4, val4);
            case 4:
                return new ValsEntry<>(key5, val5);
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    final class Values extends AbstractCollection<V> {
        @Override
        public Iterator<V> iterator() {
            return new ValuesIterator();
        }

        @Override
        public int size() {
            return SmallMap2.this.size();
        }
    }

    final class ValuesIterator implements Iterator<V> {
        private byte cursor;

        @Override
        public boolean hasNext() {
            return cursor < SmallMap2.this.size();
        }

        @Override
        public final V next() {
            return valByIdx(cursor++);
        }
    }

    final class Keys extends AbstractSet<K> {
        @Override
        public Iterator<K> iterator() {
            return new KeysIterator();
        }

        @Override
        public int size() {
            return SmallMap2.this.size();
        }
    }

    final class KeysIterator implements Iterator<K> {
        private byte cursor;

        @Override
        public boolean hasNext() {
            return cursor < SmallMap2.this.size();
        }

        @Override
        public final K next() {
            return keyByIdx(cursor++);
        }
    }

    final class EntriesSet extends AbstractSet<Entry<K, V>> {
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntriesIterator();
        }

        @Override
        public int size() {
            return SmallMap2.this.size();
        }
    }

    final class EntriesIterator implements Iterator<Entry<K, V>> {
        private byte cursor;

        @Override
        public boolean hasNext() {
            return cursor < SmallMap2.this.size();
        }

        @Override
        public final Entry<K, V> next() {
            return entryByIdx(cursor++);
        }
    }

    final static class ValsEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;

        ValsEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final String toString() {
            return key + "=" + value;
        }
    }
}
