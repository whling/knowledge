package whling.java.jvm;

/**
 * https://www.zhihu.com/question/54780889/answer/542915328
 */
public class StackSOF {

    private int deep = 0;

    public void stackLeak() {
        deep++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackSOF sof = new StackSOF();
        try {
            sof.stackLeak();
        } catch (Throwable e) {
            System.out.println(" stack deep = " + sof.deep + "\n\r" + e);
        }
    }

    /**
     * platform: mac os 10.15,jdk1.8
     * test result:
     *   arg            deep
     * -Xss160K:        772
     * -Xss200K         1238
     * -Xss210K         5855
     * -Xss220K         1471
     * -Xss320K         2634
     */
}