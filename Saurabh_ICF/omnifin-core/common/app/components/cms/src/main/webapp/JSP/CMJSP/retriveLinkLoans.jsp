<!--Author Name : Saurabh Singh-->
<!--Date of Creation : 16-April -2013-->
<!--Purpose  :Get Detail of linked Loans-->
<!--Documentation : -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="linkLoanList" >
	<logic:notEmpty name="linkLoanList" >
      <logic:iterate name="linkLoanList" id="subLinkList">
			$:${subLinkList.loanAmount}
			$:${subLinkList.loanTenure}
	</logic:iterate>
    </logic:notEmpty>    
</logic:present>
