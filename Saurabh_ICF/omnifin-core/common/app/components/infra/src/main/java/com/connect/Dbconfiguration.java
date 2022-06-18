package com.connect;
/**
 * 
 * @author admin
 *
 */
public class Dbconfiguration {
	public static String url=null;
	public static String username=null;
	public static String password=null;

	public static String getURl() {
		return url;
	}

	public static void setURl(String rl) {
		url = rl;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Dbconfiguration.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Dbconfiguration.password = password;
	}

}
