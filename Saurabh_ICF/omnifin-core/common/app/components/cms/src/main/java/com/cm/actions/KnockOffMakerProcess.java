package com.cm.actions;

import java.util.ArrayList;
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
import com.cm.dao.KnockOffDAO;
import com.cm.vo.KnockOffMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class KnockOffMakerProcess extends DispatchAction {
	private static final Logger logger = Logger.getLogger(KnockOffMakerProcess.class.getName());

        public ActionForward searchLoanDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception {
        	logger.info("Inside KnockOffMakerProcess........searchLoanDetails");
        	
        	HttpSession session =  request.getSession();
        	
			boolean flag=false;
// bussiness date is added by prashant
			String bDate="";
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj!=null){
				bDate=userobj.getBusinessdate();
			}
			else{
				logger.info("in searchLoanDetails method of  KnockOffMakerProcess action the session is out----------------");
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

		    DynaValidatorForm KnockOffDynaValidatorForm = (DynaValidatorForm)form; 		 
		    KnockOffMakerVO vo = new KnockOffMakerVO();	
		    org.apache.commons.beanutils.BeanUtils.copyProperties(vo, KnockOffDynaValidatorForm);  
			KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
			logger.info("Implementation class: "+service.getClass()); 
		    String knockOffloanId = CommonFunction.checkNull(request.getParameter("loanId"));		
		    String bpId = CommonFunction.checkNull(request.getParameter("bpId"));	
		    //ArrayList<Object> showdetails = service.getBusinessPartnerTypeList();
			logger.info("valueDate :::::::::::::::::::::::::::::: "+vo.getValueDate());
		    String valueDate ="";
		    valueDate = CommonFunction.checkNull(vo.getValueDate()).trim();
		    request.setAttribute("valueDate",valueDate);
		    ArrayList<Object> loanDataListR = service.getKnockOffDetailsDataNew(knockOffloanId, bpId,"R",valueDate);
		    
		    ArrayList<Object> loanDataListP = service.getKnockOffDetailsDataNew(knockOffloanId,bpId,"P",valueDate);
	    
		    request.setAttribute("loanDataListR", loanDataListR);		    

		    request.setAttribute("loanDataListP", loanDataListP);
		    
		    request.setAttribute("loanNumber", vo.getLoanNumber());
		    request.setAttribute("lbxLoanNoHID", vo.getLbxLoanNoHID());
		    request.setAttribute("customerName", vo.getCustomerName());
		    request.setAttribute("businessPartnerType", vo.getBusinessPartnerType());
		    request.setAttribute("hBPType", vo.gethBPType());
		    request.setAttribute("lbxBusinessPartnearHID", vo.getLbxBusinessPartnearHID());
		    request.setAttribute("businessPartnerName", vo.getBusinessPartnerName());
		    request.setAttribute("remarks", vo.getRemarks());
		    //request.setAttribute("showdetails",showdetails);
		    request.setAttribute("knockOffNew", "knockOffNew");	
		    return mapping.findForward("searchKnockOffNew");	
	}
	
	
	
	        public ActionForward saveKnockOffDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
	        	logger.info("Inside KnockOffMakerProcess........saveKnockOffDetails");
	        	
	        	HttpSession session =  request.getSession();
	        	
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				   String userId="";
					String bDate ="";
				if(userobj!=null){
					userId= userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("in saveKnockOffDetails method of  KnockOffMakerProcess action the session is out----------------");
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

			    DynaValidatorForm knockOffDynaValidatorForm = (DynaValidatorForm)form; 		 
			    KnockOffMakerVO knockOffVo = new KnockOffMakerVO();	
			   
		        org.apache.commons.beanutils.BeanUtils.copyProperties(knockOffVo, knockOffDynaValidatorForm);  
				KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
				logger.info("Implementation class: "+service.getClass()); 
			    String knockOffId="";	    
			 
				
				    
				knockOffVo.setUserId(userId);
				knockOffVo.setMakerDate(bDate);
			    request.setAttribute("valueDate", knockOffVo.getValueDate());
			    String ReceivableSum = request.getParameter("RSum");
			    String PayableSum = request.getParameter("PSum");
			    String knockOffR = request.getParameter("ReceiveValues");		   
			    String knockOffP = request.getParameter("PayableValues");
			    String loanId = request.getParameter("loanId");	
			    String Original_AmountR = request.getParameter("OriginalAmountR");
			    String Balanced_AmountR = request.getParameter("BalanceAmountR");
			    String Original_AmountP = request.getParameter("OriginalAmountP");
			    String Balanced_AmountP = request.getParameter("BalanceAmountP");
			    String TxnAdviceIdR = request.getParameter("TXNAdviceIdR");
			    String TxnAdviceIdP = request.getParameter("TXNAdviceIdP");	
			    String AmountInProcessR = request.getParameter("AmountInProcess_R");
			    String AmountInProcessP = request.getParameter("AmountInProcess_P");
			    String knockOffIdR = request.getParameter("knockOffIdR");
			    
			    logger.info("knockOffIdR.........."+knockOffIdR);
			    
			    String knockOffIdP = request.getParameter("knockOffIdP");
			    String recStatus = request.getParameter("recStatus");		    
			    logger.info("Rec Status for knock off maker: "+recStatus);
			    String[] RknockOffAmount=knockOffR.split("@"); 
			    String[] PKnockOffAmount=knockOffP.split("@");	
			    String[] ROriginalAmount = Original_AmountR.split("@");		    
			    String[] RBalancelAmount = Balanced_AmountR.split("@");		   
			    String[] POriginalAmount = Original_AmountP.split("@");		   
			    String[] PBalancelAmount = Balanced_AmountP.split("@");		   
			    String[] txnAdviceIdR = TxnAdviceIdR.split("@");		  
			    String[] txnAdviceIdP = TxnAdviceIdP.split("@");		   
			    String[] ProcessAmountR=AmountInProcessR.split("@");		        
			    String[] ProcessAmountP=AmountInProcessP.split("@");
			    String[] KnockOffId_R = knockOffIdR.split("@");
			    String[] KnockOffId_P = knockOffIdP.split("@");
			    knockOffId =  service.saveKnockOffDetails(knockOffVo,ReceivableSum,PayableSum,RknockOffAmount,PKnockOffAmount,loanId,ROriginalAmount,RBalancelAmount,POriginalAmount,PBalancelAmount,txnAdviceIdR,txnAdviceIdP,ProcessAmountR,ProcessAmountP,recStatus,KnockOffId_R,KnockOffId_P);
			    
			    if(recStatus.equalsIgnoreCase("P"))
			    {
				    ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffData(knockOffId,"P");
				    request.setAttribute("knockOffSearchList", knockOffSearchList);	
				    
					ArrayList<Object> loanDataListR = service.getKnockOffDetailsDataMaker(knockOffId,"R","P");
				    request.setAttribute("loanDataListR", loanDataListR);
				    
				    ArrayList<Object> loanDataListP = service.getKnockOffDetailsDataMaker(knockOffId,"P","P");		
				    request.setAttribute("loanDataListP", loanDataListP);
				    
				    ArrayList<Object> totalR = service.getTotalReceivableR(knockOffId,"P");
				    ArrayList<Object> totalP = service.getTotalReceivableP(knockOffId,"P");
				    request.setAttribute("totalR", totalR);
				    request.setAttribute("totalP", totalP);
				    
				    request.setAttribute("knockOffIdR", knockOffIdR);
				    request.setAttribute("knockOffId", knockOffId);
				    request.setAttribute("knockOffMakerValues", "knockOffMakerValues");	
			    }
			    if(!knockOffId.equalsIgnoreCase(""))
			    {		     	
			    	request.setAttribute("sms","S");							  
			    }
			    else
			    {
			    	request.setAttribute("sms","E");
			    }
			    if(recStatus.equalsIgnoreCase("F"))
			    {
			    	request.setAttribute("sms2","F");
			    	ArrayList<KnockOffMakerVO> knockOffSearchList = service.getKnockOffData(knockOffId,"F");
				    request.setAttribute("knockOffSearchList", knockOffSearchList);	
				    
					ArrayList<Object> loanDataListR = service.getKnockOffDetailsDataMaker(knockOffId,"R","F");
				    request.setAttribute("loanDataListR", loanDataListR);
				    
				    ArrayList<Object> loanDataListP = service.getKnockOffDetailsDataMaker(knockOffId,"P","F");		
				    request.setAttribute("loanDataListP", loanDataListP);
				    
				    ArrayList<Object> totalR = service.getTotalReceivableR(knockOffId,"F");
				    ArrayList<Object> totalP = service.getTotalReceivableP(knockOffId,"F");
				    request.setAttribute("totalR", totalR);
				    request.setAttribute("totalP", totalP);
				    
				    request.setAttribute("knockOffId", knockOffId);
				    request.setAttribute("knockOffMakerValues", "knockOffMakerValues");	
			    }
			    return mapping.findForward("saveKnockOffDetails");
	        }	 
	//Ritu	
	    	public ActionForward deleteKnockOff(ActionMapping mapping, ActionForm form,
	    			HttpServletRequest request, HttpServletResponse response)
	    			throws Exception {

	    		logger.info("In KnockOffMakerProcess deleteKnockOff().... ");
	    		HttpSession session = request.getSession();
	    		UserObject userobj = (UserObject) session.getAttribute("userobject");
	    		String userId="";
	    		String bDate="";
	    		String branchId="";
	    		//String result="success";
	    		if(userobj!=null)
	    		{
	    				userId=userobj.getUserId();
	    				bDate=userobj.getBusinessdate();
	    				branchId=userobj.getBranchId();
	    		}else{
	    			logger.info("here in deleteKnockOff () of KnockOffMakerProcess action the session is out----------------");
	    			return mapping.findForward("sessionOut");
	    		}
	    		String sessionId = session.getAttribute("sessionID").toString();

	    		// String cond = request.getParameter("saveForward");
	    		// logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+cond);

	    		ServletContext context = getServlet().getServletContext();
	    		String strFlag="";	
	    		if(sessionId!=null)
	    		{
	    			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
	    		}
	    		
	    	
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

	    		String knockOffId = "";

	    		knockOffId = request.getParameter("knockOffId");
	    		
	    		logger.info("In   deleteKnockOff ----1-------------->knockOffId " + knockOffId);
	    		//logger.info("In ConsumerDispatchAction  execute id: " + userobj.getBranchId());

	    		//PaymentDAO service = new PaymentDAOImpl();
	    		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
	    		logger.info("Implementation class: "+service.getClass()); 
	    		
	    		boolean status = service.deleteKnockOFF(knockOffId);
	    		String sms="";
	    		if(status){
	            	sms="DS";
	    			request.setAttribute("sms",sms);
	    			 
	    		}
	    		else{
	    			sms="DN";
	    			request.setAttribute("sms",sms);
	    		}	    	
	    		request.setAttribute("knockOffNew","knockOffNew");
	    		return mapping.findForward("searchKnockOffNew");
	    	}
	     }

	
	
