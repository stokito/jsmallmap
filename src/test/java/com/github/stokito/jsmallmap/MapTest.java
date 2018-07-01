package com.github.stokito.jsmallmap;

import junit.framework.TestCase;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * stolen from trove collection library, so it should be GNU licence for the test :)
 */
public class MapTest extends TestCase {

    protected Map<String, String> ss_map;
    protected Map<String, Integer> si_map;
    protected int count;

    //    private Class<? extends Map> mapClass = HashMap.class;
//    private Class<? extends Map> mapClass = SmallMap.class;
    private Class<? extends Map> mapClass = SmallMap2.class;

    public MapTest(String name) {
        super(name);
    }


    public void setUp() throws Exception {
        super.setUp();
        ss_map = newMapStrStr();
        si_map = newMapStrInt();
        count = 0;
    }

    public void tearDown() throws Exception {
        super.tearDown();
        ss_map = null;
        si_map = null;
        count = 0;
    }

    private Map<String, Integer> newMapStrInt() {
        try {
            return mapClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Integer, Integer> newMapIntInt() {
        try {
            return mapClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> newMapStrStr() {
        try {
            return mapClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> newMapStrStrCopy(Map<String, String> otherMap) {
        try {
            Constructor<?> ctor = mapClass.getConstructor(Map.class);
            Object object = ctor.newInstance(new Object[]{otherMap});
            return (Map<String, String>) object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void testConstructors() {
        String[] keys = {"Key1", "Key2", "Key3", "Key4", "Key5"};
        String[] values = {"Val1", "Val2", "Val3", "Val4", "Val5"};
        for (int i = 0; i < keys.length; i++) {
            ss_map.put(keys[i], values[i]);
        }

/*
        Map<String, String> sized = new HashMap<String, String>( 100 );
        for ( int i = 0; i < keys.length; i++ ) {
            sized.put( keys[i], values[i] );
        }
        assertTrue( "maps should be a copy of each other", ss_map.equals( sized ) );


        Map<String, String> factor = new HashMap<String, String>( 100, 1.0f );
        for ( int i = 0; i < keys.length; i++ ) {
            factor.put( keys[i], values[i] );
        }
        assertTrue( "maps should be a copy of each other", ss_map.equals( factor ) );
*/


        Map<String, String> copy = newMapStrStrCopy(ss_map);
        assertTrue("maps should be a copy of each other", ss_map.equals(copy));

        for (int i = 0; i < keys.length; i++) {
            assertEquals(values[i], copy.get(keys[i]));
        }

        Map<String, String> java_hashmap = newMapStrStr();
        for (int i = 0; i < keys.length; i++) {
            java_hashmap.put(keys[i], values[i]);
        }
        Map<String, String> java_hashmap_copy =
                newMapStrStrCopy(java_hashmap);
        assertTrue("maps should be a copy of each other",
                ss_map.equals(java_hashmap_copy));

    }

    public void testEquals() {
        String[] keys = {"Key1", "Key2", "Key3", "Key4"};
        String[] values = {"Val1", "Val2", "Val3", "Val4"};
        for (int i = 0; i < keys.length; i++) {
            ss_map.put(keys[i], values[i]);
        }

        assertFalse("should not equal random Object", ss_map.equals(new Object()));

        Map<String, String> copy = newMapStrStrCopy(ss_map);
        assertTrue("maps should be a copy of each other", ss_map.equals(copy));

        // Change the Length.
        copy.put("Key5", "Val5");
        assertFalse("maps should no longer be a copy of each other", ss_map.equals(copy));
    }


    public void testPut() throws Exception {
        assertEquals("put succeeded", null, ss_map.put("One", "two"));
        assertEquals("size did not reflect put", 1, ss_map.size());
        assertEquals("put/get failed", "two", ss_map.get("One"));
        assertEquals("second put failed", "two", ss_map.put("One", "foo"));
    }


    public void testPutIfAbsent() throws Exception {
        assertEquals("putIfAbsent succeeded", null, ss_map.putIfAbsent("One", "two"));
        assertEquals("size did not reflect putIfAbsent", 1, ss_map.size());
        assertEquals("putIfAbsent/get failed", "two", ss_map.get("One"));
        assertEquals("second putIfAbsent failed", "two", ss_map.putIfAbsent("One", "foo"));
        assertEquals("size did not reflect putIfAbsent", 1, ss_map.size());
        assertEquals("putIfAbsent/get failed", "two", ss_map.get("One"));
        assertEquals("third putIfAbsent failed", null, ss_map.putIfAbsent("Two", "bar"));
        assertEquals("size did not reflect putIfAbsent", 2, ss_map.size());
        assertEquals("putIfAbsent/get failed", "bar", ss_map.get("Two"));
    }


    public void testClear() throws Exception {
        assertEquals("initial size was not zero", 0, ss_map.size());
        assertEquals("put succeeded", null, ss_map.put("One", "two"));
        assertEquals("size did not reflect put", 1, ss_map.size());
        ss_map.clear();
        assertEquals("cleared size was not zero", 0, ss_map.size());
    }


    public void testContains() throws Exception {
        String key = "hi";
        assertTrue("should not contain key initially", !si_map.containsKey(key));
        assertEquals("put succeeded", null, si_map.put(key, Integer.valueOf(1)));
        assertTrue("key not found after put", si_map.containsKey(key));
        assertFalse("non-existant key found", si_map.containsKey("bye"));
    }


    public void testContainsKey() throws Exception {
        String key = "hi";
        assertTrue("should not contain key initially", !si_map.containsKey(key));
        assertEquals("put succeeded", null, si_map.put(key, Integer.valueOf(1)));
        assertTrue("key not found after put", si_map.containsKey(key));
        assertFalse("non-existant key found", si_map.containsKey("bye"));
    }


    public void testContainsValue() throws Exception {
        String key = "hi";
        String value = "bye";
        assertTrue("should not contain key initially", !ss_map.containsValue(value));
        assertEquals("put succeeded", null, ss_map.put(key, value));
        assertTrue("key not found after put", ss_map.containsValue(value));
        assertFalse("non-existant key found", ss_map.containsValue("whee"));
    }


    @SuppressWarnings({"SuspiciousMethodCalls"})
    public void testGet() throws Exception {
        String key = "hi", val = "one", val2 = "two";

        ss_map.put(key, val);
        assertEquals("get did not return expected value", val, ss_map.get(key));
        ss_map.put(key, val2);
        assertEquals("get did not return expected value on second put",
                val2, ss_map.get(key));

        // Invalid key should return null
        assertNull(ss_map.get(new Object()));
    }


    public void testValues() throws Exception {
        String k1 = "1", k2 = "2", k3 = "3", k4 = "4", k5 = "5";
        String v1 = "x", v2 = "y", v3 = "z";

        ss_map.put(k1, v1);
        ss_map.put(k2, v1);
        ss_map.put(k3, v2);
        ss_map.put(k4, v3);
        ss_map.put(k5, v2);
        Collection vals = ss_map.values();
        assertEquals("size was not 5", 5, vals.size());
//        vals.remove( "z" );
//        assertEquals( "size was not 4", 4, vals.size() );
//        vals.remove( "y" );
//        assertEquals( "size was not 3", 3, vals.size() );
//        vals.remove( "y" );
//        assertEquals( "size was not 2", 2, vals.size() );
//        assertEquals( "map did not diminish to 2 entries", 2, ss_map.size() );
    }


    @SuppressWarnings({"WhileLoopReplaceableByForEach"})
    public void testKeySet() throws Exception {
        String key1 = "hi", key2 = "bye", key3 = "whatever";
        String val = "x";

        ss_map.put(key1, val);
        ss_map.put(key2, val);
        ss_map.put(key3, val);

        Set keys = ss_map.keySet();
        assertTrue("keyset did not match expected set",
                keys.containsAll(Arrays.asList(key1, key2, key3)));
        assertEquals(3, ss_map.size());

        int count = 0;
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            count++;
            it.next();
        }
        assertEquals(ss_map.size(), count);

/*
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            Object o = i.next();
            if (o.equals(key2)) {
                i.remove();
            }
        }
        assertTrue("keyset did not match expected set",
                keys.containsAll(Arrays.asList(key1, key3)));
*/
    }


    @SuppressWarnings({"ForLoopReplaceableByForEach"})
    public void testKeyIterator() throws Exception {
        String[] keys = {"one", "two", "three", "four"};
        Integer[] vals = {new Integer(1),
                new Integer(2),
                new Integer(3),
                new Integer(4)
        };
        for (int i = 0; i < keys.length; i++) {
            si_map.put(keys[i], vals[i]);
        }

        int count = 0;
        for (Iterator i = si_map.keySet().iterator(); i.hasNext(); ) {
            i.next();
            count++;
        }
        assertEquals(4, count);
    }


    public void testContainsNullValue() throws Exception {
        si_map.put("a", null);
        assertFalse(si_map.containsValue(null));
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public void testEntrySetRemoveSameKeyDifferentValues() throws Exception {
        ss_map.put("0", "abc");
        si_map.put("0", Integer.valueOf(123));
        Map.Entry<String, String> ee = ss_map.entrySet().iterator().next();
        assertEquals(1, si_map.size());
        assertTrue(!si_map.entrySet().contains(ee));
        assertTrue(!si_map.entrySet().remove(ee));
    }


    public void testSizeAfterMultipleReplacingPuts() throws Exception {
        ss_map.put("key", "a");
        assertEquals(1, ss_map.size());
        ss_map.put("key", "b");
        assertEquals(1, ss_map.size());
    }


/*
   @SuppressWarnings({"unchecked"})
   public void testSerializable() throws Exception {
        // Use a non-standard load factor to more fully test serialization
        Map<String, String> map = newMapStrStr();
        map.put("a", "b");
        map.put("b", "c");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(map);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);

        Map<String, String> deserialized = mapClass.cast(ois.readObject());

        assertEquals(map, deserialized);
    }*/


    public void testPutAll() throws Exception {
        Map<String, String> otherMap = newMapStrStr();
        doTestPutAll(otherMap);
    }

    public void testPutAllFromOtherMapClass() throws Exception {
        Map<String, String> otherMap = new TreeMap<>();
        doTestPutAll(otherMap);
    }

    public void testPutAllOverrideValue() throws Exception {
        ss_map.put("key1", "oldVal");
        ss_map.put("key2", "oldVal");
        ss_map.put("key3", "oldVal");
        ss_map.put("key4", "oldVal");
        ss_map.put("key5", "oldVal");
        Map<String, String> otherMap = newMapStrStr();
        doTestPutAll(otherMap);
    }

    public void testPutAllOverrideValueFromOtherMapClass() throws Exception {
        ss_map.put("key1", "oldVal");
        ss_map.put("key2", "oldVal");
        ss_map.put("key3", "oldVal");
        ss_map.put("key4", "oldVal");
        ss_map.put("key5", "oldVal");
        Map<String, String> otherMap = new TreeMap<>();
        doTestPutAll(otherMap);
    }

    private void doTestPutAll(Map<String, String> otherMap) {
        otherMap.put("key1", "newVal");
        otherMap.put("key2", "newVal");
        otherMap.put("key3", "newVal");
        otherMap.put("key4", "newVal");
        otherMap.put("key5", "newVal");
        ss_map.putAll(otherMap);
        assertEquals("newVal", ss_map.get("key1"));
        assertEquals("newVal", ss_map.get("key2"));
        assertEquals("newVal", ss_map.get("key3"));
        assertEquals("newVal", ss_map.get("key4"));
        assertEquals("newVal", ss_map.get("key5"));
        assertEquals(5, ss_map.size());
    }

    @SuppressWarnings({"RedundantStringConstructorCall"})
    public void testHashCode() throws Exception {
        Map<String, String> ss_map2 = newMapStrStr();
        ss_map.put(new String("foo"), new String("bar"));
        ss_map2.put(new String("foo"), new String("bar"));
        assertEquals(ss_map.hashCode(), ss_map2.hashCode());
        assertEquals(ss_map, ss_map2);
        ss_map2.put(new String("cruft"), new String("bar"));
        assertTrue(ss_map.hashCode() != ss_map2.hashCode());
        assertTrue(!ss_map.equals(ss_map2));
    }

    public void testKeySetEqualsEquivalentSet() {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("doh");
        set.add("hal");

        Map<String, String> tv1 = newMapStrStr();
        tv1.put("doh", "blah");
        tv1.put("foo", "blah");
        tv1.put("hal", "notblah");
        assertTrue(tv1.keySet().equals(set));
    }


    public void testNullValueSize() {
        ss_map.put("narf", null);
        ss_map.put("narf", null);
        assertEquals(0, ss_map.size());
    }


    public void testEqualsAndHashCode() {
        Map<String, String> map1 = newMapStrStr();
        map1.put("Key1", null);

        Map<String, String> map2 = newMapStrStr();
        map2.put("Key2", "Value2");

        assertFalse("map1.equals( map2 )", map1.equals(map2));
        assertFalse("map2.equals( map1 )", map2.equals(map1));

        Map<String, String> clone_map1 = newMapStrStrCopy(map1);
        Map<String, String> clone_map2 = newMapStrStrCopy(map2);

        assertEquals(map1, clone_map1);
        assertEquals(map1.hashCode(), clone_map1.hashCode());
        assertEquals(map2, clone_map2);
        assertEquals(map2.hashCode(), clone_map2.hashCode());
    }

    public void testIterable() {
        Map<String, Integer> m = newMapStrInt();
        m.put("One", Integer.valueOf(1));
        m.put("Two", Integer.valueOf(2));

        for (String s: m.keySet()) {
            assertTrue(s.equals("One") || s.equals("Two"));
        }

        for (Integer i: m.values()) {
            assertTrue(i.intValue() == 1 || i.intValue() == 2);
        }
    }


    public void testKeysFunctions() {
        int element_count = 5;
        String[] keys = new String[element_count];
        String[] vals = new String[element_count];
        for (int i = 0; i < element_count; i++) {
            keys[i] = "Key" + i;
            vals[i] = "Vals" + i;
            ss_map.put(keys[i], vals[i]);
        }
        assertEquals(element_count, ss_map.size());

        Collection<String> keys_set = ss_map.keySet();
        assertTrue("should contain " + keys[3] + ", " + ss_map, keys_set.contains(keys[3]));

//        assertFalse("invalid remove succeeded " + keys_set, keys_set.remove("non-existant"));
//        assertTrue("remove failed: " + keys_set, keys_set.remove(keys[3]));
//        assertFalse("key set contains removed item", keys_set.contains(keys[3]));
//        assertFalse("map contains removed item" + ss_map, ss_map.containsKey(keys[3]));
//        assertFalse("map contains removed item" + ss_map, ss_map.containsKey(keys[3]));

//        assertFalse("cannot remove item not in set", keys_set.remove("non-existant"));
    }


    public void testValuesFunctions() {
        int element_count = 5;
        String[] vals = new String[element_count];
        for (int i = 0; i < element_count; i++) {
            vals[i] = "Val" + i;
            ss_map.put("Key" + i, vals[i]);
        }
        assertEquals(element_count, ss_map.size());

        Collection<String> values_set = ss_map.values();
        assertTrue("should contain " + vals[3] + ", " + ss_map, values_set.contains(vals[3]));

        Set<String> set = new HashSet<String>();
        for (int i = 0; i < element_count; i++) {
            vals[i] = "Val" + i;
            set.add(vals[i]);
        }
        assertTrue("should contain all: " + values_set + ", " + set, values_set.containsAll(set));
        set.add("cause failure");
        assertFalse("shouldn't contain all: " + values_set, values_set.containsAll(set));

/*        values_set.clear();
        assertEquals("values set size should be 0", 0, values_set.size());
        assertEquals("map size should be 0", 0, ss_map.size());
        assertTrue(values_set.isEmpty());

        try {
            values_set.add("fail");
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // Expected
        }

        try {
            values_set.addAll(Arrays.asList("fail"));
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
            // Expected
        }*/
    }


    @SuppressWarnings({"ToArrayCallWithZeroLengthArrayArgument"})
    public void testValuesToArray() {
        int element_count = 5;
        String[] vals = new String[element_count];
        for (int i = 0; i < element_count; i++) {
            vals[i] = "Val" + i;
            ss_map.put("Key" + i, vals[i]);
        }

        Collection<String> values_set = ss_map.values();
        String[] toarray = values_set.toArray(new String[0]);
        Arrays.sort(toarray);
        assertEquals(Arrays.asList(vals), Arrays.asList(toarray));

        toarray = values_set.toArray(new String[element_count]);
        Arrays.sort(toarray);
        assertEquals(Arrays.asList(vals), Arrays.asList(toarray));

        toarray = values_set.toArray(new String[element_count + 1]);
        assertEquals(null, toarray[element_count]);
        Arrays.sort(toarray, 0, element_count);
        for (int i = 0; i < element_count; i++) {
            assertEquals(vals[i], toarray[i]);
        }
    }


    public void testIteratorFunctions() throws Exception {
        int element_count = 5;
        String[] keys = new String[element_count];
        String[] vals = new String[element_count];
        for (int i = 0; i < element_count; i++) {
            keys[i] = "Key" + i;
            vals[i] = "Val" + i;
            ss_map.put(keys[i], vals[i]);
        }

        Iterator<Map.Entry<String, String>> iter = ss_map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> ee = iter.next();
            assertTrue(Arrays.asList(keys).contains(ee.getKey()));
            assertTrue(Arrays.asList(vals).contains(ee.getValue()));
            count++;
        }

        iter = ss_map.entrySet().iterator();
        assertTrue(iter.hasNext());
        Map.Entry<String, String> ee = iter.next();
//        assertTrue( "remove on entrySet() returned false",
//                ss_map.entrySet().remove( ee ) );
//
//        assertEquals( element_count - 1, ss_map.size() );
    }


    @SuppressWarnings({"unchecked"})
    public void testEntrySetFunctions() {
        int element_count = 5;
        String[] keys = new String[element_count];
        String[] vals = new String[element_count];
        for (int i = 0; i < element_count; i++) {
            keys[i] = "Key" + i;
            vals[i] = "Val" + i;
            ss_map.put(keys[i], vals[i]);
        }

        Iterator<Map.Entry<String, String>> iter = ss_map.entrySet().iterator();
        assertTrue(iter.hasNext());
        Map.Entry<String, String> ee = iter.next();

        assertNotNull(ee.getKey());
        assertNotNull(ee.getValue());
        assertNotNull(ee.getValue());
        assertNotNull(ee.getValue());

/*
        String new_value = "New Value";
        String old_value = ee.getValue();
        assertEquals( old_value, ee.setValue( new_value ) );

        assertFalse( old_value.equals( ee.getValue() ) );
        assertFalse( ss_map.values().contains( old_value ) );
        assertTrue( ss_map.values().contains( new_value ) );
        assertTrue( ss_map.containsValue( new_value ) );

        assertFalse( "equal vs. random object incorrect", ee.equals( new Object() ) );
*/
    }

    public void testToString() {
        ss_map.put("One", "1");
        ss_map.put("Two", "2");

        String to_string = ss_map.toString();
        assertEquals("[One=1, Two=2]", to_string);
    }


    public void testEntrySetToString() {
        Map<String, String> map = newMapStrStr();
        map.put("One", "1");
        map.put("Two", "2");

        String to_string = map.entrySet().toString();
        assertTrue(to_string,
                to_string.equals("[One=1, Two=2]") || to_string.equals("[Two=2, One=1]"));
    }

    public void testKeySetToString() {
        Map<String, String> map = newMapStrStr();
        map.put("One", "1");
        map.put("Two", "2");

        String to_string = map.keySet().toString();
        assertTrue(to_string,
                to_string.equals("[One, Two]") || to_string.equals("[Two, One]"));
    }

    public void testValuesToString() {
        Map<String, String> map = newMapStrStr();
        map.put("One", "1");
        map.put("Two", "2");

        String to_string = map.values().toString();
        assertTrue(to_string,
                to_string.equals("[1, 2]") || to_string.equals("[2, 1]"));
    }


    // Test for issue 3159432
    public void testEntrySetRemove() {
        Map<String, String> map = newMapStrStr();
        map.put("ONE", "1");
        map.put("TWO", "2");
        map.put("THREE", "3");

        Set<Map.Entry<String, String>> set = map.entrySet();
        set.remove(null);

        //noinspection SuspiciousMethodCalls
        set.remove("Blargh");
    }
}