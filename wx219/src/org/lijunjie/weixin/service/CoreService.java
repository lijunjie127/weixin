package org.lijunjie.weixin.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.lijunjie.weixin.message.TextMessage;
import org.lijunjie.weixin.util.MessageUtil;

public class CoreService {

	public static String processRequest(HttpServletRequest request){
//		String lianjie = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf23b20ca38223427&redirect_uri=http%3A%2F%2Fcisscool.imwork.net%2Fwx219%2FoauthServlet&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		
		TextMessage tm = new TextMessage();
		//解析微信服务器发送的请求
		try {
			HashMap<String,String> requestMap = MessageUtil.parseXML(request);
			//用户的OpenID
			String fromUserName = requestMap.get("FromUserName");
			//公众号的原始ID
			String toUserName = requestMap.get("ToUserName");
			//请求消息类型
			String msgType = requestMap.get("MsgType");
			
			tm.setFromUserName(toUserName);
			tm.setToUserName(fromUserName);
			tm.setMsgType(MessageUtil.REPS_MESSAGE_TYPE_TEXT);
			tm.setCreateTime(new Date().getTime());
			
			//判断：对不同的消息回复不同的内容
			//文本消息
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
//				tm.setContent("点击<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf23b20ca38223427&redirect_uri=http%3A%2F%2Fcisscool.imwork.net%2Fwx219%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">获取基本信息</a>");
				tm.setContent("点击<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf23b20ca38223427&redirect_uri=http%3A%2F%2Fwx.cisscool.cn%2Fwx219%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">获取基本信息</a>");
			}
			//图片消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
//				String imageUrl = requestMap.get("PicUrl");
//				tm.setContent(FacePlusPlusUtil.detectFace(imageUrl));
			}
			//链接消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LIKE)){
				tm.setContent("您发送的是链接消息！");
			}
			//地理位置消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
				tm.setContent("您发送的是地理位置消息！");
			}
			//视频消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
				tm.setContent("您发送的是视频消息！");
			}
			//语音消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				tm.setContent("您发送的是语音消息！");
			}
			//事件
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				//事件类型
				String eventType = requestMap.get("Event");
				//关注事件
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					tm.setContent("欢迎关注，cisscool！");
					//欢迎关注，李俊杰的微信订阅号：俊杰不在线！\n\n现在公众号增加了人脸识别系统，好玩有趣，赶快回复您的照片参与吧！
				}
				else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					tm.setContent("");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return MessageUtil.messageToXML(tm);
	}
}
