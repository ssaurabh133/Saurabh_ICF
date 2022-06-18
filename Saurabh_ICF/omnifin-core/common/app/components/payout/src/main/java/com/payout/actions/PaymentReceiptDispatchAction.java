package com.payout.actions;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.PayoutCilent.PayoutBusinessMasterSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.payout.vo.PaymentReceiptVO;

public class PaymentReceiptDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(PaymentReceiptDispatchAction.class.getName());
	public ActionForward openAddPaymentReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In PaymentReceiptDispatchAction........openAddPaymentReceipt()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here OpenAddActivity method of PaymentReceiptDispatchAction action the session is out----------------");
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
       
		return mapping.findForward("openAdd");
	}
		public ActionForward savePaymentReceiptMaker(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction........savePaymentReceiptMaker()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here savePaymentReceiptMaker method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.savePaymentReceiptMaker(paymentReceiptVO);
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			//request.setAttribute("status", list.get(0).getRecStatus());
			
			String sms="";
			if(!resultStr.equalsIgnoreCase("notSaved")){
				sms="S";
				request.setAttribute("sms",sms);
				paymentReceiptVO.setPaymentId(resultStr);
				list.add(paymentReceiptVO);
				request.setAttribute("list",list);
				request.setAttribute("editVal","editVal");
				
			}else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			return mapping.findForward("openAdd");
			}
		public ActionForward updatePaymentReceiptMaker(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction updatePaymentReceiptMaker()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here updatePaymentReceiptMaker method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
		
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.updatePaymentReceiptMaker(paymentReceiptVO,"P");
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			//request.setAttribute("status", list.get(0).getRecStatus());
			
			String sms="";
			if(resultStr.equalsIgnoreCase("Saved")){
				sms="S";
				request.setAttribute("sms",sms);
				list.add(paymentReceiptVO);
				request.setAttribute("list",list);
				request.setAttribute("editVal","editVal");
				
			}else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			return mapping.findForward("openAdd");
			}
		
		public ActionForward openEditPaymentReceipt(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction openEditPaymentReceipt()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openEditPaymentReceipt method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			logger.info("Payment id Code:-------------"+request.getParameter("paymentId"));
			paymentReceiptVO.setPaymentId(CommonFunction.checkNull(request.getParameter("paymentId")));
		
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	       
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			list= payMaster.openEditPaymentReceiptMaker(paymentReceiptVO);
					
			 request.setAttribute("list",list);
			 request.setAttribute("editVal","editVal");
			return mapping.findForward("openAdd");
		}	
		public ActionForward forwardPaymentReceiptMaker(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction forwordPaymentReceiptMaker()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here forwordPaymentReceiptMaker method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.forwardPaymentReceiptMaker(paymentReceiptVO);
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			//request.setAttribute("status", list.get(0).getRecStatus());
			
			String sms="";
			if(resultStr.equalsIgnoreCase("Saved")){
				sms="F";
				request.setAttribute("sms",sms);
				list.add(paymentReceiptVO);
				request.setAttribute("list",list);
			}else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			return mapping.findForward("openAdd");
		}
		
		public ActionForward allocatePayableReceivableBehind(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction allocatePayableReceivableBehind()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here allocatePayableReceivableBehind method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			paymentReceiptVO.setLbxBpId(CommonFunction.checkNull(request.getParameter("bpId")));
			paymentReceiptVO.setPaymentId(CommonFunction.checkNull(request.getParameter("paymentId")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String authorFlag=CommonFunction.checkNull(request.getParameter("authorFlag"));
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			list=payMaster.allocatePaymentReceiptBehindList(paymentReceiptVO);
			String tdsRate=payMaster.getTdsRate();
			//+"&allocateAmount="+allocateAmount+"&tds="+tds+"&totalAllocatedAmount="+tds
			String allocateAmount=CommonFunction.checkNull(request.getParameter("allocateAmount"));
			String tds=CommonFunction.checkNull(request.getParameter("tds"));
			String totalAllocatedAmount=CommonFunction.checkNull(request.getParameter("totalAllocatedAmount"));
			logger.info("kjsdhkjsdhkjsdh");
			logger.info("Allocate:--"+allocateAmount+" tds:-"+tds+" Total:-"+totalAllocatedAmount);
			if(!allocateAmount.equalsIgnoreCase("")&& !tds.equalsIgnoreCase("") && !totalAllocatedAmount.equalsIgnoreCase("")){
			logger.info("Allocate:--"+allocateAmount+" tds:-"+tds+" Total:-"+totalAllocatedAmount);
				request.setAttribute("allocateAmount", allocateAmount);
				request.setAttribute("tds", tds);
				request.setAttribute("totalAllocatedAmount", totalAllocatedAmount);
			}
			request.setAttribute("tdsRate", tdsRate);
			request.setAttribute("allocateList", list);
			request.setAttribute("paymentAmount", CommonFunction.checkNull(request.getParameter("paymentAmount")));
			request.setAttribute("paymentId", CommonFunction.checkNull(request.getParameter("paymentId")));
			if(authorFlag.equalsIgnoreCase("A")){
				request.setAttribute("authorFlag","A");	
			}
			
			
			return mapping.findForward("allocatePayRec");
		}
		public ActionForward saveAllocatePaymentReceipt(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction allocatePayableReceivableBehind()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here allocatePayableReceivableBehind method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			paymentReceiptVO.setPaymentId(CommonFunction.checkNull(request.getParameter("paymentId")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String chkValue=CommonFunction.checkNull(request.getParameter("chkValue"));
			String chk[]=null;
			ArrayList<String> txnCaseId=new ArrayList<String>();
			ArrayList<String> allocateAmount=new ArrayList<String>();
			if(!chkValue.equalsIgnoreCase("")){
				chk=chkValue.split(",");
			}
			int length =chk.length;
			for(int i=0;i<length;i++){
				txnCaseId.add(CommonFunction.checkNull(request.getParameter("txnCaseId"+chk[i])));
				allocateAmount.add(CommonFunction.checkNull(request.getParameter("allocateAmount"+chk[i])));
			}
			String resultStr=payMaster.saveAllocatePaymentReceipt(paymentReceiptVO, txnCaseId, allocateAmount);
			String sms="";
			if(resultStr.equalsIgnoreCase("Saved")){
				sms="S";
				request.setAttribute("sms",sms);
			}else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			list=payMaster.allocatePaymentReceiptBehindList(paymentReceiptVO);
			request.setAttribute("allocateList", list);
			request.setAttribute("paymentAmount", CommonFunction.checkNull(request.getParameter("paymentAmount")));
			request.setAttribute("paymentId", CommonFunction.checkNull(request.getParameter("paymentId")));
			
			return mapping.findForward("allocatePayRec");
		}
		
		public ActionForward openPaymentReceiptAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction openPaymentReceiptAuthor()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openPaymentReceiptAuthor method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			session.removeAttribute("paymentId");
			session.removeAttribute("authorList");
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			logger.info("Payment id Code:-------------"+request.getParameter("paymentId"));
			paymentReceiptVO.setPaymentId(CommonFunction.checkNull(request.getParameter("paymentId")));
		
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	       
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			list= payMaster.openEditPaymentReceiptMaker(paymentReceiptVO);
			 session.setAttribute("authorList", list);
			 session.setAttribute("author", "author");
			 session.setAttribute("paymentId", list.get(0).getPaymentId());
			 //request.setAttribute("list",list);
			 //request.setAttribute("editVal","editVal");
			return mapping.findForward("openAuthor");
		}
		public ActionForward openPaymentReceiptAuthorTab(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction openPaymentReceiptAuthorTab()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openPaymentReceiptAuthorTab method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			session.removeAttribute("paymentId");
			session.removeAttribute("authorList");
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			logger.info("Payment id Code:-------------"+request.getParameter("paymentId"));
			paymentReceiptVO.setPaymentId(CommonFunction.checkNull(request.getParameter("paymentId")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
			
			ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			list= payMaster.openEditPaymentReceiptMaker(paymentReceiptVO);
			 session.setAttribute("authorList", list);
			 session.setAttribute("author", "author");
			 session.setAttribute("paymentId", list.get(0).getPaymentId());

			 //request.setAttribute("list",list);
			 //request.setAttribute("editVal","editVal");
			return mapping.findForward("openAdd");
		}
		public ActionForward openPaymentReceiptAuthorMain(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction openPaymentReceiptAuthorMain()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openPaymentReceiptAuthorMain method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			logger.info("Payment id Code:-------------"+request.getParameter("paymentId"));
			paymentReceiptVO.setPaymentId(CommonFunction.checkNull(request.getParameter("paymentId")));

			 session.removeAttribute("authorList");
			 session.removeAttribute("author");
			 session.removeAttribute("paymentId");
			 session.setAttribute("paymentId", request.getParameter("paymentId"));
			// request.setAttribute("list",list);
			// request.setAttribute("editVal","editVal");
			return mapping.findForward("openAuthorMain");
		}
		
	public ActionForward savePaymentReceiptAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In PaymentReceiptDispatchAction........savePaymentReceiptMaker()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here savePaymentReceiptMaker method of PaymentReceiptDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			 session.removeAttribute("authorList");
			 session.removeAttribute("author");
			 session.removeAttribute("paymentId");
			PaymentReceiptVO paymentReceiptVO=new PaymentReceiptVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(paymentReceiptVO, CommonPayDynaForm);
			paymentReceiptVO.setMakerId(userobj.getUserId());
			paymentReceiptVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.savePaymentReceiptAuthor(paymentReceiptVO);
			//ArrayList<PaymentReceiptVO> list=new ArrayList<PaymentReceiptVO>();
			//request.setAttribute("status", list.get(0).getRecStatus());
			
			
			if(resultStr.equalsIgnoreCase("S")){
				request.setAttribute("message","S");
			}else{
				
				request.setAttribute("message","There is some error .Please contect Administrator!");
			}
			return mapping.findForward("openAuthorMain");
			}
}
