<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="manualDefaultDetail" >

    <logic:notEmpty name="manualDefaultDetail">          
       <logic:iterate name="manualDefaultDetail" id="subloanList" >
       
       		${subloanList.adviceType }
       		$:${subloanList.chargeAmount }
       		$:${subloanList.tdsRate } 
       		$:${subloanList.taxApplicable }
       		$:${subloanList.taxInclusive }	
          	$:${subloanList.taxRate1 }
          	$:${subloanList.taxRate2 } 
			$:${subloanList.tdsApplicable }
			$:${subloanList.chargeId }
			$:${subloanList.minChargeAmount }
			
	  </logic:iterate> 
	</logic:notEmpty>
		<logic:empty name="manualDefaultDetail">
			
		</logic:empty>
    </logic:present>
    
   
				