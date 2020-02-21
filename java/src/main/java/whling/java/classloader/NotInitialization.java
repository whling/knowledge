package whling.java.classloader;

/**
 * 通过子类引用父类的静态字段，不会导致子类初始化。
 * <p>
 * 对于静态字段，只有直接定义这个字段的类才会被初始化，因此通过其子类来引用父类中定义的静态字段，只会触发父类的初始化而不会触发子类的初始化。
 */
class SuperClass {
    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;
}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
    }
}

class ConstClass {
    static {
        System.out.println("ConstClass init!");
    }

    /**
     * 常量在编译阶段会存入调用类的常量池中(注意是调用类)，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。
     */
    public static final String HELLO_BINGO = "Hello Bingo";
}


public class NotInitialization {

    public static void main(String[] args) {
        /**
         * 不会触发父类的初始化，但会触发“[L 全类名”这个类的初始化，它由虚拟机自动生成，直接继承自 java.lang.Object，创建动作由字节码指令 newarray 触发。
         */
        SuperClass[] superClasses = new SuperClass[10];

        System.out.println("=====");
        System.out.println(SubClass.value);
        // SuperClass init!

        /**
         * 编译通过之后，常量存储到 NotInitialization 类的常量池中，NotInitialization 的 Class 文件中并没有 ConstClass 类的符号引用入口，
         * 这两个类在编译成 Class 之后就没有任何联系了。
         */
        System.out.println(ConstClass.HELLO_BINGO);
    }
}
