package com.genericUpload.actions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;
import org.springframework.web.context.request.RequestAttributes;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.actions.DocUpload;
import com.cp.dao.FileUtilityDao;
import com.cp.dao.FinancialReportDao;
import com.cp.process.ConstantValue;
import com.cp.vo.FinancialReportVo;
import com.genericUpload.actionforms.GenericUploadForm;
import com.genericUpload.dao.GenericUploadDAO;
import com.genericUpload.dao.ToDoCaseUploadDAO;
import com.genericUpload.dto.GenericUploadParametersDTO;
import com.genericUpload.vo.GenericUploadVO;
import com.genericUpload.vo.XMLBean;
import com.ibm.icu.text.DecimalFormat;
import com.login.roleManager.UserObject;
import com.utils.async.LMSMessagingClient;
import com.utils.async.LMSMessagingConstants;
/*import com.GL.DAO.VoucherUploadDAO;
import com.GL.actionForm.ExcelSheetUploadForm;
import com.GL.business.ExcelSheetUploadBusiness;
import com.GL.business.VoucherUploadBusiness;
import com.GL.dto.VoucherUploadParametersDTO;
import com.GL.glVO.VoucherUploadVO;*/
import com.cp.actions.DocUpload;
import com.connect.ConnectionDAO;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import sun.misc.BASE64Encoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.binary.Base64;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;


