package whling.knowledge.alogrithm;

public class MoneyAndCount {

    public static void main(String[] args) {

        int y = 100;
        double d = (y - Math.random()) % y;
        System.out.println(d);

        int count = 100;
        double m = 100D;

        for (int i = 0; i < count; i++) {
            double money = 0D;
            if (i == count - 1) {
                money = m;
            } else {
                money = (m - Math.random()) % m;
            }
            System.out.println(money);
            m = m - money;
        }


    }
}
