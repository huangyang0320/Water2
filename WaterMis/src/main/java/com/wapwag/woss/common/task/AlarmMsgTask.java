package com.wapwag.woss.common.task;

import com.wapwag.woss.common.sms.Intranet.SendSMS;
import com.wapwag.woss.common.sms.service.impl.SendSmsOutServiceImpl;
import com.wapwag.woss.common.sms.service.impl.SendSmsServiceImpl;
import com.wapwag.woss.common.utils.SpringContextHolder;
import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.home.entity.AlarmSendStatus;
import com.wapwag.woss.modules.home.entity.AlarmStat;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.home.service.AlarmStatService;
import com.wapwag.woss.modules.home.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时任务对象
 * @author gongll
 *
 */
public class AlarmMsgTask {

	//内网
	private static SendSmsServiceImpl sendSmsServiceImpl = SpringContextHolder.getBean(SendSmsServiceImpl.class);
	//外网
	private static SendSmsOutServiceImpl sendSmsOutServiceImpl = SpringContextHolder.getBean(SendSmsOutServiceImpl.class);



	private static UserService userService = SpringContextHolder.getBean(UserService.class);
	private static AlarmStatService alarmStatService = SpringContextHolder.getBean(AlarmStatService.class);




	private static Logger LOG = LoggerFactory.getLogger(ServicValuesHourTask.class);
	private static boolean flag = true;

	public static void sendSms() {
			try {
				LOG.info("sendSmsServiceImpl sendSms....");
				//System.out.println("sendSmsServiceImpl sendSms....");
				List<AlarmStat> alarmList = alarmStatService.getAlarmDetailBySendStatus();
				List<User> userList=getAlarmUserList(alarmList);
				//封装告警发送状态对象
				List<AlarmSendStatus> alarmSendStatusList=new ArrayList<>();

				com.wapwag.woss.common.sms.Intranet.SendSMS sms =null;
				if(null!=userList && userList.size()>0){
					for(User u:userList){
						//获取相同设备下  不同测点的告警集合数据
						List<AlarmStat> alarmListFilter = alarmList.stream().filter(item -> item.getDeviceId().equals(u.getDeviceId())).collect(Collectors.toList());
						if(null!=alarmListFilter && alarmListFilter.size()>0){
							for(AlarmStat alarm:alarmListFilter){
								if(StringUtils.isNotBlank(u.getMobile())){
									//sms = new com.wapwag.woss.common.sms.outernet.SendSMS(u.getMobile(),alarm.getPhName()+"-->"+alarm.getDeviceName()+":"+alarm.getAlarmInfo(),"1");
									//boolean isS = sendSmsOutServiceImpl.sendSms(sms);


									//测试环境需要打开
									/*sms = new com.wapwag.woss.common.sms.Intranet.SendSMS(u.getMobile(),alarm.getPhName()+"-->"+alarm.getDeviceName()+":"+alarm.getAlarmInfo(),"1");
									boolean isS = sendSmsServiceImpl.sendSms(sms);
									//Thread.sleep(1000);
									//封装告警发送状态对象
									String isSuccess=isS?"Y":"N";
									String UUID= java.util.UUID.randomUUID().toString();
									AlarmSendStatus alarmSendStatus=new AlarmSendStatus(UUID,alarm.getId(),"0",u.getMobile(),isSuccess,new Date(),"admin","Y");
									alarmSendStatusList.add(alarmSendStatus);*/
								}
							}
						}
						System.out.println(u.getMobile());
					}
					//操作告警发送状态
					List<AlarmSendStatus> AlarmSendFail = alarmSendStatusList.stream().filter(item -> item.getIsSuccess().equals("N")).collect(Collectors.toList());
					alarmStatService.insertAlarmSendStatus(alarmSendStatusList);
				}

				LOG.info("sendSmsServiceImpl sendSms....");
			} catch (Exception e){
				e.printStackTrace();
			}

	}

	private static List<User> getAlarmUserList(List<AlarmStat> alarmList) {
		if(alarmList!=null && alarmList.size()>0){
			Map<String,AlarmStat> map=new HashMap<>();
			List<String> idList= new ArrayList<>();
			for(AlarmStat alarm :alarmList){
				idList.add(alarm.getDeviceId());
			}
			return userService.getAlarmSendUserList(idList);
		}
		return null;
	}
}