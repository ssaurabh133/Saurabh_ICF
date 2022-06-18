package com.a3s.adapter.monitor;

import java.util.TimerTask;

public abstract class MonitorTimerTask extends TimerTask {

	private String monitor;

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
}
