package org.lijunjie.weixin.test;

import net.sf.json.JSONObject ;

import org.lijunjie.weixin.pojo.BaseButton;
import org.lijunjie.weixin.pojo.ClickButton;
import org.lijunjie.weixin.pojo.ComplexButton;
import org.lijunjie.weixin.pojo.Menu;
import org.lijunjie.weixin.pojo.ViewButton;
import org.lijunjie.weixin.util.CommonUtil;
import org.lijunjie.weixin.util.MenuUtil;

public class MenuTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String accesstoken = CommonUtil.getCurrentAccessToken();
		
		ViewButton cbtn1 = new ViewButton();
		cbtn1.setName("首页");
		cbtn1.setType("view");
		cbtn1.setUrl("https://www.cissdata.com/");
		
		ClickButton cbtn2 = new ClickButton();
		cbtn2.setName("每日歌曲");
		cbtn2.setType("click");
		cbtn2.setKey("KEY_MUSIC");
		
		ViewButton vbtn = new ViewButton();
		vbtn.setName("百度");
		vbtn.setType("view");
		vbtn.setUrl("http://www.baidu.com/");
		
		ComplexButton btn = new ComplexButton();
		btn.setName("菜单");
		btn.setSub_button(new BaseButton[]{cbtn2,vbtn});
		
		//菜单对象
		Menu menu = new Menu();
		menu.setButton(new BaseButton[]{cbtn1,btn});
		
		//将Java对象转换成json字符串      JSONObject.fromObject(menu);//将字符串转换成Java对象
		String json = JSONObject.fromObject(menu).toString();
//		System.out.println(json);
		
		MenuUtil.createMenu(json, accesstoken);
		
//		MenuUtil.delMenu(accesstoken);
		
	}

}
