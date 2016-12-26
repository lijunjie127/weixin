package org.lijunjie.weixin.util;

import net.sf.json.JSONObject;

/**
 * 菜单工具类
 * @author LiJunjie
 * @date 2016年11月2日 14:32:53
 */
public class MenuUtil {
	private final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private final static String MENU_DEL_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建菜单
	 * @param json 菜单结构
	 * @param accessToken 接口访问凭证
	 * @return true| false
	 */
	public static boolean createMenu(String json,String accessToken){
		boolean result = false;
		//拼接请求地址
		String requestUrl = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
		//调用接口
		String respJSON = CommonUtil.httpsRequest(requestUrl, "POST", json);
		//将json字符串转换成JSONObject对象
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		
		if(null != jsonObject){
			int errCode = jsonObject.getInt("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(0 == errCode){
				result = true ;
				System.out.println(errMsg);
			}else{
				result = false;
				System.out.println(errMsg + "\n菜单创建失败：" + respJSON);
			}
		}
		
		return result;
	}
	
	/**
	 * 删除菜单（删除全部菜单）
	 * @param json 菜单结构
	 * @param accessToken 接口访问凭证
	 * @return true| false
	 */
	public static boolean delMenu(String accessToken){
		boolean result = false;
		//拼接请求地址
		String requestUrl = MENU_DEL_URL.replace("ACCESS_TOKEN", accessToken);
		//调用接口
		String respJSON = CommonUtil.httpsRequest(requestUrl, "POST", null);
		//将json字符串转换成JSONObject对象
		JSONObject jsonObject = JSONObject.fromObject(respJSON);
		
		if(null != jsonObject){
			int errCode = jsonObject.getInt("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(0 == errCode){
				result = true ;
				System.out.println(errMsg);
			}else{
				result = false;
				System.out.println(errMsg + "\n菜单删除失败：" + respJSON);
			}
		}
		
		return result;
	}
	
	
}
