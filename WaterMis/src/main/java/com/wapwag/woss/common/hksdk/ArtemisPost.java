package com.wapwag.woss.common.hksdk;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;


/**
 * Auto Create on 2020-01-07 11:05:04
 */
public class ArtemisPost {
	/**
	 * 请根据自己的appKey和appSecret更换static静态块中的三个参数。
	 */
	static {
		ArtemisConfig.host = "10.61.96.29"; // artemis网关服务器ip端口
		ArtemisConfig.appKey = "25226760"; // 秘钥appkey
		ArtemisConfig.appSecret = "dfKxeeUTl7Snez4H67PY";// 秘钥appSecret
	}
	/**
	 * 能力开放平台的网站路径
	 * 路径不用修改，就是/artemis
	 */
	private static final String ARTEMIS_PATH = "/artemis";

	//获取资源列表v2
	public static String resources(ResourcesRequest resourcesRequest ){
		String resourcesDataApi = ARTEMIS_PATH +"/api/irds/v2/deviceResource/resources";
		Map<String,String> path = new HashMap<String,String>(2){
			{
				put("https://",resourcesDataApi);
			}
		};
		String body= JSON.toJSONString(resourcesRequest);
		String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
		return result;
	}

	//获取资源列表v2

	/**
	 * 8c097706fcbb4d378ca8d2fc1f731624
	 * fae6edc56161491e9cfec691d460f68a
	 * @param resourcesRequest
	 * @return
	 */
	public static String resources2(ResourcesRequest resourcesRequest ){
		String resourcesDataApi = ARTEMIS_PATH +"/api/acs/v1/door/states";
		Map<String,String> path = new HashMap<String,String>(2){
			{
				put("https://",resourcesDataApi);
			}
		};
		JSONObject door=new JSONObject();
		JSONArray indexId=new JSONArray();
		indexId.add("fae6edc56161491e9cfec691d460f68a");
		door.put("doorIndexCodes ",indexId);
		String body= door.toJSONString();
		String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
		return result;
	}

	//获取资源列表v2

	/**
	 * https://open.hikvision.com/docs/6df2abb4f76104afa1ffedc7cad47d93
	 *
	 参数名称	数据类型	是否必须	参数描述
	 doorIndexCodes	string[]	True	门禁点唯一标识可以通过查询门禁点列表接口获取，最大支持10个门禁点。
	 controlType	number	True	0: 常开
	 1: 门闭
	 2: 门开
	 3: 常闭
	 请求参数举例

	 json
	 {
	 "doorIndexCodes": [
	 "1f276203e5234bdca08f7d99e1097bba"
	 ],
	 "controlType": 3
	 }
	 返回参数

	 参数名称	数据类型	是否必须	参数描述
	 code	string	False	返回码
	 0: 成功
	 其他参考附录E.2.1 门禁管理错误码
	 msg	string	False	返回描述
	 （记录接口执行情况说明信息）
	 data	object[]	False	返回数据（具体接口返回信息结构，此处为反控结果信息）
	 +data	object	False
	 ++doorIndexCode	string	False	门禁点唯一标识
	 ++controlResultCode	number	False	0: 标识反控成功
	 其他: 表示失败，原因参考附录E.2.1 门禁管理错误码
	 ++controlResultDesc	string	False	与controlResultCode对应的描述，见附录E.2.1 门禁管理错误码
	 * @param resourcesRequest
	 * @return
	 */
	public static String resources3(ResourcesRequest resourcesRequest ){
		String resourcesDataApi = ARTEMIS_PATH +"/api/acs/v1/door/doControl";
		Map<String,String> path = new HashMap<String,String>(2){
			{
				put("https://",resourcesDataApi);
			}
		};
		JSONObject door=new JSONObject();
		JSONArray indexId=new JSONArray();
		indexId.add("fae6edc56161491e9cfec691d460f68a");
		door.put("doorIndexCodes",indexId);//doorIndexCodes
		door.put("controlType","2");
		String body= door.toJSONString();
		System.out.println(body);
		String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
		return result;
	}

	public static void main(String[] args) {
		ResourcesRequest re =new ResourcesRequest();
		re.setPageNo(1);
		re.setPageSize(500);
		re.setResourceType("camera");
		String result = resources(re);
		System.out.println(result);
		/*String result3 = resources3(null);
		System.out.println(result3);*/



		JSONObject jsonObj = JSONObject.parseObject(result);
		JSONObject data = jsonObj.getJSONObject("data");
		JSONArray list = data.getJSONArray("list");

		System.out.println(list);
		for(int i=0;i<list.size();i++){
			String indexCode = list.getJSONObject(i).getString("indexCode");
			System.out.println(indexCode);
		}

/*
		String result1 = resources2(null);
		System.out.println(result1);*/

	}
}
