package whling.knowledge.alogrithm;

public class Sum {

    public static void main(String[] args) {

        int n = 5;
        System.out.println(sum(5));

        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += i;
        }
        System.out.println(sum);

        System.out.println(sum2(5));
    }


    static int sum(int n) {
//        try {
//            int i = 1 / n;
//            return n + sum((--n));
//        } catch (Exception e) {
//        }
//        return n;

        if (n == 0) {
            return n;
        }
        return n + sum((--n));
    }

    /**
     * 通过&&控制
     *
     * @param n
     * @return
     */
    static int sum2(int n) {
        int t = 0;
        boolean b = (n > 0) && (t = (n + sum((--n)))) > 0;
        return t;
    }
}
