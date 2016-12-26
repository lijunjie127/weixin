package org.lijunjie.weixin.test;

import org.lijunjie.weixin.pojo.WeixinUser;
import org.lijunjie.weixin.util.AdvancedUtil;
import org.lijunjie.weixin.util.CommonUtil;

public class GetUserInfoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//o6QKruFGLDOiDhhnJPuVlP3BEIoM, o6QKruB573shHRr23IXLSJs6Qimg, o6QKruFHp427MoJ-4esoaxLZsMpA, 
		//o6QKruK5qtAHSscziXakZSTsFNQw, o6QKruKqgq8GYl_5FCm03Y5PNutg, o6QKruOSrtxghR1ubXQOLLsBAzCI, 
		//o6QKruHN-dt4mK5yl83KSD5Zqqvk, o6QKruHA2Y2H7e6kuu7HLYwOw2CQ, o6QKruHNMRiLdwh7RWG92tOQino8, 
		//o6QKruOBCzk4y7-hM2ptDPEJ5Pf0, o6QKruKKSLlGiLX58ghA3AwhPteY, o6QKruLcSVsEH3rqQWn4CX3DQfa4
		
		String accessToken = CommonUtil.getCurrentAccessToken();
		String openid = "o6QKruFGLDOiDhhnJPuVlP3BEIoM";//我的openid o6QKruKKSLlGiLX58ghA3AwhPteY
		WeixinUser weixinUser = AdvancedUtil.getUserInfo(accessToken, openid);
		System.out.println(weixinUser.getNickname());
		System.out.println(weixinUser.getCity());
		System.out.println(weixinUser.getProvince());
		System.out.println(weixinUser.getCountry());
	}

}
