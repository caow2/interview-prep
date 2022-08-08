package datastructs.Tree;

/**
 * Utility class to construct binary trees for testing from simplified input.
 */
public class TreeVendor<E> {

    /**
     * Create the binary tree from the given values. Tree construction works as follows:
     * If we label a tree left to right by level, it should correspond to the index of the value in the input.
     * For example: [a, b, c, d, null, e] would create the following tree:
     *      a
     *     / \
     *    b   c
     *   /   /
     *  d   e
     *
     * More specifically, for a node at index i, its left and right children are at index 2i + 1 and 2i + 2 respectively.
     * We assume that the given input is valid - that is, a null node cannot have any children.
     *
     * @param vals the given vals for TreeNodes
     * @return the root node of the constructed tree.
     */
    public TreeNode vendTree(Integer... vals) {
        if (vals.length == 0) {
            return null;
        }

        // Maintain an extra array to track nodes that we've already created, otherwise it's tough to reference
        // the parent/children for a node, even if we know the index of the values
        // Build the tree from top to bottom. We start at the root, and for each node we process we know where its parent
        // is in the auxiliary array
        // We also note that we process left and right children in adjacent pairs (with the exception of the root).
        // [root, left, right, left, right, ...], so we can leverage odd indices as left children and
        // even indices as right children.
        TreeNode[] treeNodes = new TreeNode[vals.length];
        treeNodes[0] = new TreeNode(vals[0]);
        for (int i = 1; i < vals.length; i++) {
            Integer currVal = vals[i];
            if (currVal == null) {
                continue;
            }

            int parentIdx = (i - 1) / 2; // Leverage ceil in integer division to round down in case this index was 2i + 2
            TreeNode parent = treeNodes[parentIdx];
            TreeNode current = new TreeNode(currVal);
            treeNodes[i] = current;
            if (i % 2 == 1) {
                // Left child
                parent.setLeft(current);
            } else {
                parent.setRight(current);
            }
        }
        return treeNodes[0];
    }
}
