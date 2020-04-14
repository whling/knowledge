package whling.java.concurrent;

import java.util.Arrays;

public class Thread_Cas {

    static volatile boolean flag = false;

    public static void main(String[] args) throws Exception {
        char[] c1 = "ABCDEFG".toCharArray();
        char[] c2 = "1234567".toCharArray();


        Thread[] ts = new Thread[]{new Thread(() -> {

            for (char c : c1) {
                while (!flag) {
                }

                System.out.print(c);
                flag = !flag;
            }
        }),

                new Thread(() -> {

                    for (char c : c2) {
                        while (flag) {
                        }

                        System.out.print(c);
                        flag = !flag;
                    }
                })};

        Arrays.stream(ts).forEach(Thread::start);

        for (Thread t : ts) {
            t.join();
        }


    }

}
