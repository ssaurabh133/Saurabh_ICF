package com.cm.actions;

import com.cm.dao.ReportsDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.UploadDocument;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.DynaActionForm;

public class NselReportDownLoadAction extends Action
{
  private static final Logger logger = Logger.getLogger(NselReportDownLoadAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.info("In NselReportDownLoadAction");
    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");

    String cibilResult = null;
    String FileName1  = null;
    ArrayList resultList = new ArrayList();
    try
    {
      HttpSession session = request.getSession();

      UserObject userobj = (UserObject)session.getAttribute("userobject");
      String businessDate = "";
      if (userobj != null) {
        businessDate = userobj.getBusinessdate();
      } else {
        logger.info(" in execute method of NselReportDownLoadAction  action the session is out----------------");
        return mapping.findForward("sessionOut");
      }
      Object sessionId = session.getAttribute("sessionID");

      ServletContext context = getServlet().getServletContext();
      String strFlag = "";

      if (sessionId != null) {
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

      Calendar currentDate = Calendar.getInstance();
      SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
      String currantDate = formatter.format(currentDate.getTime());
      String dbType = resource.getString("lbl.dbType");
      String userId = userobj.getUserId();

      businessDate = businessDate.substring(0, 2) + businessDate.substring(3, 5) + businessDate.substring(6);
      DynaActionForm daf = (DynaActionForm)form;
      String fromDate = (String)daf.get("fromDate");
      String toDate = (String)daf.get("toDate");
      String cutOffDate = (String)daf.get("cutOffDate");
      String fromDateDB = CommonFunction.changeFormat(fromDate);
      String toDateDB = CommonFunction.changeFormat(toDate);
      String reportName = (String)daf.get("report");

      ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance("REPORTD");
      logger.info("Implementation class: " + dao.getClass());
      if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("NeSL_DOWNLOAD_BIL_LAP"))
      {
         resultList = dao.NeslFileGenerator(fromDate, toDate);
        logger.info("Respone of proc got on Action::: " + resultList.size());
        logger.info("Respone of proc got on Action::: " + resultList);
         FileName1 = "NeSL_DOWNLOAD_BIL_LAP";
      }
      else
      {
    	   resultList = dao.NeslLCFileGenerator(fromDate, toDate);
          logger.info("Respone of proc got on Action::: " + resultList.size());
          logger.info("Respone of proc got on Action::: " + resultList);
           FileName1 = "NeSL_DOWNLOAD_LC";
      }

      
      int size=resultList.size();
		logger.info("CSV FILE GENERATION STARTS...");
		logger.info("File name " + FileName1);
		String fileName=FileName1+".csv";
		StringBuffer fileNameFormat = new StringBuffer();
		response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		PrintWriter out = response.getWriter();
		ArrayList subList = new ArrayList();
		try{
			for (int i=0;i<size;i++){
				subList = (ArrayList) resultList.get(i);
				int subSize=subList.size();
					for(int j=0;j<subSize;j++){
						fileNameFormat.append('"');
						fileNameFormat.append(CommonFunction.checkNull(subList.get(j)));
						fileNameFormat.append('"');
						fileNameFormat.append(',');
					}
					fileNameFormat.append("\n");
			}
		out.write(fileNameFormat.toString());
		}catch(Exception e){
			logger.info("error is:--"+e);
		}finally{
		 subList.clear();
		 resultList.clear();
		 fileName=null;
		 fileNameFormat.setLength(0);
		 fileNameFormat=null;
		 out.flush();
		 out.close();
		 userId=null;
		 strFlag=null;
		}
		
	
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (cibilResult != null)
    {
      return mapping.findForward("error");
    }

    return null;
  }

}