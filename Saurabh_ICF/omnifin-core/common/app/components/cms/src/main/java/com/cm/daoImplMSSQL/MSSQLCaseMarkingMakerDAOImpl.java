package com.cm.daoImplMSSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.CaseMarkingMakerDAO;
import com.cm.vo.CaseMarkingMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CodeDescVo;
public class MSSQLCaseMarkingMakerDAOImpl implements CaseMarkingMakerDAO {

	private static final Logger logger = Logger.getLogger(MSSQLCaseMarkingMakerDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	public ArrayList<CaseMarkingMakerVO> searchCaseMarkingMaker(
			CaseMarkingMakerVO vo) {
		logger.info("In searchCaseMarkingMaker method of CaseMarkingCheckerDAOImpl");
		
		int count=0;
		String tableName=null;
		
		ArrayList searchlist=new ArrayList();
		ArrayList detailList=new ArrayList();
		
		 try
		  {
				if( !((CommonFunction.checkNull(vo.getStatusCase())).equalsIgnoreCase("A"))){
	    			tableName="CR_CASE_MARKING_TMP_DTL";
	    			
	    		}
	    		else{
	    			tableName="CR_CASE_MARKING_DTL";
	    			
	    		}
	          	
//	        
	          	StringBuilder bufInsSql =	new StringBuilder();
	            StringBuilder bufInsSqlTempCount = new StringBuilder();
	              
	              bufInsSql.append("SELECT distinct cra.LOAN_ID,(SELECT A.LOAN_NO FROM CR_LOAN_DTL A WHERE A.LOAN_ID=CRA.LOAN_ID) as LOAN_NO,(SELECT S.CUSTOMER_NAME FROM GCD_CUSTOMER_M S WHERE S.CUSTOMER_ID=CRA.LOAN_CUSTOMER_ID) as CUSTOMER_NAME ,cra.LOAN_AMOUNT,cra.LOAN_BALANCE_PRINCIPAL,cra.REC_STATUS FROM "+tableName+" CRA ");
	              bufInsSqlTempCount.append("SELECT COUNT(distinct cra.loan_id) FROM "+tableName+" cra  ");
				 if( ((CommonFunction.checkNull(vo.getStatusCase())).equalsIgnoreCase("A")))
					{
						 bufInsSql.append("left join cr_case_marking_tmp_dtl b on b.loan_id=cra.loan_id where isnull(b.REC_STATUS,'A') NOT IN ('P','F') ");
						 bufInsSqlTempCount.append("left join cr_case_marking_tmp_dtl b on b.loan_id=cra.loan_id where isnull(b.REC_STATUS,'A')NOT IN ('P','F') ");
					}
				 	else
				 	{
					bufInsSql.append(" where rec_status not in('F','X')  and maker_id= '"+vo.getUserId()+"' ");	
					 bufInsSqlTempCount.append(" where rec_status not in('F','X') and maker_id= '"+vo.getUserId()+"'  ");
				 	}
				 	if(vo.getLbxDealNo()!=null)
					{
				 if(!(CommonFunction.checkNull(vo.getLbxDealNo()).equalsIgnoreCase("")))
					{
						bufInsSql.append(" and CRA.LOAN_ID='"+(CommonFunction.checkNull(vo.getLbxDealNo())).trim()+"'");
						bufInsSqlTempCount.append(" and CRA.LOAN_ID='"+(CommonFunction.checkNull(vo.getLbxDealNo())).trim()+"'"); 
					}
					
				if(!(CommonFunction.checkNull(vo.getStatusCase()).equalsIgnoreCase("")))
					{

					bufInsSql.append(" and CRA.REC_STATUS='"+(CommonFunction.checkNull(vo.getStatusCase())).trim()+"'");
						bufInsSqlTempCount.append(" and CRA.REC_STATUS='"+(CommonFunction.checkNull(vo.getStatusCase())).trim()+"'"); 
				}
					
					String tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	            
					searchlist = ConnectionDAO.sqlSelect(tmp);
	            count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	            tmp=null;
	            tmp1=null;
	            CaseMarkingMakerVO caseMarkingMakerVO=null;
	         int size=searchlist.size();
	          for(int i=0;i<size;i++){
	      
	          ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	   caseMarkingMakerVO= new CaseMarkingMakerVO();
	        	  
	        	  caseMarkingMakerVO.setLoanId((CommonFunction.checkNull(data.get(0)).trim()));
	        	  caseMarkingMakerVO.setLoanNo("<a href=caseMarkingMakerDispatch.do?method=openEditCaseMarkingMaker&loanId="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+"&status="+ CommonFunction.checkNull(data.get(5)).toString()+">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
	        	 
	        	  caseMarkingMakerVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	        	  caseMarkingMakerVO.setLoanAmount((CommonFunction.checkNull(data.get(3)).trim()));
	        	  caseMarkingMakerVO.setBalancePrincipal((CommonFunction.checkNull(data.get(4)).trim()));
	        	  caseMarkingMakerVO.setTotalRecordSize(count);
	        	  detailList.add(caseMarkingMakerVO);
	        	  caseMarkingMakerVO=null;
	        }
	          data.clear();
	          data=null;
		}
	          bufInsSql=null;
	          bufInsSqlTempCount=null;
	          
					}}catch(Exception e){
			e.printStackTrace();
		}
	       finally
		{
	    	   searchlist.clear();
	    	   searchlist=null;
			
			tableName=null;
			vo=null;
		}
	       return detailList;
	}

	@Override
	public String checkLoanNo(CaseMarkingMakerVO vo) {
		String statusCheck=null;
		StringBuilder query = new StringBuilder(); 
		ArrayList bdetails = new ArrayList();
		
		
		query.append("SELECT * FROM cr_case_marking_dtl WHERE LOAN_ID=(SELECT LOAN_ID FROM cr_loan_dtl WHERE LOAN_NO='"+vo.getLoanNo()+"')");
		logger.info("query" + query.toString());
		
		try {
			bdetails = ConnectionDAO.sqlSelect(query.toString());
			int size = bdetails.size();
			if (size==0){
				statusCheck="NotExist";
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		finally
		{
			vo = null;
			query = null; 
			bdetails.clear();
			bdetails= null;
			
		}
		return statusCheck;
	}
     
	public ArrayList<Object> getCaseMarkingFlagList() {
		ArrayList<Object> list=new ArrayList<Object>();
		try
		{
			StringBuilder query=new StringBuilder();
		 query.append("select VALUE,DESCRIPTION from generic_master  where GENERIC_KEY='CASE_MARKING_FLAG' ");
		ArrayList addressList = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getAddressList "+addressList.size());
		query=null;
		CodeDescVo buyerVo=null;
		int size=addressList.size();
		for(int i=0;i<size;i++){
			
			ArrayList data=(ArrayList)addressList.get(i);
			if(data.size()>0)	{
		 buyerVo=new CodeDescVo();
			
			buyerVo.setId((CommonFunction.checkNull(data.get(0))).trim());
			buyerVo.setName((CommonFunction.checkNull(data.get(1))).trim());
			
			list.add(buyerVo);
			buyerVo=null;
		
		}
			data.clear();
			data=null;
		}
		addressList.clear();
		addressList=null;
		
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return list;
	}

	@Override
	public ArrayList<CaseMarkingMakerVO> openEditCaseMarkingMaker(
			CaseMarkingMakerVO vo) {

		ArrayList searchlist = new ArrayList();
		
		String tableName=null;
		ArrayList<CaseMarkingMakerVO> caseTypeList = new ArrayList<CaseMarkingMakerVO>();
		ArrayList data=null;
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanId());
		try {
			
	     	if( !((CommonFunction.checkNull(vo.getStatusCase())).equalsIgnoreCase("A"))){
    			tableName="CR_CASE_MARKING_TMP_DTL";
    		}
    		else{
    			tableName="CR_CASE_MARKING_DTL";
    		}
			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" select a.case_id, a.loan_id,b.loan_no,c.customer_name,a.OTHER_DETAILS,a.REMARKS,a.CASE_MARKING_FLAG,dbo.DATE_FORMAT(a.CASE_MARKING_DATE,'"+ dateFormat +"'),a.AGENCY, d.description,a.rec_status  FROM "+tableName+" a inner join cr_loan_dtl b on b.loan_id=a.loan_id inner join gcd_customer_m c on c.customer_id=a.loan_customer_id inner join generic_master d on d.value=a.case_status and d.GENERIC_KEY='CASE_MARKING_FLAG'");
			
			bufInsSql.append("  WHERE a.loan_id='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo())+"'and a.rec_status!='X'");
			bufInsSql.append(" union ");
			bufInsSql.append(" SELECT null as case_id, max(a.loan_id),max(b.loan_no),max(c.customer_name),null as OTHER_DETAILS, null as REMARKS,'N' as CASE_MARKING_FLAG,null as CASE_MARKING_DATE,null as AGENCY,DESCRIPTION,max(a.rec_status) FROM GENERIC_MASTER G join "+tableName+" a on g.value<>a.case_status and LOAN_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo())+"' ");
			bufInsSql.append(" join cr_loan_dtl b on b.loan_id=a.loan_id inner join gcd_customer_m c on c.customer_id=a.loan_customer_id WHERE GENERIC_KEY='CASE_MARKING_FLAG' AND	G.VALUE NOT IN(SELECT CASE_STATUS FROM "+tableName+" WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo())+"') group by DESCRIPTION ");
			logger.info("search Query...." + bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			bufInsSql=null;
			
			CaseMarkingMakerVO caseTypeDataVo =null;
			int size=searchlist.size();
			for (int i = 0; i < size; i++) {
			
			       data = (ArrayList) searchlist.get(i);

					caseTypeDataVo = new CaseMarkingMakerVO();
					caseTypeDataVo.setCaseId(CommonFunction.checkNull(data.get(0)).toString());
					caseTypeDataVo.setLbxDealNo(CommonFunction.checkNull(data.get(1)).toString());
					caseTypeDataVo.setSearchLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					caseTypeDataVo.setSearchCustomerName(CommonFunction.checkNull(data.get(3)).toString());
					caseTypeDataVo.setOtherDetails(CommonFunction.checkNull(data.get(4)).toString());
									
					caseTypeDataVo.setRemarks(CommonFunction.checkNull(data.get(5)).toString());
					caseTypeDataVo.setChk(CommonFunction.checkNull(data.get(6)).toString());
					if((!(CommonFunction.checkNull(data.get(6)).toString().trim().equalsIgnoreCase("Y"))) || (CommonFunction.checkNull(data.get(10)).toString().trim().equalsIgnoreCase("P")))
						caseTypeDataVo.setMarkingDate(vo.getBusinessdate());
					else
						caseTypeDataVo.setMarkingDate(CommonFunction.checkNull(data.get(7)).toString());
					caseTypeDataVo.setAgency(CommonFunction.checkNull(data.get(8)).toString());
					caseTypeDataVo.setCaseStatus(CommonFunction.checkNull(data.get(9)).toString());
					caseTypeDataVo.setStatusCase(CommonFunction.checkNull(data.get(10)).toString());
					caseTypeList.add(caseTypeDataVo);
					caseTypeDataVo=null;
					data.clear();
					data=null;
}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			searchlist.clear();
			searchlist = null;
			vo=null;
			tableName=null;
			 }
		return caseTypeList;
}

	public boolean insertCrCaseMarkingDetails(CaseMarkingMakerVO vo,
			String[] checkList,String[] checkList1,String[] checkList2,String[] checkList3,String[] checkList4,String[] checkList5) {
		

		logger.info("In insertCrCaseMarkingDetails() of CaseMarkingMakerDAOImpl");
		
		
		ArrayList qryList1 = new ArrayList();
		boolean status =false;
		/*String result=null;
		String query=("select loan_id from cr_loan_dtl where loan_id='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo().trim()) + "'");
				
		logger.info("In insertCaseTypeMaster.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAO.checkStatus(query);*/
		try {
				
			 int length = checkList.length;
		
			for (int i = 0; i < length; i++) {

			
				StringBuilder bufInsSql = new StringBuilder();
				PrepStmtObject insPrepStmtObject=new PrepStmtObject();
				
				bufInsSql.append("insert  into cr_case_marking_tmp_dtl (loan_id,loan_customer_id,loan_amount,loan_overdue_amount,loan_balance_principal,LOAN_DPD,LOAN_TENURE,LOAN_STATUS,MAKER_ID,OTHER_DETAILS,REMARKS,CASE_MARKING_FLAG,CASE_MARKING_DATE,AGENCY,MAKER_DATE,REC_STATUS,CASE_STATUS)"+
						"(select loan_id,loan_customer_id,loan_loan_amount,loan_overdue_amount,loan_balance_principal,LOAN_DPD,LOAN_TENURE,REC_STATUS,?,?,?,?,dbo.STR_TO_DATE(?, '"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),?, dbo.STR_TO_DATE(?, '"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),'P',? from cr_loan_dtl where loan_id='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo().trim()) + "') ");
			
				if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
	
				if (CommonFunction.checkNull(checkList[i]).equalsIgnoreCase("$"))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString(checkList[i].toUpperCase());

				if (CommonFunction.checkNull(checkList1[i]).equalsIgnoreCase("$"))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString(checkList1[i].toUpperCase());
				
				if (CommonFunction.checkNull(checkList2[i]).equalsIgnoreCase(""))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString(checkList2[i].toUpperCase());
				
				if (CommonFunction.checkNull(checkList3[i]).equalsIgnoreCase("$"))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString(checkList3[i].toUpperCase());
				
				if (CommonFunction.checkNull(checkList4[i]).equalsIgnoreCase("$"))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkList4[i].toUpperCase()));
				
				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));	
				
				if (CommonFunction.checkNull(checkList5[i]).equalsIgnoreCase("$"))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString(checkList5[i].toUpperCase());
					insPrepStmtObject.setSql(bufInsSql.toString());
					bufInsSql=null;
					logger.info("IN insertCrCaseMarkingDetails() insert query1 ### "+ insPrepStmtObject.printQuery());
					qryList1.add(insPrepStmtObject);
					insPrepStmtObject=null;
			}
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					logger.info("Update Query: after null"+status);
		  }
		
			catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			checkList=null;
			checkList1=null;
			checkList2=null;
			checkList3=null;
			checkList4=null;
			checkList5=null;
		
			qryList1.clear();
			qryList1=null;
			vo=null;
	
			
		}
		return status;

	
		}

