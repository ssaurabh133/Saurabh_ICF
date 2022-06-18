package com.connect;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;

import org.apache.log4j.Logger;


public class PrepStmtSetter extends PrepStmtObject
{

  private Logger logger = Logger.getLogger(PrepStmtSetter.class.getName());

  public PrepStmtSetter()
  {

  }

  public PrepStmtSetter(PrepStmtObject prepStmtObject)
  {
   super(prepStmtObject);
  }

  public PreparedStatement setPreparedStmt(PreparedStatement prepStmt) throws SQLException
  {
    try{
            for(int i=0; i<values.size(); i++ )
            {
                  PrepStmtValueObject valueObject = (PrepStmtValueObject)values.get(i);
                  int type  = valueObject.getType();
                  int seq   = valueObject.getSequence();
                  Object value = valueObject.getValue();
                  switch(type){

                     case(PrepStmtTypesIFace.SETNULL) :
                        prepStmt.setNull(seq,Types.NULL);
                     break;

                     case(PrepStmtTypesIFace.SETLONG) :
                        prepStmt.setLong(seq,((Long)value).longValue());
                     break;

                     case(PrepStmtTypesIFace.SETSTR) :
                        prepStmt.setString(seq,value.toString());
                     break;

                     case(PrepStmtTypesIFace.SETDOUBLE) :
                        prepStmt.setDouble(seq,((Double) value).doubleValue());
                     break;

                     case(PrepStmtTypesIFace.SETDATE) :
                        prepStmt.setDate(seq,(Date)value);
                     break;
                 }
            }
    } catch (SQLException sqlExcep)
    {
      logger.error("Error in setPreparedStmt() ::"+sqlExcep.getMessage());
      throw new SQLException(sqlExcep.getMessage());
    }
          return   prepStmt;
  }
}