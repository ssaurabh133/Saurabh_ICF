package com.reportUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;

import com.GL.action.GenerateAllGLReports;
import com.connect.CommonFunction;

public class ReportGenerateUtility {
	private static final Logger logger = Logger.getLogger(ReportGenerateUtility.class.getName());
		
	  
	   

	    public static void closeOutputStream(String reportName, FileInputStream fin, ServletOutputStream outStream)throws IOException
	    {
	       try{
	    	if(outStream != null)
	        {
	    		outStream.flush();
	    		outStream.close();
	        }
	       }catch (Exception e) {
	    	   logger.error("Socket exception occurred. Now being handled by application to close output steam. "+reportName);
		}
	       finally
	       {
	    	 
	        try {
				fin.close();
			} catch (Exception e2) {
				logger.error("Exception occurred while closing FileInput stream. "+reportName);
			}
	       }
	    }
	
	    public static void closeOutputStream(String reportName, ServletOutputStream outStream)throws IOException
	    {
	       try{
	    	if(outStream != null)
	        {
	    		outStream.flush();
	    		outStream.close();
	        }
	       }catch (Exception e) {
	    	   logger.error("Socket exception occurred. Now being handled by application to close output steam. "+reportName);
		}
	       
	    }
	
}
