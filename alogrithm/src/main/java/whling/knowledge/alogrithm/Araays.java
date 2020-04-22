package whling.knowledge.alogrithm;

import java.util.HashMap;

public class Araays {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 4, 6};

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {

            if (map.containsKey(arr[i])) {
                System.out.println(i);
            }
            map.put(arr[i], i);
        }

        System.out.println("==========");
    }
}
