package com.scz.actions;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.scz.dao.BuyBackUploadDAO;
import com.scz.daoHibImpl.BuyBackUploadHibImplService;
import com.scz.vo.BuyBackUploadVO;

public class BuyBackProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(BuyBackProcessAction.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	static String dbType=resource.getString("lbl.dbType");

	 public ActionForward uploadCSVForBuyBack(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
		 logger.info("In FutureFlowBehindAction   ");
		 HttpSession session = request.getSession();
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			
			boolean status=false;			
			boolean uploadStatus=false;	
			String userID =null;
			String businessDate =null;
			String strFlag=null;
			String flag=null;
			String filePathWithName=null;
			int currentPageLink = 0;
			
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages =new ArrayList();
			ArrayList mainList =new ArrayList();
			String s1="";
			String s2="";
			
			if(userobj!=null){
				userID = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
			}else{
				logger.info(" in submitPoolIdUpload method of PoolIdMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
		    ServletContext context = getServlet().getServletContext();
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			logger.info("strFlag .............. "+strFlag);
			if(!strFlag.equalsIgnoreCase(""))
			{
				if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}
				return mapping.findForward("logout");
			}

			logger.info("current page link .......... "+request.getParameter("d-4008017-p"));
			
			if(request.getParameter("d-4008017-p")==null || request.getParameter("d-4008017-p").equalsIgnoreCase("0"))
			{
				currentPageLink=1;
			}
			else
			{
				currentPageLink =Integer.parseInt(request.getParameter("d-4008017-p"));
			}
			logger.info("current page link ................ "+request.getParameter("d-4008017-p"));
			 
			BuyBackUploadVO buyBackVO = new BuyBackUploadVO();
		   DynaValidatorForm BuyBackProcessActionForm=(DynaValidatorForm)form;	
		   org.apache.commons.beanutils.BeanUtils.copyProperties(buyBackVO, BuyBackProcessActionForm);

		   BuyBackUploadDAO dao=new BuyBackUploadHibImplService();
		   
		   buyBackVO.setMakerID(userID);
		   buyBackVO.setMakerDate(businessDate);
		   buyBackVO.setCurrentPageLink(currentPageLink);
			logger.info("Implementation class: "+dao.getClass());
		   
			if(!CommonFunction.checkNull(buyBackVO.getDocFile()).equalsIgnoreCase(""))
			{	    	
				// Getting the file path....
				uploadStatus=dao.getFilePath(request,(FormFile)buyBackVO.getDocFile());
				
				filePathWithName=request.getAttribute("filePathWithName").toString();
				buyBackVO.setFileName(request.getAttribute("fileName").toString());
				buyBackVO.setDocPath(request.getAttribute("filePath").toString());
				buyBackVO.setFilePathWithName(filePathWithName);
				
				flag=(String)request.getAttribute("message");
				logger.info("flag .. "+flag);
				
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("O"))
				    request.setAttribute("sms","");
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("E"))
					request.setAttribute("smsno","");

				logger.info("uploadStatus .. "+uploadStatus);
					if(uploadStatus){	
						status=dao.uploadCsvForBuyBack(request,response,buyBackVO.getDocFile().getFileName(),buyBackVO);	
						logger.info("status-----------------------"+status);
					}
					if(status){
						in.add(userID);
						out.add(s1);
						out.add(s2);
						logger.info("month  ...."+in +"    "+out);
						try
						{
							outMessages=(ArrayList) ConnectionReportDumpsDAO.callSPForHiberNate("SCZ_BUYBACK_CASES",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("After calling SCZ_BUYBACK_CASES Procedure S1 and S2 "+s1+" - "+s2);
							
							if(s1.equalsIgnoreCase("S")){
								request.setAttribute("msg", "Data Upload Saved Successfully");
							}
						}catch (Exception e) {
							logger.info("An Error..."+e);
						}
						finally{
							
						}
					}
					
			}
			return mapping.findForward("success");
	 }
}
