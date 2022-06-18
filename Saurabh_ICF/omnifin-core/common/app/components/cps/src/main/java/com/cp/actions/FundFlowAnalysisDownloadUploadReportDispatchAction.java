/**
 * 
 */
package com.cp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Array;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.eclipse.jdt.internal.compiler.impl.IntConstant;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
import com.cp.dao.FileUtilityDao;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;
import com.cp.process.FileUploadProcessTemplete;
import com.cp.util.DateUtility;
import com.cp.vo.FundFlowDownloadUploadVo;

/**
 * @author pranaya.gajpure
 *
 */
public class FundFlowAnalysisDownloadUploadReportDispatchAction extends DispatchAction
{
	private static final Logger logger = Logger.getLogger(FundFlowAnalysisDownloadUploadReportDispatchAction.class.getName());
	
	public ActionForward fundFlowAnalysisDownloadUploadReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		ServletContext context = getServlet().getServletContext();
		logger.info("In fundFlowAnalysisDownloadUploadReport.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("here in fundFlowAnalysisDownloadUploadReport method of FundFlowAnalysisDownloadUploadReportDispatchAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}
		Object sessionId = session.getAttribute("sessionID");
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}
			return mapping.findForward("logout");
		}
		return mapping.findForward("success");
	}

	public ActionForward generateFundFlowReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In generateFundFlowReport.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
		{
			logger.info("here in generateFundFlowReport method of FundFlowAnalysisDownloadUploadReportDispatchAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}
		Object sessionId = session.getAttribute("sessionID");
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}

		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}
			return mapping.findForward("logout");
		}
		
		String caseId = CommonFunction.checkNull(session.getAttribute("fundFlowDealId"));
		FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		FundFlowAnalysisDAO analysisDAO = (FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
		String refId = dao.getApplicationReferenceNo(caseId);
		logger.info("caseId----------"+caseId);
		logger.info("caseId----------"+refId);
		ArrayList list  = analysisDAO.getBankStatementData(refId);
		
		HSSFWorkbook workbook = new HSSFWorkbook();      
        HSSFSheet firstSheet = workbook.createSheet("FundFlow");
    	HSSFRow row=null;
		int size = list!=null?list.size():0;
		ArrayList sublist = null;
		for(int i = 0;i<size;i++)
		{
			 HSSFRow row1 = firstSheet.createRow(i);
			sublist=(ArrayList)list.get(i);
			
			if(sublist!=null && sublist.size()>0)
			{
				for(int j = 0;j<sublist.size();j++)
				{
					HSSFCell cell = row1.createCell(j);
					cell.setCellValue(CommonFunction.checkNull(sublist.get(j)));
				}
			}
		}
		ServletOutputStream out1= null; 
        try 
        {  
			response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=FundFlowReport.xls");
            out1=response.getOutputStream();
        	workbook.write(out1);
         } 
        catch (Exception e) 
        {  
        	e.printStackTrace(); 
        } 
        finally
        {  
        	out1.flush();  
        	out1.close();  
        }
	
        return null;
		
	}
	
	public ActionForward uploadFundFlowAnalysisReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In uploadFundFlowAnalysisReport.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		ArrayList in = new ArrayList();
		ArrayList out = new ArrayList();
		ArrayList outMessages = new ArrayList();
		
		String s1 = null;
		String s2 = null;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
		{
			logger.info("here in uploadFundFlowAnalysisReport method of FundFlowAnalysisDownloadUploadReportDispatchAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}
		Object sessionId = session.getAttribute("sessionID");
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}

		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}
			return mapping.findForward("logout");
		}
		
		String caseId = CommonFunction.checkNull(session.getAttribute("fundFlowDealId"));
		FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		FundFlowAnalysisDAO analysisDAO = (FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
		String refId = dao.getApplicationReferenceNo(caseId);
		logger.info("caseId----------"+caseId);
		logger.info("caseId----------"+refId);
		String filePath = dao.getParameterMSTInfo("UPLOAD_PATH");
		File directory = new File(filePath + "\\"+refId+"\\");
		DynaActionForm dynaActionForm = (DynaActionForm)form;
		FormFile myFile= (FormFile)dynaActionForm.get("docFile");
		
		String fileName = myFile.getFileName();
		long  timeStamp = System.nanoTime();
		//fileName=fileName+"_"+userId+"_"+timeStamp;
		String message ="";
		// Get the servers upload directory real path name
		filePath = directory.getAbsolutePath();
		logger.info("AbsolutePath::::::::::::::::::::::::"+filePath);
		
		/* Save file on the server */
		if (!fileName.equals("")) {
			logger.info("Server path:" + filePath);
			// Create file
			File fileToCreate = new File(filePath, fileName);
			int fileSize = myFile.getFileSize(); // 1048576 bytes = 1 MB
			logger.info("Size of file= " + fileSize);
			if (fileSize < 26314400) {
				// If file does not exists create file
				FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
				fileOutStream.write(myFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
				message="S";
				
			} else {
				message = "U";
			}
		} 
		
		Workbook wb = FileUploadProcessTemplete
				.getFileReadConnectionForXls(filePath,fileName);
		System.out.println(filePath+"--"+fileName);
		Sheet sheet = wb.getSheetAt(0);
		Row row;
		Cell cell;
		FundFlowDownloadUploadVo vo=null;

		int rows = 0; // No of rows
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;
		int lineCounter = 0;
		int readingFileCount=0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		for (int j = 0; j < 10 || j < rows; j++) {
			row = sheet.getRow(j);
			if (row != null) {
				tmp = sheet.getRow(j).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}
		int countS=0;
		int countF=0;
		for (int k = 1; k < rows; k++) {
			lineCounter += 1;
			row = sheet.getRow(k);
			if(null==row)
			{
				break;
			}
			vo=new FundFlowDownloadUploadVo();
			vo.setStmnt_entry_id(CommonFunction.checkNull(row.getCell(0)));
			vo.setDocument_seq_id(CommonFunction.checkNull(row.getCell(1)));//DOCUMENT_SEQ_ID
			vo.setRef_id(CommonFunction.checkNull(row.getCell(2)));//REF_ID
			vo.setBank_id(CommonFunction.checkNull(row.getCell(3)));//BANK_Id
			vo.setBank_name(CommonFunction.checkNull(row.getCell(4)));//BANK_NAME
			vo.setBank_branch(CommonFunction.checkNull(row.getCell(5)));//BANK_BRANCH
			vo.setErrorFlagPdf(CommonFunction.checkNull(row.getCell(6)));//error_flag_pdf
			vo.setAccount_no(CommonFunction.checkNull(row.getCell(7)));//ACCOUNT_NO	
			vo.setStatment_date(CommonFunction.checkNull(row.getCell(8)));//STATMENT_DATE
			vo.setTotal_cr(CommonFunction.checkNull(row.getCell(9)));//TOTAL_CR
			vo.setTotal_dr(CommonFunction.checkNull(row.getCell(10)));//TOTAL_DR
			vo.setBalance_amount(CommonFunction.checkNull(row.getCell(11)));//BALANCE_AMOUNT
			vo.setNarration(CommonFunction.checkNull(row.getCell(12)));//NARRATION
			vo.setIgnore_flag(CommonFunction.checkNull(row.getCell(13)));//IGNORE_FLAG
			vo.setRemarks(CommonFunction.checkNull(row.getCell(14)));//Remarks
			vo.setMaker_id(CommonFunction.checkNull(row.getCell(15)));//TYPE//MAKER_ID
			vo.setMaker_date(CommonFunction.checkNull(row.getCell(16)));//MAKER_DATE
			vo.setType(CommonFunction.checkNull(row.getCell(17)));
			vo.setSol(CommonFunction.checkNull(row.getCell(18)));//SOL
			vo.setBranch_code(CommonFunction.checkNull(row.getCell(19)));//BRANCH_CODE
			vo.setCheque_no(CommonFunction.checkNull(row.getCell(20)));//CHEQUE_NO
			vo.setTransaction_id(CommonFunction.checkNull(row.getCell(21)));//TRANSACTION_ID
			vo.setValue_date(CommonFunction.checkNull(row.getCell(22)));//VALUE_DATE
			vo.setTransaction_amount(CommonFunction.checkNull(row.getCell(23)));//TRANSACTION_AMOUNT
			vo.setBusiness_credit(CommonFunction.checkNull(row.getCell(24)));//BUSINESS_CREDIT
			vo.setBusiness_debit(CommonFunction.checkNull(row.getCell(25)));//BUSINESS_DEBIT
			vo.setCounter_entry(CommonFunction.checkNull(row.getCell(26)));//COUNTER_ENTRY
			vo.setEmi_debits(CommonFunction.checkNull(row.getCell(27)));
			vo.setTransfer_entry(CommonFunction.checkNull(row.getCell(28)));
			vo.setDd_issued_entry(CommonFunction.checkNull(row.getCell(29)));
			vo.setBouncing_inward_entry(CommonFunction.checkNull(row.getCell(30)));
			vo.setBouncing_outward_entry(CommonFunction.checkNull(row.getCell(31)));
			vo.setLoan_credit_entry(CommonFunction.checkNull(row.getCell(32)));
			vo.setDd_cancellation_entry(CommonFunction.checkNull(row.getCell(33)));
			vo.setInterest_credits_entry(CommonFunction.checkNull(row.getCell(34)));
			vo.setCharges_entry(CommonFunction.checkNull(row.getCell(35)));
			vo.setOthers_entry(CommonFunction.checkNull(row.getCell(36)));
			/*if(k>0)
			{*/
				String strStatus=CommonFunction.checkNull(analysisDAO.InsertUpdateFundFlowBankDtl(vo));
				vo=null;
				if(strStatus.equalsIgnoreCase("S"))
				{
					countS=countS+1;
				}
				else
				{
					countF=countF+1;
				}
			/*}*/
			
			
		}
		if(countS>0)
		{
			in.add(caseId);
			in.add(refId);
			in.add(0);
			in.add("FFA");
			out.add(s1);
			out.add(s2);
			outMessages=(ArrayList) ConnectionDAO.callSP("Banking_Entries_Classification", in, out);
			s1=CommonFunction.checkNull(outMessages.get(0));
	        s2=CommonFunction.checkNull(outMessages.get(1));
	        logger.info("s1-----------------1: "+s1);
			logger.info("s2-----------------1: "+s2);
			if(!s2.equalsIgnoreCase(""))
			{
				request.setAttribute("msg", "error");
				request.setAttribute("sms", s2);
			}
			else
			request.setAttribute("msg", "S");
		}
		else
		{
			request.setAttribute("msg", "E");
		}
	//	readDataFormFundFlowFile(filePath,fileName);
        return mapping.findForward("success");
		
	}
	
	public static void main(String[] args) {
		
		Workbook wb = FileUploadProcessTemplete
				.getFileReadConnectionForXls("C:\\Users\\saorabh.kumar\\Downloads","FundFlowReport(1).xls");
		//System.out.println(filePath+"--"+fileName;
		Sheet sheet = wb.getSheetAt(0);
		Row row;
		Cell cell;
		FundFlowDownloadUploadVo vo=null;

		int rows = 0; // No of CommonFunction.checkNull(rows
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;
		int lineCounter = 0;
		int readingFileCount=0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few CommonFunction.checkNull(rows
	
		for (int j = 0; j < 10 || j < rows; j++) {
			row = sheet.getRow(j);
			if (row != null) {
				tmp = sheet.getRow(j).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;

			}
		}
		int countS=0;
		int countF=0;
		for (int k = 1; k < rows; k++) {
			lineCounter += 1;
			row = sheet.getRow(k);
			if(null==row)
			{
				break;
			}
		
			System.out.print("   "+CommonFunction.checkNull(row.getCell(0)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(1)));//DOCUMENT_SEQ_ID
			System.out.print("   "+CommonFunction.checkNull(row.getCell(2)));//REF_ID
			System.out.print("   "+CommonFunction.checkNull(row.getCell(3)));//BANK_Id
			System.out.print("   "+CommonFunction.checkNull(row.getCell(4)));//BANK_NAME
			System.out.print("   "+CommonFunction.checkNull(row.getCell(5)));//BANK_BRANCH
			System.out.print("   "+CommonFunction.checkNull(row.getCell(6)));//ACCOUNT_NO	
			System.out.print("   "+CommonFunction.checkNull(row.getCell(7)));//STATMENT_DATE
			System.out.print("   "+CommonFunction.checkNull(row.getCell(8)));//TOTAL_CR
			System.out.print("   "+CommonFunction.checkNull(row.getCell(9)));//TOTAL_DR
			System.out.print("   "+CommonFunction.checkNull(row.getCell(10)));//BALANCE_AMOUNT
			System.out.print("   "+CommonFunction.checkNull(row.getCell(11)));//NARRATION
			System.out.print("   "+CommonFunction.checkNull(row.getCell(12)));//IGNORE_FLAG
			System.out.print("   "+CommonFunction.checkNull(row.getCell(13)));//Remarks
			System.out.print("   "+CommonFunction.checkNull(row.getCell(14)));//TYPE//MAKER_ID
			System.out.print("   "+CommonFunction.checkNull(row.getCell(15)));//MAKER_DATE
			System.out.print("   "+CommonFunction.checkNull(row.getCell(16)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(17)));//SOL
			System.out.print("   "+CommonFunction.checkNull(row.getCell(18)));//BRANCH_CODE
			System.out.print("   "+CommonFunction.checkNull(row.getCell(19)));//CHEQUE_NO
			System.out.print("   "+CommonFunction.checkNull(row.getCell(20)));//TRANSACTION_ID
			System.out.print("   "+CommonFunction.checkNull(row.getCell(21)));//VALUE_DATE
			System.out.print("   "+CommonFunction.checkNull(row.getCell(22)));//TRANSACTION_AMOUNT
			System.out.print("   "+CommonFunction.checkNull(row.getCell(23)));//BUSINESS_CREDIT
			System.out.print("   "+CommonFunction.checkNull(row.getCell(24)));//BUSINESS_DEBIT
			System.out.print("   "+CommonFunction.checkNull(row.getCell(25)));//COUNTER_ENTRY
			System.out.print("   "+CommonFunction.checkNull(row.getCell(26)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(27)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(28)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(29)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(30)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(31)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(32)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(33)));
			System.out.print("   "+CommonFunction.checkNull(row.getCell(34)));
			System.out.println("   "+CommonFunction.checkNull(row.getCell(35)));
			System.out.println("-----");
	}
	}
}
