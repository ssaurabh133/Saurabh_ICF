package com.masters.actions;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.UsedVehiclePricingVo;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.validator.DynaValidatorForm;

public class UsedVehiclePricingBehindAction extends Action
{
  private static final Logger logger = Logger.getLogger(UsedVehiclePricingBehindAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ServletContext context = getServlet().getServletContext();
    logger.info("In UsedVehiclePricingBehindAction.........");
    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    Object sessionId = session.getAttribute("sessionID");

    String strFlag = "";
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

    logger.info("strFlag .............. " + strFlag);
    if (!strFlag.equalsIgnoreCase(""))
    {
      if (strFlag.equalsIgnoreCase("sameUserSession"))
      {
        context.removeAttribute("msg");
        context.removeAttribute("msg1");
      }
      else if (strFlag.equalsIgnoreCase("BODCheck"))
      {
        context.setAttribute("msg", "B");
      }
      return mapping.findForward("logout");
    }
    UsedVehiclePricingVo vo = new UsedVehiclePricingVo();
    DynaValidatorForm CommonDynaValidatorForm = (DynaValidatorForm)form;
    BeanUtils.copyProperties(vo, CommonDynaValidatorForm);
    ArrayList list = new ArrayList();

    CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("CREDITPROCESSINGBUSSINESSMASTERREMOTE", request);

    logger.info("In UsedVehiclePricingBehindAction....list" + list.size());
    list = cpm.getUsedVehiclePricingData(vo);

    request.setAttribute("list", list);
    logger.info("In UsedVehiclePricingBehindAction..*******..list" + list.size());

    if (CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")) {
      request.setAttribute("sms", "No");
      logger.info("getused vehicle pricing for used vehicle pricing");
    }

    return mapping.findForward("success");
  }
}