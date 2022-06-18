package com.webservice.cp.pdvo;


public class UserAuthenticateRequestWrapper {
	private UserCredentials userCredentials;
	private String workingUserId;
	private String workingUserPassword;

	/**
	 * @return the userCredentials
	 */
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	/**
	 * @param userCredentials the userCredentials to set
	 */
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	/**
	 * @return the workingUserId
	 */
	public String getWorkingUserId() {
		return workingUserId;
	}
	/**
	 * @param workingUserId the workingUserId to set
	 */
	public void setWorkingUserId(String workingUserId) {
		this.workingUserId = workingUserId;
	}
	/**
	 * @return the workingUserPassword
	 */
	public String getWorkingUserPassword() {
		return workingUserPassword;
	}
	/**
	 * @param workingUserPassword the workingUserPassword to set
	 */
	public void setWorkingUserPassword(String workingUserPassword) {
		this.workingUserPassword = workingUserPassword;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserAuthenticateRequestWrapper [userCredentials="
				+ userCredentials + ", workingUserId=" + workingUserId
				+ ", workingUserPassword=" + workingUserPassword + "]";
	}
	
	
	
	
}
