package com.cp.pdfProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.UnderlineAction;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.cp.actions.UploadAction;
import com.cp.dao.FileUplaodDao;
import com.cp.daoImplMYSQL.FileUploadDaoImpl;
import com.cp.util.FormatUtility;
import com.cp.util.ValidationUtility;
import com.cp.vo.BankDetailsVO;
import com.cp.vo.UnderwritingDocUploadVo;

public class BankStatementforPNBPdf 
{
	private static final Logger logger = Logger.getLogger(BankStatementforPNBPdf.class.getName());
	
	public static ArrayList list = new ArrayList(0);
	public static ArrayList<String> valueDetailsList = new ArrayList<String>(0);
	static ArrayList<String> headerDetailsList = new ArrayList<String>(0);

	public static final String SAMPLE_PDF_FILE_PNB = "D:/workspaces/OMNIFIN_POC_TEMP/branches/ECAL_PDF_STATEMENT_READER/SamplePDFs/PNB.pdf";
	/*public static final String SAMPLE_PDF_FILE_PNB = "D:\\SamplePDFs/PNB.pdf";*/
	public static final String FIRST_HEADING_COLUMN_TOKEN_PNB = "Txn Srl";
	
	public static StringBuffer str;
	
	/*public static void main(String args[]) 
	{
		BankStatementforPNBPdf pdfReader = new BankStatementforPNBPdf();
		System.out.println("\n\n**************************\n\n");
		
		readUsingIText$PNB(SAMPLE_PDF_FILE_PNB,FIRST_HEADING_COLUMN_TOKEN_PNB);
	}*/
	
	
	
	
	public static ArrayList readUsingIText$PNB(UnderwritingDocUploadVo vo , String fileNameWithAbsolutePath, String firstHeadingLineColLabel) 
	{
		/*logger.info("readUsingITet$PNB---------");*/
		StringBuffer str = new StringBuffer();
		
		boolean headerFlag = true;
		PdfReader reader =null;
		
		try 
		{
			 reader = new PdfReader(fileNameWithAbsolutePath);
			
			for (int i = 1; i <= reader.getNumberOfPages(); i++) 
			 //for (int i = 3; i <= 4; i++)
			 
			{
				str.append(PdfTextExtractor.getTextFromPage(reader, i, new BankStatementSimpleTextExtractionStrategy1(i, firstHeadingLineColLabel)));
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			reader.close();
		}
		//Added By Abhishek Mathur : 30-Nov-2015
		String str2="";
		String sArr[] = str.toString().split("\n");
		String sArr1[];
		char c;
		for (int i=0;i<sArr.length;i++)
		{
			if (sArr[i].contains("~") && !sArr[i].contains("~1~") )
			{
				if (sArr[i].indexOf("|")>0)
				{
					sArr1 = sArr[i].split("\\|");
					for(int a=0;a<sArr1.length;a++)
					{
						for(int j=1;j<=10;j++)
						{
							if (sArr1[a].indexOf("~"+j+"~") > 0)
							{
										str2 = sArr[i-2];
										for(int k=0,l=0;k < sArr[i-2].length();k++)
										{
											c = str2.charAt(k);
											if (c == '|')
											{
												l++;
												
												if (j == l+1)
												{
													str2  = str2.substring(0,k) + sArr1[a] + str2.substring(k,str2.length());
													
												}
												if (j == 10)
												{
													str2  = sArr[i-2]+sArr1[a];
												}
											}
												
											 
										}
										str2 = str2.replaceAll("~"+j+"~","");
							
										
							}
							
						}
						sArr[i-2] = str2;
						sArr[i]="";
					}
					
				}
				else
				{
					for(int j=1;j<=10;j++)
					{
						if (sArr[i].indexOf("~"+j+"~") > 0)
						{
							if (!sArr[i].contains("~1~") )
							{	
									str2 = sArr[i-2];
									for(int k=0,l=0;k < sArr[i-2].length();k++)
									{
										c = str2.charAt(k);
										if (c == '|')
										{
											l++;
											if (j == l+1)
											{
												str2  = str2.substring(0,k) + sArr[i] + str2.substring(k,str2.length());
												
											}
											if (j == 10)
											{
												str2  = sArr[i-2]+sArr[i];
											}
										}
										 
									}
									str2 = str2.replaceAll("~"+j+"~","");
									sArr[i-2] = str2;
									sArr[i]="";
							}
							
						}
						
			
						
						
					}
				}
			}
			else if (sArr[i].contains("~"))
			{
				for(int j=1;j<=10;j++)
				{
					sArr[i] = sArr[i].replaceAll("~"+j+"~","");
				}
			}
				
			
		}
		str = new StringBuffer();
		for(int i=0;i<sArr.length;i++)
		{
			str = str.append(sArr[i]).append("\n");
			
		}
		
		//Ended By Abhishek Mathur : 30-Nov-2015
		//System.out.println(str);
		
		valueDetails(str,firstHeadingLineColLabel);
		
		FileUplaodDao dao = (FileUplaodDao)DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		ArrayList mlist = mapValuewithVoPNB(vo, headerDetailsList,list);
		return mlist;
		
		
	}

	public static void valueDetails(StringBuffer buffer,String headerStartTag) 
	{
		/*logger.info("valueDetails--------");*/
		String[] valueListArray = buffer.toString().split("\n");// buffer.toString().split("|");
		//logger.info("valueListArray--------"+valueListArray.length);
		boolean headerFlag = false;
		int count=0;
		//logger.info("headerStartTag-----"+headerStartTag);
		
		
		for (String str : valueListArray) 
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
		}

	}

