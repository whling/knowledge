package whling.springmvc.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private static CloseableHttpClient httpclient = createHttpClientDefault();

    private static final String DEFAULT_CHARSET_UTF8 = "UTF-8";

    private static final String DEFAULT_CONTENT_TYPE_JSON = "application/json";

    private static final int MAX_TIMEOUT = 10000;

    private static final int MAX_RETRY_TIMES = 10;

    private static final int MAX_THREAD_TOTAL = 50;

    /**
     * 发送http post请求
     *
     * @param action
     * @param bodyParam
     * @return
     * @throws Exception
     */

    public static String post(String action, Object bodyParam) throws Exception {

        return post(action, null, bodyParam, null, null);

    }

    /**
     * 发送http post请求
     *
     * @param action
     * @param bodyParam
     * @return
     * @throws Exception
     */

    public static String post(String action, Map<String, String> headerParam, Object bodyParam) throws Exception {

        return post(action, headerParam, bodyParam, null, null);

    }

    /**
     * 发送http post请求
     *
     * @param action
     * @return
     * @throws Exception
     */

    public static String post(String action, Map<String, String> headerParam, Object bodyParam, String contentType, String charSet) throws Exception {

        String content_type = contentType;

        if (content_type == null || "".equals(content_type)) content_type = DEFAULT_CONTENT_TYPE_JSON;

        String char_set = charSet;

        if (char_set == null || "".equals(char_set)) char_set = DEFAULT_CHARSET_UTF8;

        String url = action;

        LOGGER.info("Post请求地址：" + url);

        HttpPost httpPost = new HttpPost(url);

//header参数

        if (headerParam != null && headerParam.size() > 0) {

            LOGGER.info("Post请求Header：" + JSON.toJSONString(headerParam));

            for (String key : headerParam.keySet()) {

                httpPost.addHeader(key, headerParam.get(key));

            }

        }

//entity数据

        if (bodyParam != null) {

            LOGGER.info("Post请求Body：" + JSON.toJSONString(bodyParam));

            StringEntity entity = new StringEntity(JSON.toJSONString(bodyParam), char_set);

            entity.setContentEncoding(char_set);

            entity.setContentType(content_type);

            httpPost.setEntity(entity);

        }

        String resultStr = "";

        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);

            processCertainStatus(response.getStatusLine().getStatusCode());

            resultStr = EntityUtils.toString(response.getEntity(), "utf-8");

            httpPost.reset();

        } catch (IOException e) {

            LOGGER.error("execute http get connection", e);

        } finally {

            try {

                if (response != null)

                    response.close();

            } catch (IOException e) {

                LOGGER.error("close http get connection", e);

            }

        }

        LOGGER.info("Post请求返回：" + resultStr);

        return resultStr;

    }

    /**
     * 发送http get请求
     *
     * @param action
     * @return
     * @throws Exception
     */

    public static String get(String action) throws Exception {

        String url = action;

        LOGGER.info("Get请求地址：" + url);

        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        CloseableHttpResponse response = null;

        String resultStr = "";

        try {

            response = httpclient.execute(httpGet);

            processCertainStatus(response.getStatusLine().getStatusCode());

            resultStr = EntityUtils.toString(response.getEntity(), "utf-8");

            httpGet.reset();

        } catch (IOException e) {

            LOGGER.error("execute http get connection", e);

        } finally {

            try {

                if (response != null)
                    response.close();
            } catch (IOException e) {

                LOGGER.error("close http get connection", e);

            }

        }

        LOGGER.info("Get请求返回：" + resultStr);

        return resultStr;

    }

    private static void processCertainStatus(int statusCode) {

        if (statusCode == 401) {

//            throw new TokenInvalidException("401 token invalid!");

        }

    }

    /**
     * 发送http get请求
     *
     * @param action
     * @return
     * @throws Exception
     */

    public static String get(String action, Map<String, String> params) throws Exception {

        URIBuilder uriBuilder = new URIBuilder();

        uriBuilder.setPath(action);

        if (params != null) {

            for (String key : params.keySet()) {

                uriBuilder.setParameter(key, params.get(key));

            }

        }

        return get(uriBuilder.build().toString());

    }

    /**
     * 创建httpclient
     *
     * @return
     */

    private static synchronized CloseableHttpClient createHttpClientDefault() {

        CloseableHttpClient httpclient = null;

        try {

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                    return true;
                }

            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

            ConnectionSocketFactory psf = PlainConnectionSocketFactory.getSocketFactory();


            Registry<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf).register("http", psf).build();

            RequestConfig config = RequestConfig.custom().setSocketTimeout(MAX_TIMEOUT).setConnectTimeout(MAX_TIMEOUT).setConnectionRequestTimeout(MAX_TIMEOUT).build();

            //超时重试处理
            HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(MAX_RETRY_TIMES, true);

            //连接管理池
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registryBuilder);

            cm.setValidateAfterInactivity(60000);

            cm.setMaxTotal(MAX_THREAD_TOTAL);

            cm.setDefaultMaxPerRoute(MAX_THREAD_TOTAL);

            httpclient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config).setRetryHandler(retryHandler).build();

        } catch (KeyManagementException e) {
            LOGGER.error("KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException", e);
        } catch (KeyStoreException e) {
            LOGGER.error("KeyStoreException", e);
        }
        return httpclient;
    }

}
