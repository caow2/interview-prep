package datastructs.Map;

import lombok.Builder;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Array-based implementation of the {@link Map} interface using hashing. The underlying collision policy
 * uses separate chaining to address hashing collisions.
 *
 * The map resizes itself by a factor of 2 when one of the following happens:
 *  1. The number of elements exceeds some threshold with respect to the size of the underlying array. Defaults to
 *    .75 - that is, when the ratio of elements : array size exceeds 3/4, resize the array.
 *  2. (TBD) The max length of a chain exceeds some ratio w.r.t. the capacity of the hash table.
 * Each resize operation involves rehashing all the elements and is therefore a O(n) operation when n is the number of
 * elements in the map.
 */
public class HashMap<K,V> implements Map<K,V> {

    static final int DEFAULT_CAPACITY = 16;
    static final int DEFAULT_RESIZE_FACTOR = 2;
    static final double DEFAULT_CAPACITY_THRESHOLD = .75;

    /**
     * This stores all the chains for the map to address collisions.
     */
    private LinkedList<Entry<K,V>>[] table;

    /**
     * Size of the number of elements in the map.
     */
    private int size;

    /**
     * Threshold for resizing the table based on the {@link size} with respect to the length of the current table.
     * This is configured at initialize time and specifies that when
     * (number of elements in the table / length of the table) exceeds this threshold, the table should be resized.
     * This defaults to {@link DEFAULT_SIZE_THRESHOLD}, so when the map exceeds x% capacity, resize should happen to
     * avoid excessive collisions.
     */
    private double maxCapacityThreshold;

    @Builder
    public HashMap(int capacity, double maxCapacityThreshold) {
        // Capacity not passed in as part from Builder. This isn't a field, so we're checking this here.
        if (capacity <= 0) {
            capacity = DEFAULT_CAPACITY;
        }
        // Not sure of a way to set this by default through Builder.Default, so setting this here
        if (maxCapacityThreshold <= 0.0) {
            maxCapacityThreshold = DEFAULT_CAPACITY_THRESHOLD;
        }
        // Initialize the table
        this.table = generateTable(capacity);
        this.maxCapacityThreshold = maxCapacityThreshold;
        this.size = 0;
    }

    @Override
    public void put(@NonNull K key, @NonNull V value) {
        put(key, value, true);
    }

    /**
     * Helper function to determine if put operations should consider resizing. For normal put operations,
     * we should consider resizing but puts are also leveraged during the resize operation.
     *
     * During resizing, we should NOT consider resizing; this could lead to recursive resizes if the
     * {@link maxCapacityThreshold} is set too low.
     */
    private void put(@NonNull K key, @NonNull V value, boolean resize) {
        // Ensure the index will be bounded by [0, length - 1]
        int index = getIndex(key);
        // Since there's possibilities for collisions, scan the chain at the hashed index to find
        // the entry with the same key and update that. If it doesn't exist, we just add it to the list.
        LinkedList<Entry<K,V>> chain = table[index];
        boolean found = false;
        Iterator<Entry<K,V>> it = chain.iterator();
        while (it.hasNext() && !found) {
            Entry<K,V> entry = it.next();
            if (entry.getKey().equals(key)) {
                // Found an existing entry with the same key
                entry.setValue(value);
                found = true;
            }
        }

        // If we didn't find the entry above, add it to our map as a new entry
        if (!found) {
            chain.add(new Entry<>(key, value));
            size++;
        }

        // Determine if we need to resize
        if (resize && overCapacity()) {
            resize();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K,V>> chain = table[index];
        Iterator<Entry<K,V>> it = chain.iterator();
        boolean removed = false;

        while (it.hasNext() && !removed) {
            Entry<K,V> entry = it.next();
            if (entry.getKey().equals(key)) {
                it.remove();
                size--;
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K,V>> chain = table[index];
        Iterator<Entry<K,V>> it = chain.iterator();
        while (it.hasNext()) {
            Entry<K,V> entry = it.next();
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (LinkedList<Entry<K,V>> chain : table) {
            Set<K> chainKeys = chain.stream().map(Entry::getKey).collect(Collectors.toSet());
            keySet.addAll(chainKeys);
        }
        return keySet;
    }

    @Override
    public Set<V> values() {
        Set<V> values = new HashSet<>();
        for (LinkedList<Entry<K,V>> chain : table) {
            Set<V> chainValues = chain.stream().map(Entry::getValue).collect(Collectors.toSet());
            values.addAll(chainValues);
        }
        return values;
    }

    public int getCapacity() {
        return table.length;
    }

    private int getIndex(K key) {
        return key.hashCode() % table.length;
    }

    /**
     * Return whether the HashMap is over the configured maximum capacity threshold.
     */
    private boolean overCapacity() {
        return (size() / (double) table.length) > maxCapacityThreshold;
    }

    private void resize() {
        resize(DEFAULT_RESIZE_FACTOR);
    }

    /**
     * Create a table of with the specified capacity and initialize all the indices with empty chains.
     */
    private LinkedList<Entry<K,V>>[] generateTable(int capacity) {
        LinkedList<Entry<K,V>>[] table = (LinkedList<Entry<K,V>>[]) new LinkedList<?>[capacity];
        // Don't use Arrays.fill to replace this - fill uses the same reference to a LL for each index
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        return table;
    }

    /**
     * Resize the array backing the HashMap. The table length is increased by the given
     * resizeFactor and elements are rehashed to according to the
     * new table size.
     */
    private void resize(int resizeFactor) {
        // Expand the table by the given factor
        LinkedList<Entry<K,V>>[] oldTable = table;
        table = generateTable(oldTable.length * resizeFactor);

        // Copy all elements from old table to the expanded table and rehash the elements into the new table
        // without increasing the size, so reset size to 0 and let put increment size.
        size = 0;
        for (LinkedList<Entry<K,V>> chain : oldTable) {
            Iterator<Entry<K,V>> it = chain.iterator();
            while (it.hasNext()) {
                Entry<K,V> entry = it.next();
                this.put(entry.getKey(), entry.getValue(), false); // Adding to the new table, but don't resize
            }
        }
    }
}
