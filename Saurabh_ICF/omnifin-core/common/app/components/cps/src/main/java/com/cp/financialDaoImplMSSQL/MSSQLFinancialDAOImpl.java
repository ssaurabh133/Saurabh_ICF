package com.cp.financialDaoImplMSSQL;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.actionform.ReportsForm;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.financialDao.FinancialDAO;
import com.cp.vo.BankAccountAnalysisVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.FinancialAnalysisParamVo;
import com.cp.vo.FinancialAnalysisVo;
import com.logger.LoggerMsg;

public class MSSQLFinancialDAOImpl implements FinancialDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLFinancialDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
	//change by sachin
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	//end by sachin
	@SuppressWarnings("null")
	public String saveBalanceSheet(Object ob)
	{
		logger.info("In saveBalanceSheet DaoImpl.............................. ");
		FinancialAnalysisVo vo=(FinancialAnalysisVo)ob;
		boolean status=false;
		boolean flag=false;
		String maxId ="";
		String ids="";
		String idsinsert="";
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		    String pCode[] = vo.getpCode();
		    String year1[] = vo.getYear1();
		    String year2[] = vo.getYear2();
		    String year3[] = vo.getYear3();
		    String year4[] = vo.getYear4();
		    String year5[] = vo.getYear5();
		    String analysisYear[]=vo.getAnalysisYear();
		    ArrayList qryList=new ArrayList();
			StringBuffer bufInsSql =	new StringBuffer();		    	
		     String[][] pCodeWithYear=new String [50][50];
		     FinancialAnalysisVo vos;
		     int z=0;
		     ArrayList<FinancialAnalysisVo> finanFormula=new ArrayList<FinancialAnalysisVo>();
		     
		   //sachin work start
				try
				{
				for(int i=0,k=0;i<pCode.length;i++){
					k=0; 
					vos=new FinancialAnalysisVo();
					ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,FINANCIAL_FORMULA FROM cr_financial_param WHERE PARAM_CODE='"+pCode[i]+"'");
						for(int t=0;t<pval.size();t++){
							ArrayList subPval=(ArrayList)pval.get(t);
							if(subPval.size()>0)
							{
								vos.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
								vos.setFinancialFormula((CommonFunction.checkNull(subPval.get(1))).trim());
							    finanFormula.add(vos); 
							}
						}
				}
				
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				//sachin work end
			    //sachin work start
			    if(finanFormula.size()>0)
				 {
//					 ArrayList balanceSheetList = (ArrayList)session.getAttribute("balanceSheetAllDetailsByDeal");
//					 ArrayList othersList = (ArrayList)session.getAttribute("otherAllDetailsByDeal");
//					 ArrayList profitLossList = (ArrayList)session.getAttribute("profitLossAllDetailsByDeal");
					 ArrayList ratioParamValue = new ArrayList();
					
					 ArrayList ratioAnalysisList = new ArrayList();
					 ScriptEngineManager mgr = new ScriptEngineManager();
					 ScriptEngine engine = mgr.getEngineByName("JavaScript");
					 for(int i=0 ; i< finanFormula.size() ; i++)
					 {
						 	   flag =false;
								FinancialAnalysisVo  vo1 = (FinancialAnalysisVo)finanFormula.get(i);
						 	    //List<FinancialAnalysisVo> vo1=finanFormula.subList(0, finanFormula.size())
								System.out.println("..................."+finanFormula.get(i).getFinancialFormula());
								System.out.println("..................."+finanFormula.get(i).getParamName());
								String ratioExprFstYear ="";
								String ratioExprSecYear ="";
								String ratioExprThrdYear ="";
								String ratioExprForthYear ="";
								String ratioExprFifthYear ="";
								if(!vo1.getFinancialFormula().equalsIgnoreCase("")){
								ratioExprFstYear =vo1.getFinancialFormula();
								ratioExprSecYear =vo1.getFinancialFormula();
								ratioExprThrdYear =vo1.getFinancialFormula();
								ratioExprForthYear =vo1.getFinancialFormula();
								ratioExprFifthYear =vo1.getFinancialFormula();
								
								FinancialAnalysisVo ratioVo = new FinancialAnalysisVo();
									if(pCode.length>0)
									{
										for(int j=0;j<pCode.length;j++){
											String paramCode=pCode[j];
											if(ratioExprFstYear.indexOf(paramCode) >= 0)
											{
//												ratioVo.setRatioParamCode(vo.getParameCode());
//												ratioVo.setRatioName(vo.getRatioName());
												try{
												String yearValue1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year1[j]).trim())).toString();
												String yearValue2=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year2[j]).trim())).toString();
												String yearValue3=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year3[j]).trim())).toString();
												String yearValue4=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year4[j]).trim())).toString();
												String yearValue5=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year5[j]).trim())).toString();
												yearValue1="("+yearValue1+")";
												yearValue2="("+yearValue2+")";
												yearValue3="("+yearValue3+")";
												yearValue4="("+yearValue4+")";
												yearValue5="("+yearValue5+")";
												ratioExprFstYear =ratioExprFstYear.replaceAll(paramCode,yearValue1);
												ratioExprSecYear = ratioExprSecYear.replaceAll(paramCode,yearValue2); 
												ratioExprThrdYear = ratioExprThrdYear.replaceAll(paramCode,yearValue3);
												ratioExprForthYear = ratioExprForthYear.replaceAll(paramCode,yearValue4);
												ratioExprFifthYear =ratioExprFifthYear.replaceAll(paramCode,yearValue5);
												flag=true;
												}
												catch (Exception e) {
//													ratioVo.setRatioFirstYear("00");
													e.printStackTrace();
												}
											}	
											
										}
								logger.info("ratioExprFstYear 111111111.............. "+ratioExprFstYear);
								BigDecimal d1 = new BigDecimal("0.00");
//								logger.info("flag &&&&&&&&&&&&&&&&&&&&&&&&& "+flag);
								if(flag)
								{
								
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"").equalsIgnoreCase(""))
								    		{
												logger.info("engine.eval(ratioExprFstYear) ******************************** "+engine.eval(ratioExprFstYear));
												 d1= new BigDecimal(engine.eval(ratioExprFstYear)+"");//) engine.eval(ratioExprFstYear);
												 
												logger.info("eval first year ******************************** "+d1);
												Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"")).trim());  
								    			year1[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioFirstYear("00");
											e.printStackTrace();
										}
										
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"").equalsIgnoreCase(""))
								    		{
											 d1=new BigDecimal(engine.eval(ratioExprSecYear)+"");// (BigDecimal) engine.eval(ratioExprSecYear);
											logger.info("eval second year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"")).trim());  
											year2[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioSecondYear("00");
											e.printStackTrace();
										}
						    			
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"").equalsIgnoreCase(""))
								    		{
											 d1= new BigDecimal(engine.eval(ratioExprThrdYear)+""); //(Double) engine.eval(ratioExprThrdYear);
											logger.info("eval third year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"")).trim());  
											year3[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioThirdYear("0");
											e.printStackTrace();
										}
									
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"").equalsIgnoreCase(""))
								    		{
											 d1= new BigDecimal(engine.eval(ratioExprForthYear)+"");// (Double) engine.eval(ratioExprForthYear);
											logger.info("eval fourth year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"")).trim());  
											year4[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioFourthYear("0");
											e.printStackTrace();
										}
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"").equalsIgnoreCase(""))
								    		{
											 d1=new BigDecimal(engine.eval(ratioExprFifthYear)+"");// (Double) engine.eval(ratioExprFifthYear);
											logger.info("eval fifth year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"")).trim());  
											year5[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
		//									ratioVo.setRatioFifthYear("0");
											e.printStackTrace();
										}
						    			
								}
								z++;
								
								

					 }
								}else{
									year1[z] =year1[z];
								    year2[z] = year2[z];
								    year3[z] =year3[z]; 
								    year4[z] = year4[z];
								    year5[z]=year5[z];
								    z++;
									
								}
					 }
				 }
			    //sachin work end


			    logger.info("pCode.length ........ "+pCode.length );
		     if(pCode.length >0 )
		     {
		    for(int i=0,k=0;i<pCode.length;i++,k++)
		    {
		    	for(int j=0;j<5;j++)
		    	{
		    		
		    		if(j==0)
		    		{
		    			pCodeWithYear[i][j]=year1[k];
		    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==1)
		    		{
		    			pCodeWithYear[i][j]=year2[k];
		    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==2)
		    		{
		    			pCodeWithYear[i][j]=year3[k];
		    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==3)
		    		{
		    			pCodeWithYear[i][j]=year4[k];
		    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==4)
		    		{
		    			pCodeWithYear[i][j]=year5[k];
		    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    	}
		    }
		    int currBusinessDateYear =Integer.parseInt(StringEscapeUtils.escapeSql(vo.getCurrBusinessDateYear()));

		    try{
		    	
		    	
		    
		    if(!CommonFunction.checkNull(vo.getpCode()).equalsIgnoreCase(""))
	         {
		    	maxId = ConnectionDAO.singleReturn("select max(FINANCIAL_DATA_ID) from cr_financial_data_dtl  WITH (ROWLOCK) ");
		    	int count=0;
		    	String paramValue[]=null;
		    	String paramVal="";
		    	for(int n=0;n<pCode.length;n++)
	    		{
	    			String query1=  "SELECT distinct PARAM_CODE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(pCode[n])+"' ";
			    	logger.info("query1--saveBalanceSheet->"+query1);
			    	String paramCode=ConnectionDAO.singleReturn(query1);
			     	logger.info("status1--saveBalanceSheet-->"+paramCode);
			     
			    	if(! CommonFunction.checkNull(paramCode).equals("")){
				     	if(paramVal.equals("")){	
				     		paramVal=paramCode;
				     	}else{
				     		paramVal=paramVal+","+paramCode;
				     	}
				     	count++;
				     	paramValue=paramVal.split(",");
				     	logger.info("paramValue-length------>"+paramValue.length);
				     //	logger.info("paramValue-length------>"+paramValue[n]);
				     	logger.info("count-"+count);
				     	}
	    		}
			    	if(! CommonFunction.checkNull(paramValue).equals("")){
			    		logger.info("paramValue-length------>"+paramValue.length);
		    		  if(paramValue.length>0){
		    				logger.info("paramValue-length------>"+paramValue.length);
				    	for(int m=0;m<count;m++){
				    		 insertPrepStmtObject = new PrepStmtObject();
				    		String query = "DELETE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(paramValue[m])+"' ";
				    		logger.info("query--saveBalanceSheet-->"+query);
				    		insertPrepStmtObject.setSql(query);
				    		qryList.add(insertPrepStmtObject);
				    	}
					
		    		} 
			    	}
			    	for(int k=0;k<pCode.length; k++)
		    		{
	    			for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
	    			{
	    			 insertPrepStmtObject = new PrepStmtObject();
	    			 bufInsSql =	new StringBuffer();
	    			
	    			
	    			bufInsSql.append("insert into cr_financial_data_dtl(DEAL_ID,SOURCE_TYPE,PARAM_CODE,FINANCIAL_YEAR,PARAM_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
	    			bufInsSql.append(" values ( ");
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
					
					
	    			bufInsSql.append("  )" );
	    			
	    		  	logger.info("financial deal id **************** "+vo.getLbxDealNo());
	    		  	logger.info("saveBalanceSheet........bufInsSql"+bufInsSql.toString());
	    		  	
	    			if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
	    			
	    			if((CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getSourceType()).trim());
	    			
	    			if((CommonFunction.checkNull(pCode[k])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((pCode[k]).trim());
	    				
	    			if((CommonFunction.checkNull(analysisYear[pVal+1])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((analysisYear[pVal+1]).trim());
	    			
	    			if((CommonFunction.checkNull(pCodeWithYear[k][pVal])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addString("0.00");
					else
						try {
							insertPrepStmtObject.addString(myFormatter.parse((pCodeWithYear[k][pVal].trim())).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				
	    		
	    			insertPrepStmtObject.addString("P");//,REC_STATUS

	    			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getUserId()).trim());

	    			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getBusinessDate()));
	    			
	    				insertPrepStmtObject.setSql(bufInsSql.toString());
	    				logger.info("IN saveBalanceSheet() insert query ### "+insertPrepStmtObject.printQuery());
	    				
	    				
	    				qryList.add(insertPrepStmtObject);
	    			
	    		 }
	           }
	         }
	         
			//  logger.info("IN saveBalanceSheet() insert query: "+qryList.get(0));
		    
			  
			  //status=false;
		    logger.info("IN saveBalanceSheet()  qryList: "+qryList);
					if(qryList.size()>0){
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					if(status)
					{									
						ids = ConnectionDAO.singleReturn("SELECT STUFF((SELECT ',' + convert(varchar(20),FINANCIAL_DATA_ID) FROM cr_financial_data_dtl where FINANCIAL_DATA_ID>'"+maxId+"' FOR XML PATH ('')), 1, 1, '')" );
					}}
				} catch (Exception e) {
					e.printStackTrace();
				}
			    logger.info("In saveBalanceSheet......................"+status);
		    }
		    
		 
		    	idsinsert=null;
		    	maxId=null;
		 
			return ids;
		
	}
	public String saveBalanceSheetWithMinus(FinancialAnalysisParamVo vo)
	{
	logger.info("In saveBalanceSheetWithMinus DaoImpl.............................. ");

	boolean status=false;
	boolean flag=false;
	String maxId ="";
	String ids="";
	String idsinsert="";
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	  
	    String pCode1[] = vo.getpCode1();
	    String year1[] = vo.getYear();
	   
	    String year2[] = vo.getYear6();
	    String year3[] = vo.getYear7();
	    String year4[] = vo.getYear8();
	    String year5[] = vo.getYear9();
	    String analysisYear[]=vo.getAnalysisYear();
	    ArrayList qryList=new ArrayList();
		StringBuffer bufInsSql =	new StringBuffer();

	   
	    logger.info("pCode1.length ........ "+pCode1.length );	
	   
	     String[][] pCodeWithYear=new String [50][50];
	     FinancialAnalysisVo vos;
	     int z=0;
	     ArrayList<FinancialAnalysisVo> finanFormula=new ArrayList<FinancialAnalysisVo>();
		   //sachin work start
			try
			{
			for(int i=0,k=0;i<pCode1.length;i++){
				k=0; 
				vos=new FinancialAnalysisVo();
				ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,FINANCIAL_FORMULA FROM cr_financial_param WHERE PARAM_CODE='"+pCode1[i]+"'");
					for(int t=0;t<pval.size();t++){
						ArrayList subPval=(ArrayList)pval.get(t);
						if(subPval.size()>0)
						{
							vos.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
							vos.setFinancialFormula((CommonFunction.checkNull(subPval.get(1))).trim());
						    finanFormula.add(vos); 
						}
					}
			}
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			//sachin work end
		    //sachin work start
		    if(finanFormula.size()>0)
			 {
//				 ArrayList balanceSheetList = (ArrayList)session.getAttribute("balanceSheetAllDetailsByDeal");
//				 ArrayList othersList = (ArrayList)session.getAttribute("otherAllDetailsByDeal");
//				 ArrayList profitLossList = (ArrayList)session.getAttribute("profitLossAllDetailsByDeal");
				 ArrayList ratioParamValue = new ArrayList();
				
				 ArrayList ratioAnalysisList = new ArrayList();
				 ScriptEngineManager mgr = new ScriptEngineManager();
				 ScriptEngine engine = mgr.getEngineByName("JavaScript");
				 for(int i=0 ; i< finanFormula.size() ; i++)
				 {
					 	   flag =false;
							FinancialAnalysisVo  vo1 = (FinancialAnalysisVo)finanFormula.get(i);
					 	    //List<FinancialAnalysisVo> vo1=finanFormula.subList(0, finanFormula.size())
							System.out.println("..................."+finanFormula.get(i).getFinancialFormula());
							System.out.println("..................."+finanFormula.get(i).getParamName());
							String ratioExprFstYear ="";
							String ratioExprSecYear ="";
							String ratioExprThrdYear ="";
							String ratioExprForthYear ="";
							String ratioExprFifthYear ="";
							if(!vo1.getFinancialFormula().equalsIgnoreCase("")){
							ratioExprFstYear =vo1.getFinancialFormula();
							ratioExprSecYear =vo1.getFinancialFormula();
							ratioExprThrdYear =vo1.getFinancialFormula();
							ratioExprForthYear =vo1.getFinancialFormula();
							ratioExprFifthYear =vo1.getFinancialFormula();
							
							FinancialAnalysisVo ratioVo = new FinancialAnalysisVo();
								if(pCode1.length>0)
								{
									for(int j=0;j<pCode1.length;j++){
										String paramCode=pCode1[j];
										if(ratioExprFstYear.indexOf(paramCode) >= 0)
										{
//											ratioVo.setRatioParamCode(vo.getParameCode());
//											ratioVo.setRatioName(vo.getRatioName());
											//sachin
											try{
											String yearValue1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year1[j]).trim())).toString();
											String yearValue2=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year2[j]).trim())).toString();
											String yearValue3=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year3[j]).trim())).toString();
											String yearValue4=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year4[j]).trim())).toString();
											String yearValue5=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year5[j]).trim())).toString();
											yearValue1="("+yearValue1+")";
											yearValue2="("+yearValue2+")";
											yearValue3="("+yearValue3+")";
											yearValue4="("+yearValue4+")";
											yearValue5="("+yearValue5+")";
											ratioExprFstYear =ratioExprFstYear.replaceAll(paramCode,yearValue1);
											ratioExprSecYear = ratioExprSecYear.replaceAll(paramCode,yearValue2); 
											ratioExprThrdYear = ratioExprThrdYear.replaceAll(paramCode,yearValue3);
											ratioExprForthYear = ratioExprForthYear.replaceAll(paramCode,yearValue4);
											ratioExprFifthYear =ratioExprFifthYear.replaceAll(paramCode,yearValue5);
											//sachin
											
											flag=true;
											}
											catch (Exception e) {
//												ratioVo.setRatioFirstYear("00");
												e.printStackTrace();
											}
										}	
										
									}
							logger.info("ratioExprFstYear 111111111.............. "+ratioExprFstYear);
							BigDecimal d1 = new BigDecimal("0.00");
