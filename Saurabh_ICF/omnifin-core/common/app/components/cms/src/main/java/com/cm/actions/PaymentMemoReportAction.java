
package com.cm.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.ReportsDAO;


public class PaymentMemoReportAction extends Action 
{	
	private static final Logger logger = Logger.getLogger(PaymentMemoReportAction.class.getName());	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dbType=resource.getString("lbl.dbType");
		logger.info("In PaymentMemoReportAction");
		String cname =null;
		String strFlag=null;
		try
		{
				HttpSession session = request.getSession();
				//boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");				
				//String username =null;
				//String bDate =null;
				if(userobj!=null){
					cname = userobj.getConpanyName();
				//	username=userobj.getUserName();
				//	bDate=userobj.getBusinessdate();
				}else{
					logger.info(" in execute method of CommonActionForAllReports  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");				
				ServletContext context = getServlet().getServletContext();					
		
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
				
				DynaActionForm daf=(DynaActionForm)form;	
//Neeraj Tripathi start
				String frdLoanID=request.getParameter("frdLoanID");
				String lbx_loan_from_id =null;
				String customerName=null;
				String instrumentId=null;
				if(frdLoanID == null)
				{
					lbx_loan_from_id =(String)daf.get("lbx_loan_from_id");
					customerName=(String)daf.get("customerName");
					 instrumentId=(String)daf.get("instrumentId");
				}
				else
				{
					lbx_loan_from_id=frdLoanID;
					ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
					logger.info("Implementation class: "+dao.getClass());
					customerName=dao.getCustomerName(frdLoanID);	
					dao=null;
				}
				
				logger.info("In Payment memeo report loanID   :  "+lbx_loan_from_id+"and     customerName   :  "+customerName+"and     instrumentId   :  "+instrumentId);
//Neeraj Tripathi end
				customerName=customerName+" ";
				String p_company_name=cname+" ";
				String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
				String subReport=null;
				String SUBREPORT_DIR=null;
				String reportPath="/reports/";
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					subReport=getServlet().getServletContext().getRealPath("/")+"reports\\MSSQLREPORTS\\";
					SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\MSSQLREPORTS\\";
					reportPath=reportPath+"MSSQLREPORTS/";
				}
				else
				{
					subReport=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\";
					SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\MYSQLREPORTS\\";
					reportPath=reportPath+"MYSQLREPORTS/";
				}
									
				//String subReport=getServlet().getServletContext().getRealPath("/")+"reports\\";
				//String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
				String reportName="paymentMemoReport";
			
				Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
				Map<Object,Object> hashMap = new HashMap<Object,Object>();
									
				hashMap.put("p_customer_name",customerName);
				hashMap.put("p_loan_id",lbx_loan_from_id);
				hashMap.put("p_instrument_id",instrumentId);
				hashMap.put("p_company_name",p_company_name);
				hashMap.put("p_company_logo",p_company_logo);
				hashMap.put("subReport",subReport);
				hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);
				
				logger.info("p_customer_name  :  "+customerName);
				logger.info("p_loan_id  :  "+lbx_loan_from_id);
				logger.info("p_instrument_id  :  "+instrumentId);
				logger.info("p_company_name  :  "+p_company_name);
				logger.info("p_company_logo  :  "+p_company_logo);
				logger.info("subReport  :  "+subReport);
				logger.info("SUBREPORT_DIR  :  "+SUBREPORT_DIR);
				
			
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
				JasperPrint jasperPrint = null;
						
				try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
					hashMap.clear();
					customerName=null;
					lbx_loan_from_id=null;
					p_company_name=null;
					p_company_logo=null;
					subReport=null;
					SUBREPORT_DIR=null;
					reportName=null;
					reportPath=null;
					strFlag=null;
					cname=null;
				}				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e=null;
		}finally{
			cname=null;
			strFlag=null;			
		}
	return null;
}
	
		 
	
	public void methodForPDF(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{
	    JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
		File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
		byte[] buffer = new byte[1024];
		int n = 0;
		while ((n = fin.read(buffer)) != -1) 
			outStream.write(buffer, 0, n);			
		outStream.flush();
		fin.close();
		outStream.close();
	}		
	
}
	



