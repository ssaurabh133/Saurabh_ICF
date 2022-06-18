package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.cm.dao.GenerateBatchDAO;
import com.cm.vo.GenerateBatchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;


public class GenerateBatchDAOImpl implements GenerateBatchDAO {
	private static final Logger logger = Logger.getLogger(GenerateBatchDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeGenerateBatch"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
		
	public String generateBatch(GenerateBatchVO vo) 
	{
		
		   
			logger.info("in generateBatch() of GenerateBatchDAOImpl.");
			String retVal = "error";
			ArrayList list=new ArrayList();
			StringBuilder bufInsSql =	new StringBuilder();
			try
			{
				bufInsSql.append("( SELECT  distinct a.PDC_LOAN_ID,l.LOAN_NO,a.PDC_INSTRUMENT_ID,a.PDC_INSTRUMENT_AMOUNT " +
						" FROM CR_PDC_INSTRUMENT_DTL a " +
						" JOIN CR_LOAN_DTL l ON A.PDC_LOAN_ID = l.LOAN_ID AND l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS = 'F' " +
						" JOIN CR_REPAYSCH_DTL re ON re.LOAN_ID = A.PDC_LOAN_ID AND re.INSTL_NO = A.PDC_INSTL_NO AND re.REC_STATUS = 'A' AND re.INSTL_DATE = STR_TO_DATE('"+vo.getPrestDate().trim()+"','"+dateFormat+"') " +
						" JOIN cr_deal_dtl d ON d.DEAL_ID=l.LOAN_DEAL_ID " +
						" LEFT JOIN CR_GENERATE_BATCH_DTL GB ON(GB.LOAN_ID=A.PDC_LOAN_ID AND GB.INSTRUMENT_ID=PDC_INSTRUMENT_ID)"+
						" WHERE PDC_STATUS = 'A' AND A.PDC_PURPOSE IN ('INSTALLMENT') " +						
						" AND A.PRESENTATION_DATE IS NULL AND IFNULL(GB.REC_STATUS,'N') NOT IN('P','F')" +
						" AND PDC_INSTRUMENT_DATE = STR_TO_DATE('"+vo.getPrestDate().trim()+"','"+dateFormat+"') and l.loan_no not in (SELECT LOAN_NO FROM TEMP_GENERATE_BATCH) ");
				
				if(!vo.getPaymentMode().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND a.pdc_instrument_mode='"+vo.getPaymentMode().trim()+"'");
				if(!vo.getClearingType().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND a.pdc_clearing_type='"+vo.getClearingType().trim()+"'");
				if(!vo.getLbxBranchId().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND l.LOAN_BRANCH='"+vo.getLbxBranchId().trim()+"'");
				if(!vo.getLoanCategory().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND d.DEAL_CATEGORY='"+vo.getLoanCategory().trim()+"'");
				if(!vo.getBatchSize().trim().equalsIgnoreCase(""))
					bufInsSql.append("limit 0,"+vo.getBatchSize().trim());  
				
				bufInsSql.append(")    UNION    (");
				bufInsSql.append(" SELECT  distinct a.PDC_LOAN_ID,l.LOAN_NO,a.PDC_INSTRUMENT_ID,a.PDC_INSTRUMENT_AMOUNT " +
						" FROM CR_PDC_INSTRUMENT_DTL a " +
						" JOIN CR_LOAN_DTL l ON A.PDC_LOAN_ID = l.LOAN_ID AND l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS IN('P', 'F') " +
						" JOIN cr_deal_dtl d ON d.DEAL_ID=l.LOAN_DEAL_ID " +
						" LEFT JOIN CR_GENERATE_BATCH_DTL GB ON(GB.LOAN_ID=A.PDC_LOAN_ID AND GB.INSTRUMENT_ID=PDC_INSTRUMENT_ID)"+
						" WHERE PDC_STATUS = 'A' AND A.PDC_PURPOSE IN ('PRE EMI') " +
						" AND A.PRESENTATION_DATE IS NULL AND IFNULL(GB.REC_STATUS,'N') NOT IN('P','F')" +
						" AND PDC_INSTRUMENT_DATE = STR_TO_DATE('"+vo.getPrestDate().trim()+"','"+dateFormat+"')  and l.loan_no not in (SELECT LOAN_NO FROM TEMP_GENERATE_BATCH) ");
				
				if(!vo.getPaymentMode().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND a.pdc_instrument_mode='"+vo.getPaymentMode().trim()+"'");
				if(!vo.getClearingType().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND a.pdc_clearing_type='"+vo.getClearingType().trim()+"'");
				if(!vo.getLbxBranchId().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND l.LOAN_BRANCH='"+vo.getLbxBranchId().trim()+"'");
				if(!vo.getLoanCategory().trim().equalsIgnoreCase(""))
					bufInsSql.append(" AND d.DEAL_CATEGORY='"+vo.getLoanCategory().trim()+"'");
				if(!vo.getBatchSize().trim().equalsIgnoreCase(""))
					bufInsSql.append("limit 0,"+vo.getBatchSize().trim());
				
				bufInsSql.append(" )");
				logger.info("in generateBatch() Query  :  "+bufInsSql.toString());
				list = ConnectionDAO.sqlSelect(bufInsSql.toString());
				int size = list.size();
	            logger.info("size: "+size);
	            if(size==0)
	            	retVal="empty";
	            if(size>0)
	            {
	            	String batch_no="";
	            	
	            	String mode=vo.getPaymentMode();	            	
	            	if(mode.trim().equalsIgnoreCase("Q"))
	            		batch_no="CHQ";
	            	else if(mode.trim().equalsIgnoreCase("DIR"))
	            		batch_no="DIR";
	            	else if(mode.trim().equalsIgnoreCase("E"))
	            		batch_no="ECS";
	            	else if(mode.trim().equalsIgnoreCase("H"))
	            		batch_no="ACH";
	            	String clearingType=vo.getClearingType();
	            	if(clearingType.trim().equalsIgnoreCase("I"))
	            		batch_no=batch_no+"ICICI";
	            	else if(clearingType.trim().equalsIgnoreCase("L"))
	            		batch_no=batch_no+"LOCAL";
	            	else if(clearingType.trim().equalsIgnoreCase("O"))
	            		batch_no=batch_no+"IDBIT";
	            	else if(clearingType.trim().equalsIgnoreCase("C"))
	            		batch_no=batch_no+"CLEAN";
	            	else if(clearingType.trim().equalsIgnoreCase("E"))
	            		batch_no=batch_no+"EXPRE";
	            	else if(clearingType.trim().equalsIgnoreCase("N"))
	            		batch_no=batch_no+"NEWCS";
	            	else if(clearingType.trim().equalsIgnoreCase("M"))
	            		batch_no=batch_no+"NMMCS";
	            	else if(clearingType.trim().equalsIgnoreCase("T"))
	            		batch_no=batch_no+"TRANS";
	            	else if(clearingType.trim().equalsIgnoreCase("Q"))
	            		batch_no=batch_no+"BLNCT";
	            	else if(clearingType.trim().equalsIgnoreCase("R"))
	            		batch_no=batch_no+"BLOCA";
	            	else if(clearingType.trim().equalsIgnoreCase("D"))
	            		batch_no=batch_no+"HDFCT";
	            	else if(clearingType.trim().equalsIgnoreCase("P"))
	            		batch_no=batch_no+"LNCTS";
	            	
	            	
	            	String date=vo.getPrestDate();
	            	date=date.replace("-","_");
	            	batch_no=batch_no+date;
	            	
	            	logger.info("batch_no   :  "+batch_no);
	            	//String query="select BATCH_NO from cr_generate_batch_dtl where BATCH_ID =(SELECT MAX(BATCH_ID) FROM cr_generate_batch_dtl WHERE BATCH_NO LIKE '%"+batch_no+"%')";
	            	String query="select BATCH_NO from cr_generate_batch_dtl where BATCH_ID =(SELECT MAX(BATCH_ID) FROM cr_generate_batch_dtl WHERE BATCH_NO LIKE '%"+batch_no+"%' and BATCH_NO not in (SELECT BATCH_NO FROM cr_generate_batch_dtl where BATCH_NO LIKE '%_S_%' ))";
	            	logger.info("in generateBatch() Query for find max batch  :  "+bufInsSql.toString());
	            	String maxBatch=ConnectionDAO.singleReturn(query);
	            	logger.info("in generateBatch() maxBatch  :  "+maxBatch);
	            	if(CommonFunction.checkNull(maxBatch).equalsIgnoreCase(""))
	            		batch_no=batch_no+"_1";
	            	else
	            	{
	            		int val=Integer.parseInt(maxBatch.substring(19));
	            		val++;
	            		batch_no=batch_no+"_"+val;	            		
	            	}
	            	logger.info("in generateBatch() final BatchNO  :  "+batch_no);
	            	
	            	ArrayList qryList=new ArrayList();
	            	for(int i=0;i<size;i++)
	            	{
	            		ArrayList subList=(ArrayList)list.get(i);
	            		StringBuilder insertQuery =	new StringBuilder();
	            		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	            		insertQuery.append("insert into cr_generate_batch_dtl (BATCH_NO,LOAN_ID,LOAN_NO,INSTRUMENT_ID,PRESENTATION_DATE,INSTRUMENT_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)values(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
	            		
	            			insertPrepStmtObject.addString((batch_no).trim());   // BATCH_No
	            		
	            		if(CommonFunction.checkNull(subList.get(0)).trim().equalsIgnoreCase(""))// loan Id
	       				 	insertPrepStmtObject.addNull();
	       			 	else
	       			 		insertPrepStmtObject.addInt(Integer.parseInt((String)subList.get(0)));   
	            		
	            		if(CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase("")) // loan No
		       				insertPrepStmtObject.addNull();
		       			else
		       				insertPrepStmtObject.addString(((String)subList.get(1)).trim());  
	            		
	            		if(CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))//instrument_id
	            			insertPrepStmtObject.addNull();
		       			else
		       			    insertPrepStmtObject.addInt(Integer.parseInt((String)subList.get(2)));
	            		
	            		insertPrepStmtObject.addString(vo.getPrestDate().trim());//PRESENTATION_DATE
	            		
	            		if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))//PDC_INSTRUMENT_AMOUNT
	            			insertPrepStmtObject.addNull();
		       			else
		       			    insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(3)));
	            		
	            		
	            		insertPrepStmtObject.addString(("P").trim());   // Rec_Status
	            	
	            		// mradul
	            		insertPrepStmtObject.addString((vo.getUserId()).trim());// maker_id
	            		insertPrepStmtObject.addString((vo.getBusinessDate()).trim());// maker date
	            		
	            		insertPrepStmtObject.setSql(insertQuery.toString());
	            		qryList.add(insertPrepStmtObject);
	            		subList=null;
	            		insertQuery=null;
	            		insertPrepStmtObject=null;	            		
	            	}
	            	boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	            	if(status)
	            		retVal=batch_no;
	            	else
	            		retVal="error";
	            		
	            }

			}catch(Exception e){
				retVal="error";
				e.printStackTrace();
			}
			finally
			{
				bufInsSql=null;
				list=null;
			}
		
		return retVal;
	}
	public ArrayList<GenerateBatchVO> viewBatch(String batchNo,int currentPageLink) 
	{
		logger.info("in viewBatch() of GenerateBatchDAOImpl.");
		ArrayList searchlist=new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		StringBuilder bufInsSql =	new StringBuilder();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		ArrayList data= null;
		GenerateBatchVO vo1= null;
		
		ArrayList<GenerateBatchVO> detailList=new ArrayList();
	    try
	    {
			bufInsSql.append("SELECT a.BATCH_ID,a.BATCH_NO,a.LOAN_NO,a.INSTRUMENT_ID,b.PDC_INSTRUMENT_NO,case b.PDC_INSTRUMENT_MODE when 'Q' then 'Cheque' when 'DIR' then 'Direct Debit' when 'H' then 'ACH' when 'E' then 'ECS' else 'Other' end as PDC_INSTRUMENT_MODE,b.PDC_INSTRUMENT_AMOUNT,DATE_FORMAT(b.PDC_INSTRUMENT_DATE,'"+dateFormat+"') as PDC_INSTRUMENT_DATE,b.PDC_INSTL_NO,b.PDC_LOCATION,case a.REC_STATUS when 'P' then 'Pending for Finalized' when 'F' then 'Finalized' when 'A' then 'Approved' end as REC_STATUS,(select USER_NAME from sec_user_m where user_id=a.MAKER_ID)MAKER_ID,DATE_FORMAT(date(a.MAKER_DATE),'%d-%m-%Y')as MAKER_DATE FROM cr_generate_batch_dtl a join cr_pdc_instrument_dtl b on(a.INSTRUMENT_ID=b.PDC_INSTRUMENT_ID and a.loan_id=b.PDC_LOAN_ID) WHERE BATCH_NO='"+batchNo+"' order by a.loan_id ");
			bufInsSqlTempCount.append("select count(1) from (SELECT a.BATCH_ID,a.BATCH_NO,a.LOAN_NO,a.INSTRUMENT_ID,b.PDC_INSTRUMENT_NO,case b.PDC_INSTRUMENT_MODE when 'Q' then 'Cheque' when 'DIR' then 'Direct Debit'  when 'H' then 'ACH' when 'E' then 'ECS' else 'Other' end as PDC_INSTRUMENT_MODE,b.PDC_INSTRUMENT_AMOUNT,DATE_FORMAT(b.PDC_INSTRUMENT_DATE,'"+dateFormat+"') as PDC_INSTRUMENT_DATE,b.PDC_INSTL_NO,b.PDC_LOCATION,case a.REC_STATUS when 'P' then 'Pending for Finalized' when 'F' then 'Finalized' when 'A' then 'Approved' end as REC_STATUS FROM cr_generate_batch_dtl a join cr_pdc_instrument_dtl b on(a.INSTRUMENT_ID=b.PDC_INSTRUMENT_ID and a.loan_id=b.PDC_LOAN_ID) WHERE BATCH_NO='"+batchNo+"')AS B");
						
			logger.info("current PAge Link no .................... "+currentPageLink);
			if(currentPageLink>1)
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			logger.info("in viewBatch() Search Query  :  "+bufInsSql.toString());
			logger.info("in viewBatch() Count Query  :  "+bufInsSqlTempCount.toString());			
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
            logger.info("in viewBatch() Size  :   "+size);
            for(int i=0;i<size;i++)
            {
               data=(ArrayList)searchlist.get(i);
               if(data.size()>0)
               {
            	   vo1= new GenerateBatchVO(); 
            	   vo1.setCheckBox("<input type='checkbox' name='chkCases' onclick='calculateInstrumentNOAmount();'  id='chkCases' value='"+(CommonFunction.checkNull(data.get(0)).trim())+"'/>");
            	   vo1.setCheckBoxDis("<input type='checkbox' name='chkCases1'  id='chkCases1' disabled='disabled'/>");
            	   vo1.setLbxBatchNo((CommonFunction.checkNull(data.get(0)).trim()));
            	   vo1.setBatchNo((CommonFunction.checkNull(data.get(1)).trim()));
            	   vo1.setLoanNo((CommonFunction.checkNull(data.get(2)).trim()));
            	   vo1.setInstrumentId((CommonFunction.checkNull(data.get(3)).trim()));
            	   vo1.setInstrumentNo((CommonFunction.checkNull(data.get(4)).trim()));
            	   vo1.setInstrumentMode((CommonFunction.checkNull(data.get(5)).trim()));
            	   if(CommonFunction.checkNull(data.get(6)).equalsIgnoreCase(""))
            		   vo1.setInstrumentAmt("0.00");
            	   else
            	   {
            			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
            			vo1.setInstrumentAmt(myFormatter.format(reconNum));
            	   }
   	    	   	   vo1.setInstrumentDate((CommonFunction.checkNull(data.get(7)).trim()));
            	   vo1.setInstallmentNo((CommonFunction.checkNull(data.get(8)).trim()));
            	   vo1.setPdcLocation((CommonFunction.checkNull(data.get(9)).trim()));
            	   vo1.setRecordStatus((CommonFunction.checkNull(data.get(10)).trim()));
            	   vo1.setMakerID((CommonFunction.checkNull(data.get(11)).trim()));
            	   vo1.setMakerDate((CommonFunction.checkNull(data.get(12)).trim()));
            	   vo1.setTotalRecordSize(count);
            	   detailList.add(vo1);
               }
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			searchlist= null;
			bufInsSql = null;
			data= null;
			vo1= null;
		}
		return detailList;
	}
	public boolean finalizedBatch(String batchNo) 
	{
		logger.info("in finalizedBatch() of GenerateBatchDAOImpl.");
		boolean status = false;
		ArrayList qryList = new ArrayList();
		StringBuilder queryUpdate=new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		queryUpdate.append("update cr_generate_batch_dtl set rec_status='F' where BATCH_NO='"+batchNo+"'");
		insertPrepStmtObject.setSql(queryUpdate.toString());
		logger.info("IN finalizedBatch() update query  :  "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In finalizedBatch.........update status: "+status);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	public boolean deleteBatch(String batchIdList) 
	{
		logger.info("in deleteBatch() of GenerateBatchDAOImpl.");
		boolean status = false;
		ArrayList qryList = new ArrayList();
		StringBuilder queryUpdate=new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		queryUpdate.append("Delete from cr_generate_batch_dtl where BATCH_ID in"+batchIdList);
		insertPrepStmtObject.setSql(queryUpdate.toString());
		logger.info("IN deleteBatch() delete query  :  "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In deleteBatch.........delete status: "+status);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	public String presentationProcess(GenerateBatchVO vo) 
	{
		logger.info("in presentationProcess() of GenerateBatchDAOImpl.");
		String result="";
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1 = "";
		String s2 = "";
		String date="";
		String date1="";
	    logger.info("In presentationProcess() method ");
	    try
	    {
		   
			in.add(vo.getCompanyID() );
			date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
			in.add(date.toString());
			date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getPresentationDate()).trim());
			in.add(date1.toString());
			in.add(vo.getLbxBankID());
			in.add( vo.getLbxBranchID());
			in.add(vo.getBankAccount());
			in.add(vo.getUserId());
			in.add(vo.getBatchNo());
			out.add(s1);
			out.add(s2);
						
			logger.info("Presentation_Batch_Process("+in.toString()+","+out.toString()+")");  
			outMessages=(ArrayList) ConnectionDAO.callSP("Presentation_Batch_Process",in,out); 			
			s1=(String)outMessages.get(0);
			s2=(String)outMessages.get(1);		
			if(s1.equalsIgnoreCase("S")){
				result=s1;
			}else if(s1.equalsIgnoreCase("E")){
				result=s2;
			}
	        logger.info("s1  : "+s1);
	        logger.info("s2  : "+s2);
	        
	    }		       
	    catch(Exception e)
	    {e.printStackTrace();}
	    finally
	    {
	    	in=null;
			out=null;
			outMessages=null;
			s1=null;
			s2=null;
			date=null;
			date1=null;	    	
	    }
		return result;
	}
	public boolean saveDepositBank(GenerateBatchVO vo)
	{
		logger.info("in saveDepositBank() of GenerateBatchDAOImpl.");
		boolean status = false;
		ArrayList qryList = new ArrayList();
		StringBuilder queryUpdate=new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		queryUpdate.append("update cr_generate_batch_dtl set BANK_ID=?,BANK_NAME=?,BANK_BRANCH_ID=?,BANK_BRANCH_NAME=?," +
				"BRANCH_MICR_CODE=?,BRANCH_IFCS_CODE=?,BANK_ACCOUNT=? where BATCH_NO=?");
		
		if((CommonFunction.checkNull(vo.getLbxBankID())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
		
		if((CommonFunction.checkNull(vo.getBank())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getBank()).trim());
		
		if((CommonFunction.checkNull(vo.getLbxBranchID())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
		
		if((CommonFunction.checkNull(vo.getBranch())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getBranch()).trim());
		
		if((CommonFunction.checkNull(vo.getMicr())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMicr()).trim());
		
		if((CommonFunction.checkNull(vo.getIfscCode())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getIfscCode()).trim());
		
		if((CommonFunction.checkNull(vo.getBankAccount())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getBankAccount()).trim());
		
		if((CommonFunction.checkNull(vo.getBatchNo())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getBatchNo()).trim());		
		
		insertPrepStmtObject.setSql(queryUpdate.toString());
		logger.info("IN saveDepositBank() update query  :  "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveDepositBank.........update status: "+status);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
	}
	public ArrayList getBankDetail(String batchId)
	{
		logger.info("in getBankDetail() of GenerateBatchDAOImpl.");
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList data= null;
		GenerateBatchVO vo1= null;		
		ArrayList<GenerateBatchVO> detailList=new ArrayList();
	    try
	    {
			bufInsSql.append("select BATCH_ID,BATCH_NO,DATE_FORMAT(PRESENTATION_DATE,'%d-%m-%Y'),BANK_ID,BANK_NAME,BANK_BRANCH_ID," +
					"BANK_BRANCH_NAME,BRANCH_MICR_CODE,BRANCH_IFCS_CODE,BANK_ACCOUNT from cr_generate_batch_dtl where BATCH_ID=(select max(BATCH_ID) from cr_generate_batch_dtl where BATCH_NO like'%"+batchId.trim()+"%')");
			logger.info("in getBankDetail Query  :  "+bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
            logger.info("in viewBatch() Size  :   "+size);
            data=(ArrayList)searchlist.get(0);
            if(data.size()>0)
            {
            	   vo1= new GenerateBatchVO(); 
            	   vo1.setLbxBatchNo((CommonFunction.checkNull(data.get(1)).trim()));
            	   vo1.setBatchNo((CommonFunction.checkNull(data.get(1)).trim()));
            	   vo1.setPresentationDate((CommonFunction.checkNull(data.get(2)).trim()));
            	   vo1.setLbxBankID((CommonFunction.checkNull(data.get(3)).trim()));
            	   vo1.setBank((CommonFunction.checkNull(data.get(4)).trim()));
            	   vo1.setLbxBranchID((CommonFunction.checkNull(data.get(5)).trim()));
            	   vo1.setBranch((CommonFunction.checkNull(data.get(6)).trim()));
            	   vo1.setMicr((CommonFunction.checkNull(data.get(7)).trim()));
            	   vo1.setIfscCode((CommonFunction.checkNull(data.get(8)).trim()));            	   
            	   vo1.setBankAccount((CommonFunction.checkNull(data.get(9)).trim()));
            	   detailList.add(vo1);
            }
          }
	      catch(Exception e)
	    	{e.printStackTrace();}
	      finally
		{
			searchlist= null;
			bufInsSql = null;
			data= null;
			vo1= null;
		}
		return detailList;
	}
	@Override
	public String getTotalInstrumentAmount(String retVal) {
		
		String totalInstrumentAmount="0.00";
		String totalInstrumentAmountQuery="SELECT SUM(IFNULL(INSTRUMENT_AMOUNT,0.00)) FROM cr_generate_batch_dtl where BATCH_NO='"+CommonFunction.checkNull(retVal)+"'";
		totalInstrumentAmount=ConnectionDAO.singleReturn(totalInstrumentAmountQuery);
		totalInstrumentAmountQuery=null;
		
		return totalInstrumentAmount;
	}
	@Override
	public String getTotalInstrument(String retVal) {
		
		String totalInstrument="0";
		String totalInstrumentQuery="SELECT COUNT(INSTRUMENT_ID) FROM cr_generate_batch_dtl where BATCH_NO='"+CommonFunction.checkNull(retVal)+"'";
		totalInstrument=ConnectionDAO.singleReturn(totalInstrumentQuery);
		totalInstrumentQuery=null;
		return totalInstrument;
		
	}
	@Override
	public boolean generateMultipleBatch(String presentaionDate,
			String instrumentMode, String clearingType, String bankId,
			String userId, String makerDate) {
		
		   
		logger.info("in generateBatch() of GenerateBatchDAOImpl.");
		boolean retVal = false;
		ArrayList list=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
		try
		{
			String bankNameQuery="SELECT BANK_NAME FROM COM_BANK_M WHERE BANK_ID='"+bankId+"' ";
			String bankName=ConnectionDAO.singleReturn(bankNameQuery);
			bufInsSql.append("( SELECT  distinct a.PDC_LOAN_ID,l.LOAN_NO,a.PDC_INSTRUMENT_ID,a.PDC_INSTRUMENT_AMOUNT,a.pdc_instrument_mode,a.pdc_clearing_type " +
					" FROM CR_PDC_INSTRUMENT_DTL a " +
					" JOIN CR_LOAN_DTL l ON A.PDC_LOAN_ID = l.LOAN_ID AND l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS = 'F' "+
					" JOIN TEMP_GENERATE_BATCH temp on l.loan_no=temp.loan_no AND TEMP.BANK_ID='"+bankId+"'  " +
					" JOIN CR_REPAYSCH_DTL re ON re.LOAN_ID = A.PDC_LOAN_ID AND re.INSTL_NO = A.PDC_INSTL_NO AND re.REC_STATUS = 'A' AND re.INSTL_DATE = STR_TO_DATE('"+presentaionDate.trim()+"','"+dateFormat+"') " +
					" JOIN cr_deal_dtl d ON d.DEAL_ID=l.LOAN_DEAL_ID " +
					" LEFT JOIN CR_GENERATE_BATCH_DTL GB ON(GB.LOAN_ID=A.PDC_LOAN_ID AND GB.INSTRUMENT_ID=PDC_INSTRUMENT_ID)"+
					" WHERE PDC_STATUS = 'A' AND A.PDC_PURPOSE IN ('INSTALLMENT') " +						
					" AND A.PRESENTATION_DATE IS NULL AND IFNULL(GB.REC_STATUS,'N') NOT IN('P','F')" +
					" AND PDC_INSTRUMENT_DATE = STR_TO_DATE('"+presentaionDate.trim()+"','"+dateFormat+"') ");
			
			if(!instrumentMode.trim().equalsIgnoreCase(""))
				bufInsSql.append(" AND a.pdc_instrument_mode='"+instrumentMode.trim()+"'");
			if(!clearingType.trim().equalsIgnoreCase(""))
				bufInsSql.append(" AND a.pdc_clearing_type='"+clearingType.trim()+"'");
			
			
			bufInsSql.append(")    UNION    (");
			bufInsSql.append(" SELECT  distinct a.PDC_LOAN_ID,l.LOAN_NO,a.PDC_INSTRUMENT_ID,a.PDC_INSTRUMENT_AMOUNT,a.pdc_instrument_mode,a.pdc_clearing_type " +
					" FROM CR_PDC_INSTRUMENT_DTL a " +
					" JOIN CR_LOAN_DTL l ON A.PDC_LOAN_ID = l.LOAN_ID AND l.REC_STATUS = 'A' AND l.DISBURSAL_STATUS IN('P', 'F') " +
					" JOIN TEMP_GENERATE_BATCH temp on l.loan_no=temp.loan_no  AND TEMP.BANK_ID='"+bankId+"' " +
					" JOIN cr_deal_dtl d ON d.DEAL_ID=l.LOAN_DEAL_ID " +
					" LEFT JOIN CR_GENERATE_BATCH_DTL GB ON(GB.LOAN_ID=A.PDC_LOAN_ID AND GB.INSTRUMENT_ID=PDC_INSTRUMENT_ID)"+
					" WHERE PDC_STATUS = 'A' AND A.PDC_PURPOSE IN ('PRE EMI') " +
					" AND A.PRESENTATION_DATE IS NULL AND IFNULL(GB.REC_STATUS,'N') NOT IN('P','F')" +
					" AND PDC_INSTRUMENT_DATE = STR_TO_DATE('"+presentaionDate.trim()+"','"+dateFormat+"') ");
			
			if(!instrumentMode.trim().equalsIgnoreCase(""))
				bufInsSql.append(" AND a.pdc_instrument_mode='"+instrumentMode.trim()+"'");
			if(!clearingType.trim().equalsIgnoreCase(""))
				bufInsSql.append(" AND a.pdc_clearing_type='"+clearingType.trim()+"'");
			
			
			bufInsSql.append(" )");
			logger.info("in generateBatch() Query  :  "+bufInsSql.toString());
			list = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = list.size();
	        logger.info("size: "+size);
	        if(size==0)
	        	retVal=false;
	        if(size>0)
	        {
	        	String batch_no="";
	        	
	        	String mode=instrumentMode;	            	
	        	if(mode.trim().equalsIgnoreCase("Q"))
	        		batch_no="CHQ";
	        	else if(mode.trim().equalsIgnoreCase("DIR"))
	        		batch_no="DIR";
	        	else if(mode.trim().equalsIgnoreCase("E"))
	        		batch_no="ECS";
	        	else if(mode.trim().equalsIgnoreCase("H"))
	        		batch_no="ACH";
	        
	        	if(clearingType.trim().equalsIgnoreCase("I"))
	        		batch_no=batch_no+"INTER";
	        	else if(clearingType.trim().equalsIgnoreCase("L"))
	        		batch_no=batch_no+"LOCAL";
	        	else if(clearingType.trim().equalsIgnoreCase("O"))
	        		batch_no=batch_no+"OUTSN";
	        	else if(clearingType.trim().equalsIgnoreCase("C"))
	        		batch_no=batch_no+"CLEAN";
	        	else if(clearingType.trim().equalsIgnoreCase("E"))
	        		batch_no=batch_no+"EXPRE";
	        	else if(clearingType.trim().equalsIgnoreCase("N"))
	        		batch_no=batch_no+"NEWCS";
	        	else if(clearingType.trim().equalsIgnoreCase("M"))
	        		batch_no=batch_no+"NMMCS";
	        	else if(clearingType.trim().equalsIgnoreCase("T"))
	        		batch_no=batch_no+"TRENS";
	        	else if(clearingType.trim().equalsIgnoreCase("S"))
	        		batch_no=batch_no+"CTSCS";
	        	else if(clearingType.trim().equalsIgnoreCase("R"))
	        		batch_no=batch_no+"BLOCA";
	        	
	        	
	        	String date=presentaionDate;
	        	date=date.replace("-","_");
	        	batch_no=batch_no+date;
	        	batch_no=batch_no+"_S_"+bankId;
	        	logger.info("batch_no   :  "+batch_no);
	        	String query="select BATCH_NO from cr_generate_batch_dtl where BATCH_ID =(SELECT MAX(BATCH_ID) FROM cr_generate_batch_dtl WHERE BATCH_NO LIKE '%"+batch_no+"%')";
	        	
	        	logger.info("in generateBatch() Query for find max batch  :  "+bufInsSql.toString());
	        	String maxBatch=ConnectionDAO.singleReturn(query);
	        	logger.info("in generateBatch() maxBatch  :  "+maxBatch);
	        	if(CommonFunction.checkNull(maxBatch).equalsIgnoreCase(""))
	        		batch_no=batch_no+"_1";
	        	else
	        	{
	        		int val=Integer.parseInt(maxBatch.substring(maxBatch.length()-1));
	        		val++;
	        		batch_no=batch_no+"_"+val;	            		
	        	}
	        	logger.info("in generateBatch() final BatchNO  :  "+batch_no);
	        	
	        	ArrayList qryList=new ArrayList();
	        	for(int i=0;i<size;i++)
	        	{
	        		ArrayList subList=(ArrayList)list.get(i);
	        		StringBuilder insertQuery =	new StringBuilder();
	        		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	        		insertQuery.append("insert into cr_generate_batch_dtl (BATCH_NO,LOAN_ID,LOAN_NO,INSTRUMENT_ID,PRESENTATION_DATE,INSTRUMENT_AMOUNT,REC_STATUS,BANK_ID,BANK_NAME,MAKER_ID,MAKER_DATE)values(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
	        		
	        		
	        			insertPrepStmtObject.addString((batch_no).trim());   // BATCH_No
	        		
	        		if(CommonFunction.checkNull(subList.get(0)).trim().equalsIgnoreCase(""))// loan Id
	   				 	insertPrepStmtObject.addNull();
	   			 	else
	   			 		insertPrepStmtObject.addInt(Integer.parseInt((String)subList.get(0)));   
	        		
	        		if(CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase("")) // loan No
	       				insertPrepStmtObject.addNull();
	       			else
	       				insertPrepStmtObject.addString(((String)subList.get(1)).trim());  
	        		
	        		if(CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))//instrument_id
	        			insertPrepStmtObject.addNull();
	       			else
	       			    insertPrepStmtObject.addInt(Integer.parseInt((String)subList.get(2)));
	        		
	        		insertPrepStmtObject.addString(presentaionDate.trim());//PRESENTATION_DATE
	        		
	        		if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))//PDC_INSTRUMENT_AMOUNT
	        			insertPrepStmtObject.addNull();
	       			else
	       			    insertPrepStmtObject.addString(CommonFunction.checkNull(subList.get(3)));
	        		
	        		
	        		insertPrepStmtObject.addString(("P").trim());   // Rec_Status
	        		insertPrepStmtObject.addString((bankId).trim());// bankId
	        		insertPrepStmtObject.addString((bankName).trim());// bankName
	        		// mradul
	        		insertPrepStmtObject.addString((userId).trim());// maker_id
	        		insertPrepStmtObject.addString((makerDate).trim());// maker date
	        		
	        		insertPrepStmtObject.setSql(insertQuery.toString());
	        		qryList.add(insertPrepStmtObject);
	        		/*logger.info("+++++++++++-----"+insertQuery);
	         		logger.info("+++++++++++-----"+qryList);
	        		logger.info("+++++++++++-----"+insertPrepStmtObject);*/
	        		subList=null;
	        		insertQuery=null;
	        		insertPrepStmtObject=null;	            		
	        	}
	        	boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	        	if(status)
	        		retVal=true;
	        	else
	        		retVal=false;
	        		
	        }

		}catch(Exception e){
			retVal=false;
			e.printStackTrace();
		}
		finally
		{
			bufInsSql=null;
			list=null;
		}

	return retVal;
}
}
