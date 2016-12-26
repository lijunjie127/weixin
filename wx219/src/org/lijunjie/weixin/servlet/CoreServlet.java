package org.lijunjie.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lijunjie.weixin.service.CoreService;
import org.lijunjie.weixin.util.SignUtil;

public class CoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce     = request.getParameter("nonce");
		String echostr   = request.getParameter("echostr");
		
		PrintWriter out = response.getWriter();
		//如果校验成功
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			out.write(echostr);
		}
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//编码设置
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//请求校验
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce     = request.getParameter("nonce");
		PrintWriter out = response.getWriter();
		//如果校验成功
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			//对消息进行处理
			String respXML = CoreService.processRequest(request);
			out.write(respXML);
		}
	}

}
