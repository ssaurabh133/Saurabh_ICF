<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<logic:present name="loanDetailList" >
      <logic:iterate name="loanDetailList" id="subloanList">
			${subloanList.product }    
			$:${subloanList.scheme }
			$:${subloanList.sanctionedLoanAmount }
			$:${subloanList.utilizeLoanAmount }
			$:${subloanList.sanctionedValidtill }
			$:${subloanList.assetCost }
			$:${subloanList.margin }
			$:${subloanList.marginAmount }
			$:${subloanList.loanAmount }
			$:${subloanList.tenureMonths }
			$:${subloanList.rateType }
			$:${subloanList.dealRateMethod }
			$:${subloanList.baseRateType }
			$:${subloanList.baseRate }
			$:${subloanList.markUp }
			$:${subloanList.effectiveRate }
			$:${subloanList.repaymentType }
			$:${subloanList.installmentType }
			$:${subloanList.frequency }
			$:${subloanList.repaymentMode }			
            $:${subloanList.loanId }
			$:${subloanList.dealNo }
			$:${subloanList.dealInstallmentMode }
			$:${subloanList.typeOfDisbursal }
			$:${subloanList.noOfDisbursal }			
			$:${subloanList.loanBranch }
			$:${subloanList.loanIndustry }
			$:${subloanList.loanSubIndustry }
			$:${subloanList.loanCustomerId }
			$:${subloanList.loanCustomerType }			
			$:${subloanList.loanCustomerExisting }			
			$:${subloanList.showProduct }			
			$:${subloanList.showScheme }			
			$:${subloanList.productCat }			
			$:${subloanList.loanNoofInstall }			
			$:${subloanList.utilizeLoanAmount }			
			$:${subloanList.productType }			
			$:${subloanList.sanctionedDate }			
			$:${subloanList.sectorType }			
			$:${subloanList.showSectorType }
			$:${subloanList.loanId }			
			$:${subloanList.installments }			
			$:${subloanList.dueDay }			
			$:${subloanList.dealIrr1 }			
			$:${subloanList.dealIrr2 }			
			$:${subloanList.dealEffectiveRate }
			$:${subloanList.sanctionDate }
			$:${subloanList.dealFlatRate }
			$:${subloanList.oneDealOneLoan}
			$:${subloanList.maturityDate}
			$:${subloanList.repayEffectiveDate }
			$:${subloanList.nextDueDate }
			$:${subloanList.agreementDate}
			$:${subloanList.ltvPerc}
			$:${subloanList.minFlatRate}
			$:${subloanList.maxFlatRate}
			$:${subloanList.minEffRate}
			$:${subloanList.maxEffRate}
			$:${subloanList.minTenure}			
			$:${subloanList.maxTenure}
			$:${subloanList.lbxareaCodeVal}			
			$:${subloanList.areaCodename}
			$:${subloanList.assetFlag}
			$:${subloanList.interestCalc}
			$:${subloanList.showInterestCalc}
			$:${subloanList.daysBasis}
			$:${subloanList.tenureInDay}
			$:${subloanList.showInstallment}
			$:${subloanList.netLtv}
			$:${subloanList.fixPriod}
			$:${subloanList.noOfAsset}
			$:${subloanList.formNo}
			$:${subloanList.totalVatAmt}
			$:${subloanList.vatPercent}
			$:${subloanList.serviceTax}
			$:${subloanList.serviceAmount}
			$:${subloanList.creditPeriod}
			$:${subloanList.interestCompoundingFrequency}
			$:${subloanList.interestCalculationMethod}
			$:${subloanList.interestFrequency}
			$:${subloanList.interestDueDate}
			$:${subloanList.grossBlock}
			$:${subloanList.netBlock}
	</logic:iterate>
    </logic:present>     
         
          $:
			    <logic:present name="cycleDueDay">
			 <div id="cycleDue1">
			 <select name="dueDay" class="text" id="dueDay"  onchange="nullNextDue();getDueDay('dueDay');">
			<option value="">---Select----</option>
			<logic:iterate id="cycleDueDayObj" name="cycleDueDay"> 
			<option value="<bean:write name="cycleDueDayObj" property="id" />"><bean:write name="cycleDueDayObj" property="name" /></option>
			</logic:iterate>
			 </select>
			 </div>
			    </logic:present>
		 $:
			    <logic:present name="cycleDueDay">
			 <div id="cycleDue2">
			 <select name="dueDay" class="text" id="dueDayOneLoan"  onchange="nullNextDue();getDueDay('dueDayOneLoan');">
			<option value="">---Select----</option>
			<logic:iterate id="cycleDueDayObj" name="cycleDueDay"> 
			<option value="<bean:write name="cycleDueDayObj" property="id" />"><bean:write name="cycleDueDayObj" property="name" /></option>
			</logic:iterate>
			 </select>
			 </div>
			    </logic:present>
			    <logic:present name="loanDetailList" >
      <logic:iterate name="loanDetailList" id="subloanList">		
			$:${subloanList.insurancePremium}
			$:${subloanList.requestedLoamt}
	</logic:iterate>
    </logic:present>
    	    <logic:present name="loanDetailList" >
      <logic:iterate name="loanDetailList" id="subloanList">
	
		$:${subloanList.editDueDate}
		$:${subloanList.businessType}
			</logic:iterate>
    </logic:present> 