package com.wapwag.woss.modules.home.util;

import junit.framework.TestCase;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static com.wapwag.woss.modules.home.util.LoginUtil.easssoEncode;

/**
 * Login util Test
 * Created by Administrator on 2016/9/20 0020.
 */
@SuppressWarnings("Duplicates")
@Ignore
public class LoginUtilTest {

    private static final String LOGIN_PATH = "http://172.20.2.104:6888/eassso/login";

    private long start;


    @Before
    public void before() {
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        System.out.printf("当前方法费时：%d\n", System.currentTimeMillis() - start);
    }
    //设置当前方法在一定时间内完成，同时设置被测方法是否有异常抛出，抛出类型为SocketTimeout
    @Test(expected = SocketTimeoutException.class, timeout = 5000L)
    public void testCanAccessInternet() throws Exception {
        Jsoup.connect("http://172.20.2.69").method(Connection.Method.GET).execute();
    }

    @Test(expected = SocketTimeoutException.class, timeout = 5000L)
    public void testValidateUser() throws IOException {
        String username = "zhengxg";
        String password = "19830208zxg";
        Connection connection = Jsoup.connect(LOGIN_PATH).method(Connection.Method.GET);
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
        String easSessionId = "";
        Document document = response.parse();
        Element scripts = document.body().getElementById("easSessionId");
        easSessionId = scripts.val();

        Element lt = document.getElementById("lt");
        Element clientHostIP = document.getElementById("clientHostIP");

        connection = Jsoup.connect(LOGIN_PATH).method(Connection.Method.POST);

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
        connection.data("encryptUsername", easssoEncode(username));
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

        System.out.println(jsessionId);
        System.out.println(easSessionId);
        System.out.println(lt.val());

        if (response.body().contains("CAS Success")) {
            System.out.println("登录成功");
        } else {
            document = response.parse();
            String errorInfo = document.getElementById("loginErrorID").text();
            System.out.println(errorInfo);
        }
    }

    @Test
    public void testEncode() {
        String username = "zhengxg";
        String password = "19830208zxg";

        TestCase.assertEquals(",172,150,145,156,147,170,147", easssoEncode(username));
        TestCase.assertEquals(",61,71,70,63,60,62,60,70,172,170,147", easssoEncode(password));
    }

}
