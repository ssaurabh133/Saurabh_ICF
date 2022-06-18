/**
 * 
 */
package com.cm.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

/**
 * @author saurabh
 *
 */
public interface ManualAdviceUploadDAO {


	String IDENTITY="MDU";
	
	String getUploadManualData(String userId);
	boolean whereToUpload(HttpServletRequest request,FormFile file);	
	 int  countLine(String file);
	 boolean readExcelforManualUpload(HttpServletRequest request,HttpServletResponse response,String file);
	 // NHB Part Prepayment Upload 
	 
	 boolean whereToUploadNHB(HttpServletRequest request,FormFile file);
	 int  countLineForNHB(String file);	 
	 boolean readExcelforNHBPartPrePayUpload(HttpServletRequest request,HttpServletResponse response,String file);
		boolean readExcelforGenerateBacthUpload(HttpServletRequest request,
				HttpServletResponse response, String file,String presentaionDate);

	
	
}
