package com.masters.actions;


import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.cm.dao.ReportsDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ScoringBenchmarkMasterVo;

public class ScoringBenchmarkMasterDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ScoringBenchmarkMasterDispatchAction.class.getName());
	
	public ActionForward saveScoringDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		{ServletContext context = getServlet().getServletContext();
			//HttpSession session=request.getSession(false);
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			DynaValidatorForm ScoringBenchmarkSearchBehindAction= (DynaValidatorForm)form;
			ScoringBenchmarkMasterVo vo = new ScoringBenchmarkMasterVo();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ScoringBenchmarkSearchBehindAction);
		
			//for check User session start
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
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);
			logger.info("Inside ScoringBenchmarkMasterDispatchAction ..........");
			
        CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
        
		String sms="";
		logger.info("dataType::::"+ vo.getDataType());
		logger.info("ScorecardID::::"+ vo.getLbxScorecardID());
		logger.info("IndustryID::::"+ vo.getLbxIndustryID());
		logger.info("Weightage::::"+ vo.getWeightage());
		logger.info("Inside ScoringBenchmarkMasterDispatchAction.....NumericValueFrom..."+vo.getNumericValueFrom());
		logger.info("Inside ScoringBenchmarkMasterDispatchAction.....NumericValueTO...."+vo.getNumericValueTo());
		logger.info("Inside ScoringBenchmarkMasterDispatchAction.....CharacterValue...."+vo.getCharacterValue());
		logger.info("Inside ScoringBenchmarkMasterDispatchAction.....Rating...."+vo.getRating());
		boolean status = cpm.saveScoringDetailsMaster(vo);
		
		logger.info("status"+status);
		
		if(status)
		{
	        request.setAttribute("saveData","save");
	        request.setAttribute("search", cpm.getsearchScoringDetails(vo));
	        request.setAttribute("lbxIndustryID",vo.getLbxIndustryID());
			request.setAttribute("industry",vo.getIndustry());
			request.setAttribute("scorecardID",vo.getLbxScorecardID());
			request.setAttribute("scorecard",vo.getScorecard());
			request.setAttribute("weightage",vo.getWeightage());
			request.setAttribute("dataType",vo.getDataType());
			String dataType=vo.getDataType();
			if(CommonFunction.checkNull(dataType).trim().equalsIgnoreCase("A"))
			request.setAttribute("A","A");
		}
		return mapping.findForward("save");
		}
}
	
	public ActionForward searchScoringDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		{
			ServletContext context = getServlet().getServletContext();
			
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
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
			String userId="";
			String bDate="";
			if(userobj!=null)
			{
					userId=userobj.getUserId();
					bDate=userobj.getBusinessdate();
			}
			
			DynaValidatorForm ScoringBenchmarkMasterDyanavalidatiorForm= (DynaValidatorForm)form;
			ScoringBenchmarkMasterVo vo = new ScoringBenchmarkMasterVo();
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ScoringBenchmarkMasterDyanavalidatiorForm);	
		
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);
			
			logger.info("Inside ScoringBenchmarkMasterDispatchAction ..........");			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			request.setAttribute("search", cpm.getsearchScoringDetails(vo));
			
			request.setAttribute("lbxIndustryID",vo.getLbxIndustryID());
			request.setAttribute("industry",vo.getIndustry());
			request.setAttribute("scorecardID",vo.getLbxScorecardID());
			request.setAttribute("scorecard",vo.getScorecard());
			request.setAttribute("weightage",vo.getWeightage());
			request.setAttribute("dataType",vo.getDataType());
			
			String dataType=vo.getDataType();
			if(CommonFunction.checkNull(dataType).trim().equalsIgnoreCase("A"))
			request.setAttribute("A","A");
			//request.setAttribute("search","search");

		
		//request.setAttribute("list", "list");
		return mapping.findForward("search");
		}
		
}
	public ActionForward scoreCardView(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info("In scoreCardView");		
		HttpSession session =  request.getSession();
    	UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info(" in scoreCardView action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");		
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		String reportName="MMCAMScoreCardViewRpt";
		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			reportPath=reportPath+"MSSQLREPORTS/camSubReports/";
			SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\camSubReports\\";
		}
		else
		{
			reportPath=reportPath+"MYSQLREPORTS/camSubReports/";
			SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\camSubReports\\";
		}
		
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();		
	
		String industry=CommonFunction.checkNull(request.getParameter("industry")).trim();
			
		hashMap.put("value_Chain_Id",industry );
		hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR );
		hashMap.put("IS_IGNORE_PAGINATION",true);	
		
		logger.info("report Name  :  "+ reportName + ".jasper");
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDAO.closeConnection(connectDatabase, null);
			hashMap.clear();			
		}
		return null;	
	}
	public  void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		
		PrintWriter out=response.getWriter();
	    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
	   	response.setContentType("text/html");
	   	
		String htmlReportFileName=reportName+".html";
		JRHtmlExporter exporter = new JRHtmlExporter();
		
		response.setContentType("text/html");
        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
		
		float f1=1.2f;
		 Map imagesMap = new HashMap();
        request.getSession().setAttribute("IMAGES_MAP", imagesMap);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
       exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
       exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
       exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
       exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
       exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
       exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
       exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
       exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
       ServletContext context = this.getServlet().getServletContext();
       File reportFile = new File(context.getRealPath("/reports/"));
       String image = reportFile.getPath();
       exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,image);
       exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,image + "/");
       exporter.exportReport();     
		
			
	}
	
}


