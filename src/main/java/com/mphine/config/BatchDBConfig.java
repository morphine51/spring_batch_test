/*
 * KT GiGAeyes version 1.0
 *
 *  Copyright ⓒ 2017 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.mphine.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;


/**
 * 
 * @FileName    : BatchDBConfig.java
 * @Date        : 2018. 12. 10.
 * @Author      : hkpark
 * @Description : batch에서 이용하는(jobRepository) dataSource(H2) 설정 class
 * @History     : [20181210] 최초작성.
 * 
 */

//@Configuration
//@MapperScan(value="com.mphine.batch.mapper", sqlSessionFactoryRef="batchSqlSessionFactory")
//@EnableTransactionManagement
public class BatchDBConfig {
 /*
	@Bean(name = "batchDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.batch.datasource") 
	public DataSource batchDataSource() throws SQLException {
//		Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setPoolName("hikari-pool-h2");
		return hikariDataSource;
	}
	
	@Bean
	public PlatformTransactionManager batchMetaTransactionManager(@Qualifier("batchDataSource") DataSource batchDataSource) {
		return new DataSourceTransactionManager(batchDataSource);
	}
 
    @Bean(name = "batchSqlSessionFactory")
    @Primary
    public SqlSessionFactory batchSqlSessionFactory(@Qualifier("batchDataSource") DataSource batchDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(batchDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/batch-mapper.xml"));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }
 
    @Bean(name = "batchSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory batchSqlSessionFactory) throws Exception {
 
        return new SqlSessionTemplate(batchSqlSessionFactory);
    }  
*/
}
