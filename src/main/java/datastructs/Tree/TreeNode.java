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
        if (other instanceof TreeNode) {
            TreeNode otherTree = (TreeNode) other;
            return this.getVal() == otherTree.getVal();
        }
        return false;
    }
}
