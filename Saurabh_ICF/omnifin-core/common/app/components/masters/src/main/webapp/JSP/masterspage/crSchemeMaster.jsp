<!--Author Name : Apoorva
Date of Creation : 25-May-2011
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/formatnumber.js"/></script> 
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/crSchemeMaster.js"></script>		
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>		 
   <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
   <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
   <!-- jQuery for Datepicker -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	
		<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
	
		<script type="text/javascript" src="<%=path%>/js/jquery.simpletip-1.3.1.js"></script>
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

<body onload="enableAnchor();document.getElementById('CrSchemeMasterForm').productButton.focus();init_fields();fnChagFix();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
  <html:form styleId="CrSchemeMasterForm" action="/crSchemeMasterAction" method="post">
 <html:errors/>
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
 <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
  <fieldset>
<legend><bean:message key="lbl.schemeMaster" /></legend> 

<fieldset>
<legend><bean:message key="lbl.schemeDetails" /></legend>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">

  <tr>
    <td width="13%"><bean:message key="lbl.productDesc"/><span><font color="red">*</font></span></td>
   <logic:present name="save">

    <td width="13%">
      <html:text property="productId" styleId="productId"  styleClass="text" onblur="fnChangeCase('crSchemeMasterForm','productId')" tabindex="-1" value="${requestScope.list[0].productId}" readonly="true"  />
       <html:hidden  property="lbxProductID" styleId="lbxProductID"  value="${requestScope.list[0].lbxProductID}" />
       <html:hidden property="lbxAssetFlag" styleId="lbxAssetFlag" value="${requestScope.list[0].lbxAssetFlag}"/>
        <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(74,'chargeMasterForm','productId','','', '','','fnhide','lbxProductID','lbxAssetFlag');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  <%--<img onClick="openLOVCommon(74,'chargeMasterForm','productId','','', '','','fnhide','lbxProductID','lbxAssetFlag')" src="<%= request.getContextPath()%>/images/lov.gif" > --%>
  </td>
    </logic:present>
    <logic:notPresent name="save">
  
    <td width="13%">
      <html:text property="productId" styleId="productId" styleClass="text"  onblur="fnChangeCase('crSchemeMasterForm','productId')" tabindex="-1" value="${requestScope.list[0].productId}" readonly="true"/>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
    	<html:hidden  property="lbxProductID" styleId="lbxProductID"  value="${requestScope.list[0].lbxProductID}"/>
    	<html:hidden property="lbxAssetFlag" styleId="lbxAssetFlag" value="${requestScope.list[0].lbxAssetFlag}"/>
          <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(74,'chargeMasterForm','productId','','', '','','fnhide','lbxProductID','lbxAssetFlag');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
  	  <%--<img onClick="openLOVCommon(74,'chargeMasterForm','productId','','', '','','fnhide','lbxProductID','lbxAssetFlag')" src="<%= request.getContextPath()%>/images/lov.gif" > --%>
     </td>
    </logic:notPresent>
  
    <td width="13%"><bean:message key="lbl.schemeDesc"/><span><font color="red">*</font></span></td>
    <td width="13%"><html:text property="schemeDesc" styleId="schemeDesc" maxlength="50" onblur="fnChangeCase('CrSchemeMasterForm','schemeDesc')" styleClass="text" value="${requestScope.list[0].schemeDesc}"/></td>
    
    <td width="13%"><bean:message key="lbl.schemeExposureAmount"/></td>
    <td width="13%"><html:text property="schemeExposure" styleId="schemeExposure" style="text-align: right" styleClass="text" value="${requestScope.list[0].schemeExposure}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
  	
   
 </tr>
 
  <tr>
  	 <td width="13%"><bean:message key="lbl.customerExposureLimit"/><span><font color="red">*</font></span></td>
    <td width="13%"><html:text property="customerExposureLimit" styleId="customerExposureLimit" style="text-align: right" styleClass="text" value="${requestScope.list[0].customerExposureLimit}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
  	
  		
<td><bean:message key="lbl.minAmountFin"/><span><font color="red">*</font></span></td>    
    <td><html:text property="minAmountFin" styleId="minAmountFin" style="text-align: right" styleClass="text"  value="${requestScope.list[0].minAmountFin}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
 
    <td><bean:message key="lbl.maxAmountFin"/><span><font color="red">*</font></span></td>
    <td><html:text property="maxAmountFin"  styleId="maxAmountFin" style="text-align: right" styleClass="text" value="${requestScope.list[0].maxAmountFin}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>

  </tr>
  <tr>  
<logic:present name="list">
	<logic:iterate name="list" id="subList">
		<logic:equal name="subList" property="lbxAssetFlag" value="N">
				<td><bean:message key="lbl.minMarginRate"/></td>
    			<td><html:text property="minMarginRate" disabled="true" style="text-align: right" styleId="minMarginRate" styleClass="text" value="${requestScope.list[0].minMarginRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:equal>
		<logic:notEqual name="subList" property="lbxAssetFlag" value="N">
		 		<td><bean:message key="lbl.minMarginRate"/></td>
    			<td><html:text property="minMarginRate" styleId="minMarginRate" style="text-align: right" styleClass="text" value="${requestScope.list[0].minMarginRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:notEqual>
	</logic:iterate>
</logic:present>  
<logic:notPresent name="list">
		 <td><bean:message key="lbl.minMarginRate"/></td>
    	<td><html:text property="minMarginRate" styleId="minMarginRate" style="text-align: right" styleClass="text" value="${requestScope.list[0].minMarginRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
</logic:notPresent>  
   
<logic:present name="list">
	<logic:iterate name="list" id="subList">
		<logic:equal name="subList" property="lbxAssetFlag" value="N">
				<td><bean:message key="lbl.maxMarginRate"/></td>
    			<td><html:text property="maxMarginRate" disabled="true" style="text-align: right" styleId="maxMarginRate" styleClass="text" value="${requestScope.list[0].maxMarginRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:equal>
		<logic:notEqual name="subList" property="lbxAssetFlag" value="N">
				<td><bean:message key="lbl.maxMarginRate"/></td>
    			<td><html:text property="maxMarginRate" styleId="maxMarginRate" style="text-align: right" styleClass="text" value="${requestScope.list[0].maxMarginRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:notEqual>
	</logic:iterate>
</logic:present>  
<logic:notPresent name="list">
		<td><bean:message key="lbl.maxMarginRate"/></td>
   		 <td><html:text property="maxMarginRate" styleId="maxMarginRate" style="text-align: right" styleClass="text" value="${requestScope.list[0].maxMarginRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
</logic:notPresent>  	

  <logic:present name="list">
	<logic:iterate name="list" id="subList">
		<logic:equal name="subList" property="lbxAssetFlag" value="N">
		 		<td><bean:message key="lbl.defaultMarginRate"/></td>
    			<td><html:text property="defaultMarginRate" disabled="true" styleId="defaultMarginRate" styleClass="text" value="${requestScope.list[0].defaultMarginRate}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:equal>
		<logic:notEqual name="subList" property="lbxAssetFlag" value="N">
		 		<td><bean:message key="lbl.defaultMarginRate"/></td>
    			<td><html:text property="defaultMarginRate" styleId="defaultMarginRate"  styleClass="text" value="${requestScope.list[0].defaultMarginRate}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:notEqual>
	</logic:iterate>
