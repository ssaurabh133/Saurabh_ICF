package com.scz.actions;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.scz.dao.PoolIDDAO;
import com.scz.daoImplMYSQL.PoolIDDAOImpl;
import com.scz.vo.PoolCreationForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.delegate.SCZ_PoolDownloadDelegate;

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
		DynaValidatorForm PoolCreationDynaValidatorForm = (DynaValidatorForm)form;

		PoolCreationForCMVO poolvo = new PoolCreationForCMVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(poolvo, PoolCreationDynaValidatorForm);
		String cutOffDate=poolvo.getCutOffDate();
		cutOffDate=CommonFunction.changeFormat(cutOffDate);
		if(CommonFunction.checkNull(cutOffDate).trim().equalsIgnoreCase("")){
			cutOffDate="0000-00-00";
		}
		poolvo.setCutOffDate(cutOffDate);
     	
		poolvo.setBusinessDate(bDate);
		//ArrayList  poolList= dao.generatePoolReport(poolvo,Userid);   // Rudra.... Changed this...
		
		ArrayList  poolList = SCZ_PoolDownloadDelegate.generatePoolReport(poolvo,Userid);   // Rudra.... calling through HiberNate...
		if(poolList.get(0).equals("success")){
			request.setAttribute("msg", "message");
		}
		
     	
		
		
		/*logger.info("poolList size---------------"+poolList.size());
     	
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
		int m=0,i=1;
		
		Iterator it = poolList.iterator();
		while(it.hasNext())
		{
			row= firstSheet.createRow(i);
		Object o = it.next();
		PoolDownloadTempVO pdt = (PoolDownloadTempVO)o;
		 	row.createCell(0).setCellValue(CommonFunction.checkNull(pdt.getLoan_id()));
			row.createCell(1).setCellValue(CommonFunction.checkNull(pdt.getLoan_no()));
			row.createCell(2).setCellValue(CommonFunction.checkNull(pdt.getLoan_reference_no()));
			row.createCell(3).setCellValue(CommonFunction.checkNull(pdt.getCustomer_name()));
			row.createCell(4).setCellValue(CommonFunction.checkNull(pdt.getAddress()));
			row.createCell(5).setCellValue(CommonFunction.checkNull(pdt.getAsset_collateral_class()));
			row.createCell(6).setCellValue(CommonFunction.checkNull(pdt.getAsset_collateral_desc()));
			row.createCell(7).setCellValue(CommonFunction.checkNull(pdt.getManufaturer_desc()));
	    	row.createCell(8).setCellValue(CommonFunction.checkNull(pdt.getAsset_new_old()));
	       	row.createCell(9).setCellValue(CommonFunction.checkNull(pdt.getRepayment_mode())); 	    	
	       	row.createCell(10).setCellValue(CommonFunction.checkNull(pdt.getPdc()));
			row.createCell(11).setCellValue(CommonFunction.checkNull(pdt.getLoan_tenure()));
	    	row.createCell(12).setCellValue(CommonFunction.checkNull(pdt.getInstallment_type()));
			row.createCell(13).setCellValue(CommonFunction.checkNull(pdt.getAppl_fin_doc()));					
			row.createCell(14).setCellValue(CommonFunction.checkNull(pdt.getGur_fin_doc()));
			row.createCell(15).setCellValue(CommonFunction.checkNull(pdt.getLoan_sector_type()));
			row.createCell(16).setCellValue(CommonFunction.checkNull(pdt.getAppl_address_proof()));
			row.createCell(17).setCellValue(CommonFunction.checkNull(pdt.getAppl_id_proof()));
			row.createCell(18).setCellValue(CommonFunction.checkNull(pdt.getAppl_signature_proof()));
			row.createCell(19).setCellValue(CommonFunction.checkNull(pdt.getGur_address_proof()));
			row.createCell(20).setCellValue(CommonFunction.checkNull(pdt.getGur_id_proof()));
			row.createCell(21).setCellValue(CommonFunction.checkNull(pdt.getGur_signature_proof()));
			if(CommonFunction.checkNull(pdt.getLoan_loan_amount()).equalsIgnoreCase(""))
				row.createCell(22).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(22).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_loan_amount())));
			
			row.createCell(23).setCellValue(CommonFunction.checkNull(pdt.getDisbursal_date()));
			row.createCell(24).setCellValue(CommonFunction.checkNull(pdt.getFirstemidate()));
			row.createCell(25).setCellValue(CommonFunction.checkNull(pdt.getLast_emi()));
			row.createCell(26).setCellValue(CommonFunction.checkNull(pdt.getFirst_emi_month()));
			row.createCell(27).setCellValue(CommonFunction.checkNull(pdt.getLast_emi_month()));		
			if(CommonFunction.checkNull(pdt.getInstl_amount()).equalsIgnoreCase(""))
				row.createCell(28).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(28).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getInstl_amount())));
			
			row.createCell(29).setCellValue(CommonFunction.checkNull(pdt.getSeasoning()));
			row.createCell(30).setCellValue(CommonFunction.checkNull(pdt.getRemaining_tenure()));
			if(CommonFunction.checkNull(pdt.getLoan_emi_amount()).equalsIgnoreCase(""))
				row.createCell(31).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(31).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_emi_amount())));
				
			row.createCell(32).setCellValue(CommonFunction.checkNull(pdt.getLoan_eff_rate()));	
			row.createCell(33).setCellValue(CommonFunction.checkNull(pdt.getProduct_desc()));	
			row.createCell(34).setCellValue(CommonFunction.checkNull(pdt.getScheme_desc()));	
			row.createCell(35).setCellValue(CommonFunction.checkNull(pdt.getDescription()));	
			row.createCell(36).setCellValue(CommonFunction.checkNull(pdt.getCustomer_business_segment()));	
			row.createCell(37).setCellValue(CommonFunction.checkNull(pdt.getIndustry_desc()));	
			row.createCell(38).setCellValue(CommonFunction.checkNull(pdt.getSub_industry_desc()));	
			row.createCell(39).setCellValue(CommonFunction.checkNull(pdt.getLoan_maturity_date()));	
			row.createCell(40).setCellValue(CommonFunction.checkNull(pdt.getLoan_no_of_installment()));	
			row.createCell(41).setCellValue(CommonFunction.checkNull(pdt.getLoan_advance_instl()));	
			if(CommonFunction.checkNull(pdt.getLoan_final_rate()).equalsIgnoreCase(""))
				row.createCell(42).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(42).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_final_rate())));
			row.createCell(43).setCellValue(CommonFunction.checkNull(pdt.getDisbursal_status()));
			row.createCell(44).setCellValue(CommonFunction.checkNull(pdt.getNpa_flag()));
			row.createCell(45).setCellValue(CommonFunction.checkNull(pdt.getLoan_status()));
			row.createCell(46).setCellValue(CommonFunction.checkNull(pdt.getLoan_dpd()));
			row.createCell(47).setCellValue(CommonFunction.checkNull(pdt.getLoan_dpd_string()));
			if(CommonFunction.checkNull(pdt.getLoanadvanceemiamount()).equalsIgnoreCase(""))
				row.createCell(48).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(48).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoanadvanceemiamount())));
			
			if(CommonFunction.checkNull(pdt.getLoan_balance_principal()).equalsIgnoreCase(""))
				row.createCell(49).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(49).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_balance_principal())));	
			
			if(CommonFunction.checkNull(pdt.getLoan_overdue_principal()).equalsIgnoreCase(""))
				row.createCell(50).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(50).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_overdue_principal())));	
			if(CommonFunction.checkNull(pdt.getLoan_received_principal()).equalsIgnoreCase(""))
				row.createCell(51).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(51).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_received_principal())));
			
			row.createCell(52).setCellValue(CommonFunction.checkNull(pdt.getLoan_overdue_instl_num()));
			
			if(CommonFunction.checkNull(pdt.getLoan_overdue_amount()).equalsIgnoreCase(""))
				row.createCell(53).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(53).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_overdue_amount())));	
			
			row.createCell(54).setCellValue(CommonFunction.checkNull(pdt.getLoan_balance_instl_no()));				
			
			if(CommonFunction.checkNull(pdt.getLoan_balance_instl_amount()).equalsIgnoreCase(""))
				row.createCell(55).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(55).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getLoan_balance_instl_amount())));
			
			row.createCell(56).setCellValue(CommonFunction.checkNull(pdt.getBranch_desc()));
			row.createCell(57).setCellValue(CommonFunction.checkNull(pdt.getAdvanceflg()));	
			
			if(CommonFunction.checkNull(pdt.getSdamt()).equalsIgnoreCase(""))
				row.createCell(58).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(58).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getSdamt())));
			
			if(CommonFunction.checkNull(pdt.getReceived_sdamt()).equalsIgnoreCase(""))
				row.createCell(59).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(59).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getReceived_sdamt())));
			if(CommonFunction.checkNull(pdt.getUndisbursed()).equalsIgnoreCase(""))
				row.createCell(60).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(60).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getUndisbursed())));
			if(CommonFunction.checkNull(pdt.getInterest_component()).equalsIgnoreCase(""))
				row.createCell(61).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(61).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getInterest_component())));
			
			if(CommonFunction.checkNull(pdt.getReceived_interest_amt()).equalsIgnoreCase(""))
				row.createCell(62).setCellValue(Float.parseFloat("0"));
			else
				row.createCell(62).setCellValue(Float.parseFloat(CommonFunction.checkNull(pdt.getReceived_interest_amt())));
			i++;
			row=null;  
			subList=null;
		}

		ServletOutputStream out= null; 
        try 
        {  
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
        }*/
		
     return mapping.findForward("success");
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
			// PoolIDDAO service=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
			PoolIDDAO service= new PoolIDDAOImpl();
			logger.info("Implementation class: "+service.getClass());
			DynaValidatorForm PoolCreationDynaValidatorForm = (DynaValidatorForm)form;
			PoolCreationForCMVO poolvo = new PoolCreationForCMVO();
			org.apache.commons.beanutils.BeanUtils.copyProperties(poolvo, PoolCreationDynaValidatorForm);
			logger.info("pool_id is "+poolvo.getPoolID());
		    ArrayList list=service.downLoadPoolErrorLog(request,response,userobj.getUserId(),poolvo.getPoolID());
		    logger.info("pool ID is ---------------"+poolvo.getPoolID());
		    logger.info("user ID is ---------------"+userobj.getUserId());
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
				
								
				/*excelSheet.addCell(new Label(0, 0, "LOAN_ID"));
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
        		excelSheet.addCell(new Label(33, 0, "REJECT_REASON"));*/
				excelSheet.addCell(new Label(0, 0, "LOAN ID"));
				excelSheet.addCell(new Label(1, 0, "POOL ID"));
				excelSheet.addCell(new Label(2, 0, "LOAN NO"));
				excelSheet.addCell(new Label(3, 0, "CUSTOMER ID"));
				excelSheet.addCell(new Label(4, 0, "REJECT REASON"));

		  
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
					excelSheet.addCell(new Label(4, row, CommonFunction.checkNull(subList.get(4))));
					
					/*if(CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase("I"))
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
						*/
					
					
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