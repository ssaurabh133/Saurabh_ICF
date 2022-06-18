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

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
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

import com.cm.actionform.ReportsForm;
import com.cm.dao.ChequeStatusUpdateDAO;
import com.cm.dao.CreditManagementDAO;
import com.cm.dao.DisbursalInitiationDAO;
import com.cm.dao.EarlyClosureDAO;
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.dao.ReportsDAO;
import com.cm.vo.ChequeStatusVO;
import com.cm.vo.ClosureVO;
import com.cm.vo.DisbursalSearchVO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class SearchCMBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(SearchCMBehindAction.class.getName());
//	CreditManagementDAO cdao = new CreditManagementDAOImpl();
	//change by sachin
	CreditManagementDAO cdao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);


	//end by sachin
	public ActionForward getSearchCMScreen(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In SearchCMBehindAction ......getSearchCMScreen() ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		session.setAttribute("strFlagDV", "strFlagDV");
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in getSearchCMScreen method of SearchCMBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
		}
		session.setAttribute("userId",userId);
		logger.info("userIDDDDDD------>>>>>>>" +userId);
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
		
		session.removeAttribute("pParentGroup");
		session.removeAttribute("strParentOption");
		//added by neeraj tripathi
		session.removeAttribute("editable");
		ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+reportdao.getClass());
		String defaultFormate=reportdao.getDefaultFormateSOA();
		String earlyClosureType=reportdao.getEarlyClosureType();
		session.setAttribute("defaultFormate",defaultFormate);	
		session.setAttribute("earlyClosureType",earlyClosureType);	
		return mapping.findForward("onSuccess");	
	
	}
	public ActionForward getSearchData(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		session.removeAttribute("financialDealId");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String branchId="";
		if(userobj!=null)
		{	
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in getSearchData method of SearchCMBehindAction action the session is out----------------");
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
		logger.info("In SearchCMBehindAction ......getSearchData() ");
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
			logger.info("In getSearchData  ");
		
		//	DisbursalInitiationDAO dao = new DisbursalInitiationDAOImpl();
			DisbursalInitiationDAO dao=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
		DynaValidatorForm SearchForCMDynaValidatorForm = (DynaValidatorForm)form;
		
		DisbursalSearchVO vo = new DisbursalSearchVO();
	
		vo.setBranchId(branchId);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, SearchForCMDynaValidatorForm);
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		
		ArrayList<DisbursalSearchVO> detailListGrid = dao.searchCMGrid(vo);
		request.setAttribute("true","true");
		request.setAttribute("list", detailListGrid);
		if((detailListGrid.size())==0)
		{
			request.setAttribute("datalist","datalist");
		}
		logger.info("detailListGrid    Size:---"+detailListGrid.size());
		if(detailListGrid.size()==1)
		{
			request.setAttribute("checked","checked");
		}
		return mapping.findForward("searchData");	
	
	}
	
	public ActionForward pdcStatusPresentation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
				logger.info(" In SearchCMBehindAction ..... pdcStatusPresentation()----");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId = "";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
				}else{
					logger.info(" in pdcStatusPresentation method of SearchCMBehindAction action the session is out----------------");
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
				int id=0;
				if(request.getAttribute("loanID")!=null)
				{
					id=Integer.parseInt(request.getAttribute("loanID").toString());
				}
				else if(request.getParameter("loanID")!=null)
				{
					id=Integer.parseInt(request.getParameter("loanID"));
					
				}
				logger.info(" ID is----"+id);
				//InstrumentCapturingDAO dao= new InstrumentCapturingDAOImpl();
				InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				String loanID=request.getParameter("loanID");
				
				logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
				String functionId="";
	
				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				flag = LockRecordCheck.lockCheck(userId,functionId,loanID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "Locked");
					request.setAttribute("recordId", loanID);
					request.setAttribute("maker", "maker");
					return mapping.getInputForward();
				}
				}
			     logger.info("Implementation class: "+cdao.getClass());
				ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
				ArrayList<InstructionCapMakerVO> arrList= dao.getValuesforInstrumentforloanViewer(id);
				logger.info("ArrayList is "+arrList.size());
				request.setAttribute("arryList", arryList);
				request.setAttribute("arrList", arrList);
				request.setAttribute("check", "check");
				request.setAttribute("hold","hold");
				String loanNumber = request.getParameter("loanNumberCM");
				String customerName = request.getParameter("customerNameCM");
				request.setAttribute("loanNumber",loanNumber);
				request.setAttribute("customerName",customerName);				
				return mapping.findForward("pdcStatus");
		}
	
	public ActionForward chequeStatusForLoanViewer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" In SearchCMBehindAction ..... chequeStatusForLoanViewer()----");
			
				HttpSession session =request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info(" in chequeStatusForLoanViewer method of SearchCMBehindAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
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
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				}
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				       String loanId=request.getParameter("loanId");
			String customerName=request.getParameter("customerName");
			String loanNumber=request.getParameter("loanNumber");
			request.setAttribute("loanId",loanId);
			request.setAttribute("customerName",customerName);
			request.setAttribute("loanNumber",loanNumber);
 			return mapping.findForward("chequesByPayment");		
	}
	
	public ActionForward searchChequeStatusForLoanViewer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" In SearchCMBehindAction ..... searchChequeStatusForLoanViewer()----");
			
				HttpSession session =request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String branch = "";
				if(userobj != null)
				{
					branch = userobj.getBranchId();
				}
				if(userobj==null){
					logger.info(" in searchChequeStatusForLoanViewer method of SearchCMBehindAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				ServletContext context = getServlet().getServletContext();
				String strFlag="";	
				if(sessionId!=null )
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
				DynaValidatorForm ChequeStatusDynaValidatorForm = (DynaValidatorForm)form;
				ChequeStatusVO chequeStatusVO=new ChequeStatusVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(chequeStatusVO, ChequeStatusDynaValidatorForm);
			
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
						currentPageLink=1;
				else
						currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				chequeStatusVO.setCurrentPageLink(currentPageLink);
				chequeStatusVO.setBranch(branch);
				//change by sachin

				ChequeStatusUpdateDAO dao=(ChequeStatusUpdateDAO)DaoImplInstanceFactory.getDaoImplInstance(ChequeStatusUpdateDAO.IDENTITY);
			    logger.info("Implementation class: "+dao.getClass());
				//end by sachin
//				ChequeStatusUpdateDAO dao = new ChequeStatusUpdateDAOImpl();
			    String loanId=request.getParameter("loanId");
			    String customerName=request.getParameter("customerName");
			    String loanNumber=request.getParameter("loanNumber");
			    request.setAttribute("loanId",loanId);
			    request.setAttribute("customerName",customerName);
			    request.setAttribute("loanNumber",loanNumber);   
			    //ArrayList list= dao.searchChequesByPayment(chequeStatusVO); 
			    ArrayList list= dao.searchChequeStatusForLoanViewer(chequeStatusVO,loanId); 
			    request.setAttribute("list",list);

 			return mapping.findForward("searchChequesByPayment");
		
	}
	public ActionForward openEarlyClosureDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info(" In SearchCMBehindAction ..... openEarlyClosureDetail()fore closure report ----");
		// Mradul starts method openEarlyClosureDetail() to open fore closure report;
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String bdate="";
		int companyId=0;
		String p_company_name="";
		String userID="";
		String branchId="";
		String p_printed_by="";
		if(userobj!=null)
		{
			bdate=userobj.getBusinessdate();
			companyId=userobj.getCompanyId();
			p_company_name=userobj.getConpanyName();
			userID=userobj.getUserId();
			branchId=userobj.getBranchId();
			p_printed_by=userobj.getUserName();
		}
		else
		{
			logger.info(" in openEarlyClosureDetail method of SearchCMBehindAction action the session is out----------------");
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
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String earlyClosureType=(String)session.getAttribute("earlyClosureType");
		if(CommonFunction.checkNull(earlyClosureType).trim().equalsIgnoreCase("Y"))
		{
			EarlyClosureDAO service = (EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);		
			// 	Amit Changes Starts
			String terminationId=CommonFunction.checkNull(request.getParameter("terminationId"));
			logger.info("loanId .............. "+loanId);
			logger.info("terminationId .............. "+terminationId);
			session.removeAttribute("closureButton");
			session.removeAttribute("earlyClosureLabel");
			ArrayList<ClosureVO> cvo=new ArrayList<ClosureVO>();
			if(terminationId.equalsIgnoreCase(""))
			{
				cvo=service.getClosureValues(Integer.parseInt(loanId),bdate);
				request.setAttribute("closureData", cvo);	
			}
			else
			{
				cvo = service.selectClosureData(loanId,terminationId);
				ClosureVO status = cvo.get(0);
				request.setAttribute("status",status.getRecStatus());
				request.setAttribute("type",status.getClosureType());
				request.setAttribute("closureDataDisabled", cvo);	
			}
			return mapping.findForward("earlyClosureDetail");
		}
		else
		{		
			String reportName="foreClosure_simulation_report";
			String as_on_date="";
			ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
			reportName=dao.getEarlyClosureReport(loanId);
			if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase(""))
				reportName="foreClosure_simulation_report";
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();       
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(companyId);
			in.add(Integer.parseInt(loanId));
			as_on_date=CommonFunction.changeFormat(bdate);
			in.add(as_on_date);
			in.add(userID);
			in.add(CommonFunction.checkNull(reportName).trim());
			out.add(s1);
			out.add(s2);  
			try
			{
				logger.info("Early_Closure_Detail_Report ("+in.toString()+","+out.toString());
				outMessages=(ArrayList) ConnectionDAO.callSP("Early_Closure_Detail_Report",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
		        logger.info("s1  : "+s1);
		        logger.info("s2  : "+s2);	
		        if(s1.equalsIgnoreCase("E"))
		        { 
		        	request.setAttribute("error", s2);
		        	logger.info("In Early_Closure_Detail_Report reports can't be generate ");  
					return null;
		        }
		    }
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally
			{
				in.clear();
				out.clear();
				outMessages.clear();
				in=null;
				out=null;
				outMessages=null;			
			}
			Connection connectDatabase = ConnectionDAO.getConnection();		
			Map<Object,Object> hashMap = new HashMap<Object,Object>();
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			String dbType=resource.getString("lbl.dbType");
			String reportPath="/reports/";
			if(dbType.equalsIgnoreCase("MSSQL"))
				reportPath=reportPath+"MSSQLREPORTS/";
			else
				reportPath=reportPath+"MYSQLREPORTS/";
			String p_msg="";
			String p_address1="";
			String p_email="";
			String p_email1="";
			String p_loan_id=loanId;
			String p_report_format=request.getParameter("reportFormate");
			if(CommonFunction.checkNull(p_report_format).trim().equalsIgnoreCase(""))
			p_report_format="P";
			String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
			ArrayList<ReportsForm> list=dao.getCompanyAddress(companyId);
			ReportsForm frm = new ReportsForm();
			frm=list.get(0);
			p_msg=frm.getMsg();
			p_address1=frm.getAddress1()+" "+frm.getCity()+" "+frm.getPincode()+" "+frm.getPhone()+" "+frm.getFax();
			p_email=frm.getEmail()+" "+frm.getWebsite();
			p_email1=frm.getEmail(); 
			String p_printed_date=formate(bdate);		
			hashMap.put("p_printed_by",p_printed_by );
			hashMap.put("p_company_name",p_company_name );
			hashMap.put("p_loan_id",p_loan_id );
			hashMap.put("p_company_logo",p_company_logo );
			hashMap.put("p_user_id",userID );
			hashMap.put("p_address1",p_address1 );
			hashMap.put("p_email",p_email );
			hashMap.put("p_email1",p_email1 );
			hashMap.put("p_printed_date",p_printed_date);	
			hashMap.put("p_effective_date",p_printed_date);		
			hashMap.put("IS_IGNORE_PAGINATION",true);
			logger.info("report Name  :  "+ reportName + ".jasper");
			InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
			JasperPrint jasperPrint = null;				
			try
			{
				jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
				if(p_report_format.equals("P"))
					methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				else if(p_report_format.equals("H"))				
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
	}
	


	public ActionForward getDuesRefundsValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("In SearchCMBehindAction Class---------getDuesRefundsValues()--->");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("closureDataDisabled");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyId="";
		if(userobj!=null)
		{
			companyId=userobj.getCompanyId()+"";
		}else{
			logger.info(" in getDuesRefundsValues method of SearchCMBehindAction action the session is out----------------");
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

		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String effectiveDate = CommonFunction.checkNull(request.getParameter("effectiveDate"));
		String closureType = CommonFunction.checkNull(request.getParameter("closureType"));
		logger.info("Inside SearchCMBehindAction... loan ID: "+loanId);
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		EarlyClosureDAO dao=(EarlyClosureDAO)DaoImplInstanceFactory.getDaoImplInstance(EarlyClosureDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<ClosureVO> duesRefundsList= dao.getDuesRefundsList(companyId,loanId,effectiveDate,closureType,"cs");
		request.setAttribute("duesRefundsList", duesRefundsList);
		return mapping.findForward("showData");
	}
	public ActionForward generateSOA(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{


		logger.info("In SearchCMBehindAction Class---------generateSOA()--->");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("closureDataDisabled");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		int companyId=0;
		String p_business_date="";
		String userID="";
		String p_company_name="";
		if(userobj!=null)
		{
			companyId=userobj.getCompanyId();
			userID=userobj.getUserId();
			p_business_date=userobj.getBusinessdate();
			p_company_name=userobj.getConpanyName();
		}
		else
		{
			logger.info(" in generateSOA method of SearchCMBehindAction action the session is out----------------");
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
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		Connection connectDatabase = ConnectionDAO.getConnection();
		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		
		String p_printed_date=formate(p_business_date);
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String p_loan_id=request.getParameter("loanId");		
		String p_report_format=request.getParameter("reportFormate");		
		String query="";
		ArrayList result=new ArrayList();
		p_business_date=CommonFunction.changeFormat(p_business_date);
		String fromDate=p_business_date;
    	String toDate=p_business_date;    	
    	String source=request.getParameter("source");
    	logger.info("Changed fromDate   : "+fromDate);
    	logger.info("Changed toDate     : "+toDate);
		ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+reportdao.getClass());
		//result=reportdao.generateSOA(companyId,p_loan_id,p_business_date,userID,source,fromDate,toDate,"ALL");
		//result=reportdao.generateSOA(companyId,p_loan_id,p_business_date,userID,source,fromDate,toDate,"CS");
		result=reportdao.generateSOAForCancelLoan(companyId,p_loan_id,p_business_date,userID,source,fromDate,toDate,"CS");
		if(result.size()>0)
		{
			String stage=(String)result.get(0);
			if(CommonFunction.checkNull(stage).trim().equalsIgnoreCase("E"))
			{
				hashMap.clear();
				ConnectionDAO.closeConnection(connectDatabase, null);				
				request.setAttribute("error", "Some exception occur,Please contact Administrator...");
				logger.info("In Generate_SOA reports can't be generate ");	
				return mapping.findForward("onSuccess");
			}
			else
			{
				query=(String)result.get(2);
				logger.info("Statement of Account Query  :  "+query);
			}
				
		}
		else
		{
			hashMap.clear();
			ConnectionDAO.closeConnection(connectDatabase, null);			
			request.setAttribute("error", "Some exception occur,Please contact Administrator...");
			logger.info("In Generate_SOA reports can't be generate ");	
			return mapping.findForward("onSuccess");
		}	
		hashMap.put("p_company_name",p_company_name );
		hashMap.put("p_printed_date",p_printed_date );
		hashMap.put("p_loan_id",p_loan_id );
		hashMap.put("p_company_logo",p_company_logo );
		hashMap.put("p_user_id",userID );
		hashMap.put("p_report_format",p_report_format );
		hashMap.put("query",query );
		if(CommonFunction.checkNull(p_report_format).trim().equalsIgnoreCase("H"))
			hashMap.put("IS_IGNORE_PAGINATION",true);	
		else
			hashMap.put("IS_IGNORE_PAGINATION",false);
		String reportName="statement_of_account";
		logger.info("report Name  :  "+ reportName + ".jasper");
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(p_report_format.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(p_report_format.equals("H"))				
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
	/*shivesh*/
	public ActionForward generateCloserepayment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{


		logger.info("In SearchCMBehindAction Class---------generateCloserepayment()--->");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("closureDataDisabled");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		int companyId=0;
		String p_business_date="";
		String userID="";
		String p_company_name="";
		if(userobj!=null)
		{
			companyId=userobj.getCompanyId();
			userID=userobj.getUserId();
			p_business_date=userobj.getBusinessdate();
			p_company_name=userobj.getConpanyName();
		}
		else
		{
			logger.info(" in generateCloserepayment method of SearchCMBehindAction action the session is out----------------");
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
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		Connection connectDatabase = ConnectionDAO.getConnection();
		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		
		String p_printed_date=formate(p_business_date);
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String p_loan_id=request.getParameter("loanId");		
		String p_report_format=request.getParameter("reportFormate");		
		String query="";
		ArrayList result=new ArrayList();
		p_business_date=CommonFunction.changeFormat(p_business_date);
		String fromDate=p_business_date;
    	String toDate=p_business_date;    	
    	String source=request.getParameter("source");
    	logger.info("Changed fromDate   : "+fromDate);
    	logger.info("Changed toDate     : "+toDate);
		ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+reportdao.getClass());
		
		
		hashMap.put("p_company_name",p_company_name );
		hashMap.put("p_printed_date",p_printed_date );
		hashMap.put("p_loan_id",p_loan_id );
		hashMap.put("p_company_logo",p_company_logo );
		hashMap.put("p_user_id",userID );
		hashMap.put("p_report_format",p_report_format );
		hashMap.put("p_business_date",p_business_date );
		if(CommonFunction.checkNull(p_report_format).trim().equalsIgnoreCase("H"))
			hashMap.put("IS_IGNORE_PAGINATION",true);	
		else
			hashMap.put("IS_IGNORE_PAGINATION",false);
		String reportName="rp_repayment_calculation_closed";
		logger.info("report Name  :  "+ reportName + ".jasper");
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(p_report_format.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(p_report_format.equals("H"))				
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
	/*shivesh*/
	public ActionForward generateAllocationDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		logger.info("In SearchCMBehindAction Class---------generateAllocationDetail()--->");		
		HttpSession session =  request.getSession();
		session.removeAttribute("closureDataDisabled");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		int companyId=0;
		String p_business_date="";
		String userID="";
		String p_company_name="";
		if(userobj!=null)
		{
			companyId=userobj.getCompanyId();
			userID=userobj.getUserId();
			p_business_date=userobj.getBusinessdate();
			p_company_name=userobj.getConpanyName();
		}
		else
		{
			logger.info(" in generateAllocationDetail method of SearchCMBehindAction action the session is out----------------");
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
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		
		String p_printed_date=formate(p_business_date);
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String p_loan_id=request.getParameter("loanId");		
		String p_report_format="P";		

		hashMap.put("p_company_name",p_company_name );
		hashMap.put("p_printed_date",p_printed_date );
		hashMap.put("p_loan_id",p_loan_id );
		hashMap.put("p_company_logo",p_company_logo );
		hashMap.put("p_user_id",userID );
		hashMap.put("p_report_format",p_report_format );
		hashMap.put("IS_IGNORE_PAGINATION",true);		
		String reportName="loan_allocation_detail";
		logger.info("report Name  :  "+ reportName + ".jasper");
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
		JasperPrint jasperPrint = null;				
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(p_report_format.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
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
	
	public void methodForPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
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
	String formate(String date)
	{
		String result="";
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
		return result;
	}
}
