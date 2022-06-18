/**
 * @author Gaurav Garg
 * Created for LOS upload of ABFL
 *
 */
package com.cm.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;


public interface LosUploadDAO {

	String IDENTITY="LOSU";
	
	String getUploadLOSData(String userId);
	boolean whereToUpload(HttpServletRequest request,FormFile file);	
	int  countLine(String file);
	boolean readExcelforLOSUpload(HttpServletRequest request,HttpServletResponse response,String file,String user_id,String maker_id, String userBranch);
	int getBatchIdForErrorlog(String userID);   
	String validateDataLOSUpload(HttpServletRequest request,HttpServletResponse response,String file,String user_id,String bus_date);
	String downloadExcelFile(HttpServletRequest request,HttpServletResponse response,String filePath);
	
}

