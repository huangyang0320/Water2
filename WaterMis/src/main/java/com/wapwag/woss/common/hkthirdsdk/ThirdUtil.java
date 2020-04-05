package com.wapwag.woss.common.hkthirdsdk;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wapwag.woss.common.hkthirdsdk.pojo.AccessResp;
import com.wapwag.woss.common.hkthirdsdk.pojo.ClockRecord;
import com.wapwag.woss.common.hkthirdsdk.pojo.ControlResp;
import com.wapwag.woss.common.hkthirdsdk.pojo.DoorRecordRequest;
import com.wapwag.woss.common.hkthirdsdk.pojo.Login;
import com.wapwag.woss.common.hkthirdsdk.pojo.ThirdClockResult;
import com.wapwag.woss.common.hkthirdsdk.pojo.ThirdDoorResult;
import com.wapwag.woss.common.hkthirdsdk.pojo.UserResult;
import com.wapwag.woss.common.task.AccessRecordTask;
import com.wapwag.woss.common.utils.CodeUtil;
import com.wapwag.woss.common.utils.StringUtils;

public class ThirdUtil {

	/** 获取中心点xml */
	private static final String CONTROL_INFO = "<root><sessionId>SESSION_ID</sessionId><contentChr>0</contentChr></root>";
	
	/** 获取门禁信息xml */
	private static final String ACCESS_INFO = "<root><sessionId>SESSION_ID</sessionId><controlUnitIds>CONTROL_INFO</controlUnitIds><pageSize>10000</pageSize><pageIndex>1</pageIndex>  </root>";
	/** 获取用户信息xml */
	private static final String USER_INFO = "<root><sessionId>SESSION_ID</sessionId><personCode></personCode><deptCode>0000</deptCode><contentChr>true</contentChr></root>";
	
	
	private static Logger LOG = LoggerFactory.getLogger(ThirdUtil.class);
	
	/** 海康webservice地址 */
	private static String url = "";
	
	public static ThirdDoorResult accessRecord(
			DoorRecordRequest request) {

		ThirdDoorResult xj = null;
		LOG.debug("Exter accessRecord");
		String sessionId = qrySessionId();
		LOG.debug("SESSION ID " + sessionId);
		if (!StringUtils.isEmp(sessionId)) {
			request.setSessionId(sessionId);
			ThirdSdkServicePortTypeProxy proxy = new ThirdSdkServicePortTypeProxy(
					"");
			try {
				JAXBContext context = JAXBContext
						.newInstance(ThirdDoorResult.class);
				Marshaller marshaller = context.createMarshaller();
				Unmarshaller unmarshaller = context.createUnmarshaller();

				String result = proxy
						.getAccessEventPage(createDoorRecordXml(request));
				if (result.indexOf("errorCode>0</errorCode") > 0) {
					result = result.split("<body>")[1];
					result = result.split("</body>")[0];
					result = "<body>" + result + "</body>";
					result = result.replace("<items>", "");
					result = result.replace("</items>", "");

					xj = (ThirdDoorResult) unmarshaller
							.unmarshal(new StringReader(result));

				}
			} catch (Exception e) {
			}

		}
		return xj;
	}
	
