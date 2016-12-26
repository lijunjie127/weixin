package org.lijunjie.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.lijunjie.weixin.util.AdvancedUtil;
import org.lijunjie.weixin.util.CommonUtil;

public class TemplateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

//		System.out.println(request.getParameter("touser"));
//		System.out.println(request.getParameter("data"));
//		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
//		System.out.println(json);
//		System.out.println(request.getAttribute(null));
		
		@SuppressWarnings("rawtypes")
		Map map = request.getParameterMap();
//		System.out.println("request.getParameterMap():" + map);
		String template = map.keySet().toString().substring(1,map.keySet().toString().length()-1);
//		System.out.println(template);
		
//		String template = request.getParam;
		System.out.println(template);
//		
		String accessToken = CommonUtil.getCurrentAccessToken();
		String respJSON = null;
		
		try {
			respJSON = AdvancedUtil.sendTemplateMessage_retuenString(accessToken, template);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JSONObject jsonObject = JSONObject.fromObject(respJSON);
//			System.out.println("respJSON:" + respJSON);
//			System.out.println("jsonObject:" + jsonObject);
			if (null != jsonObject) {
				int errorCode = jsonObject.getInt("errcode");
				String errorMSg = jsonObject.getString("errmsg");
				if (0 == errorCode) {
					out.println("success");
				} else {
					out.println("模板消息发送失败errcode:" + errorCode);
					out.println("errorMSg:" + errorMSg);
				}
			}
			out.flush();
			out.close();
		}
	}

}
