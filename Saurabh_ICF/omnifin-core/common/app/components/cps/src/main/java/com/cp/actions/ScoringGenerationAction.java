package com.cp.actions;

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

import com.cm.dao.ReportsDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CommonDealVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ScoringGenerationAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ScoringGenerationAction.class.getName());
	public ActionForward getScoringDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In ScoringGenerationAction(getScoringDtl)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();				
		}else{
			logger.info("here in getScoringDtl method of ScoringGenerationAction action the session is out----------------");
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
		session.removeAttribute("leadNo");
		//String dealId = request.getParameter("dealId");
		String dealId = "";
		if (!CommonFunction.checkNull(session.getAttribute("dealId")).equalsIgnoreCase("")) {

			dealId = session.getAttribute("dealId").toString();
	
		} else if (!CommonFunction.checkNull(request.getParameter("dealId")).equalsIgnoreCase("")) {
			dealId = request.getParameter("dealId");
			
		}
//		logger.info("In ScoringGenerationAction(getScoringDtl).dealId. " + dealId);
//		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
//        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
//		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
//		CommonDealVo vo=new CommonDealVo();
//		vo.setDealNo(dealId);
//		ArrayList<CommonDealVo> scoringDtlList=service.getScoringDtlList(vo);
//		request.setAttribute("scoringDtlList", scoringDtlList);
		return mapping.findForward("success");
	}
	public ActionForward generateJSPView(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{

		logger.info("In generateJSPView() of ScoringGenerationAction");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		String userId="";	
		String businessdate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();	
			businessdate=userobj.getBusinessdate();
		}else{
			logger.info("here in getScoringDtl method of ScoringGenerationAction action the session is out----------------");
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
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dbType=resource.getString("lbl.dbType");
		
		session.removeAttribute("leadNo");
		String dealId = "";
		if (!CommonFunction.checkNull(session.getAttribute("dealId")).equalsIgnoreCase("")) 
			dealId = session.getAttribute("dealId").toString();
		else if (!CommonFunction.checkNull(request.getParameter("dealId")).equalsIgnoreCase("")) 
			dealId = request.getParameter("dealId");
		logger.info("In ScoringGenerationAction(getScoringDtl).dealId. " + dealId);
		
		ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		dao.getEligibilityRecord(dealId,businessdate,userId);
		
		String reportPath="/reports/";
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
		String reportName="MMCAMScoreCard";
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		
		hashMap.put("deal_id", dealId);
		hashMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
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
		float f1=1.15f;
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
