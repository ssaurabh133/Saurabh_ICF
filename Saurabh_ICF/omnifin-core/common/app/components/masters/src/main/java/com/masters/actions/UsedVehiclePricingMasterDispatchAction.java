package com.masters.actions;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.UsedVehiclePricingVo;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

public class UsedVehiclePricingMasterDispatchAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(UsedVehiclePricingMasterDispatchAction.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  String dbType = this.resource.getString("lbl.dbType");

  public ActionForward openAddProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    ServletContext context = getServlet().getServletContext();
    logger.info("in vehicle pricing openAddProduct()");
    HttpSession session = request.getSession();

    UserObject userobj = (UserObject)session.getAttribute("userobject");
    Object sessionId = session.getAttribute("sessionID");

    String strFlag = "";
    String bDate = null;
    String branchId = null;
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }
    if (userobj != null)
    {
      bDate = userobj.getBusinessdate();
      branchId = userobj.getBranchId();
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
    CreditProcessingMasterBussinessSessionBeanRemote bp = (CreditProcessingMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("CREDITPROCESSINGBUSSINESSMASTERREMOTE", request);

    String noOfYear = bp.getNoOfYearAtUsedVehicle();
    int tempNo = 0;
    if (!CommonFunction.checkNull(noOfYear).equalsIgnoreCase(""))
      tempNo = Integer.parseInt(noOfYear);
    bDate = userobj.getBusinessdate();
    String bYear = bDate.substring(6, 10);
    UsedVehiclePricingVo vo = null;
    ArrayList list = new ArrayList();
    for (int i = 0; i < tempNo; i++)
    {
      vo = new UsedVehiclePricingVo();
      vo.setUsedVehicleYear(bYear);
      bYear = Integer.parseInt(bYear) - 1 + "";
      list.add(vo);
    }
    request.setAttribute("usedPricingYearList", list);
    session.setAttribute("vehicleBranchId", branchId);
    vo = null;
    return mapping.findForward("openAdd");
  }

  public ActionForward saveUsedVehiclePricingAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    ServletContext context = getServlet().getServletContext();

    logger.info("In saveUsedVehiclePricingAdd:::::::::::::::::::::::::::::::::");
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
    String makeModelId = request.getParameter("makeModelId");
    logger.info("makeModelId: " + makeModelId);
    CreditProcessingMasterBussinessSessionBeanRemote bp = (CreditProcessingMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("CREDITPROCESSINGBUSSINESSMASTERREMOTE", request);

    String makerId = "";
    String makerDate = "";
    String authorId = "";
    String authorDate = "";
    DynaValidatorForm UsedVehiclePricingAddDyanavalidatiorForm = (DynaValidatorForm)form;
    UsedVehiclePricingVo usedVehiclePricingVo = new UsedVehiclePricingVo();
    if (userobj != null)
    {
      makerId = userobj.getUserId();
      makerDate = userobj.getBusinessdate();
      authorId = makerId;
      authorDate = makerDate;
    }
    usedVehiclePricingVo.setMakerId(makerId);
    usedVehiclePricingVo.setMakerDate(makerDate);
    usedVehiclePricingVo.setAuthorId(authorId);
    usedVehiclePricingVo.setAuthorDate(authorDate);
    logger.info("usedVehiclePricingVo.setMakerId" + makerId);
    BeanUtils.copyProperties(usedVehiclePricingVo, UsedVehiclePricingAddDyanavalidatiorForm);

    boolean status = bp.insertUsedVehiclePricing(usedVehiclePricingVo);
    logger.info("Inside used vehicle pricing Action.....displaying status...." + CommonFunction.checkNull(Boolean.valueOf(status)));
    ArrayList usedPricingYearList = new ArrayList();

    String sms = null;

    if (status)
      sms = "S";
    else
      sms = "E";
    logger.info("result for used vehcle pricing in saveUsedVehiclePricingAdd" + status);
    request.setAttribute("sms", sms);

    usedVehiclePricingVo = null;
    return mapping.findForward("updateSuccess");
  }

  public ActionForward selectUsedVehiclePricing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ServletContext context = getServlet().getServletContext();

    logger.info("In selectUsedVehiclePricing:::::::::::::::::::::::::::::::::");
    HttpSession session = request.getSession();

    UserObject userobj = (UserObject)session.getAttribute("userobject");
    Object sessionId = session.getAttribute("sessionID");
    String bDate = userobj.getBusinessdate();
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
    String makeModelId = request.getParameter("makeModelId");
    logger.info("makeModelId: " + makeModelId);

    CreditProcessingMasterBussinessSessionBeanRemote bp = (CreditProcessingMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("CREDITPROCESSINGBUSSINESSMASTERREMOTE", request);

    DynaValidatorForm UsedVehiclePricingAddDyanavalidatiorForm = (DynaValidatorForm)form;
    UsedVehiclePricingVo usedVehiclePricingVo = new UsedVehiclePricingVo();
    BeanUtils.copyProperties(usedVehiclePricingVo, UsedVehiclePricingAddDyanavalidatiorForm);

    String noOfYear = bp.getNoOfYearAtUsedVehicle();
    int tempNo = 0;
    int j = 0;
    if (!CommonFunction.checkNull(noOfYear).equalsIgnoreCase(""))
      tempNo = Integer.parseInt(noOfYear);
    bDate = userobj.getBusinessdate();
    String bYear = bDate.substring(6, 10);
    int currentYear = Integer.parseInt(bYear);
    ArrayList list = bp.selectUsedVehiclePricing(makeModelId);
    int listSize = list.size();
    ArrayList list1 = new ArrayList();

    for (int i = 0; i < tempNo; i++)
    {
      UsedVehiclePricingVo vo = new UsedVehiclePricingVo();
      if (j < listSize)
      {
        vo = (UsedVehiclePricingVo)list.get(j);
        if (currentYear > Integer.parseInt(vo.getUsedVehicleYear()))
        {
          UsedVehiclePricingVo vo1 = new UsedVehiclePricingVo();
          UsedVehiclePricingVo tempVo = new UsedVehiclePricingVo();
          tempVo = (UsedVehiclePricingVo)list.get(0);
          vo1.setUsedVehiclePrice("");
          vo1.setUsedVehicleYear(bYear);
          vo1.setUsedVehicleMakeSearch(tempVo.getUsedVehicleMakeSearch());
          vo1.setUsedVehicleModelSearch(tempVo.getUsedVehicleModelSearch());
          vo1.setUsedVehicleBranch(tempVo.getUsedVehicleBranch());
          vo1.setUsedVehicleState(tempVo.getUsedVehicleState());
          currentYear--;
          bYear = String.valueOf(currentYear);

          list1.add(vo1);
          vo1 = null;
        }
        if ((currentYear == Integer.parseInt(vo.getUsedVehicleYear())) && (j < listSize))
        {
          list1.add(vo);
          currentYear--;
          j++;
        }
      }
      else
      {
        UsedVehiclePricingVo vo1 = new UsedVehiclePricingVo();
        vo1.setUsedVehiclePrice("");
        bYear = String.valueOf(currentYear);
        vo1.setUsedVehicleYear(bYear);
        currentYear--;
        bYear = String.valueOf(currentYear);
        list1.add(vo1);
        vo1 = null;
      }
      vo = null;
    }
    request.setAttribute("usedPricingYearList", list1);
    request.setAttribute("makeModelId", makeModelId);
    request.setAttribute("search", "search");
    return mapping.findForward("openAdd");
  }

  public ActionForward updateVehiclePricingdata(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    ServletContext context = getServlet().getServletContext();

    logger.info("In updateBranch.......");

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

    String makeModelId = request.getParameter("makeModelId");
    logger.info("makeModelId: " + makeModelId);

    CreditProcessingMasterBussinessSessionBeanRemote bp = (CreditProcessingMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("CREDITPROCESSINGBUSSINESSMASTERREMOTE", request);
    String makerId = "";
    String makerDate = "";
    String authorId = "";
    String authorDate = "";
    DynaValidatorForm UsedVehiclePricingAddDyanavalidatiorForm = (DynaValidatorForm)form;
    UsedVehiclePricingVo usedVehiclePricingVo = new UsedVehiclePricingVo();
    if (userobj != null)
    {
      makerId = userobj.getUserId();
      makerDate = userobj.getBusinessdate();
      authorId = makerId;
      authorDate = makerDate;
    }
    usedVehiclePricingVo.setMakerId(makerId);
    usedVehiclePricingVo.setMakerDate(makerDate);
    usedVehiclePricingVo.setAuthorId(authorId);
    usedVehiclePricingVo.setAuthorDate(authorDate);
    logger.info("usedVehiclePricingVo.setMakerId" + makerId);
    BeanUtils.copyProperties(usedVehiclePricingVo, UsedVehiclePricingAddDyanavalidatiorForm);

    boolean status = bp.updateVehiclePricingdata(usedVehiclePricingVo);
    logger.info("Inside used vehicle pricing Action.....displaying status...." + CommonFunction.checkNull(Boolean.valueOf(status)));
    ArrayList usedPricingYearList = new ArrayList();
    usedPricingYearList.add(usedVehiclePricingVo);
    String message = null;
    if (status)
      message = "U";
    else
      message = "E";
    logger.info("result for used vehcle pricing in updateVehiclePricingdata" + status);
    request.setAttribute("sms", message);
    request.setAttribute("search", usedPricingYearList);
    request.setAttribute("makeModelId", makeModelId);
    return mapping.findForward("updateSuccess");
  }

  public ActionForward searchUsedVehiclePricing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
    ServletContext context = getServlet().getServletContext();
    HttpSession session = request.getSession(false);
    UserObject userobj = new UserObject();
    userobj = (UserObject)session.getAttribute("userobject");
    String userId = "";
    String bDate = "";
    if (userobj != null)
    {
      userId = userobj.getUserId();
      bDate = userobj.getBusinessdate();
    }

    boolean flag = false;

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

    CreditProcessingMasterBussinessSessionBeanRemote bp = (CreditProcessingMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("CREDITPROCESSINGBUSSINESSMASTERREMOTE", request);
    DynaValidatorForm UsedVehiclePricingAddDyanavalidatiorForm = (DynaValidatorForm)form;
    UsedVehiclePricingVo vo = new UsedVehiclePricingVo();
    BeanUtils.copyProperties(vo, UsedVehiclePricingAddDyanavalidatiorForm);
    ArrayList list = new ArrayList();
    list = bp.searchUsedVehiclePricing(vo);
    request.setAttribute("list", list);
    logger.info("In UsedVehiclePricingBehindAction..*******..list" + list.size());
    return mapping.getInputForward();
  }
}