package com.cp.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cp.actions.LoanDetailProcessAction;
import com.cp.dao.UnderwritterDao;
import com.cp.vo.UnderwriterApprovalVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class UnderwritterDaoImpl implements UnderwritterDao  
{
	private static final Logger logger = Logger.getLogger(UnderwritterDaoImpl.class.getName());
	/*
	 * Generated Methods
	 */
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	@Override
	public String getMaxPolicyApproval(String  dealId) {
		logger.info("getMaxPolicyApproval---------");
		String approvalLevel=null;
		try{
			
			logger.info("In getMaxPolicyApproval..........................DAOImpl"+dealId);
			
			String query="";
			 query="select max(APPROVAL_LEVEL) from cr_policy_decision where DEAL_ID='"+dealId+"' AND rule_result='T'  "; // and rec_status <> 'X'
			logger.info("In getMaxPolicyApproval...............query...........DAOImpl"+query);
			approvalLevel = ConnectionDAO.singleReturn(query); 
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return approvalLevel ;
	}
	@Override
	public boolean checkFinalAmount(UnderwriterApprovalVo docVo,String userId) {
		String approvalLevel=null;
		ArrayList<UnderwriterApprovalVo> list1=new 	ArrayList<UnderwriterApprovalVo>();
		try{
			
			logger.info("In checkFinalAmount..........................DAOImpl"+docVo.getDealId());
			String product=docVo.getLbxProductID();
			String scheme=docVo.getLbxscheme();
			String amount=docVo.getLoanAmount();
			
			String query="";
			 query="SELECT count(1) FROM cr_user_approval_m WHERE user_id='"+userId+"'  AND AMOUNT_FROM<='"+amount+"'AND '"+amount+"'<=AMOUNT_TO  AND REC_STATUS='A' and user_role='U' limit 1";
			logger.info("In checkFinalAmount...............query...........DAOImpl"+query);
			approvalLevel = ConnectionDAO.singleReturn(query); 
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			docVo=null;
		}
		
		return approvalLevel.equalsIgnoreCase("0")?false:true ;
	}
	@Override
	public String getUserPolicyApproval(String user1) {
		String approvalLevel=null;
		ArrayList<UnderwriterApprovalVo> list1=new 	ArrayList<UnderwriterApprovalVo>();
		try{
			
			String query="";
			 query="SELECT level FROM cr_user_approval_m WHERE user_id='"+user1+"'  AND REC_STATUS='A' and user_role='P' limit 1";
			logger.info("In getUserPolicyApproval...............query...........DAOImpl"+query);
			approvalLevel = ConnectionDAO.singleReturn(query); 
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return approvalLevel ;
	}
}




  
