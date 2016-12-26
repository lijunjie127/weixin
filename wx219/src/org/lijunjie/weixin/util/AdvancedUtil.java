package org.lijunjie.weixin.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.lijunjie.weixin.pojo.SNSUserInfo;
import org.lijunjie.weixin.pojo.Template;
import org.lijunjie.weixin.pojo.WeixinOauth2Token;
import org.lijunjie.weixin.pojo.WeixinUser;
import org.lijunjie.weixin.pojo.WeixinUserList;

public class AdvancedUtil {

	/**
	 * 获取用户列表
	 * @param accessToken 接口访问凭证
	 * @param nextOpenId 下一次拉取的OpenId,首次获取可以为空
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static WeixinUserList getWeixinUserList(String accessToken, String nextOpenId) {
		WeixinUserList weixinUserList = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		requestUrl = requestUrl.replace("NEXT_OPENID", nextOpenId);

		// 获取用户列表
		String respJSON = CommonUtil.httpsRequest(requestUrl, "GET", null);
//		System.out.println("respJSON:" + respJSON);
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setCount(jsonObject.getInt("count"));
				weixinUserList.setNext_openid(jsonObject.getString("next_openid"));
				weixinUserList.setTotal(jsonObject.getInt("total"));
				
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.toList(dataObject.getJSONArray("openid"),String.class));
			} catch (Exception e) {
				weixinUserList = null;
				
				int errorCode = jsonObject.getInt("errcode");
				String errorMSg = jsonObject.getString("errmsg");
				System.out.println("用户信息获取失败errcode:" + errorCode + "  " + errorMSg);
			}
		}
		return weixinUserList;
	}
	
	/**
	 * 获取用户基本信息
	 * @param accessToken 借口访问凭证
	 * @param openid 用户的OPENID
	 * @return
	 */
	public static WeixinUser getUserInfo(String accessToken, String openid) {
		WeixinUser weixinUser = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		requestUrl = requestUrl.replace("OPENID", openid);

		// 获取用户基本信息
		String respJSON = CommonUtil.httpsRequest(requestUrl, "GET", null);
		System.out.println("respJSON:" + respJSON);
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		if (null != jsonObject) {
			try {
				weixinUser = new WeixinUser();
				weixinUser.setSubscribe(jsonObject.getInt("subscribe"));
				weixinUser.setOpenid(jsonObject.getString("openid"));
				weixinUser.setNickname(jsonObject.getString("nickname"));
				weixinUser.setSex(jsonObject.getInt("sex"));
				weixinUser.setLanguage(jsonObject.getString("language"));
				weixinUser.setCity(jsonObject.getString("city"));
//					System.out.println(jsonObject.getString("city"));
				weixinUser.setProvince(jsonObject.getString("province"));
//					System.out.println(jsonObject.getString("province"));
				weixinUser.setCountry(jsonObject.getString("country"));
//					System.out.println(jsonObject.getString("country"));
				weixinUser.setHeadimgurl(jsonObject.getString("headimgurl"));
				weixinUser.setSubscribe_time(jsonObject.getInt("subscribe_time"));
//				weixinUser.setUnionid(jsonObject.getString("unionid"));
				weixinUser.setRemark(jsonObject.getString("remark"));
				weixinUser.setGroupid(jsonObject.getInt("groupid"));
			} catch (Exception e) {
				if(0 == weixinUser.getSubscribe()){
					System.out.println("用户" + weixinUser.getOpenid() + "已经取消关注了微信公众号");
				}else{
//					int errorCode = jsonObject.getInt("errcode");
//					String errorMSg = jsonObject.getString("errmsg");
					System.out.println("用户信息获取失败errcode:");
				}
			}
		}
		return weixinUser;
	}
	
