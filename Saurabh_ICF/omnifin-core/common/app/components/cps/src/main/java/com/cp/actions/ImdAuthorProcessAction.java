package com.cp.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cp.dao.ImdDAO;
import com.cp.vo.ImdMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ImdAuthorProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ImdAuthorProcessAction.class.getName());	
	public ActionForward imdAuthorSearchDetail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In Imd author search Detail  ");
		
		HttpSession session = request.getSession();

		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in  imdAuthorSearchDetail method of ImdAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		Object sessionId = session.getAttribute("sessionID");
	
		//for check User session start
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
		ImdMakerVO  vo = new ImdMakerVO();
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-1344872-p")==null || request.getParameter("d-1344872-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-1344872-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-1344872-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		//ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		//request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ReceiptMakerDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		
		vo.setBranchId(branchId);
		vo.setUserId(userId);
        session.setAttribute("branchId", branchId);
        session.setAttribute("userId", userId);
		ArrayList<ImdMakerVO> authordetailList = dao.imdAuthorGrid(vo);
	
		request.setAttribute("authordetailList", authordetailList);
		request.setAttribute("fromAuthor", "fromAuthor");
		return mapping.findForward("imdAuthorSearch");	
	
	}
	public ActionForward getReceipttoApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session=request.getSession();
				
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		if(userobj!=null){
			userId= userobj.getUserId();
		}else{
			logger.info(" in getReceipttoApprove method of ReceiptAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		request.setAttribute("fromAuthor", "fromAuthor");
		
		String sessionId = session.getAttribute("sessionID").toString();
		
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
		ImdMakerVO  vo = new ImdMakerVO();
		String lbxLoanNoHID=request.getParameter("lbxLoanNoHID");
		String instrumentID=request.getParameter("instrumentID");
		String lbxBPType=request.getParameter("lbxBPType");
		vo.setLbxLoanNoHID(request.getParameter("lbxLoanNoHID"));
		vo.setLbxDealNo(request.getParameter("lbxLoanNoHID"));
		vo.setInstrumentID(request.getParameter("instrumentID"));
		vo.setLbxBPType(request.getParameter("lbxBPType"));
		
//		logger.info("In getReceipttoApprove-loanId-"+request.getParameter("loanId"));  
//		logger.info("In getReceipttoApprove instrumentID-"+request.getParameter("instrumentID"));  
	
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		
	//	ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		//session.setAttribute("bussinessPartnerList", bussinessPartnerList);
		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
		boolean flag = LockRecordCheck.lockCheck(userId,functionId,lbxLoanNoHID,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", lbxLoanNoHID);
			request.setAttribute("fromAuthor", "fromAuthor");
			return mapping.findForward("receiptAuthorSearch");
		}
		}
		ArrayList<ImdMakerVO> receiptApproveList= dao.receiptdatatoApprove(vo);
		logger.info("datatoapproveList    Size:---"+receiptApproveList.size());
		session.setAttribute("datatoapproveList", receiptApproveList);
		
		 String totalRecevableAmount=dao.getTotalRec(lbxLoanNoHID,lbxBPType);
		 logger.info("Total Receivable   :  "+totalRecevableAmount);
		 session.setAttribute("totalRecevableAmount",totalRecevableAmount);
		
		session.setAttribute("loanID", request.getParameter("lbxLoanNoHID"));
		session.setAttribute("instrumentID", request.getParameter("instrumentID"));
		session.setAttribute("businessPartnerType", request.getParameter("lbxBPType"));
        
		return mapping.findForward("authorDetail");
	}
	public ActionForward imdAuthorScreen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in receiptAuthorScreen method of ReceiptAuthorProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();	
		
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		ServletContext context = getServlet().getServletContext();
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

		ArrayList<ImdMakerVO> approvalList = null;
		if(session.getAttribute("datatoapproveList")!=null)
		{
			approvalList= (ArrayList<ImdMakerVO>)session.getAttribute("datatoapproveList");
		}
		if(approvalList.size()>0)
		{
			request.setAttribute("loanRecStatus", CommonFunction.checkNull(approvalList.get(0).getLoanRecStatus()).trim());
		}
		
		
		return mapping.findForward("authorScreen");
	}
	public ActionForward viewAllocatedReceivable(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In viewAllocatedReceivable  ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		  String userId="";
			if(userobj!=null){
				userId= userobj.getUserId();
			}
			else{
				logger.info(" in viewAllocatedReceivable method of ReceiptAuthorProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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

	    ImdMakerVO receiptVO = new ImdMakerVO();		
	    ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}			
		logger.info("In onAllocatedReceivable---loanId-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		logger.info("In onAllocatedReceivable---bpType-"+bpType); 
		int instrumentId=0;
		if(!CommonFunction.checkNull(request.getParameter("instrumentID")).equalsIgnoreCase("")){
		instrumentId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentID")));
	     }	
		ArrayList<ImdMakerVO> allocatedReceivableList= dao.onAllocatedReceivable(receiptVO,loanId, bpType,instrumentId);
		request.setAttribute("allocatedReceivableList", allocatedReceivableList);
		logger.info("allocatedReceivableList"+allocatedReceivableList.size());
		return mapping.findForward("allocatedReceivable");
		
		
	}
	public ActionForward onSaveImdAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		    logger.info("in onSaveAuthor..........");
		    
		    HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			logger.info("in onSaveAuthor userobject.........."+userobj);
		String userId="";
		String businessDate="";
		if(userobj== null){
				logger.info(" in onSaveReceiptAuthor method of ReceiptAuthorProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");		
			
		}
		else
		{
			userId=userobj.getUserId();
			businessDate=userobj.getBusinessdate();			
		}
			Object sessionId = session.getAttribute("sessionID");
			
			
			String strFlag="";	
			
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}

			
			logger.info("strFlag .............. "+strFlag);
			ServletContext context = getServlet().getServletContext();
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
			
			DynaValidatorForm ImdDynaValidatorForm = (DynaValidatorForm)form;
			ImdMakerVO receiptVO = new ImdMakerVO();
		
			
			org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO, ImdDynaValidatorForm);
			receiptVO.setUserId(userId);
			receiptVO.setBusinessDate(businessDate);
			
			logger.info("In onSaveAuthor,,,,,"+receiptVO.getInstrumentID());
			
			ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
			 boolean result=false;
			 String msg="";
			 String messval="";
			 String resultproc="";
			 //result=dao.onSaveofReceiptAuthor(receiptVO);
			 resultproc=dao.onSaveofImdAuthor(receiptVO);
			 logger.info("In onSaveAuthor-------resultproc-->"+resultproc);
		//	 if(result){
			 if(resultproc.equalsIgnoreCase("NONE")){
	
				 msg="S";
				if(receiptVO.getDecision().equalsIgnoreCase("A")){
					
					    String recNoQuery=" SELECT RECIPT_NO FROM CR_INSTRUMENT_DTL WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()))+"'";
					    logger.info("recNoQuery: "+recNoQuery);
					    String receiptNo=ConnectionDAO.singleReturn(recNoQuery);
					    recNoQuery=null;
					 if((CommonFunction.checkNull(receiptNo)).trim().equalsIgnoreCase(""))
						{
						    receiptNo=null;
							String branchCodeQuery="SELECT BRANCH_SHORT_CODE FROM COM_BRANCH_M WHERE BRANCH_ID = (SELECT DEFAULT_BRANCH FROM CR_INSTRUMENT_DTL WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()))+"')";
							logger.info("branchCodeQuery: "+branchCodeQuery);
							String branchCode=ConnectionDAO.singleReturn(branchCodeQuery);
							branchCodeQuery=null;
							String generatedReceiptNo=receiptVO.getInstrumentID()+"/"+branchCode;
							String updateReceiptNoQuery="UPDATE CR_INSTRUMENT_DTL SET RECIPT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(generatedReceiptNo))+"'  WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()))+"'";
							logger.info("updateReceiptNoQuery: "+updateReceiptNoQuery);
							ArrayList list=new ArrayList();
							list.add(updateReceiptNoQuery);
							ConnectionDAO.sqlInsUpdDelete(list);
							generatedReceiptNo=null;
							branchCode=null;
							updateReceiptNoQuery=null;
							list.clear();
							list=null;
					   }
				  request.setAttribute("messval", "A");
				}
				else if(receiptVO.getDecision().equalsIgnoreCase("X"))
				 request.setAttribute("messval", "X");
				else if(receiptVO.getDecision().equalsIgnoreCase("P"))
				 request.setAttribute("messval", "P");
				 logger.info("00msg"+msg);
				 session.removeAttribute("pParentGroup");
					session.removeAttribute("loanID");
					session.removeAttribute("instrumentID");
					session.removeAttribute("businessPartnerType");
			 }
			 else{
				 request.setAttribute("procval", resultproc);
				 msg="E";
				 logger.info("msg1"+msg);
			 }
			 
		request.setAttribute("msg", msg);
		logger.info("In onSaveReceiptAuthor-------loanID-->"+ session.getAttribute("loanID"));
		return mapping.findForward("authorFlag");
	}           

}
