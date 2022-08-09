package datastructs.Tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BreadthFirstTraversalTest {

    BreadthFirstTraversal breadthFirstTraversal;
    TreeVendor vendor;

    @BeforeEach
    public void setup() {
        breadthFirstTraversal = new BreadthFirstTraversal();
        vendor = new TreeVendor();
    }

    @Test
    public void testTraverse() {
        // Arrange
        Integer[] vals = new Integer[]{1,2,3,null,null,4,5};
        TreeNode root = vendor.vendTree(vals);
        List<Integer> expected = List.of(1, 2, 3, 4, 5);

        // Act
        List<Integer> output = breadthFirstTraversal.traverse(root);

        // Assert
        assertThat(output, is(expected));
    }
}
