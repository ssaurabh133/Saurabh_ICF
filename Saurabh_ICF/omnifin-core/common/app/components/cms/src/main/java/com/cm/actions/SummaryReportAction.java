package com.cm.actions;

import java.io.File;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.ReportsDAO;
public class SummaryReportAction extends DispatchAction 
{	
	private static final Logger logger = Logger.getLogger(SummaryReportAction.class.getName());	
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	public ActionForward cpCategoryReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cpCategoryReport");
			try
			{
					HttpSession session = request.getSession();
					//boolean flag=false;
					//mradul changes starts
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					
					
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					String flow=null;
					//mradul changes starts
					flow=request.getParameter("flow");
					if(flow.trim().equalsIgnoreCase("B"))
					{
						p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
						p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					}
					
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cpDashboardCategoryDTL";
					
					String parent=null;
					String url=null;
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					hashMap.put("p_app_path",p_app_path);

				
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);

				
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						
						parent=request.getParameter("parent");
						url="/cpDashboard.do";					
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();}						
					}
					//Drilling code end
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("report Name  :  "+ reportName + ".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					//mradul ends
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						reportPath=null;
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;
						p_user_Id=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;
						p_app_path=null;
						parent=null;
					}
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e=null;
			}
		return null;
	}
	public ActionForward cpProductReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In producxcvXDSFVDFSXVtReport.");
			try
			{
					HttpSession session = request.getSession();
					//boolean flag=false;
					//mradul changes starts
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					
					
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cpDashboardProductDTL";
					String flow=null;
					String parent=null;
					String url=null;
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					hashMap.put("p_app_path",p_app_path);

					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						flow=request.getParameter("flow");
						parent=request.getParameter("parent");
						url="/summaryReportAction.do?method=cpCategoryReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date;
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();}	
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
						}
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_category_ID        :  "+p_category_ID);
					logger.info("p_category_Desc      :  "+p_category_Desc);
					logger.info("report Name  :  "+ reportName + ".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					//mradul ends
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;
						p_user_Id=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;
						p_category_ID=null;
						p_category_Desc=null;
						reportName=null;
						p_app_path=null;	
						reportPath=null;
						dbType=null;
						parent=null;
					}					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return null;
	}
	public ActionForward cpStateReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cpStateReport");
			try
			{
					HttpSession session = request.getSession();
					//boolean flag=false;
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;
					//mradul changes starts
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					String flow=null;
					flow=request.getParameter("flow");
					if(CommonFunction.checkNull(flow).trim().equalsIgnoreCase("B"))
					{
						p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
						p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					}
					String p_product_ID=request.getParameter("p_product_ID");
					String p_product_Desc=request.getParameter("p_product_Desc");
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cpDashboardStateDTL";
					String parent=null;
					String url=null;
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					hashMap.put("p_app_path",p_app_path);

					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_product_ID",p_product_ID);
					hashMap.put("p_product_Desc",p_product_Desc);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						parent=request.getParameter("parent");
						url="/cpDashboard.do";		
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();}
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
						}
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_product_ID         :  "+p_product_ID);
					logger.info("p_product_Desc       :  "+p_product_Desc);
					logger.info("p_category_ID        :  "+p_category_ID);
					logger.info("p_category_Desc      :  "+p_category_Desc);
					logger.info("report Name          :  "+ reportName+".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						form.reset(mapping, request);
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;
						p_user_Id=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;
						p_product_ID=null;
						p_product_Desc=null;
						p_category_ID=null;
						p_category_Desc=null;
						reportName=null;
						reportPath=null;
						p_app_path=null;
						dbType=null;
						parent=null;
					}					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return null;
	}
	public ActionForward cpBranchReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cpBranchReport");
			try
			{
					HttpSession session = request.getSession();
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					//mradul changes starts
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					String p_product_ID=request.getParameter("p_product_ID");
					String p_product_Desc=request.getParameter("p_product_Desc");
					String p_state_ID=request.getParameter("p_state_ID");
					String p_state_Desc=request.getParameter("p_state_Desc");
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cpDashboardBranchDTL";
					String flow=null;
					String parent=null;
					String url=null;
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_product_ID",p_product_ID);
					hashMap.put("p_product_Desc",p_product_Desc);
					hashMap.put("p_state_ID",p_state_ID);
					hashMap.put("p_state_Desc",p_state_Desc);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					hashMap.put("p_app_path",p_app_path);

					
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						flow=request.getParameter("flow");
						parent=request.getParameter("parent");
						url="/summaryReportAction.do?method=cpStateReport&p_report_format=H&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_product_ID="+p_product_ID+"&p_product_Desc="+p_product_Desc+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
						
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();}	
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
						}					
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_product_ID         :  "+p_product_ID);
					logger.info("p_product_Desc       :  "+p_product_Desc);
					logger.info("p_state_ID           :  "+p_state_ID);
					logger.info("p_state_Desc         :  "+p_state_Desc);
					logger.info("report Name          :  "+ reportName+".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						e=null;
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						form.reset(mapping, request);
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;
						p_user_Id=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;
						p_product_ID=null;
						p_product_Desc=null;
						p_state_ID=null;
						p_state_Desc=null;
						reportName=null;
						p_app_path=null;
						reportPath=null;
						parent=null;
					}
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return null;
	}
	public ActionForward cpDashboardDTL(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cpDashboardDTL");
			try
			{
					HttpSession session = request.getSession();
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					//mradul changes starts
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					String p_product_ID=request.getParameter("p_product_ID");
					String p_product_Desc=request.getParameter("p_product_Desc");
					String p_state_ID=request.getParameter("p_state_ID");
					String p_state_Desc=request.getParameter("p_state_Desc");
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cpDashboardDTL";
					String flow=request.getParameter("flow");
					String report=request.getParameter("parent");
					String reportPortion=request.getParameter("reportPortion");
					String reportSubPortion=request.getParameter("reportSubPortion");
					String id=request.getParameter("id");
					String url=null;
					String countQuery=null;
					String detailQuery=null;
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

					logger.info("report            :  "+report);
					logger.info("reportPortion     :  "+reportPortion);
					logger.info("reportSubPortion  :  "+reportSubPortion);
					String no="";
					if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
						no="Lead Number";
					if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
						no="Deal Number";
					if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
						no="Deal Number";
					if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
						no="Loan Number";
			
					
					ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		    		logger.info("Implementation class: "+reportdao.getClass());
					String appPath=reportdao.getAppServerPath();
					
					//mradul starts for DASHBOARD reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						
					{		
					logger.info("in db type:::  "+dbType);
					if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardCategoryDTL"))
					{
						url="/summaryReportAction.do?method=cpCategoryReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date;
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS IN('P','L','F') and 'a'='a') or (A.REC_STATUS NOT IN('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"')) AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS IN('P','L','F') and 'a'='a') or (A.REC_STATUS NOT IN('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"')) AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"'  and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'  and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								
								countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"'  AND A.REC_STATUS='X'  and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"'  AND A.REC_STATUS='X' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{		
								countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') and PRODUCT_CATEGORY='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
						{		
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{
								countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl 	WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <>'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <>'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))and PRODUCT_CATEGORY='"+id+"'";					
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' and PRODUCT_CATEGORY='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,dbo.date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and (C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))) and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and (C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))) and PRODUCT_CATEGORY='"+id+"'";
	     					}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN (SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN ( SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F' and PRODUCT_CATEGORY='"+id+"' group by PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F' and PRODUCT_CATEGORY='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
						{	
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(AUTHOR_DATE)<='"+p_to_date+"')) and L.REC_STATUS not in('C','L','X') and LOAN_PRODUCT_CATEGORY='"+id+"' group by LOAN_PRODUCT_CATEGORY ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"')) and L.REC_STATUS not in('C','L','X') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{		
								countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL   WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"' group by LOAN_PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL   WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS='A' AND dbo.date(AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(AUTHOR_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"' group by LOAN_PRODUCT_CATEGORY";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
							{
								countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"' group by LOAN_PRODUCT_CATEGORY ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID)  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL   WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS IN('P','F') and LOAN_PRODUCT_CATEGORY='"+id+"' group by LOAN_PRODUCT_CATEGORY ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS IN('P','F') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardProductDTL"))
					{
						url="/summaryReportAction.do?method=cpProductReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS IN('P','L','F') and 'a'='a') or (A.REC_STATUS not IN('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND A.PRODUCT='"+id+"' group by A.PRODUCT ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS IN('P','L','F') and 'a'='a') or (A.REC_STATUS not IN('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND A.PRODUCT='"+id+"'";
			
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{		
								countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"' AND A.PRODUCT='"+id+"' group by A.PRODUCT";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"' AND A.PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A' AND A.PRODUCT='"+id+"' group by A.PRODUCT";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A' AND A.PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X' AND A.PRODUCT='"+id+"' group by A.PRODUCT ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X' AND A.PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') AND A.PRODUCT='"+id+"' group by A.PRODUCT ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') AND A.PRODUCT='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
						{		
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <> 'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"')) and L.DEAL_PRODUCT='"+id+"'  group by L.DEAL_PRODUCT ";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <> 'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"')) and L.DEAL_PRODUCT='"+id+"'";							
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'  and L.DEAL_PRODUCT='"+id+"'  group by L.DEAL_PRODUCT ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"' and L.DEAL_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and L.DEAL_PRODUCT='"+id+"' group by L.DEAL_PRODUCT ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and L.DEAL_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'  and L.DEAL_PRODUCT='"+id+"' group by L.DEAL_PRODUCT  ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' and L.DEAL_PRODUCT='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
						{

							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,dbo.date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"')) and L.DEAL_PRODUCT='"+id+"'  group by L.DEAL_PRODUCT";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"')) and L.DEAL_PRODUCT='"+id+"'";
	     					}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and L.DEAL_PRODUCT='"+id+"' group by L.DEAL_PRODUCT";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'  and L.DEAL_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) LEFT JOIN (SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"' and L.DEAL_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"'  and L.DEAL_PRODUCT='"+id+"' group by L.DEAL_PRODUCT";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)LEFT JOIN ( SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"' and L.DEAL_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"'  and L.DEAL_PRODUCT='"+id+"' group by L.DEAL_PRODUCT";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F' and L.DEAL_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'  and L.DEAL_PRODUCT='"+id+"' group by L.DEAL_PRODUCT";
							}
						
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
						{	
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')  and LOAN_PRODUCT='"+id+"' group by LOAN_PRODUCT";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"')) and L.REC_STATUS not in('C','L','X')   and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)>=  '"+p_form_date+"'    AND dbo.date(LOAN_INITIATION_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"' group by LOAN_PRODUCT";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)>=  '"+p_form_date+"'    AND dbo.date(LOAN_INITIATION_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{
								countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS='A' AND dbo.date(AUTHOR_DATE)>=  '"+p_form_date+"'    AND dbo.date(AUTHOR_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"' group by LOAN_PRODUCT";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed 'when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND dbo.date(L.AUTHOR_DATE)>=  '"+p_form_date+"'    AND dbo.date(L.AUTHOR_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
							{	
								countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND (D.DATE>=  '"+p_form_date+"'   AND D.DATE<=  '"+p_to_date+"') and LOAN_PRODUCT='"+id+"' group by LOAN_PRODUCT";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed '  when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID)  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND (D.DATE>=  '"+p_form_date+"'   AND D.DATE<=  '"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS IN('P','F') and LOAN_PRODUCT='"+id+"' group by LOAN_PRODUCT";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS IN('P','F') and LOAN_PRODUCT='"+id+"'";
							}
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardStateDTL"))
					{
						url="/summaryReportAction.do?method=cpStateReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_product_ID="+p_product_ID+"&p_product_Desc="+p_product_Desc+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="select B.STATE_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS in('P','L','F') and 'a'='a') or (A.REC_STATUS not in('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND B.STATE_ID='"+id+"' group by STATE_ID";   
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS in('P','L','F') and 'a'='a') or (A.REC_STATUS not in('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND B.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{		
								countQuery="select B.STATE_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"'     AND B.STATE_ID='"+id+"' group by STATE_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"'     AND B.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select B.STATE_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'    AND B.STATE_ID='"+id+"' group by STATE_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'    AND B.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select B.STATE_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'    AND B.STATE_ID='"+id+"' group by STATE_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'    AND B.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="select B.STATE_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+id+"' group by STATE_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
						{	
							
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								
								countQuery="SELECT BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <>'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))  and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <>'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"')) and BR.STATE_ID='"+id+"'";							
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="SELECT BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'      and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'     and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'      and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'     and BR.STATE_ID='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,dbo.date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))  and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))   and BR.STATE_ID='"+id+"'";
	     					}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'       and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'       and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN (SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN ( SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT BR.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'       and BR.STATE_ID='"+id+"' group by BR.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'       and BR.STATE_ID='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
						{	
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT B.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L JOIN com_branch_m B ON(L.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"')) and L.REC_STATUS not in('C','L','X')     and B.STATE_ID='"+id+"' group by B.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')  and BR.STATE_ID='"+id+"'";					
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT B.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'     and B.STATE_ID='"+id+"' group by B.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="SELECT B.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS='A' AND dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"'     and B.STATE_ID='"+id+"' group by B.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND L.REC_STATUS='A' AND dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"'  AND dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
							{	
								countQuery="SELECT B.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"')     and B.STATE_ID='"+id+"' group by B.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID) LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND L.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"')     and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT B.STATE_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS IN('P','F')     and B.STATE_ID='"+id+"' group by B.STATE_ID ";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND L.REC_STATUS IN('P','F')     and BR.STATE_ID='"+id+"'";
							}
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardBranchDTL"))
					{
						url="/summaryReportAction.do?method=cpBranchReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_product_ID="+p_product_ID+"&p_product_Desc="+p_product_Desc+"&p_state_ID="+p_state_ID+"&p_state_Desc="+p_state_Desc+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	countQuery="select A.BRANCH_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS in('P','L','F') and 'a'='a') or (A.REC_STATUS not in('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'  group by A.BRANCH_ID ";
							detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS in('P','L','F') and 'a'='a') or (A.REC_STATUS not in('P','L','F') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="select A.BRANCH_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>= '"+p_form_date+"'  AND LEAD_GENERATION_DATE<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"' group by A.BRANCH_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>= '"+p_form_date+"'  AND LEAD_GENERATION_DATE<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select A.BRANCH_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"' group by A.BRANCH_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select A.BRANCH_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"' group by A.BRANCH_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="select A.BRANCH_ID,count(1)CT,SUM(ISNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+p_state_ID+"' and A.BRANCH_ID='"+id+"' group by A.BRANCH_ID ";
								detailQuery="select replace(concat(ISNULL(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ISNULL(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,dbo.date_format(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+p_state_ID+"' and A.BRANCH_ID='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <>'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))  AND BR.STATE_ID='"+p_state_ID+"'  and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and ((A.REC_STATUS ='P' and 'a'='a') or (A.REC_STATUS <>'P' and dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"')) AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";							
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'  AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND dbo.date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'    AND BR.STATE_ID='"+p_state_ID+"'    and A.DEAL_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' AND DEAL_PRODUCT='"+p_product_ID+"'   and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'    AND BR.STATE_ID='"+p_state_ID+"'    and A.DEAL_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' AND DEAL_PRODUCT='"+p_product_ID+"'   and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'    AND BR.STATE_ID='"+p_state_ID+"'    and A.DEAL_BRANCH='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
						{
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,dbo.date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"')) AND DEAL_PRODUCT='"+p_product_ID+"' and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND ((A.REC_STATUS='F' and 'a'='a') or (A.REC_STATUS<>'F' and C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))    AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
	     					}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' AND DEAL_PRODUCT='"+p_product_ID+"'   and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND dbo.date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{	
								countQuery="select A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN (SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"' AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN ( SELECT A.DEAL_ID,dbo.date(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(SB.APPROVAL_DATE)>='"+p_form_date+"' AND dbo.date(SB.APPROVAL_DATE)<='"+p_to_date+"'  AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,dbo.date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  dbo.date(c.TO_DTAE)>='"+p_form_date+"' AND dbo.date(c.TO_DTAE)<='"+p_to_date+"'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'  AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,dbo.date_format(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ISNULL(B.APPROVAL_DATE,'')APPROVAL_DATE,ISNULL(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,dbo.date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
						{	
							if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
							{	
								countQuery="SELECT L.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L JOIN com_branch_m B ON(L.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(AUTHOR_DATE)<='"+p_to_date+"')) and L.REC_STATUS not in('C','L','X')   AND B.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND ((L.REC_STATUS in('P','F') and 'a'='a') or (L.REC_STATUS not in('P','F') and dbo.date(AUTHOR_DATE)>='"+p_form_date+"' AND dbo.date(AUTHOR_DATE)<='"+p_to_date+"')) and L.REC_STATUS not in('C','L','X')     AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";						
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)>= '"+p_form_date+"'   AND dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'   and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND dbo.date(LOAN_INITIATION_DATE)>= '"+p_form_date+"'   AND dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'   AND BR.STATE_ID='"+p_state_ID+"'   and L.LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
							{		
								countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND dbo.date(A.AUTHOR_DATE)>= '"+p_form_date+"'   AND dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'   and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND dbo.date(L.AUTHOR_DATE)>= '"+p_form_date+"'   AND dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"'   AND BR.STATE_ID='"+p_state_ID+"'   and L.LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
							{	
								countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND (D.DATE>= '"+p_form_date+"'  AND D.DATE<='"+p_to_date+"')   AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(dbo.date(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID) LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND (D.DATE>= '"+p_form_date+"'  AND D.DATE<='"+p_to_date+"')   AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
							{	
								countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,ISNULL(SUM(ISNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS IN('P','F')   AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS IN('P','F')   AND Br.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
							}
						}						
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1CategoryDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
						{
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and dbo.date(AUTHOR_DATE)>='"+p_form_date+"' and dbo.date(AUTHOR_DATE)<='"+p_to_date+"'  and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"' and dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"'  and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
						{
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"' )  and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"' )  and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
						{
							
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"' ) and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
						{
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')  and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')  and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
						{
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' AND DISBURSAL_STATUS='F' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) AND loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')  and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' AND L.DISBURSAL_STATUS='F' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) AND loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')  and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1ProductDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
						{
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and dbo.date(AUTHOR_DATE)>='"+p_form_date+"' and dbo.date(AUTHOR_DATE)<='"+p_to_date+"' and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"' and dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"' and L.LOAN_PRODUCT='"+id+"'";
							
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
						{
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"' )and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"' )and L.LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
						{
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where dbo.date(a.author_date)>='"+p_form_date+"' and dbo.date(a.author_date)<='"+p_to_date+"' ) and loan_id not in (select distinct loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where dbo.date(a.author_date)>='"+p_form_date+"' and dbo.date(a.author_date)<='"+p_to_date+"' ) and loan_id not in (select distinct loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
						{
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where dbo.date(a.author_date)>='"+p_form_date+"' and dbo.date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where dbo.date(a.author_date)>='"+p_form_date+"' and dbo.date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
						{
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id))and loan_id in (select a.pdc_loan_id from cr_pdc_instrument_dtl a join(select pdc_loan_id,max(pdc_instrument_id)id from cr_pdc_instrument_dtl where pdc_status='A' group by pdc_loan_id )b on(b.id=a.pdc_instrument_id) where dbo.date(a.author_date)>='"+p_form_date+"' and dbo.date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id))and loan_id in (select a.pdc_loan_id from cr_pdc_instrument_dtl a join(select pdc_loan_id,max(pdc_instrument_id)id from cr_pdc_instrument_dtl where pdc_status='A' group by pdc_loan_id )b on(b.id=a.pdc_instrument_id) where dbo.date(a.author_date)>='"+p_form_date+"' and dbo.date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1StateDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
						{
							countQuery="select B.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"'  and dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"'    and B.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"'  and dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"'   and BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
						{
							countQuery="select B.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"'  and dbo.date(author_date)<='"+p_to_date+"')    and B.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"'  and dbo.date(author_date)<='"+p_to_date+"')   and BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
						{
							countQuery="select B.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')       and B.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')     and BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
						{
							countQuery="select B.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')    and B.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')   and BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
						{
							countQuery="select B.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' AND A.DISBURSAL_STATUS='F' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id NOT IN(select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') and A.LOAN_PRODUCT='"+p_product_ID+"'  and B.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' AND L.DISBURSAL_STATUS='F' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id NOT IN(select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"')    and BR.STATE_ID='"+id+"'";
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1BranchDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
						{
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(A.AUTHOR_DATE)>='"+p_form_date+"' and dbo.date(A.AUTHOR_DATE)<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"' and A.LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID)  where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(L.AUTHOR_DATE)>='"+p_form_date+"' and dbo.date(L.AUTHOR_DATE)<='"+p_to_date+"'   AND BR.STATE_ID='"+p_state_ID+"' and L.LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
						{
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"' )  AND B.STATE_ID='"+p_state_ID+"' and A.LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"' ) AND BR.STATE_ID='"+p_state_ID+"' and L.LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
						{
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
						{
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
						{
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' AND A.DISBURSAL_STATUS='F' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' AND L.DISBURSAL_STATUS='F' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND dbo.date(author_date)>='"+p_form_date+"' and dbo.date(author_date)<='"+p_to_date+"') AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2CategoryDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
						{
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
						{		
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
						{		
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
						{	
							countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2ProductDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
						{
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
						{		
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
						{		
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
						{	
							countQuery="select LOAN_PRODUCT ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2StateDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
						{
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')  and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
						{		
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
						{		
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
						{	
							countQuery="select BR.STATE_ID ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
						}
					}
					else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2BranchDTL"))
					{	
						if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
						{
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')  and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and dbo.date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and dbo.date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where dbo.date(DISBURSAL_DATE)>='"+p_form_date+"' and dbo.date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
						{		
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and dbo.date(maker_date)>='"+p_form_date+"' and dbo.date(maker_date)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and dbo.date(RECEIVED_DATE)>='"+p_form_date+"' and dbo.date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
						{		
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
						}
						else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
						{	
							countQuery="select LOAN_BRANCH ID,count(1)ct,ISNULL(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							detailQuery="SELECT replace(concat(ISNULL(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ISNULL(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ISNULL(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ISNULL(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,dbo.date_format(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,dbo.date_format(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,dbo.date_format(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ISNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and dbo.date(MAKER_DATE)>='"+p_form_date+"' and dbo.date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
					}
					}
					}else{
						logger.info("in db type:::"+dbType);
						if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardCategoryDTL"))
						{
							url="/summaryReportAction.do?method=cpCategoryReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date;
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"')) and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"')) and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"'  and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'  and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									
									countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"'  AND A.REC_STATUS='X'  and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"'  AND A.REC_STATUS='X' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{		
									countQuery="select PRODUCT_CATEGORY,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN cr_product_m B ON(A.PRODUCT=B.PRODUCT_ID)WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status ,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') and PRODUCT_CATEGORY='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
							{		
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{
									countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))and PRODUCT_CATEGORY='"+id+"'";					
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
								{	
									countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' and PRODUCT_CATEGORY='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))and PRODUCT_CATEGORY='"+id+"'";
		     					}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN (SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									countQuery="select PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN ( SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"' and PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F' and PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F' and PRODUCT_CATEGORY='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
							{	
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(AUTHOR_DATE)>='"+p_form_date+"' AND DATE(AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X') and LOAN_PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(L.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{		
									countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL   WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND DATE(LOAN_INITIATION_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND DATE(LOAN_INITIATION_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL   WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS='A' AND DATE(AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(AUTHOR_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND DATE(L.AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"' and LOAN_PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
								{
									countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID)  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT LOAN_PRODUCT_CATEGORY PRODUCT_CATEGORY,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL   WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS IN('P','F') and LOAN_PRODUCT_CATEGORY='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS IN('P','F') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								}
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardProductDTL"))
						{
							url="/summaryReportAction.do?method=cpProductReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND A.PRODUCT='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"')) AND A.PRODUCT='"+id+"'";
				
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{		
									countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"' AND A.PRODUCT='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"' AND A.PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A' AND A.PRODUCT='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A' AND A.PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X' AND A.PRODUCT='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X' AND A.PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="select A.PRODUCT PRODUCT_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') AND A.PRODUCT='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A') AND A.PRODUCT='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
							{		
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"')) and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))and L.DEAL_PRODUCT='"+id+"'";							
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'  and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"' and L.DEAL_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
								{	
									countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and L.DEAL_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'  and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' and L.DEAL_PRODUCT='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
							{

								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"')) and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"')) and L.DEAL_PRODUCT='"+id+"'";
		     					}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'  and L.DEAL_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) LEFT JOIN (SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"' and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"'  and L.DEAL_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									countQuery="select L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)LEFT JOIN ( SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"' and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"'  and L.DEAL_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT L.DEAL_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F' and L.DEAL_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'  and L.DEAL_PRODUCT='"+id+"'";
								}
							
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
							{	
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(AUTHOR_DATE)>='"+p_form_date+"' AND DATE(AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')  and LOAN_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(L.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')   and LOAN_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)>=  '"+p_form_date+"'    AND DATE(LOAN_INITIATION_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)>=  '"+p_form_date+"'    AND DATE(LOAN_INITIATION_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{
									countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS='A' AND DATE(AUTHOR_DATE)>=  '"+p_form_date+"'    AND DATE(AUTHOR_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed 'when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND DATE(L.AUTHOR_DATE)>=  '"+p_form_date+"'    AND DATE(L.AUTHOR_DATE)<=  '"+p_to_date+"'  and LOAN_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
								{	
									countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND (D.DATE>=  '"+p_form_date+"'   AND D.DATE<=  '"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed '  when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID)  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND (D.DATE>=  '"+p_form_date+"'   AND D.DATE<=  '"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT LOAN_PRODUCT PRODUCT_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND REC_STATUS IN('P','F') and LOAN_PRODUCT='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS IN('P','F') and LOAN_PRODUCT='"+id+"'";
								}
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardStateDTL"))
						{
							url="/summaryReportAction.do?method=cpStateReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_product_ID="+p_product_ID+"&p_product_Desc="+p_product_Desc+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="select B.STATE_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"'))  AND B.STATE_ID='"+id+"'";   
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"'))   AND B.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{		
									countQuery="select B.STATE_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"'     AND B.STATE_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND LEAD_GENERATION_DATE>='"+p_form_date+"' AND LEAD_GENERATION_DATE<='"+p_to_date+"'     AND B.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select B.STATE_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'    AND B.STATE_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'    AND B.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									countQuery="select B.STATE_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'    AND B.STATE_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'    AND B.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="select B.STATE_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
							{	
								
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									
									countQuery="SELECT BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))   and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))    and BR.STATE_ID='"+id+"'";							
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
								{	
									countQuery="SELECT BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'      and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'     and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'      and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'     and BR.STATE_ID='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))      and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))      and BR.STATE_ID='"+id+"'";
		     					}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'       and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'       and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN (SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									countQuery="select BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN ( SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"'       and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT BR.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'       and BR.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'       and BR.STATE_ID='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
							{	
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT B.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L JOIN com_branch_m B ON(L.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(L.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')     and B.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(L.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')       and BR.STATE_ID='"+id+"'";					
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT B.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND DATE(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND DATE(LOAN_INITIATION_DATE)<='"+p_to_date+"'     and B.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND DATE(LOAN_INITIATION_DATE)>='"+p_form_date+"'  AND DATE(LOAN_INITIATION_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="SELECT B.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS='A' AND DATE(A.AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"'     and B.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND L.REC_STATUS='A' AND DATE(L.AUTHOR_DATE)>='"+p_form_date+"'  AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"'     and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
								{	
									countQuery="SELECT B.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"')     and B.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID) LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND L.REC_STATUS='A' AND (D.DATE>='"+p_form_date+"' AND D.DATE<='"+p_to_date+"')     and BR.STATE_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT B.STATE_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND A.REC_STATUS IN('P','F')     and B.STATE_ID='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID= '"+p_user_Id+"' ) AND L.REC_STATUS IN('P','F')     and BR.STATE_ID='"+id+"'";
								}
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cpDashboardBranchDTL"))
						{
							url="/summaryReportAction.do?method=cpBranchReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_product_ID="+p_product_ID+"&p_product_Desc="+p_product_Desc+"&p_state_ID="+p_state_ID+"&p_state_Desc="+p_state_Desc+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LE"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	countQuery="select A.BRANCH_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"'))  AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
								detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(LEAD_GENERATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS IN('P','L','F'),true,(date(A.AUTHOR_DATE)>='"+p_form_date+"' AND date(A.AUTHOR_DATE)<='"+p_to_date+"'))  AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="select A.BRANCH_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>= '"+p_form_date+"'  AND LEAD_GENERATION_DATE<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND LEAD_GENERATION_DATE>= '"+p_form_date+"'  AND LEAD_GENERATION_DATE<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select A.BRANCH_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='A'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									countQuery="select A.BRANCH_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(A.AUTHOR_DATE)>= '"+p_form_date+"'  AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"' AND A.REC_STATUS='X'   AND B.STATE_ID='"+p_state_ID+"'  and A.BRANCH_ID='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="select A.BRANCH_ID,count(1)CT,SUM(IFNULL(AMOUNT_REQUIRED,0))AMT from  cr_lead_dtl A JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+p_state_ID+"' and A.BRANCH_ID='"+id+"'";
									detailQuery="select replace(concat(ifnull(B.BRANCH_DESC,''),' '),'\\n',' ') branch,A.LEAD_ID no,replace(concat(ifnull(A.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(C.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(D.SCHEME_DESC,''),' '),'\\n',' ') scheme,A.AMOUNT_REQUIRED amount,case A.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Approved ' when 'L' then 'Allocated ' when 'X' then 'Rejected ' when 'F' then 'Forwarderd ' end as status,DATE_FORMAT(A.LEAD_GENERATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(A.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.AUTHOR_ID),''),' '),'\\n',' ') author from  cr_lead_dtl A LEFT JOIN com_branch_m B ON(A.BRANCH_ID=B.BRANCH_ID) JOIN cr_product_m C ON(A.PRODUCT=C.PRODUCT_ID) LEFT JOIN cr_scheme_m D  ON(A.SCHEME=D.SCHEME_ID) WHERE A.BRANCH_ID IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS NOT IN('X','A')   AND B.STATE_ID='"+p_state_ID+"' and A.BRANCH_ID='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DC"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))   AND BR.STATE_ID='"+p_state_ID+"'  and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)<'"+p_form_date+"' and if(A.REC_STATUS ='P',true,(date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"'))   AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";							
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'  AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_INITIATION_DATE)>='"+p_form_date+"' AND date(A.DEAL_INITIATION_DATE)<='"+p_to_date+"'    AND BR.STATE_ID='"+p_state_ID+"'    and A.DEAL_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("F"))
								{	
									countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' AND DEAL_PRODUCT='"+p_product_ID+"'   and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'    AND BR.STATE_ID='"+p_state_ID+"'    and A.DEAL_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P' AND DEAL_PRODUCT='"+p_product_ID+"'   and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='P'    AND BR.STATE_ID='"+p_state_ID+"'    and A.DEAL_BRANCH='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("DP"))
							{
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) join (select A.deal_id,B.TO_DTAE from cr_deal_dtl A left join (select deal_id,date(max(APPROVAL_DATE))TO_DTAE from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A'group by deal_id)B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"')) AND DEAL_PRODUCT='"+p_product_ID+"' and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X','A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)    where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS<>'P' and A.DEAL_FORWARDED_DATE<'"+p_form_date+"' AND if(A.REC_STATUS='F',true,(C.TO_DTAE>='"+p_form_date+"' AND C.TO_DTAE<='"+p_to_date+"'))    AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
		     					}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P' AND DEAL_PRODUCT='"+p_product_ID+"'   and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND date(A.DEAL_FORWARDED_DATE) >='"+p_form_date+"' AND date(A.DEAL_FORWARDED_DATE) <='"+p_to_date+"' and A.REC_STATUS <>'P'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{	
									countQuery="select A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN (SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"' AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID)   WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('A') and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("R"))
								{	
									countQuery="select A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) LEFT JOIN ( SELECT A.DEAL_ID,DATE(A.APPROVAL_DATE)APPROVAL_DATE FROM cr_deal_approval_dtl A JOIN (SELECT MAX(DEAL_APPROVAL_ID)ID,DEAL_ID FROM cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' GROUP BY DEAL_ID)B ON(A.DEAL_ID=B.DEAL_ID AND A.DEAL_APPROVAL_ID=B.ID))SB ON(SB.DEAL_ID=A.DEAL_ID)WHERE DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(SB.APPROVAL_DATE)>='"+p_form_date+"' AND DATE(SB.APPROVAL_DATE)<='"+p_to_date+"'  AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(c.TO_DTAE,'%d-%m-%Y') authorDate,replace(concat(ifnull(c.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join (select A.deal_id,B.TO_DTAE,B.AUT from cr_deal_dtl A left join (select a.deal_id,date(APPROVAL_DATE)TO_DTAE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=APPROVAL_BY)AUT from  cr_deal_approval_dtl a join (select max(DEAL_APPROVAL_ID)id,deal_id from cr_deal_approval_dtl WHERE approval_decision in('X') and rec_status='A' group by deal_id)b on(a.deal_id=b.deal_id and a.DEAL_APPROVAL_ID=b.id))B on(A.deal_id=B.deal_id))c on (c.deal_id=A.DEAL_ID) WHERE A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.REC_STATUS in('X')and  DATE(c.TO_DTAE)>='"+p_form_date+"' AND DATE(c.TO_DTAE)<='"+p_to_date+"'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT A.DEAL_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(L.DEAL_LOAN_AMOUNT,0)),0)AMT FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID) JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID) where DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'  AND DEAL_PRODUCT='"+p_product_ID+"'  and A.DEAL_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,A.DEAL_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.DEAL_LOAN_AMOUNT amount,case A.REC_STATUS  when 'A' then 'Approved ' when 'P' then 'Pending ' when 'F' then 'Forwarded ' when 'X' then 'Rejected ' end as status,DATE_FORMAT(A.DEAL_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(A.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=A.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(AU.APPROVAL_DATE,'%d-%m-%Y') authorDate,replace(concat(ifnull(AU.AUT,''),' '),'\\n',' ') author FROM cr_deal_dtl A JOIN CR_DEAL_LOAN_DTL L ON(A.DEAL_ID=L.DEAL_ID)  JOIN cr_product_m P ON(L.DEAL_PRODUCT=P.PRODUCT_ID)LEFT JOIN com_branch_m BR ON(A.DEAL_BRANCH=BR.BRANCH_ID)LEFT JOIN cr_deal_customer_m CU ON(CU.CUSTOMER_ID=A.DEAL_CUSTOMER_ID)LEFT JOIN cr_scheme_m SC  ON(L.DEAL_SCHEME=SC.SCHEME_ID) join(select A.deal_id,ifnull(B.APPROVAL_DATE,'0000-00-00')APPROVAL_DATE,ifnull(B.AUT,'')AUT from cr_deal_dtl A left join (SELECT a.DEAL_ID,date(a.APPROVAL_DATE)APPROVAL_DATE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=a.APPROVAL_BY)AUT from cr_deal_approval_dtl a join(select deal_id,max(deal_approval_id)id from cr_deal_approval_dtl WHERE approval_decision in('A') and rec_status='A' group by deal_id)appId on(appId.id=a.deal_approval_id)) B on(A.deal_id=B.deal_id))AU on (AU.deal_id=A.DEAL_ID) where A.DEAL_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS ='F'     AND BR.STATE_ID='"+p_state_ID+"'   and A.DEAL_BRANCH='"+id+"'";
								}
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("LO"))
							{	
								if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("BF"))
								{	
									countQuery="SELECT L.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL L JOIN com_branch_m B ON(L.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(AUTHOR_DATE)>='"+p_form_date+"' AND DATE(AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')   AND B.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)<'"+p_form_date+"' AND if(L.REC_STATUS in('P','F'),true,(DATE(L.AUTHOR_DATE)>='"+p_form_date+"' AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"'))and L.REC_STATUS not in('C','L','X')     AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";						
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("N"))
								{	
									countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)>= '"+p_form_date+"'   AND DATE(LOAN_INITIATION_DATE)<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'   and A.LOAN_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND DATE(LOAN_INITIATION_DATE)>= '"+p_form_date+"'   AND DATE(LOAN_INITIATION_DATE)<='"+p_to_date+"'   AND BR.STATE_ID='"+p_state_ID+"'   and L.LOAN_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("A"))
								{		
									countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND DATE(A.AUTHOR_DATE)>= '"+p_form_date+"'   AND DATE(A.AUTHOR_DATE)<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"'   and A.LOAN_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND DATE(L.AUTHOR_DATE)>= '"+p_form_date+"'   AND DATE(L.AUTHOR_DATE)<='"+p_to_date+"'   AND BR.STATE_ID='"+p_state_ID+"'   and L.LOAN_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("D"))
								{	
									countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=A.LOAN_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS='A' AND (D.DATE>= '"+p_form_date+"'  AND D.DATE<='"+p_to_date+"')   AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN(SELECT  LOAN_ID,MIN(DATE(AUTHOR_DATE))DATE FROM cr_loan_disbursal_dtl WHERE REC_STATUS='A' GROUP BY LOAN_ID)D ON(D.LOAN_ID=L.LOAN_ID) LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS='A' AND (D.DATE>= '"+p_form_date+"'  AND D.DATE<='"+p_to_date+"')   AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
								}
								else if(CommonFunction.checkNull(reportSubPortion).trim().equalsIgnoreCase("IP"))
								{	
									countQuery="SELECT A.LOAN_BRANCH BRANCH_ID,COUNT(1)CT,IFNULL(SUM(IFNULL(LOAN_LOAN_AMOUNT,0)),0)AMT FROM CR_LOAN_DTL A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND A.REC_STATUS IN('P','F')   AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
									detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) WHERE LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.REC_STATUS IN('P','F')   AND Br.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
								}
							}						
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1CategoryDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
							{
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and date(AUTHOR_DATE)>='"+p_form_date+"' and date(AUTHOR_DATE)<='"+p_to_date+"'  and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and date(L.AUTHOR_DATE)>='"+p_form_date+"' and date(L.AUTHOR_DATE)<='"+p_to_date+"'  and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
							{
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"' )  and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"' )  and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
							{
								
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"' ) and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
							{
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')  and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')  and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
							{
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' AND DISBURSAL_STATUS='F' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) AND loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')  and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' AND L.DISBURSAL_STATUS='F' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) AND loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')  and L.LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1ProductDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
							{
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and date(AUTHOR_DATE)>='"+p_form_date+"' and date(AUTHOR_DATE)<='"+p_to_date+"' and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and date(L.AUTHOR_DATE)>='"+p_form_date+"' and date(L.AUTHOR_DATE)<='"+p_to_date+"' and L.LOAN_PRODUCT='"+id+"'";
								
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
							{
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"' )and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"' )and L.LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
							{
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where date(a.author_date)>='"+p_form_date+"' and date(a.author_date)<='"+p_to_date+"' ) and loan_id not in (select distinct loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where date(a.author_date)>='"+p_form_date+"' and date(a.author_date)<='"+p_to_date+"' ) and loan_id not in (select distinct loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
							{
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where date(a.author_date)>='"+p_form_date+"' and date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id) where date(a.author_date)>='"+p_form_date+"' and date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
							{
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where rec_status='A' and LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id))and loan_id in (select a.pdc_loan_id from cr_pdc_instrument_dtl a join(select pdc_loan_id,max(pdc_instrument_id)id from cr_pdc_instrument_dtl where pdc_status='A' group by pdc_loan_id )b on(b.id=a.pdc_instrument_id) where date(a.author_date)>='"+p_form_date+"' and date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and loan_id in (select a.loan_id from cr_loan_disbursal_dtl a join(select max(loan_disbursal_id)id,loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' group by loan_id order by loan_id)b on(b.id=a.loan_disbursal_id))and loan_id in (select a.pdc_loan_id from cr_pdc_instrument_dtl a join(select pdc_loan_id,max(pdc_instrument_id)id from cr_pdc_instrument_dtl where pdc_status='A' group by pdc_loan_id )b on(b.id=a.pdc_instrument_id) where date(a.author_date)>='"+p_form_date+"' and date(a.author_date)<='"+p_to_date+"' ) and LOAN_PRODUCT='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1StateDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
							{
								countQuery="select B.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and date(A.AUTHOR_DATE)>='"+p_form_date+"'  and date(A.AUTHOR_DATE)<='"+p_to_date+"'    and B.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and date(L.AUTHOR_DATE)>='"+p_form_date+"'  and date(L.AUTHOR_DATE)<='"+p_to_date+"'   and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
							{
								countQuery="select B.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"'  and date(author_date)<='"+p_to_date+"')    and B.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"'  and date(author_date)<='"+p_to_date+"')   and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
							{
								countQuery="select B.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')       and B.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='P' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')     and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
							{
								countQuery="select B.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')    and B.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' AND disbursal_flag='F' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')   and BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
							{
								countQuery="select B.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' AND A.DISBURSAL_STATUS='F' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and A.loan_id NOT IN(select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') and A.LOAN_PRODUCT='"+p_product_ID+"'  and B.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' AND L.DISBURSAL_STATUS='F' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.loan_id NOT IN(select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"')    and BR.STATE_ID='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard1BranchDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("AL"))
							{
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(A.AUTHOR_DATE)>='"+p_form_date+"' and date(A.AUTHOR_DATE)<='"+p_to_date+"'   AND B.STATE_ID='"+p_state_ID+"' and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID)  where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(L.AUTHOR_DATE)>='"+p_form_date+"' and date(L.AUTHOR_DATE)<='"+p_to_date+"'   AND BR.STATE_ID='"+p_state_ID+"' and L.LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("ND"))
							{
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"' )  AND B.STATE_ID='"+p_state_ID+"' and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and L.loan_id not in (select loan_id from cr_loan_disbursal_dtl where rec_status='A' and date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"' ) AND BR.STATE_ID='"+p_state_ID+"' and L.LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("PD"))
							{
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='P' and rec_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("FD"))
							{
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and A.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and L.loan_id in (select loan_id from cr_loan_disbursal_dtl where disbursal_flag='F' and rec_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("NP"))
							{
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl A JOIN com_branch_m B ON(A.LOAN_BRANCH=B.BRANCH_ID) where A.rec_status='A' AND A.DISBURSAL_STATUS='F' and A.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') AND B.STATE_ID='"+p_state_ID+"'  and A.LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,L.REC_STATUS status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.rec_status='A' AND L.DISBURSAL_STATUS='F' and L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') AND L.loan_id NOT IN (select pdc_loan_id from cr_pdc_instrument_dtl where pdc_status='A' AND date(author_date)>='"+p_form_date+"' and date(author_date)<='"+p_to_date+"') AND BR.STATE_ID='"+p_state_ID+"'  and L.LOAN_BRANCH='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2CategoryDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
							{
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
							{		
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
							{		
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
							{	
								countQuery="select LOAN_PRODUCT_CATEGORY ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT_CATEGORY='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2ProductDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
							{
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X')) and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
							{		
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
							{		
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
							{	
								countQuery="select LOAN_PRODUCT ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"') and LOAN_PRODUCT='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2StateDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
							{
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')  and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
							{		
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
							{		
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
							{	
								countQuery="select BR.STATE_ID ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(L.LOAN_PRODUCT=P.PRODUCT_ID)  LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  BR.STATE_ID='"+id+"'";
							}
						}
						else if(CommonFunction.checkNull(report).trim().equalsIgnoreCase("cmDashboard2BranchDTL"))
						{	
							if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("B"))
							{
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')  and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"') and date(LOAN_INITIATION_DATE)>='"+p_form_date+"' and date(LOAN_INITIATION_DATE)<='"+p_to_date+"'  and L.REC_STATUS not in('C','L','X','A')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("C"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_loan_disbursal_dtl where date(DISBURSAL_DATE)>='"+p_form_date+"' and date(DISBURSAL_DATE)<='"+p_to_date+"' and rec_status not in('A','X'))   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("D"))
							{		
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct pdc_loan_id from cr_pdc_instrument_dtl where pdc_status in('P','F')and date(maker_date)>='"+p_form_date+"' and date(maker_date)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("E"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct TXNID  from cr_document_dtl where stage_id='POD' and txn_type='LIM' AND REC_STATUS='F' and DATE(MAKER_DATE)>='"+p_form_date+"' and DATE(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("F"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("G"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='P' and REC_STATUS in('P','F') and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("H"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txnid from cr_instrument_dtl where INSTRUMENT_TYPE='R' and REC_STATUS ='A' and date(RECEIVED_DATE)>='"+p_form_date+"' and date(RECEIVED_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("I"))
							{		
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct txn_id from cr_manual_advice_dtl where txn_type='LIM' and REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("J"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_waiveoff_dtl where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("K"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_knockoff_m where REC_STATUS in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("L"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='P' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("M"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  Where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) Where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='U' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("N"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='R' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("O"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_resch_dtl where resch_type='A' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("P"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='T' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("Q"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='C' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("R"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_termination_dtl where termination_type='X' and rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
							else if(CommonFunction.checkNull(reportPortion).trim().equalsIgnoreCase("S"))
							{	
								countQuery="select LOAN_BRANCH ID,count(1)ct,ifnull(sum(LOAN_LOAN_AMOUNT),0)amt from cr_loan_dtl L JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID)  where LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
								detailQuery="SELECT replace(concat(ifnull(BR.BRANCH_DESC,''),' '),'\\n',' ') branch,L.LOAN_NO no,replace(concat(ifnull(CU.CUSTOMER_NAME,''),' '),'\\n',' ') customer,replace(concat(ifnull(P.PRODUCT_DESC,''),' '),'\\n',' ') product,replace(concat(ifnull(SC.SCHEME_DESC,''),' '),'\\n',' ') scheme,L.LOAN_LOAN_AMOUNT amount,case L.REC_STATUS when 'P' then 'Pending ' when 'A' then 'Active ' when 'L' then 'Cancelled ' when 'X' then 'Rejected ' when 'C' then 'Closed ' when 'F' then 'Forwarded ' end as status,DATE_FORMAT(L.LOAN_INITIATION_DATE,'%d-%m-%Y')initDate,DATE_FORMAT(L.MAKER_DATE,'%d-%m-%Y')makerDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.MAKER_ID),''),' '),'\\n',' ') maker,DATE_FORMAT(L.AUTHOR_DATE,'%d-%m-%Y') authorDate,replace(concat(IFNULL((SELECT USER_NAME FROM sec_user_m WHERE USER_ID=L.AUTHOR_ID),''),' '),'\\n',' ') author FROM CR_LOAN_DTL L  LEFT JOIN com_branch_m BR ON(L.LOAN_BRANCH=BR.BRANCH_ID) LEFT JOIN gcd_customer_m CU ON(CU.CUSTOMER_ID=L.LOAN_CUSTOMER_ID) LEFT JOIN cr_product_m P ON(LOAN_BRANCH=P.PRODUCT_ID) LEFT JOIN cr_scheme_m SC  ON(L.LOAN_SCHEME=SC.SCHEME_ID) where L.LOAN_BRANCH IN (SELECT branch_id FROM sec_user_branch_dtl WHERE USER_ID='"+p_user_Id+"' ) and L.rec_status='A' and L.loan_id in(select distinct loan_id from cr_sd_liquidation_dtl where rec_status in('P','F') and date(MAKER_DATE)>='"+p_form_date+"' and date(MAKER_DATE)<='"+p_to_date+"')   and  LOAN_BRANCH='"+id+"'";
							}
						}
					}
					logger.info("countQuery   :  "+countQuery);
					logger.info("detailQuery  :  "+detailQuery);
					
					//code for Drilling is start from here,it is only for HTML
//					if(p_report_format.trim().equalsIgnoreCase("H"))
//					{
//						ArrayList<Object> in =new ArrayList<Object>();
//						ArrayList<Object> out =new ArrayList<Object>();
//						ArrayList outMessages = new ArrayList();
//						String s1="";
//						String s2="";
//						in.add(p_user_Id); // userID
//						in.add(report); //parent report
//						in.add(reportName); // current report
//						in.add(url); // current URL
//						in.add(flow); // backForward
//						out.add(s1);
//						out.add(s2);
//						try
//						{
//							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
//							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
//							s1=CommonFunction.checkNull(outMessages.get(0));
//							s2=CommonFunction.checkNull(outMessages.get(1));
//							logger.info("s1      : "+s1);
//						    logger.info("s2      : "+s2);
//						}
//						catch (Exception e) 
//						{e.printStackTrace();}						
//					}
					//Drilling code end
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("backUrl",url);
					hashMap.put("appPath",appPath);
					hashMap.put("query",detailQuery);
					hashMap.put("id",id);
					hashMap.put("report",report);
					hashMap.put("reportPortion",reportPortion);
					hashMap.put("reportSubPortion",reportSubPortion);
					hashMap.put("p_product_ID",p_product_ID);
					hashMap.put("p_product_Desc",p_product_Desc);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					hashMap.put("p_state_ID",p_state_ID);
					hashMap.put("p_state_Desc",p_state_Desc);
					hashMap.put("no",no);
					hashMap.put("p_app_path",p_app_path);
					
					logger.info("p_app_path       :  "+p_app_path);
					//mradul starts for payout reports
				
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					String windoName=request.getParameter("windoName");									
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,windoName);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						p_company_name =null;
						p_user_Id =null;
						p_printed_by=null;
						p_printed_date =null;
						p_company_logo=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;					
						url=null;
						appPath=null;
						detailQuery=null;
						countQuery=null;
						id=null;
						report=null;
						reportPortion=null;
						reportSubPortion=null;
						p_product_ID=null;
						p_product_Desc=null;
						p_category_ID=null;
						p_category_Desc=null;
						p_state_ID=null;
						p_state_Desc=null;
						no=null;
						p_app_path=null;						
					}					
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e=null;
			}
		return null;
	}
	public ActionForward cm1CategoryReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		logger.info("In cm1CategoryReport");
		try
		{
				HttpSession session = request.getSession();
				//boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String p_company_name =null;
				String p_user_Id =null;
				String p_printed_by=null;
				String p_printed_date =null;
				if(userobj!=null)
				{
					p_company_name = userobj.getConpanyName();
					p_user_Id=userobj.getUserId();
					p_printed_by=userobj.getUserName();
					p_printed_date=userobj.getBusinessdate();
				}
				else
				    return mapping.findForward("sessionOut");
				Object sessionId = session.getAttribute("sessionID");
				ServletContext context = getServlet().getServletContext();
				String strFlag=null;
				if(sessionId!=null)
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
				
				
				String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
				String p_report_format=request.getParameter("p_report_format");
				String p_form_date=request.getParameter("p_form_date");
				String p_to_date=request.getParameter("p_to_date");
				String flow=null;
				flow=request.getParameter("flow");
				if(flow.trim().equalsIgnoreCase("B"))
				{
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
				}
				
				String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
				p_form_date=CommonFunction.changeFormat(p_form_date);
				p_to_date=CommonFunction.changeFormat(p_to_date);
				String reportName="cmDashboard1CategoryDTL";
				
				String parent=null;
				String url=null;				
				Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
				Map<Object,Object> hashMap = new HashMap<Object,Object>();				
				p_printed_date=	formate(p_printed_date);
				String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				
				logger.info("test  " + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() );
				
				hashMap.put("p_company_logo",p_company_logo);
				hashMap.put("p_company_name",p_company_name);
				hashMap.put("p_printed_date",p_printed_date);
				hashMap.put("p_printed_by",p_printed_by);
				hashMap.put("p_user_Id",p_user_Id);
				hashMap.put("p_report_format",p_report_format);
				hashMap.put("p_form_date",p_form_date);
				hashMap.put("p_to_date",p_to_date);
				hashMap.put("p_printed_date_range",p_printed_date_range);
				hashMap.put("p_app_path",p_app_path);
				if(p_report_format.trim().equalsIgnoreCase("P"))
					hashMap.put("IS_IGNORE_PAGINATION",false);
				else
					hashMap.put("IS_IGNORE_PAGINATION",true);				
				
				logger.info("p_app_path " + p_app_path);				
				//code for Drilling is start from here,it is only for HTML
				if(p_report_format.trim().equalsIgnoreCase("H"))
				{
					
					parent=request.getParameter("parent");
					url="/summaryReport.do";					
					ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					String s1=null;
					String s2=null;
					in.add(p_user_Id); // userID
					in.add(parent); //parent report
					in.add(reportName); // current report
					in.add(url); // current URL
					in.add(flow); // backForward
					out.add(s1);
					out.add(s2);
					try
					{
						logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
						outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
						s1=CommonFunction.checkNull(outMessages.get(0));
						s2=CommonFunction.checkNull(outMessages.get(1));
						logger.info("s1      : "+s1);
					    logger.info("s2      : "+s2);
					}
					catch (Exception e) 
					{e.printStackTrace();
					e=null; }	
					finally{
						in.clear();
						in=null;
						out.clear();
						out=null;
						outMessages.clear();
						outMessages=null;
						s1=null;
						s2=null;
						flow=null;
						url=null;
						parent=null;
					}
				}
				//Drilling code end				
				logger.info("p_company_logo       :  "+p_company_logo);
				logger.info("p_company_name       :  "+p_company_name);
				logger.info("p_printed_date       :  "+p_printed_date);
				logger.info("p_printed_by         :  "+p_printed_by);
				logger.info("p_user_Id            :  "+p_user_Id);
				logger.info("p_report_format      :  "+p_report_format);
				logger.info("p_form_date          :  "+p_form_date);
				logger.info("p_to_date            :  "+p_to_date);
				logger.info("p_printed_date_range :  "+p_printed_date_range);
				logger.info("report Name  :  "+ reportName + ".jasper");
				
				//mradul starts for payout reports
				String dbType=resource.getString("lbl.dbType");
				String reportPath="/reports/";
				
				if(dbType.equalsIgnoreCase("MSSQL"))
					reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
				else
					reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
				
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
				JasperPrint jasperPrint = null;
				
				try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					if(p_report_format.equals("P"))
						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
					if(p_report_format.equals("E"))				
						methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
				    if(p_report_format.equals("H"))				
						methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					e=null;
				}
				finally
				{
					ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
					hashMap.clear();
					form.reset(mapping, request);
					p_company_logo=null;
					p_company_name=null;
					p_printed_date=null;
					p_printed_by=null;
					p_user_Id=null;
					p_report_format=null;
					p_form_date=null;
					p_to_date=null;
					p_printed_date_range=null;
					reportName=null;
					reportPath=null;
					dbType=null;
				}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e=null;
		}
	return null;
}
	public ActionForward cm1ProductReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cm1ProductReport.");
			try
			{
					HttpSession session = request.getSession();
					//boolean flag=false;
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
						
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cmDashboard1ProductDTL";
					String flow=null;
					String parent=null;
					String url=null;
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					hashMap.put("p_app_path",p_app_path);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						flow=request.getParameter("flow");
						parent=request.getParameter("parent");
						url="/summaryReportAction.do?method=cm1CategoryReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date;
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();
						e=null; }		
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
							url=null;
							parent=null;
						}	
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_category_ID        :  "+p_category_ID);
					logger.info("p_category_Desc      :  "+p_category_Desc);
					logger.info("report Name  :  "+ reportName + ".jasper");
					
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						form.reset(mapping, request);
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;
						p_category_Desc=null;
						p_user_Id=null;
						p_category_ID=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;
						reportName=null;
						reportPath=null;
						dbType=null;						
					}					
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e=null;
			}
		return null;
	}
	public ActionForward cm1StateReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cm1StateReport");
			try
			{
					HttpSession session = request.getSession();
					//boolean flag=false;
					//mradul changes for null object
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					String flow=null;
					flow=request.getParameter("flow");
					if(CommonFunction.checkNull(flow).trim().equalsIgnoreCase("B"))
					{
						p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
						p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					}
					String p_product_ID=request.getParameter("p_product_ID");
					String p_product_Desc=request.getParameter("p_product_Desc");
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cmDashboard1StateDTL";
					String parent=null;
					String url=null;
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					hashMap.put("p_app_path",p_app_path);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_product_ID",p_product_ID);
					hashMap.put("p_product_Desc",p_product_Desc);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						parent=request.getParameter("parent");
						url="/summaryReport.do";				
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();}	
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
							url=null;
							parent=null;
						}	
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_product_ID         :  "+p_product_ID);
					logger.info("p_product_Desc       :  "+p_product_Desc);
					logger.info("p_category_ID        :  "+p_category_ID);
					logger.info("p_category_Desc      :  "+p_category_Desc);
					logger.info("report Name          :  "+ reportName+".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					

					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						e=null;
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						form.reset(mapping, request);
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;
						p_category_Desc=null;
						p_user_Id=null;
						p_category_ID=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;
						reportName=null;
						reportPath=null;
						reportName=null;
						dbType=null;						
					}					
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e=null;
			}
		return null;
	}
	public ActionForward cm1BranchReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cm1BranchReport");
			try
			{
					HttpSession session = request.getSession();
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag="";	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					String p_product_ID=request.getParameter("p_product_ID");
					String p_product_Desc=request.getParameter("p_product_Desc");
					String p_state_ID=request.getParameter("p_state_ID");
					String p_state_Desc=request.getParameter("p_state_Desc");
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cmDashboard1BranchDTL";
					String flow=null;
					String parent=null;
					String url=null;
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_product_ID",p_product_ID);
					hashMap.put("p_product_Desc",p_product_Desc);
					hashMap.put("p_state_ID",p_state_ID);
					hashMap.put("p_state_Desc",p_state_Desc);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					hashMap.put("p_app_path",p_app_path);
					
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						flow=request.getParameter("flow");
						parent=request.getParameter("parent");
						url="/summaryReportAction.do?method=cm1StateReport&p_report_format=H&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_product_ID="+p_product_ID+"&p_product_Desc="+p_product_Desc+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
						
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();
						e=null; }	
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
							url=null;
						}	
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_product_ID         :  "+p_product_ID);
					logger.info("p_product_Desc       :  "+p_product_Desc);
					logger.info("p_state_ID           :  "+p_state_ID);
					logger.info("p_state_Desc         :  "+p_state_Desc);
					logger.info("report Name          :  "+ reportName+".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					

					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						form.reset(mapping, request);
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;
						p_category_Desc=null;
						p_user_Id=null;
						p_category_ID=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_printed_date_range=null;
						reportName=null;
						reportPath=null;
						reportName=null;
						dbType=null;
					}
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e=null;
			}
		return null;
	}
	public ActionForward cm2CategoryReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		logger.info("In cm2CategoryReport");
		try
		{
				HttpSession session = request.getSession();
				//boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String p_company_name =null;
				String p_user_Id =null;
				String p_printed_by=null;
				String p_printed_date =null;
				if(userobj!=null)
				{
					p_company_name = userobj.getConpanyName();
					p_user_Id=userobj.getUserId();
					p_printed_by=userobj.getUserName();
					p_printed_date=userobj.getBusinessdate();
				}
				else
				    return mapping.findForward("sessionOut");
				Object sessionId = session.getAttribute("sessionID");
				ServletContext context = getServlet().getServletContext();
				String strFlag="";	
				if(sessionId!=null)
					strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
				
				
				String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
				String p_report_format=request.getParameter("p_report_format");
				String p_form_date=request.getParameter("p_form_date");
				String p_to_date=request.getParameter("p_to_date");
				String flow=null;
				flow=request.getParameter("flow");
				if(flow.trim().equalsIgnoreCase("B"))
				{
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
				}
				
				String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
				p_form_date=CommonFunction.changeFormat(p_form_date);
				p_to_date=CommonFunction.changeFormat(p_to_date);
				String reportName="cmDashboard2CategoryDTL";
				
				String parent=null;
				String url=null;
				
				Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
				Map<Object,Object> hashMap = new HashMap<Object,Object>();				
				p_printed_date=	formate(p_printed_date);
				
				String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				
				hashMap.put("p_company_logo",p_company_logo);
				hashMap.put("p_company_name",p_company_name);
				hashMap.put("p_printed_date",p_printed_date);
				hashMap.put("p_printed_by",p_printed_by);
				hashMap.put("p_user_Id",p_user_Id);
				hashMap.put("p_report_format",p_report_format);
				hashMap.put("p_form_date",p_form_date);
				hashMap.put("p_to_date",p_to_date);
				hashMap.put("p_printed_date_range",p_printed_date_range);
				if(p_report_format.trim().equalsIgnoreCase("P"))
					hashMap.put("IS_IGNORE_PAGINATION",false);
				else
					hashMap.put("IS_IGNORE_PAGINATION",true);
				
				hashMap.put("p_app_path",p_app_path);

				
				//code for Drilling is start from here,it is only for HTML
				if(p_report_format.trim().equalsIgnoreCase("H"))
				{
					
					parent=request.getParameter("parent");
					url="/summaryReport.do";					
					ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					String s1=null;
					String s2=null;
					in.add(p_user_Id); // userID
					in.add(parent); //parent report
					in.add(reportName); // current report
					in.add(url); // current URL
					in.add(flow); // backForward
					out.add(s1);
					out.add(s2);
					try
					{
						logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
						outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
						s1=CommonFunction.checkNull(outMessages.get(0));
						s2=CommonFunction.checkNull(outMessages.get(1));
						logger.info("s1      : "+s1);
					    logger.info("s2      : "+s2);
					}
					catch (Exception e) 
					{e.printStackTrace();
					e=null; }
					finally{
						in.clear();
						in=null;
						out.clear();
						out=null;
						outMessages.clear();
						outMessages=null;
						s1=null;
						s2=null;
						flow=null;
						url=null;
					}	
				}
				//Drilling code end
				
				logger.info("p_company_logo       :  "+p_company_logo);
				logger.info("p_company_name       :  "+p_company_name);
				logger.info("p_printed_date       :  "+p_printed_date);
				logger.info("p_printed_by         :  "+p_printed_by);
				logger.info("p_user_Id            :  "+p_user_Id);
				logger.info("p_report_format      :  "+p_report_format);
				logger.info("p_form_date          :  "+p_form_date);
				logger.info("p_to_date            :  "+p_to_date);
				logger.info("p_printed_date_range :  "+p_printed_date_range);
				logger.info("report Name  :  "+ reportName + ".jasper");
				logger.info("p_app_path       :  "+p_app_path);
				
				//mradul starts for payout reports
				String dbType=resource.getString("lbl.dbType");
				String reportPath="/reports/";
				
				if(dbType.equalsIgnoreCase("MSSQL"))
					reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
				else
					reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
				
				InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
				JasperPrint jasperPrint = null;
				
				try
				{
					jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
					if(p_report_format.equals("P"))
						methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
					if(p_report_format.equals("E"))				
						methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
				    if(p_report_format.equals("H"))				
						methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
					hashMap.clear();
					form.reset(mapping, request);
					p_company_logo=null;
					p_company_name=null;
					p_printed_date=null;
					p_printed_by=null;					
					p_user_Id=null;
					p_report_format=null;
					p_form_date=null;
					p_to_date=null;
					p_printed_date_range=null;
					reportName=null;
					reportPath=null;
					reportName=null;
					dbType=null;
					
				}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e=null;
		}
	return null;
}
	public ActionForward cm2ProductReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cm2ProductReport.");
			try
			{
					HttpSession session = request.getSession();
					//boolean flag=false;
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag="";	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
						
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cmDashboard2ProductDTL";
					String flow=null;
					String parent=null;
					String url=null;
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					hashMap.put("p_app_path",p_app_path);
					
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						flow=request.getParameter("flow");
						parent=request.getParameter("parent");
						url="/summaryReportAction.do?method=cm2CategoryReport&p_report_format="+p_report_format+"&p_form_date="+p_form_date+"&p_to_date="+p_to_date;
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();
						e=null; }
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
							url=null;
						}	
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_category_ID        :  "+p_category_ID);
					logger.info("p_category_Desc      :  "+p_category_Desc);
					logger.info("report Name  :  "+ reportName + ".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						e=null;
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						form.reset(mapping, request);
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;					
						p_user_Id=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_category_ID=null;
						p_printed_date_range=null;
						reportName=null;
						p_category_Desc=null;
						reportPath=null;
						reportName=null;
						dbType=null;
						
					}
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return null;
	}
	public ActionForward cm2StateReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cm2StateReport");
			try
			{
					HttpSession session = request.getSession();
					//boolean flag=false;
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag=null;	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					String flow=null;
					flow=request.getParameter("flow");
					if(CommonFunction.checkNull(flow).trim().equalsIgnoreCase("B"))
					{
						p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
						p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					}
					String p_product_ID=request.getParameter("p_product_ID");
					String p_product_Desc=request.getParameter("p_product_Desc");
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cmDashboard2StateDTL";
					
					String parent=null;
					String url=null;
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_product_ID",p_product_ID);
					hashMap.put("p_product_Desc",p_product_Desc);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					hashMap.put("p_app_path",p_app_path);
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						parent=request.getParameter("parent");
						url="/summaryReport.do";	
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();
						e=null; }
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
							url=null;
						}
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_product_ID         :  "+p_product_ID);
					logger.info("p_product_Desc       :  "+p_product_Desc);
					logger.info("p_category_ID        :  "+p_category_ID);
					logger.info("p_category_Desc      :  "+p_category_Desc);
					logger.info("report Name          :  "+ reportName+".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						form.reset(mapping, request);
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;					
						p_user_Id=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_category_ID=null;
						p_printed_date_range=null;
						reportName=null;
						p_category_Desc=null;
						reportPath=null;
						reportName=null;
						dbType=null;						
					}					
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e=null;
			}
		return null;
	}
	public ActionForward cm2BranchReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
			logger.info("In cm2BranchReport");
			try
			{
					HttpSession session = request.getSession();
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					String p_company_name =null;
					String p_user_Id =null;
					String p_printed_by=null;
					String p_printed_date =null;
					if(userobj!=null)
					{
						p_company_name = userobj.getConpanyName();
						p_user_Id=userobj.getUserId();
						p_printed_by=userobj.getUserName();
						p_printed_date=userobj.getBusinessdate();
					}
					else
					    return mapping.findForward("sessionOut");
					Object sessionId = session.getAttribute("sessionID");
					ServletContext context = getServlet().getServletContext();
					String strFlag="";	
					if(sessionId!=null)
						strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
					String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
					String p_report_format=request.getParameter("p_report_format");
					String p_form_date=request.getParameter("p_form_date");
					String p_to_date=request.getParameter("p_to_date");
					p_form_date=p_form_date.substring(8)+"-"+p_form_date.substring(5,7)+"-"+p_form_date.substring(0,4);
					p_to_date=p_to_date.substring(8)+"-"+p_to_date.substring(5,7)+"-"+p_to_date.substring(0,4);
					String p_product_ID=request.getParameter("p_product_ID");
					String p_product_Desc=request.getParameter("p_product_Desc");
					String p_state_ID=request.getParameter("p_state_ID");
					String p_state_Desc=request.getParameter("p_state_Desc");
					String p_category_ID=request.getParameter("p_category_ID");
					String p_category_Desc=request.getParameter("p_category_Desc");
					String p_printed_date_range="From  "+formate(p_form_date)+"  To "+formate(p_to_date)+" ";
					p_form_date=CommonFunction.changeFormat(p_form_date);
					p_to_date=CommonFunction.changeFormat(p_to_date);
					String reportName="cmDashboard2BranchDTL";
					String flow=null;
					String parent=null;
					String url=null;
					
					Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();		
					Map<Object,Object> hashMap = new HashMap<Object,Object>();				
					p_printed_date=	formate(p_printed_date);
					
					String p_app_path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					
					hashMap.put("p_company_logo",p_company_logo);
					hashMap.put("p_company_name",p_company_name);
					hashMap.put("p_printed_date",p_printed_date);
					hashMap.put("p_printed_by",p_printed_by);
					hashMap.put("p_user_Id",p_user_Id);
					hashMap.put("p_report_format",p_report_format);
					hashMap.put("p_form_date",p_form_date);
					hashMap.put("p_to_date",p_to_date);
					hashMap.put("p_printed_date_range",p_printed_date_range);
					if(p_report_format.trim().equalsIgnoreCase("P"))
						hashMap.put("IS_IGNORE_PAGINATION",false);
					else
						hashMap.put("IS_IGNORE_PAGINATION",true);
					hashMap.put("p_product_ID",p_product_ID);
					hashMap.put("p_product_Desc",p_product_Desc);
					hashMap.put("p_state_ID",p_state_ID);
					hashMap.put("p_state_Desc",p_state_Desc);
					hashMap.put("p_category_ID",p_category_ID);
					hashMap.put("p_category_Desc",p_category_Desc);
					hashMap.put("p_app_path",p_app_path);
					
					//code for Drilling is start from here,it is only for HTML
					if(p_report_format.trim().equalsIgnoreCase("H"))
					{
						flow=request.getParameter("flow");
						parent=request.getParameter("parent");
						url="/summaryReportAction.do?method=cm2StateReport&p_report_format=H&p_form_date="+p_form_date+"&p_to_date="+p_to_date+"&p_product_ID="+p_product_ID+"&p_product_Desc="+p_product_Desc+"&p_category_ID="+p_category_ID+"&p_category_Desc="+p_category_Desc;
						
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1=null;
						String s2=null;
						in.add(p_user_Id); // userID
						in.add(parent); //parent report
						in.add(reportName); // current report
						in.add(url); // current URL
						in.add(flow); // backForward
						out.add(s1);
						out.add(s2);
						try
						{
							logger.info("Generate_Report_Hierarchy("+in.toString()+""+out.toString()+")");
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("Generate_Report_Hierarchy",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("s1      : "+s1);
						    logger.info("s2      : "+s2);
						}
						catch (Exception e) 
						{e.printStackTrace();
						e=null; }	
						finally{
							in.clear();
							in=null;
							out.clear();
							out=null;
							outMessages.clear();
							outMessages=null;
							s1=null;
							s2=null;
							flow=null;
							url=null;
						}
					}
					//Drilling code end
					
					logger.info("p_company_logo       :  "+p_company_logo);
					logger.info("p_company_name       :  "+p_company_name);
					logger.info("p_printed_date       :  "+p_printed_date);
					logger.info("p_printed_by         :  "+p_printed_by);
					logger.info("p_user_Id            :  "+p_user_Id);
					logger.info("p_report_format      :  "+p_report_format);
					logger.info("p_form_date          :  "+p_form_date);
					logger.info("p_to_date            :  "+p_to_date);
					logger.info("p_printed_date_range :  "+p_printed_date_range);
					logger.info("p_product_ID         :  "+p_product_ID);
					logger.info("p_product_Desc       :  "+p_product_Desc);
					logger.info("p_state_ID           :  "+p_state_ID);
					logger.info("p_state_Desc         :  "+p_state_Desc);
					logger.info("report Name          :  "+ reportName+".jasper");
					logger.info("p_app_path       :  "+p_app_path);
					
					//mradul starts for payout reports
					String dbType=resource.getString("lbl.dbType");
					String reportPath="/reports/";
					
					if(dbType.equalsIgnoreCase("MSSQL"))
						reportPath=reportPath+"MSSQLREPORTS/Dashboard/";
					else
						reportPath=reportPath+"MYSQLREPORTS/Dashboard/";
					
					InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
					JasperPrint jasperPrint = null;
					
					try
					{
						jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
						if(p_report_format.equals("P"))
							methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
						if(p_report_format.equals("E"))				
							methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
					    if(p_report_format.equals("H"))				
							methodForHTML(reportName,hashMap,connectDatabase,response, jasperPrint,request,"");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
						hashMap.clear();
						form.reset(mapping, request);
						p_company_logo=null;
						p_company_name=null;
						p_printed_date=null;
						p_printed_by=null;					
						p_user_Id=null;
						p_report_format=null;
						p_form_date=null;
						p_to_date=null;
						p_category_ID=null;
						p_printed_date_range=null;
						reportName=null;
						p_category_Desc=null;
						reportPath=null;
						reportName=null;
						dbType=null;
						parent=null;
					}					
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e=null;
			}
		return null;
	}
	public void methodForPDF(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{
		    JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+"'.pdf");
			File f=new File(request.getRealPath("/reports") + "/" +reportName+"'.pdf");
			FileInputStream fin = new FileInputStream(f);
			ServletOutputStream outStream = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
			byte[] buffer = new byte[2048];
			int n = 0;
			while ((n = fin.read(buffer)) != -1) 
				outStream.write(buffer, 0, n);			
			outStream.flush();
			fin.close();
			outStream.close();
	}
	public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
			String excelReportFileName=reportName+"'.xls";		
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
	public void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request,String windowName)throws Exception
	{
		    PrintWriter out=response.getWriter();
		    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
		    out.append("<head><title>"+windowName+"</title></head>");
		    
			response.setContentType("text/html");
			
	        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
	        Map imagesMap = new HashMap();
	        float f1=1.1f;
	        request.getSession().setAttribute("IMAGES_MAP", imagesMap);
	        JRHtmlExporter exporter = new JRHtmlExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
	        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
	        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
	        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
	        exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
	        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
	        exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
	        ServletContext context = this.getServlet().getServletContext();
	        File reportFile = new File(context.getRealPath("/reports/Dashboard/"));
	        String image = reportFile.getPath();
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,image);
	        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,image + "/");
	        exporter.exportReport();  
	        out.close();
	        out.flush();
	}	
	public String formate(String date)
{
		String result=null;
		int m1=Integer.parseInt(date.substring(3,5));		
		switch(m1)
		{					
			case 1: result=date.substring(0,2)+"-Jan-"+date.substring(6);
					break;					
			case 2: result=date.substring(0,2)+"-Feb-"+date.substring(6);
					break;							
			case 3: result=date.substring(0,2)+"-Mar-"+date.substring(6);
					break;						
			case 4: result=date.substring(0,2)+"-Apr-"+date.substring(6);
					break;					
			case 5: result=date.substring(0,2)+"-May-"+date.substring(6);
					break;					
			case 6: result=date.substring(0,2)+"-Jun-"+date.substring(6);
					break;					
			case 7: result=date.substring(0,2)+"-Jul-"+date.substring(6);
					break;					
			case 8: result=date.substring(0,2)+"-Aug-"+date.substring(6);
					break;				
			case 9: result=date.substring(0,2)+"-Sep-"+date.substring(6);
					break;					
			case 10: result=date.substring(0,2)+"-Oct-"+date.substring(6);
					break;					
			case 11: result=date.substring(0,2)+"-Nov-"+date.substring(6);
					break;					
			case 12: result=date.substring(0,2)+"-Dec-"+date.substring(6);						
		}	
		date=null;
		return result;
	}
	
}