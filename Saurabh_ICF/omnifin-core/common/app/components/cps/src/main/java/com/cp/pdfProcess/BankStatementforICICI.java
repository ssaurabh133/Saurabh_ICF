package com.cp.pdfProcess;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.cp.dao.FileUplaodDao;
import com.cp.util.FormatUtility;
import com.cp.vo.BankDetailsVO;
import com.cp.vo.UnderwritingDocUploadVo;

public class BankStatementforICICI {
	private static final Logger logger = Logger.getLogger(BankStatementforICICI.class.getName());
	
	public static ArrayList list = new ArrayList(0);
	public static ArrayList<String> valueDetailsList = new ArrayList<String>(0);
	public static ArrayList<ArrayList<String>> valueList = new ArrayList(0);
	static ArrayList<String> headerDetailsList = new ArrayList<String>(0);
	
	public static final String SAMPLE_PDF_FILE_ICICI = "D:\\SamplePDFs\\ICICI.pdf";
	public static final String FIRST_HEADING_COLUMN_TOKEN_ICICI = "Date";

	public static StringBuffer str;
	
	public static void main(String args[]) 
	{
		BankStatementforICICI pdfReader = new BankStatementforICICI();
		System.out.println("\n\n**************************\n\n");
		
		readUsingIText$ICICI(null,SAMPLE_PDF_FILE_ICICI,FIRST_HEADING_COLUMN_TOKEN_ICICI);
	}
	
