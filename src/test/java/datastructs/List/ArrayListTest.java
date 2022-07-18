package datastructs.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayListTest {

    // Object under test
    ArrayList<Integer> list;

    @BeforeEach
    public void setup() {
        list = new ArrayList<>();
    }

    @Test
    public void testAdd_IncrementsSize() {
        // Act
        list.add(5);

        // Assert
        assertThat(list.size(), is(1));
        assertThat(list.get(0), is(5));
    }

    @Test
    public void testAdd_AppendsToEndOfList() {
        // Arrange
        list = new ArrayList<>(1,5,3);
        int elem = 10;

        // Act
        list.add(elem);

        // Assert
        assertThat(list.size(), is(4));
        assertThat(list.get(list.size() - 1), is(elem));
    }

    @Test
    public void testAddAll_IncrementsSizeProperly() {
        // Arrange
        Integer[] elements = new Integer[] {1,10,25};

        // Act
        list.addAll(elements);

        // Assert
        assertThat(list.size(), is(elements.length));
        for (int i = 0; i < list.size(); i++) {
            assertThat(list.get(i), is(elements[i]));
        }
    }

    @Test
    public void testIsEmpty_ReturnsTrue() {
        // Assert
        assertThat(list.isEmpty(), is(true));
        assertThat(list.size(), is(0));
    }

    @Test
    public void testIsEmpty_ReturnsFalse_WhenSizeGreaterThanZero() {
        // Arrange
        list.add(1);

        // Assert
        assertThat(list.isEmpty(), is(false));
        assertThat(list.size(), is(1));
    }

    @Test
    public void testClear_ResetsTheList() {
        // Arrange
        list = new ArrayList<>(1,2);

        // Act
        list.clear();

        // Assert
        assertThat(list.size(), is(0));
        assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void testAddIndex_ShiftsElements() {
        // Arrange
        list = new ArrayList<>(1,2,5);

        ArrayList<Integer> expected = new ArrayList(1,3,2,5);

        // Act
        // Add 3 at index 1
        list.add(1, 3);

        // Assert
        assertThat(list, is(expected));
    }

    @Test
    public void testIndexOf_ReturnsTrue_WhenElementExists() {
        // Arrange
        list = new ArrayList<>(1,2,3);
        int target = 2, expectedIndex = 1;

        // Act
        int index = list.indexOf(target);

        // Assert
        assertThat(index, is(expectedIndex));
    }

    @Test
    public void testIndexOf_ReturnsFalse_WhenElementDoesNotExist() {
        // Arrange
        list = new ArrayList<>(1,2,3);
        int target = 4, expectedIndex = -1;

        // Act
        int index = list.indexOf(target);

        // Assert
        assertThat(index, is(expectedIndex));
    }

    @Test
    public void testIndexOf_ReturnsFirstIndex_WhenDupeElementsExist() {
        // Arrange
        list = new ArrayList<>(2,1,2,2);
        int target = 2, expectedIndex = 0;

        // Act
        int index = list.indexOf(target);

        // Assert
        assertThat(index, is(expectedIndex));
    }

    @Test
    public void testRemove_DecrementsSize_WhenElementExists() {
        // Arrange
        list = new ArrayList<>(1,3,5);
        ArrayList<Integer> expectedList = new ArrayList<>(1,5);
        int target = 3;

        // Act
        boolean removed = list.remove(target);

        // Assert
        assertThat(list, is(expectedList));
        assertThat(removed, is(true));
    }

    @Test
    public void testRemoveAll_RemovesAllOccurrencesOfElement() {
        // Arrange
        list = new ArrayList<>(5,5,5,1,1);
        ArrayList<Integer> expectedList = new ArrayList<>(1, 1);
        int target = 5;

        // Act
        boolean removed = list.removeAll(target);

        // Assert
        assertThat(list, is(expectedList));
        assertThat(removed, is(true));
    }

    @Test
    public void testRemove_DoesNothingWhenElementDNE() {
        // Arrange
        list = new ArrayList<>(1,5,3);
        ArrayList<Integer> expectedList = new ArrayList<>(1, 5, 3);
        int target = 2;

        // Act
        boolean removed = list.remove(target);

        // Assert
        assertThat(list, is(expectedList));
        assertThat(removed, is(false));
    }

    @Test
    public void testRemoveIndex_ShiftsElements() {
        // Arrange
        list = new ArrayList<>(1,2,3,5);
        ArrayList<Integer> expected = new ArrayList<>(1,2,5);
        int targetIndex = 2;

        // Act
        list.removeIndex(targetIndex);

        // Assert
        assertThat(list, is(expected));
    }

    @Test
    public void testResize_MaintainsElementsAndDoublesLength() {
        // Arrange
        list = new ArrayList(4);
        list.addAll(1, 2, 3);
        int expectedLength = 8;
        ArrayList<Integer> expectedList = new ArrayList<>(1, 2, 3);

        // Act
        list.resize();

        // Assert
        assertThat(list, is(expectedList));
        assertThat(list.getLength(), is(expectedLength));
    }

    @Test
    public void testEquals_ReturnsTrue_WhenListsAreSame() {
        // Arrange
        list = new ArrayList(1, 2, 3);
        ArrayList<Integer> otherList = new ArrayList(1, 2, 3);

        // Act
        boolean equals = list.equals(otherList);

        // Assert
        assertThat(equals, is(true));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenListsHaveDifferentElements() {
        // Arrange
        list = new ArrayList<>(1, 2, 3);
        ArrayList<Integer> otherList = new ArrayList(1, 2, 5);

        // Act
        boolean equals = list.equals(otherList);

        // Assert
        assertThat(equals, is(false));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenListsAreOfDifferentSizes() {
        // Arrange
        list = new ArrayList<>(1, 2, 3);
        ArrayList<Integer> otherList = new ArrayList<>(1, 2);

        // Act
        boolean equals = list.equals(otherList);

        // Assert
        assertThat(equals, is(false));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenOtherListIsOfDifferentType() {
        // Arrange
        list = new ArrayList<>(1, 2);
        ArrayList<String> otherList = new ArrayList<String>("A", "B");

        // Act
        boolean equals = list.equals(otherList);

        // Assert
        assertThat(equals, is(false));
    }
}
