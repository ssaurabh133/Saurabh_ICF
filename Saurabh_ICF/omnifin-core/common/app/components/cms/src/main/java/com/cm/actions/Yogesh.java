package com.cm.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.cm.vo.GenerateReportVO;
import com.connect.ConnectionDAO;
import com.logger.LoggerMsg;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Yogesh {
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;
	public static void main(String[] args) throws WriteException, IOException {
		Yogesh test = new Yogesh();
		test.setOutputFile("PARTS.xls");
		test.write();//THIS METHOD WILL WRITE AN EXCEL FILE WITH TWO SHEETS
		
		System.out.println("Please check the result file under PARTS.xls ");
	}
	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public void write() throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();
		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("MY SHEET-1", 0);
		workbook.createSheet("MY SHEET-2", 1);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet);
		
		excelSheet = workbook.getSheet(1);
		createLabel(excelSheet);
		createContent(excelSheet);
		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet)
			throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);
		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(	WritableFont.TIMES, 10, WritableFont.BOLD, false,UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);
		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);
		// Write a few headers
		addHeader(sheet, 0, 0, "PART NO");
		addHeader(sheet, 1, 0, "QUANTITY");
		addHeader(sheet, 2, 0, "DESCRIPTION");
	}
	private void createContent(WritableSheet sheet) throws WriteException,
			RowsExceededException {
		// Write a few number
		for (int i = 1; i < 10; i++) {
			// First column
			addCellData(sheet, 0, i, "i + 10");
			// Second column
			addCellData(sheet, 1, i, "i * i");
			//THIRD COLUMN
			addCellData(sheet, 2, i, "i * i");
		}
	}
	private void addHeader(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}
	private void addCellData(WritableSheet sheet, int column, int row,String data) throws WriteException, RowsExceededException {
	
		sheet.addCell(new Label(column,row,data));
	}
	
	
	public ArrayList generateReportDao(GenerateReportVO generateReportVO) {
		ArrayList generateReportData = new ArrayList();

		
		LoggerMsg.info("In generateReportDao  " );
		LoggerMsg.info(" getGenerateBankingDate  " +generateReportVO.getGenerateBankingDate());
		
		try {

			ArrayList mainlist = new ArrayList();
			ArrayList subList = new ArrayList();
			ArrayList subList1= new ArrayList();

			String query = "SELECT PDC_INSTRUMENT_ID, PDC_BPTYPE, PDC_BPID, PDC_INSTRUMENT_MODE, PDC_INSTRUMENT_TYPE," +
					" PDC_FLAG, PDC_INSTRUMENT_NO, PDC_ISSUEING_BANK_ID, PDC_ISSUEING_BRANCH_ID, " +
                            "  PDC_ISSUING_MICR_CODE, PDC_ISSUING_IFSC_CODE, PDC_ISSUEING_BANK_ACCOUNT, " +
                            "  PDC_INSTRUMENT_DATE, PDC_INSTRUMENT_AMOUNT, PDC_CLEARING_TYPE, PDC_BIN_NO, PDC_LOCATION, " +
                            "  PDC_REMARKS, PDC_STATUS, MAKER_ID, MAKER_DATE, AUTHOR_ID, AUTHOR_DATE " +
                        " FROM CR_PDC_INSTRUMENT_DTL " +
                      "  WHERE PDC_STATUS = 'A' " +
                     "     AND PDC_INSTRUMENT_DATE <= '"+generateReportVO.getGenerateBankingDate()+"' " +
                   "  ORDER BY PDC_INSTRUMENT_MODE, PDC_CLEARING_TYPE, PDC_INSTRUMENT_ID ";
					

			LoggerMsg.info("In selectReportData : " + query);
			mainlist = ConnectionDAO.sqlSelect(query);
			LoggerMsg.info("In selectWaiveOffData.....mainlist size: " + mainlist.size());
			
			Yogesh test = new Yogesh();
			test.setOutputFile("PARTS.xls");
			File file = new File("D:/LPO/BulkAssign/PARTS.xls");			
			WorkbookSettings wbSettings = new WorkbookSettings();			
			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);		
			workbook.createSheet("MY SHEET-1", 0);			
			//workbook.createSheet("MY SHEET-2", 1);
			WritableSheet excelSheet = workbook.getSheet(0);
			createLabel(excelSheet);
			
			//createContent(excelSheet);
			if(mainlist.size()>0)
			{
			for (int i = 0; i < mainlist.size(); i++) {

				subList = (ArrayList) mainlist.get(i);
				LoggerMsg.info("In if subList size: " +subList.size());
				if(i<mainlist.size()-1){
				subList1 = (ArrayList) mainlist.get(i+1);
				LoggerMsg.info("In if subList1 size: " +subList1.size());
				
				LoggerMsg.info(" subList: " +subList.get(14));
				LoggerMsg.info(" subList1: " +subList1.get(14));
				
				if( subList.get(14)!= subList1.get(14)){
					
					addCellData(excelSheet, 0, i, (String) subList.get(1));
					LoggerMsg.info("In if subList: " +subList.get(14));
					LoggerMsg.info("In if subList1: " +subList1.get(14));
				
				}
				
				}
				
				if (subList.size() > 0) {
					
					addCellData(excelSheet, 0, i, (String) subList.get(1));
					
				

					/*vo.setBusinessPartnerDesc((String) subList.get(1));
					vo.setInstrumentMode((String) subList.get(2));
					vo.setInstrumentType((String) subList.get(4));
					
								
					generateReportData.add(vo);*/
				}
			}
			}
			
		} catch (Exception e) {
			LoggerMsg.info(e.getMessage());
		}

		return generateReportData;

	}
	
	
}
