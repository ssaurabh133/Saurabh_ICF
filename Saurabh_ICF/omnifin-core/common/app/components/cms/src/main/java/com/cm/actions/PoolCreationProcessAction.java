package com.cm.actions;

import java.io.IOException;
import java.io.InputStream;
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
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.DownloadDAO;
import com.cm.vo.PoolCreationForCMVO;
import com.cm.dao.PoolIDDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PoolCreationProcessAction extends DispatchAction{
	
	private static final Logger logger = Logger.getLogger(PoolCreationProcessAction.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	
	public ActionForward generatePoolReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In  PoolCreationProcessAction  generateReport()"); 
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String bDate="";
		String Userid=userobj.getUserId();
		logger.info(" getBusinessDate():-"+bDate);
		if(userobj!=null){
			bDate=userobj.getBusinessdate();
		}else{
			logger.info(" in generatePoolReport method of PoolCreationProcessAction action the session is out----------------");
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
		//UserObject sessUser=(UserObject)session.getAttribute("userobject");
		

		DynaValidatorForm PoolCreationDynaValidatorForm = (DynaValidatorForm)form;
		//CreditManagementDAO dao = new CreditManagementDAOImpl();
		//DownloadDAO dao = new DownloadDAOImpl();
		DownloadDAO dao=(DownloadDAO)DaoImplInstanceFactory.getDaoImplInstance(DownloadDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		PoolCreationForCMVO poolvo = new PoolCreationForCMVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(poolvo, PoolCreationDynaValidatorForm);
//		PrintWriter out = response.getWriter();
		String cutOffDate=poolvo.getCutOffDate();
		cutOffDate=CommonFunction.changeFormat(cutOffDate);
		if(CommonFunction.checkNull(cutOffDate).trim().equalsIgnoreCase(""))
			cutOffDate="0000-00-00";
		poolvo.setCutOffDate(cutOffDate);
     	ArrayList  poolList= dao.generatePoolReport(poolvo,Userid);     	
     	logger.info("poolList size---------------"+poolList.size());
     	
     	HSSFWorkbook workbook = new HSSFWorkbook();      
        HSSFSheet firstSheet = workbook.createSheet("Sheet 1");
        HSSFRow row1 = firstSheet.createRow(0);
        
        row1.createCell(0).setCellValue("LOAN_ID"); 
        row1.createCell(1).setCellValue("AGREEMENT_NO"); 
        row1.createCell(2).setCellValue("LEGACY_LOAN_NO"); 
       	row1.createCell(3).setCellValue("NAME_OF_CUSTOMER");
    	row1.createCell(4).setCellValue("ADDRESS");
    	row1.createCell(5).setCellValue("NATURE_OF_ASSET");
    	row1.createCell(6).setCellValue("ASSET_DESCRIPTION");
    	row1.createCell(7).setCellValue("ASSET_MANUFACTURER");
    	row1.createCell(8).setCellValue("NEW_USED");
    	row1.createCell(9).setCellValue("MODE_OF_EMI(PDC/ECS/Cash)");
    	row1.createCell(10).setCellValue("NO_OF_PDC_OBTAINED");
    	row1.createCell(11).setCellValue("LOAN_TENURE");
    	row1.createCell(12).setCellValue("INSTALLMENT_PATTERN");
    	row1.createCell(13).setCellValue("APPLICANT_FINANCIALS_DOCS");
    	row1.createCell(14).setCellValue("GUARANTOR_FINANCIALS_DOCS");
    	row1.createCell(15).setCellValue("SECTOR_TYPE");    	
    	row1.createCell(16).setCellValue("KYC_ADDRESS_PROOF(WITH DETAIL)APPLICANT");
    	row1.createCell(17).setCellValue("KYC_ID_PROOF(WITH DETAIL)APPLICANT");
    	row1.createCell(18).setCellValue("KYC_SIGNATURE_PROOF(WITH_DETAIL)APPLICANT");
   		row1.createCell(19).setCellValue("KYC_ADDRESS_PROOF(WITH DETAIL)GUARANTOR");
    	row1.createCell(20).setCellValue("KYC_ID_PROOF(WITH DETAIL)GUARANTOR");
    	row1.createCell(21).setCellValue("KYC_SIGNATURE_PROOF(WITH_DETAIL)GUARANTOR");   		  		
   		row1.createCell(22).setCellValue("LOAN_AMOUNT");
   		row1.createCell(23).setCellValue("DISBURSAL_DATE");
   		row1.createCell(24).setCellValue("FIRST_EMI_DATE");
   		row1.createCell(25).setCellValue("LAST_EMI_DATE");
   		row1.createCell(26).setCellValue("FIRST_EMI_MONTH");
   		row1.createCell(27).setCellValue("LAST_EMI_MONTH");	
        row1.createCell(28).setCellValue("ADV_EMI");	   		
   		row1.createCell(29).setCellValue("SEASONING");
		row1.createCell(30).setCellValue("REMAINING_TENURE(FUTURE_DUE)");	        	 
		row1.createCell(31).setCellValue("EMI_AMT");
   		row1.createCell(32).setCellValue("INTEREST_RATE_OF_THE_LOAN");	           		
   		row1.createCell(33).setCellValue("LOAN_PRODUCT");
   		row1.createCell(34).setCellValue("LOAN_SCHEME");	
   		row1.createCell(35).setCellValue("LOAN_CUSTOMER_CONSTITUTION");
   	    row1.createCell(36).setCellValue("LOAN_CUSTOMER_BUSINESS_SEGMENT");
        row1.createCell(37).setCellValue("LOAN_INDUSTRY");
   	    row1.createCell(38).setCellValue("LOAN_SUB_INDUSTRY");	           		
   		row1.createCell(39).setCellValue("LOAN_MATURITY_DATE");      			
   		row1.createCell(40).setCellValue("LOAN_INSTL_NUM");
   		row1.createCell(41).setCellValue("LOAN_ADV_EMI_NUM");
        row1.createCell(42).setCellValue("FIXED_INT_RATE");	
		row1.createCell(43).setCellValue("LOAN_DISBURSAL_STATUS");
	    row1.createCell(44).setCellValue("LOAN_NPA_FLAG");	
	    row1.createCell(45).setCellValue("LOAN_STATUS");
		row1.createCell(46).setCellValue("LOAN_DPD");	
		row1.createCell(47).setCellValue("LOAN_DPD_STRING");
		row1.createCell(48).setCellValue("LOAN_ADV_EMI_AMOUNT");	
		row1.createCell(49).setCellValue("LOAN_BALANCE_PRINCIPAL");
        row1.createCell(50).setCellValue("LOAN_OVERDUE_PRINCIPAL");	
		row1.createCell(51).setCellValue("LOAN_RECEIVED_PRINCIPAL");
		row1.createCell(52).setCellValue("LOAN_OVERDUE_INSTL_NUM");
		row1.createCell(53).setCellValue("LOAN_OVERDUE_AMOUNT");
		row1.createCell(54).setCellValue("LOAN_BALANCE_INSTL_NUM	");
		row1.createCell(55).setCellValue("LOAN_BALANCE_INSTL_AMOUNT");
		row1.createCell(56).setCellValue("LOAN_BRANCH");
		row1.createCell(57).setCellValue("ADVANCE_EMI");
		row1.createCell(58).setCellValue("SD_AMOUNT");
		row1.createCell(59).setCellValue("PENDING_SD_AMOUNT");
		row1.createCell(60).setCellValue("UNDISBURESD");
		row1.createCell(61).setCellValue("AGREED_VALUE");
		row1.createCell(62).setCellValue("INTEREST_AMOUNT_RECEIVED_TILL_DATE");
		ArrayList subList = new ArrayList();
		Integer seasoning = 0;
		HSSFRow row=null;
		int m=0;
		int size = poolList.size();
		for(int i=1;i<=size;i++)
		{
			row= firstSheet.createRow(i);
            // HSSFCell cell = row.createCell(0); 
        	 subList = (ArrayList) poolList.get(i-1);             
            row.createCell(0).setCellValue(CommonFunction.checkNull(subList.get(0)));
			row.createCell(1).setCellValue(CommonFunction.checkNull(subList.get(1)));
			row.createCell(2).setCellValue(CommonFunction.checkNull(subList.get(2)));
			row.createCell(3).setCellValue(CommonFunction.checkNull(subList.get(3)));
			row.createCell(4).setCellValue(CommonFunction.checkNull(subList.get(4)));
			row.createCell(5).setCellValue(CommonFunction.checkNull(subList.get(5)));
			row.createCell(6).setCellValue(CommonFunction.checkNull(subList.get(6)));
			row.createCell(7).setCellValue(CommonFunction.checkNull(subList.get(7)));
 	    	row.createCell(8).setCellValue(CommonFunction.checkNull(subList.get(8)));
 	       	row.createCell(9).setCellValue(CommonFunction.checkNull(subList.get(9))); 	    	
         	row.createCell(10).setCellValue(CommonFunction.checkNull(subList.get(10)));
			row.createCell(11).setCellValue(CommonFunction.checkNull(subList.get(11)));
 	    	row.createCell(12).setCellValue(CommonFunction.checkNull(subList.get(12)));
			row.createCell(13).setCellValue(CommonFunction.checkNull(subList.get(13)));					
			row.createCell(14).setCellValue(CommonFunction.checkNull(subList.get(14)));
			row.createCell(15).setCellValue(CommonFunction.checkNull(subList.get(15)));
			row.createCell(16).setCellValue(CommonFunction.checkNull(subList.get(16)));
			row.createCell(17).setCellValue(CommonFunction.checkNull(subList.get(17)));
			row.createCell(18).setCellValue(CommonFunction.checkNull(subList.get(18)));
			row.createCell(19).setCellValue(CommonFunction.checkNull(subList.get(19)));
			row.createCell(20).setCellValue(CommonFunction.checkNull(subList.get(20)));
			row.createCell(21).setCellValue(CommonFunction.checkNull(subList.get(21)));
			if(CommonFunction.checkNull(subList.get(22)).equalsIgnoreCase(""))
				row.createCell(22).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(22).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(22))));
			
			row.createCell(23).setCellValue(CommonFunction.checkNull(subList.get(23)));
			row.createCell(24).setCellValue(CommonFunction.checkNull(subList.get(24)));
			row.createCell(25).setCellValue(CommonFunction.checkNull(subList.get(25)));
			row.createCell(26).setCellValue(CommonFunction.checkNull(subList.get(26)));
			row.createCell(27).setCellValue(CommonFunction.checkNull(subList.get(27)));		
			if(CommonFunction.checkNull(subList.get(28)).equalsIgnoreCase(""))
				row.createCell(28).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(28).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(28))));
			
			row.createCell(29).setCellValue(CommonFunction.checkNull(subList.get(29)));
			row.createCell(30).setCellValue(CommonFunction.checkNull(subList.get(30)));
			if(CommonFunction.checkNull(subList.get(31)).equalsIgnoreCase(""))
				row.createCell(31).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(31).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(31))));
				
			row.createCell(32).setCellValue(CommonFunction.checkNull(subList.get(32)));	
			row.createCell(33).setCellValue(CommonFunction.checkNull(subList.get(33)));	
			row.createCell(34).setCellValue(CommonFunction.checkNull(subList.get(34)));	
			row.createCell(35).setCellValue(CommonFunction.checkNull(subList.get(35)));	
			row.createCell(36).setCellValue(CommonFunction.checkNull(subList.get(36)));	
			row.createCell(37).setCellValue(CommonFunction.checkNull(subList.get(37)));	
			row.createCell(38).setCellValue(CommonFunction.checkNull(subList.get(38)));	
			row.createCell(39).setCellValue(CommonFunction.checkNull(subList.get(39)));	
			row.createCell(40).setCellValue(CommonFunction.checkNull(subList.get(40)));	
			row.createCell(41).setCellValue(CommonFunction.checkNull(subList.get(41)));	
			if(CommonFunction.checkNull(subList.get(42)).equalsIgnoreCase(""))
				row.createCell(42).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(42).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(42))));
			row.createCell(43).setCellValue(CommonFunction.checkNull(subList.get(43)));
			row.createCell(44).setCellValue(CommonFunction.checkNull(subList.get(44)));
			row.createCell(45).setCellValue(CommonFunction.checkNull(subList.get(45)));
			row.createCell(46).setCellValue(CommonFunction.checkNull(subList.get(46)));
			row.createCell(47).setCellValue(CommonFunction.checkNull(subList.get(47)));
			if(CommonFunction.checkNull(subList.get(48)).equalsIgnoreCase(""))
				row.createCell(48).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(48).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(48))));
			if(CommonFunction.checkNull(subList.get(49)).equalsIgnoreCase(""))
				row.createCell(49).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(49).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(49))));	
			if(CommonFunction.checkNull(subList.get(50)).equalsIgnoreCase(""))
				row.createCell(50).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(50).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(50))));	
			if(CommonFunction.checkNull(subList.get(51)).equalsIgnoreCase(""))
				row.createCell(51).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(51).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(51))));
			row.createCell(52).setCellValue(CommonFunction.checkNull(subList.get(52)));
			if(CommonFunction.checkNull(subList.get(53)).equalsIgnoreCase(""))
				row.createCell(53).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(53).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(53))));	
			row.createCell(54).setCellValue(CommonFunction.checkNull(subList.get(54)));				
			if(CommonFunction.checkNull(subList.get(55)).equalsIgnoreCase(""))
				row.createCell(55).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(55).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(55))));
			row.createCell(56).setCellValue(CommonFunction.checkNull(subList.get(56)));
			row.createCell(57).setCellValue(CommonFunction.checkNull(subList.get(57)));	
			if(CommonFunction.checkNull(subList.get(58)).equalsIgnoreCase(""))
				row.createCell(58).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(58).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(58))));
			if(CommonFunction.checkNull(subList.get(59)).equalsIgnoreCase(""))
				row.createCell(59).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(59).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(59))));
			if(CommonFunction.checkNull(subList.get(60)).equalsIgnoreCase(""))
				row.createCell(60).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(60).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(60))));
			if(CommonFunction.checkNull(subList.get(61)).equalsIgnoreCase(""))
				row.createCell(61).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(61).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(61))));
			if(CommonFunction.checkNull(subList.get(62)).equalsIgnoreCase(""))
				row.createCell(62).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(62).setCellValue(Float.parseFloat(CommonFunction.checkNull(subList.get(62))));
			row=null;  
			subList=null;
		}
        //FileOutputStream fileOutputStream = null;  
		ServletOutputStream out= null; 
        try 
        {  
			/*Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String p_printed_date = formatter.format(currentDate.getTime());
			p_printed_date=p_printed_date.replace("-","_");
			p_printed_date=p_printed_date.replace(" ","_");
			p_printed_date=p_printed_date.replace(":","_");
            fileOutputStream = new FileOutputStream(new File("C:\\PoolCreation"+p_printed_date+".xls"));  
            workbook.write(fileOutputStream); */
        	response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=PoolCreation.xls");
            out=response.getOutputStream();
        	workbook.write(out);
         } 
        catch (Exception e) 
        {  e.printStackTrace();  } 
        finally
        {  
            if (out != null) 
            {  
                try 
                {  
                	out.flush();  
                	out.close();  
                } 
                catch (IOException e) 
                {  e.printStackTrace();}  
            }  
        }
      // code for image processing 
        /*finally
        {  
            if (fileOutputStream != null) 
            {  
                try 
                {  
                    fileOutputStream.flush();  
                    fileOutputStream.close();  
                } 
                catch (IOException e) 
                {  e.printStackTrace();}  
            }  
        }*/
	return null;
}
	
	

	
	public ActionForward errorLogPoolIdMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		    logger.info("In errorLogPoolDownload()..Action. ");
			HttpSession session=request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			String userId="";
			if(userobj!=null){
				  userId=userobj.getUserId();	
			}else{
				logger.info(" in errorLogPoolIdMaker method of PoolCreationProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		   	    
			//CreditManagementDAO service=new CreditManagementDAOImpl();
			PoolIDDAO service=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass());
			

		    ArrayList list=service.downLoadPoolErrorLog(request,response,userId);
		    logger.info("poolList size---------------"+list.size());
		    boolean flag=false;
	     	WorkbookSettings wbSettings = new WorkbookSettings();
			 
			
			WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=PoolErroreport.xls");
	 	     workbook.createSheet("MY SHEET_1", 0);
	 	
		
			 
			WritableSheet excelSheet =workbook.getSheet(0);
			
			StringBuffer xlReport = new StringBuffer();
			ArrayList subList = new ArrayList();
			ArrayList subList1 = new ArrayList();
			String disbursalStatus="";
			String customerType="";
			
	      
			if(!flag){ 
				
								
				excelSheet.addCell(new Label(0, 0, "LOAN_ID"));
				excelSheet.addCell(new Label(1, 0, "LOAN_PRODUCT"));
				excelSheet.addCell(new Label(2, 0, "LOAN_SCHEME"));
				excelSheet.addCell(new Label(3, 0, "LOAN_CUSTOMER_ID"));
				excelSheet.addCell(new Label(4, 0, "LOAN_CUSTOMER_TYPE"));
				excelSheet.addCell(new Label(5, 0, "LOAN_CUSTOMER_CATEGORY"));
           		excelSheet.addCell(new Label(6, 0, "LOAN_CUSTOMER_CONSTITUTION"));
           	    excelSheet.addCell(new Label(7, 0, "LOAN_CUSTOMER_BUSINESS_SEGMENT"));                     	   
           	    excelSheet.addCell(new Label(8, 0, "LOAN_INDUSTRY"));           	   
           		excelSheet.addCell(new Label(9, 0, "LOAN_SUB_INDUSTRY"));	           		
           		excelSheet.addCell(new Label(10, 0, "LOAN_DISBURSAL_DATE"));      			
           		excelSheet.addCell(new Label(11, 0, "LOAN_MATURITY_DATE"));
           		excelSheet.addCell(new Label(12, 0, "LOAN_TENURE"));
                excelSheet.addCell(new Label(13, 0, "LOAN_BALANCE_TENURE"));	
                excelSheet.addCell(new Label(14, 0, "LOAN_INSTL_NUM"));
                excelSheet.addCell(new Label(15, 0, "LOAN_ADV_EMI_NUM"));
                excelSheet.addCell(new Label(16, 0, "LOAN_INT_RATE"));
        		excelSheet.addCell(new Label(17, 0, "LOAN_DISBURSAL_STATUS"));
        	    excelSheet.addCell(new Label(18, 0, "LOAN_NPA_FLAG"));	
        		excelSheet.addCell(new Label(19, 0, "LOAN_DPD"));	
        		excelSheet.addCell(new Label(20, 0, "LOAN_DPD_STRING"));
        		excelSheet.addCell(new Label(21, 0, "LOAN_ASSET_COST"));
        		excelSheet.addCell(new Label(22, 0, "LOAN_AMOUNT"));
        		excelSheet.addCell(new Label(23, 0, "LOAN_EMI"));
        		excelSheet.addCell(new Label(24, 0, "LOAN_ADV_EMI_AMOUNT"));	
        		excelSheet.addCell(new Label(25, 0, "LOAN_BALANCE_PRINCIPAL"));
                excelSheet.addCell(new Label(26, 0, "LOAN_OVERDUE_PRINCIPAL"));	
        		excelSheet.addCell(new Label(27, 0, "LOAN_RECEIVED_PRINCIPAL"));
        		excelSheet.addCell(new Label(28, 0, "LOAN_OVERDUE_INSTL_NUM"));
        		excelSheet.addCell(new Label(29, 0, "LOAN_OVERDUE_AMOUNT"));
        		excelSheet.addCell(new Label(30, 0, "LOAN_BALANCE_INSTL_NUM	"));
        		excelSheet.addCell(new Label(31, 0, "LOAN_BALANCE_INSTL_AMOUNT"));
        		excelSheet.addCell(new Label(32, 0, "REC_STATUS"));
        		excelSheet.addCell(new Label(33, 0, "REJECT_REASON"));

		  
    	   flag=true;
	   }
	        
	        if(list.size()>0)
			{
	        	
				int row=1;
			for (int i = 0; i < list.size(); i++) {

				subList = (ArrayList) list.get(i);
				 
	               
				
				if (subList.size() > 0) {
					
					
	 				excelSheet.addCell(new Label(0, row, CommonFunction.checkNull(subList.get(0))));
	 				excelSheet.addCell(new Label(1, row, CommonFunction.checkNull(subList.get(1))));
					excelSheet.addCell(new Label(2, row, CommonFunction.checkNull(subList.get(2))));
					excelSheet.addCell(new Label(3, row, CommonFunction.checkNull(subList.get(3))));
					
					if(CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase("I"))
			          {
				    customerType ="INDIVIDUAL";
	  	      	  }
			         else if(CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase("C"))
	  	      	  {
			        	 customerType ="CORPORATE";	  
	  	      	  }
	  	            excelSheet.addCell(new Label(4, row, CommonFunction.checkNull(customerType)));
	  
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
					if(CommonFunction.checkNull(subList.get(17)).trim().equalsIgnoreCase("N"))
	    	        {
	    		       disbursalStatus ="NO DISBURSAL";
		    	      	  }
				    else if(CommonFunction.checkNull(subList.get(17)).trim().equalsIgnoreCase("P"))
				           {
		    	      	  
				    	disbursalStatus ="PARTIAL";	  
		    	      	  }
		    	
	 	            else if(CommonFunction.checkNull(subList.get(17)).trim().equalsIgnoreCase("F"))
	 	             {
	    	    
	 	            	disbursalStatus ="FULL";	  
	    	    	  }
	  	    	      
		    	    
					excelSheet.addCell(new Label(17, row, CommonFunction.checkNull(disbursalStatus)));
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
						
					
					
					row++;
				
				
				}
				
			}//end of for loop
			
			}//end of outer If
			 
	        if(flag){
	         workbook.write();
			 workbook.close(); 
			 
	        }
	
			return null;
		}
	
	

	
	public ActionForward generatePool(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info("In  PoolCreationProcessAction  generatePool()"); 
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
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
		DynaValidatorForm dynaForm = (DynaValidatorForm)form;
		
		String reportType=dynaForm.getString("reportType");
		String poolType=dynaForm.getString("poolType");
		String poolID=dynaForm.getString("lbxPoolID");
		String instituteID=dynaForm.getString("lbxinstituteID");
		String reportName="";
		if(CommonFunction.checkNull(reportType).trim().equalsIgnoreCase("R"))
			reportName="poolreport";
		else
			reportName="poolDumpReport";
		
		//mradul starts
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
				
		Connection connectDatabase = ConnectionUploadDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		hashMap.put("p_pool_id",poolID);
		hashMap.put("institution_id",instituteID);
		hashMap.put("poolType",poolType);
		hashMap.put("IS_IGNORE_PAGINATION",true);
		
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			String excelReportFileName=reportName+".xls";		
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
		catch(Exception e)
		{
			e.printStackTrace();
		}
	    finally
		{
			ConnectionUploadDAO.closeConnection(connectDatabase, null);
			hashMap.clear();
		}
		return null;
	}	
}