<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<fieldset>	
		 <legend><bean:message key="lbl.balParamDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td><center><b><bean:message key="lbl.subType"/></b></center></td>
        <td><center><b><bean:message key="lbl.parameCode"/></b></center></td>
        <td><center ><b>${requestScope.year1}</b></center><input type="hidden" name="yr01" id="yr01" value="${requestScope.year1}"/></td>
        <td><center><b>${requestScope.year2}</b></center><input type="hidden" name="yr02" id="yr02" value="${requestScope.year2}"/> </td>
        <td><center><b>${requestScope.year3}</b></center><input type="hidden" name="yr03" id="yr03" value="${requestScope.year3}"/></td>
       <%--  <td><center><b>${requestScope.year4}</b></center><input type="hidden" name="yr04" id="yr04" value="${requestScope.year4}"/></td>
        <td><center><b>${requestScope.year5}</b></center><input type="hidden" name="yr05" id="yr05" value="${requestScope.year5}"/></td> --%>
		
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year1}"/>
	</tr>
	<tr>
	
	
<logic:notEmpty name="balanceSheetAllDetailsByDeal">
	<logic:iterate name="balanceSheetAllDetailsByDeal" id="subBalanceSheetAllDetailsByDeal" indexId="count">
		<logic:equal name="subBalanceSheetAllDetailsByDeal" property="autoCalculated" value="Y">
		<tr  class="white1">
		<td>
			<b>${subBalanceSheetAllDetailsByDeal.subTypeDesc}
			<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subBalanceSheetAllDetailsByDeal.subType}"/>
			</b>
		</td>
		<td>
			<b>
			${subBalanceSheetAllDetailsByDeal.paramName}
			<input type="hidden" name="financialFormula" id="financialFormula" value="${subBalanceSheetAllDetailsByDeal.financialFormula}"/>
			<input type="hidden" name="pCode" id="pCode" value="${subBalanceSheetAllDetailsByDeal.parameCode}"/>
			<input type="hidden" name="financialIds" id="financialIds" value="${subBalanceSheetAllDetailsByDeal.financialId}"/>
			</b>
		</td>
		<td><b><input class="text" name="year1" readonly="readonly" id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.firstYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		<td ><b><input class="text" name="year2" readonly="readonly" id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.secondYear}"" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		<td><b><input class="text" name="year3" readonly="readonly" id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.thirdYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		<%-- <td><b><input class="text" name="year4" readonly="readonly" id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${subBalanceSheetAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		<td><b><input class="text" name="year5" readonly="readonly" id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		 --%></tr>	
	    </logic:equal>
	    <logic:equal name="subBalanceSheetAllDetailsByDeal" property="autoCalculated" value="N">
	    	<logic:equal name="subBalanceSheetAllDetailsByDeal" property="negativeAllow" value="X">
			<tr  class="white1">
			<td >${subBalanceSheetAllDetailsByDeal.subTypeDesc}
				<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subBalanceSheetAllDetailsByDeal.subType}"/>
			</td>
			<td >${subBalanceSheetAllDetailsByDeal.paramName}
			    <input type="hidden" name="pCode" id="pCode" value="${subBalanceSheetAllDetailsByDeal.parameCode}"/>
			    <input type="hidden" name="financialIds" id="financialIds" value="${subBalanceSheetAllDetailsByDeal.financialId}"/>
			</td>
			<td><input class="text" name="year1"  id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.firstYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td ><input class="text" name="year2"  id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.secondYear}"" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td><input class="text" name="year3"  id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.thirdYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<%-- <td><input class="text" name="year4"  id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${subBalanceSheetAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td><input class="text" name="year5"  id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			 --%></tr>	
	        </logic:equal>
	        <logic:equal name="subBalanceSheetAllDetailsByDeal" property="negativeAllow" value="A">
			<tr  class="white1">
			<td >${subBalanceSheetAllDetailsByDeal.subTypeDesc}
				<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subBalanceSheetAllDetailsByDeal.subType}"/>
			</td>
			<td >${subBalanceSheetAllDetailsByDeal.paramName}
			    <input type="hidden" name="pCode" id="pCode" value="${subBalanceSheetAllDetailsByDeal.parameCode}"/>
			    <input type="hidden" name="financialIds" id="financialIds" value="${subBalanceSheetAllDetailsByDeal.financialId}"/>
			</td>															
			<td ><input class="text" name="year1"  id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.firstYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td ><input class="text" name="year2"  id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.secondYear}"" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td><input class="text" name="year3"  id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.thirdYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<%-- <td><input class="text" name="year4"  id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${subBalanceSheetAllDetailsByDeal.fourthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td><input class="text" name="year5"  id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.fifthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			 --%></tr>	
	        </logic:equal>
	    </logic:equal>
	   	</logic:iterate>
</logic:notEmpty>
<logic:empty name="balanceSheetAllDetailsByDeal">
<logic:present name="paramList">	
<logic:iterate name="paramList" id="subParamList" indexId="count">
	<logic:equal name="subParamList" property="autoCalculated" value="Y">
	<tr  class="white1">
	<td ><b>${subParamList.subTypeDesc}
		<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subParamList.subType}"/></b>
	</td>
	<td ><b>${subParamList.paramName}
		 <input type="hidden" name="financialFormula" id="financialFormula" value="${subParamList.financialFormula}"/>
		 <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
		 <input type="hidden" name="financialIds" id="financialIds" value=""/></b>
	</td>
	<td ><b><input class="text" name="year1" readonly="readonly" value="${subParamList.paramValue}" id="year1<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
	<td ><b><input class="text" name="year2" readonly="readonly" value="${subParamList.paramValue}" id="year2<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
	<td><b><input class="text" name="year3" readonly="readonly" value="${subParamList.paramValue}" id="year3<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
	<%-- <td><b><input class="text" name="year4" readonly="readonly" id="year4<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
	<td><b><input class="text" name="year5" readonly="readonly" id="year5<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
	 --%></tr>	
    </logic:equal>
    <logic:equal name="subParamList" property="autoCalculated" value="N">
    <logic:equal name="subParamList" property="negativeAllow" value="X">
	<tr  class="white1">
	<td >${subParamList.subTypeDesc}
			<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subParamList.subType}"/>
	</td>
	<td >${subParamList.paramName}
		    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
		    <input type="hidden" name="financialIds" id="financialIds" value=""/>
	</td>
	<td ><input class="text" name="year1" value="${subParamList.paramValue}" id="year1<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td ><input class="text" name="year2" value="${subParamList.paramValue}" id="year2<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td><input class="text" name="year3" value="${subParamList.paramValue}" id="year3<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<%-- <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	 --%></tr>
	</logic:equal>
	<logic:equal name="subParamList" property="negativeAllow" value="A">
	<tr  class="white1">
	<td >${subParamList.subTypeDesc}
		<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subParamList.subType}"/>
	</td>
	<td >${subParamList.paramName}
	    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
	    <input type="hidden" name="financialIds" id="financialIds" value=""/>
	</td>
	<td ><input class="text" name="year1" value="${subParamList.paramValue}" id="year1<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td ><input class="text" name="year2" value="${subParamList.paramValue}" id="year2<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td><input class="text" name="year3" value="${subParamList.paramValue}" id="year3<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<%-- <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	 --%></tr>
	</logic:equal>		
    </logic:equal>
</logic:iterate>
</logic:present>
</logic:empty>
 
	</tr>
 </table>    </td>
</tr>
</table>

</fieldset>

	