</logic:present>  
<logic:notPresent name="list">
		 <td><bean:message key="lbl.defaultMarginRate"/></td>
    	<td><html:text property="defaultMarginRate" styleId="defaultMarginRate"  styleClass="text" value="${requestScope.list[0].defaultMarginRate}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
</logic:notPresent>  
 </tr>    
  
<tr>    
       <td><bean:message key="lbl.rateType"/><span><font color="red">*</font></span></td>
	    <td> <html:select property="rateType" styleId="rateType" styleClass="text" value="${requestScope.list[0].rateType}" onchange="fnFlat();" >
			    <html:option  value="">--Select--</html:option>
			    <html:option value="F">FLAT</html:option>
			    <html:option value="E" >EFFECTIVE</html:option>    
   			</html:select>    </td>
    	
<logic:present name="list">
	<logic:iterate name="list" id="subList">
    	<logic:equal name="subList" property="rateType" value="F">		
		<td><bean:message key="lbl.rateMethod"/><span><font color="red">*</font></span></td>
		<td><input type="radio" disabled="disabled" name="rateMethod" id="rateMethod" value="F" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFixed"/>
						<input type="radio" disabled="disabled" name="rateMethod" id="rateMethod1" value="L" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFloating"/></td>
	  </logic:equal>
	  
	<logic:notEqual name="subList" property="rateType" value="F">
	
<!--	 <logic:equal name="subList" property="rateMethod" value="F">	-->
<!--	  <td><bean:message key="lbl.rateMethod"/><span><font color="red">*</font></span></td>-->
<!--	  		<td><input type="radio" name="rateMethod" id="rateMethod" value="F" checked="checked" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFixed"/>-->
<!--				<input type="radio" name="rateMethod" id="rateMethod1" value="L" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFloating"/></td>-->
<!--	  </logic:equal>-->
	
	 <logic:equal name="subList" property="rateMethod" value="L">		
		<td><bean:message key="lbl.rateMethod"/><span><font color="red">*</font></span></td>
		<td><input type="radio" name="rateMethod" id="rateMethod" value="F" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFixed"/>
						<input type="radio" name="rateMethod" checked="checked" id="rateMethod1" value="L" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFloating"/></td>
	  </logic:equal>
	
	 <logic:equal name="subList" property="rateMethod" value="F">		
		<td><bean:message key="lbl.rateMethod"/><span><font color="red">*</font></span></td>
		<td><input type="radio" name="rateMethod" checked="checked" id="rateMethod" value="F" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFixed"/>
						<input type="radio" name="rateMethod" id="rateMethod1" value="L" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFloating"/></td>
	  </logic:equal>
	
	  </logic:notEqual>
	  	  
	 	  </logic:iterate>
	  </logic:present>
	<logic:notPresent name="list">
	 <td><bean:message key="lbl.rateMethod"/><span><font color="red">*</font></span></td>
		<td><input type="radio" name="rateMethod" id="rateMethod" value="F" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFixed" />
						<input type="radio" name="rateMethod" id="rateMethod1" value="L" onchange="fnChagFix()"/><bean:message key="lbl.baseRateFloating"/></td>
	</logic:notPresent>
	

 <logic:present name="list">
 
	<logic:iterate name="list" id="subList">
    	     
		<td><bean:message key="lbl.baseRateType"/><span><font color="red">*</font></span></td>
		
		<td>
		<logic:equal name="subList" property="rateType" value="E">		
		<div id="lov">
			<html:text property="baseRateType" styleId="baseRateType" maxlength="10" readonly="true" tabindex="-1" styleClass="text" value="${requestScope.list[0].baseRateType}"/>
			<html:hidden  property="lbxBaseRateType" styleId="lbxBaseRateType"  value=""/>
			  <html:button property="baseButton" styleId="baseButton" onclick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" value=" " styleClass="lovbutton"> </html:button>
  	  <%--<img onClick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" src="<%= request.getContextPath()%>/images/lov.gif" > --%>
		
		</div>
		<div id="disabledLov" style="display: none;">
				 	<html:text property="baseRateType" styleClass="text" styleId="baseRateType" maxlength="10" disabled="true" />
		</div>
		</logic:equal>
		<logic:equal name="subList" property="rateType" value="F">		
		<div id="lov" style="display: none;">
				<html:text property="baseRateType" styleId="baseRateType" maxlength="10" readonly="true" tabindex="-1" styleClass="text" value="${requestScope.list[0].baseRateType}"/>
				<html:hidden  property="lbxBaseRateType" styleId="lbxBaseRateType"  value=""/>
			  <html:button property="baseButton" styleId="baseButton" onclick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" value=" " styleClass="lovbutton"> </html:button>
  	  <%--<img onClick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" src="<%= request.getContextPath()%>/images/lov.gif" > --%>
		</div>
		<div id="disabledLov">
				 	<html:text property="baseRateType" styleClass="text" styleId="baseRateType" maxlength="10" disabled="true" />
		</div>
		</logic:equal>
		<logic:equal name="subList" property="rateType" value="">		
		<div id="lov" style="display: none;">
				<html:text property="baseRateType" styleId="baseRateType" maxlength="10" tabindex="-1" readonly="true" styleClass="text" value="${requestScope.list[0].baseRateType}"/>
				<html:hidden  property="lbxBaseRateType" styleId="lbxBaseRateType"  value=""/>
			  <html:button property="baseButton" styleId="baseButton" onclick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" value=" " styleClass="lovbutton"> </html:button>
  	  <%--<img onClick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" src="<%= request.getContextPath()%>/images/lov.gif" > --%>
		</div>
		<div id="disabledLov">
				 	<html:text property="baseRateType" styleClass="text" styleId="baseRateType" maxlength="10" disabled="true" />
		</div>
				
		</logic:equal>
		</td>
		</logic:iterate>
	</logic:present>
	
<logic:notPresent name="list">
<td><bean:message key="lbl.baseRateType"/><span><font color="red">*</font></span></td>
		<td>
		<div id="lov" style="display: none;">
			<html:text property="baseRateType" styleId="baseRateType" maxlength="10" tabindex="-1" readonly="true" styleClass="text" value="${requestScope.list[0].baseRateType}"/>
			<html:hidden  property="lbxBaseRateType" styleId="lbxBaseRateType"  value=""/>
			  <html:button property="baseButton" styleId="baseButton" onclick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" value=" " styleClass="lovbutton"> </html:button>
  	  <%--<img onClick="openLOVCommon(105,'chargeMasterForm','baseRateType','','', '','','','lbxBaseRateType')" src="<%= request.getContextPath()%>/images/lov.gif" > --%>
		</div>
		<div id="disabledLov">
				 	<html:text property="baseRateType" styleClass="text" styleId="baseRateType" maxlength="10" disabled="true" />
		</div>
</td>
</logic:notPresent>
		
	
    </tr> 
   
      <tr>		
