package org.lijunjie.weixin.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.lijunjie.weixin.pojo.Template;
import org.lijunjie.weixin.pojo.TemplateParam;
import org.lijunjie.weixin.util.CommonUtil;

public class TemplateMessageTest {

	public static void main(String[] args) {
//		String accessToken = CommonUtil.getCurrentAccessToken();
//		String accessToken = CommonUtil.getNewAccessToken().getAccess_token();

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<TemplateParam> tempateParamList = new ArrayList<TemplateParam>();
		tempateParamList.add(new TemplateParam("first", "询价单状态变更", "#173177"));//"#173177"
		tempateParamList.add(new TemplateParam("keyword1", "询价完成", "#00EE00"));//状态
		tempateParamList.add(new TemplateParam("keyword2", "4522716526431623", "#173177"));//单号
		tempateParamList.add(new TemplateParam("keyword3", dateFormater.format(new Date()), "#173177"));//时间
		tempateParamList.add(new TemplateParam("remark", "点击查看询价详情", "#173177"));

		Template template = new Template();
		template.setTemplateId("LgN3OHuk_LtwYy-scNCPpI29-Ed9CuMpsKHgAt-UDbE");// 此处填写模板消息ID
		template.setToUser("o6QKruKKSLlGiLX58ghA3AwhPteY");
		template.setTopColor("#173177");
		template.setUrl("https://www.cissdata.com/");
		template.setTemplateParamList(tempateParamList);
		
		System.out.println(template.toJSON());
		
		try {//			http://wx.cisscool.cn/weixin219/coreService
//			String respJSON = CommonUtil.httpRequest("http://wx.cisscool.cn/template/myServlet", "POST", template.toJSON());//template.toJSON()
//			String respJSON = CommonUtil.httpRequest("http://127.0.0.1:8080/template/myServlet", "GET", template.toJSON());
			String respJSON = CommonUtil.httpRequest("http://wx.cisscool.cn/wx219/templateServlet", "POST", template.toJSON());
			System.out.println("服务器返回结果：" + respJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
