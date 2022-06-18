package com.masters.daoImplMSSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;

import com.masters.dao.masterParameterDAO;
import com.masters.vo.ParameterScoreDefVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;


public class MSSQLMasterParameterDAOIMPL implements masterParameterDAO
{
	private static final Logger logger = Logger.getLogger(MSSQLMasterParameterDAOIMPL.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	Connection con=null;
	ResultSet rs=null;
	PreparedStatement ps = null;
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	Statement st=null;
	public boolean saveParameterDescription(ParameterScoreDefVO vo)
	{
	boolean status=false;	
	con=ConnectionDAO.getConnection();
	ArrayList list= new ArrayList();
	try 
	{ 
		String dQuery="delete from cr_scorecard_values where SCORE_CARD_ID="+vo.getScoreCardId();
		st=con.createStatement();
		int sttusDelete=st.executeUpdate(dQuery);
	 
		logger.info("Delete :"+sttusDelete);
		StringBuilder query = new StringBuilder();
		query.append("insert into cr_scorecard_values(SCORE_CARD_ID,SCORING_PARAM_CODE," +" NUMERIC_VALUE_FROM,NUMERIC_VALUE_TO,CHARACTER_VALUE,PARAMETER_SCORE,MAKER_ID,MAKER_DATE)values(?,?,?,?,?,?,?,");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							              
	      String [] parametergGridCode=vo.getParametergGridCode();
	      String [] gridtype=vo.getGridtype();
	      String [] gridnumericFrom=vo.getGridnumericFrom();
	      String [] gridnumericTo=vo.getGridnumericTo();
	      String [] gridcharecter=vo.getGridcharecter();
	      String [] gridscore=vo.getGridscore();
	      ps=con.prepareStatement(query.toString());
	      for(int a=0;a<parametergGridCode.length;a++)
	      { 
	    	  ps.setString(1, vo.getScoreCardId()) ; 
	    	  ps.setString(2,parametergGridCode[a]) ; 
	    	  if(CommonFunction.checkNull(gridnumericFrom[a]).equalsIgnoreCase(""))
	    	  {
	    		//  gridnumericFrom[a]="0.0000";
	    		  ps.setString(3,"0.0000"); 
	    	  }
	    	  else
	    	  {
	    	  ps.setString(3,gridnumericFrom[a]);
	    	  }
	    	  if(CommonFunction.checkNull(gridnumericTo[a]).equalsIgnoreCase(""))
	    	  {
	    		  ps.setString(4,"0.0000");   
	    	  }
	    	  else
	    	  {
	    		  ps.setString(4,gridnumericTo[a]);   
	    	  }    	 
	    	  ps.setString(5, gridcharecter[a]) ; 
	    	  ps.setString(6, gridscore[a]) ;
	    	  ps.setString(7, vo.getMakerId()) ;
	    	  ps.setString(8, vo.getMakerDate()) ;
	    	  ps.addBatch();		      
	      }
	      int[] updateCounts = ps.executeBatch();
	      logger.info("Batch updateCounts:"+updateCounts.length);
	     if(updateCounts.length>=0)
	     {
	    	 status=true; 
	     }
	}
	catch (Exception e)
	{
		logger.info("Exception"+e.getMessage());
	}
	finally{
		try{
			con.close();
		}
		catch (Exception e)
		{
			logger.info("Exception"+e.getMessage());
		}
		
	}
	return status;
	}

	public ArrayList getParameterDef(String sid,String pcode)
	{
		ArrayList list= new ArrayList();
		String query="select cd.SCORING_PARAM_CODE,cd.NUMERIC_VALUE_FROM,cd.NUMERIC_VALUE_TO," +
				" cd.CHARACTER_VALUE,cd.PARAMETER_SCORE,cs.DATA_TYPE from cr_scorecard_values " +
				" cd left outer join cr_scoring_param cs on cd.SCORING_PARAM_CODE=cs.SCORING_PARAM_CODE " +
				" WHERE SCORE_CARD_ID="+sid;
		try 
		{
			logger.info("Query:"+query);
			con=ConnectionDAO.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
			ParameterScoreDefVO vo= new ParameterScoreDefVO();	
			vo.setAllchk("<input type='radio' name='chk' id='chk' />");
			vo.setParameterCode(rs.getString("SCORING_PARAM_CODE"));
			if(rs.getString("NUMERIC_VALUE_FROM").equalsIgnoreCase("0.0000"))
			{
				vo.setNumericFrom("");	
			}
			else
			{
				vo.setNumericFrom(rs.getString("NUMERIC_VALUE_FROM"));
			}
			if(rs.getString("NUMERIC_VALUE_TO").equalsIgnoreCase("0.0000"))
			{
				vo.setNumericTo("");	
			}
			else
			{
				vo.setNumericTo(rs.getString("NUMERIC_VALUE_TO"));
			}			
			
			vo.setCharecter(rs.getString("CHARACTER_VALUE"));
			vo.setScore(rs.getString("PARAMETER_SCORE"));
			vo.setType(rs.getString("DATA_TYPE"));
			list.add(vo);			
			}
		}
		catch (Exception e)
		{
		logger.info("ERROR"+e.getMessage());
		}
		finally{
			try{
				con.close();
			}
			catch (Exception e)
			{
				logger.info("Exception"+e.getMessage());
			}
			
		}
		return list;
	}
	
	
	public ArrayList getParameterEdit(String sid,String pcode)
	{
		ArrayList list= new ArrayList();
		String query="select cd.SCORING_PARAM_CODE,cd.NUMERIC_VALUE_FROM,cd.NUMERIC_VALUE_TO," +
				" cd.CHARACTER_VALUE,cd.PARAMETER_SCORE,cs.DATA_TYPE from cr_scorecard_values " +
				" cd left outer join cr_scoring_param cs on cd.SCORING_PARAM_CODE=cs.SCORING_PARAM_CODE " +
				" WHERE SCORE_CARD_ID="+sid+" and cd.SCORING_PARAM_CODE='"+pcode+"'";
		try 
		{
			logger.info("Query:"+query);
			con=ConnectionDAO.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
			ParameterScoreDefVO vo= new ParameterScoreDefVO();	
			vo.setAllchk("<input type='radio' name='chk' id='chk' />");
			vo.setParameterCode(rs.getString("SCORING_PARAM_CODE"));
			vo.setNumericFrom(rs.getString("NUMERIC_VALUE_FROM"));
			vo.setNumericTo(rs.getString("NUMERIC_VALUE_TO"));
			vo.setCharecter(rs.getString("CHARACTER_VALUE"));
			vo.setScore(rs.getString("PARAMETER_SCORE"));
			vo.setType(rs.getString("DATA_TYPE"));
			list.add(vo);			
			}
		}
		catch (Exception e)
		{
		logger.info("ERROR"+e.getMessage());
		}
		finally{
			try{
				con.close();
			}
			catch (Exception e)
			{
				logger.info("Exception"+e.getMessage());
			}
			
		}
		return list;
	}
	
	public ArrayList getParameterDefOnLoad(ParameterScoreDefVO vo1)
	{	
		ArrayList list= new ArrayList();
		try 
		{
//			select  sm.SCORE_CARD_ID,sm.SCORE_CARD_DESC,pm.PRODUCT_DESC from 
//			cr_scorecard_m sm left outer join 
//			cr_product_m pm  on sm.PRODUCT_ID=pm.PRODUCT_ID 
			
			StringBuffer bufInsSql = new StringBuffer();			
			bufInsSql.append(" select sm.SCORE_CARD_ID,sm.SCORE_CARD_DESC,pm.PRODUCT_DESC from");
			bufInsSql.append(" cr_scorecard_m sm left outer join ");
			bufInsSql.append(" cr_product_m pm  on sm.PRODUCT_ID=pm.PRODUCT_ID ");
					
			if (!vo1.getScoreCardId().equals("") ) {
				bufInsSql.append(" WHERE sm.SCORE_CARD_ID = '" + vo1.getScoreCardId()+"'");			
			}

//			else if (!vo.getDescription() .equals("")) {
//				bufInsSql.append(" WHERE PRODUCT_ID = '" + prodID + "' ");			
//			}
//
//			else if (!proDesc.equals("")) {
//				bufInsSql.append(" WHERE PRODUCT_DESC like '%" + proDesc + "%' ");
//				bufInsSqlTempCount.append(" WHERE PRODUCT_DESC like '%" + proDesc + "%' ");
//			}
//			
			
			
			
			
			logger.info("Query:"+bufInsSql.toString());
			con=ConnectionDAO.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(bufInsSql.toString());
			while(rs.next())
			{
			ParameterScoreDefVO vo= new ParameterScoreDefVO();	
			//vo.setScoreCardId(rs.getString("cd.SCORE_CARD_ID"));
			vo.setParameterLink("<a href='parameterScoreBusiness.do?method=openAddParameter&scorecardid="+rs.getString("SCORE_CARD_ID")+"' >"+rs.getString("SCORE_CARD_ID")+"</>");
			vo.setDescription(rs.getString("SCORE_CARD_DESC"));
			vo.setProfit(rs.getString("PRODUCT_DESC"));
			
			list.add(vo);			
			}
		}
		catch (Exception e)
		{
		logger.info("ERROR"+e.getMessage());
		}
		finally{
			try{
				con.close();
			}
			catch (Exception e)
			{
				logger.info("Exception"+e.getMessage());
			}
			
		}
		return list;
	}
	
	public ArrayList getParameterScoreDef(String sid)
	{
		ArrayList list= new ArrayList();
		String query="select  sm.SCORE_CARD_ID,sm.SCORE_CARD_DESC,pm.PRODUCT_DESC from " +
				" cr_scorecard_m sm left outer join" +
				" cr_product_m pm  on sm.PRODUCT_ID=pm.PRODUCT_ID where sm.SCORE_CARD_ID="+sid;
		try 
		{
			logger.info("Query:"+query);
			con=ConnectionDAO.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
			ParameterScoreDefVO vo= new ParameterScoreDefVO();			
			vo.setScardid(rs.getString("SCORE_CARD_ID"));
			vo.setSdescription(rs.getString("SCORE_CARD_DESC"));
			vo.setSproduct(rs.getString("PRODUCT_DESC"));		
			list.add(vo);			
			}
		}
		catch (Exception e)
		{
		logger.info("ERROR"+e.getMessage());
		}
		finally{
			try{
				con.close();
			}
			catch (Exception e)
			{
				logger.info("Exception"+e.getMessage());
			}
			
		}
		return list;
	}
	
	public int updateParameterList(ParameterScoreDefVO vo)
	{
		int a=0;		
		try 
		{
			StringBuilder query = new StringBuilder();
			query.append("update cr_scorecard_values set SCORE_CARD_ID=?,SCORING_PARAM_CODE=?,NUMERIC_VALUE_FROM=?,NUMERIC_VALUE_TO=?,CHARACTER_VALUE=?,PARAMETER_SCORE=?,MAKER_ID=?,MAKER_DATE=");
			query.append(dbo);
			query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			query.append(" where SCORE_CARD_ID=? and SCORING_PARAM_CODE=?");
			
					
		  con=ConnectionDAO.getConnection();            
          ps=con.prepareStatement(query.toString());
    	  ps.setString(1, vo.getScoreCardId()) ; 
    	  ps.setString(2,vo.getParameterCode()) ; 
    	  if(CommonFunction.checkNull(vo.getNumericFrom()).equalsIgnoreCase(""))
    	  {    		
    		  ps.setString(3,"0.0000"); 
    	  }
    	  else
    	  {
    	  ps.setString(3,vo.getNumericFrom());
    	  }
    	  if(CommonFunction.checkNull(vo.getNumericTo()).equalsIgnoreCase(""))
    	  {
    		  ps.setString(4,"0.0000");   
    	  }
    	  else
    	  {
    		  ps.setString(4,vo.getNumericTo());   
    	  }    	 
    	  ps.setString(5, CommonFunction.checkNull(vo.getCharecter())) ; 
    	  ps.setString(6, vo.getScore());   

    	  ps.setString(7, vo.getMakerId()) ;
    	  ps.setString(8, vo.getMakerDate()) ;
    	  ps.setString(9, vo.getScoreCardId()); 
    	  ps.setString(10, vo.getParameterCode()); 
		  logger.info("Editgg Query:"+ps.toString());
		  a=ps.executeUpdate();
		}	
		catch (Exception e)
		{
			 logger.info("Edit Error:"+e.getMessage());
		}
		finally{
			try{
				con.close();
			}
			catch (Exception e)
			{
				logger.info("Exception"+e.getMessage());
			}
			
		}
		return a;
	}
}
