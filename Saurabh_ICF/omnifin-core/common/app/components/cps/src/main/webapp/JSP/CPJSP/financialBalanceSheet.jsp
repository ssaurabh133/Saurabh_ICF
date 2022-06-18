<!--Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface to financial balance sheet 
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
  		<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/fileTackingSystem.js"></script> --%>
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
	<body onunload="closeAllLovCallUnloadBody();" onload="checkEntityType();enableAnchor();checkChanges('financialBalacnceSheetForm');" >
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	<logic:notPresent name="underWriterViewData">
	<html:form action="/balanceSheetBehindAction" styleId="financialBalacnceSheetForm" method="post" enctype="multipart/form-data">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	 <input type="hidden" id="reloadFlag" value="${reloadFlag}" />
	   <fieldset>	  
	<legend><bean:message key="lbl.balanceSheetdetails"/></legend>
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
		<!-- added By Sarvesh -->
		<tr>
		<td colspan="2"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">

		<html:text property="entityCustomerType" styleId="entityCustomerType" styleClass="text" maxlength="50" value="${entityCustomerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'financialBalacnceSheetForm','entityCustomerName','','', '','','','entityCustomerType','lbxCustomerRoleType');changeReloadFlag();"
								value=" " name="dealButton" onblur="checkEntityType();"/>
							<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${lbxCustomerId}"/>
								<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${lbxCustomerRoleType}" />
								
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
		<html:text  styleClass="text" property="sourceDesc" styleId="sourceDesc" value="BALANCE SHEET" maxlength="1" readonly="true"/>
		<html:hidden  styleClass="text" property="sourceType" styleId="sourceType" value="B"/>
    </td>

	  </tr>
	  <tr>
	  <td colspan="3"><button type="reload" name="reload"  id="reload" title="Alt+U" accesskey="R" class="topformbutton2" onclick="return reloadBalanceSheet('financialBalacnceSheetForm');" ><bean:message key="button.reload"/></button></td>
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
		  				<button type="button" name="upload"  id="upload" title="Alt+U" accesskey="U" class="topformbutton2" onclick="uploadBalanceSheet();" ><bean:message key="button.upload"/></button>
		  				<button type="button" name="errorLog" class="topformbutton2" id="errorLog" title="Alt+E" accesskey="E" onclick="generateBalacnceSheetErrorLog();"><bean:message key="button.errorlog"/></button>
		  		</td>
				<td><button type="button" name="BalanceSheet" class="topformbutton4" id="BalanceSheet" title="Alt+B" accesskey="B" onclick="downloadFile('Financial(BalanceSheet).xls');">Download Template</button> 
		  		<!--<td><a href="#" onclick="downloadFile('Financial(BalanceSheet).xls');">Template Financial(BalanceSheet)</a>
     		<input type="hidden" name="Financial(BalanceSheet)" id="Financial(BalanceSheet)" value="Financial(BalanceSheet).xls"/>-->
     	</td>
		</tr>		
		</table>
	</fieldset>
	<fieldset>	
		 <legend><bean:message key="lbl.balParamDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    	<td ><span>*</span><input type="checkbox" name="chkd" id="allchk" onclick="allChecked();"  checked/></td>
        <td><center><b><bean:message key="lbl.subType"/></b></center></td>
        <td><center><b><bean:message key="lbl.parameCode"/></b></center></td>
        <td><center ><b>${requestScope.year1}</b></center><input type="hidden" name="yr01" id="yr01" value="${requestScope.year1}"/></td>
        <td><center><b>${requestScope.year2}</b></center><input type="hidden" name="yr02" id="yr02" value="${requestScope.year2}"/> </td>
        <td><center><b>${requestScope.year3}</b></center><input type="hidden" name="yr03" id="yr03" value="${requestScope.year3}"/></td>
        <td><center><b>${requestScope.year4}</b></center><input type="hidden" name="yr04" id="yr04" value="${requestScope.year4}"/></td>
        <td><center><b>${requestScope.year5}</b></center><input type="hidden" name="yr05" id="yr05" value="${requestScope.year5}"/></td>
		
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year1}"/>
	</tr>
	<tr>
	
	
