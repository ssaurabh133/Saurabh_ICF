package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cm.dao.PdcViewerDao;
import com.cm.vo.PdcViewerVo;


public class MSSQLPdcViewerDAOImpl implements PdcViewerDao{
	
	
	private static final Logger logger = Logger.getLogger(MSSQLPdcViewerDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	
	public ArrayList<PdcViewerVo> searchPDCViewerData(PdcViewerVo vo) 
	{
		ArrayList<PdcViewerVo> searchDataList = new ArrayList<PdcViewerVo>();
		
		logger.info("In searchPDCViewerData ...::::::::::::In DAOImpl");
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		String countStr="";
		String userName="";
		
		
		try {
			logger.info(" vo.getLbxLoanNo()--------->"+vo.getLbxLoanNo());
			logger.info(" getInstrumenttype()--------->"+vo.getInstrumenttype());
			int count = 0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			//QUERY CHANGED BY NEERAJ TRIPATHI
			
			
			bufInsSql.append(" select PDC_LOAN_ID,(SELECT A.DESCRIPTION FROM generic_master A WHERE GENERIC_KEY='BPTYPE' AND A.VALUE  = PDC_BPTYPE) AS BPTYPE,CASE PDC_INSTRUMENT_MODE WHEN 'Q' THEN 'Cheque' WHEN 'C' THEN 'Cash' WHEN 'D' THEN 'Draft' WHEN 'R' THEN 'RTGS' WHEN 'N' THEN 'NEFT' WHEN 'DIR' THEN 'Direct Debit' WHEN 'E' THEN 'ECS' WHEN 'O' THEN 'Others' ELSE 'No' END AS PDC_INSTRUMENT_MODE,PDC_INSTL_NO,PDC_PURPOSE,PDC_INSTRUMENT_NO,(select A.BANK_NAME from com_bank_m A where A.BANK_ID=PDC_ISSUEING_BANK_ID) BANK_NAME,(SELECT A.BANK_BRANCH_NAME FROM com_bankbranch_m A WHERE A.BANK_BRANCH_ID=PDC_ISSUEING_BRANCH_ID) BRANCH_NAME,PDC_ISSUING_MICR_CODE, PDC_ISSUING_IFSC_CODE,PDC_INSTRUMENT_DATE,PDC_ISSUEING_BANK_ACCOUNT,PDC_INSTRUMENT_AMOUNT,PDC_LOCATION,PDC_REMARKS,generic_master.DESCRIPTION,(select user_name from sec_user_m where USER_ID=cr_pdc_instrument_dtl.maker_id) as maker,(select user_name from sec_user_m where USER_ID=cr_pdc_instrument_dtl.author_id)as author,NAME_OF_PDC_SUBMIT_BY   ");
			bufInsSql.append(",CASE WHEN PDC_CLEARING_TYPE='C' then 'Clean' WHEN PDC_CLEARING_TYPE='S' then 'CTS' WHEN PDC_CLEARING_TYPE='E' THEN 'Express' WHEN PDC_CLEARING_TYPE='I' THEN 'Inter Bank' WHEN PDC_CLEARING_TYPE='L' THEN 'Local' WHEN PDC_CLEARING_TYPE='N' THEN 'New' WHEN PDC_CLEARING_TYPE='M' THEN 'NMM' WHEN PDC_CLEARING_TYPE='O' THEN 'OutStation' WHEN PDC_CLEARING_TYPE='T' THEN 'Transfer' ELSE 'NA' END AS PDC_CLEARING_TYPE");
			bufInsSql.append(",(ISNULL(cr.INSTL_AMOUNT,0)+ISNULL(cr.OTHER_CHARGES,0)) as TOTAL_EMI,MAKER_REMARKS,dbo.DATE_FORMAT(cr_pdc_instrument_dtl.MAKER_DATE,'%d-%m-%Y'),dbo.DATE_FORMAT(cr_pdc_instrument_dtl.AUTHOR_DATE,'%d-%m-%Y') ");
			bufInsSql.append(" from cr_pdc_instrument_dtl LEFT JOIN cr_repaysch_dtl cr ON(cr_pdc_instrument_dtl.PDC_LOAN_ID=CR.LOAN_ID AND cr_pdc_instrument_dtl.PDC_INSTL_NO=CR.INSTL_NO ) LEFT JOIN generic_master ON(generic_master.VALUE=cr_pdc_instrument_dtl.SUBMIT_BY AND GENERIC_KEY='CUST_ROLE' and generic_master.REC_STATUS='A')where PDC_INSTRUMENT_TYPE ='R'   AND PDC_LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo()).trim()+"'");
			bufInsSqlTempCount.append("select count(1) from cr_pdc_instrument_dtl LEFT JOIN cr_repaysch_dtl cr ON(cr_pdc_instrument_dtl.PDC_LOAN_ID=CR.LOAN_ID AND cr_pdc_instrument_dtl.PDC_INSTL_NO=CR.INSTL_NO ) LEFT JOIN generic_master ON(generic_master.VALUE=cr_pdc_instrument_dtl.SUBMIT_BY AND GENERIC_KEY='CUST_ROLE' and generic_master.REC_STATUS='A') where PDC_INSTRUMENT_TYPE ='R' AND PDC_LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo()).trim()+"'");
					
			if(!CommonFunction.checkNull(vo.getStatus()).trim().equals(""))
			{
				bufInsSql.append(" AND PDC_STATUS='"+vo.getStatus()+"'");
				bufInsSqlTempCount.append(" AND PDC_STATUS='"+vo.getStatus()+"'");
			}
			logger.info("In searchPDCViewerData   Search Query for count : " + bufInsSqlTempCount.toString());
			countStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("Count String   : "+countStr);
			if(countStr.equalsIgnoreCase(""))
				count=0;
			else
				count = Integer.parseInt(countStr);
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex ...................... "+endRecordIndex);
			}
			
			bufInsSql.append(" ORDER BY PDC_LOAN_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
		//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			
  			
			logger.info("In searchPDCViewerData   Search Query  : " + bufInsSql.toString());
			mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			// Logic for paging ends
						
			int size = mainlist.size();
			logger.info("In searchPDCViewerData.....mainlist size: "+ size);
			PdcViewerVo pVo=null;
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) 
				{
					
					pVo= new PdcViewerVo();					
					pVo.setShowloan(CommonFunction.checkNull(subList.get(0)).trim());
					pVo.setLbxLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
					pVo.setBptye(CommonFunction.checkNull(subList.get(1)).trim());
					pVo.setInst_mode(CommonFunction.checkNull(subList.get(2)).trim());
					pVo.setInst_no(CommonFunction.checkNull(subList.get(3)).trim());
					pVo.setPurpose(CommonFunction.checkNull(subList.get(4)).trim());					
					pVo.setInstr_no((CommonFunction.checkNull(subList.get(5)).trim()));
					pVo.setBank_name((CommonFunction.checkNull(subList.get(6)).trim()));
					pVo.setBranch_name((CommonFunction.checkNull(subList.get(7)).trim()));
					pVo.setMicr_code((CommonFunction.checkNull(subList.get(8)).trim()));
					pVo.setIfsc_code((CommonFunction.checkNull(subList.get(9)).trim()));
					pVo.setInstr_date((CommonFunction.checkNull(subList.get(10)).trim()));
					pVo.setBank_ac((CommonFunction.checkNull(subList.get(11)).trim()));
					pVo.setInstr_amt((CommonFunction.checkNull(subList.get(12)).trim()));
					pVo.setLocation((CommonFunction.checkNull(subList.get(13)).trim()));
					pVo.setRemarks((CommonFunction.checkNull(subList.get(14)).trim()));
					pVo.setSubmitBy((CommonFunction.checkNull(subList.get(15)).trim()));
					pVo.setMaker((CommonFunction.checkNull(subList.get(16)).trim()));
					pVo.setAuthor((CommonFunction.checkNull(subList.get(17)).trim()));
					pVo.setPdcSubmitCustomerName((CommonFunction.checkNull(subList.get(18)).trim()));
					pVo.setClearingType((CommonFunction.checkNull(subList.get(19)).trim()));
					Number totalEMI =myFormatter.parse((CommonFunction.checkNull(subList.get(20))).trim());
					pVo.setTotalEMI(myFormatter.format(totalEMI));
					pVo.setMakerRemarks((CommonFunction.checkNull(subList.get(21)).trim()));
					pVo.setMakerDate((CommonFunction.checkNull(subList.get(22)).trim()));
					pVo.setAuthorDate((CommonFunction.checkNull(subList.get(23)).trim()));
					pVo.setTotalRecordSize(count);
					searchDataList.add(pVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			vo = null;
			mainlist= null;
			subList = null;
			bufInsSql= null;
			bufInsSqlTempCount = null;
			countStr= null;
			
		}
		
		return searchDataList;
	}
	public ArrayList calculatePDC(String loanId) 
	{

		logger.info("In calculatePDC()   loanId  :  "+loanId);
		ArrayList list = new ArrayList();		
		
		String presented=null;
		String toBePresented=null;
		StringBuilder bufInsSql1=new StringBuilder();
		StringBuilder bufInsSql2=new StringBuilder();
				
		try 
		{
			bufInsSql1.append("select count(1) from cr_pdc_instrument_dtl where  PRESENTATION_DATE is null and PDC_LOAN_ID="+loanId);
			bufInsSql2.append("select count(1) from cr_pdc_instrument_dtl where  PRESENTATION_DATE is not null and PDC_LOAN_ID="+loanId);
			
			logger.info("In calculatePDC PRESENTATION_DATE is null Query       :  " + bufInsSql1.toString());
			logger.info("In calculatePDC PRESENTATION_DATE is not null Query   :  " + bufInsSql2.toString());
			
			toBePresented  = ConnectionDAO.singleReturn(bufInsSql1.toString());
			presented = ConnectionDAO.singleReturn(bufInsSql2.toString());
		
			list.add(presented);
			list.add(toBePresented);
			toBePresented=null;
			presented=null;
			bufInsSql1=null;
			bufInsSql1=null;
		}
		catch (Exception e) 
		{e.printStackTrace();}
		finally
		{
			bufInsSql1=null;
			bufInsSql1=null;
		}
		return list;
	}
	
	
					
	
	
}