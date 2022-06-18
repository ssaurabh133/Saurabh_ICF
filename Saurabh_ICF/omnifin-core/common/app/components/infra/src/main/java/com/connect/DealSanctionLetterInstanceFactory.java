package com.connect;









import com.cp.daoImplMYSQL.DealSanctionLetterDAOImpl;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;


public class DealSanctionLetterInstanceFactory
{
  private static final Logger logger = Logger.getLogger(DealSanctionLetterInstanceFactory.class.getName());

  public static Object getDaoImplInstance(String daoType)
  {
    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
    String dbType = resource.getString("lbl.dbType");
    Object ob = null;
	if(daoType.equalsIgnoreCase("DSL"))
	{
		
			ob= new DealSanctionLetterDAOImpl(); 
		
	}	

    return ob;
  }
}