package com.wuji.basic.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * 登录认证过滤器
 */
public class AuthFilter implements Filter {

	private String[] resources;

	private String[] anonymousUrl;

	/**
	 * Default constructor.
	 */
	public AuthFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// 取得根目录所对应的绝对路径:
		String currentURL = httpServletRequest.getRequestURI();
		// 截取到当前文件名用于比较
		String targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());
		boolean isStaticResource = this.isStaticResource(targetURL);
		boolean isAnonymousUrl = this.isAnonymousUrl(targetURL);

		// Intercept conditions
		if (!isStaticResource && !isAnonymousUrl) {
			// 判断当前页是否是重定向以后的登录页面页面，如果是就不做session的判断，防止出现死循环
			HttpSession session = httpServletRequest.getSession(false);
			if (session == null || session.getAttribute("activityUser") == null) {
				// *用户登录以后需手动添加session
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");// 如果session为空表示用户没有登录就重定向到login.jsp页面
				return;
			}
		}
		if (targetURL.equals("/login.jsp") && httpServletRequest.getSession(false) != null
				&& httpServletRequest.getSession(false).getAttribute("activityUser") != null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.jsp");// 如果session不为空表示用户没有登录就重定向到index.jsp页面
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * 判断是否为允许匿名访问的资源
	 *
	 * @param targetURL
	 * @return
	 */
	private boolean isAnonymousUrl(String targetURL) {
		for (String string : this.anonymousUrl) {
			if (targetURL.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为静态资源
	 *
	 * @param targetURL
	 * @return
	 */
	private boolean isStaticResource(String targetURL) {
		for (String string : this.resources) {
			if (targetURL.endsWith(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// 获取静态资源的后缀
		String staticResource = fConfig.getInitParameter("staticResource");
		// 获取允许匿名访问的URL
		String anonymous = fConfig.getInitParameter("anonymous");
		this.resources = staticResource.split(",");
		this.anonymousUrl = anonymous.split(",");
	}

}
