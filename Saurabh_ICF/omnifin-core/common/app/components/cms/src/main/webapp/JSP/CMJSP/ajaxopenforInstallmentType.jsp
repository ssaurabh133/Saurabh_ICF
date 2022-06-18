<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

  <logic:present name="insNonInsFlag">
	<logic:iterate name= "insNonInsFlag" id="insNonInsFlagObj">
	
          ${insNonInsFlagObj.loanInstallmentMode}
          $:${insNonInsFlagObj.loanAdvanceInstallment}
          $:${insNonInsFlagObj.installmentType}
          $:${insNonInsFlagObj.otherInstallmentCharges}
          $:${insNonInsFlagObj.totalChargeInstallmentAmount} 
          $:${insNonInsFlagObj.repayment}   
         </logic:iterate>
         </logic:present> 
         
      <logic:present name="getNameOfPDCSubmitBy">
         <div id="s4">
         <html:select property="pdcSubmitCustomerName" styleId="pdcSubmitCustomerName"	styleClass="text" value="">
				<logic:notEmpty name="getNameOfPDCSubmitBy">
					<html:optionsCollection name="getNameOfPDCSubmitBy" label="pdcSubmitCustomerName" value="pdcSubmitCustomerName" />
				</logic:notEmpty>
		</html:select>
		</div>
       </logic:present>  
         
         
       
         