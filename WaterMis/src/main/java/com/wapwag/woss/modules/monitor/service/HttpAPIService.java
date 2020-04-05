package com.wapwag.woss.modules.monitor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wapwag.woss.modules.config.HttpResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author leo
 * @since 2018/4/19 13:13
 */
@Component
public class HttpAPIService implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired(required = false)
    private RequestConfig requestConfig;

    /**
     * 指定GET请求，响应状态码为200时返回数据，否则返回null
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException {
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * 执行带参数的GET请求，响应状态码为200时返回数据，否则返回null
     *
     * @param url
     * @param param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public String doGet(String url, Map<String, String> param) throws ClientProtocolException, IOException,
            URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        return this.doGet(builder.build().toString());
    }

    /**
     * 执行POST请求
     *
     * @param url
     * @param param
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> param) throws ParseException, IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != param) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : param.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 执行POST请求，提交json
     *
     * @param url
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPostJson(String url, String json) throws ParseException, IOException {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        if (null != json) {
            // 构造一个String的实体
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public HttpResult doPostObject(String url, Object object) throws ParseException, IOException {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
                // 设置请求和传输超时时间
                .setConnectTimeout(6000).setConnectionRequestTimeout(6000).setSocketTimeout(6000).build();
        httpPost.setConfig(globalConfig);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (globalConfig != null) {
            // 构造StringEntity
            StringEntity stringEntity = new StringEntity(new ObjectMapper().writeValueAsString(object), "utf-8");
            stringEntity.setContentType("application/json");
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            /*response = this.getHttpClient().execute(httpPost);*/
            response = httpClient.execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), "UTF-8"));
        }catch (SocketTimeoutException e){
            return new HttpResult(400,"请求超时");
        }
        finally {
            if (response != null) {
                response.close();
            }
        }

    }

    /**
     * 执行无参的POST请求
     *
     * @param url
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public HttpResult doPost(String url) throws ParseException, IOException {
        return this.doPost(url, null);
    }

    private CloseableHttpClient getHttpClient() {
        //每次都从Spring容器中获取Httpclient对象，保证该对象是多例
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    public static HttpResult httpPost(String url, String json) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResult result= null;
        String respContent = "";
        int code=0;
        try{
            // 将JSON进行UTF-8编码,以便传输中文
            //String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            //设置编码和
            StringEntity se = new StringEntity(json,ContentType.APPLICATION_JSON);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(se);
            HttpResponse resp = httpClient.execute(httpPost);
            code = resp.getStatusLine().getStatusCode();
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
        }finally {
            httpClient.close();
            result = new HttpResult(code,respContent);
            return result;
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
