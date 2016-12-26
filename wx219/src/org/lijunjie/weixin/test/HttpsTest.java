package org.lijunjie.weixin.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.lijunjie.weixin.util.MyX509TrustManager;


public class HttpsTest {

	public static void main(String[] args) throws Exception {
		
		// 创建SSLContext
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		TrustManager[] tm = { new MyX509TrustManager() };
		// 初始化
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 获取SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		
		URL url = new URL("https://kyfw.12306.cn/");
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		// 设置当前实例使用的SSLSocketFactory
		conn.setSSLSocketFactory(ssf);
		conn.connect();

		// 读取服务器端返回的内容
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
		        buffer.append(line);
		}

		System.out.println(buffer.toString());

	}
}
