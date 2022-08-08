package datastructs.Tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PreOrderTraversalTest {

    // Object under test
    PreOrderTraversal preOrder;

    TreeVendor vendor;

    @BeforeEach
    public void setup() {
        preOrder = new PreOrderTraversal();
        vendor = new TreeVendor();
    }

    @Test
    public void testRecursiveTreeTraversal() {
        // Arrange
        Integer[] values = new Integer[] {1, 2, 3, 4, null, 5, 6};
        TreeNode root = vendor.vendTree(values);

        List<Integer> expected = List.of(1, 2, 4, 3, 5, 6);

        // Act
        List<Integer> output = preOrder.traverseRecursive(root);

        // Assert
        assertThat(output, is(expected));
    }

    @Test
    public void testIterativeTreeTraversal() {
        // Arrange
        Integer[] values = new Integer[] {1, 2, 3, null, 4, 5, 6, null, null, 7};
        TreeNode root = vendor.vendTree(values);

        List<Integer> expected = List.of(1, 2, 4, 7, 3, 5, 6);

        // Act
        List<Integer> output = preOrder.traverseIterative(root);

        // Assert
        assertThat(output, is(expected));
    }


}
