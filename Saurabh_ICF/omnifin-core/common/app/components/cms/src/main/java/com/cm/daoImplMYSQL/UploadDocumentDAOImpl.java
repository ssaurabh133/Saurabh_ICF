package com.cm.daoImplMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.connect.ConnectionUploadDAO;
import com.cm.dao.UploadDocumentDAO;

public class UploadDocumentDAOImpl implements UploadDocumentDAO {
	
	private static final Logger logger = Logger.getLogger(UploadDocumentDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public StringBuffer getUploadData(String makerId)
	{
		logger.info("In.....getUploadData().......DAOIMPL....");
		Connection conn=null;
		Statement stmt =null;
		StringBuffer data= new StringBuffer();
		String query= null;
		try
		{
		if(makerId!=null)
		{
		query= "Select file_name from upload_SUMMARY where maker_id='"+makerId +"' and MAKER_DATE=CURRENT_DATE";
		logger.info("In getUploadData()...............query...........DAOImpl "+query);
		conn= ConnectionUploadDAO.getConnection();
		stmt= conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			data.append("~");
			data.append(rs.getString(1).toString());
	    }
		logger.info("IN getUploadData() : data:==>> "+data.toString());		
		}
		
		else
		{
			logger.info("In.....getUploadData()...DAOIMPL...MakerId..."+makerId);
			
		}
		//conn.close();
		logger.info("IN getUploadData() : data:==>> "+data.toString());		
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		finally
				{
					try {
						// conn.commit();
						conn.close();
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					query = null;
					makerId = null;
				}//checking how many value have come from database..
		
		logger.info(" ..getUploadData()..File Name..is...."+data);

		return data; 
		
		
	   }
	
	public ArrayList getUploadSummary(String makerId, HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		logger.info("in getUploadSummary... DAOI ");
		ArrayList list= new ArrayList();
		//Connection conn1=null;
		//Statement stmt,stmt1 =null;
		StringBuffer data= new StringBuffer();
		StringBuilder query = new StringBuilder(); 
		StringBuilder queryForTMP = new StringBuilder();
		StringBuilder total = new StringBuilder();
		try
		{
		if(makerId!=null)
		{
			// select batch_id ,maker_date,no_of_records_rejected,no_of_records_uploaded from upload_summary where maker_id=1 and batch_id=( select max(batch_id) from upload_summary)
		query.append("select batch_id ,maker_date,no_of_records_rejected,no_of_records_uploaded from upload_summary where maker_id= '"+ makerId+ "' and batch_id=( select max(batch_id) from upload_summary)");
		logger.info("In getUploadData()...............query...........DAOImpl "+query.toString());
		list=ConnectionUploadDAO.sqlSelect(query.toString());
		logger.info("In getUploadData()...............list...........DAOImpl "+list);
		queryForTMP.append("Select count('total') from cheque_status_upload_tmp where uploaded_flag='N' or uploaded_flag='Y' and maker_id='"+makerId+"'"); 
	
		logger.info("In..getUploadSummary()...Query"+queryForTMP.toString());
		
		total.append(ConnectionUploadDAO.singleReturn(queryForTMP.toString()));		
		logger.info("in getUploadSummary()..in daoi..total is."+total);
		session.setAttribute("TotalUploadandReject", total);
		logger.info("Total uploaded and rejected.."+total);
		}
		else
		{
			logger.info("In.....getUploadData()...DAOIMPL...MakerId..."+makerId);
			
		}
	
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			
		}
		finally
		{
			query = null;
			queryForTMP = null;
			total = null;
			makerId= null;
			data = null;
			query = null;
			queryForTMP = null;
			total = null;
		}
		
		return list;
	}
}
