package com.wuji.authority.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.wuji.authority.shiro.PasswordHelper;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/properties/db.properties")
@ComponentScan(basePackages = { "com.wuji.authority.dao", "com.wuji.authority.service" })
public class MyDaoConfig {
	@Value("${jdbc.driver}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;

	@Value("classpath:/mapping")
	private Resource mappingDirectoryLocations;

	@Bean
	public DataSource dataSource() {
		/**
		 * <!-- 最小空闲连接数 --> <property name="minIdle" value="10" /> <!--
		 * 配置连接池初始化大小 --> <property name="initialSize" value="20" /> <!-- 最大连接数
		 * --> <property name="maxActive" value="20" /> <!-- 获取连接等待超时的时间，单位：毫秒
		 * --> <property name="maxWait" value="2000" />
		 */
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(this.driverClassName);
		dataSource.setUrl(this.jdbcUrl);
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);
		dataSource.setInitialSize(20);
		dataSource.setMinIdle(10);
		dataSource.setMaxActive(20);
		dataSource.setMaxWait(2000);
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(this.dataSource());
		sessionFactory.setMappingDirectoryLocations(this.mappingDirectoryLocations);
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		sessionFactory.setHibernateProperties(properties);
		return sessionFactory;
	}

	@Bean
	public PasswordHelper passwordHelper() {
		return new PasswordHelper();
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(this.sessionFactory().getObject());
		return transactionManager;
	}
}
