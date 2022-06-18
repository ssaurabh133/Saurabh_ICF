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

public class BankStatementforSBOPPdf {
	private static final Logger logger = Logger.getLogger(BankStatementforSBOPPdf.class.getName());
	
	public static ArrayList list = new ArrayList(0);
	public static ArrayList<String> valueDetailsList = new ArrayList<String>(0);
	public static ArrayList<ArrayList<String>> valueList = new ArrayList(0);
	static ArrayList<String> headerDetailsList = new ArrayList<String>(0);
	
	public static final String SAMPLE_PDF_FILE_SBOP = "D:\\SamplePDFs\\SBOP12.pdf";
	public static final String FIRST_HEADING_COLUMN_TOKEN_SBOP = "Txn Date";

	public static StringBuffer str;
	
	public static void main(String args[]) 
	{
		BankStatementforSBOPPdf pdfReader = new BankStatementforSBOPPdf();
		System.out.println("\n\n**************************\n\n");
		
		readUsingIText$SBOP(null,SAMPLE_PDF_FILE_SBOP,FIRST_HEADING_COLUMN_TOKEN_SBOP);
	}
	
	public static ArrayList readUsingIText$SBOP(UnderwritingDocUploadVo vo ,String fileNameWithAbsolutePath, String firstHeadingLineColLabel) 
	{
/*		StringBuffer str = new StringBuffer();
		boolean headerFlag = true;
		PdfReader reader=null;

		try 
		{
			 reader = new PdfReader(fileNameWithAbsolutePath);

			for (int i = 1; i <= reader.getNumberOfPages(); i++) 
			{
				str.append(PdfTextExtractor
						.getTextFromPage(reader, i, new BankStatementSimpleTextExtractionStrategy6(i, firstHeadingLineColLabel)));
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally{
			reader.close();
		}
		 System.out.println(str.toString());
		 valueDetails(str,firstHeadingLineColLabel);
			
		 FileUplaodDao dao = (FileUplaodDao)DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		 ArrayList mlist = mapValuewithVoSBOP(vo,headerDetailsList,list);
		 return mlist;*/
		
StringBuffer strBuffer = new StringBuffer();
		
		boolean headerFlag = true;
		
		PdfReader reader = null;
		
		try
		{
			reader = new PdfReader(fileNameWithAbsolutePath);
			
			BankStatementSimpleTextExtractionStrategy6 textExtractionStrategyInstance = null;
			
		   /* float colStartPositions[] = {33.000f,68.000f,150.000f,280.000f,361.000f,405.000f,491.000f,564.000f};
		    float colEndPositions[] = {68.000f,150.000f,280.000f,361.000f,405.000f,491.000f,564.000f,700.000f};*/
			 float colStartPositions[] = {37.000f,90.000f,143.000f,221.000f,299.000f,352.000f,416.000f,477.000f};
			    float colEndPositions[] = {90.000f,143.000f,221.000f,299.000f,352.000f,416.000f,477.000f,557.000f};

			for (int i = 1; i <= reader.getNumberOfPages(); i++) 
			{
			    textExtractionStrategyInstance = new BankStatementSimpleTextExtractionStrategy6();
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
		String[] valueListArray = buffer.toString().split("\n");// buffer.toString().split("|");
		boolean headerFlag = false;
		
		for (String strLine : valueListArray) 
		{ 
			if (strLine.contains(headerStartTag) && !headerFlag) 
			{
				headerDetails(strLine);
				headerFlag = true;
			} 
			else 
			{
				if(strLine.contains("The count of")){
					break;
				}
								
				valueDetails(strLine);
			}
		}

	}
	public static void valueDetails(String valueLine) 
	{
		//logger.info("valueDetails--------");
		String[] valueDetailsArray = valueLine.split("\\|");
		//String[] valueListArray = buffer.toString().split("\n");// buffer.toString().split("|");
		//logger.info("valueLine------"+valueLine);
		//logger.info("valueListArray--------"+valueListArray.length);
		ArrayList<String> valueDetailsList = new ArrayList<String>(8);
		//boolean headerFlag = false;
		//int count=0;
		//logger.info("headerStartTag-----"+headerStartTag);
		for (String str : valueDetailsArray) 
		{
			//logger.info("str---------------"+str);
			valueDetailsList.add(str);
		}
		if(valueDetailsList.size()==8)
		{
			valueList.add(valueDetailsList);
		}
		else
		{
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
		
				
		
		/*for (String str : valueListArray) 
		{ //logger.info("str.contains(headerStartTag)-----"+str.contains(headerStartTag));
			if (str.contains(headerStartTag) && !headerFlag) 
			{
				headerDetails(str);
				headerFlag = true;
			} 
			else 
			{
				headerValueDetails(str, headerFlag,count++,headerStartTag);
			}
		}*/

	}

	public static void headerDetails(String headerString) 
	{
		logger.info("headerDetails--------");
		String[] headerDetailsArray = headerString.split("\\|");

		for (String str : headerDetailsArray) 
		{
			headerDetailsList.add(str);
		}
		System.out.println("headerDetailsList--------"+headerDetailsList);
	}

	public static void headerValueDetails(String headerString, boolean headerFlag,int i,String headerStartTag)
	{
		//logger.info("headerValueDetails--------"+i);
		String[] headerDetailsArray = headerString.split("\\|");
		valueDetailsList = new ArrayList<String>();
		/*logger.info("--------"+headerDetailsArray.length);*/
		//logger.info(headerFlag+"--"+!headerString.contains(headerStartTag)+"--"+(headerDetailsArray.length));
		int count=0;
		if (headerFlag && !headerString.contains(headerStartTag)) 
		{
			
			for (String str : headerDetailsArray) 
			{
				valueDetailsList.add(str);
				
				/*if(headerDetailsArray.length<10 && count==3 && headerStartTag.equalsIgnoreCase("Txn Srl"))
					valueDetailsList.add("-");*/
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
	    		bankDetailsVO.	  setTotalDr(CommonFunction.checkNull(bdetails1.get(5)));
	     		bankDetailsVO.	  setTotalCr(CommonFunction.checkNull(bdetails1.get(6)));	
	    		bankDetailsVO.	  setBalanceAmount(CommonFunction.checkNull(bdetails1.get(7)));	
	    		bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(2)));		
//	    		bankDetailsVO.	  setRemarks(CommonFunction.checkNull(vo.getRemarks()));
//	    		bankDetailsVO.	  setUploadFlag(CommonFunction.checkNull(vo.getu));
	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(vo.getMakerId()));	
	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(FormatUtility.dateFormat(vo.getMakerDate(),"yyyy-MM-dd")));	
	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(vo.getLastUpdatedBY()));	
	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(vo.getLastUpdateDate()));	
//	    		bankDetailsVO.	  setType(CommonFunction.checkNull(vo.gett));	
//	    		bankDetailsVO.	  setSol(CommonFunction.checkNull(bdetails1.get()));	
	    		bankDetailsVO.	  setBranchCode(CommonFunction.checkNull(bdetails1.get(4)));	
	    		bankDetailsVO.	  setChequeNo(CommonFunction.checkNull(bdetails1.get(3)));	
//	    		bankDetailsVO.	  setTransactionId(CommonFunction.checkNull(vo.getTxnId()));	
	    		bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(1)));	
//	    		bankDetailsVO.	  setTransactionAmount(CommonFunction.checkNull(vo.gett));
	    		bankDetailsVO.	  setCaseId(CommonFunction.checkNull(vo.getCaseId()));
	    		
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
	    		bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(2)));		
	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(vo.getMakerId()));	
	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(FormatUtility.dateFormat(vo.getMakerDate(),"yyyy-MM-dd")));	
	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(vo.getLastUpdatedBY()));	
	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(vo.getLastUpdateDate()));	
	    		bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(1)));	
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
