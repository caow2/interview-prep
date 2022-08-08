package datastructs.Tree;

import java.util.List;

public interface TreeTraversal {
    List<Integer> traverseRecursive(TreeNode root);

    List<Integer> traverseIterative(TreeNode root);
}
