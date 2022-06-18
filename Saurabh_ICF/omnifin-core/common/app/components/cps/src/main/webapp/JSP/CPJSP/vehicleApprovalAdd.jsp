<!--Purpose  : Information of Vehicle Approval Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
 
         <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>

       <script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/vehicalApprovalScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/vehicalApprovalScript.js"></script>

</head>

<body onload="enableAnchor();checkVehicleType();init_fields();">

<!--  <div id="centercolumn">
	
<div id="processingImage">
-->

<html:form styleId="vehicleApprovalForm"  method="post"  action="/vehicleApprovalGridDispatchAction" >
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<input type="hidden" name="vehicleApprovalID" value="${requestScope.list[0].vehicleApprovalID}" id="vehicleApprovalID"/>
<fieldset>
<legend><bean:message key="lbl.vehicleApprovalGrid" /></legend>
  <table width="100%">
       <input type="hidden" name="gridId" id="gridId" value="${requestScope.list[0].gridId}" />
    
   <tr>
	<td width="220" >
	<bean:message key="lbl.product" /><span><font color="red">*</font></span></td>
	</td>
	<td>
   <html:text property="product" styleId="product" tabindex="-1" styleClass="text" value="${requestScope.list[0].product}" readonly="true" ></html:text>
	<input type="button" class="lovbutton" id="productButton" onclick="openLOVCommon(17,'vehicleApprovalForm','product','','scheme','lbxScheme','','','lbxProductID')"
	value=" "  name="productButton" />
      <input type="hidden" name="lbxProductID" id="lbxProductID" value="${requestScope.list[0].lbxProductID}" />
     </td> 
     	<td width="220" >
		<bean:message key="lbl.scheme" /><span><font color="red">*</font></span></td>
		</td>
        <td>
          <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value="${requestScope.list[0].scheme}" tabindex="-1"/>
          <html:hidden  property="lbxScheme" styleId="lbxScheme" value="${requestScope.list[0].lbxScheme}" />
          <html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(25,'vehicleApprovalForm','scheme','product','lbxScheme', 'lbxProductID','Select Product LOV','','schemeId')" value=" " styleClass="lovbutton"></html:button>  								    
          <input type="hidden" id="schemeId" name="schemeId"/>
        </td> 
       </tr>
     <tr>
     <td><bean:message key="lbl.vehicleType"/><span><font color="red">*</font></span></td>
		<td>
	    <html:select property="vehicleType" styleId="vehicleType" styleClass="text"  value="${requestScope.list[0].vehicleType}" onchange="return checkVehicleType()">
	    <html:option value="N">NEW</html:option>
		<html:option value="O">OLD</html:option> 
		</html:select></td>
     <td width="220" >
	<bean:message key="lbl.manufact" /><span><font color="red">*</font></span></td>
     <td>
     
      <html:text property="manufacturer" tabindex="-1" styleClass="text" readonly="true" styleId="manufacturer" maxlength="25" value="${requestScope.list[0].manufacturer}" /> 
      <html:hidden property="manufactId" styleId="manufactId" value="${requestScope.list[0].manufactId}"/>
      <html:button property="manufactButton" styleId="manufactButton" tabindex="" onclick="openLOVCommon(272,'vehicleApprovalForm','manufactId','','', '','','','manufacturer')" value=" " styleClass="lovbutton"  ></html:button>
      <html:hidden property="manufacturerId" styleId="manufacturerId" value=""/>  
          
     </td>
         
     </tr>         
         <tr>
           <td width="220" >
	<bean:message key="lbl.model" /><span><font color="red">*</font></span></td>
     <td>
      <html:text property="modelDesc" tabindex="-1" styleClass="text" readonly="true" styleId="modelDesc" maxlength="25" value="${requestScope.list[0].modelDesc}" /> 
      <html:hidden property="modelDescId" styleId="modelDescId" value="${requestScope.list[0].modelDescId}"/>
      <html:button property="modelButton" styleId="modelButton" tabindex="" onclick="openLOVCommon(19241,'vehicleApprovalForm','modelDescId','','', '','','','modelDesc')" value=" " styleClass="lovbutton"  ></html:button>
      <html:hidden property="modelId" styleId="modelId" value=""/>      
     </td>
     <td width="220" >
	<bean:message key="lbl.branchAmount" /> <br></td>
     <td>
     <html:text property="branchAmt" styleId="branchAmt" tabindex="-1" styleClass="text" value="${requestScope.list[0].branchAmt}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"></html:text>
     </td>
     
     
     </tr>
     <tr>
     <td width="220" >
	<bean:message key="lbl.nationalAmount" /> <br></td>
     <td>
     <html:text property="nationalAmt" styleId="nationalAmt" tabindex="-1" styleClass="text" value="${requestScope.list[0].nationalAmt}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"></html:text>
     </td>
     <td width="220" >
	<bean:message key="lbl.HOAmount" /> <br></td>
     <td>
     <html:text property="hoAmt" styleId="hoAmt" tabindex="-1" styleClass="text" value="${requestScope.list[0].hoAmt}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"></html:text>
     </td>
     </tr>

	</table>		

