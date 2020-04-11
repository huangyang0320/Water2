package com.wapwag.woss.modules.monitor.service;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.wapwag.woss.modules.config.ErrorCode.CTRL_ERROR;
import static com.wapwag.woss.modules.config.ErrorCode.CTRL_ERROR_NULL;
import static com.wapwag.woss.modules.config.ErrorCode.CTRL_FORBIDDEN;
import static com.wapwag.woss.modules.config.ErrorCode.CTRL_INVALID_VALUE;
import static com.wapwag.woss.modules.config.ErrorCode.CTRL_NULL_DOOR;
import static com.wapwag.woss.modules.config.ErrorCode.SYS_NOT_EXIST;
import static com.wapwag.woss.modules.config.RestResult.fail;
import static com.wapwag.woss.modules.config.RestResult.failDor;
import static com.wapwag.woss.modules.config.RestResult.success;
import static com.wapwag.woss.modules.config.RestResult.successDor;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wapwag.woss.common.utils.DateUtils;
import com.wapwag.woss.common.utils.PropUtils;
import com.wapwag.woss.modules.biz.dao.AccessDeviceDao;
import com.wapwag.woss.modules.biz.entity.AccessDevice;
import com.wapwag.woss.modules.biz.entity.DeviceInfo;
import com.wapwag.woss.modules.biz.entity.Services;
import com.wapwag.woss.modules.biz.entity.SysDict;
import com.wapwag.woss.modules.biz.service.FunctionService;
import com.wapwag.woss.modules.config.ErrorCode;
import com.wapwag.woss.modules.config.HttpResult;
import com.wapwag.woss.modules.config.RestResult;
import com.wapwag.woss.modules.home.dao.DeviceDao;
import com.wapwag.woss.modules.home.service.EnergyService;
import com.wapwag.woss.modules.monitor.dao.PumpConfigurationMapper;
import com.wapwag.woss.modules.monitor.pumpNode.CtrlDTO;
import com.wapwag.woss.modules.monitor.pumpNode.CtrlParam;
import com.wapwag.woss.modules.monitor.pumpNode.CtrlPoint;
import com.wapwag.woss.modules.monitor.pumpNode.CurDevicePos;
import com.wapwag.woss.modules.monitor.pumpNode.DevicePoint;
import com.wapwag.woss.modules.monitor.pumpNode.GwDevice;
import com.wapwag.woss.modules.monitor.pumpNode.PhNode;
import com.wapwag.woss.modules.monitor.pumpNode.Poi;
import com.wapwag.woss.modules.monitor.pumpNode.Point;
import com.wapwag.woss.modules.monitor.pumpNode.PointDate;
import com.wapwag.woss.modules.monitor.pumpNode.PointEnum;
import com.wapwag.woss.modules.monitor.pumpNode.PumpNodeVO;
import com.wapwag.woss.modules.monitor.pumpNode.PumpPositionInfo;
import com.wapwag.woss.modules.monitor.pumpNode.PumpService;
import com.wapwag.woss.modules.monitor.pumpNode.RemoteCtrl;
import com.wapwag.woss.modules.monitor.pumpNode.VideoInfo;
import com.wapwag.woss.modules.sys.dao.UserDao;
import com.wapwag.woss.modules.sys.entity.UserTreeSelection;
import com.wapwag.woss.modules.sys.service.CountService;