	public static ArrayList readUsingIText$ICICI(UnderwritingDocUploadVo vo ,String fileNameWithAbsolutePath, String firstHeadingLineColLabel) 
	{

		StringBuffer strBuffer = new StringBuffer();
		
		boolean headerFlag = true;
		
		PdfReader reader = null;
		
		try
		{
			reader = new PdfReader(fileNameWithAbsolutePath);
			
			BankStatementSimpleTextExtractionStrategy8 textExtractionStrategyInstance = null;
			
		    float colStartPositions[] = {15.00f,60.00f,170.00f,280.00f,340.00f,400.00f,460.00f,520.00f};
			 float colEndPositions[] = {60.00f,170.00f,280.00f,340.00f,400.00f,460.00f,520.00f,600.00f};

			for (int i = 1; i <= reader.getNumberOfPages(); i++) 
			{
			    textExtractionStrategyInstance = new BankStatementSimpleTextExtractionStrategy8();
				textExtractionStrategyInstance.setColPositions(colStartPositions , colEndPositions );
				textExtractionStrategyInstance.setCurrentPageNo(i);
				strBuffer.append(PdfTextExtractor.getTextFromPage(reader, i, textExtractionStrategyInstance));
				strBuffer.append("\n");
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		finally
		{
			//logger.info("Str----------\n\n"+strBuffer.toString());
			reader.close();
		}
		logger.info("******************Final Result Str******************\n\n"+strBuffer.toString());
		
		parsePDFString(strBuffer,firstHeadingLineColLabel);
		logValueList();
		FileUplaodDao  dao = (FileUplaodDao)DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		
		ArrayList mlist = mapValuewithVoSBOP(vo, headerDetailsList, valueList);
		
		return mlist;
		
	}
	
	private static void logValueList(){
		logger.info("**********valueList***************\n\n"+valueList);
		logger.info("******************Final Value Array List******************\n\n");
		StringBuffer lineBuffer = new StringBuffer();
		
		for(ArrayList<String> line: valueList){
			for(String value: line){
				lineBuffer.append(value).append("\t");
			}
			lineBuffer.append("\n");
		}
		
		logger.info("\n"+lineBuffer.toString());
	}
	
	private static void parsePDFString(StringBuffer buffer,String headerStartTag) 
	{
		String[] valueListArray = buffer.toString().split("\n");
		boolean headerFlag = false;
		for (String strLine : valueListArray) 
		{ 
			if(!strLine.isEmpty())
			{
				if (strLine.contains(headerStartTag) && !headerFlag) 
					{
						//System.out.println("strLine----------"+strLine);
						headerDetails(strLine);
						headerFlag = true;
					} 
				else 
					{
						if(strLine.contains("Page Total"))
							{
								break;
							}
						
						valueDetails(strLine);
					}
				
				
			}
		}

	}
	public static void valueDetails(String valueLine) 
	{
		//logger.info("valueLine------------------"+valueLine);
		String[] valueDetailsArray = valueLine.split("\\|");
		ArrayList<String> valueDetailsList = new ArrayList<String>(8);
		for (String str : valueDetailsArray) 
		{
			valueDetailsList.add(str);
		}
		
		if(valueDetailsList.size()==8)
		{
			valueList.add(valueDetailsList);
		}
		else
		{
			//logger.info("valueList.size()-----------"+valueList.size());
			ArrayList<String> lastMainLineArray = valueList.get(valueList.size()-1);
			for(int i=0;i<lastMainLineArray.size();i++)
			{
				String wrappedValueStr = "";
				if(i<valueDetailsList.size())
				{
					if(valueDetailsList.get(i)!=null)
					{
						wrappedValueStr = valueDetailsList.get(i);
					}
					String tempVal = lastMainLineArray.get(i);
					lastMainLineArray.set(i, tempVal+wrappedValueStr);
				}
				else
				{
					break;
				}
			}
		}
		
	}

	public static void headerDetails(String headerString) 
	{
		//logger.info("headerDetails--------");
		String[] headerDetailsArray = headerString.split("\\|");

		for (String str : headerDetailsArray) 
		{
			headerDetailsList.add(str);
		}
		//System.out.println("headerDetailsList--------"+headerDetailsList);
	}

	public static void headerValueDetails(String headerString, boolean headerFlag,int i,String headerStartTag)
	{
		//logger.info("headerValueDetails--------"+i);
		String[] headerDetailsArray = headerString.split("\\|");
		valueDetailsList = new ArrayList<String>();
		int count=0;
		if (headerFlag && !headerString.contains(headerStartTag)) 
		{
			
			for (String str : headerDetailsArray) 
			{
				valueDetailsList.add(str);
				count++;
			}
			System.out.println("valueDetailsList--------"+valueDetailsList);
			 list.add(valueDetailsList);
		}
	}
	public static ArrayList mapValuewithVoSBOP(UnderwritingDocUploadVo vo,ArrayList headerDetailsList,ArrayList valueDetailsList)
	{
		logger.info("mapValuewithVo--------");
		BankDetailsVO bankDetailsVO = null;
		ArrayList reList = new ArrayList();
		for(int i=0; i<valueDetailsList.size(); i++)
		{
			ArrayList bdetails1 = (ArrayList)valueDetailsList.get(i);
			if(bdetails1.size()>0 && bdetails1.size()==8)
			{
				bankDetailsVO 	=  new BankDetailsVO();
				System.out.println("valueDetailsList"+bdetails1);
				
//				bankDetailsVO.	  setStmntEntryid(CommonFunction.checkNull(bdetails1.get()));	
	    	  	bankDetailsVO.	  setDocumentId(CommonFunction.checkNull(vo.getDocId()));	
//	    		bankDetailsVO.	  setDocumentSeqId(CommonFunction.checkNull(bdetails1.get(0)));	
	    		bankDetailsVO.	  setRefId(CommonFunction.checkNull(vo.getRefId()));	
	    		bankDetailsVO.	  setEntityType(CommonFunction.checkNull(vo.getCustomerRoleType()));	
	    		bankDetailsVO.	  setEntityId(CommonFunction.checkNull(vo.getEntityCustomerId()));	
	    		bankDetailsVO.	  setBankName(CommonFunction.checkNull(vo.getLbxBankID()));	
	    		bankDetailsVO.	  setBankBranch(CommonFunction.checkNull(vo.getBankBranch()));	
	    		bankDetailsVO.	  setAccountNo(CommonFunction.checkNull(vo.getAccountNo()));	
	    		bankDetailsVO.	  setAccountType(CommonFunction.checkNull(vo.getAccountType()));	
	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(bdetails1.get(0)));	
//	    		bankDetailsVO.	  setStatementMonth(CommonFunction.checkNull(vo.gets));	
//	    		bankDetailsVO.	  setStatmentDay(CommonFunction.checkNull(vo.gets));	
//	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(vo.gets));	
	    		bankDetailsVO.	  setTotalDr(CommonFunction.checkNull(bdetails1.get(4)));
	     		bankDetailsVO.	  setTotalCr(CommonFunction.checkNull(bdetails1.get(3)));	
	    		bankDetailsVO.	  setBalanceAmount(CommonFunction.checkNull(bdetails1.get(7)));	
	    		bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(1)));		
//	    		bankDetailsVO.	  setRemarks(CommonFunction.checkNull(vo.getRemarks()));
//	    		bankDetailsVO.	  setUploadFlag(CommonFunction.checkNull(vo.getu));
	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(vo.getMakerId()));	
	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(FormatUtility.dateFormat(vo.getMakerDate(),"yyyy-MM-dd")));	
	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(vo.getLastUpdatedBY()));	
	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(vo.getLastUpdateDate()));	
