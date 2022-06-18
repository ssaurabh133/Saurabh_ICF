package com.genericUpload.actions;

import com.connect.CommonFunction;
import com.genericUpload.actionforms.GenericUploadForm;
import com.genericUpload.daoImplMYSQL.GenericUploadDAOImpl;
import com.genericUpload.dto.GenericUploadParametersDTO;
import com.genericUpload.vo.XMLBean;
import com.login.roleManager.UserObject;
import com.utils.async.LMSMessagingClient;
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
import org.apache.struts.upload.FormFile;

public class GenericUploadMakerAction<E> extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(GenericUploadMakerAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    GenericUploadDAOImpl glDao = new GenericUploadDAOImpl();
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
    logger.info("In GeneicUploadmakerAction functionid === " + functionId);

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

      logger.info("inside action name null");
      ArrayList list = glDao.searchData(userId, xmlbean);
      request.setAttribute("searchList", list);
      request.setAttribute("size", Integer.valueOf(list.size()));
      request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
      request.setAttribute("lovid", xmlbean.getLov_id());

      request.setAttribute("functionId", functionId);
      excelForm.reset(mapping, request);
      return mapping.findForward("sucesss");
    }

    if (actionName != null)
    {
      logger.info("action value-::-" + actionName);

      if (actionName.equalsIgnoreCase("saveCsv"))
      {
        XMLBean xmlbean = readXML(functionId);

        String upload_type = xmlbean.getName();
        String filename = excelForm.getDocFile().getFileName();

        boolean uploadStatus = glDao.docUploadForExcel(request, excelForm.getDocFile());

        logger.info("File copied to server ========" + uploadStatus);

        String filePathWithName = request.getAttribute("filePathWithName").toString();
        String ktrpath = String.valueOf(request.getAttribute("ktrpath"));

        if (uploadStatus)
        {
          int batchid = glDao.saveUploadSummary(upload_type, businessDate, userId, filename);

          GenericUploadParametersDTO dto = new GenericUploadParametersDTO();

          dto.setBatch_id(String.valueOf(batchid));
          dto.setUser_id(userId);
          dto.setExcelfilepath(filePathWithName);
          dto.setSmooksfilepath(xmlbean.getSmooksfiles());

          dto.setCompany_id(String.valueOf(compid));
          dto.setBusiness_date(businessDate);
          dto.setBranch_id(branchId);
          dto.setFilename(filename);
          dto.setFunctionid(functionId);
          dto.setMaker_proc_name(xmlbean.getMakerproc());
          dto.setAuthor_proc_name(xmlbean.getAuthorproc());
          dto.setTame_table_name(xmlbean.getTemp_table_name());

          boolean flag = sendMessageToGenericUploadMakerQueue(dto);
          if (flag)
          {
            request.setAttribute("batchid", Integer.valueOf(batchid));
            request.setAttribute("uploadStatus", "success");
          }
          else
          {
            request.setAttribute("uploadStatus", "error");
          }
        }
        else
        {
          request.setAttribute("uploadStatus", "error");
        }

        ArrayList list = glDao.searchData(userId, xmlbean);
        request.setAttribute("searchList", list);
        request.setAttribute("size", Integer.valueOf(list.size()));
        request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
        request.setAttribute("lovid", xmlbean.getLov_id());
      }

      if (actionName.equalsIgnoreCase("delete"))
      {
        XMLBean xmlbean = readXML(functionId);

        String batch_id = request.getParameter("batch_id");
        logger.info("inside delete---------------batch_id ===" + batch_id);

        int result = glDao.deleteBatch(batch_id, userId, xmlbean);
        if (result > 0)
        {
          logger.info("batch " + batch_id + " Deleted Successfully.....");
          request.setAttribute("output", "Batch " + batch_id + " deleted successfully");
        }
        else
        {
          logger.info("batch " + batch_id + " Cant Be Deleted.....");
          request.setAttribute("output", "Batch " + batch_id + " can't be deleted");
        }

        ArrayList list = glDao.searchData(userId, xmlbean);
        request.setAttribute("searchList", list);
        request.setAttribute("size", Integer.valueOf(list.size()));
        request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
        request.setAttribute("lovid", xmlbean.getLov_id());
      }

      if (actionName.equalsIgnoreCase("forward"))
      {
        XMLBean xmlbean = readXML(functionId);

        String batch_id = request.getParameter("batch_id");
        logger.info("inside forward---------------batch_id ===" + batch_id);

        int result = glDao.forwardBatch(batch_id, userId);
        if (result > 0)
        {
          logger.info("Batch " + batch_id + " Forwarded Successfully.....");
          request.setAttribute("output", "Batch " + batch_id + " forwarded successfully");
        }
        else
        {
          logger.info("Batch " + batch_id + " Can't Be Forwarded.....");
          request.setAttribute("output", "Batch " + batch_id + " can't be forwarded");
        }

        ArrayList list = glDao.searchData(userId, xmlbean);
        request.setAttribute("searchList", list);
        request.setAttribute("size", Integer.valueOf(list.size()));
        request.setAttribute("jsfile", xmlbean.getJavascriptfilename());
        request.setAttribute("lovid", xmlbean.getLov_id());
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

      if (actionName.equalsIgnoreCase("downloadtemplate"))
      {
        XMLBean xmlbean = readXML(functionId);

        logger.info("inside downloaddump..");
        String batch = request.getParameter("batch");
        logger.info("batch === " + batch);

        ArrayList list = glDao.downloadtemplate(batch, xmlbean);
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
          response.setHeader("Content-Disposition", "attachment; filename=ACHTemplate.csv");
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

      if (actionName.equalsIgnoreCase("downloadFormat"))
      {
        XMLBean xmlbean = readXML(functionId);
        logger.info(functionId);
        logger.info("inside downloadFormat..");
        String batch = request.getParameter("batch");
        logger.info("batch === " + batch);

        ArrayList list = glDao.downloadFormat(xmlbean, functionId);
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
          response.setHeader("Content-Disposition", "attachment; filename=Uploadformat.csv");
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
    }

    excelForm.reset(mapping, request);
    return mapping.findForward("sucesss");
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
    logger.info("Inside readXML");

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

      String name = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/name");
      String smooksfiles = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/smooksfiles");
      String smookscsvbean = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/smooks-csv-bean");
      String javascriptfilename = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/java-script-file-name");
      String autoforwardtoauthor = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/auto-forward-to-author");
      String autoauthor = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/auto-author");
      String autoauthoruserid = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/auto-author-userid");
      String temp_table_name = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/temp-table-name");
      String temp_table_dump_query = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/temp-table-dump-query");
      String main_table_dump_query = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/main-table-dump-query");
      String lov_id = xmlConfig.getString("all-uploads/upload[maker-function-id = '" + function_id + "']/lov-id");

      logger.info("name=" + name);
      logger.info("smooksfiles=" + smooksfiles);
      logger.info("smookscsvbean=" + smookscsvbean);
      logger.info("javascriptfilename=" + javascriptfilename);
      logger.info("autoforwardtoauthor=" + autoforwardtoauthor);
      logger.info("authoauthor=" + autoauthor);
      logger.info("autoauthoruserid=" + autoauthoruserid);
      logger.info("temp_table_name=" + temp_table_name);
      logger.info("temp_table_dump_query==" + temp_table_dump_query);
      logger.info("main_table_dump_query==" + main_table_dump_query);
      logger.info("lov_id==" + lov_id);

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
      bean.setTemp_table_dump_query(temp_table_dump_query);
      bean.setMain_table_dump_query(main_table_dump_query);
      bean.setLov_id(lov_id);

      return bean;
    }
    catch (ConfigurationException e)
    {
      e.printStackTrace();
      logger.info(e);
    }

    return null;
  }
}