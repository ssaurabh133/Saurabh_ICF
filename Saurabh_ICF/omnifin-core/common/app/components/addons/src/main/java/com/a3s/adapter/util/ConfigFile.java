package com.a3s.adapter.util;

import java.io.File;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import com.infodart.adapter.conf.EntityType;
import com.infodart.adapter.conf.ExecutorType;
import com.infodart.adapter.conf.MonitorType;
import com.infodart.adapter.conf.PosadapterDocument;
import com.infodart.adapter.conf.ProtocolType;

/**
 * This class read the configuration file.
 * 
 * @author Ritesh Srivastava
 * 
 */
public class ConfigFile {

	public static final String PARAM_MONITOR = "MONITOR_NAME";
	public static final String PARAM_ARCHIVE_DIR = "ARCHIVE_OUTPUT_FILE_DIR";
	public static final String PARAM_TEMPDIR = "TEMDIR";
	public static final String PARAM_IP = "IP";
	public static final String PARAM_USERNAME = "USERNAME";
	public static final String PARAM_PASSWORD = "PASSWORD";
	public static final String PARAM_PORT = "PORT";
	public static final String PARAM_PROCESSING_DATABASE_HOST = "PROCESSING_DATABASE_HOST";
	public static final String PARAM_PROCESSING_DATABASE_PORT = "PROCESSING_DATABASE_PORT";
	public static final String PARAM_PROCESSING_DATABASE_NAME = "PROCESSING_DATABASE_NAME";
	public static final String PARAM_PROCESSING_DATABASE_USER = "PROCESSING_DATABASE_USER";
	public static final String PARAM_PROCESSING_DATABASE_PASSWORD = "PROCESSING_DATABASE_PASSWORD";
	private static ConfigFile configFile = null;
	private PosadapterDocument posAdapterDocument;

	private ConfigFile() {
	}

	public synchronized static ConfigFile getInstance() {
		if (configFile == null)
			configFile = new ConfigFile();
		return configFile;
	}

	public static void init(File fconf) throws IOException, XmlException {
		getInstance().posAdapterDocument = PosadapterDocument.Factory
				.parse(fconf);
	}

	/**
	 * This method is responsible to replace the pattern "${..}" in location
	 * path with System Property.
	 * 
	 * @param inputPath
	 * @return newInputPath
	 */
	public static String replaceLocationPathVariable(String locPath) {

		String sysPropKey = null;
		String variable = null;
		String newLocPath = null;
		String sysPropValue = null;
		
		int varStartIndex = locPath.indexOf("${");
		int varEndIndex = locPath.indexOf("}");

		if ((varStartIndex != -1) && (varEndIndex != -1)) {

			variable = locPath.substring(varStartIndex, varEndIndex + 1);
			sysPropKey = locPath.substring(varStartIndex + 2, varEndIndex);
			sysPropValue = System.getProperty(sysPropKey);

			if (sysPropKey.equals("")) {
				return locPath;
			} else {
				newLocPath = locPath.replace(variable, sysPropValue);
				return newLocPath;
			}
		} else {
			return locPath;
		}
	}

	public PosadapterDocument getPosAdapterDocument() {
		return posAdapterDocument;
	}

	/**
	 * @return InputPath
	 */
	public String getInputpath(String monitor) {
		for (MonitorType monitorType : getMonitorArray())
			if (monitorType.getName().equalsIgnoreCase(monitor))
				return replaceLocationPathVariable(monitorType.getInputpath());

		throw new RuntimeException("Monitor with name " + monitor
				+ " not found.");
	}

	/**
	 * @return Pattern
	 */
	public String getPattern(String monitor) {
		for (MonitorType monitorType : getMonitorArray())
			if (monitorType.getName().equalsIgnoreCase(monitor))
				return monitorType.getPattern();

		throw new RuntimeException("Monitor with name " + monitor
				+ " not found.");
	}

	/**
	 * @return PreHandler
	 */
	public String getPrehandler(String monitor) {
		for (MonitorType monitorType : getMonitorArray())
			if (monitorType.getName().equalsIgnoreCase(monitor))
				return monitorType.getPrehandler();

		throw new RuntimeException("Monitor with name " + monitor
				+ " not found.");
	}

	/**
	 * @return PostHandler
	 */
	public String getPosthandler(String monitor) {
		for (MonitorType monitorType : getMonitorArray())
			if (monitorType.getName().equalsIgnoreCase(monitor))
				return monitorType.getPosthandler();

		throw new RuntimeException("Monitor with name " + monitor
				+ " not found.");
	}

	/**
	 * @return EntityType[]
	 */
	public EntityType[] getEntityArray() {
		return ConfigFile.getInstance().getPosAdapterDocument().getPosadapter()
				.getTransformations().getEntities().getEntityArray();
	}

	/**
	 * @return ExecutorType[]
	 */
	public ExecutorType[] getExecutorArray() {
		return ConfigFile.getInstance().getPosAdapterDocument().getPosadapter()
				.getExecutors().getExecutorArray();
	}

	/**
	 * @return Protocol
	 */
	public String getProtocol(String monitor) {
		for (MonitorType monitorType : getMonitorArray())
			if (monitorType.getName().equalsIgnoreCase(monitor))
				return monitorType.getProtocol();

		throw new RuntimeException("Monitor with name " + monitor
				+ " not found.");
	}

	/**
	 * @return ScheduleTime(long)
	 */
	public long getScheduletime(String monitor) {
		for (MonitorType monitorType : getMonitorArray())
			if (monitorType.getName().equalsIgnoreCase(monitor))
				return monitorType.getScheduletime();

		throw new RuntimeException("Monitor with name " + monitor
				+ " not found.");
	}

	/**
	 * @return ProtocolType[]
	 */
	public ProtocolType[] getProtocolArray() {
		return ConfigFile.getInstance().getPosAdapterDocument().getPosadapter()
				.getProtocols().getProtocolArray();
	}

	public MonitorType[] getMonitorArray() {
		return ConfigFile.getInstance().getPosAdapterDocument().getPosadapter()
				.getMonitors().getMonitorArray();
	}
}