	/**
	 * 查询所有部门
	 * @param sessionId
	 * @return
	 */
	public static List<String> getDepartments(){
		String sessionId = qrySessionId();
		List<String> deptIdList = new ArrayList<String>();
		if (StringUtils.isEmp(sessionId)) {
			return deptIdList;
		}
		String reqBody = "<root><sessionId>SESSION_ID</sessionId><deptCode>0000</deptCode><contentChr>1</contentChr></root>";
		reqBody = reqBody.replace("SESSION_ID", sessionId);
		LOG.info("getDepartments in" + reqBody);
		ThirdSdkServicePortTypeProxy proxy = new ThirdSdkServicePortTypeProxy(
				url);
		try {
			JAXBContext context = JAXBContext
					.newInstance(ControlResp.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			String result = proxy
					.getDepartments(reqBody);
			LOG.info("getDepartments result" + result);
			if (result.indexOf("errorCode>0</errorCode") > 0) {
				String[] deptIds = result.split("<deptId>");
				if (deptIds.length > 1) {
					for (int i = 1; i < deptIds.length; i++) {
						deptIdList.add(deptIds[i].split("<")[0]);
					}
				}

			}
		} catch (Exception e) {
		}
		return deptIdList;
	}

	public static String getControlUnitInfo(String sessionId) {
		
		ControlResp xj = null;
		if (!StringUtils.isEmp(sessionId)) {
			String requert = CONTROL_INFO;
			requert = requert.replace("SESSION_ID", sessionId);
			ThirdSdkServicePortTypeProxy proxy = new ThirdSdkServicePortTypeProxy(
					url);
			try {
				JAXBContext context = JAXBContext
						.newInstance(ControlResp.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();

				String result = proxy
						.getControlUnitInfo(requert);
				if (result.indexOf("errorCode>0</errorCode") > 0) {
					result = result.split("<body>")[1];
					result = result.split("</body>")[0];
					result = "<body>" + result + "</body>";
					result = result.replace("<items>", "");
					result = result.replace("</items>", "");

					xj = (ControlResp) unmarshaller
							.unmarshal(new StringReader(result));

				}
			} catch (Exception e) {
			}
		}
		
		StringBuffer contIds = new StringBuffer();
		if (null != xj && null != xj.getControlUnits()) {
			for (int i = 0; i < xj.getControlUnits().size(); i++) {
				if (0 == i) {
					contIds.append(xj.getControlUnits().get(i).getControlUnitId());
				}else {
					contIds.append(",").append(xj.getControlUnits().get(i).getControlUnitId());
				}
			}
		}
		return contIds.toString();
	}
	
	public static AccessResp getAccessControlPage(String sessionId) {
		AccessResp xj = null;
		String contIds = getControlUnitInfo(sessionId);
		if (!StringUtils.isEmp(sessionId) && !StringUtils.isEmp(contIds)) {
			String requert = ACCESS_INFO;
			requert = requert.replace("SESSION_ID", sessionId);
			requert = requert.replace("CONTROL_INFO", contIds);
			ThirdSdkServicePortTypeProxy proxy = new ThirdSdkServicePortTypeProxy(
					url);
			try {
				JAXBContext context = JAXBContext
						.newInstance(AccessResp.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();

				String result = proxy
						.getAccessControlPage(requert);
				if (result.indexOf("errorCode>0</errorCode") > 0) {
					result = result.split("<body>")[1];
					result = result.split("</body>")[0];
					result = "<body>" + result + "</body>";
					result = result.replace("<items>", "");
					result = result.replace("</items>", "");

					xj = (AccessResp) unmarshaller
							.unmarshal(new StringReader(result));

				}
			} catch (Exception e) {
			}
		}
		return xj;
	}
	
	public static UserResult getPresons(String sessionId) {
		UserResult xj = null;
		if (!StringUtils.isEmp(sessionId) ) {
			String requert = USER_INFO;
			requert = requert.replace("SESSION_ID", sessionId);
			ThirdSdkServicePortTypeProxy proxy = new ThirdSdkServicePortTypeProxy(
					url);
			try {
				JAXBContext context = JAXBContext
						.newInstance(UserResult.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();

				String result = proxy
						.getPresons(requert);
				if (result.indexOf("errorCode>0</errorCode") > 0) {
					result = result.split("<body>")[1];
					result = result.split("</body>")[0];
					result = "<body>" + result + "</body>";
					result = result.replace("<items>", "");
					result = result.replace("</items>", "");

					xj = (UserResult) unmarshaller
							.unmarshal(new StringReader(result));

				}
			} catch (Exception e) {
			}
		}
		return xj;
	}

	public static String qrySessionId() {
		url = AccessRecordTask.sysConfig.get("HK_SERVICE_URL");
		Login login = new Login();
		String sessionId = "";
		login = new Login();
		login.setUserName(AccessRecordTask.sysConfig.get("HK_SERVICE_USERNAME"));
		login.setUserPwd(CodeUtil.Encode("SHA-256", AccessRecordTask.sysConfig.get("HK_SERVICE_USERPWD")));
		login.setIp(AccessRecordTask.sysConfig.get("HK_SERVICE_IP"));
		login.setPort(AccessRecordTask.sysConfig.get("HK_SERVICE_PORT"));
		ThirdSdkServicePortTypeProxy proxy;
		LOG.debug(url);
		LOG.debug(createLoginXml(login));

		try {
			proxy = new ThirdSdkServicePortTypeProxy(
					"");
			LOG.info("qrySessionId in" + login);
			String result = proxy.userLogin(createLoginXml(login));
			LOG.info("qrySessionId result" + result);
			if (result.indexOf("errorCode>0</errorCode") > 0) {
				result = result.split("<sessionId>")[1];
				result = result.split("</sessionId>")[0];
				sessionId = result;

			}
		} catch (Exception e) {
			LOG.error(e.toString());
			LOG.error("HK Service login error");
		}
		return sessionId;
	}

	private static String createLoginXml(Login login) {

		String xml = "";
		try {
			JAXBContext context = JAXBContext.newInstance(Login.class);
			Marshaller marshaller = context.createMarshaller();
			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringWriter writer = new StringWriter();
			marshaller.marshal(login, writer);

			xml = writer.toString();
			xml = xml.split("\\?>")[1];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return xml;
	}

	public static String createDoorRecordXml(DoorRecordRequest request) {

		String xml = "";
		try {
			JAXBContext context = JAXBContext
					.newInstance(DoorRecordRequest.class);
			Marshaller marshaller = context.createMarshaller();
			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringWriter writer = new StringWriter();
			marshaller.marshal(request, writer);

			xml = writer.toString();
			xml = xml.split("\\?>")[1];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return xml;
	}

	public static ThirdClockResult getClockRecordPage(ClockRecord request) {

		ThirdClockResult xj = null;
		String sessionId = qrySessionId();
		if (!StringUtils.isEmp(sessionId)) {
			request.setSessionId(sessionId);
			ThirdSdkServicePortTypeProxy proxy = new ThirdSdkServicePortTypeProxy(
					url);
			try {
				JAXBContext context = JAXBContext
						.newInstance(ThirdClockResult.class);
				Marshaller marshaller = context.createMarshaller();
				Unmarshaller unmarshaller = context.createUnmarshaller();

				String result = proxy
						.getClockRecordPage(createClockRecordXml(request));
				if (result.indexOf("errorCode>0</errorCode") > 0) {
					result = result.split("<body>")[1];
					result = result.split("</body>")[0];
					result = "<body>" + result + "</body>";
					result = result.replace("<items>", "");
					result = result.replace("</items>", "");

					xj = (ThirdClockResult) unmarshaller
							.unmarshal(new StringReader(result));

				}
			} catch (Exception e) {
			}

		}
		return xj;
	}
	
	public static String createClockRecordXml(ClockRecord request) {

		String xml = "";
		try {
			JAXBContext context = JAXBContext
					.newInstance(ClockRecord.class);
			Marshaller marshaller = context.createMarshaller();
			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringWriter writer = new StringWriter();
			marshaller.marshal(request, writer);

			xml = writer.toString();
			xml = xml.split("\\?>")[1];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return xml;
	}
}
