 package com.cp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.LoanInitiationDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.UploadDocument;
import com.cp.dao.CreditProcessingDAO;
import com.commonFunction.dao.commonDao;
//import com.cp.dao.PartnerCapturingDAO;
import com.cp.dao.DmsCapturingDao;
import com.cp.vo.CollateralVo;
import com.cp.vo.ConsumerVo;
import com.cp.vo.FieldVerificationVo;
import com.cp.vo.PartnerCaptureVO;
import com.cp.vo.UnderwritingDocUploadVo;
//import com.dmsNewgen.DMSUtility;
import com.gcd.VO.ShowCustomerDetailVo;
import com.gcd.dao.CorporateDAO;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.BranchMasterVo;
import org.apache.log4j.Logger;
import  com.cp.dao.DmsCapturingDao;
import com.cp.daoImplMYSQL.DmsCapturingDAOImpl;
import java.io.*;

public class DmsCaptureDispcthAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(DmsCaptureDispcthAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dbType=resource.getString("lbl.dbType");

public ActionForward openDocUpload(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception { 
			
			    logger.info("In CollateralProcessAction(openDocUpload)");
			    HttpSession session = request.getSession();
			    UserObject userobj=(UserObject)session.getAttribute("userobject");
			    String functionId=(String)session.getAttribute("functionId");
			    DynaValidatorForm CommonDealDynaValidatorForm = (DynaValidatorForm)form;
			    PartnerCaptureVO sh = new PartnerCaptureVO();
				org.apache.commons.beanutils.BeanUtils.copyProperties(sh, CommonDealDynaValidatorForm);		
			    String docId=CommonFunction.checkNull(request.getParameter("docId"));
			    session.setAttribute("docId", docId);
			    System.out.println("docId.................."+docId);
			    String bpId=CommonFunction.checkNull(request.getParameter("bpId"));
			    DmsCapturingDAOImpl service;
			   
			    if(CommonFunction.checkNull(bpId).equalsIgnoreCase("")){
			    	if(session.getAttribute("loanId")!=null){
			    		bpId=CommonFunction.checkNull(session.getAttribute("loanId").toString());
			    	}
			    }
			    String userName=userobj.getUserId();
			    System.out.println("bpId.................."+bpId);
			    String bpType=CommonFunction.checkNull(request.getParameter("bpType"));
			    System.out.println("bpType.................."+bpType);
			    session.removeAttribute("DocType");
			    String DocType = (String)request.getParameter("DocType");
			    session.setAttribute("DocType", DocType);
			    
			    session.removeAttribute("entityId");
			    String entityId=CommonFunction.checkNull(request.getParameter("entityId"));
			    session.setAttribute("entityId", entityId);
			    System.out.println("entityId.................."+entityId);
			    session.setAttribute("DocType", DocType);
			    if(CommonFunction.checkNull(docId).equalsIgnoreCase("")){
			    	String docIdQuery="select value from generic_master where generic_key='DMS_DOCS' and parent_value='"+functionId+"' and rec_status='A'";
			    	docId=ConnectionDAO.singleReturn(docIdQuery);
			    }
			   // DmsCapturingDao service=(DmsCapturingDao)DaoImplInstanceFactory.getDaoImplInstance(DmsCapturingDao.IDENTITY);
			    try
			    {
			       service= new  DmsCapturingDAOImpl();
		        //logger.info("openDocUpload: "+service.getClass()); 
			    logger.info("docId..........."+docId);
			    if(CommonFunction.checkNull(docId).equalsIgnoreCase("")){
			    	if(CommonFunction.checkNull(DocType).equalsIgnoreCase("FVI")){
			    		docId="1001";
			    	}else if(CommonFunction.checkNull(DocType).equalsIgnoreCase("FVILM")){
			    		docId="1001";
			    	}else if(CommonFunction.checkNull(DocType).equalsIgnoreCase("RVI")){
			    		docId="1004";
			    	}else if(CommonFunction.checkNull(DocType).equalsIgnoreCase("RVILM")){
			    		docId="1004";
			    	}
			    }
			    request.setAttribute("docId", docId);
			    sh.setDocId(docId);
			    sh.setBpId(bpId);
			    sh.setBpType(bpType);
			    if(CommonFunction.checkNull(DocType).equalsIgnoreCase("")){
					DocType = bpType;
				}
			    sh.setDocType(DocType);
			    sh.setEntityId(entityId);
			    ArrayList<Object> fetchUploadDocument = service.fetchUploadDocDocWise(docId,bpId,bpType,DocType,entityId);
			    /*ArrayList<Object> fetchUploadDocument = service.fetchUploadDoc(docId,bpId,bpType);*/
			    logger.info("fetchUploadDocument"+fetchUploadDocument);
			    request.setAttribute("fetchUploadDocument", fetchUploadDocument);
				/*Check Master Child documents */
			    /*String UploadFlag = (String)request.getParameter("UploadFlag");*/
			    String dmsProviderQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='DMS_PROVIDER'";
				String dmsProvider=ConnectionDAO.singleReturn(dmsProviderQuery);
				request.setAttribute("dmsProvider", dmsProvider);
				//CreditProcessingDAO creditProcessingDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
				//DmsCapturingDAOImpl service=new DmsCapturingDAOImpl();
		       // logger.info("Implementation class: "+service.getClass()); 
				Map<String, String> map =service.getDMSDetails(bpType,bpId,DocType,entityId,userName,docId);
				request.setAttribute("dmsDetailsMap", map);
				Map<String,String> dmsCredential=CommonFunction.getDmsCredential();
				request.setAttribute("dmsCredential", dmsCredential);
			    boolean MasterChildDocs = false;;
			    ArrayList documentStatus=new ArrayList();
			    MasterChildDocs = service.masterChildDocs(sh);
			    if(MasterChildDocs){
			    	if(StringUtils.equals("4000146", functionId)||StringUtils.equals("4000151", functionId)){
			    		
			    		 documentStatus=service.uploadDocumentChildDatafortempTable(sh);
				    	 logger.info("Document Status : "+documentStatus);
				    	 request.setAttribute("documentStatus", documentStatus);
				    	 logger.info("Document Status for screen 4000146:"+documentStatus);
				    	 if(documentStatus.size()>0)
				    		
				    		 request.setAttribute("childAvailble", "childAvailble");
				    	 else  
				    		 request.setAttribute("msg", "childNotFound");
			    		
			    	}else{
			    		
			    		
			    		 documentStatus=service.uploadDocumentChildData(sh);
				    	 logger.info("Document Status : "+documentStatus);
				    	 request.setAttribute("documentStatus", documentStatus);
				    	 logger.info("Document Status  not for screen 4000146: "+documentStatus);
				    	 if(documentStatus.size()>0)
				    		 request.setAttribute("childAvailble", "childAvailble");
				    	 else  
				    		 request.setAttribute("msg", "childNotFound");
			    		
			    	}
			    	
			    	 
			    }
			    String int_ext=service.Internal_ExternalDocument();
				logger.info("int_ext"+int_ext);
				if(CommonFunction.checkNull(int_ext).equalsIgnoreCase("Y"))
					request.setAttribute("InternalDocs", "InternalDocs");
				String stage = "PRS";
				String commonId = bpId;
				ArrayList documents = service.getPartnerDocuments(commonId,stage,bpType);
				logger.info("documents Size: " + documents.size());
				logger.info("functionId: " +functionId);
				request.setAttribute("calDoc", documents.size());
				if(documents.size()>0){
					request.setAttribute("dataFound","dataFound");
				}
				/*if(CommonFunction.checkNull(UploadFlag).equalsIgnoreCase("E")){
					request.setAttribute("Editable","Editable");
				}
				else if(CommonFunction.checkNull(UploadFlag).equalsIgnoreCase("V")){
					request.setAttribute("view","view");
				}
				else if(CommonFunction.checkNull(UploadFlag).equalsIgnoreCase("")){
					request.setAttribute("Editable","Editable");
				}*/
				if(StringUtils.equals("4001231", functionId) || StringUtils.equals("3000951", functionId)||StringUtils.equals("10000832", functionId)||StringUtils.equals("8000311", functionId)||StringUtils.equals("4000111", functionId))
				{
					request.setAttribute("viewDeal", "");
				}
				//Rohit changes for Verification DMS starts
				if(StringUtils.equals("3000226", functionId) || StringUtils.equals("4000134", functionId)
						||StringUtils.equals("10000831", functionId) ||StringUtils.equals("8000242", functionId))
				{
					request.setAttribute("viewDeal", "");
				}
				//Rohit changes ends
			    }catch(Exception e)
			    {e.printStackTrace();}
			    finally
			    {
			    	service=null;
			    }
			    return mapping.findForward("uploadedDocs");
		}
public ActionForward refreshCheckListDMSParentPage(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
{
	logger.info("Inside refreshCheckListDMSParentPage(refreshCheckListDMSParentPage)");
	HttpSession session = request.getSession();
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	String userName="";
	 String functionId = (String)session.getAttribute("functionId");
	DmsCapturingDAOImpl service;
	if(userobj!=null)
		userName=userobj.getUserId();
	else
		return mapping.findForward("sessionOut");
	Object sessionId = session.getAttribute("sessionID");
	ServletContext context = getServlet().getServletContext();
	String strFlag="";	
	
	if(sessionId!=null)
	{
		strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
	}
	if(!"".equalsIgnoreCase(strFlag))
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
	
	String docId="";
	if(session.getAttribute("docId")!=null)
	 docId=CommonFunction.checkNull(session.getAttribute("docId").toString());
	
	String bpId = "";
	if(session.getAttribute("bpId")!=null){
		 bpId = session.getAttribute("bpId").toString();
	}
	
	
	String bpType = "";
	if(session.getAttribute("bpType")!=null){
		bpType = session.getAttribute("bpType").toString();
	}
			
	String checkedvalue=(String) request.getParameter("chk");
	if(CommonFunction.checkNull(bpId).equalsIgnoreCase("")){
		if(session.getAttribute("dealId")!=null){
			bpId = session.getAttribute("dealId").toString();
			bpType = "DC";
		}
	}
	if(CommonFunction.checkNull(bpId).equalsIgnoreCase("")){
		if(session.getAttribute("loanId")!=null){
			bpId = session.getAttribute("loanId").toString();
			bpType = "LIM";
		}
	}
	PartnerCaptureVO vo = new PartnerCaptureVO();
	DynaValidatorForm UnderwriterUploadDynaValidatorForm = (DynaValidatorForm) form;
	org.apache.commons.beanutils.BeanUtils.copyProperties(vo,UnderwriterUploadDynaValidatorForm);
	vo.setBpId(bpId);
	vo.setMakerId(userName);
	vo.setCheckedValue(checkedvalue);
	vo.setDocId(docId);
	
	/*CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	PartnerCapturingDAO service1=(PartnerCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(PartnerCapturingDAO.IDENTITY);*/
	//DmsCapturingDao service=(DmsCapturingDao)DaoImplInstanceFactory.getDaoImplInstance(DmsCapturingDao.IDENTITY);
	try
	{
	 service=new DmsCapturingDAOImpl();
    logger.info("Implementation class: "+service.getClass()); 	//changed by asesh
	boolean uploadStatus = false;
	ArrayList documentStatus=new ArrayList();
	
	ArrayList fetchUploadDocument=new ArrayList();
	String DocType = (String)session.getAttribute("DocType");
	String entityId = (String)session.getAttribute("entityId");
	if(CommonFunction.checkNull(DocType).equalsIgnoreCase("")){
		DocType = bpType;
	}
	if(CommonFunction.checkNull(docId).equalsIgnoreCase("")){
    	String docIdQuery="select value from generic_master where generic_key='DMS_DOCS' and parent_value='"+functionId+"' and rec_status='A'";
    	docId=ConnectionDAO.singleReturn(docIdQuery);
    }
	if(CommonFunction.checkNull(docId).equalsIgnoreCase("")){
    	if(CommonFunction.checkNull(DocType).equalsIgnoreCase("FVI")){
    		docId="1001";
    	}else if(CommonFunction.checkNull(DocType).equalsIgnoreCase("FVILM")){
    		docId="1001";
    	}else if(CommonFunction.checkNull(DocType).equalsIgnoreCase("RVI")){
    		docId="1004";
    	}else if(CommonFunction.checkNull(DocType).equalsIgnoreCase("RVILM")){
    		docId="1004";
    	}
    }
	fetchUploadDocument = service.fetchUploadDocDocWise(docId,bpId,bpType,DocType,entityId);
	if(fetchUploadDocument.size()>0){
		request.setAttribute("fetchUploadDocument", fetchUploadDocument);
	}
	vo.setDocType(DocType);
	vo.setBpType(bpType);
	vo.setEntityId(entityId);
	documentStatus=service.uploadDocumentChildData(vo);
	logger.info("Document Status : "+documentStatus);
	request.setAttribute("documentStatus", documentStatus);
	String int_ext=service.Internal_ExternalDocument();
	logger.info("int_ext"+int_ext);
	if(documentStatus.size()>0)
		request.setAttribute("childAvailble", "childAvailble"); 
	if(CommonFunction.checkNull(int_ext).equalsIgnoreCase("Y"))
	request.setAttribute("InternalDocs", "InternalDocs");
	request.setAttribute("Editable","Editable");
	
	String dmsProviderQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='DMS_PROVIDER'";
	String dmsProvider=ConnectionDAO.singleReturn(dmsProviderQuery);
	request.setAttribute("dmsProvider", dmsProvider);
	//CreditProcessingDAO creditProcessingDAO=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	logger.info("Implementation class: "+service.getClass()); 
	Map<String, String> map =service.getDMSDetails(bpType,bpId,DocType,entityId,userName,docId);
	request.setAttribute("dmsDetailsMap", map);
	Map<String,String> dmsCredential=CommonFunction.getDmsCredential();
	request.setAttribute("dmsCredential", dmsCredential);
	service=null;
	vo=null;
	userName=null;
	strFlag=null;
	dmsCredential=null;
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		service=null;
	}
	return mapping.findForward("DocUploaded");
  }
}

