package com.wapwag.woss.modules.biz.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wapwag.woss.modules.biz.dao.LifepredictionDao;
import com.wapwag.woss.modules.biz.entity.AccListLifeDto;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.biz.entity.ProductComponent;
import com.wapwag.woss.modules.biz.entity.PumpDeviceRepairInfo;

@Service
public class LifepredictionService {
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private LifepredictionDao lifepredictionDao;
	
	@Autowired
	private PumpDeviceRepairInfoService pumpDeviceRepairInfoService;
	  
	    /**  
	    * @Title: initData  
	    * @Description: TODO(获取设备的运行天数和启用日期)  
	    * @param @param deviceId    参数  
	    * @return void    返回类型   
	    */  	    
	public Map<String,Object> initData(String deviceId) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Device device = deviceService.get(deviceId);
		int deviceRunDays = (int) ((new Date().getTime() - device.getCreatetime().getTime())/(1000*60*60*24));
		resultMap.put("deviceRunDays", String.valueOf(deviceRunDays));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		resultMap.put("startDate", df.format(device.getCreatetime()));
		List<ProductComponent> components = lifepredictionDao.getComponentByProductId(device.getType());
		resultMap.put("components", components);
		return resultMap;
	}
	  
	    /**  
	    * @Title: getDeviceLife  
	    * @Description: TODO(获取设备的寿命预估)  
	    * @param @param deviceId
	    * @param @return    参数  
	    * @return AccListLifeDto    返回类型  
	    */  
	public AccListLifeDto getDeviceLife(String deviceId){
		String[] xData = {"启用","第一年","第二年","第三年","第四年","第五年","第六年","第七年","第八年","第九年","第十年"};
		AccListLifeDto listLifeDto = new AccListLifeDto();
		Device device = deviceService.get(deviceId);
		//获取设备的全部配件
		List<ProductComponent> components = lifepredictionDao.getComponentByProductId(device.getType());
		//获取总权重
		double num = 100;
//		for(ProductComponent component:components) {
//			num += Double.parseDouble(component.getWeight());
//		}
		List<Double[]> lifeValuesList = new ArrayList<Double[]>();
		for(ProductComponent component:components) {
			//获取配件的当前生命值
			Double[] lifeValues = getFailureRate(component, device);
			lifeValuesList.add(lifeValues);
		}
		int deviceRunDays = (int) ((new Date().getTime() - device.getCreatetime().getTime())/(1000*60*60*24));
		int currentYear = deviceRunDays/365+1;
		xData = getXDatas(xData, deviceRunDays, currentYear);
		Double[] values = new Double[xData.length];
		for(int i=0;i<xData.length;i++) {
			double value = 0.0;
			for(int j=0;j<lifeValuesList.size();j++) {
				double weight = Double.parseDouble(components.get(j).getWeight());
				value += (1-lifeValuesList.get(j)[i])*(weight/num);
			}
			DecimalFormat df = new DecimalFormat("0.0##");
			values[i]=Double.parseDouble(df.format(1-value));
		}
		listLifeDto.setValue(values);
		listLifeDto.setxData(xData);
		listLifeDto.setCurrentIndex(currentYear);
		return listLifeDto;
	}
	  
	    /**  
	    * @Title: getFailureRate  
	    * @Description: TODO(这里用一句话描述这个方法的作用)  
	    * @param @param component
	    * @param @param device
	    * @param @return    参数  
	    * @return String[]    返回类型  
	    * @throws  
	    */  
	public Double[] getFailureRate(ProductComponent component,Device device) {
		Double[] values  = null;
		List<PumpDeviceRepairInfo> list = pumpDeviceRepairInfoService.getLastReplaceInfo(device.getId(), component.getId());
		int deviceRunDays = (int) ((new Date().getTime() - device.getCreatetime().getTime())/(1000*60*60*24));
		int currentYear = deviceRunDays/365+1;
		if(list.size()>0) {
			//有更换过
			PumpDeviceRepairInfo repairInfo = list.get(0);
			int runDays =(int) ((new Date().getTime() - repairInfo.getRepairTime().getTime())/(1000*60*60*24));
			if(deviceRunDays-runDays<365*10) {
				values = getValuesByRaplace(component.getFailureRate(), deviceRunDays, runDays);
			}else {
				values = getValues(component.getFailureRate(), currentYear, deviceRunDays);
			}
		}else {
			//没有更换过
			values = getValues(component.getFailureRate(), currentYear, deviceRunDays);
		}
		return  values;
	}
	  
	    /**  
	    * @Title: getValuesByRaplace  
	    * @Description: TODO(更换配件后重新生成配件生命值)  
	    * @param @param oldValues 设备值
	    * @param @param deviceRunDays 设备运行天数
	    * @param @param compRunDays 配件更换后的运行天数
	    * @return String[]    返回类型  
	    */  
	    
	public Double[] getValuesByRaplace(Double[] oldValues,int deviceRunDays,int compRunDays) {
		Double[] values = new Double[oldValues.length];
		int deviceRunYears = deviceRunDays/365+1;
		double point = ((deviceRunDays-compRunDays)%365)/365D;
		int oldIndex = 0;
		//把更换后和更换前重新组合为10年的生命值
		for(int i=0;i<oldValues.length;i++) {
			//deviceRunYears-compRunYears 在那一年更换的 || 没有更换
			if(i<((deviceRunDays-compRunDays)/365D) || deviceRunDays <= compRunDays) {
				values[i] = oldValues[i];
				oldIndex++;
			}else {
				//更换后的 重新计算
				double preValue = oldValues[i-oldIndex];
				double nextValue = oldValues[i-oldIndex+1];
				double currentValue = preValue-((preValue-nextValue)/356D)*point; 
				values[i] =  currentValue;
			}
		}
		//组合后计算 当前生命值
		if(deviceRunYears%365D>0 && deviceRunYears<12) {
			double currentValue = 0.0;
			if(deviceRunDays%365+compRunDays<=365 && compRunDays>=0) {
				//更换零件没到设备的下一年的
				double preValue = oldValues[0];
				double nextValue = oldValues[1];
				currentValue = preValue-((preValue-nextValue)/356D)*(compRunDays%365D);
			}else if(compRunDays<0) {
				//还没有到更换时间
				double preValue = oldValues[deviceRunYears-1];
				double nextValue = oldValues[deviceRunYears];
				currentValue = preValue-((preValue-nextValue)/356D)*(deviceRunYears%365D);
			} else {
				//到下一年的
				double preValue = values[deviceRunYears-1];
				double nextValue = values[deviceRunYears];
				currentValue = preValue-((preValue-nextValue)/356D)*(deviceRunYears%365D);
			}
			Double[] valuesTmp = new Double[values.length+1];
			System.arraycopy(values, 0, valuesTmp, 0, deviceRunYears);
			System.arraycopy(values, deviceRunYears, valuesTmp, deviceRunYears+1, values.length-deviceRunYears);
			DecimalFormat df = new DecimalFormat("0.0##");
			valuesTmp[deviceRunYears] =Double.parseDouble(df.format(currentValue));
			values = valuesTmp;
		}
		return values;
	}
	
	    /**  
	    * @Title: getComponentLife  
	    * @Description: TODO(获取组件生命预估)
	    * @param @param deviceId
	    * @param @param componentId
	    * @param @return    参数  
	    * @return AccListLifeDto    返回类型  
	    * @throws  
	    */  
	public AccListLifeDto getComponentLife(String deviceId,String componentId){
		ProductComponent component = lifepredictionDao.getProductComponentById(componentId);
		List<PumpDeviceRepairInfo> list = pumpDeviceRepairInfoService.getLastReplaceInfo(deviceId, componentId);
		AccListLifeDto listLifeDto = new AccListLifeDto();
		Device device = deviceService.get(deviceId);
		int deviceRunDays =(int) ((new Date().getTime() - device.getCreatetime().getTime())/(1000*60*60*24));
		int runDays = deviceRunDays;
		if(component != null && component.getId() != null) {
			if(list.size()>0) {
				PumpDeviceRepairInfo repairInfo = list.get(0);
				runDays =(int) ((new Date().getTime() - repairInfo.getRepairTime().getTime())/(1000*60*60*24));
			}
		}
		getAccListLife(component,listLifeDto,deviceRunDays,runDays);
		return listLifeDto;
	}
	  
	    /**  
	    * @Title: getAccListLife  
	    * @Description: TODO 计算生命值
	    * @param @param component
	    * @param @param accListLifeDto
	    * @param @param runDays    参数  
	    * @return void    返回类型  
	    */  	    
	private void getAccListLife(ProductComponent component,AccListLifeDto accListLifeDto,int deviceRunDays,int runDays) {
		//当前运行天数
		int currentYear   = deviceRunDays/365+1;
		//初始值
		String[] xData = {"启用","第一年","第二年","第三年","第四年","第五年","第六年","第七年","第八年","第九年","第十年"};
		Double[] values = component.getFailureRate();
		//不是整年 就添加x坐标和值，如果是整年或者超出十年则不做处理
		if(deviceRunDays%365>0 && currentYear < xData.length) {
			//x坐标处理 把当前天数插入到初始值
			xData = getXDatas(xData, deviceRunDays, currentYear);
			if(runDays<deviceRunDays && deviceRunDays-runDays<365*10) {
				values = getValuesByRaplace(values, deviceRunDays, runDays);
			}else {
				values = getValues(values, currentYear, runDays);
			}
		}
		accListLifeDto.setCurrentIndex(currentYear);
		accListLifeDto.setValue(values);
		accListLifeDto.setxData(xData);
	}
	    
	    /**  
	    * @Title: getXDatas  
	    * @Description: TODO(这里用一句话描述这个方法的作用)  
	    * @param @param oldData
	    * @param @param runDays
	    * @param @param currentYear
	    * @param @return    参数  
	    * @return String[]    返回类型  
	    * @throws  
	    */  
	public String[] getXDatas(String[] oldData, int runDays ,int currentYear) {
		  if(runDays%365>0 && currentYear < oldData.length) {
				String[] xDataTmp = new String[oldData.length+1];
				System.arraycopy(oldData, 0, xDataTmp, 0, currentYear);
				xDataTmp[currentYear] = "当前运行"+runDays+"天";
				System.arraycopy(oldData, currentYear, xDataTmp, currentYear+1, oldData.length-currentYear);
				return xDataTmp;
		  }else {
			  return oldData;
		  }
	  }
	    /**  
	    * @Title: getValues  
	    * @Description: TODO(这里用一句话描述这个方法的作用)  
	    * @param @param oldValues
	    * @param @param currentYear
	    * @param @param runDays
	    * @param @return    参数  
	    * @return String[]    返回类型  
	    */  
	public Double[] getValues(Double[] oldValues,int currentYear,int runDays) {
		if(runDays%365>0 && currentYear<oldValues.length) {
			Double[] valuesTmp = new Double[oldValues.length+1];
			System.arraycopy(oldValues, 0, valuesTmp, 0, currentYear);
			System.arraycopy(oldValues, currentYear, valuesTmp, currentYear+1, oldValues.length-currentYear);
			//计算当前生命值 把前后两年的值当做直线 计算当前值
			double value = Double.valueOf(valuesTmp[currentYear-1]) - 
					(Double.valueOf(valuesTmp[currentYear-1])-Double.valueOf(oldValues[currentYear+1]))*( (runDays%365)/365D);
			DecimalFormat df = new DecimalFormat("0.0##");
			valuesTmp[currentYear] =Double.parseDouble(df.format(value));
			return valuesTmp;
		}else {
			return oldValues;
		}
	}
}
