<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


    <logic:present name="getChequeList" >
      <logic:iterate name="getChequeList" id="subChequeList">
      
      
   		 ${subChequeList.lbxBankID}
     $:${subChequeList.bank}
     $:${subChequeList.lbxBranchID}
     $:${subChequeList.branch}
     $:${subChequeList.micr}
     $:${subChequeList.ifsCode}
     
			
	</logic:iterate>
    </logic:present>  
    
     <logic:present name="amount" >
     	 ${amount}  
    </logic:present> 
     <logic:present name="calculatePDCList" >
     	  ${presented}
     	$:${toBePresented} 
    </logic:present>     
    <logic:present name="getChequeListR" >
      <logic:iterate name="getChequeListR" id="subChequeList">
      
       ${subChequeList.lbxBankID}
     $:${subChequeList.bank}
     $:${subChequeList.lbxBranchID}
     $:${subChequeList.branch}
     $:${subChequeList.micr}
     $:${subChequeList.ifsCode}
			
	</logic:iterate>
    </logic:present>  
    
           <logic:present name="allocated">
           <%=request.getAttribute("allocated") %>
           </logic:present>
           
        
    
           <logic:present name="checkAllocation">
            <logic:iterate name="checkAllocation" id="list">
            <bean:write name="list" property="VallocatedAmount" />
		$:<bean:write name="list" property="VtdsAmount" />
           </logic:iterate>
           </logic:present>
           
         