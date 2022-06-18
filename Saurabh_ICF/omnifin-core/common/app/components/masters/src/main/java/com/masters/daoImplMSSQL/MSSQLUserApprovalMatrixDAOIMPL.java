package com.masters.daoImplMSSQL;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import com.masters.dao.UserApprovalMatrixDAO;
import com.masters.vo.UserApprovalMatrixVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import java.text.DecimalFormat;

public class MSSQLUserApprovalMatrixDAOIMPL implements UserApprovalMatrixDAO{
	
	static final Logger logger = Logger.getLogger(MSSQLUserApprovalMatrixDAOIMPL.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
    java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public boolean saveUserApprovalMatrix(UserApprovalMatrixVo fieldVo,String flag)
	{		
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject=null;
		try {
				insertPrepStmtObject = new PrepStmtObject();			
				logger.info("In saveUserApprovalMatrix");
				
				StringBuffer bufInsSql = new StringBuffer();
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
				{
					bufInsSql.append(" insert into CR_USER_APPROVAL_TEMP(USER_ID,USER_ROLE,SUB_RULE_TYPE,LEVEL,AMOUNT_FROM,AMOUNT_TO,REC_STATUS," +
			         "MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,MAKER_AUTHOR_STATUS) values(");				
					bufInsSql.append(" ?,");//USER_ID
					bufInsSql.append(" ?,");//USER_ROLE
					bufInsSql.append(" ?,");//SUB_RULE_TYPE
					bufInsSql.append(" ?,");//LEVEL
					bufInsSql.append(" ?,");//AMOUNT_FROM
					bufInsSql.append(" ?,");//AMOUNT_TO  
					bufInsSql.append(" ?,");//REC_STATUS
					bufInsSql.append(" ?,");//MAKER_ID
					bufInsSql.append(" ?,");//MAKER_DATE
					bufInsSql.append(" ?,");//AUTHOR_ID 
					bufInsSql.append(" ?,");//AUTHOR_DATE 
					bufInsSql.append(" ?)");//MAKER_AUTHOR_STATUS
				}
				else
				{
					bufInsSql.append(" insert into cr_user_approval_m(USER_ID,USER_ROLE,SUB_RULE_TYPE,LEVEL,AMOUNT_FROM,AMOUNT_TO,REC_STATUS," +
			         "MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE) values(");				
					bufInsSql.append(" ?,");//USER_ID
					bufInsSql.append(" ?,");//USER_ROLE
					bufInsSql.append(" ?,");//SUB_RULE_TYPE
					bufInsSql.append(" ?,");//LEVEL
					bufInsSql.append(" ?,");//AMOUNT_FROM
					bufInsSql.append(" ?,");//AMOUNT_TO  
					bufInsSql.append(" ?,");//REC_STATUS
					bufInsSql.append(" ?,");//MAKER_ID
					bufInsSql.append(" ?,");//MAKER_DATE
					bufInsSql.append(" ?,");//AUTHOR_ID 
					bufInsSql.append(" ?)");//AUTHOR_DATE
				}
				

				if (CommonFunction.checkNull(fieldVo.getLbxUserId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getLbxUserId().trim());
				
				System.out.println("fieldVo.getRole()  :  "+fieldVo.getRole());
				if (CommonFunction.checkNull(fieldVo.getRole()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getRole().trim());
				
				System.out.println("fieldVo.getSubRuleType()  :  "+fieldVo.getSubRuleType());
				if(fieldVo.getRole().equals("P"))
				{
					if (CommonFunction.checkNull(fieldVo.getSubRuleType()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getSubRuleType().trim());
										
				}else
					insertPrepStmtObject.addNull();	
								
				System.out.println("fieldVo.getLevel()  :  "+fieldVo.getLevel());
				if (CommonFunction.checkNull(fieldVo.getLevel()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getLevel().trim());
				
				System.out.println("fieldVo.getAmountFrom()  :  "+fieldVo.getAmountFrom());
				if(fieldVo.getRole().equals("U"))
				{
					if (CommonFunction.checkNull(fieldVo.getAmountFrom()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getAmountFrom().trim());
				}else
					insertPrepStmtObject.addNull();	
				
				System.out.println("fieldVo.getAmountTo()  :  "+fieldVo.getAmountTo());
				if(fieldVo.getRole().equals("U"))
				{
					if (CommonFunction.checkNull(fieldVo.getAmountTo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getAmountTo().trim());
				}else
					insertPrepStmtObject.addNull();	
				
				System.out.println("fieldVo.getRecStatus()  :  "+fieldVo.getRecStatus());
				if (CommonFunction.checkNull(fieldVo.getRecStatus()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getRecStatus().trim());
				
				System.out.println("fieldVo.getMakerId()  :  "+fieldVo.getMakerId());
				if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerId().trim());
											
				System.out.println("fieldVo.getMakerDate()  :  "+fieldVo.getMakerDate());
				if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());
				
				System.out.println("fieldVo.getAuthorId() :  "+fieldVo.getAuthorId());
				if (CommonFunction.checkNull(fieldVo.getAuthorId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getAuthorId().trim());
				
				System.out.println("fieldVo.getAuthorDate()  :  "+fieldVo.getAuthorDate());
				if (CommonFunction.checkNull(fieldVo.getAuthorDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getAuthorDate().trim());
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
					insertPrepStmtObject.addString("P");
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("print query....."+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);				
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	public ArrayList<UserApprovalMatrixVo> getApprovedUser(int currentPageLink,String flag) 
	{
		logger.info("In getApprovedUser() bbb---->"+flag);
		DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
		ArrayList list=new ArrayList();
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			
				
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
			{
				bufInsSql.append(" select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE ");
				bufInsSql.append(" from CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where a.MAKER_AUTHOR_STATUS='P'");
				bufInsSqlTempCount.append(" select count(1) from (select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE from CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where a.MAKER_AUTHOR_STATUS='P') as c");
			}
			else
			{
				bufInsSql.append(" select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE ");
				bufInsSql.append(" from cr_user_approval_m a left outer join sec_user_m b on(b.USER_ID=a.USER_ID)");
				bufInsSqlTempCount.append(" select count(1) from (select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE from cr_user_approval_m a left outer join sec_user_m b on(b.USER_ID=a.USER_ID)) as c");
			}
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			logger.info("current PAge Link no .................... "+currentPageLink);
			if(currentPageLink>1)
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				bufInsSql.append(" ORDER BY a.USER_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search getApprovedUser query for SQL SERVER : " + bufInsSql.toString());

				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
				

			logger.info("query : "+bufInsSql);
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		   
		   		
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				 
				if(header1!=null && header1.size()>0)
				{
					
					UserApprovalMatrixVo fieldVo = new UserApprovalMatrixVo();
					fieldVo.setCheckBoxDis("<input type= checkbox name=checkId id=checkId  />");
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					
					String role=(CommonFunction.checkNull(header1.get(2))).trim();
					if(role.equals("P"))
						role="Policy Approval";
					if(role.equals("U"))
						role="Under Writer";
					fieldVo.setUserId("<a href=userApprovalMatrixAction.do?method=updateUserApprovedData&flag="+(CommonFunction.checkNull(flag)).trim()+"&userId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&userRole="+role+">"+(CommonFunction.checkNull(header1.get(1))).trim()+"</a>");
					fieldVo.setRole(role);
					fieldVo.setLevel((CommonFunction.checkNull(header1.get(3))).trim());
					if(!(CommonFunction.checkNull(header1.get(4))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
						fieldVo.setAmountFrom(myFormatter.format(reconNum));
					}
					if(!(CommonFunction.checkNull(header1.get(5))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
						fieldVo.setAmountTo(myFormatter.format(reconNum));
					}
					String rec=(CommonFunction.checkNull(header1.get(6))).trim();
					if(rec.equals("A"))
						rec="Active";
					else
						rec="Inactive";
					fieldVo.setRecStatus(rec);
					fieldVo.setMakerId((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setMakerDate((CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setAuthorId((CommonFunction.checkNull(header1.get(9))).trim());
					fieldVo.setAuthorDate((CommonFunction.checkNull(header1.get(10))).trim());
					fieldVo.setSubRuleType((CommonFunction.checkNull(header1.get(11))).trim());
					fieldVo.setTotalRecordSize(count);
					
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return list;
	}
	
	public ArrayList getDetail(String userId, String userRole,String flag) 
	{
		
		ArrayList list=new ArrayList();
		String urole=userRole.charAt(0)+"";
		String query;
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
		{
			query =" select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,"+
            " a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE from "+
            " CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) "+
            " where a.USER_ID='"+userId+"' and  USER_ROLE='"+urole+"' ";
		}
		else
		{
			query =" select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,"+
            " a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE from "+
            " cr_user_approval_m a left outer join sec_user_m b on(a.USER_ID=b.USER_ID) "+
            " where a.USER_ID='"+userId+"' and  USER_ROLE='"+urole+"' ";
		}
		
		logger.info("query : "+query);		
		UserApprovalMatrixVo fieldVo = null;
		try
		{
			ArrayList header = ConnectionDAOforEJB.sqlSelect(query);
			for(int i=0;i<header.size();i++)
			{	ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fieldVo = new UserApprovalMatrixVo();					
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setUserId((CommonFunction.checkNull(header1.get(1))).trim());
					String role=(CommonFunction.checkNull(header1.get(2))).trim();
					String roleTemp=role;
					if(role.equals("P"))
						role="Policy Approval";
					if(role.equals("U"))
						role="Under Writer";
					fieldVo.setRole(role);
					fieldVo.setLevel((CommonFunction.checkNull(header1.get(3))).trim());
					if(!(CommonFunction.checkNull(header1.get(4))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
						fieldVo.setAmountFrom(myFormatter.format(reconNum));
					}
					if(!(CommonFunction.checkNull(header1.get(5))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
						fieldVo.setAmountTo(myFormatter.format(reconNum));
					}
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setMakerId((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setMakerDate((CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setAuthorId((CommonFunction.checkNull(header1.get(9))).trim());
					fieldVo.setAuthorDate((CommonFunction.checkNull(header1.get(10))).trim());
					if(roleTemp.equals("P"))
						fieldVo.setSubRuleTypeP((CommonFunction.checkNull(header1.get(11))).trim());
					if(roleTemp.equals("U"))
						fieldVo.setSubRuleType((CommonFunction.checkNull(header1.get(11))).trim());
					list.add(fieldVo);				
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean updateUserApprovedRecords(UserApprovalMatrixVo fieldVo,String flag) 
	{
		logger.info("In updateUserApprovedRecords");	
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject=null;
		String userId=fieldVo.getLbxUserId().trim();
		String role=fieldVo.getRole().trim();
		try 
		{
				insertPrepStmtObject = new PrepStmtObject();						
				StringBuffer bufInsSql = new StringBuffer();
				if(CommonFunction.checkNull(flag).equalsIgnoreCase("N"))
				{
					boolean st = ConnectionDAOforEJB.checkStatus("select count(USER_ID) from CR_USER_APPROVAL_TEMP where USER_ID='"+userId+"' and USER_ROLE='"+role+"'");
					logger.info("status before insert in temp table : " + st);
					if(st)
					{
						ArrayList qryList1 = new ArrayList();
						PrepStmtObject insertPrepStmtObject1=null;
						try {
								insertPrepStmtObject1 = new PrepStmtObject();			
								logger.info("In saveUserApprovalMatrix");
								
								StringBuffer bufInsSql1 = new StringBuffer();
								
								bufInsSql1.append(" insert into CR_USER_APPROVAL_TEMP(USER_ID,USER_ROLE,SUB_RULE_TYPE,LEVEL,AMOUNT_FROM,AMOUNT_TO,REC_STATUS," +
						         "MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,MAKER_AUTHOR_STATUS) values(");				
								bufInsSql1.append(" ?,");//USER_ID
								bufInsSql1.append(" ?,");//USER_ROLE
								bufInsSql1.append(" ?,");//SUB_RULE_TYPE
								bufInsSql1.append(" ?,");//LEVEL
								bufInsSql1.append(" ?,");//AMOUNT_FROM
								bufInsSql1.append(" ?,");//AMOUNT_TO  
								bufInsSql1.append(" ?,");//REC_STATUS
								bufInsSql1.append(" ?,");//MAKER_ID
								bufInsSql1.append(" ?,");//MAKER_DATE
								bufInsSql1.append(" ?,");//AUTHOR_ID 
								bufInsSql1.append(" ?,");//AUTHOR_DATE 
								bufInsSql1.append(" ?)");//MAKER_AUTHOR_STATUS
							
								if (CommonFunction.checkNull(fieldVo.getLbxUserId()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getLbxUserId().trim());
								
								if (CommonFunction.checkNull(fieldVo.getRole()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getRole().trim());
								
								if(fieldVo.getRole().equals("P"))
								{
									if (CommonFunction.checkNull(fieldVo.getSubRuleTypeP()).equalsIgnoreCase(""))
										insertPrepStmtObject1.addNull();
									else
										insertPrepStmtObject1.addString(fieldVo.getSubRuleTypeP().trim());
														
								}else
									insertPrepStmtObject1.addNull();	
												
								if (CommonFunction.checkNull(fieldVo.getLevel()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getLevel().trim());
							
								if(fieldVo.getRole().equals("U"))
								{
									if (CommonFunction.checkNull(fieldVo.getAmountFrom()).equalsIgnoreCase(""))
										insertPrepStmtObject1.addNull();
									else
										insertPrepStmtObject1.addString(fieldVo.getAmountFrom().trim());
								}else
									insertPrepStmtObject1.addNull();	
								
								if(fieldVo.getRole().equals("U"))
								{
									if (CommonFunction.checkNull(fieldVo.getAmountTo()).equalsIgnoreCase(""))
										insertPrepStmtObject1.addNull();
									else
										insertPrepStmtObject1.addString(fieldVo.getAmountTo().trim());
								}else
									insertPrepStmtObject1.addNull();	
							
								if (CommonFunction.checkNull(fieldVo.getRecStatus()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getRecStatus().trim());
						
								if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getMakerId().trim());
															
								if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getMakerDate().trim());
								
								if (CommonFunction.checkNull(fieldVo.getAuthorId()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getAuthorId().trim());
								
								if (CommonFunction.checkNull(fieldVo.getAuthorDate()).equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(fieldVo.getAuthorDate().trim());
								insertPrepStmtObject1.addString("P");
								
								insertPrepStmtObject1.setSql(bufInsSql1.toString());
								logger.info("print query....in updateInsert."+insertPrepStmtObject1.printQuery());
								qryList1.add(insertPrepStmtObject1);
								status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList1);				
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else
				{
					if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
					{
						bufInsSql.append(" update CR_USER_APPROVAL_TEMP set SUB_RULE_TYPE=?, LEVEL=?,AMOUNT_FROM=?,AMOUNT_TO=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=?,AUTHOR_ID=?,AUTHOR_DATE=? ");
						bufInsSql.append(" where USER_ID='"+userId+"' and USER_ROLE='"+role+"'");
					}
					else
					{
						bufInsSql.append(" update cr_user_approval_m set SUB_RULE_TYPE=?, LEVEL=?,AMOUNT_FROM=?,AMOUNT_TO=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=?,AUTHOR_ID=?,AUTHOR_DATE=? ");
						bufInsSql.append(" where USER_ID='"+userId+"' and USER_ROLE='"+role+"'");
					}	
					if(role.equals("P"))
					{
						if (CommonFunction.checkNull(fieldVo.getSubRuleTypeP()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(fieldVo.getSubRuleTypeP().trim());
					}
					else
						insertPrepStmtObject.addNull();
					
					System.out.println("\n\nfieldVo.getLevel()  :  "+fieldVo.getLevel());
					if (CommonFunction.checkNull(fieldVo.getLevel()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getLevel().trim());
					
					if(role.equals("U"))
					{
						if (CommonFunction.checkNull(fieldVo.getAmountFrom()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(fieldVo.getAmountFrom().trim());
					}
					else
						insertPrepStmtObject.addNull();
					System.out.println("fieldVo.getAmountTo()  :  "+fieldVo.getAmountTo());
					if(role.equals("U"))
					{
						if (CommonFunction.checkNull(fieldVo.getAmountTo()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(fieldVo.getAmountTo().trim());
					}
					else
						insertPrepStmtObject.addNull();
					
					System.out.println("fieldVo.getRecStatus()  :  "+fieldVo.getRecStatus());
					if (CommonFunction.checkNull(fieldVo.getRecStatus()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getRecStatus().trim());
					
					System.out.println("fieldVo.getMakerId()  :  "+fieldVo.getMakerId());
					if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getMakerId().trim());
												
					System.out.println("fieldVo.getMakerDate()  :  "+fieldVo.getMakerDate());
					if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());
					
					System.out.println("fieldVo.getAuthorId() :  "+fieldVo.getAuthorId());
					if (CommonFunction.checkNull(fieldVo.getAuthorId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getAuthorId().trim());
					
					System.out.println("fieldVo.getAuthorDate()  :  "+fieldVo.getAuthorDate());
					if (CommonFunction.checkNull(fieldVo.getAuthorDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getAuthorDate().trim());				
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN updateUserApprovedRecords() update query ### "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);	
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				}
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		return status;
	}
	public ArrayList<UserApprovalMatrixVo> getsearchApprovedUser(UserApprovalMatrixVo vo, String flag, String recStatus,String makerAuthorFlag) 
	{
		logger.info("In getsearchApprovedUser"+flag);
		logger.info("makerAuthorFlag::: "+makerAuthorFlag);
		DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
		ArrayList list=new ArrayList();
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			String userId=vo.getLbxUserId().trim();
			String role=vo.getRole();
				
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
			{
				bufInsSql.append("select distinct a.USER_ID,b.USER_NAME,a.USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE ");
				bufInsSql.append(" from CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where MAKER_AUTHOR_STATUS='P' ");
				bufInsSqlTempCount.append(" select count(1) from (select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE from CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where MAKER_AUTHOR_STATUS='P' ");
			}
			else
			{
				if(CommonFunction.checkNull(makerAuthorFlag).equalsIgnoreCase("Y"))
				{
					bufInsSql.append("select distinct a.USER_ID,b.USER_NAME,a.USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE ");
					bufInsSql.append(" from cr_user_approval_m a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where a.USER_ID not in (select USER_ID from CR_USER_APPROVAL_TEMP c where c.user_role=a.USER_ROLE)");
					bufInsSqlTempCount.append(" select count(1) from (select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE from cr_user_approval_m a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where a.USER_ID not in (select USER_ID from CR_USER_APPROVAL_TEMP c where c.user_role=a.USER_ROLE) ");
				}
				else
				{
					bufInsSql.append("select distinct a.USER_ID,b.USER_NAME,a.USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE ");
					bufInsSql.append(" from cr_user_approval_m a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where 'a'='a' ");
					bufInsSqlTempCount.append(" select count(1) from (select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE from cr_user_approval_m a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where 'a'='a' ");
				}
			}
			
			if(userId.length()!=0)
			{
				bufInsSql.append(" and a.USER_ID='"+userId+"'");
				bufInsSqlTempCount.append(" and a.USER_ID='"+userId+"'");
			}
			if(role.length()!=0)
			{
				bufInsSql.append(" and USER_ROLE='"+role+"'");
				bufInsSqlTempCount.append(" and USER_ROLE='"+role+"'");
			}
			bufInsSqlTempCount.append(" ) as c");
			logger.info("query : "+bufInsSql);
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				
			}
			
			bufInsSql.append(" ORDER BY a.USER_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search getsearchApprovedUser query for SQL SERVER : " + bufInsSql.toString());

			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		//for mssql server
			//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());						
			for(int i=0;i<header.size();i++)
			{
			
				ArrayList header1=(ArrayList)header.get(i);
				logger.info("header: "+header1.size());
				logger.info("ROLE: "+header1.get(2));
				if(header1!=null && header1.size()>0)
				{
					
					UserApprovalMatrixVo fieldVo = new UserApprovalMatrixVo();
					fieldVo.setCheckBoxDis("<input type= checkbox name=checkId id=checkId  />");
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					
					String userRole=(CommonFunction.checkNull(header1.get(2))).trim();
					if(userRole.equals("P"))
						userRole="Policy Approval";
					if(userRole.equals("U"))
						userRole="Under Writer";
					fieldVo.setUserId("<a href=userApprovalMatrixAction.do?method=updateUserApprovedData&flag="+(CommonFunction.checkNull(flag)).trim()+"&userId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&userRole="+userRole+">"+(CommonFunction.checkNull(header1.get(1))).trim()+"</a>");
					fieldVo.setRole(userRole);
					fieldVo.setLevel((CommonFunction.checkNull(header1.get(3))).trim());
					if(!(CommonFunction.checkNull(header1.get(4))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
						fieldVo.setAmountFrom(myFormatter.format(reconNum));
					}
					if(!(CommonFunction.checkNull(header1.get(5))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
						fieldVo.setAmountTo(myFormatter.format(reconNum));
					}
					String rec=(CommonFunction.checkNull(header1.get(6))).trim();
					if(rec.equals("A"))
						rec="Active";
					else
						rec="Inactive";
					fieldVo.setRecStatus(rec);
					fieldVo.setMakerId((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setMakerDate((CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setAuthorId((CommonFunction.checkNull(header1.get(9))).trim());
					fieldVo.setAuthorDate((CommonFunction.checkNull(header1.get(10))).trim());
					fieldVo.setSubRuleType((CommonFunction.checkNull(header1.get(11))).trim());
					fieldVo.setTotalRecordSize(count);				
					if(CommonFunction.checkNull(flag).equalsIgnoreCase("Y"))
						fieldVo.setStatusCase("UnApproved");
					else
						fieldVo.setStatusCase("Approved");
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return list;
		
	}
	public int checkUserId(String userId,String role) 
	{
		int count=0;
		String query =" select count(USER_ID) from cr_user_approval_m where USER_ID='"+userId.trim()+"' and USER_ROLE='"+role.trim()+"'";
		logger.info("query : "+query);		
		try
		{
			ArrayList header = ConnectionDAOforEJB.sqlSelect(query);
			ArrayList subList=(ArrayList)header.get(0);
			String num=(String)subList.get(0);
			count=Integer.parseInt(num);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	public ArrayList<UserApprovalMatrixVo> getBranches(String userId) 
	{
		ArrayList list=new ArrayList();
		String query =" select sec_user_branch_dtl.USER_ID,sec_user_m.USER_NAME,BRANCH_DESC from sec_user_branch_dtl left outer join "+
						" com_branch_m on(com_branch_m.BRANCH_ID=sec_user_branch_dtl.BRANCH_ID) left outer join "+
						" sec_user_m on(sec_user_m.USER_ID=sec_user_branch_dtl.USER_ID)"+
		              " where sec_user_branch_dtl.USER_ID='"+userId+"' and sec_user_branch_dtl.REC_STATUS='A'";

		logger.info("query : "+query);		
		UserApprovalMatrixVo fieldVo = null;
		try
		{
			ArrayList header = ConnectionDAOforEJB.sqlSelect(query);
			for(int i=0;i<header.size();i++)
			{	ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fieldVo = new UserApprovalMatrixVo();					
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setUserId((CommonFunction.checkNull(header1.get(1))).trim());
					fieldVo.setBranchName((CommonFunction.checkNull(header1.get(2))).trim());
					list.add(fieldVo);				
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<UserApprovalMatrixVo> getProducts(String userId) 
	{
		ArrayList list=new ArrayList();
		String query =" select cr_user_product_mapping_m.USER_ID,sec_user_m.USER_NAME,cr_product_m.PRODUCT_DESC from cr_user_product_mapping_m "+
					  " INNER join sec_user_m on(sec_user_m.USER_ID=cr_user_product_mapping_m.USER_ID) INNER join cr_product_m "+
					  " on(cr_product_m.PRODUCT_ID=cr_user_product_mapping_m.PRODUCT_ID) "+
		              " where cr_user_product_mapping_m.USER_ID='"+userId+"' and cr_user_product_mapping_m.REC_STATUS='A'";

		logger.info("query : "+query);		
		UserApprovalMatrixVo fieldVo = null;
		try
		{
			ArrayList header = ConnectionDAOforEJB.sqlSelect(query);
			for(int i=0;i<header.size();i++)
			{	ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fieldVo = new UserApprovalMatrixVo();					
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setUserId((CommonFunction.checkNull(header1.get(1))).trim());
					fieldVo.setProductName((CommonFunction.checkNull(header1.get(2))).trim());
					list.add(fieldVo);				
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public String makerAuthorFlag()
	{
		String result = ConnectionDAOforEJB.singleReturn("select PARAMETER_VALUE from PARAMETER_MST where PARAMETER_KEY='MASTER_MAKER_AUTHOR'");
		return result;
	}
	public boolean forwardUserApprovedRecords(UserApprovalMatrixVo fieldVo) 
	{
		logger.info("In forwardUserApprovedRecords");	
		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject=null;
		String userId=fieldVo.getLbxUserId().trim();
		String role=fieldVo.getRole().trim();
		try 
		{
				insertPrepStmtObject = new PrepStmtObject();						
				StringBuffer bufInsSql = new StringBuffer();
				
				bufInsSql.append(" update CR_USER_APPROVAL_TEMP set SUB_RULE_TYPE=?, LEVEL=?,AMOUNT_FROM=?,AMOUNT_TO=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=?,AUTHOR_ID=?,AUTHOR_DATE=?,MAKER_AUTHOR_STATUS='F' ");
				bufInsSql.append(" where USER_ID='"+userId+"' and USER_ROLE='"+role+"'");
				
				if(role.equals("P"))
				{
					if (CommonFunction.checkNull(fieldVo.getSubRuleTypeP()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getSubRuleTypeP().trim());
				}
				else
					insertPrepStmtObject.addNull();
				
				System.out.println("\n\nfieldVo.getLevel()  :  "+fieldVo.getLevel());
				if (CommonFunction.checkNull(fieldVo.getLevel()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getLevel().trim());
				
				if(role.equals("U"))
				{
					if (CommonFunction.checkNull(fieldVo.getAmountFrom()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getAmountFrom().trim());
				}
				else
					insertPrepStmtObject.addNull();
				System.out.println("fieldVo.getAmountTo()  :  "+fieldVo.getAmountTo());
				if(role.equals("U"))
				{
					if (CommonFunction.checkNull(fieldVo.getAmountTo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getAmountTo().trim());
				}
				else
					insertPrepStmtObject.addNull();
				
				System.out.println("fieldVo.getRecStatus()  :  "+fieldVo.getRecStatus());
				if (CommonFunction.checkNull(fieldVo.getRecStatus()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getRecStatus().trim());
				
				System.out.println("fieldVo.getMakerId()  :  "+fieldVo.getMakerId());
				if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerId().trim());
											
				System.out.println("fieldVo.getMakerDate()  :  "+fieldVo.getMakerDate());
				if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());
				
				System.out.println("fieldVo.getAuthorId() :  "+fieldVo.getAuthorId());
				if (CommonFunction.checkNull(fieldVo.getAuthorId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getAuthorId().trim());
				
				System.out.println("fieldVo.getAuthorDate()  :  "+fieldVo.getAuthorDate());
				if (CommonFunction.checkNull(fieldVo.getAuthorDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getAuthorDate().trim());				
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN updateUserApprovedRecords() update query ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);	
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		return status;
	}
	public ArrayList<UserApprovalMatrixVo> getAuthorSearchUser(int currentPageLink,String userId,String userRole) 
	{
		logger.info("In getAuthorSearchUser---->");
		DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
		ArrayList list=new ArrayList();
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			
				
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append(" select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE ");
			bufInsSql.append(" from CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where a.MAKER_AUTHOR_STATUS='F'");
			bufInsSqlTempCount.append(" select count(1) from (select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE from CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) where a.MAKER_AUTHOR_STATUS='F'");
			if(!(CommonFunction.checkNull(userId).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and a.USER_ID='"+userId+"'");
				bufInsSqlTempCount.append(" and a.USER_ID='"+userId+"'");
			}
			if(!(CommonFunction.checkNull(userRole).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and a.USER_ROLE='"+userRole+"'");
				bufInsSqlTempCount.append(" and a.USER_ROLE='"+userRole+"'");
			}
			bufInsSqlTempCount.append(") as c");
								
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			logger.info("current PAge Link no .................... "+currentPageLink);
			if(currentPageLink>1)
			{
				startRecordIndex = (currentPageLink-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				bufInsSql.append(" ORDER BY a.USER_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search getApprovedUser query for SQL SERVER : " + bufInsSql.toString());

				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
				

			logger.info("query : "+bufInsSql);
		    header = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		   
		   		
			for(int i=0;i<header.size();i++)
			{
				ArrayList header1=(ArrayList)header.get(i);
				 
				if(header1!=null && header1.size()>0)
				{
					
					UserApprovalMatrixVo fieldVo = new UserApprovalMatrixVo();
					fieldVo.setCheckBoxDis("<input type= checkbox name=checkId id=checkId  />");
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					
					String role=(CommonFunction.checkNull(header1.get(2))).trim();
					if(role.equals("P"))
						role="Policy Approval";
					if(role.equals("U"))
						role="Under Writer";
					fieldVo.setUserId("<a href=userApprovalMatrixAction.do?method=getUserApproalAuthor&userId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&userRole="+role+">"+(CommonFunction.checkNull(header1.get(1))).trim()+"</a>");
					fieldVo.setRole(role);
					fieldVo.setLevel((CommonFunction.checkNull(header1.get(3))).trim());
					if(!(CommonFunction.checkNull(header1.get(4))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
						fieldVo.setAmountFrom(myFormatter.format(reconNum));
					}
					if(!(CommonFunction.checkNull(header1.get(5))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
						fieldVo.setAmountTo(myFormatter.format(reconNum));
					}
					String rec=(CommonFunction.checkNull(header1.get(6))).trim();
					if(rec.equals("A"))
						rec="Active";
					else
						rec="Inactive";
					fieldVo.setRecStatus(rec);
					fieldVo.setMakerId((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setMakerDate((CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setAuthorId((CommonFunction.checkNull(header1.get(9))).trim());
					fieldVo.setAuthorDate((CommonFunction.checkNull(header1.get(10))).trim());
					fieldVo.setSubRuleType((CommonFunction.checkNull(header1.get(11))).trim());
					fieldVo.setTotalRecordSize(count);
					
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return list;
	}
	public ArrayList getAuthorDetail(String userId, String userRole) 
	{
		
		ArrayList list=new ArrayList();
		String urole=userRole.charAt(0)+"";
		String query =" select distinct a.USER_ID,b.USER_NAME,USER_ROLE,LEVEL,AMOUNT_FROM,AMOUNT_TO,a.REC_STATUS,"+
            " a.MAKER_ID,a.MAKER_DATE,a.AUTHOR_ID,a.AUTHOR_DATE,SUB_RULE_TYPE from "+
            " CR_USER_APPROVAL_TEMP a left outer join sec_user_m b on(b.USER_ID=a.USER_ID) "+
            " where a.USER_ID='"+userId+"' and  USER_ROLE='"+urole+"'";
						
		logger.info("query : "+query);		
		UserApprovalMatrixVo fieldVo = null;
		try
		{
			ArrayList header = ConnectionDAOforEJB.sqlSelect(query);
			for(int i=0;i<header.size();i++)
			{	ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fieldVo = new UserApprovalMatrixVo();					
					fieldVo.setLbxUserId((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setUserId((CommonFunction.checkNull(header1.get(1))).trim());
					String role=(CommonFunction.checkNull(header1.get(2))).trim();
					/*if(role.equals("P"))
						role="Policy Approval";
					if(role.equals("U"))
						role="Under Writer";*/
					fieldVo.setRole((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setLevel((CommonFunction.checkNull(header1.get(3))).trim());
					if(!(CommonFunction.checkNull(header1.get(4))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
						fieldVo.setAmountFrom(myFormatter.format(reconNum));
					}
					if(!(CommonFunction.checkNull(header1.get(5))).trim().equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(5))).trim());
						fieldVo.setAmountTo(myFormatter.format(reconNum));
					}
					fieldVo.setRecStatus((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setMakerId((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setMakerDate((CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setAuthorId((CommonFunction.checkNull(header1.get(9))).trim());
					fieldVo.setAuthorDate((CommonFunction.checkNull(header1.get(10))).trim());
					if(role.equals("P"))
						fieldVo.setSubRuleTypeP((CommonFunction.checkNull(header1.get(11))).trim());
					if(role.equals("U"))
						fieldVo.setSubRuleType((CommonFunction.checkNull(header1.get(11))).trim());
					list.add(fieldVo);				
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public String saveUserApprovalAuthor(Object ob,String userId,String role) {
		
		UserApprovalMatrixVo vo = (UserApprovalMatrixVo) ob;
		String status = "";
		logger.info("In saveUserApprovalAuthor.....................................Dao Impl ");
		
		String decision="";
		String s1= "";
		String s2 = "";

		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		decision=CommonFunction.checkNull(vo.getDecison());
		try {

				logger.info("In saveUserApprovalAuthor......decision , userId, role : " + decision + userId + role);
				logger.info("Author date : " + vo.getAuthorDate());
				
				StringBuffer bufInsSql = new StringBuffer();
				
					logger.info("In PROCEDURE..#####.. USER_APPROVAL_AUTHOR..#####.....");
					try {
						  //in.add(vo.getDealId());
						  in.add(CommonFunction.checkNull (userId).trim()); //@I_USER_ID
						  in.add(CommonFunction.checkNull (role).trim());//@I_USER_ROLE
						  in.add(CommonFunction.checkNull (decision).trim());//@I_DECESION
						  in.add(CommonFunction.checkNull (vo.getTextArea()).trim());//@I_REMARKS
						  in.add(CommonFunction.checkNull (vo.getAuthorId()).trim());//@I_AUTHOR_ID
						  String date=CommonFunction.changeFormat(CommonFunction.checkNull (vo.getAuthorDate()).trim());
						  logger.info("Author date 1 : " + date);
						  if(date != null)
			        	    	in.add(date);//@I_CURR_DATE
						  			        	    
			        	   out.add(s1);
			        	   out.add(s2);
			        	    
						 outMessages=(ArrayList) ConnectionDAOforEJB.callSP("USER_APPROVAL_AUTHOR",in,out);
						    s1=CommonFunction.checkNull(outMessages.get(0));
			        	    s2=CommonFunction.checkNull(outMessages.get(1));
			        	  

		        	    if(!(CommonFunction.checkNull(s2).equalsIgnoreCase("")))
							status=s2;	
						else
							status=s1;
//						
						
						logger.info("s1-----------------1: "+s1);
						logger.info("s2-----------------1: "+s2);
					} catch (Exception e) {
						e.printStackTrace();
					}	

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("In saveUserApprovalAuthor................. status:----"+status);
		
	
		return status;

	}
	
}
