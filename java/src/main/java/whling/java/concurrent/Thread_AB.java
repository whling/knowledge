package whling.java.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 线程交替打印ABAB
 */
public class Thread_AB {

    static Object o = new Object();

    static class MyPrinter implements Runnable {

        Object o;
        String content;

        public MyPrinter(Object o, String content) {
            this.o = o;
            this.content = content;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (o) {
                    System.out.print(content);
                    o.notifyAll();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        /**
                         * 对某一线程调用 interrupt()时。假设该线程正在执行普通的代码，那么该线程根本就不会抛出InterruptedException。
                         *
                         * 可是，一旦该线程进入到 wait()/sleep()/join()后，就会立马抛出InterruptedException 。
                         */
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Thread A = new Thread(new MyPrinter(o, "A"));
        A.start();

        new Thread(new MyPrinter(o, "B")).start();



        TimeUnit.SECONDS.sleep(1L);

        A.interrupt();


        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);

    }


}
