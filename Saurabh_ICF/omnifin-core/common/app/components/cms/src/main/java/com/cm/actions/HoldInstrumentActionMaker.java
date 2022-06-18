package com.cm.actions;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cm.dao.CreditManagementDAO;
import com.cm.dao.HoldInstrumentDAO;
import com.cm.dao.ReleaseInstrumentDAO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class HoldInstrumentActionMaker extends DispatchAction{
	private static final Logger logger = Logger.getLogger(HoldInstrumentActionMaker.class.getName());		
	public ActionForward searchHoldInstrumentMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			
				logger.info("In searchHoldInstrumentMaker");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				
				String userId="";
				String branchId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}else{
					logger.info("in  searchHoldInstrumentMaker metohd of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				
				session.removeAttribute("arryList");
				session.removeAttribute("arrList");
				session.removeAttribute("loanID");
				session.removeAttribute("author");
				session.removeAttribute("releasenotCheck");
				session.removeAttribute("notCheck");
				
				 logger.info("current page link .......... "+request.getParameter("d-49520-p"));
					
					int currentPageLink = 0;
					if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
					{
						currentPageLink=1;
					}
					else
					{
						currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
					}
					
					logger.info("current page link ................ "+request.getParameter("d-49520-p"));
					
					instructionCapMakerVO.setCurrentPageLink(currentPageLink);
					
				String alertMsg ="";
				
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);

//				if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
//						{ 
//					instructionCapMakerVO.setReportingToUserId(userId);
//						   //logger.info("When user id is not selected by the user:::::"+userId);
//						}
//						logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
						instructionCapMakerVO.setBranchId(branchId);
						instructionCapMakerVO.setUserID(userId);
						//HoldInstrumentDAO dao = new HoldInstrumentDAOImpl();
						HoldInstrumentDAO dao=(HoldInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(HoldInstrumentDAO.IDENTITY);
						logger.info("Implementation class: "+dao.getClass()); 
						if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals(""))
						{
			    ArrayList<InstructionCapMakerVO> searchHoldInstrumentList=dao.searchHoldInstrumentMaker(instructionCapMakerVO);
			    
			    logger.info("In searchHoldInstrumentList--"+searchHoldInstrumentList.size());
			    
			    if(searchHoldInstrumentList.size() > 0){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsg", alertMsg);
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsg", alertMsg);
			    }
			    request.setAttribute("list",searchHoldInstrumentList );
						}
			    request.setAttribute("maker", "maker");
			    request.setAttribute("holdinstrumentCapturingMakerSearch", "holdinstrumentCapturingMakerSearch");
			    session.setAttribute("userId", userId);
				return mapping.findForward("searchHoldInstrumentList");
		
	}
	public ActionForward searchReleaseInstrumentMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	

				logger.info("In searchReleaseInstrumentMaker");
				
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				
				String userId="";
				String branchId="";
				if(userobj!=null){
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}else{
					logger.info("here searchReleaseInstrumentMaker method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				
				session.removeAttribute("arryList");
				session.removeAttribute("arrList");
				session.removeAttribute("loanID");
				session.removeAttribute("author");
				session.removeAttribute("releasenotCheck");
				session.removeAttribute("notCheck");
				
				 logger.info("current page link .......... "+request.getParameter("d-49520-p"));
					
					int currentPageLink = 0;
					if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
					{
						currentPageLink=1;
					}
					else
					{
						currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
					}
					
					logger.info("current page link ................ "+request.getParameter("d-49520-p"));
					
					instructionCapMakerVO.setCurrentPageLink(currentPageLink);
					
				String alertMsg ="";
				
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				
				instructionCapMakerVO.setBranchId(branchId);
				instructionCapMakerVO.setUserID(userId);
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
//				if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
//				{ 
//					instructionCapMakerVO.setReportingToUserId(userId);
//				   //logger.info("When user id is not selected by the user:::::"+userId);
//				}
//				logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
				ReleaseInstrumentDAO dao=(ReleaseInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(ReleaseInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals(""))
				{
			    ArrayList<InstructionCapMakerVO> searchReleaseInstrumentList=dao.searchReleaseInstrumentMaker(instructionCapMakerVO);
			    logger.info("In searchReleaseInstrumentList--"+searchReleaseInstrumentList.size());
			    
			    if(searchReleaseInstrumentList.size() > 0){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsg", alertMsg);
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsg", alertMsg);
			    }
			    
			    request.setAttribute("list",searchReleaseInstrumentList );
							}
			    
			    request.setAttribute("release", "release");
			    request.setAttribute("true","true");
			    request.setAttribute("releaseinstrumentCapturingMakerSearch", "releaseinstrumentCapturingMakerSearch");
			    session.setAttribute("userId", userId);
				return mapping.findForward("searchReleaseInstrumentList");
		
	}
	
	
	
	
	public ActionForward searchHoldInstrumentAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
				logger.info("In searchHoldInstrumentAuthor");
					

				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				
				String userId="";
				String branchId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}else{
					logger.info("here in searchHoldInstrumentAuthor method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				session.removeAttribute("arryList");
				session.removeAttribute("arrList");
				session.removeAttribute("loanID");
				session.removeAttribute("author");
				session.removeAttribute("releasenotCheck");
				session.removeAttribute("notCheck");
				
				 logger.info("current page link .......... "+request.getParameter("d-49520-p"));
					
					int currentPageLink = 0;
					if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
					{
						currentPageLink=1;
					}
					else
					{
						currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
					}
					
					logger.info("current page link ................ "+request.getParameter("d-49520-p"));
					
					instructionCapMakerVO.setCurrentPageLink(currentPageLink);
					
				String alertMsg="";
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				
				instructionCapMakerVO.setBranchId(branchId);
				instructionCapMakerVO.setUserID(userId);
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);

//				if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
//						{ 
//							instructionCapMakerVO.setReportingToUserId(userId);
//						   //logger.info("When user id is not selected by the user:::::"+userId);
//						}
//						logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
						
				HoldInstrumentDAO dao=(HoldInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(HoldInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals(""))
				{
			    ArrayList<InstructionCapMakerVO> searchInstrumentAuthorList=dao.searchHoldInstrumentAuthor(instructionCapMakerVO);
			    logger.info("In searchInstrumentAuthorList--"+searchInstrumentAuthorList.size());
			    if(searchInstrumentAuthorList.size() > 0){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsg", alertMsg);
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsg", alertMsg);
			    }
			    request.setAttribute("list",searchInstrumentAuthorList );
				}
			    request.setAttribute("author", "author");
			    request.setAttribute("true","true");
			    request.setAttribute("holdinstrumentCapturingAuthorSearch", "holdinstrumentCapturingAuthorSearch");
			    session.setAttribute("userId", userId);
				return mapping.findForward("searchHoldInstrumentList");
	}
	
	
	public ActionForward searchReleaseInstrumentAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				logger.info("In searchReleaseInstrumentAuthor");
					

				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
	
				String userId="";
				String branchId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
					branchId=userobj.getBranchId();
				}else{
					logger.info("here in searchReleaseInstrumentAuthor method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				//for check User session start
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
				
				session.removeAttribute("arryList");
				session.removeAttribute("arrList");
				session.removeAttribute("loanID");
				session.removeAttribute("author");
				session.removeAttribute("releasenotCheck");
				session.removeAttribute("notCheck");
				
				 logger.info("current page link .......... "+request.getParameter("d-49520-p"));
					
					int currentPageLink = 0;
					if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
					{
						currentPageLink=1;
					}
					else
					{
						currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
					}
					
					logger.info("current page link ................ "+request.getParameter("d-49520-p"));
					
					instructionCapMakerVO.setCurrentPageLink(currentPageLink);
					
				String alertMsg ="";
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				
				instructionCapMakerVO.setBranchId(branchId);
				instructionCapMakerVO.setUserID(userId);
				
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);

//				if(CommonFunction.checkNull(instructionCapMakerVO.getReportingToUserId()).equalsIgnoreCase(""))
//						{ 
//					instructionCapMakerVO.setReportingToUserId(userId);
//						   //logger.info("When user id is not selected by the user:::::"+userId);
//						}
//						logger.info("user Id:::::"+instructionCapMakerVO.getReportingToUserId());
				ReleaseInstrumentDAO dao=(ReleaseInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(ReleaseInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				if(!CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim().equals("") ||!CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim().equals("")  || !CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim().equals("") || !CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim().equals(""))
				{
			    ArrayList<InstructionCapMakerVO> searchReleaseInstrumentAuthorList=dao.searchReleaseInstrumentAuthor(instructionCapMakerVO);
			    logger.info("In searchReleaseInstrumentAuthorList--"+searchReleaseInstrumentAuthorList.size());
			    
			    if(searchReleaseInstrumentAuthorList.size() > 0){
			    	alertMsg = "Y";
			    	request.setAttribute("alertMsg", alertMsg);
			    
			    }else{
			    	alertMsg = "N";
			    	request.setAttribute("alertMsg", alertMsg);
			    }
			    request.setAttribute("list",searchReleaseInstrumentAuthorList );
				}
			    request.setAttribute("releaseAuthor","releaseAuthor");
			    request.setAttribute("true","true");
			    request.setAttribute("releaseinstrumentCapturingAuthorSearch", "releaseinstrumentCapturingAuthorSearch");
			    session.setAttribute("userId", userId);
				return mapping.findForward("searchReleaseInstrumentAuthorList");
	}
	
	public ActionForward updateHoldInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
				logger.info(" In the updateHoldInstrument----------");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId="";
				if(userobj!=null)
				{
					userId=userobj.getUserId();
				}else{
					logger.info("here in updateHoldInstrument method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				int id=0;
				if(request.getAttribute("loanID")!=null)
				{
					id=Integer.parseInt(request.getAttribute("loanID").toString());
				}
				else if(request.getParameter("loanID")!=null)
				{
					id=Integer.parseInt(request.getParameter("loanID"));
					
				}
				logger.info(" ID is----"+id);
				HoldInstrumentDAO dao=(HoldInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(HoldInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				String loanID=request.getParameter("loanID");
				
				logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
				String functionId="";
				
				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				flag = LockRecordCheck.lockCheck(userId,functionId,loanID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "Locked");
					request.setAttribute("recordId", loanID);
					request.setAttribute("maker", "maker");
					return mapping.getInputForward();
				}
				}
				//change by sachin
				CreditManagementDAO cdao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
			     logger.info("Implementation class: "+cdao.getClass());

				//end by sachin
//				CreditManagementDAO cdao = new CreditManagementDAOImpl();
				ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
				ArrayList<InstructionCapMakerVO> arrList= dao.getValuesforHoldInstrument(id);
				logger.info("ArrayList is "+arrList.size());
				request.setAttribute("arryList", arryList);
				request.setAttribute("arrList", arrList);
				request.setAttribute("check", "check");
				request.setAttribute("hold","hold");
				return mapping.findForward("updateHoldInstrument");
		}
	
	public ActionForward editReleaseInstrumentMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
				logger.info(" In the editReleaseInstrumentMaker----------");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId ="";
				if(userobj!=null)
				{
				 userId = userobj.getUserId();
				}else{
					logger.info("here in editReleaseInstrumentMaker method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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

				int id=0;
				if(request.getAttribute("loanID")!=null)
				{
					id=Integer.parseInt(request.getAttribute("loanID").toString());
				}
				else if(request.getParameter("loanID")!=null)
				{
					id=Integer.parseInt(request.getParameter("loanID"));
					
				}
				logger.info(" Id is----------"+id);
				ReleaseInstrumentDAO dao=(ReleaseInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(ReleaseInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				String loanID=request.getParameter("loanID");
				
				logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
				String functionId="";

				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				flag = LockRecordCheck.lockCheck(userId,functionId,loanID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "ReleaseMLocked");
					request.setAttribute("recordId", loanID);
					request.setAttribute("release", "release");
					return mapping.getInputForward();
				}
				}
				//change by sachin
				CreditManagementDAO cdao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
			     logger.info("Implementation class: "+cdao.getClass());

				//end by sachin
//				CreditManagementDAO cdao = new CreditManagementDAOImpl();
				ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
				ArrayList<InstructionCapMakerVO> arrList= dao.getValuesforReleaseInstrument(id);
				
				logger.info("ArrayList is "+arrList.size());
				request.setAttribute("arryList", arryList);
				request.setAttribute("arrList", arrList);
				request.setAttribute("release", "release");
				return mapping.findForward("editReleaseInstrumentMaker");
		}
	
	
	
	 public ActionForward searchIndiHoldInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		 
				 logger.info(" In the searchIndiHoldInstrument----------");
				 HttpSession session =request.getSession();
					boolean flag=false;
					UserObject userobj=(UserObject)session.getAttribute("userobject");
					if(userobj==null){
						logger.info("here in searchIndiHoldInstrument method of HoldInstrumentActionMaker action the session is out----------------");
						return mapping.findForward("sessionOut");
					}
					Object sessionId = session.getAttribute("sessionID");
					//for check User session start
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
				int id=0;
				if(request.getAttribute("loanID")!=null)
				{
					id=Integer.parseInt(request.getAttribute("loanID").toString());
				}
				else if(request.getParameter("loanID")!=null)
				{
					id=Integer.parseInt(request.getParameter("loanID"));
					
				}
				logger.info(" In the searchIndiHoldInstrument----------"+id);
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				HoldInstrumentDAO dao=(HoldInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(HoldInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				//change by sachin
				CreditManagementDAO cdao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
			     logger.info("Implementation class: "+cdao.getClass());

				//end by sachin
	//			CreditManagementDAO cdao = new CreditManagementDAOImpl();
				ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
				ArrayList<InstructionCapMakerVO> arrList= dao.getValuesforIndiHoldInstrument(id,instructionCapMakerVO);
				logger.info("ArrayList is "+arrList.size());
				request.setAttribute("arryList", arryList);
				request.setAttribute("arrList", arrList);
				request.setAttribute("check", "check");
				request.setAttribute("hold","hold");
				return mapping.findForward("updateHoldInstrument");
		}

	 public ActionForward searchIndiReleaseInstrument(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception{
		 
			    logger.info(" In the searchIndiReleaseInstrument----------");
			    HttpSession session=request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info("here in searchIndiReleaseInstrument method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				int id=0;
				if(request.getAttribute("loanID")!=null)
				{
					id=Integer.parseInt(request.getAttribute("loanID").toString());
				}
				else if(request.getParameter("loanID")!=null)
				{
					id=Integer.parseInt(request.getParameter("loanID"));
					
				}
				logger.info(" ID is ----------"+id);
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				
				ReleaseInstrumentDAO dao=(ReleaseInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(ReleaseInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				//change by sachin
				CreditManagementDAO cdao=(CreditManagementDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditManagementDAO.IDENTITY);
			     logger.info("Implementation class: "+cdao.getClass());

				//end by sachin
//				CreditManagementDAO cdao = new CreditManagementDAOImpl();
				ArrayList<InstructionCapMakerVO> arryList = cdao.getretriveCutInsValues(id);
				ArrayList<InstructionCapMakerVO> arrList= dao.getValuesforIndiReleaseInstrument(id,instructionCapMakerVO);
				logger.info("ArrayList is "+arrList.size());
				request.setAttribute("arryList", arryList);
				request.setAttribute("arrList", arrList);
				request.setAttribute("release","release");
				
				return mapping.findForward("updateHoldInstrument");
			}
	
	 
	 
	 
	 public ActionForward savenForPDCHoldInstrument(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("In savenForPDCHoldInstrument");
			    HttpSession session =request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userobj!=null){
					makerID= userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in savenForPDCHoldInstrument method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				HoldInstrumentDAO dao=(HoldInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(HoldInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	
			
				//UserObject userObj = (UserObject)session.getAttribute("userobject");
				
		
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				instructionCapMakerVO.setMakerID(makerID);
				instructionCapMakerVO.setMakerDate(bDate);
				String alertMsgfrsvnfrhold ="";
				String loanId=request.getParameter("loanID");
				String checkedhold = request.getParameter("checkedhold");
                String[] checkedholdList= checkedhold.split("/");
	            String checkedStatus = request.getParameter("checkedStatus");	
                String[] checkedStatusList= checkedStatus.split("/");                               
		        String instrumentid = request.getParameter("instrumentid");
		        String[] instrumentidList= instrumentid.split("/");
	            String newStatus = request.getParameter("newStatus");
			    String[] newStatusList= newStatus.split("/");
			    logger.info("Before updateIndiHoldInstrument");
			   boolean status = dao.updateIndiHoldInstrument(checkedholdList,checkedStatusList,instrumentidList,newStatusList,instructionCapMakerVO);
			    if(status){
			    	alertMsgfrsvnfrhold = "Y";
			    	request.setAttribute("alertMsgfrsvnfrhold", alertMsgfrsvnfrhold);
			    	
			    }else{
			    	alertMsgfrsvnfrhold = "N";
			    	request.setAttribute("alertMsgfrsvnfrhold", alertMsgfrsvnfrhold);
			    }
			   request.setAttribute("loanID", loanId);
			    return mapping.findForward("savenForPDCHoldInstrument");
		}
	 
	 public ActionForward savenForPDCReleaseInstrument(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			
			    logger.info("in savenForPDCReleaseInstrument"); 
			    HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userobj!=null){
					makerID= userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here in savenForPDCReleaseInstrument method of HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				ReleaseInstrumentDAO dao=(ReleaseInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(ReleaseInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				
				String alertMsgfrsvnfrrel ="";
				//UserObject userObj=(UserObject)session.getAttribute("userobject");

				
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();	
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				instructionCapMakerVO.setMakerID(makerID);
				instructionCapMakerVO.setMakerDate(bDate);
				String loanId=request.getParameter("loanID");
				logger.info("loanId"+loanId);
				
				String checkedhold = request.getParameter("checkedhold");
	            String[] checkedholdList= checkedhold.split("/");
	            String checkedStatus = request.getParameter("checkedStatus"); 			
	            String[] checkedStatusList= checkedStatus.split("/");                               
		        String instrumentid = request.getParameter("instrumentid");                    			
	            String[] instrumentidList= instrumentid.split("/");                
	            String newStatus = request.getParameter("newStatus");                     			
	            String[] newStatusList= newStatus.split("/");
	            logger.info("Before updateIndiReleaseInstrument"); 
			   boolean status = dao.updateIndiReleaseInstrument(checkedholdList,checkedStatusList,instrumentidList,newStatusList,instructionCapMakerVO);
			   if(status){
				   alertMsgfrsvnfrrel = "Y";
			    	request.setAttribute("alertMsgfrsvnfrrel", alertMsgfrsvnfrrel);
			    
			    }else{
			    	alertMsgfrsvnfrrel = "N";
			    	request.setAttribute("alertMsgfrsvnfrrel", alertMsgfrsvnfrrel);
			    }
			   
			   
			   request.setAttribute("loanID", loanId);
			    return mapping.findForward("savenForPDCReleaseInstrument");
		}
	 
	 
		public ActionForward holdInstrumentAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			
				logger.info(" In the holdInstrumentAuthor----------");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId ="";
				if(userobj!=null){
				userId = userobj.getUserId();
				}else{
					logger.info("here in holdInstrumentAuthor HoldInstrumentActionMaker action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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

				int id = Integer.parseInt(request.getParameter("id"));
				String ID=request.getParameter("id");
				HoldInstrumentDAO dao=(HoldInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(HoldInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				
				logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
				String functionId="";

				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				flag = LockRecordCheck.lockCheck(userId,functionId,ID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "AuthorLocked");
					request.setAttribute("recordId", ID);
					 request.setAttribute("author", "author");
					return mapping.getInputForward();
				}
				}
				ArrayList<InstructionCapMakerVO> arrList = dao.getValuesforHoldInstrumentAuthor(id);
				logger.info("ArrayList is "+arrList);
				session.setAttribute("notCheck", "notCheck");
				session.setAttribute("arrList", arrList);
				session.setAttribute("loanID", id);
				session.setAttribute("author", "author");
				return mapping.findForward("holdInstrumentAuthor");
			}
		
		
		public ActionForward editReleaseInstrumentAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			
				logger.info(" In the editReleaseInstrumentAuthor----------");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String userId = "";
				if(userobj!=null){
					userId = userobj.getUserId();
						}else{
							logger.info("here editReleaseInstrumentAuthor method of  HoldInstrumentActionMaker action the session is out----------------");
							return mapping.findForward("sessionOut");
						}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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

				int id = Integer.parseInt(request.getParameter("id"));
				ReleaseInstrumentDAO dao=(ReleaseInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(ReleaseInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				String ID=request.getParameter("id");
				
				logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
				String functionId="";

				if(session.getAttribute("functionId")!=null)
				{
					functionId=session.getAttribute("functionId").toString();
				}
				
				
				//ServletContext context=getServlet().getServletContext();
				if(context!=null)
				{
				flag = LockRecordCheck.lockCheck(userId,functionId,ID,context);
				logger.info("Flag ........................................ "+flag);
				if(!flag)
				{
					logger.info("Record is Locked");			
					request.setAttribute("alertMsg", "ReleaseALocked");
					request.setAttribute("recordId", ID);
					request.setAttribute("releaseAuthor","releaseAuthor");
					return mapping.getInputForward();
				}
				}
				ArrayList<InstructionCapMakerVO> arrList = dao.getValuesforReleaseInstrumentAuthor(id);
				logger.info("ArrayList is "+arrList.size());
				session.setAttribute("releasenotCheck", "releasenotCheck");
				session.setAttribute("arrList", arrList);
				session.setAttribute("loanID", id);
				session.setAttribute("author", "author");
				return mapping.findForward("editReleaseInstrumentAuthor");
			}
		
		
		public ActionForward saveholdInstrumentAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception{
			
				logger.info("In saveholdInstrumentAuthor-");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userobj!=null){
					makerID= userobj.getUserId();
					bDate=userobj.getBusinessdate();
				}else{
					logger.info("here saveholdInstrumentAuthor method of HoldInstrumentActionMaker  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				boolean  flag1=false;
				DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;

				//UserObject userObj=(UserObject)session.getAttribute("userobject");


				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				int loanID=Integer.parseInt(request.getParameter("loanID"));
				logger.info("loanID"+loanID);
				
				String[] instrumentID =request.getParameterValues("instrumentID");
				logger.info("list _____--------"+instrumentID.length);
	
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				HoldInstrumentDAO dao=(HoldInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(HoldInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass()); 
				instructionCapMakerVO.setMakerID(makerID);
				instructionCapMakerVO.setMakerDate(bDate);
			    flag1=dao.updateCommentNDecisionforHoldIns(instructionCapMakerVO,instrumentID);
			    
		        if(flag1){
			    	 request.setAttribute("savedSuccessfully", "S"); 
			     }else{
			    	 request.setAttribute("savedSuccessfully", "N");
			     }

			    request.setAttribute("author", "author");
				return mapping.findForward("saveholdInstrumentAuthor");
			 
		}
		
		public ActionForward savereleaseInstrumentAuthor(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws IllegalAccessException, InvocationTargetException, ParseException ,Exception{
			
				logger.info("In savereleaseInstrumentAuthor Method---------");
				//boolean  flag1=false;
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userObj=(UserObject)session.getAttribute("userobject");
				String makerID="";
				String bDate ="";
				if(userObj!=null){
					makerID= userObj.getUserId();
					bDate= userObj.getBusinessdate();
				}else{
					logger.info("here savereleaseInstrumentAuthor method of HoldInstrumentActionMaker  action the session is out----------------");
					return mapping.findForward("sessionOut");
				}
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				ServletContext context = getServlet().getServletContext();
				String strFlag="";	
				
				if(sessionId!=null)
				{
					strFlag = UserSessionCheck.checkSameUserSession(userObj,sessionId.toString(),"",request);
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


				
		        DynaValidatorForm InstrumentCapturingMakerFirstDynaValidatorForm = (DynaValidatorForm)form;
				InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
				int loanID=Integer.parseInt(request.getParameter("loanID"));
				logger.info("loanID"+loanID);
				
				String[] instrumentID =request.getParameterValues("instrumentID");
								
				org.apache.commons.beanutils.BeanUtils.copyProperties(instructionCapMakerVO, InstrumentCapturingMakerFirstDynaValidatorForm);
				ReleaseInstrumentDAO dao=(ReleaseInstrumentDAO)DaoImplInstanceFactory.getDaoImplInstance(ReleaseInstrumentDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				instructionCapMakerVO.setMakerID(makerID);
				instructionCapMakerVO.setMakerDate(bDate);
				flag=dao.updateCommentNDecisionforReleaseIns(instructionCapMakerVO,instrumentID);
			     if(flag){
			    	 request.setAttribute("savedReleaseSuccessfully", "S"); 
			     }else{
			    	 request.setAttribute("savedReleaseSuccessfully", "N");
			     }
			    request.setAttribute("releasenotCheck", "releasenotCheck");
				return mapping.findForward("savereleaseInstrumentAuthor");

		}

		
}
