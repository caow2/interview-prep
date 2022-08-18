package datastructs.Tree;

import lombok.Getter;

/**
 * Implementation of a range-query data structure where each "node" represents a range interval [x:y]
 * and stores some data about that interval. The data represented by a node becomes more granular per level and each leaf
 * node represents a single value. This is particularly efficient when the data for a range is updated often; a prefix-sum
 * array is a good approach for performing range queries but fails in the efficiency department when it comes to updating
 * partial range data - that's where a segment tree comes in.
 *
 * i.e. Consider an array A with some integer data for which we want to perform range queries over. An appropriate segment
 * tree may look something like the following:
 *          A[0:3]
 *         /     \
 *      A[0:1]   A[2:3]
 *     /   \     /   \
 *   A[0] A[1] A[2]  A[3]
 *
 * Range query operations can be done in O(logn) time as we need to find the node(s) corresponding to a range [x:y] and
 * sum them up.
 *
 * Update operation for a single element (say, changing the value of A[1]) can be done in O(logn) time by querying a
 * balanced segment tree for the specific leaf node corresponding to that element, performing the update and then propagating
 * changes to the relevant nodes back up the tree. Each parent node contains aggregated information about its
 * descendants.
 *
 * To ensure a relatively balanced tree, each node represents half of the data of its parent and we cannot expand a
 * segment tree to include new elements. Additionally, an array is used as the underlying implementation rather than
 * a {@link TreeNode} data structure for a simpler implementation since we can leverage the property that for each
 * node at an index, its left and right children can be found at indices 2i + 1 and 2i + 2 respectively - similar to a
 * heap.
 *
 * For a more detailed explanation, please see
 * https://leetcode.com/articles/a-recursive-approach-to-segment-trees-range-sum-queries-lazy-propagation/
 */
@Getter
public class SegmentTree {
    private int[] tree;

    /**
     * Auxiliary array that has the same structure as {@link #tree}. This stores update values and allows for lazy update
     * propagation within the segment tree.
     * lazy[i] corresponds to the amount that needs to be added to tree[i] when tree[i] is normalized to remove
     * laziness.
     */
    private int[] lazy;

    /**
     * Retain the original array for a leaf-node view of the segment tree.
     */
    private int[] vals;

    public SegmentTree() {
        tree = new int[]{};
        vals = new int[]{};
    }

    public SegmentTree(int[] vals) {
        this.tree = make(vals);
        this.vals = vals;
    }

    /**
     * Create the segment tree by leveraging a recursive helper. This function returns the root of the underlying tree.
     * This creates a segment tree of size 4n. This is justified by the following for an input of size n:
     * 1. For completely balanced trees, we need n nodes in the last level, n / 2 for the prior level, n/4, ..., 1.
     *    This produces the space requirement of n + n/2 + n/4 + ... + 1 < 2n.
     * 2. For unbalanced trees, we need an extra level for nodes in the unbalanced part of the tree. Since in an unbalanced tree
     *    there are n nodes in the last level, in an unbalanced tree we need at most 2n nodes in the new level as each
     *    node in the prior level can have 2 children. This produces the space requirement of 4n.
     */
    private int[] make(int[] vals) {
        int[] tree = new int[vals.length * 4];
        build(vals, tree, 0, 0, vals.length - 1);
        return tree;
    }

    /**
     * This function updates the segment tree at the given index for the given range vals[left ... right] by recursively
     * building the tree from "bottom-up" and leveraging child values for fast computation of a range sum.
     *
     * For a "node" at a given index, its left and right children are at indices 2i + 1 and 2i + 2 respectively.
     *
     * @param vals  The input array to produce a segment tree from
     * @param tree  The segment tree to update
     * @param index The index of the current node in the segment tree
     * @param left  The left bound index (inclusive) of the range for the current segment tree node
     * @param right The right bound index (inclusive) of the range for the current segment tree node
     */
    private void build(int[] vals, int[] tree, int index, int left, int right) {
        // Base case - leaf node
        if (left == right) {
            tree[index] = vals[left];
            return;
        }

        // Recursive case - compute the sums of the left and right children first
        int mid = (left + right) / 2;
        int leftChild = 2 * index + 1;
        build(vals, tree, leftChild, left, mid);
        int rightChild = 2 * index + 2;
        build(vals, tree, rightChild, mid + 1, right);

        // Build the current index
        tree[index] = tree[leftChild] + tree[rightChild];
    }

