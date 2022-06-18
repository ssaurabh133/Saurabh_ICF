<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="siRdName" >
          
  <logic:iterate name="siRdName" id="subloanList" >

      ${subloanList.siRdName}
  		
  	</logic:iterate> 
    </logic:present>