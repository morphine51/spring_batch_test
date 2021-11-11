package com.mphine.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class OneStep implements Tasklet{
	
	@Value("#{jobParameters[logKey]}")
	private String logKey;
	
	@Value("#{jobParameters[fileName]}")
	private String fileName;
	
	@Autowired
	private TaskRecordFileMove taskFileMove;
	
	@Autowired
	private TaskRecFileSizeCheck taskRecFileSizeCheck;
	
	@Autowired
	private TaskFileNameValidCheck taskFileNameValidCheck;
	
	@Autowired
	private TaskFileCopyWorkDirToSharedMemory taskFileCopyWorkDirToSharedMemory;
	
	@Autowired
	private TaskCutOff taskCutOff;
	
	@Autowired
	private TaskCutOffValidCheck taskCutOffValidCheck;
	
	@Autowired
	private TaskEncrypt taskEncrypt;
	
	@Autowired
	private TaskEncryptMoveToNas taskEncryptMoveToNas;
	
	@Autowired
	private TaskInsertVmsVideoInfo taskInsertVmsVideoInfo;
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
		
		// Step.1
		try {
			taskFileMove.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepFileMove");
			throw e;
		}
		
		// Step.2
		try {
			taskRecFileSizeCheck.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepRecFileSizeCheck");
			throw e;
		}
		
		// Step.3
		try {
			taskFileNameValidCheck.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepFileNameValidCheck");
			throw e;
		}
		
		// Step.4
		try {
			taskFileCopyWorkDirToSharedMemory.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepFileCopyWorkDirToSharedMemory");
			throw e;
		}
		
		// Step.5
		try {
			taskCutOff.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepCutOff");
			throw e;
		}
		
		// Step.6
		try {
			taskCutOffValidCheck.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepCutOffValidCheck");
			throw e;
		}
		
		// Step.7
		try {
			taskEncrypt.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepEncrypt");
			throw e;
		}
		
		// Step.8
		try {
			taskEncryptMoveToNas.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepEncryptMoveToNas");
			throw e;
		}
		
		// Step.9
		try {
			taskInsertVmsVideoInfo.execute(contribution, context);
		}catch(Exception e) {
			context.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("exceptionStepName", "stepInsertVmsVideoInfo");
			throw e;
		}
		
		return RepeatStatus.FINISHED;
	}	
}
