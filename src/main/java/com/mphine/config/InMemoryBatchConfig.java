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

import java.util.concurrent.ThreadPoolExecutor;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.mphine.batch.OneStep;
import com.mphine.batch.TaskCutOff;
import com.mphine.batch.TaskCutOffValidCheck;
import com.mphine.batch.TaskEncrypt;
import com.mphine.batch.TaskEncryptMoveToNas;
import com.mphine.batch.TaskFileCopyWorkDirToSharedMemory;
import com.mphine.batch.TaskFileNameValidCheck;
import com.mphine.batch.TaskInsertVmsVideoInfo;
import com.mphine.batch.TaskRecFileSizeCheck;
import com.mphine.batch.TaskRecordFileMove;
import com.mphine.batch.listener.JobListener;
import com.mphine.batch.listener.StepListener;

/**
 * 
 * @FileName    : BatchConfig.java
 * @Date        : 2018. 12. 10.
 * @Author      : hkpark
 * @Description : spring batch 설정 class
 * @History     : [20181210] 최초작성.
 * 
 */

//@Configuration
//@EnableBatchProcessing
public class InMemoryBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobListener jobListener;	
	
	@Autowired
	private StepListener stepListener;	
	
	@Autowired
	private TaskRecFileSizeCheck taskRecFileSizeCheck;
	
	@Autowired
	private TaskRecordFileMove taskFileMove;	
	
	@Autowired
	private TaskFileNameValidCheck taskFileNameValidCheck;
	
	@Autowired
	private	TaskFileCopyWorkDirToSharedMemory taskFileCopyWorkDirToSharedMemory;
	
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
	
	@Autowired
	private OneStep oneStep;
	
	@Value("${vwork.batch.core-pool-size}")
	private int corePoolSize;
	
	@Value("${vwork.batch.max-pool-size}")
	private int maxPoolSize;
	
	@Value("${vwork.batch.queue-capacity}")
	private int queueCapacity;		
	
	/**
	 * batchThreadPool 설정
	 * @return
	 */
	@Bean(name = "batchExecutor")
	public TaskExecutor batchThreadPoolExecutor() {
    	
    	ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    	// CallerRunsPolicy: queue 가 가득차면 호출한 쪽의 thread를 가져다 사용한다.
    	// 기본 AbortPolicy: TaskRejectedException 이 발생 (batch_job_execution 테이블에서 확인 가능)
    	taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    	/** 기본 쓰레드 사이즈 */
    	taskExecutor.setCorePoolSize(corePoolSize);
    	/** 최대 쓰레드 사이즈 */
    	taskExecutor.setMaxPoolSize(maxPoolSize);
    	taskExecutor.setQueueCapacity(queueCapacity);
    	taskExecutor.setThreadNamePrefix("BatchExecutor-");
    	taskExecutor.initialize();
        return taskExecutor;
	}	
	
	/**
	 * jobLaucher 설정
	 * @param jobRepository
	 * @return
	 * @throws Exception
	 */
	@Bean(name="vworkJobLauncher")
	public JobLauncher jobLauncher(JobRepository jobRepository, @Qualifier("batchExecutor") TaskExecutor batchThreadPoolExecutor) {
	    SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
	    simpleJobLauncher.setTaskExecutor(batchThreadPoolExecutor);
	    simpleJobLauncher.setJobRepository(jobRepository);
	    return simpleJobLauncher;
	}	
	
	/**
	 * recFileSizeCheck step 설정
	 * @return
	 */
	@Bean
	public Step stepRecFileSizeCheck() {
		return stepBuilderFactory.get("stepRecFileSizeCheck")
				.listener(stepListener).tasklet(taskRecFileSizeCheck).build();
	}
	
	/**
	 * fileMove step 설정
	 * @return
	 */
	@Bean
	public Step stepFileMove() {
		return stepBuilderFactory.get("stepFileMove")
				.listener(stepListener).tasklet(taskFileMove).build();
	}
	
	/**
	 * fileNameValidCheck step 설정
	 * @return
	 */
	@Bean
	public Step stepFileNameValidCheck() {
		return stepBuilderFactory.get("stepFileNameValidCheck")
				.listener(stepListener).tasklet(taskFileNameValidCheck).build();
	}
	
	/**
	 * stepFileCopyWorkDirToSharedMemory step 설정
	 * @return
	 */
	@Bean
	public Step stepFileCopyWorkDirToSharedMemory() {
		return stepBuilderFactory.get("stepFileCopyWorkDirToSharedMemory")
				.listener(stepListener).tasklet(taskFileCopyWorkDirToSharedMemory).build();
	}
	
	/**
	 * cutoff step 설정
	 * @return
	 */
	@Bean
	public Step stepCutOff() {
		return stepBuilderFactory.get("stepCutOff")
				.listener(stepListener).tasklet(taskCutOff).build();
	}	
	
	/**
	 * cutOffValidCheck step 설정
	 * @return
	 */
	@Bean
	public Step stepCutOffValidCheck() {
		return stepBuilderFactory.get("stepCutOffValidCheck")
				.listener(stepListener).tasklet(taskCutOffValidCheck).build();
	}
	
	/**
	 * encrypt step 설정 
	 * @return
	 */
	@Bean
	public Step stepEncrypt() {
		return stepBuilderFactory.get("stepEncrypt")
				.listener(stepListener).tasklet(taskEncrypt).build();
	}
	
	/**
	 * encryptMoveToNas step 설정
	 * @return
	 */
	@Bean
	public Step stepEncryptMoveToNas() {
		return stepBuilderFactory.get("stepEncryptMoveToNas")
				.listener(stepListener).tasklet(taskEncryptMoveToNas).build();
	}
	
	/**
	 * insertVmsVideoInfo step 설정
	 * @return
	 */
	@Bean
	public Step stepInsertVmsVideoInfo() {
		return stepBuilderFactory.get("stepInsertVmsVideoInfo")
				.listener(stepListener).tasklet(taskInsertVmsVideoInfo).build();
	}
	
	/**
	 * job 설정
	 * @return
	 */
	@Bean("vworkJob")
	public Job vworkJob() {
		return jobBuilderFactory.get("vworkJob").preventRestart().incrementer(new RunIdIncrementer())
				.start(stepFileMove())
				.next(stepRecFileSizeCheck())
				.next(stepFileNameValidCheck())
				.next(stepFileCopyWorkDirToSharedMemory())
				.next(stepCutOff())
				.next(stepCutOffValidCheck())				
				.next(stepEncrypt())
				.next(stepEncryptMoveToNas())
				.next(stepInsertVmsVideoInfo())
				.listener(jobListener).build();
	}
	
	
	// step 1개짜리 설정
	@Bean("vworkOnStep")
	public Step vworkOneStep() {
		return stepBuilderFactory.get("vworkOneStep")
				.listener(stepListener).tasklet(oneStep).build();
	}
	
	@Bean("vworkOneStepJob")
	public Job vworkOneStepJob() {
		return jobBuilderFactory.get("vworkOnStep").preventRestart().incrementer(new RunIdIncrementer())
				.start(vworkOneStep())
				.listener(jobListener).build();
	}
}
