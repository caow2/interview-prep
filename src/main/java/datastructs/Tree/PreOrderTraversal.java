package datastructs.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class performs traversal on {@link TreeNode}s using a preorder scheme:
 * 1. Visit the root
 * 2. Visit its left child
 * 3. Visit its right child
 */
public class PreOrderTraversal implements TreeTraversal {

    @Override
    public List<Integer> traverseRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traverseRecursiveHelper(root, result);
        return result;
    }

    /**
     * Helper function to perform a preorder traversal and populate the result list as we visit each node.
     */
    private void traverseRecursiveHelper(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.getVal());
        traverseRecursiveHelper(root.getLeft(), result);
        traverseRecursiveHelper(root.getRight(), result);
    }

    /**
     * For the iterative version, we want to simulate the recursion stack from {@link PreOrderTraversal#traverseRecursive}.
     * So starting from the root, we add it to some list and traverse to the left child. However, since we're not
     * completely done with the root (it still needs to process the right child), we have to maintain a reference to it.
     * <p>
     * We do the above for each node in the tree via a pointer, and once we cannot go left anymore, we need to go back to the
     * last TreeNode we processed to traverse its right child - hence keeping a stack will help here.
     */
    @Override
    public List<Integer> traverseIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !st.isEmpty()) {
            // Go all the way to the left leaf that we can, "visiting" each node before their children and keeping
            // references to the nodes onto our Stack so we can process the right children later on.
            while (curr != null) {
                result.add(curr.getVal());
                st.push(curr);
                curr = curr.getLeft();
            }

            // We've gone to the leftmost node that we can, so now we need to rely on our Stack to process the right
            // children that we've missed.
            // Since curr was either not null (meaning we added something to the Stack), or the Stack wasn't empty
            // if we're at this point, so there has to be at least one item in the Stack.
            curr = st.pop().getRight();
        }
        return result;
    }
}