	public ArrayList<Object> forwardCaseMarkingMaker(CaseMarkingMakerVO vo) {
		logger.info("In forwardCaseMarkingMaker()");
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
				in.add(vo.getStatusCase());
				in.add(vo.getUserId());
				out.add(s1);
				out.add(s2);
				logger.info("CASE_MARKING_MAKER("+in.toString()+","+out.toString()+")");
				outMessages=(ArrayList) ConnectionDAO.callSP("CASE_MARKING_MAKER",in,out);
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


	public boolean updateCrCaseMarkingDetails(CaseMarkingMakerVO vo,
			String[] checkList, String[] checkList1, String[] checkList2,
			String[] checkList3, String[] checkList4, String[] checkList5,
			String[] checkList6) {
		
		logger.info("In updateCaseMarkingMaker..........inside ejb server file............Dao Impl");
		
		String tableName=null;
		String stat=null;
		ArrayList updatelist = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		boolean status = false;
		int exist_loan_id=0;
		String userNameQ1="select count(1) from CR_CASE_MARKING_TMP_DTL where LOAN_ID='"+vo.getLbxDealNo()+"'";
		exist_loan_id=Integer.parseInt(ConnectionDAO.singleReturn(userNameQ1));
		userNameQ1=null;

		try {
	     	if( !((CommonFunction.checkNull(vo.getStatusCase())).equalsIgnoreCase("A"))){
    			tableName="CR_CASE_MARKING_TMP_DTL";
    			stat = "P";
    		}
    		else{
    			tableName="CR_CASE_MARKING_DTL";
    			stat = "A";
    		}
	     	int length=0;
			length = checkList.length;
			
			PrepStmtObject insertPrepStmtObject=null;
			StringBuilder bufInsSql=null;
			for (int i = 0; i < length; i++) 
			{
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuilder();
				if (!(CommonFunction.checkNull(checkList6[i]).equalsIgnoreCase("$")))
				{
					bufInsSql.append(" UPDATE "+tableName+" SET MAKER_ID=?,OTHER_DETAILS=?,REMARKS=?,CASE_MARKING_FLAG=?,CASE_MARKING_DATE=" );
					bufInsSql.append("dbo.STR_TO_DATE(?, '"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
					bufInsSql.append(" MAKER_DATE=" );
					bufInsSql.append("dbo.STR_TO_DATE(?, '"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),REC_STATUS=?,AGENCY=? ");
					bufInsSql.append(" where LOAN_ID=? and case_id=? and rec_status in ('P','A')");
		
						if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
						else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));;
					
						if (CommonFunction.checkNull(checkList[i]).equalsIgnoreCase("$"))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(checkList[i].toUpperCase());
		
						if (CommonFunction.checkNull(checkList1[i]).equalsIgnoreCase("$"))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(checkList1[i].toUpperCase());
						
						if (CommonFunction.checkNull(checkList2[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(checkList2[i].toUpperCase());
					
						if (CommonFunction.checkNull(checkList3[i]).equalsIgnoreCase("$")){
							
							insertPrepStmtObject.addNull();}
						else
						{	
							insertPrepStmtObject.addString((CommonFunction.checkNull(checkList3[i])).trim());
						
						}
						if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));	
						
						if((CommonFunction.checkNull(stat).trim().equalsIgnoreCase("")))
							insertPrepStmtObject.addString(stat);
						else
							
						insertPrepStmtObject.addString(stat);
						
						if (CommonFunction.checkNull(checkList4[i]).equalsIgnoreCase("$"))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkList4[i].toUpperCase()));
						
						if (CommonFunction.checkNull(vo.getLbxDealNo()).equalsIgnoreCase(""))
						  insertPrepStmtObject.addNull();
					    else
						insertPrepStmtObject.addString(vo.getLbxDealNo());
						
						if (CommonFunction.checkNull(checkList6[i]).equalsIgnoreCase("$"))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(checkList6[i].toUpperCase());
		
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN updateCaseMarkingMaker() insert query1 ### "+ insertPrepStmtObject.printQuery());
					updatelist.add(insertPrepStmtObject);
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
				}
				else
				{
					bufInsSql.append("insert  into cr_case_marking_tmp_dtl (loan_id,loan_customer_id,loan_amount,loan_overdue_amount,loan_balance_principal,LOAN_DPD,LOAN_TENURE,LOAN_STATUS,MAKER_ID,OTHER_DETAILS,REMARKS,CASE_MARKING_FLAG,CASE_MARKING_DATE,AGENCY,MAKER_DATE,REC_STATUS,CASE_STATUS)"+
							"(select loan_id,loan_customer_id,loan_loan_amount,loan_overdue_amount,loan_balance_principal,LOAN_DPD,LOAN_TENURE,REC_STATUS,?,?,?,?,dbo.STR_TO_DATE(?, '"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),?, dbo.STR_TO_DATE(?, '"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),'P',? from cr_loan_dtl where loan_id='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo().trim()) + "') ");
				
					if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getUserId()).trim()));
		
					if (CommonFunction.checkNull(checkList[i]).equalsIgnoreCase("$"))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList[i].toUpperCase());

					if (CommonFunction.checkNull(checkList1[i]).equalsIgnoreCase("$"))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList1[i].toUpperCase());
					
					if (CommonFunction.checkNull(checkList2[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList2[i].toUpperCase());
					
					if (CommonFunction.checkNull(checkList3[i]).equalsIgnoreCase("$"))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList3[i].toUpperCase());
					
					if (CommonFunction.checkNull(checkList4[i]).equalsIgnoreCase("$"))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList4[i].toUpperCase());
					
					if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));	
					
					if (CommonFunction.checkNull(checkList5[i]).equalsIgnoreCase("$"))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList5[i].toUpperCase());
						insertPrepStmtObject.setSql(bufInsSql.toString());
						bufInsSql=null;
						logger.info("IN insertCrCaseMarkingDetails() insert query1 ### "+ insertPrepStmtObject.printQuery());
						qryList1.add(insertPrepStmtObject);
						
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
						logger.info("Update Query: after null"+status);
				}
				insertPrepStmtObject=null;
				bufInsSql=null;
			}
			if(exist_loan_id==0)
				{
			insertPrepStmtObject = new PrepStmtObject();
			 bufInsSql = new StringBuilder();
			
			bufInsSql.append("INSERT INTO CR_CASE_MARKING_TMP_DTL  (LOAN_ID,LOAN_CUSTOMER_ID,LOAN_AMOUNT,LOAN_TENURE,LOAN_DPD,LOAN_STATUS,LOAN_OVERDUE_AMOUNT,");
			bufInsSql.append("LOAN_BALANCE_PRINCIPAL,CASE_MARKING_FLAG,CASE_STATUS,CASE_MARKING_DATE,AGENCY,OTHER_DETAILS,");
			bufInsSql.append("REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,MAIN_CASE_ID,REC_STATUS)");
			bufInsSql.append("SELECT LOAN_ID,LOAN_CUSTOMER_ID,LOAN_AMOUNT,LOAN_TENURE,LOAN_DPD,LOAN_STATUS,LOAN_OVERDUE_AMOUNT,");
			bufInsSql.append("LOAN_BALANCE_PRINCIPAL,CASE_MARKING_FLAG,CASE_STATUS,CASE_MARKING_DATE,AGENCY,OTHER_DETAILS,");
			bufInsSql.append("REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,CASE_ID,'P' FROM CR_CASE_MARKING_DTL where LOAN_ID='"+vo.getLbxDealNo()+"'" );
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN updateCaseMarkingMaker() insert query1 ### "+ insertPrepStmtObject.printQuery());
			updatelist.add(insertPrepStmtObject);
			bufInsSql=null;
			insertPrepStmtObject=null;
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			checkList=null;	
			checkList1=null;	
			checkList2=null;	
			checkList3=null;	
			checkList4=null;	
			checkList5=null;	
			checkList6=null;
			updatelist.clear();
			updatelist=null;			
			qryList1.clear();
			qryList1=null;
			tableName=null;
			stat=null;
			
		}
		return status;
	}
	
	public String deleteCaseMarking(String caseMarkingLoanId,String tableStatus) {
		
		boolean status=false;
		String returnValue=null;
		logger.info("deleteCaseMarking");
		String tableName="CR_CASE_MARKING_TMP_DTL";
//		if( !((CommonFunction.checkNull(tableStatus)).equalsIgnoreCase("A"))){
//			tableName="CR_CASE_MARKING_TMP_DTL";
//		
//		}
//		else{
//			tableName="CR_CASE_MARKING_DTL";
//		
//		}
		StringBuilder query=new StringBuilder();
		query.append(" delete from "+tableName+" where LOAN_ID='"+CommonFunction.checkNull(caseMarkingLoanId)+"'");
		logger.info(" delete query: "+query.toString());
		caseMarkingLoanId=null;
		ArrayList list=new ArrayList();
		list.add(query);
		try {
			status=ConnectionDAO.sqlInsUpdDelete(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.clear();
		list=null;
		query=null;
		tableName=null;
		tableStatus=null;
	    if(status)
	    {
	    	returnValue="Y";
	    }
	    else
	    {
	    	returnValue="N";
	    }
		// TODO Auto-generated method stub
		return returnValue;
	}

	@Override
	public ArrayList<CaseMarkingMakerVO> searchCaseMarkingMaker(
			CaseMarkingMakerVO caseMarkingCheckerVO, String statusCase) {
		// TODO Auto-generated method stub
		return null;
	}
}

	
	

