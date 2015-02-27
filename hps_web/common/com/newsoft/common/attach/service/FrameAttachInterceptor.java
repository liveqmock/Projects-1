package com.newsoft.common.attach.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 统一附件标签的拦截器
 * 
 * @author mengxw
 * 
 */
public class FrameAttachInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		String deletedIds = request.getParameter("deletedIds");
//		String fileIds = request.getParameter("fileIds");
//
//		FrameAttachService attachService = (FrameAttachService) SpringBeanManager
//				.getBean("frameAttachServiceImpl");
//
//		// 添加附件和信息的关联关系
//		if (!StringTools.isEmpty(fileIds)) {
//
//		}
	}
}