<logic:present name="list">   
    <logic:iterate name="list" id="subList">
    	<logic:equal name="subList" property="rateType" value="F">
    		 <td><bean:message key="lbl.minEffRate"/><span><font color="red">*</font></span></td>
   			 <td><html:text property="minEffRate" styleId="minEffRate" style="text-align: right" styleClass="text" value="${requestScope.list[0].minEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:equal>
		<logic:notEqual name="subList" property="rateType" value="F">
		 	<td><bean:message key="lbl.minEffRate"/><span><font color="red">*</font></span></td>
   			<td><html:text property="minEffRate" styleId="minEffRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].minEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:notEqual>
    </logic:iterate>
</logic:present>  
<logic:notPresent name="list">
		<td><bean:message key="lbl.minEffRate"/><span><font color="red">*</font></span></td>
   		<td><html:text property="minEffRate" styleId="minEffRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].minEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
</logic:notPresent> 

<logic:present name="list">   
    <logic:iterate name="list" id="subList">
    	<logic:equal name="subList" property="rateType" value="F">
			    <td><bean:message key="lbl.maxEffRate"/><span><font color="red">*</font></span></td>
			    <td><html:text property="maxEffRate" styleId="maxEffRate" style="text-align: right" styleClass="text" value="${requestScope.list[0].maxEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
 		</logic:equal>
		<logic:notEqual name="subList" property="rateType" value="F">
				<td><bean:message key="lbl.maxEffRate"/><span><font color="red">*</font></span></td>
			    <td><html:text property="maxEffRate" styleId="maxEffRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].maxEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:notEqual>
 	</logic:iterate>
</logic:present>
<logic:notPresent name="list">
  		<td><bean:message key="lbl.maxEffRate"/><span><font color="red">*</font></span></td>
		<td><html:text property="maxEffRate" styleId="maxEffRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].maxEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
</logic:notPresent>

<logic:present name="list">
	<logic:iterate name="list" id="subList">
  		<logic:equal name="subList" property="rateType" value="F">
  		    <td><bean:message key="lbl.defEffRate"/></td>
    		<td><html:text property="defEffRate" styleId="defEffRate" style="text-align: right" styleClass="text" value="${requestScope.list[0].defEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:equal>
		<logic:notEqual name="subList" property="rateType" value="F">
				<td><bean:message key="lbl.defEffRate"/></td>
    			<td><html:text property="defEffRate" styleId="defEffRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].defEffRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>		
		</logic:notEqual>
	</logic:iterate>
</logic:present>
<logic:notPresent name="list">
		<td><bean:message key="lbl.defEffRate"/></td>
    	<td><html:text property="defEffRate" styleId="defEffRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].defEffRate}"  onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>		
</logic:notPresent>	
   </tr>
   
  	<tr> 
 <logic:notPresent name="list">
		<td><bean:message key="lbl.fixPriod"/></td>
   		 <td><html:text property="fixPriod" styleId="fixPriod" style="text-align: right" styleClass="text"  maxlength="3" value="${requestScope.list[0].fixPriod}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
	  
	 <td><bean:message key="lbl.reviewEvnet"/></td>
			<td><input type="radio" name="reviewEvnet" id="reviewEvnet" value="I"/><bean:message key="lbl.imme" />
			<input type="radio" name="reviewEvnet" id="reviewEvnet1" value="O"/><bean:message key="lbl.nextDate"/></td>
			
	 <td><bean:message key="lbl.gapReview"/></td>
   		 <td><html:text property="gapReview" styleId="gapReview" style="text-align: right" styleClass="text" maxlength="3" value="${requestScope.list[0].gapReview}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
	  	</logic:notPresent>
	  	
	  	<logic:present name="list">
	  	<logic:iterate name="list" id="subList">
    	<logic:equal name="subList" property="rateType" value="F">
		<td><bean:message key="lbl.fixPriod"/></td>
   		 <td><html:text property="fixPriod" styleId="fixPriod" disabled="ture" style="text-align: right" styleClass="text"  maxlength="3" value="${requestScope.list[0].fixPriod}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
	  
	 <td><bean:message key="lbl.reviewEvnet"/></td>
			<td><input type="radio" name="reviewEvnet" disabled="disabled" id="reviewEvnet" value="I"/><bean:message key="lbl.imme" />
			<input type="radio" name="reviewEvnet"  disabled="disabled" id="reviewEvnet1" value="O"/><bean:message key="lbl.nextDate"/></td>
			
	 <td><bean:message key="lbl.gapReview"/></td>
   		 <td><html:text property="gapReview" styleId="gapReview" disabled="true" style="text-align: right" styleClass="text" maxlength="3" value="${requestScope.list[0].gapReview}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
	  	</logic:equal>
	  	
	  	<logic:notEqual name="subList" property="rateType" value="F">
		<td><bean:message key="lbl.fixPriod"/></td>
   		 <td><html:text property="fixPriod" styleId="fixPriod" style="text-align: right" styleClass="text"  maxlength="3" value="${requestScope.list[0].fixPriod}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
	  
	   <logic:equal name="subList" property="reviewEvnet" value="I">
		 <td><bean:message key="lbl.reviewEvnet"/></td>
			<td><input type="radio" name="reviewEvnet" checked="checked" id="reviewEvnet" value="I"/><bean:message key="lbl.imme" />
			<input type="radio" name="reviewEvnet"  id="reviewEvnet1" value="O"/><bean:message key="lbl.nextDate"/></td>
		</logic:equal>
		
		  <logic:equal name="subList" property="reviewEvnet" value="O">
		 <td><bean:message key="lbl.reviewEvnet"/></td>
			<td><input type="radio" name="reviewEvnet" id="reviewEvnet" value="I"/><bean:message key="lbl.imme" />
			<input type="radio" name="reviewEvnet"  checked="checked" id="reviewEvnet1" value="O"/><bean:message key="lbl.nextDate"/></td>
		</logic:equal>
		
		 <logic:equal name="subList" property="reviewEvnet" value="">
		 <td><bean:message key="lbl.reviewEvnet"/></td>
			<td><input type="radio" name="reviewEvnet" id="reviewEvnet" value="I"/><bean:message key="lbl.imme" />
			<input type="radio" name="reviewEvnet" id="reviewEvnet1" value="O"/><bean:message key="lbl.nextDate"/></td>
		</logic:equal>
			
	 <td><bean:message key="lbl.gapReview"/></td>
   		 <td><html:text property="gapReview" styleId="gapReview" style="text-align: right" styleClass="text" maxlength="3" value="${requestScope.list[0].gapReview}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
	  	</logic:notEqual>
	  	</logic:iterate>
	</logic:present>
	
 	</tr>  
   
     	<tr> 
 <logic:notPresent name="list">

	 <td><bean:message key="lbl.increse"/></td>
	    <td> <html:select property="increse" styleId="increse" styleClass="text" value="${requestScope.list[0].increse}" >
			    <html:option  value="">--Select--</html:option>
			    <html:option value="A">Automatic</html:option>
			    <html:option value="M" >Manual</html:option>    
   			</html:select>    </td>
	
	
	 <td><bean:message key="lbl.decrese"/></td>
	    <td> <html:select property="decrese" styleId="decrese" styleClass="text" value="${requestScope.list[0].decrese}" >
			    <html:option  value="">--Select--</html:option>
			    <html:option value="A">Automatic</html:option>
			    <html:option value="M" >Manual</html:option>    
   			</html:select>    </td>
	  	</logic:notPresent>
	  	
	  	 <logic:present name="list">

	 <td><bean:message key="lbl.increse"/></td>
	    <td> <html:select property="increse" styleId="increse" styleClass="text" value="${requestScope.list[0].increse}" >
			    <html:option  value="">--Select--</html:option>
			    <html:option value="A">Automatic</html:option>
			    <html:option value="M" >Manual</html:option>    
   			</html:select>    </td>
	
	
	 <td><bean:message key="lbl.decrese"/></td>
	    <td> <html:select property="decrese" styleId="decrese" styleClass="text" value="${requestScope.list[0].decrese}" >
			    <html:option  value="">--Select--</html:option>
			    <html:option value="A">Automatic</html:option>
			    <html:option value="M" >Manual</html:option>    
   			</html:select>    </td>
	  	</logic:present>
 	</tr>  
   
   
  <tr>
    <logic:present name="list">
	<logic:iterate name="list" id="subList">
  		<logic:equal name="subList" property="rateType" value="E">
  		    <td><bean:message key="lbl.minFlatRate"/><span><font color="red">*</font></span></td>
    		<td><html:text property="minFlatRate" styleId="minFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].minFlatRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:equal>
		<logic:notEqual name="subList" property="rateType" value="E">
			<td><bean:message key="lbl.minFlatRate"/><span><font color="red">*</font></span></td>
    		<td><html:text property="minFlatRate" styleId="minFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].minFlatRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:notEqual>
	</logic:iterate>
