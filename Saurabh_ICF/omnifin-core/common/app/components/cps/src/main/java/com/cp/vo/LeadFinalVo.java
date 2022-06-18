package com.cp.vo;

import java.io.Serializable;

public class LeadFinalVo implements Serializable{
	private String applicationdate;
	private String meetingdatetime;
	private String followup;
	private String followupdate;
	private String meetingremarks;
	
	
	
	public String getMeetingdatetime() {
		return meetingdatetime;
	}
	public void setMeetingdatetime(String meetingdatetime) {
		this.meetingdatetime = meetingdatetime;
	}
	public String getFollowup() {
		return followup;
	}
	public void setFollowup(String followup) {
		this.followup = followup;
	}
	public String getFollowupdate() {
		return followupdate;
	}
	public void setFollowupdate(String followupdate) {
		this.followupdate = followupdate;
	}
	public String getMeetingremarks() {
		return meetingremarks;
	}
	public void setMeetingremarks(String meetingremarks) {
		this.meetingremarks = meetingremarks;
	}
	public void setApplicationdate(String applicationdate) {
		this.applicationdate = applicationdate;
	}
	public String getApplicationdate() {
		return applicationdate;
	}
	
	

}
