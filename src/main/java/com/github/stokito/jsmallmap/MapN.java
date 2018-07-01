package com.github.stokito.jsmallmap;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * An array-based Map implementation. There is a single array "table" that
 * contains keys and values interleaved: table[0] is kA, table[1] is vA,
 * table[2] is kB, table[3] is vB, etc. The table size must be even. It must
 * also be strictly larger than the size (the number of key-value pairs contained
 * in the map) so that at least one null key is always present.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public final class MapN<K, V> extends AbstractMap<K, V> {
    /**
     * A "salt" value used for randomizing iteration order. This is initialized once
     * and stays constant for the lifetime of the JVM. It need not be truly random, but
     * it needs to vary sufficiently from one run to the next so that iteration order
     * will vary between JVM runs.
     */
    static final int SALT;

    static {
        long nt = System.nanoTime();
        SALT = (int) ((nt >>> 32) ^ nt);
    }

    /**
     * The reciprocal of load factor. Given a number of elements
     * to store, multiply by this factor to get the table size.
     */
    static final int EXPAND_FACTOR = 2;

    final Object[] table; // pairs of key, value
    final int size; // number of pairs

    MapN(Object... input) {
        if ((input.length & 1) != 0) { // implicit nullcheck of input
            throw new InternalError("length is odd");
        }
        size = input.length >> 1;

        int len = EXPAND_FACTOR * input.length;
        len = (len + 1) & ~1; // ensure table is even length
        table = new Object[len];

        for (int i = 0; i < input.length; i += 2) {
            @SuppressWarnings("unchecked")
            K k = Objects.requireNonNull((K) input[i]);
            @SuppressWarnings("unchecked")
            V v = Objects.requireNonNull((V) input[i + 1]);
            int idx = probe(k);
            if (idx >= 0) {
                throw new IllegalArgumentException("duplicate key: " + k);
            } else {
                int dest = -(idx + 1);
                table[dest] = k;
                table[dest + 1] = v;
            }
        }
    }

    @Override
    public boolean containsKey(Object o) {
        return probe(o) >= 0; // implicit nullcheck of o
    }

    @Override
    public boolean containsValue(Object o) {
        for (int i = 1; i < table.length; i += 2) {
            Object v = table[i];
            if (v != null && o.equals(v)) { // implicit nullcheck of o
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < table.length; i += 2) {
            Object k = table[i];
            if (k != null) {
                hash += k.hashCode() ^ table[i + 1].hashCode();
            }
        }
        return hash;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object o) {
        int i = probe(o);
        if (i >= 0) {
            return (V) table[i + 1];
        } else {
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<Entry<K, V>>() {
            @Override
            public int size() {
                return MapN.this.size;
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new Iterator<Map.Entry<K, V>>() {
                    int idx = 0;

                    @Override
                    public boolean hasNext() {
                        while (idx < table.length) {
                            if (table[idx] != null)
                                return true;
                            idx += 2;
                        }
                        return false;
                    }

                    @Override
                    public Map.Entry<K, V> next() {
                        if (hasNext()) {
                            @SuppressWarnings("unchecked")
                            Map.Entry<K, V> e =
                                    new KeyValueHolder<>((K) table[idx], (V) table[idx + 1]);
                            idx += 2;
                            return e;
                        } else {
                            throw new NoSuchElementException();
                        }
                    }
                };
            }
        };
    }

    // returns index at which the probe key is present; or if absent,
    // (-i - 1) where i is location where element should be inserted.
    // Callers are relying on this method to perform an implicit nullcheck
    // of pk.
    private int probe(Object pk) {
        int idx = Math.floorMod(pk.hashCode() ^ SALT, table.length >> 1) << 1;
        while (true) {
            @SuppressWarnings("unchecked")
            K ek = (K) table[idx];
            if (ek == null) {
                return -idx - 1;
            } else if (pk.equals(ek)) {
                return idx;
            } else if ((idx += 2) == table.length) {
                idx = 0;
            }
        }
    }
}