</logic:present>
<logic:notPresent name="list">
		<td><bean:message key="lbl.minFlatRate"/><span><font color="red">*</font></span></td>
    		<td><html:text property="minFlatRate" styleId="minFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].minFlatRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
</logic:notPresent>	
  
  <logic:present name="list">
	<logic:iterate name="list" id="subList">
  		<logic:equal name="subList" property="rateType" value="E">
  		    <td><bean:message key="lbl.maxFlatRate"/><span><font color="red">*</font></span></td>
   			<td><html:text property="maxFlatRate" styleId="maxFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].maxFlatRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>    
		   	<td><bean:message key="lbl.defFlatRate"/></td>
    		<td><html:text property="defFlatRate" styleId="defFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].defFlatRate}"  onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:equal>
		<logic:notEqual name="subList" property="rateType" value="E">
			<td><bean:message key="lbl.maxFlatRate"/><span><font color="red">*</font></span></td>
    		<td><html:text property="maxFlatRate" styleId="maxFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].maxFlatRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		    <td><bean:message key="lbl.defFlatRate"/></td>
    		<td><html:text property="defFlatRate" styleId="defFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].defFlatRate}"  onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</logic:notEqual>
	</logic:iterate>
</logic:present>
<logic:notPresent name="list">
		<td><bean:message key="lbl.maxFlatRate"/><span><font color="red">*</font></span></td>
   		<td><html:text property="maxFlatRate" styleId="maxFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].maxFlatRate}" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>	
   		<td><bean:message key="lbl.defFlatRate"/></td>
    	<td><html:text property="defFlatRate" styleId="defFlatRate" styleClass="text" style="text-align: right" value="${requestScope.list[0].defFlatRate}"  onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
</logic:notPresent>	    
 </tr> 
    <tr>

	<td><bean:message key="lbl.minIrr"/></td>
    <td><html:text property="minIrr" styleId="minIrr" styleClass="text" value="${requestScope.list[0].minIrr}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
    <td><bean:message key="lbl.maxIrr"/></td>
    <td><html:text property="maxIrr" styleId="maxIrr" styleClass="text" value="${requestScope.list[0].maxIrr}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
  </tr>
  
  <tr>
    <td><bean:message key="lbl.minTenure"/><span><font color="red">*</font></span></td>
    <td><html:text property="minTenure" styleId="minTenure" styleClass="text" value="${requestScope.list[0].minTenure}" style="text-align: right" onkeypress="return isNumberKey(event);" /></td>
    <td><bean:message key="lbl.maxTenure"/><span><font color="red">*</font></span></td>
    <td><html:text property="maxTenure" styleId="maxTenure" styleClass="text" value="${requestScope.list[0].maxTenure}" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
    <td><bean:message key="lbl.defTenure"/></td>
    <td><html:text property="defTenure" styleId="defTenure" styleClass="text" value="${requestScope.list[0].defTenure}" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
  </tr>
   <tr>   
    <td><bean:message key="lbl.repaymentFreq"/></td>
    <td>  
    <html:select property="repaymentFreq" styleId="repaymentFreq" styleClass="text" value="${requestScope.list[0].repaymentFreq}">
   
    <html:option value="M">MONTHLY</html:option>
    <html:option value="Q">QUARTERLY</html:option>    
    <html:option value="H">HALFYERALY</html:option> 
    <html:option value="Y">YEARLY</html:option> 
    </html:select>
    </td>

  <td><bean:message key="lbl.repaymentMode"/></td>
    <td>        
    <html:select property="repaymentMode" styleId="repaymentMode" styleClass="text" value="${requestScope.list[0].repaymentMode}">
    <html:option  value="">--Select--</html:option>
		<logic:present name="repaymentModeList">
		<logic:notEmpty name="repaymentModeList">
			<html:optionsCollection name="repaymentModeList"
			label="repaymentLabel" value="repaymentMode" />
		</logic:notEmpty>
		</logic:present> 
    </html:select>
    </td>
    
    
   
      </tr>
      <tr>
      		<td><bean:message key="lbl.valdity"/><span><font color="red">*</font></span></td>
      		<td><html:text property="validityDays" styleId="validityDays" styleClass="text" value="${requestScope.list[0].validityDays}" maxlength="3" style="text-align: right" onkeypress="return isNumberKey(event);"/></td>
      		<td><bean:message key="lbl.expiryDate"/><span><font color="red">*</font></span></td>
      		<td><html:text property="expiryDate" styleClass="text" styleId="expiryDate" value="${requestScope.list[0].expiryDate}" maxlength="10" onchange="return checkDate('expiryDate');" /></td>
      </tr>
        <tr>
 <td><bean:message key="lbl.installmentType"/></td>
    <td> 
  
    <html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.list[0].installmentType}">
    <html:option  value="">--Select--</html:option>
    <html:option  value="E">EQUATED INSTALLMENT</html:option >
    <html:option  value="G">GRADED INSTALLMENT</html:option >   
    <html:option  value="P">EQUATED PRINCIPAL</html:option > 
    <html:option  value="Q">GRADED PRINCIPAL-EQUATED PRINCIPAL</html:option >
    <html:option  value="R">GRADED PRINCIPAL-EQUATED INSTALLMENT</html:option > 
    </html:select>
    
    </td>       
 
    <td><bean:message key="lbl.installmentMode"/></td>
    <td> 
    <html:select property="installmentMode" styleId="installmentMode" styleClass="text" value="${requestScope.list[0].installmentMode}">
    <html:option  value="">--Select--</html:option> 
    <html:option value="A">ADVANCE</html:option>
    <html:option value="R">ARREAR</html:option>         
    </html:select>
    </td>
    
   
