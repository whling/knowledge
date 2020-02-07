package whling.mybatis.custom.io;

import java.io.IOException;
import java.io.InputStream;

public class Resources {

    public static InputStream getResourceAsStream(String resource) throws IOException {
        return Resources.class.getClassLoader().getResourceAsStream(resource);
    }
}