<logic:notEmpty name="balanceSheetAllDetailsByDeal">
	<logic:iterate name="balanceSheetAllDetailsByDeal" id="subBalanceSheetAllDetailsByDeal" indexId="count">
		<logic:equal name="subBalanceSheetAllDetailsByDeal" property="autoCalculated" value="Y">
		<tr  class="white1">
		
		<td>
		<logic:equal name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
		
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
		<td><b><input class="text" name="year4" readonly="readonly" id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${subBalanceSheetAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		<td><b><input class="text" name="year5" readonly="readonly" id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></b></td>
		</tr>	
	    </logic:equal>
	    <logic:equal name="subBalanceSheetAllDetailsByDeal" property="autoCalculated" value="N">
	    	<logic:equal name="subBalanceSheetAllDetailsByDeal" property="negativeAllow" value="X">
			<tr  class="white1">
			<td>
		<logic:equal name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
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
			<td><input class="text" name="year4"  id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${subBalanceSheetAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td><input class="text" name="year5"  id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			</tr>	
	        </logic:equal>
	        <logic:equal name="subBalanceSheetAllDetailsByDeal" property="negativeAllow" value="A">
			<tr  class="white1">
			<td>
		<logic:equal name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
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
			<td><input class="text" name="year4"  id="year4<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;"  value="${subBalanceSheetAllDetailsByDeal.fourthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			<td><input class="text" name="year5"  id="year5<%= count.intValue()+1 %>" maxlength="22" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.fifthYear}" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			</tr>	
	        </logic:equal>
	    </logic:equal>
	   	</logic:iterate>
</logic:notEmpty>
<logic:empty name="balanceSheetAllDetailsByDeal">
<logic:present name="paramList">	
<logic:iterate name="paramList" id="subParamList" indexId="count">
	<logic:equal name="subParamList" property="autoCalculated" value="Y">
	<tr  class="white1">
	<td>
		<logic:equal name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
	<td ><b>${subParamList.subTypeDesc}
		<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subParamList.subType}"/></b>
	</td>
	<td ><b>${subParamList.paramName}
		 <input type="hidden" name="financialFormula" id="financialFormula" value="${subParamList.financialFormula}"/>
		 <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
		 <input type="hidden" name="financialIds" id="financialIds" value=""/></b>
	</td>
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
	<td>
		<logic:equal name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
	<td >${subParamList.subTypeDesc}
			<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subParamList.subType}"/>
	</td>
	<td >${subParamList.paramName}
		    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
		    <input type="hidden" name="financialIds" id="financialIds" value=""/>
	</td>
	<td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	<td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" value="0" style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	</tr>
	</logic:equal>
	<logic:equal name="subParamList" property="negativeAllow" value="A">
	<tr  class="white1">
	<td>
		<logic:equal name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
	<td >${subParamList.subTypeDesc}
		<input type="hidden" name="subType" id="subType<%= count.intValue()+1 %>" value="${subParamList.subType}"/>
	</td>
	<td >${subParamList.paramName}
	    <input type="hidden" name="pCode" id="pCode" value="${subParamList.parameCode}"/>
	    <input type="hidden" name="financialIds" id="financialIds" value=""/>
	</td>
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
</logic:empty>
 
	</tr>
 </table>    </td>
</tr>
</table>

</fieldset>
<tr>	  

			<td  colspan="10" class="white">
			<logic:present name="financialDealId">
			    <input type="hidden" id="dealId" value="${sessionScope.financialDealId}"/>
		    </logic:present>
			 <button type="button" name="Save" class="topformbutton2" id="Save" onclick="saveFinancialBalanceSheet();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>	
			 <button type="button" name="button"  id="Forward" title="Alt+F" accesskey="F" class="topformbutton3" onclick="return financialAnalysisForward();"><bean:message key="button.forward" /></button>
