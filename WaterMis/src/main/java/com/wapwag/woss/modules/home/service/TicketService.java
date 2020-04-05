package com.wapwag.woss.modules.home.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wapwag.woss.modules.config.HttpResult;
import com.wapwag.woss.modules.home.dao.PersonMapper;
import com.wapwag.woss.modules.home.dao.PumpHouseDao;
import com.wapwag.woss.modules.home.entity.*;
import com.wapwag.woss.modules.monitor.service.HttpAPIService;
import com.wapwag.woss.modules.sys.entity.BootPage;
import com.wapwag.woss.modules.sys.utils.DictUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * guoln
 * 2018-12-27
 */
/*@Service*/
public class TicketService {

    private final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private PumpHouseDao pumpHouseDao;
    
    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private HttpAPIService httpAPIService;

    @Value(value = "${workOrderUrl}")
    private String ticketUrl;

    private static final String TICKET_LIST = "/WaterMisTicket/getTicketList";

    public BootPage ticketList(TicketDTO ticketDTO) {
        TicketBO ticketBO = new TicketBO();
        List<String> idList = pumpHouseDao.selectPumpIdByUser(ticketDTO.getUserId());
        ticketBO.setPumpIdList(idList);
        ticketBO.setTemplateCode(ticketDTO.getTemplateCode());
        ticketBO.setAlarmType(ticketDTO.getAlarmType());
        ticketBO.setBeginTime(ticketDTO.getBeginTime());
        ticketBO.setEndTime(ticketDTO.getEndTime());
        ticketBO.setCurrentPage(ticketDTO.getPageNumber());
        ticketBO.setPageSize(ticketDTO.getPageSize());
        ticketBO.setPumpName(ticketDTO.getPumpName());
        ticketBO.setAlarmContent(ticketDTO.getAlarmContent());
        ticketBO.setCreateTimeStart(ticketDTO.getCreateBeginTime());
        ticketBO.setCreateTimeEnd(ticketDTO.getCreateEndTime());

        String s = new Gson().toJson(ticketBO);

        HttpResult httpResult = null;
        try {
            httpResult = httpAPIService.httpPost(ticketUrl + TICKET_LIST, s);
        } catch (Exception e) {
            logger.error("查询工单失败", e);
        }
        if(httpResult == null || 200 != httpResult.getCode()) return new BootPage(0, new ArrayList<TicketVO>());
        JSONObject jsonObject = parseObject(httpResult.getBody());
        JSONObject resultData = jsonObject.getJSONObject("resultData");
        if ("error".equals(jsonObject.getString("status")) || resultData == null){
            return new BootPage(0, new ArrayList<TicketVO>());
        }

        int total = resultData.getInteger("total");
        JSONArray records = resultData.getJSONArray("records");
        List<TicketVO> ticketVOList = new ArrayList<TicketVO>();
        for (Object record : records) {
            TicketVO ticketVO = parseObject(record.toString(), TicketVO.class);
            if(ticketVO.getCurrentUser().contains(",")){
                ticketVO.setCurrentUser("");
            }
            ticketVOList.add(ticketVO);
        }
        return new BootPage(total, ticketVOList);
    }

    public List<PersonPositionVO> personList(QueryDTO queryDTO){
        List<PersonPositionVO> allPerson = personMapper.getPersonNewPosition(queryDTO.getKeyword());
        int onlineMinuteInterval = getOnlineMinuteInterval();
        for (PersonPositionVO person : allPerson) {
            PersonPositionVO userInfo = personMapper.getUserInfoByMobile(person.getMobile());
            if (userInfo != null){
                person.setNo(userInfo.getNo());
                person.setName(userInfo.getName());
                person.setLoginName(userInfo.getLoginName());
                person.setOfficeName(userInfo.getOfficeName());
                person.setRoleName(userInfo.getRoleName());
                DateTime onlineTime = new DateTime().minusMinutes(onlineMinuteInterval);
                if (person.getCreateDate().getTime() < onlineTime.getMillis()){
                    person.setOnlineStatus("离线");//离线
                }else {
                    person.setOnlineStatus("在线");//在线
                }
            }
        }
        return allPerson;
    }

    private int getOnlineMinuteInterval(){
        int timeInterval = 10; //默认10分钟
        String minuteInterval = DictUtils.getDictLabel("1", "person_online_minute_interval", Integer.toString(timeInterval));
        timeInterval = Integer.parseInt(minuteInterval);
        return timeInterval;
    }

}
