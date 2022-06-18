package com.cm.actions;

import java.io.IOException;
import java.io.PrintWriter;
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

//import com.business.PoolBussiness.PoolBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.actionform.CMUploadForm;
import com.GL.business.ExcelSheetUploadBusiness;
import com.cm.dao.PoolIDDAO;

public class PoolUploadKtrAction  extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PoolUploadKtrAction.class.getName());	

	 ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	 
	 String dbType=resource.getString("lbl.dbType");
	
public ActionForward uploadFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
{
	logger.info("Inside uploadFile");
	HttpSession session = request.getSession();

	UserObject userobj=(UserObject)session.getAttribute("userobject");
	boolean uploadStatus=false;	
    boolean flag=false;
    int compid =0;
    String msg= null;		

	String branchId="";
	String businessDate="";
	String userId ="";

	CMUploadForm excelForm = (CMUploadForm) form;
	ExcelSheetUploadBusiness uploadObj =null;
	uploadObj = new ExcelSheetUploadBusiness();
	
	if(userobj==null){
		logger.info("in uploadfile method of  PoolUploadKtrAction action the session is out----------------");
		return mapping.findForward("sessionOut");
	}
	Object sessionId = session.getAttribute("sessionID");
	//for check User session start
	ServletContext context = getServlet().getServletContext();
	String strFlag="";	
	if(sessionId!=null )
		strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
	
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
	
	session.removeAttribute("Processfile");
	
   
    if(userobj!=null){
 		businessDate=userobj.getBusinessdate();
		compid=userobj.getCompanyId();
		branchId=userobj.getBranchId();
		userId = userobj.getUserId();
	}
    excelForm.setCompanyId(compid);
	excelForm.setMakerId(userId);
	excelForm.setBranchId(branchId);
	excelForm.setBusinessDate(businessDate);
	excelForm.setSessionId(sessionId.toString());
	
	uploadStatus=uploadObj.docUploadForExcel(request,(FormFile)excelForm.getDocFile());
				
		String filePathWithName=request.getAttribute("filePathWithName").toString();
		
		if(uploadStatus){			
			 excelForm.setFilePathWithName(filePathWithName);
			  flag=uploadObj.uploadCsv_PoolProcess( request,response,excelForm.getDocFile().getFileName(),excelForm);	
			  logger.info("flag-----------------------"+flag);
		}
		
		if(!flag){
			  request.setAttribute("fieldUpdate", "Some problem in sheet format..");
		}else{
			 
			 msg=uploadObj.savePoolProcess(request,excelForm);
			 logger.info("msg after save in action:--- "+msg);
			 			 
			  if(CommonFunction.checkNull(msg).equalsIgnoreCase("S")){
				  request.setAttribute("errorMsg", "Data Upload Sucessfully.");
			  }else{
				  request.setAttribute("errorMsg", "Some problem in the Sheet.");
			  }
		}	 
		
		if("S".equalsIgnoreCase(msg)){
			 request.setAttribute("sms","");
		}else if ("N".equalsIgnoreCase(msg)) {
			request.setAttribute("srj","");
		}else{			
			 request.setAttribute("smsno","");
		}
		
		//PoolBussinessSessionBeanRemote dao=(PoolBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PoolBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		PoolIDDAO dao=(PoolIDDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolIDDAO.IDENTITY);
		String poolNo = dao.getPoolNo();				
		
		if(poolNo.equalsIgnoreCase("")){
			int p = 1;
			request.setAttribute("poolNo", p);
		}else{
			int p = Integer.parseInt(poolNo);
		    p = p + 1;
		    logger.info("^^^^^^"+p);
		    request.setAttribute("poolNo", p);
		}
		request.setAttribute("poolIdMaker", "poolIdMaker");
		request.setAttribute("forNew","");
		excelForm.reset(mapping, request);
		
	return mapping.findForward("success");
}

public String openExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws IOException {
	logger.info("checking................................................");
	ExcelSheetUploadBusiness uploadObj =null;
	uploadObj = new ExcelSheetUploadBusiness();
	CMUploadForm excelForm = (CMUploadForm) form;

	ArrayList list=uploadObj.outfilequery(excelForm);
	logger.info("Report Size  :  "+list.size());
	int size=list.size();
	if(size==0)
	{
		request.setAttribute("error","error");
	}
	else
	{
		StringBuffer fileNameFormat = new StringBuffer();
		PrintWriter out = response.getWriter();
		response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-Disposition", "attachment; filename="+"UploadOutputDump.csv");
		ArrayList subList = new ArrayList();
			try{
				for (int i=0;i<size;i++){
					subList = (ArrayList) list.get(i);
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
			logger.error(e.getMessage().toString());
		}finally{
			subList.clear();
			list.clear();
			fileNameFormat.setLength(0);
			fileNameFormat=null;
			out.flush();
			out.close();
		}
		return null;
	}
	return null;
}
	
}
