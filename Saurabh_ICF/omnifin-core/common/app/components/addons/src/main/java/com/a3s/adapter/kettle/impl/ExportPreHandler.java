package com.a3s.adapter.kettle.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;

import com.a3s.adapter.PreHandler;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.exception.AdapterException;

public class ExportPreHandler implements PreHandler {
	private Log log = LogFactory.getLog(ExportPreHandler.class);

	@Override
	public void handle(Task job) throws AdapterException {
		try {
			synchronized (KETTLE_LOCK) {
				if (!KettleEnvironment.isInitialized()) {
					//KettleEnvironment.init();
					KettleEnvironment.init(false);
					EnvUtil.environmentInit();
				}
			}
		} catch (KettleException e) {
			log.fatal("Error during kettle environment initialization", e);
			throw new AdapterException(
					"There were errors during the initialization of Kettle environment.",
					e);
		}

	}
}