package datastructs.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Simple POJO to store a key and a value.
 */
@AllArgsConstructor
@Getter
public class Entry<K, V> {
    private K key;

    @Setter
    private V value;
}
