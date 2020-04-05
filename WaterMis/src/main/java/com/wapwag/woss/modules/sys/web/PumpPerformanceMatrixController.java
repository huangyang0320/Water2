package com.wapwag.woss.modules.sys.web;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.modules.biz.entity.PerformanceMatrixDto;
import com.wapwag.woss.modules.sys.entity.PumpPerformanceMatrix;
import com.wapwag.woss.modules.sys.service.PumpPerformanceMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 水泵性能曲线Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/performanceMatrix")
public class PumpPerformanceMatrixController {

	@Autowired
	private PumpPerformanceMatrixService performanceMatrixService;

	/**
	 * 前台读取数据分页
	 * @param pumpPerformanceMatrix
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllPerformanceMatrix")
	public Page<PumpPerformanceMatrix> findPerformanceMatrix(@RequestBody PumpPerformanceMatrix pumpPerformanceMatrix, HttpServletRequest request, HttpServletResponse response) {
		Page<PumpPerformanceMatrix> page = new Page<>(request, response);
		page.setPageNo(pumpPerformanceMatrix.getPageNo());
		page.setPageSize(pumpPerformanceMatrix.getPageSize());
		page = performanceMatrixService.findPage(page, pumpPerformanceMatrix);
		return page;
	}

	/**
	 * 流量/（扬程、功率、效率）曲线图
	 * @param pumpModel
	 * @param hz
	 * @param character
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMatrix")
	public PerformanceMatrixDto queryMatrix(String  pumpModel,String hz,String character) {
		return performanceMatrixService.queryMatrix(pumpModel,hz,character);
	}

	/**
	 * 查找设备
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPumpModel")
	public List<String> queryPumpModel() {
		return performanceMatrixService.queryPumpModel();
	}

	/**
	 * 查找hz
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryHzByPumpModel")
	public List<String> queryHzByPumpModel(String pumpModel) {
		return performanceMatrixService.queryHzByPumpModel(pumpModel);
	}

}
