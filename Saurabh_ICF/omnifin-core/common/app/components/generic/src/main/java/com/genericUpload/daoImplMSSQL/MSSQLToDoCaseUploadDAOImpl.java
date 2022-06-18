package com.genericUpload.daoImplMSSQL;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.genericUpload.actionforms.ExcelSheetUploadForm;
import com.genericUpload.actionforms.GenericUploadForm;
import com.genericUpload.dao.ToDoCaseUploadDAO;
import com.genericUpload.dto.GenericUploadParametersDTO;
import com.genericUpload.vo.GenericUploadVO;
import com.genericUpload.vo.XMLBean;
import com.ibm.icu.text.DecimalFormat;

public class MSSQLToDoCaseUploadDAOImpl implements ToDoCaseUploadDAO
{
	private static final Logger logger = Logger.getLogger(MSSQLToDoCaseUploadDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	 String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	 String dateFormat = resource.getString("lbl.dateInDao");
	 SimpleDateFormat dateFormatCheck = new SimpleDateFormat("dd-MM-yyyy");
	 SimpleDateFormat fileNameFormat = new SimpleDateFormat ("yyyy-MM-dd'_'hh-mm-ss");
	 String dbType=resource.getString("lbl.dbType");
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	
	   Connection con = null;
	   PreparedStatement ptmt = null;
	   ResultSet rs = null;
	   Statement stmt = null;
	   CallableStatement csmt = null;


	
@Override
public ArrayList<GenericUploadVO> getToDoCaseUploadDetails(String user_id) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public int saveToDoCaseUpload(String maker_id,String maker_date) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public boolean updateToDoCaseUpload(Map<String, String> parameter) {
	// TODO Auto-generated method stub
	return false;
}


@Override
public Map<String, String> fetchSystemOutputSheetToDoCase(String batchId,
		String makerId, String makerDate, String fileName, String filePath,
		String branch) {
	// TODO Auto-generated method stub
	return null;
}


}
		  
		   
		
	   
	    

	    
   
   
   