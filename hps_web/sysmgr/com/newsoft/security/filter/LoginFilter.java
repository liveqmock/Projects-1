package com.newsoft.security.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.sysmanager.cache.UserCacheSupport;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.StringTools;

/**
 * 登录时的过滤器，用于在Spring Security 过滤器之前处理前置业务逻辑
 * 
 * @author mengxw
 * 
 */
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		// String enableValidateCode =
		// config.getInitParameter("useValidateCode");
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 判断登录用户是否是普通会员身份，普通会员不能登录后台管理系统
		UserCacheSupport userCacheSupport = (UserCacheSupport) SpringBeanManager.getBean("userCacheSupport");
		String account = request.getParameter("j_username");
		if (!StringTools.isEmpty(account)) {
			UserVo user = userCacheSupport.getUserByAccount(account);
			if (user != null) {
				if (!user.getUserState()) {
					response.sendRedirect("login.jsp?login_error=12");
					return;
				}
				List<Role> roles = userCacheSupport.getUserRoles(user.getUserId());
				for (Role role : roles) {
					if (role.getRoleId().equals(Role.ROLE_EXCE_MEMBER)) {
						response.sendRedirect("login.jsp?login_error=11");
						return;
					}
				}
			}

		}

		filterChain.doFilter(request, response);
	}

}