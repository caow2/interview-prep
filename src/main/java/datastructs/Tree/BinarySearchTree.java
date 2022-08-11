package datastructs.Tree;

/**
 * Binary Search Tree built on top of {@link TreeNode}s. This class has no guarantee on tree balancing and doesn't allow duplicates.
 *
 *
 * Functions of this class are taken from a superset in the standard {@link java.util.TreeSet} API.
 */
public class BinarySearchTree {
    private TreeNode root;
    private int size;

    public BinarySearchTree(int... vals) {
        for (int val : vals) {
            this.add(val);
        }
    }

    public boolean add(int num) {
        if (root == null) {
            root = new TreeNode(num);
            size++;
            return true;
        }
        // Find the parent of the new TreeNode. We "search" the tree until the current node is null to find its relevant spot
        // and keep a reference to the last parent node we looked at so we can add the new node after.
        TreeNode node = root, parent = null;
        while (node != null) {
            parent = node;
            if (node.getVal() == num) {
                return false; // Can't add a number if it already exists
            } else if (node.getVal() < num) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }

        if (num < parent.getVal()) {
            parent.setLeft(new TreeNode(num));
        } else {
            parent.setRight(new TreeNode(num));
        }
        size++;
        return true;
    }

    public boolean contains(int num) {
        TreeNode node = root;

        while (node != null) {
            if (node.getVal() == num) {
                return true;
            } else if (node.getVal() < num) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }
        return false;
    }

    /**
     * Returns the smallest TreeNode in the binary search tree.
     */
    public TreeNode first() {
        TreeNode node = root;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Returns the largest TreeNode that's less than given number, or null if that doesn't exist.
     */
    public TreeNode higher(int num) {
        // Same idea as lower, but we track the last right node we visited. Only update the right when pivoting to a left
        // subtree while searching.
        TreeNode curr = root, right = null;
        while (curr != null) {
            if (curr.getVal() > num) {
                // Pivot to the left
                right = curr;
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        return right;
    }

    /**
     * Returns the smallest TreeNode that's greater than the given number, or null if that doesn't exist.
     */
    public TreeNode lower(int num) {
        // The idea is to traverse the tree and keep a pointer to the last left node we visited (i.e. we pivot to
        // a right subtree from a left node). Since we constantly move closer to the number through searching the tree,
        // the left nodes we track are increasing in value.
        TreeNode curr = root, left = null;
        while (curr != null) {
            if (curr.getVal() >= num) {
                // Don't stop when we find the value -- the left subtree can still have a node that's greater than the
                // left nodes we've looked at.
                curr = curr.getLeft();
            } else {
                // Pivot to the right
                left = curr;
                curr = curr.getRight();
            }
        }
        return left;
    }

    /**
     * Returns the largest TreeNode in the binary search tree.
     */
    public TreeNode last() {
        TreeNode node = root;
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Remove the node corresponding to the given number, if it exists in the tree. We have the following cases:
     * 1. The node has no children. Trivial case
     * 2. The node has one child. We can just replace the deleted node with the child.
     * 3. The node has two children. We need to find the in-order successor (next smallest value) and replace the deleted node
     * with it. Afterwards, the reference to the (original) in-order successor in the tree needs to be deleted.
     */
    public boolean remove(int num) {
        if (!contains(num)) {
            return false;
        }
        // Proceed to remove
        root = removeHelper(num, root);
        size--;
        return true;
    }

    private TreeNode removeHelper(int num, TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.getVal() < num) {
            node.setRight(removeHelper(num, node.getRight()));
        } else if (node.getVal() > num) {
            node.setLeft(removeHelper(num, node.getLeft()));
        } else {
            if (node.getRight() == null) {
                return node.getLeft(); // Cover cases 1 and 2
            } else {
                // There's a right subtree, so there has to be a successor. We copy the successor's value over, and then
                // delete the successor from the right subtree.
                TreeNode successor = higher(num);
                node.setVal(successor.getVal());
                node.setRight(removeHelper(successor.getVal(), node.getRight()));
            }
        }
        return node;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    protected TreeNode getRoot() {
        return root;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BinarySearchTree) {
            BinarySearchTree otherTree = (BinarySearchTree) other;
            return TreeNode.sameTree(this.getRoot(), otherTree.getRoot());
        }
        return false;
    }
}