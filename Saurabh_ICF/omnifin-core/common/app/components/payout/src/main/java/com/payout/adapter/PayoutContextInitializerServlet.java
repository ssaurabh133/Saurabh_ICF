package com.payout.adapter;

import java.io.File;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.a3s.ImportServiceManager;










/**
 * This class is responsible for calling PayoutAdapter for scheduling jobs
 * @author Ritesh Srivastava
 * @created 25-july-2012	
 */
public class PayoutContextInitializerServlet extends HttpServlet {
	
	/**
	 * Constructor of the object.
	 */
	public PayoutContextInitializerServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		
		String appHomePath;
		String configFileName = null;
		String adapterFlag="1";
		final String[] configFile = new String[1];
		
		appHomePath = PropertyHome.getAppHome().getAbsolutePath();
			if(appHomePath == null || appHomePath.trim().isEmpty()){
			System.err.println("No Application home defined");
			throw new ServletException("No Application home defined");
		}
		File appHomePathFile = new File(appHomePath);
		if(!appHomePathFile.exists() || !appHomePathFile.isDirectory()){
			System.err.println("No valid application home defined - " + appHomePathFile.getAbsolutePath());
			throw new ServletException("No valid Application home defined- " + appHomePathFile.getAbsolutePath());
		}
		configFileName = "PayoutConfig.xml";
		File adapterConfFile = new File(appHomePathFile, configFileName);
		if(!adapterConfFile.exists() || !adapterConfFile.isFile() || !adapterConfFile.canRead()){
			System.err.println("No valid adapter conf file found - " + adapterConfFile.getAbsolutePath());
			adapterFlag = "0";
		}else{
			configFile[0] = adapterConfFile.getAbsolutePath();
			}
	
		boolean startImportService = false;
		if(adapterFlag.equals("1")){
			startImportService = true;
			
		}
		
		//Thread starts
		if(startImportService){
			System.out.println("Starting adapter with configuration - " + adapterConfFile.getAbsolutePath());
			Thread adapterThread = new Thread() {
				 public void run() {
				 new ImportServiceManager().sync(configFile);  
		         }
			};
			adapterThread.start();
		}
	}//init ends

}
