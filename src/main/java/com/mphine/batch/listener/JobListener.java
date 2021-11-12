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
package com.mphine.batch.listener;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobListener extends JobExecutionListenerSupport{

	@Override
	public void afterJob(JobExecution jobExecution) {
		
		String fileName = jobExecution.getJobParameters().getString("fileName");
		
		Date jobStartTime = jobExecution.getStartTime();
		Date jobEndTime = jobExecution.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);
		String jobStartTimeString = sdf.format(jobStartTime);
		String jobEndTimeString = sdf.format(jobEndTime);
		double jobExecutionTime = (jobEndTime.getTime() - jobStartTime.getTime()) / 1000.0;
		
		log.info("[{}] StartTime: {}, EndTime: {}, ExecutionTime: {} sec", 
				fileName, jobStartTimeString, jobEndTimeString, jobExecutionTime);
		
		Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
		
		if(log.isDebugEnabled()) {
			
			for(StepExecution stepExecution : stepExecutions) {
				log.debug("Elapsed - [{}] stepName: {}, startTime: {}, endTime: {}, stepExecutionTime: {} sec", 
						fileName, stepExecution.getStepName(), sdf.format(stepExecution.getStartTime()), sdf.format(stepExecution.getEndTime()), 
						(stepExecution.getEndTime().getTime() - stepExecution.getStartTime().getTime()) / 1000.0);
			}		
		}
		
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.debug("[{}] job success", fileName);                      		
    		
        } else {
        	
        	String exceptionStepName = 
    				jobExecution.getExecutionContext().get("exceptionStepName") == null 
    					? "" : jobExecution.getExecutionContext().get("exceptionStepName").toString();
        	
        	log.error("####### Exception : "+exceptionStepName);
        	
        	log.debug("[{}] job fail", fileName);
        	
        }
	}


	
}
