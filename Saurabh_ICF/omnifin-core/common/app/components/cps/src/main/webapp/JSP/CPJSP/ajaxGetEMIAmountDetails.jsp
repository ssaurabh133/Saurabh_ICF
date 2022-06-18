<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


    <logic:present name="list" >
    <logic:notEmpty name="list">
      <logic:iterate name="list" id="listObj">
   		${listObj.epmonth}		
	</logic:iterate>
	</logic:notEmpty>

    </logic:present>  
    
     