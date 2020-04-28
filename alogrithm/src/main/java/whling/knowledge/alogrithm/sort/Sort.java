package whling.knowledge.alogrithm.sort;

import java.util.Arrays;

public class Sort {

    public static void main(String[] args) {

        Integer[] intArr = {8, 29, 87, 1, 98, 23, 32, 12};

//        dubbleSort(intArr);
//
//        Arrays.asList(intArr).stream().forEach(System.out::println);
//        System.out.println("========");


        selectSort(intArr);
        System.out.println(Arrays.toString(intArr));
        System.out.println("========");


        selectSort2(intArr);
        System.out.println(Arrays.toString(intArr));
        System.out.println("========");

        mergeSort(intArr);
        System.out.println(Arrays.toString(intArr));
        System.out.println("========");
    }

    static void dubbleSort(Integer[] intArr) {
        for (int i = 0; i < intArr.length; i++) {
            for (int j = 0; j < intArr.length - i - 1; j++) {
                if (intArr[j] > intArr[j + 1]) {
                    Integer temp = intArr[j];
                    intArr[j] = intArr[j + 1];
                    intArr[j + 1] = temp;
                }
            }
        }
    }

    static void selectSort(Integer[] intArr) {
        for (int i = 0; i < intArr.length; i++) {
            for (int j = i + 1; j < intArr.length; j++) {
                if (intArr[i] > intArr[j]) {
                    Integer temp = intArr[j];
                    intArr[j] = intArr[i];
                    intArr[i] = temp;
                }
            }
        }
    }

    static void selectSort2(Integer[] intArr) {
        for (int i = 0; i < intArr.length; i++) {
            int tempMin = i;
            for (int j = i + 1; j < intArr.length; j++) {

                if (intArr[i] > intArr[j]) {
                    tempMin = j;
                }


            }
            int temp = intArr[tempMin];
            intArr[tempMin] = intArr[i];
            intArr[i] = temp;
        }
    }


    /*
     * 归并排序，O(nlogn)
     */
    static Integer[] mergeSort(Integer[] intArr) {
        if (intArr.length <= 1) {
            return intArr;
        }
        int mid = intArr.length / 2;

        Integer[] left = new Integer[mid];
        Integer[] right = new Integer[intArr.length - mid];

        for (int i = 0, l = 0, r = 0; i < intArr.length; i++) {
            if (i < mid) {
                left[l++] = intArr[i];
            } else {
                right[r++] = intArr[i];
            }
        }

        return mergeArrays(mergeSort(left), mergeSort(right));
    }

    static Integer[] mergeArrays(Integer[] left, Integer[] right) {
        Integer[] res = new Integer[left.length + right.length];

        int l = 0, r = 0;

        for (int i = 0; i < left.length + right.length; i++) {
            if (r == right.length) {
                res[i] = left[l++];
            } else if (l == left.length) {
                res[i] = right[r++];
            } else if (left[l] > right[r]) {
                res[i] = right[r++];
            } else {
                res[i] = left[l++];
            }
        }

        return res;
    }

}
