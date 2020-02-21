package whling.java.agent.hotdeploy2;

public class TimeServiceHandler {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    BaseService service = ServiceFactory.getService(ServiceFactory.MY_MANAGER);
                    service.say();

                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }
}
