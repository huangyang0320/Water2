package com.wapwag.woss.modules.home.util.hik;


import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
public class HttpClientSSLUtils
{
  private static HttpClient clients = null;

  protected static final Integer DEFAULT_CONNECTION_TIME_OUT = Integer.valueOf(100000);

  protected static final Integer DEFAULT_SOCKET_TIME_OUT = Integer.valueOf(200000);
  protected static final String DEFAULT_CHAR_SET = "UTF-8";

  public static String doPost(String url, String jsonText)
    throws Exception
  {
    HttpClient client = null;
    HttpPost post = new HttpPost(url);
    try {
      if ((jsonText != null) && (!(jsonText.isEmpty()))) {
        StringEntity entity = new StringEntity(jsonText, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
      }

      RequestConfig.Builder customReqConf = RequestConfig.custom();
      customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
      customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
      post.setConfig(customReqConf.build());
      HttpResponse res = null;
      if (url.startsWith("https"))
      {
        client = createSSLInsecureClient();
        res = client.execute(post);
      }
      else {
        client = clients;
        res = client.execute(post);
      }
      String str = IOUtils.toString(res.getEntity().getContent(), "UTF-8");

      return str;
    }
    finally
    {
      post.releaseConnection();
      if ((url.startsWith("https")) && (client != null) && (client instanceof CloseableHttpClient))
        ((CloseableHttpClient)client).close();
    }
  }

  public static String doGet(String url)
    throws Exception
  {
    HttpClient client = null;
    HttpGet get = new HttpGet(url);
    String result = "";
    try
    {
      RequestConfig.Builder customReqConf = RequestConfig.custom();
      customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
      customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
      get.setConfig(customReqConf.build());
      HttpResponse res = null;
      if (url.startsWith("https"))
      {
        client = createSSLInsecureClient();
        res = client.execute(get);
      }
      else {
        client = client;
        res = client.execute(get);
      }
      result = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
    } finally {
      get.releaseConnection();
      if ((url.startsWith("https")) && (client != null) && (client instanceof CloseableHttpClient)) {
        ((CloseableHttpClient)client).close();
      }
    }
    return result;
  }

  private static CloseableHttpClient createSSLInsecureClient()
    throws GeneralSecurityException
  {
    try
    {
      SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
      {
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          return true;
        }

      }).build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier()
      {
        public boolean verify(String hostname, SSLSession session)
        {
          return true;
        }

      });
      return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    } catch (GeneralSecurityException e) {
      throw e;
    }
  }

  public static void main(String[] args)
  {}

  static
  {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(128);
    cm.setDefaultMaxPerRoute(128);
    clients = HttpClients.custom().setConnectionManager(cm).build();
  }
  
  
  public boolean verify(String hostname, SSLSession session)
  {
    return true;
  }
}





