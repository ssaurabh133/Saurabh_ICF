package com.scz.daoHibImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.connect.CommonFunction;
import com.scz.hibernateUtil.HibernateSessionFactory;
import com.scz.dao.DownloadPoolUploadDataDAO;
import com.scz.vo.BankUploadUploadVO;
import com.scz.vo.DownloadPoolDataVO;

public class DownloadPoolUploadDataHibImplService implements
		DownloadPoolUploadDataDAO {
	private static final Logger logger = Logger.getLogger(DownloadPoolUploadDataHibImplService.class.getName());
	
	public HSSFWorkbook downloadPoolData(DownloadPoolDataVO downloadPoolVO) {
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.beginTransaction();
		ArrayList mainList=new ArrayList();
		
		String hql = "select customer_id,deal_no,loan_id,loan_no,loan_ref_no,loan_initiation_date,case_book_date,loan_expiry_date,loan_status, " +
		"loan_disbursal_status,loan_type,loan_scheme,first_emi_date,advance_emi_date,first_emi_amount,advance_emi_amount,emi_pattern," +
		"no_of_advance_emi,classification,standard_classification,vehicle_category,vehicle_model,seasoning_without_advance,seasoning_with_advance," +
		"pos_cut_off_date,future_flow_cut_off_date,finance_amount,loan_repayment_frequency,loan_tenure,remaning_tenure,new_used,effective_irr," +
		"processing_amount,file_charges,customer_name,owner_name,branch,state,asset_cost,gross_ltv,net_ltv,dpd_last_month,dpd_bucket, " +
		"rate_bucket1,rate_bucket2,seasoning_bucket_without_advance,seasoning_bucket_with_advance,customer_segment,customer_category, " +
		"dealer_name,manufacturer,vehicle_no,chassis_no,engine_no,overdue_amount,overdue_prin_amt,overdue_int_amt,total_age_of_vehicle_loan_sanction, " +
		"total_age_of_vehicle_loan_maturity,total_future_cashflows,age_of_borrower,dob,year_of_manufacture,repayment_mode,address,city_customer, " +
		"district_customer,state_customer,pan_number,id_proof,address_proof,co_applicant_name,gurantor_name,total_vehicles,name_of_landholding, " +
		"weaker_section,landholding_size,agri_owner_name,relation_with_applicant,peak_dpd,net_paid_amount_customer,loan_inst_mode,do_date, " +
		"do_number,security_deposit,npa_status,buyback " +
		"from CrSecuritizationPoolDtlTempVO where pool_id="+downloadPoolVO.getPoolID()+"";
		Query query = session.createQuery(hql);
		List list = query.list();
		mainList = new ArrayList(list);
		
			logger.info(" updated list is this ...."+ mainList);
			
			
			HSSFWorkbook workbook = new HSSFWorkbook();      
	        HSSFSheet firstSheet = workbook.createSheet("Pool_Upload_Data");
	        HSSFRow row1 = firstSheet.createRow(0);
	        																								

	        row1.createCell(0).setCellValue("CUSTOMER_ID");
	        row1.createCell(1).setCellValue("DEAL_NO"); 
	        row1.createCell(2).setCellValue("LOAN_ID"); 
	        row1.createCell(3).setCellValue("LOAN_NO"); 
	       	row1.createCell(4).setCellValue("LOAN_REF_NO");
	       	row1.createCell(5).setCellValue("LOAN_INITIATION_DATE");
	       	row1.createCell(6).setCellValue("CASE_BOOK_DATE");
	       	
	        row1.createCell(7).setCellValue("LOAN_EXPIRY_DATE"); 
	        row1.createCell(8).setCellValue("LOAN_STATUS"); 
	        row1.createCell(9).setCellValue("LOAN_DISBURSAL_STATUS"); 
	       	row1.createCell(10).setCellValue("LOAN_TYPE");
	       	row1.createCell(11).setCellValue("LOAN_SCHEME");
	       	row1.createCell(12).setCellValue("FIRST_EMI_DATE");
	       	
	        row1.createCell(13).setCellValue("ADVANCE_EMI_DATE"); 
	        row1.createCell(14).setCellValue("FIRST_EMI_AMOUNT"); 
	        row1.createCell(15).setCellValue("ADVANCE_EMI_AMOUNT"); 
	       	row1.createCell(16).setCellValue("EMI_PATTERN");
	       	row1.createCell(17).setCellValue("NO_OF_ADVANCE_EMI");
	       	row1.createCell(18).setCellValue("CLASSIFICATION");
	       	
	        row1.createCell(19).setCellValue("STANDARD_CLASSIFICATION"); 
	        row1.createCell(20).setCellValue("VEHICLE_CATEGORY"); 
	        row1.createCell(21).setCellValue("VEHICLE_MODEL"); 
	       	row1.createCell(22).setCellValue("SEASONING_WITHOUT_ADVANCE");
	       	row1.createCell(23).setCellValue("SEASONING_WITH_ADVANCE");
	       	row1.createCell(24).setCellValue("POS_CUT_OFF_DATE");
	       	
	        row1.createCell(25).setCellValue("FUTURE_FLOW_CUT_OFF_DATE"); 
	        row1.createCell(26).setCellValue("FINANCE_AMOUNT"); 
	        row1.createCell(27).setCellValue("LOAN_REPAYMENT_FREQUENCY"); 
	       	row1.createCell(28).setCellValue("LOAN_TENURE");
	       	row1.createCell(29).setCellValue("REMANING_TENURE");
	       	row1.createCell(30).setCellValue("NEW_USED");
	       	
	        row1.createCell(31).setCellValue("EFFECTIVE_IRR(FUTURE_DUE)"); 
	        row1.createCell(32).setCellValue("PROCESSING_AMOUNT"); 
	        row1.createCell(33).setCellValue("FILE_CHARGES"); 
	       	row1.createCell(34).setCellValue("CUSTOMER_NAME");
	       	row1.createCell(35).setCellValue("OWNER_NAME");
	       	row1.createCell(36).setCellValue("BRANCH");
	       	
	      	row1.createCell(37).setCellValue("STATE"); 
	        row1.createCell(38).setCellValue("ASSET_COST"); 
	        row1.createCell(39).setCellValue("GROSS_LTV"); 
	       	row1.createCell(40).setCellValue("NET_LTV");
	       	row1.createCell(41).setCellValue("DPD_LAST_MONTH");
	       	row1.createCell(42).setCellValue("DPD_BUCKET");
	       	
	       	row1.createCell(43).setCellValue("RATE_BUCKET1"); 
	        row1.createCell(44).setCellValue("RATE_BUCKET2"); 
	        row1.createCell(45).setCellValue("SEASONING_BUCKET_WITHOUT_ADVANCE"); 
	       	row1.createCell(46).setCellValue("SEASONING_BUCKET_WITH_ADVANCE");
	       	row1.createCell(47).setCellValue("CUSTOMER_SEGMENT");
	       	row1.createCell(48).setCellValue("CUSTOMER_CATEGORY");
	       	
	       	row1.createCell(49).setCellValue("DEALER_NAME"); 
	        row1.createCell(50).setCellValue("MANUFACTURER"); 
	        row1.createCell(51).setCellValue("VEHICLE_NO"); 
	       	row1.createCell(52).setCellValue("CHASSIS_NO");
	       	row1.createCell(53).setCellValue("ENGINE_NO");
	       	row1.createCell(54).setCellValue("OVERDUE_AMOUNT");
	       	
	       	row1.createCell(55).setCellValue("OVERDUE_PRIN_AMT"); 
	        row1.createCell(56).setCellValue("OVERDUE_INT_AMT"); 
	        row1.createCell(57).setCellValue("TOTAL_AGE_OF_VEHICLE_LOAN_SANCTION"); 
	       	row1.createCell(58).setCellValue("TOTAL_AGE_OF_VEHICLE_LOAN_MATURITY");
	       	row1.createCell(59).setCellValue("TOTAL_FUTURE_CASHFLOWS");
	       	row1.createCell(60).setCellValue("AGE_OF_BORROWER");
	       	
	       	row1.createCell(61).setCellValue("DOB"); 
	        row1.createCell(62).setCellValue("YEAR_OF_MANUFACTURE"); 
	        row1.createCell(63).setCellValue("REPAYMENT_MODE"); 
	       	row1.createCell(64).setCellValue("ADDRESS");
	       	row1.createCell(65).setCellValue("CITY_CUSTOMER");
	       	row1.createCell(66).setCellValue("DISTRICT_CUSTOMER");
	       	row1.createCell(67).setCellValue("STATE_CUSTOMER");
	       	
	       	row1.createCell(68).setCellValue("PAN_NUMBER"); 
	        row1.createCell(69).setCellValue("ID_PROOF"); 
	        row1.createCell(70).setCellValue("ADDRESS_PROOF"); 
	       	row1.createCell(71).setCellValue("CO_APPLICANT_NAME");
	       	row1.createCell(72).setCellValue("GURANTOR_NAME");
	       	row1.createCell(73).setCellValue("TOTAL_VEHICLES");
	       	
	        row1.createCell(74).setCellValue("NAME_OF_LANDHOLDING"); 
	        row1.createCell(75).setCellValue("WEAKER_SECTION"); 
	        row1.createCell(76).setCellValue("LANDHOLDING_SIZE"); 
	       	row1.createCell(77).setCellValue("AGRI_OWNER_NAME");
	       	row1.createCell(78).setCellValue("RELATION_WITH_APPLICANT");
	       	row1.createCell(79).setCellValue("PEAK_DPD");
	       	
	    	row1.createCell(80).setCellValue("NET_PAID_AMOUNT_CUSTOMER"); 
	        row1.createCell(81).setCellValue("LOAN_INST_MODE"); 
	        row1.createCell(82).setCellValue("DO_DATE"); 
	       	row1.createCell(83).setCellValue("DO_NUMBER");
	       	row1.createCell(84).setCellValue("SECURITY_DEPOSIT");
	       	row1.createCell(85).setCellValue("NPA_STATUS");
	       	
	        row1.createCell(86).setCellValue("BUYBACK"); 
	        						
	       	
	       	HSSFRow row=null;
			int m=1,i=1;
			String sb="";
			Iterator it = mainList.iterator();
			while(it.hasNext())
			{
				row= firstSheet.createRow(i);
				Object ob = it.next();
				logger.info("ob is "+ob);
				Object[] objects = (Object[])ob;
			 	
				row.createCell(0).setCellValue(CommonFunction.checkNull(objects[0])); 
		        row.createCell(1).setCellValue(CommonFunction.checkNull(objects[1])); 
		        row.createCell(2).setCellValue(CommonFunction.checkNull(objects[2])); 
		        row.createCell(3).setCellValue(CommonFunction.checkNull(objects[3])); 
		       	row.createCell(4).setCellValue(CommonFunction.checkNull(objects[4]));
		       	row.createCell(5).setCellValue(CommonFunction.checkNull(objects[5]));
		       	row.createCell(6).setCellValue(CommonFunction.checkNull(objects[6]));
		       	
		        row.createCell(7).setCellValue(CommonFunction.checkNull(objects[7])); 
		        row.createCell(8).setCellValue(CommonFunction.checkNull(objects[8])); 
		        row.createCell(9).setCellValue(CommonFunction.checkNull(objects[9])); 
		       	row.createCell(10).setCellValue(CommonFunction.checkNull(objects[10]));
		       	row.createCell(11).setCellValue(CommonFunction.checkNull(objects[11]));
		       	row.createCell(12).setCellValue(CommonFunction.checkNull(objects[12]));
		       	
		        row.createCell(13).setCellValue(CommonFunction.checkNull(objects[13])); 
		        row.createCell(14).setCellValue(CommonFunction.checkNull(objects[14])); 
		        row.createCell(15).setCellValue(CommonFunction.checkNull(objects[15])); 
		       	row.createCell(16).setCellValue(CommonFunction.checkNull(objects[16]));
		       	row.createCell(17).setCellValue(CommonFunction.checkNull(objects[17]));
		       	row.createCell(18).setCellValue(CommonFunction.checkNull(objects[18]));
		       	
		        row.createCell(19).setCellValue(CommonFunction.checkNull(objects[19])); 
		        row.createCell(20).setCellValue(CommonFunction.checkNull(objects[20])); 
		        row.createCell(21).setCellValue(CommonFunction.checkNull(objects[21])); 
		       	row.createCell(22).setCellValue(CommonFunction.checkNull(objects[22]));
		       	row.createCell(23).setCellValue(CommonFunction.checkNull(objects[23]));
		       	row.createCell(24).setCellValue(CommonFunction.checkNull(objects[24]));
		       	
		        row.createCell(25).setCellValue(CommonFunction.checkNull(objects[25])); 
		        row.createCell(26).setCellValue(CommonFunction.checkNull(objects[26])); 
		        row.createCell(27).setCellValue(CommonFunction.checkNull(objects[27])); 
		       	row.createCell(28).setCellValue(CommonFunction.checkNull(objects[28]));
		       	row.createCell(29).setCellValue(CommonFunction.checkNull(objects[29]));
		       	row.createCell(30).setCellValue(CommonFunction.checkNull(objects[30]));
		       	
		        row.createCell(31).setCellValue(CommonFunction.checkNull(objects[31])); 
		        row.createCell(32).setCellValue(CommonFunction.checkNull(objects[32])); 
		        row.createCell(33).setCellValue(CommonFunction.checkNull(objects[33])); 
		       	row.createCell(34).setCellValue(CommonFunction.checkNull(objects[34]));
		       	row.createCell(35).setCellValue(CommonFunction.checkNull(objects[35]));
		       	row.createCell(36).setCellValue(CommonFunction.checkNull(objects[36]));
		       	
		        row.createCell(37).setCellValue(CommonFunction.checkNull(objects[37])); 
		        row.createCell(38).setCellValue(CommonFunction.checkNull(objects[38])); 
		        row.createCell(39).setCellValue(CommonFunction.checkNull(objects[39])); 
		       	row.createCell(40).setCellValue(CommonFunction.checkNull(objects[40]));
		       	row.createCell(41).setCellValue(CommonFunction.checkNull(objects[41]));
		       	row.createCell(42).setCellValue(CommonFunction.checkNull(objects[42]));
		       	row.createCell(43).setCellValue(CommonFunction.checkNull(objects[43])); 
		        row.createCell(44).setCellValue(CommonFunction.checkNull(objects[44])); 
		        row.createCell(45).setCellValue(CommonFunction.checkNull(objects[45])); 
		       	row.createCell(46).setCellValue(CommonFunction.checkNull(objects[46]));
		       	row.createCell(47).setCellValue(CommonFunction.checkNull(objects[47]));
		       	row.createCell(48).setCellValue(CommonFunction.checkNull(objects[48]));
		       	
		       	row.createCell(49).setCellValue(CommonFunction.checkNull(objects[49])); 
		        row.createCell(50).setCellValue(CommonFunction.checkNull(objects[50])); 
		        row.createCell(51).setCellValue(CommonFunction.checkNull(objects[51])); 
		       	row.createCell(52).setCellValue(CommonFunction.checkNull(objects[52]));
		       	row.createCell(53).setCellValue(CommonFunction.checkNull(objects[53]));
		       	row.createCell(54).setCellValue(CommonFunction.checkNull(objects[54]));
		       	
		       	row.createCell(55).setCellValue(CommonFunction.checkNull(objects[55])); 
		        row.createCell(56).setCellValue(CommonFunction.checkNull(objects[56])); 
		        row.createCell(57).setCellValue(CommonFunction.checkNull(objects[57])); 
		       	row.createCell(58).setCellValue(CommonFunction.checkNull(objects[58]));
		       	row.createCell(59).setCellValue(CommonFunction.checkNull(objects[59]));
		       	row.createCell(60).setCellValue(CommonFunction.checkNull(objects[60]));
		        row.createCell(61).setCellValue(CommonFunction.checkNull(objects[61])); 
		        
		        row.createCell(62).setCellValue(CommonFunction.checkNull(objects[62])); 
		        row.createCell(63).setCellValue(CommonFunction.checkNull(objects[63])); 
		       	row.createCell(64).setCellValue(CommonFunction.checkNull(objects[64]));
		       	row.createCell(65).setCellValue(CommonFunction.checkNull(objects[65]));
		       	row.createCell(66).setCellValue(CommonFunction.checkNull(objects[66]));
		       	row.createCell(67).setCellValue(CommonFunction.checkNull(objects[67]));
		       	
		       	row.createCell(68).setCellValue(CommonFunction.checkNull(objects[68])); 
		        row.createCell(69).setCellValue(CommonFunction.checkNull(objects[69])); 
		        row.createCell(70).setCellValue(CommonFunction.checkNull(objects[70])); 
		       	row.createCell(71).setCellValue(CommonFunction.checkNull(objects[71]));
		       	row.createCell(72).setCellValue(CommonFunction.checkNull(objects[72]));
		       	
		       	row.createCell(73).setCellValue(CommonFunction.checkNull(objects[73])); 
		        row.createCell(74).setCellValue(CommonFunction.checkNull(objects[74])); 
		        row.createCell(75).setCellValue(CommonFunction.checkNull(objects[75])); 
		       	row.createCell(76).setCellValue(CommonFunction.checkNull(objects[76]));
		       	row.createCell(77).setCellValue(CommonFunction.checkNull(objects[77]));
		       	
		       	row.createCell(78).setCellValue(CommonFunction.checkNull(objects[78])); 
		        row.createCell(79).setCellValue(CommonFunction.checkNull(objects[79])); 
		        row.createCell(80).setCellValue(CommonFunction.checkNull(objects[80])); 
		       	row.createCell(81).setCellValue(CommonFunction.checkNull(objects[81]));
		       	row.createCell(82).setCellValue(CommonFunction.checkNull(objects[82]));
		       	
		       	row.createCell(83).setCellValue(CommonFunction.checkNull(objects[83])); 
		        row.createCell(84).setCellValue(CommonFunction.checkNull(objects[84])); 
		        row.createCell(85).setCellValue(CommonFunction.checkNull(objects[85])); 
		       	row.createCell(86).setCellValue(CommonFunction.checkNull(objects[86]));
		       	
			 	i++;
				row=null;
			}
			return workbook;
	}

	public HSSFWorkbook downloadBankData(DownloadPoolDataVO downloadPoolVO) {
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.beginTransaction();
		ArrayList mainList = new ArrayList();
		HSSFWorkbook workbook=null;
		try{
			
			String hql = "select pool_id,instl_no,instl_date,instl_amount,prin_comp,int_comp,au_prin_comp," +
					"au_int_comp,distribution_ratio_bank,distribution_ratio_au,prin_os from BankUploadUploadVO " +
					"where pool_id="+downloadPoolVO.getPoolID()+"";
			Query query = session.createQuery(hql);
			List list = query.list();
			mainList = new ArrayList(list);
			
			logger.info(" the query is "+ hql);
			logger.info(" updated list is this .... Rudra"+ mainList);
			
			workbook = new HSSFWorkbook();      
	        HSSFSheet firstSheet = workbook.createSheet("Bank_Upload_Data");
	        HSSFRow row1 = firstSheet.createRow(0);
	        
	        row1.createCell(0).setCellValue("POOL_ID"); 
	        row1.createCell(1).setCellValue("INSTL_NO"); 
	        row1.createCell(2).setCellValue("INSTL_DATE"); 
	       	row1.createCell(3).setCellValue("INSTL_AMOUNT");
	       	row1.createCell(4).setCellValue("PRIN_COMP");
	       	row1.createCell(5).setCellValue("INT_COMP");
	       	row1.createCell(6).setCellValue("AU_PRIN_COMP"); 
	        row1.createCell(7).setCellValue("AU_INT_COMP"); 
	        row1.createCell(8).setCellValue("DISTRIBUTION_RATIO_BANK"); 
	       	row1.createCell(9).setCellValue("DISTRIBUTION_RATIO_AU");
	       	row1.createCell(10).setCellValue("PRIN_OS");
	       	
	       	HSSFRow row=null;
			int i=1;
			Iterator it = mainList.iterator();
			BankUploadUploadVO poolDtlVO = null;
			while(it.hasNext())
			{
				row= firstSheet.createRow(i);
			Object ob = it.next();
			logger.info("ob is "+ob);
			 Object[] objects = (Object[])ob;
			 row.createCell(0).setCellValue(CommonFunction.checkNull(objects[0]));						
			 	row.createCell(1).setCellValue(CommonFunction.checkNull(objects[1])); 
		        row.createCell(2).setCellValue(CommonFunction.checkNull(objects[2])); 
		       	row.createCell(3).setCellValue(CommonFunction.checkNull(objects[3]));
			 	row.createCell(4).setCellValue(CommonFunction.checkNull(objects[4]));
			 	row.createCell(5).setCellValue(CommonFunction.checkNull(objects[5]));
			 	
			 	row.createCell(6).setCellValue(CommonFunction.checkNull(objects[6])); 
		        row.createCell(7).setCellValue(CommonFunction.checkNull(objects[7])); 
		        row.createCell(8).setCellValue(CommonFunction.checkNull(objects[8])); 
		       	row.createCell(9).setCellValue(CommonFunction.checkNull(objects[9]));
		       	row.createCell(10).setCellValue(CommonFunction.checkNull(objects[10]));
		       	
		      	i++;
				row=null;
			}
		}catch (Exception e) {
			logger.info("error obtains "+e);
		}
			return workbook;
	}

	public HSSFWorkbook downloadRePayData(DownloadPoolDataVO downloadPoolVO) {
		Session session = HibernateSessionFactory.currentSession();
		Transaction transaction = session.beginTransaction();
		ArrayList mainList = new ArrayList();
		HSSFWorkbook workbook=null;
		try{
			
			String hql = "select loan_id,instl_no,instl_date,instl_amount,prin_comp,int_comp,excess_int," +
					"instl_amount_recd,prin_comp_recd,int_comp_recd,excess_int_recd,prin_os from RepaymentScheduleUploadVO " +
					"where pool_id="+downloadPoolVO.getPoolID()+"";
			Query query = session.createQuery(hql);
			List list = query.list();
			mainList = new ArrayList(list);
			
			logger.info(" the query is "+ hql);
			logger.info(" updated list is this .... Rudra"+ mainList);
			
			workbook = new HSSFWorkbook();      
	        HSSFSheet firstSheet = workbook.createSheet("RePayment_Upload_Data");
	        HSSFRow row1 = firstSheet.createRow(0);
	        
	        row1.createCell(0).setCellValue("LOAN_ID"); 
	        row1.createCell(1).setCellValue("INSTL_NO"); 
	        row1.createCell(2).setCellValue("INSTL_DATE"); 
	       	row1.createCell(3).setCellValue("INSTL_AMOUNT");
	       	row1.createCell(4).setCellValue("PRIN_COMP");
	       	row1.createCell(5).setCellValue("INT_COMP");
	       	row1.createCell(6).setCellValue("EXCESS_INT"); 
	        row1.createCell(7).setCellValue("INSTL_AMOUNT_RECD"); 
	        row1.createCell(8).setCellValue("PRIN_COMP_RECD"); 
	       	row1.createCell(9).setCellValue("INT_COMP_RECD");
	       	row1.createCell(10).setCellValue("EXCESS_INT_RECD");
	       	row1.createCell(11).setCellValue("PRIN_OS");
	       	
	       	HSSFRow row=null;
			int i=1;
			Iterator it = mainList.iterator();
			while(it.hasNext())
			{
			 row= firstSheet.createRow(i);
			 Object ob = it.next();
			 Object[] objects = (Object[])ob;
			 row.createCell(0).setCellValue(CommonFunction.checkNull(objects[0]));						
			 	row.createCell(1).setCellValue(CommonFunction.checkNull(objects[1])); 
		        row.createCell(2).setCellValue(CommonFunction.checkNull(objects[2])); 
		       	row.createCell(3).setCellValue(CommonFunction.checkNull(objects[3]));
			 	row.createCell(4).setCellValue(CommonFunction.checkNull(objects[4]));
			 	row.createCell(5).setCellValue(CommonFunction.checkNull(objects[5]));
			 	
			 	row.createCell(6).setCellValue(CommonFunction.checkNull(objects[6])); 
		        row.createCell(7).setCellValue(CommonFunction.checkNull(objects[7])); 
		        row.createCell(8).setCellValue(CommonFunction.checkNull(objects[8])); 
		       	row.createCell(9).setCellValue(CommonFunction.checkNull(objects[9]));
		       	row.createCell(10).setCellValue(CommonFunction.checkNull(objects[10]));
		       	row.createCell(11).setCellValue(CommonFunction.checkNull(objects[11]));
		       i++;
				row=null;
			}
		}catch (Exception e) {
			logger.info("error obtains "+e);
		}
			return workbook;
	}
}
