package com.wapwag.woss.modules.config;

import org.springframework.stereotype.Component;

/**
 * @author leo
 * @since 2018/4/19 13:18
 */
@Component
public class HttpClientProperties {

    private Integer maxTotal;
    private Integer defaultMaxPerRoute;
    private Integer connectTimeout;
    private Integer connectionRequestTimeout;
    private Integer socketTimeout;
    private boolean staleConnectionCheckEnabled;
}
