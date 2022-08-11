package datastructs.Tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TreeNodeTest {
    TreeVendor vendor;

    @BeforeEach
    public void setup() {
        vendor = new TreeVendor();
    }

    @Test
    public void testSameTree_ReturnsTrue_WhenTreesAreNull() {
        // Act
        boolean same = TreeNode.sameTree(null, null);

        // Assert
        assertThat(same, is(true));
    }

    @Test
    public void testSameTree_ReturnsFalse_WhenOneTreeIsNull() {
        // Arrange
        Integer[] vals1 = new Integer[]{1,2,3};
        TreeNode t1 = vendor.vendTree(vals1);

        // Act
        boolean sameWhenRightNull = TreeNode.sameTree(t1, null);
        boolean sameWhenLeftNull = TreeNode.sameTree(null, t1);

        // Assert
        assertThat(sameWhenLeftNull, is(false));
        assertThat(sameWhenRightNull, is(false));
    }

    @Test
    public void testSameTree_ReturnsTrue_WhenTreesAreSame() {
        // Arrange
        Integer[] vals1 = new Integer[]{1,2,3,null,4,5,6};
        Integer[] vals2 = new Integer[]{1,2,3,null,4,5,6};
        TreeNode t1 = vendor.vendTree(vals1);
        TreeNode t2 = vendor.vendTree(vals2);

        // Act
        boolean same = TreeNode.sameTree(t1, t2);

        // Assert
        assertThat(same, is(true));
    }

    @Test
    public void testSameTree_ReturnsFalse_WhenTreesHaveDifferentVals() {
        // Arrange
        Integer[] vals1 = new Integer[]{1,2,3,4,5};
        Integer[] vals2 = new Integer[]{1,2,null,4,5};
        TreeNode t1 = vendor.vendTree(vals1);
        TreeNode t2 = vendor.vendTree(vals2);

        // Act
        boolean same = TreeNode.sameTree(t1, t2);

        // Assert
        assertThat(same, is(false));
    }

    @Test
    public void testSameTree_ReturnsFalse_WhenOneTreeIsASubtreeOfAnother() {
        // Arrange
        Integer[] vals1 = new Integer[]{1,2,3,4};
        Integer[] vals2 = new Integer[]{1,2,3,4,5,6,7};
        TreeNode t1 = vendor.vendTree(vals1);
        TreeNode t2 = vendor.vendTree(vals2);

        // Act
        boolean same = TreeNode.sameTree(t1, t2);

        // Assert
        assertThat(same, is(false));
    }
}
