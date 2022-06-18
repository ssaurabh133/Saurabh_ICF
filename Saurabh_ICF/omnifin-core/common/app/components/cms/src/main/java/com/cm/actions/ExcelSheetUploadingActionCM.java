package com.cm.actions;

import com.cm.actionform.ExcelSheetUploadFormCM;
import com.cm.business.ExcelSheetUploadBusiness;
import com.connect.CommonFunction;
import com.login.roleManager.UserObject;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class ExcelSheetUploadingActionCM<E> extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(ExcelSheetUploadingActionCM.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("in ExcelSheetUploadingAction IN CM---------------");
    ExcelSheetUploadFormCM excelForm = (ExcelSheetUploadFormCM)form;
    String dbType = this.resource.getString("lbl.dbType");
    ExcelSheetUploadBusiness uploadObj = null;
    uploadObj = new ExcelSheetUploadBusiness();
    String actionName = null;

    actionName = excelForm.getActionName();
    logger.info("actionName:------" + actionName);
    HttpSession session = request.getSession(false);
    UserObject sessUser = (UserObject)session.getAttribute("userobject");
    int compid = 0;

    String branchId = "";

    String businessDate = "";
    String userId = "";
    String sessionID = "";
    String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
    if (sessUser != null) {
      businessDate = sessUser.getBusinessdate();
      compid = sessUser.getCompanyId();

      branchId = sessUser.getBranchId();

      userId = sessUser.getUserId();
      sessionID = session.getId();
    }
    excelForm.setCompanyId(compid);
    excelForm.setSessionId(sessionID);
    excelForm.setMakerId(userId);
    excelForm.setBranchId(branchId);
    excelForm.setBusinessDate(businessDate);
    if(functionId.equalsIgnoreCase("4000452")){
		String prestDate="";
if(request.getParameter("prestDate")!=null){
	prestDate=request.getParameter("prestDate").toString();
	session.setAttribute("presentationDate", prestDate);
	excelForm.setBusinessDate(prestDate);
}
if(session.getAttribute("presentationDate")!=null){
	prestDate=session.getAttribute("presentationDate").toString();
	excelForm.setBusinessDate(prestDate);
}
		}
    
    if (functionId.equalsIgnoreCase("10000606")) {
      request.setAttribute("bounceRealization", "Stationary Upload");
    }
    else if (functionId.equalsIgnoreCase("10000817")) {
      request.setAttribute("bounceRealization", "Recovery Executive Allocation Change");
    }
    else if (functionId.equalsIgnoreCase("10000691")) {
      request.setAttribute("bounceRealization", "CMS UPLOAD");
    }
    else if (functionId.equalsIgnoreCase("10000625")) {
      request.setAttribute("bounceRealization", "Allocation UPLOAD");
    }
    else if(functionId.equalsIgnoreCase("4000452")){
		 request.setAttribute("bounceRealization", "Multiple batch Upload");
	 }
    else if(functionId.equalsIgnoreCase("10000640")) {
		 request.setAttribute("bounceRealization", "SPDC UPLOAD");
	 }
    if (actionName != null)
    {
      logger.info("action value-::-" + actionName);

      if (actionName.equalsIgnoreCase("saveExcel"))
      {
        logger.info("import Action in ExcelSheetUploading------");

        boolean uploadStatus = UploadDocuments.docUpload(request, excelForm.getDocFile());
        boolean flag = false;

        logger.info("ExcelSheetUploading.getDocFile().getFileName()=====" + excelForm.getDocFile().getFileName());
        logger.info("uploadStatus :==>>" + uploadStatus);

        if (uploadStatus) {
          logger.info("uploadStatus-----------------------" + uploadStatus);
          flag = uploadObj.uploadExcel(request, response, excelForm.getDocFile().getFileName(), excelForm);
          logger.info("flag-----------------------" + flag);
        }
        if (!flag) {
          String dateCheck = (String)request.getAttribute("dateCheck");

          if (CommonFunction.checkNull(dateCheck).equalsIgnoreCase(""))
            request.setAttribute("fieldUpdate", "Some problem in excel sheet format..");
          else
            request.setAttribute("fieldUpdate", dateCheck);
        }
        else {
        	String msg="";
			if(functionId.equalsIgnoreCase("4000452")){
				if(flag){
					msg="A";
				}else{
					msg="E";
				}
				
			}else{
			 msg=uploadObj.saveVoucher(request,excelForm);
			}
          logger.info("msg after voucher save in excel sheet action:---" + msg);
          if (CommonFunction.checkNull(msg).equalsIgnoreCase("S")) {
            request.setAttribute("errorMsg", "Voucher Upload Sucessfully");
          }else if(CommonFunction.checkNull(msg).equalsIgnoreCase("A")){
			  request.setAttribute("errorMsg", "Data Upload Sucessfully");
		  }
          else {
            request.setAttribute("errorMsg", "Some problem in ExcelSheet");
          }
        }

      }

      if (actionName.equalsIgnoreCase("saveCsv"))
      {
        String filename = excelForm.getDocFile().getFileName();

        boolean check = uploadObj.checkPreviousFiles(filename);
        if (check)
        {
          logger.info(filename + " is already uploaded");
          request.setAttribute("filecheck", filename + " is already uploaded");
        }
        if (!check)
        {
          logger.info("Import Action in CSvSheetUploading------");

          boolean uploadStatus = uploadObj.docUploadForExcel(request, excelForm.getDocFile());

          String filePathWithName = request.getAttribute("filePathWithName").toString();
          logger.info("filePathWithName:::::" + filePathWithName);

          boolean flag = false;
          logger.info("uploadStatus----------------------- " + uploadStatus);

          if (uploadStatus)
          {
            excelForm.setFilePathWithName(filePathWithName);
            flag = uploadObj.uploadCsv(request, response, excelForm.getDocFile().getFileName(), excelForm);
            logger.info("flag-----------------------" + flag);
          }
          if (!flag) {
            request.setAttribute("fieldUpdate", "Some problem in sheet format..");
          }
          else
          {
            String msg = null;

            if (functionId.equalsIgnoreCase("10000602"))
            {
              logger.info("Action for Boucne Realization.... " + functionId);
              msg = uploadObj.saveBounce(request, excelForm);
            }
            else if (functionId.equalsIgnoreCase("10000601"))
            {
              logger.info("Action for Receipt Upload.... " + functionId);
              msg = uploadObj.saveReceipt(request, excelForm);
            }
            else if (functionId.equalsIgnoreCase("10000817")) {
              msg = uploadObj.recoveryUploadSave(request, excelForm);
            }
            else if (functionId.equalsIgnoreCase("10000606"))
            {
              logger.info("Action for STATIONARY Upload.... " + functionId);
              msg = uploadObj.saveStationary(request, excelForm);
            }
            else if (functionId.equalsIgnoreCase("10000625")) {
              msg = uploadObj.AllocationProcessUploadSave(request, excelForm);
            }
            else if (functionId.equalsIgnoreCase("10000691"))
            {
              logger.info("Action for CMS UPLOAD.... " + functionId);
              msg = uploadObj.save_CmsUpload(request, excelForm);
            }
            else if(functionId.equalsIgnoreCase("4000452")) 
			{
				logger.info("Action for waive off Upload.... "+functionId);
				msg=uploadObj.saveMultipleBatch(request,excelForm);
			}
            else if(functionId.equalsIgnoreCase("10000640")) 
			{
				logger.info("Action for SPDC Upload.... "+functionId);
				msg=uploadObj.saveSPDCBatch(request,excelForm);
			}
            else
            {
              logger.info("Action for GL VOUCHER UPLOAD.... " + functionId);
              msg = uploadObj.saveVoucher(request, excelForm);
            }

            logger.info("msg after save in action:--- " + msg);

            if (CommonFunction.checkNull(msg).equalsIgnoreCase("S"))
            {
              request.setAttribute("errorMsg", "Data Upload Sucessfully.");
            }
            else
            {
              request.setAttribute("errorMsg", "Some problem in the Sheet.");
            }

          }

        }

      }

      if (actionName.equalsIgnoreCase("openExcel")) {
        ArrayList list = uploadObj.reportAdHoc(excelForm, functionId);
        logger.info("Report Size  :  " + list.size());
        int size = list.size();
        if (size == 0)
        {
          request.setAttribute("error", "error");
        }
        else
        {
          StringBuffer fileNameFormat = new StringBuffer();
          PrintWriter out = response.getWriter();
          response.setContentType("application/vnd.ms-excel");
          response.setHeader("Content-Disposition", "attachment; filename=Dump.csv");
          ArrayList subList = new ArrayList();
          try {
            for (int i = 0; i < size; i++) {
              subList = (ArrayList)list.get(i);
              int subSize = subList.size();
              for (int j = 0; j < subSize; j++) {
                fileNameFormat.append('"');
                fileNameFormat.append(CommonFunction.checkNull(subList.get(j)));
                fileNameFormat.append('"');
                fileNameFormat.append(',');
              }
              fileNameFormat.append("\n");
            }
            out.write(fileNameFormat.toString());
          } catch (Exception e) {
            logger.error(e.getMessage().toString());
          } finally {
            subList.clear();
            list.clear();
            fileNameFormat.setLength(0);
            fileNameFormat = null;
            out.flush();
            out.close();
          }
          return null;
        }
      }

    }

    if (CommonFunction.checkNull(dbType).equalsIgnoreCase("MSSQL")) {
      request.setAttribute("show", "show");
    }

    excelForm.reset(mapping, request);
    return mapping.findForward("sucesss");
  }
}