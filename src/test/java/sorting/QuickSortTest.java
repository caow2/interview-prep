package sorting;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class QuickSortTest {

    QuickSort<Integer> sorter;

    Random spyRandom;

    @BeforeEach
    public void setup() {
        // See https://github.com/mockito/mockito/issues/2560 for why withoutAnnotations() is necessary
        spyRandom = mock(Random.class, withSettings()
                .withoutAnnotations()
                .spiedInstance(new Random()));
        sorter = new QuickSort<>(spyRandom);
    }

    @Test
    public void testSort() {
        // Arrange
        Integer[] input = new Integer[] {1, 4, 3, 9, 5};
        Integer[] expected = new Integer[] {1, 3, 4, 5, 9};

        // Act
        sorter.sort(input);

        // Assert
        assertThat(input, is(expected));
    }

    @Test
    public void testSort_WithDuplicates() {
        // Arrange
        Integer[] input = new Integer[] {1, 9, 1, 5, 3, 3};
        Integer[] expected = new Integer[] {1, 1, 3, 3, 5, 9};

        // Act
        sorter.sort(input);

        // Assert
        assertThat(input, is(expected));
    }

    @Test
    public void testPartition() {
        // Arrange
        Integer[] input = new Integer[] {2, 3, 1, 6, 5, 2};
        Integer[] expected = new Integer[] {2, 1, 2, 3, 5, 6};
        int pivotIndex = 1;
        doReturn(pivotIndex).when(spyRandom).nextInt(anyInt());

        // Act
        sorter.partition(input, 0, input.length - 1);

        // Assert
        assertThat(input, is(expected));
    }
}
