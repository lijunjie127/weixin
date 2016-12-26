package org.lijunjie.weixin.pojo;

/**
 * 复合类型的按钮（包含二级菜单的一级菜单）
 * @author LiJunjie
 * @date 2016年11月2日 14:32:53
 */
public class ComplexButton extends BaseButton {
	private BaseButton[] sub_button;

	public BaseButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(BaseButton[] sub_button) {
		this.sub_button = sub_button;
	}
	
	
}
