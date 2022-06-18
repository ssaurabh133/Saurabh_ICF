package com.cp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.connect.ConnectionDAO;
import com.cp.vo.UnderwritingDocUploadVo;

public class DocUpload {

	private static final Logger logger = Logger.getLogger(DocUpload.class
			.getName());

	public static boolean docUpload(HttpServletRequest request,
			FormFile myFile, String dealId, Object ob) {
		boolean status = false;
		String fileName = "";
		String filePath = "";
		String filePath1 = "";
		String message = "";
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo) ob;
		try 
			{
				String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UPLOAD_PATH'";
				String rpt = ConnectionDAO.singleReturn(query);
				File homeDir = new File(rpt);
				boolean isHomeDir = homeDir.isDirectory();
			
				
				if (isHomeDir) 
					{
						logger.info("the name you have entered is a directory  : " + homeDir);
						logger.info("the path is " + homeDir.getAbsolutePath());
					} 
				else 
					{
						boolean success = (new File(rpt)).mkdir();
						logger.info("Directory created successfully with name  : "+ homeDir);
						logger.info("the path is " + homeDir.getAbsolutePath());
					}
				
				logger.info("the name you have entered is a file : " + myFile);
				File directory = new File(rpt + "\\" + vo.getRefId());

				boolean isDirectory = directory.isDirectory();
				if (isDirectory) 
					{
						logger.info("the name you have entered is a directory  : " + directory);
						logger.info("the path is " + directory.getAbsolutePath());
					} 
				else 
					{
						boolean success = (new File(rpt + "\\" + vo.getRefId())).mkdir();   //Customer Ref. No.
						logger.info("Directory created successfully with name  : "+ rpt + "\\" + dealId);
						logger.info("the path is " + directory.getAbsolutePath());
						logger.info("the contet is " + myFile.getFileSize());
						logger.info("the contet is " + myFile.getContentType());
					}
			
		
				directory = new File(directory.getAbsolutePath() + "\\" + vo.getDocEntity()); //Customer Type(DocEntity)
				if (directory.isDirectory())
					{
						logger.info("the name you have entered is a directory  : " + directory);
						logger.info("the path is " + directory.getAbsolutePath());
					} 
				else 
					{
						boolean success = directory.mkdir();
						logger.info("Directory created successfully with name  : "+ rpt + "\\" + dealId);
						logger.info("the path is " + directory.getAbsolutePath());
						logger.info("the contet is " + myFile.getFileSize());
						logger.info("the contet is " + myFile.getContentType());
					}
				
			
				directory = new File(directory.getAbsolutePath() + "\\" + vo.getDocType()); //DocType
				if (directory.isDirectory()) 
					{
						logger.info("the name you have entered is a directory  : "+ directory);
						logger.info("the path is " + directory.getAbsolutePath());
					}
				else 
					{
						boolean success = directory.mkdir();
						logger.info("Directory created successfully with name  : "+ rpt + "\\" + dealId);
						logger.info("the path is " + directory.getAbsolutePath());
						logger.info("the contet is " + myFile.getFileSize());
						logger.info("the contet is " + myFile.getContentType());
					}
			
			
			// Get the file name
			fileName = myFile.getFileName();
			// Get the servers upload directory real path name
			filePath = directory.getAbsolutePath();
			filePath1=directory.getCanonicalPath();
			logger.info("AbsolutePath::::::::::::::::::::::::"+filePath);
			logger.info("conicalPath::::::::::::::::::::::::"+filePath1);
			/* Save file on the server */
			if (!fileName.equals("")) {
				logger.info("Server path:" + filePath);
				// Create file
				File fileTcpeate = new File(filePath, fileName);
				int fileSize = myFile.getFileSize(); // 1048576 bytes = 1 MB
				logger.info("Size of file= " + fileSize);
				if (fileSize < (1048576*2)) {
					// If file does not exists create file
					if (!fileTcpeate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(fileTcpeate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
						status = true;
					} else {
						message = "E";
						// logger.info("File already exists in the specified directory.");
						status = false;
					}
				} else {
					message = "U";
					// logger.info("File size exceeds the upper limit of 1 MB.");
					status = false;
				}
			} else {
				message = "S";
				// logger.info("Please select a File.");
				status = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("fileName", fileName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("message", message);
		return status;

	}

	public static boolean docDelete(String DOC, String dealId) {
		boolean status = false;
		try {
			String query1 ="SELECT DOCUMENT_PATH FROM CR_UPLOADED_DOCUMENTS where DOCUMENT_ID='"+DOC+"' and txn_id='"+dealId+"' ";
			logger.info("query1---------"+query1);
			String docPath = ConnectionDAO.singleReturn(query1);
			String query2 ="SELECT file_name FROM CR_UPLOADED_DOCUMENTS where DOCUMENT_ID='"+DOC+"' and txn_id='"+dealId+"' ";
			logger.info("query2---------"+query2);
			String fileName = ConnectionDAO.singleReturn(query2);
			String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UPLOAD_PATH'";
			String rpt = ConnectionDAO.singleReturn(query);
			File directory = new File(rpt+"/"+docPath);
			logger.info("directory---------------"+directory);
			boolean isDirectory = directory.isDirectory();
			if (isDirectory) {
				logger.info("the name you have entered is a directory  : "
						+ directory);
				logger.info("the path is " + directory.getAbsolutePath());
			}
			File file = new File(directory.getAbsolutePath(), fileName);
			boolean exists = file.exists();
			if (exists) {
				logger.info("the name you have entered is a file  : "
						+ file.getName());
				logger.info("the path is " + file.getAbsolutePath());
				status = file.delete();
				logger.info("status-----"+status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response, String downloadPath) 
		{
			boolean status = false;
			// response.setContentType("application/octet-stream");
			try 
				{
				String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UPLOAD_PATH'";
				String rpt = ConnectionDAO.singleReturn(query);
					logger.info("Check Point 1 ...");
					logger.info("Check Point 1 ...");
					File f = new File(rpt + "/" + downloadPath);
					logger.info("downloadPath In DownloadFile Method====="+downloadPath);
					FileInputStream in = new FileInputStream(f);
					int formDataLength = (int) f.length();
					ServletOutputStream out = response.getOutputStream();
					logger.info("Check Point formDataLength ..." + formDataLength);
		
					byte[] outputByte = new byte[formDataLength * 2];
					// copy binary content to output stream
					while (in.read(outputByte, 0, formDataLength) != -1)
						{
							out.write(outputByte, 0, formDataLength);
						}
					in.close();
					out.flush();
					out.close();
					status = true;
		
				} 
				catch (Exception e)
					{
						e.printStackTrace();
					}
		return status;

	}
	
	public static boolean kycDocUpload(HttpServletRequest request,
			FormFile myFile, String userId) {
		boolean status = false;
		String fileName = "";
		String filePath = "";
		String filePath1 = "";
		String message = "";
		
		try 
			{
				String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UPLOAD_PATH'";
				String rpt = ConnectionDAO.singleReturn(query);
				File homeDir = new File(rpt);
				boolean isHomeDir = homeDir.isDirectory();
			
				
				if (isHomeDir) 
					{
						logger.info("the name you have entered is a directory  : " + homeDir);
						logger.info("the path is " + homeDir.getAbsolutePath());
					} 
				else 
					{
						boolean success = (new File(rpt)).mkdir();
						logger.info("Directory created successfully with name  : "+ homeDir);
						logger.info("the path is " + homeDir.getAbsolutePath());
					}
				
				logger.info("the name you have entered is a file : " + myFile);
				File directory = new File(rpt + "\\" + userId);

				boolean isDirectory = directory.isDirectory();
				if (isDirectory) 
					{
						logger.info("the name you have entered is a directory  : " + directory);
						logger.info("the path is " + directory.getAbsolutePath());
					} 
				else 
					{
						boolean success = (new File(rpt + "\\" + userId)).mkdir();   //Customer Ref. No.
						logger.info("Directory created successfully with name  : "+ rpt);
						logger.info("the path is " + directory.getAbsolutePath());
						logger.info("the contet is " + myFile.getFileSize());
						logger.info("the contet is " + myFile.getContentType());
					}
				
			
			
				fileName = myFile.getFileName();
			
				filePath = directory.getAbsolutePath();
				filePath1=directory.getCanonicalPath();
				logger.info("AbsolutePath::::::::::::::::::::::::"+filePath);
				logger.info("conicalPath::::::::::::::::::::::::"+filePath1);
			
				if (!fileName.equals("")) {
					logger.info("Server path:" + filePath);
			
					File fileToCreate = new File(filePath, fileName);
					int fileSize = myFile.getFileSize(); // 1048576 bytes = 1 MB
					logger.info("Size of file= " + fileSize);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							fileOutStream.write(myFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
							status = true;
						} else {
							 File file = new File(fileToCreate.toString());
							 boolean blnDeleted = file.delete();
							 if(blnDeleted){
								 	
								 	FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
									fileOutStream.write(myFile.getFileData());
									fileOutStream.flush();
									fileOutStream.close();
									status = true;
							 }
						}
					
				} else {
					message = "S";
					status = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("fileName", fileName);
			request.setAttribute("filePath", filePath);
			request.setAttribute("message", message);
			return status;

	}
public static boolean toDoCaseUploadExcel(HttpServletRequest request,FormFile myFile, String toDoId) {
			boolean status = false;
			String fileName = "";
			String filePath = "";
			String filePath1 = "";
			String message = "";
			try{
				String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='TODO_CASE_UPLOAD_PATH'";
				filePath = ConnectionDAO.singleReturn(query);
				filePath =filePath+File.separator+toDoId;
				
				File homeDir = new File(filePath);
				if(!homeDir.exists()){
					homeDir.mkdirs();
				}
				fileName = myFile.getFileName();
				
				String ext=fileName.substring(fileName.lastIndexOf(".")+1);
				String flName=fileName.substring(0,fileName.lastIndexOf("."));
				
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				Date date = new Date();
				String currentTime = dateFormat.format(date) ;
				
				fileName=flName+"_"+currentTime+"."+ext;
				logger.info("Server path:" + filePath +" fileName : "+fileName);
				/* Save file on the server */
				if (!fileName.equals("")) {
					// Create file
					File fileTcpeate = new File(filePath, fileName);
					int fileSize = myFile.getFileSize(); // 1048576 bytes = 1 MB
					logger.info("Size of file= " + fileSize);
					if(fileSize>0){
						if (fileSize < (1048576*2)) {
							// If file does not exists create file
							if (!fileTcpeate.exists()) {
								FileOutputStream fileOutStream = new FileOutputStream(fileTcpeate);
								fileOutStream.write(myFile.getFileData());
								fileOutStream.flush();
								fileOutStream.close();
								status = true;
							} else {
								 File file = new File(fileTcpeate.toString());
								 boolean blnDeleted = file.delete();
								 if(blnDeleted){
									 	
									 	FileOutputStream fileOutStream = new FileOutputStream(fileTcpeate);
										fileOutStream.write(myFile.getFileData());
										fileOutStream.flush();
										fileOutStream.close();
										status = true;
								 }
								// logger.info("File already exists in the specified directory.");
								
							}
						} else {
							message = "U";
							// logger.info("File size exceeds the upper limit of 1 MB.");
							status = false;
						}
					}
				}else{
					message = "S";
					status = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("fileName", fileName);
			request.setAttribute("filePath", filePath);
			request.setAttribute("message", message);
			return status;

		}
}