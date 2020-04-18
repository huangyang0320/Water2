package com.wapwag.woss.modules.sys.service;

import com.wapwag.woss.modules.sys.dao.SortDao;
import com.wapwag.woss.modules.sys.utils.AlarmEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SortService {
    @Autowired
    private SortDao sortDao;

    /**
     * 禁止 或者  启用告警
     * @param code
     * @return
     */
    public boolean getSortValueByCode(String code){
        if(StringUtils.isNotBlank(code)){
            String result = sortDao.getSortValueByCode(code);
            if(StringUtils.isNotBlank(result) && AlarmEnum.STOP.getValue().equals(result)){
                //屏蔽告警
                return false;
            }else if(StringUtils.isNotBlank(result) && AlarmEnum.START_UP.getValue().equals(result)){
                //告警提醒
                return true;
            }
        }
        return false;

    }
}
