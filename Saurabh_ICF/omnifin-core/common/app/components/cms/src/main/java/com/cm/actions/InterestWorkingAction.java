/**
 * 
 */
package com.cm.actions;

/**
 * @author pranaya.gajpure
 *
 */

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
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

import com.cm.dao.InterestWorkingDAO;
import com.cm.vo.InterestWorkingVO;
import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class InterestWorkingAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(GenerateReportDispatchAction.class.getName());	
	public ActionForward generateInterestWorking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		logger.info("Inside interest working report generation----------");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String companyName="";
		String bDate="";
		if(userobj==null){
			logger.info("In generateInterestWorking() method of InterestWorkingAction, the session is out----");
			return mapping.findForward("sessionOut");
		}
		else{
			companyName=userobj.getConpanyName();
			bDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null){
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		logger.info("strFlag------------"+strFlag);
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
		
		boolean flag=false;
		boolean stat=false;
		DynaValidatorForm InterestWorkingDynaValidatorForm = (DynaValidatorForm)form;
		InterestWorkingVO ivo = new InterestWorkingVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(ivo, InterestWorkingDynaValidatorForm);
		
		InterestWorkingDAO dao = (InterestWorkingDAO)DaoImplInstanceFactory.getDaoImplInstance(InterestWorkingDAO.IDENTITY);
		ArrayList ldgList = dao.reportledgerDump();
		ArrayList interestDataList = dao.generateInterestWorkingDao(ivo);
		logger.info("interestDataList size is------------------------"+interestDataList.size());
		
		WritableWorkbook workbook = null;
		ServletOutputStream out = null;
		WritableCellFormat cfObj2 = new WritableCellFormat();
		cfObj2.setWrap(true);
		cfObj2.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		if(interestDataList.size() > 0){
			out = response.getOutputStream();
			workbook = Workbook.createWorkbook(out);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Interest_Working_Report.xls");
			WritableSheet excelSheet = null;
			
			int sheet = 0;
			int row = 5;
			int r = 1;
			
			ArrayList subList = new ArrayList();
			ArrayList subList1 = new ArrayList();
			//int rowCount = dao.generateInterestWorkingCount(ivo);
			ArrayList custList = dao.getInterestCustomerList(ivo);
			ArrayList custData = new ArrayList();
			StringBuilder qry = new StringBuilder();
			ArrayList result = new ArrayList();
			StringBuilder qry1 = new StringBuilder();
			ArrayList result1 = new ArrayList();
			ArrayList resData = new ArrayList();
			ArrayList resData1 = new ArrayList();
			ArrayList sumData = new ArrayList();
			
			if(!stat){
				workbook.createSheet("INTEREST COLLECTION", sheet);
				WritableFont wfObj = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
				WritableFont wfObj1 = new WritableFont(WritableFont.ARIAL, 10 );
			//	Number number = new Number(nc)
				WritableCellFormat cfObj = new WritableCellFormat(wfObj);
				WritableCellFormat cfObj1 = new WritableCellFormat(wfObj1);
				
				
				
				cfObj.setBackground(Colour.GREY_25_PERCENT);
				cfObj.setBorder(Border.ALL, BorderLineStyle.HAIR);
				cfObj.setWrap(true);
				cfObj.setVerticalAlignment(VerticalAlignment.CENTRE);
				
				cfObj1.setBackground(Colour.GREY_25_PERCENT);
				cfObj1.setBorder(Border.ALL, BorderLineStyle.HAIR);
				cfObj1.setWrap(true);
				cfObj1.setVerticalAlignment(VerticalAlignment.CENTRE);
				
				
				
			/*	cfObj.setVerticalAlignment(VerticalAlignment.JUSTIFY);
				cfObj1.setVerticalAlignment(VerticalAlignment.JUSTIFY);*/
				excelSheet = workbook.getSheet(sheet);
				excelSheet.setColumnView(0, 9);
				excelSheet.addCell(new Label(0, 0, "Loan Id", cfObj));
				excelSheet.setColumnView(1, 40);
				excelSheet.addCell(new Label(1, 0, "Name of the Borrower (Amt in INR)", cfObj));
				excelSheet.setColumnView(2, 10);
				excelSheet.addCell(new Label(2, 0, "", cfObj));
				excelSheet.setColumnView(3, 10);
				excelSheet.addCell(new Label(3, 0, "Due Date", cfObj));
				excelSheet.setColumnView(4, 8);
				excelSheet.addCell(new Label(4, 0, "ROI", cfObj));
				excelSheet.setColumnView(5, 15);
				excelSheet.addCell(new Label(5, 0, "Normal Interest for the period", cfObj));
				excelSheet.setColumnView(6, 15);
				excelSheet.addCell(new Label(6, 0, "Normal Int Net of TDS", cfObj));
				excelSheet.setColumnView(7, 15);
				excelSheet.addCell(new Label(7, 0, "Penal Interest for the period", cfObj));
				excelSheet.setColumnView(8, 15);
				excelSheet.addCell(new Label(8, 0, "Penal Int Net of TDS", cfObj));
				excelSheet.setColumnView(9, 15);
				excelSheet.addCell(new Label(9, 0, "Total Interest Net of TDS", cfObj));
				excelSheet.setColumnView(10, 15);
				excelSheet.addCell(new Label(10, 0, "Amt Recd", cfObj));
				excelSheet.setColumnView(11, 15);
				excelSheet.addCell(new Label(11, 0, "Interest Payable(Rs)", cfObj));
				excelSheet.setColumnView(12, 15);
				excelSheet.addCell(new Label(12, 0, "Invoice No.", cfObj));
				
				for(int c = 0; c < custList.size(); c++){	
					custData = (ArrayList) custList.get(c);
					qry = new StringBuilder();
					result = new ArrayList();
					qry.append(" SELECT DISTINCT B.LOAN_ID AS LOAN_NUMBER,B.LOAN_ID,CUSTOMER_NAME, "
							+ " case when INT_FREQ = 'M' then 'Monthly' "
							+ " when INT_FREQ = 'Q' THEN 'Quarterly' "
							+ " when INT_FREQ = 'H' then 'Half Yearly' "
							+ " when INT_FREQ = 'Y' then 'Yearly' end as Int_Frequency,DATE_FORMAT(DUE_DATE,'%d-%b-%y') AS DUE_DATE  "
							+ " ,CONCAT(ROUND(B.EFF_RATE,2),'%') AS ROI, ROUND(C.INTEREST,0) AS INTEREST,"
							+ " ROUND((C.INTEREST * 0.9),0) AS TDS, "
							+ " ROUND(c.penal_interest,0) AS PENAL_INT_SUM, "
							+ " ROUND((c.penal_interest*0.9),0) AS NET_TDS, "
							+ " ROUND((C.INTEREST * 0.9),0) + ROUND((c.penal_interest*0.9),0) AS TOTAL_NET_TDS,sum(ifnull(b.receipts,0)), "
							+ " ROUND((C.INTEREST * 0.9),0) + ROUND((c.penal_interest*0.9),0)-sum(ifnull(b.receipts,0)) AS INT_PAYABLE "
							/*+ " IFNULL(CASM.INVOICE_NO ,'') "*/
							+ " FROM SEPARATE_INTEREST_REPORT_TEMP B "
							+ " LEFT JOIN (SELECT LOAN_ID,SUM(INTEREST)AS INTEREST ,sum(penal_interest) as penal_interest  FROM SEPARATE_INTEREST_REPORT_TEMP GROUP BY LOAN_ID)C ON C.LOAN_ID=B.LOAN_ID "
							+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
							/*+ " LEFT JOIN CR_ASSET_COLLATERAL_M CASM ON CASM.EXISTING_LOAN_ID = B.LOAN_ID "*/
							+ " WHERE B.LOAN_ID = '"+CommonFunction.checkNull(custData.get(0))+"' "
							+ " ORDER BY B.LOAN_ID, SEQ_NO; ");
					result = ConnectionReportDumpsDAO.sqlSelect(qry.toString());
					for(int s = 0; s < result.size(); s++){
						sumData = (ArrayList) result.get(s);
						excelSheet.addCell(new Label(0, r, CommonFunction.checkNull(sumData.get(1)),cfObj2));
						excelSheet.addCell(new Label(1, r, CommonFunction.checkNull(sumData.get(2)), cfObj1));
						excelSheet.addCell(new Label(2, r, CommonFunction.checkNull(sumData.get(3)),cfObj2));
						excelSheet.addCell(new Label(3, r, CommonFunction.checkNull(sumData.get(4)),cfObj2));
						excelSheet.addCell(new Label(4, r, CommonFunction.checkNull(sumData.get(5).toString()),cfObj2));
						excelSheet.addCell(new Number(5, r, CommonFunction.checkDouble(sumData.get(6)),cfObj2));
						excelSheet.addCell(new Number(6, r, CommonFunction.checkDouble(sumData.get(7)),cfObj2));
						excelSheet.addCell(new Number(7, r, CommonFunction.checkDouble(sumData.get(8)),cfObj2));
						excelSheet.addCell(new Number(8, r, CommonFunction.checkDouble(sumData.get(9)),cfObj2));
						excelSheet.addCell(new Number(9, r, CommonFunction.checkDouble(sumData.get(10)),cfObj2));
						excelSheet.addCell(new Number(10, r, CommonFunction.checkDouble(sumData.get(11)),cfObj2));
						excelSheet.addCell(new Number(11, r, CommonFunction.checkDouble(sumData.get(12)),cfObj2));
						r++;
						excelSheet.insertRow(r);
					}
					qry = null;
					result = null;
					sumData = null;
				}
				custData = null;
				stat = true;
			}
				
			sheet = 1;
			for(int i=0; i < interestDataList.size(); i++){
				subList = (ArrayList) interestDataList.get(i);
				System.out.print("Size000: "+subList.size());				
				if(subList.size() > 0){
					if(!flag){
						workbook.createSheet(CommonFunction.checkNull(subList.get(1)), sheet);
						WritableFont wfObj = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
						WritableCellFormat cfObj = new WritableCellFormat(wfObj);
						cfObj.setWrap(true);
						cfObj.setVerticalAlignment(VerticalAlignment.CENTRE);
						
						excelSheet = workbook.getSheet(sheet);
						excelSheet.setColumnView(0, 20);
						excelSheet.setColumnView(1, 10);
						excelSheet.setColumnView(2, 16);
						excelSheet.setColumnView(3, 12);
						excelSheet.setColumnView(4, 12);
						excelSheet.setColumnView(5, 12);
						excelSheet.setColumnView(6, 12);
						excelSheet.setColumnView(7, 12);
						excelSheet.setColumnView(8, 12);
						excelSheet.setColumnView(9, 12);
						excelSheet.setColumnView(10, 12);
						excelSheet.setColumnView(11, 12);
						excelSheet.setColumnView(12, 12);
						
						excelSheet.mergeCells(0, 0, 12, 0);
						excelSheet.addCell(new Label(0, 0, subList.get(2).toString(),cfObj));
						
						/*For summary..................*/
						excelSheet.addCell(new Label(0, 1, subList.get(0).toString(),cfObj2));
						excelSheet.addCell(new Label(1, 1, "", cfObj));
						excelSheet.addCell(new Label(2, 1, "Benchmark", cfObj));
						excelSheet.addCell(new Label(3, 1, "Effective Rate", cfObj));
						excelSheet.addCell(new Label(4, 1, "Spread", cfObj));
						excelSheet.addCell(new Label(5, 1, "", cfObj));
						excelSheet.addCell(new Label(6, 1, "ROI", cfObj));
						excelSheet.addCell(new Label(7, 1, "Penal", cfObj));
						excelSheet.addCell(new Label(8, 1, "", cfObj));
					    excelSheet.addCell(new Label(9, 1, "", cfObj));
					    excelSheet.addCell(new Label(10, 1, "", cfObj));
					    excelSheet.addCell(new Label(11, 1, "", cfObj));
					    excelSheet.addCell(new Label(12, 1, "", cfObj));
					    
						
						excelSheet.addCell(new Label(0, 2, subList.get(1).toString(),cfObj2));
						excelSheet.addCell(new Label(1, 2, "",cfObj2));
						excelSheet.addCell(new Label(2, 2, "", cfObj));
						excelSheet.addCell(new Label(3, 2, "", cfObj));
						excelSheet.addCell(new Label(4, 2, "", cfObj));
						excelSheet.addCell(new Label(5, 2, "", cfObj));
						excelSheet.addCell(new Label(6, 2, CommonFunction.checkNull(subList.get(8).toString()),cfObj2));
						excelSheet.addCell(new Label(7, 2, CommonFunction.checkNull(subList.get(12).toString()),cfObj2));
						excelSheet.addCell(new Label(8, 2, "", cfObj));
					    excelSheet.addCell(new Label(9, 2, "", cfObj));
					    excelSheet.addCell(new Label(10, 2, "", cfObj));
					    excelSheet.addCell(new Label(11, 2, "", cfObj));
					    excelSheet.addCell(new Label(12, 2, "", cfObj));
						
						excelSheet.addCell(new Label(0, 3, "No of days in period", cfObj));
						excelSheet.addCell(new Label(1, 3, subList.get(11).toString(),cfObj2));
						excelSheet.addCell(new Label(2, 3, "", cfObj));
						excelSheet.addCell(new Label(3, 3, "Opening Balance", cfObj));
						excelSheet.addCell(new Label(4, 3,subList.get(3).toString(),cfObj2));
						excelSheet.addCell(new Label(5, 3, "", cfObj));
						excelSheet.addCell(new Label(6, 3, "Total Interest for period", cfObj));
						excelSheet.addCell(new Label(7, 3, CommonFunction.checkNull(subList.get(14)),cfObj2));
						excelSheet.addCell(new Label(8, 3, "", cfObj));
					    excelSheet.addCell(new Label(9, 3, "", cfObj));
					    excelSheet.addCell(new Label(10, 3, "", cfObj));
					    excelSheet.addCell(new Label(11, 3, "", cfObj));
					    excelSheet.addCell(new Label(12, 3, "", cfObj));
						
						/*For Column headers..............*/
						excelSheet.addCell(new Label(0, 4, "Balance for Int Cal", cfObj));
						excelSheet.addCell(new Label(1, 4, "Date", cfObj));
						excelSheet.addCell(new Label(2, 4, "Opening Principal", cfObj));
						excelSheet.addCell(new Label(3, 4, "Additions", cfObj));
						excelSheet.addCell(new Label(4, 4, "Subtractions", cfObj));
						excelSheet.addCell(new Label(5, 4, "Interest Received", cfObj));
						excelSheet.addCell(new Label(6, 4, "Closing Principal", cfObj));
						excelSheet.addCell(new Label(7, 4, "Rate of Int", cfObj));
						excelSheet.addCell(new Label(8, 4, "Int", cfObj));
					    excelSheet.addCell(new Label(9, 4, "No of Days(Interest)", cfObj));
					    excelSheet.addCell(new Label(10, 4, "", cfObj));
					    excelSheet.addCell(new Label(11, 4, "No of Days(Penal Interest)", cfObj));
					    excelSheet.addCell(new Label(12, 4, "Penal Interest", cfObj));

						/*For data rows----------------*/
						qry1 = new StringBuilder();
						result1 = new ArrayList();
						qry1.append(" SELECT B.LOAN_ID AS LOAN_NUMBER, SEQ_NO "
									+ " FROM SEPARATE_INTEREST_REPORT_TEMP B "
									+ " LEFT JOIN (SELECT LOAN_ID, SUM(DAYS) AS DAYS FROM SEPARATE_INTEREST_REPORT_TEMP "
									+ " GROUP BY LOAN_ID) AS A ON A.LOAN_ID = B.LOAN_ID "
									+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
									+ " WHERE B.LOAN_ID = '"+CommonFunction.checkNull(subList.get(0))+"' "
									+ " ORDER BY B.LOAN_ID, SEQ_NO; ");
						result1 = ConnectionReportDumpsDAO.sqlSelect(qry1.toString());
						for(int h = 1; h <= result1.size(); h++){
								qry = new StringBuilder();
								result = new ArrayList();
								qry.append(" SELECT B.LOAN_ID AS LOAN_NUMBER, CUSTOMER_NAME, ROUND(OPENING_BALANCE,0) AS OPENINGBALANCE, DATE_FORMAT(CURR_DATE,'%d-%b-%y') AS TRANSACTION_DATE, "
										+ " ROUND(DISBURSALS,0), ROUND(PREPAYMENT,0), ROUND(RECEIPTS,0), CONCAT(ROUND(EFF_RATE,2),'%') AS ROI, ROUND(B.INTEREST,0) AS INTERESTAMOUNT, "
										+ " ROUND(CLOSING_BALANCE,0), A.DAYS, "
										+ " CONCAT(ROUND(IFNULL(CTD.DEAL_CHARGE_FINAL_AMOUNT ,0),0),'%') AS DEAL_CHARGE_FINAL_AMOUNT, SEQ_NO,B.DAYS,round(PENAL_INTEREST,0) as PENAL_INTEREST ,round(b.OPENING_PRINCIPAL,0) AS OPENING_PRINCIPAL,round(b.CLOSING_PRINCIPAL,0) as CLOSING_PRINCIPAL "
										+ " FROM SEPARATE_INTEREST_REPORT_TEMP B "
										+ " LEFT JOIN (SELECT LOAN_ID, SUM(DAYS) AS DAYS FROM SEPARATE_INTEREST_REPORT_TEMP "
										+ " GROUP BY LOAN_ID) AS A ON A.LOAN_ID = B.LOAN_ID "
										+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
										+ " WHERE B.LOAN_ID = '"+CommonFunction.checkNull(subList.get(0))+"' "
										+ " AND SEQ_NO = '"+h+"' "
										+ " ORDER BY B.LOAN_ID, SEQ_NO; ");
								
								
								
							result = ConnectionReportDumpsDAO.sqlSelect(qry.toString());
							for(int b = 0; b < result.size(); b++){
								resData = (ArrayList) result.get(b);
								excelSheet.addCell(new Number(0, row, CommonFunction.checkDouble(resData.get(2)),cfObj2));
								excelSheet.addCell(new Label(1, row, CommonFunction.checkNull(resData.get(3)),cfObj2));
								excelSheet.addCell(new Number(2, row, CommonFunction.checkDouble(resData.get(15)),cfObj2));
								excelSheet.addCell(new Number(3, row, CommonFunction.checkDouble(resData.get(4)),cfObj2));
								excelSheet.addCell(new Number(4, row, CommonFunction.checkDouble(resData.get(5)),cfObj2));
								excelSheet.addCell(new Number(5, row, CommonFunction.checkDouble(resData.get(6)),cfObj2));
								excelSheet.addCell(new Number(6, row, CommonFunction.checkDouble(resData.get(16)),cfObj2));
								excelSheet.addCell(new Label(7, row, CommonFunction.checkNull(resData.get(7)),cfObj2));
								excelSheet.addCell(new Number(8, row, CommonFunction.checkDouble(resData.get(8)),cfObj2));
								excelSheet.addCell(new Number(9, row, CommonFunction.checkDouble(resData.get(13)),cfObj2));
								excelSheet.addCell(new Number(11, row, CommonFunction.checkDouble(resData.get(13)),cfObj2));
								excelSheet.addCell(new Number(12, row, CommonFunction.checkDouble(resData.get(14)),cfObj2));
								row++;
								excelSheet.insertRow(row);
							}
							result = null;
							qry = null;
						}
						result1 = null;
						qry1 = null;
						resData = null;
						flag = true;
					}
					if(i <= interestDataList.size()-1){
						if(i <= 0){
							subList1 = (ArrayList) interestDataList.get(i);
						}
						else{
							subList1 = (ArrayList) interestDataList.get(i-1);
						}
						if(!CommonFunction.checkNull(subList.get(0)).equals(CommonFunction.checkNull(subList1.get(0))) 
								|| !CommonFunction.checkNull(subList.get(1)).equals(CommonFunction.checkNull(subList1.get(1)))){
							sheet++;
							row = 5;
							workbook.createSheet(CommonFunction.checkNull(subList.get(1)), sheet);
							WritableFont wfObj = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
							WritableCellFormat cfObj = new WritableCellFormat(wfObj);
							cfObj.setWrap(true);
							cfObj.setVerticalAlignment(VerticalAlignment.CENTRE);
							
							excelSheet = workbook.getSheet(sheet);
							System.out.print("Size1111: "+subList.size());	
							/*For summary..................*/
							excelSheet.setColumnView(0, 20);
							excelSheet.setColumnView(1, 10);
							excelSheet.setColumnView(2, 16);
							excelSheet.setColumnView(3, 12);
							excelSheet.setColumnView(4, 12);
							excelSheet.setColumnView(5, 12);
							excelSheet.setColumnView(6, 12);
							excelSheet.setColumnView(7, 12);
							excelSheet.setColumnView(8, 12);
							excelSheet.setColumnView(9, 12);
							excelSheet.setColumnView(10, 12);
							excelSheet.setColumnView(11, 12);
							excelSheet.setColumnView(12, 12);
							
							excelSheet.mergeCells(0, 0, 12, 0);
							excelSheet.addCell(new Label(0, 0, subList.get(2).toString(),cfObj));
							
							excelSheet.addCell(new Label(0, 1, subList.get(0).toString(),cfObj2));
							excelSheet.addCell(new Label(1, 1, "", cfObj));
							excelSheet.addCell(new Label(2, 1, "Benchmark", cfObj));
							excelSheet.addCell(new Label(3, 1, "Effective Rate", cfObj));
							excelSheet.addCell(new Label(4, 1, "Spread", cfObj));
							excelSheet.addCell(new Label(5, 1, "", cfObj));
							excelSheet.addCell(new Label(6, 1, "ROI", cfObj));
							excelSheet.addCell(new Label(7, 1, "Penal", cfObj));
							excelSheet.addCell(new Label(8, 1, "", cfObj));
						    excelSheet.addCell(new Label(9, 1, "", cfObj));
						    excelSheet.addCell(new Label(10, 1, "", cfObj));
						    excelSheet.addCell(new Label(11, 1, "", cfObj));
						    excelSheet.addCell(new Label(12, 1, "", cfObj));
						    
						    excelSheet.addCell(new Label(0, 2, subList.get(1).toString(),cfObj2));
							excelSheet.addCell(new Label(1, 2, "",cfObj2));
							excelSheet.addCell(new Label(2, 2, "", cfObj));
							excelSheet.addCell(new Label(3, 2, "", cfObj));
							excelSheet.addCell(new Label(4, 2, "", cfObj));
							excelSheet.addCell(new Label(5, 2, "", cfObj));
							excelSheet.addCell(new Label(6, 2, CommonFunction.checkNull(subList.get(8).toString()),cfObj2));
							excelSheet.addCell(new Label(7, 2, CommonFunction.checkNull(subList.get(12).toString()),cfObj2));
							excelSheet.addCell(new Label(8, 2, "", cfObj));
						    excelSheet.addCell(new Label(9, 2, "", cfObj));
						    excelSheet.addCell(new Label(10, 2, "", cfObj));
						    excelSheet.addCell(new Label(11, 2, "", cfObj));
						    excelSheet.addCell(new Label(12, 2, "", cfObj));
						    
						    excelSheet.addCell(new Label(0, 3, "No of days in period", cfObj));
							excelSheet.addCell(new Label(1, 3, subList.get(11).toString(),cfObj2));
							excelSheet.addCell(new Label(2, 3, "", cfObj));
							excelSheet.addCell(new Label(3, 3, "Opening Balance", cfObj));
							excelSheet.addCell(new Label(4, 3,subList.get(3).toString(),cfObj2));
							excelSheet.addCell(new Label(5, 3, "", cfObj));
							excelSheet.addCell(new Label(6, 3, "Total Interest for period", cfObj));
							excelSheet.addCell(new Label(7, 3, CommonFunction.checkNull(subList.get(14)),cfObj2));
							excelSheet.addCell(new Label(8, 3, "", cfObj));
						    excelSheet.addCell(new Label(9, 3, "", cfObj));
						    excelSheet.addCell(new Label(10, 3, "", cfObj));
						    excelSheet.addCell(new Label(11, 3, "", cfObj));
						    excelSheet.addCell(new Label(12, 3, "", cfObj));
							
							
							/*For Column headers..............*/
						    excelSheet.addCell(new Label(0, 4, "Balance for Int Cal", cfObj));
							excelSheet.addCell(new Label(1, 4, "Date", cfObj));
							excelSheet.addCell(new Label(2, 4, "Opening Principal", cfObj));
							excelSheet.addCell(new Label(3, 4, "Additions", cfObj));
							excelSheet.addCell(new Label(4, 4, "Subtractions", cfObj));
							excelSheet.addCell(new Label(5, 4, "Interest Received", cfObj));
							excelSheet.addCell(new Label(6, 4, "Closing Principal", cfObj));
							excelSheet.addCell(new Label(7, 4, "Rate of Int", cfObj));
							excelSheet.addCell(new Label(8, 4, "Int", cfObj));
						    excelSheet.addCell(new Label(9, 4, "No of Days(Interest)", cfObj));
						    excelSheet.addCell(new Label(10, 4, "", cfObj));
						    excelSheet.addCell(new Label(11, 4, "No of Days(Penal Interest)", cfObj));
						    excelSheet.addCell(new Label(12, 4, "Penal Interest", cfObj));
						    
							qry1 = new StringBuilder();
							result1 = new ArrayList();
							qry1.append(" SELECT B.LOAN_ID AS LOAN_NUMBER, SEQ_NO "
										+ " FROM SEPARATE_INTEREST_REPORT_TEMP B "
										+ " LEFT JOIN (SELECT LOAN_ID, SUM(DAYS) AS DAYS FROM SEPARATE_INTEREST_REPORT_TEMP "
										+ " GROUP BY LOAN_ID) AS A ON A.LOAN_ID = B.LOAN_ID "
										+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
										+ " WHERE B.LOAN_ID = '"+CommonFunction.checkNull(subList.get(0))+"' "
										+ " ORDER BY B.LOAN_ID, SEQ_NO; ");
							result1 = ConnectionReportDumpsDAO.sqlSelect(qry1.toString());
							for(int d = 1; d <= result1.size(); d++){
									qry = new StringBuilder();
									result = new ArrayList();
									qry.append(" SELECT B.LOAN_ID AS LOAN_NUMBER, CUSTOMER_NAME, ROUND(OPENING_BALANCE,0) AS OPENINGBALANCE, DATE_FORMAT(CURR_DATE,'%d-%b-%y') AS TRANSACTION_DATE, "
											+ " ROUND(DISBURSALS,0), ROUND(PREPAYMENT,0), ROUND(RECEIPTS,0), CONCAT(ROUND(EFF_RATE,2),'%'), ROUND(B.INTEREST,0) AS INTERESTAMOUNT, "
											+ " ROUND(CLOSING_BALANCE,2), A.DAYS, "
											+ " CONCAT(ROUND(IFNULL(CTD.DEAL_CHARGE_FINAL_AMOUNT ,0),0),'%') AS DEAL_CHARGE_FINAL_AMOUNT, SEQ_NO,B.DAYS,round(PENAL_INTEREST,0) as PENAL_INTEREST ,round(b.OPENING_PRINCIPAL,0) as OPENING_PRINCIPAL, round(b.CLOSING_PRINCIPAL,0) as CLOSING_PRINCIPAL "
											+ " FROM SEPARATE_INTEREST_REPORT_TEMP B "
											+ " LEFT JOIN (SELECT LOAN_ID, SUM(DAYS) AS DAYS FROM SEPARATE_INTEREST_REPORT_TEMP "
											+ " GROUP BY LOAN_ID) AS A ON A.LOAN_ID = B.LOAN_ID "
											+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
											+ " WHERE B.LOAN_ID = '"+CommonFunction.checkNull(subList.get(0))+"' "
											+ " AND SEQ_NO = '"+d+"' "
											+ " ORDER BY B.LOAN_ID, SEQ_NO; ");
								
								result = ConnectionReportDumpsDAO.sqlSelect(qry.toString());
								for(int b = 0; b < result.size(); b++){
									resData1 = (ArrayList) result.get(b);
									excelSheet.addCell(new Number(0, row, CommonFunction.checkDouble(resData1.get(2)),cfObj2));
									excelSheet.addCell(new Label(1, row, CommonFunction.checkNull(resData1.get(3)),cfObj2));
									excelSheet.addCell(new Number(2, row, CommonFunction.checkDouble(resData1.get(15)),cfObj2));
									excelSheet.addCell(new Number(3, row, CommonFunction.checkDouble(resData1.get(4)),cfObj2));
									excelSheet.addCell(new Number(4, row, CommonFunction.checkDouble(resData1.get(5)),cfObj2));
									excelSheet.addCell(new Number(5, row, CommonFunction.checkDouble(resData1.get(6)),cfObj2));
									excelSheet.addCell(new Number(6, row, CommonFunction.checkDouble(resData1.get(16)),cfObj2));
									excelSheet.addCell(new Label(7, row, CommonFunction.checkNull(resData1.get(7)),cfObj2));
									excelSheet.addCell(new Number(8, row, CommonFunction.checkDouble(resData1.get(8)),cfObj2));
									excelSheet.addCell(new Number(9, row, CommonFunction.checkDouble(resData1.get(13)),cfObj2));
									excelSheet.addCell(new Number(11, row, CommonFunction.checkDouble(resData1.get(13)),cfObj2));
									excelSheet.addCell(new Number(12, row, CommonFunction.checkDouble(resData1.get(14)),cfObj2));
									row++;
									excelSheet.insertRow(row);
								}
								result = null;
								qry = null;
							}
							result1 = null;
							qry1 = null;
							resData1 = null;
						}
					}
				}
			}
		}
		else{
			request.setAttribute("empty", "empty");
			return mapping.findForward("success");
		}
		if(flag){
			workbook.write();
			workbook.close();
			out.flush();
			out.close();
		}
		return null;
	}
}
