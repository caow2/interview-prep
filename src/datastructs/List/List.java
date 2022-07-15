package datastructs.List;

/**
 * Barebones interface for the {@link java.util.List} API for simplicity on implementations.
 */
public interface List<E> {
    public void add(E elem);
    
    public void addAll(E... elems);

    public void add(int index, E elem);

    public int indexOf(E elem);

    public boolean remove(E elem);

    public boolean removeIndex(int index);

    public int size();

    public boolean isEmpty();

    public void clear();
}
