package com.genericUpload.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.genericUpload.actionforms.ExcelSheetUploadForm;
import com.genericUpload.actionforms.GenericUploadForm;
import com.genericUpload.dto.GenericUploadParametersDTO;
import com.genericUpload.vo.GenericUploadVO;
import com.genericUpload.vo.XMLBean;


public interface ToDoCaseUploadDAO
{
	
public String IDENTITY = "TODO";

public ArrayList<GenericUploadVO> getToDoCaseUploadDetails(String user_id);
public int saveToDoCaseUpload(String maker_id,String maker_date);
public boolean updateToDoCaseUpload(Map<String, String> parameter);
public Map<String,String> fetchSystemOutputSheetToDoCase(String batchId,String makerId,String makerDate,String fileName,String filePath,String branch);

}
		         
		         
		     

		  
		   
		
	   
	    

	    
   
   
   