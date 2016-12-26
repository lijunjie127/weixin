package org.lijunjie.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.lijunjie.weixin.pojo.Token;

public class CommonUtil {
	/**
	 * 处理https GET/POST请求
	 * @param requestUtil请求地址
	 * @param requestMethod请求方法（GET/POST）
	 * @param outPutStr参数 
	 * @return
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
		
		StringBuffer buffer = null;
		try {
			// 创建SSLContext
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			TrustManager[] tm = { new MyX509TrustManager() };//指定信任管理器，自定义的信任管理器
			// 初始化
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 获取SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);//请求地址
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);//请求方式
			// 设置当前实例使用的SSLSocketFactory
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.connect();

			//往服务器端写内容
			if(null != outputStr) {
				OutputStream os = conn.getOutputStream();//这句没有执行
				os.write(outputStr.getBytes("utf-8"));
				System.out.println("写入服务器的内容：" + outputStr);
				os.close();
			}
			
			// 读取服务器端返回的内容
			System.out.println("读取服务器端返回的内容:");
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			
			buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
			        buffer.append(line);
			}

//			System.out.println(buffer.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	
	/**
	 * 处理http GET/POST请求
	 * @param requestUtil请求地址
	 * @param requestMethod请求方法（GET/POST）
	 * @param outPutStr参数 
	 * @return
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) throws Exception{
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(requestMethod);
		conn.setDoOutput(true);
		conn.connect();
		
		//往服务器端写内容
		if(null != outputStr) {
			OutputStream os = conn.getOutputStream();
			os.write(outputStr.getBytes("utf-8"));
//			System.out.println("写入服务器的内容：" + outputStr);
			os.close();
		}

		// 读取服务器端返回的内容
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
		        buffer.append(line);
		}

		return buffer.toString();
	}
	
	/**
	 * 获取数据库中存在的access_token，若过期则重新获取
	 * @param 数据库中的第1个字段current创建新的accesstoken的时间（秒）
	 * @param 数据库中的第2个字段accesstoken
	 * @param 数据库中的第3个字段expires有效期（秒）
	 * @return accessToken（String）
	 */
	public static String getCurrentAccessToken(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from wx_accesstoken");
			int current = (int) (System.currentTimeMillis() / 1000); 
			if(rs.next()==false || current>(rs.getInt(1) + rs.getInt(3))){
//				System.out.println(rs.getInt(1) + "\n" + rs.getString(2) + "\n" + rs.getInt(3));
				Token token = CommonUtil.getNewAccessToken();
//				System.out.println(current);
//				System.out.println(token.getAccess_token());
//				System.out.println(token.getExpires_in());
				st.executeUpdate("UPDATE wx_accesstoken SET current = " + current + ",accesstoken =  '" + token.getAccess_token() + "',expires = " + token.getExpires_in()*0.9);
				return token.getAccess_token();
			}else{
//				System.out.println("token有效：" + rs.getString(2));
//				System.out.println("剩余时间：" + (rs.getInt(3)-(current-rs.getInt(1))) + "秒");
				return rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.release(rs, st, conn);
		}
		return null;
	}
	
	/**
	 * 获取access_token的请求地址（GET）
	 */
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf23b20ca38223427&secret=8df6ceb11ac976cc5c98e26f59a55e10";
	
	/**
	 * 获取access_token
	 * @param appid公众号的唯一凭证
	 * @param appsecret密钥
	 * @return accessToken（java对象）
	 */
	public static Token getNewAccessToken(){//String appid, String appsecret
		Token token = new Token();
		//拼接请求地址,这里的token_url是已经拼接好的，不用动了
		String requestUrl = token_url;
		//调用接口，获取json字符串
		String jsonString = CommonUtil.httpsRequest(requestUrl, "GET", null);
		//将json字符串转换成java对象
		{
			String[] strTemp = jsonString.split("\":\"");
			String json = strTemp[1];
			strTemp = json.split("\",\"");
//			System.out.println(strTemp[0]);//****************
			token.setAccess_token(strTemp[0]);//获取access_token
			
			json = strTemp[1];
			strTemp = json.split("\":");
			json = strTemp[1].substring(0, strTemp[1].length()-1);
//			System.out.println(Integer.parseInt(json));//*************
			token.setExpires_in(Integer.parseInt(json));//获取expires_in
		}
		
		return token;
	}
	
	/**
	 * 处理解析json数据，得到java对象（获取accessToken）
	 * @param json数据
	 * @return accessToken（java对象）
	 */
	@SuppressWarnings("null")
	public static Token json_To_Token(String json){
		
		Token token = null;
		
		String[] strTemp = json.split("\":\"");
		json = strTemp[1];
//		System.out.println(json);
		strTemp = json.split("\",\"");
		token.setAccess_token(strTemp[0]);//获取access_token
		System.out.println(strTemp[0]);//****************
		
		json = strTemp[1];
		strTemp = json.split("\":");
		System.out.println(strTemp[1]);//*************
//		token.setExpires_in((int)strTemp[1]);
		return token;//现在的json已经不是之前的json了
	}
	
	/**
	 * 对链接地址进行UTF-8编码
	 * @param url
	 * @return
	 */
	public static String urlEncodeUTF8(String url){
		String encodeUrl = url;
		
		try {
			encodeUrl = java.net.URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeUrl;
	}
	
	public static void main(String[] args) {//http://wx.cisscool.cn/wx219/oauthServlet
		System.out.println(urlEncodeUTF8("http://cisscool.imwork.net/wx219/oauthServlet"));
		System.out.println(urlEncodeUTF8("http://wx.cisscool.cn/wx219/oauthServlet"));
	}
}
