package com.wuji.authority.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.wuji.basic.filter.SystemContextFilter;

/**
 * 同web.xml作用相同
 *
 * @author Yayun
 *
 */
public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.setAllowCircularReferences(true);
		// webApplicationContext.setConfigLocation("classpath:/spring/applicationContext-shiro.xml");
		webApplicationContext.register(MyDaoConfig.class, ShiroConfig.class, MyMvcConfig.class);
		webApplicationContext.setServletContext(servletContext);
		/**
		 * spring 请求字符编码过滤器
		 */
		javax.servlet.FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("enCoding",
				new CharacterEncodingFilter("UTF-8", true, true));
		encodingFilter.addMappingForUrlPatterns(null, true, "/*");
		/**
		 * 分页及系统变量过滤器
		 */
		javax.servlet.FilterRegistration.Dynamic systemContextFilter = servletContext.addFilter("systemContextFilter",
				new SystemContextFilter());
		systemContextFilter.addMappingForUrlPatterns(null, true, "/*");
		/**
		 * Shiro过滤器
		 */
		javax.servlet.FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter",
				new DelegatingFilterProxy());
		shiroFilter.setInitParameter("targetFilterLifecycle", "true");
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.noneOf(DispatcherType.class);
		dispatcherTypes.add(DispatcherType.FORWARD);
		dispatcherTypes.add(DispatcherType.REQUEST);
		shiroFilter.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
		/**
		 * 注册springMVC dispatcherServlet
		 */
		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
		servlet.setAsyncSupported(true);
		/**
		 * hibernate5 OpenSessionInViewFilter
		 * <filter> <filter-name>OpenSessionInViewFilter</filter-name>
		 * <filter-class>org.springframework.orm.hibernate5.support.
		 * OpenSessionInViewFilter</filter-class>
		 * <init-param> <param-name>sessionFactoryBeanName</param-name>
		 * <param-value>sessionFactory</param-value> </init-param>
		 * <init-param> <param-name>singleSession</param-name>
		 * <param-value>true</param-value> </init-param>
		 * <init-param> <param-name>flushMode</param-name>
		 * <param-value>AUTO</param-value> </init-param> </filter>
		 *
		 */

		javax.servlet.FilterRegistration.Dynamic OpenSessionInViewFilter = servletContext
				.addFilter("OpenSessionInViewFilter", new OpenSessionInViewFilter());
		Map<String, String> initParameters = new HashMap<>();
		initParameters.put("sessionFactoryBeanName", "sessionFactory");
		initParameters.put("singleSession", "true");
		initParameters.put("flushMode", "AUTO");
		OpenSessionInViewFilter.setInitParameters(initParameters);
		OpenSessionInViewFilter.addMappingForUrlPatterns(null, true, "/*");
		/**
		 * 注册ContextLoaderListener 相当与web.xml中的
		 * <listener-class>org.springframework.web.context.ContextLoaderListener
		 * </listener-class>
		 */
		ContextLoaderListener contextLoaderListener = new ContextLoaderListener(webApplicationContext);
		contextLoaderListener.initWebApplicationContext(servletContext);

	}

}
