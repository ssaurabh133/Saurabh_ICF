/**
 *    Developed By-      Sanjog
      Date of creation-  24/08/2011
      Purpose-           Lead Details     
 */

package com.cp.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.business.CPClient.LeadProcessingRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class LeadDetailBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(LeadDetailBehindAction.class.getName());


	public ActionForward execute(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)
	
		throws Exception{
		logger.info("In LeadDetailBehindAction(leadDetails)");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId="";
		String userName="";
		String branchId="";
		String branchName="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				userName=userobj.getUserName();
				branchId=userobj.getBranchId();
				branchName=userobj.getBranchName();
		}else{
			logger.info("here in execute method of LeadDetailBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();
		
		boolean flag ;
		
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
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

		logger.info("In LeadDetailBehindAction(leadDetails)");
		String leadId = CommonFunction.checkNull(request.getParameter("leadId"));
		logger.info("In LeadDetailBehindAction...... leadId "+ leadId);
		
		String FromDealCap = CommonFunction.checkNull(request.getParameter("FromDealCap"));
		logger.info("In LeadDetailBehindAction(leadDetails)............. "+ FromDealCap);
	
		
					//--------------Record Locking-----------------
					String leadno = request.getParameter("leadId");
					logger.info("In LeadDetailBehindAction.......leadId. " + leadId);
					
					logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
					String functionId="";
					int funId = 0;
				
					
					if(session.getAttribute("functionId")!=null)
					{
						functionId=session.getAttribute("functionId").toString();
						funId = Integer.parseInt(functionId);
					}
					
					
					//ServletContext context=getServlet().getServletContext();
					//flag = LockRecordCheck.lockCheck(userId,functionId,leadno,context);
					if(funId==3000106)
					{
						session.setAttribute("userNameForProAtLe", userId);
					}
					if (funId ==3000952)
					{
						flag = true;
					}
					else
					{
						flag = LockRecordCheck.lockCheck(userId,functionId,leadno,context);
					}
					logger.info("Flag ........................................ "+flag);
										

					logger.info("session.getAttribute('allocationid')............."+session.getAttribute("allocationid"));
			
					session.setAttribute("leadTrackNote", leadId);
										
					int a=0;
					a=Integer.parseInt((String)session.getAttribute("functionId"));
					if(a==3000952)
						a=0;
					//commented by neeraj
//					if(session.getAttribute("leadpageid")!=null)
//					{
//						String attr=session.getAttribute("leadpageid").toString();
//						a=Integer.parseInt(attr);
//					}

					 LeadProcessingRemote lp = (LeadProcessingRemote) LookUpInstanceFactory.getLookUpInstance(LeadProcessingRemote.REMOTE_IDENTITY, request);
					
					CreditProcessingLeadDetailDataVo leadIdVo = new CreditProcessingLeadDetailDataVo();
					session.removeAttribute("LeadViewMode");
					session.removeAttribute("sourceList");
					
					
//Sanjog Changes End Here
					String val="";
					String source ="";
					ArrayList getSourceDetailList = lp.getSourceDetailList(source);
					ArrayList defaultcountry =lp.defaultcountry();
					 ArrayList  addresstypeList=lp.addresstype();
						ArrayList<Object> constitutionlist=lp.getContitutionList();
						ArrayList<Object> corconstitutionlist=lp.getCorContitutionList();
						ArrayList<Object> indconstitutionlist=lp.getIndContitutionList();
						ArrayList<Object> businessSegmentList = lp.getBusinessSegmentList();
						ArrayList<Object> eduDetail = lp.getEduDetailList();
						session.setAttribute("addresstypeList", addresstypeList);
					session.setAttribute("businessSegmentList",businessSegmentList);
					session.setAttribute("constitutionlist",constitutionlist);
					session.setAttribute("indconstitutionlist",indconstitutionlist);
					session.setAttribute("corconstitutionlist",corconstitutionlist);
					if(leadId.equalsIgnoreCase("NEW")){
						logger.info("NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW NEW");
						session.removeAttribute("leadRMDetails");
						session.removeAttribute("leadOther");
						session.removeAttribute("leadDetails");
						session.removeAttribute("trackDetails");
						leadIdVo.setBranchCode1(branchId);
						leadIdVo.setBranchName1(branchName);
						ArrayList<Object> userDetails = lp.existingUser(leadIdVo,userId);
						session.setAttribute("sourceList", getSourceDetailList);
						if(userDetails.size()>0){
						session.setAttribute("leadRMDetails", userDetails);
						}
						
						session.setAttribute("leadNew", userDetails);
						request.setAttribute("NEW","NEW");
						session.setAttribute("genNewLead", userId);
						//request.setAttribute("userName", userName);
						request.setAttribute("defaultcountry", defaultcountry);
						request.setAttribute("eduDetail", eduDetail);
						//request.setAttribute("saveLead", leadDetails);
					}else{
						if(!flag)
						{
							logger.info("Record is Locked");			
							request.setAttribute("sms", "Locked");
							session.setAttribute("sourceList", getSourceDetailList);
							request.setAttribute("recordId", leadId);
							session.setAttribute("trackId", leadId);
							request.setAttribute("userId", userId);
							return mapping.findForward("locked");
						}
						request.removeAttribute("NEW");
						session.removeAttribute("leadNew");
						session.removeAttribute("leadRMDetails");
						
//						DynaValidatorForm LeadCapturingDynaValidatorForm = (DynaValidatorForm) form;
//						org.apache.commons.beanutils.BeanUtils.copyProperties(leadIdVo, LeadCapturingDynaValidatorForm);
						CreditProcessingLeadDetailDataVo leadIdVo1=new CreditProcessingLeadDetailDataVo();					
						ArrayList leadDetails = lp.getLeadCapturingDetailsList(leadId);
						
						if(leadDetails!=null && leadDetails.size()>0)
						{
							leadIdVo1 =  (CreditProcessingLeadDetailDataVo) leadDetails.get(0);
							session.setAttribute("leadDetails", leadDetails);						
							request.setAttribute("saveLead", leadDetails);
						}
					
						
						logger.info("leadIdVo1 *********************************** "+leadIdVo1.getLeadGenerator());
						
						logger.info("other other other other other other other other other other other other other other");
						request.setAttribute("eduDetail", eduDetail);
						session.setAttribute("sourceList", getSourceDetailList);
						ArrayList<Object> leadRMDetails= lp.getRmDetail(leadId);
						session.setAttribute("leadRMDetails", leadRMDetails);
						session.setAttribute("leadOther", leadRMDetails);
						ArrayList<Object> trackDetails = lp.getTrackingDetail(leadId);
						session.setAttribute("trackDetails", trackDetails);						
						
						ArrayList<Object> getAllocationDetail = lp.getAllocationDetail(leadId);
						
						
						logger.info("We are in Lead Allocation phase..."+leadIdVo.getLeadGenerator()+"..."+leadIdVo.getLeadId());
						
						if(a ==3000111){
							logger.info("We are in Lead Allocation phase..."+leadIdVo1.getLeadGenerator());
							if(CommonFunction.checkNull(leadIdVo1.getLeadGenerator()).equalsIgnoreCase("BRANCH")){
								//session.setAttribute("getLoanType", getLoanTypeList);
								session.removeAttribute("tracking");
								session.removeAttribute("alocation");
								session.setAttribute("alocationBranch", "alocationBranch");
								request.setAttribute("getAlloDetail", getAllocationDetail);
							}else{
								session.removeAttribute("getLoanType");
								session.removeAttribute("tracking");
								session.removeAttribute("alocationBranch");
								session.setAttribute("alocation", "alocation");
								request.setAttribute("getAlloDetail", getAllocationDetail);
							}
						}else if(a == 3000116){
							logger.info("We are in Lead Tracking phase");
							session.removeAttribute("alocation");
							session.removeAttribute("alocationBranch");
							session.setAttribute("tracking", "tracking");
							request.setAttribute("getAlloDetail", getAllocationDetail);
						}
					}
					
					if(FromDealCap.equalsIgnoreCase("V"))
					{
						a=0;
						session.removeAttribute("leadpageid");
						logger.info("for  "+leadId+" value of a"+a);
						session.removeAttribute("leadTrackNote");
						session.removeAttribute("genNewLead");
						session.removeAttribute("leadNew");
						session.removeAttribute("leadOther");
						session.removeAttribute("leadDetails");
						ArrayList<CreditProcessingLeadDetailDataVo> leadDetails = lp.getLeadCapturingDetailsList(leadId);
						session.setAttribute("leadDetails", leadDetails);
						ArrayList<Object> leadRMDetails= lp.getRmDetail(leadId);
						request.setAttribute("leadRMDetails", leadRMDetails);
						request.setAttribute("leadOther", leadRMDetails);
						ArrayList<Object> getAllocationDetail = lp.getAllocationDetail(leadId);
						session.removeAttribute("alocation");
						session.removeAttribute("alocationBranch");
						request.setAttribute("FromDealCap", "V");
						request.setAttribute("tracking", "tracking");
						request.setAttribute("getAlloDetail", getAllocationDetail);
					}
					
					logger.info("session.getAttribute('leadTrackNote')............."+session.getAttribute("leadTrackNote"));

//Sanjog Changes Start Here					
					String statusForleadView = CommonFunction.checkNull(request.getParameter("LeadViewMode"));
					
					logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$------"+statusForleadView);
					if(statusForleadView.equalsIgnoreCase(null) || statusForleadView.equalsIgnoreCase("LeadViewMode")){
						logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$------inside if condition"+statusForleadView);
						session.removeAttribute("alocation");
						session.removeAttribute("tracking");
						session.setAttribute("LeadViewMode", "LeadViewMode");
					}else{
						logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$------inside else condition"+statusForleadView);
						//session.removeAttribute("LeadViewMode");			
					}
					ArrayList getLoanTypeList = lp.getLoanTypeList();
					ArrayList sectorList = lp.getSectorList();
					session.setAttribute("getLoanType", getLoanTypeList);
					session.setAttribute("sector",sectorList);
					
//Sanjog Changes End Here
					if(a == 3000106){
						//Nishant Space starts
						String emailFlag = lp.getEmailMandatoryFlag();
						request.setAttribute("emailMandatoryFlag", emailFlag);
						emailFlag = null;
						//Nishant space end
						return mapping.findForward("success");
						
					}else{
						session.setAttribute("sourceList", getSourceDetailList);
						return mapping.findForward("allocate");
					}

		
	}

}