//							logger.info("flag &&&&&&&&&&&&&&&&&&&&&&&&& "+flag);
							if(flag)
							{
							
								
									try{
										if(!CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"").equalsIgnoreCase(""))
							    		{
											logger.info("engine.eval(ratioExprFstYear) ******************************** "+engine.eval(ratioExprFstYear));
											 d1= new BigDecimal(engine.eval(ratioExprFstYear)+"");//) engine.eval(ratioExprFstYear);
											 
											logger.info("eval first year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"")).trim());  
							    			year1[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) {
//										ratioVo.setRatioFirstYear("00");
										e.printStackTrace();
									}
									
									try{
										if(!CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"").equalsIgnoreCase(""))
							    		{
										 d1=new BigDecimal(engine.eval(ratioExprSecYear)+"");// (BigDecimal) engine.eval(ratioExprSecYear);
										logger.info("eval second year ******************************** "+d1);
										Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"")).trim());  
										year2[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) {
//										ratioVo.setRatioSecondYear("00");
										e.printStackTrace();
									}
					    			
									try{
										if(!CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"").equalsIgnoreCase(""))
							    		{
										 d1= new BigDecimal(engine.eval(ratioExprThrdYear)+""); //(Double) engine.eval(ratioExprThrdYear);
										logger.info("eval third year ******************************** "+d1);
										Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"")).trim());  
										year3[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) {
//										ratioVo.setRatioThirdYear("0");
										e.printStackTrace();
									}
								
								
									try{
										if(!CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"").equalsIgnoreCase(""))
							    		{
										 d1= new BigDecimal(engine.eval(ratioExprForthYear)+"");// (Double) engine.eval(ratioExprForthYear);
										logger.info("eval fourth year ******************************** "+d1);
										Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"")).trim());  
										year4[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) {
//										ratioVo.setRatioFourthYear("0");
										e.printStackTrace();
									}
								
									try{
										if(!CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"").equalsIgnoreCase(""))
							    		{
										 d1=new BigDecimal(engine.eval(ratioExprFifthYear)+"");// (Double) engine.eval(ratioExprFifthYear);
										logger.info("eval fifth year ******************************** "+d1);
										Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"")).trim());  
										year5[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) {
	//									ratioVo.setRatioFifthYear("0");
										e.printStackTrace();
									}
					    			
							}
							z++;
							
							

				 }
							}else{
								year1[z] =year1[z];
							    year2[z] = year2[z];
							    year3[z] =year3[z]; 
							    year4[z] = year4[z];
							    year5[z]=year5[z];
							    z++;
								
							}
				 }
			 }
		    //sachin work end

	     
	     if(pCode1.length >0 )
	     {
	    for(int i=0,k=0;i<pCode1.length;i++,k++)
	    {
	    	for(int j=0;j<5;j++)
	    	{
	    		
	    		if(j==0)
	    		{
	    			pCodeWithYear[i][j]=year1[k];
	    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
	    		}
	    		else if(j==1)
	    		{
	    			pCodeWithYear[i][j]=year2[k];
	    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
	    		}
	    		else if(j==2)
	    		{
	    			pCodeWithYear[i][j]=year3[k];
	    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
	    		}
	    		else if(j==3)
	    		{
	    			pCodeWithYear[i][j]=year4[k];
	    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
	    		}
	    		else if(j==4)
	    		{
	    			pCodeWithYear[i][j]=year5[k];
	    			logger.info("two d value ...................... "+pCodeWithYear[i][j]);
	    		}
	    	}
	    }
	   
	    int currBusinessDateYear =Integer.parseInt(StringEscapeUtils.escapeSql(vo.getCurrBusinessDateYear()));
	    try{
	    	
		
	    	
	    
	    if(!CommonFunction.checkNull(vo.getpCode1()).equalsIgnoreCase(""))
         {
	    	maxId = ConnectionDAO.singleReturn("select max(FINANCIAL_DATA_ID) from cr_financial_data_dtl  WITH (ROWLOCK) ");
	    	
	    	int count=0;
	    	String paramValue[]=null;
	    	String paramVal="";

	    	for(int n=0;n<pCode1.length;n++)
    		{
    			String query1=  "SELECT distinct PARAM_CODE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(pCode1[n])+"' ";
		    	logger.info("query1--saveBalanceSheetWithMinus->"+query1);
		    	String paramCode=ConnectionDAO.singleReturn(query1);
		     	logger.info("status1--saveBalanceSheetWithMinus-->"+paramCode);
		     			    	if(! CommonFunction.checkNull(paramCode).equals("")){
			     	if(paramVal.equals("")){	
			     		paramVal=paramCode;
			     	}else{
			     		paramVal=paramVal+","+paramCode;
			     	}
			     	count++;
			     	paramValue=paramVal.split(",");
			     	logger.info("paramValue-length------>"+paramValue.length);
			     	logger.info("paramValue-length------>"+paramValue[n]);
			     	logger.info("count-"+count);
			     	}
    		}
		    	if(! CommonFunction.checkNull(paramValue).equals("")){
		    		logger.info("paramValue-length------>"+paramValue.length);
	    		  if(paramValue.length>0){
	    				logger.info("paramValue-length------>"+paramValue.length);
			    	for(int m=0;m<count;m++){
			    		 insertPrepStmtObject = new PrepStmtObject();
			    		String query = "DELETE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(paramValue[m])+"' ";
			    		logger.info("query--saveBalanceSheetWithMinus-->"+query);
			    		insertPrepStmtObject.setSql(query);
			    		qryList.add(insertPrepStmtObject);
			    	}
	    		  }
	    		} 
    		for(int k=0;k<pCode1.length; k++)
    		{
    			for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
    			{
    			 insertPrepStmtObject = new PrepStmtObject();
    			 bufInsSql =	new StringBuffer();
    			
    			
    			bufInsSql.append("insert into cr_financial_data_dtl(DEAL_ID,SOURCE_TYPE,PARAM_CODE,FINANCIAL_YEAR,PARAM_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
    			bufInsSql.append(" values ( ");
    			bufInsSql.append(" ?," );
    			bufInsSql.append(" ?," );
    			bufInsSql.append(" ?," );
    			bufInsSql.append(" ?," );
    			bufInsSql.append(" ?," );
    			bufInsSql.append(" ?," );
    			bufInsSql.append(" ?," );
    			bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
				
    			bufInsSql.append("  )" );
    			
    		  	logger.info("saveBalanceSheetWithMinus-----------financial deal id **************** "+vo.getLbxDealNo());
    		  	
    			if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
    				insertPrepStmtObject.addNull();
    			else
    				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
    			
    			if((CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
    				insertPrepStmtObject.addNull();
    			else
    				insertPrepStmtObject.addString((vo.getSourceType()).trim());
    			
    			if((CommonFunction.checkNull(pCode1[k])).trim().equalsIgnoreCase(""))
    				insertPrepStmtObject.addNull();
    			else
    				insertPrepStmtObject.addString((pCode1[k]).trim());
    				
    			if((CommonFunction.checkNull(analysisYear[pVal+1])).trim().equalsIgnoreCase(""))
    				insertPrepStmtObject.addNull();
    			else
    				insertPrepStmtObject.addString((analysisYear[pVal+1]).trim());
    			
    			if((CommonFunction.checkNull(pCodeWithYear[k][pVal])).trim().equalsIgnoreCase(""))
    				insertPrepStmtObject.addString("0.00");
				else
					try {
						insertPrepStmtObject.addString(myFormatter.parse((pCodeWithYear[k][pVal].trim())).toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
    		
    			insertPrepStmtObject.addString("P");//,REC_STATUS

    			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
    				insertPrepStmtObject.addNull();
    			else
    				insertPrepStmtObject.addString((vo.getUserId()).trim());

    			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
    				insertPrepStmtObject.addNull();
    			else
    				insertPrepStmtObject.addString((vo.getBusinessDate()));
    			
    				insertPrepStmtObject.setSql(bufInsSql.toString());
    				logger.info("IN saveBalanceSheetWithMinus() insert query ### "+insertPrepStmtObject.printQuery());
    				
    				
    				qryList.add(insertPrepStmtObject);
    			
    		 }
           }
         }

		//  logger.info("IN saveBalanceSheet() insert query: "+qryList.get(0));
	    
		  
		  //status=false;
	    logger.info("IN saveBalanceSheetWithMinus()  qryList: "+qryList);
				if(qryList.size()>0){
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				if(status)
				{									
					ids = ConnectionDAO.singleReturn("SELECT STUFF((SELECT ',' + convert(varchar(20),FINANCIAL_DATA_ID) FROM cr_financial_data_dtl where FINANCIAL_DATA_ID>'"+maxId+"' FOR XML PATH ('')), 1, 1, '')" );
				}}
			} catch (Exception e) {
				e.printStackTrace();
			}
		    logger.info("In saveBalanceSheetWithMinus......................"+status);
	    }
	    
	 
	    	idsinsert=null;
	    	maxId=null;
	 
		return ids;
	
	}
	public ArrayList<Object> getYears(String businessDate)
	{

		ArrayList<Object> list=new ArrayList<Object>();
		Date date;
		DateFormat formatter ; 
		formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date =(Date)formatter.parse(businessDate);
		
			 System.out.println("businessDate: " +businessDate);
			 SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
			 int year =Integer.parseInt(simpleDateformat.format(date));
			  logger.info("Year: " + simpleDateformat.format(date));
			  for(int i=year;i>=year-5;i--)
			  {
				  BankAccountAnalysisVo vo = new BankAccountAnalysisVo();
				  vo.setStatementMonthAndYear(i+"");
				  list.add(vo);
			  }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<CommonDealVo> financialSearchGetDetail(CommonDealVo vo) {
		ArrayList list=new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = 5;


		try
		{
			logger.info("Inside financialSearchGetDetail.....");
			ArrayList header=null;

			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
			 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			 			String userName=ConnectionDAO.singleReturn(userNameQ);
			 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			 			
			 			userNameQ=null;
			 			
			CommonDealVo fetchVo= (CommonDealVo) vo;
			boolean appendSQL=false;
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			String joinStatus="INNER";
			if(vo.getRecStatus().equalsIgnoreCase("A"))
			{
				joinStatus="LEFT";
			}
			
			
			bufInsSql.append("select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(d.DEAL_DATE,'" +dateFormat+ "') as DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT,d.DEAL_CUSTOMER_TYPE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID ");
			
			bufInsSql.append(" from cr_deal_dtl d");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_deal_dtl d ");
			
			bufInsSql.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
			
			bufInsSql.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSqlTempCount.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
			
			bufInsSql.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSqlTempCount.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
			
			bufInsSql.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");			
			bufInsSqlTempCount.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
		if(!CommonFunction.checkNull(vo.getRecStatus()).equalsIgnoreCase("A"))
		{
			bufInsSql.append(" "+joinStatus+" JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL AND DEAL_STAGE_ID='FAC' AND M.REC_STATUS='A' ");			
			bufInsSqlTempCount.append(" "+joinStatus+" JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL AND DEAL_STAGE_ID='FAC' AND M.REC_STATUS='A' ");
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&
					(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&
					(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&
					(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase("")))&&
					(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserId()).trim()).equalsIgnoreCase(""))))
			{
				bufInsSql.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' d.Rec_status='F' AND B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'");
				bufInsSqlTempCount.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' d.Rec_status='F' AND B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'");
			}
		}	
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getInitiationDate().equalsIgnoreCase("")))||((vo.getCustomername().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))){
				appendSQL=true;
			}
		if(!CommonFunction.checkNull(vo.getRecStatus()).equalsIgnoreCase("A"))
		 {	
			if(appendSQL)
			{
				logger.info("In Where Clause");
				
				bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' AND d.Rec_status='F' ");
				
				bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"'  AND d.Rec_status='F'");
				
			}
			if (!((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserId())).trim().equalsIgnoreCase(""))))
		    {

				bufInsSql.append(" and d.DEAL_ID in (select DEAL_ID from cr_financial_data_dtl B where REC_STATUS='P'  and B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'  ) ");	
				bufInsSqlTempCount.append(" and d.DEAL_ID in (select DEAL_ID from cr_financial_data_dtl B where REC_STATUS='P'  and B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'  )");
				appendSQL = true;
						
			    }
			   

			
		 }
			  
		if(!CommonFunction.checkNull(vo.getRecStatus()).equalsIgnoreCase("A"))
	    {  
		 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
	        bufInsSql.append(" AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
	        bufInsSqlTempCount.append(" AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
	   
	   	 	appendSQL=true;
	   	  
	     }
	    }
		else
		{
			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
			        bufInsSql.append("WHERE d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
			        bufInsSqlTempCount.append("WHERE d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
			   
			   	 	appendSQL=true;
			   	  
			     }
		}
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim().equalsIgnoreCase("")))) {
		   	  bufInsSql.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  appendSQL=true;
		     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	bufInsSqlTempCount.append("AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	  appendSQL=true;
			     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	bufInsSqlTempCount.append("AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	  appendSQL=true;
			     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserId())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("AND B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' ");
			   	bufInsSqlTempCount.append("AND B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' ");
			   	  appendSQL=true;
			     }
			
			LoggerMsg.info("query : "+bufInsSql.toString());
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			bufInsSqlTempCount=null;
			
//			if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null && StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null) || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomername()).trim().equalsIgnoreCase("")) || (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null  && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase("")) || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null && StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*5;
				endRecordIndex = 5;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			
  			
			
//			}
			logger.info("query : "+bufInsSql.toString());
			
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());

		    bufInsSql=null;
						
		    for(int i=0;i<header.size();i++){
				logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fetchVo = new CommonDealVo();
					fetchVo.setLbxDealNo((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
					
					
					logger.info("CommonFunction.checkNull(header1.get(8) ))))))))))))))))))) "+CommonFunction.checkNull(header1.get(8)).toString());
					
					if(CommonFunction.checkNull(header1.get(8)).toString().equalsIgnoreCase("I"))
					{
						fetchVo.setCustomerType("INDIVIDUAL");
						//fetchVo.setDealNo("<a href=\"#\" onclick=\"editIndividualFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "','"+CommonFunction.checkNull(header1.get(1)).toString()+"','"+CommonFunction.checkNull(header1.get(2)).toString()+"');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
						fetchVo.setDealNo("<a href=\"#\" onclick=\"editIndividualFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					}
					else
					{
						fetchVo.setCustomerType("CORPORATE");
						fetchVo.setDealNo("<a href=\"#\" onclick=\"editFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					}
					
					
					//fetchVo.setDealNo("<a href=\"#\" onclick=\"editFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "','"+CommonFunction.checkNull(header1.get(1)).toString()+"','"+CommonFunction.checkNull(header1.get(2)).toString()+"');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");

					fetchVo.setDeal((CommonFunction.checkNull(header1.get(1))).trim());
					
					//fetchVo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					
					fetchVo.setApplicationdate((CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setTenure((CommonFunction.checkNull(header1.get(6))).trim());
					fetchVo.setLoanAmount((CommonFunction.checkNull(header1.get(7))).trim());
					if(!(CommonFunction.checkNull(header1.get(7))).trim().equalsIgnoreCase(""))
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(7))).trim());  
						fetchVo.setLoanAmount(myFormatter.format(reconNum));
					}
					else
					{
						fetchVo.setLoanAmount("0.00");
					}
					
					fetchVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(9))).trim());
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		LoggerMsg.info("Detail List when searchList is : "+list.size());
		
		return list;
	}

	public ArrayList getParamDetailDetails(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{
			
		if(sourceType.equalsIgnoreCase(""))
		{
			query.append("SELECT PARAM_CODE,PARAM_NAME,ISNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA FROM cr_financial_param WHERE REC_STATUS='A'");
		}
		else
		{
			query.append("SELECT PARAM_CODE,PARAM_NAME,SUB_TYPE,CASE SUB_TYPE WHEN 'A' THEN 'ASSET' WHEN 'L' THEN 'LIABILITY' END AS SUB_TYPE_DESC,ISNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA,NEGATIVE_ALLOWED FROM cr_financial_param WHERE REC_STATUS='A' and SOURCE_TYPE='"+CommonFunction.checkNull(sourceType)+"' AND NEGATIVE_ALLOWED IN('A','X') ORDER BY SEQUENCE_NO");
		}
		logger.info("query : "+query.toString());
		ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisVo vo = new FinancialAnalysisVo();
				vo.setParameCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setParamName((CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setSubType((CommonFunction.checkNull(subParamDetail.get(2))).trim());
				vo.setSubTypeDesc((CommonFunction.checkNull(subParamDetail.get(3))).trim());
				vo.setAutoCalculated((CommonFunction.checkNull(subParamDetail.get(4))).trim());
				vo.setFinancialFormula((CommonFunction.checkNull(subParamDetail.get(5))).trim());
				vo.setNegativeAllow((CommonFunction.checkNull(subParamDetail.get(6))).trim());
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	
	public ArrayList getParamMinusDetails(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{
			
			query.append("SELECT PARAM_CODE,PARAM_NAME,SUB_TYPE,CASE SUB_TYPE WHEN 'A' THEN 'ASSET' WHEN 'L' THEN 'LIABILITY' END AS SUB_TYPE_DESC FROM cr_financial_param WHERE REC_STATUS='A' and SOURCE_TYPE='"+CommonFunction.checkNull(sourceType)+"' AND NEGATIVE_ALLOWED IN('A','X') ORDER BY SEQUENCE_NO");
	
		logger.info("query : "+query.toString());
		ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisParamVo vo = new FinancialAnalysisParamVo();
				vo.setNegativeParamCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setNegativeparamName((CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setSubType((CommonFunction.checkNull(subParamDetail.get(2))).trim());
				vo.setSubTypeDesc((CommonFunction.checkNull(subParamDetail.get(3))).trim());
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	
	
	public ArrayList getRatioParamDetails(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
		try{
		StringBuilder query=new StringBuilder();
	 query.append("SELECT RATIO_CODE,RATIO_NAME,RATIO_FORMULA  FROM cr_ratios_m WHERE REC_STATUS='A' ORDER BY SEQUENCE_NO");
		
		logger.info("query : "+query.toString());
		ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisVo vo = new FinancialAnalysisVo();
				vo.setParameCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				vo.setRatioName((CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setRatioExpr((CommonFunction.checkNull(subParamDetail.get(2))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
			
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	
	public ArrayList<CommonDealVo> financialGetDetailBehind(CommonDealVo vo,HttpServletRequest request) {
		ArrayList list=new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList header=new ArrayList();
		try
		{
			logger.info("Inside financialGetDetailBehind()...............DaoImpl.....");
			
			
			CommonDealVo fetchVo= (CommonDealVo) vo;
			boolean appendSQL=false;
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			logger.info("    financialGetDetailBehind()  getReportingToUserId  ++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
 			/*String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
 			String userName=ConnectionDAO.singleReturn(userNameQ);
 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
 			
 			userNameQ=null;
 			userName=null;*/
			
			bufInsSql.append("select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,case when d.DEAL_CUSTOMER_TYPE='C' then 'CORPORATE' else 'INDIVIDUAL' end as DEAL_CUSTOMER_TYPE,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID ");
			bufInsSql.append(" from cr_deal_dtl d");
			bufInsSql.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSql.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSql.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSql.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");			
			bufInsSql.append(" inner JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL AND DEAL_STAGE_ID='FAC' AND M.REC_STATUS='A' ");		
			
			bufInsSqlTempCount.append(" select count(1) from cr_deal_dtl d");
			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSqlTempCount.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSqlTempCount.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSqlTempCount.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");			
			bufInsSqlTempCount.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL AND DEAL_STAGE_ID='FAC' AND M.REC_STATUS='A' ");
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			{
				bufInsSql.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
				bufInsSqlTempCount.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")) ||(vo.getCustomername().equalsIgnoreCase(""))||(vo.getLbxProductID().equalsIgnoreCase(""))||(vo.getLbxscheme().equalsIgnoreCase(""))))
			{
				appendSQL=true;
			}
			
			
			
			if(appendSQL){
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' and d.DEAL_ID not in (select DEAL_ID from cr_financial_data_dtl B where REC_STATUS='P' ");
				bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' and d.DEAL_ID not in (select DEAL_ID from cr_financial_data_dtl B where REC_STATUS='P' ");
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReportingToUserId())).trim().equalsIgnoreCase(""))))
				{
					bufInsSql.append(" and B.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ");	
					bufInsSqlTempCount.append(" and B.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"'");
					appendSQL = true;
					logger.debug("Rituuuuuuuuuuu a");				
				}
				else
				{
					bufInsSql.append(" and B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' ");	
					bufInsSqlTempCount.append(" and B.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"'");
					appendSQL = true;
					logger.debug("Rituuuuuuuuuuu b");
				}
				
				bufInsSql.append(" )");
				bufInsSqlTempCount.append(" )");
				
			}
			
			

			LoggerMsg.info("financialGetDetailBehind()....................query : "+bufInsSql.toString());
			logger.info(" financialGetDetailBehind() .................... : "+bufInsSqlTempCount.toString());
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info(" count......... : "+count);
			bufInsSqlTempCount=null;
			
//			if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null && StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null) || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomername()).trim().equalsIgnoreCase("")) || (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null  && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase("")) || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null && StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
			//{
			
			//logger.info("getCurrentPageLink() .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				//logger.info("startRecordIndex .................... "+startRecordIndex);
				//logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
	//		}
			logger.info(" financialGetDetailBehind()-----------------query : "+bufInsSql);
			
			
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
		    
		    bufInsSql=null;
						
			for(int i=0;i<header.size();i++){
				
				logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					logger.info("header list size ....................."+header.size());
					fetchVo = new CommonDealVo();
				
					fetchVo.setLbxDealNo((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
					
					logger.info("   financialGetDetailBehind() CommonFunction.checkNull(header1.get(0) ))))))))))))))))))) "+CommonFunction.checkNull(header1.get(5)).toString());
					
					if(CommonFunction.checkNull(header1.get(5)).toString().equalsIgnoreCase("INDIVIDUAL"))
					{
						fetchVo.setDealNo("<a href=\"#\" onclick=\"editIndividualFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					}
					else
					{
						fetchVo.setDealNo("<a href=\"#\" onclick=\"editFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					}
					
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setCustomerType((CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(6))).trim());
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		if(header.size()==0)
//		{
//			CommonDealVo fetchVo = new CommonDealVo();
//			fetchVo.setTotalRecordSize(count);
//			list.add(fetchVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+list.size());
//		}
//		LoggerMsg.info("Detail List when searchList is : "+list.size());
		
		return list;
	}
	
	
	public boolean saveFinancialRatioAnalysisDetail(Object ob)
	{
		logger.info("In saveFinancialRatioAnalysisDetail() DaoImpl.............................. ");
		FinancialAnalysisVo vo=(FinancialAnalysisVo)ob;
		StringBuffer bufInsSql =null;
		ArrayList qryList=new ArrayList();
		
		    String pCode[] = vo.getpCode();
		    String year1[] = vo.getYear1();
		    String year2[] = vo.getYear2();
		    String year3[] = vo.getYear3();
		    String year4[] = vo.getYear4();
		    String year5[] = vo.getYear5();
		    logger.info("year1.......................... "+year1.toString());
		    logger.info("year2.......................... "+year2.toString());
		    logger.info("year3.......................... "+year3.toString());
		    logger.info("year4.......................... "+year4.toString());
		    logger.info("year5.......................... "+year5.toString());
		    String[][] pCodeWithYear=new String [50][50];
		    for(int i=0,k=0;i<pCode.length;i++,k++)
		    {
		    	for(int j=0;j<5;j++)
		    	{
		    		
		    		if(j==0)
		    		{
		    			pCodeWithYear[i][j]=year1[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==1)
		    		{
		    			pCodeWithYear[i][j]=year2[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==2)
		    		{
		    			pCodeWithYear[i][j]=year3[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==3)
		    		{
		    			pCodeWithYear[i][j]=year4[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==4)
		    		{
		    			pCodeWithYear[i][j]=year5[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    	}
		    }
		   
		    int currBusinessDateYear =Integer.parseInt(StringEscapeUtils.escapeSql(vo.getCurrBusinessDateYear()));
		    logger.info("currBusinessDateYear.......................... "+currBusinessDateYear);
		    if(!CommonFunction.checkNull(vo.getpCode()).equalsIgnoreCase(""))
	         {
	    		for(int k=0;k<pCode.length; k++)
	    		{
	    			for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
	    			{
	    				
	    			logger.info("param Code in saveBalanceSheet: "+pCode[k]);
	    			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	    			 bufInsSql =	new StringBuffer();
	    			
	    			bufInsSql.append("insert into cr_financial_data_dtl(DEAL_ID,SOURCE_TYPE,PARAM_CODE,FINANCIAL_YEAR,PARAM_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
	    			bufInsSql.append(" values ( ");
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
					
	    			bufInsSql.append("  )" );
	    			
	    		  	//logger.info("financial deal id **************** "+vo.getLbxDealNo());
	    		  	
	    			if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
	    			
	    			if((CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getSourceType()).trim());
	    			
	    			if((CommonFunction.checkNull(pCode[k])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((pCode[k]).trim());
	    				
	    			//logger.info("financial year  **************** "+y);
	    			if((CommonFunction.checkNull(y)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((y+"").trim());
	    			
	    			//logger.info("param value  **************** "+CommonFunction.checkNull(year1[pVal]));
	    			
	    			if((CommonFunction.checkNull(pCodeWithYear[k][pVal])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((pCodeWithYear[k][pVal]).trim());
	    			
	    		
	    			insertPrepStmtObject.addString("P");//,REC_STATUS

	    			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getUserId()).trim());

	    			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getBusinessDate()));
	    			
	    				insertPrepStmtObject.setSql(bufInsSql.toString());
	    				logger.info("IN saveDocument() UPDATE query1 ### "+insertPrepStmtObject.printQuery());
	    				qryList.add(insertPrepStmtObject);
	    				bufInsSql=null;
	    		 }
	           }
	         }

			  logger.info("IN saveBalanceSheet() insert query: "+qryList.get(0));
			  
			  boolean status=false;
				try {
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			    logger.info("In saveBalanceSheet......................"+status);
			return status;
		
	}
	
	public boolean financialAnalysisForward(String dealId) {

		logger.info("In financialAnalysisForward....... "+dealId);
		ArrayList qryList = null;
		qryList=new ArrayList();
		boolean status=false;
//		String deal="";
		StringBuilder deal=new StringBuilder();
		deal.append(ConnectionDAO.singleReturn("select DEAL_ID from cr_financial_data_dtl WHERE DEAL_ID='"+dealId+"'"));
		if(deal!=null && !deal.toString().equalsIgnoreCase(""))
		{
			StringBuilder query=new StringBuilder();
			
			 query.append("update cr_financial_data_dtl set REC_STATUS='A' WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"");
			logger.info("query.."+query.toString());			
	        qryList.add(query);
	        
	       	query=null;
	
			try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In stageForward......................status= "+status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			status=false;
		}
		deal=null;
		
		return status;
	}
	public boolean financialRatioAnalysisUpdate(String dealId) {

		logger.info("In individualRatioAnalysisUpdate....... "+dealId);
		ArrayList qryList = null;
		qryList=new ArrayList();
		boolean status=false;
		String deal="";
		deal = ConnectionDAO.singleReturn("select DEAL_ID from cr_ratio_analysis_dtl WHERE DEAL_ID='"+dealId+"'");
		String pQuery="select PARAMETER_VALUE from parameter_mst WHERE PARAMETER_KEY='RATIO_ANALYSIS_BY_PASS' ";
		String pResult=ConnectionDAO.singleReturn(pQuery);
		if(CommonFunction.checkNull(pResult).trim().equalsIgnoreCase("Y"))
			status=true;
		if(deal!=null && !deal.equalsIgnoreCase(""))
		{
			String query="update cr_ratio_analysis_dtl set REC_STATUS='A' WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"";
			logger.info("individualRatioAnalysisUpdate.."+query.toString());			
	        qryList.add(query);
	        
			try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In individualRatioAnalysisUpdate......................status= "+status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public ArrayList getdealAllParamDeatils(CommonDealVo vo1) {
		logger.info("getdealAllParamDeatils------------------>");
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{

			 StringBuilder query1= new StringBuilder();
				
			  query1.append("select  distinct f.PARAM_CODE,p.PARAM_NAME,P.SUB_TYPE,case p.SUB_TYPE when 'A' then 'ASSET' when 'L' then 'LIABILITY' end as sub_type_desc,ISNULL(P.AUTO_CALCULATED,'N'),P.FINANCIAL_FORMULA,p.NEGATIVE_ALLOWED,p.SEQUENCE_NO  from cr_financial_data_dtl f right JOIN cr_financial_param p on f.SOURCE_TYPE=p.SOURCE_TYPE where  ");
			  if(!CommonFunction.checkNull(vo1.getRecStatus()).equalsIgnoreCase("A"))
			    {
			       query1.append(" f.REC_STATUS='P' and ");
			    }
			query1.append(" f.PARAM_CODE=p.PARAM_CODE and f.DEAL_ID='"+vo1.getLbxDealNo()+"' and f.SOURCE_TYPE='"+vo1.getSourceType()+"' and p.REC_STATUS='A' and p.NEGATIVE_ALLOWED IN('X','A') ORDER BY p.SEQUENCE_NO ");
			logger.info("In getdealAllParamDeatils query1 "+query1.toString());
			ArrayList fParamCodelist=ConnectionDAO.sqlSelect(query1.toString());
			query1=null;
			
			logger.info("fParamCodelist...size...."+fParamCodelist.size());
			query.append("select p.PARAM_CODE ,f.PARAM_VALUE ,f.FINANCIAL_DATA_ID,p.PARAM_NAME,P.SUB_TYPE,case p.SUB_TYPE when 'A' then 'ASSET' when 'L' then 'LIABILITY' end as sub_type_desc,ISNULL(P.AUTO_CALCULATED,'N'),P.FINANCIAL_FORMULA " );
			query.append(" from cr_financial_data_dtl f right JOIN cr_financial_param p on f.SOURCE_TYPE=p.SOURCE_TYPE where " );
			
			 if(!CommonFunction.checkNull(vo1.getRecStatus()).equalsIgnoreCase("A"))
			  {
			       query.append(" f.REC_STATUS='P' and ");
			  }
			query.append(" f.PARAM_CODE=p.PARAM_CODE and f.DEAL_ID='"+vo1.getLbxDealNo()+"' and f.SOURCE_TYPE='"+vo1.getSourceType()+"'  " );
            query.append(" and p.REC_STATUS='A' and p.NEGATIVE_ALLOWED IN('X','A')" );
			
            logger.info("In getdealAllParamDeatils dataList query  "+query.toString());
		ArrayList dataList = ConnectionDAO.sqlSelect(query.toString());
		logger.info("dataList........size......"+dataList.size());
		String[] paramValues=null ;
		
		query=null;
		String paramCode="";
		String paramValue="";
		String finnId="";
		String[] finnIdss=null ;
		FinancialAnalysisVo vo = new FinancialAnalysisVo();
		
		
		    logger.info("fParamCode---size----- "+fParamCodelist.size());
			for(int k=0;k<fParamCodelist.size();k++){
				ArrayList subList=(ArrayList) fParamCodelist.get(k);
				if(subList.size()>0){
					String fParamCode=(String) subList.get(0);
					String fParamName=(String) subList.get(1);
					String fSubType=(String) subList.get(2);
					String fSubTypeDesc=(String) subList.get(3);
					String autoCalculated=(String) subList.get(4);
					String financialFormula=(String) subList.get(5);
					String negativeAllow=(String) subList.get(6);
					
			
				
					if(dataList.size()>0){
					
						
						for(int i=0 ; i< dataList.size(); i++)
						{	
							
							ArrayList dataList1=(ArrayList)dataList.get(i);
						
							if(dataList1!=null && dataList1.size()>0)
							{
								paramCode=CommonFunction.checkNull(dataList1.get(0)).trim();
								if(paramCode.equalsIgnoreCase(fParamCode))
								{
									
									
									
									String paramVal=(CommonFunction.checkNull(dataList1.get(1))).trim();
									
									if(paramValue.equalsIgnoreCase("")){
										paramValue=paramVal;
									}else{
										paramValue=paramValue+","+paramVal;
									}
									
									paramValues = (paramValue).split(",");
									
									String finId=(CommonFunction.checkNull(dataList1.get(2))).trim();
									
									if(finnId.equalsIgnoreCase("")){
										finnId=finId;
									}else{
										finnId=finnId+","+finId;
									}
									
									finnIdss = (finnId).split(",");
									
								}
								
							}
							
						}
						
					if(paramValues.length>0){
						for(int i=0;i<paramValues.length;i=i+5){
							
							 vo = new FinancialAnalysisVo();
							 vo.setParameCode(fParamCode);
							 vo.setParamName(fParamName);
							 vo.setSubType(fSubType);
							 vo.setSubTypeDesc(fSubTypeDesc);
							 vo.setAutoCalculated(autoCalculated);
							 vo.setFinancialFormula(financialFormula);
							 vo.setNegativeAllow(negativeAllow);
						
					
						
						if(!CommonFunction.checkNull(paramValues[i]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i])).trim());  
				    		vo.setFirstYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i]).trim());
						
						if(!CommonFunction.checkNull(paramValues[i+1]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+1])).trim());  
				    		vo.setSecondYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+1]).trim());
						if(!CommonFunction.checkNull(paramValues[i+2]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+2])).trim());  
				    		vo.setThirdYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+2]).trim());
						if(!CommonFunction.checkNull(paramValues[i+3]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+3])).trim());  
				    		vo.setFourthYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+3]).trim());
						if(!CommonFunction.checkNull(paramValues[i+4]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+4])).trim());  
				    		vo.setFifthYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+4]).trim());
			    		
			    	
			    	
						}
						list.add(vo);
					}
				
					}
			}
		
						
				
				
			
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	public ArrayList getdealAllMinusParamDeatils(CommonDealVo vo1) {
		logger.info("getdealAllParamDeatils------------------>");
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{

			String query1="select  distinct f.PARAM_CODE,p.PARAM_NAME,p.SUB_TYPE,case p.SUB_TYPE when 'A' then 'ASSET' when 'L' then 'LIABILITY' end as sub_type_desc from cr_financial_data_dtl f right JOIN cr_financial_param p on f.SOURCE_TYPE=p.SOURCE_TYPE where  "+
			"f.REC_STATUS='P' and f.PARAM_CODE=p.PARAM_CODE and f.DEAL_ID='"+vo1.getLbxDealNo()+"' and f.SOURCE_TYPE='"+vo1.getSourceType()+"' and p.REC_STATUS='A' and p.NEGATIVE_ALLOWED IN('X','A') "; 
			ArrayList fParamCodelist=ConnectionDAO.sqlSelect(query1);
			
			logger.info("fParamCodelist...size...."+fParamCodelist.size());
			query.append("select p.PARAM_CODE ,f.PARAM_VALUE ,f.FINANCIAL_DATA_ID,p.PARAM_NAME,p.SUB_TYPE,case p.SUB_TYPE when 'A' then 'ASSET' when 'L' then 'LIABILITY' end as sub_type_desc " +
			" from cr_financial_data_dtl f right JOIN cr_financial_param p on f.SOURCE_TYPE=p.SOURCE_TYPE " +
			"where  f.REC_STATUS='P' and f.PARAM_CODE=p.PARAM_CODE and f.DEAL_ID='"+vo1.getLbxDealNo()+"' and f.SOURCE_TYPE='"+vo1.getSourceType()+"'  " +
			" and p.REC_STATUS='A' and p.NEGATIVE_ALLOWED IN('X','A') group by p.PARAM_CODE,f.FINANCIAL_YEAR");
			logger.info("query : "+query.toString());
		
		ArrayList dataList = ConnectionDAO.sqlSelect(query.toString());
		logger.info("dataList........size......"+dataList.size());
		String[] paramValues=null ;
		
		query=null;
		String paramCode="";
		String paramValue="";
		String finnId="";
		String[] finnIdss=null ;
		FinancialAnalysisParamVo vo = new FinancialAnalysisParamVo();
		
		
		    logger.info("fParamCode---size----- "+fParamCodelist.size());
			for(int k=0;k<fParamCodelist.size();k++){
				ArrayList subList=(ArrayList) fParamCodelist.get(k);
				if(subList.size()>0){
					String fParamCode=(String) subList.get(0);
					String fParamName=(String) subList.get(1);
					String fSubType=(String) subList.get(2);
					String fSubTypeDesc=(String) subList.get(3);
					logger.info("fParamCode-------- "+fParamCode);
					logger.info("fSubType:::::::::::::::::: "+fSubType);
					logger.info("fSubTypeDesc:::::::::::::: "+fSubTypeDesc);
						
				
					if(dataList.size()>0){
					
						logger.info("pparamValues......."+paramValues);
						for(int i=0 ; i< dataList.size(); i++)
						{	
							
							ArrayList dataList1=(ArrayList)dataList.get(i);
						
							if(dataList1!=null && dataList1.size()>0)
							{
								paramCode=CommonFunction.checkNull(dataList1.get(0)).trim();
								if(paramCode.equalsIgnoreCase(fParamCode))
								{
									
									
									logger.info("dataList1.get(0)---1---- "+dataList1.get(0));
									String paramVal=(CommonFunction.checkNull(dataList1.get(1))).trim();
									logger.info("paramVal......................."+paramVal);
									if(paramValue.equalsIgnoreCase("")){
										paramValue=paramVal;
									}else{
										paramValue=paramValue+","+paramVal;
									}
									logger.info("paramValue...................."+paramValue);
									paramValues = (paramValue).split(",");
									logger.info("paramValues size is ....................................."+paramValues.length);
									String finId=(CommonFunction.checkNull(dataList1.get(2))).trim();
									logger.info("finId.........."+finId);
									if(finnId.equalsIgnoreCase("")){
										finnId=finId;
									}else{
										finnId=finnId+","+finId;
									}
									logger.info("finnId........."+finnId);
									finnIdss = (finnId).split(",");
									
								}
								
							}
							
						}
						logger.info("finnIdss.......length......"+paramValues.length);
						logger.info("finnIdss.......length......"+paramValues);
					if(paramValues.length>0){
						for(int i=0;i<paramValues.length;i=i+5){
							
							 vo = new FinancialAnalysisParamVo();
							 vo.setNegativeParamCode(fParamCode);
							 vo.setNegativeparamName (fParamName);
							 vo.setSubType(fSubType);
							 vo.setSubTypeDesc(fSubTypeDesc);
						logger.info("-------aaaaaaaa--------");
					
						logger.info("paramValues[0]........"+paramValues[i]);
						if(!CommonFunction.checkNull(paramValues[i]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i])).trim());  
				    		vo.setFirstYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i]).trim());
						
						if(!CommonFunction.checkNull(paramValues[i+1]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+1])).trim());  
				    		vo.setSecondYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+1]).trim());
						if(!CommonFunction.checkNull(paramValues[i+2]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+2])).trim());  
				    		vo.setThirdYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+2]).trim());
						if(!CommonFunction.checkNull(paramValues[i+3]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+3])).trim());  
				    		vo.setFourthYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+3]).trim());
						if(!CommonFunction.checkNull(paramValues[i+4]).equalsIgnoreCase(""))
			    		{
			    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(paramValues[i+4])).trim());  
				    		vo.setFifthYear(myFormatter.format(reconNum));
			    		}vo.setFinancialId(CommonFunction.checkNull(finnIdss[i+4]).trim());
			    		
			    	
			    	
						}
						list.add(vo);
					}
				
					}
			}
		
						
				
				
			
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}

	public String saveProfitandBalance(FinancialAnalysisVo vo)
	{
		logger.info("In saveProfitandBalance DaoImpl.............................. ");
	
		boolean status=false;
		boolean flag=false;
		String maxId ="";
		String ids="";
		String idsinsert="";
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		    String pCode[] = vo.getpCode();
		    String year1[] = vo.getYear1();
		    String year2[] = vo.getYear2();
		    String year3[] = vo.getYear3();
		    String year4[] = vo.getYear4();
		    String year5[] = vo.getYear5();
		    String analysisYear[]=vo.getAnalysisYear();
		    ArrayList qryList=new ArrayList();
			StringBuffer bufInsSql =	new StringBuffer();
			FinancialAnalysisVo vos;
			int z=0;
			ArrayList<FinancialAnalysisVo> finanFormula=new ArrayList<FinancialAnalysisVo>();
//		    logger.info("year1.......................... "+year1.toString());
//		    logger.info("year2.......................... "+year2.toString());
//		    logger.info("year3.......................... "+year3.toString());
//		    logger.info("year4.......................... "+year4.toString());
//		    logger.info("year5.......................... "+year5.toString());
		  
		    	
		     String[][] pCodeWithYear=new String [50][50];
			   //sachin work start
				try
				{
				for(int i=0,k=0;i<pCode.length;i++){
					k=0; 
					vos=new FinancialAnalysisVo();
					ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,FINANCIAL_FORMULA FROM cr_financial_param WHERE PARAM_CODE='"+pCode[i]+"'");
						for(int t=0;t<pval.size();t++){
							ArrayList subPval=(ArrayList)pval.get(t);
							if(subPval.size()>0)
							{
								vos.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
								vos.setFinancialFormula((CommonFunction.checkNull(subPval.get(1))).trim());
							    finanFormula.add(vos); 
							}
						}
				}
				
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				//sachin work end
			    //sachin work start
			    if(finanFormula.size()>0)
				 {
//					 ArrayList balanceSheetList = (ArrayList)session.getAttribute("balanceSheetAllDetailsByDeal");
//					 ArrayList othersList = (ArrayList)session.getAttribute("otherAllDetailsByDeal");
//					 ArrayList profitLossList = (ArrayList)session.getAttribute("profitLossAllDetailsByDeal");
					 ArrayList ratioParamValue = new ArrayList();
					
					 ArrayList ratioAnalysisList = new ArrayList();
					 ScriptEngineManager mgr = new ScriptEngineManager();
					 ScriptEngine engine = mgr.getEngineByName("JavaScript");
					 for(int i=0 ; i< finanFormula.size() ; i++)
					 {
						 	   flag =false;
								FinancialAnalysisVo  vo1 = (FinancialAnalysisVo)finanFormula.get(i);
						 	    //List<FinancialAnalysisVo> vo1=finanFormula.subList(0, finanFormula.size())
								System.out.println("..................."+finanFormula.get(i).getFinancialFormula());
								System.out.println("..................."+finanFormula.get(i).getParamName());
								String ratioExprFstYear ="";
								String ratioExprSecYear ="";
								String ratioExprThrdYear ="";
								String ratioExprForthYear ="";
								String ratioExprFifthYear ="";
								if(!vo1.getFinancialFormula().equalsIgnoreCase("")){
								ratioExprFstYear =vo1.getFinancialFormula();
								ratioExprSecYear =vo1.getFinancialFormula();
								ratioExprThrdYear =vo1.getFinancialFormula();
								ratioExprForthYear =vo1.getFinancialFormula();
								ratioExprFifthYear =vo1.getFinancialFormula();
								
								FinancialAnalysisVo ratioVo = new FinancialAnalysisVo();
									if(pCode.length>0)
									{
										for(int j=0;j<pCode.length;j++){
											String paramCode=pCode[j];
											if(ratioExprFstYear.indexOf(paramCode) >= 0)
											{
//												ratioVo.setRatioParamCode(vo.getParameCode());
//												ratioVo.setRatioName(vo.getRatioName());
												try{
												//sachin
													String yearValue1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year1[j]).trim())).toString();
													String yearValue2=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year2[j]).trim())).toString();
													String yearValue3=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year3[j]).trim())).toString();
													String yearValue4=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year4[j]).trim())).toString();
													String yearValue5=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year5[j]).trim())).toString();
													yearValue1="("+yearValue1+")";
													yearValue2="("+yearValue2+")";
													yearValue3="("+yearValue3+")";
													yearValue4="("+yearValue4+")";
													yearValue5="("+yearValue5+")";
													ratioExprFstYear =ratioExprFstYear.replaceAll(paramCode,yearValue1);
													ratioExprSecYear = ratioExprSecYear.replaceAll(paramCode,yearValue2); 
													ratioExprThrdYear = ratioExprThrdYear.replaceAll(paramCode,yearValue3);
													ratioExprForthYear = ratioExprForthYear.replaceAll(paramCode,yearValue4);
													ratioExprFifthYear =ratioExprFifthYear.replaceAll(paramCode,yearValue5);
											
												//sachin
												
												flag=true;
												}
												catch (Exception e) {
//													ratioVo.setRatioFirstYear("00");
													e.printStackTrace();
												}
											}	
											
										}
								logger.info("ratioExprFstYear 111111111.............. "+ratioExprFstYear);
								BigDecimal d1 = new BigDecimal("0.00");
