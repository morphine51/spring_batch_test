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
package com.mphine.batch;

import org.slf4j.MDC;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class TaskEncrypt implements Tasklet{
	
	@Value("#{jobParameters[logKey]}")
	private String logKey;
	
	@Value("#{jobParameters[fileName]}")
	private String fileName;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
		
		MDC.put("LOG_KEY",logKey);
		
		log.info("[{}] TaskEncrypt", fileName);		
		
		return RepeatStatus.FINISHED;
	}	
}
