package com.connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;


public class ConnectionDAOForOthers {
	
	private static final Logger logger = Logger.getLogger(ConnectionDAO.class.getName());
	private ConnectionDAOForOthers()
	{
		logger.info("Singleton Class------------");
	}
	
	public static Connection getConnection() {
		Connection connection = null;
		logger.info("Singleton Class------------");
		
		try {
			//ReadAndPrintXMLFile.callXML();
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//String url=Dbconfiguration.getURl();
//			String username=Dbconfiguration.getUsername();
//			String password=Dbconfiguration.getPassword();
//			Hashtable env = new Hashtable();
//			env.put(Context.SECURITY_PRINCIPAL, "jsmith");
//			env.put(Context.SECURITY_CREDENTIALS, "xxxxxxx");
//			Context ctx = new InitialContext(env);
			String DATASOURCE_CONTEXT = "java:comp/env/jdbc/FinCraftDB2";

			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup(DATASOURCE_CONTEXT);
			
			if (ds != null)
			{
				connection = ds.getConnection();
		    }
		    else
		    {
		       logger.info("Failed to lookup datasource.");
		    }

		} catch (Exception e) {
			logger.info("Error occur in ConnectionDAO class-getConnection method:--"+e.getMessage());

		}
		return connection;
	}

	public static void closeConnection(Connection connection, Statement statement) {
		
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
			}
		}
	}
	
	public static void closeConnection(Connection connection, Statement statement,ResultSet resultset) {
		if (resultset != null) {
			try {
				resultset.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.debug("Error occur in ConnectionDAO class-closeConnection method:--"+e.getMessage());
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
	              
	          }
	      }catch (Exception e)
	      {
	          logger.error("Exception while closing Statement" + e);
	      }
	    }

	    private static void releaseResultSet(ResultSet rs)
	    {
	        try
	        {
	            if (rs != null)
	            {
	                rs.close();
	            }
	        }catch (Exception e)
	        {
	            logger.error("Exception while closing resultset" + e);
	        }
	    }

	    private static void releaseConnection(Connection con1) {
	        try {
	            if (con1 != null) {
	                con1.close();
	            }
	        } catch (Exception e) {
	            logger.error("In method releaseConnection():  Exception while closing resources :" + e.getMessage());
	        }
	    }

	    public static String singleReturn(String sqlQuery) {
	        Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        String strReturn = null;
	        
	        try {
	            con = getConnection();
	            con.setAutoCommit(false);
	            stmt = con.createStatement();
	            logger.info("singleReturn sql query ................................ "+sqlQuery);
	            rs = stmt.executeQuery(sqlQuery);
                 
	            if (rs.next()) {
	                strReturn = CommonFunction.checkNull(rs.getString(1));
	                logger.info("singleReturn sql strReturn ................................ "+rs.getString(1));
	            }
	        } catch (Exception e) {
	        	try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            logger.error("In method singleReturn() :" + e.getMessage());
	          //  throw new EJBException(e.getMessage());
	        } finally {
	        	try {
					con.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            releaseResultSet(rs);
	            releaseStatement(stmt);
	            releaseConnection(con);
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
	            con.setAutoCommit(false);
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(sqlQuery);

	            if (rs!=null && rs.next()) {
	            	logger.info("In checkStatus:"+rs.getString(1));
	            	if(!CommonFunction.checkNull(rs.getString(1)).equalsIgnoreCase(""))
	            	{
	            		strReturn=true;
	            	}
	            	
	            }
	        } catch (Exception e) {
	        	try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            logger.error("In method singleReturn() :" + e.getMessage());
	          //  throw new EJBException(e.getMessage());
	        } finally {
	        	try {
					con.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            releaseResultSet(rs);
	            releaseStatement(stmt);
	            releaseConnection(con);
	        }

	        return strReturn;
	    }
	    
	     public static boolean sqlInteraction(Collection sql) throws java.sql.SQLException {
	        boolean retVal = false;
	        Connection conn = null;
	        Statement stmt = null;
	        String sqlIt = null;

	        try {
	            conn = getConnection();

	            Iterator sqlIterator = sql.iterator();

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
	            throw new SQLException("In method sqlInteraction() :SQL Trap Session : " + e.getMessage());
	        } catch (Exception e) {
	            logger.error("In method sqlInteraction() :" + e.getMessage());
	             conn.rollback();
	          //  throw new EJBException("In method sqlInteraction() :Unidentified Trap Session : " + e.getMessage());
	        } finally {
	            logger.debug("In method sqlInteraction() :Connection is " + conn);
	            releaseStatement(stmt);
	            releaseConnection(conn);
	        }

	        return retVal;
	    }
	    
	    
	    public static ArrayList sqlSelect(String sql) throws java.sql.SQLException {
	        Connection conn = null;
	        Statement stmt = null;

	        //ArrayList defined to hold the out type parameters
	        ResultSet rs = null;
	        ArrayList vRecords = new ArrayList();

	        try {
	            //Get the Connection
	            conn = getConnection();
	            conn.setAutoCommit(false);
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
	                ArrayList records = new ArrayList();

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
	        	conn.rollback();
	         //   throw new EJBException("Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	conn.commit();
	        	releaseResultSet(rs);
	            releaseStatement(stmt);
	            releaseConnection(conn);
	        }
	        return vRecords;
	    }
	    

	  public static boolean sqlBatchProcess(java.util.ArrayList sqlQueries) throws java.sql.SQLException {
	        Connection con = null; //holds the connection object
	        Statement stmt = null; //used to execute the batch queries
	        boolean flag = true;
	        try {
	            con = getConnection(); //fetch the connection object
	            stmt = con.createStatement(); //fetch the statement object

	            /* Add the sql queries into a batch*/
	            for (int i = 0; i < sqlQueries.size(); i++)
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
	           // throw new EJBException(e.getMessage());
	        }
	        finally
	        {
	          releaseStatement(stmt);
	          releaseConnection(con);
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
	            }

	            /*getting the no of fields selected*/
	            int noOfFields = (rs.getMetaData()).getColumnCount();

	            //counter which will be incremented for the no of fields
	            //check whether the records have been returned            

	            while (rs.next()) {
	                int counter = 1; //this will restart the count every time from 1

	                ArrayList records = new ArrayList();

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
	           // throw new EJBException("Unidentified Trap Session : " + e.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(stmt);
	            releaseConnection(conn);
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

	        try {
	            //get the database connection
	            conn = getConnection();
	            conn.setAutoCommit(false);
	            //Iterator for the sqls to be executed
	            Iterator sqlIterator = sql.iterator();

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
	            	conn.commit();
	            }

	        } catch (SQLException e) {
	            logger.error("In method sqlInsUpdDelete():" + e.getMessage()+"\n The Failed Query  is :  "+sqlIt);
	             conn.rollback();
	            throw new SQLException("In method sqlInsUpdDelete(): SQL Trap Session : " + e.getMessage());
	        } catch (Exception e) {
	            logger.error("In method sqlInsUpdDelete():" + e.getMessage());
	             conn.rollback();
	            //throw new EJBException("In method sqlInsUpdDelete(): Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	
	            releaseStatement(stmt);
	            releaseConnection(conn);
	        }

	        //Return the boolean value which is true if the transaction was successful otherwise false
	        return retVal;
	    }



	    
	    
	    public static String singleReturnPrepStmt(PrepStmtObject prepStmtObject) throws java.sql.SQLException,java.rmi.RemoteException{
	        Connection con = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;
	        String strReturn = null;

	        try {
	            con = getConnection();

	            String sql = prepStmtObject.getSql();            

	            prepStmt = con.prepareStatement(sql);
	            prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);
	            rs = prepStmt.executeQuery();
	            if (rs.next()) {
	                strReturn = rs.getString(1);
	            }
	        } catch (Exception e) {
	            logger.error("In method singleReturnPrepStmt() :" + e.getMessage());
	           // throw new EJBException(e.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(prepStmt);
	            releaseConnection(con);
	        }

	        return strReturn;
	    }
	    
	 /*   public static boolean sqlInteractionpPrepStmt(Collection sqlHashMap) throws java.sql.SQLException,java.rmi.RemoteException {

	        boolean retVal = false;
	        Connection conn = null;
	        PreparedStatement prepStmt = null;
	        PrepStmtObject prepStmtObject = null;
	        String sql = null;
	        
	        try {
	            //get the database connection
	            conn = getConnection();
	            Iterator sqlIterator = sqlHashMap.iterator();

	            while (sqlIterator.hasNext()) {
	            	
	            	conn.setAutoCommit(false);
	                prepStmtObject = (PrepStmtObject) sqlIterator.next();
	                sql = prepStmtObject.getSql();

	                prepStmt = conn.prepareStatement(sql);
	                prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);

	                //Check whether there is any change in the database, if not that means some problem with the query
	                if (prepStmt.executeUpdate() == 0) {
	                    //rollback the transaction if any query fails
	                     conn.rollback();
	                    throw new java.sql.SQLException("NO DATA FOUND");
	                } else {
	                    retVal = true;
	                    conn.commit();
	                }

	                releaseStatement(prepStmt);
	            }
	            if(retVal)
	            {
	            	conn.commit();
	            }

	        } catch (java.sql.SQLException e) {
	            logger.error("In method sqlInteractionpPrepStmt() :" + e.getMessage()+ "\n The Failed prepStmtObject is : "+prepStmtObject);
	             conn.rollback();
	            throw new java.sql.SQLException("In method sqlInteractionpPrepStmt() :SQL Trap Session : " + e.getMessage());
	        } catch (Exception e) {
	            logger.error("In method sqlInteractionpPrepStmt() :" + e.getMessage());
	             conn.rollback();
	          //  throw new EJBException("In method sqlInteractionpPrepStmt() :Unidentified Trap Session : " + e.getMessage());
	        } finally {
	            //Release or close the connection
	            logger.debug("In method sqlInteractionpPrepStmt() :Connection is " + conn);
	            releaseStatement(prepStmt);
	            releaseConnection(conn);
	        }

	        //Return the boolean value which is true if the transaction was successful otherwise false
	        return retVal;
	    }
	    */
	    public static ArrayList sqlSelectPrepStmt(PrepStmtObject prepStmtObject) throws java.sql.SQLException,java.rmi.RemoteException {

	        Connection conn            = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;
	        ArrayList vRecords = new ArrayList();

	        try {
	            //Get the Connection
	            conn = getConnection();
	          
	            String sql       = prepStmtObject.getSql();

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
	                ArrayList records = new ArrayList();

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
	           // throw new EJBException("Unidentified Trap Session in method sqlSelectPrepStmt: " + e.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(prepStmt);
	            releaseConnection(conn);
	        }
	        return vRecords;
	    }
	    
	    public static boolean sqlBatchProcessPrepStmt(String sql, java.util.ArrayList alValues) throws java.sql.SQLException,java.rmi.RemoteException {

	        Connection conn = null; //holds the connection object
	        PreparedStatement prepStmt = null; //used to execute the batch queries
	        boolean flag = true;
	        try {
	            conn = getConnection(); //fetch the connection object

	            prepStmt  = conn.prepareStatement(sql);

	            for (int i = 0; i < alValues.size(); i++)
	            {
	                ArrayList values = (ArrayList) alValues.get(i);

	                PrepStmtObject prepStmtObject = new PrepStmtObject(sql,values);
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
	           // throw new EJBException(e.getMessage());
	        }
	        finally
	        {
	          releaseStatement(prepStmt);
	          releaseConnection(conn);
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

	        try {
	            /*Get the Connection*/
	            conn = getConnection();

	            String sql       = prepStmtObject.getSql();

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

	                ArrayList records = new ArrayList();

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
	            //throw new EJBException("Unidentified Trap Session : " + e.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(prepStmt);
	            releaseConnection(conn);
	        }

	        return vRecords;
	    }
	    
	    public static boolean sqlInsUpdDeletePrepStmt(Collection sqlHashMap) throws java.sql.SQLException,java.rmi.RemoteException{

	        boolean retVal = false;
	        Connection conn = null;
	        PreparedStatement prepStmt = null;
	        PrepStmtObject prepStmtObject = null;
	        String sql = null;
	        
	        try {
	            //get the database connection
	            conn = getConnection();
	            conn.setAutoCommit(false);
	            //Iterator for the sqls to be executed
	            Iterator sqlIterator = sqlHashMap.iterator();

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
	            	conn.commit();
	            }

	        } catch (SQLException e) {
	            logger.error("In method sqlInsUpdDeletePrepStmt():" + e.getMessage()+ "\n The Failed prepStmtObject is :  "+prepStmtObject);
	             conn.rollback();
	            throw new SQLException("In method sqlInsUpdDeletePrepStmt(): SQL Trap Session : " + e.getMessage());
	        } catch (Exception e) {
	            logger.error("In method sqlInsUpdDeletePrepStmt():" + e.getMessage());
	             conn.rollback();
	         //   throw new EJBException("In method sqlInsUpdDeletePrepStmt(): Unidentified Trap Session : " + e.getMessage());
	        } finally {
	        	
	            releaseStatement(prepStmt);
	            releaseConnection(conn);
	        }

	        //Return the boolean value which is true if the transaction was successful otherwise false
	        return retVal;
	    }
	    
	    public static String singleReturnFromMultipleRecords(String sqlQuery) {
	        Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        String strReturn = null;
	        logger.info("check query .......................................... ...................... "+sqlQuery);
	        try {
	            con = getConnection();
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(sqlQuery);

	            while (rs.next()) {
	            	
	            	if(rs.getString(1).equals("Y"))
	            	{
	            		logger.info("check rs.getString(1) .......................................... ...................... "+rs.getString(1));
	            		strReturn = rs.getString(1);
	            		break;
	            	}
	                
	            }
	        } catch (Exception e) {
	            logger.error("In method singleReturn() :" + e.getMessage());
	          //  throw new EJBException(e.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(stmt);
	            releaseConnection(con);
	        }
	        logger.info("check strReturn .......................................... ...................... "+strReturn);
	        return strReturn;
	    }
	    
	   /* public ArrayList sqlSelectHashMapPrepStmt(PrepStmtObject prepStmtObject) throws java.sql.SQLException,java.rmi.RemoteException{
	        Connection conn = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;
	        ResultSetMetaData rsmd = null;
	        int numberOfColumns = 0;
	        ArrayList resultSetList = new ArrayList();

	        try {
	            conn = getConnection();

	            String sql = prepStmtObject.getSql();

	            prepStmt = conn.prepareStatement(sql);

	            prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);

	            rs = prepStmt.executeQuery();

	            rsmd = rs.getMetaData();
	            numberOfColumns = rsmd.getColumnCount();

	            while (rs.next()) {
	                HashMap hsmap = new HashMap();

	                for (int j = 0; j < numberOfColumns; j++) {
	                    // putting data values in HashMap (each row in single HashMap)
	                    hsmap.put(rsmd.getColumnName(j + 1).toUpperCase(), rs.getString(j + 1)); // column name is the key for the value
	                }

	                // adding above HashMap means Row in ArrayList
	                resultSetList.add(hsmap);
	            }
	        } catch (SQLException sqlEx) {
	            throw new SQLException("In method sqlSelectHashMap() : Error in HashMap Session : " + sqlEx.getMessage());
	        } catch (Exception ex) {
	            throw new EJBException("In method sqlSelectHashMap() : Error in HashMap Session : " + ex.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(prepStmt);
	            releaseConnection(conn);
	        }

	        return resultSetList;
	    }
	    
	        public HashMap sqlSelectMultipleHashMapPrepStmt(HashMap sqlHashMap) throws java.sql.SQLException,java.rmi.RemoteException {
	        HashMap resultHashMap = new HashMap(); // hashmap to store results
	        sqlHashMap = (sqlHashMap == null) ? new HashMap() : sqlHashMap; // incase sql hashmap is null no null point exception

	        Iterator keyIt = sqlHashMap.keySet().iterator();

	        while (keyIt.hasNext()) {
	            String key = (String) keyIt.next();
	            PrepStmtObject prepStmtObject = (PrepStmtObject) sqlHashMap.get(key);
	            ArrayList resultSetList = sqlSelectHashMapPrepStmt(prepStmtObject);
	            resultHashMap.put(key, resultSetList);
	        }

	        return resultHashMap;
	    }
	    
	    public HashMap sqlSelectHashMapGDO(HashMap hashMapSQL) throws SQLException{
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;        
	      //  GenericDataObject gdo = null;                
	        HashMap resultHashMap = null;
	        try {
	            conn = getConnection();
	            stmt = conn.createStatement();

	            hashMapSQL = (hashMapSQL == null) ? new HashMap() : hashMapSQL; // incase sql hashmap is null no null point exception

	            resultHashMap = new HashMap(hashMapSQL.size()); // hashmap to store results

	            Iterator keyIt = hashMapSQL.keySet().iterator();

	            while (keyIt.hasNext()) {
	                String key = (String) keyIt.next();
	                String sqlQuery = (String) hashMapSQL.get(key);

	                rs = stmt.executeQuery(sqlQuery);
	               // gdo = new GenericDataObject(rs);
	               // resultHashMap.put(key, gdo);

	                releaseResultSet(rs);
	            }
	        } catch (SQLException sqlEx) {
	            throw new SQLException("SQLException in method sqlSelectHashMapGDO() : " + sqlEx.getMessage());
	        } catch (Exception ex) {
	            throw new EJBException("Exception in method sqlSelectHashMapGDO() : " + ex.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(stmt);
	            releaseConnection(conn);
	        }
	        //Returning the output
	        return resultHashMap;
	    }
	    
	    public HashMap sqlSelectHashMapGDOPrepStmt(HashMap hashMapSQL) throws SQLException,java.rmi.RemoteException{
	        Connection conn = null;
	        PreparedStatement prepStmt = null;
	        ResultSet rs = null;        
	       // GenericDataObject gdo = null;                
	        HashMap resultHashMap = null;        
	        try {
	            conn = getConnection();

	            hashMapSQL = (hashMapSQL == null) ? new HashMap() : hashMapSQL; // incase sql hashmap is null no null point exception

	            resultHashMap = new HashMap(hashMapSQL.size()); // hashmap to store results

	            Iterator keyIt = hashMapSQL.keySet().iterator();

	            while (keyIt.hasNext()) {
	                String key = (String) keyIt.next();

	                PrepStmtObject prepStmtObject = (PrepStmtObject) hashMapSQL.get(key);

	                String sqlQuery = prepStmtObject.getSql();

	                prepStmt = conn.prepareStatement(sqlQuery);

	                prepStmt = new PrepStmtSetter(prepStmtObject).setPreparedStmt(prepStmt);

	                rs = prepStmt.executeQuery();

	                //gdo = new GenericDataObject(rs);
	               // resultHashMap.put(key, gdo);
	                releaseResultSet(rs);
	                releaseStatement(prepStmt); 
	            }

	        } catch (SQLException sqlEx) {
	            throw new SQLException("SQLException in method sqlSelectHashMapGDO() : " + sqlEx.getMessage());
	        } catch (Exception ex) {
	            throw new EJBException("Exception in method sqlSelectHashMapGDO() : " + ex.getMessage());
	        } finally {
	            releaseResultSet(rs);
	            releaseStatement(prepStmt);
	            releaseConnection(conn);
	        }
	        //Returning the output
	        return resultHashMap;
	    }
	
	 private DataSource ds = null;
	    
	    public Collection callSP(String spName, Collection inParam, Collection outParam) throws java.sql.SQLException {
	        Connection conn = null;
	        CallableStatement sp = null;

	        ArrayList outMessages = new ArrayList();

	        try {
	            conn = getConnection();

	            StringBuffer sql = new StringBuffer();

	            sql.append(" begin ");
	            sql.append(spName);
	            sql.append(" ( ");

	            int count = 0;

	            for (count = 0; count < inParam.size(); count++)
	                sql.append(" ?,");

	            for (count = 0; count < outParam.size(); count++)
	                sql.append(" ?,");

	            sql.deleteCharAt(sql.length() - 1);

	            sql.append("); end;");

	            sp = conn.prepareCall(sql.toString());

	            Iterator inParams = inParam.iterator();

	            for (count = 1; count <= inParam.size(); count++) {
	                sp.setString(count, inParams.next().toString());
	            }

	            for (count = 1; count <= outParam.size(); count++)
	                sp.registerOutParameter(inParam.size() + count, Types.VARCHAR);

	            sp.execute();

	            for (count = 1; count <= outParam.size(); count++) {
	                outMessages.add(new String(sp.getString(inParam.size() + count)));
	            }
	        } catch (SQLException e) {
	            throw new SQLException("SQL Exception caught in callSP() of UtilsEJB: " + e.getMessage());
	       } catch (Exception e) {
	            throw new EJBException("Generic Unidentified exception caught in callSP() of UtilsEJB : " + e.getMessage());
	        } finally {
	            releaseStatement(sp);
	            releaseConnection(conn);
	        }
	        return outMessages;
	    }


	    private Connection getConnection() throws java.sql.SQLException, java.lang.Exception {
	        try {
	            ds = getDataSource();
	            logger.debug("### getConnection ##### ");
	            return ds.getConnection();
	          //    return fetchConnection();
	        } catch (Exception e) {
	            logger.fatal("In method getConnection(): SQLException in CreateDataSource again" + e.getMessage());
	            throw e;
	        }
	    }

	
	 private DataSource getDataSource() throws java.lang.Exception {
	        try {
	            if (ds == null) {
	                logger.debug(" ### getDataSource ## 1 ");
	                InitialContext ctx = new InitialContext();
	                InitialContext evn = (InitialContext) initContext.lookup("java:comp/env");
	                DataSource dataS = (DataSource)evn.lookup("LPODataSource");
	                logger.debug(" ### getDataSource ## 2 ");
	                return dataS;
	            } else {
	                // return the datasource
	                return ds;
	            }
	        } catch (Exception e) {
	            throw e;
	        }
	    }
	  

	    private DataSource getDataSource() throws java.lang.Exception {
	        try {
	            if (ds == null) {
	            logger.debug(" ### getDataSource ## 1 ");
	                InitialContext ctx = new InitialContext();
	                return (DataSource) ctx.lookup("LPODataSource");
	            } else {
	                // return the datasource
	                return ds;
	            }
	        } catch (Exception e) {
	            throw e;
	        }
	    }
	    
	      public ArrayList sqlSelectHashMap(String sql) throws java.sql.SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        int numberOfColumns = 0;
        ArrayList resultSetList = new ArrayList();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rsmd = rs.getMetaData();
            numberOfColumns = rsmd.getColumnCount();

            while (rs.next()) {
                HashMap hsmap = new HashMap();

                for (int j = 0; j < numberOfColumns; j++) {
                    // putting data values in HashMap (each row in single HashMap)
                    hsmap.put(rsmd.getColumnName(j + 1).toUpperCase(), rs.getString(j + 1)); // column name is the key for the value
                }

                // adding above HashMap means Row in ArrayList
                resultSetList.add(hsmap);
            }
        } catch (SQLException sqlEx) {
            throw new SQLException("In method sqlSelectHashMap() : Error in HashMap Session : " + sqlEx.getMessage());
        } catch (Exception ex) {
            throw new EJBException("In method sqlSelectHashMap() : Error in HashMap Session : " + ex.getMessage());
        } finally {
            releaseResultSet(rs);
            releaseStatement(stmt);
            releaseConnection(conn);
        }

        return resultSetList;
    }

    
    public HashMap sqlSelectMultipleHashMap(HashMap sqlHashMap) throws java.sql.SQLException {
        HashMap resultHashMap = new HashMap(); // hashmap to store results
        sqlHashMap = (sqlHashMap == null) ? new HashMap() : sqlHashMap; // incase sql hashmap is null no null point exception

        Iterator keyIt = sqlHashMap.keySet().iterator();

        while (keyIt.hasNext()) {
            String key = (String) keyIt.next();
            String sqlQuery = (String) sqlHashMap.get(key);
            ArrayList resultSetList = sqlSelectHashMap(sqlQuery);
            resultHashMap.put(key, resultSetList);
        }

        return resultHashMap;
    }*/
}
