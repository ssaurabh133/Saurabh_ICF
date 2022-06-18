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
import org.apache.poi.hssf.util.Region;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
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
import com.scz.vo.MonthlyDistributionVO;

public class MonthlyDistributionProcessAction extends DispatchAction{
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
		 
		
		MonthlyDistributionVO monthlyCollVO = new MonthlyDistributionVO();
		DynaValidatorForm MonthlyDistributionDynaValidatorFormBean=(DynaValidatorForm)form;	
		org.apache.commons.beanutils.BeanUtils.copyProperties(monthlyCollVO, MonthlyDistributionDynaValidatorFormBean);
		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages =new ArrayList();
		ArrayList mainList =new ArrayList();
		String s1="";
		String s2="";
		in.add(monthlyCollVO.getPoolID());
		in.add(userId);
		out.add(s1);
		out.add(s2);
		/*try
		{
			logger.info("Before calling SCZ_Monthly_Collection_Report proc S1 and S2 ");
			outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_Monthly_Distribution_Report",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("After calling SCZ_Monthly_Distribution_Report proc S1 and S2 "+s1+" - "+s2);
		}catch (Exception e) {
			logger.info("An Error..."+e);
		}
		
		String date = DateFormator.dmyToYMD(monthlyCollVO.getMonth());
		date=(date.substring(0,8)+"%");
		Session session1 = HibernateSessionFactory.currentSession();
		Transaction transaction = session1.beginTransaction();
		logger.info("Date  "+date);*/
		
		/*if(s1.equalsIgnoreCase("S")){
			  Criteria criteria =  session1.createCriteria(MonthlyCollectionReoportVO.class);
			  criteria.add(Restrictions.and(Restrictions.eq("poolId",Integer.parseInt(monthlyCollVO.getPoolID())), Restrictions.like("monthOfReport", date)));
			  List l = criteria.list();
			  mainList = new ArrayList(l);
		}*/
		
		
		logger.info("list is this .... Rudra"+ mainList);
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();      
        HSSFSheet firstSheet = workbook.createSheet("Monthly Collection");
        HSSFRow row1 = firstSheet.createRow(0);
        
        row1.createCell(0).setCellValue("SR. NO."); 
        row1.createCell(1).setCellValue("CASE NUMBER"); 
        row1.createCell(2).setCellValue("COLLECTION FOR THE MONTH"); 
       	row1.createCell(3).setCellValue("ALLOCATION FOR THE MONTH");
       	row1.createCell(4).setCellValue("ALLOCATION TO BANK PERCENTAGE");
       	firstSheet.addMergedRegion(new Region(0,(short)4,0,(short)5));
       	row1.createCell(6).setCellValue("ALLOCATION TO BANK AMOUNT");
       	firstSheet.addMergedRegion(new Region(0,(short)6,0,(short)9));
       	row1.createCell(10).setCellValue("OD AMOUNT");
       	row1.createCell(11).setCellValue("OD COLLECTED FROM CUSTOMER");
       	row1.createCell(12).setCellValue("OD PAYABLE TO BANK");
       	
       	row1 = firstSheet.createRow(1);
    	row1.createCell(4).setCellValue("PRINCIPAL RATIO");
    	row1.createCell(5).setCellValue("INTEREST RATIO");
    	row1.createCell(6).setCellValue("PRINCIPAL");
    	row1.createCell(7).setCellValue("INTEREST");
    	row1.createCell(8).setCellValue("SERVICE CHARGES");
    	row1.createCell(9).setCellValue("EMI");
    	
       	HSSFRow row=null;
		int m=1,i=2;
		String sb="";
		Iterator it = mainList.iterator();
		while(it.hasNext())
		{
			row= firstSheet.createRow(i);
		Object ob = it.next();
		MonthlyCollectionReoportVO monthlyCollRepoVO = (MonthlyCollectionReoportVO)ob;
		 	row.createCell(0).setCellValue(m++);						
		 	row.createCell(1).setCellValue(CommonFunction.checkNull(monthlyCollRepoVO.getPoolId()));
		 	row.createCell(2).setCellValue(CommonFunction.checkNull(monthlyCollRepoVO.getOpeningOverdue()));
		 	row.createCell(3).setCellValue(CommonFunction.checkNull(monthlyCollRepoVO.getBillingForMonth()));
		 	row.createCell(4).setCellValue(CommonFunction.checkNull(monthlyCollRepoVO.getCollectionForMonth()));
		 	row.createCell(5).setCellValue(CommonFunction.checkNull(monthlyCollRepoVO.getCloasingOverdue()));
		 	i++;
			row=null;
		}
		ServletOutputStream out1= null; 
        try 
        {  
			response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=MonthlyDistribution.xls");
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
