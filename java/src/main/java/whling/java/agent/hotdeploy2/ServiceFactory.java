package whling.java.agent.hotdeploy2;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {

    //加载热加载类的加载信息
    private static final Map<String, LoadInfo> loadTimeMap = new HashMap<String, LoadInfo>();

    //要加载的类的classpath
    public static final String CLASS_PATH = "/Users/whling/IdeaProjects/knowledge/java/target/classes/";

    //实现热加载的类的全名称,包名+类名
    public static final String MY_MANAGER = "whling.java.agent.hotdeploy2.CatService";

    public static void main(String[] args) {
        System.out.println(MY_MANAGER.replace(".", "/") + ".class");
    }

    public static BaseService getService(String className) {
        File loadFile = new File(CLASS_PATH + className.replace(".", "/") + ".class");
        long lastModified = loadFile.lastModified();
        //不包含className为key的loadinfo信息,证明没有被加载,需要加载这么类到JVM,重新加载,
        if (loadTimeMap.get(className) == null) {
            load(className, lastModified);
            //加载类的时间戳变化了,同样重新加载这个类到JVM
        } else if (loadTimeMap.get(className).getLoadTime() != lastModified) {
            load(className, lastModified);
        }
        return loadTimeMap.get(className).getBaseService();
    }

    public static void load(String className, long lastModified) {
        CustomClassLoader myClassLoader = new CustomClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        try {
            loadClass = myClassLoader.findClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BaseService manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(myClassLoader, lastModified);
        loadInfo.setBaseService(manager);
        loadTimeMap.put(className, loadInfo);

    }

    private static BaseService newInstance(Class<?> loadClass) {
        try {
            return (BaseService) loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
