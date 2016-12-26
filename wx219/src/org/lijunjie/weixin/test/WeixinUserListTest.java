package org.lijunjie.weixin.test;

import org.lijunjie.weixin.pojo.WeixinUserList;
import org.lijunjie.weixin.util.AdvancedUtil;
import org.lijunjie.weixin.util.CommonUtil;

public class WeixinUserListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String accessToken = CommonUtil.getCurrentAccessToken();
		WeixinUserList weixinUserList = AdvancedUtil.getWeixinUserList(accessToken, "");
		System.out.println("总关注用户数：" + weixinUserList.getTotal());
		System.out.println("总获取的用户数：" + weixinUserList.getCount());
		System.out.println("OPENID列表：" + weixinUserList.getOpenIdList());
		System.out.println("下一次拉取的OPENID：" + weixinUserList.getNext_openid());
	}

}
