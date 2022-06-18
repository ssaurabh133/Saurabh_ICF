

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<logic:present name="receiptAmountData">
<logic:iterate name="receiptAmountData" id="receiptAmountList">

      $:${receiptAmountList.receiptAmountNew}
      $:${receiptAmountList.receiptAmount}
      

 
</logic:iterate>

</logic:present>
