<%--
      Author Name-  Amit Kumar2 
      Date of creation 25/11/2013
      Purpose-   Used Vehical Pricing Add       
      
 --%>

<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
 <%@include file="/JSP/commonIncludeContent.jsp" %> 
 <%@page import="com.connect.CommonFunction"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	<head>
		
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/usedVehicalPricingScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/cpScript/formatnumber.js"></script>
<script type="text/javascript">

</script>
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
	<body onload="enableAnchor();fntab();" >
	
	<div id="centercolumn">
	
	<div id="processingImage">

	<html:form action="/usedVehiclePricingAdd" method="post" styleId="vehiclePricing" >
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	 <input type="hidden"  name="refinanceId" id="refinanceId"  value="${usedPricingYearList[0].refinanceId}" />
		<fieldset>
			<legend><bean:message key="lbl.usedVehicalPricing"/></legend>
			<table width="100%"  border="0" cellspacing="2" cellpadding="0">
			<logic:present name="usedPricingYearList">
			
			<tr>
     <td width="13%"><bean:message key="lbl.usedVehicleMake" /><span><font color="red">*</font></span>   </td>
     <td>
	     <html:text property="usedVehicleMakeSearch" styleId="usedVehicleMakeSearch" styleClass="text" value="${usedPricingYearList[0].usedVehicleMakeSearch}"  readonly="true" tabindex="-1"/>
         <html:hidden property="makeModelId" styleId="makeModelId"  value="${usedPricingYearList[0].makeModelId}" />
         <html:button property="vehicleMakeButton" styleId="vehicleMakeButton" 
         onclick="openLOVCommon(2062,'vehiclePricing','usedVehicleMakeSearch','','','','','','usedVehicleModelSearch','hid','hid1');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>	
          <html:hidden property="make" styleId="make"  value="" />
          <input type="hidden" name="hid" id="hid"  value="" />
          <input type="hidden" name="hid1" id="hid1"  value="" />
     </td>
     
     <td width="13%"> <bean:message key="lbl.usedVehicleModel" /><span><font color="red">*</font></span>   </td>
  <td> <html:text property="usedVehicleModelSearch"  styleId="usedVehicleModelSearch" maxlength="50" value="${usedPricingYearList[0].usedVehicleModelSearch}" readonly="true" />        
	</td>
	
	</tr>
	
			<tr>
			 <td width="10%" ><bean:message key="lbl.usedVehicleState"/></td>
			  <td width="10%" >
 <html:text property="usedVehicleState" styleId="usedVehicleState" styleClass="text" value="${usedPricingYearList[0].usedVehicleState}"  readonly="true" tabindex="-1"/>
    <html:hidden  property="usedState" styleId="usedState"  value="${usedPricingYearList[0].usedState}" />
    <input type="hidden"  name="uState" id="uState"  value="${usedPricingYearList[0].usedState}" />
    <html:button property="vehicleStateButton" styleId="vehicleStateButton" 
    onclick="openLOVCommon(2060,'vehiclePricing','usedVehicleState','','', '','','','usedState');"
     value=" " styleClass="lovbutton"> </html:button>
   </td>
   
   <td width="8%"><bean:message key="lbl.usedVehicleBranch"/></td>
	
		   <td width="8%">
 <html:text property="usedVehicleBranch" styleId="usedVehicleBranch" styleClass="text" readonly="true"  tabindex="-1"value="${usedPricingYearList[0].usedVehicleBranch}"/>
	<html:hidden  property="usedBranch" styleId="usedBranch"  value="${usedPricingYearList[0].usedBranch}" />	
	<input type="hidden"  name="uBranch" id="uBranch"  value="${usedPricingYearList[0].usedBranch}" />	  
	 <html:button property="vehicleBranchButton" styleId="vehicleBranchButton"		 
	onclick="openLOVCommon(2061,'vehiclePricing','usedVehicleBranch','usedVehicleState','usedBranch','usedState','Please select State','','usedBranch','hid');"	
	value=" " styleClass="lovbutton"> </html:button>	
	  <input type="hidden" name="hid" id="hid"  value="" />
			  </td>		
			
</tr>
			
	
	</logic:present>
	
    		</table>
		</fieldset>
		<fieldset>	
		 <legend><bean:message key="lbl.usedVehicalPricing"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
 
        <td ><b><bean:message key="lbl.usedVehicleYear"/><span><font color="red">*</font></span></b></td>
        <td ><b><bean:message key="lbl.usedVehiclePrice"/><span><font color="red">*</font></span></b></td>
       
         
 
	</tr>
	
	
	
<logic:present name="usedPricingYearList">
	<logic:iterate name="usedPricingYearList" id="sublist" indexId="counter">
	
	
	<tr class="white1">

     <td width="10%">
     <html:text property="usedVehicleYearArr" styleId="usedVehicleYear${counter+1}" readonly="true" value="${sublist.usedVehicleYear}"/>
      </td>
        
        <td width="10%">
       <html:text property="usedVehiclePriceArr" styleId="usedVehiclePrice${counter+1}" styleClass="text" maxlength="16" onkeypress="return isNumberKey(event);" value="${sublist.usedVehiclePrice}"/>
             </td>
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		


 </table>    </td>
</tr>

<tr>
		  	<td colspan="2">
		  		 <br>
      				
     				<logic:notPresent name="search" >   
     			<button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveUsedVehiclePricingAdd();" class="topformbutton2" ><bean:message key="button.save" /></button>
    			</logic:notPresent>
    			<logic:present name="search" >   			
   				 <button type="button" name="update" id="update" class="topformbutton2" onclick="return updateVehiclePricingdata();"><bean:message key="button.save" /></button>
   			</logic:present>
    			<br>
		    </td> 
		 </tr>
</table>
	</fieldset>
</html:form>
	</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

		<logic:present name="sms">
<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("vehiclePricing").action=contextPath+"/usedVehiclePricingBehind.do";
	    document.getElementById("vehiclePricing").submit();
	}
	
	
	
	
		if('<%=request.getAttribute("sms").toString()%>'=='U')
		{
		alert("<bean:message key="lbl.dataModify" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("vehiclePricing").action=contextPath+"/usedVehiclePricingBehind.do";
		document.getElementById("vehiclePricing").submit();
		}
	

		if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
		alert("<bean:message key="lbl.dataExist" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("vehiclePricing").action=contextPath+"/usedVehiclePricingBehind.do";
		document.getElementById("vehiclePricing").submit();
		}

</script>
</logic:present>

</body>
</html:html>