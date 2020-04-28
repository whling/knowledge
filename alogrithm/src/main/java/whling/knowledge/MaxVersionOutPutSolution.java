package whling.knowledge;

/**
 * 输出一堆版本号中最大的那个
 */
public class MaxVersionOutPutSolution {

    public static void main(String[] args) {
        String s1 = "23.24.01";
        String s2 = "23.24.01.0.1";
        String s3 = "23.25.01.0.1";
        String s4 = "24.26.01.0.1";
        String s5 = "23.27.01.0.1";

        String[] arr = {s1, s2, s3, s4, s5};

        String temp = arr[0];

        for (int i = 0; i < arr.length; i++) {
            temp = maxStr(arr[i], temp);
        }
        System.out.println(temp);
    }


    public static String maxStr(String s1, String s2) {
        String str = s1;

        if (s1.equals(s2)) {
            return str;
        }

        String[] arr1 = s1.split("\\.");
        String[] arr2 = s2.split("\\.");
        int minLen = arr1.length >= arr2.length ? arr2.length : arr1.length;
        int index = 0;

        while (index < minLen) {
            if (!arr1[index].equals(arr2[index])) {
                break;
            }
            index++;
        }

        if (index < minLen) {
            str = arr1[index].compareTo(arr2[index]) > 0 ? s1 : s2;
        } else {
            boolean flag = true;
            if (index == arr1.length) {
                // 如果后面一位为0，则需要返回与之相对应的那个字符串
                for (int i = index; i < arr2.length; i++) {
                    if (!arr2[i].trim().equals("0")) {
                        flag = false;
                    }
                }
                str = flag ? s1 : s2;
            } else {
                for (int i = index; i < arr1.length; i++) {
                    if (!arr1[i].trim().equals("0")) {
                        flag = false;
                    }
                }
                str = flag ? s2 : s1;

            }
        }
        return str;
    }
}
