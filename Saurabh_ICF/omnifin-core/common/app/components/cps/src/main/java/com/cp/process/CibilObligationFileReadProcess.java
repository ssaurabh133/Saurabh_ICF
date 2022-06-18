package com.cp.process;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FileUplaodDao;
import com.cp.vo.CibilObligationVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class CibilObligationFileReadProcess {
	private static final Logger logger = Logger.getLogger(CibilObligationFileReadProcess.class.getName());
	public static boolean cibilObligationSheetFileReading(Object ob) {
		UnderwritingDocUploadVo vo =(UnderwritingDocUploadVo)ob;
		boolean status =false;
		logger.info("in cibilObligationSheetFileReading");
		try {
			Workbook wb = FileUploadProcessTemplete
					.getFileReadConnectionForXls(vo.getDocPath(),
							vo.getFileName());
			
			Sheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;

			int rows = 0; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			int cols = 0; // No of columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it
			// doesn't start from first few rows
		
			for (int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}
			
			ArrayList<CibilObligationVo> list = readDataFromCibilObligationFile(sheet,rows,cols,vo);
			status = saveRecordCibilObligation(list);
			
			
			
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static ArrayList<CibilObligationVo> readDataFromCibilObligationFile(Sheet sheet, int cols,int rows,Object ob)
	{
		logger.info("in readDataFromCibilObligationFile");
		Row row;
		Cell cell;
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		int r = 0,c=0;
		ArrayList<CibilObligationVo> list = new ArrayList<CibilObligationVo>();
		try {
			
			CibilObligationVo	obligationVo =null;
			for ( r = 1; r < rows; r++) {
				
				
				row = sheet.getRow(r);
				if (row != null) {
					obligationVo=new CibilObligationVo();
					obligationVo.setCustIdN(CommonFunction.checkNull(row.getCell(1)));
					obligationVo.setCustDetail(CommonFunction.checkNull(row.getCell(2)));//custDetail
					obligationVo.setScore(CommonFunction.checkNull(row.getCell(3)));//score
					obligationVo.setMember(CommonFunction.checkNull(row.getCell(4)));//member
					obligationVo.setAccType(CommonFunction.checkNull(row.getCell(5)));//accType
					obligationVo.setAccno(CommonFunction.checkNull(row.getCell(6)));//accno
					obligationVo.setDpd(CommonFunction.checkNull(row.getCell(7)));//dpd	
					obligationVo.setOwnIndi(CommonFunction.checkNull(row.getCell(8)));//ownIndi
					obligationVo.setDateOpen(CommonFunction.checkNull(row.getCell(9)));//dateOpen
					obligationVo.setDateLastPay(CommonFunction.checkNull(row.getCell(10)));//dateLastPay
					obligationVo.setDateReported(CommonFunction.checkNull(row.getCell(11)));//dateReported
					obligationVo.setDateClosed(CommonFunction.checkNull(row.getCell(12)));//dateClosed
					obligationVo.setSenAmt(CommonFunction.checkNull(row.getCell(13)));//senAmt
					obligationVo.setCurrBal(CommonFunction.checkNull(row.getCell(14)));//currBal
					obligationVo.setAmtOverdue(CommonFunction.checkNull(row.getCell(15)));//amtOverdue
					obligationVo.setWritOffStatus(CommonFunction.checkNull(row.getCell(16)));//writOffStatus
					obligationVo.setTenure(CommonFunction.checkNull(row.getCell(17)));//tenure
					obligationVo.setCollateral(CommonFunction.checkNull(row.getCell(18)));//collateral
					obligationVo.setCollateralType(CommonFunction.checkNull(row.getCell(19)));//collateralType
					obligationVo.setCreditLimit(CommonFunction.checkNull(row.getCell(20)));//creditLimit
					obligationVo.setCashLimit(CommonFunction.checkNull(row.getCell(21)));//cashLimit
					obligationVo.setInterestRate(CommonFunction.checkNull(row.getCell(22)));//interestRate
					obligationVo.setEmiAmt(CommonFunction.checkNull(row.getCell(23)));//emiAmt
					obligationVo.setWritOffPrinAmt(CommonFunction.checkNull(row.getCell(24)));//writOffPrinAmt
					obligationVo.setWritOffTotalAmt(CommonFunction.checkNull(row.getCell(25)));//writOffTotalAmt
					obligationVo.setSettlementAmt(CommonFunction.checkNull(row.getCell(26)));//settlementAmt
					obligationVo.setPaymtFrequency(CommonFunction.checkNull(row.getCell(27)));//paymtFrequency
					obligationVo.setActPaymtAmt(CommonFunction.checkNull(row.getCell(28)));//actPaymtAmt
					obligationVo.setErrCode(CommonFunction.checkNull(row.getCell(29)));//errCode
					obligationVo.setErrCodeEntryDate(CommonFunction.checkNull(row.getCell(30)));//errCodeEntryDate
					obligationVo.setCblRemEntryDate(CommonFunction.checkNull(row.getCell(31)));//cblRemEntryDate
					obligationVo.setCblRemCode(CommonFunction.checkNull(row.getCell(32)));//CblRemCode
					obligationVo.setDisputeRmkEntryDate(CommonFunction.checkNull(row.getCell(33)));//disputeRmkEntryDate
					obligationVo.setDisputeRmk1(CommonFunction.checkNull(row.getCell(34)));//disputeRmk1
					obligationVo.setDisputeRmk2(CommonFunction.checkNull(row.getCell(35)));//disputeRmk2
					obligationVo.setSuitfild(CommonFunction.checkNull(row.getCell(36)));//suitfild
					obligationVo.setMakerId(vo.getMakerId());
					obligationVo.setMakerDate(vo.getMakerDate());
					obligationVo.setCaseId(vo.getCaseId());
					obligationVo.setCaseCustomerId(vo.getEntityCustomerId());
					obligationVo.setEntityType(vo.getDocEntity());
					obligationVo.setDocumentId(vo.getDocId());
					
					list.add(obligationVo);
				}
				
			}
		} catch (Exception e) {
			logger.info("Problem in file Reading  "+e.getMessage());
		}
			
			return list;
	}
	
	public static boolean saveRecordCibilObligation(ArrayList<CibilObligationVo> list)
	{
		FileUplaodDao dao = (FileUplaodDao)DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		return dao.saveRecordCibilObligation(list);
	}
}
