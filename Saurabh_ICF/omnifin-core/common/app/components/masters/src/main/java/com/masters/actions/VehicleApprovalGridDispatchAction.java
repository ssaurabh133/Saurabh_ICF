package com.masters.actions;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.VehicleDAO;
import com.masters.vo.VehicleApprovalGridVo;
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

public class VehicleApprovalGridDispatchAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(VehicleApprovalGridDispatchAction.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  String dbType = this.resource.getString("lbl.dbType");

  public ActionForward openAddVehicleGrid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ServletContext context = getServlet().getServletContext();
    logger.info(" In vehicleApprovalGridDispatchAction()");
    HttpSession session = request.getSession();

    UserObject userobj = (UserObject)session.getAttribute("userobject");
    Object sessionId = session.getAttribute("sessionID");

    String strFlag = "";
    String bDate = null;
    String branchId = null;
    if (sessionId != null) {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }
    if (userobj != null) {
      bDate = userobj.getBusinessdate();
      branchId = userobj.getBranchId();
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

    VehicleDAO dao = (VehicleDAO)DaoImplInstanceFactory.getDaoImplInstance("VD");
    String noOfYear = dao.getNoOfYearAtUsedVehicle();
    int tempNo = 0;
    if (!CommonFunction.checkNull(noOfYear).equalsIgnoreCase(""))
      tempNo = Integer.parseInt(noOfYear);
    bDate = userobj.getBusinessdate();
    String bYear = bDate.substring(6, 10);
    VehicleApprovalGridVo vo = null;
    ArrayList list = new ArrayList();
    for (int i = 0; i < tempNo; i++)
    {
      vo = new VehicleApprovalGridVo();
      vo.setVehicleApprovalYear(bYear);
      bYear = Integer.parseInt(bYear) - 1 + "";
      list.add(vo);
    }
    request.setAttribute("list", list);

    vo = null;

    return mapping.findForward("openAdd");
  }

  public ActionForward saveVehicleApprovalGrid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ServletContext context = getServlet().getServletContext();

    logger.info("In saveVehicleApprovalGrid:::::::::::::::::::::::::::::::::");
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

    VehicleDAO dao = (VehicleDAO)DaoImplInstanceFactory.getDaoImplInstance("VD");
    DynaValidatorForm vehicleApprovalDyanavalidatiorForm = (DynaValidatorForm)form;
    VehicleApprovalGridVo vo = new VehicleApprovalGridVo();
    BeanUtils.copyProperties(vo, vehicleApprovalDyanavalidatiorForm);

    if (userobj != null) {
      vo.setMakerId(userobj.getUserId());
      vo.setMakerDate(userobj.getBusinessdate());
    }
    boolean status = dao.insertVehicleApprovalGrid(vo);
    logger.info("Inside VehicleApprovalGrid Action.....displaying status...." + CommonFunction.checkNull(Boolean.valueOf(status)));
    String sms = null;

    if (status)
      sms = "S";
    else
      sms = "E";
    logger.info("result for VehicleApprovalGrid in saveVehicleApprovalGrid " + status);
    request.setAttribute("sms", sms);
    vo = null;
    return mapping.findForward("saveData");
  }

  public ActionForward updateVehicleApprovalGrid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    ServletContext context = getServlet().getServletContext();

    logger.info("In updateVehicleApprovalGrid:::::::::::::::::::::::::::::::::");
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
    String lbxProductID = request.getParameter("lbxProductID");
    logger.info("lbxProductID: " + lbxProductID);

    VehicleDAO dao = (VehicleDAO)DaoImplInstanceFactory.getDaoImplInstance("VD");
    DynaValidatorForm vehicleApprovalDyanavalidatiorForm = (DynaValidatorForm)form;
    VehicleApprovalGridVo vo = new VehicleApprovalGridVo();
    BeanUtils.copyProperties(vo, vehicleApprovalDyanavalidatiorForm);

    if (userobj != null) {
      vo.setMakerId(userobj.getUserId());
      vo.setMakerDate(userobj.getBusinessdate());
    }
    boolean status = dao.updateVehicleApprovalGrid(vo);
    logger.info("Inside updateVehicleApprovalGrid.....displaying status...." + CommonFunction.checkNull(Boolean.valueOf(status)));
    ArrayList list = new ArrayList();
    list.add(vo);

    String sms = null;

    if (status)
      sms = "M";
    else
      sms = "E";
    logger.info("result for vehicle approval grid in updateVehicleApprovalGrid " + status);
    request.setAttribute("sms", sms);

    request.setAttribute("lbxProductID", lbxProductID);

    vo = null;
    return mapping.findForward("updateData");
  }

  public ActionForward selectVehicleApprovalGrid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ServletContext context = getServlet().getServletContext();

    logger.info("In selectVehicleApprovalGrid:::::::::::::::::::::::::::::::::");
    HttpSession session = request.getSession();
    boolean flag = false;
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

    VehicleDAO dao = (VehicleDAO)DaoImplInstanceFactory.getDaoImplInstance("VD");

    String vehicleApprovalID = request.getParameter("vehicleApprovalId");

    logger.info("vehicleApprovalID: " + vehicleApprovalID);

    String noOfYear = dao.getNoOfYearAtUsedVehicle();
    int tempNo = 0;
    int j = 0;
    if (!CommonFunction.checkNull(noOfYear).equalsIgnoreCase(""))
      tempNo = Integer.parseInt(noOfYear);
    bDate = userobj.getBusinessdate();
    String bYear = bDate.substring(6, 10);
    int currentYear = Integer.parseInt(bYear);

    ArrayList list = dao.selectVehicleApprovalGrid(vehicleApprovalID);
    int listSize = list.size();
    ArrayList list1 = new ArrayList();

    for (int i = 0; i < tempNo; i++)
    {
      VehicleApprovalGridVo vo = new VehicleApprovalGridVo();

      if (j < listSize)
      {
        vo = (VehicleApprovalGridVo)list.get(j);
        if (vo.getVehicleType().equalsIgnoreCase("O"));
        vo.setVehicleApprovalYear(bYear);
        bYear = Integer.parseInt(bYear) - 1 + "";

        if (currentYear > Integer.parseInt(vo.getVehicleApprovalYear()))
        {
          VehicleApprovalGridVo vo1 = new VehicleApprovalGridVo();
          VehicleApprovalGridVo tempVo = new VehicleApprovalGridVo();
          tempVo = (VehicleApprovalGridVo)list.get(0);

          vo1.setVehicleApprovalYear(bYear);
          vo1.setLbxProductID(tempVo.getLbxProductID());
          vo1.setProduct(tempVo.getProduct());
          vo1.setLbxScheme(tempVo.getLbxScheme());
          vo1.setScheme(tempVo.getScheme());
          vo1.setManufactId(tempVo.getManufactId());
          vo1.setManufacturer(tempVo.getManufacturer());
          vo1.setModelDescId(tempVo.getModelDescId());
          vo1.setModelDesc(tempVo.getModel());
          vo1.setGridBranchAmt(tempVo.getGridBranchAmt());
          vo1.setGridNationalAmt(tempVo.getGridNationalAmt());
          vo1.setGridHoAmt(tempVo.getGridHoAmt());
          vo1.setVehicleApprovalYear(bYear);
          vo1.setVehicleApprovalID(tempVo.getVehicleApprovalID());

          currentYear -= 1;
          bYear = String.valueOf(currentYear);

          list1.add(vo1);
          vo1 = null;
        }
        if ((currentYear == Integer.parseInt(vo.getVehicleApprovalYear())) && (j < listSize))
        {
          list1.add(vo);
          currentYear -= 1;
          j += 1;
        }

        if (vo.getVehicleType().equalsIgnoreCase("N"))
        {
          if (currentYear > Integer.parseInt(vo.getVehicleApprovalYear()))
          {
            VehicleApprovalGridVo vo1 = new VehicleApprovalGridVo();
            VehicleApprovalGridVo tempVo = new VehicleApprovalGridVo();
            tempVo = (VehicleApprovalGridVo)list.get(0);

            vo1.setLbxProductID(tempVo.getLbxProductID());
            vo1.setProduct(tempVo.getProduct());
            vo1.setLbxScheme(tempVo.getLbxScheme());
            vo1.setScheme(tempVo.getScheme());
            vo1.setManufactId(tempVo.getManufactId());
            vo1.setManufacturer(tempVo.getManufacturer());
            vo1.setModelDescId(tempVo.getModelDescId());
            vo1.setModelDesc(tempVo.getModelDesc());
            vo1.setBranchAmt(tempVo.getBranchAmt());
            vo1.setNationalAmt(tempVo.getNationalAmt());
            vo1.setHoAmt(tempVo.getHoAmt());
            vo1.setVehicleApprovalID(tempVo.getVehicleApprovalID());

            currentYear -= 1;
            bYear = String.valueOf(currentYear);

            list1.add(vo1);
            vo1 = null;
          }
          if ((currentYear == Integer.parseInt(vo.getVehicleApprovalYear())) && (j < listSize))
          {
            list1.add(vo);
            currentYear -= 1;
            j += 1;
          }

        }

      }
      else
      {
        VehicleApprovalGridVo vo1 = new VehicleApprovalGridVo();
        vo1.setGridBranchAmt("");
        vo1.setGridNationalAmt("");
        vo1.setGridHoAmt("");
        bYear = String.valueOf(currentYear);
        vo1.setVehicleApprovalYear(bYear);
        currentYear -= 1;
        bYear = String.valueOf(currentYear);
        list1.add(vo1);
        vo1 = null;
      }
      vo = null;
    }
    request.setAttribute("list", list1);
    request.setAttribute("vehicleApprovalID", vehicleApprovalID);
    request.setAttribute("editVal", "editVal");
    return mapping.findForward("openAdd");
  }
}