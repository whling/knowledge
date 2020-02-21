package whling.java.agent.hotdeploy;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义类加载器，并override findClass方法
 * 目的：打破双亲委派模型
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 我们实现的ClassLoader都继承于java.lang.ClassLoader类,父加载器都是AppClassLoader，
     * 所以在上层逻辑中依旧要保证该模型，所以一般不覆盖loadClass函数
     *
     *
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream is = this.getClass().getResourceAsStream(fileName);
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}