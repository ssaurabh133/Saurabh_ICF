package com.masters.actions;

import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.VehicleDAO;
import com.masters.vo.VehicleApprovalGridVo;
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

public class VehicleApprovalGridBehindAction extends Action
{
  private static final Logger logger = Logger.getLogger(VehicleApprovalGridBehindAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ServletContext context = getServlet().getServletContext();
    logger.info("In VehicleApprovalGridBehindAction.........");
    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    Object sessionId = session.getAttribute("sessionID");

    String strFlag = "";
    if (sessionId != null) {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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
    logger.info("current page link .......... " + request.getParameter("d-49520-p"));
    int currentPageLink = 0;
    if ((request.getParameter("d-49520-p") == null) || (request.getParameter("d-49520-p").equalsIgnoreCase("0")))
      currentPageLink = 1;
    else
      currentPageLink = Integer.parseInt(request.getParameter("d-49520-p"));
    logger.info("current page link ................ " + request.getParameter("d-49520-p"));

    VehicleApprovalGridVo vo = new VehicleApprovalGridVo();
    vo.setCurrentPageLink(currentPageLink);
    DynaValidatorForm CommonDynaValidatorForm = (DynaValidatorForm)form;
    BeanUtils.copyProperties(vo, CommonDynaValidatorForm);
    ArrayList list = new ArrayList();
    VehicleDAO dao = (VehicleDAO)DaoImplInstanceFactory.getDaoImplInstance("VD");
    logger.info("In VehicleApprovalGridBehindAction....list" + list.size());
    list = dao.getVehicleApprovalSearchGrid(vo);

    request.setAttribute("list", list);

    return mapping.findForward("success");
  }
}