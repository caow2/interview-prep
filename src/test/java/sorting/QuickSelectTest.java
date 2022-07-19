package sorting;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class QuickSelectTest {
    QuickSelect<Integer> quickSelect;

    @BeforeEach
    public void before() {
        quickSelect = new QuickSelect(new Random());
    }

    @Test
    public void testQuickSelect() {
        // Arrange
        Integer[] input = new Integer[] {5, 1, 2, 6, 0};
        int k = 3, expected = 5;

        // Act
        Integer output = quickSelect.select(input, k);

        // Assert
        assertThat(output, is(expected));
    }

    @Test
    public void testQuickSelectWithDupes() {
        // Arrange
        Integer[] input = new Integer[] {5, 5, 5, 1, 2};
        int k = 1, expected = 2;

        // Act
        Integer output = quickSelect.select(input, k);

        // Assert
        assertThat(output, is(expected));
    }
}
