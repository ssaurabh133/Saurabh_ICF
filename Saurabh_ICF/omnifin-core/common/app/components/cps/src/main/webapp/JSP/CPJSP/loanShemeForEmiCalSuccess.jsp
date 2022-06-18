<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:present name="loanShemeListForEmiCal">
<logic:iterate name="loanShemeListForEmiCal" id="subloanShemeListForEmiCal">

    ${subloanShemeListForEmiCal.defMarginRate}
    $:${subloanShemeListForEmiCal.rateTYPE}
    $:${subloanShemeListForEmiCal.rateMethod}
    $:${subloanShemeListForEmiCal.baseRateType}
    $:${subloanShemeListForEmiCal.baseRate}
    $:${subloanShemeListForEmiCal.defFlatRate}
    $:${subloanShemeListForEmiCal.defEffRate}
    $:${subloanShemeListForEmiCal.defTenure}
    $:${subloanShemeListForEmiCal.repaymentFREQ}
    $:${subloanShemeListForEmiCal.installmentTYPE}
    $:${subloanShemeListForEmiCal.repaymentMODE}
    $:${subloanShemeListForEmiCal.installmentMODE}
    $:${subloanShemeListForEmiCal.repaymentType}
    $:${subloanShemeListForEmiCal.revolvingFlag}
    $:${subloanShemeListForEmiCal.minMarginRate}
    $:${subloanShemeListForEmiCal.maxMarginRate}
    $:${subloanShemeListForEmiCal.minTenure}
    $:${subloanShemeListForEmiCal.maxTenure}
    $:${subloanShemeListForEmiCal.minFinance}
    $:${subloanShemeListForEmiCal.maxFinance}
    $:${subloanShemeListForEmiCal.assetFlag}
    
   
    
</logic:iterate>

</logic:present>
	