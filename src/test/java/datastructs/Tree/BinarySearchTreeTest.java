package datastructs.Tree;

import static datastructs.Tree.TreeNode.sameTree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {

    BinarySearchTree bst;
    TreeVendor vendor;

    @BeforeEach
    public void setup() {
        bst = new BinarySearchTree();
        vendor = new TreeVendor();
    }

    @Test
    public void testAdd_ReturnsTrue_WhenTreeIsEmpty() {
        // Arrange
        TreeNode expectedTree = vendor.vendTree(new Integer[]{5});

        // Act
        boolean added = bst.add(5);

        // Assert
        assertThat(added, is(true));
        assertThat(bst.size(), is(1));
        assertThat(sameTree(bst.getRoot(), expectedTree), is(true));
    }

    @Test
    public void testAdd_ReturnsTrue_WhenTreeIsNotEmpty() {
        // Arrange
        bst = new BinarySearchTree(5, 2, 8, 3, 1);
        int originalSize = 5;
        Integer[] expectedVals = new Integer[]{5, 2, 8, 1, 3, null, 9};
        TreeNode expectedTree = vendor.vendTree(expectedVals);

        // Act
        boolean added = bst.add(9);

        // Assert
        assertThat(added, is(true));
        assertThat(bst.size(), is(originalSize + 1));
        assertThat(sameTree(bst.getRoot(), expectedTree), is(true));
    }

    @Test
    public void testAdd_ReturnsFalse_WhenValueAlreadyExists() {
        // Arrange
        bst = new BinarySearchTree(5, 9, 6, 2, 1, 3);
        int originalSize = 6;
        Integer[] expectedVals = new Integer[]{5, 2, 9, 1, 3, 6};
        TreeNode expectedTree = vendor.vendTree(expectedVals);

        // Act
        boolean added = bst.add(3);

        // Assert
        assertThat(added, is(false));
        assertThat(bst.size(), is(originalSize));
        assertThat(sameTree(bst.getRoot(), expectedTree), is(true));
    }

    @Test
    public void testContains_ReturnsTrue_WhenValueExistsInTree() {
        // Arrange
        bst = new BinarySearchTree(5, 4, 9, 8, 11, 1);

        // Act
        boolean contains = bst.contains(11);

        // Assert
        assertThat(contains, is(true));
    }

    @Test
    public void testContains_ReturnsFalse_WhenValueDoesNotExistInTree() {
        // Arrange
        bst = new BinarySearchTree(5, 2, 9, 7, 1, 3);

        // Act
        boolean contains = bst.contains(8);

        // Assert
        assertThat(contains, is(false));
    }

    @Test
    public void testFirst_ReturnsLeftMostChild() {
        // Arrange
        bst = new BinarySearchTree(7, 5, 2, 4, 3, 1, 8);

        // Act
        TreeNode first = bst.first();

        // Assert
        assertThat(first.getVal(), is(1));
    }

    @Test
    public void testHigher_ReturnsNextSmallestElement_IgnoresSameValueInTree() {
        // Arrange
        bst = new BinarySearchTree(7, 5, 9, 4, 11, 8);
        int expected = 9;

        // Act
        TreeNode higher = bst.higher(8);

        // Assert
        assertThat(higher, is(notNullValue()));
        assertThat(higher.getVal(), is(expected));
    }

    @Test
    public void testHigher_ReturnsNextSmallestElement_WhenValueNotInTree() {
        // Arrange
        bst = new BinarySearchTree(7, 2, 1, 5, 4, 9, 10);
        int expected = 4;

        // Act
        TreeNode higher = bst.higher(3);

        // Assert
        assertThat(higher, is(notNullValue()));
        assertThat(higher.getVal(), is(expected));
    }

    @Test
    public void testHigher_ReturnsNull_WhenNoGreaterElement() {
        // Arrange
        bst = new BinarySearchTree(7, 9, 1, 5, 10);

        // Act
        TreeNode higher = bst.higher(11);

        // Assert
        assertThat(higher, is(nullValue()));
    }

    @Test
    public void testLower_ReturnsLastLargestElement_IgnoresSameValueInTree() {
        // Arrange
        bst = new BinarySearchTree(7, 8, 9, 2, 3, 1);
        int expected = 3;

        // Act
        TreeNode lower = bst.lower(7);

        // Assert
        assertThat(lower, is(notNullValue()));
        assertThat(lower.getVal(), is(expected));
    }

    @Test
    public void testLower_ReturnsLastLargestElement_WhenValueNotInTree() {
        // Arrange
        bst = new BinarySearchTree(7, 9, 1, 5, 4, 10);
        int expected = 5;

        // Act
        TreeNode lower = bst.lower(6);

        // Assert
        assertThat(lower, is(notNullValue()));
        assertThat(lower.getVal(), is(expected));
    }

    @Test
    public void testLower_ReturnsNull_WhenNoLowerNode() {
        // Arrange
        bst = new BinarySearchTree(4, 3, 6, 2);

        // Act
        TreeNode lower = bst.lower(2);

        // Assert
        assertThat(lower, is(nullValue()));
    }

    @Test
    public void testLast_ReturnsRightMostChild() {
        // Arrange
        bst = new BinarySearchTree(5,1,7,2,9,11,3);
        int expected = 11;

        // Act
        TreeNode last = bst.last();

        // Assert
        assertThat(last.getVal(), is(expected));
    }

    @Test
    public void testRemove_ReturnsTrue_WhenRemovedNodeHasNoChildren() {
        // Arrange
        bst = new BinarySearchTree(6, 9, 1, 3, 0, 7);
        int originalSize = 6;
        Integer[] expectedVals = new Integer[]{6, 1, 9, 0, 3};
        TreeNode expectedTree = vendor.vendTree(expectedVals);

        // Act
        boolean removed = bst.remove(7);

        // Assert
        assertThat(removed, is(true));
        assertThat(bst.size(), is(originalSize - 1));
        assertThat(sameTree(bst.getRoot(), expectedTree), is(true));
    }

    @Test
    public void testRemove_ReturnsTrue_WhenRemovedNodeHasOneChild() {
        // Arrange
        bst = new BinarySearchTree(6, 9, 1, 3, 0, 7, 8);
        int originalSize = 7;
        Integer[] expectedVals = new Integer[]{6, 1, 7, 0, 3, null, 8};
        TreeNode expectedTree = vendor.vendTree(expectedVals);

        // Act
        boolean removed = bst.remove(9);

        // Assert
        assertThat(removed, is(true));
        assertThat(bst.size(), is(originalSize - 1));
        assertThat(sameTree(bst.getRoot(), expectedTree), is(true));
    }

    @Test
    public void testRemove_ReturnsTrue_WhenRemovedNodeHasTwoChildren() {
        // Arrange
        bst = new BinarySearchTree(6, 9, 1, 3, 0, 7, 8, 10);
        int originalSize = 8;
        Integer[] expectedVals = new Integer[]{7, 1, 9, 0, 3, 8, 10};
        TreeNode expectedTree = vendor.vendTree(expectedVals);

        // Act
        boolean removed = bst.remove(6);

        // Assert
        assertThat(removed, is(true));
        assertThat(bst.size(), is(originalSize - 1));
        assertThat(sameTree(bst.getRoot(), expectedTree), is(true));
    }

    @Test
    public void testRemove_ReturnsFalse_WhenValueNotInTree() {
        // Arrange
        bst = new BinarySearchTree(6, 3, 4, 9, 7, 10);
        int originalSize = 6;
        Integer[] expectedVals = new Integer[]{6, 3, 9, null, 4, 7, 10};
        TreeNode expectedTree = vendor.vendTree(expectedVals);

        // Act
        boolean removed = bst.remove(8);

        // Assert
        assertThat(removed, is(false));
        assertThat(bst.size(), is(originalSize));
        assertThat(sameTree(bst.getRoot(), expectedTree), is(true));
    }

    @Test
    public void testIsEmpty_ReturnsTrue_WhenBSTEmpty() {
        // Act
        boolean isEmpty = bst.isEmpty();

        // Assert
        assertThat(isEmpty, is(true));
    }

    @Test
    public void testIsEmpty_ReturnsFalse_WhenTreeHasElements() {
        // Arrange
        bst = new BinarySearchTree(1, 5, 0);

        // Act
        boolean isEmpty = bst.isEmpty();

        // Assert
        assertThat(isEmpty, is(false));
    }
}

