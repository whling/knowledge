package whling.java;

/**
 * Integer判断==遇到的坑
 *     在范围[-128,127]的Integer做等于比较，不要用==
 *     2中方法解决：
 *      1）将Integer转为int
 *      2) 使用Integer的equal方法
 */
public class IntegerTest {

    public static void main(String[] args) {


        test1();
        test2();
        test3();
    }

    /**
     * Integer i = 1234;会进行自动装箱，等价于Integer i = Integer.valueOf(1234);
     * 该方法有个逻辑就是，如果在缓存范围之内的，直接返回，不在的话，就会创建对象，该缓存默认IntegerCache.low 是-128，Integer.high是127，范围[-128,127]
     *
     */
    private static void test1() {
        Integer i = 1234;
        Integer j = 1234;

        System.out.println(i == j);
    }

    private static void test2() {
        Integer i = -128;
        Integer j = -128;

        System.out.println(i == j);
    }


    private static void test3() {
        int i = -128;
        int j = -128;

        System.out.println(i == j);

        i = 1234;
        j = 1234;

        System.out.println(i == j);
    }
}