/**
 * @author ChangWei Li
 * @version 2017-12-26 13:10
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PumpConfigurationService {

	private static Logger log = LoggerFactory.getLogger(EnergyService.class);


    /**远程上传文件的服务器地址 */
    public static final String IMAGE_URL = PropUtils.getPropertiesString("application.properties", "remote.images.url");
    /**数据库 */
    private static final String SV = "service_values_";
    private static final String CTRL_SUCCESS = "下控成功";
    private static final int HTTP_STATUS_OK = 200;
    private static final Integer REMOTE_CTRL_STATUS = 0;
   // private static final String downUrl = "https://www.cloud4water.com/down/jsonParam";
   // private static final String newDownUrl = "http://47.100.198.103:8089/down/jsonParam";
   // private static final String newDownUrl = "http://127.0.0.1:8089/down/jsonParam";

    @Value(value = "${newDownUrl}")
    private String newDownUrl;

    private static final String PH_DEFAULT_URL = "theme/images/zt20180615162421.jpg";


    @Autowired
    private UserDao userDao;

    @Autowired
    private  HttpAPIService httpAPIService;

    @Autowired
    private PumpConfigurationMapper pumpConfigurationMapper;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private AccessDeviceDao accessDeviceDao;

    @Autowired
    private CountService countService;

    @Autowired
    private FunctionService functionService;

    @Transactional(readOnly = false)
    public String getPumpNode(String phId) {
        CurDevicePos curDevicePos = new CurDevicePos();
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data=null;
        // 设备数据
        PumpNodeVO pumpNodeVO = pumpConfigurationMapper.getDeviceInfo(IMAGE_URL, phId);
        if(pumpNodeVO == null ) pumpNodeVO = new PumpNodeVO();
        /*先查询数据库得到PumpPosition*/
        List<PumpPositionInfo> phPosition = pumpConfigurationMapper.getPhPosition(phId);
        //新建pumpPosition
        PumpPositionInfo pumpPosition = new PumpPositionInfo();
        //如果跟据项目id查询为空时
        if(phPosition.size() <= 0) {
            //直接查询pump_position_info表
            pumpNodeVO.setIdentification("0");//未绑定组态图
            if(pumpNodeVO.getPhUrl() == null ) pumpNodeVO.setPhUrl(PH_DEFAULT_URL);
            data=gson.toJson(pumpNodeVO);
            return data;
        }else {
            pumpNodeVO.setIdentification("1");//绑定组态图
            pumpPosition = phPosition.get(0);
        }
        //分别获取pump_position_info中的x轴y轴
        List<String> remoteCtrlPos = Arrays.asList(pumpPosition.getRemoteCtrlPos().split(";"));
        List<Poi> remoteCtrlPoi = strToList(remoteCtrlPos);

        List<String> inletPos = Arrays.asList(pumpPosition.getInletPos().split(";"));
        List<Poi> inletPosi = strToList(inletPos);

        List<String> outletPos = Arrays.asList(pumpPosition.getOutletPos().split(";"));
        List<Poi> outletPosi = strToList(outletPos);

        List<String> tankPos = Arrays.asList(pumpPosition.getTankPos().split(";"));
        List<Poi> tankPosi = strToList(tankPos);

        List<String> deviceNamePos = Arrays.asList(pumpPosition.getDeviceNamePos().split(";"));
        List<Poi> deviceNamePosi = strToList(deviceNamePos);
        //存放所有坐标
        List<List<Poi>> pumpPosi = new ArrayList<List<Poi>>();
        //获取水泵位置坐标
        List<String> pumpPositionStr = Arrays.asList(pumpPosition.getPumpPos().split("/"));
        for (String s : pumpPositionStr) {
            List<String> tmpStr = Arrays.asList(s.split(";"));
            List<Poi> pois = strToList(tmpStr);
            pumpPosi.add(pois);
        }
        curDevicePos.setRemoteCtrlPos(remoteCtrlPoi);
        curDevicePos.setInletPos(inletPosi);
        curDevicePos.setOutletPos(outletPosi);
        curDevicePos.setDeviceNamePos(deviceNamePosi);
        curDevicePos.setPumpPos(pumpPosi);
        curDevicePos.setTankPos(tankPosi);

        // 点位坐标数据
        pumpNodeVO.setCurDevicePos(curDevicePos);

        // 视频数据
        List<VideoInfo> videoInfo = pumpConfigurationMapper.getVideoInfo(phId);
        pumpNodeVO.setVideoInfo(videoInfo);

        // 节点的测点数据;
        List<PhNode> phNode = pumpConfigurationMapper.getPhNode(phId);
        List<PhNode> singleNodeTemp = new ArrayList<PhNode>();
        List<PhNode> multiNode = new ArrayList<PhNode>();
        for (PhNode node : phNode) {
            if(node.getNodeType() == 0){
                //单节点
                singleNodeTemp.add(node);
            }else if (node.getNodeType() == 1){
                //多节点
                multiNode.add(node);
            }
        }
        // 单节点,前端需要有门禁和温度信息(1.门禁,2.温度)
        PhNode temperatureNode = new PhNode("temperature","环境温度",0,new ArrayList<DevicePoint>());
        List<PhNode> singleNode = new ArrayList<PhNode>();
        List<PhNode> singleNode01 = new ArrayList<PhNode>();
        for (PhNode node : singleNodeTemp) {
            if(node != null){
                /*if("doorAccess".equals(node.getNodeCode())){
                    doorAccessNode = node;
                }else */if( "temperature".equals(node.getNodeCode())){
                    temperatureNode = node;
                }else {
                    singleNode01.add(node);
                }
            }
        }
        /*singleNode.add(doorAccessNode);*/
        singleNode.add(temperatureNode);
        singleNode.addAll(singleNode01);

        pumpNodeVO.setSingleNode(singleNode);
        pumpNodeVO.setMultiNode(multiNode);
        //判断设备是否是水箱的
        try {
        	List<SysDict> selectSysDictByType = selectSysDictByType("water_tank_type");
        	List<String> list = new ArrayList<String>();
        	for (SysDict sysDict : selectSysDictByType) {
        		list.add(sysDict.getValue());
			}
        	 List<com.wapwag.woss.modules.monitor.pumpNode.DeviceInfo> deviceInfoList = pumpNodeVO.getDeviceInfo();
        	 for (com.wapwag.woss.modules.monitor.pumpNode.DeviceInfo deviceInfo : deviceInfoList) {
				if(list.contains(deviceInfo.getType())){
					deviceInfo.setIsWaterTank("1");
				}
			}
		} catch (Exception e) {
		}

        data=gson.toJson(pumpNodeVO);
        return data;
    }

    private List<Poi> strToList(List<String> strList ){
        List<Poi> listPoi = new ArrayList<Poi>();
        for (String item : strList) {
            Poi poi = JSON.parseObject(item, Poi.class);
            listPoi.add(poi);
        }
        return listPoi;
    }

    /**
     * 创建搜索表名
     * @return
     */
    public static String createTableName() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String format = df.format(new Date());
        String time = format.split(" ")[0].replace("-", "");
        String tableName = SV + time.substring(0, 8);
        return tableName;
    }

    public String getServiceSetValues(PumpService pumpService){
        List<DeviceInfo> idDevice = deviceDao.findDeviceListByPumpHouse(pumpService.getPhId());
        if(idDevice!=null && idDevice.size()>0){
            List<String> deviceIds=new ArrayList<>();
            for(DeviceInfo d:idDevice){
                deviceIds.add(d.getDeviceId());
            }
            pumpService.setDeviceList(deviceIds);
        }
        List<PumpService> format =pumpConfigurationMapper.getServiceSetValues(pumpService.getDeviceList(),pumpService.getCodeList());
        for(PumpService pump : format){
            if(pump.getPv()!=null&&pump.getPv().length()>4){
                pump.setPv(pump.getPv().substring(0,5));
            }
        }
        String data=new Gson().toJson(format);
        return data;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String getServiceValues(String phId ){
        String tableName = PumpConfigurationService.createTableName();
        List<DeviceInfo> idDevice = deviceDao.findDeviceListByPumpHouse(phId);
        List<PhNode> phNode = pumpConfigurationMapper.getPhNode(phId);
        List<PumpService> format = new ArrayList<PumpService>();
        if(idDevice!=null && idDevice.size()>0){
            List<String> code = pumpConfigurationMapper.findDeviceCodeById(idDevice);
            HashSet h = new HashSet(code);
            code.clear();
            code.addAll(h);
            code.add("OutletPressure");
            code.add("InletPressure");
            code.add("No1TankLevel");
            code.add("No2TankLevel");
            code.add("TankLevel");


            code.add("tubeburst_outwater");//
            code.add("stop_electric");//
            code.add("overpressure_pumptotal");//
            code.add("nonewater_signal");//
            code.add("error_pump13");//
            code.add("error_pump12");//
            code.add("error_pump11");//

            /**
             * 设定值
             */
            code.add("level_tank_highwset");
            code.add("level_tank_lowset");
            code.add("flow_burstset");
            code.add("pressure_alarmset");



            code.add("openend_directvalve");//直供水电动阀打开
          code.add("openend_tankinvalve");//水箱电动阀打开

            code.add("pressure_szjs1");//总进水 压力
            code.add("pressure_szjs2");//总进水 压力

            code.add("flow_instant");//总进水瞬时流量
            code.add("run_pump11");//
            code.add("run_pump12");//
            code.add("run_pump13");//

            code.add("flowinstant_outwater");
            code.add("frequency_pump11");//
            code.add("frequency_pump12");//
            code.add("frequency_pump13");//
            code.add("chlorine_js");//进水余氯
            code.add("turbidity_js");//进水浊度
            code.add("sewagepump_run1");//潜水泵运作状态
            code.add("sewagepump_run2");
            code.add("pressure_outwater");//出口压力/出水压力
            code.add("level_tank1");//液位
            code.add("level_tank2");
            for (PhNode node : phNode){
                if(node.getPoints()!=null&&node.getPoints().size()>0){
                    for(DevicePoint point:node.getPoints()){
                        code.add(point.getPointName());
                    }
                }
            }
        	format = pumpConfigurationMapper.getServiceValues(tableName,idDevice,code);
        }
        for(PumpService pump : format){
            if(pump.getPv()!=null&&pump.getPv().length()>4){
                pump.setPv(pump.getPv().substring(0,5));
            }
        }
        String data=new Gson().toJson(format);
        return data;
    }

    @SuppressWarnings("rawtypes")
	public RestResult remoteCtrl(CtrlDTO ctrlDTO,String userId,String delFlag) throws IOException {
        String pointCode = ctrlDTO.getPointCode();
        String deviceId = ctrlDTO.getDeviceId();
        //判断是否有权限pointCode，deviceId是否为空
        if (pointCode == null || deviceId == null || !hasPermissionCtrl(userId,delFlag)) return fail(CTRL_FORBIDDEN);

        CtrlPoint ctrlPoint = pumpConfigurationMapper.getPointRatio(deviceId, pointCode);
        if (ctrlPoint==null||ctrlPoint.getDeviceId()==null)return fail(CTRL_ERROR_NULL);

        //folat 存在为空的情况 从  大点表再次确认
        CtrlPoint function = pumpConfigurationMapper.getFunctionById(ctrlPoint.getFunctionId());

        if("Float".equals(ctrlPoint.getDataType()) || "uint".equals(function.getDataType())){//只有曲线参数进行 最大 最小值校验
        	Float inputValue = Float.valueOf(ctrlDTO.getPointValue());
        	if (ctrlPoint.getMaxData() != null && ctrlPoint.getMinData() != null
        			&& !"".equals(ctrlPoint.getMaxData())
        			&& !"".equals(ctrlPoint.getMinData())) {
        		if (!(Double.parseDouble(ctrlPoint.getMinData()) <= inputValue && inputValue <= Double.parseDouble(ctrlPoint.getMaxData())))
        			return fail(CTRL_INVALID_VALUE);
        	}
        }
       //Float value = inputValue / ctrlPoint.getRatio();
		if( ctrlPoint.getRatio()==0){//如果是 0  说明是 null
			ctrlPoint.setRatio(1);
		}
		String pointValue  = "";
		if("Float".equals(ctrlPoint.getDataType()) || "uint".equals(function.getDataType())){
			BigDecimal multiply = new BigDecimal(ctrlDTO.getPointValue()).divide(new BigDecimal(ctrlPoint.getRatio()),0,BigDecimal.ROUND_HALF_UP);
			pointValue = multiply.toString();
		}else{
			pointValue=ctrlDTO.getPointValue();
		}

        HttpResult httpResult = null;
//        String key = deviceId + "." + pointCode;
//        CtrlParm ctrlParm = new CtrlParm(key, ctrlDTO.getPointValue());

        if (pointCode.contains("Set")) {
        	log.error("有set开头的测点需要下控："+deviceId+pointCode);
//            httpResult = httpAPIService.doPostObject(downUrl, ctrlParm);
//            if (httpResult.getCode() == HTTP_STATUS_OK) {
//                RemoteCtrl remoteCtrl = parseObject(httpResult.getBody(), RemoteCtrl.class);
//                if (remoteCtrl != null && remoteCtrl.getRet() == REMOTE_CTRL_STATUS){
//                    // SetNo2GivenFrequency --> No2GivenFrequencyConfirm, 进行确定设置
//                    String tmp = pointCode.substring(3);
//                    String newKey = deviceId + "." + tmp + "Confirm";
//                    CtrlParm setZero = new CtrlParm(newKey, "0");
//                    HttpResult zeroStatus = httpAPIService.doPostObject(downUrl, setZero);
//                    if (zeroStatus.getCode() == HTTP_STATUS_OK) {
//                        RemoteCtrl zeroRemote = parseObject(zeroStatus.getBody(), RemoteCtrl.class);
//                        if (zeroRemote != null && zeroRemote.getRet() == REMOTE_CTRL_STATUS){
//                            CtrlParm setOne = new CtrlParm(newKey, "1");
//                            HttpResult oneStatus = httpAPIService.doPostObject(downUrl, setOne);
//                            if (oneStatus.getCode() == HTTP_STATUS_OK) {
//                                RemoteCtrl oneRemote = parseObject(oneStatus.getBody(), RemoteCtrl.class);
//                                if (oneRemote != null && oneRemote.getRet() == REMOTE_CTRL_STATUS) return success(CTRL_SUCCESS);
//                            }
//
//                        }
//                    }
//                }
//            }
        } else {
        	if(!PointEnum.getPointEnumKeyByType("1").contains(pointCode)&&
        			!PointEnum.getPointEnumKeyByType("2").contains(pointCode)&&
        			!PointEnum.getPointEnumKeyByType("3").contains(pointCode)){
        		return  fail(CTRL_ERROR_NULL);
        	}
        	//先判断是否是网关。 不是走工控机
        	List<GwDevice> getgwIdByDeviceId = pumpConfigurationMapper.getgwIdByDeviceId(deviceId);
        	if(getgwIdByDeviceId!=null && getgwIdByDeviceId.size()>0){
        		//网关
        		httpResult = newRemoteCtrlByGW(deviceId, pointCode, Integer.parseInt(pointValue));
        	}else{
	        	//工控机下控
	        	httpResult = newRemoteCtrl(deviceId, pointCode, Integer.parseInt(pointValue));
        	}
        	log.info("下控结果"+httpResult);
            //httpResult = httpAPIService.doPostObject(downUrl, ctrlParm);
            if(httpResult==null){
                return fail(CTRL_ERROR);
            }
            if (httpResult.getCode() == HTTP_STATUS_OK) {
                RemoteCtrl remoteCtrl = parseObject(httpResult.getBody(), RemoteCtrl.class);
                log.info(httpResult.getBody());
                log.info(remoteCtrl.getRet()+"");
                log.info("下控返回结果："+remoteCtrl);
                if (remoteCtrl != null && remoteCtrl.getRet() == REMOTE_CTRL_STATUS){
                    return success(CTRL_SUCCESS);
                }else{
                	log.error("请求结果错误"+remoteCtrl.getMsg());
                }
            }
        }

        return fail(CTRL_ERROR);
    }

    public HttpResult newRemoteCtrlByGW(String deviceId, String pointCode,Integer pointValue) {
    	Map<String, Object> map = new HashMap<String,Object>();
    	//直接 原测点 下控
		Map<String, Object> mapCode = new HashMap<String,Object>();
    	mapCode.put(pointCode, pointValue);
    	map.put("values", mapCode);
    	map.put("deviceId", deviceId);
    	map.put("timestamp", DateUtils.formatDateTime(new Date()));
    	String jsonString = JSONUtils.toJSONString(map);
    	JSONObject parseObject2 = JSONObject.parseObject(jsonString);
    	log.info("下控参数json:"+jsonString);
    	HttpResult httpResult = null;
    	try {
    		httpResult = httpAPIService.doPostObject(newDownUrl, parseObject2);
			System.out.println(httpResult);
		} catch (Exception e) {
			log.error("下控结果异常",e);
		}
    	//第三种
    	if(PointEnum.getPointEnumKeyByType("3").contains(pointCode)){
    		//再次复位
    		if(httpResult!=null){
    			if (httpResult.getCode() == HTTP_STATUS_OK) {
    				 RemoteCtrl remoteCtrl = parseObject(httpResult.getBody(), RemoteCtrl.class);
    	                if (remoteCtrl != null && remoteCtrl.getRet() == REMOTE_CTRL_STATUS){
    	                	try {
    	    	    			Map<String, Object> temp = new HashMap<String,Object>();
    	    	    			temp.put(pointCode, 0);//复位
    	    	            	map.put("values", temp);
    	    	            	log.info("再次下控参数复位json:"+JSONUtils.toJSONString(map));
    							httpResult = httpAPIService.doPostObject(newDownUrl, JSONObject.parseObject(JSONUtils.toJSONString(map)));//json对象
    							log.info("下控复位结果："+httpResult);
    	    	    		} catch (Exception e) {
    	    	    			log.error("下控复位结果异常",e);
    	    	    		}
    	                }
    			}
    		}
    	}
    	return httpResult;
	}
	/**
     *
     * @param deviceId
     * @param pointCode
     * @param pointValue  除以倍率后的结果
     */
    public  HttpResult newRemoteCtrl(String deviceId,String pointCode,Integer pointValue){
    	//第一种数值类的下控 需要转换 set / confirm 结构 传输
    	Map<String, Object> map = new HashMap<String,Object>();
    	if(PointEnum.getPointEnumKeyByType("1").contains(pointCode)){
    		List<PointEnum> list = PointEnum.getPointEnumByType("1");
    		for (PointEnum pointEnum : list) {
				if(pointCode.equals(pointEnum.getCode())){
			  		pointCode=pointEnum.getMessage();
		        	Map<String, Object> mapCode = new HashMap<String,Object>();
		        	mapCode.put("Set"+pointCode, pointValue);
		        	mapCode.put(pointCode+"Confirm", 1);
		        	map.put("values", mapCode);
				}
			}
    	}
    	//第二种  测点 直接下控
    	if(PointEnum.getPointEnumKeyByType("2").contains(pointCode) ||
    			PointEnum.getPointEnumKeyByType("3").contains(pointCode)){
    		Map<String, Object> mapCode = new HashMap<String,Object>();
        	mapCode.put(pointCode, pointValue);
        	map.put("values", mapCode);
    	}
    	map.put("deviceId", deviceId);
    	map.put("timestamp", DateUtils.formatDateTime(new Date()));
    	String jsonString = JSONUtils.toJSONString(map);
    	JSONObject parseObject2 = JSONObject.parseObject(jsonString);
    	log.info("下控参数json:"+jsonString);
    	HttpResult httpResult = null;
    	try {
    		httpResult = httpAPIService.doPostObject(newDownUrl, parseObject2);
			System.out.println(httpResult);
		} catch (Exception e) {
			log.error("下控结果异常",e);
		}
    	//第一种 再次 comfirm
    	if(PointEnum.getPointEnumKeyByType("1").contains(pointCode)){
    		if(httpResult!=null){
    			if (httpResult.getCode() == HTTP_STATUS_OK) {
    				 RemoteCtrl remoteCtrl = parseObject(httpResult.getBody(), RemoteCtrl.class);
    	                if (remoteCtrl != null && remoteCtrl.getRet() == REMOTE_CTRL_STATUS){
				    		//再次confirm
				    		List<PointEnum> list = PointEnum.getPointEnumByType("1");
				    		for (PointEnum pointEnum : list) {
								if(pointCode.equals(pointEnum.getCode())){
							  		pointCode=pointEnum.getMessage();
						        	Map<String, Object> mapCode = new HashMap<String,Object>();
						        	mapCode.remove("Set"+pointCode);
						        	mapCode.put(pointCode+"Confirm", 0);//
						        	map.put("values", mapCode);
						        	log.info("再次下控参数Confirm-json:"+JSONUtils.toJSONString(map));
									try {
										httpResult = httpAPIService.doPostObject(newDownUrl, JSONObject.parseObject(JSONUtils.toJSONString(map)));//json对象
										log.info("下控Confirm结果："+httpResult);
									} catch (Exception e) {
										log.error("下控Confirm结果异常",e);
									}
								}
							}
    	                }
    	             }
    			}
    	}
    	//第三种
    	if(PointEnum.getPointEnumKeyByType("3").contains(pointCode)){
    		//再次复位
    		if(httpResult!=null){
    			if (httpResult.getCode() == HTTP_STATUS_OK) {
    				 RemoteCtrl remoteCtrl = parseObject(httpResult.getBody(), RemoteCtrl.class);
    	                if (remoteCtrl != null && remoteCtrl.getRet() == REMOTE_CTRL_STATUS){
    	                	try {
    	    	    			Map<String, Object> mapCode = new HashMap<String,Object>();
    	    	            	mapCode.put(pointCode, 0);//复位
    	    	            	map.put("values", mapCode);
    	    	            	log.info("再次下控参数复位json:"+JSONUtils.toJSONString(map));
    							httpResult = httpAPIService.doPostObject(newDownUrl, JSONObject.parseObject(JSONUtils.toJSONString(map)));//json对象
    							log.info("下控复位结果："+httpResult);
    	    	    		} catch (Exception e) {
    	    	    			log.error("下控复位结果异常",e);
    	    	    		}
    	                }
    			}
    		}
    	}
    	return httpResult;
    }

    /**
     * 下控权限验证
     * @param userId
     * @param delFlag
     * @return
     */
    private Boolean hasPermissionCtrl(String userId,String delFlag) {
        if(userId==null||"null".equals(userId)||"".equals(userId))return false;
        UserTreeSelection userTree = userDao.findUserTreeHave(userId,delFlag);
        if (userTree!=null&&userTree.getIsControl().equals("1")) return true;
        return false;
    }


    public String getCtrlParms(String deviceCode,String pumpId) {

        List<Point> paramsList = pumpConfigurationMapper.getCtrlParms(deviceCode);


        //默认为网关设备,不具备远程手动控制功能
        CtrlParam ctrlParam = new CtrlParam(2, 0);
        ctrlParam.setDeviceId(deviceCode);
        String doorSource="0";//无门禁
        String resquest;
        List<AccessDevice> accessDevice=accessDeviceDao.findByPumpId(pumpId);
        if(accessDevice!=null&&accessDevice.size()>0)doorSource="1";//海康门禁

        if (paramsList != null && paramsList.size() > 0) {
            // 如果没有配置 R_StartUp, 默认加上该测点.
            Map<String, List<Point>> listMap = paramsList.stream().collect(Collectors.groupingBy(Point::getPointCode));
            if (!listMap.containsKey("R_StartUp")){
                Point point = new Point();
                point.setPointCode("R_StartUp");
                point.setPointName("远程功能开");
                point.setPointType("bool");
                paramsList.add(point);
            }
            if (!listMap.containsKey("R_E_stop_feedback")){
                Point point = new Point();
                point.setPointCode("R_E_stop_feedback");
                point.setPointName("远程急停复位反馈");
                point.setPointType("bool");
                paramsList.add(point);
            }
            /*if(listMap.containsKey("DoorAccess")&&!"1".equals(doorSource)){
                doorSource="2";//测点门禁
            }*/
            // 获取 下控参数的实时数据
            String tableName = PumpConfigurationService.createTableName();
            List<String> idService = new ArrayList<String>();
            for (Point plis:paramsList) {
                String pointCode=plis.getPointCode();
                idService.add(deviceCode+"\\"+pointCode);
            }

            List<PointDate> realtimeDataJson =pumpConfigurationMapper.getPointData(tableName,deviceCode,idService);

            Map<String, Object> values = new HashMap<>();
            if (realtimeDataJson!=null) {
                values = realtimeDataJson.stream().collect(Collectors.toMap(PointDate::getPointCode, PointDate::getPointValue));
            }
            int pumpNum = 0;
            String regEx = "^No[0-9]_R_Stop$";
            Pattern pattern = Pattern.compile(regEx);
            String regExs = "^No[0-9]_R_Manual$";
            Pattern patterns = Pattern.compile(regExs);

            List<Point> hasNoList = new ArrayList<>();
            List<Point> notNoList = new ArrayList<>();

            for (Point point : paramsList) {
                String pointId = point.getPointCode();
                if (pointId == null) break;
                if ("R_StartUp".toLowerCase().equals(pointId.toLowerCase())) {
                    // 如果现场设备没有该测点，则设置为默认值1
                    point.setPointValue(getMapValue(values, pointId, 1f));
                    ctrlParam.setrStartUp(point);
                } else if ("R_E_stop".toLowerCase().equals(pointId.toLowerCase())) {
                    point.setPointValue(getMapValue(values, pointId, 0f));
                    ctrlParam.setrEStop(point);
                } /*else if ("DoorAccess".toLowerCase().equals(pointId.toLowerCase())) {
                    point.setPointValue(getMapValue(values, pointId, 0f));
                    ctrlParam.setDoorAccess(point);
                }*/ else if ("R_Auto".toLowerCase().equals(pointId.toLowerCase())) {
                    point.setPointValue(getMapValue(values, pointId, 0f));
                    ctrlParam.setrAuto(point);
                } else if ("R_E_stop_feedback".toLowerCase().equals(pointId.toLowerCase())) {
                    point.setPointValue(getMapValue(values, pointId, 0f));
                    ctrlParam.setRfeedback(point);
                } else if ("R_Manual".toLowerCase().equals(pointId.toLowerCase())) {
                    // 如果包含 R_Manual 则该设备具有远程手动控制功能
                    ctrlParam.setManualStatus(1);
                    point.setPointValue(getMapValue(values, pointId, 0f));
                    ctrlParam.setrManual(point);
                } else if (pointId.contains("Set")) {
                    // 包含Set表示工控机,同时把 没有Set的测点的值赋给有Set的测点.
                    ctrlParam.setDeviceType(1);
                    String replace = pointId.replace("Set", "");
                    point.setPointValue(getMapValue(values, replace, 0f));
                } else {
                    point.setPointValue(getMapValue(values, pointId, 0f));
                }
                // 获取水泵最大值
                if(pattern.matcher(pointId).matches()||patterns.matcher(pointId).matches()){
                    String pNo = pointId.substring(2, 3);
                    int pint = Integer.parseInt(pNo);
                    if (pumpNum < pint) pumpNum = pint;
                }

                // 取出包含No字符的测点
                if (pointId.contains("No")){
                    hasNoList.add(point);
                }else {
                    notNoList.add(point);
                }
            }

            // 对notNoList进行分组,real类型数据直接赋给setParams
            Map<String, List<Point>> notNoMap = notNoList.stream().collect(Collectors.groupingBy(Point::getPointType));
            List<Point> real = notNoMap.get("real");
            ctrlParam.setSetParam(real);

            // 对hasNoList装换为No1,No2....等多个水泵数据信息,赋给pumpParams
            Map<String, Point> hasNoMap = hasNoList.stream().collect(Collectors.toMap(Point::getPointCode, a -> a, (k1, k2) -> k1));
            List<List<Point>> pumpList = new ArrayList<>();
            for (int i = 1; i <= pumpNum; i++) {
                List<Point> list = new ArrayList<>();
                String noStart = "No" + i + "_R_Manual";
                String noStartManual = "No" + i + "_R_M_PowerFreq";
                String noStop = "No" + i + "_R_Stop";
                String noGivenFrequency = "No" + i + "GivenFrequency";
                String noSetGivenFrequency = "SetNo" + i + "GivenFrequency";
                String noFrequencyOper = "No" + i + "FrequencyOper";

                if (hasNoMap.containsKey(noStart) ){
                    list.add(hasNoMap.get(noStart));
                } else if( hasNoMap.containsKey(noStartManual)){
                    list.add(hasNoMap.get(noStartManual));
                }

                if (hasNoMap.containsKey(noStop) ){
                    list.add(hasNoMap.get(noStop));
                }

                if (hasNoMap.containsKey(noGivenFrequency) ){
                    list.add(hasNoMap.get(noGivenFrequency));
                } else if( hasNoMap.containsKey(noSetGivenFrequency)){
                    list.add(hasNoMap.get(noSetGivenFrequency));
                }

                if(hasNoMap.containsKey(noFrequencyOper)){
                    list.add(hasNoMap.get(noFrequencyOper));
                }
                if (list.size() > 0) pumpList.add(list);
            }
            ctrlParam.setPumpParam(pumpList);
        } else {
            resquest=new Gson().toJson(failDor(SYS_NOT_EXIST,doorSource));
            return resquest;
        }
        resquest=new Gson().toJson(successDor(ctrlParam,doorSource));
        return resquest;
    }

    private Float getMapValue(Map<String, Object> map, String key, Float setValue) {
        if (map.containsKey(key)) {
            String textValue = String.valueOf(map.get(key));
            return Float.valueOf(textValue);
        } else {
            return setValue;
        }
    }

    public RestResult<String> controalDoor(CtrlDTO ctrlDTO,String userId,String delFlag) throws IOException {
        //判断是否有权限pointCode，deviceId是否为空
        if (ctrlDTO.getDeviceId() == null || !hasPermissionCtrl(userId,delFlag)) return fail(CTRL_FORBIDDEN);
        String msg=countService.controalDoor(ctrlDTO.getDeviceId(),ctrlDTO.getPointValue());
        if("0".equals(msg))return fail(CTRL_ERROR);
        if("1".equals(msg))return fail(CTRL_NULL_DOOR);
        return success(msg);
    }

	/**
	 * 下控测点
	 * @param ctrlDTO
	 * @return
	 */
    public RestResult<String> remoteCtrlByluzhou(CtrlDTO ctrlDTO,String userId) {
    	Boolean hasPermissionCtrl = hasPermissionCtrl(userId, "0");
    	if(StringUtils.isEmpty(ctrlDTO.getPointValue())){return fail(ErrorCode.POINT_VALUE_EMPTY);}
    	if(!hasPermissionCtrl){return fail(CTRL_FORBIDDEN);}
    	Services getserviceMaxMin = pumpConfigurationMapper.getserviceMaxMin(ctrlDTO.getDeviceId(), ctrlDTO.getPointCode());
    	if(getserviceMaxMin!=null){
    		if(!(Double.parseDouble(getserviceMaxMin.getRangeminimum()) <= Double.parseDouble(ctrlDTO.getPointValue())
    				&& Double.parseDouble(ctrlDTO.getPointValue())<= Double.parseDouble(getserviceMaxMin.getRangemaximum()))){
    			return fail(CTRL_INVALID_VALUE);
    		}
    		//倍率转换
    		BigDecimal multiply = new BigDecimal(ctrlDTO.getPointValue()).divide(getserviceMaxMin.getRatio(),0,BigDecimal.ROUND_HALF_UP);
    		ctrlDTO.setPointValue(multiply.toString());
    	}
    	Map<String, Object> map = new HashMap<String,Object>();
    	//直接 原测点 下控
		Map<String, Object> mapCode = new HashMap<String,Object>();
    	mapCode.put(ctrlDTO.getPointCode(), ctrlDTO.getPointValue());
    	map.put("values", mapCode);
    	map.put("deviceId", ctrlDTO.getDeviceId());
    	map.put("timestamp", DateUtils.formatDateTime(new Date()));
    	String jsonString = JSONUtils.toJSONString(map);
    	JSONObject parseObject2 = JSONObject.parseObject(jsonString);
    	log.info("下控参数json:"+jsonString);
    	HttpResult httpResult = null;
    	try {
    		httpResult = httpAPIService.doPostObject(newDownUrl, parseObject2);
		} catch (Exception e) {
			log.error("下控结果异常",e);
		}
    	log.info("下控结果"+httpResult);
        if(httpResult==null){
            return fail(CTRL_ERROR);
        }
        if (httpResult.getCode() == HTTP_STATUS_OK) {
            RemoteCtrl remoteCtrl = parseObject(httpResult.getBody(), RemoteCtrl.class);
            log.info(httpResult.getBody());
            log.info(remoteCtrl.getRet()+"");
            log.info("下控返回结果："+remoteCtrl);
            if (remoteCtrl != null && remoteCtrl.getRet() == REMOTE_CTRL_STATUS){
                return success(CTRL_SUCCESS);
            }else{
            	log.error("请求结果错误"+remoteCtrl.getMsg());
            }
        }
        return fail(CTRL_ERROR);
	}

    public List<SysDict> selectSysDictByType(String type){
        if (StringUtils.isEmpty(type)) return null;
        return pumpConfigurationMapper.selectSysDictByType(type);
    }

}
