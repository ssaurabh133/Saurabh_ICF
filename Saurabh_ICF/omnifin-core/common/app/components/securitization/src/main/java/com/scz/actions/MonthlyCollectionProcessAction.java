package com.scz.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.DateFormator;
import com.scz.vo.MonthlyCollectionReoportVO;
import com.scz.vo.MonthlyCollectionVO;
import com.scz.vo.PaymentAllocationReportExcelDataVO;
import com.scz.vo.PaymentAllocationReportVO;

public class MonthlyCollectionProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(FutureFlowProcessAction.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	static String dbType=resource.getString("lbl.dbType");
	
	public ActionForward generateReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
	 logger.info("In FutureFlowBehindAction   ");
	 HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId =null;
		String businessDate =null;
		String strFlag=null;
		int currentPageLink = 0;
		
		if(userobj!=null){
			userId = userobj.getUserId();
			businessDate=userobj.getBusinessdate();
		}else{
			logger.info(" in submitPoolIdUpload method of PoolIdMakerProcessAction action the session is out----------------");
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

		logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
		
		if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
		}
		logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
		 
		
		MonthlyCollectionVO monthlyCollVO = new MonthlyCollectionVO();
		DynaValidatorForm MonthlyCollectionDynaValidatorFormBean=(DynaValidatorForm)form;	
		org.apache.commons.beanutils.BeanUtils.copyProperties(monthlyCollVO, MonthlyCollectionDynaValidatorFormBean);
		
		logger.info("hello... " + DateFormator.dmyToYMD(monthlyCollVO.getMonth())+".........."+ monthlyCollVO.getPoolID());
		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages =new ArrayList();
		ArrayList mainList =new ArrayList();
		String s1="";
		String s2="";
		in.add(monthlyCollVO.getPoolID());
		in.add(DateFormator.dmyToYMD(monthlyCollVO.getMonth()));
		in.add(userId);
		in.add(monthlyCollVO.getMonthType());
		out.add(s1);
		out.add(s2);
		try
		{
			logger.info("The IN Parameter " +in.toString());
			outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_Monthly_Collection_Report",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("After calling SCZ_Monthly_Collection_Report proc S1 and S2 "+s1+" - "+s2);
		}catch (Exception e) {
			logger.info("An Error..."+e);
		}
		
		String date = DateFormator.dmyToYMD(monthlyCollVO.getMonth());
		date=(date.substring(0,8)+"%");
		Session session1 = HibernateSessionFactory.currentSession();
		Transaction transaction = session1.beginTransaction();
		logger.info(".hel "+date);
		if(s1.equalsIgnoreCase("S")){
			
			String hql = "select loanId, openingOverdue,billingForMonth,collectionForMonth,monthOfReport " +
			"from MonthlyCollectionReoportVO where poolId="+Integer.parseInt(monthlyCollVO.getPoolID())+" and monthOfReport like '"+date+"'";
			Query query = session1.createQuery(hql);
			List list = query.list();
			mainList = new ArrayList(list);
			logger.info("query is "+hql);
			 /* Criteria criteria =  session1.createCriteria(MonthlyCollectionReoportVO.class);
			  criteria.add(Restrictions.and(Restrictions.eq("poolId",Integer.parseInt(monthlyCollVO.getPoolID())), Restrictions.like("monthOfReport", date)));
			  List l = criteria.list();
			  mainList = new ArrayList(l);*/
		}
		
		
		logger.info("list is this .... Rudra"+ mainList);
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();      
        HSSFSheet firstSheet = workbook.createSheet("Monthly Collection");
        HSSFRow row1 = firstSheet.createRow(0);
        
        row1.createCell(0).setCellValue("SR. NO."); 
        row1.createCell(1).setCellValue("CASE NUMBER"); 
        row1.createCell(2).setCellValue("OPENING OVERDUE"); 
       	row1.createCell(3).setCellValue("BILLING FOR MONTH");
       	row1.createCell(4).setCellValue("COLLECTION FOR MONTH");
       	row1.createCell(5).setCellValue("CLOSING OVERDUE");
       	
       	HSSFRow row=null;
		int m=1,i=1;
		String sb="";
		Iterator it = mainList.iterator();
		while(it.hasNext())
		{
			row= firstSheet.createRow(i);
		Object ob = it.next();
		Object[] objects = (Object[])ob;
		 	row.createCell(0).setCellValue(m++);						
		 	row.createCell(1).setCellValue(CommonFunction.checkNull(objects[0]));
		 	row.createCell(2).setCellValue(CommonFunction.checkNull(objects[1]));
		 	row.createCell(3).setCellValue(CommonFunction.checkNull(objects[2]));
		 	row.createCell(4).setCellValue(CommonFunction.checkNull(objects[3]));
		 	row.createCell(5).setCellValue(CommonFunction.checkNull(objects[4]));
		 	i++;
			row=null;
		}
		ServletOutputStream out1= null; 
        try 
        {  
			response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=MonthlyCollection.xls");
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
		in.add("Monthly Collection");
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
