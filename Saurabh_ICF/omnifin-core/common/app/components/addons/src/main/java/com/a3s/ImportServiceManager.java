package com.a3s;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlException;
import org.pentaho.di.version.BuildVersion;

import com.a3s.adapter.monitor.Monitor;
import com.a3s.adapter.monitor.MonitorTimerTask;
import com.a3s.adapter.util.ConfigFile;
import com.infodart.adapter.conf.MonitorType;

/**
 * This class responsible to schedule the monitor at given time(in seconds).
 * @author Ritesh Srivastava
 *
 */
public class ImportServiceManager {

	private Monitor[] monitorArray;
	private TimerTask[] timerTaskArray;
	private Log log = LogFactory.getLog(ImportServiceManager.class);
	
	public ImportServiceManager(){
		
	}

	public void sync(String[] arg) {
		String[] args = arg;
		File configfile = new File(args[0]);
		
		if(!configfile.exists() || !configfile.canRead()){
			log.error("Invalid configuration file -" + configfile.getAbsolutePath());
			throw new RuntimeException("Invalid configuration file -" + configfile.getAbsolutePath() );
		}
				
		try {
			ConfigFile.init(configfile);
		}catch (FileNotFoundException e) {
			log.error("Provide the valid config file.", e);
		}catch (IOException e) {
			log.error("IO error while reading config file.", e);
		} catch (XmlException e) {
			log.error("XML parsing error while reading config file.", e);
		}
		
		if(configfile.exists()){
			startMonitors();
		}

    }

	private void startMonitors() {
		log.info("Pentaho version " +BuildVersion.getInstance().getVersion());
		MonitorType[] monitors = ConfigFile.getInstance().getMonitorArray();
		monitorArray = new Monitor [monitors.length];
		timerTaskArray = new TimerTask[monitors.length];
		
		for(int j = 0; j < monitors.length; ++j) {
			MonitorType monitor = monitors[j];
			monitorArray[j] = new Monitor();
			
			final MonitorTimerTask monitorTimerTask;
			try {
				monitorTimerTask = (MonitorTimerTask) Class.forName(monitor.getTaskclassname()).newInstance();
				log.info("successfully loaded timer task with class name "
						+ monitorTimerTask.getClass().getName());
			} catch(Exception e) {
				log.error("Could not load monitor timer task with class name " + monitor.getTaskclassname());
				throw new RuntimeException(e);
			}
			
			monitorTimerTask.setMonitor(monitor.getName());
			timerTaskArray[j] = monitorTimerTask;
			final int iConst=j;
			monitorArray[iConst].schedule(timerTaskArray[iConst], 0, ConfigFile.getInstance().getScheduletime(monitorTimerTask.getMonitor())*1000);
				}
	}
	
	/**
	 * Cancel the running Timer, and clean all the cancelled tasks from monitor.
	 * Start new Timer and schedule the monitor at given time(in seconds). 
	 */
	public  void reSync() {
		// TODO - verify if any schedule is already under execution
		// 		  throw exeception if so is the case.
		for(int j = 0; j < monitorArray.length; ++j) {
			if((System.currentTimeMillis() - timerTaskArray[j].scheduledExecutionTime()) > 0)
				throw new IllegalStateException("Scheduled task is under process");
	
			timerTaskArray[j].cancel();
			monitorArray[j].purge();
		}
		
		startMonitors();
	}

	public void setEnabled(boolean enabledflag) {
		// TODO Auto-generated method stub
		
	}

}
