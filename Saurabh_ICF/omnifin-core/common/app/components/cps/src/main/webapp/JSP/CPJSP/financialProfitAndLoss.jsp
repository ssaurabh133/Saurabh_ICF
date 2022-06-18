<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface to financial Profit and loss
 -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/cibilReport.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->		
	</head> 
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="checkEntityType();enableAnchor();checkChanges('profitAndLossForm');" >
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	<div id="centercolumn">
	<div id="revisedcontainer">
<logic:notPresent name="underWriterViewData">
	<html:form action="/profitAndLossBehindAction" styleId="profitAndLossForm" method="post"  enctype="multipart/form-data">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <input type="hidden" id="reloadFlag" value="${reloadFlag}" />
	   <fieldset>	  
	<legend><bean:message key="lbl.profitAndLoss"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td colspan="2"><bean:message key="lbl.dealNo"/></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true" value="${financialDetails[0].deal}"  tabindex="-1"/>

     </td>
	<td width="17%"><bean:message key="lbl.applicantName"/></td>
	<td width="28%">
	<html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" value="${financialDetails[0].customername}"/>
	</td>
		</tr>
		<!-- Added By Sarvesh -->
		<tr>
		<td colspan="2"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">

		<html:text property="entityCustomerType" styleId="entityCustomerType" styleClass="text" maxlength="50" value="${entityCustomerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'profitAndLossForm','entityCustomerName','','', '','','','entityCustomerType','lbxCustomerRoleType');changeReloadFlag();"
								value=" " name="dealButton" onblur="checkEntityType();"/>
							<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${lbxCustomerId}"/>
								<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="17%"><bean:message key="lbl.entityCustomerName"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="entityCustomerName" styleId="entityCustomerName" value="${entityCustomerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		
		<tr>
		<td colspan="2"><bean:message key="lbl.applicationDate"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="applicationDate" styleId="applicationDate" styleClass="text" maxlength="50" value="${financialDetails[0].applicationdate}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.product"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="product" styleId="product" value="${financialDetails[0].product}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.scheme"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="scheme" styleId="scheme" styleClass="text" maxlength="50" value="${financialDetails[0].scheme}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.loanAmount"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${financialDetails[0].loanAmount}" maxlength="50" tabindex="-1" readonly="true"/>
    </td>
		</tr>
		
	  <tr>
	  
	  <td colspan="2"><bean:message key="lbl.tenure"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="tenure" styleId="tenure" styleClass="text" maxlength="50" value="${financialDetails[0].tenure}" tabindex="-1" readonly="true"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.sourceType"/></td>
	<td width="28%">
	<html:hidden  styleClass="text" property="sourceType" styleId="sourceType" value="P"/>
	<html:text  styleClass="text" property="sourceDesc" styleId="sourceDesc" value="PROFIT AND LOSS A/C" maxlength="1" readonly="true"/>
	</td>

	  </tr>
	  
	   <tr>
	  <td colspan="3"><button type="reload" name="reload"  id="reload" title="Alt+U" accesskey="R" class="topformbutton2" onclick="return reloadBalanceSheet('profitAndLossForm');" ><bean:message key="button.reload"/></button></td>
	  </tr>
	  
	  	  </table>
	  	  <fieldset>	
	 		<legend><bean:message key="lbl.UploadData"/></legend>  	 
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
		  		<td><bean:message key="lbl.fileDescription"/></td>
		  		<td><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
		  		<td></td>
		  		<td>
		  				<button type="button" name="upload"  id="upload" title="Alt+U" accesskey="U" class="topformbutton2" onclick="uploadProfitAndLoss();" ><bean:message key="button.upload"/></button>
		  				<button type="button" name="errorLog" class="topformbutton2" id="errorLog" title="Alt+E" accesskey="E" onclick="generateProfitAndLossErrorLog();"><bean:message key="button.errorlog"/></button>
		  		</td>
		  		<td><button type="button" name="BalanceSheet" class="topformbutton4" id="BalanceSheet" title="Alt+B" accesskey="B" onclick="downloadFileProfitLoss('Financial(ProfitLoss).xls');">Download Template</button> 
		  		<!--<td><a href="#" onclick="downloadFile('Financial(BalanceSheet).xls');">Template Financial(BalanceSheet)</a>
     		<input type="hidden" name="Financial(BalanceSheet)" id="Financial(BalanceSheet)" value="Financial(ProfitLoss).xls"/>-->
     	</td>
			</tr>		
			</table>
		</fieldset>
	 	 <fieldset>	
		 <legend><bean:message key="lbl.pLParamDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    <td ><span>* </span><input type="checkbox" name="chkd" id="allchk" onclick="allChecked();"  checked/></td>
        <td><center><b><bean:message key="lbl.parameCode"/></b></center></td>
        <td><center><b>${requestScope.year1}</b></center></td>
        <td><center><b>${requestScope.year2}</b></center></td>
        <td><center><b>${requestScope.year3}</b></center></td>
        <td><center><b>${requestScope.year4}</b></center></td>
        <td><center><b>${requestScope.year5}</b></center></td>
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year1}"/>
	</tr>
	<tr>
	
			
			<logic:notEmpty name="profitLossAllDetailsByDeal">
			
				<logic:iterate name="profitLossAllDetailsByDeal" id="subProfitLossAllDetailsByDeal" indexId="count">
				 <logic:equal name="subProfitLossAllDetailsByDeal" property="autoCalculated" value="Y">
					<tr  class="white1">
						<logic:equal name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  /></td>
						</logic:notEqual>
					     <td ><b>${subProfitLossAllDetailsByDeal.paramName}
				            <input type="hidden" name="financialFormula" id="financialFormula" value="${subProfitLossAllDetailsByDeal.financialFormula}"/>
			
					    <input type="hidden" name="pCode" id="pCode" value="${subProfitLossAllDetailsByDeal.parameCode}"/>
					    <input type="hidden" name="financialIds" id="financialIds" value="${subProfitLossAllDetailsByDeal.financialId}"/></b></td>
						<td ><b><input class="text" name="year1" readonly="readonly" id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.firstYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td ><b><input class="text" name="year2" readonly="readonly" id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.secondYear}"" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td><b><input class="text" name="year3" readonly="readonly" id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.thirdYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td><b><input class="text" name="year4" readonly="readonly" id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td><b><input class="text" name="year5" readonly="readonly" id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
						 
					 </tr>	
	            </logic:equal>
	            <logic:equal name="subProfitLossAllDetailsByDeal" property="autoCalculated" value="N">
	            <logic:equal name="subProfitLossAllDetailsByDeal" property="negativeAllow" value="X">
					<tr  class="white1">
					
						<logic:equal name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  /></td>
						</logic:notEqual>

					
					    <td >${subProfitLossAllDetailsByDeal.paramName}
				         			
					    <input type="hidden" name="pCode" id="pCode" value="${subProfitLossAllDetailsByDeal.parameCode}"/>
					    <input type="hidden" name="financialIds" id="financialIds" value="${subProfitLossAllDetailsByDeal.financialId}"/></td>
						<td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.firstYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.secondYear}"" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.thirdYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
						 
					 </tr>	
				</logic:equal>
				<logic:equal name="subProfitLossAllDetailsByDeal" property="negativeAllow" value="A">
					<tr  class="white1">
						<logic:equal name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
					    <td >${subProfitLossAllDetailsByDeal.paramName}
				         			
					    <input type="hidden" name="pCode" id="pCode" value="${subProfitLossAllDetailsByDeal.parameCode}"/>
					    <input type="hidden" name="financialIds" id="financialIds" value="${subProfitLossAllDetailsByDeal.financialId}"/></td>
						<td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.firstYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.secondYear}"" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.thirdYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fourthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fifthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
						 
					 </tr>	
				</logic:equal>
	            </logic:equal>
	   			</logic:iterate>
	   			  <tr>
			
		</logic:notEmpty>
			<logic:notEmpty name="profitLossAllMinusDetailsByDeal">
			
				<logic:iterate name="profitLossAllMinusDetailsByDeal" id="sublist" indexId="count">
				 <logic:equal name="sublist" property="autoCalculated" value="Y">
					<tr  class="white1">
						<logic:equal name="sublist" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="sublist" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
					     <td ><b>${sublist.negativeparamName}
				            <input type="hidden" name="financialFormula" id="financialFormula" value="${sublist.financialFormula}"/>
			
					        <input type="hidden" name="negativeActive" id="negativeActive" value="Y" />
					    <input type="hidden" name="pCode1" id="pCode1" value="${sublist.negativeParamCode}"/>
					    <input type="hidden" name="financialIds1" id="financialIds1" value="${sublist.financialId}"/></b></td>
						<td ><b><input class="text" name="year" readonly="readonly" id="year10<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.firstYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td ><b><input class="text" name="year6" readonly="readonly" id="year20<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.secondYear}"" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td><b><input class="text" name="year7" readonly="readonly" id="year30<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.thirdYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td><b><input class="text" name="year8" readonly="readonly" id="year40<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${sublist.fourthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
					    <td><b><input class="text" name="year9" readonly="readonly" id="year50<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.fifthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
						 
					 </tr>	
	             </logic:equal>
	              <logic:equal name="sublist" property="autoCalculated" value="N">
					<tr  class="white1">
					<logic:equal name="sublist" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="sublist" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
					       <td >${sublist.negativeparamName}
				           
					        <input type="hidden" name="negativeActive" id="negativeActive" value="Y" />
					    <input type="hidden" name="pCode1" id="pCode1" value="${sublist.negativeParamCode}"/>
					    <input type="hidden" name="financialIds1" id="financialIds1" value="${sublist.financialId}"/></td>
						<td ><input class="text" name="year" id="year10<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.firstYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td ><input class="text" name="year6" id="year20<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.secondYear}"" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year7" id="year30<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.thirdYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year8" id="year40<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${sublist.fourthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					    <td><input class="text" name="year9" id="year50<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${sublist.fifthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
						 
					 </tr>	
	             </logic:equal>
	   		  </logic:iterate>
		</logic:notEmpty>
<logic:empty name="profitLossAllDetailsByDeal">
	<logic:empty name="profitLossAllMinusDetailsByDeal">
		<!--<logic:notPresent name="profitLossParamCodeValues">
	
			 -->
		<logic:present name="paramList">	
		<logic:iterate name="paramList" id="subParamList" indexId="count">
		 <logic:equal name="subParamList" property="autoCalculated" value="Y"> 
			<tr  class="white1">
						<logic:equal name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
		       <td ><b>${subParamList.paramName}
				            <input type="hidden" name="financialFormula" id="financialFormula" value="${subParamList.financialFormula}"/>
			
		    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
		    <input type="hidden" name="financialIds" id="financialIds" value=""/></b></td>
		    <td ><b><input class="text" name="year1" readonly="readonly" id="year1<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td ><b><input class="text" name="year2" readonly="readonly" id="year2<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td><b><input class="text" name="year3" readonly="readonly" id="year3<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td><b><input class="text" name="year4" readonly="readonly" id="year4<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td><b><input class="text" name="year5" readonly="readonly" id="year5<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		       
		 </tr>	
      </logic:equal>
       <logic:equal name="subParamList" property="autoCalculated" value="N">
       <logic:equal name="subParamList" property="negativeAllow" value="X">
			<tr  class="white1">
						<logic:equal name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
		      <td >${subParamList.paramName}
		    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
		    <input type="hidden" name="financialIds" id="financialIds" value=""/></td>
		    <td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		       
		 </tr>
	  </logic:equal>
	  <logic:equal name="subParamList" property="negativeAllow" value="A">
			<tr  class="white1">
						<logic:equal name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
		      <td >${subParamList.paramName}
		    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
		    <input type="hidden" name="financialIds" id="financialIds" value=""/></td>
		    <td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		       
		 </tr>
	  </logic:equal>  	
      </logic:equal>
   		</logic:iterate>
   
   		</logic:present>
   <!--</logic:notPresent> -->
 		<logic:present name="paramMinusList">	
	
		<logic:iterate name="paramMinusList" id="list" indexId="count">
		 <logic:equal name="list" property="autoCalculated" value="Y">
		  <tr  class="white1">
						<logic:equal name="list" property="chkValue" value="Y">
						<td>127<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="list" property="chkValue" value="Y">
						<td>128<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
		      <td ><b>${list.negativeparamName}
				            <input type="hidden" name="financialFormula" id="financialFormula" value="${list.financialFormula}"/>
			
		    <input type="hidden" name="negativeActive" id="negativeActive" value="Y" />
		    <input type="hidden" name="pCode1" id="pCode1" value="${list.negativeParamCode}"/>
		    <input type="hidden" name="financialIds1" id="financialIds1" value=""/></b></td>
		    <td ><b><input class="text" name="year" readonly="readonly" id="year10<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td ><b><input class="text" name="year6" readonly="readonly" id="year20<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td><b><input class="text" name="year7" readonly="readonly" id="year30<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td><b><input class="text" name="year8" readonly="readonly" id="year40<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    <td><b><input class="text" name="year9" readonly="readonly" id="year50<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		    
		 </tr>	
    </logic:equal>
     <logic:equal name="list" property="autoCalculated" value="N">
		  <tr  class="white1">
						<logic:equal name="list" property="chkValue" value="Y">
						<td>129<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked /></td>
						</logic:equal>
						<logic:notEqual name="list" property="chkValue" value="Y">
						<td>130<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"/></td>
						</logic:notEqual>
		   <td >${list.negativeparamName}
		    <input type="hidden" name="negativeActive" id="negativeActive" value="Y" />
		    <input type="hidden" name="pCode1" id="pCode1" value="${list.negativeParamCode}"/>
		    <input type="hidden" name="financialIds1" id="financialIds1" value=""/></td>
		    <td ><input class="text" name="year" id="year10<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td ><input class="text" name="year6" id="year20<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year7" id="year30<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year8" id="year40<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td><input class="text" name="year9" id="year50<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    
		 </tr>	
   </logic:equal>
   </logic:iterate>
   </logic:present>
    	</logic:empty>
    	</logic:empty>
	</tr>
 </table>    </td>
</tr>
</table>

	</fieldset>


<tr>

			<td>
			<button type="button" name="Save" class="topformbutton2" id="Save" onclick="saveProfitAndLoss();" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
<!--			<button type="button" value="calculate" class="topformbutton3" onclick="financialAutoCalculation();" accesskey="A" title="Alt+A"><bean:message key="button.autoCalculate" /></button>-->
			
			</td>
			
			</tr>

		</table>
		

	
	
	<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Bank Analysis Detail!!!");
	
    </script>
    </logic:present>
	
	 
	</fieldset>	
	<br/>
<span>* </span><bean:message key="lbl.starCondition"/>
</html:form>
</logic:notPresent>
<%--   -------------- -------------------- ---------View  mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">
<html:form action="/profitAndLossBehindAction" styleId="profitAndLossForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <input type="hidden" id="reloadFlag" value="${reloadFlag}" />
	   <fieldset>	  
	<legend><bean:message key="lbl.profitAndLoss"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td colspan="2"><bean:message key="lbl.dealNo"/></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true" value="${financialDetails[0].deal}"  tabindex="-1"/>

     </td>
	<td width="17%"><bean:message key="lbl.applicantName"/></td>
	<td width="28%">
	<html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" value="${financialDetails[0].customername}"/>
	</td>
		</tr>
		<!-- addded By Sarvesh -->
		<tr>
		<td colspan="2"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">

		<html:text property="entityCustomerType" styleId="entityCustomerType" styleClass="text" maxlength="50" value="${entityCustomerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'profitAndLossForm','entityCustomerName','','', '','','','entityCustomerType','lbxCustomerRoleType');changeReloadFlag();"
								value=" " name="dealButton" onblur="checkEntityType();"/>
							<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${lbxCustomerId}"/>
								<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="17%"><bean:message key="lbl.entityCustomerName"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="entityCustomerName" styleId="entityCustomerName" value="${entityCustomerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.applicationDate"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="applicationDate" styleId="applicationDate" styleClass="text" maxlength="50" value="${financialDetails[0].applicationdate}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.product"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="product" styleId="product" value="${financialDetails[0].product}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.scheme"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="scheme" styleId="scheme" styleClass="text" maxlength="50" value="${financialDetails[0].scheme}" readonly="true" tabindex="-1"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.loanAmount"/></td>
	<td width="28%">
	<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${financialDetails[0].loanAmount}" maxlength="50" tabindex="-1" readonly="true"/>
    </td>
		</tr>
		
	  <tr>
	  
	  <td colspan="2"><bean:message key="lbl.tenure"/></td>
		<td colspan="4" nowrap="nowrap">
		
		<html:text property="tenure" styleId="tenure" styleClass="text" maxlength="50" value="${financialDetails[0].tenure}" tabindex="-1" readonly="true"/>
  
     </td>
	<td width="17%"><bean:message key="lbl.sourceType"/></td>
	<td width="28%">
	<html:hidden  styleClass="text" property="sourceType" styleId="sourceType" value="P"/>
	<html:text  styleClass="text" property="sourceDesc" styleId="sourceDesc" value="PROFIT AND LOSS A/C" maxlength="1" readonly="true"/>
	</td>

	  </tr>
	  
	  	   <tr>
	  <td colspan="3"><button type="reload" name="reload"  id="reload" title="Alt+U" accesskey="R" class="topformbutton2" onclick="return reloadBalanceSheet('profitAndLossForm');" ><bean:message key="button.reload"/></button></td>
	  </tr>
	  	  </table>
	 	  <fieldset>	
		 <legend><bean:message key="lbl.pLParamDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
   		 <td ><span>* </span><input type="checkbox" name="chkd" id="allchk" onclick="allChecked();"  checked/></td>
        <td><center><b><bean:message key="lbl.parameCode"/></b></center></td>
        <td><center><b>${requestScope.year1}</b></center></td>
        <td><center><b>${requestScope.year2}</b></center></td>
        <td><center><b>${requestScope.year3}</b></center></td>
        <td><center><b>${requestScope.year4}</b></center></td>
        <td><center><b>${requestScope.year5}</b></center></td>
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year1}"/>
	</tr>
	<tr>
	
			
			<logic:notEmpty name="profitLossAllDetailsByDeal">
			
				<logic:iterate name="profitLossAllDetailsByDeal" id="subProfitLossAllDetailsByDeal" indexId="count">
				<tr  class="white1">
						<logic:equal name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true" checked /></td>
						</logic:equal>
						<logic:notEqual name="subProfitLossAllDetailsByDeal" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true"/></td>
						</logic:notEqual>
				    <td >${subProfitLossAllDetailsByDeal.paramName}
				    <input type="hidden" name="pCode" id="pCode" value="${subProfitLossAllDetailsByDeal.parameCode}"/>
				    <input type="hidden" name="financialIds" id="financialIds" value="${subProfitLossAllDetailsByDeal.financialId}"/></td>
					<td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.firstYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.secondYear}"" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.thirdYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subProfitLossAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					 
				 </tr>	
	 
	   			</logic:iterate>
	   			  <tr>
			
		</logic:notEmpty>
			<logic:notEmpty name="profitLossAllMinusDetailsByDeal">
			
				<logic:iterate name="profitLossAllMinusDetailsByDeal" id="sublist" indexId="count">
				<tr  class="white1">
						<logic:equal name="sublist" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true" checked /></td>
						</logic:equal>
						<logic:notEqual name="sublist" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true"/></td>
						</logic:notEqual>
				    <td >${sublist.negativeparamName}
				        <input type="hidden" name="negativeActive" id="negativeActive" value="Y" />
				    <input type="hidden" name="pCode1" id="pCode1" value="${sublist.negativeParamCode}"/>
				    <input type="hidden" name="financialIds1" id="financialIds1" value="${sublist.financialId}"/></td>
					<td ><input class="text" name="year" id="year10<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${sublist.firstYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td ><input class="text" name="year6" id="year20<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${sublist.secondYear}"" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year7" id="year30<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${sublist.thirdYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year8" id="year40<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;"  value="${sublist.fourthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year9" id="year50<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${sublist.fifthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					 
				 </tr>	
	 
	   			</logic:iterate>
		</logic:notEmpty>
<logic:empty name="profitLossAllDetailsByDeal">
	<logic:empty name="profitLossAllMinusDetailsByDeal">
		<!--<logic:notPresent name="profitLossParamCodeValues">
	
			 -->
		<logic:present name="paramList">	
		<logic:iterate name="paramList" id="subParamList" indexId="count">
		<tr  class="white1">
						<logic:equal name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true" checked /></td>
						</logic:equal>
						<logic:notEqual name="subParamList" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true"/></td>
						</logic:notEqual>
	    <td >${subParamList.paramName}
	    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
	    <input type="hidden" name="financialIds" id="financialIds" value=""/></td>
	    <td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	       
	 </tr>	
 
   		</logic:iterate>
   
   		</logic:present>
   <!--</logic:notPresent> -->
 		<logic:present name="paramMinusList">	
	
		<logic:iterate name="paramMinusList" id="list" indexId="count">
		<tr  class="white1">
						<logic:equal name="list" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true" checked /></td>
						</logic:equal>
						<logic:notEqual name="list" property="chkValue" value="Y">
						<td><input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" disabled="true"/></td>
						</logic:notEqual>
	    <td >${list.negativeparamName}
	    <input type="hidden" name="negativeActive" id="negativeActive" value="Y" />
	    <input type="hidden" name="pCode1" id="pCode1" value="${list.negativeParamCode}"/>
	    <input type="hidden" name="financialIds1" id="financialIds1" value=""/></td>
	    <td ><input class="text" name="year" id="year10<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td ><input class="text" name="year6" id="year20<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year7" id="year30<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year8" id="year40<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year9" id="year50<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    
	 </tr>	
 
   </logic:iterate>
   </logic:present>
    	</logic:empty>
    	</logic:empty>
	</tr>
 </table>    </td>
</tr>
</table>

	</fieldset>




		</table>
		

	
	
	<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Bank Analysis Detail!!!");
	
    </script>
    </logic:present>
	
	 
	</fieldset>	
	<br/>
<span>* </span><bean:message key="lbl.starCondition"/>
</html:form>
</logic:present>
</div>



</div>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		location.href="profitAndLossBehindAction.do?method=profitAndLossBehindDetail";
		
	}
	
	
	</script>
</logic:present>
<logic:present name="notForward">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.notForward" />")
		
</script>
</logic:present>
<logic:present name="uploadError">
	<script type="text/javascript">
	alert('${uploadError}');
	</script>
</logic:present>
<script>
	setFramevalues("profitAndLossForm");
</script>


</body>
</html:html>