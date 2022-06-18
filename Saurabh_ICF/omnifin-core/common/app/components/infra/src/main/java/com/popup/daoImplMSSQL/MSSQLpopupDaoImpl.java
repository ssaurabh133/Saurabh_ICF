package com.popup.daoImplMSSQL;
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
import com.popup.dao.popupDao;
import com.popup.vo.PopupVo;
import com.popup.vo.TableVo;

public class MSSQLpopupDaoImpl implements popupDao{
	private static final Logger logger = Logger.getLogger(MSSQLpopupDaoImpl.class.getName());

	
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	
	int no=Integer.parseInt(resource.getString("msg.pageSizeForLov"));
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	public ArrayList<Object> fetchData(String hdnLOVId,String pParentGroup,String strParentOption,String branchId,int currPageLink,String method, ArrayList childLOVParam,int lovCount) 
	{
		if(strParentOption.trim().equals(","))
			strParentOption="";
		Connection con=null;		
		ArrayList<Object> list = new ArrayList<Object>();
		ResultSet rs = null; 
		ResultSet rs1 = null;
		Statement stmt = null;
		Statement stmt1 = null;
		String query1="";	
		String pParentGroupArr[] = pParentGroup.split(",");
		String strParentOptionArr[]=strParentOption.split(",");
		int count=0;
		try {
				con = ConnectionDAO.getConnection();
	            String strLOVQuery = "SELECT LOV_QRY,LOV_FRM_ELEMENT, LOV_KEY,LOV_VALUE,LOV_OPERATOR,LOV_PRNT_COL_NAME,LOV_PAGE_TITLE,LOV_KEY_TITLE,LOV_VALUE_TITLE," +
	            		"LOV_CODE_DESC_FLAG,LOV_PRNT_COL_NAME1,LOV_TITLE1,LOV_TITLE2,LOV_TITLE3,LOV_SEC_PRNT_COL_NAME,LOV_THIRD_PRNT_COL_NAME,LOV_COUNT_QRY" +
	            		" FROM lov_data_table WHERE LOV_ID='"+hdnLOVId+"'";
				if (con != null) 
				{
					logger.info("Get LOV Query and other attrubutes From lov table: "+strLOVQuery);
					stmt = con.createStatement();
					rs = stmt.executeQuery(strLOVQuery);
					String query="";
					String countQuery="";
					if(rs.next())
					{
						stmt1 = con.createStatement();
						String lovQuery=CommonFunction.checkNull(rs.getString(1));
						String lovCountQry=CommonFunction.checkNull(rs.getString("LOV_COUNT_QRY"));
						logger.info("origional lov query *********************** "+CommonFunction.checkNull(rs.getString(1)));
						logger.info("origional lov Count Qry *********************** "+lovCountQry);
						ArrayList subList = new ArrayList();
						if(childLOVParam.size()>0)
						{
							
							for (int i = 0; i < childLOVParam.size(); i++) 
							{
								subList = (ArrayList) childLOVParam.get(i);
								if (subList.size() > 0)
								{
									
							    	if(lovQuery.indexOf(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0))).trim()) > 0)
									{
										lovQuery =  lovQuery.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
										lovCountQry=lovCountQry.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
									}
									
								}
							}
						}
						logger.info("lovQuery *********************** "+lovQuery);
						query=lovQuery;
						countQuery=lovCountQry;
						logger.info("rs.getString(LOV_PRNT_COL_NAME) *********************** "+rs.getString("LOV_PRNT_COL_NAME"));
						logger.info("rs.getString(LOV_PRNT_COL_NAME1) *********************** "+rs.getString("LOV_PRNT_COL_NAME1"));
						logger.info("rs.getString(strParentOption.trim().length()) *********************** "+strParentOption.trim().length());
						
						
						//if((!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("")) && (!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase("")))
						if(rs.getString("LOV_PRNT_COL_NAME")!=null && rs.getString("LOV_PRNT_COL_NAME1")!= null)
						{		
							if(strParentOption != null && strParentOption.trim().length() !=0){
									query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionArr[0]+"'";
									countQuery=countQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionArr[0]+"'";
							}
							if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase("")){
							        query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
							        countQuery=countQuery+" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
							}
							        logger.info("In If(LOV_PRNT_COL_NAME,LOV_PRNT_COL_NAME1) Query:-"+query);
							        logger.info("In If(LOV_PRNT_COL_NAME,LOV_PRNT_COL_NAME1) count Query:-"+countQuery);
						}					
						else 
						{
							if(rs.getString("LOV_PRNT_COL_NAME")!=null)
							{
								if(strParentOption != null && strParentOption.trim().length() !=0){
									query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionArr[0]+"'";
									countQuery=countQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+strParentOptionArr[0]+"'";
								}
									logger.info("In else (LOV_PRNT_COL_NAME) if Query:-"+query);
									logger.info("In else (LOV_PRNT_COL_NAME) if countQuery:-"+countQuery);
							}
							else
							{
								if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1")).equalsIgnoreCase(""))
								{
									query=query.toUpperCase();
									countQuery=countQuery.toUpperCase();
									if(query.lastIndexOf("WHERE") > 0){
										query = query +" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
										countQuery=countQuery+" AND "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
									}else{
										query = query +" WHERE "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
										countQuery=countQuery+" WHERE "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME1"))+"='"+branchId+"'";
									}
									logger.info("In if (LOV_PRNT_COL_NAME1) if Query:-"+query);
									logger.info("In if (LOV_PRNT_COL_NAME1) if countQuery:-"+countQuery);
								}
							}					
						}
						if(rs.getString("LOV_SEC_PRNT_COL_NAME") != null){
							query=query+" AND "+rs.getString("LOV_SEC_PRNT_COL_NAME")+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1]).trim())+"'";
							countQuery=countQuery+" AND "+rs.getString("LOV_SEC_PRNT_COL_NAME")+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1]).trim())+"'";
						}
						if(rs.getString("LOV_THIRD_PRNT_COL_NAME") != null){
							query=query+" AND "+rs.getString("LOV_THIRD_PRNT_COL_NAME")+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2]).trim())+"'";
							countQuery=countQuery+" AND "+rs.getString("LOV_THIRD_PRNT_COL_NAME")+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2]).trim())+"'";
						}						
						/*String tempQuery="";
						int distintExist =  query.toUpperCase().indexOf("DISTINCT");
						logger.info("distinct index ..................... "+distintExist);
						if(distintExist>0)
						{
							//int commaIndex = query.indexOf(",");
							String str = query.toUpperCase().substring(distintExist+8,query.indexOf(","));
						    logger.info("str: "+str);
							str=" count(DISTINCT "+str+")";
							tempQuery =query.replace(query.substring(distintExist,query.toUpperCase().indexOf("FROM")), str);
							//tempQuery = query.replace(query.substring(commaIndex+1,query.toUpperCase().indexOf("FROM")-1), " ) ");
						}
						else
						{
							tempQuery =query.replace(query.substring(query.toUpperCase().indexOf(" "),query.toUpperCase().indexOf("FROM")), " count(1) ");
						}*/
							
						//logger.info("temp query ....................................... "+tempQuery);
						// START BY PRASHANT
						/*if(CommonFunction.checkNull(tempQuery).toUpperCase().contains("GROUP BY"))
						   {
							tempQuery="SELECT COUNT(*) FROM ("+tempQuery+") AS TEMP";
						   }*/
						//END BY PRASHANT
						//count = Integer.parseInt(ConnectionDAO.singleReturn(tempQuery));
						count = Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
						logger.info("count:---------------------------------------------"+count);
						logger.info("query:---------------------------------------------"+query);
					int startRecordIndex=0;
					int endRecordIndex = no;
					if(currPageLink>1)
					{
						startRecordIndex = (currPageLink-1)*no;
						endRecordIndex = no;
					}
					//Nishant space starts

						ResultSet rs2 = null;
						logger.info("MSSQL QUERY BEFORE ADDING ORDER BY query " + query);
						//logger.info("MSSQL QUERY BEFORE ADDING ORDER BY strLOVQuery " + strLOVQuery);
						if(!CommonFunction.checkNull(query).equalsIgnoreCase(""))
						rs2 = stmt1.executeQuery(query);
						ResultSetMetaData colName = rs2.getMetaData();
						String columnName = "";
						//if(rs2.next())
						//{
							logger.info("MSSQL (colName.getColumnName(1)) : " + colName.getColumnName(1));
							columnName=colName.getColumnName(1);
						//}
						query = query +" ORDER BY "+columnName+" OFFSET "+startRecordIndex+" ROWS FETCH NEXT "+endRecordIndex+" ROWS ONLY ";
						logger.info("MSSQL LOV QUERY : " + query);
						
