package whling.java.agent.hotdeploy2;

public class LoadInfo {

    //自定义的类加载器
    private CustomClassLoader myClassLoader;

    //记录要加载的类的时间戳 --加载的时间
    private long loadTime;

    private BaseService baseService;

    public LoadInfo(CustomClassLoader myClassLoader, long loadTime) {
        this.myClassLoader = myClassLoader;
        this.loadTime = loadTime;
    }


    public CustomClassLoader getMyClassLoader() {
        return myClassLoader;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public BaseService getBaseService() {
        return baseService;
    }

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }
}