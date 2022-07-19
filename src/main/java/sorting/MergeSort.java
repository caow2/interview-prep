package sorting;

import java.lang.reflect.Array;

/**
 * MergeSort implementation based on the high-level algorithm:
 * 1. Base Case: Size of the list to sort is 1 - in which case, it's already sorted.
 * 2. Recursive Case: The list is not sorted, so we partition it in half: [left, mid], [mid + 1, right]
 *    and invoke mergeSort on both halves.
 *    Once both halves are sorted, we combine them back into a single sorted array.
 *
 * The work can be described by the recurrence relationship:
 * T(n) = 2 T(n / 2) + O(n)
 * T(1) = 1
 * Since we divide the array into two halves at each level of mergeSort, it takes approximately
 * log2(n) steps to reach the base case. Since the work done at each level is O(n), we can reason that the time
 * complexity is O(nlogn).
 * As an auxillary array is used per level to combine the sorted halves, the space complexity at each level is
 * O(n). But since we only maintain the array temporarily, the overall space complexity is O(n).
 */
public class MergeSort<E extends Comparable> implements Sort<E> {

    @Override
    public void sort(E[] list) {
        sort(list, 0, list.length - 1);
    }

    /**
     * Recursive helper to sort the list within the given range [left, right].
     * Inclusive of both indices.
     */
     void sort(E[] list, int left, int right) {
        // List has reached length 1, which is our base case
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        sort(list, left, mid);
        sort(list, mid + 1, right);
        merge(list, left, mid, right);
    }

    /**
     * Merges the sorted sub-arrays from [left, mid] and [mid + 1, right]
     */
    void merge(E[] list, int left, int mid, int right) {
        // Create copies of the sorted left and right halves
        E[] leftArr = copyOf(list, left, mid);
        E[] rightArr = copyOf(list, mid + 1, right);

        // Merge them together in the original list
        int l = 0, r = 0;
        for (int i = left; i <= right; i++) {
            // Compare the val at leftArr to the val at rightArr to see which next element we should use
            int compareTo;
            if (l >= leftArr.length) {
                compareTo = 1; // No leftVal left to process
            } else if (r >= rightArr.length) {
                compareTo = -1; // No rightVal left to process
            } else {
                // There are still valid leftArr and rightArr elements to process
                compareTo = leftArr[l].compareTo(rightArr[r]);
            }

            // Take the leftArr as the smaller element
            if (compareTo <= 0) {
                list[i] = leftArr[l++];
            } else {
                list[i] = rightArr[r++];
            }
        }
    }

    /**
     * Create a copy of the given array within the specified indices (inclusive).
     */
    E[] copyOf(E[] arr, int start, int end) {
        E[] copy = (E[]) Array.newInstance(arr[0].getClass(), end - start + 1);
        for (int i = 0; i < copy.length; i++) {
            copy[i] = arr[start + i];
        }
        return copy;
    }
}
