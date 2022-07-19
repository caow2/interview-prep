package sorting;

import java.util.Random;

/**
 * QuickSort implementation based off the high-level algorithm:
 * 1. Select a pivot element
 * 2. Partition - move the pivot element to it's correct spot in the array. That is, all elements left of the array will
 *    be smaller than the pivot and all elements to the right of the array will be greater than or equal to the pivot.
 * 3. Now the pivot element has been sorted, sort the left and right halves around the pivot.
 *
 * In each step, a pivot element will be moved to its correct spot. We can define the work being done here with the recurrence:
 * Best case: T(n) = 2 * T(n / 2) + O(n) where O(n) is the upper bound for the partition work if we choose a poor pivot.
 * For example, if the pivot was already the smallest element in the sub-array.
 * Since there's O(n) work done per level of the recursion tree in the best case and there are log(n) levels before
 * the list is reduced to size 1 (and already sorted), best case is O(nlogn)
 *
 * Worst case: T(n) = T(n - 1) + O(n) if our pivot is poorly chosen, we can't divide the work to sort the remaining list.
 * This results in the summation O(n + n-1 + n-2 + ... + 1), or O(n^2)
 *
 * Space-wise, each level doesn't use any additional space to partition around the pivot. However, for poorly chosen pivots
 * our recursion stack is bounded by O(n). In the best case scenario, it's O(logn).
 */
public class QuickSort<E extends Comparable> implements Sort<E> {

    private Random random;

    public QuickSort(Random random) {
        this.random = random;
    }

    public void sort(E[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public void sort(E[] arr, int left, int right) {
        if (left >= right) {
            return; // No sorting required for a single element
        }
        int pivot = partition(arr, left, right);
        sort(arr, left, pivot - 1);
        sort(arr, pivot + 1, right);
    }

    /**
     * This function selects a pivot element and shifts it to it's correct location in the given range of the array.
     * Correct in this sense implies that elements to the left of the array are less than the pivot and elements to the
     * right of the array are greater than or equal to the pivot.
     *
     * @return the pivot index
     */
    int partition(E[] arr, int left, int right) {
        int pivot = left + random.nextInt(right - left); // Generate random offset [0, right - left)
        // Move pivot to the start of the range
        swap(arr, left, pivot);
        pivot = left;

        // Each time we find an element < pivot element:
        // Swap the current element with the pivot index.
        // Increment the pivot index and swap the existing element (>= pivot) with the current index.
        // Populate the pivot index with the pivot element.
        // The above will maintain the partition property.
        for (int i = left + 1; i <= right; i++) {
            int compareVal = arr[i].compareTo(arr[pivot]);
            if (compareVal < 0) {
                swap(arr, i, pivot); // Place smaller element at the pivot index and move the pivot index up
                pivot++;
                swap(arr, pivot, i); // Place larger element to the right of the pivot and pivot elem in correct spot
            }
        }
        return pivot;
    }

    /**
     * Swaps the elements at indices a and b.
     */
    void swap(E[] arr, int a, int b) {
        E temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
