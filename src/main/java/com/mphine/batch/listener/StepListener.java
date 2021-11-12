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

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @FileName    : StepListener.java
 * @Date        : 2018. 12. 10.
 * @Author      : hkpark
 * @Description : 예외 발생한 step 이름을 context에 저장하는 Class
 * @History     : [20181210] 최초작성.
 * 
 */

@Component
@Slf4j
public class StepListener extends StepExecutionListenerSupport{

	@Override
    public ExitStatus afterStep(StepExecution stepExecution) {
    	log.debug("taskletlStepListener afterStep");
    	
    	log.debug("step Batch status: {}", stepExecution.getStatus());
    	log.debug("step name: {}", stepExecution.getStepName());          
    	
    	if(stepExecution.getStatus() == BatchStatus.FAILED) {
    		
    		stepExecution.getJobExecution().getExecutionContext().put("exceptionStepName", stepExecution.getStepName());
    		
    		return ExitStatus.FAILED;
    	}
    	
        return ExitStatus.COMPLETED;
    }
}
