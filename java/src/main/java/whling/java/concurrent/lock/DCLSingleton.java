package whling.java.concurrent.lock;

public class DCLSingleton {

    private DCLSingleton() {
    }

    private volatile static DCLSingleton dclSingleton;

    public static DCLSingleton getDclSingleton() {
        if (dclSingleton == null) {
            synchronized (DCLSingleton.class) {
                if (dclSingleton == null) {
                    dclSingleton = new DCLSingleton();
                }
            }
        }
        return dclSingleton;
    }
}
