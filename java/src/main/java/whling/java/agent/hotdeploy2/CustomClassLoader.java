package whling.java.agent.hotdeploy2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class CustomClassLoader extends ClassLoader {

    private String classPath;

    public CustomClassLoader(String classPath) {
        super();
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        //返回加载后的Class对象
        return this.defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        try {
            name = name.replace(".", "/");
            FileInputStream is = new FileInputStream(new File(classPath + name + ".class"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = is.read()) != -1) {
                bos.write(b);
            }
            is.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
