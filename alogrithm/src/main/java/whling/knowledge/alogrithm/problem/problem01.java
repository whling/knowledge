package whling.knowledge.alogrithm.problem;

/**
 * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）
 */
public class problem01 {

    public static void main(String[] args) {

        System.out.println(slove(5));
    }

    static int slove(int n) {
        if (n == 0) {
            return 0;
        }
        return n + slove(n - 1);
    }
}
