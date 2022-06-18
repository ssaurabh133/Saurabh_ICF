package com.a3s.adapter;

import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.exception.AdapterException;

/**
 * @author Ritesh Srivastava
 * 
 */
public interface PostHandler {

	void handle(Task job, TaskResult result) throws AdapterException;
}
