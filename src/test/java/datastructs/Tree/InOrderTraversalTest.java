package datastructs.Tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InOrderTraversalTest {
    InOrderTraversal inOrderTraversal;
    TreeVendor vendor;

    @BeforeEach
    public void setup() {
        inOrderTraversal = new InOrderTraversal();
        vendor = new TreeVendor();
    }

    @Test
    public void testInOrderTraversalRecursive() {
        // Arrange
        Integer[] vals = new Integer[]{1, 2, 3, 4, 5, 6};
        TreeNode root = vendor.vendTree(vals);
        List<Integer> expected = List.of(4, 2, 5, 1, 6, 3);

        // Act
        List<Integer> output = inOrderTraversal.traverseRecursive(root);

        // Assert
        assertThat(output, is(expected));
    }

    @Test
    public void testInOrderTraversalIterative() {
        // Arrange
        Integer[] vals = new Integer[]{1, 2, 3, 4, 5, null, 6, 7, 8};
        TreeNode root = vendor.vendTree(vals);
        List<Integer> expected = List.of(7, 4, 8, 2, 5, 1, 3, 6);

        // Act
        List<Integer> output = inOrderTraversal.traverseIterative(root);

        // Assert
        assertThat(output, is(expected));
    }
}
