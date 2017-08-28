package com.wuji.authority.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.wuji.authority.util.GlobalConstant;
import com.wuji.authority.vo.ActivityUser;

public class MyPermissionFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = this.getSubject(request, response);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		if (subject == null) {
			return true;
		}
		Session session = subject.getSession(false);
		if (session.getAttribute(GlobalConstant.ACTIVITY_USER) != null) {
			ActivityUser activityUser = (ActivityUser) session.getAttribute(GlobalConstant.ACTIVITY_USER);
			if (activityUser.isAdmin()) {
				return true;
			} else {
				String uri = httpServletRequest.getRequestURI();
				// Set<String> permitCodes = activityUser.getPermitCodes();
				// permitCodes.contains(o);
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}

}
