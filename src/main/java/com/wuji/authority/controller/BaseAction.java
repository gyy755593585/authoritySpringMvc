/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.wuji.authority.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuji.authority.util.Result;

/**
 * 基础ACTION,其他ACTION继承此ACTION来获得writeJson和ActionSupport的功能 s
 *
 * @author Yayun
 *
 */

public class BaseAction {

	protected final Logger logger;
	{
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	/**
	 * spring ModelAttribute
	 * 放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面
	 */
	@ModelAttribute
	public void setBaseController(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 *
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(Object object) {
		try {
			// 禁用FastJson的“循环引用检测”特性。
			// String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd
			// HH:mm:ss");
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",
					SerializerFeature.DisableCircularReferenceDetect);
			this.response.setContentType("text/html;charset=utf-8");
			this.response.getWriter().write(json);
			this.response.getWriter().flush();
			this.response.getWriter().close();
		} catch (IOException e) {
			this.logger.error("write json error", e);
		}
	}

	public String getJson(Object object) {
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",
				SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * ajax失败
	 *
	 * @param msg
	 *            失败的消息
	 * @return {Object}
	 */
	public Object renderError(String msg) {
		Result result = new Result();
		result.setMsg(msg);
		return result;
	}

	/**
	 * ajax成功
	 *
	 * @return {Object}
	 */
	public Object renderSuccess() {
		Result result = new Result();
		result.setSuccess(true);
		return result;
	}

	/**
	 * ajax成功
	 *
	 * @param msg
	 *            消息
	 * @return {Object}
	 */
	public Object renderSuccess(String msg) {
		Result result = new Result();
		result.setSuccess(true);
		result.setMsg(msg);
		return result;
	}

	/**
	 * ajax成功
	 *
	 * @param obj
	 *            成功时的对象
	 * @return {Object}
	 */
	public Object renderSuccess(Object obj) {
		Result result = new Result();
		result.setSuccess(true);
		result.setObj(obj);
		return result;
	}

	/**
	 * redirect跳转
	 *
	 * @param url
	 *            目标url
	 */
	protected String redirect(String url) {
		return new StringBuilder("redirect:").append(url).toString();
	}

}
