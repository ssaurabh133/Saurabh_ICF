

package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.GenerateBankingDAO;
import com.cm.vo.GenerateReportVO;

public class GenerateReportDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(GenerateReportDispatchAction.class.getName());	
	public ActionForward generateReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside generateReport...........");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyName="";
		if(userobj==null)
		{
			logger.info("in  generateReport method of GenerateReportDispatchAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
			companyName=userobj.getConpanyName();
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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

		//PrintWriter out = response.getWriter();
		//HttpSession session=request.getSession();
		//UserObject sessUser=(UserObject)session.getAttribute("userobject");
		
		//String date=(sessUser.getBusinessdate());
		//logger.info(" paymentMakerForCMVO.getBusinessDate():-"+date);
//		logger.info("Printing type in ClosureSearchDispatchAction: "+request.getParameter("type"));
//		logger.info("Printing Status in ClosureSearchDispatchAction: "+status);
		
		//boolean flag=false;
		DynaValidatorForm GenerateReportDynaValidatorForm= (DynaValidatorForm)form;
		GenerateReportVO generateReportVO = new GenerateReportVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(generateReportVO, GenerateReportDynaValidatorForm);
		//GenerateBankingDAO service = new GenerateBankingDAOImpl();
		GenerateBankingDAO service=(GenerateBankingDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBankingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList reportDataList = service.generateReportDao(generateReportVO);
		//Yogesh ob=new Yogesh();
		//ArrayList reportDataList=ob.generateReportDao(generateReportVO);
		//int a=1233;
		//String s34 = String.format("%08d", a);
		//StringUtils.rightPad(s34,5);
		//LoggerMsg.info("................................... "+s34);
		logger.info("in generateReport reportDataList size---------------"+reportDataList.size());
		//
	//	File file = new File("D:/LPO/BulkAssign/Instrument_BankingReport_"+generateReportVO.getGenerateBankingDate()+".xls");
		  
		//WorkbookSettings wbSettings = new WorkbookSettings();
		//WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		WritableWorkbook workbook=null;
		ServletOutputStream out=null;
        if(reportDataList.size()>0)
		{
        	out=response.getOutputStream();
        	workbook = Workbook.createWorkbook(out);
    		response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=BankReport.xls");
    		
    		int	sheet=0;
    		WritableSheet excelSheet =null;
    		StringBuffer xlReport = new StringBuffer();
    		ArrayList subList = new ArrayList();
    		ArrayList subList1 = new ArrayList();
    		
			int row=1;
			for (int i = 0; i < reportDataList.size(); i++) {
				

			subList = (ArrayList) reportDataList.get(i);
		//	logger.info("In sublist size: "+ subList.size());
		//	logger.info("In sublist size: "+ subList.toString());
			if (subList.size() > 0) {
				
				if(!flag){
					
	        		//LoggerMsg.info("generateReportVO.getGenerateBankingDate().... " +generateReportVO.getGenerateBankingDate());
					if(CommonFunction.checkNull(subList.get(28)).equalsIgnoreCase("0")){
						workbook.createSheet(CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate(), sheet);
		            	LoggerMsg.info("sheet name : "+CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate());
		            }else{
		            	workbook.createSheet(CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate()+"_S"+"_"+CommonFunction.checkNull(subList.get(29)), sheet);
		            	LoggerMsg.info("sheet name : "+CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate()+"_S"+"_"+CommonFunction.checkNull(subList.get(29)));
		            }
	            	 
	            	WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
	         	    WritableCellFormat cfobj=new WritableCellFormat(wfobj);
	         	    //cfobj.setWrap(true);
	         	    
	            	excelSheet = workbook.getSheet(sheet);
	            	
	            	if(CommonFunction.checkNull(subList.get(9)).equals("ECS") || CommonFunction.checkNull(subList.get(9)).equals("DIRECT DEBIT"))
	            	{
	            		excelSheet.addCell(new Label(0, 0, "ECS LOCATION",cfobj)); 
						excelSheet.addCell(new Label(1, 0, "ECS Transaction Code",cfobj)); 
		            	excelSheet.addCell(new Label(2, 0, "CUSTOMER BANK MICR CODE",cfobj));
		            	excelSheet.addCell(new Label(3, 0, "CUSTOMER ACCOUNT TYPE",cfobj));
		            	excelSheet.addCell(new Label(4, 0, "LEDGER FOLIO NUMBER",cfobj));
		           		excelSheet.addCell(new Label(5, 0, "CUSTOMER BANK ACCOUNT NUMBER",cfobj));
		           		excelSheet.addCell(new Label(6, 0, "CUSTOMER NAME",cfobj));
		           		excelSheet.addCell(new Label(7, 0, "SPONSOR BANK BRANCH CODE",cfobj));
		           		excelSheet.addCell(new Label(8, 0, "UTILITY NUMBER",cfobj));
		           		excelSheet.addCell(new Label(9, 0, "USER NAME",cfobj));
		           		excelSheet.addCell(new Label(10, 0, "UNIQUE REFERANCE NUMBER, THIS IS FROM RE-PAYMENT SCHEDULE",cfobj));
		           		excelSheet.addCell(new Label(11, 0, "EMI AMOUNT",cfobj));
		           		excelSheet.addCell(new Label(12, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(13, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(14, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(15, 0, "FILLER (TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(16, 0, "NO OF ECS",cfobj));
		           		excelSheet.addCell(new Label(17, 0, "EMI DUE DATE",cfobj));
		           		excelSheet.addCell(new Label(18, 0, "PDC/ECS GIVEN BY",cfobj));
		           }
	            	else if(CommonFunction.checkNull(subList.get(9)).equals("ACH"))
					{
	            		excelSheet.addCell(new Label(0, 0, "ACH LOCATION",cfobj)); 
						excelSheet.addCell(new Label(1, 0, "ACH Transaction Code",cfobj)); 
		            	excelSheet.addCell(new Label(2, 0, "CUSTOMER BANK MICR CODE",cfobj));
		            	excelSheet.addCell(new Label(3, 0, "CUSTOMER ACCOUNT TYPE",cfobj));
		            	excelSheet.addCell(new Label(4, 0, "LEDGER FOLIO NUMBER",cfobj));
		           		excelSheet.addCell(new Label(5, 0, "CUSTOMER BANK ACCOUNT NUMBER",cfobj));
		           		excelSheet.addCell(new Label(6, 0, "CUSTOMER NAME",cfobj));
		           		excelSheet.addCell(new Label(7, 0, "SPONSOR BANK BRANCH CODE",cfobj));
		           		excelSheet.addCell(new Label(8, 0, "UTILITY NUMBER",cfobj));
		           		excelSheet.addCell(new Label(9, 0, "USER NAME",cfobj));
		           		excelSheet.addCell(new Label(10, 0, "UNIQUE REFERANCE NUMBER, THIS IS FROM RE-PAYMENT SCHEDULE",cfobj));
		           		excelSheet.addCell(new Label(11, 0, "EMI AMOUNT",cfobj));
		           		excelSheet.addCell(new Label(12, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(13, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(14, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(15, 0, "FILLER (TO BE KEPT BLANK BY USER)",cfobj));
		           		excelSheet.addCell(new Label(16, 0, "NO OF ACH",cfobj));
		           		excelSheet.addCell(new Label(17, 0, "EMI DUE DATE",cfobj));
		           		excelSheet.addCell(new Label(18, 0, "PDC/ECS GIVEN BY",cfobj));
					}
					
	            	else
	            	{
	            		excelSheet.addCell(new Label(0, 0, "LOAN NUMBER",cfobj));		// 8
	            		excelSheet.addCell(new Label(1, 0, "CUSTOMER NAME",cfobj));		//9
	            		excelSheet.addCell(new Label(2, 0, "INSTRUMENT TYPE",cfobj));	//NEW
	            		excelSheet.addCell(new Label(3, 0, "AMOUNT",cfobj));			//2
	            		excelSheet.addCell(new Label(4, 0, "CHEQUE NUMBER",cfobj));		//3
	            		excelSheet.addCell(new Label(5, 0, "CHEQUE DATE",cfobj));		//4
	            		excelSheet.addCell(new Label(6, 0, "EMI DUE DATE",cfobj));		//15
	            		excelSheet.addCell(new Label(7, 0, "BANK CODE",cfobj));			//5
	            		excelSheet.addCell(new Label(8, 0, "CLIENT CODE",cfobj));		//6
	            		excelSheet.addCell(new Label(9, 0, "DEPOSITION DATE",cfobj));	//7
	            		excelSheet.addCell(new Label(10, 0, "B-BRN",cfobj));			//10
	            	//	excelSheet.addCell(new Label(11, 0, "",cfobj));			//11
	            		excelSheet.addCell(new Label(12, 0, "NO OF PDC",cfobj));		//12
	            		excelSheet.addCell(new Label(13, 0, "PDC LOCATION",cfobj));		//13
	            		excelSheet.addCell(new Label(14, 0, "NO OF PDC",cfobj));		//16
	            		excelSheet.addCell(new Label(15, 0, "PDC/ECS GIVEN BY",cfobj));	//17
	            		excelSheet.addCell(new Label(16, 0, "DEPOSIT  SLIP",cfobj));     // 0
	            	//	excelSheet.addCell(new Label(17, 0, "PICK UP LOCATION",cfobj));	//1
	            		excelSheet.addCell(new Label(18, 0, "MAKER REMARKS",cfobj));	//14
            	}
            	   flag=true;
            	   
	        	   }
 				
				if(i<=reportDataList.size()-1){
					if(i<=0)
					{
						subList1 = (ArrayList) reportDataList.get(i);
					}
					else
					{
						subList1 = (ArrayList) reportDataList.get(i-1);
					}
					
					
					logger.info(" subList: " +subList.get(10));
				
					if(!(CommonFunction.checkNull(subList.get(10)).equals(CommonFunction.checkNull(subList1.get(10))))  ||  !(CommonFunction.checkNull(subList.get(9)).equals(CommonFunction.checkNull(subList1.get(9)))) || !(CommonFunction.checkNull(subList.get(28)).equals(CommonFunction.checkNull(subList1.get(28)))) || !(CommonFunction.checkNull(subList.get(29)).equals(CommonFunction.checkNull(subList1.get(29)))))
					{
	
						sheet++;	
					    row=1;
					    if(CommonFunction.checkNull(subList.get(28)).equalsIgnoreCase("0")){
					    workbook.createSheet(CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate(), sheet);
					    logger.info(" subList sheet name  : " +CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate());
					    }else{
						    workbook.createSheet(CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate()+"_S"+"_"+CommonFunction.checkNull(subList.get(29)), sheet);
						    logger.info(" subList sheet name  : " +CommonFunction.checkNull(subList.get(9))+"_"+CommonFunction.checkNull(subList.get(10))+"_"+generateReportVO.getGenerateBankingDate()+"_S"+"_"+CommonFunction.checkNull(subList.get(29)));
					    }
					    			WritableFont wfobj=new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
					         	    WritableCellFormat cfobj=new WritableCellFormat(wfobj);
									excelSheet = workbook.getSheet(sheet);
									 //excelSheet.addCell(new Label(0, 0, "PDC_INSTRUMENT_ID"));
									if(CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase("ECS") || CommonFunction.checkNull(subList.get(9)).equals("DIRECT DEBIT"))
									{
										excelSheet.addCell(new Label(0, 0, "ECS LOCATION",cfobj)); 
										excelSheet.addCell(new Label(1, 0, "ECS Transaction Code",cfobj)); 
						            	excelSheet.addCell(new Label(2, 0, "CUSTOMER BANK MICR CODE",cfobj));
						            	excelSheet.addCell(new Label(3, 0, "CUSTOMER ACCOUNT TYPE",cfobj));
						            	excelSheet.addCell(new Label(4, 0, "LEDGER FOLIO NUMBER",cfobj));
						           		excelSheet.addCell(new Label(5, 0, "CUSTOMER BANK ACCOUNT NUMBER",cfobj));
						           		excelSheet.addCell(new Label(6, 0, "CUSTOMER NAME",cfobj));
						           		excelSheet.addCell(new Label(7, 0, "SPONSOR BANK BRANCH CODE",cfobj));
						           		excelSheet.addCell(new Label(8, 0, "UTILITY NUMBER",cfobj));
						           		excelSheet.addCell(new Label(9, 0, "USER NAME",cfobj));
						           		excelSheet.addCell(new Label(10, 0, "UNIQUE REFERANCE NUMBER, THIS IS FROM RE-PAYMENT SCHEDULE",cfobj));
						           		excelSheet.addCell(new Label(11, 0, "EMI AMOUNT",cfobj));
						           		excelSheet.addCell(new Label(12, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(13, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(14, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(15, 0, "FILLER (TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(16, 0, "NO OF ECS",cfobj));
						           		excelSheet.addCell(new Label(17, 0, "EMI DUE DATE",cfobj));
						           		excelSheet.addCell(new Label(18, 0, "PDC/ECS GIVEN BY",cfobj));
									}
									
									else if(CommonFunction.checkNull(subList.get(9)).equals("ACH"))
									{
										excelSheet.addCell(new Label(0, 0, "ACH LOCATION",cfobj)); 
										excelSheet.addCell(new Label(1, 0, "ACH Transaction Code",cfobj)); 
						            	excelSheet.addCell(new Label(2, 0, "CUSTOMER BANK MICR CODE",cfobj));
						            	excelSheet.addCell(new Label(3, 0, "CUSTOMER ACCOUNT TYPE",cfobj));
						            	excelSheet.addCell(new Label(4, 0, "LEDGER FOLIO NUMBER",cfobj));
						           		excelSheet.addCell(new Label(5, 0, "CUSTOMER BANK ACCOUNT NUMBER",cfobj));
						           		excelSheet.addCell(new Label(6, 0, "CUSTOMER NAME",cfobj));
						           		excelSheet.addCell(new Label(7, 0, "SPONSOR BANK BRANCH CODE",cfobj));
						           		excelSheet.addCell(new Label(8, 0, "UTILITY NUMBER",cfobj));
						           		excelSheet.addCell(new Label(9, 0, "USER NAME",cfobj));
						           		excelSheet.addCell(new Label(10, 0, "UNIQUE REFERANCE NUMBER, THIS IS FROM RE-PAYMENT SCHEDULE",cfobj));
						           		excelSheet.addCell(new Label(11, 0, "EMI AMOUNT",cfobj));
						           		excelSheet.addCell(new Label(12, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(13, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(14, 0, "RESERVED ( TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(15, 0, "FILLER (TO BE KEPT BLANK BY USER)",cfobj));
						           		excelSheet.addCell(new Label(16, 0, "NO OF ACH",cfobj));
						           		excelSheet.addCell(new Label(17, 0, "EMI DUE DATE",cfobj));
						           		excelSheet.addCell(new Label(18, 0, "PDC/ECS GIVEN BY",cfobj));
									}
					            	else
					            	{
					            		excelSheet.addCell(new Label(0, 0, "LOAN NUMBER",cfobj));		// 8
					            		excelSheet.addCell(new Label(1, 0, "CUSTOMER NAME",cfobj));		//9
					            		excelSheet.addCell(new Label(2, 0, "INSTRUMENT TYPE",cfobj));	//NEW
					            		excelSheet.addCell(new Label(3, 0, "AMOUNT",cfobj));			//2
					            		excelSheet.addCell(new Label(4, 0, "CHEQUE NUMBER",cfobj));		//3
					            		excelSheet.addCell(new Label(5, 0, "CHEQUE DATE",cfobj));		//4
					            		excelSheet.addCell(new Label(6, 0, "EMI DUE DATE",cfobj));		//15
					            		excelSheet.addCell(new Label(7, 0, "BANK CODE",cfobj));			//5
					            		excelSheet.addCell(new Label(8, 0, "CLIENT CODE",cfobj));		//6
					            		excelSheet.addCell(new Label(9, 0, "DEPOSITION DATE",cfobj));	//7
					            		excelSheet.addCell(new Label(10, 0, "B-BRN",cfobj));			//10
					            	//	excelSheet.addCell(new Label(11, 0, "LOCATION",cfobj));			//11
					            		excelSheet.addCell(new Label(11, 0, "NO OF PDC",cfobj));		//12
					            		excelSheet.addCell(new Label(12, 0, "PDC LOCATION",cfobj));		//13
					            		excelSheet.addCell(new Label(13, 0, "NO OF PDC",cfobj));		//16
					            		excelSheet.addCell(new Label(14, 0, "PDC/ECS GIVEN BY",cfobj));	//17
					            		excelSheet.addCell(new Label(15, 0, "DEPOSIT  SLIP",cfobj));     // 0
					            	//	excelSheet.addCell(new Label(17, 0, "PICK UP LOCATION",cfobj));	//1
					            		excelSheet.addCell(new Label(16, 0, "MAKER REMARKS",cfobj));	//14
					            	}
								}
					
					}  
				
				if(CommonFunction.checkNull(subList.get(9)).equals("ECS") || CommonFunction.checkNull(subList.get(9)).equals("DIRECT DEBIT")|| CommonFunction.checkNull(subList.get(9)).equals("ACH"))
            	{
					excelSheet.addCell(new Label(0, row, CommonFunction.checkNull(subList.get(20))));
					excelSheet.addCell(new Label(1, row, CommonFunction.checkNull(subList.get(1))));
					excelSheet.addCell(new Label(2, row, CommonFunction.checkNull(subList.get(2))));
					excelSheet.addCell(new Label(3, row, CommonFunction.checkNull(subList.get(3))));
					excelSheet.addCell(new Label(4, row, "   "));
					String accountNo = CommonFunction.checkNull(subList.get(4));
					accountNo= org.apache.commons.lang.StringUtils.rightPad(accountNo, 15, " ");
					excelSheet.addCell(new Label(5, row, accountNo));
					String name="";
					name=CommonFunction.checkNull(subList.get(5));
				
					name=org.apache.commons.lang.StringUtils.rightPad(name, 40, " ");
					excelSheet.addCell(new Label(6, row, name));
					
					excelSheet.addCell(new Label(7, row, CommonFunction.checkNull(subList.get(11))));
					excelSheet.addCell(new Label(8, row, CommonFunction.checkNull(subList.get(6))));
					excelSheet.addCell(new Label(9, row, companyName));
				
					excelSheet.addCell(new Label(10, row, CommonFunction.checkNull(subList.get(7))));
					
					double am=Double.parseDouble(CommonFunction.checkNull(subList.get(8)).toString());
					
					/*int amu =(int) am;
					String amt = amu+"";*/
					String amt = CommonFunction.checkNull(subList.get(8)).toString()+"";
					//DecimalFormat twoDForm = new DecimalFormat("#.##");
					logger.info("amount  ***************......................"+amt);

					amt=amt+"";

					amt=org.apache.commons.lang.StringUtils.leftPad(amt, 13, "");
	
					excelSheet.addCell(new Label(11, row,amt));
					
					excelSheet.addCell(new Label(12, row, "          "));
					excelSheet.addCell(new Label(13, row, "          "));
					excelSheet.addCell(new Label(14, row, " "));
					excelSheet.addCell(new Label(15, row, "  "));
					excelSheet.addCell(new Label(16, row, CommonFunction.checkNull(subList.get(24)+"/"+CommonFunction.checkNull(subList.get(23)))));
					excelSheet.addCell(new Label(17, row, CommonFunction.checkNull(subList.get(22))));
					excelSheet.addCell(new Label(18, row, CommonFunction.checkNull(subList.get(26))));
            	}
				else
				{
					excelSheet.addCell(new Label(0, row, CommonFunction.checkNull(subList.get(7))));
					excelSheet.addCell(new Label(1, row, CommonFunction.checkNull(subList.get(5))));
					excelSheet.addCell(new Label(2, row, CommonFunction.checkNull(subList.get(27))));
					excelSheet.addCell(new Label(3, row, CommonFunction.checkNull(subList.get(8))));
					excelSheet.addCell(new Label(4, row, CommonFunction.checkNull(subList.get(12))));
					excelSheet.addCell(new Label(5, row, CommonFunction.checkNull(subList.get(18))));
					excelSheet.addCell(new Label(6, row, CommonFunction.checkNull(subList.get(22))));
					excelSheet.addCell(new Label(7, row, CommonFunction.checkNull(subList.get(15))));
					excelSheet.addCell(new Label(8, row, ""));
					excelSheet.addCell(new Label(9, row, ""));
					excelSheet.addCell(new Label(10, row, ""));
					//excelSheet.addCell(new Label(11, row, CommonFunction.checkNull(subList.get(20))));
					//excelSheet.addCell(new Label(11, row, CommonFunction.checkNull(subList.get(20))));
					excelSheet.addCell(new Label(11, row, CommonFunction.checkNull(subList.get(24)+"/"+CommonFunction.checkNull(subList.get(23)))));
					//excelSheet.addCell(new Label(13, row, "DELHI"));
					excelSheet.addCell(new Label(12, row, ""));
					excelSheet.addCell(new Label(13, row, CommonFunction.checkNull(subList.get(24))));
					excelSheet.addCell(new Label(14, row, CommonFunction.checkNull(subList.get(26))));
					excelSheet.addCell(new Label(15, row, ""));
					// excelSheet.addCell(new Label(17, row, ""));
					logger.info("bank code ;;;;;;;;;;;;;;;;;;;;;;;;;;;"+CommonFunction.checkNull(subList.get(15)));
					excelSheet.addCell(new Label(16, row, CommonFunction.checkNull(subList.get(21))));
	
				}
				row++;
			}
			
		}//end of for loop
		
		}//end of outer If
        else
        {
        	request.setAttribute("empty","empty");
        	return mapping.findForward("success");
        }
		 
        if(flag)
        {
         workbook.write();
		 workbook.close(); 
		 out.flush();
	     out.close();
        }
//		out.print(xlReport.toString());
        
		return null;
	}
	
	
}
