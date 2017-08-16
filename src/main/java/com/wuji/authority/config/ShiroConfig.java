package com.wuji.authority.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wuji.authority.shiro.RetryLimitHashedCredentialsMatcher;
import com.wuji.authority.shiro.UserRealm;

@Configuration
public class ShiroConfig {

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:xml/ehcache.xml");
		return cacheManager;
	}

	@SuppressWarnings("deprecation")
	@Bean
	public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(
				this.cacheManager());
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setHashSalted(true);
		return credentialsMatcher;
	}

	@Bean
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(this.credentialsMatcher());
		userRealm.setCachingEnabled(true);
		userRealm.setAuthenticationCachingEnabled(true);
		userRealm.setAuthorizationCacheName("authorizationCache");
		userRealm.setAuthenticationCacheName("authenticationCache");
		return userRealm;
	}

	@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}

	@Bean
	public SimpleCookie sessionIdCookie() {
		SimpleCookie sessionIdCookie = new SimpleCookie("sid");
		sessionIdCookie.setHttpOnly(true);
		sessionIdCookie.setMaxAge(180_000);
		return sessionIdCookie;
	}

	@Bean
	public EnterpriseCacheSessionDAO sessionDao() {
		EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
		sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		sessionDAO.setSessionIdGenerator(this.sessionIdGenerator());
		return sessionDAO;
	}

	@Bean
	public QuartzSessionValidationScheduler sessionValidationScheduler() {
		QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
		sessionValidationScheduler.setSessionManager(this.sessionManager());
		return sessionValidationScheduler;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1_800_000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionDAO(this.sessionDao());
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(this.sessionIdCookie());
		return sessionManager;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(this.userRealm());
		securityManager.setSessionManager(this.sessionManager());
		securityManager.setCacheManager(this.cacheManager());
		SecurityUtils.setSecurityManager(securityManager);
		return securityManager;
	}

	@Bean
	public FormAuthenticationFilter formAuthenticationFilter() {
		FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
		formAuthenticationFilter.setUsernameParam("username");
		formAuthenticationFilter.setPasswordParam("password");
		formAuthenticationFilter.setLoginUrl("/login.jsp");
		return formAuthenticationFilter;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(this.securityManager());
		shiroFilter.setLoginUrl("/login.jsp");
		shiroFilter.setUnauthorizedUrl("/notAccess.jsp");
		Map<String, Filter> filters = new HashMap<>();
		filters.put("authc", this.formAuthenticationFilter());
		shiroFilter.setFilters(filters);
		/**
		 * /static/** = anon /notAccess.jsp = anon /loginAction!register = anon
		 * /login.jsp = anon /loginAction!logout = anon /** = authc
		 */
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/notAccess.jsp", "anon");
		filterChainDefinitionMap.put("/loginAction/register", "anon");
		filterChainDefinitionMap.put("/login.jsp", "anon");
		filterChainDefinitionMap.put("/loginAction/logout", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}

}
