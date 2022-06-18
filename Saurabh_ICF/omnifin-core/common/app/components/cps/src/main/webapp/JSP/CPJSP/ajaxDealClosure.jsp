<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


    <logic:present name="closureList" >
      <logic:iterate name="closureList" id="list">
      
      
   		${list.dealDate}
     	$:${list.appFormNumber}
     	$:${list.product}
     	$:${list.scheme}
     	$:${list.assetCost}
     	$:${list.loanAmount}
     	$:${list.marginPercentage}
     	$:${list.marginAmount}
     	$:${list.rate}
     	$:${list.tenure}
     	$:${list.dealStatus}
     	$:${list.frequency}
     	$:${list.lbxProduct}
     	$:${list.lbxScheme}  
     
			
	</logic:iterate>
    </logic:present>  
    
    <logic:present name="PayeeList">     
   		${PayeeList[0]}
     	$:${PayeeList[1]}
     </logic:present>  
    
    
     