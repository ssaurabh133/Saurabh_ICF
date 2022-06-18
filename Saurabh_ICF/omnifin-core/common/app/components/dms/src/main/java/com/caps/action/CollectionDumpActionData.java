package com.caps.action;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.caps.dao.CollectionDumpDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;



public class CollectionDumpActionData extends Action 
{
private static final Logger logger = Logger.getLogger(CollectionDumpActionData.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{	
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		 String makerID ="";
			String businessDate ="";
	
				
		if(userobj!=null){
			makerID = userobj.getUserId();
			businessDate=userobj.getBusinessdate();				
		}
		else{
			logger.info("here execute method of  CollectionDumpActionData action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		//logger.info("maker id::::::::::::?????"+makerID);
		Object sessionId = session.getAttribute("sessionID");	

		ServletContext context = getServlet().getServletContext();
		String strFlag="";	

		if(sessionId!=null)
		{
			strFlag =UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			
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
		String fromDate=(String)daf.get("fromDate");
		String toDate=(String)daf.get("toDate");
		
		logger.info("value of from date is :::::::>>>>>>>>>"+fromDate);
		logger.info("value of to date is :::::::>>>>>>>>>"+toDate);	
		CollectionDumpDAO dao=(CollectionDumpDAO)DaoImplInstanceFactory.getDaoImplInstance(CollectionDumpDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ArrayList list2=dao.insertDump(makerID,businessDate,fromDate,toDate);
		logger.info("after insert methoh :::>>>>>>>>");

  	    logger.info("list2 size---------------"+list2.size());
     	

     	ServletOutputStream out=response.getOutputStream();

		WritableWorkbook workbook = Workbook.createWorkbook(out);
		response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=CollDumpReport.xls");
 	    workbook.createSheet("CollDumpReport", 0);
	
		 
		WritableSheet excelSheet =workbook.getSheet(0);
		
		
		
		
		if(!flag){        		

			excelSheet.addCell(new Label(0,0,"LOAN_NO"));
			excelSheet.addCell(new Label(1,0,"APPLICATION_NO"));
			excelSheet.addCell(new Label(2,0,"BRANCH"));
			excelSheet.addCell(new Label(3,0,"PRODUCT"));
			excelSheet.addCell(new Label(4,0,"SCHEME"));
			excelSheet.addCell(new Label(5,0,"LOAN_AMOUNT"));
			excelSheet.addCell(new Label(6,0,"TENURE"));
			excelSheet.addCell(new Label(7,0,"LOAN_STATUS"));
			excelSheet.addCell(new Label(8,0,"LOAN_MATURITY_DATE"));
			excelSheet.addCell(new Label(9,0,"MAKER_ID"));
			excelSheet.addCell(new Label(10,0,"MAKER_DATE"));
			excelSheet.addCell(new Label(11,0,"ASSET_DETAILS"));
			excelSheet.addCell(new Label(12,0,"CUSTOMER_NAME"));
			excelSheet.addCell(new Label(13,0,"CUSTOMER_CATEGORY"));
			excelSheet.addCell(new Label(14,0,"FATHER_NAME"));
			excelSheet.addCell(new Label(15,0,"RESIDENCE_ADDRESS"));
			excelSheet.addCell(new Label(16,0,"PINCODE"));
			excelSheet.addCell(new Label(17,0,"TELE1"));
			excelSheet.addCell(new Label(18,0,"TELE2"));
			excelSheet.addCell(new Label(19,0,"MOBILE"));
			excelSheet.addCell(new Label(20,0,"GAURANTOR_NAME"));
			excelSheet.addCell(new Label(21,0,"GAURANTOR_FATHER_NAME"));
			excelSheet.addCell(new Label(22,0,"GAURANTOR_ADDRESS"));// Rohit change for collection bugList
			excelSheet.addCell(new Label(23,0,"GAURANTOR_PINCODE"));
			excelSheet.addCell(new Label(24,0,"GAURANTOR_TEL1"));
			excelSheet.addCell(new Label(25,0,"GAURANTOR_TEL2"));
			excelSheet.addCell(new Label(26,0,"GAURANTOR_MOBILE"));
			excelSheet.addCell(new Label(27,0,"DISBURSAL_AMOUNT"));// Rohit change for collection bugList
			excelSheet.addCell(new Label(28,0,"DISBURSAL_STATUS"));
			excelSheet.addCell(new Label(29,0,"DISBURSAL_DATE"));
			excelSheet.addCell(new Label(30,0,"DUE_DAY"));
			excelSheet.addCell(new Label(31,0,"EMI_AMOUNT"));
			excelSheet.addCell(new Label(32,0,"OVERDUE_EMI_AMT"));
			excelSheet.addCell(new Label(33,0,"OD_POS"));
			excelSheet.addCell(new Label(34,0,"CURRENT_QUEUE"));
			excelSheet.addCell(new Label(35,0,"CURRENT_DPD"));
			excelSheet.addCell(new Label(36,0,"CURRENT_QUEUE_STATE"));
			excelSheet.addCell(new Label(37,0,"COLL_AMOUNT"));
			excelSheet.addCell(new Label(38,0,"LAST_COLL_DATE"));
			excelSheet.addCell(new Label(39,0,"MODE_OF_COLL"));
			excelSheet.addCell(new Label(40,0,"BKT_MGR"));
			excelSheet.addCell(new Label(41,0,"TEAM_LEAD"));
			excelSheet.addCell(new Label(42,0,"FOS"));
			excelSheet.addCell(new Label(43,0,"DEALING_EXECUTIVE"));
			excelSheet.addCell(new Label(44,0,"VENDOR_DEALER_NAME"));
			excelSheet.addCell(new Label(45,0,"CURRENT_EMI_NO"));
			excelSheet.addCell(new Label(46,0,"LPP"));
			excelSheet.addCell(new Label(47,0,"CBC"));
			excelSheet.addCell(new Label(48,0,"NO_OF_CHQ_BOUNCE"));
			excelSheet.addCell(new Label(49,0,"COLLETRAL_MONEY"));
			excelSheet.addCell(new Label(50,0,"POS"));
//    		excelSheet.addCell(new Label(61, 0, "Gaurantor Pincode	"));
//    		excelSheet.addCell(new Label(62, 0, "Gaurantor Tel1"));
//    		excelSheet.addCell(new Label(63, 0, "Gaurantor Tel2"));
//    		excelSheet.addCell(new Label(64, 0, "Gaurantor Mobile No."));
//    		excelSheet.addCell(new Label(65, 0, "Toal Over due"));
//    		excelSheet.addCell(new Label(66, 0, "Loan Pending"));
//    		excelSheet.addCell(new Label(67, 0, "SD"));
//    		excelSheet.addCell(new Label(68, 0, "Colletral Mony "));
//    		excelSheet.addCell(new Label(69, 0, "POS"));
//    		
//    		excelSheet.addCell(new Label(70, 0, "Net POS"));
//    		excelSheet.addCell(new Label(71, 0, "Proposal Status"));
//    		excelSheet.addCell(new Label(72, 0, "Proposal Case Status"));
//    		excelSheet.addCell(new Label(73, 0, "Termination Date"));
//    		excelSheet.addCell(new Label(74, 0, "Sec-138"));
//    		excelSheet.addCell(new Label(75, 0, "Arb(Arbitation)"));
//    		excelSheet.addCell(new Label(76, 0, "Section 420"));
//    		excelSheet.addCell(new Label(77, 0, "Section156(3)"));
//    		excelSheet.addCell(new Label(78, 0, "Section17 /9"));
//    		excelSheet.addCell(new Label(79, 0, "Civil Cases"));
//    		
//    		excelSheet.addCell(new Label(80, 0, "Mediation Cell"));
//    		excelSheet.addCell(new Label(81, 0, "Winding up"));
//    		excelSheet.addCell(new Label(82, 0, "Execution"));  
           		
        	   flag=true;
    	   }
	
	
		ArrayList subList = new ArrayList();
		ArrayList subList1 = new ArrayList();
		Integer seasoning = 0;
		int m=0;
        
        if(list2.size()>0)
		{
        	
			int row=1;
		for (int i = 0; i < list2.size(); i++) {

			subList = (ArrayList) list2.get(i);		
               
			
			if (subList.size() > 0) {				
				
 				excelSheet.addCell(new Label(0, row, CommonFunction.checkNull(subList.get(0))));
				excelSheet.addCell(new Label(1, row, CommonFunction.checkNull(subList.get(1))));
				excelSheet.addCell(new Label(2, row, CommonFunction.checkNull(subList.get(2))));
				excelSheet.addCell(new Label(3, row, CommonFunction.checkNull(subList.get(3))));
				excelSheet.addCell(new Label(4, row, CommonFunction.checkNull(subList.get(4))));
				excelSheet.addCell(new Label(5, row, CommonFunction.checkNull(subList.get(5))));
				excelSheet.addCell(new Label(6, row, CommonFunction.checkNull(subList.get(6))));
				excelSheet.addCell(new Label(7, row, CommonFunction.checkNull(subList.get(7))));
				excelSheet.addCell(new Label(8, row, CommonFunction.checkNull(subList.get(8)))); 	
       	    	excelSheet.addCell(new Label(9, row, CommonFunction.checkNull(subList.get(9))));      	
				excelSheet.addCell(new Label(10, row, CommonFunction.checkNull(subList.get(10))));
				
				excelSheet.addCell(new Label(11, row, CommonFunction.checkNull(subList.get(11))));
				excelSheet.addCell(new Label(12, row, CommonFunction.checkNull(subList.get(12))));
				excelSheet.addCell(new Label(13, row, CommonFunction.checkNull(subList.get(13))));
				excelSheet.addCell(new Label(14, row, CommonFunction.checkNull(subList.get(14))));
				excelSheet.addCell(new Label(15, row, CommonFunction.checkNull(subList.get(15))));
				excelSheet.addCell(new Label(16, row, CommonFunction.checkNull(subList.get(16))));
				excelSheet.addCell(new Label(17, row, CommonFunction.checkNull(subList.get(17))));
				excelSheet.addCell(new Label(18, row, CommonFunction.checkNull(subList.get(18))));
				excelSheet.addCell(new Label(19, row, CommonFunction.checkNull(subList.get(19))));
				excelSheet.addCell(new Label(20, row, CommonFunction.checkNull(subList.get(20))));
				
				excelSheet.addCell(new Label(21, row, CommonFunction.checkNull(subList.get(21))));
				excelSheet.addCell(new Label(22, row, CommonFunction.checkNull(subList.get(22))));
				excelSheet.addCell(new Label(23, row, CommonFunction.checkNull(subList.get(23))));
				excelSheet.addCell(new Label(24, row, CommonFunction.checkNull(subList.get(24))));
				excelSheet.addCell(new Label(25, row, CommonFunction.checkNull(subList.get(25))));
				excelSheet.addCell(new Label(26, row, CommonFunction.checkNull(subList.get(26))));
				excelSheet.addCell(new Label(27, row, CommonFunction.checkNull(subList.get(27))));
				excelSheet.addCell(new Label(28, row, CommonFunction.checkNull(subList.get(28))));
				excelSheet.addCell(new Label(29, row, CommonFunction.checkNull(subList.get(29))));
				excelSheet.addCell(new Label(30, row, CommonFunction.checkNull(subList.get(30))));	

				
				excelSheet.addCell(new Label(31, row, CommonFunction.checkNull(subList.get(31))));
				excelSheet.addCell(new Label(32, row, CommonFunction.checkNull(subList.get(32))));
				excelSheet.addCell(new Label(33, row, CommonFunction.checkNull(subList.get(33))));
				excelSheet.addCell(new Label(34, row, CommonFunction.checkNull(subList.get(34))));
				excelSheet.addCell(new Label(35, row, CommonFunction.checkNull(subList.get(35))));
				excelSheet.addCell(new Label(36, row, CommonFunction.checkNull(subList.get(36))));
				excelSheet.addCell(new Label(37, row, CommonFunction.checkNull(subList.get(37))));
				excelSheet.addCell(new Label(38, row, CommonFunction.checkNull(subList.get(38))));
				excelSheet.addCell(new Label(39, row, CommonFunction.checkNull(subList.get(39))));		
  	            excelSheet.addCell(new Label(40, row, CommonFunction.checkNull(subList.get(40))));
  
				excelSheet.addCell(new Label(41, row, CommonFunction.checkNull(subList.get(41))));
				excelSheet.addCell(new Label(42, row, CommonFunction.checkNull(subList.get(42))));
				excelSheet.addCell(new Label(43, row, CommonFunction.checkNull(subList.get(43))));
				excelSheet.addCell(new Label(44, row, CommonFunction.checkNull(subList.get(44))));
				excelSheet.addCell(new Label(45, row, CommonFunction.checkNull(subList.get(45))));
				excelSheet.addCell(new Label(46, row, CommonFunction.checkNull(subList.get(46))));
				excelSheet.addCell(new Label(47, row, CommonFunction.checkNull(subList.get(47))));
				excelSheet.addCell(new Label(48, row, CommonFunction.checkNull(subList.get(48))));
				excelSheet.addCell(new Label(49, row, CommonFunction.checkNull(subList.get(49))));
				excelSheet.addCell(new Label(50, row, CommonFunction.checkNull(subList.get(50))));
				
//				excelSheet.addCell(new Label(51, row, CommonFunction.checkNull(subList.get(51))));
//	    	    excelSheet.addCell(new Label(52, row, CommonFunction.checkNull(subList.get(52))));  
//				excelSheet.addCell(new Label(53, row, CommonFunction.checkNull(subList.get(53))));
//				excelSheet.addCell(new Label(54, row, CommonFunction.checkNull(subList.get(54))));
//				excelSheet.addCell(new Label(55, row, CommonFunction.checkNull(subList.get(55))));
//				excelSheet.addCell(new Label(56, row, CommonFunction.checkNull(subList.get(56))));
//				excelSheet.addCell(new Label(57, row, CommonFunction.checkNull(subList.get(57))));
//				excelSheet.addCell(new Label(58, row, CommonFunction.checkNull(subList.get(58))));	
//				excelSheet.addCell(new Label(59, row, CommonFunction.checkNull(subList.get(59))));
				
				
//				excelSheet.addCell(new Label(61, row, CommonFunction.checkNull(subList.get(61))));
//				excelSheet.addCell(new Label(62, row, CommonFunction.checkNull(subList.get(62))));
//				excelSheet.addCell(new Label(63, row, CommonFunction.checkNull(subList.get(63))));
//				excelSheet.addCell(new Label(64, row, CommonFunction.checkNull(subList.get(64))));	
//			    excelSheet.addCell(new Label(65, row, CommonFunction.checkNull(subList.get(65))));  
//				excelSheet.addCell(new Label(66, row, CommonFunction.checkNull(subList.get(66))));
//				excelSheet.addCell(new Label(67, row, CommonFunction.checkNull(subList.get(67))));
//				excelSheet.addCell(new Label(68, row, CommonFunction.checkNull(subList.get(68))));
//				excelSheet.addCell(new Label(69, row, CommonFunction.checkNull(subList.get(69))));
//				excelSheet.addCell(new Label(70, row, CommonFunction.checkNull(subList.get(70))));
//			
//				excelSheet.addCell(new Label(71, row, CommonFunction.checkNull(subList.get(71))));	
//				excelSheet.addCell(new Label(72, row, CommonFunction.checkNull(subList.get(72))));
//				excelSheet.addCell(new Label(73, row, CommonFunction.checkNull(subList.get(73))));
//				excelSheet.addCell(new Label(74, row, CommonFunction.checkNull(subList.get(74))));
//				excelSheet.addCell(new Label(75, row, CommonFunction.checkNull(subList.get(75))));
//				excelSheet.addCell(new Label(76, row, CommonFunction.checkNull(subList.get(76))));
//				excelSheet.addCell(new Label(77, row, CommonFunction.checkNull(subList.get(77))));	
//				excelSheet.addCell(new Label(78, row, CommonFunction.checkNull(subList.get(78))));
//				excelSheet.addCell(new Label(79, row, CommonFunction.checkNull(subList.get(79))));	
//				excelSheet.addCell(new Label(80, row, CommonFunction.checkNull(subList.get(80))));
//				
//				excelSheet.addCell(new Label(81, row, CommonFunction.checkNull(subList.get(81))));
//				excelSheet.addCell(new Label(82, row, CommonFunction.checkNull(subList.get(82))));
			
				
				row++;
			
			}
			
		}//end of for loop
		
		}//end of outer If
		 
        if(flag){        	
         workbook.write();
		 workbook.close(); 		
        }


        out.flush();
        out.close();
    	

        
		return null;
		  }
	
}
	




