/**
 *
 */
package com.wuji.authority.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yayun
 *
 */
public abstract class BaseServiceImpl {

	protected final Logger logger;
	{
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
}
