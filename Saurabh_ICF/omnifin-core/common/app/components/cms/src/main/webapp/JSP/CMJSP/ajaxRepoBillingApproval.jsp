<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="repoList" >
          
  <logic:iterate name="repoList" id="subloanList" >

      ${subloanList.billingStopped}
     $:${subloanList.interestStopped}
     $:${subloanList.SDInterest}
  		
  	</logic:iterate> 
    </logic:present>