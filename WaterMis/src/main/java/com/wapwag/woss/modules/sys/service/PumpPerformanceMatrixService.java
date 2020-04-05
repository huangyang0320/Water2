package com.wapwag.woss.modules.sys.service;

import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.PerformanceMatrixDto;
import com.wapwag.woss.modules.biz.entity.SeriesDto;
import com.wapwag.woss.modules.sys.dao.PumpPerformanceMatrixDao;
import com.wapwag.woss.modules.sys.entity.PumpPerformanceMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 水泵性能曲线Service
 */
@Service
@Transactional(readOnly = true)
public class PumpPerformanceMatrixService extends CrudService<PumpPerformanceMatrixDao, PumpPerformanceMatrix> {

	@Autowired
	private PumpPerformanceMatrixDao pumpPerformanceMatrixDao;

	public List<PumpPerformanceMatrix> findList(PumpPerformanceMatrix pumpPerformanceMatrix) {
		return super.findList(pumpPerformanceMatrix);
	}

	public PerformanceMatrixDto queryMatrix(String pumpModel,String hz,String character){
		//返回前台对象PerformanceMatrixDto
		PerformanceMatrixDto performanceMatrix =new PerformanceMatrixDto();
		//接受数据库对象
		List<PumpPerformanceMatrix> pumpPerformanceMatrix;
		pumpPerformanceMatrix = pumpPerformanceMatrixDao.queryMatrix(pumpModel, hz);
		//判断图条件
		dataProcessing(performanceMatrix,pumpPerformanceMatrix,character);
		return performanceMatrix;
	}

	public void dataProcessing(PerformanceMatrixDto performanceMatrix,List<PumpPerformanceMatrix> pumpPerformanceMatrix,String character){
		List<Double> flow=new ArrayList();
		List<SeriesDto> yData=new ArrayList<>();
		SeriesDto seriesDto =new SeriesDto();
		//判断为那个数据图
		if(character.equals("lift")){
			List<Double> lift=new ArrayList();
			for (PumpPerformanceMatrix data : pumpPerformanceMatrix) {
				flow.add(data.getFlow());
				lift.add(data.getLift());
			}
			seriesDto.setName("扬程");
			seriesDto.setData(lift);
			yData.add(seriesDto);
		}else if(character.equals("power")){
			List<Double> inputPower=new ArrayList();
			List<Double> outputPower=new ArrayList();
			for (PumpPerformanceMatrix data : pumpPerformanceMatrix) {
				flow.add(data.getFlow());
				inputPower.add(data.getInputPower());
				outputPower.add(data.getOutputPower());
			}
			seriesDto.setName("输入功率");
			seriesDto.setData(inputPower);
			SeriesDto seriesDtos =new SeriesDto();
			seriesDtos.setName("输出功率");
			seriesDtos.setData(outputPower);
			yData.add(seriesDto);
			yData.add(seriesDtos);
		}else if(character.equals("efficiency")){
			List<Double> efficiency=new ArrayList();
			for (PumpPerformanceMatrix data : pumpPerformanceMatrix) {
				flow.add(data.getFlow());
				efficiency.add(data.getEfficiency()*100);//效率乘一百
			}
			seriesDto.setName("效率");
			seriesDto.setData(efficiency);
			yData.add(seriesDto);
		}
		performanceMatrix.setxData(flow);
		performanceMatrix.setyData(yData);
	}

	public List<String> queryPumpModel(){
		return pumpPerformanceMatrixDao.queryPumpModel();
	}
	public List<String> queryHzByPumpModel(String pumpModel){
		return pumpPerformanceMatrixDao.queryHzByPumpModel(pumpModel);
	}

}
