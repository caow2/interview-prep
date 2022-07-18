package datastructs.List;

/**
 * Barebones interface for the {@link java.util.List} API for simplicity on implementations.
 */
public interface List<E> {
    public void add(E elem);
    
    public void addAll(E... elems);

    public void add(int index, E elem);

    public E get(int index);

    public int indexOf(E elem);

    public boolean remove(E elem);

    public boolean removeAll(E elem);

    public void removeIndex(int index);

    public int size();

    public boolean isEmpty();

    public void clear();
}