<!--    <td><bean:message key="lbl.preEmi"/><font color="red">*</font></td>-->
<!--    <td><html:text property="preEMI" onchange="return checkRate('preEMI');" styleId="preEMI" style="text-align:right" styleClass="text" onkeypress="return isNumberKey(event);" value="${requestScope.list[0].preEMI}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>-->
<!--    -->
   
   
    <td>
     <bean:message key="lbl.status"/> </td>
     
       <td>
       <logic:notPresent name="status">
       <input type="checkbox" name="status" id="status" checked="checked" />
       </logic:notPresent>
       
       <logic:present name="status">
       <logic:equal value="Active" name="status">
       <input type="checkbox" name="status" id="status" checked="checked" />
       </logic:equal>     
       <logic:notEqual value="Active" name="status">
       <input type="checkbox" name="status" id="status"  />
       </logic:notEqual>
       </logic:present>
          </td>    
</tr>
</table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.resheduleDetail"/></legend>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">

<tr>
    <td width="13%"><bean:message key="lbl.reschAllowed"/></td>
    <td width="13%"> <logic:equal value="Active" name="reschAllowed">
              <input type="checkbox" name="reschAllowed" id="reschAllowed" checked="checked" onclick="fndisableResch();" />
      </logic:equal>     
         <logic:notEqual value="Active" name="reschAllowed">
      	 		  <input type="checkbox" name="reschAllowed" id="reschAllowed" onclick="fndisableResch();" />
         </logic:notEqual>
    <html:hidden property="reschAllowed" styleId="reschAllowed" styleClass="text" value="${requestScope.list[0].reschAllowed}"/></td>
 
 <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="reschAllowed" value="Active">
 		<td width="13%"><bean:message key="lbl.reschLockinPeriod"/></td>
    	<td width="13%"><html:text property="reschLockinPeriod" styleId="reschLockinPeriod" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" value="${requestScope.list[0].reschLockinPeriod}"/></td>
 </logic:equal>
 <logic:notEqual name="subList" property="reschAllowed" value="Active">
 	<td width="13%"><bean:message key="lbl.reschLockinPeriod"/></td>
    <td width="13%"><html:text property="reschLockinPeriod" styleId="reschLockinPeriod" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" disabled="true" value="${requestScope.list[0].reschLockinPeriod}"/></td>
  </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
 	<td width="13%"><bean:message key="lbl.reschLockinPeriod"/></td>
    <td width="13%"><html:text property="reschLockinPeriod" styleId="reschLockinPeriod" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" disabled="true" /></td>
 </logic:notPresent>

   <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="reschAllowed" value="Active">
  <td width="13%"><bean:message key="lbl.minimumGapResch"/></td>
    <td width="13%"><html:text property="minimumGapResch" styleId="minimumGapResch" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" value="${requestScope.list[0].minimumGapResch}"/></td> 
 
 </logic:equal>
 <logic:notEqual name="subList" property="reschAllowed" value="Active">
  <td width="13%"><bean:message key="lbl.minimumGapResch"/></td>
    <td width="13%"><html:text property="minimumGapResch" styleId="minimumGapResch" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" disabled="true" value="${requestScope.list[0].minimumGapResch}"/></td> 
 
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
  <td width="13%"><bean:message key="lbl.minimumGapResch"/></td>
    <td width="13%"><html:text property="minimumGapResch" styleId="minimumGapResch" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" disabled="true" /></td> 
 </logic:notPresent>  
   </tr> 
       
  <tr> 
  <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="reschAllowed" value="Active">
 	<td><bean:message key="lbl.minPeriodResch"/></td>
    <td><html:text property="minPeriodResch" styleId="minPeriodResch" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text"  value="${requestScope.list[0].minPeriodResch}"/></td>
 	<td><bean:message key="lbl.numberReschAllowedYear"/></td>
    <td><html:text property="numberReschAllowedYear" styleId="numberReschAllowedYear" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" value="${requestScope.list[0].numberReschAllowedYear}"/></td>
    <td><bean:message key="lbl.numberReschAllowedTotal"/></td>
    <td><html:text property="numberReschAllowedTotal" styleId="numberReschAllowedTotal" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" styleClass="text" value="${requestScope.list[0].numberReschAllowedTotal}"/></td>
 
 </logic:equal>
 <logic:notEqual name="subList" property="reschAllowed" value="Active">
 	<td><bean:message key="lbl.minPeriodResch"/></td>
    <td><html:text property="minPeriodResch" styleId="minPeriodResch" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true" value="${requestScope.list[0].minPeriodResch}"/></td>
 	<td><bean:message key="lbl.numberReschAllowedYear"/></td>
    <td><html:text property="numberReschAllowedYear" styleId="numberReschAllowedYear" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" disabled="true" styleClass="text" value="${requestScope.list[0].numberReschAllowedYear}"/></td>
    <td><bean:message key="lbl.numberReschAllowedTotal"/></td>
    <td><html:text property="numberReschAllowedTotal" styleId="numberReschAllowedTotal" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" disabled="true" styleClass="text" value="${requestScope.list[0].numberReschAllowedTotal}"/></td>
 
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
  	<td><bean:message key="lbl.minPeriodResch"/></td>
    <td><html:text property="minPeriodResch" styleId="minPeriodResch" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true"/></td>
     <td><bean:message key="lbl.numberReschAllowedYear"/></td>
    <td><html:text property="numberReschAllowedYear" styleId="numberReschAllowedYear" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" disabled="true" styleClass="text"/></td>
    <td><bean:message key="lbl.numberReschAllowedTotal"/></td>
    <td><html:text property="numberReschAllowedTotal" styleId="numberReschAllowedTotal" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" disabled="true" styleClass="text"/></td>
 
 </logic:notPresent>
    
     </tr> 
    
</table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.deferralDetail"/></legend>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">

 <tr>
    <td width="13%"><bean:message key="lbl.deferralAllowed"/></td>
    <td width="13%"><logic:equal value="Active" name="deferralAllowed">
              <input type="checkbox" name="deferralAllowed" id="deferralAllowed" checked="checked" onclick="fndisableDef();" />
      </logic:equal>     
         <logic:notEqual value="Active" name="deferralAllowed">
      	 		  <input type="checkbox" name="deferralAllowed" id="deferralAllowed" onclick="fndisableDef();" />
         </logic:notEqual>
    <html:hidden property="deferralAllowed" styleId="deferralAllowed" styleClass="text" value="${requestScope.list[0].deferralAllowed}"/></td>
    
 <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="deferralAllowed" value="Active">
  	<td width="13%"><bean:message key="lbl.defrLockinPeriod"/></td>
    <td width="13%"><html:text property="defrLockinPeriod" styleId="defrLockinPeriod" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].defrLockinPeriod}"/></td>
    <td width="13%"><bean:message key="lbl.minimumGapDefr"/></td>
    <td width="13%"><html:text property="minimumGapDefr" styleId="minimumGapDefr" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].minimumGapDefr}"/></td>

