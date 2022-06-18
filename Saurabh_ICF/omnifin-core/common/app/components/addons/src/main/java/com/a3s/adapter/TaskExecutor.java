package com.a3s.adapter;

import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.exception.AdapterException;

/**
 * @author Ritesh Srivastava
 * 
 */
public interface TaskExecutor {

	TaskResult execute(Task job) throws AdapterException;
}
