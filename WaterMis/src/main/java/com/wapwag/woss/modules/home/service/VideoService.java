package com.wapwag.woss.modules.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.wapwag.woss.modules.home.dao.VideoDao;
import com.wapwag.woss.modules.home.entity.HikQryInfo;
import com.wapwag.woss.modules.home.entity.VideoInfo;
import com.wapwag.woss.modules.home.util.CodeUtil;
import com.wapwag.woss.modules.home.util.VideoPool;
import com.wapwag.woss.modules.home.util.hik.HikLogin;
import com.wapwag.woss.modules.home.util.hik.OpenApiHttp;
/**
 * 视频Service
 * 
 * @author gongll
 * @version 2017-10-27 14:16
 */
@Service
public class VideoService {

    private static final String LOGIN_URL = "/vms/services/VmsSdkWebService/sdkLogin?";
    private static final String LOGIN_OUT_URL = "/vms/services/VmsSdkWebService/sdkLogout?";
    
    private static final String TOKEN_URL = "/vms/services/VmsSdkWebService/applyToken?";
    private static final String SOURCE_URL = "/vms/services/VmsSdkWebService/getPreviewOcxOptions?";
    private static final String PLAY_BACK_URL = "/vms/services/VmsSdkWebService/getPlaybackOcxOptions?";

    private static final Pattern LOGIN_PATTERN = Pattern.compile("tgt=\"(.+?)\"");
    private static final Pattern TOKEN_PATTERN = Pattern.compile("st=\"(.+?)\"");
    private static final Pattern SOURSE_PATTERN = Pattern.compile("Preview(.+?)Preview");
    private static final Pattern BACK_PATTERN = Pattern.compile("PlaybackBasicInfo(.+?)PlaybackBasicInfo");
    
    private static Map<String, String> tokens = new HashMap<String, String>();
    private static Map<String, String> appKeys = new HashMap<String, String>();
    
    private static final String VIDEO_IP = "139.196.77.237";
    private static final String VIDEO_PORT = "80";
    
    private static Logger log = LoggerFactory.getLogger(VideoService.class);

    
    //海康登录对象
  	private static HikLogin HIK_LOGIN = null;

      /**  */
  	private static final Long INIT_MAX_TIME = 600000L;

    private final VideoDao videoMapper;

    @Autowired
    public VideoService(VideoDao videoMapper) {
        this.videoMapper = videoMapper;
    }



    /**
     * 获取Appkey
     * @param info
     * @return
     */
    public String hikAppKey(String videoNo) {
    	return getKey(videoNo);
    }

    /**
     * 获取实时视频xml
     * @param info
     * @return
     */
    public String hikRealSourseXML(String videoNo) {
    	if (!isEmpty(VideoPool.getVideXml(videoNo))) {
			return VideoPool.getVideXml(videoNo);
		}
    	String appKey = getKey(videoNo);
    	if (isEmpty(appKey)) {
			return "";
		}
        return getResouseByCode(videoNo,appKey);
    }
    

    /**
     * 获取回放xml
     * @param info
     * @return
     */
    public String hikBackResouseXML(HikQryInfo info) {
    	String appKey = getKey(info.getVideoNo());
    	if (isEmpty(appKey)) {
			return "";
		}
        return getBackXml(info,appKey);
    }

    private String getKey(String code){
    	VideoInfo info = videoMapper.getVideoByCode(code);
        if (null == info) {
			return "";
		}
        String token = tokens.get(info.getUserName());
        if (isEmpty(token)) {
        	token = getToken(info);
		}
        String appKey = getAppKey(token);
        if (isEmpty(appKey)) {
        	token = getToken(info);
        	appKey = getAppKey(token);
		}
        return appKey;
    }
    