	public static void headerDetails(String headerString) 
	{
		/*logger.info("headerDetails--------");*/
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
		if (headerFlag && !headerString.contains(headerStartTag) ) 
		{
			
			for (String str : headerDetailsArray) 
			{
					valueDetailsList.add(str);
				/*if (str.lastIndexOf("  ") != -1)
					valueDetailsList.add("$$");*/
				if(headerDetailsArray.length<10 && count==3 && headerStartTag.equalsIgnoreCase("Txn Srl"))
					valueDetailsList.add("-");
				count++;
			}
			System.out.println("valueDetailsList--------"+valueDetailsList);
			 list.add(valueDetailsList);
		}
	}
	
    
	public static ArrayList mapValuewithVoPNB(UnderwritingDocUploadVo vo,ArrayList headerDetailsList,ArrayList valueDetailsList)
	{
		/*logger.info("mapValuewithVo--------");*/
		BankDetailsVO bankDetailsVO = null;
		ArrayList reList = new ArrayList();
		
		for(int i=0; i<valueDetailsList.size(); i++)
		{
			ArrayList bdetails1 = (ArrayList)valueDetailsList.get(i);
			//logger.info("bdetails1.size()----"+betails1.size());
			
			if(bdetails1.size()>0  && bdetails1.size()==10)
			{
				bankDetailsVO 	=  new BankDetailsVO();
				System.out.println("valueDetailsList"+bdetails1);
				
				bankDetailsVO.	  setStmntEntryid(CommonFunction.checkNull(bdetails1.get(0)));	
	    	  	bankDetailsVO.	  setDocumentId(CommonFunction.checkNull(vo.getDocId()));	
	    		bankDetailsVO.	  setDocumentSeqId(CommonFunction.checkNull(bdetails1.get(0)));	
	    		bankDetailsVO.	  setRefId(CommonFunction.checkNull(vo.getRefId()));	
	    		bankDetailsVO.	  setEntityType(CommonFunction.checkNull(vo.getCustomerRoleType()));	
	    		bankDetailsVO.	  setEntityId(CommonFunction.checkNull(vo.getEntityCustomerId()));	
	    		bankDetailsVO.	  setBankName(CommonFunction.checkNull(vo.getLbxBankID()));	
	    		bankDetailsVO.	  setBankBranch(CommonFunction.checkNull(vo.getBankBranch()));	
	    		bankDetailsVO.	  setAccountNo(CommonFunction.checkNull(vo.getAccountNo()));	
	    		bankDetailsVO.	  setAccountType(CommonFunction.checkNull(vo.getAccountType()));	
	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(bdetails1.get(2)));	
//	    		bankDetailsVO.	  setStatementMonth(CommonFunction.checkNull(vo.gets));	
//	    		bankDetailsVO.	  setStatmentDay(CommonFunction.checkNull(vo.gets));	
//	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(vo.gets));	
	    		if(CommonFunction.checkNull(bdetails1.get(7)).equalsIgnoreCase("Cr"))
	    		bankDetailsVO.	  setTotalCr(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(8))));
	    		else
	     		bankDetailsVO.	  setTotalDr(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(8))));	
	    		bankDetailsVO.	  setBalanceAmount(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(9))));	
	    		bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(5)));		
//	    		bankDetailsVO.	  setRemarks(CommonFunction.checkNull(vo.getRemarks()));
//	    		bankDetailsVO.	  setUploadFlag(CommonFunction.checkNull(vo.getu));
	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(vo.getMakerId()));	
	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(FormatUtility.dateFormat(vo.getMakerDate(),"yyyy-MM-dd")));	
	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(vo.getLastUpdatedBY()));	
	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(vo.getLastUpdateDate()));	
