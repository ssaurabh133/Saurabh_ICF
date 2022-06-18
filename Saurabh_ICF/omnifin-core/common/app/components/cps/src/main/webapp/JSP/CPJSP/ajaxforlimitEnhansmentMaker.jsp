<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<logic:present name="limitList" >
	 ${limitList[0].oldSenAmount}
     $:${limitList[0].oldSenDate}
     $:${limitList[0].utilAmount}
     $:${limitList[0].oldSenValidTill}
     $:${limitList[0].addSenAmount}
     $:${limitList[0].applicationdate}
</logic:present>
<logic:present name="limitLoanList" >
	 ${oldLoanAmount}
	 ${oldSenDate}
   	 ${oldSenValidTill}
</logic:present>

