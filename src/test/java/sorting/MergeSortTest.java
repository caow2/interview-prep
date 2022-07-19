package sorting;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MergeSortTest {

    MergeSort<Integer> sorter;

    @BeforeEach
    public void setup() {
        sorter = new MergeSort<>();
    }

    @Test
    public void testSort() {
        // Arrange
        Integer[] input = new Integer[] {3, 2, 1, 4};
        Integer[] expected = new Integer[] {1, 2, 3, 4};

        // Act
        sorter.sort(input);

        // Assert
        assertThat(input, is(expected));
    }

    @Test
    public void testSort_SortsDuplicates() {
        // Arrange
        Integer[] input = new Integer[] {1, 5, 5, 3, 5, 2, 3};
        Integer[] expected = new Integer[] {1, 2, 3, 3, 5, 5, 5};

        // Act
        sorter.sort(input);

        // Assert
        assertThat(input, is(expected));
    }

    @Test
    public void testSortHelper_OnlySortsWithinRange() {
        // Arrange
        Integer[] input = new Integer[] {5, 4, 3, 2, 1};
        Integer[] expected = new Integer[] {5, 2, 3, 4, 1};
        int left = 1, right = 3;

        // Act
        sorter.sort(input, left, right);

        // Assert
        assertThat(input, is(expected));
    }
}