//								logger.info("flag &&&&&&&&&&&&&&&&&&&&&&&&& "+flag);
								if(flag)
								{
								
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"").equalsIgnoreCase(""))
								    		{
												logger.info("engine.eval(ratioExprFstYear) ******************************** "+engine.eval(ratioExprFstYear));
												 d1= new BigDecimal(engine.eval(ratioExprFstYear)+"");//) engine.eval(ratioExprFstYear);
												 
												logger.info("eval first year ******************************** "+d1);
												Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"")).trim());  
								    			year1[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioFirstYear("00");
											e.printStackTrace();
										}
										
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"").equalsIgnoreCase(""))
								    		{
											 d1=new BigDecimal(engine.eval(ratioExprSecYear)+"");// (BigDecimal) engine.eval(ratioExprSecYear);
											logger.info("eval second year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"")).trim());  
											year2[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioSecondYear("00");
											e.printStackTrace();
										}
						    			
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"").equalsIgnoreCase(""))
								    		{
											 d1= new BigDecimal(engine.eval(ratioExprThrdYear)+""); //(Double) engine.eval(ratioExprThrdYear);
											logger.info("eval third year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"")).trim());  
											year3[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioThirdYear("0");
											e.printStackTrace();
										}
									
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"").equalsIgnoreCase(""))
								    		{
											 d1= new BigDecimal(engine.eval(ratioExprForthYear)+"");// (Double) engine.eval(ratioExprForthYear);
											logger.info("eval fourth year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"")).trim());  
											year4[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioFourthYear("0");
											e.printStackTrace();
										}
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"").equalsIgnoreCase(""))
								    		{
											 d1=new BigDecimal(engine.eval(ratioExprFifthYear)+"");// (Double) engine.eval(ratioExprFifthYear);
											logger.info("eval fifth year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"")).trim());  
											year5[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
		//									ratioVo.setRatioFifthYear("0");
											e.printStackTrace();
										}
						    			
								}
								z++;
								
								

					 }
								}else{
									year1[z] =year1[z];
								    year2[z] = year2[z];
								    year3[z] =year3[z]; 
								    year4[z] = year4[z];
								    year5[z]=year5[z];
								    z++;
									
								}
					 }
				 }
			    //sachin work end

		     
		     logger.info("pCode.length ........ "+pCode.length );
		     if(pCode.length >0 )
		     {
		    for(int i=0,k=0;i<pCode.length;i++,k++)
		    {
		    	for(int j=0;j<5;j++)
		    	{
		    		
		    		if(j==0)
		    		{
		    			pCodeWithYear[i][j]=year1[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==1)
		    		{
		    			pCodeWithYear[i][j]=year2[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==2)
		    		{
		    			pCodeWithYear[i][j]=year3[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==3)
		    		{
		    			pCodeWithYear[i][j]=year4[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==4)
		    		{
		    			pCodeWithYear[i][j]=year5[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    	}
		    }
		   
		    int currBusinessDateYear =Integer.parseInt(StringEscapeUtils.escapeSql(vo.getCurrBusinessDateYear()));
		    try{
		    	
		    
		    if(!CommonFunction.checkNull(vo.getpCode()).equalsIgnoreCase(""))
	         {
		    	maxId = ConnectionDAO.singleReturn("select max(FINANCIAL_DATA_ID) from cr_financial_data_dtl  WITH (ROWLOCK) ");
		    	int count=0;
		    	String paramValue[]=null;
		    	String paramVal="";
		    	for(int n=0;n<pCode.length;n++)
	    		{
	    			String query1=  "SELECT distinct PARAM_CODE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(pCode[n])+"' ";
			    	logger.info("query1--saveProfitandBalance->"+query1);
			    	String paramCode=ConnectionDAO.singleReturn(query1);
			     	logger.info("status1--saveProfitandBalance-->"+paramCode);
			     	
			     	
			     	paramVal=paramVal+","+paramCode;
			     	count++;
			     	paramValue=paramVal.split(",");
			     	
	    		}
	    		  if(count>0){
				    	for(int m=0;m<count;m++){
				    		 insertPrepStmtObject = new PrepStmtObject();
				    		String query = "DELETE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(pCode[m])+"' ";
				    		logger.info("query--saveProfitandBalance-->"+query);
				    		insertPrepStmtObject.setSql(query);
				    		qryList.add(insertPrepStmtObject);
				    	}
					
		    		} 
			    	for(int k=0;k<pCode.length; k++)
		    		{
	    			 
	    			for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
	    			{
	    			 insertPrepStmtObject = new PrepStmtObject();
	    			 bufInsSql =	new StringBuffer();
	    			
	    			
	    			bufInsSql.append("insert into cr_financial_data_dtl(DEAL_ID,SOURCE_TYPE,PARAM_CODE,FINANCIAL_YEAR,PARAM_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
	    			bufInsSql.append(" values ( ");
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
	    			bufInsSql.append("  )" );
	    			
	    		  	logger.info("financial deal id **************** "+vo.getLbxDealNo());
	    		  	
	    			if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
	    			
	    			if((CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getSourceType()).trim());
	    			
	    			if((CommonFunction.checkNull(pCode[k])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((pCode[k]).trim());
	    				
	    			if((CommonFunction.checkNull(analysisYear[pVal+1])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((analysisYear[pVal+1]).trim());
	    			
	    			if((CommonFunction.checkNull(pCodeWithYear[k][pVal])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addString("0.00");
					else
						try {
							insertPrepStmtObject.addString(myFormatter.parse((pCodeWithYear[k][pVal].trim())).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				
	    		
	    			insertPrepStmtObject.addString("P");//,REC_STATUS

	    			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getUserId()).trim());

	    			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getBusinessDate()));
	    			
	    				insertPrepStmtObject.setSql(bufInsSql.toString());
	    				logger.info("IN saveBalanceSheet() insert query ### "+insertPrepStmtObject.printQuery());
	    				
	    				
	    				qryList.add(insertPrepStmtObject);
	    			
	    		 }
	           }
	         }

			//  logger.info("IN saveBalanceSheet() insert query: "+qryList.get(0));
		    
			  
			  //status=false;
		    logger.info("IN saveBalanceSheet()  qryList: "+qryList);
					if(qryList.size()>0){
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					if(status)
					{									
						ids = ConnectionDAO.singleReturn("SELECT STUFF((SELECT ',' + convert(varchar(20),FINANCIAL_DATA_ID) FROM cr_financial_data_dtl where FINANCIAL_DATA_ID>'"+maxId+"' FOR XML PATH ('')), 1, 1, '')" );
					}}
				} catch (Exception e) {
					e.printStackTrace();
				}
			    logger.info("In saveBalanceSheet......................"+status);
		    }
		    
		 
		    	idsinsert=null;
		    	maxId=null;
		 
			return ids;
		
	}
	public String saveProfitandBalanceWithMinus(FinancialAnalysisParamVo vo)
	{
		logger.info("In saveProfitandBalanceWithMinus DaoImpl.............................. ");
	
		boolean status=false;
		boolean flag=false;
		String maxId ="";
		String ids="";
		String idsinsert="";
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		   String pCode1[] = vo.getpCode1();
		    String year1[] = vo.getYear();
		    String year2[] = vo.getYear6();
		    String year3[] = vo.getYear7();
		    String year4[] = vo.getYear8();
		    String year5[] = vo.getYear9();
		    String analysisYear[]=vo.getAnalysisYear();
		    ArrayList qryList=new ArrayList();
			StringBuffer bufInsSql =	new StringBuffer();
//		    logger.info("year1.......................... "+year1.toString());
//		    logger.info("year2.......................... "+year2.toString());
//		    logger.info("year3.......................... "+year3.toString());
//		    logger.info("year4.......................... "+year4.toString());
//		    logger.info("year5.......................... "+year5.toString());
		    
		    logger.info("pCode1........length.......... "+pCode1.length);
		     String[][] pCodeWithYear=new String [50][50];
		     FinancialAnalysisVo vos;
		     int z=0;
		     ArrayList<FinancialAnalysisVo> finanFormula=new ArrayList<FinancialAnalysisVo>();
		     
		   //sachin work start
				try
				{
				for(int i=0,k=0;i<pCode1.length;i++){
					k=0; 
					vos=new FinancialAnalysisVo();
					ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,FINANCIAL_FORMULA FROM cr_financial_param WHERE PARAM_CODE='"+pCode1[i]+"'");
						for(int t=0;t<pval.size();t++){
							ArrayList subPval=(ArrayList)pval.get(t);
							if(subPval.size()>0)
							{
								vos.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
								vos.setFinancialFormula((CommonFunction.checkNull(subPval.get(1))).trim());
							    finanFormula.add(vos); 
							}
						}
				}
				
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				//sachin work end
			    //sachin work start
			    if(finanFormula.size()>0)
				 {
//					 ArrayList balanceSheetList = (ArrayList)session.getAttribute("balanceSheetAllDetailsByDeal");
//					 ArrayList othersList = (ArrayList)session.getAttribute("otherAllDetailsByDeal");
//					 ArrayList profitLossList = (ArrayList)session.getAttribute("profitLossAllDetailsByDeal");
					 ArrayList ratioParamValue = new ArrayList();
					
					 ArrayList ratioAnalysisList = new ArrayList();
					 ScriptEngineManager mgr = new ScriptEngineManager();
					 ScriptEngine engine = mgr.getEngineByName("JavaScript");
					 for(int i=0 ; i< finanFormula.size() ; i++)
					 {
						 	   flag =false;
								FinancialAnalysisVo  vo1 = (FinancialAnalysisVo)finanFormula.get(i);
						 	    //List<FinancialAnalysisVo> vo1=finanFormula.subList(0, finanFormula.size())
								System.out.println("..................."+finanFormula.get(i).getFinancialFormula());
								System.out.println("..................."+finanFormula.get(i).getParamName());
								String ratioExprFstYear ="";
								String ratioExprSecYear ="";
								String ratioExprThrdYear ="";
								String ratioExprForthYear ="";
								String ratioExprFifthYear ="";
								if(!vo1.getFinancialFormula().equalsIgnoreCase("")){
								ratioExprFstYear =vo1.getFinancialFormula();
								ratioExprSecYear =vo1.getFinancialFormula();
								ratioExprThrdYear =vo1.getFinancialFormula();
								ratioExprForthYear =vo1.getFinancialFormula();
								ratioExprFifthYear =vo1.getFinancialFormula();
								
								FinancialAnalysisVo ratioVo = new FinancialAnalysisVo();
									if(pCode1.length>0)
									{
										for(int j=0;j<pCode1.length;j++){
											String paramCode=pCode1[j];
											if(ratioExprFstYear.indexOf(paramCode) >= 0)
											{
//												ratioVo.setRatioParamCode(vo.getParameCode());
//												ratioVo.setRatioName(vo.getRatioName());
												try{
												//sachin
													String yearValue1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year1[j]).trim())).toString();
													String yearValue2=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year2[j]).trim())).toString();
													String yearValue3=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year3[j]).trim())).toString();
													String yearValue4=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year4[j]).trim())).toString();
													String yearValue5=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year5[j]).trim())).toString();
													yearValue1="("+yearValue1+")";
													yearValue2="("+yearValue2+")";
													yearValue3="("+yearValue3+")";
													yearValue4="("+yearValue4+")";
													yearValue5="("+yearValue5+")";
													ratioExprFstYear =ratioExprFstYear.replaceAll(paramCode,yearValue1);
													ratioExprSecYear = ratioExprSecYear.replaceAll(paramCode,yearValue2); 
													ratioExprThrdYear = ratioExprThrdYear.replaceAll(paramCode,yearValue3);
													ratioExprForthYear = ratioExprForthYear.replaceAll(paramCode,yearValue4);
													ratioExprFifthYear =ratioExprFifthYear.replaceAll(paramCode,yearValue5);
												
												//sachin	
												flag=true;
												}
												catch (Exception e) {
//													ratioVo.setRatioFirstYear("00");
													e.printStackTrace();
												}
											}	
											
										}
								logger.info("ratioExprFstYear 111111111.............. "+ratioExprFstYear);
								BigDecimal d1 = new BigDecimal("0.00");
//								logger.info("flag &&&&&&&&&&&&&&&&&&&&&&&&& "+flag);
								if(flag)
								{
								
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"").equalsIgnoreCase(""))
								    		{
												logger.info("engine.eval(ratioExprFstYear) ******************************** "+engine.eval(ratioExprFstYear));
												 d1= new BigDecimal(engine.eval(ratioExprFstYear)+"");//) engine.eval(ratioExprFstYear);
												 
												logger.info("eval first year ******************************** "+d1);
												Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"")).trim());  
								    			year1[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioFirstYear("00");
											e.printStackTrace();
										}
										
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"").equalsIgnoreCase(""))
								    		{
											 d1=new BigDecimal(engine.eval(ratioExprSecYear)+"");// (BigDecimal) engine.eval(ratioExprSecYear);
											logger.info("eval second year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"")).trim());  
											year2[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioSecondYear("00");
											e.printStackTrace();
										}
						    			
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"").equalsIgnoreCase(""))
								    		{
											 d1= new BigDecimal(engine.eval(ratioExprThrdYear)+""); //(Double) engine.eval(ratioExprThrdYear);
											logger.info("eval third year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"")).trim());  
											year3[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioThirdYear("0");
											e.printStackTrace();
										}
									
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"").equalsIgnoreCase(""))
								    		{
											 d1= new BigDecimal(engine.eval(ratioExprForthYear)+"");// (Double) engine.eval(ratioExprForthYear);
											logger.info("eval fourth year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"")).trim());  
											year4[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
//											ratioVo.setRatioFourthYear("0");
											e.printStackTrace();
										}
									
										try{
											if(!CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"").equalsIgnoreCase(""))
								    		{
											 d1=new BigDecimal(engine.eval(ratioExprFifthYear)+"");// (Double) engine.eval(ratioExprFifthYear);
											logger.info("eval fifth year ******************************** "+d1);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"")).trim());  
											year5[z]=myFormatter.format(reconNum);
								    		}
										}
										catch (Exception e) {
		//									ratioVo.setRatioFifthYear("0");
											e.printStackTrace();
										}
						    			
								}
								z++;
								
								

					 }
								}else{
									year1[z] =year1[z];
								    year2[z] = year2[z];
								    year3[z] =year3[z]; 
								    year4[z] = year4[z];
								    year5[z]=year5[z];
								    z++;
									
								}
					 }
				 }
			    //sachin work end

		     if(pCode1.length >0 )
		     {
		    for(int i=0,k=0;i<pCode1.length;i++,k++)
		    {
		    	for(int j=0;j<5;j++)
		    	{
		    		
		    		if(j==0)
		    		{
		    			pCodeWithYear[i][j]=year1[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==1)
		    		{
		    			pCodeWithYear[i][j]=year2[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==2)
		    		{
		    			pCodeWithYear[i][j]=year3[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==3)
		    		{
		    			pCodeWithYear[i][j]=year4[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    		else if(j==4)
		    		{
		    			pCodeWithYear[i][j]=year5[k];
		    			//logger.info("two d value ...................... "+pCodeWithYear[i][j]);
		    		}
		    	}
		    }
		   
		    int currBusinessDateYear =Integer.parseInt(StringEscapeUtils.escapeSql(vo.getCurrBusinessDateYear()));
		    try{
		    	int count=0;
		    	String paramValue[]=null;
		    	String paramVal="";
		    	for(int n=0;n<pCode1.length; n++)
	    		{
	    			String query1=  "SELECT distinct PARAM_CODE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(pCode1[n])+"' ";
			    	logger.info("query1--saveProfitandBalanceWithMinus->"+query1);
			    	String paramCode=ConnectionDAO.singleReturn(query1);
			     	logger.info("status1--saveProfitandBalanceWithMinus-->"+paramCode);
			     	
			    	if(! CommonFunction.checkNull(paramCode).equals("")){
				     	if(paramVal.equals("")){	
				     		paramVal=paramCode;
				     	}else{
				     		paramVal=paramVal+","+paramCode;
				     	}
				     	count++;
				     	paramValue=paramVal.split(",");
				     	logger.info("paramValue-length------>"+paramValue.length);
				     	logger.info("paramValue[n]------>"+paramValue[n]);
				     	logger.info("count-"+count);
				     	}
	    		}
			    	if(! CommonFunction.checkNull(paramValue).equals("")){
			    		logger.info("paramValue-length------>"+paramValue.length);
		    		  if(paramValue.length>0){
		    				logger.info("paramValue-length------>"+paramValue.length);
				    	for(int m=0;m<count;m++){
				    		 insertPrepStmtObject = new PrepStmtObject();
				    		String query = "DELETE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='"+CommonFunction.checkNull(vo.getSourceType())+"' and  PARAM_CODE='"+CommonFunction.checkNull(paramValue[m])+"' ";
				    		logger.info("query--saveProfitandBalanceWithMinus-->"+query);
				    		insertPrepStmtObject.setSql(query);
				    		qryList.add(insertPrepStmtObject);
				    	}
					
		    		} 
			    	}
		    if(!CommonFunction.checkNull(vo.getpCode1()).equalsIgnoreCase(""))
	         {
		    	maxId = ConnectionDAO.singleReturn("select max(FINANCIAL_DATA_ID) from cr_financial_data_dtl  WITH (ROWLOCK) ");
		    	
	    		
	    		for(int k=0;k<pCode1.length; k++)
	    		{
	    			for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
	    			{
	    			 insertPrepStmtObject = new PrepStmtObject();
	    			 bufInsSql =	new StringBuffer();
	    			
	    			
	    			bufInsSql.append("insert into cr_financial_data_dtl(DEAL_ID,SOURCE_TYPE,PARAM_CODE,FINANCIAL_YEAR,PARAM_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
	    			bufInsSql.append(" values ( ");
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(" ?," );
	    			bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
	    			bufInsSql.append("  )" );
	    			
	    		  	logger.info("financial deal id *****saveProfitandBalanceWithMinus*********** "+vo.getLbxDealNo());
	    		  	
	    			if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
	    			
	    			if((CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getSourceType()).trim());
	    			
	    			if((CommonFunction.checkNull(pCode1[k])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((pCode1[k]).trim());
	    				
	    			if((CommonFunction.checkNull(analysisYear[pVal+1])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((analysisYear[pVal+1]).trim());
	    			
	    			if((CommonFunction.checkNull(pCodeWithYear[k][pVal])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addString("0.00");
					else
						try {
							insertPrepStmtObject.addString(myFormatter.parse((pCodeWithYear[k][pVal].trim())).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				
	    		
	    			insertPrepStmtObject.addString("P");//,REC_STATUS

	    			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getUserId()).trim());

	    			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getBusinessDate()));
	    			
	    				insertPrepStmtObject.setSql(bufInsSql.toString());
	    				logger.info("IN saveProfitandBalanceWithMinus() insert query ### "+insertPrepStmtObject.printQuery());
	    				
	    				
	    				qryList.add(insertPrepStmtObject);
	    			
	    		 }
	           }
	         }

			//  logger.info("IN saveBalanceSheet() insert query: "+qryList.get(0));
		    
			  
			  //status=false;
		    logger.info("IN saveProfitandBalanceWithMinus()  qryList: "+qryList);
					if(qryList.size()>0){
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					if(status)
					{									
						ids = ConnectionDAO.singleReturn("SELECT STUFF((SELECT ',' + convert(varchar(20),FINANCIAL_DATA_ID) FROM cr_financial_data_dtl where FINANCIAL_DATA_ID>'"+maxId+"' FOR XML PATH ('')), 1, 1, '')" );
					}}
				} catch (Exception e) {
					e.printStackTrace();
				}
			    logger.info("In saveProfitandBalanceWithMinus............."+status);
		    }
		    
		 
		    	idsinsert=null;
		    	maxId=null;
		 
			return ids;
		
	}
	//method added by sachin 
	public boolean insertCorRatioData(ArrayList ratioAnalysisList,String dealId, String userID, String businessDate, String[] year) 
	{
		boolean status=false;			
		try
		{
			ArrayList list =new ArrayList();
			String deleteQuery="delete from cr_ratio_analysis_dtl where deal_id='"+CommonFunction.checkNull(dealId).trim()+"'";
			list.add(deleteQuery);
			boolean delete=ConnectionDAO.sqlInsUpdDelete(list);
			ArrayList qryList=new ArrayList();
			PrepStmtObject insertPrepStmtObject=null;
		    StringBuffer bufInsSql=null;
			for(int i=0 ;i<ratioAnalysisList.size();i++)
			{	
				for(int k=0; k<5;k++)
			    {
					FinancialAnalysisVo vo=(FinancialAnalysisVo)ratioAnalysisList.get(i);
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql =	new StringBuffer();
					
			    	String ratioValue[]=new String[5];
			    	ratioValue[0]=vo.getRatioFirstYear();
			    	ratioValue[1]=vo.getRatioSecondYear();
			    	ratioValue[2]=vo.getRatioThirdYear();
			    	ratioValue[3]=vo.getRatioFourthYear();
			    	ratioValue[4]=vo.getRatioFifthYear();
			    				    	
			    	bufInsSql.append("insert into cr_ratio_analysis_dtl(DEAL_ID,FINANCIAL_YEAR,RATIO_CODE,RATIO_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
			    	bufInsSql.append(" values ( ");
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			    	bufInsSql.append("  )" );
			    	
			    		
			    	if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString(dealId.trim());
			
			    	if((CommonFunction.checkNull(year[k+1])).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString((year[k+1]).trim());
		
			    	if((CommonFunction.checkNull(vo.getRatioParamCode())).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString((vo.getRatioParamCode()).trim());
				
			    	if((CommonFunction.checkNull(ratioValue[k])).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addString("0.00");
			    	else
						try {
							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(ratioValue[k])).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	insertPrepStmtObject.addString("P");//,REC_STATUS

			    	if((CommonFunction.checkNull(userID)).trim().equals(""))//,MAKER_ID
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString((userID).trim());

			    	if((CommonFunction.checkNull(businessDate)).equalsIgnoreCase(""))//,MAKER_DATE
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString((businessDate));
			
			    	insertPrepStmtObject.setSql(bufInsSql.toString());
			    	logger.info("IN saveBalanceSheet() insert query ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				 }
			}
			status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
		}
		catch (Exception e) 
		{e.printStackTrace();}
		logger.info("Status for ratio analysis insert   :    "+status);
		return status;
	}
	@Override
	public boolean insertIndRatioData(ArrayList ratioAnalysisList,String dealId, String userID, String businessDate) {
		
		
		boolean status=false;
				
		try
		{

			ArrayList list =new ArrayList();
			String deleteQuery="delete from cr_ratio_analysis_dtl where deal_id='"+CommonFunction.checkNull(dealId).trim()+"'";
			list.add(deleteQuery);
			boolean delete=ConnectionDAO.sqlInsUpdDelete(list);
			ArrayList qryList=new ArrayList();
			PrepStmtObject insertPrepStmtObject=null;
		    StringBuffer bufInsSql=null;
		    StringBuilder proposedEmiQry=new StringBuilder();
		    proposedEmiQry.append("select ");
		    proposedEmiQry.append("TOP 1 ");
		    proposedEmiQry.append("ISNULL(INSTL_AMOUNT,0) AS INSTL_AMOUNT from cr_deal_repaysch_dtl where INSTL_NO=1 AND REC_STATUS='A' and  deal_id='"+dealId+"' order by REPAYSCH_ID ");
		   
		    logger.info("proposedEmiQry......qryIncome"+proposedEmiQry.toString());
		    String proposedEmi=ConnectionDAO.singleReturn(proposedEmiQry.toString());
		    BigDecimal proposedEmiBig=new BigDecimal("0.0");
		    if(!CommonFunction.checkNull(proposedEmi).equalsIgnoreCase(""))
		    {
		    	   proposedEmiBig= new BigDecimal(proposedEmi);
		    }
		  
		    logger.info("proposedEmi:----"+proposedEmi);
		    
		    StringBuilder qryIncome=new StringBuilder();
		    qryIncome.append("select ");
		    qryIncome.append("ISNULL(sum(FINANCIAL_MONTH),0) AS FINANCIAL_MONTH from cr_financial_data_dtl where deal_id='"+CommonFunction.checkNull(dealId).trim()+"'");
		    logger.info("insertIndRatioData......qryIncome"+qryIncome.toString());
		    String incomeStr=ConnectionDAO.singleReturn(qryIncome.toString());
		    logger.info("incomeStr:----"+incomeStr);
		    if(incomeStr.equalsIgnoreCase(""))
		    	incomeStr="0.0";
		    StringBuilder qryObligation=new StringBuilder();
		    qryObligation.append("select ");
		    qryObligation.append("ISNULL(sum(EMI),0) AS EMI  from cr_obligation_analysis_dtl where deal_id='"+CommonFunction.checkNull(dealId).trim()+"' and OBLIGATION_TYPE='LOAN'" );
		    logger.info("insertIndRatioData.......qryObligation"+qryObligation.toString());
		    String obligationStr=ConnectionDAO.singleReturn(qryObligation.toString());
		    if(obligationStr.equalsIgnoreCase(""))
		    	obligationStr="0.0";
		    logger.info("obligationStr:----"+obligationStr);
		    BigDecimal incomeBig= new BigDecimal(incomeStr);
		    logger.info("incomeBig:----"+incomeBig);
		    BigDecimal obligationBig= new BigDecimal(obligationStr);
		    logger.info("obligationBig:----"+obligationBig);
		   // BigDecimal foir=(obligationBig.divide(incomeBig,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"));
		    BigDecimal foir=(proposedEmiBig.add(obligationBig)).divide(incomeBig,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
		    logger.info("foir:----"+foir);
		    //BigDecimal irr=(foir.multiply(incomeBig)).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP);
		    BigDecimal subt=incomeBig.subtract(obligationBig);
		    logger.info("income-obligationBig:----"+subt);
		    BigDecimal irr=(proposedEmiBig.divide(subt,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"));
		    logger.info("irr:----"+irr);
		    
		    insertPrepStmtObject = new PrepStmtObject();
			bufInsSql =	new StringBuffer();
			
		    bufInsSql.append("insert into cr_ratio_analysis_dtl(DEAL_ID,RATIO_CODE,RATIO_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
	    	bufInsSql.append(" values ( ");
	    	bufInsSql.append(" ?," );
	    	bufInsSql.append(" ?," );
	    	bufInsSql.append(" ?," );
	    	bufInsSql.append(" ?," );
	    	bufInsSql.append(" ?," );
	    	bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
	    	bufInsSql.append("  )" );
	    	
	    	logger.info("insertIndRatioData.........bufInsSql"+bufInsSql.toString());
		    
	    	if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString(dealId.trim());
	
	    	insertPrepStmtObject.addString("FOIR");
		
    		if(CommonFunction.checkNull(foir).trim().equalsIgnoreCase(""))
	    		insertPrepStmtObject.addNull();
	    	else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(foir.toString()).toString());
			
	    	
	    	insertPrepStmtObject.addString("P");//,REC_STATUS

	    	if((CommonFunction.checkNull(userID)).trim().equals(""))//,MAKER_ID
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString((userID).trim());

	    	if((CommonFunction.checkNull(businessDate)).equalsIgnoreCase(""))//,MAKER_DATE
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString((businessDate));
	
	    	insertPrepStmtObject.setSql(bufInsSql.toString());
	    	logger.info("IN saveBalanceSheet() insert query ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			//For IRR
			insertPrepStmtObject = new PrepStmtObject();
			
			if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString(dealId.trim());
	
	    	insertPrepStmtObject.addString("IIR");
		
    		if(CommonFunction.checkNull(irr).trim().equalsIgnoreCase(""))
	    		insertPrepStmtObject.addNull();
	    	else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(irr.toString()).toString());
			
	    	
	    	insertPrepStmtObject.addString("P");//,REC_STATUS

	    	if((CommonFunction.checkNull(userID)).trim().equals(""))//,MAKER_ID
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString((userID).trim());

	    	if((CommonFunction.checkNull(businessDate)).equalsIgnoreCase(""))//,MAKER_DATE
	    		insertPrepStmtObject.addNull();
	    	else
	    		insertPrepStmtObject.addString((businessDate));
	
	    	insertPrepStmtObject.setSql(bufInsSql.toString());
	    	logger.info("IN saveBalanceSheet() insert query ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
			/*for(int i=0 ;i<ratioAnalysisList.size();i++)
			{	
				for(int k=0; k<1;k++)
			    {
					FinancialAnalysisVo vo=(FinancialAnalysisVo)ratioAnalysisList.get(i);
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql =	new StringBuffer();
					
			    	String ratioValue[]=new String[5];
			    	ratioValue[0]=vo.getRatioFirstYear();
			    	ratioValue[1]=vo.getRatioSecondYear();
			    	ratioValue[2]=vo.getRatioThirdYear();
			    	ratioValue[3]=vo.getRatioFourthYear();
			    	ratioValue[4]=vo.getRatioFifthYear();
			    				    	
			    	bufInsSql.append("insert into cr_ratio_analysis_dtl(DEAL_ID,FINANCIAL_MONTH,RATIO_CODE,RATIO_VALUE,REC_STATUS,MAKER_ID,MAKER_DATE)");
			    	bufInsSql.append(" values ( ");
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" ?," );
			    	bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)" );
			    	bufInsSql.append("  )" );
			    	
			    		
			    	if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString(dealId.trim());
			
			    	if((CommonFunction.checkNull(vo.getMonthValue())).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addNull();
			    	else
						try {
							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getMonthValue())).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		
			    	if((CommonFunction.checkNull(vo.getRatioParamCode())).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString((vo.getRatioParamCode()).trim());
				
			    	if((CommonFunction.checkNull(vo.getYearValue())).trim().equalsIgnoreCase(""))
			    		insertPrepStmtObject.addString("0.00");
			    	else
						try {
							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getYearValue())).toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	
			    	insertPrepStmtObject.addString("P");//,REC_STATUS

			    	if((CommonFunction.checkNull(userID)).trim().equals(""))//,MAKER_ID
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString((userID).trim());

			    	if((CommonFunction.checkNull(businessDate)).equalsIgnoreCase(""))//,MAKER_DATE
			    		insertPrepStmtObject.addNull();
			    	else
			    		insertPrepStmtObject.addString((businessDate));
			
			    	insertPrepStmtObject.setSql(bufInsSql.toString());
			    	logger.info("IN saveBalanceSheet() insert query ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				 }
			}*/
			status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
		}
		catch (Exception e) 
		{e.printStackTrace();}
		logger.info("Status for ratio analysis insert   :    "+status);
		return status;

	}
//	end by sachin
//Added by Arun Starts here
	
	public ArrayList<FinancialAnalysisVo> getIndRAtioDataList(FinancialAnalysisVo vo){
		ArrayList<FinancialAnalysisVo> list=new ArrayList<FinancialAnalysisVo>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{
			
		query.append("select RATIO_CODE,");
		query.append("ISNULL(RATIO_VALUE,0),(select ");
		query.append("TOP 1 ");
		query.append("ISNULL(INSTL_AMOUNT,0) from cr_deal_repaysch_dtl where deal_id='"+vo.getLbxDealNo()+"' order by REPAYSCH_ID");
		query.append(") INSTL_AMOUNT from cr_ratio_analysis_dtl where deal_id='"+vo.getLbxDealNo()+"' order by RATIO_CODE");
		
		logger.info("query : "+query.toString());
		ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisVo nvo = new FinancialAnalysisVo();
				nvo.setRatioName((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
				if(CommonFunction.checkNull(subParamDetail.get(1)).trim().equalsIgnoreCase(""))
				{
					nvo.setYearValue("0.00");
				}
				else
				{
					nvo.setYearValue(myFormatter.format(myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(1))).trim())));
				}
				//nvo.setYearValue(myFormatter.format(myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(1))).trim())));
				if(CommonFunction.checkNull(subParamDetail.get(2)).trim().equalsIgnoreCase(""))
				{
					nvo.setEmi("0.00");
				}
				else
				{
					nvo.setEmi(myFormatter.format(myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(2))).trim())));
				}
                
				list.add(nvo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	//added by aryn ends here
	//Code by arun for default obligation and  totAtL DR Cr  in other Starts here
	@Override
	public CommonDealVo getPremValueForOtherPerm(CommonDealVo vo) {
		
		ArrayList list=new ArrayList();
		String nunm="0.00";
		CommonDealVo nVo=new CommonDealVo();
		try{
			
			StringBuilder query=new StringBuilder();
			query.append("select ");
			query.append("ISNULL(SUM(ISNULL(OUTSTANDING_AMOUNT,0)),0)  FROM cr_obligation_analysis_dtl WHERE DEAL_ID='"+vo.getDealNo()+"' group by OUTSTANDING_AMOUNT");
		    String obligation=CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
		    if(!obligation.equalsIgnoreCase("")){
		    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(obligation)));  
	    		nunm=myFormatter.format(reconNum);
	    		reconNum=null;
	    		nVo.setObligationPram(nunm);
	    		nunm=null;
		    }else{
		    	nVo.setObligationPram("0.00");
		    }
		    query=new StringBuilder();
		    query.append("Select sum(ISNULL(TOTAL_DR,0)),sum(ISNULL(TOTAL_CR,0)) from cr_bank_analysis_dtl WHERE DEAL_ID='"+vo.getDealNo()+"' group by TOTAL_DR,TOTAL_CR ");
		    
		    logger.info("query for  DR CR:------"+query.toString());
		    ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			
			for(int i=0;i<paramDetail.size();i++){

				ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
				if(subParamDetail.size()>0)
				{
					if(!CommonFunction.checkNull(subParamDetail.get(0)).equalsIgnoreCase("")){
						Number totalDr =myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(0))).trim());  
						String totalDrPram=myFormatter.format(totalDr);
						totalDr=null;
			    		nVo.setTotalDrPram(totalDrPram);	
			    		totalDrPram=null;
					}else{
						nVo.setTotalDrPram("0.00");		
					}
					if(!CommonFunction.checkNull(subParamDetail.get(1)).equalsIgnoreCase("")){
						Number totalCr =myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(1))).trim());  
						String totalCrPram=myFormatter.format(totalCr);
						totalCr=null;
			    		nVo.setTotalCrPram(totalCrPram);
			    		totalCrPram=null;
						
					}else{
						nVo.setTotalCrPram("0.00");	
					}
		    		
				}
				}
		   
		}catch(Exception e){
		e.printStackTrace();	
		}
		return nVo;
	}
	//Code by arun for default obligation totAtL DR Cr  in other Ends here
	
	//SANJOG CODE START FOR Financial Analysis
	public int getYearForFinancialAnalysis() {
		ArrayList list = new ArrayList();
		int count = 0;
		try {
			logger.info("In getApprovalfromPmst.............inside ejb server file............Dao Impl");
			String query = "select PARAMETER_VALUE from parameter_mst where parameter_key = 'FINANCIAL_ANALYSIS_SET_YEAR'";
			FinancialAnalysisVo vO = null;
			String countStr = ConnectionDAO.singleReturn(query);
			count = Integer.parseInt(countStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public FinancialAnalysisVo getParamCode(String pcode,FinancialAnalysisVo vo1) {
		// TODO Auto-generated method stub
		try{
		ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,SUB_TYPE,CASE SUB_TYPE WHEN 'A' THEN 'ASSET' WHEN 'L' THEN 'LIABILITY' END AS SUB_TYPE_DESC,ISNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA,NEGATIVE_ALLOWED FROM cr_financial_param WHERE PARAM_CODE='"+pcode+"'");
		for(int t=0;t<pval.size();t++){
			ArrayList subPval=(ArrayList)pval.get(t);
			if(subPval.size()>0)
			{
				vo1.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
				vo1.setSubType((CommonFunction.checkNull(subPval.get(1))).trim());
				vo1.setSubTypeDesc((CommonFunction.checkNull(subPval.get(2))).trim());
				vo1.setAutoCalculated((CommonFunction.checkNull(subPval.get(3))).trim());
				vo1.setFinancialFormula((CommonFunction.checkNull(subPval.get(4))).trim());
				vo1.setNegativeAllow((CommonFunction.checkNull(subPval.get(5))).trim());
                
			}
		}
		}
		 catch (Exception e) {
				e.printStackTrace();
			}
		return vo1;
	}
	@Override
	public FinancialAnalysisParamVo getNegativeParamCode(String pcode,FinancialAnalysisParamVo fvo1) {
		// TODO Auto-generated method stub
		try{
		 ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,SUB_TYPE,CASE SUB_TYPE WHEN 'A' THEN 'ASSET' WHEN 'L' THEN 'LIABILITY' END AS SUB_TYPE_DESC,ISNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA FROM cr_financial_param WHERE PARAM_CODE='"+pcode+"'");
			 for(int t=0;t<pval.size();t++){

					ArrayList subPval=(ArrayList)pval.get(t);
					if(subPval.size()>0)
					{
						fvo1.setNegativeparamName((CommonFunction.checkNull(subPval.get(0))).trim());
						fvo1.setSubType((CommonFunction.checkNull(subPval.get(1))).trim());
						fvo1.setSubTypeDesc((CommonFunction.checkNull(subPval.get(2))).trim());
						fvo1.setAutoCalculated((CommonFunction.checkNull(subPval.get(3))).trim());
						fvo1.setFinancialFormula((CommonFunction.checkNull(subPval.get(4))).trim());
		                
					}
				}
		}
	
	catch (Exception e) {
		e.printStackTrace();
	}
	return fvo1;
}
	@Override
	public FinancialAnalysisParamVo getBalanceSheetNegativeParamCode(String pcode, FinancialAnalysisParamVo fvo1) {
		try{
		ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,ISNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA FROM cr_financial_param WHERE PARAM_CODE='"+pcode+"'");
		for(int t=0;t<pval.size();t++){
			ArrayList subPval=(ArrayList)pval.get(t);
			if(subPval.size()>0)
			{
				fvo1.setNegativeparamName((CommonFunction.checkNull(subPval.get(0))).trim());
				fvo1.setAutoCalculated((CommonFunction.checkNull(subPval.get(1))).trim());
				fvo1.setFinancialFormula((CommonFunction.checkNull(subPval.get(2))).trim());
                
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return fvo1;
	}
	@Override
	public FinancialAnalysisVo getBalanceSheetParamCode(String pcode,FinancialAnalysisVo vo1) {
		try{ 
		ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,ISNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA,NEGATIVE_ALLOWED FROM cr_financial_param WHERE PARAM_CODE='"+pcode+"'");
			for(int t=0;t<pval.size();t++){
				ArrayList subPval=(ArrayList)pval.get(t);
				if(subPval.size()>0)
				{
					vo1.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
					vo1.setAutoCalculated((CommonFunction.checkNull(subPval.get(1))).trim());
					vo1.setFinancialFormula((CommonFunction.checkNull(subPval.get(2))).trim());
					vo1.setNegativeAllow((CommonFunction.checkNull(subPval.get(3))).trim());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return vo1;
	}
	//method added by neeraj
	public ArrayList getUploadedData(String dealId, String userId,String sourceType) 
	{
		logger.info("In getScheme()");
		ArrayList reportlist = new ArrayList();
		String[] pCode;
		String[] year1;
		String[] year2;
		String[] year3;
		String[] year4;
		String[] year5;
		ArrayList<String>tparameter=new ArrayList<String>();
		ArrayList<String>tyear1=new ArrayList<String>();
		ArrayList<String>tyear2=new ArrayList<String>();
		ArrayList<String>tyear3=new ArrayList<String>();
		ArrayList<String>tyear4=new ArrayList<String>();
		ArrayList<String>tyear5=new ArrayList<String>();
		ReportsForm reportName = null;
		String query=null;
		ArrayList totalList =new ArrayList(); 
		ArrayList result=new ArrayList();
		
		try 
		{		
			 query ="SELECT PARAMETER,YEAR1,YEAR2,YEAR3,YEAR4,YEAR5 " +
					"from cr_financial_analysis_upload where DEAL_ID='"+CommonFunction.checkNull(dealId).trim()+"' " +
					"and SOURCE_TYPE='"+CommonFunction.checkNull(sourceType).trim()+"' and MAKER_ID='"+CommonFunction.checkNull(userId).trim()+"';";		
			logger.info("getUploadedData  query  :   "+query);		
			totalList = ConnectionDAO.sqlSelect(query.toString());
			for (int i = 0; i < totalList.size(); i++) 
			{
				ArrayList<String> data = (ArrayList) totalList.get(i);
				tparameter.add(data.get(0));
				tyear1.add(data.get(1));
				tyear2.add(data.get(2));
				tyear3.add(data.get(3));
				tyear4.add(data.get(4));
				tyear5.add(data.get(5));
				data.clear();
				data=null;
			}
			pCode=(String[])tparameter.toArray(new String[0]);
			year1=(String[])tyear1.toArray(new String[0]);
			year2=(String[])tyear2.toArray(new String[0]);
			year3=(String[])tyear3.toArray(new String[0]);
			year4=(String[])tyear4.toArray(new String[0]);
			year5=(String[])tyear5.toArray(new String[0]);
			
			result.add(pCode);
			result.add(year1);
			result.add(year2);
			result.add(year3);
			result.add(year4);
			result.add(year5);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			pCode=null;
			year1=null;
			year2=null;
			year3=null;
			year4=null;
			year5=null;
			tparameter.clear();
			tparameter=null;
			tyear1.clear();
			tyear1=null;
			tyear2.clear();
			tyear2=null;
			tyear3.clear();
			tyear3=null;
			tyear4.clear();
			tyear4=null;
			tyear5.clear();
			tyear5=null;
			query=null;
			totalList.clear();
			totalList=null;
		}
		return result;
	}
	//method added by sachin
	public String benchBranchRatioList(String ratioCode, String dealId) 
	{
		logger.info("In MSSQLFinancialDaoImpl............................benchBranchRatioList");
		String result="";
		StringBuilder query=new StringBuilder();
		try
		{
				query.append("select benchmark_ratio from cr_deal_dtl A ");
				query.append(" JOIN CR_DEAL_CUSTOMER_M B ON(a.deal_customer_id=b.CUSTOMER_ID) ");
				query.append(" JOIN cr_benchmark_ratio_m C ON(b.CUSTOMER_INDUSTRY=C.BENCHMARK_INDUSTRY_ID AND C.BENCHMARK_RATIO_CODE='"+ratioCode+"' ) ");
				query.append(" WHERE DEAL_ID='"+dealId+"' ");
		    	logger.info("query--benchBranchRatioList->"+query.toString());
		    	result=ConnectionDAO.singleReturn(query.toString());
		    	logger.info("result"+result);
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	@Override
	public ArrayList getdealAllParamDeatilsForRatio(String dealId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList getdealAllParamDeatils(String sourceType, String caseId,
			String customerId, String recStatus, int cy, int py1, int py2) {
		// TODO Auto-generated method stub
		return null;
	}
}
