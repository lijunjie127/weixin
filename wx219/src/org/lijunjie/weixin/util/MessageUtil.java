package org.lijunjie.weixin.util;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.lijunjie.weixin.message.Articles;
import org.lijunjie.weixin.message.ImageMessage;
import org.lijunjie.weixin.message.MusicMessage;
import org.lijunjie.weixin.message.NewsMessage;
import org.lijunjie.weixin.message.TextMessage;
import org.lijunjie.weixin.message.VideoMessage;
import org.lijunjie.weixin.message.VoiceMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {

	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	public static final String REQ_MESSAGE_TYPE_LIKE = "link";
	
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	
	public static final String REPS_MESSAGE_TYPE_TEXT = "text";
	public static final String REPS_MESSAGE_TYPE_IMAGE = "image";
	public static final String REPS_MESSAGE_TYPE_VOICE = "voice";
	public static final String REPS_MESSAGE_TYPE_VIDEO = "video";
	public static final String REPS_MESSAGE_TYPE_MUSIC = "music";
	public static final String REPS_MESSAGE_TYPE_NEWS = "news";
	
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	
	public static HashMap<String,String> parseXML(HttpServletRequest request) throws Exception{
		//解析XML，输出键值对
		HashMap<String,String> map = new HashMap<String, String>();
		
		//通过IO获得Document
		SAXReader reader = new SAXReader();
		Document doc = reader.read(request.getInputStream());
		
		//得到XML根节点
		Element root = doc.getRootElement();
		
		recursivepaserXML(root,map);
		
		return map;
		
	}

	@SuppressWarnings("unchecked")
	private static void recursivepaserXML(Element root, HashMap<String, String> map) {
		//得到根节点的子节点列表
		List<Element> elementList = root.elements();
		
		//判断有没有子元素列表
		if(elementList.size() == 0){
			map.put(root.getName(), root.getTextTrim());
		}else{
			//遍历
			for(Element e : elementList){
				recursivepaserXML(e, map);
			}
		}
		
	}
	
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				boolean cdata = true;
				
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz){
					super.startNode(name,clazz);
				}
				
				protected void writeText(QuickWriter writer,String text){
					if(cdata){
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else{
						writer.write(text);
					}
				}
			};
		}
	}) ;
	
	public static String messageToXML(TextMessage textMessage){
		xstream.alias("xml", TextMessage.class);
		return xstream.toXML(textMessage);
	}
	
	public static String messageToXML(ImageMessage imageMessage){
		xstream.alias("xml", ImageMessage.class);
		return xstream.toXML(imageMessage);
	}
	
	public static String messageToXML(MusicMessage musicMessage){
		xstream.alias("xml", MusicMessage.class);
		return xstream.toXML(musicMessage);
	}
	
	public static String messageToXML(NewsMessage newsMessage){
		xstream.alias("xml", NewsMessage.class);
		xstream.alias("item", Articles.class);
		return xstream.toXML(newsMessage);
	}
	
	public static String messageToXML(VideoMessage videoMessage){
		xstream.alias("xml", VideoMessage.class);
		return xstream.toXML(videoMessage);
	}
	
	public static String messageToXML(VoiceMessage voiceMessage){
		xstream.alias("xml", VoiceMessage.class);
		return xstream.toXML(voiceMessage);
	}
}
