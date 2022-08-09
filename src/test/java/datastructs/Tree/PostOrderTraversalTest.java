package datastructs.Tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PostOrderTraversalTest {

    PostOrderTraversal postOrderTraversal;
    TreeVendor vendor;

    @BeforeEach
    public void setup() {
        postOrderTraversal = new PostOrderTraversal();
        vendor = new TreeVendor();
    }

    @Test
    public void testPostOrderTraversalRecursive() {
        // Arrange
        Integer[] vals = new Integer[]{1, 2, 3, 4, null, 5, null, 6, 7};
        TreeNode root = vendor.vendTree(vals);
        List<Integer> expected = List.of(3, 5, 1, 2, 7, 4, 6);

        // Act
        List<Integer> output = postOrderTraversal.traverseRecursive(root);

        // Assert
        assertThat(output, is(expected));
    }

    @Test
    public void testPostOrderTraversalIterative() {
        // Arrange
        Integer[] vals = new Integer[]{1, 2, 3, null, null, 4, 5, null, null, null, null, null, 6};
        TreeNode root = vendor.vendTree(vals);
        List<Integer> expected = List.of(5, 3, 6, 4, 1 ,2);

        // Act
        List<Integer> output = postOrderTraversal.traverseIterative(root);

        // Assert
        assertThat(output, is(expected));
    }
}
