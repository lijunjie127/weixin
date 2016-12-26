package org.lijunjie.weixin.test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * fun 平常测试小知识点
 * fun1 算存钱利息的
 * @author Ciss
 *
 */
public class SBGG {
	public static void main(String[] args) {
		fun();
	}
	
	
	public static void fun(){
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		System.out.print(dateFormater.format(date));
	}
	
	public static void fun1(){
		double XX = 100;
		int year = 1;
		int temp ;
		
		double x = XX;
		double rate = 0.1;//10%
		int i = year*12;//计数，几个周期，一个周期一个月
		temp = i;
		while(i-- > 0){
			x = x*rate/12 + x;
			System.out.println("第个" + (temp- i) + "周期后:" + x);
		}
		System.out.println("按照月的方式存" + year + "年" + x);
		
		x = XX;
		rate = 0.12;//三个月的利率
		i = year*4;//计数，几个周期，一个周期3个月
		temp = i;
		System.out.println();
		while(i-- > 0){
			x = x*rate/4 + x;
			System.out.println("第个" + (temp- i) + "周期后:" + x);
		}
		System.out.println("按照季度的方式存" + year + "年" + x);
		
		x = XX;
		rate = 0.14;//六个月的利率
		i = year*2;//计数，几个周期，一个周期6个月
		temp = i;
		System.out.println();
		while(i-- > 0){
			x = x*rate/2 + x;
			System.out.println("第个" + (temp- i) + "周期后:" + x);
		}
		System.out.println("按照半年的方式存" + year + "年：" + x);
	}
}
