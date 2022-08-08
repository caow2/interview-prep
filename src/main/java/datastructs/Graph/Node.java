package datastructs.Graph;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * POJO representing a vertex for an object-oriented representation of a graph.
 */
@Getter
public class Node<E> {
    private E val;
    private Set<Node<E>> neighbors;

    public Node(E val) {
        this.val = val;
        neighbors = new HashSet<>();
    }
}
