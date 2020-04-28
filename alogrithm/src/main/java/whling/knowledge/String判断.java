package whling.knowledge;

public class String判断 {

    public static void main(String[] args) {

        System.out.println('a'-0); // 97 98 99  97
        System.out.println('Z'-0); // 65 66 67  90
        System.out.println('a' - 32); // 97
        System.out.println('A' - 'a'); // 97
        System.out.println("=======");

        String str = "ABDa";


        char[] chars = str.toCharArray();
        char temp = chars[0]; // A
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (Math.abs(chars[i] - temp) >=7) {
                count++;
            }
            temp = chars[i];
        }

        System.out.println(count);
        System.out.println(count + chars.length);
    }
}
