package org.lijunjie.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lijunjie.weixin.pojo.SNSUserInfo;
import org.lijunjie.weixin.pojo.WeixinOauth2Token;
import org.lijunjie.weixin.util.AdvancedUtil;

public class OAuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");
		//获取code
		String code = request.getParameter("code"); 
		System.out.println("授权码：" + code);
		
		//用户同意授权
		if(null != code){
			//用code换取access_token（同时得到OpenID）
			WeixinOauth2Token wot = AdvancedUtil.getOAuth2Token("wxf23b20ca38223427", "8df6ceb11ac976cc5c98e26f59a55e10", code);
			System.out.println("用户的OpenId：" + wot.getOpenid());
			
			String openId = wot.getOpenid();
			String codeAccessToken = wot.getAccess_token();
			//获取用户基本信息
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(codeAccessToken, openId);
			System.out.println("昵称：" + snsUserInfo.getNickname());
			
			request.setAttribute("openId", wot.getOpenid());
			request.setAttribute("nickname", snsUserInfo.getNickname());
			request.setAttribute("sex", snsUserInfo.getSex());
			request.setAttribute("headimgurl", snsUserInfo.getHeadimgurl());
			request.setAttribute("city", snsUserInfo.getCity());
			
			System.out.println("wot.getOpenid():" + wot.getOpenid());
			System.out.println("snsUserInfo.getNickname():" + snsUserInfo.getNickname());
			System.out.println("snsUserInfo.getSex():" + snsUserInfo.getSex());
			System.out.println("snsUserInfo.getHeadimgurl():" + snsUserInfo.getHeadimgurl());
			System.out.println("snsUserInfo.getCity():" + snsUserInfo.getCity());
			
//			request.setAttribute("city", snsUserInfo.getCity());
//			request.setAttribute("country", snsUserInfo.getCountry());
//			request.setAttribute("headimgurl", snsUserInfo.getHeadimgurl());
//			request.setAttribute("nickname", snsUserInfo.getNickname());
//			request.setAttribute("openid", snsUserInfo.getOpenid());
//			request.setAttribute("privilege", snsUserInfo.getPrivilege());
//			request.setAttribute("province", snsUserInfo.getProvince());
//			request.setAttribute("sex", snsUserInfo.getSex());
//			request.setAttribute("unionid", snsUserInfo.getUnionid());
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
