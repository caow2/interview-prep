package sorting;

import java.util.Random;

/**
 * QuickSelect algorithm based on the partitioning strategy leveraged in QuickSort to find the kth smallest
 * element in a list. However, instead of partitioning both halves around a pivot, we only partition for the
 * half of the list contains k.
 *
 * High level algorithm:
 * 1. Select a random pivot index.
 * 2. Partition the list around the pivot such that all elements to left of pivot element are less than it and all elements
 *    to the right of the pivot element are greater than or equal to.
 * 3. The pivot element is now in its correct spot in the list. Therefore if the pivot index is less than k,
 *    we still have to partition the list to the right of pivot to find the kth smallest element.
 * 4. If the pivot index is greater than k, then the kth smallest element is to the left of the pivot index.
 * 5. If the pivot is equivalent to k, then we're done.
 *
 * In terms of time complexity, if we choose the worst pivot each time, we could go through k iterations until we pivot around
 * k. The work here would be O(n + n-1 + n-2 + ... + n - k). Since k is bounded by n, n - k = 0, and the summation
 * here is just n * (n / 2), or O(n^2).
 *
 * In the best case, we pick a good pivot that evenly partitions the list in half each time. The work here would then be
 * n + n / 2 + n / 4 + ... + 1. That sequence is < 2n, therefore is O(n).
 */
public class QuickSelect<E extends Comparable> {
    private Random random;

    public QuickSelect(Random random) {
        this.random = random;
    }

    /**
     * Select the kth smallest element using the QuickSelect algorithm
     */
    public E select(E[] list, int k) {
        int pivot = -1, left = 0, right = list.length - 1;
        while (pivot != k) {
            pivot = partition(list, left, right);
            if (pivot < k) {
                left = pivot + 1;
            } else if (pivot > k) {
                right = pivot - 1;
            }
        }
        return list[k];
    }

    int partition(E[] list, int left, int right) {
        if (left == right) {
            return left; // Nothing to partition. Also nextInt doesn't like when the bound is 0.
        }
        // Select a random element within the range as the pivot and move it to start of the range
        int offSet = random.nextInt(right - left), pivot = left + offSet;
        swap(list, left, pivot);
        pivot = left;

        // Iterate through the range of values [left + 1, right]. If there's an element that's strictly less than the pivot element,
        // Place it at the pivot index and increment the pivot index.
        // Place the next element (now at the pivot index) in the current index
        // Place the pivot element in the pivot index.
        // This means that for dupes equivalent to pivot elem, we still place it to the right of the pivot.
        for (int i = left + 1; i <= right; i++) {
            int compareVal = list[pivot].compareTo(list[i]);
            if (compareVal > 0) {
                E temp = list[pivot];
                list[pivot] = list[i];
                pivot++;
                list[i] = list[pivot];
                list[pivot] = temp;
            }
        }
        return pivot;
    }

    void swap(E[] list, int a, int b) {
        E temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }

}
