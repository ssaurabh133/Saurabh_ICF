package com.cm.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.ReceiptDAO;
import com.cm.dao.ReportsDAO;
import com.cm.vo.ReceiptMakerVO;
import com.communication.engn.dao.SmsDAO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReceiptMakerProcessAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(ReceiptMakerProcessAction.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.utill");
	String dbType = resource.getString("lbl.dbType");

	
	public ActionForward saveForReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("in saveForReceipt..........");

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		String makerID = null;
		String bDate = null;
		String defaultBranch = null;
		String compId="0";
		if (userobj != null) {
			makerID = userobj.getUserId();
			bDate = userobj.getBusinessdate();
			defaultBranch = userobj.getBranchId();
			compId = CommonFunction.checkNull(userobj.getCompanyId());
		} else {
			logger
					.info(" in saveForwardReceipt method of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		//session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		//session.removeAttribute("loanId");
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";

		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm) form;
		ReceiptMakerVO vo = new ReceiptMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				ReceiptMakerDynaValidatorForm);
		//By Manish Baranwal on 15-04-2014
		ReceiptDAO dao = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		//End By Manish Baranwal on 15-04-2014
		logger.info("Implementation class: " + dao.getClass());
		vo.setDefaultBranch(defaultBranch);
		vo.setMakerId(makerID);
		vo.setBusinessDate(bDate);
		vo.setRecStatus("P");
		ArrayList result=null;
		boolean status=dao.saveReceiptData(vo);
		logger.info("status:   "+status);
		if (status)
		{
			 result=dao.saveOrUpdateOrForwardOperations(compId,vo.getLbxLoanNoHID(),vo.getInstrumentID(),makerID,bDate,vo.getRecStatus(),"N");
		}
		if(result.size()>0)
		{
			String stat=CommonFunction.checkNull(result.get(0));
			logger.info("stat "+stat);
			if(CommonFunction.checkNull(stat).equalsIgnoreCase("S"))
			{
				   String instrumentId=CommonFunction.checkNull(result.get(1));
				   vo.setInstrumentID(instrumentId);
				
				   logger.info("instrumentId at save: "+instrumentId);
				    ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
				    request.setAttribute("savedReceipt", savedReceipt);
				    if (savedReceipt.size() > 0) {

						request.setAttribute("loanRecStatus", savedReceipt.get(0)
								.getLoanRecStatus());
						if (defaultBranch.equalsIgnoreCase(savedReceipt.get(0)
								.getLoanBranch())) {
							request.setAttribute("loanBranchStatus", 'N');
						} else {
							request.setAttribute("loanBranchStatus", 'Y');
						}
					}
					String chargeFlagQuery = ConnectionDAO
							.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
					if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					
						ArrayList charges = new ArrayList();
						charges = dao.getchargesDetailOnReceipt(vo);
						request.setAttribute("charges", charges);
						ArrayList otherAddCharges = dao
								.getOtherAddChargesDetailOnReceipt(vo);
						request.setAttribute("otherAddCharges", otherAddCharges);
						ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
						request.setAttribute("showTotal", showTotal);
						
					}
					request.setAttribute("instrumentID", instrumentId);
					request.setAttribute("canForward", "canForward");
					request.setAttribute("msg", "S");
			}
			else
			{
				String erroMsg=CommonFunction.checkNull(result.get(0));
				request.setAttribute("msg", erroMsg);
				String instrumentId=CommonFunction.checkNull(result.get(1));
				logger.info("instrumentId at without save: "+instrumentId+" loan id: "+CommonFunction.checkNull(vo.getLbxLoanNoHID()));
				if(CommonFunction.checkNull(instrumentId).equalsIgnoreCase("0")||CommonFunction.checkNull(instrumentId).equalsIgnoreCase(""))
				{
					//Manish Baranwal
					String loanid=CommonFunction.checkNull(vo.getLbxLoanNoHID());
					String installmentType=dao.getInstallmentTypeReciept(loanid);
					//String installmentTypeQuery="SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' ";
					//String installmentType=ConnectionDAO.singleReturn(installmentTypeQuery);
					ArrayList detailList=new ArrayList();
					vo.setLoanRepaymentType(installmentType);
					detailList.add(vo);
					request.setAttribute("savedReceipt", detailList);
					ArrayList otherChargeList=(ArrayList) request.getAttribute("savedReceipt");
					ReceiptMakerVO v0q=(ReceiptMakerVO) otherChargeList.get(0);
					String unsavedLengthOtherCharge[]=v0q.getLbxAllocChargeStringArray();
					List otherChargeCodeList=Arrays.asList(unsavedLengthOtherCharge);
					request.setAttribute("otherChargeCodeList", otherChargeCodeList);
				}
				else
				{
					 ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
					 request.setAttribute("savedReceipt", savedReceipt);
				}
				if (request.getParameter("loanRecStatus") != null
						&& !request.getParameter("loanRecStatus")
								.equalsIgnoreCase("")) {
					request.setAttribute("loanRecStatus", request
							.getParameter("loanRecStatus"));
				}
				
				String chargeFlagQuery=dao.getChargeReceipt();
				/*String chargeFlagQuery = ConnectionDAO
				.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
				if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					ArrayList charges = new ArrayList();
					//Manish
					String instrumentID=vo.getInstrumentID();
					String entryGrossAllocation=dao.getEntryGrossAllocationReceipt(instrumentID);
				    /*String entryGrossAllocationQuery="SELECT COUNT(1) FROM CR_GROSS_ALLOCATION_DTL WHERE INSTRUMENT_ID='"+vo.getInstrumentID()+"'";
				    String entryGrossAllocation=ConnectionDAO.singleReturn(entryGrossAllocationQuery);*/
				    logger.info("entryGrossAllocation: "+entryGrossAllocation );
				    if(CommonFunction.checkNull(entryGrossAllocation).equalsIgnoreCase("0"))
				    {
				    	charges = dao.getchargesDetailBeforeSave(vo);
				    }
				    else
				    {
				    	charges = dao.getchargesDetailOnReceipt(vo);
				    }
					request.setAttribute("charges", charges);
					ArrayList otherAddCharges = dao
					.getOtherAddChargesDetailOnReceipt(vo);
			        request.setAttribute("otherAddCharges",otherAddCharges);
					String allocationChargeCode = dao.getAllocationChargeCode(vo.getLbxLoanNoHID());
					session.setAttribute("allocationChargeCode", allocationChargeCode);
					ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
					request.setAttribute("showTotal", showTotal);
				}
				  
			}
			   
			
		}
		String amount = dao.getTotalRec(vo.getLbxLoanNoHID(),vo.getLbxBPType());
		request.setAttribute("amount", amount);
		return mapping.getInputForward();
	}
	
	// method added by neeraj tripathi for generate receipt report
	public ActionForward generateRecptReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		logger.info("In generateRecptReport");
		try 
		{
			HttpSession session = request.getSession();
			boolean flag = false;
			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String cname = "";
			String username = "";
			String bDate = "";
			String p_user_id="";
			if (userobj != null) {
				cname = userobj.getConpanyName();
				username = userobj.getUserName();
				bDate = userobj.getBusinessdate();
				p_user_id=userobj.getUserId();
			} else {
				logger
						.info(" in generateRecptReport() session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";
			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
			}
			logger.info("strFlag .............. " + strFlag);
			if (!strFlag.equalsIgnoreCase("")) 
			{
				if (strFlag.equalsIgnoreCase("sameUserSession")) {
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				} else if (strFlag.equalsIgnoreCase("BODCheck")) {
					context.setAttribute("msg", "B");
				}
				return mapping.findForward("logout");
			}
			String p_txnID=request.getParameter("frdLoanID");	
			String p_instrumentID=request.getParameter("frdInstrumentID");
			String p_printed_by=username+" ";
			String p_printed_date=CommonFunction.changeFormat(bDate);
			String p_company_name=cname+" ";
			
			//code for generating barcode start
			BitMatrix bitMatrix;
	        Writer writer = new QRCodeWriter();
	        try 
	        {
	        	 //  Write Barcode
	        	 p_instrumentID=CommonFunction.checkNull(p_instrumentID).trim();
	             bitMatrix = new Code128Writer().encode(p_instrumentID, BarcodeFormat.CODE_128, 150, 80, null);
	             String imagePath=getServlet().getServletContext().getRealPath("/")+"reports/barcode.gif";
	             MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File(imagePath)));
	        } 
	        catch (Exception e) 
	        {
	        	 e.printStackTrace();
	        }
			//barcode logic end
			//String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
			String p_company_logo="/OmniFin/reports/logo.bmp";
			String p_barcode_logo="/OmniFin/reports/barcode.gif";
			String p_backGround_logo="/OmniFin/reports/backGround.bmp";
			String reportPath="/reports/";
			String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
			String reportName="receiptMemoReport";
			
			ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
			logger.info("Implementation class: "+reportdao.getClass());
			reportName=reportdao.getReceiptReportName();
			if(CommonFunction.checkNull(reportName).trim().equalsIgnoreCase(""))
				reportName="receiptMemoReport";		
		
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				reportPath=reportPath+"MSSQLREPORTS/";
				SUBREPORT_DIR=SUBREPORT_DIR+"MSSQLREPORTS\\";
			}
			else
			{
				reportPath=reportPath+"MYSQLREPORTS/";
				SUBREPORT_DIR=SUBREPORT_DIR+"MYSQLREPORTS\\";
			}
		
			Connection connectDatabase = ConnectionDAO.getConnection();		
			Map<Object,Object> hashMap = new HashMap<Object,Object>();
			hashMap.put("p_txnID",p_txnID);
			hashMap.put("p_instrumentID",p_instrumentID);
			hashMap.put("p_printed_by",p_printed_by);
			hashMap.put("p_printed_date",p_printed_date);
			hashMap.put("p_company_name",p_company_name);
			hashMap.put("p_company_logo",p_company_logo);
			hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);	
			hashMap.put("p_barcode_logo",p_barcode_logo);				
			hashMap.put("IS_IGNORE_PAGINATION",true);
			hashMap.put("p_user_id",p_user_id);		
			hashMap.put("p_backGround_logo",p_backGround_logo);		
							
			
			
			logger.info("p_txnID  :  "+p_txnID);
			logger.info("p_instrumentID  :  "+p_instrumentID);
			logger.info("p_printed_by    :  "+p_printed_by);
			logger.info("p_printed_date  :  "+p_printed_date);
			logger.info("p_company_name  :  "+p_company_name);
			logger.info("p_company_logo  :  "+p_company_logo);
			logger.info("p_user_id       :  "+p_user_id);
			
				
			InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+reportName+".jasper");
			JasperPrint jasperPrint = null;			
			try
			{
				jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
				methodForHTML(reportName,hashMap,connectDatabase,response,jasperPrint,request);						
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally 
			{
				ConnectionDAO.closeConnection(connectDatabase, null);
				hashMap.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	public  void methodForHTML(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint,HttpServletRequest request)throws Exception
	{
		PrintWriter out=response.getWriter();
	    out.append("<head><script type='text/javascript' src='"+request.getContextPath()+"/js/report/report.js'></script></head>");
	    out.append("<body style='background-image: url(/OmniFin/reports/backGround.bmp)' onload=print();window.close()></body>");	
	   	String htmlReportFileName=reportName+".html";
		JRHtmlExporter exporter = new JRHtmlExporter();		
		response.setContentType("text/html");
        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);		
		float f1=1.2f;
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN ,Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,"");
        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS ,Boolean.TRUE); 
        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,"");
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
        exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO ,f1);
        exporter.exportReport();        
	}	 
	// mradul starts
	public ActionForward getReceiptAmountData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("in getReceiptAmountData..........");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String makerID = "";
		String bDate = "";
		String defaultBranch = "";
		if (userobj != null) {
			makerID = userobj.getUserId();
			bDate = userobj.getBusinessdate();
			defaultBranch = userobj.getBranchId();
		} else {
			logger.info(" in getReceiptAmountData method ----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";

		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		//Manish Baranwal
		ReceiptDAO dao = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		logger.info("Implementation class: " + dao.getClass());
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm) form;
		ReceiptMakerVO receiptVO = new ReceiptMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO,
				ReceiptMakerDynaValidatorForm);
		ArrayList receiptAmountData = dao.getreceiptAmountData(receiptVO);
		logger.info("receiptAmountData:::: " + receiptAmountData);
		request.setAttribute("receiptAmountData", receiptAmountData);
		return mapping.findForward("successAmount");
	}

	// mradul ends

	// Ritu
	public ActionForward deleteReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In ReceiptMakerViewAction deleteReceipt().... ");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
		String bDate = "";
		String branchId = "";
		// String result="success";
		if (userobj != null) {
			userId = userobj.getUserId();
			bDate = userobj.getBusinessdate();
			branchId = userobj.getBranchId();
		} else {
			logger
					.info("here in deleteReceipt () of ReceiptMakerViewAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();

		// String cond = request.getParameter("saveForward");
		// logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+cond);

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		String instrumentID = "";

		instrumentID = request.getParameter("instrumentID");

		logger.info("In   deleteReceipt ----1-------------->instrumentID "
				+ instrumentID);
		// logger.info("In ConsumerDispatchAction  execute id: " +
		// userobj.getBranchId());

		//Manish Baranwal
		ReceiptDAO service = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		logger.info("Implementation class: " + service.getClass());

		boolean status = service.deleteReceipt(instrumentID);
		String msg = "";
		if (status) {
			msg = "DS";
			request.setAttribute("msg", msg);

		} else {
			msg = "DN";
			request.setAttribute("msg", msg);
		}

		logger.info("In   deletePayment ----1-------------->status:-" + status);
		return mapping.findForward("SUCCESS");
	}

	public ActionForward updateAllocationChargeReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("in updateAllocationChargeReceipt..........");

		HttpSession session = request.getSession();

		UserObject userobj = (UserObject) session.getAttribute("userobject");
		// UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID = "";
		String bDate = "";
		String defaultBranch = "";
		if (userobj != null) {
			makerID = userobj.getUserId();
			bDate = userobj.getBusinessdate();
			defaultBranch = userobj.getBranchId();
		} else {
			logger
					.info(" in updateAllocationChargeReceipt metohd of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("loanId");
		Object sessionId = session.getAttribute("sessionID");
		//Manish Baranwal
		ReceiptDAO dao = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		logger.info("Implementation class: " + dao.getClass());
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";

		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm) form;
		ReceiptMakerVO receiptVO = new ReceiptMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO,
				ReceiptMakerDynaValidatorForm);

		receiptVO.setDefaultBranch(defaultBranch);
		receiptVO.setMakerId(makerID);
		receiptVO.setBusinessDate(bDate);

		String receiptMode = CommonFunction.checkNull(receiptVO
				.getReceiptMode());
		int existInstrumentNo = 0;
		if (CommonFunction.checkNull(receiptMode).equalsIgnoreCase("Q")
				|| CommonFunction.checkNull(receiptMode).equalsIgnoreCase("D")) {
			existInstrumentNo = dao.existInsNo(receiptVO);
			logger.info("existInstrumentNo -Q and D---" + existInstrumentNo);
		} else if (CommonFunction.checkNull(receiptMode).equalsIgnoreCase("N")
				|| CommonFunction.checkNull(receiptMode).equalsIgnoreCase("R")) {
			existInstrumentNo = dao.existInsNForNR(receiptVO);
			logger.info("existInstrumentNo-- N and R--- " + existInstrumentNo);
		} else {
			existInstrumentNo = 0;
		}

		// mradul starts
		boolean result = false;
		String reciptStatus = "";
		String receiptInventoryStatus = "";
		String msg = "";
		reciptStatus = dao.checkReciptStatus(receiptVO);
		logger.info("recipt number status" + reciptStatus);
		receiptInventoryStatus = dao.checkReciptStatusFromInventory(receiptVO);
		ArrayList<ReceiptMakerVO> savedReceipt = dao.getReceiptList(receiptVO);
		if (existInstrumentNo == 0) {
			// change by sachin
			if (reciptStatus.equalsIgnoreCase("0")) {
				// result=dao.updateOnReceiptSave(receiptVO);

				if (!receiptInventoryStatus.equalsIgnoreCase("0")) {
					String chargeFlagQuery=dao.getChargeReceipt();
					result = dao.updateOnReceiptSave(receiptVO);
					logger.info("result" + result);
					
					
					/*String chargeFlagQuery = ConnectionDAO
							.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
					if (CommonFunction.checkNull(chargeFlagQuery)
							.equalsIgnoreCase("Y")) {
						boolean allocationResult = dao
								.saveAllocationReceipt(receiptVO,"P");
						// Nishant space starts for charges
						ArrayList charges = new ArrayList();
						charges = dao.getchargesDetailOnReceipt(receiptVO);
						request.setAttribute("charges", charges);
						ArrayList otherAddCharges = dao
								.getOtherAddChargesDetailOnReceipt(receiptVO);
						request
								.setAttribute("otherAddCharges",
										otherAddCharges);
						ArrayList showTotal = dao.getshowTotalOnReceipt(receiptVO);
						request.setAttribute("showTotal",showTotal);
					}
					// Nishant space end for charges
				} else {

					msg = "RNI";
					request.setAttribute("receiptInventoryStatus", msg);

					request.setAttribute("savedReceipt", savedReceipt);
					request.setAttribute("canForward", "canForward");

				}
				// mradul changes

			} else {
				msg = "RS";

				request.setAttribute("reciptStatusNo", msg);
				request.setAttribute("savedReceipt", savedReceipt);
				request.setAttribute("canForward", "canForward");

			}
			// end by sachin
			if (result) {
				msg = "S";

				String amount = dao.getTotalRec(receiptVO.getLbxLoanNoHID(),
						receiptVO.getLbxBPType());
				request.setAttribute("amount", amount);

				savedReceipt = dao.getReceiptList(receiptVO);
				request.setAttribute("savedReceipt", savedReceipt);
				request.setAttribute("canForward", "canForward");

				if (savedReceipt.size() > 0) {

					request.setAttribute("loanRecStatus", savedReceipt.get(0)
							.getLoanRecStatus());
					if (defaultBranch.equalsIgnoreCase(savedReceipt.get(0)
							.getLoanBranch())) {
						request.setAttribute("loanBranchStatus", 'N');
					} else {
						request.setAttribute("loanBranchStatus", 'Y');
					}
				}
			} else {
				msg = "E";
				if (request.getParameter("loanRecStatus") != null
						&& !request.getParameter("loanRecStatus")
								.equalsIgnoreCase("")) {
					request.setAttribute("loanRecStatus", request
							.getParameter("loanRecStatus"));
				}

				logger.info("msg1" + msg);
			}
		} else {
			msg = "D";
			logger.info("msg2" + msg);
		}


		request.setAttribute("msg", msg);
		logger.info("result" + result);

		return mapping.findForward("updateOnSave");
	}

	public ActionForward getDefaultBusinessPartnerTypeReceipt(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// boolean flag=false;
		
		logger.info("getDefaultBusinessPartnerTypeReceipt......");
		
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String bDate = null;
		if (userobj == null) {
			logger
					.info("here in getDefaultBusinessPartnerTypeReceipt method of AjaxActionforCM action the session is out----------------");
			return mapping.findForward("sessionOut");
		} else {
			bDate = userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String lbxLoanNoHID = request.getParameter("lbxLoanNoHID");
		String bpType = request.getParameter("bpType");
		String vehicleNo = request.getParameter("vehicleNo");
		
		logger.info("getDefaultBusinessPartnerTypeReceipt lbxLoanNoHID---"
				+ lbxLoanNoHID + " bpType : " + bpType + "vehicleNo : " + vehicleNo);
		if (CommonFunction.checkNull(bpType).equalsIgnoreCase(""))
			bpType = "CS";

		ReceiptDAO dao = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		logger.info("Implementation class: " + dao.getClass());

		ArrayList bpList = dao.getDefaultBusinessPartnerTypeReceipt(
				lbxLoanNoHID, bpType);
		request.setAttribute("bpList", bpList);
		logger.info("getDefaultBusinessPartnerTypeReceipt    Size:---"
				+ bpList.size());
		String amount = dao.getTotalRec(lbxLoanNoHID, bpType);
		logger.info("Total Receivable   :  " + amount);
		request.setAttribute("amount", amount);
		// Nishant space starts for charges
		ReceiptMakerVO vo = new ReceiptMakerVO();
		if (bpList.size() > 0) {
			vo = (ReceiptMakerVO) bpList.get(0);
		} else {
			vo.setBusinessPartnerType("CUSTOMER");
			vo.setLbxBPType("CS");
		}
		vo.setLbxLoanNoHID(lbxLoanNoHID);
		vo.setBusinessDate(bDate);
		vo.setVehicleNo(vehicleNo);
		
		String chargeFlagQuery=dao.getChargeReceipt();
		/*String chargeFlagQuery = ConnectionDAO
				.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
		if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
			ArrayList charges = new ArrayList();
			charges = dao.getchargesDetailBeforeSave(vo);
			request.setAttribute("charges", charges);
			ArrayList otherAddCharges = dao
			.getOtherAddChargesDetailOnReceipt(vo);
	        request.setAttribute("otherAddCharges",otherAddCharges);
			String allocationChargeCode = dao.getAllocationChargeCode(vo.getLbxLoanNoHID());
			session.setAttribute("allocationChargeCode", allocationChargeCode);
			session.setAttribute("loanId", lbxLoanNoHID);
			ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
			request.setAttribute("showTotal", showTotal);
		}
		String repoFlag= dao.getRepoFlag(lbxLoanNoHID);
		session.setAttribute("repoFlagMarked", repoFlag);
		// Nishant space end for charges
	
		return mapping.findForward("getDefaultBusinessPartnerTypeReceipt");
	}
	
	public ActionForward onDirectForwardOnReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("in onDirectForwardOnReceipt..........");

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		String makerID = null;
		String bDate = null;
		String defaultBranch = null;
		String compId="0";
		if (userobj != null) {
			makerID = userobj.getUserId();
			bDate = userobj.getBusinessdate();
			defaultBranch = userobj.getBranchId();
			compId = CommonFunction.checkNull(userobj.getCompanyId());
		} else {
			logger
					.info(" in saveForwardReceipt method of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("loanId");
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";

		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm) form;
		ReceiptMakerVO vo = new ReceiptMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				ReceiptMakerDynaValidatorForm);
		
		ReceiptDAO dao = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		logger.info("Implementation class: " + dao.getClass());
		vo.setDefaultBranch(defaultBranch);
		vo.setMakerId(makerID);
		vo.setBusinessDate(bDate);
		vo.setRecStatus("F");
		ArrayList result=null;
		boolean status=dao.saveReceiptData(vo);
		logger.info("status:   "+status);
		if (status)
		{
			 result=dao.saveOrUpdateOrForwardOperations(compId,vo.getLbxLoanNoHID(),vo.getInstrumentID(),makerID,bDate,vo.getRecStatus(),"Y");
		}
		if(result.size()>0)
		{
			String stat=CommonFunction.checkNull(result.get(0));
			logger.info("stat "+stat);
			if(CommonFunction.checkNull(stat).equalsIgnoreCase("S"))
			{
				   String instrumentId=CommonFunction.checkNull(result.get(1));
				   vo.setInstrumentID(instrumentId);
				
				    ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
				    request.setAttribute("savedReceipt", savedReceipt);
				    if (savedReceipt.size() > 0) {

						request.setAttribute("loanRecStatus", savedReceipt.get(0)
								.getLoanRecStatus());
						if (defaultBranch.equalsIgnoreCase(savedReceipt.get(0)
								.getLoanBranch())) {
							request.setAttribute("loanBranchStatus", 'N');
						} else {
							request.setAttribute("loanBranchStatus", 'Y');
						}
					}
				    String chargeFlagQuery=dao.getChargeReceipt();
					/*String chargeFlagQuery = ConnectionDAO
							.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
					if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					
						ArrayList charges = new ArrayList();
						charges = dao.getchargesDetailOnReceipt(vo);
						request.setAttribute("charges", charges);
						ArrayList otherAddCharges = dao
								.getOtherAddChargesDetailOnReceipt(vo);
						request.setAttribute("otherAddCharges", otherAddCharges);
						ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
						request.setAttribute("showTotal", showTotal);
						request.setAttribute("instrumentID", instrumentId);
						
					}
					request.setAttribute("msg", "F");
			}
			else
			{
				String erroMsg=CommonFunction.checkNull(result.get(0));
				request.setAttribute("msg", erroMsg);
				String instrumentId=CommonFunction.checkNull(result.get(1));
				logger.info("instrumentId at without save or forward : "+instrumentId+" loan Id: "+CommonFunction.checkNull(vo.getLbxLoanNoHID()));
				if(CommonFunction.checkNull(instrumentId).equalsIgnoreCase("0")||CommonFunction.checkNull(instrumentId).equalsIgnoreCase(""))
				{
					String loanid=CommonFunction.checkNull(vo.getLbxLoanNoHID());
					String installmentType=dao.getInstallmentTypeReciept(loanid);
					/*String installmentTypeQuery="SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' ";
					String installmentType=ConnectionDAO.singleReturn(installmentTypeQuery);*/
					ArrayList detailList=new ArrayList();
					vo.setLoanRepaymentType(installmentType);
					detailList.add(vo);
					
					request.setAttribute("savedReceipt", detailList);
					ArrayList otherChargeList=(ArrayList) request.getAttribute("savedReceipt");
					ReceiptMakerVO v0q=(ReceiptMakerVO) otherChargeList.get(0);
					String unsavedLengthOtherCharge[]=v0q.getLbxAllocChargeStringArray();
					List otherChargeCodeList=Arrays.asList(unsavedLengthOtherCharge);
					request.setAttribute("otherChargeCodeList", otherChargeCodeList);
				}
				else
				{
					 ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
					 request.setAttribute("savedReceipt", savedReceipt);
				}
				if (request.getParameter("loanRecStatus") != null
						&& !request.getParameter("loanRecStatus")
								.equalsIgnoreCase("")) {
					request.setAttribute("loanRecStatus", request
							.getParameter("loanRecStatus"));
				}
				String chargeFlagQuery=dao.getChargeReceipt();
				/*String chargeFlagQuery = ConnectionDAO
				.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
				if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					ArrayList charges = new ArrayList();
					  String instrumentID=vo.getInstrumentID();
					  String entryGrossAllocation=dao.getEntryGrossAllocationReceipt(instrumentID);
					  /*String entryGrossAllocationQuery="SELECT COUNT(1) FROM CR_GROSS_ALLOCATION_DTL WHERE INSTRUMENT_ID='"+vo.getInstrumentID()+"'";
					    String entryGrossAllocation=ConnectionDAO.singleReturn(entryGrossAllocationQuery);*/
					    logger.info("entryGrossAllocation: "+entryGrossAllocation);
					    if(CommonFunction.checkNull(entryGrossAllocation).equalsIgnoreCase("0"))
					    {
					    	charges = dao.getchargesDetailBeforeSave(vo);
					    }
					    else
					    {
					    	charges = dao.getchargesDetailOnReceipt(vo);
					    }
					request.setAttribute("charges", charges);
					ArrayList otherAddCharges = dao
					.getOtherAddChargesDetailOnReceipt(vo);
			        request.setAttribute("otherAddCharges",otherAddCharges);
					String allocationChargeCode = dao.getAllocationChargeCode(vo.getLbxLoanNoHID());
					session.setAttribute("allocationChargeCode", allocationChargeCode);
					ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
					request.setAttribute("showTotal", showTotal);
				}
				  
			}
			   
			
		}
	
		return mapping.getInputForward();
	}
	
	public ActionForward checkRepoForwardOnReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("in checkRepoForwardOnReceipt..........");

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		String makerID = null;
		String bDate = null;
		String defaultBranch = null;
		String compId="0";
		if (userobj != null) {
			makerID = userobj.getUserId();
			bDate = userobj.getBusinessdate();
			defaultBranch = userobj.getBranchId();
			compId = CommonFunction.checkNull(userobj.getCompanyId());
		} else {
			logger
					.info(" in saveForwardReceipt method of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("loanId");
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";

		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm) form;
		ReceiptMakerVO vo = new ReceiptMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				ReceiptMakerDynaValidatorForm);
		
		ReceiptDAO dao = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		logger.info("Implementation class: " + dao.getClass());
		vo.setDefaultBranch(defaultBranch);
		vo.setMakerId(makerID);
		vo.setBusinessDate(bDate);
		vo.setRecStatus("F");
		ArrayList result=null;
		boolean status=dao.saveReceiptData(vo);
		logger.info("status:   "+status);
		if (status)
		{
			 result=dao.saveOrUpdateOrForwardOperations(compId,vo.getLbxLoanNoHID(), vo.getInstrumentID(),makerID,bDate,vo.getRecStatus(),"Y");
		}
		if(result.size()>0)
		{
			String stat=CommonFunction.checkNull(result.get(0));
			logger.info("stat "+stat);
			if(CommonFunction.checkNull(stat).equalsIgnoreCase("S"))
			{
				   String instrumentId=CommonFunction.checkNull(result.get(1));
				   vo.setInstrumentID(instrumentId);
				
				    ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
				    request.setAttribute("savedReceipt", savedReceipt);
				    if (savedReceipt.size() > 0) {

						request.setAttribute("loanRecStatus", savedReceipt.get(0)
								.getLoanRecStatus());
						if (defaultBranch.equalsIgnoreCase(savedReceipt.get(0)
								.getLoanBranch())) {
							request.setAttribute("loanBranchStatus", 'N');
						} else {
							request.setAttribute("loanBranchStatus", 'Y');
						}
					}
				    String chargeFlagQuery=dao.getChargeReceipt();
					/*String chargeFlagQuery = ConnectionDAO
							.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
					if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					
						ArrayList charges = new ArrayList();
						charges = dao.getchargesDetailOnReceipt(vo);
						request.setAttribute("charges", charges);
						ArrayList otherAddCharges = dao
								.getOtherAddChargesDetailOnReceipt(vo);
						request.setAttribute("otherAddCharges", otherAddCharges);
						ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
						request.setAttribute("showTotal", showTotal);
						request.setAttribute("instrumentID", instrumentId);
						
					}
					request.setAttribute("msg", "F");
			}
			else
			{
				String erroMsg=CommonFunction.checkNull(result.get(0));
				request.setAttribute("msg", erroMsg);
				String instrumentId=CommonFunction.checkNull(result.get(1));
				logger.info("instrumentId at without save or forward : "+instrumentId+" loan Id: "+CommonFunction.checkNull(vo.getLbxLoanNoHID()));
				if(CommonFunction.checkNull(instrumentId).equalsIgnoreCase("0")||CommonFunction.checkNull(instrumentId).equalsIgnoreCase(""))
				{
					String loanid=CommonFunction.checkNull(vo.getLbxLoanNoHID());
					String installmentType=dao.getInstallmentTypeReciept(loanid);
					/*String installmentTypeQuery="SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' ";
					String installmentType=ConnectionDAO.singleReturn(installmentTypeQuery);*/
					ArrayList detailList=new ArrayList();
					vo.setLoanRepaymentType(installmentType);
					detailList.add(vo);
					
					request.setAttribute("savedReceipt", detailList);
					ArrayList otherChargeList=(ArrayList) request.getAttribute("savedReceipt");
					ReceiptMakerVO v0q=(ReceiptMakerVO) otherChargeList.get(0);
					String unsavedLengthOtherCharge[]=v0q.getLbxAllocChargeStringArray();
					List otherChargeCodeList=Arrays.asList(unsavedLengthOtherCharge);
					request.setAttribute("otherChargeCodeList", otherChargeCodeList);
				}
				else
				{
					 ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
					 request.setAttribute("savedReceipt", savedReceipt);
				}
				
				if (request.getParameter("loanRecStatus") != null
						&& !request.getParameter("loanRecStatus")
								.equalsIgnoreCase("")) {
					request.setAttribute("loanRecStatus", request
							.getParameter("loanRecStatus"));
				}
				String chargeFlagQuery=dao.getChargeReceipt();
				/*String chargeFlagQuery = ConnectionDAO
				.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
				if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					ArrayList charges = new ArrayList();
					  String instrumentID=vo.getInstrumentID();
					  String entryGrossAllocation=dao.getEntryGrossAllocationReceipt(instrumentID);
					  /*String entryGrossAllocationQuery="SELECT COUNT(1) FROM CR_GROSS_ALLOCATION_DTL WHERE INSTRUMENT_ID='"+vo.getInstrumentID()+"'";
					    String entryGrossAllocation=ConnectionDAO.singleReturn(entryGrossAllocationQuery);*/
					    logger.info("entryGrossAllocation: "+entryGrossAllocation);
					    if(CommonFunction.checkNull(entryGrossAllocation).equalsIgnoreCase("0"))
					    {
					    	charges = dao.getchargesDetailBeforeSave(vo);
					    }
					    else
					    {
					    	charges = dao.getchargesDetailOnReceipt(vo);
					    }
					request.setAttribute("charges", charges);
					ArrayList otherAddCharges = dao
					.getOtherAddChargesDetailOnReceipt(vo);
			        request.setAttribute("otherAddCharges",otherAddCharges);
					String allocationChargeCode = dao.getAllocationChargeCode(vo.getLbxLoanNoHID());
					session.setAttribute("allocationChargeCode", allocationChargeCode);
					ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
					request.setAttribute("showTotal", showTotal);
				}
				  
			}
			   
			
		}
	
		return mapping.getInputForward();
	}
	
	public ActionForward saveForwardReceiptTemp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("in saveForwardReceiptTemp..........");

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		String makerID = null;
		String bDate = null;
		String defaultBranch = null;
		String compId="0";
		if (userobj != null) {
			makerID = userobj.getUserId();
			bDate = userobj.getBusinessdate();
			defaultBranch = userobj.getBranchId();
			compId = CommonFunction.checkNull(userobj.getCompanyId());
		} else {
			logger
					.info(" in saveForwardReceipt method of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		//session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		//session.removeAttribute("loanId");
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";

		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm) form;
		ReceiptMakerVO vo = new ReceiptMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				ReceiptMakerDynaValidatorForm);
		
		
		
		
			
		ReceiptDAO dao = (ReceiptDAO) DaoImplInstanceFactory
				.getDaoImplInstance(ReceiptDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		logger.info("Implementation class: " + dao.getClass());
		vo.setDefaultBranch(defaultBranch);
		vo.setMakerId(makerID);
		vo.setBusinessDate(bDate);
		vo.setRecStatus("F");
		ArrayList result=null;
		boolean status=dao.saveReceiptData(vo);
		logger.info("status:   "+status);
		if (status)
		{
			 result=dao.saveOrUpdateOrForwardOperations(compId,vo.getLbxLoanNoHID(),vo.getInstrumentID(),makerID,bDate,vo.getRecStatus(),"N");
		}
		if(result.size()>0)
		{
			String stat=CommonFunction.checkNull(result.get(0));
			logger.info("stat "+stat);
			if(CommonFunction.checkNull(stat).equalsIgnoreCase("S"))
			{
				   String instrumentId=CommonFunction.checkNull(result.get(1));
				   vo.setInstrumentID(instrumentId);
				   logger.info("instrumentId at save or forward : "+instrumentId);
				    ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
				    request.setAttribute("savedReceipt", savedReceipt);
				    if (savedReceipt.size() > 0) {

						request.setAttribute("loanRecStatus", savedReceipt.get(0)
								.getLoanRecStatus());
						if (defaultBranch.equalsIgnoreCase(savedReceipt.get(0)
								.getLoanBranch())) {
							request.setAttribute("loanBranchStatus", 'N');
						} else {
							request.setAttribute("loanBranchStatus", 'Y');
						}
					}
				    String chargeFlagQuery=dao.getChargeReceipt();
					/*String chargeFlagQuery = ConnectionDAO
							.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
					if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					
						ArrayList charges = new ArrayList();
						charges = dao.getchargesDetailOnReceipt(vo);
						request.setAttribute("charges", charges);
						ArrayList otherAddCharges = dao
								.getOtherAddChargesDetailOnReceipt(vo);
						request.setAttribute("otherAddCharges", otherAddCharges);
						ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
						request.setAttribute("showTotal", showTotal);
						request.setAttribute("instrumentID", instrumentId);
						
					}
					request.setAttribute("msg", "F");
					
				//	Rohit changes starts for implement shreeSms for fiafl
					
					String EventName="Receipt";
					String qury1="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+EventName+"' and TEMPLATE_TYPE='S' ";
	  	    		String recSMS=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury1.toString()));
	  	    		if(recSMS.equalsIgnoreCase("A"))
	  	    		{
	  	    			new SmsDAOImpl().getEmailDetails( vo.getLbxLoanNoHID(), bDate, EventName);	
	  	    		}
					
				
					//Rohit changes end for implement shreeSms for fiafl
			}
			else
			{
				String erroMsg=CommonFunction.checkNull(result.get(0));
				logger.info("erroMsg: "+erroMsg);
				request.setAttribute("msg", erroMsg);

				if (request.getParameter("loanRecStatus") != null
										&& !request.getParameter("loanRecStatus")
												.equalsIgnoreCase("")) {
									request.setAttribute("loanRecStatus", request
											.getParameter("loanRecStatus"));
								}		
				String instrumentId=CommonFunction.checkNull(result.get(1));
				logger.info("instrumentId at without save or forward : "+instrumentId+" loan Id: "+CommonFunction.checkNull(vo.getLbxLoanNoHID()));
				 vo.setInstrumentID(instrumentId);
				if(CommonFunction.checkNull(instrumentId).equalsIgnoreCase("0")||CommonFunction.checkNull(instrumentId).equalsIgnoreCase(""))
				{
					String loanid=CommonFunction.checkNull(vo.getLbxLoanNoHID());
					String installmentType=dao.getInstallmentTypeReciept(loanid);
					/*String installmentTypeQuery="SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' ";
					String installmentType=ConnectionDAO.singleReturn(installmentTypeQuery);*/
					ArrayList detailList=new ArrayList();
					vo.setLoanRepaymentType(installmentType);
					detailList.add(vo);
					request.setAttribute("savedReceipt", detailList);
					
					ArrayList otherChargeList=(ArrayList) request.getAttribute("savedReceipt");
					ReceiptMakerVO v0q=(ReceiptMakerVO) otherChargeList.get(0);
					String unsavedLengthOtherCharge[]=v0q.getLbxAllocChargeStringArray();
					List otherChargeCodeList=Arrays.asList(unsavedLengthOtherCharge);
					request.setAttribute("otherChargeCodeList", otherChargeCodeList);
					
				}
				else
				{
					 ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
					 request.setAttribute("savedReceipt", savedReceipt);
				}
				
				String chargeFlagQuery=dao.getChargeReceipt();
				/*String chargeFlagQuery = ConnectionDAO
				.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");*/
				if (CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y")) {
					ArrayList charges = new ArrayList();
					
					  String instrumentID=vo.getInstrumentID();
					  String entryGrossAllocation=dao.getEntryGrossAllocationReceipt(instrumentID);
					  /*String entryGrossAllocationQuery="SELECT COUNT(1) FROM CR_GROSS_ALLOCATION_DTL WHERE INSTRUMENT_ID='"+vo.getInstrumentID()+"'";
					    String entryGrossAllocation=ConnectionDAO.singleReturn(entryGrossAllocationQuery);*/
					    logger.info("entryGrossAllocation: "+entryGrossAllocation);
					    if(CommonFunction.checkNull(entryGrossAllocation).equalsIgnoreCase("0"))
					    {
					    	charges = dao.getchargesDetailBeforeSave(vo);
					    }
					    else
					    {
					    	charges = dao.getchargesDetailOnReceipt(vo);
					    }
					request.setAttribute("charges", charges);
					ArrayList otherAddCharges = dao
					.getOtherAddChargesDetailOnReceipt(vo);
			        request.setAttribute("otherAddCharges",otherAddCharges);
					String allocationChargeCode = dao.getAllocationChargeCode(vo.getLbxLoanNoHID());
					session.setAttribute("allocationChargeCode", allocationChargeCode);
					ArrayList showTotal = dao.getshowTotalOnReceipt(vo);
					request.setAttribute("showTotal", showTotal);
					
				}
				  
			}
			   
			
		}
		
		logger.info("instrumentId before set in session for memo report : "+vo.getInstrumentID()+" loan Id: "+CommonFunction.checkNull(vo.getLbxLoanNoHID()));
		session.setAttribute("forwardLoanID",vo.getLbxLoanNoHID());
		session.setAttribute("forwardInstrumentID",vo.getInstrumentID());
		session.setAttribute("autoManualFlag", vo.getStatusReceipt());
		
		String amount = dao.getTotalRec(vo.getLbxLoanNoHID(),vo.getLbxBPType());
		request.setAttribute("amount", amount);
		return mapping.getInputForward();
	}
}