public class ToDoListUploadAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(ToDoListUploadAction.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	public ActionForward searchtoDoListUpload(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("In searchToDoListUpload.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		String strFlag="";	
		String branchId="";
		String userId="";
		String branch="";
		if(sessionId!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
		}
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
		}else{
			logger.info("here in ToDoListUploadAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String fieldSetLebel="ToDo Case Upload";
		String functionId=(String)session.getAttribute("functionId");
		if(StringUtils.isBlank(functionId)){
			functionId="0";
		}
		functionId=functionId.trim();
		request.setAttribute("fieldSetLebel", fieldSetLebel);
		ToDoCaseUploadDAO glDao = (ToDoCaseUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(ToDoCaseUploadDAO.IDENTITY);
		GenericUploadForm  excelForm = (GenericUploadForm ) form;
		ArrayList<GenericUploadVO> list = glDao.getToDoCaseUploadDetails(userId);
		if(list!=null){
			GenericUploadVO vo=list.get(0);
			String batchId=vo.getBatch_id();
			String status=vo.getStatus();
			if(StringUtils.isBlank(batchId)){
				batchId="0";
			}
			batchId=batchId.trim();
			request.setAttribute("batchId",batchId);
			request.setAttribute("searchList", list);
			request.setAttribute("recStatus", status);
			request.setAttribute("size", list.size());
		}else{
			request.setAttribute("batchId","0");
			request.setAttribute("recStatus","I");
		}
		return mapping.findForward("sucesss");
	}
	public ActionForward uploadToDoCaseExcel(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		logger.info("In uploadToDoCaseExcel.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		String strFlag="";	
		String branchId="";
		String userId="";
		String branch="";
		String bussinessDate="";
		if(sessionId!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
			bussinessDate=userobj.getBusinessdate();
		}
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
			bussinessDate=userobj.getBusinessdate();
		}else{
			logger.info("here in uploadToDoCaseAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String fieldSetLebel="ToDo Case Upload";
		String functionId=(String)session.getAttribute("functionId");
		if(StringUtils.isBlank(functionId)){
			functionId="0";
		}
		request.setAttribute("fieldSetLebel", fieldSetLebel);
		ToDoCaseUploadDAO glDao = (ToDoCaseUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(ToDoCaseUploadDAO.IDENTITY);
		GenericUploadForm  excelForm = (GenericUploadForm ) form;
		//INSERT QUERY 
		int uploadId=glDao.saveToDoCaseUpload(userId,bussinessDate);
		String operationMessage="";
		if(uploadId>0){
			boolean uploadStatus=DocUpload.toDoCaseUploadExcel(request,excelForm.getDocFile(),uploadId+"");
			boolean vstatus=false;
			if(uploadStatus){
				String fileName = (String)request.getAttribute("fileName");
				String filePath = (String)request.getAttribute("filePath");
				logger.info("fileName : "+fileName+" filePath : "+filePath);
				// READ UPLOADED FILE
				Map<String,String> readResponse=glDao.fetchSystemOutputSheetToDoCase(uploadId+"",userId,bussinessDate,fileName,filePath,branch);
				String operationStatus=readResponse.get("operationStatus");
				operationMessage=readResponse.get("operationMessage");
				operationMessage=operationMessage.replaceAll("\\'","");
				String noOfRecords=readResponse.get("rowCount");
				String recStatus="E";
				if(StringUtils.equalsIgnoreCase(operationStatus,"1")){
					recStatus="A";
				}
				// UPDATE QUERY
				Map<String,String> parameter=new HashMap<String,String>();
				parameter.put("userId", userId);
				parameter.put("bussinessDate", bussinessDate);
				parameter.put("fileName", fileName);
				parameter.put("filePath", filePath);
				parameter.put("noOfRecords", noOfRecords);
				parameter.put("operationMessage", operationMessage);
				parameter.put("recStatus",recStatus);
				parameter.put("uploadId",uploadId+"");
				boolean updateStatus=glDao.updateToDoCaseUpload(parameter);
				
			}else{
				// UPDATE QUERY
				Map<String,String> parameter=new HashMap<String,String>();
				parameter.put("userId", userId);
				parameter.put("bussinessDate", bussinessDate);
				parameter.put("fileName", "");
				parameter.put("filePath", "");
				parameter.put("noOfRecords", "0");
				parameter.put("operationMessage", "Some unknown error occurred during file upload");
				parameter.put("recStatus", "E");
				parameter.put("uploadId",uploadId+"");
				boolean updateStatus=glDao.updateToDoCaseUpload(parameter);
			}
		}
		if(StringUtils.isBlank(operationMessage)){
			operationMessage="Some unknown error occurred, please contact to admin.";
			// UPDATE QUERY
			Map<String,String> parameter=new HashMap<String,String>();
			parameter.put("userId", userId);
			parameter.put("bussinessDate", bussinessDate);
			parameter.put("fileName", "");
			parameter.put("filePath", "");
			parameter.put("noOfRecords", "0");
			parameter.put("operationMessage",operationMessage);
			parameter.put("recStatus", "E");
			parameter.put("uploadId",uploadId+"");
			boolean updateStatus=glDao.updateToDoCaseUpload(parameter);
		}
		request.setAttribute("operationMessage",operationMessage);
		ArrayList<GenericUploadVO> list = glDao.getToDoCaseUploadDetails(userId);
		if(list!=null){
			GenericUploadVO vo=list.get(0);
			String batchId=vo.getBatch_id();
			String status=vo.getStatus();
			if(StringUtils.isBlank(batchId)){
				batchId="0";
			}
			batchId=batchId.trim();
			request.setAttribute("batchId",batchId);
			request.setAttribute("searchList", list);
			request.setAttribute("size", list.size());
			request.setAttribute("recStatus", status);
		}else{
			request.setAttribute("batchId","0");
			request.setAttribute("recStatus","I");
		}
		return mapping.findForward("sucesss");
	}
	
	public ActionForward downloadToDoCaseExcel(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Connection connectDatabase = null;
		String destinationPath="ToDo_Upload_Data";
		boolean status=false;
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		try{
			connectDatabase = ConnectionDAO.getConnection();
			String path="/reports/MYSQLREPORTS/ToDo_Case_Upload.jasper";
			InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(path);
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			methodForExcel("ToDo_Case_Upload",hashMap,connectDatabase,response, jasperPrint);
		}catch(Exception e){
			status=false;
			e.printStackTrace();
		}finally{
			if(connectDatabase!=null){
				connectDatabase.close();
			}
		}
		return null;
	}
	
	public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
		String excelReportFileName=reportName+".xls";
		JExcelApiExporter exporterXLS = new JExcelApiExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
		response.setContentType("application/vnd.ms-excel");
		exporterXLS.exportReport();
	}
	public static String getCurrentDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date date = new Date(0);
		String currentTime = dateFormat.format(date) ;
		return currentTime;
	}
}
