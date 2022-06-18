package com.cm.actions;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.connect.CommonFunction;




import com.cm.dao.LosUploadDAO;
import com.cm.vo.LosUploadVO;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LosUploadDispatchAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(LosUploadDispatchAction.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");

	public ActionForward validateData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		logger.info(" Virender :validateData method of LosUploadDispatchAction");
		String user_id="";
		String bus_date="";
		String validateLOSUpload="";
		String valPos="";
		String valInfo="";
		String validateMsg="";

		HttpSession session = request.getSession();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  validateData method of  LosUploadDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		} else {
			user_id=userobj.getUserId();
			bus_date=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj,
					sessionId.toString(), "", request);
		}

		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		session.removeAttribute("Processfile");

		LosUploadVO  losUploadVO=new LosUploadVO();
		DynaValidatorForm manualDeviationDynaValidatorForm=(DynaValidatorForm)form;	    
		org.apache.commons.beanutils.BeanUtils.copyProperties(losUploadVO,manualDeviationDynaValidatorForm);

		LosUploadDAO service = (LosUploadDAO) DaoImplInstanceFactory.getDaoImplInstance(LosUploadDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass());

		String sms = "";
		boolean uploadStatus=false;	

		uploadStatus=service.whereToUpload(request,losUploadVO.getDocFile());			
		losUploadVO.setFileName(request.getAttribute("fileName").toString());
		losUploadVO.setDocPath(request.getAttribute("filePath").toString());
		String flag=(String)request.getAttribute("message");

		logger.info("In validateData() : flag:==>"+flag);
		logger.info("In validateData() : uploadStatus:==>>"+uploadStatus);
		if (uploadStatus) {
			int count = service.countLine(losUploadVO.getDocFile().toString());
			logger.info("In validateData() : count:==>>" + count + "losUploadVO.getDocFile().toString():::::::"+ losUploadVO.getDocFile().toString());
			if (count > 1000) {
				request.setAttribute("maxCount", "");
				logger.info("....In UpLoadDispatchAction..Total Line.." + count);
			} 
			else {
				// for start process file name ..
				session.setAttribute("Processfile", losUploadVO.getDocFile().toString());
			}
		}
		String file_name = (String) session.getAttribute("Processfile");
		validateMsg = service.validateDataLOSUpload(request, response,file_name, user_id, bus_date);

		String valMsg[]=validateMsg.split("~");
		validateLOSUpload=valMsg[0];
		if(valMsg.length>1)
		valPos=valMsg[1];
		if(valMsg.length>1)
		valInfo=valMsg[2];
		
		/*String 	=(String)request.getAttribute("ValMsg");
		//session.setAttribute("InfoMsg",sheetInfoMsg);*/
		

		if (flag == "O" && validateLOSUpload.equals("S")) {
			request.setAttribute("sms","");

		} else if (flag == "O" && validateLOSUpload.equals("F")) {
			String smsfail = "F";
			request.setAttribute("smsfail",smsfail);
			session.setAttribute("valPos",valPos);
			session.setAttribute("valInfo", valInfo);
			session.removeAttribute("Processfile");
		}
		if (flag == "E") {
			request.setAttribute("smsno", "");
		}
		request.setAttribute("uploadFlag", "N");

		return mapping.findForward("success");
	}

	public ActionForward startProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		logger.info(" in startProcess method of LosUploadDispatchAction:::::::::::::::::::::::");
		String user_id="";
		String maker_date="";
		String userBranch = "";
		HttpSession session = request.getSession();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in  validateData method of  LosUploadDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}else{
			user_id=userobj.getUserId();
			maker_date=userobj.getBusinessdate();
			userBranch=userobj.getBranchId();
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj,
					sessionId.toString(), "", request);
		}

		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		LosUploadVO losUploadVO = new LosUploadVO();
		DynaValidatorForm manualDeviationDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(losUploadVO,
				manualDeviationDynaValidatorForm);

		LosUploadDAO service = (LosUploadDAO) DaoImplInstanceFactory
				.getDaoImplInstance(LosUploadDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass());

		String file=(String)session.getAttribute("Processfile");
		logger.info("startProcess() : file:==>>"+file);

		boolean status = service.readExcelforLOSUpload(request, response, file,
				user_id, maker_date,userBranch);
		logger.info(" LosUpload:startProcess():status:==>> " + status);
		if (status) {

			request.setAttribute("inserted", "Done");

		} else {

			request.setAttribute("noinserted", "Done");

		}

		session.removeAttribute("Processfile");		
		request.setAttribute("uploadFlag", "Y");

		return mapping.findForward("success");
	}

	
	
	public void errorLogDownload(ActionMapping mapping,	ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		HttpSession so=request.getSession();
		String pstr=(String)so.getAttribute("valPos");
		String istr=(String)so.getAttribute("valInfo");
		ServletOutputStream out = response.getOutputStream();
		out.write((pstr).getBytes());
		out.write('\n');
		out.write('\n');
		out.write((istr).getBytes());
		response.setContentType("text/plain"); 
		response.setHeader("Content-Disposition", "attachment; filename=ErrorReports.txt");
	}

	public void xlsFileDownload(ActionMapping mapping,	ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		LosUploadDAO service = (LosUploadDAO) DaoImplInstanceFactory.getDaoImplInstance(LosUploadDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass());
		String filePath = service.downloadExcelFile( request,response,"");
		if(!filePath.equalsIgnoreCase("")){
			System.out.println("file download Successfull"+filePath);
		}else{
			System.out.println("file not generated");
		}

	}
}
