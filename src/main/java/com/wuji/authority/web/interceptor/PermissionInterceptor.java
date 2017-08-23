package com.wuji.authority.web.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wuji.authority.util.GlobalConstant;
import com.wuji.authority.vo.ActivityUser;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	private static Logger log = LoggerFactory.getLogger(PermissionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long startTime = System.currentTimeMillis();
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		String requsetURI = uri.replace(contextPath, "");
		String loginURI = "/loginAction/*";
		log.info("本次请求路径为" + requsetURI);
		request.setAttribute("startTime", startTime);
		AntPathMatcher matcher = new AntPathMatcher();
		if (matcher.match(loginURI, requsetURI)) {
			return true;
		}
		HttpSession session = request.getSession();
		if (session.getAttribute(GlobalConstant.ACTIVITY_USER) != null) {
			ActivityUser activityUser = (ActivityUser) session.getAttribute(GlobalConstant.ACTIVITY_USER);
			if (activityUser.isAdmin()) {
				return true;
			} else {
				Set<String> permitCodes = activityUser.getPermitCodes();
				for (String permitCode : permitCodes) {
					log.info(permitCode);
					if (matcher.match(permitCode, requsetURI)) {
						return true;
					}
				}
			}
		}
		// 执行到这里拦截，跳转到无权访问的提示页面
		request.getRequestDispatcher("/notAccess.jsp").forward(request, response);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
		request.removeAttribute("startTime");
		long endTime = System.currentTimeMillis();
		log.info("本次请求处理时间为" + (endTime - startTime) + "ms");
		request.setAttribute("handlingTime", (endTime - startTime));
	}
}
