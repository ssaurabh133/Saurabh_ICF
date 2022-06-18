
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:present name="loanShemeList">
<logic:iterate name="loanShemeList" id="subloanShemeList">

    ${subloanShemeList.defMarginRate}
    $:${subloanShemeList.rateTYPE}
    $:${subloanShemeList.rateMethod}
    $:${subloanShemeList.baseRateType}
    $:${subloanShemeList.baseRate}
    $:${subloanShemeList.defFlatRate}
    $:${subloanShemeList.defEffRate}
    $:${subloanShemeList.defTenure}
    $:${subloanShemeList.repaymentFREQ}
    $:${subloanShemeList.installmentTYPE}
    $:${subloanShemeList.repaymentMODE}
    $:${subloanShemeList.installmentMODE}
    $:${subloanShemeList.repaymentType}
    $:${subloanShemeList.revolvingFlag}
    $:${subloanShemeList.minMarginRate}
    $:${subloanShemeList.maxMarginRate}
    $:${subloanShemeList.minTenure}
    $:${subloanShemeList.maxTenure}
    $:${subloanShemeList.minFinance}
    $:${subloanShemeList.maxFinance}
    $:${subloanShemeList.assetFlag}
    $:${subloanShemeList.daysBasis}
    $:${subloanShemeList.fixPriod}
    $:${subloanShemeList.minFlatRate}
    $:${subloanShemeList.maxFlatRate}
    $:${subloanShemeList.minEffectiveRate}
    $:${subloanShemeList.maxEffectiveRate}
 
</logic:iterate>

</logic:present>
 $:
    <logic:present name="cycleDueDay">
 <div id="cycleDue">
 <select name="cycleDate" class="text" id="cycleDate"  onchange="nullNextDue();getDueDay();">
<option value="">---Select----</option>
<logic:iterate id="cycleDueDayObj" name="cycleDueDay"> 
<option value="<bean:write name="cycleDueDayObj" property="id" />"><bean:write name="cycleDueDayObj" property="name" /></option>
</logic:iterate>
 </select>
 </div>
    </logic:present>
	