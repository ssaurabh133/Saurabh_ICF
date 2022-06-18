package com.pagelevel.pagedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.connect.ConnectionDAO;
import com.logger.LoggerMsg;

public class CommonService implements CommonInterface{

	public ArrayList<Object> selectBranchDetail() {
		
		ArrayList<Object> al = new ArrayList<Object>();
		ResultSet rs=null;
		CommonVo vo = null;
		Statement psmt1=null;
		Connection con=null;
		
		con = ConnectionDAO.getConnection();

		try {
			psmt1 = con.createStatement();
			rs = psmt1.executeQuery("select * from m_branch_master");
		    while(rs.next())
		    {
		    	vo = new CommonVo();
		    	vo.setCode(rs.getString(1));
		    	vo.setDesc(rs.getString(2));
		    	vo.setStatus(rs.getString(3));
		    	al.add(vo);
		    }
		    
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerMsg.info("In selectBranchDetail"+e);
		}
		finally
		{
			try
			{
				rs.close();
				psmt1.close();
				con.close();
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
		return al;
	}

	public ArrayList<Object> selectProductDetail() {
		ArrayList<Object> al = new ArrayList<Object>();
		ResultSet rs=null;
		CommonVo vo = null;
		Statement psmt1=null;
		Connection con=null;
		
		con = ConnectionDAO.getConnection();

		try {
			psmt1 = con.createStatement();
			rs = psmt1.executeQuery("select * from m_product_master");
		    while(rs.next())
		    {
		    	vo = new CommonVo();
		    	vo.setCode(rs.getString(1));
		    	vo.setDesc(rs.getString(2));
		    	vo.setStatus(rs.getString(3));
		    	al.add(vo);
		    }
		    
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerMsg.info("In selectProductDetail"+e);
		}
		finally
		{
			try
			{
				rs.close();
				psmt1.close();
				con.close();
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
		return al;

	}
public ArrayList<Object> selectSchemeDetail() {
		
		ArrayList<Object> als = new ArrayList<Object>();
		ResultSet rs=null;
		CommonVo vo = null;
		Statement psmt1=null;
		Connection con=null;
		
		con = ConnectionDAO.getConnection();

		try {
			psmt1 = con.createStatement();
			rs = psmt1.executeQuery("select * from m_scheme_master");
		    while(rs.next())
		    {
		    	vo = new CommonVo();
		    	vo.setCode(rs.getString(1));
		    	vo.setName(rs.getString(2));
		    	vo.setDesc(rs.getString(3));
		    	vo.setStatus(rs.getString(4));
		    	als.add(vo);
		    }
		    
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LoggerMsg.info("In selectSchemeDetail"+e);
		}
		finally
		{
			try
			{
				rs.close();
				psmt1.close();
				con.close();
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		
		return als;
	}

public ArrayList<Object> getSchemeDetail() {
	
	ArrayList<Object> list = new ArrayList<Object>();
	ResultSet rs=null;
	CommonVo vo = null;
	Statement psmt1=null;
	Connection con=null;
	
	con = ConnectionDAO.getConnection();

	try {
		psmt1 = con.createStatement();
		rs = psmt1.executeQuery("select product_code,product_desc from m_product_master");
	    while(rs.next())
	    {
	    	vo = new CommonVo();
	    	vo.setCode(rs.getString("product_code"));
            vo.setDesc(rs.getString("product_desc"));
	    	
	    	list.add(vo);
	    }
	    
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		LoggerMsg.info("In selectSchemeDetail"+e);
	}
	finally
	{
		try
		{
			rs.close();
			psmt1.close();
			con.close();
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	return list;
}
		
	}


