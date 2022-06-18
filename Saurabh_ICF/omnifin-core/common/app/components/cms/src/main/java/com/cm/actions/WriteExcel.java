package com.cm.actions;

import java.io.File;
import java.io.IOException;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
public class WriteExcel {
	
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;
	public static void main(String[] args) throws WriteException, IOException {
		WriteExcel test = new WriteExcel();
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
			addCellData(sheet, 0, i, i + 10);
			// Second column
			addCellData(sheet, 1, i, i * i);
			//THIRD COLUMN
			addCellData(sheet, 2, i, i * i);
		}
	}
	private void addHeader(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}
	private void addCellData(WritableSheet sheet, int column, int row,Integer integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}
}
