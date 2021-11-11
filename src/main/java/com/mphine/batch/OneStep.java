package com.mphine.batch;

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
		
		taskFileMove.execute(contribution, context);
		taskRecFileSizeCheck.execute(contribution, context);
		taskFileNameValidCheck.execute(contribution, context);
		taskFileCopyWorkDirToSharedMemory.execute(contribution, context);
		taskCutOff.execute(contribution, context);
		taskCutOffValidCheck.execute(contribution, context);
		taskEncrypt.execute(contribution, context);
		taskEncryptMoveToNas.execute(contribution, context);
		taskInsertVmsVideoInfo.execute(contribution, context);
		
		return RepeatStatus.FINISHED;
	}	
}
