package com.hw.springbootprotostuff;

import com.hw.springbootprotostuff.dto.RestResponse;
import com.hw.springbootprotostuff.utils.ProtostuffUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.UUID;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/12/26 16:32
 * @Version 1.0
 */
public class ControllerTest {


    @Test
    public void test() throws MalformedURLException {

        String id = String.valueOf(UUID.randomUUID()).substring(24);
        final URL url = new URL("http://localhost:8080/api/hello?name=" + id);
        byte[] serializer = sendGet(url);
        System.out.println("请求序列化结果：" + Arrays.toString(serializer));
        Student deserializer = ProtostuffUtil.deserializer(sendGet(url), Student.class);
        System.out.println("请求反序列化：" + deserializer);

    }


    @Test
    public void test1() throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new
                HttpGet("http://localhost:8080/api/hello?name=asaqs");
        HttpResponse result = httpClient.execute(method);
        if (result.getStatusLine().getStatusCode() == 200) {
            byte[] bytes = EntityUtils.toByteArray(result.getEntity());
            Student deserializer = ProtostuffUtil.deserializer(bytes, Student.class);
            System.out.println(deserializer.toString());
        }
    }

    @Test
    public void test2() throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new
                HttpGet("http://localhost:8080/api/test?name=asaqs");
        HttpResponse result = httpClient.execute(method);
        if (result.getStatusLine().getStatusCode() == 200) {

            byte[] bytes = EntityUtils.toByteArray(result.getEntity());
            RestResponse deserializer = ProtostuffUtil.deserializer(bytes, RestResponse.class);
            System.out.println(deserializer.toString());

        }
    }


    private static byte[] sendGet(URL realUrl) {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type", "application/x-protobuf;charset=UTF-8");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            is = connection.getInputStream();
            os = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            return os.toByteArray();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new byte[0];
    }

}
