<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="getDefaultWaiveOffDetail" >

              
       <logic:iterate name="getDefaultWaiveOffDetail" id="subloanList" >

       ${subloanList.chargeAmount}
     $:${subloanList.taxAmount1}
     $:${subloanList.taxAmount2}
     $:${subloanList.adviceAmount}
     $:${subloanList.txnAdjustedAmount}
     $:${subloanList.balanceAmount}
  	 $:${subloanList.waiveOffAmountNotUsed}
     $:${subloanList.txnAdviceId}
     $:${subloanList.taxRate1}
     $:${subloanList.taxRate2}
     $:${subloanList.amountInProcess}
     
     
  	</logic:iterate> 
    </logic:present>