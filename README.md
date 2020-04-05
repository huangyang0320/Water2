集成swagger2.0

1.maven包
<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.4.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.4.0</version>
		</dependency>
		
		
2.  SwaggerConfig.java类

3. 在spring-mvc.xml中加载自定义配置类，以及拦截其中增加静态资源访问控制。
	<bean class="com.wapwag.woss.common.config.SwaggerConfig" />
	<mvc:resources mapping="swagger-ui.html" location="classpath:/META-INF/resources/" />
	<mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars/" />
4.web.xml 配置

	<servlet-mapping>
		<servlet-name>springServlet</servlet-name>
		<url-pattern>/</url-pattern>
		<url-pattern>/swagger/*</url-pattern>
		<url-pattern>/api-docs</url-pattern>
	</servlet-mapping>

	