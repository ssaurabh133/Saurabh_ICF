<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="pddUpdateList" >
          
  <logic:iterate name="pddUpdateList" id="subloanList" >

       ${subloanList.invoiceNumber}
     $:${subloanList.vehicleInvoiceDate}
     $:${subloanList.invoiceAmount}
     $:${subloanList.vehicleChesisNo}
     $:${subloanList.engineNumber}
     $:${subloanList.rcReceived}
  	 $:${subloanList.rcReceivedDate}
     $:${subloanList.vehicleRegNo}
     $:${subloanList.vehicleRegDate}
     $:${subloanList.vehicleYearOfManufact}
     $:${subloanList.vehicleOwner}
     $:${subloanList.permitReceived}
     $:${subloanList.permitReceivedDate}
     $:${subloanList.vehicleInsurer}
     $:${subloanList.vehicleInsureDate}
     $:${subloanList.idv}	 			
	 $:${subloanList.updateVal}		
  	</logic:iterate> 
    </logic:present>