package com.a3s.adapter.kettle.impl;

import com.a3s.adapter.TaskExecutor;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.exception.AdapterException;

public class ExportTaskExecutor extends KettleTaskExecutor implements
		TaskExecutor {
	public TaskResult execute(Task task) throws AdapterException {
		return super.execute(task);
	}
}
