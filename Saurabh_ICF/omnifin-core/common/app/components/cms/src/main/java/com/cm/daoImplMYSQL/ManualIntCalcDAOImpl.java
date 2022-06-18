package com.cm.daoImplMYSQL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.cm.dao.ManualIntCalcDAO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureAuthorVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ClosureVO;
import com.cm.vo.DumpDownLoadVO;
import com.cm.vo.LoanDetailForCMVO;
import com.cm.vo.ManualIntCalcVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.RepayScheduleVo;

public class ManualIntCalcDAOImpl implements ManualIntCalcDAO{
	
	
	private static final Logger logger = Logger.getLogger(ManualIntCalcDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));	
	
	
	public ArrayList<ManualIntCalcVO> getMICValues(int lbxLoanNoHID,String businessDate) {
		// TODO Auto-generated method stub
		ArrayList<ManualIntCalcVO> loanDetailList = new ArrayList<ManualIntCalcVO>();
		logger.info("In getMICValues Loan Id : " + lbxLoanNoHID);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		ManualIntCalcVO loanvo = null;
		String query1 ="";
		ManualIntCalcVO loanvo1 = null;
		try {
			 query.append(" select a.loan_no,DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), b.customer_name,c.product_desc, d.scheme_desc,");
			 query.append(" a.loan_id,DATE_FORMAT(a.LOAN_REPAY_EFF_DATE,'"+dateFormat+"'),DATE_FORMAT(a.LAST_ACCRUAL_DATE,'"+dateFormat+"'),format(a.LOAN_EFF_RATE,2),DATE_FORMAT(a.LAST_LPP_DATE,'"+dateFormat+"'),a.LOAN_BALANCE_PRINCIPAL ");			
			 query.append(" from cr_loan_dtl a,gcd_customer_m b,");
			 query.append(" cr_product_m c, cr_scheme_m d where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
			 query.append(" and d.scheme_id = a.loan_scheme and a.loan_id='"+ lbxLoanNoHID + "'" );

			logger.info("In getMICValues : " + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			int size = mainlist.size();
			logger.info("In getMICValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new ManualIntCalcVO();
					loanvo.setLoanAc((CommonFunction.checkNull(subList.get(0)).trim()));
					loanvo.setLoanDate((CommonFunction.checkNull(subList.get(1)).trim()));
					loanvo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
					loanvo.setProduct((CommonFunction.checkNull(subList.get(3)).trim()));
					loanvo.setScheme((CommonFunction.checkNull(subList.get(4)).trim()));					
					loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(5)).trim()));
					loanvo.setRepayEffectiveDate((CommonFunction.checkNull(subList.get(6)).trim()));
					loanvo.setLastAccrualDate((CommonFunction.checkNull(subList.get(7)).trim()));
					loanvo.setRoi((CommonFunction.checkNull(subList.get(8)).trim()));
					loanvo.setLastIntCalcDate((CommonFunction.checkNull(subList.get(9)).trim()));
					loanvo.setBalancePrincipal((CommonFunction.checkNull(subList.get(10)).trim()));
					loanDetailList.add(loanvo);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
			query = null;
			loanvo = null;
			query1 = null;
			loanvo1 = null;
		}
		return loanDetailList;

	}
	public ArrayList<ManualIntCalcVO> getDetails(ManualIntCalcVO vo) throws SQLException{
		
		logger.info("In getDuesRefundsList.............Dao Impl");
	
	ArrayList<ManualIntCalcVO> getDetails = new ArrayList<ManualIntCalcVO>();	
	BigDecimal interestTillDate = null;
	Number intTillDate=0.0;
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	String result="";
	String s1="";
	String s2="";

 	try {

 		if(CommonFunction.checkNull(vo.getCompanyId()).trim().equalsIgnoreCase(""))
 			in.add(0);
 			else 
 			in.add(CommonFunction.checkNull(vo.getCompanyId()).trim());				//I_COMPANY_ID
 			in.add(0); 																//I_PROCESS_ID
 			in.add(CommonFunction.checkNull(vo.getLbxLoanNoHID().trim()));			//I_LOAN_ID		
 			String date=CommonFunction.changeFormat(CommonFunction.checkNull((vo.getDate())).trim());
 		    if(date != null)
 		    	in.add(date);														//I_CURR_DATE
 		    in.add(vo.getMakerId());												//I_USER_ID
 		    in.add(vo.getCalledFrom());												//I_CALLED_FROM
 		    out.add(result);														//O_OD_INTEREST
 			out.add(s1);															//o_error_flag
 			out.add(s2);															//o_error_msg		
 		
 			
 			outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Billing_OD",in,out);
		
		if((CommonFunction.checkNull(outMessages.get(0)).trim()).equalsIgnoreCase(""))
			vo.setInterestTillDate("0.00");
		else
		{
			
			
			intTillDate = myFormatter.parse((CommonFunction.checkNull(outMessages.get(0)).trim()));
			vo.setInterestTillDate(myFormatter.format(intTillDate));			
			
		}		
		interestTillDate = new BigDecimal(intTillDate.toString());			
		s1 = (CommonFunction.checkNull(outMessages.get(1)).trim());
		s2 = (CommonFunction.checkNull(outMessages.get(2)).trim());
		logger.info("s1: " + s1);
		logger.info("s2: " + s2);
		logger.info("Size of List: " + getDetails.size());	
		
		
		  if(s1.equalsIgnoreCase("E"))
			  vo.setProcWarning(s2);
		  getDetails.add(vo);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{				
		vo = null;		
		s1 = null;
		s2 = null;
	}
	return getDetails;
}

	public String generateAdvice(ManualIntCalcVO vo) 
	{
	logger.info("In insertClosureData ..................EarlyClosureDAOImpl::::::::::::::::In EarlyClosureDAOImpl");	
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	String result="";
	String s1="";
	String s2="";

	try {
		
		if(CommonFunction.checkNull(vo.getCompanyId()).trim().equalsIgnoreCase(""))
		in.add(0);
		else 
		in.add(CommonFunction.checkNull(vo.getCompanyId()).trim());				//I_COMPANY_ID
		in.add(0); 																//I_PROCESS_ID
		in.add(CommonFunction.checkNull(vo.getLbxLoanNoHID().trim()));			//I_LOAN_ID		
		String date=CommonFunction.changeFormat(CommonFunction.checkNull((vo.getDate())).trim());
	    if(date != null)
	    	in.add(date);														//I_CURR_DATE
	    in.add(vo.getMakerId());												//I_USER_ID
	    in.add(vo.getCalledFrom());												//I_CALLED_FROM
	    out.add(result);														//O_OD_INTEREST
		out.add(s1);															//o_error_flag
		out.add(s2);															//o_error_msg		
		outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Billing_OD",in,out);
		result=CommonFunction.checkNull(outMessages.get(0));
 	    s1=CommonFunction.checkNull(outMessages.get(1));
 	    s2=CommonFunction.checkNull(outMessages.get(2));
        logger.info("result: "+result);
        logger.info("s2: "+s1);  
        logger.info("s3: "+s2); 
        if(s1.equalsIgnoreCase("S"))
        {
			//con.commit();
        }
        else if(s1.equalsIgnoreCase("E"))
        {
        	//con.rollback();
        }
	}
	catch (Exception e) {
		
		e.printStackTrace();
	}
	finally
	{
	
		//s1= null;
        //s2 = null;
	}
	return s1;
}
}