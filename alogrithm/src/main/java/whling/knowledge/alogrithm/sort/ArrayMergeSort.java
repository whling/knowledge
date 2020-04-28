package whling.knowledge.alogrithm.sort;

import java.util.Arrays;

/**
 * 数组的归并排序实现
 */
public class ArrayMergeSort {

    public static void main(String[] args) {

        int[] arr = {3, 8, 9, 2, 10};

        System.out.println(Arrays.toString(splitArray(arr)));
    }

    private static int[] splitArray(int[] arr) {

        if (arr.length <= 1) {
            return arr;
        }

        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        int leftIndex = 0, rightIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i < mid) {
                left[leftIndex++] = arr[i];
            } else {
                right[rightIndex++] = arr[i];
            }
        }

        return mergeSort(splitArray(left), splitArray(right));
    }

    private static int[] mergeSort(int[] left, int[] right) {
        int[] newArr = new int[left.length + right.length];

        int leftMinIndex = 0, rightMinIndex = 0, arrIndex = 0;
        while (leftMinIndex < left.length && rightMinIndex < right.length) {
            if (left[leftMinIndex] > right[rightMinIndex]) {
                newArr[arrIndex++] = right[rightMinIndex++];
            } else {
                newArr[arrIndex++] = left[leftMinIndex++];
            }
        }

        if (leftMinIndex < newArr.length) {
            for (int i = leftMinIndex; i < left.length; i++) {
                newArr[arrIndex++] = left[i];
            }
        }

        if (rightMinIndex < newArr.length) {
            for (int i = rightMinIndex; i < right.length; i++) {
                newArr[arrIndex++] = right[i];
            }
        }

        return newArr;
    }

}