//***********FOR MSSQL 2008**********************************************************//					
//						ResultSet rs2 = null;
//						query = query + ")AS REQUIREDTABLE ";
//						logger.info("Lov query in MSSQL : "+query);
//						rs2 = stmt1.executeQuery(query);
//						logger.info("Lov query for SQL SERVER : " + query);
//						ResultSetMetaData rsmd = rs2.getMetaData();
//						int noOfColumns = rsmd.getColumnCount(); 
//						String columnName = rsmd.getColumnName(1);
//						logger.info("No of columns : " + noOfColumns );
//						logger.info("Last column name : " + rsmd.getColumnName(noOfColumns));				
//						query = query + CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex);	
//***********FOR MSSQL 2008**********************************************************//							
						
					//Nishant space end
					rs1 = stmt1.executeQuery(query);
				}
				String columnName[]= new String[5];
				ResultSetMetaData rsMetaData = rs1.getMetaData();
			    int numberOfColumns = rsMetaData.getColumnCount();
			   // numberOfColumns=numberOfColumns-1;
				
			      for (int i = 1, j=0; i < numberOfColumns + 1; i++,j++) {
				       columnName[j] = rsMetaData.getColumnName(i);
				     
				    }
						while(rs1.next())
						{
							PopupVo voObject = new PopupVo();
							
							if(numberOfColumns==2)
						    {
								//logger.info("id value ..222......................... "+CommonFunction.checkNull(rs1.getString(1)));
								voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
								//voObject.setLovKey(rs1.getString(1));
								voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
								
						    }
						    else if(numberOfColumns==3)
						    {
						    	logger.info("id value ....333....................... "+CommonFunction.checkNull(rs1.getString(1))+"2:    "+CommonFunction.checkNull(rs1.getString(2))+" 3: "+CommonFunction.checkNull(rs1.getString(3)));
						    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+CommonFunction.checkNull(StringEscapeUtils.escapeHtml(rs1.getString(1)))+""+"\" />");
								
								voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
								logger.info("id value: "+voObject.getLovValue());
								voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3))))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
						    }	
						    else if(numberOfColumns==4)
						    {
						    	//logger.info("id value ..444......................... "+CommonFunction.checkNull(rs1.getString(1)));
						    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
								voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
								voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
								
								voObject.setValOfotherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+""+"\" />");
								
						    }	
						    else if(numberOfColumns==5)
						    {
						    	//logger.info("id value ..555......................... "+CommonFunction.checkNull(rs1.getString(1)));
						    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' />");
								voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
								voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
								voObject.setValOfotherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+""+"\" />");
								voObject.setValOfotherComponent3(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(5)))+" "+"<input type='hidden' name='otherComponent3' id='otherComponent3' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(5)))+""+"\" />");
								
						    }	
							
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
					    else if(numberOfColumns==4)
					    {
					    
							tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
							tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
							tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE2"))));
							tableVoObj.setNoOfColumn("4");
					    }	
					    else if(numberOfColumns==5)
					    {
					    	
							tableVoObj.setLovValue(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE")));
							//logger.info("title 1 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE1")));
							//logger.info("title 2 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE2")));
							//logger.info("title 3 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE3")));
							tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
							tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE2"))));
							tableVoObj.setOtherComponent3(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE3"))));
							tableVoObj.setNoOfColumn("5");
					    }	
							
							tableVoObj.setLovIdComponent(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_FRM_ELEMENT"))));
							
							
							tableVoObj.setTotalRecordSize(count);
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
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(0);
		result.add(list);
		
		return result;
		
	}
	
	public ArrayList<Object> fetchDataByParameter(String lovDesc, String nextField , String lovId,String strParentOption,int currentPageLink,String method,String branchId,ArrayList childLOVParam,int lovCount)
	{	
		Connection con=null;
		if(strParentOption.trim().equals(","))
			strParentOption="";
		ArrayList<Object> list = new ArrayList<Object>();
		ResultSet rs = null; 
		ResultSet rs1 = null;
		//ResultSet rs2 = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		String query1="";
		int count=0;
		
		String strParentOptionArr[]=strParentOption.split(",");
		for(int i=0;i<strParentOptionArr.length;i++)
		{
			logger.info("strParentOptionArr................................... "+strParentOptionArr[i]);
		}
	
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
//			Changes By Amit Starts
//				    logger.info("Label of Column 2: "+rsMetaData.getColumnName(2));
//				    logger.info("Datatype of Column 2: "+rsMetaData.getColumnTypeName(2));
//				    logger.info("Label of Column 3: "+rsMetaData.getColumnName(3));
//				    logger.info("Datatype of Column 3: "+rsMetaData.getColumnTypeName(3));
				    int indexDateCol2 = 0;
				    int indexDateCol3 = 0;
				    indexDateCol2 = rsMetaData.getColumnName(2).indexOf("date_format");
				    indexDateCol3 = rsMetaData.getColumnName(3).indexOf("date_format");
//				    logger.info("Index of date in 2: "+indexDateCol2);
//				    logger.info("Index of date in 3: "+indexDateCol3);
//			Changes by Amit End
				    logger.info("total no of column............................."+numberOfColumns);
				    for (int i = 1, j=0; i < numberOfColumns + 1; i++,j++) {
				    	columnName[j] = rsMetaData.getColumnLabel(i);
					     
					    }
				   
				    String lovQuery=CommonFunction.checkNull(rs.getString(1));
					String lovCountQry=CommonFunction.checkNull(rs.getString("LOV_COUNT_QRY"));
					logger.info("origional lov query *********************** "+CommonFunction.checkNull(rs.getString(1)));
					logger.info("origional lov Count Qry *********************** "+lovCountQry);
					
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
									lovCountQry=lovCountQry.replace(CommonFunction.checkNull(subList.get(0)).trim(), CommonFunction.checkNull(subList.get(1))).trim();
								}
								
							}
						}
					}
					
					logger.info("lovQuery *********************** "+lovQuery);
					logger.info("lovCountQry *********************** "+lovCountQry);
					
				    
				    int whereIndexNo =0;
				    whereIndexNo= lovQuery.lastIndexOf("where");
				    if(whereIndexNo<0)
				    {
				    	whereIndexNo= lovQuery.lastIndexOf("WHERE");
				    }
					if(!CommonFunction.checkNull(lovDesc).equalsIgnoreCase(""))
					{
						logger.info("lovDesc: "+lovDesc);
						if(whereIndexNo > 0)
						{
						// Changes by Amit Starts
							if(indexDateCol2>=0){
								query=lovQuery+ " and  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"','"+dateFormat+"')";
								countQuery=lovCountQry+ " and  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"','"+dateFormat+"')";
							}else{
								query=lovQuery+ " and  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
								countQuery=lovCountQry+ " and  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
							}
								// Changes by Amit Ends
						}
						else
						{
						// Changes by Amit Starts
							if(indexDateCol2>=0){
								query=lovQuery+ " WHERE  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"','"+dateFormat+"')";
								countQuery=lovCountQry+ " WHERE  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"','"+dateFormat+"')";
							}else{
								query=lovQuery+ " WHERE  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
								countQuery=lovCountQry+ " WHERE  "+CommonFunction.checkNull(rs.getString("LOV_VALUE"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lovDesc.toUpperCase()).trim())+"%'";
							}
						// Changes by Amit Ends
						}
						
						if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase(""))
						{
							if(strParentOption != null && strParentOption.trim().length() !=0){
								query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0]).trim())+"'";
								countQuery=countQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0]).trim())+"'";
							}
						}
					}
					else if(!CommonFunction.checkNull(nextField).equalsIgnoreCase(""))
					{
						if(whereIndexNo > 0)
						{
						// Changes by Amit Starts
							if(indexDateCol3>=0){
								query=lovQuery+ " and "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"','"+dateFormat+"')";
								countQuery=lovCountQry+" and "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"','"+dateFormat+"')";
							}else	{
								query=lovQuery+ " and "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"%'";
								countQuery=lovCountQry+ " and "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"%'";
							}
						// Changes by Amit Ends
						}
						else
						{
						// Changes by Amit Starts
							if(indexDateCol3>=0){
								query=lovQuery+ " WHERE "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"','"+dateFormat+"')";
								countQuery=lovCountQry+ " WHERE "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" = str_to_date('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField.trim()).trim())+"','"+dateFormat+"')";
							}else	{
								query=lovQuery+ " WHERE "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField).trim())+"%'";
								countQuery=lovCountQry+ " WHERE "+CommonFunction.checkNull(rs.getString("LOV_KEY"))+" like '%"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(nextField).trim())+"%'";
							}
						// Changes by Amit Ends
						}
						
						if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase(""))
						{
							if(strParentOption != null && strParentOption.trim().length() !=0){
								query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0].trim()))+"'";
								countQuery=countQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0].trim()))+"'";
							}
						}
					}
					else
					{
						
						if(!CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME")).equalsIgnoreCase(""))
						{
							query=lovQuery;
							if(strParentOption != null && strParentOption.trim().length() !=0){
								query=query+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0]).trim())+"'";
								countQuery=countQuery+" "+CommonFunction.checkNull(rs.getString("LOV_OPERATOR"))+" "+CommonFunction.checkNull(rs.getString("LOV_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[0]).trim())+"'";
							}
						}
						else
						{
							query=lovQuery;
							countQuery=lovCountQry;
						}
					}
					
					if((!CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME")).equalsIgnoreCase("")))
					{
						query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1]).trim())+"'"; 
						countQuery=countQuery+" AND "+CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1]).trim())+"'";
					}
					if((!CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME")).equalsIgnoreCase("") && !CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME")).equalsIgnoreCase("")))
					{
						query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2]).trim())+"'";  
						countQuery=countQuery+" AND "+CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2]).trim())+"'";
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
				
					/*String tempQuery="";
					int distintExist =  query.toUpperCase().indexOf("DISTINCT");
					if(distintExist>0)
					{
						int commaIndex = query.indexOf(",");
						String str = query.toUpperCase().substring(distintExist+8,query.indexOf(","));
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
					 
					 */
					//count = Integer.parseInt(ConnectionDAO.singleReturn(tempQuery));
					logger.info("Final countQuery:   "+countQuery);
					if(countQuery.contains("SELECT"))
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
			
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			}
								
			rs1.close();
				//query = query + ")AS REQUIREDTABLE";
				logger.info("record id query ......................."+query);
				stmt2 = con.createStatement();
				rs1 = stmt2.executeQuery(query);
				
			    while(rs1.next())
				{
					PopupVo voObject = new PopupVo();
					
					if(numberOfColumns==2)
				    {
						voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
						
						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
						
				    }
				    else if(numberOfColumns==3)
				    {
				    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
						
						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
						voObject.setValOfotherComponent1(StringEscapeUtils.escapeJavaScript(CommonFunction.checkNull(rs1.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
				    }	
				    else if(numberOfColumns==4)
				    {
				    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
						voObject.setValOfotherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
						voObject.setValOfotherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+""+"\" />");
						
				    }	
				    else if(numberOfColumns==5)
				    {
				    	voObject.setRadiobutton("<input type='radio' name='selectRadioBtn' id='selectRadioBtn' onclick=\"return saveRecord('"+method+"');\" value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+""+"\" />");
						voObject.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+" "+"<input type='hidden' name='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' id='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(1)))+"' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(2)))+""+"\" />");
						voObject.setValOfotherComponent1(CommonFunction.checkNull(rs1.getString(3))+" "+"<input type='hidden' name='otherComponent1' id='otherComponent1' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(3)))+""+"\" />");
						voObject.setValOfotherComponent2(CommonFunction.checkNull(rs1.getString(4))+" "+"<input type='hidden' name='otherComponent2' id='otherComponent2' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(4)))+""+"\" />");
						voObject.setValOfotherComponent3(CommonFunction.checkNull(rs1.getString(5))+" "+"<input type='hidden' name='otherComponent3' id='otherComponent3' value="+"\""+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs1.getString(5)))+""+"\" />");
						
				    }	
					
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
			    else if(numberOfColumns==4)
			    {
			    	
					tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
					tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
					tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE2"))));
					tableVoObj.setNoOfColumn("4");
			    }	
			    else if(numberOfColumns==5)
			    {
			    	
					tableVoObj.setLovValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_VALUE_TITLE"))));
					//logger.info("title 1 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE1")));
					//logger.info("title 2 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE2")));
					//logger.info("title 3 ................................ "+CommonFunction.checkNull(rs.getString("LOV_TITLE3")));
					tableVoObj.setOtherComponent1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE1"))));
					tableVoObj.setOtherComponent2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE2"))));
					tableVoObj.setOtherComponent3(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(rs.getString("LOV_TITLE3"))));
					tableVoObj.setNoOfColumn("5");
			    }	
					
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
		
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(0);
		result.add(list);
		
		return result;
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
						logger.info("query 1*********************** "+query);
						
						
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
						
						logger.info("query 2 : " + query);
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
						
						logger.info("query 3*********************** "+query);
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
						logger.info("query 4:"+query);
					}
				
				}
			//	query = query + ")AS REQUIREDTABLE";
				logger.info("query 5 :"+query);
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
					
//					if((CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME"))!=null && !CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME")).equalsIgnoreCase("")))
//					{
//						query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_SEC_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[1]).trim())+"'"; 
//					
//					}
//					if((CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME"))!=null && !CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME")).equalsIgnoreCase("")))
//					{
//						query=query+" AND "+CommonFunction.checkNull(rs.getString("LOV_THIRD_PRNT_COL_NAME"))+"='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(strParentOptionArr[2]).trim())+"'";  
//						
//					}
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
			
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			}
					
			rs1.close();
				//query = query + ")AS REQUIREDTABLE ";
				logger.info("LOV query ..........Test 3 : ............."+query);
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