//	    		bankDetailsVO.	  setType(CommonFunction.checkNull(vo.gett));	
//	    		bankDetailsVO.	  setSol(CommonFunction.checkNull(bdetails1.get()));	
	    		//bankDetailsVO.	  setBranchCode(CommonFunction.checkNull(bdetails1.get(4)));	
	    		bankDetailsVO.	  setChequeNo(CommonFunction.checkNull(bdetails1.get(2)));	
//	    		bankDetailsVO.	  setTransactionId(CommonFunction.checkNull(vo.getTxnId()));	
	    		//bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(1)));	
//	    		bankDetailsVO.	  setTransactionAmount(CommonFunction.checkNull(vo.gett));
	    		bankDetailsVO.	  setCaseId(CommonFunction.checkNull(vo.getCaseId()));
	    		bankDetailsVO.	  setAutoSweep(CommonFunction.checkNull(bdetails1.get(5)));	
	    		bankDetailsVO.	  setReverseSweep(CommonFunction.checkNull(bdetails1.get(6)));	
	    		
	    		
	    		if(CommonFunction.checkNull(bdetails1.get(7)).equalsIgnoreCase("-") || CommonFunction.checkNull(bdetails1.get(7)).endsWith("."))
    			{
    			String balanceValue = CommonFunction.checkNull(bdetails1.get(7)).equalsIgnoreCase("-")?CommonFunction.checkNull(bdetails1.get(7))+"0.0":CommonFunction.checkNull(bdetails1.get(7))+"0";
    			bankDetailsVO.	  setBalanceAmount(balanceValue);
    			bankDetailsVO.	  setErrorFlag("E");
    			}
	    		reList.add(bankDetailsVO);
			}
			
		
			else if(bdetails1.size()>2)
			{
				bankDetailsVO 	=  new BankDetailsVO();
	    	  	bankDetailsVO.	  setDocumentId(CommonFunction.checkNull(vo.getDocId()));	
	    		bankDetailsVO.	  setRefId(CommonFunction.checkNull(vo.getRefId()));	
	    		bankDetailsVO.	  setEntityType(CommonFunction.checkNull(vo.getCustomerRoleType()));	
	    		bankDetailsVO.	  setEntityId(CommonFunction.checkNull(vo.getEntityCustomerId()));	
	    		bankDetailsVO.	  setBankName(CommonFunction.checkNull(vo.getLbxBankID()));	
	    		bankDetailsVO.	  setBankBranch(CommonFunction.checkNull(vo.getBankBranch()));	
	    		bankDetailsVO.	  setAccountNo(CommonFunction.checkNull(vo.getAccountNo()));	
	    		bankDetailsVO.	  setAccountType(CommonFunction.checkNull(vo.getAccountType()));	
	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(bdetails1.get(0)));	
	    		bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(1)));		
	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(vo.getMakerId()));	
	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(FormatUtility.dateFormat(vo.getMakerDate(),"yyyy-MM-dd")));	
	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(vo.getLastUpdatedBY()));	
	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(vo.getLastUpdateDate()));	
	    		//bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(1)));	
	    		bankDetailsVO.	  setCaseId(CommonFunction.checkNull(vo.getCaseId()));
	    		bankDetailsVO.	  setErrorFlag("E");
	    		reList.add(bankDetailsVO);
	    		
			}
	    		
		 	bdetails1.clear();
		  	bdetails1=null;
		}
		return reList;
	}
}
