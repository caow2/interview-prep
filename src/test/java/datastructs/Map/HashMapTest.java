package datastructs.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashMapTest {
    HashMap<Integer, Integer> map;

    @BeforeEach
    public void setup() {
        map = HashMap.<Integer, Integer>builder().build();
    }

    @Test
    public void testPut_AddsEntryAndIncreasesSize_WhenEntryNotInMap() {
        // Arrange
        map.put(1, 2);
        int originalSize = map.size();

        // Act
        map.put(3, 4);

        // Assert
        assertThat(map.get(3), is(4));
        assertThat(map.size(), is(2));
        assertThat(map.size(), is(originalSize + 1));
    }

    @Test
    public void testPut_OverwritesEntry_WhenMapHasEntry() {
        // Arrange
        map.put(1, 2);
        int originalSize = map.size();

        // Act
        map.put(1, 3);

        // Assert
        assertThat(map.get(1), is(3));
        assertThat(map.size(), is(1));
        assertThat(map.size(), is(originalSize));
    }

    @Test
    public void testPut_ResizesMap_WhenAboveMaxCapacityThreshold() {
        // Arrange
        // Map defaults to the capacity of 16, and adding one element uses up 6.25% capacity (1/16)
        // So after adding a single element the capacity should double.
        map = HashMap.<Integer, Integer>builder()
                .maxCapacityThreshold(.001) // Set to some absurdly low value so each add should trigger a resize
                .build();
        int expectedCapacity = map.getCapacity() * 2;

        // Act
        map.put(1, 2);

        // Assert
        assertThat(map.getCapacity(), is(expectedCapacity));
    }

    @Test
    public void testRemove_ReturnsTrue_WhenKeyExists() {
        // Arrange
        map.put(5,10);
        map.put(3,6);
        int originalSize = 2;

        // Act
        boolean output = map.remove(3);

        // Assert
        assertThat(output, is(true));
        assertThat(map.size(), is(1));
        assertThat(map.size(), is(originalSize - 1));
    }

    @Test
    public void testGet_ReturnsValue_WhenKeyExists() {
        // Arrange
        map.put(10, 11);
        map.put(10, 15);

        // Act
        Integer output = map.get(10);

        // Assert
        assertThat(output, is(15));
    }

    @Test
    public void testGet_ReturnsNull_WhenKeyDoesNotExist() {
        // Arrange
        map.put(10, 11);
        map.put(10, 15);

        // Act
        Integer output = map.get(15);

        // Assert
        assertThat(output, is(nullValue()));
    }

    @Test
    public void testRemove_ReturnsFalse_WhenKeyDoesNotExist() {
        // Arrange
        map.put(3, 6);
        int originalSize = map.size();

        // Act
        boolean output = map.remove(1);

        // Assert
        assertThat(output, is(false));
        assertThat(map.size(), is(1));
        assertThat(map.size(), is(originalSize));
    }

    @Test
    public void testContainsKey_ReturnsFalse_WhenElementDoesNotExist() {
        // Act
        boolean output = map.containsKey(5);

        // Assert
        assertThat(output, is(false));
    }

    @Test
    public void testContainsKey_ReturnsTrue_WhenElementExists() {
        // Arrange
        map.put(5, 10);

        // Act
        boolean output = map.containsKey(5);

        // Assert
        assertThat(output, is(true));
    }

    @Test
    public void testKeySet_ReturnsAllKeysInMap() {
        // Arrange
        int elements = 5;
        for (int i = 1; i <= elements; i++) {
            map.put(i, i * 10);
        }
        Set<Integer> expected = new HashSet<>(List.of(1,2,3,4,5));

        // Act
        Set<Integer> output = map.keySet();

        // Assert
        assertThat(output.size(), is(expected.size()));
        assertThat(output, is(expected));
    }

    @Test
    public void testValues_ReturnsAllValuesInMap() {
        // Arrange
        int elements = 5;
        for (int i = 1; i <= elements; i++) {
            map.put(i, i * 10);
        }
        Set<Integer> expected = new HashSet<>(List.of(10, 20, 30, 40, 50));

        // Act
        Set<Integer> output = map.values();

        // Assert
        assertThat(output.size(), is(expected.size()));
        assertThat(output, is(expected));
    }

    @Test
    public void testSize_ReturnsZero_WhenMapIsEmpty() {
        // Arrange
        int expected = 0;

        // Act
        int output = map.size();

        // Assert
        assertThat(output, is(expected));
    }

    @Test
    public void testSize_ReturnsExpectedSize_WhenMapNotEmpty() {
        // Arrange
        int expected = 5;
        for (int i = 0; i < expected; i++) {
            map.put(i, i); // Actual contents don't matter
        }

        // Act
        int output = map.size();

        // Assert
        assertThat(output, is(expected));
    }

    @Test
    public void testIsEmpty_ReturnsTrue_WhenMapIsEmpty() {
        // Act
        boolean output = map.isEmpty();

        // Assert
        assertThat(output, is(true));
    }

    @Test
    public void testIsEmpty_ReturnsFalse_WhenMapNotEmpty() {
        // Arrange
        map.put(1, 2);

        // Act
        boolean output = map.isEmpty();

        // Assert
        assertThat(output, is(false));
    }
}
