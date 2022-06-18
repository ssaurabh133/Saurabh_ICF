package com.cp.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FileUplaodDao;
import com.cp.dao.FileUtilityDao;
import com.cp.pdfProcess.BankDetailsReadFileProcessForPDF;
import com.cp.util.DateUtility;
import com.cp.util.FormatUtility;
import com.cp.util.ValidationUtility;
import com.cp.vo.BankDetailsVO;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class FileUploadMDBProcessTemplate 
{
	private static final Logger logger = Logger.getLogger(FileUploadMDBProcessTemplate.class.getName());

	public static boolean saveFileUploadDataProcess(UnderwritingDocUploadVo uwDocVo) 
	{
		logger.info("in saveFileUploadDataProcess");
		boolean b = false;
		String s1= null;
		String s2 = null;
		ArrayList in = new ArrayList();
		ArrayList out = new ArrayList();
		ArrayList outMessages = new ArrayList();
		ArrayList<CommonXlsVo> list = new ArrayList<CommonXlsVo>();
		FileUplaodDao uploadDao = (FileUplaodDao) DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		
		String fileTypeValidation = NomenClatureProcess.nomenClatureForHeading(uwDocVo);
		
		b = uploadDao.updateWorkFlowRecords(uwDocVo, "OCRQueueUpload", "P","DUM");
		logger.info(" flag value " + b);

		if (b) 
		{
			if (uwDocVo.getDocType().equalsIgnoreCase("BS") && (uwDocVo.getFileName().endsWith("xls") || uwDocVo.getFileName().endsWith("xlsx"))) 
			{
				Map<String, Map<String, String>> outerMap = BankDetailsReadFileProcessForXLS.readingFileProcess(uwDocVo.getDocPath(), uwDocVo.getFileName());
				b = uploadDao.saveBankingDetails(uwDocVo, outerMap);

			}
			if (uwDocVo.getDocType().equalsIgnoreCase("BS") && uwDocVo.getFileName().endsWith("pdf") ) 
			{
				ArrayList pdfList = BankDetailsReadFileProcessForPDF.readingFileProcess(uwDocVo.getDocPath(), uwDocVo.getFileName(),uwDocVo);
				b = uploadDao.saveBankingDetailsDataForPDF(pdfList, null, uwDocVo);

			}
			else if (uwDocVo.getDocType().equalsIgnoreCase("P&L")) {
				
				 list = ProfitLossFileReadProcess.profitAndLossSheetFileReading(uwDocVo);
				
			} else if (uwDocVo.getDocType().equalsIgnoreCase("BALS")) {
				
				list = BalanceSheetFileReadProcess.balanceSheetFileReading(uwDocVo);
				
			}
			else if(uwDocVo.getDocType().equalsIgnoreCase("CO"))
			{
				logger.info("in Co Block"); 
				b= CibilObligationFileReadProcess.cibilObligationSheetFileReading(uwDocVo);
			}
			
			
			if(b && (uwDocVo.getDocType().equalsIgnoreCase("BALS")||uwDocVo.getDocType().equalsIgnoreCase("P&L")))
			{
				b = uploadDao.saveBalanceSheet(uwDocVo, list);
					Date d = DateUtility.convertToDate(uwDocVo.getMakerDate());
					
					in.add(uwDocVo.getDealId());
					in.add(uwDocVo.getSourceType());
					in.add(FormatUtility.dateFormat(d,"yyyy-MM-dd"));
					in.add(uwDocVo.getMakerId());
					in.add(uwDocVo.getFormatType());
					in.add(uwDocVo.getDocId());
					in.add(uwDocVo.getEntityCustomerId());
					out.add(s1);
					out.add(s2);
					try{
					 outMessages=(ArrayList) ConnectionDAOforEJB.callSP("FINANCIAL_DATA_PROC", in, out);
					    s1=CommonFunction.checkNull(outMessages.get(0));
		        	    s2=CommonFunction.checkNull(outMessages.get(1));
		        	  
		        	    logger.info("s1-----------------1: "+s1);
						logger.info("s2-----------------1: "+s2);
					if(!(CommonFunction.checkNull(s2).equalsIgnoreCase("")))
						b=false;	
					else
						b=true;	
		
					}catch(Exception e)
					{
						b=false;
						e.printStackTrace();
					}
					
					
				
			}
		}
		if (b)
		{
			b = uploadDao.updateWorkFlowRecords(uwDocVo, "OCRQueueUpload", "A",
					"DUM");
		}
		else
			b = uploadDao.updateWorkFlowRecords(uwDocVo, "OCRQueueUpload", "F",
					"DUM");
		return b;
	}
	
	public static boolean bankDetailsDataValidation(UnderwritingDocUploadVo uwDocVo) {
		logger.info("in bankDetailsDataValidation");
		
		FileUplaodDao uploadDao = (FileUplaodDao) DaoImplInstanceFactory
				.getDaoImplInstance(FileUplaodDao.IDENTITY);
		FileUtilityDao utilityDao = (FileUtilityDao) DaoImplInstanceFactory
				.getDaoImplInstance(FileUtilityDao.IDENTITY);
		ArrayList list = uploadDao.getBankingDetailsData(uwDocVo);
		String dateFormat = utilityDao.getGenericMasterDesc("BANKSTMNTDATEFORMAT", uwDocVo.getBankStmtDateFormat());
		boolean flag = dataValidationProcess(list,dateFormat);
		ArrayList outMessages = new ArrayList();
		String s1= null;
		String s2 = null;
		ArrayList in = new ArrayList();
		ArrayList out = new ArrayList();
			if(!flag)
			{
				uploadDao.updateBankingDetailsTemp(list);
			}
			else
			{
				
				flag=uploadDao.saveBankingDetailsInMain(list);
				in.add(uwDocVo.getDealId());
				in.add(uwDocVo.getRefId());
				in.add(uwDocVo.getDocId());
				in.add("DUM");
				logger.info("uwDocVo.getDealId()---"+uwDocVo.getDealId());
				logger.info("uwDocVo.getRefId()---"+uwDocVo.getRefId());
				logger.info("uwDocVo.getDocId()---"+uwDocVo.getDocId());
				out.add(s1);
				out.add(s2);
				try{
					if(flag)
					 outMessages=(ArrayList) ConnectionDAOforEJB.callSP("Banking_Entries_Classification", in, out);
					    s1=CommonFunction.checkNull(outMessages.get(0));
		        	    s2=CommonFunction.checkNull(outMessages.get(1));
		        	  
		        	    logger.info("s1-----------------1: "+s1);
						logger.info("s2-----------------1: "+s2);
					if(!(CommonFunction.checkNull(s2).equalsIgnoreCase("")))
						flag=false;	
					else
						flag=true;	
					if (flag)
					{
						flag= uploadDao.updateWorkFlowRecords(uwDocVo, "OCRQueueUpload", "A",
								"DUM");
					}
					else
						flag = uploadDao.updateWorkFlowRecords(uwDocVo, "OCRQueueUpload", "F",
								"DUM");
		
					}catch(Exception e)
					{
						flag=false;
						e.printStackTrace();
					}
			}
		return flag;
	}
	public static boolean dataValidationProcess(ArrayList list, String dateFormat)
	{
		logger.info("dataValidationProcess");
		boolean returnFlag = true;
		for(int i = 0; i<list.size();i++)
		{
		BankDetailsVO vo = (BankDetailsVO)list.get(i);
				if(!CommonFunction.checkNull(vo.getTotalCr()).trim().equalsIgnoreCase(""))
				{
					if(!ValidationUtility.isNumeric(vo.getTotalCr().trim()))
					{
						logger.info(vo.getTotalCr()+" vo.getTotalCr()");
						vo.setUploadFlag("Y");
						vo.setRemarks("Incorrect Value for Cebit Amount");
						returnFlag=false;
					}
				}
				if(!CommonFunction.checkNull(vo.getTotalDr()).trim().equalsIgnoreCase(""))
				{
					if(!ValidationUtility.isNumeric(vo.getTotalDr().trim()))
					{
						logger.info(vo.getTotalDr()+" vo.getTotalDr()");
						vo.setUploadFlag("Y");
						vo.setRemarks("Incorrect Value for Debit Amount");
						returnFlag=false;
					}
				}
				if(!CommonFunction.checkNull(vo.getBalanceAmount()).trim().equalsIgnoreCase(""))
				{
					if(!ValidationUtility.isNumeric(vo.getBalanceAmount().trim()))
					{
						logger.info(vo.getBalanceAmount()+" vo.getBalanceAmount()");
						vo.setUploadFlag("Y");
						vo.setRemarks("Incorrect Value for Balance Amount");
						returnFlag=false;
					}
				}
				if(!CommonFunction.checkNull(vo.getTransactionAmount()).trim().equalsIgnoreCase(""))
				{
					if(!ValidationUtility.isNumeric(vo.getTransactionAmount().trim()))
					{
						vo.setUploadFlag("Y");
						vo.setRemarks("Incorrect for Transaction Amount");
						returnFlag=false;
					}
				}
				if(!CommonFunction.checkNull(vo.getStatmentDate()).trim().equalsIgnoreCase(""))
				{
					Date date = DateUtility.convertToDate(vo.getStatmentDate().trim(),dateFormat);
					if(date==null)
					{
					vo.setUploadFlag("Y");
					vo.setRemarks("Incorrect Date for Statment Date");
					returnFlag=false;
					}
					else
					{
						String stmnDate = FormatUtility.dateFormat(date);
						vo.setStatementYear(FormatUtility.getYear(stmnDate));
						vo.setStatementMonth(FormatUtility.getMonth(stmnDate));
						vo.setStatmentDay(FormatUtility.getDay(stmnDate));
						vo.setStatmentDate(stmnDate);
					}
				}
				if(!CommonFunction.checkNull(vo.getValueDate()).trim().equalsIgnoreCase(""))
				{
					Date date = DateUtility.convertToDate(vo.getValueDate().trim(),dateFormat);
					if(date==null)
					{
					vo.setUploadFlag("Y");
					vo.setRemarks("Incorrect Date Value Date");
					returnFlag=false;
					}
					else
					{
						vo.setValueDate(FormatUtility.dateFormat(date));
					}
					
				}
		}
		return returnFlag;
	}
}
