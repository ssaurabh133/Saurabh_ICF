package com.a3s.adapter.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.PostHandler;
import com.a3s.adapter.PreHandler;
import com.a3s.adapter.TaskExecutor;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.exception.AdapterException;
import com.a3s.adapter.util.ConfigFile;
import com.infodart.adapter.conf.EntityType;
import com.infodart.adapter.conf.ExecutorType;
import com.infodart.adapter.conf.ParamType;

public class ExportTimerTask extends MonitorTimerTask {
	private Log log = LogFactory.getLog(KettleTimerTask.class);
	Map<String, String> params;

	public ExportTimerTask() {
	}

	@Override
	public void run() {
		PreHandler preHandler;
		PostHandler postHandler;
		TaskExecutor taskExecutor;
		ExecutorType executorType = null;
		List<EntityType> entityTypes = findEntities(getMonitor());
		for (EntityType entity : entityTypes) {
			executorType = findExecutor(entity.getExecutor());
			try {
				preHandler = (PreHandler) Class.forName(
						ConfigFile.getInstance().getPrehandler(getMonitor()))
						.newInstance();
				postHandler = (PostHandler) Class.forName(
						ConfigFile.getInstance().getPosthandler(getMonitor()))
						.newInstance();
						taskExecutor = (TaskExecutor) Class.forName(
						executorType.getClass1()).newInstance();
			} catch (Exception e) {
				RuntimeException _rte = new RuntimeException(
						"Pre-Post handlers could not be loaded.", e);
				log.error("Error Loading Task Handlers", _rte);
				throw _rte;
			}
			prepareMap(executorType, entity);
			Task task = new Task(params);
						try {
				
					preHandler.handle(task);
					TaskResult taskResult = taskExecutor.execute(task);
					postHandler.handle(task, taskResult);	
				

			} catch (AdapterException e) {
				log.error(e);
				throw new RuntimeException(e);
			}
		}
	}

	private List<EntityType> findEntities(String monitor) {
		List<EntityType> entityTypeList = new ArrayList<EntityType>();
		for (EntityType entityType : ConfigFile.getInstance().getEntityArray()) {
			if (entityType.getMonitorname().equalsIgnoreCase(monitor)) {
				entityTypeList.add(entityType);
			}
		}
		return entityTypeList;
	}

	private ExecutorType findExecutor(String name) {
		ExecutorType[] executors = ConfigFile.getInstance().getExecutorArray();
		for (ExecutorType executor : executors) {
			if (name.equals(executor.getName())) {
				return executor;
			}
		}
		throw new RuntimeException("Executor with name " + name + " not found.");
	}

	private Map<String, String> prepareMap(ExecutorType executor,
			EntityType entity) {

		params = new HashMap<String, String>();
		for (ParamType p : executor.getParams().getParamArray()) {
			params.put(p.getName(),
					ConfigFile.replaceLocationPathVariable(p.getValue()));
		}

		for (ParamType p : entity.getParams().getParamArray()) {
			params.put(p.getName(),
					ConfigFile.replaceLocationPathVariable(p.getValue()));
		}
		return params;
	}
}
