package com.connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Iterator;


import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class ConnectionDAOforEJB {
	
	private static final Logger logger = Logger.getLogger(ConnectionDAOforEJB.class.getName());
	private static int count =0;
	private ConnectionDAOforEJB()
	{
	
	}
	
	public static Connection getConnection() {
		Connection connection = null;
		String DATASOURCE_CONTEXT =null;
		DataSource ds=null;
		try {

			
 // --------------------- For Jboss Application Server 4.x------------------------	

			DATASOURCE_CONTEXT = "java:FinCraftDB1";
			
			
			
 // --------------------- For Jboss Application Server 7.x------------------------	
			
			//String DATASOURCE_CONTEXT = "java:jboss/datasources/FinCraftDB1";

			//Properties properties = new Properties();
			//properties.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
			//properties.setProperty(InitialContext.PROVIDER_URL,"jnp://localhost:1099");

			Context ctx = new InitialContext(); 

//--------------------- For Jboss Application Server ------------------------
 		
// -------------------- For Tomcat server------------------------------------
			
			//String DATASOURCE_CONTEXT = "java:comp/env/jdbc/FinCraftDB1";
			//Context ctx = new InitialContext();  
			
// -------------------- For Tomcat server------------------------------------
			
			
			 ds = (DataSource)ctx.lookup(DATASOURCE_CONTEXT);
			
			if (ds != null)
			{
				count++;
				connection = ds.getConnection();
		    }
		    else
		    {
		       logger.info("Failed to lookup datasource.");
		    }

		} catch (Exception e) {
			logger.info("Error occur in ConnectionDAO class-getConnection method:--"+e.getMessage());
			e=null;

		}
		finally
		{
			DATASOURCE_CONTEXT=null;
			ds=null;
		}
	
		return connection;
	}

	public static void closeConnection(Connection connection, Statement statement) {
		
		if (statement != null) {
			try {
				statement.close();
				statement=null;
			} catch (SQLException e) {
				
				// TODO Auto-generated catch block
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}

		if (connection != null) {
			try {
				connection.close();
				count--;
				connection=null;
				//logger.info("closeConnection(Connection connection, Statement statement) count close:::::::::: "+count);
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
	}
	
	public static void closeConnection(Connection connection, Statement statement,ResultSet resultset) {
		if (resultset != null) {
			try {
				resultset.close();
				resultset=null;
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}

		if (statement != null) {
			try {
				statement.close();
				statement=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}

		if (connection != null) {
			try {
				connection.close();
				count--;
				connection=null;
				//logger.info("closeConnection(Connection connection, Statement statement,ResultSet resultset) count close:::::::::: "+count);
				
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				e=null;
			}
		}
	}


	    private static void releaseStatement(Statement stmt)
	    {
	      try
	      {
	          if (stmt != null)
	          {
	              stmt.close();
	              stmt=null;
	              
	          }
	      }catch (Exception e)
	      {
	          logger.error("Exception while closing Statement" + e);
	          e=null;
	      }
	    }

	    private static void releaseResultSet(ResultSet rs)
	    {
	        try
	        {
	            if (rs != null)
	            {
	                rs.close();
	                rs=null;
	            }
	        }catch (Exception e)
	        {
	            logger.error("Exception while closing resultset" + e);
	            e=null;
	        }
	    }

	    private static void releaseConnection(Connection con1) {
	        try {
	            if (con1 != null) {
	                con1.close();
	                count--;
	                con1=null;
	                //logger.info("In releaseConnection(Connection con1) count close:::::::::: "+count);
	            }
	        } catch (Exception e) {
	            logger.error("In method releaseConnection():  Exception while closing resources :" + e.getMessage());
	            e=null;
	        }
	    }
	    
	    public static void closeConnectionPmt(Connection connection, PreparedStatement pmtStatment,ResultSet resultset) {
			if (resultset != null) {
				try {
					resultset.close();
					resultset=null;
				} catch (SQLException e) {
					logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
					e=null;
				}
			}

			if (pmtStatment != null) {
				try {
					pmtStatment.close();
					pmtStatment=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
					e=null;
				}
			}

			if (connection != null) {
				try {
					connection.close();
					count--;
					connection=null;
					//logger.info("closeConnection(Connection connection, Statement statement,ResultSet resultset) count close:::::::::: "+count);
				} catch (SQLException e) {
					logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
					e=null;
				}
			}
		}

		public static void closeConnectionPmt(Connection connection, PreparedStatement pmtStatment) {
			
			if (pmtStatment != null) {
				try {
					pmtStatment.close();
					pmtStatment=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
					e=null;
				}
			}

			if (connection != null) {
				try {
					connection.close();
					count--;
					connection=null;
				//	logger.info("closeConnection(Connection connection, Statement statement,ResultSet resultset) count close:::::::::: "+count);
				} catch (SQLException e) {
					logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
				}
			}
		}


	    public static String singleReturn(String sqlQuery) {
	        Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        String strReturn = null;
	        
	        try {
	            con = getConnection();

	           // con.setAutoCommit(false);
	            stmt = con.createStatement();
	         
	            rs = stmt.executeQuery(sqlQuery);
                 
	            if (rs.next()) {
	                strReturn = CommonFunction.checkNull(rs.getString(1));
	               
	            }
	        } catch (Exception e) {
	        	
	            logger.error("In method singleReturn() :" + e.getMessage());
	            e=null;
	          //  throw new EJBException(e.getMessage());
	        } finally {
	        	closeConnection(con,stmt,rs);
	        	sqlQuery=null;
	         
	        }

	        return strReturn;
	    }
	    
	    public static boolean checkStatus(String sqlQuery) {
	        Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        boolean strReturn = false;

	        try {
	            con = getConnection();
	            //con.setAutoCommit(false);
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(sqlQuery);

	            if (!CommonFunction.checkNull(rs).equalsIgnoreCase("") && rs.next()) {
	            	
	            	if(!CommonFunction.checkNull(rs.getString(1)).equalsIgnoreCase(""))
	            	{
	            		strReturn=true;
	            	}
	            	
	            }
	        } catch (Exception e) {
	        	
	            logger.error("In method checkStatus() :" + e.getMessage());
	            e=null;
	          //  throw new EJBException(e.getMessage());
	        } finally {
	        	closeConnection(con,stmt,rs);
	        	sqlQuery=null;
	        }

	        return strReturn;
	    }
	    
	    public static boolean checkStatusPrepStmt(PrepStmtObject prepStmtObject) throws java.sql.SQLException,java.rmi.RemoteException{
	        Connection con = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;
	        boolean strReturn = false;
	        String sql=null;
	        try {
	            con = getConnection();

	            sql = prepStmtObject.getSql();            

	            prepStmt = con.prepareStatement(sql);
	            prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);
	            rs = prepStmt.executeQuery();
	            if (!CommonFunction.checkNull(rs).equalsIgnoreCase("") && rs.next()) {
	            
	            	if(!CommonFunction.checkNull(rs.getString(1)).equalsIgnoreCase(""))
	            	{
	            		strReturn=true;
	            	}
	            }
	        } catch (Exception e) {
	            logger.error("In method singleReturnPrepStmt() :" + e.getMessage());
	            e=null;
	           // throw new EJBException(e.getMessage());
	        } finally {
	        	closeConnectionPmt(con,prepStmt,rs);
	        	prepStmtObject=null;
	        	sql=null;
	        }

	        return strReturn;
	    }

	     public static boolean sqlInteraction(Collection sql) throws java.sql.SQLException {
	        boolean retVal = false;
	        Connection conn = null;
	        Statement stmt = null;
	        String sqlIt = null;
	        Iterator sqlIterator=null;
	        try {
	            conn = getConnection();

	            sqlIterator = sql.iterator();

	            while (sqlIterator.hasNext()) {
	                stmt = conn.createStatement();

	                sqlIt = sqlIterator.next().toString();

	                if (stmt.executeUpdate(sqlIt) == 0) {
	                     conn.rollback();
	                    throw new SQLException("NO DATA FOUND");
	                } else {
	                    retVal = true;
	                }
	                releaseStatement(stmt);
	            }
	            if(retVal)
	            {
	            	conn.commit();
	            }

	        } catch (SQLException e) {
	            logger.error("In method sqlInteraction() :" + e.getMessage()+ "\n The Failed Query is : "+sqlIt);
	             conn.rollback();
	             e=null;
	          //  throw new SQLException("In method sqlInteraction() :SQL Trap Session : " + e.getMessage());
	        } catch (Exception e) {
	            logger.error("In method sqlInteraction() :" + e.getMessage());
	             conn.rollback();
	             e=null;
	          //  throw new EJBException("In method sqlInteraction() :Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	closeConnection(conn,stmt);
	        	sql=null;
	        	sqlIterator=null;
	        }

	        return retVal;
	    }
	    
	    
	    public static ArrayList sqlSelect(String sql) throws java.sql.SQLException {
	        Connection conn = null;
	        Statement stmt = null;

	        //ArrayList defined to hold the out type parameters
	        ResultSet rs = null;
	        ArrayList vRecords = new ArrayList();
	        ArrayList records=null;
	        try {
	            //Get the Connection
	            conn = getConnection();
	            //conn.setAutoCommit(false);
	            stmt = conn.createStatement();

	            //Executing the Select Query
	            rs = stmt.executeQuery(sql);

	            //getting the no of fields selected
	            int noOfFields = (rs.getMetaData()).getColumnCount();

	            //counter which will be incremented for the no of fields
	            //check whether the records have been returned
	            

	            while (rs.next()) {
	                int counter = 1; //this will restart the count every time from 1

	                //change made ..arraylist to beinitialized within the rs.next()
	                 records = new ArrayList();

	                while (counter <= noOfFields) {
	                    //adding the column values in the arraylist
	                    records.add(rs.getString(counter));
	                    counter++;
	                }

	                //adding the arraylist to the vector
	                vRecords.add(records);
	                
	            } //end of rs.next()
	        } //end of try
	        catch (SQLException e) {
	            throw new SQLException("Error Trap Session : " + e.getMessage());
	        } catch (Exception e) {
	        	 e=null;
	        	//conn.rollback();
	         //   throw new EJBException("Unidentified Trap Session : " + e.getMessage());
	        } finally {

	        	closeConnection(conn,stmt,rs);
	        	sql=null;
	           	records=null;
	        }
	        return vRecords;
	    }
	    
	   /* public static ArrayList sqlSelectPrepStmt(Collection sql) throws java.sql.SQLException {
	    	ArrayList vRecords = new ArrayList();
	        Connection conn = null;
	        Statement stmt = null;
	        String sqlIt = null;
	        ResultSet rs = null;
	        try {
	            //get the database connection
	            conn = getConnection();
	            conn.setAutoCommit(false);
	            //Iterator for the sqls to be executed
	            Iterator sqlIterator = sql.iterator();

	            while (sqlIterator.hasNext()) {
	                stmt = conn.createStatement();

	                sqlIt = sqlIterator.next().toString();

	                stmt.executeQuery(sqlIt);

	                int noOfFields = (rs.getMetaData()).getColumnCount();

		            //counter which will be incremented for the no of fields
		            //check whether the records have been returned
		            

		            while (rs.next()) {
		                int counter = 1; //this will restart the count every time from 1

		                //change made ..arraylist to beinitialized within the rs.next()
		                ArrayList records = new ArrayList();

		                while (counter <= noOfFields) {
		                    //adding the column values in the arraylist
		                    records.add(rs.getString(counter));
		                    counter++;
		                }

		                //adding the arraylist to the vector
		                vRecords.add(records);
		                
		            } //end of rs.next()
		        } }//end of try
		        catch (SQLException e) {
		            throw new SQLException("Error Trap Session : " + e.getMessage());
		        } catch (Exception e) {
		        	conn.rollback();
		         //   throw new EJBException("Unidentified Trap Session : " + e.getMessage());
		        } finally {
		        	conn.commit();
		        	try {
						conn.commit();
						rs.close();
						stmt.close();
						conn.close();
						count--;
						logger.info("In sqlSelectPrepStmt count close:::::::::: "+count);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//		        	releaseResultSet(rs);
//		            releaseStatement(stmt);
//		            releaseConnection(conn);
		        }
		        return vRecords;
		    }
	    */

	  public static boolean sqlBatchProcess(java.util.ArrayList sqlQueries) throws java.sql.SQLException {
	        Connection con = null; //holds the connection object
	        Statement stmt = null; //used to execute the batch queries
	        boolean flag = true;
	        try {
	            con = getConnection(); //fetch the connection object
	            stmt = con.createStatement(); //fetch the statement object

	            /* Add the sql queries into a batch*/
	            int size=sqlQueries.size();
	            for (int i = 0; i <size ; i++)
	                stmt.addBatch(sqlQueries.get(i).toString());

	            /* Execute the batch process*/
	            int[] status = stmt.executeBatch();

	           
              
	            for (int i = 0; i < status.length; i++) {
	                if (status[i] == 0) {
	                    flag = false;

	                    break;
	                }
	            }

	          
	        } catch (Exception e) {
	            logger.error("exception in sqlbactch process*" + e.getMessage());
	            e=null;
	           // throw new EJBException(e.getMessage());
	        }
	        finally
	        {
	        	con.commit();
				closeConnection(con,stmt);
				sqlQueries=null;
	        }
	        if (flag) {
                return true;
            } else {
                 con.rollback();
                return false;
            }
	    }

	    /**
	    *Description: The method is used to fetch data based upon a certain set of records per page.
	    */
	    public static ArrayList sqlSearchResult(String strSqlQuery, int maxRows, int startRows) throws  java.sql.SQLException {
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        ArrayList vRecords = new ArrayList(); //object that holds the data
	        ArrayList records=null;
	        try {
	            /*Get the Connection*/
	            conn = getConnection();

	            /*make a scrollable resulst set object*/
	            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	            /* set the max number of rows to be fetched*/
	            stmt.setMaxRows(maxRows);

	            /*Executing the Select Query*/
	            rs = stmt.executeQuery(strSqlQuery);

	            try {
	                /* set the row number from where the data fetching would be started */
	                rs.absolute(startRows);
	            } catch (Exception e) {
	                logger.error("Exception in sqlSearchSelect: " + e.getMessage());
	                e=null;
	            }

	            /*getting the no of fields selected*/
	            int noOfFields = (rs.getMetaData()).getColumnCount();

	            //counter which will be incremented for the no of fields
	            //check whether the records have been returned            

	            while (rs.next()) {
	                int counter = 1; //this will restart the count every time from 1

	                 records = new ArrayList();

	                while (counter <= noOfFields) {
	                    /*adding the column values in the arraylist*/
	                    records.add(rs.getString(counter));
	                    counter++;
	                }

	                /*adding the arraylist to the result data arraylist*/
	                vRecords.add(records);                
	            } //end of rs.next()

	            rs.close();
	            stmt.close();
	        } //end of try
	        catch (SQLException e) {
	            throw new SQLException("SQl Exception in sqlSearchResult method : " + e.getMessage());
	        } catch (Exception e) {
	        	e=null;
	           // throw new EJBException("Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	strSqlQuery=null;
				closeConnection(conn,stmt,rs);
				records=null;
	        }

	        return vRecords;
	    }

	    /**
	    Description: The method is used to insert/update even if no data found
	    */
	    public static boolean sqlInsUpdDelete(Collection sql) throws java.sql.SQLException {
	        boolean retVal = false;
	        Connection conn = null;
	        Statement stmt = null;
	        String sqlIt = null;
	        Iterator sqlIterator=null;
	        try {
	            //get the database connection
	            conn = getConnection();
	            //conn.setAutoCommit(false);
	            //Iterator for the sqls to be executed
	             sqlIterator = sql.iterator();

	            while (sqlIterator.hasNext()) {
	                stmt = conn.createStatement();

	                sqlIt = sqlIterator.next().toString();

	                stmt.executeUpdate(sqlIt);

	                //Check whether there is any change in the database, if not that means some problem with the query
	                retVal = true;

//	                stmt.close();

	                releaseStatement(stmt);
	            }
	            if(retVal)
	            {
	            	//conn.commit();
	            }

	        } catch (SQLException e) {
	            logger.error("In method sqlInsUpdDelete():" + e.getMessage()+"\n The Failed Query  is :  "+sqlIt);
	            //conn.rollback();
	            throw new SQLException("In method sqlInsUpdDelete(): SQL Trap Session : " + e.getMessage());
	        } catch (Exception e) {
	            logger.error("In method sqlInsUpdDelete():" + e.getMessage());
	            // conn.rollback();
	             e=null;
	            //throw new EJBException("In method sqlInsUpdDelete(): Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	
	        	closeConnection(conn,stmt);
	        	sql=null;
	        	sqlIterator=null;
	        }

	        //Return the boolean value which is true if the transaction was successful otherwise false
	        return retVal;
	    }



	    
	    
	    public static String singleReturnPrepStmt(PrepStmtObject prepStmtObject) throws java.sql.SQLException,java.rmi.RemoteException{
	        Connection con = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;
	        String strReturn = null;
	        String sql=null;
	        try {
	            con = getConnection();

	             sql = prepStmtObject.getSql();            

	            prepStmt = con.prepareStatement(sql);
	            prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);
	            rs = prepStmt.executeQuery();
	            if (rs.next()) {
	                strReturn = rs.getString(1);
	            }
	        } catch (Exception e) {
	            logger.error("In method singleReturnPrepStmt() :" + e.getMessage());
	            e=null;
	           // throw new EJBException(e.getMessage());
	        } finally {
	        	closeConnectionPmt(con,prepStmt,rs);
	        	prepStmtObject=null;
	        	sql=null;
	        }

	        return strReturn;
	    }
	    

	    public static ArrayList sqlSelectPrepStmt(PrepStmtObject prepStmtObject) throws java.sql.SQLException,java.rmi.RemoteException {

	        Connection conn            = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;
	        ArrayList vRecords = new ArrayList();
	        ArrayList records=null;
	        String sql=null;
	        try {
	            //Get the Connection
	            conn = getConnection();
	            conn.setAutoCommit(false);
	             sql       = prepStmtObject.getSql();

	            prepStmt = conn.prepareStatement(sql);
	            prepStmt =  new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);

	            //Executing the Select Query
	            rs = prepStmt.executeQuery();

	            //getting the no of fields selected
	            int noOfFields = (rs.getMetaData()).getColumnCount();

	            //counter which will be incremented for the no of fields
	            //check whether the records have been returned
	            while (rs.next()) {
	                int counter = 1; //this will restart the count every time from 1

	                //change made ..arraylist to beinitialized within the rs.next()
	                 records = new ArrayList();

	                while (counter <= noOfFields) {
	                    //adding the column values in the arraylist
	                    records.add(rs.getString(counter));
	                    counter++;
	                }

	                //adding the arraylist to the vector
	                vRecords.add(records);                
	            } //end of rs.next()
	        } //end of try
	        catch (SQLException e) {
	            throw new SQLException("Error Trap Session in method sqlSelectPrepStmt: " + e.getMessage());
	        } catch (Exception e) {
	        	e=null;
	           // throw new EJBException("Unidentified Trap Session in method sqlSelectPrepStmt: " + e.getMessage());
	        } finally {
	        	closeConnectionPmt(conn,prepStmt,rs);
	        	prepStmtObject=null;
	        	records=null;
	        	sql=null;
	        }
	        return vRecords;
	    }
	    
	    public static boolean sqlBatchProcessPrepStmt(String sql, java.util.ArrayList alValues) throws java.sql.SQLException,java.rmi.RemoteException {

	        Connection conn = null; //holds the connection object
	        PreparedStatement prepStmt = null; //used to execute the batch queries
	        boolean flag = true;
	        ArrayList values=null;
	        PrepStmtObject prepStmtObject=null;
	        try {
	            conn = getConnection(); //fetch the connection object

	            prepStmt  = conn.prepareStatement(sql);
                int size=alValues.size();
	            for (int i = 0; i <size ; i++)
	            {
	                 values = (ArrayList) alValues.get(i);

	                prepStmtObject = new PrepStmtObject(sql,values);
	                prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);
	                prepStmt.addBatch();
	            }
	            /* Execute the batch process*/
	            int[] status = prepStmt.executeBatch();

	         

	            for (int i = 0; i < status.length; i++) {
	                if (status[i] == 0) {
	                    flag = false;

	                    break;
	                }
	            }

	         
	        } catch (Exception e) {
	            logger.error("exception in method sqlBatchProcessPrepStmt():" + e.getMessage());
	            e=null;
	           // throw new EJBException(e.getMessage());
	        }
	        finally
	        {
	        	closeConnectionPmt(conn,prepStmt);
	        	sql=null;
	        	values=null;
	        	alValues=null;
	        	prepStmtObject=null;
	        }
	        if (flag) {
                return true;
            } else {
                 conn.rollback();
                return false;
            }
	    }


	public static ArrayList sqlSearchResultPrepStmt(PrepStmtObject prepStmtObject, int maxRows, int startRows) throws  java.sql.SQLException,java.rmi.RemoteException {

	        Connection conn = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;
	        ArrayList vRecords = new ArrayList(); //object that holds the data
	        ArrayList records=null;
	        String sql =null;
	        try {
	            /*Get the Connection*/
	            conn = getConnection();

	             sql       = prepStmtObject.getSql();

	            /*make a scrollable resulst set object*/
	            prepStmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	            prepStmt.setMaxRows(maxRows);

	            // set the values
	            prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);

	            /*Executing the Select Query*/
	            rs = prepStmt.executeQuery();

	            try {
	                /* set the row number from where the data fetching would be started */
	                rs.absolute(startRows);
	            } catch (Exception e) {
	                logger.error("Exception in sqlSearchResultPrepStmt: " + e.getMessage());
	            }

	            /*getting the no of fields selected*/
	            int noOfFields = (rs.getMetaData()).getColumnCount();

	            //counter which will be incremented for the no of fields
	            //check whether the records have been returned
	            while (rs.next()) {
	                int counter = 1; //this will restart the count every time from 1

	                 records = new ArrayList();

	                while (counter <= noOfFields) {
	                    /*adding the column values in the arraylist*/
	                    records.add(rs.getString(counter));
	                    counter++;
	                }

	                /*adding the arraylist to the result data arraylist*/
	                vRecords.add(records);                
	            } //end of rs.next()

	        } //end of try
	        catch (SQLException e) {
	            throw new SQLException("SQl Exception in sqlSearchResultPrepStmt method : " + e.getMessage());
	        } catch (Exception e) {
	        	e=null;
	            //throw new EJBException("Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	closeConnectionPmt(conn,prepStmt,rs);
	        	prepStmtObject=null;
	        	records=null;
	        	sql=null;
	        }

	        return vRecords;
	    }
	    
	    public static boolean sqlInsUpdDeletePrepStmt(Collection sqlHashMap) throws java.sql.SQLException,java.rmi.RemoteException{

	        boolean retVal = false;
	        Connection conn = null;
	        PreparedStatement prepStmt = null;
	        PrepStmtObject prepStmtObject = null;
	        String sql = null;
	        Iterator sqlIterator=null;
	        try {
	            //get the database connection
	            conn = getConnection();
	           // conn.setAutoCommit(false);
	            //Iterator for the sqls to be executed
	             sqlIterator = sqlHashMap.iterator();

	            while (sqlIterator.hasNext()) {

	                prepStmtObject = (PrepStmtObject) sqlIterator.next();
	                sql = prepStmtObject.getSql();

	                prepStmt = conn.prepareStatement(sql);
	                prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);

	                prepStmt.executeUpdate();

	                //Check whether there is any change in the database, if not that means some problem with the query
	                retVal = true;

	            releaseStatement(prepStmt);


	            }
	            if(retVal)
	            {
	            	//conn.commit();
	            }

	        } catch (SQLException e) {
	            logger.error("In method sqlInsUpdDeletePrepStmt():" + e.getMessage()+ "\n The Failed prepStmtObject is :  "+prepStmtObject);
	            // conn.rollback();
	           // throw new SQLException("In method sqlInsUpdDeletePrepStmt(): SQL Trap Session : " + e.getMessage());
	            e=null;
	        } catch (Exception e) {
	            logger.error("In method sqlInsUpdDeletePrepStmt():" + e.getMessage());
	            e=null;
	            // conn.rollback();
	         //   throw new EJBException("In method sqlInsUpdDeletePrepStmt(): Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	    closeConnectionPmt(conn,prepStmt);
					sqlHashMap.clear();
					sqlHashMap=null;
					sqlIterator=null;
					prepStmtObject=null;
					sql=null;
	        }

	        //Return the boolean value which is true if the transaction was successful otherwise false
	        return retVal;
	    }
	    
	    public static String singleReturnFromMultipleRecords(String sqlQuery) {
	        Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        String strReturn = null;
	     
	        try {
	            con = getConnection();
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(sqlQuery);

	            while (rs.next()) {
	            	
	            	if(rs.getString(1).equals("Y"))
	            	{
	            	
	            		strReturn = rs.getString(1);
	            		break;
	            	}
	                
	            }
	        } catch (Exception e) {
	            logger.error("In method singleReturn() :" + e.getMessage());
	            e=null;
	          //  throw new EJBException(e.getMessage());
	        } finally {
	        	  closeConnection(con,stmt,rs);
	        	  sqlQuery=null;
	        }
	      
	        return strReturn;
	    }
	    
	    public static Collection callSP(String spName, Collection inParam, Collection outParam) throws Exception {
	        Connection conn = null;
	        CallableStatement sp = null;
	        boolean status=false;
	        ArrayList outMessages = new ArrayList();
	        StringBuffer sql = new StringBuffer();
	        Iterator inParams=null;
	        String  s1=null;
	        logger.info("In callSP :input parameter :==>> " +inParam.toString());
	        try {
	            conn = getConnection();
	            
	           
	            sql.append("{ call ");
	            sql.append(spName);
	            sql.append(" ( ");

	            int count = 0;
	            int size=inParam.size();
	            for (count = 0; count < size; count++)
	                sql.append(" ?,");
	            int outerSize=outParam.size();
	            for (count = 0; count < outerSize; count++)
	                sql.append(" ?,");

	            sql.deleteCharAt(sql.length() - 1);

	            sql.append(") };");
	            logger.info("Procedure: "+sql.toString());
	            sp = conn.prepareCall(sql.toString());

	             inParams = inParam.iterator();
	             int sizeIn=inParam.size();
	            for (count = 1; count <= sizeIn; count++) {
	                sp.setString(count, inParams.next().toString());
	            }
	            int sizeOut=outParam.size();
	            for (count = 1; count <= sizeOut; count++)
	                sp.registerOutParameter(inParam.size() + count, Types.VARCHAR);

	            status=sp.execute();
	            //logger.info("Proc Status: "+status);
	            //logger.info("Proc outParam.size(): "+outParam.size());
	            //logger.info("Proc sp: "+sp);
	            int sizeOutParam=outParam.size();
	            for (count = 1; count <= sizeOutParam; count++) {
	            	//logger.info("Proc count: "+count);
	            	//logger.info("Proc inParam.size(): "+inParam.size());
	                outMessages.add(new String(CommonFunction.checkNull(sp.getString(inParam.size()+count))));
	            }
	            s1=CommonFunction.checkNull(outMessages.get(outMessages.size()-2));
	        
	          if(!s1.equalsIgnoreCase("E")){
	        	  
	        	
	        	  
	        	   //conn.commit(); 
	        		
	          }else{
	        	 // conn.rollback();  
	          }
	        } catch (SQLException e) {
	            throw new SQLException("SQL Exception caught in callSP() of ConnectionDAO: " + e.getMessage());
	       } catch (Exception e) {
	            throw new Exception("Generic Unidentified exception caught in callSP() of ConnectionDAO : " + e.getMessage());
	        } finally {
	        	 releaseStatement(sp);
		            releaseConnection(conn);
		            sql=null;
		            spName=null;
		            inParam=null;
		            outParam=null;
		            inParams=null;
		            s1=null;
	        }
	        return outMessages;
	    }
	    

}
