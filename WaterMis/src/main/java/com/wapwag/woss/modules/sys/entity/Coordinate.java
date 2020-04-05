package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 用户实时坐标信息
 * @author gongll
 *
 */
public class Coordinate  extends DataEntity<Coordinate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String name;  //用户ID
	private  String id;  //用户ID
	private  String mobile;  //手机号
	private  String no;  //工号
	private  String Longi;  //经度
	private  String Lati;  //维度
	private  String status;  //状态
	private  String position;  //位置
	private  String date_time;  //上传时间
	private  String create_time;  //创建时间
	private Date addDate;
	
	public String getLongi() {
		return Longi;
	}
	public void setLongi(String longi) {
		Longi = longi;
	}
	public String getLati() {
		return Lati;
	}
	public void setLati(String lati) {
		Lati = lati;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
