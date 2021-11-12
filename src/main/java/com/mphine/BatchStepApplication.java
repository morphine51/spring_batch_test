package com.mphine;

import org.slf4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mphine.batch.utils.LogKeyUtil;

@SpringBootApplication
public class BatchStepApplication implements CommandLineRunner {

	@Autowired
	@Qualifier("vworkJobLauncher")
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("vworkJob")
	private Job vworkJob;
	
	@Autowired
	@Qualifier("vworkOneStepJob")
	private Job vworkOneStepJob;
	
	@Autowired
	private	LogKeyUtil logKeyUtil;
	
	public static void main(String[] args){
		SpringApplication.run(BatchStepApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
//		while(true) {
			
			String logKey = logKeyUtil.getRandomKey();
			MDC.put("LOG_KEY",logKey);
			
			JobParameters params = new JobParametersBuilder()
					.addString("fileName", "fileName")
					.addString("logKey", logKey)
					.toJobParameters();						
				
//			jobLauncher.run(vworkJob, params);
			jobLauncher.run(vworkOneStepJob, params);
			
//		}
	}

}
