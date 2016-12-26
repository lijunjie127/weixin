package org.lijunjie.weixin.pojo;

import java.util.List;

public class WeixinUserList {
	private int total;//	关注该公众账号的总用户数
	private int count;//	拉取的OPENID个数，最大值为10000
	private List<String> OpenIdList;//	列表数据，OPENID的列表
	private String next_openid;//拉取列表的最后一个用户的OPENID
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getOpenIdList() {
		return OpenIdList;
	}
	public void setOpenIdList(List<String> openIdList) {
		OpenIdList = openIdList;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
}
