package com.cp.pdfProcess;
import javax.ejb.Remove;

import com.cp.pdfProcess.BankStatementforPNBPdf;

public class Test extends BankStatementforPNBPdf
{
	public static void main(String args[]) 
	{
		
		/*String emiReceivedDate="5234Cr5234234Cr";*/
	
		// emiReceivedDate = emiReceivedDateCheck(emiReceivedDate);*/
		 String financialYear = "2015-2016";
		 /*financialYear = financialYear.substring(0, 4);*/
		 /*System.out.println("financialYear-----------"+financialYear);*/
		  financialYear = financialYear.substring(financialYear.lastIndexOf("-")+1,financialYear.length());
		System.out.println("financialYear----------"+financialYear);
		// String colStartPositions[] = {"1111","2222","3333","444","","","555"};
		
		/*
			 //if(!colStartPositions[i].isEmpty())
				 for(String strLine : colStartPositions)
				 {
					if(!strLine.isEmpty())
					{
						System.out.println("colStartPositions-----------"+strLine);
					}
				 }
		 */
		
		
	
	}

	/*public static String emiReceivedDateCheck(String emiReceivedDate )
	{
		if(emiReceivedDate.isEmpty())
		{
			System.out.println("String is blank");
		}
		else
		{
			System.out.println("String is not blank");
		}
		if(emiReceivedDate.contains("Cr"))
		{
			emiReceivedDate =	emiReceivedDate.replace("Cr","");
			 System.out.println("emiReceivedDate-------"+emiReceivedDate);
		}
			
			return emiReceivedDate;
	}*/
}
