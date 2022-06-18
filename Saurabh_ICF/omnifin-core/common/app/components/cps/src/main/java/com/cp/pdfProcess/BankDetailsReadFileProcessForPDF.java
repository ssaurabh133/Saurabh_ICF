package com.cp.pdfProcess;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.connect.CommonFunction;
import com.cp.process.BankDetailsReadFileProcessForXLS;
import com.cp.process.FileUploadProcessTemplete;
import com.cp.process.NomenClatureProcess;
import com.cp.vo.UnderwritingDocUploadVo;

public class BankDetailsReadFileProcessForPDF {
	private static final Logger logger = Logger
			.getLogger(BankDetailsReadFileProcessForPDF.class.getName());

	public static ArrayList readingFileProcess(String path, String fileName,UnderwritingDocUploadVo vo) 
	{
		//UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();

		BankStatementforHDFCPdf bankStatementforHDFCPdf = new BankStatementforHDFCPdf();
		
		vo.setDocType("BS");

		String csvFile = path + "//" + fileName;

		logger.info("File name " + csvFile);

		BufferedReader br = null;

		String line = "";
		String cvsSplitBy = ",";

		boolean flag = false;
		int count = 0;
		int i = 0;
		int lineCounter = 0;
		int readingFileCount = 0;
		boolean headerFlag = false;
		ArrayList list = new ArrayList();
		if (fileName.contains("PUNJA")) {
			String fileNameWithAbsolutePath = csvFile;
			String firstHeadingLineColLabel = "Txn Srl";
			list = BankStatementforPNBPdf.readUsingIText$PNB(vo, csvFile,firstHeadingLineColLabel);
		}
		else if(fileName.contains("STATE"))
		{
			String fileNameWithAbsolutePath = csvFile;
			String firstHeadingLineColLabel = "Txn Date";
			list = BankStatementforSBOPPdf.readUsingIText$SBOP(vo, csvFile,firstHeadingLineColLabel);
		}
		else if(fileName.contains("HDFCB"))
		{
			String fileNameWithAbsolutePath = csvFile;
			String firstHeadingLineColLabel = "Date";
			list = bankStatementforHDFCPdf.readUsingIText$HDFC(vo, csvFile,firstHeadingLineColLabel);
			
		}
		else if(fileName.contains("ICICI"))
		{
			String fileNameWithAbsolutePath = csvFile;
			String firstHeadingLineColLabel = "Date";
			list = BankStatementforICICI.readUsingIText$ICICI(vo, csvFile,firstHeadingLineColLabel);
			
		}
		else if(fileName.contains("KOTAK"))
		{
			String fileNameWithAbsolutePath = csvFile;
			String firstHeadingLineColLabel = "Date";
			list = BankStatementforKOTAK.readUsingIText$KOTAK(vo, csvFile,firstHeadingLineColLabel);
			
		}

		System.out.println("Done");
		return list;
	}
}