    /**
     * 获取登录获取Token
     * @param info
     * @return
     */
    private String getToken(VideoInfo info) {
    	log.info("Enter VideoService getToken()");
        String loginToken = "";
        BasicNameValuePair[] nameValuePairs = new BasicNameValuePair[5];
        nameValuePairs[0] = new BasicNameValuePair("loginAccount", info.getUserName());
        nameValuePairs[1] = new BasicNameValuePair("password", CodeUtil.Encode(info.getUserPassword()));
        nameValuePairs[2] = new BasicNameValuePair("serviceIp", VIDEO_IP);
        nameValuePairs[3] = new BasicNameValuePair("clientIp", "223.5.5.5");
        nameValuePairs[4] = new BasicNameValuePair("clientMac", "1w:3e:4r:12:43:12");
        String url = "Http://";
        if (isEmpty(VIDEO_PORT)) {
            url += VIDEO_IP;
        } else {
            url += VIDEO_IP + ":" + "80";
        }
        try {
            String result = Request.Post(url + LOGIN_URL + System.currentTimeMillis()).bodyForm(nameValuePairs).execute().returnContent().asString();

            Matcher matcher = LOGIN_PATTERN.matcher(result);

            if (matcher.find()) {
                loginToken = matcher.group();
                loginToken = loginToken.replace("tgt=", "").replace("\"", "");
            }
        } catch(Exception e) {
        	log.error("视频，getTiken异常:",e);
        }
        log.info("loginToken:" + loginToken);
        if (!isEmpty(loginToken)) {
			tokens.put(info.getUserName(), loginToken);
		}
        log.info("Exit VideoService getToken()");
        return loginToken;
    }
    
    /**
     * 获取AppKey
     * @param info
     * @return
     */
    private String getAppKey(String token) {
    	log.info("Enter VideoService getAppKey()");
        String loginToken = "";
        String url = "Http://";
        url += VIDEO_IP + ":" + "80";
        try {
            BasicNameValuePair[] nameValuePairs = new BasicNameValuePair[1];
            nameValuePairs[0] = new BasicNameValuePair("tgc", token);
            String result = Request.Post(url + TOKEN_URL + System.currentTimeMillis()).bodyForm(nameValuePairs).execute().returnContent().asString();

            Matcher matcher = TOKEN_PATTERN.matcher(result);

            if (matcher.find()) {
                loginToken = matcher.group();
                loginToken = loginToken.replace("st=", "").replace("\"", "");
            }

        } catch(Exception e) {
        	log.error("视频，getAppKey异常:",e);
        }
        log.info("appk:" + loginToken);
        log.info("Exit VideoService getAppKey()");
        return loginToken;
    }


    /**
     * 获取实时视频流
     * @param info
     * @return
     */
    private String getResouseByCode(String videoNo,String appKey) {
    	log.info("Enter VideoService getResouse()");
    	String loginToken = "";
        String url = "Http://";
        url += VIDEO_IP + ":" + "80";
        try {
            //获取资源XML
            BasicNameValuePair[] nameValuePairs = new BasicNameValuePair[3];
            nameValuePairs[0] = new BasicNameValuePair("token", appKey);
            nameValuePairs[1] = new BasicNameValuePair("cameraIndexCode", videoNo);
            nameValuePairs[2] = new BasicNameValuePair("clientIp", VIDEO_IP);
            String result = Request.Post(url + SOURCE_URL + System.currentTimeMillis()).bodyForm(nameValuePairs).execute().returnContent().asString();

            Matcher matcher = SOURSE_PATTERN.matcher(result);

            if (matcher.find()) {
                loginToken = matcher.group();
                loginToken = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><" + loginToken + ">";
                loginToken = loginToken.replace("&lt;", "<").replace("&gt;", ">").replace("&#x4e49;&#x4e4c;&#x7eff;&#x57ce;&#x73ab;&#x7470;&#x56ed;", "义乌绿城玫瑰园");
                VideoPool.addVide(videoNo, loginToken);
            }

        } catch(Exception e) {
        	log.error("视频，getResouseByCode异常:",e);
        }
        log.info("Resouse:" + loginToken);
        log.info("Exit VideoService getResouse()");
        return loginToken;
    }
    /**
	 * 查询视频回放xml
	 * @param info
	 * @return
	 */
    private String getBackXml(HikQryInfo info,String appKey) {
    	log.info("Enter VideoService getBackXml()");
        String result = "";
        String url = "Http://";
        url += VIDEO_IP + ":" + "80";
        try {
            //获取资源XML
            BasicNameValuePair[] nameValuePairs = new BasicNameValuePair[6];
            nameValuePairs[0] = new BasicNameValuePair("token", appKey);
            nameValuePairs[1] = new BasicNameValuePair("cameraIndexCode", info.getVideoNo());
            nameValuePairs[2] = new BasicNameValuePair("clientIp", VIDEO_IP);
            nameValuePairs[3] = new BasicNameValuePair("beginTime", info.getBeginTime());
            nameValuePairs[4] = new BasicNameValuePair("endTime", info.getEndTime());
            nameValuePairs[5] = new BasicNameValuePair("storeDeviceType", "0");
            String resultBody = Request.Post(url + PLAY_BACK_URL + System.currentTimeMillis()).bodyForm(nameValuePairs).execute().returnContent().asString();

            Matcher matcher = BACK_PATTERN.matcher(resultBody);

            if (matcher.find()) {
                result = matcher.group();
                result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><" + result + ">";
                result = result.replace("&lt;", "<").replace("&gt;", ">").replace("&#x4e49;&#x4e4c;&#x7eff;&#x57ce;&#x73ab;&#x7470;&#x56ed;", "义乌绿城玫瑰园");
            }
        } catch(Exception e) {
        	log.error("视频，getBackXml异常:",e);
        }
        log.info("backXml:" + result);
        log.info("Exit VideoService getBackXml()");
        return result;
    }
    
