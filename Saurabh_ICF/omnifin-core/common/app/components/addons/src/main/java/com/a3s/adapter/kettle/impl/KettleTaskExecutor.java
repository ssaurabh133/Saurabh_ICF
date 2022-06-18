package com.a3s.adapter.kettle.impl;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;

import com.a3s.adapter.TaskExecutor;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.exception.AdapterException;

public class KettleTaskExecutor implements TaskExecutor {
	protected Log log = LogFactory.getLog(KettleTaskExecutor.class);
	protected KettleException e = null;
	protected TaskResult result = new TaskResult();

	@Override
	public TaskResult execute(Task task) throws AdapterException {
		JobMeta jobMeta = null;
		String fileName = task.getParams().get("ktlfile");
		try {
			jobMeta = new JobMeta(fileName, null);
		} catch (KettleXMLException e) {
			log.error("error occured while initializing jobMeta", e);
			throw new AdapterException(e);
		}
		Job job = new Job(null, jobMeta);
		Set paramsSet = task.getParams().entrySet();
		Iterator iterator = paramsSet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry) iterator.next();
			job.setVariable(entry.getKey(), entry.getValue());
		}
		job.initializeVariablesFrom(null);
		job.getJobMeta().setInternalKettleVariables(job);
		job.getJobMeta().setArguments(null);
		job.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread arg0, Throwable arg1) {
				log.error("Uncaught exception while job execution.", arg1);
			}
		});
		try {
			job.execute(1, null);
		} catch (KettleException e) {
			// changed to logging only so that the process doesn't
			// terminate here
			log.error("Errors encountered during job execution", e);
			result.setException(e);
			throw new AdapterException(e);
		}
		job.waitUntilFinished();
		if (job.getErrors() > 0) {
			// changed to logging only so that the process doesn't
			// terminate here
			log.error("There were errors during job execution");
			result.setException(e);
		}else{
			log.info("**** JOB COMPLETED SUCCESSFULLY ******");
			System.out.println("");
		}
		job.clearParameters();
		jobMeta.clear();
		job = null;
		return result;
	}
}
