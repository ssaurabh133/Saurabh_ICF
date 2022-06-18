package com.cp.actions;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.ScoringVO;
import com.login.roleManager.UserObject;

public class ScoringBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(ScoringBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		// TODO Auto-generated method stub
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		if(userobj!=null)
			{
			userId=userobj.getUserId();						
			}else{
				logger.info("here in execute method of ScoringBehindAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		ScoringVO scoringVo = new ScoringVO();	
		DynaValidatorForm CommonDealDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(scoringVo, CommonDealDynaValidatorForm);
		
		if(CommonFunction.checkNull(scoringVo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			scoringVo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}

		//scoringVo.setUserId(userobj.getUserName());
		logger.info("In ScoringBehindAction ");
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 			// changed by asesh
		//CreditProcessingDAO dao =new CreditProcessingDAOImpl();
			
		ArrayList<ScoringVO>dealdetails= dao.scoringSearchGrid(scoringVo);

	    logger.info("In searchDealCapturing....list: "+dealdetails.size());
		
	    request.setAttribute("list", dealdetails);
		return mapping.findForward("success");
}
}                                                                         