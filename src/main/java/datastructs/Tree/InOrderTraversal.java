package datastructs.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class performs traversal on {@link TreeNode}s using an in order schemee:
 * 1. Visiting the left
 * 2. Visiting the root
 * 3. Visiting the right
 */
public class InOrderTraversal implements TreeTraversal {

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
        traverseRecursiveHelper(root.getLeft(), result);
        result.add(root.getVal());
        traverseRecursiveHelper(root.getRight(), result);
    }

    /**
     * This follows a similar logic to {@link PreOrderTraversal#traverseIterative}, with the difference being that
     * we first visit all the way to the left and only add to a list when we pop from the stack. That is,
     * after all the left children have been processed and we now need to process the right children of a node.
     */
    @Override
    public List<Integer> traverseIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !st.isEmpty()) {
            while (curr != null) {
                st.push(curr);
                curr = curr.getLeft();
            }
            // Curr is now null and we've visited all the left children that we can. Fetch the last node, add it to
            // the result list and visit the right subtree.
            TreeNode parent = st.pop();
            result.add(parent.getVal());
            curr = parent.getRight();
        }
        return result;
    }
}
