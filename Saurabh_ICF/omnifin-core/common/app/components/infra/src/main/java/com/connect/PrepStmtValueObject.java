package com.connect;
import java.io.Serializable;


public class PrepStmtValueObject implements Serializable
{

  private Object value = null;   
  private int type = 0;         
  private int sequence = 0;     

  
  /**
   * 
   */
  public PrepStmtValueObject()
  {
  }


  public PrepStmtValueObject(int sequence,int type, Object value)
  {
    this.sequence = sequence;
    this.type = type;
    this.value = value;
  }

  public void setValue(Object value)
  {
    this.value = value;
  }


  public Object getValue()
  {
    return value;
  }


  public void setSequence(int sequence)
  {
    this.sequence = sequence;
  }


  public int getSequence()
  {
    return sequence;
  }


  public void setType(int type)
  {
    this.type = type;
  }


  public int getType()
  {
    return type;
  }


 public String toString()
 {
   return "{"+value +", "+type+", "+sequence+"}";
 }
}