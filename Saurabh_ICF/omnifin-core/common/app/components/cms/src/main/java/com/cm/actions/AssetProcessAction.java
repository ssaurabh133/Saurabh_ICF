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
import com.cm.dao.assetInsuranceDAO;
import com.cm.vo.AssetForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class AssetProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AssetProcessAction.class.getName());	
	public ActionForward showAssetData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" In showAssetData ");
		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in execute  method of AssetProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
	
		boolean flag=false;
	
	
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
		session.removeAttribute("assetInsuranceId");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("insuranceDoneByList");
		session.removeAttribute("showInsuranceRelWithNominee");
		AssetForCMVO assetMakervo = new AssetForCMVO(); 
		//change by sachin
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
        String entityType = CommonFunction.checkNull(request.getParameter("entityType"));
        String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		assetMakervo.setLoanId(loanId);
		assetMakervo.setEntityType(entityType);
		request.setAttribute("assetInsuranceId", assetInsuranceId);
		request.setAttribute("loanId", loanId);
		//logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
		String functionId="";


		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", loanId);
			//request.setAttribute("userId", userId);
			return mapping.findForward("success");
		}
		}
		ArrayList<AssetForCMVO> assetMakerList= dao.searchAssetData(assetMakervo);
		logger.info("assetMakerList ---"+assetMakerList);	
		//String sum = dao.getSumOfPremiumAmount(assetMakervo);
		logger.info("assetMakerList    Size:---"+assetMakerList.size());
		request.setAttribute("assetMakerList", assetMakerList);
		//request.setAttribute("sum",sum);
		request.setAttribute("DBCheck","DBCheck");
		ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
		request.setAttribute("insuranceDoneByList",insuranceDoneByList);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
		request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
		ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
		request.setAttribute("YearNo", yearNo);
		ArrayList<AssetForCMVO> assetInsuranceViewerGrid=dao.getAssetInsuranceViewerMaker(assetMakervo);
		request.setAttribute("assetInsuranceViewerGrid", assetInsuranceViewerGrid);
		return mapping.findForward("assetData");
	}

	public ActionForward onAssetInsuranceViewer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" In onAssetInsuranceViewer ");		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{			
			return mapping.findForward("sessionOut");
		}	
		boolean flag=false;	
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
		
		AssetForCMVO assetMakervo = new AssetForCMVO();		
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
        logger.info("loan Id:---"+loanId);
        String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		assetMakervo.setLoanId(loanId);
		request.setAttribute("assetInsuranceId", assetInsuranceId);
		request.setAttribute("loanId", loanId);
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}		
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", loanId);		
			return mapping.findForward("success");
		}
		}
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
		request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
		ArrayList<AssetForCMVO> assetInsuranceViewer=dao.getAssetInsuranceViewer(assetMakervo);		
		request.setAttribute("assetInsuranceViewer", assetInsuranceViewer);			
		return mapping.findForward("assetViewer");
	}
	public ActionForward onAssetInsuranceViewerMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" In onAssetInsuranceViewerMaker ");		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{			
			return mapping.findForward("sessionOut");
		}	
		boolean flag=false;	
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
		
		AssetForCMVO assetMakervo = new AssetForCMVO();		
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
        logger.info("loan Id:---"+loanId);
        String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		assetMakervo.setLoanId(loanId);
		request.setAttribute("assetInsuranceId", assetInsuranceId);
		//request.setAttribute("loanId", loanId);
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}		
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", loanId);		
			return mapping.findForward("success");
		}
		}
		ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
		request.setAttribute("insuranceDoneByList",insuranceDoneByList);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
		request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
		ArrayList<AssetForCMVO> assetInsuranceViewerGrid=dao.getAssetInsuranceViewerMaker(assetMakervo);
		ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
		request.setAttribute("YearNo", yearNo);
		request.setAttribute("assetInsuranceViewerGrid", assetInsuranceViewerGrid);
		
		if(assetInsuranceViewerGrid.size()==0)
		{
			String loanNoQuery = "select loan_no from cr_loan_dtl where loan_id="+loanId+"";
			String customerNameQuery = "SELECT DISTINCT CUSTOMER_NAME FROM CR_LOAN_DTL C  JOIN GCD_CUSTOMER_M G ON C.LOAN_CUSTOMER_ID=G.CUSTOMER_ID WHERE C.LOAN_ID="+loanId+"";
			String loanNo=ConnectionDAO.singleReturn(loanNoQuery);
			String cusName=ConnectionDAO.singleReturn(customerNameQuery);
			session.setAttribute("loanNo", loanNo);
			session.setAttribute("cusName", cusName);
			session.setAttribute("loanId", loanId);
			loanNoQuery=null;
			customerNameQuery=null;
			
		}
		else
		{
			request.setAttribute("assetLoanList", assetInsuranceViewerGrid);
			session.removeAttribute("loanNo");
			session.removeAttribute("cusName");
			session.removeAttribute("loanId");
		}
		return mapping.findForward("assetViewerMaker");
	}
	
	public ActionForward onAssetInsurancePolicyViewer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" In onAssetInsurancePolicyViewer ");		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{			
			return mapping.findForward("sessionOut");
		}	
		boolean flag=false;	
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
		
		AssetForCMVO assetMakervo = new AssetForCMVO();		
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
        logger.info("loan Id:---"+loanId);
        String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
        String entityType = CommonFunction.checkNull(request.getParameter("entityType"));
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		assetMakervo.setLoanId(loanId);
		assetMakervo.setEntityType(entityType);
		request.setAttribute("assetInsuranceId", assetInsuranceId);
		request.setAttribute("loanId", loanId);
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}		
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", loanId);		
			return mapping.findForward("success");
		}
		}
		ArrayList<AssetForCMVO> onAssetInsurancePolicyViewer=dao.onInsurancePolicyViewer(assetMakervo);		
		request.setAttribute("onAssetInsurancePolicyViewer", onAssetInsurancePolicyViewer);			
		return mapping.findForward("assetPolicy");
	}
	public ActionForward getVehicleDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" In getVehicleDetails ");		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{			
			return mapping.findForward("sessionOut");
		}	
		boolean flag=false;	
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
		
		AssetForCMVO assetMakervo = new AssetForCMVO();		
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        String lbxEntity = CommonFunction.checkNull(request.getParameter("lbxEntity"));
        logger.info("lbxEntity:---"+lbxEntity);      
		String functionId="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}		
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,lbxEntity,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", lbxEntity);		
			return mapping.findForward("success");
		}
		}
		ArrayList<AssetForCMVO> onAssetGetVehicleDetails=dao.assetVehicleDetails(assetMakervo,lbxEntity);
		request.setAttribute("onAssetGetVehicleDetails", onAssetGetVehicleDetails);			
		return mapping.findForward("assetVehicleDetails");
	}
	public ActionForward showAssetDataInGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" In showAssetDataInGrid ");		
		HttpSession session =  request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in execute  method of AssetProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}	
		boolean flag=false;	
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
		AssetForCMVO assetMakervo = new AssetForCMVO(); 
		assetInsuranceDAO dao=(assetInsuranceDAO)DaoImplInstanceFactory.getDaoImplInstance(assetInsuranceDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass());
        String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
        String entityType = CommonFunction.checkNull(request.getParameter("entityType"));
        String assetInsuranceId= CommonFunction.checkNull(request.getParameter("assetInsuranceId"));
		assetMakervo.setAssetInsuranceId(assetInsuranceId);
		assetMakervo.setLoanId(loanId);
		assetMakervo.setEntityType(entityType);
		request.setAttribute("assetInsuranceId", assetInsuranceId);
		request.setAttribute("loanId", loanId);	
		String functionId="";
		
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", loanId);			
			return mapping.findForward("success");
		}
		}
		ArrayList<AssetForCMVO> assetMakerList= dao.showAssetDataInGrid(assetMakervo);		
		request.setAttribute("assetMakerList", assetMakerList);	
		request.setAttribute("assetMakerListAuthorFlag", "Y");	
		request.setAttribute("DBCheck","DBCheck");
		ArrayList<Object> insuranceDoneByList=dao.getInsuranceDoneByList();
		request.setAttribute("insuranceDoneByList",insuranceDoneByList);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
		request.setAttribute("showInsuranceRelWithNominee",showInsuranceRelWithNominee);
		ArrayList<AssetForCMVO> assetInsuranceViewerGrid=dao.getAssetInsuranceViewerMaker(assetMakervo);
		request.setAttribute("assetInsuranceViewerGrid", assetInsuranceViewerGrid);
		ArrayList<AssetForCMVO> yearNo=dao.getMaxYearNo(loanId);
		request.setAttribute("YearNo", yearNo);
		return mapping.findForward("assetData");
	}
	
}