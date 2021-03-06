/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.io.IOException;
import java.io.InputStream;
import com.caps.VO.ContactRecordingSearchVO;
import com.caps.dao.CollDAO;
import com.cm.dao.ReportsDAO;
import com.communication.engn.servlets.CommMessageData;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CommonDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import com.connect.UploadDocument;

/**
 * MyEclipse Struts Creation date: 05-13-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/commondeal" name="CommonDealDynaValidatorForm"
 *                input="/JSP/CPJSP/commonDeal.jsp" parameter="method"
 *                scope="request" validate="true"
 */
public class CommondealAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CommondealAction.class.getName());
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward searchDealCapturing(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In CommondealAction(searchDealCapturing)");
		HttpSession session = request.getSession();
		String userId=null;
		String branch=null;
		String bDate=null;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in searchDealCapturing method of CommondealAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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

		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;

		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
		CommonDealVo vo = new CommonDealVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		String stage = CommonFunction.checkNull(request.getParameter("stage"));
		//logger.info("stage: " + stage);
		String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
		logger.info("INSIDE searchListUnderWriter Function Id =="+functionId);
		
		vo.setStage(stage);
		vo.setUserId(userId);
		vo.setBranchId(branch);
		vo.setBusinessdate(bDate);
		vo.setFunctionId(functionId);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		else
		{
			vo.setReportingToUserId(vo.getReportingToUserId());
		}
		String uwSearchUser=vo.getReportingToUserId();
		session.setAttribute("uwSearchUser",uwSearchUser);		
		
		//logger.info("user Id:::::"+vo.getReportingToUserId());
		if (vo.getApplicationdate().equalsIgnoreCase(dateFormat)) {
			vo.setApplicationdate("");
		}
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDAO.getClass()); 			// changed by asesh
		//CreditProcessingDAO creditDAO = new CreditProcessingDAOImpl();
		ArrayList dealdetails=new ArrayList();
		
	//	logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
	//	logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		dealdetails= creditDAO.fetchDealDetail(vo,functionId);

	    //logger.info("In searchDealCapturing....list: "+dealdetails.size());
		
	    request.setAttribute("list", dealdetails);
		
		//logger.info("list.isEmpty(): "+dealdetails.size());
		
		request.setAttribute("dealdetails", dealdetails);
	
		//	Himanshu Verma		Changes started for new button at deal capturing
		String preModuleQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PRE_DEAL_MODULE'";
		String preModuleQueryResult=ConnectionDAO.singleReturn(preModuleQuery);
		request.setAttribute("preDealModuleExists", preModuleQueryResult);
		//	Himanshu Verma		Changes ended for new button at deal capturing
		
		if (stage.equalsIgnoreCase("F")) {
			 String customerExposureRequestedLoanAmountQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='CUSTOMER_EXPOSURE_REQUESTED_LOAN_AMOUNT'";
			 String customerExposureRequestedLoanAmount=ConnectionDAO.singleReturn(customerExposureRequestedLoanAmountQuery);
			// logger.info("In fetchDealDetail customerExposureRequestedLoanAmountQuery: "+customerExposureRequestedLoanAmountQuery+"     customerExposureRequestedLoanAmount  "+customerExposureRequestedLoanAmount);
	         request.setAttribute("customerExposureRequestedLoanAmount", customerExposureRequestedLoanAmount);
			//logger.info("list.isEmpty() In F: "+dealdetails.size());
			session.removeAttribute("dealId");
			CommonDealDynaValidatorForm.reset(mapping, request);
			stage=null;
			userId=null;
			 branch=null;
			 bDate=null;
			vo=null;
			creditDAO=null;
			resource=null;
			dateFormat=null;
			customerExposureRequestedLoanAmountQuery=null;
			return mapping.findForward("UnderSerach");
		} else {
			stage=null;
			userId=null;
			 branch=null;
			 bDate=null;
			vo=null;
			resource=null;
			creditDAO=null;
			dateFormat=null;
			CommonDealDynaValidatorForm.reset(mapping, request);
			return mapping.findForward("DealSerach");
		}
}
	
	
	 public ActionForward searchDealLnik(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		logger.info("In CommondealAction(searchDealLnik)");
		String userId=null;
		String branch=null;
		String bDate=null;
		UserObject userobj=null;
		DynaValidatorForm CommonDealDynaValidatorForm = null;	
		ResourceBundle resource = null;
		String dateFormat = null;
		CommonDealVo vo = null;
		CreditProcessingDAO creditDAO=null;
		ArrayList dealdetails=new ArrayList();
		
		try
		{
			
			HttpSession session = request.getSession();					
			session.removeAttribute("strFlagDV");
			userobj=(UserObject)session.getAttribute("userobject");			
			if(userobj!=null)
			{	userId=userobj.getUserId();
				branch=userobj.getBranchId();
				bDate=userobj.getBusinessdate();
			}
			else
			{				
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag="";	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
			
			session.setAttribute("rmChBranchId", branch);
			session.removeAttribute("viewBlackList");
			CommonDealDynaValidatorForm = (DynaValidatorForm) form;	
			resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
			vo = new CommonDealVo();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
			String stage = "P";
			vo.setStage(stage);
			vo.setBranchId(branch);
			vo.setBusinessdate(bDate);
			if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
				vo.setReportingToUserId(userId);
			
			if (vo.getApplicationdate().equalsIgnoreCase(dateFormat)) 
			{
				vo.setApplicationdate("");
			}
			creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+creditDAO.getClass()); 			// changed by asesh
			
			int currentPageLink = 0;
			if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
			}
			vo.setCurrentPageLink(currentPageLink);
			vo.setUserId(userId);			String functionId="";
			if(session.getAttribute("functionId")!=null)
			{
				functionId=session.getAttribute("functionId").toString();
			}
			if(functionId.equals(""))
			{
				session.setAttribute("pre", "pre");
				
			}
			
			vo.setCurrentPageLink(currentPageLink);
			vo.setUserId(userId);
			dealdetails= creditDAO.fetchDealDetail(vo,functionId);
		    request.setAttribute("list", dealdetails);
			request.setAttribute("dealdetails", dealdetails);
			
			//	Himanshu Verma		Changes started for new button at deal capturing
			if (!functionId.equalsIgnoreCase("8000312"))
			{
				String preModuleQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PRE_DEAL_MODULE'";
				String preModuleQueryResult=ConnectionDAO.singleReturn(preModuleQuery);
				request.setAttribute("preDealModuleExists", preModuleQueryResult);
			} else {
				request.setAttribute("preDealModuleExists", "N");	
			}
			//	Himanshu Verma		Changes ended for new button at deal capturing
			
			
			logger.info("stage: "+stage);
			if (stage.equalsIgnoreCase("F")) {
				
				vo=null;
				creditDAO=null;
				resource=null;
				dateFormat=null;
				userId=null;
				 branch=null;
				 bDate=null;
				 CommonDealDynaValidatorForm.reset(mapping, request);
				return mapping.findForward("UnderSerach");
			} else {
				vo=null;
				creditDAO=null;
				resource=null;
				dateFormat=null;
				userId=null;
				 branch=null;
				 bDate=null;
				 CommonDealDynaValidatorForm.reset(mapping, request);
				return mapping.findForward("DealSerach");
			}
		 }
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			userId=null;
			branch=null;
			bDate=null;
			userobj=null;
			CommonDealDynaValidatorForm = null;	
			resource = null;
			dateFormat = null;
			vo = null;
			creditDAO=null;
		
		}
}
	
	
	public ActionForward searchListUnderWriter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			
	{

		logger.info("In CommondealAction(searchListUnderWriter)");
		HttpSession session = request.getSession();
		
		String userId=null;
		String branch=null;
		String bDate=null;
		session.removeAttribute("strFlagDV");
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in searchListUnderWriter method of CommondealAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
		logger.info("INSIDE searchListUnderWriter Function Id =="+functionId);	
		
		session.removeAttribute("countFundFlow");
		session.removeAttribute("countFinancialAnalysis");
		session.removeAttribute("countRepayType");
		DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm) form;// TODO
	
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateFormat(dd-mm-yyyy)");
		CommonDealVo vo = new CommonDealVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CommonDealDynaValidatorForm);
		String stage = "F";
		//logger.info("stage: " + stage);
		vo.setStage(stage);
		vo.setBranchId(branch);
		vo.setBusinessdate(bDate);
		vo.setFunctionId(functionId);
		/*if(session.getAttribute("branchId")!=null)
		{
			session.removeAttribute("branchId");
		}
		else
		{
			session.setAttribute("branchId", branch);
		}*/
		session.setAttribute("branchId", branch);
		session.setAttribute("userId", userId);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		//logger.info("user Id:::::"+vo.getReportingToUserId());
		session.removeAttribute("viewDeal");
		
		if (vo.getApplicationdate().equalsIgnoreCase(dateFormat))
		{
			vo.setApplicationdate("");
		}
		CreditProcessingDAO creditDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditDAO.getClass()); 			// changed by asesh
		ArrayList dealdetails=new ArrayList();
		
		//logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		//logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		vo.setUserId(userId);
	
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		dealdetails= creditDAO.fetchDealDetail(vo,functionId);

	   // logger.info("In searchDealCapturing....list: "+dealdetails.size());
		
	    request.setAttribute("list", dealdetails);
		
		//logger.info("list.isEmpty(): "+dealdetails.size());
		
		 request.setAttribute("dealdetails", dealdetails);
		 String customerExposureRequestedLoanAmountQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='CUSTOMER_EXPOSURE_REQUESTED_LOAN_AMOUNT'";
		 String customerExposureRequestedLoanAmount=ConnectionDAO.singleReturn(customerExposureRequestedLoanAmountQuery);
		 logger.info("In fetchDealDetail customerExposureRequestedLoanAmountQuery: "+customerExposureRequestedLoanAmountQuery+"     customerExposureRequestedLoanAmount  "+customerExposureRequestedLoanAmount);
         request.setAttribute("customerExposureRequestedLoanAmount", customerExposureRequestedLoanAmount);
         customerExposureRequestedLoanAmountQuery=null;
         logger.info("stage:  "+stage);
         session.removeAttribute("businessList");
		if (stage.equalsIgnoreCase("F")) 
		{
			vo=null;
			creditDAO=null;
			resource=null;
			dateFormat=null;
			userId=null;
			 branch=null;
			 bDate=null;
			 CommonDealDynaValidatorForm.reset(mapping, request);
			
			return mapping.findForward("UnderSerach");
		} 
		else 
		{
			vo=null;
			creditDAO=null;
			resource=null;
			dateFormat=null;
			userId=null;
			 branch=null;
			 bDate=null;
			 CommonDealDynaValidatorForm.reset(mapping, request);
			return mapping.findForward("DealSerach");
		}
		
	}
	
	//added by ranjeet singh
	
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branch="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branch=userobj.getBranchId();
		}else{
			logger.info("here in execute  method of downloadFile action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		boolean flag=false;
		
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";

		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		String lbxDocId = request.getParameter("lbxDocId");
		if(StringUtils.isBlank(lbxDocId)){
			lbxDocId="0";
		}
		lbxDocId=lbxDocId.trim();
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		Map<String,String> map = service.getFileInfo(lbxDocId);
		String downloadPath=map.get("DOCUMENT_PATH");
		String fileName=map.get("FILE_NAME");
		UploadDocument.downloadFileAll(request, response,context,downloadPath +File.separator+ fileName);
		return null;
	}
public void downloadApplicationForm(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
{
	try{
		String applicationReport="";
		String operationStatus="0";
		String message="Some error occurred.";
		String salesLeadId= request.getParameter("salesLeadId");
		String pathQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UNDERWRITER_UPLOAD'";
		String documentPath= "";
		String fileServerPath=ConnectionDAO.singleReturn(pathQuery);
		logger.info("In Application Form pathQuery: "+pathQuery+" fileServerPath  "+fileServerPath);
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String p_satin_hindi_declaration=getServlet().getServletContext().getRealPath("/")+"reports/satin_hindi_declaration.jpg";
		String companyNameQuery=" SELECT COMPANY_DESC FROM COM_COMPANY_M LIMIT 1 ";
		String p_company_name=ConnectionDAO.singleReturn(companyNameQuery);
		String applicationFormPath=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\";
		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\";
		String p_stage="STAGE";
		String currentTime=getCurrentDateTime();
		String replaceTimeStamp =currentTime.replaceAll("[-:\\s]", "");
		String p_checkbox_path=getServlet().getServletContext().getRealPath("/")+"reports//";
		String showInsuranceForm="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='MOBILE_SHOWINSURENCE'";
		String  showInsuranceTypeFlag= ConnectionDAO.singleReturn(showInsuranceForm);
		logger.info("In Application Form showInsuranceType: "+showInsuranceForm+" showInsuranceTypeFlag  "+showInsuranceTypeFlag);
		boolean status = generateApplicationForm(salesLeadId, fileServerPath, p_company_logo, p_company_name,applicationFormPath,SUBREPORT_DIR,p_stage,replaceTimeStamp,p_checkbox_path,p_satin_hindi_declaration,showInsuranceTypeFlag);
		if(status){
			documentPath=fileServerPath+"/Application_Form/"+salesLeadId+"_"+replaceTimeStamp+".pdf";
			File applicationForm = new File(documentPath);
			try{
				applicationReport = readDataAsBase64String(applicationForm);
				}catch (IOException e) {
					e.printStackTrace();
				}
				operationStatus="1";
				message="Success";
				}
		String operationResponse="{\"operationStatus\":\""+operationStatus+"\",\"message\":\""+message+"\",\"applicationForm\":\""+applicationReport+"\"}";
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject jsonObject = JSONObject.fromObject(operationResponse, jsonConfig);
		response.getWriter().write(jsonObject.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
}
public boolean generateApplicationForm(String salesLeadId,String fileServerPath,String logo,String p_company_name,String applicationFormPath,String SUBREPORT_DIR,String p_stage,String replaceTimeStamp,String p_checkbox_path,String satinHindiDeclaration,String showInsuranceTypeFlag)throws Exception{
	Connection connectDatabase = null;
	String destinationPath="Application_Form";
	boolean status=false;
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
	hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
	hashMap.put("p_company_logo", logo);
	hashMap.put("p_deal_id", salesLeadId);
	hashMap.put("p_company_name", p_company_name);
	hashMap.put("p_stage", p_stage);
	hashMap.put("p_checkbox_path", p_checkbox_path);
	hashMap.put("p_satin_hindi_declaration", satinHindiDeclaration);
	hashMap.put("p_show_insurance_form", showInsuranceTypeFlag);
	try{
		String reportTypeQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='APPLICATION_FORM_TYPE'";
		String report=ConnectionDAO.singleReturn(reportTypeQuery);
		logger.info("In Application Form reportTypeQuery: "+reportTypeQuery+" report  "+report);
		connectDatabase = ConnectionDAO.getConnection();
		File pathFile = new File(fileServerPath+"/"+destinationPath);
		pathFile.mkdirs();
		JasperPrint jasperPrint = null;
		if(StringUtils.equalsIgnoreCase(report, "ART")){
		jasperPrint = JasperFillManager.fillReport(applicationFormPath+"/LOAN_APPLICATION_FORM.jasper", hashMap,connectDatabase);
		}else if (StringUtils.equalsIgnoreCase(report, "SATIN")){
		jasperPrint = JasperFillManager.fillReport(applicationFormPath+"/Loan_Application_Form_Satin.jasper", hashMap,connectDatabase);	
		}
		JasperExportManager.exportReportToPdfFile(jasperPrint,fileServerPath+"/"+destinationPath+"/"+salesLeadId+"_"+replaceTimeStamp+".pdf");
		status=true;
		//connectDatabase.commit();
		}catch(Exception e){
			status=false;
			e.printStackTrace();
			connectDatabase.rollback();
		}finally{
			connectDatabase.close();
			hashMap.clear();
		}
	return status;
}
public static String getCurrentDateTime(){
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	Date date = new Date();
	String currentTime = dateFormat.format(date) ;
	return currentTime;
}
public static String readDataAsBase64String(File file) throws IOException {
		String stream="";
		byte[] buffer = new byte[(int) file.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(file);
			if (ios.read(buffer) == -1) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
			stream=new String(Base64.encodeBase64(buffer));
			}finally{
				try{
					if (ios != null)
					ios.close();
					}catch (IOException e) {
					}
				}
		return stream;
	}
public void downloadInterestCertificate(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
	try{
		String report="";
		String operationStatus="0";
		String message="Some error occurred.";
		String loanId= request.getParameter("loanId");
		String pathQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UNDERWRITER_UPLOAD'";
		String documentPath= "";
		String fileServerPath=ConnectionDAO.singleReturn(pathQuery);
		logger.info("In downloadCamReport pathQuery: "+pathQuery+"     fileServerPath  "+fileServerPath);
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String companyNameQuery=" SELECT COMPANY_DESC FROM COM_COMPANY_M LIMIT 1 ";
		String p_company_name=ConnectionDAO.singleReturn(companyNameQuery);
		String interestCertificateReportPath=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\";
		String customer_detail_location=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\loanSubReports\\";
		boolean status = generateInterestCertificateReport(loanId, fileServerPath, p_company_logo, p_company_name,customer_detail_location,interestCertificateReportPath);
		if(status){
			documentPath=fileServerPath+"/INEREST_CERTIFICATE/"+loanId+".pdf";
			File file1 = new File(documentPath);
			
			try{
				report = readDataAsBase64String(file1);
			}catch (IOException e) {
				e.printStackTrace();
}
			operationStatus="1";
			message="Success";
		}
		String operationResponse="{\"operationStatus\":\""+operationStatus+"\",\"message\":\""+message+"\",\"report\":\""+report+"\"}";
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject jsonObject = JSONObject.fromObject(operationResponse, jsonConfig);
		response.getWriter().write(jsonObject.toString());
	}catch(Exception e)
	{
		e.printStackTrace();
	}
} 
public boolean generateInterestCertificateReport(String loanId,String fileServerPath,String logo,String companyName,String customerDetailLocation,String interestCertificateReportPath)throws Exception{
	Connection connectDatabase = null;
	String destinationPath="INEREST_CERTIFICATE";
	boolean status=false;
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
	hashMap.put("p_company_logo",logo);
	hashMap.put("loan_id",loanId);
	hashMap.put("p_company_name",companyName);
	hashMap.put("customer_detail_location",customerDetailLocation);
	try{
		connectDatabase = ConnectionDAO.getConnection();
		File pathFile = new File(fileServerPath+"/"+destinationPath);
		pathFile.mkdirs();
		JasperPrint jasperPrint = null;
		jasperPrint = JasperFillManager.fillReport(interestCertificateReportPath+"/interest_certificate.jasper", hashMap,connectDatabase);
		JasperExportManager.exportReportToPdfFile(jasperPrint,fileServerPath+"/"+destinationPath+"/"+loanId+".pdf");
		status=true;
		//connectDatabase.commit();
	}catch(Exception e){
		status=false;
		e.printStackTrace();
		connectDatabase.rollback();
	}
	finally{
		connectDatabase.close();
		hashMap.clear();
	}
	return status;
}

public void downloadMITCReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
	try{
		String mitcReport="";
		String operationStatus="0";
		String message="Some error occurred.";
		String dealId= request.getParameter("dealNumber");
		String pathQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UNDERWRITER_UPLOAD'";
		String documentPath= "";
		String fileServerPath=ConnectionDAO.singleReturn(pathQuery);
		logger.info("In downloadCamReport pathQuery: "+pathQuery+" fileServerPath  "+fileServerPath);
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String companyNameQuery=" SELECT COMPANY_DESC FROM COM_COMPANY_M LIMIT 1 ";
		String p_company_name=ConnectionDAO.singleReturn(companyNameQuery);
		String mitcReportPath=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\";
		String customer_detail_location=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\dealSubReports\\";
		boolean status = generateMITCReport(dealId, fileServerPath, p_company_logo, p_company_name,customer_detail_location,mitcReportPath);
		if(status){
			documentPath=fileServerPath+"/MITC_REPORT/"+dealId+".pdf";
			File file1 = new File(documentPath);
			try{
				mitcReport = readDataAsBase64String(file1);
				}catch (IOException e) {
					e.printStackTrace();
				}
				operationStatus="1";
				message="Success";
				}
		String operationResponse="{\"operationStatus\":\""+operationStatus+"\",\"message\":\""+message+"\",\"mitcReport\":\""+mitcReport+"\"}";
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject jsonObject = JSONObject.fromObject(operationResponse, jsonConfig);
		response.getWriter().write(jsonObject.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
}
public boolean generateMITCReport(String dealId,String fileServerPath,String logo,String companyName,String customerDetailLocation,String mitcReportPath)throws Exception{
	Connection connectDatabase = null;
	String destinationPath="MITC_REPORT";
	boolean status=false;
	Map<Object,Object> hashMap = new HashMap<Object,Object>();
	hashMap.put("p_company_logo",logo);
	hashMap.put("deal_id",dealId);
	hashMap.put("p_company_name",companyName);
	hashMap.put("customer_detail_location",customerDetailLocation);
	try{
		connectDatabase = ConnectionDAO.getConnection();
		File pathFile = new File(fileServerPath+"/"+destinationPath);
		pathFile.mkdirs();
		JasperPrint jasperPrint = null;
		jasperPrint = JasperFillManager.fillReport(mitcReportPath+"/mitc_report.jasper", hashMap,connectDatabase);
		JasperExportManager.exportReportToPdfFile(jasperPrint,fileServerPath+"/"+destinationPath+"/"+dealId+".pdf");
		status=true;
		//connectDatabase.commit();
		}catch(Exception e){
			status=false;
			e.printStackTrace();
			connectDatabase.rollback();
			}finally{
			connectDatabase.close();
			hashMap.clear();
			}
	return status;
}


public void setPayInSlipDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
	logger.info("In setPayInSlipDetails. ");
	int sessionCheck=0;
	HttpSession session = request.getSession();
	UserObject userobj = (UserObject) session.getAttribute("userobject");
	String userId="";
	String bDate="";
	String branchId=null;
	if(userobj!=null)
	{
		userId=userobj.getUserId();
		bDate=userobj.getBusinessdate();
		branchId= userobj.getBranchId();
	}else{
		logger.info("here in setPayInSlipDetails method of CommondealAction out----------------");
		sessionCheck=1;
	}
	String sessionId = session.getAttribute("sessionID").toString();
	ServletContext context = getServlet().getServletContext();
	String strFlag="";	
	if(sessionId!=null)
	{
		strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
	}
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
		sessionCheck=1;
	}
	StringBuilder jsonStr = new StringBuilder();
	if(sessionCheck==0){
		String transactionType=request.getParameter("transactionType");
		String receiptSource=request.getParameter("receiptSource");
		String payInSlipUploadDate=request.getParameter("payInSlipUploadDate");
		if(StringUtils.isBlank(payInSlipUploadDate)){
			payInSlipUploadDate="0";
		}
		payInSlipUploadDate=payInSlipUploadDate.trim();
		
		String loanAccNo=request.getParameter("loanAccNo");
		if(StringUtils.isBlank(loanAccNo)){
			loanAccNo="0";
		}
		loanAccNo=loanAccNo.trim();
		
		session.setAttribute("transactionType",transactionType);
		session.setAttribute("receiptSource",receiptSource);
		session.setAttribute("payInSlipUploadDate",payInSlipUploadDate);
		session.setAttribute("loanAccNo",loanAccNo);
		jsonStr.append("{");
		jsonStr.append("\"OPERATION_STATUS\":\"1\"");
		jsonStr.append(",\"OPERATION_MESSAGE\":\"Operation Completed Successfully.\"");
		jsonStr.append("}");
	}else{
		jsonStr.append("{");
		jsonStr.append("\"OPERATION_STATUS\":\"0\"");
		jsonStr.append(",\"OPERATION_MESSAGE\":\"Login Session has been expire.\"");
		jsonStr.append(",\"PD_FINANCIAL_ANALYSIS_ID\":\"0\"");
		jsonStr.append("}");
	}
	response.getWriter().write(jsonStr.toString());
}
}
