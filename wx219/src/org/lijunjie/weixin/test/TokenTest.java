package org.lijunjie.weixin.test;

import java.sql.SQLException;

import org.lijunjie.weixin.util.CommonUtil;

public class TokenTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {

		System.out.println(CommonUtil.getCurrentAccessToken());
		
//		try {
//			Connection conn = ConnectionFactory.getConnection();
//			Statement st = conn.createStatement();
//			ResultSet rs = st.executeQuery("select * from wx_accesstoken");
//			int current = (int) (System.currentTimeMillis() / 1000); 
//			if(!rs.next() || current>(rs.getInt(1) + rs.getInt(3))){
//				System.out.println(rs.getInt(1) + "\n" + rs.getString(2) + "\n" + rs.getInt(3));
//				Token token = CommonUtil.getNewAccessToken();
//				System.out.println(current);
//				System.out.println(token.getAccess_token());
//				System.out.println(token.getExpires_in());
//				st.executeUpdate("UPDATE wx_accesstoken SET current = " + current + ",accesstoken =  '" + token.getAccess_token() + "',expires = " + token.getExpires_in()*0.8);
//			}else{
//				System.out.println("token有效：" + rs.getString(2));
//				System.out.println("剩余时间：" + (rs.getInt(3)-(current-rs.getInt(1))) + "秒");
//			}
//			DatabaseUtils.release(rs, st, conn);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
