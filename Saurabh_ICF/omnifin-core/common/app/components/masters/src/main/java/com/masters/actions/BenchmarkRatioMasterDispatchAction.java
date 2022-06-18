package com.masters.actions;
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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.BenchmarkRatioVo;
public class BenchmarkRatioMasterDispatchAction extends DispatchAction{

	private static final Logger logger = Logger.getLogger(BenchmarkRatioMasterDispatchAction.class.getName());
	public ActionForward addBenchMarkRatio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
				ServletContext context = getServlet().getServletContext();
				logger.info("in BenchmarkRatioMasterDispatchAction  ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
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
				request.setAttribute("save","save");
				return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveBenchMarkDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		HttpSession session=request.getSession(false);
		logger.info("in saveBenchMarkDetails  ");
		UserObject userobj=new UserObject();
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String status="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
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
		
		DynaValidatorForm BenchmarkRatioMasterDynaValidatorForm= (DynaValidatorForm)form;
		BenchmarkRatioVo vo = new BenchmarkRatioVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BenchmarkRatioMasterDynaValidatorForm);
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		String sms="";	
		
		status=bp.saveBenchmarkRatioMaster(vo);
		ArrayList list = new ArrayList();
		list.add(vo);
		request.setAttribute("List",list);
		logger.info("insert status in action : " + status);
		request.setAttribute("save", "save");
		if((status.equalsIgnoreCase("datasaved"))){
			sms="S";
			request.setAttribute("sms",sms);
		}else if((status.equalsIgnoreCase("dataExist"))){
			sms="DE";
			request.setAttribute("sms",sms);
						
		}else{
			sms="E";
			request.setAttribute("sms",sms);
						
		}

		return mapping.getInputForward();
	


	}
	
	
	public ActionForward openEditBenchMarkRatio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
			BenchmarkRatioVo vo=new BenchmarkRatioVo(); 
			ServletContext context = getServlet().getServletContext();
				logger.info("In openEditBenchMarkRatio ");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				String benchmarkRatioSeq=request.getParameter("benchmarkRatioSeq");
				request.setAttribute("benchmarkRatioSeq",benchmarkRatioSeq);
				logger.info("In benchmarkRatioSeq  "+request.getParameter("benchmarkRatioSeq"));
				String ratioId=request.getParameter("benchmarkRatioSeq");
				logger.info("lbxRatio BenchmarkRatioMasterDispatchAction::::"+ratioId);
				vo.setRatioCodeModify(ratioId);
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		        ArrayList<BenchmarkRatioVo> list = bp.searchBenchMarkRatioEdit(vo);
				logger.info("In openEditBenchMarkRatio BenchmarkRatioVo list size = "+list.size());
				request.setAttribute("list", list);
				String recStatus="";
				if(list.size()>0)
				{
					BenchmarkRatioVo vo1 = (BenchmarkRatioVo)list.get(0);
					recStatus=vo1.getRecStatus();
				}
				logger.info("In recStatus "+recStatus);
				
				logger.info("In openEditUser userMasterVo list"+list.size());
				request.setAttribute("list", list);
				request.setAttribute("status",recStatus);
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("editBenchMark");	
			}
	
	public ActionForward updateBenchMarkRatio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In updateBenchMarkRatio.......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		
		
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
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
		String benchmarkRatioSeq=request.getParameter("benchmarkRatioSeq");
		BenchmarkRatioVo vo=new BenchmarkRatioVo(); 
		DynaValidatorForm BenchmarkRatioMasterDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BenchmarkRatioMasterDynaValidatorForm);	
		vo.setBenchmarkRatioSeq(benchmarkRatioSeq);	

		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);	
        String resultStatus=bp.updateBenchMarkRatioData(vo);
        String sms="";
        if(resultStatus.equalsIgnoreCase("saved")){
        	sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("editValUpdate", "editValUpdate");
		}
		else if(resultStatus.equalsIgnoreCase("notsaved")){
			sms="E";
			request.setAttribute("sms",sms);
			ArrayList<BenchmarkRatioVo> list =new ArrayList<BenchmarkRatioVo>();
			list.add(vo);
			logger.info("In updateSubDealerDetails list"+ list.size());
			
			request.setAttribute("editValUpdate", "editValUpdate");
			request.setAttribute("list", list);
			request.setAttribute("status", vo.getRecStatus());
		}else if(resultStatus.equalsIgnoreCase("dataExist")){
			sms="UPDE";
			request.setAttribute("sms",sms);
			
			ArrayList<BenchmarkRatioVo> list =new ArrayList<BenchmarkRatioVo>();
			list.add(vo);
			logger.info("In updateBenchMarkRatio list"+ list.size());
			logger.info("list valusesssss"+list);
			logger.info("saurabhsingh"+benchmarkRatioSeq);
			request.setAttribute("benchmarkRatioSeq",benchmarkRatioSeq);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", vo.getRecStatus());
		    return mapping.findForward("editBenchMark");
		}
             
       // return mapping.getInputForward();
        logger.info("update status : " + sms);
        return mapping.findForward("updateSearch");
      
		
	}

	

	
}