//	    		bankDetailsVO.	  setType(CommonFunction.checkNull(vo.gett));	
	    		bankDetailsVO.	  setType(CommonFunction.checkNull(bdetails1.get(3)));
	    		
	    		bankDetailsVO.	  setBranchCode(CommonFunction.checkNull(bdetails1.get(6)));	
	    		bankDetailsVO.	  setChequeNo(CommonFunction.checkNull(bdetails1.get(4)));	
//	    		bankDetailsVO.	  setTransactionId(CommonFunction.checkNull(vo.getTxnId()));	
	    		bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(1)));	
//	    		bankDetailsVO.	  setTransactionAmount(CommonFunction.checkNull(vo.gett));
	    		bankDetailsVO.	  setCaseId(CommonFunction.checkNull(vo.getCaseId()));
	    		if(CommonFunction.checkNull(bdetails1.get(9)).equalsIgnoreCase("-") || CommonFunction.checkNull(bdetails1.get(9)).endsWith("."))
	    			{
	    			String balanceValue = CommonFunction.checkNull(bdetails1.get(9)).equalsIgnoreCase("-")?CommonFunction.checkNull(bdetails1.get(9))+"0.0":CommonFunction.checkNull(bdetails1.get(9))+"0";
	    			bankDetailsVO.	  setBalanceAmount(balanceValue);
	    			bankDetailsVO.	  setErrorFlag("E");
	    			}
	    		
	    		reList.add(bankDetailsVO);
			}
			else if(bdetails1.size()>2)
			{
				bankDetailsVO 	=  new BankDetailsVO();
				System.out.println("valueDetailsList--------E---"+bdetails1);
				
				bankDetailsVO.	  setStmntEntryid(CommonFunction.checkNull(bdetails1.get(0)));	
	    	  	bankDetailsVO.	  setDocumentId(CommonFunction.checkNull(vo.getDocId()));	
	    		bankDetailsVO.	  setDocumentSeqId(CommonFunction.checkNull(bdetails1.get(0)));	
	    		bankDetailsVO.	  setRefId(CommonFunction.checkNull(vo.getRefId()));	
	    		bankDetailsVO.	  setEntityType(CommonFunction.checkNull(vo.getCustomerRoleType()));	
	    		bankDetailsVO.	  setEntityId(CommonFunction.checkNull(vo.getEntityCustomerId()));	
	    		bankDetailsVO.	  setBankName(CommonFunction.checkNull(vo.getLbxBankID()));	
	    		bankDetailsVO.	  setBankBranch(CommonFunction.checkNull(vo.getBankBranch()));	
	    		bankDetailsVO.	  setAccountNo(CommonFunction.checkNull(vo.getAccountNo()));	
	    		bankDetailsVO.	  setAccountType(CommonFunction.checkNull(vo.getAccountType()));	
	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(bdetails1.get(2)));	
//	    		bankDetailsVO.	  setStatementMonth(CommonFunction.checkNull(vo.gets));	
//	    		bankDetailsVO.	  setStatmentDay(CommonFunction.checkNull(vo.gets));	
//	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(vo.gets));	
	    		if(CommonFunction.checkNull(bdetails1.get(7)).equalsIgnoreCase("Cr"))
	    		bankDetailsVO.	  setTotalCr(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(8))));
	    		else
	     		bankDetailsVO.	  setTotalDr(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(8))));	
	    	//	bankDetailsVO.	  setBalanceAmount(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(9))));	
	    	//	bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(5)));		
//	    		bankDetailsVO.	  setRemarks(CommonFunction.checkNull(vo.getRemarks()));
//	    		bankDetailsVO.	  setUploadFlag(CommonFunction.checkNull(vo.getu));
	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(vo.getMakerId()));	
	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(FormatUtility.dateFormat(vo.getMakerDate(),"yyyy-MM-dd")));	
	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(vo.getLastUpdatedBY()));	
	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(vo.getLastUpdateDate()));	
//	    		bankDetailsVO.	  setType(CommonFunction.checkNull(vo.gett));	
	    	//	bankDetailsVO.	  setSol(CommonFunction.checkNull(bdetails1.get(3)));	
	    //		bankDetailsVO.	  setBranchCode(CommonFunction.checkNull(bdetails1.get(6)));	
	   // 		bankDetailsVO.	  setChequeNo(CommonFunction.checkNull(bdetails1.get(4)));	
//	    		bankDetailsVO.	  setTransactionId(CommonFunction.checkNull(vo.getTxnId()));	
	    		bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(1)));	
//	    		bankDetailsVO.	  setTransactionAmount(CommonFunction.checkNull(vo.gett));
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