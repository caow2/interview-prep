package datastructs.Tree;

import lombok.Getter;
import lombok.Setter;

/**
 * POJO for a node of a binary tree.
 */
@Getter
@Setter
public class TreeNode {
    private int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public boolean equals(Object other) {
        // Null fails instanceof check, so it's rightfully returned as false
        if (other instanceof TreeNode) {
            TreeNode otherTree = (TreeNode) other;
            return sameTree(this, otherTree);
        }
        return false;
    }

    /**
     * Compare two given trees and return whether they're the same structurally.
     */
    public static boolean sameTree(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2 == null;
        } else if (t2 == null) {
            return t1 == null;
        }

        // Both are not null now - so we check their values and subtrees
        return t1.getVal() == t2.getVal() && sameTree(t1.getLeft(), t2.getLeft()) &&
                sameTree(t1.getRight(), t2.getRight());

    }
}
