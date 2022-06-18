<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


    <logic:present name="otherLoanDetails" >
      <logic:iterate name="otherLoanDetails" id="subotherLoanDetails">
   
   		 ${subotherLoanDetails.industryPercent}
		
	</logic:iterate>
    </logic:present>  
    
    
           
         