    /**
     * Update the value of a given index in the segment tree (as represented in the original array) and
     * recompute the range query sums involving that index.
     */
    public void update(int index, int val) {
        update(index, val, 0, 0, vals.length - 1);
        vals[index] = val;
    }

    /**
     * Recursive helper to update the segment at the given index (as represented in the element-wise array) with a new value.
     *
     * @param index Index of the original array to update
     * @param val   Value to update the original array's index with
     * @param currentIndex  Current node index within the segment tree
     * @param left  Index of the left bound value (in original array) of the current node in the segment tree
     * @param right Index of the right bound value (in original array) of the current node in the segment tree
     */
    private void update(int index, int val, int currentIndex, int left, int right) {
        // Base case
        if (left == right) {
            this.tree[currentIndex] = val;
            return;
        }

        // Recursive case - pivot to one side based on the middle index to determine which side the desired index lies on
        int leftChild = 2 * currentIndex + 1, rightChild = leftChild + 1;
        int mid = (left + right) / 2;
        if (index <= mid) {
            // Left side covers [left, mid]
            update(index, val, leftChild, left, mid);
        } else {
            update(index, val, rightChild, mid + 1, right);
        }
        // Now that the appropriate child has been updated, recompute the prefix sum for this node.
        tree[currentIndex] = tree[leftChild] + tree[rightChild];
    }

    /**
     * Query the underlying interval tree for the values from the range [left, right] as if we were computing
     * this from the vals array.
     */
    public int query(int left, int right) {
        return query(0, 0, vals.length - 1, left, right);
    }

    /**
     * Recursive call to query a segment tree and track left and right index bounds. When we consider a node in the tree,
     * there are 3 cases:
     *
     * 1. The bounds of the current node don't overlap with the given range. In this case there's no computation needed here
     *    as the node isn't relevant for the range sum.
     * 2. The bounds of the current node is within the range of [left, right]. In that case we just return the current segment sum.
     * 3. The bounds of the current node overlaps [left, right] and we need to recurse deeper to find the value of the appropriate
     * segments within the range of [left, right].
     *
     * Since we propagate top-down, we only visit leaf nodes if we absolutely need to.
     * The current index tracks our current location in the segment tree.
     *
     * TODO - explain runtime complexity of this search for logn
     */
    private int query(int currentIndex, int leftBound, int rightBound, int left, int right) {
        if (rightBound < left || leftBound > right) {
            return 0;
        } else if (leftBound >= left && rightBound <= right) {
            return tree[currentIndex];
        } else {
            // Partial match case - split the search range since we may require segment sums from both children.
            // Best case is that we only need from one child and one recursive call ends up without any overlap with
            // [left, right]
            int mid = (leftBound + rightBound) / 2, leftChild = 2 * currentIndex + 1, rightChild = leftChild + 1;
            return query(leftChild, leftBound, mid, left, right) +
                    query(rightChild, mid + 1, rightBound, left, right);
        }
    }

    /**
     * Updates the range [start, end] in the segment tree to be reflect the new value.
     * Rather than call the {@link #update} function multiple times (with each call incurring a cost of logn time),
     * this function seeks to propagate all updates in one pass, reducing time complexity from O(klogn) to O(n) for a
     * range update of size k.
     * TODO - Add lazy propagation
     *
     * @param start The start index of the range to update
     * @param end   The end index of the range to update
     * @param val   The value to set to each element in the range
     */
    public void updateRange(int start, int end, int val) {
        updateRange(start, end, val, 0, 0, vals.length - 1);
        while (start <= end) {
            vals[start++] = val;
        }
    }

    public void updateRange(int start, int end, int val, int currentIndex, int left, int right) {
        // No overlap in current segment with specified range
        if (right < start || left > end) {
            return;
        } else if (left == right) {
            // Current node is a leaf node that's within range
            tree[currentIndex] = val;
        } else {
            // Not a leaf node, but current range has some overlap
            int mid = (left + right) / 2;
            int leftChild = 2 * currentIndex + 1;
            int rightChild = 2 * currentIndex + 2;
            updateRange(start, end, val, leftChild, left, mid);
            updateRange(start, end, val, rightChild, mid + 1, right);
            // Update the current node after children updates
            tree[currentIndex] = tree[leftChild] + tree[rightChild];
        }
    }
}
