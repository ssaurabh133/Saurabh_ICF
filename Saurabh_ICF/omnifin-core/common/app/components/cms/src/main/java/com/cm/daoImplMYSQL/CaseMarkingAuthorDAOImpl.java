package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.CaseMarkingAuthorDAO;
import com.cm.vo.CaseMarkingAuthorVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class CaseMarkingAuthorDAOImpl implements CaseMarkingAuthorDAO {

	private static final Logger logger = Logger.getLogger(CaseMarkingAuthorDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<CaseMarkingAuthorVO> searchCaseMarkingAuthor(
			CaseMarkingAuthorVO vo) {
		logger.info("In searchCaseMarkingMaker method of CaseMarkingCheckerDAOImpl");
		
		int count=0;
		ArrayList searchlist=new ArrayList();
		ArrayList detailList=new ArrayList();
		 try{
	              
	          
	          
	       StringBuilder bufInsSql =	new StringBuilder();
	       StringBuilder bufInsSqlTempCount = new StringBuilder();
	       bufInsSql.append("SELECT distinct LOAN_ID,(SELECT A.LOAN_NO FROM CR_LOAN_DTL A WHERE A.LOAN_ID=CRA.LOAN_ID) as LOAN_NO,(SELECT S.CUSTOMER_NAME FROM GCD_CUSTOMER_M S WHERE S.CUSTOMER_ID=CRA.LOAN_CUSTOMER_ID) as CUSTOMER_NAME ,LOAN_AMOUNT,LOAN_BALANCE_PRINCIPAL,REC_STATUS FROM  CR_CASE_MARKING_TMP_DTL CRA WHERE 5=5");
	       bufInsSqlTempCount.append("SELECT COUNT(distinct loan_id) FROM CR_CASE_MARKING_TMP_DTL CRA WHERE 5=5 ");
	      
	       
	       if(!(CommonFunction.checkNull(vo.getLbxDealNo()).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and CRA.LOAN_ID='"+(CommonFunction.checkNull(vo.getLbxDealNo())).trim()+"'");
				bufInsSqlTempCount.append(" and CRA.LOAN_ID='"+(CommonFunction.checkNull(vo.getLbxDealNo())).trim()+"'"); 
			}
	       bufInsSql.append(" and rec_status='F'and maker_id!='"+vo.getUserId()+"'");	
			 bufInsSqlTempCount.append(" and rec_status='F'and maker_id!='"+vo.getUserId()+"'");
		 	String tmp = bufInsSql.toString();
			String tmp1 = bufInsSqlTempCount.toString();
	        searchlist = ConnectionDAO.sqlSelect(tmp);
	        tmp=null;
	        count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	        tmp1=null;
	        CaseMarkingAuthorVO caseMarkingAuthorVO=null;
	       int size=searchlist.size();
	        for(int i=0;i<size;i++){
	       
	        ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  caseMarkingAuthorVO= new CaseMarkingAuthorVO();
	        	  
	        	  caseMarkingAuthorVO.setLoanId((CommonFunction.checkNull(data.get(0)).trim()));
	        	  caseMarkingAuthorVO.setLoanNo("<a href=caseMarkingAuthorDispatch.do?method=openEditCaseMarkingAuthor&loanId="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
	        	  caseMarkingAuthorVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	        	  caseMarkingAuthorVO.setLoanAmount((CommonFunction.checkNull(data.get(3)).trim()));
	        	  caseMarkingAuthorVO.setBalancePrincipal((CommonFunction.checkNull(data.get(4)).trim()));
	        	  caseMarkingAuthorVO.setTotalRecordSize(count);
	        	 detailList.add(caseMarkingAuthorVO);
	        	 caseMarkingAuthorVO=null;
	        }
	          data.clear();
	          data=null;
		}
	        searchlist.clear();
	          searchlist=null;
	          bufInsSql=null;
	          bufInsSqlTempCount=null;
	          
		}catch(Exception e){
			e.printStackTrace();
		}
	       finally
		{
			vo=null;
		
		}

		return detailList;
	}
public ArrayList<CaseMarkingAuthorVO> openEditCaseMarkingAuthor(CaseMarkingAuthorVO vo) {

		ArrayList searchlist = new ArrayList();
		
		ArrayList<CaseMarkingAuthorVO> caseTypeList = new ArrayList<CaseMarkingAuthorVO>();
		logger.info("ratioid in openEditCaseMarkingAuthor &***************************** = "+vo.getLoanId());
		try {
			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" select a.case_id, a.loan_id,b.loan_no,c.customer_name,a.OTHER_DETAILS,a.REMARKS,a.CASE_MARKING_FLAG,DATE_FORMAT(a.CASE_MARKING_DATE,'"+ dateFormat +"'),a.AGENCY, d.description  FROM CR_CASE_MARKING_TMP_DTL a inner join cr_loan_dtl b on b.loan_id=a.loan_id inner join gcd_customer_m c on c.customer_id=a.loan_customer_id inner join generic_master d on d.value=a.case_status and d.GENERIC_KEY='CASE_MARKING_FLAG'");
			bufInsSql.append("  WHERE a.loan_id='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo())+"'AND A.REC_STATUS!='X'");
			logger.info("IN openEditCaseMarkingAuthor() search query1 ### "+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			bufInsSql=null;
			CaseMarkingAuthorVO caseTypeDataVo=null;
			int size=searchlist.size();
			for (int i = 0; i < size; i++)
			{
				ArrayList data = (ArrayList) searchlist.get(i);
				caseTypeDataVo = new CaseMarkingAuthorVO();
				caseTypeDataVo.setCaseId(CommonFunction.checkNull(data.get(0)).toString());
				caseTypeDataVo.setLbxDealNo(CommonFunction.checkNull(data.get(1)).toString());
				caseTypeDataVo.setSearchLoanNo(CommonFunction.checkNull(data.get(2)).toString());
				caseTypeDataVo.setSearchCustomerName(CommonFunction.checkNull(data.get(3)).toString());
				caseTypeDataVo.setOtherDetails(CommonFunction.checkNull(data.get(4)).toString());
				caseTypeDataVo.setRemarks(CommonFunction.checkNull(data.get(5)).toString());
				caseTypeDataVo.setChk(CommonFunction.checkNull(data.get(6)).toString());
				caseTypeDataVo.setMarkingDate(CommonFunction.checkNull(data.get(7)).toString());
				caseTypeDataVo.setAgency(CommonFunction.checkNull(data.get(8)).toString());
				caseTypeDataVo.setCaseStatus(CommonFunction.checkNull(data.get(9)).toString());
				caseTypeList.add(caseTypeDataVo);
				caseTypeDataVo=null;
				data.clear();
				data=null;
}
				} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			searchlist.clear();
			searchlist = null;
			vo=null;
		         }
		return caseTypeList;
}		
	
	public ArrayList<Object> saveCaseMarkingCheckerDetails(CaseMarkingAuthorVO vo) {
	logger.info("In saveCaseMarkingCheckerDetails()");
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	ArrayList result = new ArrayList();
	String s1=null;
	String s2=null;
	String error=null;
	try
	{
		
			in.add(vo.getLbxDealNo());
			in.add(vo.getDecision());
			in.add(vo.getUserId());
			out.add(s1);
			out.add(s2);
			logger.info("CASE_MARKING_AUTHOR("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionDAO.callSP("CASE_MARKING_AUTHOR",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("s1  : "+s1);
			logger.info("s2  : "+s2);
			result.add(error);
			result.add(s1);
			result.add(s2);
	 }
	catch (Exception e) 
	{
		error="error";
		result.add(error);
        result.add(s1);
        result.add(s2);
        e.printStackTrace();
    }
	finally
	{
	
		in=null;
		out =null;
		outMessages=null;
		s1=null;
		s2=null;
		error=null;
		vo=null;
	}
	return result;
}
	}
