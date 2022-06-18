package com.cp.actions;

import com.cm.dao.CreditManagementDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CollateralVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;
import java.util.ArrayList;
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

public class CollateralBehindAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(CollateralBehindAction.class.getName());

  public ActionForward collateralBehindDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In CollateralBehindAction(collateralBehindDetail)  ");
    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null) {
      logger.info("here in collateralBehindDetail method of CollateralBehindAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }

    Object sessionId = session.getAttribute("sessionID");

    ServletContext context = getServlet().getServletContext();
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

    String qry = "select parameter_value from parameter_mst where PARAMETER_KEY='MACHINE_ADDRESS'";
    String addVal = ConnectionDAO.singleReturn(qry);
    if (CommonFunction.checkNull(addVal).trim().equalsIgnoreCase(""))
      addVal = "N";
    session.setAttribute("machinAddress", addVal);

    DynaValidatorForm collateralDynaValidatorForm = (DynaValidatorForm)form;

    String cmStatus = CommonFunction.checkNull(request.getParameter("cmStatus"));

    logger.info("In CollateralBehindAction(collateralBehindDetail) cmStatus: " + cmStatus);

    CollateralVo sh = new CollateralVo();

    BeanUtils.copyProperties(sh, collateralDynaValidatorForm);

    CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info("Implementation class: " + service.getClass());

    String dealId = "";

    if (session.getAttribute("dealId") != null)
    {
      dealId = session.getAttribute("dealId").toString();
    }
    else if (session.getAttribute("maxId") != null) {
      dealId = session.getAttribute("maxId").toString();
    }

    if (userobj != null)
    {
      logger.info("Checking------------------------------------------------" + userobj.getBranchId());
      String branchId = userobj.getBranchId();
      session.setAttribute("branchId", branchId);
      logger.info("Checking------------------------------------------------" + branchId);
    }

    logger.info("In CollateralBehindAction(collateralBehindDetail) DealId " + dealId);

    if ((dealId != null) && (!dealId.equalsIgnoreCase("")) && (cmStatus.equalsIgnoreCase("")))
    {
      String productCat = service.getProductType(dealId);
      logger.info("productCat: " + productCat);
      request.setAttribute("productCat", productCat);

      String goldSchemeId = service.getSchemeForGoldList(dealId);
      session.setAttribute("goldSchemeId", goldSchemeId);

      String goldloanAmount = service.getloanAmountForGoldOrnament(dealId);
      session.setAttribute("goldloanAmount", goldloanAmount);

      ArrayList showCollateralDetails = service.getCollateralDetailsAll(dealId);
      ArrayList assets = service.getAssetsAll(productCat);

      ArrayList collaterals = service.getCollateralsAll(productCat);

      ArrayList securityType = service.getSecurityType();
      session.setAttribute("securityType", securityType);
      ArrayList mortageList = service.getMortageList();
      session.setAttribute("mortageList", mortageList);

      ArrayList propertyType = service.getPropertyType();
      session.setAttribute("propertyType", propertyType);
      session.setAttribute("assets", assets);
      session.setAttribute("collaterals", collaterals);
      if (showCollateralDetails.size() > 0) {
        request.setAttribute("showCollateralDetails", showCollateralDetails);
      }
      String repayQ = "select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID=" + dealId;
      logger.info("Repayment Query: " + repayQ);
      String repayType = ConnectionDAO.singleReturn(repayQ);
      logger.info("Repayment Type:" + repayType);
      String loanAmount = "select DEAL_LOAN_AMOUNT from cr_deal_loan_dtl where DEAL_ID=" + dealId;
      logger.info("loanAmount Query: " + loanAmount);
      String loanAmt = ConnectionDAO.singleReturn(loanAmount);

      session.setAttribute("loanAmount", loanAmt);
      String assetFlag = "SELECT ASSET_FLAG FROM CR_DEAL_LOAN_DTL A JOIN CR_PRODUCT_M B ON A.DEAL_PRODUCT=B.PRODUCT_ID  AND A.DEAL_ID=" + dealId;

      logger.info("assetFlag Query: " + assetFlag);
      String assetFlg = ConnectionDAO.singleReturn(assetFlag);
      if (((repayType != null) && (repayType.equalsIgnoreCase("N"))) || (CommonFunction.checkNull(assetFlg).equalsIgnoreCase("N")))
      {
        session.setAttribute("repayType", repayType);
      }
      else
      {
        session.removeAttribute("repayType");
      }
      String checkRefinaceReqInfo = service.checkRefinaceReqInfo();
      request.setAttribute("checkRefinaceReqInfo", checkRefinaceReqInfo);

      ArrayList viewPropertyTitle = new ArrayList();
      try {
        StringBuffer query = new StringBuffer();
        query.append("SELECT VALUE,DESCRIPTION FROM generic_master  where GENERIC_KEY='PROPERTY_TITLE' and REC_STATUS in ('A','I')");

        logger.info("In creditProcessingDaoI getPropertytTitle" + query);

        ArrayList collateralsAll = ConnectionDAO.sqlSelect(query.toString());
        query = null;
        for (int i = 0; i < collateralsAll.size(); i++) {
          ArrayList sublist = (ArrayList)collateralsAll.get(i);
          if (sublist.size() > 0) {
            CodeDescVo av = new CodeDescVo();
            av.setId(CommonFunction.checkNull(sublist.get(0)).trim());
            av.setName(CommonFunction.checkNull(sublist.get(1)).trim());
            viewPropertyTitle.add(av);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      ArrayList viewMortageList = new ArrayList();
      try {
        StringBuffer query = new StringBuffer();
        query.append("SELECT VALUE,DESCRIPTION FROM generic_master  where GENERIC_KEY='MORTAGE_TYPE' and REC_STATUS in ('A','I')");

        logger.info("In creditProcessingDaoI getMortageList" + query.toString());

        ArrayList mortage = ConnectionDAO.sqlSelect(query.toString());
        query = null;
        for (int i = 0; i < mortage.size(); i++) {
          ArrayList sublist = (ArrayList)mortage.get(i);
          if (sublist.size() > 0) {
            CodeDescVo av = new CodeDescVo();
            av.setId(CommonFunction.checkNull(sublist.get(0)).trim());

            av.setName(CommonFunction.checkNull(sublist.get(1)).trim());

            viewMortageList.add(av);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      session.setAttribute("viewPropertyTitle", viewPropertyTitle);

      session.setAttribute("viewMortageList", viewMortageList);

      return mapping.findForward("success");
    }
    logger.info("In CollateralBehindAction(collateralBehindDetail) Back ");
    request.setAttribute("back", "B");
    return mapping.findForward("backToFirst");
  }

  public ActionForward openACPopup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In openACPopup(collateralBehindDetail)  ");
    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null) {
      logger.info("here in collateralBehindDetail method of CollateralBehindAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    Object sessionId = session.getAttribute("sessionID");

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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
    CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info("Implementation class: " + service.getClass());
    String assetClass = request.getParameter("assetClass");
    String assetCollateralType = request.getParameter("assetCollateralType");
    String productCat = request.getParameter("productCat");

    String dealId = "";
    String loanId = "";
    if (session.getAttribute("dealId") != null)
      dealId = session.getAttribute("dealId").toString();
    else if (session.getAttribute("maxId") != null) {
      dealId = session.getAttribute("maxId").toString();
    }
    if (session.getAttribute("loanId") != null)
    {
      loanId = session.getAttribute("loanId").toString();
    }

    if (!CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
    {
      String loanAmount = "select DEAL_LOAN_AMOUNT from cr_deal_loan_dtl where DEAL_ID=" + dealId;
      logger.info("loanAmount Query: " + loanAmount);
      String loanAmt = ConnectionDAO.singleReturn(loanAmount);
      String assetCost = "select DEAL_ASSET_COST  from cr_deal_loan_dtl where DEAL_ID=" + dealId;
      logger.info("Asset Cost Query: " + assetCost);
      String assetAmt = ConnectionDAO.singleReturn(assetCost);
      String productId = ConnectionDAO.singleReturn("select DEAL_PRODUCT from cr_deal_loan_dtl where DEAL_ID=" + dealId + "");
      String schemeId = ConnectionDAO.singleReturn("select DEAL_SCHEME from cr_deal_loan_dtl where DEAL_ID=" + dealId + "");
      CollateralVo vo = new CollateralVo();
      ArrayList fetchCollateralDetails = new ArrayList();
      vo.setLoanAmount(loanAmt);
      vo.setVehicleCost(assetAmt);
      vo.setAssetsCollateralValue(assetAmt);
      vo.setColltype2("ASSET");
      vo.setVehicleDiscount("0.00");

      vo.setColltype2(assetCollateralType);
      fetchCollateralDetails.add(vo);
      session.setAttribute("productId", productId);
      session.setAttribute("schemeId", schemeId);
      request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
    }
    else
    {
      String loanAmount = "select LOAN_LOAN_AMOUNT from cr_loan_dtl where LOAN_ID=" + loanId;
      logger.info("loanAmount Query: " + loanAmount);
      String loanAmt = ConnectionDAO.singleReturn(loanAmount);
      String assetCost = "select LOAN_ASSET_COST  from cr_loan_dtl where LOAN_ID=" + loanId;
      logger.info("Asset Cost Query: " + assetCost);
      String assetAmt = ConnectionDAO.singleReturn(assetCost);
      String productId = ConnectionDAO.singleReturn("select LOAN_PRODUCT from cr_loan_dtl where LOAN_ID=" + loanId + "");
      String schemeId = ConnectionDAO.singleReturn("select LOAN_SCHEME from cr_loan_dtl where LOAN_ID=" + loanId + "");

      CollateralVo vo = new CollateralVo();
      ArrayList fetchCollateralDetails = new ArrayList();
      vo.setLoanAmount(loanAmt);
      vo.setVehicleCost(assetAmt);
      vo.setAssetsCollateralValue(assetAmt);
      vo.setColltype2("ASSET");
      vo.setVehicleDiscount("0.00");
      if ((!CommonFunction.checkNull(loanAmt).equalsIgnoreCase("")) && (!CommonFunction.checkNull(assetAmt).equalsIgnoreCase("")))
      {
        double loan = Double.parseDouble(loanAmt);
        double value = Double.parseDouble(assetAmt);
        double ltv = loan * 100.0D / value;
        String ltvValue = ltv + "00000";
        int index = ltvValue.indexOf(".");
        if (index > 0)
          ltvValue = ltvValue.substring(0, index + 3);
        vo.setCollateralSecurityMargin(ltvValue);
      }
      vo.setColltype2(assetCollateralType);
      fetchCollateralDetails.add(vo);
      session.setAttribute("productId", productId);
      session.setAttribute("schemeId", schemeId);
      request.setAttribute("fetchCollateralDetails", fetchCollateralDetails);
    }

    if (assetClass != null)
    {
      request.setAttribute("assetClass", assetClass);
    }
    request.setAttribute("new", "N");
    logger.info("assetCollateralType::::::::::::::::::::" + assetCollateralType);
    if (assetCollateralType != null)
    {
      request.setAttribute("productCat", productCat);
      if (assetCollateralType.equalsIgnoreCase("ASSET")) {
        request.setAttribute("ASSET", assetCollateralType);
      }
      else
      {
        request.setAttribute("assetCollateralTypeColl", assetCollateralType);
        request.setAttribute("COLLATERAL", assetCollateralType);
      }
    }
    String checkRefinaceReqInfo = service.checkRefinaceReqInfo();
    request.setAttribute("checkRefinaceReqInfo", checkRefinaceReqInfo);
    return mapping.findForward("collateralVehicle");
  }

  public ActionForward deleteCollateralDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.info("In CollateralBehindAction(deleteCollateralDetails) ");
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null)
      return mapping.findForward("sessionOut");
    String sessionId = session.getAttribute("sessionID").toString();
    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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

    String source = "NE";
    String functionId = (String)session.getAttribute("functionId");
    int funid = Integer.parseInt(functionId);
    if ((funid == 4000122) || (funid == 4000123)) {
      source = "ED";
    }
    CollateralVo sh = new CollateralVo();

    String dealId = "";
    String loanId = "";
    if (session.getAttribute("dealId") != null)
      dealId = session.getAttribute("dealId").toString();
    else if (session.getAttribute("maxId") != null)
      dealId = session.getAttribute("maxId").toString();
    logger.info("In CollateralBehindAction(collateralBehindDetail) DealId " + dealId);

    boolean status = false;
    CreditProcessingDAO detail = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info("Implementation class: " + detail.getClass());

    String type = "";
    String retStr = "";
    String[] id = null;
    String CommonId = "";
    String assetName = "";
    String productCat = "";

    if ((request.getParameter("type") != null) && (!CommonFunction.checkNull(request.getParameter("type")).equalsIgnoreCase("")))
    {
      type = request.getParameter("type");
      session.setAttribute("type", type);
    }
    else
    {
      type = session.getAttribute("type").toString();
      session.removeAttribute("type");
    }

    if ((request.getParameter("checkAssetValue") != null) && (!CommonFunction.checkNull(request.getParameter("checkAssetValue")).equalsIgnoreCase("")))
    {
      logger.info("if asset id " + CommonFunction.checkNull(request.getParameter("checkAssetValue")));
      id = CommonFunction.checkNull(request.getParameter("checkAssetValue")).split(",");
      session.setAttribute("id", id);
    }
    else
    {
      id = (String[])session.getAttribute("id");
      logger.info("else asset id " + id);
      session.removeAttribute("id");
    }

    String[] collType = null;
    if ((request.getParameterValues("collType") != null) && (!CommonFunction.checkNull(request.getParameterValues("collType")).equalsIgnoreCase("")))
    {
      collType = request.getParameterValues("collType");
      session.setAttribute("collType", collType);
    }
    else
    {
      collType = (String[])session.getAttribute("collType");
      session.removeAttribute("collType");
    }

    if (session.getAttribute("dealId") != null)
      CommonId = session.getAttribute("dealId").toString();
    else if (session.getAttribute("maxId") != null)
      CommonId = session.getAttribute("maxId").toString();
    if (session.getAttribute("loanId") != null)
    {
      CommonId = session.getAttribute("loanId").toString();
      loanId = session.getAttribute("loanId").toString();
    }
    else if (session.getAttribute("maxIdInCM") != null)
    {
      CommonId = session.getAttribute("maxIdInCM").toString();
      if (session.getAttribute("loanId") != null)
        loanId = session.getAttribute("loanId").toString();
    }
    String confirmStatus = request.getParameter("confirmStatus");
    logger.info("confirmStatus: " + confirmStatus);
    if ((dealId != null) && (!CommonFunction.checkNull(dealId).equalsIgnoreCase("")) && (CommonFunction.checkNull(confirmStatus).equalsIgnoreCase("N")))
    {
      assetName = detail.checkAssetVerifInit(id, dealId);
    }
    else if (CommonFunction.checkNull(confirmStatus).equalsIgnoreCase("N"))
    {
      assetName = detail.checkAssetVerifInitAtCM(id, loanId);
    }
    logger.info("assetName: " + assetName);
    if ((dealId != null) && (!CommonFunction.checkNull(dealId).equalsIgnoreCase("")) && (!CommonFunction.checkNull(confirmStatus).equalsIgnoreCase(""))) {
      sh.setDealId(dealId);
      if (CommonFunction.checkNull(assetName).equalsIgnoreCase(""))
      {
        for (int k = 0; k < id.length; k++)
        {
          status = detail.deleteCollateralDetails(id[k], type, source);
          String repayQ = "select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID=" + CommonId;
          logger.info("Repayment Query: " + repayQ);
          String repayType = ConnectionDAO.singleReturn(repayQ);
          logger.info("Repayment Type:" + repayType);
          String assetFlag = "SELECT ASSET_FLAG FROM CR_DEAL_LOAN_DTL A JOIN CR_PRODUCT_M B ON A.DEAL_PRODUCT=B.PRODUCT_ID  AND A.DEAL_ID=" + CommonId;
          logger.info("assetFlag: " + assetFlag);
          String assetFlg = ConnectionDAO.singleReturn(assetFlag);

          if (((repayType != null) && (repayType.equalsIgnoreCase("N"))) || (CommonFunction.checkNull(assetFlg).equalsIgnoreCase("N")))
            session.setAttribute("repayType", repayType);
          else
            session.removeAttribute("repayType");
          CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
          logger.info("Implementation class: " + service.getClass());
          if ((dealId != null) && (!dealId.equalsIgnoreCase("")))
          {
            productCat = service.getProductType(dealId);
            request.setAttribute("productCat", productCat);

            ArrayList showCollateralDetails = service.getCollateralDetailsAll(dealId);
            request.setAttribute("showCollateralDetails", showCollateralDetails);
          }
          else if ((loanId != null) && (!loanId.equalsIgnoreCase("")))
          {
            CreditManagementDAO dao = (CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance("CMD");
            logger.info("Implementation class: " + dao.getClass());

            productCat = dao.getProductTypeInCM(loanId);
            logger.info("productCat: " + productCat);
            request.setAttribute("productCat", productCat);

            ArrayList showCollateralDetails = dao.selectAsset(loanId, source);
            request.setAttribute("showCollateralDetails", showCollateralDetails);
          }
          ArrayList assets = service.getAssetsAll(productCat);
          ArrayList collaterals = service.getCollateralsAll(productCat);
          session.setAttribute("assets", assets);
          session.setAttribute("collaterals", collaterals);
          request.setAttribute("sms", "");
        }
        if (status)
        {
          if (type.equalsIgnoreCase("D"))
          {
            status = detail.deleteVerificationInitAsset(CommonId, id, type);
          }
          status = detail.deleteCollateralAssetDocs(CommonId, id, collType, type);
          session.removeAttribute("assetsId");
          session.removeAttribute("assetsCollateralDesc");
          session.removeAttribute("assetsCollateralValue");
          session.removeAttribute("colltype1");
          RefreshFlagVo vo = new RefreshFlagVo();
          if ((CommonId != null) && (!CommonId.trim().equalsIgnoreCase("")))
            vo.setRecordId(Integer.parseInt(CommonId.trim()));
          if ((!CommonFunction.checkNull(dealId).equalsIgnoreCase("")) && (CommonFunction.checkNull(loanId).equalsIgnoreCase("")))
          {
            vo.setTabIndex(10);
            vo.setModuleName("CP");
          }
          else
          {
            vo.setTabIndex(2);
            vo.setModuleName("CM");
          }
          RefreshFlagValueInsert.updateRefreshFlag(vo);
        }

      }
      else
      {
        String repayQ = "select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where DEAL_ID=" + CommonId;
        logger.info("Repayment Query: " + repayQ);
        String repayType = ConnectionDAO.singleReturn(repayQ);
        logger.info("Repayment Type:" + repayType);
        String assetFlag = "SELECT ASSET_FLAG FROM CR_DEAL_LOAN_DTL A JOIN CR_PRODUCT_M B ON A.DEAL_PRODUCT=B.PRODUCT_ID  AND A.DEAL_ID=" + CommonId;
        logger.info("assetFlag: " + assetFlag);
        String assetFlg = ConnectionDAO.singleReturn(assetFlag);

        if (((repayType != null) && (repayType.equalsIgnoreCase("N"))) || (CommonFunction.checkNull(assetFlg).equalsIgnoreCase("N")))
          session.setAttribute("repayType", repayType);
        else
          session.removeAttribute("repayType");
        CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
        logger.info("Implementation class: " + service.getClass());
        if ((dealId != null) && (!dealId.equalsIgnoreCase("")))
        {
          productCat = service.getProductType(dealId);
          request.setAttribute("productCat", productCat);

          ArrayList showCollateralDetails = service.getCollateralDetailsAll(dealId);
          request.setAttribute("showCollateralDetails", showCollateralDetails);
        }

        ArrayList assets = service.getAssetsAll(productCat);
        ArrayList collaterals = service.getCollateralsAll(productCat);
        session.setAttribute("assets", assets);
        session.setAttribute("collaterals", collaterals);
        request.setAttribute("assetNameVerification", assetName);
        request.setAttribute("sms", "");
      }
    }
    else
    {
      CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
      logger.info("Implementation class: " + service.getClass());
      CreditManagementDAO dao = (CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance("CMD");
      logger.info("Implementation class: " + dao.getClass());
      if (CommonFunction.checkNull(assetName).equalsIgnoreCase("")) {
        sh.setLoanId(CommonId);
        for (int k = 0; k < id.length; k++)
        {
          status = detail.deleteCollateralDetails(id[k], type, source);

          String repayQ = "select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID=" + CommonId;
          logger.info("Repayment Query: " + repayQ);
          String repayType = ConnectionDAO.singleReturn(repayQ);
          logger.info("Repayment Type:" + repayType);
          String assetFlag = "SELECT ASSET_FLAG FROM CR_LOAN_DTL A JOIN CR_PRODUCT_M B ON A.DEAL_PRODUCT=B.PRODUCT_ID  AND A.LOAN_ID=" + CommonId;
          logger.info("assetFlag: " + assetFlag);
          String assetFlg = ConnectionDAO.singleReturn(assetFlag);

          if (((repayType != null) && (repayType.equalsIgnoreCase("N"))) || (CommonFunction.checkNull(assetFlg).equalsIgnoreCase("N")))
            session.setAttribute("repayType", repayType);
          else {
            session.removeAttribute("repayType");
          }

          if ((loanId != null) && (!loanId.equalsIgnoreCase("")))
          {
            productCat = dao.getProductTypeInCM(loanId);
            logger.info("productCat: " + productCat);
            request.setAttribute("productCat", productCat);

            ArrayList showCollateralDetails = dao.selectAsset(loanId, source);
            request.setAttribute("showCollateralDetails", showCollateralDetails);
          }
          ArrayList assets = service.getAssetsAll(productCat);
          ArrayList collaterals = service.getCollateralsAll(productCat);
          session.setAttribute("assets", assets);
          session.setAttribute("collaterals", collaterals);
        }

        if (status)
        {
          boolean status1;
          if (type.equalsIgnoreCase("L"))
          {
            status1 = detail.deleteVerificationInitAsset(CommonId, id, type);
          }
          boolean status2 = detail.deleteCollateralAssetDocs(CommonId, id, collType, type);
          session.removeAttribute("assetsId");
          session.removeAttribute("assetsCollateralDesc");
          session.removeAttribute("assetsCollateralValue");
          session.removeAttribute("colltype1");
          RefreshFlagVo vo = new RefreshFlagVo();
          if ((CommonId != null) && (!CommonId.trim().equalsIgnoreCase(""))) {
            vo.setRecordId(Integer.parseInt(CommonId.trim()));
          }
          vo.setTabIndex(2);
          vo.setModuleName("CM");

          RefreshFlagValueInsert.updateRefreshFlag(vo);
          request.setAttribute("deleteMsg", "S");
        }

      }
      else
      {
        productCat = dao.getProductTypeInCM(loanId);
        logger.info("productCat: " + productCat);
        request.setAttribute("productCat", productCat);
        ArrayList showCollateralDetails = dao.selectAsset(loanId, source);
        request.setAttribute("showCollateralDetails", showCollateralDetails);
        ArrayList assets = service.getAssetsAll(productCat);
        ArrayList collaterals = service.getCollateralsAll(productCat);
        session.setAttribute("assets", assets);
        session.setAttribute("collaterals", collaterals);
        request.setAttribute("assetNameVerification", assetName);
      }
    }

    if (type.equalsIgnoreCase("D")) {
      retStr = "delCollateralDeal";
    } else if (type.equalsIgnoreCase("L")) {
      String checkParaQuery = "SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='NEW_ASSET_COLLATERAL'";
      logger.info("checkParaQuery: " + checkParaQuery);
      String checkParaValue = ConnectionDAO.singleReturn(checkParaQuery);
      logger.info("checkParaValue: " + checkParaValue);
      if (CommonFunction.checkNull(checkParaValue).equalsIgnoreCase("N"))
      {
        retStr = "delCollateralLoan";
      }
      else {
        retStr = "newDeleteCollateralLoan";
      }
    }

    sh.setSource(source);
    sh.setType(type);
    String productID = CommonFunction.checkNull(session.getAttribute("productId"));
    String productLoanType = detail.getProductLoanType(productID);
    logger.info("productLoanType------------" + productLoanType);
    boolean checkFlag;
    if (productLoanType.equalsIgnoreCase("HP")) {
      checkFlag = detail.vatDetail(sh);
    }

    String checkRefinaceReqInfo = detail.checkRefinaceReqInfo();
    request.setAttribute("checkRefinaceReqInfo", checkRefinaceReqInfo);

    return mapping.findForward(retStr);
  }

  public ActionForward openPropertyPopup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In openPropertyPopup(collateralBehindDetail)  ");
    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null) {
      logger.info("here in openPropertyPopup method of CollateralBehindAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    Object sessionId = session.getAttribute("sessionID");

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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
    ArrayList propertyOwnerList = null;
    String assetClass = request.getParameter("assetClass");
    String assetCollateralType = request.getParameter("assetCollateralType");
    String productCat = request.getParameter("productCat");

    String dealId = "";
    String loanId = "";
    if (session.getAttribute("dealId") != null)
      dealId = session.getAttribute("dealId").toString();
    else if (session.getAttribute("maxId") != null) {
      dealId = session.getAttribute("maxId").toString();
    }
    session.setAttribute("dealId", dealId);

    if (session.getAttribute("loanId") != null)
    {
      loanId = session.getAttribute("loanId").toString();
    }

    if ((dealId != null) && (!dealId.equalsIgnoreCase("")))
    {
      request.setAttribute("dealAsset", "dealAsset");
    }
    if ((loanId != null) && (!loanId.equalsIgnoreCase("")))
    {
      request.setAttribute("loanAsset", "loanAsset");
    }
    CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info("Implementation class: " + service.getClass());

    request.setAttribute("propertyOwnerList", propertyOwnerList);
    ArrayList showPropertyStatusDetails = service.getPropertyStatus();

    ArrayList showPropertyTitleDetails = service.getPropertytTitle();
    request.setAttribute("propertyTitle", showPropertyTitleDetails);

    request.setAttribute("propertyStatus", showPropertyStatusDetails);
    ArrayList propertyType = service.getPropertyType();
    request.setAttribute("propertyType", propertyType);

    return mapping.findForward("collateralProperty");
  }

  public ActionForward openGoldPopup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In openPropertyPopup(collateralBehindDetail)  ");
    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null) {
      logger.info("here in openGoldPopup method of CollateralBehindAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    Object sessionId = session.getAttribute("sessionID");

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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

    String dealId = "";
    String loanId = "";
    if (session.getAttribute("dealId") != null)
      dealId = session.getAttribute("dealId").toString();
    else if (session.getAttribute("maxId") != null) {
      dealId = session.getAttribute("maxId").toString();
    }
    session.setAttribute("dealId", dealId);

    if (session.getAttribute("loanId") != null)
    {
      loanId = session.getAttribute("loanId").toString();
    }

    if ((dealId != null) && (!dealId.equalsIgnoreCase("")))
    {
      request.setAttribute("dealAsset", "dealAsset");
    }
    if ((loanId != null) && (!loanId.equalsIgnoreCase("")))
    {
      request.setAttribute("loanAsset", "loanAsset");
    }
    CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info("Implementation class: " + service.getClass());

    return mapping.findForward("collateralGold");
  }

  public ActionForward openInsurancePopup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In openInsurancePopup(collateralBehindDetail)  ");
    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null) {
      logger.info("here in openInsurancePopup method of CollateralBehindAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    Object sessionId = session.getAttribute("sessionID");

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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
    ArrayList propertyOwnerList = null;
    String assetClass = request.getParameter("assetClass");
    String assetCollateralType = request.getParameter("assetCollateralType");
    String productCat = request.getParameter("productCat");

    String dealId = "";
    String loanId = "";
    if (session.getAttribute("dealId") != null)
      dealId = session.getAttribute("dealId").toString();
    else if (session.getAttribute("maxId") != null) {
      dealId = session.getAttribute("maxId").toString();
    }
    if (session.getAttribute("loanId") != null)
    {
      loanId = session.getAttribute("loanId").toString();
    }

    if ((dealId != null) && (!dealId.equalsIgnoreCase("")))
    {
      request.setAttribute("dealAsset", "dealAsset");
    }
    if ((loanId != null) && (!loanId.equalsIgnoreCase("")))
    {
      request.setAttribute("loanAsset", "loanAsset");
    }
    CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info("Implementation class: " + service.getClass());

    ArrayList showInsuranceRelWithNominee = service.showInsuranceRelWithNominee();
    request.setAttribute("showInsuranceRelWithNominee", showInsuranceRelWithNominee);

    return mapping.findForward("collateralInsurance");
  }
}