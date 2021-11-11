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
package com.mphine.batch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @FileName    : BatchMapper.java
 * @Date        : 2018. 12. 10.
 * @Author      : hkpark
 * @Description : batchJobRepository 데이터 삭제와 작업 밀림, 스레드 멈춤 조회를 위한 Mapper Interface 
 * @History     : [20181210] 최초작성.
 * 
 */

@Mapper
public interface BatchMapper {	
	
	public void setReferentialIntegrityOff();

	public int deleteBatchJobExecution(Map<String, Integer> deleteParamMap);	
	
	public int deleteBatchJobInstance();	
	
	public int deleteBatchJobExecutionParams();
	
	public int deleteBatchJobExecutionContext();
	
	public int deleteBatchStepExecution();
	
	public int deleteBatchStepExecutionContext();
	
	public void setReferentialIntegrityOn();
	
	public List<String> selectBatchProcessDelayList(int jobDelaySecondForSelect);
	
	public Double selectBatchJobAvgExecutionTimeRecentlyFive();
}
