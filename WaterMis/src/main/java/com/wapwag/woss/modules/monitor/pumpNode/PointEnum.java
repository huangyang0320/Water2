package com.wapwag.woss.modules.monitor.pumpNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 下控测点枚举转换
 * 
 * @author zhaoming
 * 1 下控测点 需要 转 set/cofirm----->再次confirm  0
 * 2 是直接下控
 * 3 下控完成 需要继续复位下控  2次接口 需要
 * 
 * 4 注释掉的测点 不再支持下控  徐州的
 * @version 
 */
public enum PointEnum {

	GIVENPRESSURE("GivenPressure","GivenPressure","1"),//给定压力
	PRESSURECAPVALUE("PressureCapValue","PressureCapValue","1"),//压力上限
	PRESSURELIMITVALUE("PressureLimitValue","PressureLimitValue","1"),//压力下限
	WAKEUPPRESSURE("WakeUpPressure","WakeUpPressure","1"),//唤醒压力
	//徐州项目 测点 特殊处理
	//NEGATIVEPRESSURELIMITVALUE("NegativePressureLimitValue","SP02","1"),//负压下限
	SP02("SP02","SP02","1"),//负压下限
	//NEGATIVEPRESSURECAPVALUE("NegativePressureCapValue","SP03","1"),//负压解除
	SP03("SP03","SP03","1"),//负压解除
	
	//远程自动
	R_AUTO("R_Auto","R_Auto","2"),
	//远程手动
	//R_MANUAL("R_Manual","R_Manual","2"),  
	//远程功能开
	R_STARTUP("R_StartUp","R_StartUp","2"),  
	//水泵下控
	//NO1_R_MANUAL("No1_R_Manual","No1_R_Manual","2"),//远程启动1号泵（1启动0停止）
	//NO2_R_MANUAL("No2_R_Manual","No2_R_Manual","2"),
	//NO3_R_MANUAL("No3_R_Manual","No3_R_Manual","2"),
	//远程急停
	R_E_STOP("R_E_stop","R_E_stop","3"),//（1停止0复位） 

	//门禁
	DOORACCESS("DoorAccess","DoorAccess","3"),//("1开门")

	;
	
	private String code;

	private String message;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	PointEnum(String code, String message,String type) {
		this.code = code;
		this.message = message;
	    this.type = type;
	}
	
	public static void main(String args[]) {
//		System.out.println("第一种通过反射");
//		Class<PointEnum> clz = PointEnum.class;
//		for (PointEnum obj : clz.getEnumConstants()) {
//			System.out.println(obj.getMessage());
//		}
//
//		System.out.println("第二种通过枚举静态方法values()");
//		for (PointEnum rate : PointEnum.values()) {
//			System.out.println(rate.getMessage());
//		}
		//List<PointEnum> pointEnumByType = getPointEnumByType("1");
		List<String> pointEnumKeyByType = getPointEnumKeyByType("1");
		System.out.println(pointEnumKeyByType);
		//System.out.println(pointEnumByType);
	}
	
	public static  List<PointEnum> getPointEnumByType(String type){
		List<PointEnum> list = new ArrayList<PointEnum>();
		for (PointEnum rate : PointEnum.values()) {
			if(rate.getType().equals(type)){
				list.add(rate);
			}
		}
		return list;
	}
	
	public static  List<String> getPointEnumKeyByType(String type){
		List<String> list = new ArrayList<String>();
		for (PointEnum rate : PointEnum.values()) {
			if(rate.getType().equals(type)){
				list.add(rate.getCode());
			}
		}
		return list;
	}


}
