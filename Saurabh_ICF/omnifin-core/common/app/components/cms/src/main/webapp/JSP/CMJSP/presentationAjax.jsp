<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="result" >
<logic:iterate name="result" id="list">

   		 ${list.lbxBatchNo}
     $:${list.batchNo}
     $:${list.presentationDate}
     $:${list.lbxBankID}
     $:${list.bank}
     $:${list.lbxBranchID}
     $:${list.branch}
     $:${list.micr}
     $:${list.ifscCode}
     $:${list.bankAccount}
</logic:iterate>
</logic:present>
<logic:present name="totalInstrument" >
     $:${totalInstrument}
</logic:present>
<logic:present name="totalInstrumentAmount" >
     $:${totalInstrumentAmount}
</logic:present>

     
    