	/**
	 * 通过code换取access_token
	 * @param appID
	 * @param appSecret
	 * @param code 授权码
	 * @return
	 */
	public static WeixinOauth2Token getOAuth2Token(String appID, String appSecret, String code) {
		WeixinOauth2Token wot = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appID);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);

		// 接口调用
		String respJSON = CommonUtil.httpsRequest(requestUrl, "GET", null);
		System.out.println("respJSON:" + respJSON);
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		if (null != jsonObject) {
			try {
				wot = new WeixinOauth2Token();
				wot.setAccess_token(jsonObject.getString("access_token"));
				wot.setExpires_in(jsonObject.getInt("expires_in"));
				wot.setRefresh_token(jsonObject.getString("refresh_token"));
				wot.setOpenid(jsonObject.getString("openid"));
				wot.setScope(jsonObject.getString("scope"));
//				wot.setUnionId(jsonObject);
				
//				System.out.println("wot.getAccess_token() " + wot.getAccess_token());
//				System.out.println("wot.getExpires_in() " + wot.getExpires_in());
//				System.out.println("wot.getOpenid() " + wot.getOpenid());
//				System.out.println("wot.getRefresh_token() " + wot.getRefresh_token());
//				System.out.println("wot.getScope() " + wot.getScope());
				
			} catch (Exception e) {
				wot = null;
				int errorCode = jsonObject.getInt("errcode");
//				String errorMSg = jsonObject.getString("errmsg");
				System.out.println("通过code换取access_token失败 errcode:" + errorCode);
			}
		}
		return wot;
	}
	
	/**
	 * 网页授权获取用户信息
	 * @param accesstoken
	 * @param openId
	 * @return
	 */
	public static SNSUserInfo getSNSUserInfo(String accesstoken, String openId) {
		SNSUserInfo snsUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accesstoken);
		requestUrl = requestUrl.replace("OPENID", openId);

		// 接口调用
		String respJSON = CommonUtil.httpsRequest(requestUrl, "GET", null);
		System.out.println("respJSON:" + respJSON);
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setOpenid(jsonObject.getString("openid"));
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				snsUserInfo.setProvince(jsonObject.getString("province"));
				snsUserInfo.setCity(jsonObject.getString("city"));
				snsUserInfo.setCountry(jsonObject.getString("country"));
				snsUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
//				snsUserInfo.setPrivilege(jsonObject.getJSONArray("privilege"));
				snsUserInfo.setPrivilege(JSONArray.toList(jsonObject.getJSONArray("privilege"),List.class));
//				snsUserInfo.setUnionid(jsonObject.getString("unionid"));
//				snsUserInfo.set(jsonObject.getString("access_token"));
//				wot.setExpiresIn(jsonObject.getInt("expires_in"));
//				wot.setRefresToken(jsonObject.getString("refresh_token"));
//				wot.setOpenId(jsonObject.getString("openid"));
//				wot.setScope(jsonObject.getString("scope"));
//				wot.setUnionId(jsonObject);
				
			} catch (Exception e) {
				e.printStackTrace();
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
//				String errorMSg = jsonObject.getString("errmsg");
				System.out.println("网页授权获取用户信息失败 errcode:" + errorCode);
			}
		}
		return snsUserInfo;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param accessToken
	 * @param template
	 * @return
	 */
	public static boolean sendTemplateMessage(String accessToken,Template template) {
		boolean result = false;
		// 接收请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		System.out.println("1accessToken: " + accessToken);
		// 发送模板消息
		String respJSON = CommonUtil.httpsRequest(requestUrl, "POST",
				template.toJSON());
		System.out.println("respJSON:" + respJSON);
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
//			String errorMSg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				System.out.println("模板消息发送失败errcode:" + errorCode);
			}
		}
		return result;
	}
	
	/**
	 * 发送模板消息
	 * 
	 * @param accessToken
	 * @param String
	 * @return
	 */
	public static boolean sendTemplateMessage(String accessToken,String template) {
		boolean result = false;
		// 接收请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		System.out.println("1accessToken: " + accessToken);
		// 发送模板消息
		String respJSON = CommonUtil.httpsRequest(requestUrl, "POST", template);
		System.out.println("respJSON:" + respJSON);
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
//			String errorMSg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				System.out.println("模板消息发送失败errcode:" + errorCode);
			}
		}
		return result;
	}
	
	/**
	 * 发送模板消息
	 * 
	 * @param accessToken
	 * @param template
	 * @return
	 */
	public static String sendTemplateMessage_retuenString(String accessToken,String template) {
		// 接收请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
//		System.out.println("1accessToken: " + accessToken);
		// 发送模板消息
		String respJSON = CommonUtil.httpsRequest(requestUrl, "POST", template);
//		System.out.println("respJSON:" + respJSON);
//		JSONObject jsonObject = JSONObject.fromObject(respJSON);
//		if (null != jsonObject) {
//			int errorCode = jsonObject.getInt("errcode");
////			String errorMSg = jsonObject.getString("errmsg");
//			if (0 == errorCode) {
//				result = true;
//			} else {
//				System.out.println("模板消息发送失败errcode:" + errorCode);
//			}
//		}
		return respJSON;
	}
}
