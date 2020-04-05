package com.wapwag.woss.common.hksdk.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.common.hksdk.ArtemisPost;
import com.wapwag.woss.common.hksdk.ResourcesRequest;
import com.wapwag.woss.common.hksdk.service.IGetResourceListService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取设备的基本信息
 */
@Service
public class GetResourceListServiceImpl implements IGetResourceListService {

    /**
     * 根据字段名获取对应的字段信息
     * @param fieldName 如下：
     *
     *          "regionPath": "@root000000@",
     * 			"regionIndexCode": "root000000",
     * 			"regionPathName": "华衍水务",
     * 			"indexCode": "9ed77a41521b40d5a49b30788aa7de12",
     * 			"channelType": "digital",
     * 			"updateTime": "2019-12-25T23:25:25.313+08:00",
     * 			"sort": 1,
     * 			"cameraType": 2,
     * 			"cameraRelateTalk": "d637699e9eb24833b11bc770fd6b75e3",
     * 			"capability": "event_audio@io@event_veh_compare@event_veh@event_veh_recognition@event_ias@event_heat@drawFrameInPlayBack@ptz@record@vss@event_io@net@maintenance@event_device@status",
     * 			"transType": 1,
     * 			"createTime": "2019-12-25T23:22:16.730+08:00",
     * 			"name": "高速御府1",
     * 			"decodeTag": "hikvision",
     * 			"comId": "vms",
     * 			"parentIndexCode": "59f2923903b94f19a65f42e2f729b6bc",
     * 			"chanNum": 33,
     * 			"resourceType": "camera"
     *
     *
     *
     *
     * @return
     */
    @Override
    public JSONArray getDeviceList() {
        ResourcesRequest re =new ResourcesRequest();
        re.setPageNo(1);
        re.setPageSize(500);
        re.setResourceType("camera");
        String result = ArtemisPost.resources(re);
        JSONObject jsonObj = JSONObject.parseObject(result);
        JSONObject data = jsonObj.getJSONObject("data");
        JSONArray list = data.getJSONArray("list");
        /*List<String> listInfo=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            String fieldValue = list.getJSONObject(i).getString(fieldName);
            listInfo.add(fieldValue);
        }*/
        return  list;
    }
}
