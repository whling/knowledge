package whling.java.concurrent;

import java.util.ArrayList;

public class Thread_Volatile implements Runnable {

    private /*volatile*/ int count = 100;

    @Override
    public void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count:" + count);
    }

    public static void main(String[] args) {
        Thread_Volatile t = new Thread_Volatile();
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Thread(t, "t-" + i));
        }

        list.parallelStream().forEach(Thread::start);
    }
}
