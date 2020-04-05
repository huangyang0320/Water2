package com.wapwag.woss.modules.home.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Using Jsoup for logging integrate with OA interface
 * Created by Administrator on 2016/9/20 0020.
 */
public class LoginUtil {

    private static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    static String easssoEncode(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char tmpStr : str.toCharArray()) {
            stringBuilder.append(",");
            stringBuilder.append(Integer.toOctalString(tmpStr));
        }

        return stringBuilder.toString();
    }

    public static String validateUser(String loginName, String password, String loginPath) {
        String result = "";
        int retryCount = 3;
        while (retryCount-- > 0) {
            try {
                Connection connection = Jsoup.connect(loginPath).method(Connection.Method.GET);
                connection.header("Accept", "text/html," +
                        "application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                connection.header("Accept-Encoding", "gzip, deflate, sdch");
                connection.header("Accept-Language", "zh-CN,zh;q=0.8");
                connection.header("Cache-Control", "max-age=0");
                connection.header("Connection", "keep-alive");
                connection.header("Host", "172.20.2.104:6888");
                connection.header("Upgrade-Insecure-Requests", "1");
                connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/45.0.2454.101 Safari/537.36");
                Connection.Response response = connection.execute();
                String jsessionId = response.cookie("JSESSIONID");
                String easSessionId;
                Document document = response.parse();
                Element scripts = document.body().getElementById("easSessionId");
                easSessionId = scripts.val();

                Element lt = document.getElementById("lt");
                Element clientHostIP = document.getElementById("clientHostIP");

                connection = Jsoup.connect(loginPath).method(Connection.Method.POST);

                connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                connection.header("Accept-Encoding", "gzip, deflate");
                connection.header("Accept-Language", "zh-CN,zh;q=0.8");
                connection.header("Cache-Control", "max-age=0");
                connection.header("Connection", "keep-alive");
                connection.header("Content-Type", "application/x-www-form-urlencoded");
                connection.header("Cookie", "JSESSIONID=" + jsessionId);
                connection.header("Host", "172.20.2.104:6888");
                connection.header("Origin", "http://172.20.2.104:6888");
                connection.header("Referer", "http://172.20.2.104:6888/eassso/login?_flowId=login-webflow");
                connection.header("Upgrade-Insecure-Requests", "1");
                connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/45.0.2454.101 Safari/537.36");

                connection.data("locale", "L2");
                connection.data("dataCenter", "G01");
                connection.data("username", "username");
                connection.data("encryptUsername", easssoEncode(loginName));
                connection.data("password", "password");
                connection.data("enPassword", easssoEncode(password));
                connection.data("lt", lt.val());
                connection.data("_eventId", "submit");
                connection.data("_flowExecutionKey", "e1s1");
                connection.data("isPureWeb", "true");
                connection.data("solutionName", "eas");
                connection.data("dbType", "2");
                connection.data("easSessionId", easSessionId);
                connection.data("userAuthPattern", "BaseDB");
                connection.data("loginFlow", "true");
                connection.data("clientHostIP", clientHostIP.val());
                connection.data("usbkeyInfo", "");
                connection.data("usbkeyPinVerifyResult", "");
                connection.data("bgMode", "4");
                response = connection.execute();

                if (response.body().contains("CAS Success")) {
                    result = "登录成功";
                } else {
                    document = response.parse();
                    result = document.getElementById("loginErrorID").text();
                }
                break;
            } catch (Exception e) {
                logger.error("登录异常：{}", e.getMessage());
                result = "网络异常，登录失败";
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(result);
        }
        return result;
    }

}