<!--             <button type="button" value="calculate" class="topformbutton3" onclick="financialAutoCalculation();" accesskey="A" title="Alt+A"><bean:message key="button.autoCalculate" /></button>-->
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
	<html:form action="/balanceSheetBehindAction" styleId="financialBalacnceSheetForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	  <input type="hidden" id="reloadFlag" value="${reloadFlag}" />
	   <fieldset>	  
	<legend><bean:message key="lbl.balanceSheetdetails"/></legend>
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
		<!-- Added  By Sarvesh -->
		<tr>
		<td colspan="2"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">

		<html:text property="entityCustomerType" styleId="entityCustomerType" styleClass="text" maxlength="50" value="${entityCustomerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'financialBalacnceSheetForm','entityCustomerName','','', '','','','entityCustomerType','lbxCustomerRoleType');changeReloadFlag();"
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
		<html:text  styleClass="text" property="sourceDesc" styleId="sourceDesc" value="BALANCE SHEET" maxlength="1" readonly="true"/>
		<html:hidden  styleClass="text" property="sourceType" styleId="sourceType" value="B"/>
    </td>

	  </tr>
	  
	   <tr>
	  <td colspan="3"><button type="reload" name="reload"  id="reload" title="Alt+U" accesskey="R" class="topformbutton2" onclick="return reloadBalanceSheet('financialBalacnceSheetForm');" ><bean:message key="button.reload"/></button></td>
	  </tr>
	  
	  	  </table>
	  <fieldset>	
		 <legend><bean:message key="lbl.balParamDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    	<td ><span>* </span><input type="checkbox" name="chkd" id="allchk" onclick="allChecked();" disabled checked/></td>
        <td><center><b><bean:message key="lbl.subType"/></b></center></td>
        <td><center><b><bean:message key="lbl.parameCode"/></b></center></td>
        <td><center><b>${requestScope.year1}</b></center></td>
        <td><center><b>${requestScope.year2}</b></center></td>
        <td><center><b>${requestScope.year3}</b></center></td>
        <td><center><b>${requestScope.year4}</b></center></td>
        <td><center><b>${requestScope.year5}</b></center></td>
		<html:hidden property="currBusinessDateYear" styleId="currBusinessDateYear" value="${requestScope.year1}"/>
	</tr>
	<tr>
	
	
		<logic:notEmpty name="balanceSheetAllDetailsByDeal">
			
				<logic:iterate name="balanceSheetAllDetailsByDeal" id="subBalanceSheetAllDetailsByDeal" indexId="count">
				<tr  class="white1">
				<td>
		<logic:equal name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subBalanceSheetAllDetailsByDeal" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
				    <td >${subBalanceSheetAllDetailsByDeal.subTypeDesc}
					<input type="hidden" name="subType" id="subType" value="${subBalanceSheetAllDetailsByDeal.subType}"/>
					</td>
				    <td >${subBalanceSheetAllDetailsByDeal.paramName}
				    <input type="hidden" name="pCode" id="pCode" value="${subBalanceSheetAllDetailsByDeal.parameCode}"/>
				    <input type="hidden" name="financialIds" id="financialIds" value="${subBalanceSheetAllDetailsByDeal.financialId}"/></td>
					<td ><input class="text" name="year1" id="year1<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.firstYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td ><input class="text" name="year2" id="year2<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.secondYear}"" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year3" id="year3<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.thirdYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year4" id="year4<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;"  value="${subBalanceSheetAllDetailsByDeal.fourthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
				    <td><input class="text" name="year5" id="year5<%= count.intValue()+1 %>" maxlength="22" readonly="readonly" style="text-align: right;" value="${subBalanceSheetAllDetailsByDeal.fifthYear}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
					 
				 </tr>	
	 
	   			</logic:iterate>
		</logic:notEmpty>
		
		
		<logic:notEmpty name="balanceSheetAllMinusDetailsByDeal">
			
				<logic:iterate name="balanceSheetAllMinusDetailsByDeal" id="sublist" indexId="count">
				<tr  class="white1">
					<td>
		<logic:equal name="sublist" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="sublist" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
				    <td >${sublist.subTypeDesc}
					<input type="hidden" name="subType" id="subType" value="${sublist.subType}"/>
					</td>
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
	<logic:empty name="balanceSheetAllDetailsByDeal">
<logic:empty name="balanceSheetAllMinusDetailsByDeal">
		<logic:present name="paramList">	
	
		<logic:iterate name="paramList" id="subParamList" indexId="count">
		<tr  class="white1">
		<td>
		<logic:equal name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="subParamList" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
		<td >${subParamList.subTypeDesc}
		<input type="hidden" name="subType" id="subType" value="${subParamList.subType}"/>
		</td>
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
   <logic:present name="paramMinusList">	
	
		<logic:iterate name="paramMinusList" id="list" indexId="count">
		<tr  class="white1">
	<td>
		<logic:equal name="list" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>" checked />
		</logic:equal>
		<logic:notEqual name="list" property="chkValue" value="Y">
		<input type="checkbox" name="chk"  id="chk<%= count.intValue()+1 %>"  />
		</logic:notEqual>
		</td>
		<td >${list.subTypeDesc}
		<input type="hidden" name="subType" id="subType" value="${list.subType}"/>
		</td>
	    <td >${list.negativeparamName}
	    <input type="hidden" name="negativeActive" id="negativeActive" value="Y" />
	    <input type="hidden" name="pCode1" id="pCode1" value="${list.negativeParamCode}"/>
	    <input type="hidden" name="financialIds1" id="financialIds1" value=""/></td>
	    <td ><input class="text" name="year" id="year10<%= count.intValue()+1 %>" readonly="readonly" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td ><input class="text" name="year6" id="year20<%= count.intValue()+1 %>" readonly="readonly" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year7" id="year30<%= count.intValue()+1 %>" readonly="readonly" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year8" id="year40<%= count.intValue()+1 %>" readonly="readonly" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    <td><input class="text" name="year9" id="year50<%= count.intValue()+1 %>" readonly="readonly" maxlength="22" value="0" style="text-align: right;" onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	    
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
	  
           <tr>
			<td  colspan="10" class="white">
			<logic:present name="financialDealId">
			    <input type="hidden" id="dealId" value="${sessionScope.financialDealId}"/>
		    </logic:present>
			
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
</logic:present>
</div>



</div>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='B')
	{
			alert("<bean:message key="lbl.firstUpdateThenForward" />");
			location.href="balanceSheetBehindAction.do?method=balanceSheetBehindDetail";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='BB')
	{
			alert("<bean:message key="lbl.updateRatioAnalysis" />");
			location.href="balanceSheetBehindAction.do?method=balanceSheetBehindDetail";   
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.forwardSuccess" />");
		parent.location.href="financialAnalysisSearchBehindAction.do";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		location.href="balanceSheetBehindAction.do?method=balanceSheetBehindDetail";
	}
	
	</script>
</logic:present>

<logic:present name="uploadError">
	<script type="text/javascript">
	alert('${uploadError}');
	</script>
</logic:present>
	

<logic:present name="notForward">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.notForward" />");
		
</script>
</logic:present>
<logic:present name="checkStageM">

	<script type="text/javascript">
	
		alert('${checkStageM}');
		
</script>
</logic:present>
<script>
	setFramevalues("financialBalacnceSheetForm");
</script>

</body>
</html:html>