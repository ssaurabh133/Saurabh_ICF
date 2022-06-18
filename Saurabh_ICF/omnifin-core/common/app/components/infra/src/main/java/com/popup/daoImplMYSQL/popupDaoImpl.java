package com.popup.daoImplMYSQL;
import java.sql.Connection;
import org.apache.commons.lang.StringEscapeUtils;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.popup.dao.popupDao;
import com.popup.vo.PopupVo;
import com.popup.vo.TableVo;

public class popupDaoImpl implements popupDao{
	private static final Logger logger = Logger.getLogger(popupDaoImpl.class.getName());
	
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	
	int no=Integer.parseInt(resource.getString("msg.pageSizeForLov"));
	String dateFormat=resource.getString("lbl.dateInDao");
	
public ArrayList<Object> fetchData(String hdnLOVId,String pParentGroup,String strParentOption,String branchId,int currPageLink,String method, ArrayList childLOVParam,int lovCount) 
{
	Connection con=null;
	if(strParentOption.trim().equals(","))
		strParentOption="";
	String strParentOptionArr[]=strParentOption.split(",");
	ArrayList<Object> lovRowH = new ArrayList<Object>();
	ArrayList<Object> lovRowD = new ArrayList<Object>();
	StringBuffer lovTableQuery = new StringBuffer();
	StringBuffer lovQuery = new StringBuffer();
	StringBuffer lovCountQry = new StringBuffer();
	StringBuffer lovOperator = new StringBuffer();
	ArrayList<Object> resultList = new ArrayList<Object>();
	ArrayList<Object> list = new ArrayList<Object>();
	ArrayList<Object> subList = new ArrayList<Object>();
	String prtValue1="";
	String prtValue2="";
	String prtValue3="";
	ResultSet rs = null; 
	Statement stmt = null;
	ResultSetMetaData rsMetaData=null;
	int count=0;
	int numberOfColumns=0;
	lovTableQuery.append("SELECT LOV_QRY,LOV_FRM_ELEMENT, LOV_KEY,LOV_VALUE,LOV_OPERATOR,LOV_PRNT_COL_NAME,LOV_PAGE_TITLE,LOV_KEY_TITLE,LOV_VALUE_TITLE," );
	lovTableQuery.append("LOV_CODE_DESC_FLAG,LOV_PRNT_COL_NAME1,LOV_TITLE1,LOV_TITLE2,LOV_TITLE3,LOV_SEC_PRNT_COL_NAME,LOV_THIRD_PRNT_COL_NAME,LOV_COUNT_QRY" );
	lovTableQuery.append(" FROM lov_data_table WHERE LOV_ID='"+hdnLOVId+"'");
	try
	{
		lovRowH = ConnectionDAO.sqlSelect(lovTableQuery.toString());
		lovRowD=(ArrayList)lovRowH.get(0);
		String query=CommonFunction.checkNull(lovRowD.get(0));
		String ctQuery=CommonFunction.checkNull(lovRowD.get(16));
		
		if(childLOVParam.size()>0)
		{
			for (int i=0;i<childLOVParam.size();i++) 
			{
				subList = (ArrayList) childLOVParam.get(i);
				if (subList.size() > 0)
				{
					
					query=query.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
					ctQuery=ctQuery.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
				}
				subList.clear();
				subList=null;				
			}	
		}
		lovQuery.append(query);
		lovCountQry.append(ctQuery);
		query=null;
		ctQuery=null;
		int whereIndexNo=lovQuery.toString().toLowerCase().lastIndexOf("where");
		if(whereIndexNo <0)
		{
			lovQuery.append( " WHERE 1=1 ");
			lovCountQry.append( " WHERE 1=1 ");
		}
		lovOperator.append(CommonFunction.checkNull(lovRowD.get(4)).trim());
		if(lovOperator.toString().trim().equalsIgnoreCase("where") || lovOperator.toString().trim().equalsIgnoreCase(""))
			lovOperator.append(" AND ");
		if(strParentOptionArr.length>=1)
		prtValue1=strParentOptionArr[0];
		if(CommonFunction.checkNull(prtValue1).trim().length()>0 && !CommonFunction.checkNull(lovRowD.get(5)).equalsIgnoreCase("") )
		{
			//try to append LOV_OPERATOR,LOV_PRNT_COL_NAME
			lovQuery.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(5))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0].trim()))+"'");
			lovCountQry.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(5))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0].trim()))+"'");
		}
		if(strParentOptionArr.length>=2)
			prtValue2=strParentOptionArr[1];
		if(CommonFunction.checkNull(prtValue2).trim().length()>0 && !CommonFunction.checkNull(lovRowD.get(14)).equalsIgnoreCase("") )
		{
			//try to append LOV_OPERATOR,LOV_SEC_PRNT_COL_NAME
			lovQuery.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(14))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1].trim()))+"'");
			lovCountQry.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(14))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1].trim()))+"'");
		}
		if(strParentOptionArr.length>=3)
			prtValue3=strParentOptionArr[1];
		if(CommonFunction.checkNull(prtValue3).trim().length()>0 && !CommonFunction.checkNull(lovRowD.get(15)).equalsIgnoreCase("") )
		{
			//try to append LOV_OPERATOR,LOV_THIRD_PRNT_COL_NAME
			lovQuery.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(15))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2].trim()))+"'");
			lovCountQry.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(15))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2].trim()))+"'");
		}
		//Try to add Default Paraent
		if(!CommonFunction.checkNull(lovRowD.get(10)).equalsIgnoreCase("") )
		{
			lovQuery.append(" AND "+CommonFunction.checkNull(lovRowD.get(10))+"='"+branchId+"'");
			lovCountQry.append(" AND "+CommonFunction.checkNull(lovRowD.get(10))+"='"+branchId+"'");
		}
		int startRecordIndex=0;
		int endRecordIndex = no;
		if(currPageLink==1)
		{
			logger.info("Final Count Query  :   "+lovCountQry);	
			String cha=ConnectionDAO.singleReturn(lovCountQry.toString());
			if(CommonFunction.checkNull(cha).trim().equalsIgnoreCase(""))
				cha="0";
			count = Integer.parseInt(cha);
		}
		if(currPageLink>1)
		{
			startRecordIndex = (currPageLink-1)*no;
			endRecordIndex = no;
			count=lovCount;
		}
		lovQuery.append(" limit "+startRecordIndex+","+endRecordIndex);
		logger.info("Final LOV Query    :   "+lovQuery);
		con = (Connection) ConnectionDAO.getConnection();	
		stmt = con.createStatement();
		rs = stmt.executeQuery(lovQuery.toString());
		rsMetaData =rs.getMetaData();
	    numberOfColumns = rsMetaData.getColumnCount();
		
	    
	    while(rs.next())
	    {
	    	PopupVo voObject = new PopupVo();
	    	if(numberOfColumns==2)
	    	{
	    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
	    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
			}
	    	else if(numberOfColumns==3)
	    	{
	    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
	    		
	    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
	    		voObject.setValOfotherComponent1(StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3))))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+""+"\" />");
	    	}	
	    	else if(numberOfColumns==4)
	    	{
	    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
	    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
	    		voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+""+"\" />");
	    		voObject.setValOfotherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(4)))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(4)))+""+"\" />");
	    		
	    	}	
	    	else if(numberOfColumns==5)
	    	{
	    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
	    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
	    		voObject.setValOfotherComponent1(CommonFunction.checkNull(rs.getString(3))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+""+"\" />");
	    		voObject.setValOfotherComponent2(CommonFunction.checkNull(rs.getString(4))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(4)))+""+"\" />");
	    		voObject.setValOfotherComponent3(CommonFunction.checkNull(rs.getString(5))+" "+"<input type='hidden' name='otherComponent3' id='otherComponent3' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(5)))+""+"\" />");
	    		
	    	}	
	    	list.add(voObject);
	    }
	    TableVo tableVoObj = new TableVo();
	    tableVoObj.setLovPageTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(6))));
		tableVoObj.setLovKeyTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(7))));		  
	    tableVoObj.setLovValueTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));		   
	    if(numberOfColumns==2)
	    {
	    	tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
			tableVoObj.setNoOfColumn("2");
	    }
	    else if(numberOfColumns==3)
	    {
	    	tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
			tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(11))));
			tableVoObj.setNoOfColumn("3");
	    }	
	    else if(numberOfColumns==4)
	    {
	    	tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
			tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(11))));
			tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(12))));
			tableVoObj.setNoOfColumn("4");
	    }	
	    else if(numberOfColumns==5)
	    {
	    	
			tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
			tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(11))));
			tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(12))));
			tableVoObj.setOtherComponent3(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(13))));
			tableVoObj.setNoOfColumn("5");
	    }	
		tableVoObj.setLovIdComponent(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(1))));
		tableVoObj.setTotalRecordSize(count);
		list.add(tableVoObj);			
	}
	catch (Exception ex) 
	{ex.printStackTrace();}
	finally 
	{
		prtValue1=null;
		prtValue2=null;
		prtValue3=null;
		lovRowH.clear();
		lovRowH=null;
		lovRowD.clear();
		lovRowD=null;
		lovTableQuery=null;
		hdnLOVId=null;
		strParentOption=null;
		method=null;
		branchId=null;
		childLOVParam=null;
		lovOperator=null;
		try
		{
			rsMetaData=null;
			rs.close();
			stmt.close();
			ConnectionDAO.closeConnection(con, stmt, rs);
		} 
		catch (SQLException e) 
		{e.printStackTrace();}
	}	
	resultList.add(count);
	resultList.add(list);
	return resultList;
	}
	
	public ArrayList<Object> fetchDataByParameter(String lovDesc, String nextField , String lovId,String strParentOption,int currPageLink,String method,String branchId,ArrayList childLOVParam,int lovCount)
	{	
		Connection con=null;
		if(strParentOption.trim().equals(","))
			strParentOption="";
		String strParentOptionArr[]=strParentOption.split(",");
		ArrayList<Object> lovRowH = new ArrayList<Object>();
		ArrayList<Object> lovRowD = new ArrayList<Object>();
		StringBuffer lovTableQuery = new StringBuffer();
		StringBuffer lovOperator = new StringBuffer();		
		ArrayList<Object> list = new ArrayList<Object>();
		ArrayList<Object> resultList = new ArrayList<Object>();		
		ArrayList subList = new ArrayList();
		StringBuffer lovQuery = new StringBuffer();
		StringBuffer lovCountQry = new StringBuffer();
		String prtValue1="";
		String prtValue2="";
		String prtValue3="";
		ResultSet rs = null; 
		Statement stmt = null;
		ResultSetMetaData rsMetaData=null;
		int count=0;
		int numberOfColumns=0;
		lovTableQuery.append("SELECT LOV_QRY,LOV_FRM_ELEMENT, LOV_KEY,LOV_VALUE,LOV_OPERATOR,LOV_PRNT_COL_NAME,LOV_PAGE_TITLE,LOV_KEY_TITLE,LOV_VALUE_TITLE," );
		lovTableQuery.append("LOV_CODE_DESC_FLAG,LOV_PRNT_COL_NAME1,LOV_TITLE1,LOV_TITLE2,LOV_TITLE3,LOV_SEC_PRNT_COL_NAME,LOV_THIRD_PRNT_COL_NAME,LOV_COUNT_QRY" );
		lovTableQuery.append(" FROM lov_data_table WHERE LOV_ID='"+lovId+"'");
		try
		{
			lovRowH = ConnectionDAO.sqlSelect(lovTableQuery.toString());
			lovRowD=(ArrayList)lovRowH.get(0);
			String query=CommonFunction.checkNull(lovRowD.get(0));
			String ctQuery=CommonFunction.checkNull(lovRowD.get(16));
			
			if(childLOVParam.size()>0)
			{
				for (int i=0;i<childLOVParam.size();i++) 
				{
					subList = (ArrayList) childLOVParam.get(i);
					if (subList.size() > 0)
					{
						query=query.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
						ctQuery=ctQuery.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
					}
					subList.clear();
					subList=null;				
				}	
			}
			lovQuery.append(query);
			lovCountQry.append(ctQuery);
			query=null;
			ctQuery=null;
			int whereIndexNo=lovQuery.toString().toLowerCase().lastIndexOf("where");
			if(whereIndexNo <0)
			{
				lovQuery.append( " WHERE 'a'='a' ");
				lovCountQry.append( " WHERE 'a' ='a' ");
			}
			lovOperator.append(CommonFunction.checkNull(lovRowD.get(4)).trim());
			if(lovOperator.toString().trim().equalsIgnoreCase("where") || lovOperator.toString().trim().equalsIgnoreCase(""))
				lovOperator.append(" AND ");
			if(!CommonFunction.checkNull(lovDesc).equalsIgnoreCase(""))
			{
				String fieldName=CommonFunction.checkNull(lovRowD.get(3)).trim()+",'%d-%m-%Y'";
				int date=lovQuery.toString().toLowerCase().lastIndexOf(fieldName);
				if(date>0)
				{
					
					boolean status=CommonFunction.check(lovDesc);
					if(status)
						lovDesc=lovDesc.substring(6)+"-"+lovDesc.substring(3,5)+"-"+lovDesc.substring(0,2);
					else
						lovDesc=lovDesc;
					lovQuery.append(" and  "+CommonFunction.checkNull(lovRowD.get(3))+"='"+CommonFunction.checkNull(lovDesc).trim()+"'");
					lovCountQry.append(" and  "+CommonFunction.checkNull(lovRowD.get(3))+"='"+CommonFunction.checkNull(lovDesc).trim()+"'");
				}
				else
				{
					lovQuery.append(" and  "+CommonFunction.checkNull(lovRowD.get(3))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'");
					lovCountQry.append(" and  "+CommonFunction.checkNull(lovRowD.get(3))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'");
				}
			}
			if(!CommonFunction.checkNull(nextField).equalsIgnoreCase(""))
			{
				String fieldName=CommonFunction.checkNull(lovRowD.get(2)).trim()+",'%d-%m-%Y'";
				int date=lovQuery.toString().lastIndexOf(fieldName);
				if(date>0)
				{
					boolean status=CommonFunction.check(nextField);
					if(status)
						nextField=nextField.substring(6)+"-"+nextField.substring(3,5)+"-"+nextField.substring(0,2);
					else
						nextField=nextField;
					lovQuery.append(" and  "+CommonFunction.checkNull(lovRowD.get(2))+"='"+CommonFunction.checkNull(nextField).trim()+"'");
					lovCountQry.append(" and  "+CommonFunction.checkNull(lovRowD.get(2))+"='"+CommonFunction.checkNull(nextField).trim()+"'");
				}
				else
				{
					lovQuery.append(" and "+CommonFunction.checkNull(lovRowD.get(2))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"%'");
					lovCountQry.append(" and "+CommonFunction.checkNull(lovRowD.get(2))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"%'");
				}				
			}			
			if(strParentOptionArr.length>=1)
				prtValue1=strParentOptionArr[0];
				if(CommonFunction.checkNull(prtValue1).trim().length()>0 && !CommonFunction.checkNull(lovRowD.get(5)).equalsIgnoreCase("") )
				{
					//try to append LOV_OPERATOR,LOV_PRNT_COL_NAME
					lovQuery.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(5))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0].trim()))+"'");
					lovCountQry.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(5))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0].trim()))+"'");
				}
				if(strParentOptionArr.length>=2)
					prtValue2=strParentOptionArr[1];
				if(CommonFunction.checkNull(prtValue2).trim().length()>0 && !CommonFunction.checkNull(lovRowD.get(14)).equalsIgnoreCase("") )
				{
					//try to append LOV_OPERATOR,LOV_SEC_PRNT_COL_NAME
					lovQuery.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(14))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1].trim()))+"'");
					lovCountQry.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(14))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1].trim()))+"'");
				}
				if(strParentOptionArr.length>=3)
					prtValue3=strParentOptionArr[1];
				if(CommonFunction.checkNull(prtValue3).trim().length()>0 && !CommonFunction.checkNull(lovRowD.get(15)).equalsIgnoreCase("") )
				{
					//try to append LOV_OPERATOR,LOV_THIRD_PRNT_COL_NAME
					lovQuery.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(15))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2].trim()))+"'");
					lovCountQry.append(" "+lovOperator.toString()+" "+CommonFunction.checkNull(lovRowD.get(15))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2].trim()))+"'");
				}
				//Try to add Default Paraent
				if(!CommonFunction.checkNull(lovRowD.get(10)).equalsIgnoreCase("") )
				{
					lovQuery.append(" AND "+CommonFunction.checkNull(lovRowD.get(10))+"='"+branchId+"'");
					lovCountQry.append(" AND "+CommonFunction.checkNull(lovRowD.get(10))+"='"+branchId+"'");
				}
				
				int startRecordIndex=0;
				int endRecordIndex = no;
				if(currPageLink==1)
				{
					logger.info("Final Count Query  :   "+lovCountQry);	
					String cha=ConnectionDAO.singleReturn(lovCountQry.toString());
					if(CommonFunction.checkNull(cha).trim().equalsIgnoreCase(""))
						cha="0";
					count = Integer.parseInt(cha);
				}
				if(currPageLink>1)
				{
					startRecordIndex = (currPageLink-1)*no;
					endRecordIndex = no;
					count=lovCount;
				}
				lovQuery.append(" limit "+startRecordIndex+","+endRecordIndex);
				logger.info("Final LOV Query    :   "+lovQuery);
				con = (Connection) ConnectionDAO.getConnection();	
				stmt = con.createStatement();
				rs = stmt.executeQuery(lovQuery.toString());
				rsMetaData =rs.getMetaData();
			    numberOfColumns = rsMetaData.getColumnCount();
			
		    
		    while(rs.next())
		    {
		    	PopupVo voObject = new PopupVo();
		    	if(numberOfColumns==2)
		    	{
		    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
		    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
				}
		    	else if(numberOfColumns==3)
		    	{
		    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
		    		
		    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
		    		voObject.setValOfotherComponent1(StringEscapeUtils.escapeJavaScript(CommonFunction.checkNull(rs.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+""+"\" />");
		    	}	
		    	else if(numberOfColumns==4)
		    	{
		    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
		    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
		    		voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+""+"\" />");
		    		voObject.setValOfotherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(4)))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(4)))+""+"\" />");
		    		
		    	}	
		    	else if(numberOfColumns==5)
		    	{
		    		voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+""+"\" />");
		    		voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(2)))+""+"\" />");
		    		voObject.setValOfotherComponent1(CommonFunction.checkNull(rs.getString(3))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(3)))+""+"\" />");
		    		voObject.setValOfotherComponent2(CommonFunction.checkNull(rs.getString(4))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(4)))+""+"\" />");
		    		voObject.setValOfotherComponent3(CommonFunction.checkNull(rs.getString(5))+" "+"<input type='hidden' name='otherComponent3' id='otherComponent3' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString(5)))+""+"\" />");
		    		
		    	}	
		    	list.add(voObject);
		    }
		    TableVo tableVoObj = new TableVo();
		    tableVoObj.setLovPageTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(6))));
			tableVoObj.setLovKeyTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(7))));		  
		    tableVoObj.setLovValueTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));		   
		    if(numberOfColumns==2)
		    {
		    	tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
				tableVoObj.setNoOfColumn("2");
		    }
		    else if(numberOfColumns==3)
		    {
		    	tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
				tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(11))));
				tableVoObj.setNoOfColumn("3");
		    }	
		    else if(numberOfColumns==4)
		    {
		    	tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
				tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(11))));
				tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(12))));
				tableVoObj.setNoOfColumn("4");
		    }	
		    else if(numberOfColumns==5)
		    {
		    	
				tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(8))));
				tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(11))));
				tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(12))));
				tableVoObj.setOtherComponent3(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(13))));
				tableVoObj.setNoOfColumn("5");
		    }	
			tableVoObj.setLovIdComponent(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(lovRowD.get(1))));
			tableVoObj.setTotalRecordSize(count);
			list.add(tableVoObj);			
		}
		catch (Exception ex) 
		{ex.printStackTrace();}
		finally 
		{
			prtValue1=null;
			prtValue2=null;
			prtValue3=null;
			lovRowH.clear();
			lovRowH=null;
			lovRowD.clear();
			lovRowD=null;
			lovTableQuery=null;
			lovDesc=null;
			nextField=null;
			lovId=null;
			strParentOption=null;
			lovOperator=null;
			method=null;
			branchId=null;
			childLOVParam=null;
			try
			{
				rsMetaData=null;
				rs.close();
				stmt.close();
				ConnectionDAO.closeConnection(con, stmt, rs);
			} 
			catch (SQLException e) 
			{e.printStackTrace();}
		}		
		resultList.add(count);
		resultList.add(list);
		return resultList;
	}
	
	
	
	
	//multi select LOV methods
	public ArrayList<Object> multiSelectFetchData(String hdnLOVId,String pParentGroup,String strParentOption,String branchId,String LovListItemsIds,ArrayList childLOVParam) 
	{
		Connection con=null;
	
		ArrayList<Object> list = new ArrayList<Object>();
		ResultSet rs = null; 
		ResultSet rs1 = null;
		Statement stmt = null;
		Statement stmt1 = null;
		
		String []strParentOptionValues = strParentOption.toString().split("\\|");
		String []LovListItemsIdsValues = LovListItemsIds.toString().split("\\|");
		for(int i=0;i<strParentOptionValues.length;i++)
		{
			logger.info("strParentOptionValues.............................."+strParentOptionValues[i]);
		}
		
		try {
				con = ConnectionDAO.getConnection();
	            String strLOVQuery = "SELECT LOV_QRY,LOV_FRM_ELEMENT, LOV_KEY,LOV_VALUE,LOV_OPERATOR,LOV_PRNT_COL_NAME,LOV_PAGE_TITLE,LOV_KEY_TITLE,LOV_VALUE_TITLE," +
	            		"LOV_CODE_DESC_FLAG,LOV_PRNT_COL_NAME1,LOV_TITLE1,LOV_TITLE2,LOV_TITLE3 FROM lov_data_table WHERE LOV_ID='"+hdnLOVId+"'";
				if (con != null) {
					logger.info(strLOVQuery);
				stmt = con.createStatement();
				rs = stmt.executeQuery(strLOVQuery);
				String query="";
				if(rs.next())
				{
					
					stmt1 = con.createStatement();
					if((!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("")) && (CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))!=null && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase("")))
					{
						query=CommonFunction.checkNull(rs.getString(1));
						
						//String lovQuery=CommonFunction.checkNull(rs.getString(1));
						logger.info("origional lov query *********************** "+CommonFunction.checkNull(rs.getString(1)));
						ArrayList subList = new ArrayList();
						if(childLOVParam.size()>0)
						{
							
							for (int i = 0; i < childLOVParam.size(); i++) 
							{
								subList = (ArrayList) childLOVParam.get(i);
								if (subList.size() > 0)
								{
									logger.info("child lov variable name *********************** "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim());
									logger.info("child lov value *********************** "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1))).trim());
									if(query.indexOf(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim()) > 0)
									{
										query =  query.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
									}
									
								}
							}
						}
						
						logger.info("query *********************** "+query);
						
						
						query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionValues[0]+"'";
						 for(int i=1;i<strParentOptionValues.length;i++)
						    {
						    	query=query+" OR "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionValues[i]+"'";
						    }
						 
						query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
						logger.info(query);
					}
					else 
					if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase(""))
					{
						
					    query=CommonFunction.checkNull(rs.getString(1));
					    query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionValues[0]+"'";
					    for(int i=1;i<strParentOptionValues.length;i++)
					    {
					    	query=query+" OR "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionValues[i]+"'";
					    }
						
						logger.info(query);
					}
					else
					{
						query=CommonFunction.checkNull(rs.getString(1));
						
						//String lovQuery=CommonFunction.checkNull(rs.getString(1));
						logger.info("origional lov query *********************** "+CommonFunction.checkNull(rs.getString(1)));
						ArrayList subList = new ArrayList();
						if(childLOVParam.size()>0)
						{
							
							for (int i = 0; i < childLOVParam.size(); i++) 
							{
								subList = (ArrayList) childLOVParam.get(i);
								if (subList.size() > 0)
								{
									logger.info("child lov variable name *********************** "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim());
									logger.info("child lov value *********************** "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1))).trim());
									if(query.indexOf(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim()) > 0)
									{
										query =  query.replace(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim(), StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1))).trim());
									}
									
								}
							}
						}
						
						logger.info("query *********************** "+query);
						if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase(""))
						{
							query=query.toUpperCase();
							if(query.lastIndexOf("WHERE") > 0)
							{
								query = query +" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
							}
							else
							{
								query = query +" WHERE "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
							}
						}
						logger.info("query :"+query);
					}
				
				}
				rs1 = stmt1.executeQuery(query);
				String columnName[]= new String[5];
				ResultSetMetaData rsMetaData = rs1.getMetaData();
			    int numberOfColumns = rsMetaData.getColumnCount();
			    logger.info("total no of column............................."+numberOfColumns);
			    for (int i = 1, j=0; i < numberOfColumns + 1; i++,j++) {
				       columnName[j] = CommonFunction.checkNull(rsMetaData.getColumnName(i));
				      
				      
				    }
						while(rs1.next())
						{
							boolean flag=false;
							PopupVo voObject = new PopupVo();
							
							if(numberOfColumns==2)
						    {
								//logger.info("id value ..222......................... "+CommonFunction.checkNull(rs1.getString(1)));
								for(int i=0;i<LovListItemsIdsValues.length;i++)
								{
									if(CommonFunction.checkNull(rs1.getString(1)).toString().equalsIgnoreCase(LovListItemsIdsValues[i]))
									{
										flag=true;
										break;
										
									}
								}
								if(flag)
								{
									voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn'  id='checkboxBtn' checked='checked' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
								}
								else
								{
									voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn' id='checkboxBtn' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
								}
								//voObject.setLovKey(rs1.getString(1));
								voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
								
						    }
						    else if(numberOfColumns==3)
						    {
						    	//logger.info("id value ....333....................... "+CommonFunction.checkNull(rs1.getString(3)));
						    	int i=0;
						    	for(i=0;i<LovListItemsIdsValues.length;i++)
								{
									if(CommonFunction.checkNull(rs1.getString(1)).toString().trim().equalsIgnoreCase(LovListItemsIdsValues[i].trim()))
									{
										flag=true;
										break;
									}
								}
						    	if(flag)
						    	{
						    		//logger.info("LovListItemsIdsValues....333************************* "+LovListItemsIdsValues[i]);
									voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn' id='checkboxBtn' checked='checked' value="+"\""+CommonFunction.checkNull(StringEscapeUtils.escapeHtml(rs1.getString(1)))+""+"\" />");
						    	}
						    	else
						    	{
						    		voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn' id='checkboxBtn' value="+"\""+CommonFunction.checkNull(StringEscapeUtils.escapeHtml(rs1.getString(1)))+""+"\" />");
						    	}
								voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
								voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
						    }	
						   	
							list.add(voObject);
						}
					    TableVo tableVoObj = new TableVo();
					    tableVoObj.setLovPageTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_PAGE_TITLE"))));
						tableVoObj.setLovKeyTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_KEY_TITLE"))));
					    //tableVoObj.setLovKeyTitle(columnName[1]);
					    tableVoObj.setLovValueTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
					    //tableVoObj.setLovValueTitle(columnName[2]);
					    if(numberOfColumns==2)
					    {
					    	//tableVoObj.setLovKey(columnName[0]);
							tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
							tableVoObj.setNoOfColumn("2");
					    }
					    else if(numberOfColumns==3)
					    {
					    	//tableVoObj.setLovKey(columnName[0]);
							tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
							tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
							tableVoObj.setNoOfColumn("3");
					    }	
					    
							
							tableVoObj.setLovIdComponent(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_FRM_ELEMENT"))));
							list.add(tableVoObj);
			}
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs1.close();
				stmt1.close();
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionDAO.closeConnection(con, stmt, rs);
			
		}
	
		return list;
		
	}
	
	public ArrayList<Object> MultiSelectfetchDataByParameter(String lovDesc, String nextField , String lovId,String strParentOption,int currentPageLink,String method,String LovListItemsIds, String branchId,ArrayList childLOVParam)
	{	
		Connection con=null;
		
		ArrayList<Object> list = new ArrayList<Object>();
		ResultSet rs = null; 
		ResultSet rs1 = null;
		//ResultSet rs2 = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		String query1="";
		int count=0;
		String parent1Values = "(";
		
		if(strParentOption!=null || !strParentOption.equalsIgnoreCase(""))
		{
			String[] strParentOptionArr=strParentOption.toString().split("\\|");
			
			parent1Values = parent1Values+"'"+strParentOptionArr[0]+"'";
			for(int i=1;i<strParentOptionArr.length;i++)
			{
				parent1Values = parent1Values+",'"+strParentOptionArr[i]+"'";
			}
			parent1Values=parent1Values.substring(0, parent1Values.length())+")";
			
		}
		String []LovListItemsIdsValues = LovListItemsIds.toString().split("\\|");
		
	try {
				con = (Connection) ConnectionDAO.getConnection();
									
				String strLOVQuery = "SELECT LOV_QRY,LOV_FRM_ELEMENT, LOV_KEY,LOV_VALUE,LOV_OPERATOR,LOV_PRNT_COL_NAME,LOV_PAGE_TITLE,LOV_KEY_TITLE,LOV_VALUE_TITLE," +
        		"LOV_CODE_DESC_FLAG,LOV_PRNT_COL_NAME1,LOV_TITLE1,LOV_TITLE2,LOV_TITLE3,LOV_SEC_PRNT_COL_NAME,LOV_THIRD_PRNT_COL_NAME,LOV_COUNT_QRY" +
        		" FROM lov_data_table WHERE LOV_ID='"+lovId+"'";
		if (con != null)
		{
			logger.info(strLOVQuery);
			stmt = con.createStatement();
			rs = stmt.executeQuery(strLOVQuery);
			
			if(rs.next())
			{
				stmt1 = con.createStatement();
				rs1 = stmt1.executeQuery(CommonFunction.checkNull(rs.getString("LOV_QRY")));
			}
			
			
			String columnName[]= new String[5];
			String query="";
			String countQuery="";
			ResultSetMetaData rsMetaData = rs1.getMetaData();
		    int numberOfColumns = rsMetaData.getColumnCount();
		    logger.info("total no of column............................."+numberOfColumns);
		    for (int i = 1, j=0; i < numberOfColumns + 1; i++,j++) {
		    	columnName[j] = rsMetaData.getColumnLabel(i);
			      
			    }
		   
		    String lovQuery=CommonFunction.checkNull(rs.getString(1));
		    String lovCountQuery=CommonFunction.checkNull(rs.getString("LOV_COUNT_QRY"));
			logger.info("origional lov query *********************** "+CommonFunction.checkNull(rs.getString(1)));
			logger.info("origional lov Count Query *********************** "+lovCountQuery);
			ArrayList subList = new ArrayList();
			if(childLOVParam.size()>0)
			{
				
				for (int i = 0; i < childLOVParam.size(); i++) 
				{
					subList = (ArrayList) childLOVParam.get(i);
					if (subList.size() > 0)
					{
						logger.info("child lov variable name *********************** "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim());
						logger.info("child lov value *********************** "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1))).trim());
						if(lovQuery.indexOf(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim()) > 0)
						{
							lovQuery =  lovQuery.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
							lovCountQuery=lovCountQuery.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
						}
						
					}
				}
			}
			
			logger.info("lovQuery *********************** "+lovQuery);
			logger.info("lovCountQuery *********************** "+lovCountQuery);
		    
		    int whereIndexNo =0;
		    int ctr=0;
		    whereIndexNo= lovQuery.lastIndexOf("where");
		    if(whereIndexNo<0)
		    {
		    	whereIndexNo= lovQuery.lastIndexOf("WHERE");
		    }
			if(lovDesc!="")
			{
				if(whereIndexNo > 0)
				{
					query=lovQuery+ " and  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
					countQuery=lovCountQuery+ " and  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
				}
				else
				{
					query=lovQuery+ " WHERE  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
					countQuery=lovCountQuery+ " WHERE  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
				}
				if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase(""))
				{
						query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+" in "+parent1Values+"";
						countQuery=lovCountQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+" in "+parent1Values+"";
				}
			}
			else if(nextField!="")
			{
				if(whereIndexNo > 0)
				{
					//logger.info("next field where ...........................");
					query=lovQuery+ " and "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"%'";
					countQuery=lovCountQuery+ " and "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"%'";
				}
				else
				{
					query=lovQuery+ " WHERE "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField).trim())+"%'";
					countQuery=lovCountQuery+ " WHERE "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField).trim())+"%'";
				}
				
				if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase(""))
				{
						 query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+" in "+parent1Values+"";
						 countQuery=countQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+" in "+parent1Values+"";
				}
			}
			else
			{
				if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase(""))
				{
					query=lovQuery;
					countQuery=lovCountQuery;
					query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+" in "+parent1Values+"";
					countQuery=countQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+" in "+parent1Values+"";
				}
				else
				{
					query=lovQuery;
					countQuery=lovCountQuery;
				}
			}
			
			if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase(""))
			{
				query=query.toUpperCase();
				countQuery=countQuery.toUpperCase();
				if(query.lastIndexOf("WHERE") > 0)
				{
					query = query +" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
					countQuery=countQuery+" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
				}
				else
				{
					query = query +" WHERE "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
					countQuery=countQuery+" WHERE "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
				}
			}
			
