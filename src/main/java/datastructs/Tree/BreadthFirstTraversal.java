package datastructs.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class performs traversal on {@link TreeNode}s in a breadth-first fashion. Specifically,
 * we maintain a queue to track the nodes that still need to be visited. Starting at the root, we visit nodes and add
 * their children to the queue to be traversed later.
 */
public class BreadthFirstTraversal {

    public List<Integer> traverse(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.getVal());
            if (node.getLeft() != null) {
                queue.offer(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.offer(node.getRight());
            }
        }
        return result;
    }
}
