package com.wuji.authority.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.wuji.basic.filter.SystemContextFilter;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(MyMvcConfig.class);
		webApplicationContext.register(MyDaoConfig.class);
		webApplicationContext.setServletContext(servletContext);
		javax.servlet.FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("enCoding",
				new CharacterEncodingFilter("UTF-8", true, true));
		encodingFilter.addMappingForUrlPatterns(null, true, "/*");
		javax.servlet.FilterRegistration.Dynamic systemContextFilter = servletContext.addFilter("systemContextFilter",
				new SystemContextFilter());
		systemContextFilter.addMappingForUrlPatterns(null, true, "/*");
		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
		servlet.setAsyncSupported(true);
	}

}
