package datastructs.Map;

import java.util.Collection;

/**
 * Bare-bones definition of the {@link java.util.Map} interface.
 */
public interface Map<K,V> {
    void put(K key, V value);

    int size();

    boolean remove(K key);

    boolean containsKey(K key);

    V get(K key);

    boolean isEmpty();

    Collection<K> keySet();

    Collection<V> values();
}
