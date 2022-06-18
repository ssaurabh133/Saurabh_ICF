package com.cp.dao;

import java.sql.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cm.vo.AssetVerificationVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.UploadDocument;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import com.connect.PrepStmtObject;
import com.cp.vo.CommonDealVo;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.NewCaptureVO;
import com.cp.vo.TradeCheckInitVo;
import com.logger.LoggerMsg;
import com.masters.vo.BankBranchMasterVo;
import com.masters.vo.MasterVo;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;


public class NewFieldVerificationDAOImpl 
{
	private static final Logger logger = Logger.getLogger(NewFieldVerificationDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	/*Connection con=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	CallableStatement cstm=null;
	ResultSet rs=null;
	StringBuffer bufInsUpdSql = null;
	ArrayList qryList = null;
	ArrayList qryList1 = null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");*/
	//DecimalFormat myFormatter = new DecimalFormat("###.##");
	
	ArrayList qryListB = null;
	ArrayList qryListS = null;

	public ArrayList getTradeHeader(String dealId) 
	{
		ArrayList list=new ArrayList();
		try
		{
			String query = " select d.deal_id, deal_no,deal.CUSTOMER_NAME,DATE_FORMAT(deal_date,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY from cr_deal_dtl d "+
							    " left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID"+
								" left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID"+
								" left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID"+
								" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID"+
								" where d.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" limit 1";
			HeaderInfoVo vo= null;
			ArrayList header = ConnectionDAO.sqlSelect(query);
			for(int i=0;i<header.size();i++)
			{
				logger.info("header"+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					vo = new HeaderInfoVo();
					vo.setDealId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(0))).trim());
					vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(1))).trim());
					vo.setDealCustomerName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(2))).trim());
					vo.setDealDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(3))).trim());
					vo.setDealProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(4))).trim());
					vo.setDealScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(5))).trim());
					vo.setDealProductCat(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(6))).trim());
					list.add(vo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList getCustomerDetail(String dealId) 
	{
		System.out.println("\n\ngetCustomerDetail method is called of NewFieldVerificationDAOImpl class \n");
		ArrayList list=new ArrayList();
		try
		{
			System.out.println("\n\ni am in try block\n\n");
			String query = "select cr_deal_customer_role.DEAL_CUSTOMER_ID as customer_id,cr_deal_customer_m.CUSTOMER_NAME,case " +
					"when cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' then 'Applicant' " +
					"When cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='COAPPL' then 'Co-Applicant' " +
					"When cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR' then 'Gurantor' " +
					"end as Applicant_type  from cr_deal_customer_role " +
					"left outer join cr_deal_customer_m on cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID " +
					"where cr_deal_customer_role.DEAL_ID='"+dealId+"'";
			NewCaptureVO vo= null;
			ArrayList detail = ConnectionDAO.sqlSelect(query);
			LoggerMsg.info("query:::::::::::::::::::::::::::::::"+query);
			for(int i=0;i<detail.size();i++)
			{
				System.out.println("\nim am in for loop\n");
				logger.info("\n\ndetail neeraj"+detail.size());
				ArrayList detail1=(ArrayList)detail.get(i);
				if(detail1!=null && detail1.size()>0)
				{
					System.out.println("i am in if condition");
					vo = new NewCaptureVO();
					vo.setCustomer_id(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(detail1.get(0))).trim());
					vo.setCustomer_name(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(detail1.get(1))).trim());
					vo.setApplicant_type(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(detail1.get(2))).trim());
					list.add(vo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
}







	
