package whling.java.url;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * get方式只能支持ASCII字符，向服务器传的中文字符可能会乱码。
 * <p>
 * <p>
 * 在服务器端
 * <p>
 * 我们吧“测试”编码一次的字符串%E6%B5%8B%E8%AF%95  ，提交，服务器端用request.getParameter("name")的到参数，然后我们解码
 * <p>
 * System.out.println(java.net.URLDecoder.decode(name, "UTF-8"));
 * <p>
 * 我们发现得到的结果 ???è?  显示乱码。
 * <p>
 * 因为在request.getParameter("name")之前会自动做一次解码的工作，而且是默认的ISO-8859-1，相当于调用了一次java.net.URLDecoder.decode(name, "ISO-8859-1")
 * <p>
 * 所以我们再用URLDecoder.decode(name, "UTF-8")就是乱码了。
 */
public class UrlEncoderDecoderTest {

    public static void main(String[] args) throws Exception {

        String str = "hello world,我好了";

        // hello+world%2C%E6%88%91%E5%A5%BD%E4%BA%86
        System.out.println(URLEncoder.encode(str, "utf-8"));

        System.out.println(URLDecoder.decode("hello+world%2C%E6%88%91%E5%A5%BD%E4%BA%86", "utf-8"));


        System.out.println("======");

        System.out.println(URLEncoder.encode(URLEncoder.encode(str, "ISO-8859-1"), "utf-8"));
        System.out.println(URLDecoder.decode(URLDecoder.decode(str, "utf-8"), "ISO-8859-1"));


    }
}
