package com.wapwag.woss.modules.home.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SentSm {


    public static void main(String[] args) {
        try {
            //  String y = doGet("http://video.sina.com.cn/life/tips.html", null, "GBK", true);
            //  System.out.println(y);
//              Map params = new HashMap();
//              params.put("param1", "value1");
//              params.put("json", "{\"aa\":\"11\"}");
//              String j = doPost("http://localhost/uplat/manage/test.do?reqCode=add", params, "UTF-8", true);
//              System.out.println(j);


            /**
             * * 执行一个http/https post请求，返回请求响应的文本数据
             *      *
             *      * @param url       请求的URL地址
             *      * @param params    请求的查询参数,可以为null
             *      * @param charset   字符集
             *      * @param pretty    是否美化
             *      * @return          返回请求响应的文本数据
             */

            /*JSONObject params=new JSONObject();
            params.put("defKey","checkManage");
            params.put("instId","");
            params.put("isAgree","");
            params.put("nodeName","盘点任务制定");
            //System.out.println("发起流程Url=\""+flowUrl);

            Map<String,String> map=new HashMap<>();
            map.put("xxx",Encrypt.paramEncrypt(params.toString()));
            //String value =  doPost("https://127.0.0.1:8080/bpm/actWebService/getUserByNodeIdOrgIdInstIdDefKey",map,"UTF-8",false);
            String value =  post("https://127.0.0.1:8080/bpm/actWebService/getUserByNodeIdOrgIdInstIdDefKey",Encrypt.paramEncrypt(params.toString()));

            System.out.println(value);*/

            String str=getXmlInfo();
            poststh(str);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static final String POST_URL = "http://172.16.11.50:8080/api/sendsm.json?sign=CF58222E6FA51E5170AA50C024FDF235&sign_method=hmac&timestamp=20161116143638&request_md5=C747491A764FD00CB2DD2D6017945263&app_key=10001";
    public static void poststh(String content){//content为你要拼写的XML文档字符串
        URL url = null ;

        try {
            url = new URL(POST_URL);
        } catch (MalformedURLException e1) {
// TODO Auto-generated catch block
            e1.printStackTrace();
        }
        URLConnection uc = null;
        OutputStreamWriter out = null;
        BufferedReader rd = null;
        try {
            uc = url.openConnection();
            uc.setDoOutput(true); //....必须设置为'true'.
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");   //记住这行不能少否则会出错
            out = new OutputStreamWriter(uc.getOutputStream(),"utf-8");
            out.write(content);
            out.flush();
            out.close();

            rd = new BufferedReader(new InputStreamReader(uc.
                    getInputStream(),"utf-8"));
            String responseStr;
            while ( (responseStr = rd.readLine()) != null) {
                System.out.println("发送结果=" + responseStr);
            }

            rd.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


    public static String getXmlInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("<batchid>B20161031125959001</batchid><data><id>1000</id><phone>18217312653</phone><content>短信内容</content><sendtime></sendtime></data></request>");

        return sb.toString();
    }

}
