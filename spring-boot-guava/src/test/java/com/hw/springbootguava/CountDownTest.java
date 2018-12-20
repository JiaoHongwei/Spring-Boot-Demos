package com.hw.springbootguava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/12/20 15:02
 * @Version 1.0
 */
public class CountDownTest {
    static int concurrentCount = 50;
    static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(concurrentCount);

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(concurrentCount, concurrentCount, 50, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < concurrentCount; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        COUNT_DOWN_LATCH.await();
                        sendRequest();
                    } catch (InterruptedException | MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });
            COUNT_DOWN_LATCH.countDown();
        }
        executor.shutdown();
    }

    private static void sendRequest() throws MalformedURLException {
        String id = String.valueOf(UUID.randomUUID()).substring(24);
        final URL url = new URL("http://localhost:8080/hello?id=" + id);
        String result = sendGet(url);
        System.out.println(result);
    }


    /**
     * get请求
     *
     * @param realUrl
     * @return
     */
    public static String sendGet(URL realUrl) {
        String result = "";
        BufferedReader in = null;
        try {
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
