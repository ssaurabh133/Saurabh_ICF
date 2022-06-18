package com.cp.pdfProcess;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.cp.dao.FileUplaodDao;
import com.cp.util.FormatUtility;
import com.cp.util.ValidationUtility;
import com.cp.vo.BankDetailsVO;
import com.cp.vo.UnderwritingDocUploadVo;

public class BankStatementforHDFCPdf 
{
	private static final Logger logger = Logger.getLogger(BankStatementforHDFCPdf.class.getName());
	public static ArrayList<ArrayList<String>> valueList = new ArrayList(0);
	static ArrayList<String> headerDetailsList = new ArrayList<String>(0);

	/*public static final String SAMPLE_PDF_FILE_HDFC = "D:/workspaces/OMNIFIN_POC_TEMP/branches/ECAL_PDF_STATEMENT_READER/SamplePDFs/HDFC.pdf";*/
	public static final String SAMPLE_PDF_FILE_HDFC = "D:\\SamplePDFs/HDFC12.pdf";
	public static final String FIRST_HEADING_COLUMN_TOKEN_HDFC = "Date";
	public StringBuffer strBuffer; 
	
	public static void main(String[] args) 
	{
		BankStatementforHDFCPdf bankStatementforHDFCPdf = new BankStatementforHDFCPdf();
//		System.out.println("**********************************************************SOP**********************************************************");
		bankStatementforHDFCPdf.readUsingIText$HDFC(null, SAMPLE_PDF_FILE_HDFC, FIRST_HEADING_COLUMN_TOKEN_HDFC);
	}
	
	
	public ArrayList readUsingIText$HDFC(UnderwritingDocUploadVo vo ,String fileNameWithAbsolutePath, String firstHeadingLineColLabel) 
	{
//		logger.info("readUsingIText$HDFC------------"+fileNameWithAbsolutePath);
		
		StringBuffer strBuffer = new StringBuffer();
		
		boolean headerFlag = true;
		
		PdfReader reader = null;
		
		try
		{
			reader = new PdfReader(fileNameWithAbsolutePath);
			
			BankStatementSimpleTextExtractionStrategy5 textExtractionStrategyInstance = null;
			
		    float colStartPositions[] = {33.000f,68.000f,280.000f,361.000f,405.000f,491.000f,564.000f};
		    float colEndPositions[] = {68.000f,280.000f,361.000f,405.000f,491.000f,564.000f,700.000f};

			for (int i = 1; i <= reader.getNumberOfPages(); i++) 
			{
			    textExtractionStrategyInstance = new BankStatementSimpleTextExtractionStrategy5();
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
		
		ArrayList mlist = mapValuewithVoHDFC(vo, headerDetailsList, valueList);
		
		return mlist;
	}
	
	private void logValueList(){
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
				if(strLine.contains("STATEMENT SUMMARY :-")){
					break;
				}
								
				valueDetails(strLine);
			}
		}

	}

	private static void headerDetails(String headerLine) 
	{
//		logger.info("headerDetails--------");
		String[] headerDetailsArray = headerLine.split("\\|");

		for (String str : headerDetailsArray) 
		{
			headerDetailsList.add(str);
		}
		//System.out.println("headerDetailsList--------"+headerDetailsList);
	}

	private static void valueDetails(String valueLine)
	{
		String[] valueDetailsArray = valueLine.split("\\|");
		
		ArrayList<String> valueDetailsList = new ArrayList<String>(7);

		for (String str : valueDetailsArray) 
		{
			valueDetailsList.add(str);
		}
		
		if(valueDetailsList.size()==7){
			 valueList.add(valueDetailsList);
		}else{
			ArrayList<String> lastMainLineArray = valueList.get(valueList.size()-1);
			for(int i=0;i<lastMainLineArray.size();i++){
				String wrappedValueStr = "";
				if(i<valueDetailsList.size()){
					if(valueDetailsList.get(i)!=null){
						wrappedValueStr = valueDetailsList.get(i);
					}
					String tempVal = lastMainLineArray.get(i);
					lastMainLineArray.set(i, tempVal+wrappedValueStr);
				}else{
					break;
				}
			}
		}
	}
	
	private static ArrayList mapValuewithVoHDFC(UnderwritingDocUploadVo vo,ArrayList headerDetailsList,ArrayList valueDetailsList)
	{
		//logger.info("mapValuewithVo--------");
		BankDetailsVO bankDetailsVO = null;
		ArrayList reList = new ArrayList();
		for(int i=0; i<valueDetailsList.size(); i++)
		{
			ArrayList bdetails1 = (ArrayList)valueDetailsList.get(i);
			if(bdetails1.size()>0 && bdetails1.size()==7)
			{
				bankDetailsVO 	=  new BankDetailsVO();
				System.out.println("valueDetailsList"+bdetails1);
				
//				bankDetailsVO.	  setStmntEntryid(CommonFunction.checkNull(bdetails1.get(0)));	
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
	    		bankDetailsVO.	  setTotalCr(CommonFunction.checkNull(bdetails1.get(5)));
	     		bankDetailsVO.	  setTotalDr(CommonFunction.checkNull(bdetails1.get(4)));	
	    		bankDetailsVO.	  setBalanceAmount(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(6))));	
	    		bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(1)));		
//	    		bankDetailsVO.	  setRemarks(CommonFunction.checkNull(vo.getRemarks()));
//	    		bankDetailsVO.	  setUploadFlag(CommonFunction.checkNull(vo.getu));
	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(vo.getMakerId()));	
	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(FormatUtility.dateFormat(vo.getMakerDate(),"yyyy-MM-dd")));	
	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(vo.getLastUpdatedBY()));	
	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(vo.getLastUpdateDate()));	
//	    		bankDetailsVO.	  setType(CommonFunction.checkNull(vo.gett));	
//	    		bankDetailsVO.	  setSol(CommonFunction.checkNull(bdetails1.get(3)));	
//	    		bankDetailsVO.	  setBranchCode(CommonFunction.checkNull(bdetails1.get(6)));	
	    		bankDetailsVO.	  setChequeNo(CommonFunction.checkNull(bdetails1.get(2)));	
//	    		bankDetailsVO.	  setTransactionId(CommonFunction.checkNull(vo.getTxnId()));	
	    		bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(3)));	
//	    		bankDetailsVO.	  setTransactionAmount(CommonFunction.checkNull(vo.gett));
	    		bankDetailsVO.	  setCaseId(CommonFunction.checkNull(vo.getCaseId()));
	    		if(CommonFunction.checkNull(bdetails1.get(6)).equalsIgnoreCase("-") || CommonFunction.checkNull(bdetails1.get(6)).endsWith("."))
    			{
    			String balanceValue = CommonFunction.checkNull(bdetails1.get(6)).equalsIgnoreCase("-")?CommonFunction.checkNull(bdetails1.get(6))+"0.0":CommonFunction.checkNull(bdetails1.get(6))+"0";
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