</logic:equal>
 <logic:notEqual name="subList" property="deferralAllowed" value="Active">
  	<td width="13%"><bean:message key="lbl.defrLockinPeriod"/></td>
    <td width="13%"><html:text property="defrLockinPeriod" styleId="defrLockinPeriod" maxlength="3" style="text-align:right" style="text-align:right" onkeypress="return isNumberKey(event);" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true" value="${requestScope.list[0].defrLockinPeriod}"/></td>
 	 <td width="13%"><bean:message key="lbl.minimumGapDefr"/></td>
    <td width="13%"><html:text property="minimumGapDefr" styleId="minimumGapDefr" maxlength="3" styleClass="text" disabled="true" style="text-align:right" onkeypress="return isNumberKey(event);" value="${requestScope.list[0].minimumGapDefr}"/></td>
 
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
  	<td width="13%"><bean:message key="lbl.defrLockinPeriod"/></td>
    <td width="13%"><html:text property="defrLockinPeriod" styleId="defrLockinPeriod" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true"/></td>
 	<td width="13%"><bean:message key="lbl.minimumGapDefr"/></td>
    <td width="13%"><html:text property="minimumGapDefr" styleId="minimumGapDefr" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true"/></td>

 </logic:notPresent>   
</tr>
   
  <tr>  
  <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="deferralAllowed" value="Active">
  <td><bean:message key="lbl.maximumDefrMonthsAllowed"/></td>
    <td><html:text property="maximumDefrMonthsAllowed" styleId="maximumDefrMonthsAllowed" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].maximumDefrMonthsAllowed}"/></td>
    <td><bean:message key="lbl.maximumDefrMonthsTotal"/></td>
    <td><html:text property="maximumDefrMonthsTotal" styleId="maximumDefrMonthsTotal" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].maximumDefrMonthsTotal}"/></td>
 	<td><bean:message key="lbl.numberDefrAllowedYear"/><span></span></td>
    <td><html:text property="numberDefrAllowedYear" maxlength="3" styleId="numberDefrAllowedYear" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].numberDefrAllowedYear}"/></td>
 
 </logic:equal>
 <logic:notEqual name="subList" property="deferralAllowed" value="Active">
  <td><bean:message key="lbl.maximumDefrMonthsAllowed"/></td>
    <td><html:text property="maximumDefrMonthsAllowed" styleId="maximumDefrMonthsAllowed" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" disabled="true" styleClass="text" value="${requestScope.list[0].maximumDefrMonthsAllowed}"/></td>
    <td><bean:message key="lbl.maximumDefrMonthsTotal"/></td>
    <td><html:text property="maximumDefrMonthsTotal" styleId="maximumDefrMonthsTotal" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" disabled="true" styleClass="text" value="${requestScope.list[0].maximumDefrMonthsTotal}"/></td>
    <td><bean:message key="lbl.numberDefrAllowedYear"/><span></span></td>
    <td><html:text property="numberDefrAllowedYear" maxlength="3" styleId="numberDefrAllowedYear" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true" value="${requestScope.list[0].numberDefrAllowedYear}"/></td>
    
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
		<td><bean:message key="lbl.maximumDefrMonthsAllowed"/></td>
		<td><html:text property="maximumDefrMonthsAllowed" styleId="maximumDefrMonthsAllowed" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" disabled="true" styleClass="text"/></td>
		<td><bean:message key="lbl.maximumDefrMonthsTotal"/></td>
		<td><html:text property="maximumDefrMonthsTotal" styleId="maximumDefrMonthsTotal" style="text-align:right" onkeypress="return isNumberKey(event);" maxlength="3" disabled="true" styleClass="text"/></td>
		<td><bean:message key="lbl.numberDefrAllowedYear"/><span></span></td>
		<td><html:text property="numberDefrAllowedYear" maxlength="3" styleId="numberDefrAllowedYear" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true"/></td>
 
   </logic:notPresent>     
      </tr> 
   
    <tr>
<logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="deferralAllowed" value="Active">
     <td><bean:message key="lbl.numberDefrAllowedTotal"/></td>
    <td><html:text property="numberDefrAllowedTotal" maxlength="3" styleId="numberDefrAllowedTotal" styleClass="text" value="${requestScope.list[0].numberDefrAllowedTotal}"/></td>

 </logic:equal>
 <logic:notEqual name="subList" property="deferralAllowed" value="Active">
 	 <td><bean:message key="lbl.numberDefrAllowedTotal"/></td>
    <td><html:text property="numberDefrAllowedTotal" maxlength="3" styleId="numberDefrAllowedTotal" styleClass="text" style="text-align:right" onkeypress="return isNumberKey(event);" disabled="true" value="${requestScope.list[0].numberDefrAllowedTotal}"/></td>
 
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
 	    <td><bean:message key="lbl.numberDefrAllowedTotal"/></td>
    <td><html:text property="numberDefrAllowedTotal" maxlength="3" styleId="numberDefrAllowedTotal" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true"/></td>
 </logic:notPresent> 
</tr>
</table>
</fieldset>


<fieldset>
<legend><bean:message key="lbl.prepayDetail"/></legend>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">

<tr>
    <td width="13%"><bean:message key="lbl.prepayAllowed"/></td>
        <td width="13%">
       <logic:equal value="Active" name="prepayAllowed">
              <input type="checkbox" name="prepayAllowed" id="prepayAllowed" checked="checked" onclick="fndisablePrepay();"/>
      </logic:equal>     
         <logic:notEqual value="Active" name="prepayAllowed">
      	 		  <input type="checkbox" name="prepayAllowed" id="prepayAllowed" onclick="fndisablePrepay();" />
         </logic:notEqual>
    <html:hidden property="prepayAllowed" styleId="prepayAllowed" styleClass="text" value="${requestScope.list[0].prepayAllowed}"/></td>

  <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="prepayAllowed" value="Active">
      <td width="13%"><bean:message key="lbl.prepayLockinPeriod"/></td>
    <td width="13%"><html:text property="prepayLockinPeriod" styleId="prepayLockinPeriod" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].prepayLockinPeriod}"/></td>
 
    <td width="13%"><bean:message key="lbl.minimumGapPrepay"/></td>
    <td width="13%"><html:text property="minimumGapPrepay" maxlength="3" styleId="minimumGapPrepay" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].minimumGapPrepay}"/></td>
 </logic:equal>
 <logic:notEqual name="subList" property="prepayAllowed" value="Active">
     <td width="13%"><bean:message key="lbl.prepayLockinPeriod"/></td>
    <td width="13%"><html:text property="prepayLockinPeriod" styleId="prepayLockinPeriod" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" disabled="true" styleClass="text" value="${requestScope.list[0].prepayLockinPeriod}"/></td>
    <td width="13%"><bean:message key="lbl.minimumGapPrepay"/></td>
    <td width="13%"><html:text property="minimumGapPrepay" maxlength="3" styleId="minimumGapPrepay" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true" value="${requestScope.list[0].minimumGapPrepay}"/></td>
    </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
    <td width="13%"><bean:message key="lbl.prepayLockinPeriod"/></td>
    <td width="13%"><html:text property="prepayLockinPeriod" styleId="prepayLockinPeriod" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" disabled="true" styleClass="text" /></td>
    <td width="13%"><bean:message key="lbl.minimumGapPrepay"/></td>
    <td width="13%"><html:text property="minimumGapPrepay" maxlength="3" styleId="minimumGapPrepay" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true" /></td>
     </logic:notPresent>
      
</tr> 
  
