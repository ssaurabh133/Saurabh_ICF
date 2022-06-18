
package com.cm.actions;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.vo.GenerateBatchVO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class GenerateBatchBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(GenerateBatchBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info(" in execute() of  GenerateBatchBehindAction ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String prestDate="";
		if(userobj==null){
			logger.info(" in execute() of  GenerateBatchBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			prestDate=userobj.getBusinessdate();
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
		String finalDate=CommonFunction.changeFormat(prestDate);
		ArrayList<Object> record = new ArrayList();
		GenerateBatchVO vo = new GenerateBatchVO();
		vo.setPrestDate(prestDate);
		vo.setFinalDate(finalDate);
		record.add(vo);
		request.setAttribute("record",record);
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+creditProcessing.getClass()); 
		//CreditProcessingDAO creditProcessing = new CreditProcessingDAOImpl();
		ArrayList<Object> dealCatList = creditProcessing.getDealCatList();
		request.setAttribute("dealCatList", dealCatList);
		//InstrumentCapturingDAO dao = new InstrumentCapturingDAOImpl();
		InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		ArrayList<InstructionCapMakerVO> clearingTypeList = dao.getClearingType();
		request.setAttribute("clearingTypeList", clearingTypeList);
		vo=null;
		creditProcessing=null;
		dao=null;
		return mapping.findForward("success");
	}
	

}


