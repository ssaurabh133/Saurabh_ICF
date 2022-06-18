<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


    <logic:present name="groupExposureList" >
      <logic:iterate name="groupExposureList" id="list" length="1">
      
      
   		${list.customerId}
     	$:${list.customerName}
     	$:${list.groupType}
     	$:${list.groupNameText}
     	$:${list.hGroupId}
     	$:${list.groupDescription}
     	$:${list.groupExposureLimit}
     	$:${productExposure}
     	$:${schemeExposure}
     	$:${totalCustomerExposure}
     	$:${shemeCustomerExposure}
     	$:${groupExposure}
     	$:${industryExposure}
     	$:${subIndustryExposure}  
     	$:${LOAN_BALANCE_PRINCIPAL}  
     	$:${LOAN_OVERDUE_PRINCIPAL}  
     	$:${SD_ADVICE_AMOUNT}  
     	$:${DEAL_SD_CHARGES}  
     	$:${EXPOSURE_AMOUNT}  
     	$:${TERM_LOAN_AMOUNT}  
     	
	</logic:iterate>
    </logic:present>  
    
     