<tr>
 <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="prepayAllowed" value="Active">
 	<td><bean:message key="lbl.minimumPrepayPercent"/></td>
    <td><html:text property="minimumPrepayPercent" styleId="minimumPrepayPercent" styleClass="text" value="${requestScope.list[0].minimumPrepayPercent}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td> 
 	
 	<td><bean:message key="lbl.maximumPrepayPercent"/></td>
    <td><html:text property="maximumPrepayPercent" styleId="maximumPrepayPercent"  styleClass="text" value="${requestScope.list[0].maximumPrepayPercent}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
    
    <td><bean:message key="lbl.numberPrepayAllowedYear"/></td>
    <td><html:text property="numberPrepayAllowedYear" maxlength="3" styleId="numberPrepayAllowedYear" style="text-align:right" styleClass="text" onkeypress="return isNumberKey(event);" value="${requestScope.list[0].numberPrepayAllowedYear}"/></td>
    
         
 </logic:equal>
 <logic:notEqual name="subList" property="prepayAllowed" value="Active">
		<td><bean:message key="lbl.minimumPrepayPercent"/></td>
		<td><html:text property="minimumPrepayPercent" styleId="minimumPrepayPercent" styleClass="text" disabled="true" value="${requestScope.list[0].minimumPrepayPercent}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		
		<td><bean:message key="lbl.maximumPrepayPercent"/></td>
		<td><html:text property="maximumPrepayPercent" styleId="maximumPrepayPercent" disabled="true"  styleClass="text" value="${requestScope.list[0].maximumPrepayPercent}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		
		<td><bean:message key="lbl.numberPrepayAllowedYear"/></td>
		<td><html:text property="numberPrepayAllowedYear" maxlength="3" styleId="numberPrepayAllowedYear" styleClass="text" disabled="true" onkeypress="return isNumberKey(event);" style="text-align:right" value="${requestScope.list[0].numberPrepayAllowedYear}"/></td>
     
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
		<td><bean:message key="lbl.minimumPrepayPercent"/></td>
		<td><html:text property="minimumPrepayPercent" styleId="minimumPrepayPercent" styleClass="text" disabled="true" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		
		<td><bean:message key="lbl.maximumPrepayPercent"/></td>
		<td><html:text property="maximumPrepayPercent" styleId="maximumPrepayPercent" disabled="true"  styleClass="text" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		
		<td><bean:message key="lbl.numberPrepayAllowedYear"/></td>
		<td><html:text property="numberPrepayAllowedYear" maxlength="3" styleId="numberPrepayAllowedYear" style="text-align:right" onkeypress="return isNumberKey(event);" styleClass="text" disabled="true"/></td>
     
 </logic:notPresent>  
</tr>  
<tr>
 <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="prepayAllowed" value="Active">
	<td><bean:message key="lbl.numberPrepayAllowedTotal"/></td>
    <td><html:text property="numberPrepayAllowedTotal" styleId="numberPrepayAllowedTotal" maxlength="3" styleClass="text" style="text-align:right" onkeypress="return isNumberKey(event);" value="${requestScope.list[0].numberPrepayAllowedTotal}"/></td>      
 </logic:equal>
 <logic:notEqual name="subList" property="prepayAllowed" value="Active">
	<td><bean:message key="lbl.numberPrepayAllowedTotal"/></td>
    <td><html:text property="numberPrepayAllowedTotal" styleId="numberPrepayAllowedTotal" style="text-align:right" maxlength="3" disabled="true" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].numberPrepayAllowedTotal}"/></td> 
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
	<td><bean:message key="lbl.numberPrepayAllowedTotal"/></td>
    <td><html:text property="numberPrepayAllowedTotal" styleId="numberPrepayAllowedTotal" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" disabled="true" styleClass="text" /></td> 
 </logic:notPresent>  

</tr>   
</table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.addDisbDetails"/></legend>
<table width="100%" border="0" cellspacing="1" cellpadding="2">
<tr>
   <td width="2%"><bean:message key="lbl.additionalDisbAllowed"/></td>
   	<td width="13%">

   
   
   
      <logic:equal value="Active" name="additionalDisbAllowedStatus">
              <input type="checkbox" name="additionalDisbAllowed" id="additionalDisbAllowed" checked="checked" />
      </logic:equal>
      
         <logic:notEqual value="Active" name="additionalDisbAllowedStatus">
      	 		  <input type="checkbox" name="additionalDisbAllowed" id="additionalDisbAllowed"  />
         </logic:notEqual>
   
    </td>
</tr>
</table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.terminationDetail"/> </legend>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
      
<tr>
	<td width="13%"><bean:message key="lbl.terminationAllowed"/></td>
	<td width="13%"><logic:equal value="Active" name="terminationAllowed">
	<input type="checkbox" name="terminationAllowed" id="terminationAllowed" checked="checked" onclick="fndisableTermi();"/>
</logic:equal>     
<logic:notEqual value="Active" name="terminationAllowed">
	<input type="checkbox" name="terminationAllowed" id="terminationAllowed" onclick="fndisableTermi();" />
</logic:notEqual>
	<html:hidden property="terminationAllowed" styleId="terminationAllowed" styleClass="text" value="${requestScope.list[0].terminationAllowed}"/></td>
<logic:present name="list">
<logic:iterate name="list" id="subList">
<logic:equal name="subList" property="terminationAllowed" value="Active">
		<td width="13%"><bean:message key="lbl.terminationLockinPeriod"/></td>
		<td width="13%"><html:text property="terminationLockinPeriod" styleId="terminationLockinPeriod" onkeypress="return isNumberKey(event);" maxlength="3" style="text-align: right" styleClass="text" value="${requestScope.list[0].terminationLockinPeriod}"/></td> 
<%--<td width="13%"><bean:message key="lbl.minimumGapTermination"/></td> --%>		
		<td width="13%"><html:hidden property="minimumGapTermination" styleId="minimumGapTermination" /></td>

 </logic:equal>
 <logic:notEqual name="subList" property="terminationAllowed" value="Active">
		<td width="13%"><bean:message key="lbl.terminationLockinPeriod"/></td>
		<td width="13%"><html:text property="terminationLockinPeriod" styleId="terminationLockinPeriod" maxlength="3" style="text-align: right" onkeypress="return isNumberKey(event);" disabled="true" styleClass="text" value="${requestScope.list[0].terminationLockinPeriod}"/></td> 
<!--		<td width="13%"><bean:message key="lbl.minimumGapTermination"/></td>-->
		<td width="13%"><html:hidden property="minimumGapTermination" styleId="minimumGapTermination" /></td>
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
		<td width="13%"><bean:message key="lbl.terminationLockinPeriod"/></td>
		<td width="13%"><html:text property="terminationLockinPeriod" styleId="terminationLockinPeriod" style="text-align: right" maxlength="3" styleClass="text" disabled="true" onkeypress="return isNumberKey(event);"/></td> 
<!--		<td width="13%"><bean:message key="lbl.minimumGapTermination"/></td>-->
		<td width="13%"><html:hidden property="minimumGapTermination" styleId="minimumGapTermination"   /></td>
 </logic:notPresent>
 
