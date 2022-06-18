package com.connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;




import sun.misc.BASE64Decoder;

import com.lowagie.text.pdf.codec.Base64;

import antlr.StringUtils;




import com.reportUtility.ReportGenerateUtility;

import java.util.ResourceBundle;


public class UploadDocument {
	
	private static final Logger logger = Logger.getLogger(UploadDocument.class.getName());
	public static boolean docUpload(HttpServletRequest request,FormFile myFile,String dealId) {
	    String file = myFile.toString();
	    logger.info("FILE:" + file);
	    logger.info("myFile:" + myFile);
	    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");		
		boolean status=false;
		String fileName="";
		String filePath="";
		String message="";
		try{
			String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UNDERWRITER_UPLOAD'";
			String rpt= ConnectionDAO.singleReturn(query);
			File homeDir = new File(rpt);
			boolean isHomeDir = homeDir.isDirectory();
			if (isHomeDir) {
			      logger.info("the name you have entered is a directory  : "  +homeDir);
			      logger.info("the path is "  + homeDir.getAbsolutePath());
				}
			else
			{
				boolean success = (new File(rpt)).mkdir();
				logger.info("Directory created successfully with name  : "  +homeDir); 
				logger.info("the path is "  + homeDir.getAbsolutePath());
			}
			
		      String AllowedFiles = resource.getString("allowedfiletype");
		      logger.info("AllowedFiles  " + AllowedFiles);
		      logger.info("File name : " + file.substring(file.indexOf(".") + 1).toLowerCase());

		      if (AllowedFiles.contains(file.substring(file.indexOf(".") + 1).toLowerCase()))
		      {
		        logger.info("Inside File extension check exist");
		      }
		      else
		      {
		        throw new Exception("File type Not supportive ");
		      }			
			
			logger.info("the name you have entered is a file : "  +myFile);
			File directory = null;
			String customerId="";
			if(request.getAttribute("custId")!=null && request.getAttribute("custId").toString()!="")
			{
				 customerId=request.getAttribute("custId").toString();
				directory=new File(rpt+"/"+dealId+"_"+customerId);
			}
			else{
				directory=new File(rpt+"/"+dealId);
			}
			boolean isDirectory = directory.isDirectory();
			if (isDirectory) {
			      logger.info("the name you have entered is a directory  : "  +directory);
			      logger.info("the path is "  + directory.getAbsolutePath());
				}
			else
			{
				if(request.getAttribute("custId")!=null && request.getAttribute("custId").toString()!="")
				{
				customerId=request.getAttribute("custId").toString();
				boolean success = (new File(rpt+"/"+dealId+"_"+customerId)).mkdir();
				}
				else{
					boolean success1 = (new File(rpt+"/"+dealId)).mkdir();
				}
				logger.info("Directory created successfully with name  : "  +rpt+"/"+dealId); 
				logger.info("the path is "  + directory.getAbsolutePath());
				logger.info("the contet is "  + myFile.getFileSize());
				logger.info("the contet is "  + myFile.getContentType());
			}
	    //Get the file name
	        fileName    = myFile.getFileName();
	    //Get the servers upload directory real path name
	        filePath = directory.getAbsolutePath();
	    /* Save file on the server */
	        if(!fileName.equals("")){  
	        	logger.info("Server path:" +filePath);
	        	//Create file
	        	File fileToCreate = new File(filePath, fileName);
	        		int fileSize = myFile.getFileSize(); //1048576 bytes = 1 MB
	        		logger.info("Size of file= "+fileSize);
	        		if(fileSize<1048576)
	        		{
		        		//If file does not exists create file                      
		        		if(!fileToCreate.exists()){
		        				FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
		        				fileOutStream.write(myFile.getFileData());
		        				fileOutStream.flush();
		        				fileOutStream.close();
		        				status=true;
		        		}
		        		else
		        		{
		        			message="E";
		        			//logger.info("File already exists in the specified directory.");
				        	status=false;
		        		}
	        		}
	        		else
	        		{
	        			message="U";
	        			//logger.info("File size exceeds the upper limit of 1 MB.");
			        	status=false;
	        		}
	        }
	        else
	        {
	        	message="S";
	        	//logger.info("Please select a File.");
	        	status=false;
	        }
	        
		}catch(Exception e){
			 message = "X";
		     request.setAttribute("message", message);
		     status = false;
			e.printStackTrace();
		}
		request.setAttribute("fileName", fileName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("message",message);
		return status;
		
	}
	
	public static boolean docDelete(String fileName,String dealId)
	{
		boolean status=false;
		try
		{
			String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UNDERWRITER_UPLOAD'";
			String rpt= ConnectionDAO.singleReturn(query);
			File directory = new File(rpt+"/"+dealId);
			boolean isDirectory = directory.isDirectory();
			if (isDirectory) {
			      logger.info("the name you have entered is a directory  : "  +directory);
			      logger.info("the path is "  + directory.getAbsolutePath());
				}
			File file= new File(directory.getAbsolutePath(),fileName);
			boolean exists = file.exists();
			if(exists)
			{
				logger.info("the name you have entered is a file  : "  +file.getName());
			    logger.info("the path is "  + file.getAbsolutePath());
				file.delete();
				status=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
	}
	
	public static boolean downloadFile(HttpServletRequest request,HttpServletResponse response,String downloadPath)
	{
		boolean status=false;
		//response.setContentType("application/octet-stream");
		try
		{
			logger.info("Check Point 1 ...");
			logger.info("Check Point 1 ...");
			File f = new File(downloadPath);
			FileInputStream in =new FileInputStream(f);
			int formDataLength =(int)f.length();
			ServletOutputStream out = response.getOutputStream();
            logger.info("Check Point formDataLength ..."+formDataLength);
			 
	        byte[] outputByte = new byte[formDataLength*2];
	        //copy binary content to output stream
	        while(in.read(outputByte, 0, formDataLength) != -1){
	        	out.write(outputByte, 0, formDataLength);
	        }
	        in.close();
	        out.flush();
	        out.close();
	        status=true;

		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
	}
	public static boolean webDocUpload(HttpServletRequest request,String myFile,String dealId) {
		String file=myFile;
		logger.info("FILE:"+file);
		logger.info("myFile:"+myFile);
		java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");	
		boolean status=false;
		String fileName="";
		String filePath="";
		String message="";
	
		try{
			String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UNDERWRITER_UPLOAD'";
			String rpt= ConnectionDAO.singleReturn(query);
			File homeDir = new File(rpt);
			boolean isHomeDir = homeDir.isDirectory();
			if (isHomeDir) {
			      logger.info("the name you have entered is a directory  : "  +homeDir);
			      logger.info("the path is "  + homeDir.getAbsolutePath());
				}
			else
			{
				boolean success = (new File(rpt)).mkdir();
				logger.info("Directory created successfully with name  : "  +homeDir); 
				logger.info("the path is "  + homeDir.getAbsolutePath());
			}
			

			File directory = null;
			String customerId="";
			if(request.getAttribute("custId")!=null && request.getAttribute("custId").toString()!="")
			{
				 customerId=request.getAttribute("custId").toString();
				directory=new File(rpt+"/"+dealId+"_"+customerId);
			}
			else{
				directory=new File(rpt+"/"+dealId);
			}
			boolean isDirectory = directory.isDirectory();
			if (isDirectory) {
			      logger.info("the name you have entered is a directory  : "  +directory);
			      logger.info("the path is "  + directory.getAbsolutePath());
				}
	
			else
			{
				if(request.getAttribute("custId")!=null && request.getAttribute("custId").toString()!="")
				{
				customerId=request.getAttribute("custId").toString();
				boolean success = (new File(rpt+"/"+dealId+"_"+customerId)).mkdir();
				}
				else{
					boolean success1 = (new File(rpt+"/"+dealId)).mkdir();
				}
				logger.info("Directory created successfully with name  : "  +rpt+"/"+dealId); 
				logger.info("the path is "  + directory.getAbsolutePath());
	
			}
	    //Get the file name fileDes
			if(request.getAttribute("fileDes")!=null && request.getAttribute("fileDes").toString()!="")
			{
				String fileDescribtion=request.getAttribute("fileDes").toString();
				fileName    = fileDescribtion+".png";
			}
			else
			{
	        fileName    = customerId+".png";
			}
	    //Get the servers upload directory real path name
	        filePath = directory.getAbsolutePath();
	    /* Save file on the server */
	        if(!fileName.equals("")){  
	        	logger.info("Server path:" +filePath);
	        	//Create file
	        	File fileToCreate = new File(filePath, fileName);

		        		//If file does not exists create file                      
		        		if(!fileToCreate.exists()){
		        			
/*		        			byte[] btDataFile = new sun.misc.BASE64Decoder().decodeBuffer(myFile);
		        			File of = new File("yourFile.png");
		        			FileOutputStream osf = new FileOutputStream(of);
		        			osf.write(btDataFile);
		        			osf.flush();*/
		        				FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
		        				byte[] decodedBytes = new sun.misc.BASE64Decoder().decodeBuffer(myFile);
		   //     				byte[] decodedBytes =Base64.decode(myFile.getBytes(),0,0,0);

		        				fileOutStream.write(decodedBytes);
		        				fileOutStream.flush();
		        				fileOutStream.close();
		        				status=true;
		        		}
		        		else
		        		{
		        			message="E";
		        			//logger.info("File already exists in the specified directory.");
				        	status=false;
		        		}

	        }
	        else
	        {
	        	message="S";
	        	//logger.info("Please select a File.");
	        	status=false;
	        }
	        
		}catch(Exception e){
			
		  	message="X";
		  	request.setAttribute("message", message );
		  	status=false;

			e.printStackTrace();
		}
		request.setAttribute("fileName", fileName);
		request.setAttribute("filePath", filePath);
		request.setAttribute("message",message);
		return status;
		
	}

	public static boolean customerDocDelete(String docName,String customerId,String source)
	{
		boolean status=false;
		try
		{
			String query = "SELECT DOCUMENT_PATH  FROM "+StringEscapeUtils.escapeSql(CommonFunction.checkNull(source)).trim()+"  WHERE customer_Id="+customerId+"";
			String rpt= ConnectionDAO.singleReturn(query);
			File directory = new File(rpt);
			boolean isDirectory = directory.isDirectory();
			if (isDirectory) {
			      logger.info("the name you have entered is a directory  : "  +directory);
			      logger.info("the path is "  + directory.getAbsolutePath());
				}
			File file= new File(directory.getAbsolutePath(),docName);
			boolean exists = file.exists();
			if(exists)
			{
				logger.info("the name you have entered is a file  : "  +file.getName());
			    logger.info("the path is "  + file.getAbsolutePath());
				file.delete();
				status=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
	}
	//added by ranjeet singh
		public static boolean downloadFileAll(HttpServletRequest request,HttpServletResponse response,ServletContext context,String filePath)
		{
			boolean status=false;
			try
			{
				File downloadFile = new File(filePath);
				FileInputStream inStream = new FileInputStream(downloadFile);
				String relativePath = context.getRealPath("");
				System.out.println("relativePath = " + relativePath);
				String mimeType = context.getMimeType(filePath);
				if (mimeType == null) {        
					mimeType = "application/octet-stream";
				}
				System.out.println("MIME type: " + mimeType);
				response.setContentType(mimeType);
				response.setContentLength((int) downloadFile.length());
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
				response.setHeader(headerKey, headerValue);
				OutputStream outStream = response.getOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				while ((bytesRead = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				inStream.close();
				outStream.close(); 
				status=true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return status;
		}



}