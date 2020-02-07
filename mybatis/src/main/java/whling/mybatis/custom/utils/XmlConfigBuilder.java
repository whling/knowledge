package whling.mybatis.custom.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import whling.mybatis.custom.Configuration;
import whling.mybatis.custom.Mapper;
import whling.mybatis.custom.io.Resources;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlConfigBuilder {

    public static Configuration loadConfig(InputStream inputStream) {

        //创建SaxReader对象
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            List<Node> selectNodes = document.selectNodes("//property");
            String driver = null,url=null,username=null,password=null;
            for (Node list : selectNodes) {
                Element element = (Element) list;
                String name = element.attributeValue("name");
                String value = element.attributeValue("value");
                //封装成对象
                if ("driver".equals(name)) {
                    driver = value;
                }
                if ("url".equals(name)) {
                    url = value;
                }
                if ("username".equals(name)) {
                    username=value;
                }
                if ("password".equals(name)) {
                    password=value;
                }
            }
            Configuration configuration = new Configuration(driver, username, password, url);
            //获取所有的mapper标签
            List<Node> mapperElements = document.selectNodes("//mapper");
            for (Node list : mapperElements) {
                Element element = (Element) list;
                String resource = element.attributeValue("resource");
                //解析映射配置文件
                Map<String, Mapper> mapper = loadMapper(resource);
                configuration.addMapper(mapper);
            }
            return configuration;
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 解析映射配置文件
     *
     * @param resource
     */
    private static Map<String, Mapper> loadMapper(String resource) {
        Map<String, Mapper> map = new HashMap();
        SAXReader saxReader = new SAXReader();
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            //获取 namespace 标签值
            String namespace = rootElement.attributeValue("namespace");
            //获取select标签
            List<Node> selectNodes = document.selectNodes("//select");
            for (Node list : selectNodes) {
                Element element = (Element) list;
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String sql = element.getTextTrim();
                Mapper mapper = new Mapper();
                mapper.setSql(sql);
                mapper.setReturnType(Class.forName(resultType));
                //通过nameSpace + 方法名 作为唯一标识
                map.put(namespace + "." + id, mapper);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
