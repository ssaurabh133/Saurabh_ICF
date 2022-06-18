package com.connect;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PrepStmtObject implements Serializable {
    String sql = ""; 
    ArrayList values = null; 
    static final Comparator seqComparator = new Comparator() 
     {
        public int compare(Object o1, Object o2) {
            PrepStmtValueObject valueObject1 = (PrepStmtValueObject) o1;
            PrepStmtValueObject valueObject2 = (PrepStmtValueObject) o2;

            return (valueObject1.getSequence() - valueObject2.getSequence());
        }
    };

    
    public PrepStmtObject() {
        values = new ArrayList();
    }


    public PrepStmtObject(String sql, ArrayList values) {
        this.sql = sql;
        this.values = values;
    }


    public PrepStmtObject(PrepStmtObject prepStmtObject) {
        this.sql = prepStmtObject.sql;
        this.values = prepStmtObject.values;
    }


    public void setSql(String sql) {
        this.sql = sql;
    }


    public String getSql() {
        return sql;
    }


    public void setValues(ArrayList values) {
        this.values = values;
    }

    public ArrayList getValues() {
        return values;
    }


    public void addValueObject(PrepStmtValueObject valueObject) {
        values.add(valueObject);
    }


    public void addValueObject(int sequence, int type, Object value) {
        PrepStmtValueObject valueObject = new PrepStmtValueObject();
        valueObject.setSequence(sequence);
        valueObject.setType(type);
        valueObject.setValue(value);

        addValueObject(valueObject);

        valueObject = null;
    }


    public void addNull() {
        addValueObject(values.size() + 1, PrepStmtTypesIFace.SETNULL, null);
    }


    public void addInt(long value) {
        addValueObject(values.size() + 1, PrepStmtTypesIFace.SETLONG, new Long(value));
    }


    public void addString(String value) {
        addValueObject(values.size() + 1, PrepStmtTypesIFace.SETSTR, value);
    }


    public void addFloat(double value) {
        addValueObject(values.size() + 1, PrepStmtTypesIFace.SETDOUBLE, new Double(value));
    }


    public void addDate(String date, String format) {
        java.util.Date utilDate = DateTimeStamp.getDate(date, format);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        addValueObject(values.size() + 1, PrepStmtTypesIFace.SETDATE, sqlDate);
    }
    

    public void addDate(java.sql.Date sqlDate)
    {
        addValueObject(values.size() + 1, PrepStmtTypesIFace.SETDATE, sqlDate);
    }

 
    public void addNull(int sequence) {
        addValueObject(sequence, PrepStmtTypesIFace.SETNULL, null);
    }


    public void addInt(int sequence, long value) {
        addValueObject(sequence, PrepStmtTypesIFace.SETLONG, new Long(value));
    }


    public void addString(int sequence, String value) {
        addValueObject(sequence, PrepStmtTypesIFace.SETSTR, value);
    }


    public void addFloat(int sequence, double value) {
        addValueObject(sequence, PrepStmtTypesIFace.SETDOUBLE, new Double(value));
    }


    public void addDate(int sequence, String date, String format) {
        java.util.Date utilDate = DateTimeStamp.getDate(date, format);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        addValueObject(sequence, PrepStmtTypesIFace.SETDATE, sqlDate);
    }


    public String printQuery() {
        PrepStmtValueObject valueObject = null;        
        String value;
        int type;
        String printSql = sql;

        Collections.sort(values, seqComparator); 

        for (int i = 0; i < values.size(); i++) {
            valueObject = (PrepStmtValueObject) values.get(i);
            value = String.valueOf(valueObject.getValue());
            type = valueObject.getType();

            switch (type) {
            case (PrepStmtTypesIFace.SETNULL):
                printSql = printSql.replaceFirst("\\?", "NULL"); 

                break;

            case (PrepStmtTypesIFace.SETLONG):
            case (PrepStmtTypesIFace.SETDOUBLE):
                printSql = printSql.replaceFirst("\\?", value); 

                break;

            case (PrepStmtTypesIFace.SETSTR):
                value = value.replaceAll("[$]", "################");
                printSql = printSql.replaceFirst("\\?", "'" + value + "'"); 
                printSql = printSql.replaceAll("################", "\\$");

                break;

            case (PrepStmtTypesIFace.SETDATE):
                printSql = printSql.replaceFirst("\\?", "to_date('" + value + "','yyyy-mm-dd')"); 

                break;
            }
        }

        return printSql;
    }

    public String toString() {
        StringBuffer sbReturn = new StringBuffer(100);
        sbReturn.append("sql:" + sql + "\n values{value,type,sequence}: [ ");

        for (int i = 0; i < values.size(); i++) {
            sbReturn.append(((PrepStmtValueObject) values.get(i)).toString() + " ");
        }

        sbReturn.append("]");

        return sbReturn.toString();
    }
}
