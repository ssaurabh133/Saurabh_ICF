
package com.cm.actions;

import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.GenerateBatchDAO;
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.vo.GenerateBatchVO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.cp.dao.CreditProcessingDAO;





public class GenerateBatchDispatchAction extends DispatchAction 
{
	private static final Logger logger = Logger.getLogger(GenerateBatchDispatchAction.class.getName());	
	public ActionForward generateBatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in generateBatch()of GenerateBatchDispatchAction change");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate="";
		
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			businessDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("here in generateBatch method of GenerateBatchDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		GenerateBatchVO vo= new GenerateBatchVO();
		DynaValidatorForm dynaForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,dynaForm);
		vo.setUserId(userId);
		vo.setBusinessDate(businessDate);
		ArrayList<GenerateBatchVO> record=new ArrayList<GenerateBatchVO>();
		String finalDate=vo.getPrestDate();
		finalDate=CommonFunction.changeFormat(finalDate);
		vo.setFinalDate(finalDate);
		record.add(vo);
		request.setAttribute("record",record);
		//GenerateBatchDAO dao = new GenerateBatchDAOImpl();
		GenerateBatchDAO dao=(GenerateBatchDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBatchDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String retVal=dao.generateBatch(vo);
		
		String noOfRejectedRecordQuery="SELECT COUNT(1) FROM TEMP_GENERATE_BATCH WHERE IFNULL(UPLOADED_FLAG,'Y')='N' ";
		String noOfUploadedRecordQuery="SELECT COUNT(1) FROM TEMP_GENERATE_BATCH WHERE IFNULL(UPLOADED_FLAG,'Y')='Y' ";
		String noOfRejectedRecord=ConnectionDAO.singleReturn(noOfRejectedRecordQuery);
		String noOfUploadedRecord=ConnectionDAO.singleReturn(noOfUploadedRecordQuery);
		if(CommonFunction.checkNull(noOfRejectedRecord).equalsIgnoreCase("0") && !CommonFunction.checkNull(noOfUploadedRecord).equalsIgnoreCase("0")){
			java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			String dateFormat=resource.getString("lbl.dateInDao");
			ArrayList list=new ArrayList();
			String batchQuery="select distinct a.pdc_instrument_mode,a.pdc_clearing_type,b.bank_id from cr_pdc_instrument_dtl a join cr_loan_dtl l on l.loan_id=a.PDC_LOAN_ID "
					+ "join TEMP_GENERATE_BATCH b on l.loan_no=b.loan_no "
					+ "where a.PDC_INSTRUMENT_DATE=STR_TO_DATE('"+vo.getPrestDate().trim()+"','"+dateFormat+"') and a.PRESENTATION_DATE is null AND  l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS in ('F','P') "
					+ "and a.PDC_STATUS = 'A' AND a.PDC_PURPOSE IN ('PRE EMI','INSTALLMENT') ";
			list=ConnectionDAO.sqlSelect(batchQuery);
			for(int i=0;i<list.size();i++)
    	    {	    	    
				ArrayList subList1=(ArrayList)list.get(i);
    	    	if(subList1.size()>0)
    			{
        		String instrumentMode=CommonFunction.checkNull(subList1.get(0)).trim();
        		String clearingType=CommonFunction.checkNull(subList1.get(1)).trim();
        		String bankId=CommonFunction.checkNull(subList1.get(2)).trim();
        		boolean st=dao.generateMultipleBatch(vo.getPrestDate(),instrumentMode,clearingType,bankId,userId,businessDate);
        	}
    	    }
		}
		
		if(CommonFunction.checkNull(retVal).equalsIgnoreCase("error"))		
			request.setAttribute("error","error");
		else if(CommonFunction.checkNull(retVal).equalsIgnoreCase("empty"))		
			request.setAttribute("empty","empty");
		else 
		{
			request.setAttribute("success","success");
			
			String totalInstrument= dao.getTotalInstrument(retVal);
			request.setAttribute("totalInstrument", totalInstrument);
		
			String totalInstrumentAmount= dao.getTotalInstrumentAmount(retVal);
			request.setAttribute("totalInstrumentAmount", totalInstrumentAmount);
		}
		
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 		// changed by asesh
		//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
		ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
		request.setAttribute("dealCatList", dealCatList);
		//InstrumentCapturingDAO dao1 = new InstrumentCapturingDAOImpl();
		InstrumentCapturingDAO dao1=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
		logger.info("Implementation class: "+dao1.getClass()); 
		ArrayList<InstructionCapMakerVO> clearingTypeList = dao1.getClearingType();
		request.setAttribute("clearingTypeList", clearingTypeList);
		vo=null;
		finalDate=null;
		dao=null;
		creditProcessing=null;
		dao1=null;
		return mapping.findForward("success");
	}
	public ActionForward generatePaySlip(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in generatePaySlip()of GenerateBatchDispatchAction change");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId =null;
		String businessDate=null;
		String p_company_name=null;
		if(userobj!=null)
		{
			userId = userobj.getUserId();	
			businessDate=userobj.getBusinessdate();
			p_company_name=userobj.getConpanyName();
		}
		else
		{
			logger.info("here in generateBatch method of GenerateBatchDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
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
		
		String p_batch_no =request.getParameter("batchNo");
		String p_bank_id =request.getParameter("bankID");
		String p_branch_id =request.getParameter("branchID");
		String p_account =request.getParameter("account");		
		
		String p_image_I=getServlet().getServletContext().getRealPath("/")+"reports\\imageI.bmp";
		String p_image_L=getServlet().getServletContext().getRealPath("/")+"reports\\imageL.bmp";
		String p_image_O=getServlet().getServletContext().getRealPath("/")+"reports\\imageO.bmp";
		
		//logger.info("p_batch_no     :     "+p_batch_no);
		//logger.info("p_bank_id      :     "+p_bank_id);
		//logger.info("p_branch_id    :     "+p_branch_id);
		//logger.info("p_account      :     "+p_account);
		//logger.info("p_image_I      :     "+p_image_I);
		//logger.info("p_image_L      :     "+p_image_L);
		//logger.info("p_image_O      :     "+p_image_O);
		//logger.info("p_company_name :     "+p_company_name);
		//p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports\\logo.bmp";

		Connection connectDatabase = ConnectionDAO.getConnection();		
		HashMap hashMap=new HashMap();
		
		hashMap.put("p_batch_no",p_batch_no);
		hashMap.put("p_bank_id",p_bank_id);
		hashMap.put("p_branch_id",p_branch_id);
		hashMap.put("p_account",p_account);
		hashMap.put("p_image_I",p_image_I);
		hashMap.put("p_image_L",p_image_L);
		hashMap.put("p_image_O",p_image_O);
		hashMap.put("p_company_name",p_company_name);
		String reporttype="P";
		reporttype=CommonFunction.checkNull(request.getParameter("reportType")).trim();
		if(reporttype.equalsIgnoreCase(""))
			reporttype="P";
		String reportName="paySlip";
		logger.info("reporttype"+reporttype);
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		//String dateFormat=resource.getString("lbl.dateInDao");
		String dbType=resource.getString("lbl.dbType");
		String reportPath="/reports/";
		if(dbType.equalsIgnoreCase("MSSQL"))
			reportPath=reportPath+"MSSQLREPORTS/";
		else
			reportPath=reportPath+"MYSQLREPORTS/";
		
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath+"paySlip.jasper");
		JasperPrint jasperPrint = null;
		
		try
		{
			
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			if(reporttype.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(reporttype.equals("E"))				
				methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);
		}	
		catch(Exception e)
		{e.printStackTrace();}	
		finally
		{
				connectDatabase.close();
				p_batch_no=null;
				p_bank_id=null;
				p_branch_id=null;
				p_account=null;
				p_image_I=null;
				p_image_L=null;
				p_image_O=null;
				userId=null;
				businessDate=null;
				p_company_name=null;
				reporttype=null;
				reportName=null;
		}		
		return null;
	}
	public void methodForPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{
		
		 
		JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
		File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
		byte[] buffer = new byte[1024];
		int n = 0;
		while ((n = fin.read(buffer)) != -1) 
			outStream.write(buffer, 0, n);			
		outStream.flush();
		fin.close();
		outStream.close();
	}	
	public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
		String excelReportFileName=reportName+".xls";		
		JExcelApiExporter exporterXLS = new JExcelApiExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
		response.setContentType("application/vnd.ms-excel");
		exporterXLS.exportReport();
	}
	public ActionForward viewBatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in viewBatch()of GenerateBatchDispatchAction");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String prestDate="";
		if(userobj==null)
	    {
			logger.info("here in deleteBatch method of GenerateBatchDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		else
			prestDate=userobj.getBusinessdate();
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		String status=(String)request.getParameter("status");
		logger.info("record Status   :   "+status);		
		if(status != null)
		if(CommonFunction.checkNull(status).equalsIgnoreCase("Pending for Finalized"))
			request.setAttribute("pending","pending"); 
		else
			request.setAttribute("finalized","finalized");
		
		logger.info("current page link .......... "+request.getParameter("d-6936073-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-6936073-p")==null || request.getParameter("d-6936073-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-6936073-p"));
		logger.info("current page link ................ "+request.getParameter("d-6936073-p"));
		
		GenerateBatchVO vo= new GenerateBatchVO();
		DynaValidatorForm dynaForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,dynaForm);
		ArrayList<GenerateBatchVO> record=new ArrayList<GenerateBatchVO>();
		String finalDate=vo.getPrestDate();
		finalDate=CommonFunction.changeFormat(finalDate);
		vo.setFinalDate(finalDate);
		record.add(vo);
		request.setAttribute("record",record);
		GenerateBatchDAO dao=(GenerateBatchDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBatchDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String batchNo=vo.getBatchNo();
		logger.info("in viewBatch()  batchNo  :  "+batchNo);		
		ArrayList<GenerateBatchVO>viewBatchList=dao.viewBatch(batchNo,currentPageLink);
		request.setAttribute("viewBatchList",viewBatchList);
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
		request.setAttribute("dealCatList", dealCatList);
		InstrumentCapturingDAO dao1=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
		logger.info("Implementation class: "+dao1.getClass()); 
		ArrayList<InstructionCapMakerVO> clearingTypeList = dao1.getClearingType();
		request.setAttribute("clearingTypeList", clearingTypeList);
		String totalInstrument= dao.getTotalInstrument(batchNo);
		request.setAttribute("totalInstrument", totalInstrument);
		String totalInstrumentAmount= dao.getTotalInstrumentAmount(batchNo);
		request.setAttribute("totalInstrumentAmount", totalInstrumentAmount);
		vo=null;
		finalDate=null;
		dao=null;
		creditProcessing=null;
		dao1=null;
		return mapping.findForward("success");
	}
	public ActionForward openDepositPopup(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in openDepositPopup()of GenerateBatchDispatchAction");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
	    {
			logger.info("here in openDepositPopup method of GenerateBatchDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		String batchNo=request.getParameter("batchNo");
		String prestDate=request.getParameter("prestDate");
		GenerateBatchVO vo= new GenerateBatchVO();
		vo.setBatchNo(batchNo);
		vo.setPresentationDate(prestDate);
		String bankIdQuery="select IFNULL(BANK_ID,'') from CR_GENERATE_BATCH_DTL where BATCH_NO='"+batchNo+"' limit 1";
		String bankNameQuery="select IFNULL(BANK_NAME,'') from CR_GENERATE_BATCH_DTL where  BATCH_NO='"+batchNo+"' limit 1";
		String bankId=ConnectionDAO.singleReturn(bankIdQuery);
		String bankName=ConnectionDAO.singleReturn(bankNameQuery);
		vo.setBank(bankName);
		vo.setLbxBankID(bankId);
		ArrayList<GenerateBatchVO> result=new ArrayList<GenerateBatchVO>();
		result.add(vo);
		request.setAttribute("result",result);
		batchNo=null;
		prestDate=null;
		vo=null;
		return mapping.findForward("openPopup");
	}
	public ActionForward finalizedBatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in finalizedBatch()of GenerateBatchDispatchAction.");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String prestDate="";
		if(userobj==null)
	    {
			logger.info("here in deleteBatch method of GenerateBatchDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		else
			prestDate=userobj.getBusinessdate();
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		GenerateBatchDAO dao=(GenerateBatchDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBatchDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String batchNo=request.getParameter("batchNo");
		logger.info("in finalizedBatch()  batchNo  :  "+batchNo);		
		boolean finalized=dao.finalizedBatch(batchNo);
		if(finalized)		
			request.setAttribute("finalized",finalized);
		String finalDate=CommonFunction.changeFormat(prestDate);
		request.setAttribute("prestDate",prestDate);
		request.setAttribute("finalDate",finalDate);
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
		request.setAttribute("dealCatList", dealCatList);
		InstrumentCapturingDAO dao1=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
		logger.info("Implementation class: "+dao1.getClass()); 
		ArrayList<InstructionCapMakerVO> clearingTypeList = dao1.getClearingType();
		request.setAttribute("clearingTypeList", clearingTypeList);
		dao=null;
		batchNo=null;
		creditProcessing=null;
		dao1=null;
		return mapping.findForward("success");
	}
	public ActionForward deleteBatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("here in deleteBatch()of GenerateBatchDispatchAction.");
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String prestDate="";
		if(userobj==null)
	    {
			logger.info("here in deleteBatch method of GenerateBatchDispatchAction.");
			return mapping.findForward("sessionOut");
		}
		else
			prestDate=userobj.getBusinessdate();
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		GenerateBatchDAO dao=(GenerateBatchDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBatchDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String batchIdList=request.getParameter("batchIdList");
		logger.info("in deleteBatch()  batchIdList  :  "+batchIdList);		
		boolean delete=dao.deleteBatch(batchIdList);
		if(delete)
		{
			request.setAttribute("delete",delete);		
			String status=(String)request.getParameter("status");
			logger.info("record Status   :   "+status);		
			if(status != null)
				if(CommonFunction.checkNull(status).equalsIgnoreCase("Pending for Finalized"))
					request.setAttribute("pending","pending"); 
				else
					request.setAttribute("finalized","finalized");
			
			logger.info("current page link .......... "+request.getParameter("d-6936073-p"));		
			int currentPageLink = 0;
			if(request.getParameter("d-6936073-p")==null || request.getParameter("d-6936073-p").equalsIgnoreCase("0"))
				currentPageLink=1;
			else
				currentPageLink =Integer.parseInt(request.getParameter("d-6936073-p"));
			logger.info("current page link ................ "+request.getParameter("d-6936073-p"));
			
			GenerateBatchVO vo= new GenerateBatchVO();
			DynaValidatorForm dynaForm = (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo,dynaForm);
			ArrayList<GenerateBatchVO> record=new ArrayList<GenerateBatchVO>();
			String finalDate=vo.getPrestDate();
			finalDate=CommonFunction.changeFormat(finalDate);
			vo.setFinalDate(finalDate);
			record.add(vo);
			request.setAttribute("record",record);
			String batchNo=vo.getBatchNo();
			logger.info("in viewBatch()  batchNo  :  "+batchNo);		
			ArrayList<GenerateBatchVO>viewBatchList=dao.viewBatch(batchNo,currentPageLink);
			request.setAttribute("viewBatchList",viewBatchList);
			CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass()); 
			ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
			request.setAttribute("dealCatList", dealCatList);
			InstrumentCapturingDAO dao1=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
			logger.info("Implementation class: "+dao1.getClass()); 
			ArrayList<InstructionCapMakerVO> clearingTypeList = dao1.getClearingType();
			request.setAttribute("clearingTypeList", clearingTypeList);
			dao=null;
			vo=null;
			creditProcessing=null;
			dao1=null;
			finalDate=null;
		}		
		return mapping.findForward("success");
	}
	
	public ActionForward presentationProcess(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In presentationProcess() method....");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		int companyId=0;
		String userId="";
		String makerDate="";
		if(userobj==null)
		{
			logger.info("in presentationProcess method of  GenerateBatchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			companyId=userobj.getCompanyId();
			userId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		
		DynaValidatorForm dForm = (DynaValidatorForm)form;
		GenerateBatchVO vo=new GenerateBatchVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dForm);
		vo.setCompanyID(companyId);
		vo.setUserId(userId);
		vo.setMakerDate(makerDate);
		GenerateBatchDAO dao=(GenerateBatchDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBatchDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String result = dao.presentationProcess(vo);
		//String result="S";
		logger.info("in presentationProcess()   result   :   "+result);
		if(CommonFunction.checkNull(result).trim().equalsIgnoreCase("S"))
		{
			request.setAttribute("success","sucess");
			request.setAttribute("successBatchNo",vo.getBatchNo().trim());
			request.setAttribute("successBankId",vo.getLbxBankID().trim());
			request.setAttribute("successBranchID",vo.getLbxBranchID().trim());
			request.setAttribute("successAccount",vo.getBankAccount().trim());
		}			
		else
			request.setAttribute("error",result);
		String finalDate=CommonFunction.changeFormat(makerDate);
		request.setAttribute("finalDate",finalDate);
		vo=null;
		dao=null;
		return mapping.findForward("success");
	}
	public ActionForward getBankDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In getBankDetail() method....");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		int companyId=0;
		String userId="";
		String makerDate="";
		if(userobj==null)
		{
			logger.info("in getBankDetail method of  GenerateBatchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			companyId=userobj.getCompanyId();
			userId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		String batchId=request.getParameter("batchId");
		logger.info("batchId   :  "+batchId);
		GenerateBatchDAO dao=(GenerateBatchDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBatchDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList result = dao.getBankDetail(batchId);
		String totalInstrument= dao.getTotalInstrument(batchId);
		request.setAttribute("totalInstrument", totalInstrument);
		String totalInstrumentAmount= dao.getTotalInstrumentAmount(batchId);
		request.setAttribute("totalInstrumentAmount", totalInstrumentAmount);
		request.setAttribute("result",result);
		return mapping.findForward("ajax");
	}
	public ActionForward saveDepositBank(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In saveDepositBank() method....");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		int companyId=0;
		String userId="";
		String makerDate="";
		if(userobj==null)
		{
			logger.info("in saveDepositBank method of  GenerateBatchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			companyId=userobj.getCompanyId();
			userId=userobj.getUserId();
			makerDate=userobj.getBusinessdate();
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";			
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		
		DynaValidatorForm dForm = (DynaValidatorForm)form;
		GenerateBatchVO vo=new GenerateBatchVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, dForm);
		GenerateBatchDAO dao=(GenerateBatchDAO)DaoImplInstanceFactory.getDaoImplInstance(GenerateBatchDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		logger.info("reportType"+vo.getReportType());
		boolean status = dao.saveDepositBank(vo);
		if(status)
			request.setAttribute("save","save");
		else
			request.setAttribute("notSave","notSave");
		ArrayList<GenerateBatchVO> result=new ArrayList<GenerateBatchVO>();
		result.add(vo);
		request.setAttribute("result",result);		
		vo=null;
		dao=null;
		return mapping.findForward("openPopup");
	}	
}