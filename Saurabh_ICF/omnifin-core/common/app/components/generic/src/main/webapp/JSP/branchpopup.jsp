<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="branchList" >

        <logic:notEmpty name="branchList" >     
       <logic:iterate name="branchList" id="subloanList" >
          	 <bean:write name="subloanList" property="branchId" />
			$:<bean:write name="subloanList" property="branchName" />    
			
	</logic:iterate> 
	</logic:notEmpty>
    </logic:present>  
    