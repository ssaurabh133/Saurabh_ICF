package com.genericUpload.actions;

import com.business.utils.async.LMSMessagingClient;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.genericUpload.actionforms.GenericUploadForm;
import com.genericUpload.dao.GenericUploadDAO;
import com.genericUpload.dto.GenericUploadParametersDTO;
import com.genericUpload.vo.XMLBean;
import com.login.roleManager.UserObject;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class GenericUploadAuthorAction<E> extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(GenericUploadAuthorAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    GenericUploadDAO glDao = (GenericUploadDAO)DaoImplInstanceFactory.getDaoImplInstance("GUD");

    logger.info("in GenericUploadAuthorAction---------------");
    GenericUploadForm excelForm = (GenericUploadForm)form;

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
    logger.info("INSIDE GenericUploadAuthorAction Function Id ==" + functionId);

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

    String extra = request.getParameter("extra");
    if ((extra != null) && (extra.equals("grid")))
    {
      actionName = null;
    }

    if (actionName == null)
    {
      XMLBean xmlbean = readXML(functionId);

      ArrayList list = glDao.searchUploads(userId, xmlbean);
      request.setAttribute("size", Integer.valueOf(list.size()));
      request.setAttribute("searchList", list);
      request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
      request.setAttribute("lovid", xmlbean.getLov_id());
      logger.info("records found =====" + list.size());

      excelForm.reset(mapping, request);
      return mapping.findForward("success");
    }

    if (actionName != null)
    {
      logger.info("actionname ======== " + actionName);

      if (actionName.equalsIgnoreCase("Search"))
      {
        XMLBean xmlbean = readXML(functionId);

        ArrayList list = glDao.searchUploads(excelForm, xmlbean);
        request.setAttribute("size", Integer.valueOf(list.size()));
        request.setAttribute("searchList", list);
        request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
        request.setAttribute("lovid", xmlbean.getLov_id());
        logger.info("records found =====" + list.size());
      }

      if (actionName.equalsIgnoreCase("downloaddump"))
      {
        XMLBean xmlbean = readXML(functionId);

        logger.info("inside downloaddump..");
        String batch = request.getParameter("batch");
        logger.info("batch === " + batch);

        ArrayList list = glDao.downloadDump(batch, xmlbean);
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
          response.setHeader("Content-Disposition", "attachment; filename=UploadOutputDump.csv");
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
            functionId = null;
          }
          return null;
        }

      }

      if (actionName.equalsIgnoreCase("openExcel"))
      {
        XMLBean xmlbean = readXML(functionId);

        String batch_id = excelForm.getBatch_id();
        logger.info("inside openExcel ----------------------");
        logger.info("inside openExcel batch_id ==========" + batch_id);

        ArrayList list = glDao.reportAdHoc(excelForm, xmlbean);
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
          response.setHeader("Content-Disposition", "attachment; filename=UploadOutputDump.csv");
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
            functionId = null;
          }
          return null;
        }

      }

      if (actionName.equalsIgnoreCase("approve"))
      {
        XMLBean xmlbean = readXML(functionId);

        String batch_id = excelForm.getBatch_id();
        logger.info("inside approve ---------------------------------" + batch_id);

        GenericUploadParametersDTO dto = new GenericUploadParametersDTO();
        dto.setBatch_id(excelForm.getBatch_id());
        dto.setBusiness_date(businessDate);
        dto.setCompany_id(String.valueOf(compid));
        dto.setBranch_id(branchId);
        dto.setUser_id(userId);
        dto.setFunctionid(functionId);
        dto.setExtra("author");

        dto.setTame_table_name(xmlbean.getTemp_table_name());

        sendMessageToGenericUploadMakerQueue(dto);

        glDao.updateBatchStatus(batch_id, userId);

        ArrayList list = glDao.searchUploads(userId, xmlbean);
        request.setAttribute("size", Integer.valueOf(list.size()));
        request.setAttribute("searchList", list);
        request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
        request.setAttribute("lovid", xmlbean.getLov_id());
        logger.info("records found for approve =====" + list.size());

        request.setAttribute("msg", " " + xmlbean.getWhat_upload() + " creation has been Successfully done for batchid " + batch_id + " ");
      }

      if (actionName.equalsIgnoreCase("sendBack"))
      {
        XMLBean xmlbean = readXML(functionId);

        String comment = excelForm.getComment();
        String batch_id = excelForm.getBatch_id();
        logger.info("inside sendBack batch_id ======" + batch_id + "  comment === " + comment);

        glDao.sendBack(batch_id, userId, comment);

        ArrayList list = glDao.searchUploads(userId, xmlbean);
        request.setAttribute("size", Integer.valueOf(list.size()));
        request.setAttribute("searchList", list);
        request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
        request.setAttribute("lovid", xmlbean.getLov_id());
        logger.info("records found =====" + list.size());
        request.setAttribute("msg", "Data for batchid " + batch_id + " sent back");
      }

      if (actionName.equalsIgnoreCase("reject"))
      {
        XMLBean xmlbean = readXML(functionId);

        String batch_id = excelForm.getBatch_id();
        String comment = excelForm.getComment();
        logger.info("inside reject batch_id ======" + batch_id + " comment ==" + comment);

        glDao.reject(batch_id, userId, comment);

        ArrayList list = glDao.searchUploads(userId, xmlbean);
        request.setAttribute("size", Integer.valueOf(list.size()));
        request.setAttribute("searchList", list);
        request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
        request.setAttribute("lovid", xmlbean.getLov_id());
        logger.info("records found =====" + list.size());
        request.setAttribute("msg", "Data for batchid " + batch_id + " has been rejected");
      }

    }

    excelForm.reset(mapping, request);
    return mapping.findForward("success");
  }

  private boolean sendMessageToGenericUploadMakerQueue(GenericUploadParametersDTO dto)
  {
    LMSMessagingClient messageClient = new LMSMessagingClient();
    try
    {
      messageClient.sendObjectMessage(dto, "GenericUploadMakerQueue");
    }
    catch (Exception e)
    {
      logger.info("error in sendMessageToGenericUploadMakerQueue(GenericUploadParametersDTO dto)");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private XMLBean readXML(String function_id)
  {
    EnvironmentConfiguration envConfig = new EnvironmentConfiguration();
    XMLConfiguration xmlConfig = null;
    try
    {
      xmlConfig = new XMLConfiguration("AllUploadConfig.xml");
      xmlConfig.setExpressionEngine(new XPathExpressionEngine());

      String defaultsmooksconfigfolder = xmlConfig.getString("all-uploads/default-config[name = 'default']/smooksConfigFolder");
      String defaultmakerproc = xmlConfig.getString("all-uploads/default-config[name = 'default']/maker-proc-name");
      String defaultauthorproc = xmlConfig.getString("all-uploads/default-config[name = 'default']/author-proc-name");
      String defaultautoforward = xmlConfig.getString("all-uploads/default-config[name = 'default']/auto-forward-to-author");
      String defaultautoauthor = xmlConfig.getString("all-uploads/default-config[name = 'default']/autho-author");
      String defaultautoauthorid = xmlConfig.getString("all-uploads/default-config[name = 'default']/auto-author-userid");

      logger.info("defaultsmooksconfigfolder=" + defaultsmooksconfigfolder);
      logger.info("defaultsmakerproc=" + defaultmakerproc);
      logger.info("defaultsauthorproc=" + defaultauthorproc);
      logger.info("defaultautoforward=" + defaultautoforward);
      logger.info("defaultautoauthor=" + defaultautoauthor);
      logger.info("defaultautoauthorid=" + defaultautoauthorid);

      String name = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/name");
      String smooksfiles = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/smooksfiles");
      String smookscsvbean = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/smooks-csv-bean");
      String javascriptfilename = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/java-script-file-name");
      String autoforwardtoauthor = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/auto-forward-to-author");
      String autoauthor = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/auto-author");
      String autoauthoruserid = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/auto-author-userid");
      String temp_table_name = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/temp-table-name");
      String main_table_dump_query = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/main-table-dump-query");
      String temp_table_dump_query = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/temp-table-dump-query");
      String what_upload = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/what-upload");
      String lov_id = xmlConfig.getString("all-uploads/upload[author-function-id = '" + function_id + "']/lov-id");

      logger.info("name=" + name);
      logger.info("smooksfiles= " + smooksfiles);
      logger.info("smookscsvbean= " + smookscsvbean);
      logger.info("javascriptfilename= " + javascriptfilename);
      logger.info("autoforwardtoauthor= " + autoforwardtoauthor);
      logger.info("authoauthor= " + autoauthor);
      logger.info("autoauthoruserid= " + autoauthoruserid);
      logger.info("temp_table_name= " + temp_table_name);
      logger.info("query_for_dump= " + main_table_dump_query);
      logger.info("what_upload = " + what_upload);
      logger.info("lov_id== " + lov_id);

      XMLBean bean = new XMLBean();
      bean.setDefaultsmooksconfigfolder(defaultsmooksconfigfolder);
      bean.setDefaultmakerproc(defaultmakerproc);
      bean.setDefaultauthorproc(defaultauthorproc);
      bean.setDefaultautoforward(defaultautoforward);
      bean.setDefaultautoauthor(defaultautoauthor);
      bean.setDefaultautoauthorid(defaultautoauthorid);

      bean.setName(name);
      bean.setSmooksfiles(smooksfiles);
      bean.setSmookscsvbean(smookscsvbean);
      bean.setJavascriptfilename(javascriptfilename);
      bean.setAutoforwardtoauthor(autoforwardtoauthor);
      bean.setAutoauthor(autoauthor);
      bean.setAutoauthoruserid(autoauthoruserid);
      bean.setTemp_table_name(temp_table_name);
      bean.setMain_table_dump_query(main_table_dump_query);
      bean.setTemp_table_dump_query(temp_table_dump_query);
      bean.setWhat_upload(what_upload);
      bean.setLov_id(lov_id);

      return bean;
    }
    catch (ConfigurationException e)
    {
      e.printStackTrace();
      logger.info("problem in loadXMLConfigFile method" + e.getStackTrace());
    }

    return null;
  }
}