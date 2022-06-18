package com.scz.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.apache.poi.hssf.util.Region;
import com.scz.actions.PoolIdAuthorProcessAction;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;
import com.scz.vo.PaymentAllocationReportExcelDataVO;
import com.scz.vo.PaymentAllocationReportVO;

public class PaymentAllocationProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PoolIdAuthorProcessAction.class.getName());
	 public ActionForward generateReportForPaymentAllocation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 logger.info("In generateReportForPaymentAllocation of PaymentAllocationProcessAction  ");
		 	HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId ="";
			String businessDate ="";
			String p_company_name ="";
			int compid =0;
			String p_printed_by="";
			if(userobj!=null)
			{
				userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
				p_company_name=userobj.getConpanyName();
				p_printed_by=userobj.getUserName();
				logger.info("company id is :: "+compid);
			}
			else{
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
			
			logger.info("generateReportForPaymentAllocation .............. Rudra ");
			
			PaymentAllocationReportVO paymentAllocationVO = new PaymentAllocationReportVO();
			DynaValidatorForm PaymentAllocationDynaValidatorFormBean=(DynaValidatorForm)form;	
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentAllocationVO, PaymentAllocationDynaValidatorFormBean);
			
			logger.info("generateReportForPaymentAllocation .............. Rudra "+ paymentAllocationVO.getPoolID());
			
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages =new ArrayList();
			ArrayList mainList =new ArrayList();
			String s1="";
			String s2="";
			logger.info("month  ...."+paymentAllocationVO.getMonth()+"       "+DateFormator.dmyToYMD(paymentAllocationVO.getMonth()));
			in.add(paymentAllocationVO.getPoolID());
			in.add(paymentAllocationVO.getPoolName());
			in.add(DateFormator.dmyToYMD(paymentAllocationVO.getPoolCreationDate()));
			String date = DateFormator.dmyToYMD(paymentAllocationVO.getMonth());
			in.add(date);
			in.add(userId);
			in.add(DateFormator.dmyToYMD(businessDate));
			in.add(paymentAllocationVO.getMonthType());
			out.add(s1);
			out.add(s2);
			logger.info("The IN Parameter " +in.toString());
			try
			{
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_Payment_Allocation_Report_Gen",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("After calling SCZ_Payment_Allocation_Report_Gen Procedure S1 and S2 "+s1+" - "+s2);
			}catch (Exception e) {
				logger.info("An Error..."+e);
			}
			//2014-02-12
			date=(date.substring(0,8)+"%");
			Session session1 = HibernateSessionFactory.currentSession();
			Transaction transaction = session1.beginTransaction();
			if(s1.equalsIgnoreCase("S")){
				  Criteria criteria =  session1.createCriteria(PaymentAllocationReportExcelDataVO.class);
				  criteria.add(Restrictions.and(Restrictions.eq("pool_id",Integer.parseInt(paymentAllocationVO.getPoolID())), Restrictions.like("month", date)));
				  				  
				  List l = criteria.list();
				  mainList = new ArrayList(l);
			}
			logger.info("list is this .... Rudra"+mainList);
			
			
			HSSFWorkbook workbook = new HSSFWorkbook();      
	        HSSFSheet firstSheet = workbook.createSheet("Payment Allocation");
	        HSSFRow row1 = firstSheet.createRow(0);
	        
	        row1.createCell(0).setCellValue("Sr. No."); 
	        row1.createCell(1).setCellValue("POOL NAME"); 
	        row1.createCell(2).setCellValue("Loan no"); 
	       	row1.createCell(3).setCellValue("Omni Fin Loan no");
	    	row1.createCell(4).setCellValue("Customer name");
	    	row1.createCell(5).setCellValue("Expiry Date");
	    	row1.createCell(6).setCellValue("Last date of Collection Month");
	    	row1.createCell(7).setCellValue("Opening Pos");
	    	row1.createCell(8).setCellValue("Opening Future Flow");
	    	
	    	row1.createCell(9).setCellValue("Opening Over due"); 
	        row1.createCell(10).setCellValue("Opening Over due Pos"); 
	       	row1.createCell(11).setCellValue("Opening Over due Int");
	    	row1.createCell(12).setCellValue("Actual EMI");
	    	row1.createCell(13).setCellValue("POS");
	    	
	    	row1.createCell(14).setCellValue("Total Int");
	    	
	    	firstSheet.addMergedRegion(new Region(0,(short)14,0,(short)15));
	    	
	    	row1.createCell(16).setCellValue("Collection Against Opening Over due");
	    	row1.createCell(17).setCellValue("Collection Against Opening Over due Pos");
	    	
	    	row1.createCell(18).setCellValue("Collection Against Opening Over due Int"); 
	        row1.createCell(19).setCellValue("Collection Against Current billing"); 
	       	row1.createCell(20).setCellValue("Collection Against Current billing  Pos");
	    	row1.createCell(21).setCellValue("Collection Against Current billing  Int");
	    	row1.createCell(22).setCellValue("Total Collection against pos");
	    	row1.createCell(23).setCellValue("Total Collection against Int");
	    	row1.createCell(24).setCellValue("Total Collection");
	    	row1.createCell(25).setCellValue("Excess Amount Other Than Emi");
	    	
	    	row1.createCell(26).setCellValue("Pre closure Amount"); 
	       // row1.createCell(27).setCellValue("Insurance Amount of collection month"); 
	       	row1.createCell(27).setCellValue("DPD");
	    	row1.createCell(28).setCellValue("Dpd Bucket");
	    	row1.createCell(29).setCellValue("Total Closing Over due");
	    	row1.createCell(30).setCellValue("Closing Over due Pos");
	    	//row1.createCell(32).setCellValue("Closing Over due Int with insurance amount");
	    	row1.createCell(31).setCellValue("Closing Interest");
	    	
	    	row1.createCell(32).setCellValue("Closing Future  Pos "); 
	        row1.createCell(33).setCellValue("Closing Future Flow"); 
	       	row1.createCell(34).setCellValue("Pos  at Customer End");
	    	row1.createCell(35).setCellValue("EMI  at Customer End");
	    	row1.createCell(36).setCellValue("Status");
	    	row1.createCell(37).setCellValue("Emi Pattern");
	    	row1.createCell(38).setCellValue("Date of Repo");
	    	row1.createCell(39).setCellValue("Vehicle Cateogry");
	    	
	    	row1.createCell(40).setCellValue("Make");
	    	row1.createCell(41).setCellValue("Classification");
	    	row1.createCell(42).setCellValue("Branch");
	    	row1.createCell(43).setCellValue("State");
	    	
	    	row1 = firstSheet.createRow(1);
	    	row1.createCell(14).setCellValue("Total Int As Per bank");
	    	row1.createCell(15).setCellValue("Total Int As Per FURTUNE");
	    	
	    	HSSFRow row=null;
			int m=1,i=2;
			String sb="";
			Iterator it = mainList.iterator();
			while(it.hasNext())
			{
				row= firstSheet.createRow(i);
			Object ob = it.next();
			PaymentAllocationReportExcelDataVO paymentAllocationTempVO = (PaymentAllocationReportExcelDataVO)ob;
			 	row.createCell(0).setCellValue(m++);						// A
			 	row.createCell(1).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getPool_name()));  		//B POOL NAME
			 																							
				row.createCell(2).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getLoan_no()));   	//C Loan no
				row.createCell(3).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getOmniFinLoanNo()));		//D Omni Fin Loan no
				row.createCell(4).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getCustomer_Name()));		//E Customer name
				row.createCell(5).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getExpiryDate()));		//F Expiry Date
				row.createCell(6).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getLastDateOfCollMonth()));		//G Last date of Collection Month
				row.createCell(7).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getOpening_POS()));		//H Opening Pos
		    	row.createCell(8).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getOpening_Future_Flow()));		//I Opening Future Flow		
		    																						
		    	row.createCell(9).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getOpening_Overdue()));		//J Opening Over due
				row.createCell(10).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getOpeningOverduePos()));		//K Opening Over due Pos
				row.createCell(11).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getOpeningOverdueInt()));		//L Opening Over due Int
				row.createCell(12).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getActualEMI()));		//M Actual EMI
				row.createCell(13).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getOpening_POS()));		//N  POS
				row.createCell(14).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getTotalIntAsPerBank()));		//O&P Total Int
				row.createCell(15).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getTotalIntAsPerAU()));		//	P
		    	row.createCell(16).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getCollectionAgainstOpeningOverdue()));		//Q Collection Against Opening Over due
		    																								
		    	row.createCell(17).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getCollectionAgainstOpeningOverduePOS()));		//R Collection Against Opening Over due Pos
				row.createCell(18).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getCollectionAgainstOpeningOverdueINT()));		//S Collection Against Opening Over due Int
				row.createCell(19).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getCollectionAgainstCurrentBilling()));		//T Collection Against Current billing
				row.createCell(20).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getCollectionAgainstCurrentBillingPOS()));		//U Collection Against Current billing  Pos
				row.createCell(21).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getCollectionAgainstCurrentBillingINT()));		//V Collection Against Current billing  Int
				row.createCell(22).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getTotalCollectionAgainstPOS()));		//W Total Collection against pos
		    	row.createCell(23).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getTotalCollectionAgainstINT()));		//X Total Collection against Int
		    																			
		    	row.createCell(24).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getTotalCollection()));		//Y Total Collection
				row.createCell(25).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getExcessAmountOtherThanEmi()));		//Z Excess Amount Other Than Emi
				row.createCell(26).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getPreClosureAmount()));		//AA Pre closure Amount
				//row.createCell(27).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getInsuranceAmountOfCollMonth()));		//AB Insurance Amount of collection month
				row.createCell(27).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getDpd()));		//AC DPD
				row.createCell(28).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getDpdBucket()));		//AD Dpd Bucket
		    	row.createCell(29).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getTotalClosingOverdue()));		//AE Total Closing Over due   
		    															
		    	row.createCell(30).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getClosingOverduePos()));		//AF Closing Over due Pos   
				//row.createCell(32).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getClosingOverdueIntWithInsuranceAmount()));		//AG Closing Over due Int with insurance amount
				row.createCell(31).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getTotalClosingOverdueExculdingInsurance()));		//AH Total Closing Over due Exculding insurance
				row.createCell(32).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getClosingFuturePos()));		//AI Closing Future  Pos    
				row.createCell(33).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getClosingFutureFlow()));		//AJ Closing Future Flow
				row.createCell(34).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getPosAtCustomerEnd()));		//AK Pos  at Customer End
		    	row.createCell(35).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getEmiAtCustomerEnd()));		//AL EMI  at Customer End
		    																							
		    	row.createCell(36).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getStatus()));		//AM Status
				row.createCell(37).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getEmiPattern()));		//AN Emi Pattern
				row.createCell(38).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getDateOfRepo()));		//AO Date of Repo
				row.createCell(39).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getVehicleCateogry()));		//AP Vehicle Cateogry
				row.createCell(40).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getMake()));		//AQ Make
				row.createCell(41).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getClassification()));		//AR Classification
		    	row.createCell(42).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getBranch()));		//AS Branch
		    																				
		    	row.createCell(43).setCellValue(CommonFunction.checkNull(paymentAllocationTempVO.getState()));		//AT State
		        i++;
				row=null;  
			}
			ServletOutputStream out1= null; 
	        try 
	        {  
				response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "attachment; filename=PaymentAllocationReport.xls");
	            out1=response.getOutputStream();
	        	workbook.write(out1);
	         } 
	        catch (Exception e) 
	        {  e.printStackTrace();  } 
	        finally
	        {  
	        	HibernateSessionFactory.closeSession();
	        	if (out != null) 
	            {  
	                try 
	                {  
	                	out1.flush();  
	                	out1.close();  
	                } 
	                catch (IOException e) 
	                {  e.printStackTrace();}  
	            }  
	        } 
	        return null;
	 }

	 public ActionForward checkValid(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 logger.info("In check Valid of PaymentAllocationProcessAction  ");
		 	HttpSession session = request.getSession();
		 	UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId ="";
			String businessDate ="";
			if(userobj!=null)
			{
				userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
			}
			else{
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
			
			PaymentAllocationReportVO paymentAllocationVO = new PaymentAllocationReportVO();
			DynaValidatorForm PaymentAllocationDynaValidatorFormBean=(DynaValidatorForm)form;	
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentAllocationVO, PaymentAllocationDynaValidatorFormBean);
			
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages =new ArrayList();
			ArrayList mainList =new ArrayList();
			String s1="";
			String s2=""; 
			
			in.add(paymentAllocationVO.getPoolID());
			in.add(DateFormator.dmyToYMD(paymentAllocationVO.getPoolCreationDate()));
			in.add(DateFormator.dmyToYMD(paymentAllocationVO.getMonth()));
			in.add("Payment Allocation");
			out.add(s1);
			out.add(s2);
			logger.info("The IN Parameter " +in.toString());
			outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("scz_date_difference",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("After scz_date_difference Procedure S1 and S2 "+s1+" - "+s2);
			
			if(s1.equals("S")||s1.equals("F")){
				request.setAttribute("msg1", s2);
				request.setAttribute("s1", s1);
			}else if(s1.equals("R")){
				request.setAttribute("ref", s2);
				request.setAttribute("s1", s1);
				request.setAttribute("refresh", "R");
			}else if(s1.equals("X")) {
				request.setAttribute("ref", s2);
				request.setAttribute("s1", "F");
			}else{
				request.setAttribute("msg", s2);
			}
			
			
			
		 	return mapping.findForward("success");
	 }
}
