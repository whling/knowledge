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

        Arrays.asList(intArr).stream().forEach(System.out::println);
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

}
