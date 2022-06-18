package com.a3s.adapter.monitor;

import java.io.File;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.a3s.adapter.AbstractProtocolProvider;
import com.a3s.adapter.IDataFile;
import com.a3s.adapter.IProtocol;
import com.a3s.adapter.PostHandler;
import com.a3s.adapter.PreHandler;
import com.a3s.adapter.TaskExecutor;
import com.a3s.adapter.dto.Task;
import com.a3s.adapter.dto.TaskResult;
import com.a3s.adapter.exception.AdapterException;
import com.a3s.adapter.kettle.impl.KettlePostHandler;
import com.a3s.adapter.util.ConfigFile;
import com.infodart.adapter.conf.EntityType;
import com.infodart.adapter.conf.ExecutorType;
import com.infodart.adapter.conf.ParamType;
import com.infodart.adapter.conf.ProtocolType;

/**
 * This class responsible to monitor the task on scheduled time. 
 * @author Ritesh Srivasatava
 *
 */
/**
 * @author Ritesh Srivasatava
 * 
 */
public class KettleTimerTask extends MonitorTimerTask {

	protected Log log = LogFactory.getLog(KettleTimerTask.class);
	private SimpleDateFormat renameDateFormatter = null;
	protected Map<String, String> params = new HashMap<String, String>();

	public KettleTimerTask() {
		this.renameDateFormatter = new SimpleDateFormat(
				"'.ERROR.doneat'yyyyMMMdd'-'HH:mm:ss");
	}

	@Override
	public void run() {
		ProtocolType protocolType = null;
		protocolType = findProtocol(ConfigFile.getInstance().getProtocol(
				getMonitor()));

		Map<String, String> params = prepareMap(protocolType);
		params.put(ConfigFile.PARAM_MONITOR, getMonitor());

		IProtocol protocolProvider = AbstractProtocolProvider
				.getProtocolProvider(ConfigFile.getInstance().getProtocol(
						getMonitor()), params);

		List<IDataFile> _allFiles = protocolProvider.getList();
		List<IDataFile> _elligibleFiles = getElligibleFiles(_allFiles);
		for (Iterator<IDataFile> iterator = _elligibleFiles.iterator(); iterator
				.hasNext();) {
			IDataFile _file = iterator.next();
			PreHandler preHandler;
			PostHandler postHandler;
			TaskExecutor taskExecutor;
			ExecutorType executorType = null;
			EntityType entity = null;

			try {
				entity = findEntity(_file.getName());
				if (entity == null) {
					continue;
				}
				log.info("\nprocessing file ------ [ "
						+ _file.getFile().getAbsolutePath() + " ]");
				executorType = findExecutor(entity.getExecutor());
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
				continue;
			}

			params = rePopulatePrepareMap(executorType, entity, _file);
			Task task = new Task(params);
			try {
				preHandler.handle(task);
				TaskResult taskResult = taskExecutor.execute(task);
				postHandler.handle(task, taskResult);
			} catch (AdapterException e) {
				log.error(e);
				throw new RuntimeException(e);
			}

			File renamedFile = KettlePostHandler.getFtrg();
			boolean filestatus = KettlePostHandler.getFileStatus();
			_file.renameFile(renamedFile, filestatus);

			if (_file.moveFileToArchive()) {
				log
						.info("\n****** file has been moved to archive directory successfully *******");
			} else {
				log
						.info("\n****** file not moved to archive directory *******");
			}
		}
	}

