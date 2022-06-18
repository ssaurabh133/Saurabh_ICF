package com.cp.daoImplMYSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.dao.CreditApprovalDAO;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.CreditApprovalVo;

public  class CreditApprovalDAOImpl implements CreditApprovalDAO {

	private static final Logger logger = Logger.getLogger(CreditApprovalDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
	
	public ArrayList getCreditApprovalDetails(CommonDealVo vo) {
		
		
		ArrayList<Object> list=new ArrayList<Object>();
		ArrayList data=null;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		int countt = 0;
		try
		{
			StringBuilder query=new StringBuilder();
			StringBuilder count=new StringBuilder();
			query.append("SELECT DISTINCT d.deal_id,D.APPLICATION_REFERENCE_NO,C.CUSTOMER_NAME,p.product_desc, s.scheme_desc, user_name"+
					// " FROM CR_deal_DTL D"+ 
					" FROM (select deal_id, APPLICATION_REFERENCE_NO, deal_CUSTOMER_ID,product,scheme, MAKER_ID from CR_deal_DTL  where REC_STATUS='F'  and deal_branch = '"+vo.getBranchId()+"' )d  "
					+ " JOIN CR_deal_CUSTOMER_M C ON D.deal_CUSTOMER_ID=C.CUSTOMER_ID"+
					//"  join cr_bank_analysis_dtl b on b.deal_id=d.deal_id and b.rec_status='A'"+
					//"  join cr_financial_data_dtl f on f.deal_id=d.deal_id and f.rec_status='A'"+
					" left join cr_product_m p on p.PRODUCT_ID=d.product"+
					" left join cr_scheme_m s on s.SCHEME_ID = d.scheme"+
					" left join sec_user_m u on u.user_id=d.MAKER_ID "+ 
					" left join cr_deal_movement_dtl CM on cm.deal_id = d.deal_id"+
					" WHERE  "
					+ "cm.deal_STAGE_ID = 'CA' and cm.deal_FORWARDED = '0000-00-00 00:00:00'"
					+ " AND CM.deal_FORWARD_USER = '' and cm.deal_RECEIVED <> '0000-00-00 00:00:00' "
					+ "and CM.deal_RECEIVED_USER <> ''"
					/*+ " exists (select 1 from cr_bank_analysis_dtl b where b.deal_id = D.deal_id and b.REC_STATUS='A' limit 1)"
					+ " and exists (select 1 from cr_financial_data_dtl f where f.deal_id = D.deal_id and f.REC_STATUS='A' limit 1)"*/
					);
			
			count.append("SELECT count(DISTINCT d.deal_id)"+
					// " FROM CR_deal_DTL D"+ 
					" FROM (select deal_id, APPLICATION_REFERENCE_NO, deal_CUSTOMER_ID,product,scheme, MAKER_ID from CR_deal_DTL  where REC_STATUS='F'  and deal_branch = '"+vo.getBranchId()+"' )d  "
					+ " JOIN CR_deal_CUSTOMER_M C ON D.deal_CUSTOMER_ID=C.CUSTOMER_ID"+
					//"  join cr_bank_analysis_dtl b on b.deal_id=d.deal_id and b.rec_status='A'"+
					//"  join cr_financial_data_dtl f on f.deal_id=d.deal_id and f.rec_status='A'"+
					" left join cr_product_m p on p.PRODUCT_ID=d.product"+
					" left join cr_scheme_m s on s.SCHEME_ID = d.scheme"+
					" left join sec_user_m u on u.user_id=d.MAKER_ID"+
					" left join cr_deal_movement_dtl CM on cm.deal_id = d.deal_id"+
					" WHERE  "
					+ "cm.deal_STAGE_ID = 'CA' and cm.deal_FORWARDED = '0000-00-00 00:00:00'"
					+ " AND CM.deal_FORWARD_USER = '' and cm.deal_RECEIVED <> '0000-00-00 00:00:00' "
					+ "and CM.deal_RECEIVED_USER <> ''"
					/*+ " exists (select 1 from cr_bank_analysis_dtl b where b.deal_id = D.deal_id and b.REC_STATUS='A' limit 1)"
					+ " and exists (select 1 from cr_financial_data_dtl f where f.deal_id = D.deal_id and f.REC_STATUS='A' limit 1)"*/
					);
		
		if(!CommonFunction.checkNull(vo.getApplicationNo()).equalsIgnoreCase(""))
			{
				query.append(" and D.APPLICATION_REFERENCE_NO = '"+vo.getApplicationNo()+"'");
				count.append(" and D.APPLICATION_REFERENCE_NO = '"+vo.getApplicationNo()+"'");
			}
		if(!CommonFunction.checkNull(vo.getProduct()).equalsIgnoreCase(""))
		{
			query.append(" and p.product_desc like '%"+vo.getProduct()+"%' ");
			count.append(" and p.product_desc like '%"+vo.getProduct()+"%' ");
		}
		if(!CommonFunction.checkNull(vo.getCustomername()).equalsIgnoreCase(""))
		{
			query.append(" and C.CUSTOMER_NAME like '%"+vo.getCustomername()+"%' ");
			count.append(" and C.CUSTOMER_NAME like '%"+vo.getCustomername()+"%' ");
		}
		if(!CommonFunction.checkNull(vo.getScheme()).equalsIgnoreCase(""))
		{
			query.append(" and s.scheme_desc like '%"+vo.getScheme()+"%' ");
			count.append(" and s.scheme_desc like '%"+vo.getScheme()+"%' ");
		}
		if(!CommonFunction.checkNull(vo.getUserName()).equalsIgnoreCase(""))
		{
			query.append(" and user_name = '"+vo.getUserName()+"'");
			count.append(" and user_name = '"+vo.getUserName()+"'");
		}
		/*if(!CommonFunction.checkNull(vo.getBranchId()).equalsIgnoreCase(""))
		{
			query.append(" and d.deal_branch = '"+vo.getBranchId()+"'");
		}
		*/
		if (vo.getCurrentPageLink() > 1) {
			startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
			endRecordIndex = no;
			// logger.info("startRecordIndex .................... "+startRecordIndex);
			// logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		query.append(" limit " + startRecordIndex + ","
				+ endRecordIndex);
		
		countt = Integer.parseInt(ConnectionDAO.singleReturn(count.toString()));
		logger.info("In CreditApprovalSearch...............query...........DAOImpl "+query.toString());
		logger.info("In CreditApprovalSearch...............count query...........DAOImpl "+count.toString());
		logger.info("In CreditApprovalSearch...............count "+countt);
		CreditApprovalVo crAppVo = null;
		ArrayList product = ConnectionDAO.sqlSelect(query.toString());	
		query=null;
		for(int i=0;i<product.size();i++){
			
			 data=(ArrayList)product.get(i);
			if(data.size()>0)	{
				crAppVo=new CreditApprovalVo();
				crAppVo.setCaseId((CommonFunction.checkNull(data.get(0))).trim());
				crAppVo.setApplicationNo("<a href=creditApprovalAction.do?method=viewCustomerDemographyCapturing&caseId="+ CommonFunction.checkNull(data.get(0)).trim()+ " >"
				+CommonFunction.checkNull(data.get(1)).trim()+"");
				
				crAppVo.setCustomername((CommonFunction.checkNull(data.get(2))).trim());
				crAppVo.setProduct((CommonFunction.checkNull(data.get(3))).trim());
				crAppVo.setScheme((CommonFunction.checkNull(data.get(4))).trim());
				crAppVo.setUserName((CommonFunction.checkNull(data.get(5))).trim());
				//crAppVo.setTotalRecordsSize(product.size());
				//uwDocVo.setFileName(CommonFunction.checkNull(data.get(5)).trim());
				crAppVo.setTotalRecordsSize(countt);
				list.add(crAppVo);
				crAppVo=null;
			}
		}
		
		
	/*	
logger.info("tnxId===================================================================="+dealId);
		
		logger.info("In ViewerSearch...............query...........DAOImpl"+query.toString());
		UnderwritingDocUploadVo uwDocVo = null;
		ArrayList product = ConnectionDAO.sqlSelect(query.toString());
		//logger.info("getUploadUnderwritingData size of list Product "+product.size());
		query=null;
		for(int i=0;i<product.size();i++){
			
			 data=(ArrayList)product.get(i);
			if(data.size()>0)	{
				uwDocVo=new UnderwritingDocUploadVo();
				uwDocVo.setDealId((CommonFunction.checkNull(data.get(0))).trim());
				uwDocVo.setDocEntity((CommonFunction.checkNull(data.get(1))).trim());
				//uwDocVo.setDocDescription((CommonFunction.checkNull(data.get(2))).trim());
				uwDocVo.setUserName((CommonFunction.checkNull(data.get(2))).trim());
				uwDocVo.setCustomerName((CommonFunction.checkNull(data.get(3))).trim());
				//uwDocVo.setUploadedState((CommonFunction.checkNull(data.get(4))).trim());
				uwDocVo.setCustRef((CommonFunction.checkNull(data.get(4))).trim());
				//uwDocVo.setFileName(CommonFunction.checkNull(data.get(5)).trim());
				list.add(uwDocVo);
				uwDocVo=null;
			}
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		
		data=null;
		
	}

	return list;
}*/
		
		
		
		
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("CreditApproval size is .................................."+list.size());
		return list; 
	}

	@Override
	public boolean getCreditApprovalUpdate(String caseId,
			CreditApprovalVo vo) {
		
		boolean status=false;
		ArrayList<Object> list=new ArrayList<Object>();
		ArrayList data= new ArrayList();
		try
		{
			StringBuilder query=new StringBuilder();
			if(!CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("P"))
			{
				query.append("UPDATE cr_deal_dtl set rec_status='"+CommonFunction.checkNull(vo.getDecision())+"' where deal_id ='"+caseId+"' ");
				logger.info("In CreditApprovalSearch...............query...........DAOImpl "+query.toString());
			
				data.add(query.toString());
				status = ConnectionDAO.sqlInsUpdDelete(data);
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		logger.info("CreditApproval size is .................................."+list.size());
		return status; 
		

	}
	@Override
	public ArrayList<CodeDescVo> getCaseMovementList(String caseId) {
		logger.info("getCaseMovementList ..................................");
		String qry = "select distinct m.deal_stage_id,s.stage_desc from cr_deal_movement_dtl m left join cr_stage_m s on s.stage_id = m.deal_stage_id "
				+ "where deal_id = '"+caseId+"' and deal_FORWARDED <> '0000-00-00 00:00:00' and deal_RECEIVED <> '0000-00-00 00:00:00' ";
		logger.info("select qry --"+qry);
		ArrayList list = null;
		ArrayList sublist = null;
		ArrayList<CodeDescVo> ReturnList  = new ArrayList<CodeDescVo>();
		CodeDescVo vo = null;
		try {
			 list = ConnectionDAO.sqlSelect(qry);
			 int size = list==null?0:list.size();
			 for (int i = 0; i < size; i++) {
				sublist =  (ArrayList)list.get(i);
				if(sublist.size()>0)
				{
					vo = new CodeDescVo();
					vo.setStageCode(CommonFunction.checkNull(sublist.get(0)));
					vo.setStageDesc(CommonFunction.checkNull(sublist.get(1)));
				}
				ReturnList.add(vo);
			}
			 
			 
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ReturnList;
	}
	
	

	
	
}
