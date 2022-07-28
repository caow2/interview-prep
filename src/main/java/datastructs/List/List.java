package datastructs.List;

/**
 * Barebones interface for the {@link java.util.List} API for simplicity on implementations.
 */
public interface List<E> {
    void add(E elem);
    
    void addAll(E... elems);

    void add(int index, E elem);

    E get(int index);

    int indexOf(E elem);

    boolean remove(E elem);

    boolean removeAll(E elem);

    void removeIndex(int index);

    int size();

    boolean isEmpty();

    void clear();
}
