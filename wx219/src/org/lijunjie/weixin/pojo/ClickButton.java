package org.lijunjie.weixin.pojo;

/**
 * click类型的按钮
 * @author LiJunjie
 * @date 2016年11月2日 14:32:53
 */
public class ClickButton extends BaseButton {
	private String type;
	private String key;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
