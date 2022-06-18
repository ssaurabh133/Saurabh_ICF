package com.cm.actions;

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

import com.cm.actionform.CreditMgmtExcelSheetUploadVoForm;
import com.cm.actions.UploadDocuments;
import com.cm.business.CreditMgmtExcelSheetUploadBusiness;
import com.connect.CommonFunction;
import com.login.roleManager.UserObject;

public class CreditMgmtExcelSheetUploadAction <E> extends DispatchAction
{
	 private static final Logger logger = Logger.getLogger( CreditMgmtExcelSheetUploadAction.class.getName());
	 ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info("in ExcelSheetUploadingAction IN CM---------------");
		CreditMgmtExcelSheetUploadVoForm  excelForm = (CreditMgmtExcelSheetUploadVoForm ) form;
		String dbType=resource.getString("lbl.dbType");
		CreditMgmtExcelSheetUploadBusiness uploadObj =null;
		uploadObj = new CreditMgmtExcelSheetUploadBusiness();
		String actionName = null;
		
		actionName = excelForm.getActionName();
		logger.info("actionName:------"+actionName);
		HttpSession session=request.getSession(false);
		UserObject sessUser = (UserObject) session.getAttribute("userobject");
		int compid =0;
		//String cName ="";
		String branchId="";
	//	String branchName="";
		String businessDate="";
		String userId ="";
		String sessionID="";
		String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
		if(sessUser!=null){
			businessDate=sessUser.getBusinessdate();
			compid=sessUser.getCompanyId();
		//	cName=sessUser.getConpanyName();
			branchId=sessUser.getBranchId();
		//	branchName=sessUser.getBranchName();
			userId = sessUser.getUserId();
			sessionID=session.getId();
		}
		excelForm.setCompanyId(compid);
		excelForm.setSessionId(sessionID);
		excelForm.setMakerId(userId);
		excelForm.setBranchId(branchId);
		excelForm.setBusinessDate(businessDate);
		//add by Ashish
		if(functionId.equalsIgnoreCase("10000606")) {
			 request.setAttribute("bounceRealization", "Stationary Upload");
		 }
		else if(functionId.equalsIgnoreCase("10000817")) {
			 request.setAttribute("bounceRealization", "Recovery Executive Allocation Change");
		 }
		 else if(functionId.equalsIgnoreCase("10000691")) {
			 request.setAttribute("bounceRealization", "CMS UPLOAD");
		 }
		 else if(functionId.equalsIgnoreCase("10000625")) {
			 request.setAttribute("bounceRealization", "Allocation UPLOAD");
		 }
		/*else if(functionId.equalsIgnoreCase("10000602")){
			 request.setAttribute("bounceRealization", "Bounce/Realization Upload");
		}*/
		// end by Ashish
		if (actionName != null) 
		{
			logger.info("action value-::-" + actionName);
			
		
			if (actionName.equalsIgnoreCase("saveExcel"))
			{
				logger.info("import Action in ExcelSheetUploading------");
				
				boolean uploadStatus=UploadDocuments.docUpload(request,excelForm.getDocFile());
				boolean  flag=false;
				
				logger.info("ExcelSheetUploading.getDocFile().getFileName()====="+excelForm.getDocFile().getFileName());
				logger.info("uploadStatus :==>>"+uploadStatus);
				
				if(uploadStatus){
					  logger.info("uploadStatus-----------------------"+uploadStatus);
					  flag=uploadObj.uploadExcel( request,response,excelForm.getDocFile().getFileName(),excelForm);	
					  logger.info("flag-----------------------"+flag);
				}
				if(!flag){
					  	String dateCheck=(String) request.getAttribute("dateCheck");

					  	if(CommonFunction.checkNull(dateCheck).equalsIgnoreCase("")){
							request.setAttribute("fieldUpdate", "Some problem in excel sheet format..");
						}else{
							request.setAttribute("fieldUpdate", dateCheck);
						}
				}else{
					 String msg=uploadObj.saveVoucher(request,excelForm);
					 logger.info("msg after voucher save in excel sheet action:---"+msg);
					  if(CommonFunction.checkNull(msg).equalsIgnoreCase("S")){
						  request.setAttribute("errorMsg", "Voucher Upload Sucessfully");
					  }else{
						  //String erorMsg=(String) request.getAttribute("errorMsg");
						  request.setAttribute("errorMsg", "Some problem in ExcelSheet");
					  }
				}
			}
			
			//space start by raj
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
		          logger.info("filePathWithName:::::"+filePathWithName);

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
		            /*else if (functionId.equalsIgnoreCase("10000604"))
		            {
		              logger.info("Action for Manual Advice Upload.... " + functionId);
		              msg = uploadObj.saveManual(request, excelForm);
		            }*/
		            /*else if (functionId.equalsIgnoreCase("10000603"))
		            {
		              logger.info("Action for Rate Review Upload.... " + functionId);
		              msg = uploadObj.saveRate(request, excelForm);
		            }
		            else if (functionId.equalsIgnoreCase("10000605"))
		            {
		              logger.info("Action for NHB PART PRE PAYMENT.... " + functionId);
		              msg = uploadObj.saveNHB(request, excelForm);
		            }*/
		            else if(functionId.equalsIgnoreCase("10000817")) {
						 msg=uploadObj.recoveryUploadSave(request,excelForm); //request.setAttribute("bounceRealization", "Recovery Executive Upload");
					 }
		            else if (functionId.equalsIgnoreCase("10000606"))
		            {
		              logger.info("Action for STATIONARY Upload.... " + functionId);
		              msg = uploadObj.saveStationary(request, excelForm);
		            }/*
		            else if (functionId.equalsIgnoreCase("10000609"))
		            {
		              logger.info("Action for BANK STATEMENT.... " + functionId);
		              msg = uploadObj.saveBankStmt(request, excelForm);
		            }
		            else if (functionId.equalsIgnoreCase("10000620"))
		            {
		              logger.info("Action for SECTOR PURPOSE.... " + functionId);
		              msg = uploadObj.saveSectorPurpose(request, excelForm);
		            }
		            else if (functionId.equalsIgnoreCase("10000621"))
		            {
		              logger.info("Action for NOTEPAD UPLOAD.... " + functionId);
		              msg = uploadObj.save_NotepadUpload(request, excelForm);
		            }
		            else if (functionId.equalsIgnoreCase("10000691"))
		            {
		              logger.info("Action for CMS UPLOAD.... " + functionId);
		              msg = uploadObj.save_CmsUpload(request, excelForm);
		            }
		            else if (functionId.equalsIgnoreCase("10000622")) {
		              msg = uploadObj.recoveryUploadSave(request, excelForm);
		            }*/
		            else if (functionId.equalsIgnoreCase("10000625")) {
		              msg = uploadObj.AllocationProcessUploadSave(request, excelForm);
		            }
		            else if(functionId.equalsIgnoreCase("10000691")) 
					{
						logger.info("Action for CMS UPLOAD.... "+functionId);
						msg=uploadObj.save_CmsUpload(request,excelForm);
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
			//space end by raj
			//comment start by raj
			/*
			if (actionName.equalsIgnoreCase("saveCsv"))
			{
				logger.info("import Action in CSvSheetUploading------");
				
				boolean uploadStatus=uploadObj.docUploadForExcel(request,excelForm.getDocFile());
			//	String filePathWithName=request.getAttribute("filePathWithName").toString();
				boolean  flag=false;
				if(uploadStatus){
					  logger.info("uploadStatus-----------------------"+uploadStatus);
					//  excelForm.setFilePathWithName(filePathWithName);
					  flag=uploadObj.uploadCsv( request,response,excelForm.getDocFile().getFileName(),excelForm);	
					  logger.info("flag-----------------------"+flag);
				}
				if(!flag){
					  request.setAttribute("fieldUpdate", "Some problem in excel sheet format..");
				}
				else
				{
					String msg= null;
					//add by Ashish
					if(functionId.equalsIgnoreCase("10000606"))
					{
						logger.info("Action for STATIONARY Upload.... "+functionId);
						msg=uploadObj.saveStationary(request,excelForm);
					}
					else if(functionId.equalsIgnoreCase("10000817")) {
						 msg=uploadObj.recoveryUploadSave(request,excelForm); //request.setAttribute("bounceRealization", "Recovery Executive Upload");
					 }
					else if(functionId.equalsIgnoreCase("10000602"))
					{
						logger.info("Action for Boucne Realization.... "+functionId);
						msg=uploadObj.saveBounce(request,excelForm);
					}
					// end by Ashish
					else
					{
						msg=uploadObj.saveVoucher(request,excelForm);
					}
					 logger.info("msg after voucher save in excel sheet action:---"+msg);
					  if(CommonFunction.checkNull(msg).equalsIgnoreCase("S")){
						   if(functionId.equalsIgnoreCase("10000602"))
							{
						  request.setAttribute("errorMsg", "Data Uploaded Sucessfully");
							}
						   else
						   {
							   request.setAttribute("errorMsg", "Voucher Upload Sucessfully");
						   }
					  }else{
						  //String erorMsg=(String) request.getAttribute("errorMsg");
						  request.setAttribute("errorMsg", "Some problem in ExcelSheet");
					  }
				}
			}*/
			
			//comment end by raj 
			
			if(actionName.equalsIgnoreCase("openExcel")){
				ArrayList list=uploadObj.reportAdHoc(excelForm,functionId);
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
					response.setHeader("Content-Disposition", "attachment; filename=VoucherDump.csv");
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
						
			}
	      }
		
		if(CommonFunction.checkNull(dbType).equalsIgnoreCase("MSSQL"))
			request.setAttribute("show", "show");
		
		
		excelForm.reset(mapping, request);
		return mapping.findForward("sucesss");
		
	}

}
