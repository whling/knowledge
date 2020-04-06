package whling.java.string;

public class StrToBinary {
    public static void main(String[] args) {
        String str = "abc";
        char[] chars = str.toCharArray();
        for (char c : chars) {
            System.out.println(Integer.toBinaryString(c));
        }

        System.out.println("=======");
    }

    //将二进制字符串转换成int数组
    public int[] binstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    //将二进制转换成字符
    public char binstrToChar(String binStr) {
        int[] temp = binstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    public void BinstrToStr() {
        long l1 = 10000_00000_0000L;
        String binStr = "111001110001011 1001011011101010 ";
        String[] tempStr = binStr.split(" ");
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = binstrToChar(tempStr[i]);
        }
        System.out.println(String.valueOf(tempChar));
    }
}