</fieldset>

<fieldset>	
		 <legend><bean:message key="lbl.vehicleApprovalGrid"/></legend>  
 
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">  
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        
        <td ><b><bean:message key="lbl.vehicleApprovalYear"/><span><font color="red">*</font></span></b></td>
        <td ><b><bean:message key="lbl.branchAmountGrid"/><span><font color="red">*</font></span></b></td>
        <td ><b><bean:message key="lbl.nationalAmountGrid"/><span><font color="red">*</font></span></b></td>
        <td ><b><bean:message key="lbl.HOAmountGrid"/><span><font color="red">*</font></span></b></td> 
         
	</tr>
		
<logic:present name="list">
	<logic:iterate name="list" id="sublist" indexId="counter">
	
	<tr class="white1">

     <td width="10%">
     <html:text property="vehicleApprovalYearArr" styleId="vehicleApprovalYear${counter+1}" readonly="true" maxlength="16" value="${sublist.vehicleApprovalYear}"/>
      </td>
        
        <td width="10%">
       <html:text property="vehicleApprovalBranchArr" styleId="gridBranchAmt${counter+1}" styleClass="text" maxlength="16" onkeypress="return isNumberKey(event);" value="${sublist.gridBranchAmt}"/>
             </td>
             
      <td width="10%">
     <html:text property="vehicleApprovalNationalArr" styleId="gridNationalAmt${counter+1}" styleClass="text" maxlength="16" onkeypress="return isNumberKey(event);" value="${sublist.gridNationalAmt}"/>
      </td>
      
      <td width="10%">
     <html:text property="vehicleApprovalHOArr" styleId="gridHoAmt${counter+1}" styleClass="text" maxlength="16" onkeypress="return isNumberKey(event);" value="${sublist.gridHoAmt}"/>
      </td>       
 
	</tr>
	
 </logic:iterate>
	</logic:present>	
		
 </table> </td>
</tr>

<tr>
	<td>		
    	<br>
    <logic:present name="editVal">
      <button type="button"  id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return updateVehicleApprovalGrid();" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="editVal">   
      <button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveVehicleApprovalGrid();" ><bean:message key="button.save" /></button> 
   </logic:notPresent>
       <br>		
   </td> 
</tr>
</table>
</fieldset>
</html:form>
 <!--  	</div>
</div>   -->     
<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
	
<logic:present name="sms">
<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("vehicleApprovalForm").action="vehicleApprovalGridBehindAction.do";
	    document.getElementById("vehicleApprovalForm").submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("vehicleApprovalForm").action="vehicleApprovalGridBehindAction.do";
	    document.getElementById("vehicleApprovalForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	
</script>
</logic:present>		
		
  </body>
		</html:html>