	/**
	 * @param allFiles
	 * @return sorted files on their last modification time
	 */
	protected List<IDataFile> getElligibleFiles(List<IDataFile> allFiles) {
		List<IDataFile> _filteredFiles = new ArrayList<IDataFile>();
		// TODO - for optimization, consider moving to static blocks
		String fileExt = ConfigFile.getInstance().getPattern(getMonitor());
		Pattern _extPattern = Pattern.compile(fileExt);
		Matcher _matcher = _extPattern.matcher("");

		for (Iterator<IDataFile> iterator = allFiles.iterator(); iterator
				.hasNext();) {
			IDataFile _aFile = iterator.next();
			_matcher.reset(_aFile.getName());
			if (_matcher.matches()) {
				_filteredFiles.add(_aFile);
			}
		}

		Collections.sort(_filteredFiles, new Comparator<IDataFile>() {
			public int compare(IDataFile f1, IDataFile f2) {
				if ((getPriority(f1) - getPriority(f2)) == 0) {

					long lcmp = (f1.timeStamp() - f2.timeStamp());
					if (lcmp < 0)
						return -1;
					else if (lcmp > 0)
						return 1;
					else
						return 0;
				}
				return (getPriority(f1) - getPriority(f2));
			}
		});

		return _filteredFiles;
	}

	/**
	 * @param <IDataFile>file
	 * @return priority
	 */
	private int getPriority(IDataFile file) {
		int value = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();

		for (EntityType entity : ConfigFile.getInstance().getEntityArray()) {
			map.put(entity.getFilepattern(), entity.getPriority());
		}

		for (Iterator<Entry<String, Integer>> iterator = map.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry<String, Integer> _aFile = (Map.Entry<String, Integer>) iterator
					.next();
			Pattern _extPattern = Pattern.compile(_aFile.getKey());
			Matcher _matcher = _extPattern.matcher(file.getName());
			if (_matcher.matches()) {
				value = _aFile.getValue();
			}
		}
		return value;
	}

	/**
	 * @param name
	 * @return EntityType
	 */
	protected EntityType findEntity(String name) {
		EntityType[] entityTypes = ConfigFile.getInstance().getEntityArray();
		for (EntityType e : entityTypes) {
			if (e.getDisabled() == null
					|| ConfigFile.replaceLocationPathVariable(e.getDisabled())
							.equalsIgnoreCase("false")) {
				if (e.getMonitorname().equalsIgnoreCase(getMonitor())
						&& Pattern.matches(e.getFilepattern(), name)) {
					return e;
				}
			}
		}
		return null;
	}

	/**
	 * @param name
	 * @return ExecutorType
	 */
	protected ExecutorType findExecutor(String name) {
		ExecutorType[] executors = ConfigFile.getInstance().getExecutorArray();
		for (ExecutorType executor : executors) {
			if (name.equals(executor.getName())) {
				return executor;
			}
		}
		throw new RuntimeException("Executor with name " + name + " not found.");
	}

	/**
	 * @param name
	 * @return ProtocolType
	 */
	protected ProtocolType findProtocol(String name) {
		ProtocolType[] protocols = ConfigFile.getInstance().getProtocolArray();
		for (ProtocolType protocol : protocols) {
			if (name.equals(protocol.getName())) {
				return protocol;
			}
		}
		return null;
	}

	/**
	 * @param executor
	 *            , IDataFile _fil
	 * @param entity
	 * @param _file
	 * @return Map<String, String>
	 */
	protected Map<String, String> prepareMap(ProtocolType protocol) {

		if (protocol != null) {
			for (ParamType p : protocol.getParams().getParamArray()) {
			params.put(p.getName(), ConfigFile
						.replaceLocationPathVariable(p.getValue()));
			}
		}
		return params;
	}

	protected Map<String, String> rePopulatePrepareMap(ExecutorType executor,
			EntityType entity, IDataFile _file) {

		for (ParamType p : executor.getParams().getParamArray()) {
			params.put(p.getName(), ConfigFile.replaceLocationPathVariable(p
					.getValue()));
		}

		for (ParamType p : entity.getParams().getParamArray()) {
			params.put(p.getName(), ConfigFile.replaceLocationPathVariable(p
					.getValue()));
		}

		params.put("INPUT_FILE_LOCATION", _file.getFile().getAbsolutePath());

		return params;
	}
}