package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 设备配置标牌Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class DeviceSetLabel extends DataEntity<DeviceSetLabel> {
	
	private static final long serialVersionUID = 1L;
	private Device device;		// 设备ID
	private String equipmentModelNo;		// 设备型号
	private String pupmpModelNo;		// 水泵型号
	private String sbedgsll;		// 设备额定供水流量
	private String powerRating;		// 设备额定功率
	private String wlgGg;		// 稳流罐规格
	private String shapgeSize;		// 设备外形尺寸
	private String llkzq;		// 流量控制器
	private String qygGg;		// 气压罐规格
	private String sxGg;		// 水箱规格
	private String weight;		// 设备重量
	private String environmentalTemperature;		// 环境温度
	private String pumpQuantity;		// 水泵台数
	private String cabinetModelNo;		// 控制柜型号
	private String powerVoltage;		// 配套电源电压
	private String sbedgsyc;		// 设备额定供水扬程
	private String totalPower;		// 设备总功率
	private String zyzzGg;		// 增压装置规格
	private String scbcq;		// 双向补偿器规格
	private Date exFactoryDate;		// 出厂日期
	private String fullDeviceXh;		// 整机设备序号
	private String znYinshui;		// 智能引水装置
	private String fbXinghao;		// 副泵型号
	private String qtGuige;		// 腔体规格
	private String slKongzhi;		// 综合水力控制单元
	private String wyBuchang;		// 稳压补偿罐规格
	
	public DeviceSetLabel() {
		super();
	}

	public DeviceSetLabel(String id){
		super(id);
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	@Length(min=0, max=200, message="设备型号长度必须介于 0 和 200 之间")
	public String getEquipmentModelNo() {
		return equipmentModelNo;
	}

	public void setEquipmentModelNo(String equipmentModelNo) {
		this.equipmentModelNo = equipmentModelNo;
	}
	
	@Length(min=0, max=20, message="水泵型号长度必须介于 0 和 20 之间")
	public String getPupmpModelNo() {
		return pupmpModelNo;
	}

	public void setPupmpModelNo(String pupmpModelNo) {
		this.pupmpModelNo = pupmpModelNo;
	}
	
	@Length(min=0, max=10, message="设备额定供水流量长度必须介于 0 和 10 之间")
	public String getSbedgsll() {
		return sbedgsll;
	}

	public void setSbedgsll(String sbedgsll) {
		this.sbedgsll = sbedgsll;
	}
	
	@Length(min=0, max=10, message="设备额定功率长度必须介于 0 和 10 之间")
	public String getPowerRating() {
		return powerRating;
	}

	public void setPowerRating(String powerRating) {
		this.powerRating = powerRating;
	}
	
	@Length(min=0, max=50, message="稳流罐规格长度必须介于 0 和 50 之间")
	public String getWlgGg() {
		return wlgGg;
	}

	public void setWlgGg(String wlgGg) {
		this.wlgGg = wlgGg;
	}
	
	@Length(min=0, max=200, message="设备外形尺寸长度必须介于 0 和 200 之间")
	public String getShapgeSize() {
		return shapgeSize;
	}

	public void setShapgeSize(String shapgeSize) {
		this.shapgeSize = shapgeSize;
	}
	
	@Length(min=0, max=500, message="流量控制器长度必须介于 0 和 500 之间")
	public String getLlkzq() {
		return llkzq;
	}

	public void setLlkzq(String llkzq) {
		this.llkzq = llkzq;
	}
	
	@Length(min=0, max=500, message="气压罐规格长度必须介于 0 和 500 之间")
	public String getQygGg() {
		return qygGg;
	}

	public void setQygGg(String qygGg) {
		this.qygGg = qygGg;
	}
	
	@Length(min=0, max=500, message="水箱规格长度必须介于 0 和 500 之间")
	public String getSxGg() {
		return sxGg;
	}

	public void setSxGg(String sxGg) {
		this.sxGg = sxGg;
	}
	
	@Length(min=0, max=10, message="设备重量长度必须介于 0 和 10 之间")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@Length(min=0, max=100, message="环境温度长度必须介于 0 和 100 之间")
	public String getEnvironmentalTemperature() {
		return environmentalTemperature;
	}

	public void setEnvironmentalTemperature(String environmentalTemperature) {
		this.environmentalTemperature = environmentalTemperature;
	}
	
	@Length(min=0, max=10, message="水泵台数长度必须介于 0 和 10 之间")
	public String getPumpQuantity() {
		return pumpQuantity;
	}

	public void setPumpQuantity(String pumpQuantity) {
		this.pumpQuantity = pumpQuantity;
	}
	
	@Length(min=0, max=200, message="控制柜型号长度必须介于 0 和 200 之间")
	public String getCabinetModelNo() {
		return cabinetModelNo;
	}

	public void setCabinetModelNo(String cabinetModelNo) {
		this.cabinetModelNo = cabinetModelNo;
	}
	
	@Length(min=0, max=10, message="配套电源电压长度必须介于 0 和 10 之间")
	public String getPowerVoltage() {
		return powerVoltage;
	}

	public void setPowerVoltage(String powerVoltage) {
		this.powerVoltage = powerVoltage;
	}
	
	@Length(min=0, max=200, message="设备额定供水扬程长度必须介于 0 和 200 之间")
	public String getSbedgsyc() {
		return sbedgsyc;
	}

	public void setSbedgsyc(String sbedgsyc) {
		this.sbedgsyc = sbedgsyc;
	}
	
	@Length(min=0, max=10, message="设备总功率长度必须介于 0 和 10 之间")
	public String getTotalPower() {
		return totalPower;
	}

	public void setTotalPower(String totalPower) {
		this.totalPower = totalPower;
	}
	
	@Length(min=0, max=200, message="增压装置规格长度必须介于 0 和 200 之间")
	public String getZyzzGg() {
		return zyzzGg;
	}

	public void setZyzzGg(String zyzzGg) {
		this.zyzzGg = zyzzGg;
	}
	
	@Length(min=0, max=200, message="双向补偿器规格长度必须介于 0 和 200 之间")
	public String getScbcq() {
		return scbcq;
	}

	public void setScbcq(String scbcq) {
		this.scbcq = scbcq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExFactoryDate() {
		return exFactoryDate;
	}

	public void setExFactoryDate(Date exFactoryDate) {
		this.exFactoryDate = exFactoryDate;
	}
	
	@Length(min=0, max=50, message="整机设备序号长度必须介于 0 和 50 之间")
	public String getFullDeviceXh() {
		return fullDeviceXh;
	}

	public void setFullDeviceXh(String fullDeviceXh) {
		this.fullDeviceXh = fullDeviceXh;
	}
	
	@Length(min=0, max=50, message="智能引水装置长度必须介于 0 和 50 之间")
	public String getZnYinshui() {
		return znYinshui;
	}

	public void setZnYinshui(String znYinshui) {
		this.znYinshui = znYinshui;
	}
	
	@Length(min=0, max=500, message="副泵型号长度必须介于 0 和 500 之间")
	public String getFbXinghao() {
		return fbXinghao;
	}

	public void setFbXinghao(String fbXinghao) {
		this.fbXinghao = fbXinghao;
	}
	
	@Length(min=0, max=500, message="腔体规格长度必须介于 0 和 500 之间")
	public String getQtGuige() {
		return qtGuige;
	}

	public void setQtGuige(String qtGuige) {
		this.qtGuige = qtGuige;
	}
	
	@Length(min=0, max=500, message="综合水力控制单元长度必须介于 0 和 500 之间")
	public String getSlKongzhi() {
		return slKongzhi;
	}

	public void setSlKongzhi(String slKongzhi) {
		this.slKongzhi = slKongzhi;
	}
	
	@Length(min=0, max=500, message="稳压补偿罐规格长度必须介于 0 和 500 之间")
	public String getWyBuchang() {
		return wyBuchang;
	}

	public void setWyBuchang(String wyBuchang) {
		this.wyBuchang = wyBuchang;
	}
	
}