    /**
     * 校验视频信息是否合法
     * @param info
     * @return
     */
    private boolean checkVideoInfo(VideoInfo info) {
        boolean isLegit = true;
        if (null == info) {
            isLegit = false;
        }
        
        if (isEmpty(info.getUserName())) {
            isLegit = false;
        } 
        
        if (isEmpty(info.getUserPassword())) {
            isLegit = false;
        }
        
        return isLegit;
    }
    
    private boolean isEmpty(String str){
    	if (null == str || "".equals(str) || "null".equals(str)) {
			return true;
		}
    	return false;
    }

    /**********************8700平台接口******************************/
    /**
	 * 根据视频ID获取视频流地址
	 * @param dto
	 * @return
	 */
	public String getVideoUrlByVideoUUid(String videoCode){
		
		if (null == HIK_LOGIN || System.currentTimeMillis() - HIK_LOGIN.getInitTime() > INIT_MAX_TIME) {
			createLogin();
		}
		
		if (null == HIK_LOGIN || StringUtils.isEmpty(videoCode)) {
			return videoCode;
		}
		return OpenApiHttp.getPreview(HIK_LOGIN, videoCode);
	}
    
    /**
   	 * 根据视频ID获取回放视频流地址
   	 * @param dto
   	 * @return
   	 */
   	public String getBackResouseByCode8700(String videoCode){
   		
   		if (null == HIK_LOGIN || System.currentTimeMillis() - HIK_LOGIN.getInitTime() > INIT_MAX_TIME) {
   			createLogin();
   		}
   		
   		if (null == HIK_LOGIN || StringUtils.isEmpty(videoCode)) {
   			return videoCode;
   		}
   		return OpenApiHttp.getBackResouse(HIK_LOGIN, videoCode);
   	}

    /**
	 * 初始化海康登录数据
	 */
    private void createLogin(){
		
		List<Map<String, Object>> conf = videoMapper.hikConf();
		Map<String, String> temp = new HashMap<String, String>();
		
		for (int i = 0; i < conf.size(); i++) {
			
			temp.put(conf.get(i).get("label").toString(), conf.get(i).get("value").toString());
		}
		
		HikLogin info = new HikLogin();
		if (null != temp.get("ApiKey")) {
			info.setAppKey(temp.get("ApiKey"));
		}else {
			return;
		}
		
		if (null != temp.get("AppSecret")) {
			info.setSecret(temp.get("AppSecret"));
		}else {
			return;
		}
		
		if (null != temp.get("ApiUrl") && null != temp.get("ApiPort")) {
			info.setUrl("http://" + temp.get("ApiUrl") + ":" + temp.get("ApiPort"));
		}else {
			return;
		}
		
		if (null != temp.get("AppUser")) {
			info.setUserName(temp.get("AppUser"));
		}else {
			return;
		}
		
		if (null != temp.get("VideoUrl") && null != temp.get("VideoPort")) {
			info.setVideoUrl("http://" + temp.get("VideoUrl") + ":" + temp.get("VideoPort"));
		}else {
			return;
		}
		
		if (null != temp.get("ZoonName")) {
			info.setVideoIP(temp.get("ZoonName"));
		}else {
			return;
		}
		
		//获取系统配置用户uuid
		String operUser = OpenApiHttp.getOperUser(info);
		if (!StringUtils.isEmpty(operUser)) {
			info.setUserUuid(operUser);
		}else {
			return;
		}
		
		//获取系统配置网络站点UUid
		String netZone = OpenApiHttp.getNetZoneUUid(info);
		if (!StringUtils.isEmpty(netZone)) {
			info.setNetZoneUUid(netZone);
		}else {
			return;
		}
		
		info.setInitTime(System.currentTimeMillis());
		HIK_LOGIN = info;
	}
}