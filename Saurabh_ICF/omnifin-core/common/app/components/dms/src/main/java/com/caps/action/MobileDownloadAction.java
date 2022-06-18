package com.caps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cm.dao.ReportsDAO;
import com.cm.daoImplMYSQL.ReportsDAOImpl;
import com.connect.CommonFunction;
import com.login.roleManager.UserObject;



public class MobileDownloadAction extends Action{
	private static final Logger logger = Logger.getLogger(MobileDownloadAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				String businessDate="";
				if(userobj==null){
					logger.info("here execute method of MobileDownloadAction action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				else
				{
					businessDate=userobj.getBusinessdate();
					userId=userobj.getUserId();
				}
						
				logger.info("In MobileDownloadAction");
			
				try{
					
					ReportsDAO dao = new ReportsDAOImpl();
				/*	select parameter_value from parameter_mst where parameter_key = 'Mobile_Download_Path'*/
					String camTemplatePath   = dao.getcamTemplatePath();
					String readFile = camTemplatePath;// + "/"+"OmniFinDMSAndroid"+".apk";

				logger.info("readFile---------" + readFile);

				String strHesderFileName = readFile.substring(readFile.lastIndexOf("/")+1);
				
				/*ArrayList outMessage = dao.saveTargetValuesTmp(userobj.getBusinessdate(), userobj.getUserId());
				logger.info("s1---" + CommonFunction.checkNull(outMessage.get(0))
						+ "---s2---" + CommonFunction.checkNull(outMessage.get(1)));*/
				/*if (CommonFunction.checkNull(outMessage.get(0)).equalsIgnoreCase("S"))
					new PushDataInTargetSheet().pushDataInTargetSheet(readFile,caseId);
*/
				File file = new File(readFile);

				response.setContentType("application/vnd.android.package-archive");

				response.addHeader("Content-Disposition", "attachment; filename="+strHesderFileName);

				response.setContentLength((int) file.length());

				FileInputStream fileInputStream = new FileInputStream(file);

				OutputStream responseOutputStream = response.getOutputStream();

				int bytes;

				while ((bytes = fileInputStream.read()) != -1) {

					responseOutputStream.write(bytes);
				}
				fileInputStream.close();

				responseOutputStream.close();
			}
				
		catch (Exception e)
			{
				e.printStackTrace();
			}
						    return null;
			
			}
}
