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
import org.apache.struts.validator.DynaValidatorForm;
import org.eclipse.jdt.internal.compiler.impl.IntConstant;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
import com.cp.dao.FileUtilityDao;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;
import com.cp.fundFlowDaoImplMYSQL.FundFlowAnalysisDAOImpl;
import com.cp.dao.OcrDAO;
import com.cp.process.FileUploadProcessTemplete;
import com.cp.util.DateUtility;
import com.cp.vo.FundFlowDownloadUploadVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class AccountUploadDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(AccountUploadDispatchAction.class.getName());
	
	public ActionForward bankAccountdtlUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In fundFlowAnalysisDownloadUploadReport.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
				{
					logger.info("here in fundFlowAnalysisDownloadUploadReport method of AccountUploadDispatchAction  action the session is out----------------");
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

	
	public ActionForward uploadBankAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In uploadFundFlowAnalysisReport.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		String bDate="";
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
				{
					logger.info("here in uploadFundFlowAnalysisReport method of AccountUploadDispatchAction  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				else
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
					bDate=userobj.getBusinessdate();
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
		
		String caseId = request.getParameter("caseId");
		logger.info("caseId1-------------------------------------------->"+caseId);
	/*	String caseId = CommonFunction.checkNull(request.getParameter("caseId"));*/
		if(caseId.equalsIgnoreCase(""))
		{
			caseId = CommonFunction.checkNull(session.getAttribute("caseId"));
			logger.info("caseId-------------------------------------------->"+session.getAttribute("caseId"));
		}
		request.setAttribute("caseId",caseId);
		
		FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		String customerId = dao.getCustomerID(caseId);
		String entityType = dao.getCustomerRoleType(caseId);
		String refId = dao.getApplicationReferenceNo(caseId);
		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		DynaValidatorForm UnderwriterUploadDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo,	UnderwriterUploadDynaValidatorForm);
		String docType = CommonFunction.checkNull(request.getParameter("docType"));
		
		String customerName = dao.getCustomerNameByCustomerId(customerId);
		
		uwDocVo.setCustomerId(customerId);
		uwDocVo.setCustomerName(customerName);
		
		OcrDAO ocrDAO = (OcrDAO)DaoImplInstanceFactory.getDaoImplInstance(OcrDAO.IDENTITY);
		uwDocVo.setDealId(caseId);
		uwDocVo.setMakerId(userId);
		uwDocVo.setUserName(userId);
		uwDocVo.setMakerDate(bDate);
		FundFlowAnalysisDAO analysisDAO = (FundFlowAnalysisDAO)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAO.IDENTITY);
		String filePath = dao.getParameterMSTInfo("UPLOAD_PATH");
		filePath=filePath+"\\"+refId;
		File directory = new File(filePath + "\\");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		DynaActionForm dynaActionForm = (DynaActionForm)form;
		FormFile myFile= (FormFile)dynaActionForm.get("docFile");
		
		String fileName = myFile.getFileName();
		long  timeStamp = System.nanoTime();
		String message ="";
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
					FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
					fileOutStream.write(myFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
					message="S";
			} else {
				message = "E";
				logger.info("File size exceeds the upper limit of 25 MB.");
			} 
			
		} 
		uwDocVo.setFileName(fileName);
		uwDocVo.setDocPath(filePath);
		uwDocVo.setRefId(refId);
		uwDocVo.setDocEntity(entityType);
		uwDocVo.setDocType(docType);
		Workbook wb = FileUploadProcessTemplete
				.getFileReadConnectionForXls(filePath,fileName);
		System.out.println(filePath+"--"+fileName);
		logger.info("uw.getDocType----"+docType);
		logger.info("fileName----"+fileName);
		logger.info("filePath----"+filePath);
		logger.info("todate----"+uwDocVo.getToDate());
		
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
		ArrayList<FundFlowDownloadUploadVo> valueList = new ArrayList<FundFlowDownloadUploadVo>();
		for (int k = 1; k < rows; k++) {
			lineCounter += 1;
			row = sheet.getRow(k);
			if(null==row)
			{
				break;
			}
			vo=new FundFlowDownloadUploadVo();
			
			logger.info("in uploadBankAccount partyname::::::::::::::::::::::::"+CommonFunction.checkNull(row.getCell(1)));
			//vo.setSerialNo(CommonFunction.checkNull(row.getCell(0)));//S.No.
			vo.setRef_id(CommonFunction.checkNull(caseId));//Case_ID
			vo.setCaseCustomerID(CommonFunction.checkNull(customerId));//Customer_Id
			vo.setEntityType(CommonFunction.checkNull(entityType));//Entity_Type
			vo.setAccount_no(CommonFunction.checkNull(row.getCell(0)));//ACCOUNT_NO
			vo.setPartyName(CommonFunction.checkNull(row.getCell(1)));//Party_name
			vo.setLoanNo(CommonFunction.checkNull(row.getCell(2)));//Loan_No
			//vo.setBank_id(CommonFunction.checkNull(row.getCell(3)));//BANK_Id
			vo.setMaker_id(CommonFunction.checkNull(userId));//MAKER_ID
			vo.setMaker_date(CommonFunction.checkNull(bDate));//MAKER_DATE
			/*if(k>0)
			{
				String strStatus=CommonFunction.checkNull(analysisDAO.InsertBankAccountDtl(vo));
				if(strStatus.equalsIgnoreCase("S"))
				{
					
					ocrDAO.uploadUnderwritingData(uwDocVo);
					countS=countS+1;
				}
				else
				{
					countF=countF+1;
				}
			}*/
			valueList.add(vo);
			
		}
		
		if(valueList.size()>0)
		{
			String strStatus=CommonFunction.checkNull(analysisDAO.InsertBankAccountDtl(valueList));
			if(strStatus.equalsIgnoreCase("S"))
			{
				
				ocrDAO.uploadUnderwritingData(uwDocVo);
				countS=countS+1;
			}
			else
			{
				countF=countF+1;
			}
		}
		
		
		if(countS>0)
		{
			request.setAttribute("msg", "S");
		}
		else
		{
			request.setAttribute("msg", "E");
		}
		
        return mapping.findForward("success");
		
	}
	public ActionForward generateAccountUploadFormat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In generateAccountUploadFormat.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		if(userobj==null)
				{
					logger.info("here in generateAccountUploadFormat method of AccountUploadDispatchAction  action the session is out----------------");
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
		
		//String caseId = CommonFunction.checkNull(session.getAttribute("fundFlowDealId"));
		FundFlowAnalysisDAOImpl analysisDAO = (FundFlowAnalysisDAOImpl)DaoImplInstanceFactory.getDaoImplInstance(FundFlowAnalysisDAOImpl.IDENTITY);
		ArrayList list  = analysisDAO.getAccountStatmentData();
		
		HSSFWorkbook workbook = new HSSFWorkbook();      
        HSSFSheet firstSheet = workbook.createSheet("BankAccountDtl");
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
            response.setHeader("Content-Disposition", "attachment; filename=BankAccountDetail.xls");
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

	
	

	
}
