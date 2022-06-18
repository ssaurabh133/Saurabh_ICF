package com.scz.actions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.scz.actions.PoolIdAuthorProcessAction;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;
import com.scz.vo.FutureFlowReportTempVO;
import com.scz.vo.PaymentScheduleReportTempVO;
import com.scz.vo.PaymentScheduleReportVO;

public class PaymentScheduleProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PaymentScheduleProcessAction.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String collectionByCustomer=resource.getString("lbl.collectionByCustomer");
	String differentByCustomer=resource.getString("lbl.differentByCustomer");

	 public ActionForward generateReportForPaymentSchedule(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 logger.info("In generateReportForPaymentSchedule of PaymentScheduleProcessAction  ");
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
			
			logger.info("generateReportForPaymentSchedule .............. Rudra ");
			
			PaymentScheduleReportVO paymentScheduleVO = new PaymentScheduleReportVO();
			DynaValidatorForm PaymentScheduleDynaValidatorFormBean=(DynaValidatorForm)form;	
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentScheduleVO, PaymentScheduleDynaValidatorFormBean);
			
			logger.info("generateReportForPaymentSchedule .............. Rudra "+ paymentScheduleVO.getPoolID());
			
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages =new ArrayList();
			ArrayList mainList =new ArrayList();
			String s1="";
			String s2="";
			in.add(paymentScheduleVO.getPoolID());
			in.add(userId);
			out.add(s1);
			out.add(s2);
			try
			{
				outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_Payment_Schedule_Report_Gen",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("After calling SCZ_Payment_Schedule_Report_Gen Procedure S1 and S2 "+s1+" - "+s2);
			}catch (Exception e) {
				logger.info("An Error..."+e);
			}
			Session session1 = HibernateSessionFactory.currentSession();
			//Transaction transaction = session1.beginTransaction();
			if(s1.equalsIgnoreCase("S")){
				  Criteria criteria =  session1.createCriteria(PaymentScheduleReportTempVO.class);
				  //criteria.add(Restrictions.eq("maker_id", userId));
				  List l = criteria.list();
				  mainList = new ArrayList(l);
			}
			logger.info("list is this .... Rudra"+mainList);
			
			
			HSSFWorkbook workbook = new HSSFWorkbook();      
	        HSSFSheet firstSheet = workbook.createSheet("Sheet 1");
	        HSSFRow row1 = firstSheet.createRow(0);
	        
	        row1.createCell(0).setCellValue("Sr. No."); 
	        row1.createCell(1).setCellValue("ACCRUAL_MONTH"); 
	        row1.createCell(2).setCellValue("COLLECTION DATE"); 
	       	row1.createCell(3).setCellValue("TOTAL PRINCIPLE AMORT");
	    	row1.createCell(4).setCellValue("INTEREST");
	    	row1.createCell(5).setCellValue("RESIDUAL PRINCIPLE");
	    	row1.createCell(6).setCellValue("MONTHLY CASH FLOW (POS+INT)");
	    	row1.createCell(7).setCellValue(collectionByCustomer);
	    	row1.createCell(8).setCellValue(differentByCustomer);
	    	
	    	HSSFRow row=null;
			int m=1,i=1;
			String sb="";
			Iterator it = mainList.iterator();
			while(it.hasNext())
			{
				row= firstSheet.createRow(i);
			Object o = it.next();
			PaymentScheduleReportTempVO pdt = (PaymentScheduleReportTempVO)o;
			 	row.createCell(0).setCellValue(m++);
			 	if(sb==null || sb.equals("")){
			 		row.createCell(1).setCellValue(CommonFunction.checkNull(sb));
			 	}else{
			 		row.createCell(1).setCellValue(CommonFunction.checkNull(sb.substring(3, 11)));
			 		sb="";
			 	}
				sb=DateFormator.dateConvert(new String(pdt.getInstl_Date().toString()));
				row.createCell(2).setCellValue(CommonFunction.checkNull(sb));
				
				row.createCell(3).setCellValue(CommonFunction.checkNull(pdt.getTotal_Principle_Amort()));
				
				row.createCell(4).setCellValue(CommonFunction.checkNull(pdt.getInterest()));
				row.createCell(5).setCellValue(CommonFunction.checkNull(pdt.getResidual_Principle()));
				row.createCell(6).setCellValue(CommonFunction.checkNull(pdt.getMonthly_Cash_Flow()));
				row.createCell(7).setCellValue(CommonFunction.checkNull(pdt.getTotal_EMI()));
		    	row.createCell(8).setCellValue(CommonFunction.checkNull(pdt.getDifference()));
		        i++;
				row=null;  
			}
			ServletOutputStream out1= null; 
	        try 
	        {  
				response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "attachment; filename=PaymentScheduleReport.xls");
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
	 
	 
}
