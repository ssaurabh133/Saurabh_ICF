
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/nhbMapping.js"></script>
<script type="text/javascript">

</script>


</head>

<body onload="enableAnchor();" >
<logic:present name="listOfData">
<html:form styleId="NhbForm"  method="post"  action="/NhbMapping" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.nhbMapping" /></legend>

<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
 <table width="100%">
  <tr>
    <td  ><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
	<td >		
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" value="${listOfData[0].dealNo}" />
		<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${listOfData[0].lbxDealNo}"  />						
	</td>
    <td><bean:message key="lbl.customerName"/></td>
	<td  ><html:text property="customerName"   styleClass="text" styleId="customerName" maxlength="50"  value="${listOfData[0].customerName}" readonly="true"  tabindex="-1"/></td> 
		
 </tr>
	 
  <tr>	
	<td><bean:message key="lbl.product"/></td> 
    <td>
     <html:text  property="product" styleId="product" styleClass="text" readonly="true"  value="${listOfData[0].product}" tabindex="-1"/>             
     </td>
     <td ><bean:message key="lbl.scheme"/></td>
     <td >
     <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value="${listOfData[0].scheme}" tabindex="-1"/>
     </td>     

   <tr>
	<td ><bean:message key="lbl.nhbCategory"/><span><font color="red">*</font></span></td> 
	<td>
	<html:hidden  property="nhbCategoryId" styleId="nhbCategoryId" value="${listOfData[0].nhbCategoryId}"  />
   	<html:select property="nhbCategory" styleId="nhbCategory" size="5" multiple="multiple" style="width: 150" >  
   	<logic:present name="nhbDesc"> 			
    <html:optionsCollection name="nhbDesc" value="nhbCategoryId" label="nhbCategory"/>   
    </logic:present>   		
    </html:select>
    <html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(2016,'NhbForm','nhbCategory','','', '','','','nhbCategoryId');" value=" " styleClass="lovbutton"></html:button>	   
	</td>
  </tr>
      
  <tr>
   <td>
   <logic:present name="editVal">
   <button type="button" name="save" id="save" title="Alt+V" accesskey="V" tabindex="5"onclick="return saveInEditMode();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   </td> 
 </tr>

</table>		


</fieldset>
</html:form>
</logic:present>
<logic:notPresent name="listOfData">
<html:form styleId="NhbForm"  method="post"  action="/NhbMapping" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.nhbCategory" /></legend>

<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<table width="100%">
 <tr>
  <td  ><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
  <td >	
	<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
	<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value='' />						
	<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(2015,'NhbForm','dealNo','','','','','','customerName','product','scheme');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button> 	        
  </td>
  <td><bean:message key="lbl.customerName"/></td>
  <td  ><html:text property="customerName"   styleClass="text" styleId="customerName" maxlength="50" readonly="true"  tabindex="-1"/></td> 
		
</tr>
	 
 <tr>
		
	<td><bean:message key="lbl.product"/></td> 
    <td>
     <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>             
     </td>
     <td ><bean:message key="lbl.scheme"/></td>
     <td >
     <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
     </td>     
</tr>
   	
 <tr>
	<td ><bean:message key="lbl.nhbCategory"/><span><font color="red">*</font></span></td> 
	<td>
	<html:hidden  property="nhbCategoryId" styleId="nhbCategoryId" value=""  />
   	<html:select property="nhbCategory" styleId="nhbCategory" size="5" multiple="multiple" style="width: 150" >  
   	<logic:present name="nhbDesc"> 			
     <html:optionsCollection name="nhbDesc" value="nhbCategory" label="nhbCategory"/>   
     </logic:present>   		
     </html:select>
     <html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(2016,'NhbForm','nhbCategory','','', '','','','nhbCategoryId');" value=" " styleClass="lovbutton"></html:button>	   
	</td>
 </tr>
      
 <tr>
 	<td>
   <logic:present name="save">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" tabindex="5" onclick="return saveNhbMapping();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
</html:form>

</logic:notPresent>
		
		<logic:present name="sms">
<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("NhbForm").action="searchNhbMapping.do?method=searchNhbMapping";
	    document.getElementById("NhbForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("NhbForm").action="searchNhbMapping.do?method=searchNhbMapping";
	    document.getElementById("NhbForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')

	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
</html:html>