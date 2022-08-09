package datastructs.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class performs traversal on {@link TreeNode} using a post order scheme:
 * 1. Visit the right children
 * 2. Visit the root
 * 3. Visit the left children
 */
public class PostOrderTraversal implements TreeTraversal {
    @Override
    public List<Integer> traverseRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traverseRecursiveHelper(root, result);
        return result;
    }

    private void traverseRecursiveHelper(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        traverseRecursiveHelper(root.getRight(), result);
        result.add(root.getVal());
        traverseRecursiveHelper(root.getLeft(), result);
    }

    /**
     * This employs similar logic to {@link PreOrderTraversal#traverseIterative} but instead of visiting all the way
     * to the left, we visit all the way to the right first. Nodes in the stack represent parents whose left subtrees
     * we still need to visit.
     */
    @Override
    public List<Integer> traverseIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !st.isEmpty()) {
            while (curr != null) {
                st.push(curr);
                curr = curr.getRight();
            }

            // Curr is now null so we need to process any left children of parents in the "recursion" stack.
            TreeNode parent = st.pop();
            result.add(parent.getVal());
            curr = parent.getLeft();
        }
        return result;
    }
}
