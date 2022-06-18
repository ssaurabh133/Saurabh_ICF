<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="mainList">
         <logic:iterate id="mainListobj" name="mainList">
       
            
       
          ${mainListobj.loanID }
          $:${mainListobj.loanProduct}
          
           $:${mainListobj.loanScheme }
          $:${mainListobj.loanCustomerID }
              $:${mainListobj.loanCustomerType }
          $:${mainListobj.loanCustomerCtegory }
          $:${mainListobj.loanCustomerConstituion }
          
           $:${mainListobj.loanCustomerBusinessSeg }
          $:${mainListobj.loanIndustry }
            $:${mainListobj.loanSubIndustry }
          $:${mainListobj.loanDisbursalDate }
          $:${mainListobj.loanMaturityDate}
          
           $:${mainListobj.loanTenure }
          $:${mainListobj.loanBalanceTenure }
           $:${mainListobj.loanInstallmentNum }
          $:${mainListobj.loanAdvEMINUm }
          $:${mainListobj.loanInitRate }
          
           $:${mainListobj.loanDisbursalStatus}
          $:${mainListobj.loanNPAFlag }
          $:${mainListobj.loanDPD}
          $:${mainListobj.loanDPDString }
           $:${mainListobj.loanAssetCost}
          
         $:${mainListobj.loanAmount}
          $:${mainListobj.loanEMI }
          $:${mainListobj.loanAdvEMIAmount}
          $:${mainListobj.loanBalPrincipal}
           $:${mainListobj.loanOverduePrincipal}
	 
          
          $:${mainListobj.loanReceivedPrincipal}
          $:${mainListobj.loanOverdueInstNo }
          $:${mainListobj.loanOverdueAmount }
           $:${mainListobj.loanBalnceInstNo }
          $:${mainListobj.loanBalInstlAmount}
	 
          
          
         </logic:iterate>
         </logic:present>    
         