</tr>  
<tr>
 <logic:present name="list">
 <logic:iterate name="list" id="subList">
 <logic:equal name="subList" property="terminationAllowed" value="Active">
	<td><bean:message key="lbl.minGapBetweenPrepayAndTermination"/></td>
    <td><html:text property="minimumGapBetPrepayAndTer" styleId="minimumGapBetPrepayAndTer" maxlength="3" styleClass="text" style="text-align:right" onkeypress="return isNumberKey(event);" value="${requestScope.list[0].minimumGapBetPrepayAndTer}"/></td>      
 </logic:equal>
 <logic:notEqual name="subList" property="terminationAllowed" value="Active">
	<td><bean:message key="lbl.minGapBetweenPrepayAndTermination"/></td>
    <td><html:text property="minimumGapBetPrepayAndTer" styleId="minimumGapBetPrepayAndTer" style="text-align:right" maxlength="3" disabled="true" onkeypress="return isNumberKey(event);" styleClass="text" value="${requestScope.list[0].minimumGapBetPrepayAndTer}"/></td> 
 </logic:notEqual>
 </logic:iterate>
 </logic:present>
 <logic:notPresent name="list">
	<td><bean:message key="lbl.minGapBetweenPrepayAndTermination"/></td>
    <td><html:text property="minimumGapBetPrepayAndTer" styleId="minimumGapBetPrepayAndTer" maxlength="3" style="text-align:right" onkeypress="return isNumberKey(event);" disabled="true" styleClass="text" /></td> 
 </logic:notPresent>
 
 </tr>
 <tr>
 <td><bean:message key="lbl.branchselection" /><span><font color="red">*</font></span></td>
      	<td><bean:message key="lbl.chkAll" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		<logic:equal value="A" name="selectionAccecc">
       			<input type="radio" onclick="chkAll();" name="allselection" value="A" checked="checked" id="allselection" />
       		</logic:equal>
        	<logic:notEqual value="A" name="selectionAccecc">
       			<input type="radio" onclick="chkAllEdit();" name="allselection" value="A"  id="allselection" />
       		</logic:notEqual>
       		</br>
      		<bean:message key="lbl.chkSelective" /> 
       		<logic:equal value="S" name="selectionAccecc">
      			<input type="radio" onclick="chkSelectiveEdit();" checked="checked"  name="allselection" value="S" id="singleselection"/>
     		 </logic:equal>
      		 <logic:notEqual value="S" name="selectionAccecc">
      			<input type="radio" onclick="chkSelectiveEdit();" name="allselection" value="S" id="singleselection"/>
      		</logic:notEqual>
      	</td>
 
 <td><bean:message key="lbl.branchId" /></td> 
 <td>
 <html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 150" tabindex="-1" >
 	<logic:present name="branchList"> 			
       		<html:optionsCollection name="branchList" value="branchId" label="branchDesc"/>   
 	</logic:present>   	
 </html:select>
 <html:hidden  property="lbxBranchIds" styleId="lbxBranchIds" value="${requestScope.list[0].lbxBranchIds}" />
 <html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(223,'userBranchMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>
 </td>
 </tr>
 
  <%--PRASHANT START CHANGE HERE --%> 
     
      <%--PRASHANT END CHANGE HERE --%> 
     

 
<tr>

	<logic:present name="save">
	<td colspan="3">
	<div id="saveDisbaled"  style="float:left; padding-right: 5px;">
		<button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="detailBeforeSave();" ><bean:message key="button.save" /></button>
	</div>	
	<div id="saveEnabled" style="display:none; float:left; padding-right: 5px;">
		<button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="schemeDetailsSave('<bean:message key="msg.reschLockinPeriod1" />','<bean:message key="msg.minimumGapResch1" />','<bean:message key="msg.minPeriodResch1" />','<bean:message key="msg.numberReschAllowedYear1" />','<bean:message key="msg.numberReschAllowedTotal1" />','<bean:message key="msg.defrLockinPeriod1" />','<bean:message key="msg.minimumGapDefr1" />','<bean:message key="msg.maximumDefrMonthsAllowed1" />','<bean:message key="msg.maximumDefrMonthsTotal1" />','<bean:message key="msg.numberDefrAllowedYear1" />','<bean:message key="msg.numberDefrAllowedTotal1" />','<bean:message key="msg.prepayLockinPeriod1" />','<bean:message key="msg.minimumGapPrepay1" />','<bean:message key="msg.minimumPrepayPercent1" />','<bean:message key="msg.maximumPrepayPercent1" />','<bean:message key="msg.numberPrepayAllowedYear1" />','<bean:message key="msg.numberPrepayAllowTotal1" />','<bean:message key="msg.terminationLockinPeriod1" />','<bean:message key="msg.minimumGapBetweenPrepayAndTermination1" />');" > <bean:message key="button.save" /></button>
	</div>
	  <button type="button" name="save" id="save" title="Alt+M" accesskey="M" onclick="detailMapping();" class="topformbutton4" > <bean:message key="button.acdetailMapping" /> </button>
	 </td> 
	
	</logic:present>
	<logic:notPresent name="save">
	<td colspan="3"> 
		     <button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return schemeDetailsModify('<bean:message key="msg.reschLockinPeriod1" />','<bean:message key="msg.minimumGapResch1" />','<bean:message key="msg.minPeriodResch1" />','<bean:message key="msg.numberReschAllowedYear1" />','<bean:message key="msg.numberReschAllowedTotal1" />','<bean:message key="msg.defrLockinPeriod1" />','<bean:message key="msg.minimumGapDefr1" />','<bean:message key="msg.maximumDefrMonthsAllowed1" />','<bean:message key="msg.maximumDefrMonthsTotal1" />','<bean:message key="msg.numberDefrAllowedYear1" />','<bean:message key="msg.numberDefrAllowedTotal1" />','<bean:message key="msg.prepayLockinPeriod1" />','<bean:message key="msg.minimumGapPrepay1" />','<bean:message key="msg.minimumPrepayPercent1" />','<bean:message key="msg.maximumPrepayPercent1" />','<bean:message key="msg.numberPrepayAllowedYear1" />','<bean:message key="msg.numberPrepayAllowTotal1" />','<bean:message key="msg.terminationLockinPeriod1" /> ','<bean:message key="msg.minimumGapBetweenPrepayAndTermination1" />','${requestScope.list[0].schemeId}');" > <bean:message key="button.save" /></button>
		 
		  <button type="button" name="save" id="save" title="Alt+M" accesskey="M" onclick="return accountMapping('${requestScope.list[0].schemeId}');" class="topformbutton4" > <bean:message key="button.acdetailMapping" /></button>
	</td> 
	</logic:notPresent>		
	</tr>   	
</table></fieldset>
</fieldset>

</html:form>

<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("CrSchemeMasterForm").action="crSchemeSearchBehind.do";
	    document.getElementById("CrSchemeMasterForm").submit();
	   	window.close();	
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("CrSchemeMasterForm").action="crSchemeSearchBehind.do";
	    document.getElementById("CrSchemeMasterForm").submit();
	    	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
	
		alert("<bean:message key="msg.notepadError" />");
		window.close();	
	}

</script>
</logic:present>
   <logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='NONE')
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
		
	}
	</script>
</logic:present>
  </body>
</html:html>
