package com.wapwag.woss.modules.monitor.dao;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.DeviceInfo;
import com.wapwag.woss.modules.biz.entity.Services;
import com.wapwag.woss.modules.biz.entity.SysDict;
import com.wapwag.woss.modules.monitor.pumpNode.*;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zx
 * @version 2018-06-14
 */
@MyBatisDao("com.wapwag.woss.modules.monitor.dao.PumpConfigurationMapper")
public interface PumpConfigurationMapper {
    PumpNodeVO getDeviceInfo(@Param("prefix") String prefix , @Param("phId") String phId);

    List<PumpPositionInfo> getPhPosition(@Param("phId") String phId);

    PumpPositionInfo getDefaultPumpPosition(@Param("prefix") String prefix);

    List<VideoInfo> getVideoInfo(@Param("phId") String phId);

    List<PhNode> getPhNode(@Param("phId") String phId);

    List<PumpService> getServiceValues(@Param("dateNumber")String dateNumber, @Param("idDevice")List<DeviceInfo> idDevice, @Param("code")List<String> code);

    List<PumpService> getServiceSetValues(@Param("idDevice")List<String> idDevice, @Param("code")List<String> code);


    List<Point> getCtrlParms(String deviceCode);

    List<PointDate> getPointData(@Param("tableName")String tableName,@Param("idDevice")String deviceCode,@Param("idService") List<String> idService);

    List<String> findDeviceCodeById(@Param("idDevice")List<DeviceInfo> idDevice);

    CtrlPoint getPointRatio(@Param("deviceId") String deviceId, @Param("pointCode") String pointCode);

    List<GwDevice> getgwIdByDeviceId(@Param("deviceId") String deviceId);

    CtrlPoint getFunctionById(@Param("functionId") String functionId);

    //兼容泸州下空
	Services getserviceMaxMin(@Param("deviceId") String deviceId, @Param("code") String code);

	List<SysDict> selectSysDictByType(String type);

    List<PumpService>  getDateHour(@Param("tableName")String tableName);

    List<PumpService>  getDatePv(@Param("tableName")String tableName);

    List<PumpService>  getPName();

    List<PumpService>  getPvData(@Param("pName")String tableName);


}
