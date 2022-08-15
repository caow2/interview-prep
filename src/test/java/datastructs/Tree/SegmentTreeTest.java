package datastructs.Tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SegmentTreeTest {

    private SegmentTree tree;

    @BeforeEach
    public void setup() {
        tree = new SegmentTree();
    }

    @Test
    public void testMakeSegmentTree() {
        // Arrange
        int[] input = new int[] {5, 9, 2, 3, 4, 1};
        int[] expectedTree = new int[] {24, 16, 8, 14, 2, 7, 1, 5, 9, 0, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        // Act
        tree = new SegmentTree(input);

        // Assert
        assertThat(tree.getTree().length, is(expectedTree.length));
        assertThat(tree.getTree(), is(expectedTree));
        assertThat(tree.getVals().length, is(input.length));
        assertThat(tree.getVals(), is(input));
    }

    @Test
    public void testUpdateSegmentTree() {
        // Arrange
        int[] input = new int[] {5, 9, 2, 3, 4, 1};
        tree = new SegmentTree(input);
        int[] expectedTree = new int[] {67, 59, 8, 14, 45, 7, 1, 5, 9, 0, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] expectedVals = new int[] {5, 9, 45, 3, 4, 1};

        // Act
        tree.update(2, 45);

        // Assert
        assertThat(tree.getTree(), is(expectedTree));
        assertThat(tree.getVals(), is(expectedVals));
    }

    @Test
    public void testQuerySegmentTree_WhenRangeIsNotWithinTree() {
        // Arrange
        int[] input = new int[] {5, 9, 2, 3, 4, 1};
        tree = new SegmentTree(input);
        int expectedOutput = 0;

        // Act
        int rangeSum = tree.query(10, 25);

        // Assert
        assertThat(rangeSum, is(expectedOutput));
    }

    @Test
    public void testQuerySegmentTree_WhenRangeMatchesANode() {
        // Arrange
        int[] input = new int[] {5, 9, 2, 3, 4, 1};
        tree = new SegmentTree(input);
        int expectedOutput = 7;

        // Act
        int rangeSum = tree.query(3, 4);

        // Assert
        assertThat(rangeSum, is(expectedOutput));
    }

    @Test
    public void testQuerySegmentTree_WhenRangeIsSplit() {
        // Arrange
        int[] input = new int[] {5, 9, 2, 3, 4, 1};
        tree = new SegmentTree(input);
        int expectedOutput = 18;

        // Act
        int rangeSum = tree.query(1, 4);

        // Assert
        assertThat(rangeSum, is(expectedOutput));
    }
}
