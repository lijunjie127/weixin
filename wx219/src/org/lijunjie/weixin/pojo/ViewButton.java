package org.lijunjie.weixin.pojo;

/**
 * view类型的按钮
 * @author LiJunjie
 * @date 2016年11月2日 14:32:53
 */
public class ViewButton extends BaseButton {
	private String type;
	private String url;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