//			if((CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME"))!=null && !CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME")).equalsIgnoreCase("")))
//			{
//				query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1]).trim())+"'"; 
//			
//			}
//			if((CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME"))!=null && !CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME")).equalsIgnoreCase("")))
//			{
//				query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2]).trim())+"'";  
//				
//			}
			/*String tempQuery="";
			int distintExist =  query.toUpperCase().indexOf("DISTINCT");
			if(distintExist>0)
			{
				String str = query.toUpperCase().substring(0, query.toUpperCase().indexOf("FROM"));
				str = str.toUpperCase().substring(distintExist+8,str.indexOf(","));
				str=" count(DISTINCT "+str+")";
				tempQuery =query.replace(query.substring(distintExist,query.toUpperCase().indexOf("FROM")), str);
			}
			else
			{
				tempQuery =query.replace(query.substring(query.toUpperCase().indexOf(" "),query.toUpperCase().indexOf("FROM")), " count(1) ");
			}
			logger.info("temp query ....................................... "+tempQuery);
			// START BY PRASHANT
			if(CommonFunction.checkNull(tempQuery).toUpperCase().contains("GROUP BY"))
			   {
				tempQuery="SELECT COUNT(*) FROM ("+tempQuery+") AS TEMP";
			   }
			//END BY PRASHANT
*/			//count = Integer.parseInt(ConnectionDAO.singleReturn(tempQuery));
			count = Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
			
			if(lovDesc.trim()==null && nextField.trim()==null)
			{
			int startRecordIndex=0;
			int endRecordIndex = no;
			
			if(currentPageLink>1)
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
			}
			
			query = query +" limit "+startRecordIndex+","+endRecordIndex;
			}
					
			rs1.close();
				logger.info("LOV query ......................."+query);
				stmt2 = con.createStatement();
				rs1 = stmt2.executeQuery(query);
				
			    while(rs1.next())
				{
			    	boolean flag=false;
					PopupVo voObject = new PopupVo();
					
					if(numberOfColumns==2)
				    {
						
						for(int i=0;i<LovListItemsIdsValues.length;i++)
						{
							if(CommonFunction.checkNull(rs1.getString(1)).toString().equalsIgnoreCase(LovListItemsIdsValues[i]))
							{
								flag=true;
								break;
							}
						}
						if(flag)
						{
							voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn' id='checkboxBtn' checked='checked' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
						}
						else
						{
							voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn' id='checkboxBtn' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
						}
						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
						
				    }
				    else if(numberOfColumns==3)
				    {
				    	for(int i=0;i<LovListItemsIdsValues.length;i++)
						{
							if(CommonFunction.checkNull(rs1.getString(1)).toString().equalsIgnoreCase(LovListItemsIdsValues[i]))
							{
								flag=true;
								break;
								
							}
						}
						if(flag)
						{
							voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn' id='checkboxBtn' checked='checked' value="+"\""+CommonFunction.checkNull(StringEscapeUtils.escapeHtml(rs1.getString(1)))+""+"\" />");
						}
						else
						{
							voObject.setRadiobutton("<input type='checkbox' name='checkboxBtn' id='checkboxBtn' value="+"\""+CommonFunction.checkNull(StringEscapeUtils.escapeHtml(rs1.getString(1)))+""+"\" />");
						}
				    	
						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
						voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
				    }	
//				    else if(numberOfColumns==4)
//				    {
//				    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' />");
//						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+"' />");
//						voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+"' />");
//						voObject.setValOfotherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+"' />");
//						
//				    }	
//				    else if(numberOfColumns==5)
//				    {
//				    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' />");
//						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+"' />");
//						voObject.setValOfotherComponent1(CommonFunction.checkNull(rs1.getString(3))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+"' />");
//						voObject.setValOfotherComponent2(CommonFunction.checkNull(rs1.getString(4))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+"' />");
//						voObject.setValOfotherComponent3(CommonFunction.checkNull(rs1.getString(5))+" "+"<input type='hidden' name='otherComponent3' id='otherComponent3' value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(5)))+"' />");
//						
//				    }	
					
					list.add(voObject);
				}
			    TableVo tableVoObj = new TableVo();
			    tableVoObj.setLovPageTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_PAGE_TITLE"))));
				tableVoObj.setLovKeyTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_KEY_TITLE"))));
			  
			    tableVoObj.setLovValueTitle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
			   
			    if(numberOfColumns==2)
			    {
			    	
					tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
					tableVoObj.setNoOfColumn("2");
			    }
			    else if(numberOfColumns==3)
			    {
			    	
					tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
					tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
					tableVoObj.setNoOfColumn("3");
			    }	
//			    else if(numberOfColumns==4)
//			    {
//			    	
//					tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
//					tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
//					tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE2"))));
//					tableVoObj.setNoOfColumn("4");
//			    }	
//			    else if(numberOfColumns==5)
//			    {
//			    	
//					tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
//					//logger.info("title 1 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE1")));
//					//logger.info("title 2 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE2")));
//					//logger.info("title 3 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE3")));
//					tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
//					tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE2"))));
//					tableVoObj.setOtherComponent3(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE3"))));
//					tableVoObj.setNoOfColumn("5");
//			    }	
					
					tableVoObj.setLovIdComponent(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_FRM_ELEMENT"))));
					
					tableVoObj.setTotalRecordSize(count);
					list.add(tableVoObj);
		}
		
	   }
		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs1.close();
				stmt1.close();
				stmt2.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectionDAO.closeConnection(con, stmt, rs);
		}
		
		return list;
	}
}

