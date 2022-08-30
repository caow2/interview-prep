package datastructs.Graph;

/**
 * This data structure supports the following operations to solve the connected components problem:
 * 1. find(x) - outputs the root of the component for element x
 * 2. union(x, y) - merges the components containing elements x and y.
 *
 * Rank and path compression are leveraged to optimize the runtime for above operations.
 */
public class UnionFind {
    /**
     * An array that references the "parent" for an element at index i. If the parent of an index is itself, then element i
     * is considered the root of its connected component.
     */
    int[] parents;
    /**
     * This stores the rank for an element i. Rank serves as a proxy for the height of the component from element i and
     * is used as a heuristic for merging components in {@link #union(int, int)}.
     */
    int[] rank;

    public UnionFind(int numElems) {
        parents = new int[numElems];
        for (int i = 0; i < numElems; i++) {
            parents[i] = i;
        }
        rank = new int[numElems];
    }

    public UnionFind(int[] parents, int[] rank) {
        this.parents = parents;
        this.rank = rank;
    }

    /**
     * The find operation recursively travels a long parents until reaching a root. For path compression to optimize on
     * future finds operations for an element, we directly point each element in the path to the root to the root.
     */
    public int find(int x) {
        int parent = parents[x];
        if (x == parent) {
            return x;
        }
        int root = find(parent);
        parents[x] = root;
        return parents[x];
    }

    /**
     * Union operations can be performed by joining the two components containing x and y together (if they're different).
     * Specifically, this involves linking the roots of x and y together. Therefore, find operations on x and y are required.
     * To maintain relatively balanced trees, we use rank as a merging heuristic for the following cases:
     *
     * 1. rank(root(x)) < rank(root(y)): Merge root(x) as a child of root(y) since it won't increase the overall rank of
     * root(y).
     * 2. rank(root(x)) == rank(root(y)): Choose one root to merge into another and increase the rank of the root that
     * becomes the parent.
     *
     * Return true iff the components were merged.
     */
    public boolean union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) {
            // x and y are in the same component already, nothing to merge.
            return false;
        }

        // Merge heuristic for roots of x and y
        if (rank[rootX] == rank[rootY]) {
            parents[rootY] = parents[rootX];
            rank[rootX]++;
        } else if (rank[rootX] < rank[rootY]) {
            parents[rootX] = parents[rootY];
        } else {
            parents[rootY] = parents[rootX];
        }
        return true;
    }
}
