
package com.wuji.basic.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.wuji.authority.util.GlobalConstant;
import com.wuji.authority.vo.ActivityUser;
import com.wuji.basic.model.SystemRequest;
import com.wuji.basic.model.SystemRequestHolder;

/**
 *
 * 对系统参数进行设置 如分页等
 *
 * @author Yayun
 */
public class SystemContextFilter implements Filter {

	/**
	 * {@inheritDoc}
	 *
	 * TODO - Add javadoc for the sub-type.
	 */
	@Override
	public void destroy() {

	}

	/**
	 * {@inheritDoc}
	 *
	 * TODO - Add javadoc for the sub-type.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		int page = 1;
		int rows = 10;
		try {
			try {
				rows = Integer.parseInt(request.getParameter(GlobalConstant.ROWS));
				page = Integer.parseInt(request.getParameter(GlobalConstant.PAGE));
			} catch (NumberFormatException e) {
			}
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpSession session = httpServletRequest.getSession(false);
			SystemRequest systemRequest = new SystemRequest();
			if (session != null) {
				ActivityUser activityUser = (ActivityUser) session.getAttribute(GlobalConstant.ACTIVITY_USER);
				if (activityUser != null) {
					systemRequest.setCurrentUser(activityUser.getLoginName());
				}
			}
			if (StringUtils.isBlank(systemRequest.getCurrentUser())) {
				systemRequest.setCurrentUser(GlobalConstant.DEFAULT_CURRENT_USER);
			}
			int pageOffset = (page - 1) * rows;
			systemRequest.setPageOffset(pageOffset);
			systemRequest.setPageSize(rows);
			systemRequest.setRequest(httpServletRequest);
			SystemRequestHolder.initRequestHolder(systemRequest);
			chain.doFilter(request, response);
		} finally {
			SystemRequestHolder.remove();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * TODO - Add javadoc for the sub-type.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	// ------- Constants (static final) ----------------------------------------

	// ------- Static Variables (static) ---------------------------------------

	// ------- Instance Variables (private) ------------------------------------

	// ------- Constructors ----------------------------------------------------

	// ------- Instance Methods (public) ---------------------------------------

	// ------- Instance Methods (protected) ------------------------------------

	// ------- Instance Methods (private) --------------------------------------

	// ------- Static Methods --------------------------------------------------

	// ------- Optional Inner Class ------------------------------------------

}
