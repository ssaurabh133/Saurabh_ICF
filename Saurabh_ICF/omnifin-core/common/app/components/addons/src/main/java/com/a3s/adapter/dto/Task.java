package com.a3s.adapter.dto;

import java.util.Map;

/**
 * @author  Ritesh Srivastava
 *
 */
public class Task {
	
	private Map<String, String> params;
	
	public Task(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getParams() {
		return params;
	}
	

}
