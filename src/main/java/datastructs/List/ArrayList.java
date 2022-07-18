package datastructs.List;

/**
 * Array-based that list offers resizing once the list reaches full capacity.
 * TODO - Add IndexOutOfBounds exceptions for add, get, removeIndex, etc.
 */
public class ArrayList<E> implements List<E> {

    private static final int DEFAULT_SIZE = 10;

    private int size;
    private E[] arr; // VisibleForTesting

    public ArrayList() {
        this(DEFAULT_SIZE);
    }

    public ArrayList(int size) {
        arr = (E[]) new Object[size];
        this.size = 0;
    }

    public ArrayList(E... elements) {
        this(elements.length * 2);
        this.addAll(elements);
    }

    @Override
    public void add(E elem) {
        // We can use size as a pointer to where the next element should be added at the end of the list.
        add(size(), elem);
    }

    @Override
    public void addAll(E... elems) {
        // TODO - Could be made more efficient by just shifting all elements at once, rather than perform n
        //  shift operations
        for (E elem : elems) {
            add(elem);
        }
    }

    @Override
    public void add(int index, E elem) {
        // List reaches full capacity when size is length. But when we add we need to ensure there is an additional
        // empty index available for shifting, hence we check for length - 1.
        if (size() >= arr.length - 1) {
            resize();
        }
        // Shift appropriate elements to the right: [index, size - 1 (the last element in the list)]
        for (int i = size() - 1; i >= index; i--) {
            arr[i + 1] = arr[i];
        }
        arr[index] = elem;
        size++;
    }

    @Override
    public int indexOf(E elem) {
        for (int i = 0; i < size(); i++) {
            if (arr[i].equals(elem)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean remove(E elem) {
        int index = indexOf(elem);
        boolean canRemove = index != -1;
        if (canRemove) {
            removeIndex(index);
        }
        return canRemove;
    }

    @Override
    public boolean removeAll(E elem) {
        // Start from the end of minimize shift operations.
        boolean removed = false;
        for (int i = size() - 1; i >= 0; i--) {
            if (get(i).equals(elem)) {
                removeIndex(i);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public E get(int index) {
        return arr[index];
    }

    @Override
    public void removeIndex(int index) {
        // Decrement size first to avoid copying an extra element at the end of the loop operation.
        size--;
        // Shift all elements on the right of index one to the left
        for (int i = index; i < size(); i++) {
            arr[i] = arr[i + 1];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            sb.append(arr[i]);
            if (i < size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ArrayList)) {
            return false;
        }
        ArrayList<?> otherList = (ArrayList<?>) other;
        boolean same = otherList.size() == this.size();
        for (int i = 0; same && i < size(); i++) {
            if (!get(i).equals(otherList.get(i))) {
                same = false;
            }
        }
        return same;
    }

    /**
     * Visible for testing.
     */
    int getLength() {
        return arr.length;
    }

    /**
     * Visible for testing.
     */
    void resize() {
        E[] temp = (E[]) new Object[this.arr.length * 2];
        // Copy existing elements over
        for (int i = 0; i < size; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }
}
