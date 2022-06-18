package com.a3s.adapter;

import com.a3s.adapter.dto.Task;
import com.a3s.adapter.exception.AdapterException;

/**
 * @author Ritesh Srivastava
 * 
 */
public interface PreHandler {
	public static final Object KETTLE_LOCK = new Object();

	void handle(Task job) throws AdapterException;
}
