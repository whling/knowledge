package whling.knowledge.datastructure.arrays;

import java.util.Arrays;

public class ReserveArray {

    public static void main(String[] args) {

        int[] arr = new int[]{9, 6, 8, 5};

        /**
         * 数组反转
         */
        int n = 0, m = arr.length - 1;

        for (int i = 0; i < arr.length / 2; i++) {
            if (n != m) {

                int temp = arr[n];
                arr[n] = arr[m];
                arr[m] = temp;

                n++;
                m--;
            }
        }

        System.out.println(Arrays.toString(arr));


        /**
         * 数组最大，最小值下标获取
         */
        int minTemp = 0, maxTemp = 0;
        int min = arr[0], max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < arr[minTemp]) {
                minTemp = i;
            }

            if (arr[i] > arr[maxTemp]) {
                maxTemp = i;
            }

            if ((arr[i] < min)) {
                min = arr[i];
            }
            if ((arr[i] > max)) {
                max = arr[i];
            }
        }

        System.out.println(minTemp);
        System.out.println(maxTemp);
        System.out.println(min);
        System.out.println(max);


        int[] arr2 = {9, 6, 8, 5, 4};
        for (int i = 0; i < arr2.length / 2; i++) {

            int temp = arr2[i];
            arr2[i] = arr2[arr2.length - 1 - i];
            arr2[arr2.length - 1 - i] = temp;
        }

        System.out.println("==========");
        System.out.println(Arrays.toString(arr2));


    }
}
