<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.connect.CommonFunction"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" import="java.util.Calendar"%>
<%@ page language="java" import="java.text.SimpleDateFormat,java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	    
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
			
	
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	
	<%
		     ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	  
	         String dateFormat=resource.getString("lbl.dateForDisbursal");
			 Calendar currentDate = Calendar.getInstance();
			 SimpleDateFormat formatter= new SimpleDateFormat(dateFormat);
			 String sysDate = formatter.format(currentDate.getTime());	 
			 request.setAttribute("sysDate",sysDate);		
		 %>


	
		
   
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
<BODY oncontextmenu="return false;" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('customerForm');">
<logic:present name="pageStatuss">
<script type="text/javascript">
  // alert("Notok");
   top.opener.location.href="custEntryAction.do";
   //alert("ok");
    
</script>
</logic:present>
<!---->
<logic:present name="updateInDeal">
<script type="text/javascript">
  //alert("Notok");
   top.opener.location.href="custEntryAction.do";
   <%session.removeAttribute("updateInDeal");%>
  
    
</script>
</logic:present>

<input type="hidden" id="contextPath" name="contextPath" value="<%=path%>"/>
<logic:notPresent name="approve">

<DIV id=centercolumn >
<DIV id=revisedcontainer>

<div id="updateAddress">
<FIELDSET ><LEGEND>Customer Relation Details</LEGEND>
<HTML>
<HEAD>
<TITLE>Customer Relation Details</TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
</HEAD>

<BODY oncontextmenu="return false" onload="enableAnchor();document.getElementById('customerForm').addr_type.focus();">

<center><input type="hidden" id="duplicateAdd" value="${requestScope.commAddressCheck}"/></center>
<%-- Div for screen Saver (Calender picker)--%>

<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
		<logic:present name="image">
    	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent> 
    	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

		<div id=centercolumn>
		<div id=revisedcontainer>
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
    		
<html:form action="/customerAddressAction" styleId="customerForm" method="post">
 <input type="hidden" name="hcommon" id="hcommon" />
<logic:present name="update">
<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.relationShip" /></legend>
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />	
<html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId }" />
<html:hidden property="addressId" styleClass="text" styleId="addressId" value="${requestScope.addressId }" />
<html:hidden property="baseCustomerType" styleClass="text" styleId="baseCustomerType" value="${sessionScope.baseCustomerType }" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2"> 
			<td><B><bean:message key="lbl.customerID" /></b></td>
			<td><B><bean:message key="lbl.customerName" /></b></td>
			<td><B><bean:message key="lbl.primary" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			
		</tr>
			<logic:present name="briefAddrReation">
			<logic:iterate id="subbriefAddrReation" name="briefAddrReation"  indexId="counter">
		<tr class="white1">
			<html:hidden property="relationAddressId" styleId="relationAddressId${counter+1}" value="${subbriefAddrReation.relationAddressId}"/>
			<td > <html:text property="relationCustomerId" readonly="true" styleId="relationCustomerId${counter+1}" value="${subbriefAddrReation.relationCustomerId}" /></td>
			<td > <html:text property="relationCustomerName"  readonly="true" styleId="relationCustomerName${counter+1}" value="${subbriefAddrReation.relationCustomerName}" /></td>
			<td >${subbriefAddrReation.relationPhone}</td>
			<logic:equal name="subbriefAddrReation"	property="relationCustomerType" value="C">
			<td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeCorporate" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:equal>
                  <logic:notEqual name="subbriefAddrReation"	property="relationCustomerType" value="C">
                  <logic:equal name="baseCustomerType" value="I">
                  <td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeIndividual" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:equal>
                  <logic:notEqual name="baseCustomerType" value="I">
			<td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeCorporate" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:notEqual>
                  </logic:notEqual>
		
		</tr>
			
			</logic:iterate>
			</logic:present>		
		</table>
</table>
		<button type="button" name="button1" class="topformbutton2" title="Alt+S" accesskey="S" onclick="saveRelationDetails();"><bean:message key="button.save" /></button>

</logic:present>
<logic:notPresent name="update">
<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.relationShip" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />	
<html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId }" />	
<html:hidden property="addressId" styleClass="text" styleId="addressId" value="${requestScope.addressId }" />
<html:hidden property="baseCustomerType" styleClass="text" styleId="baseCustomerType" value="${sessionScope.baseCustomerType }" />	
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2"> 
			<td><B><bean:message key="lbl.customerID" /></b></td>
			<td><B><bean:message key="lbl.customerName" /></b></td>
			<td><B><bean:message key="lbl.primary" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			
		</tr>
			<logic:present name="briefAddrReation">
			<logic:iterate id="subbriefAddrReation" name="briefAddrReation"  indexId="counter">
		<tr class="white1">
			<html:hidden property="relationAddressId" styleId="relationAddressId${counter+1}" value="${subbriefAddrReation.relationAddressId}"/>
			<td > <html:text property="relationCustomerId"  readonly="true" styleId="relationCustomerId${counter+1}" value="${subbriefAddrReation.relationCustomerId}" /></td>
			<td > <html:text property="relationCustomerName" readonly="true"  styleId="relationCustomerName${counter+1}" value="${subbriefAddrReation.relationCustomerName}" /></td>
			<td >${subbriefAddrReation.relationPhone}</td>
			<logic:equal name="subbriefAddrReation"	property="relationCustomerType" value="C">
			<td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeCorporate" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:equal>
                  <logic:notEqual name="subbriefAddrReation"	property="relationCustomerType" value="C">
			<logic:equal name="baseCustomerType" value="I">
                  <td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeIndividual" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:equal>
                  <logic:notEqual name="baseCustomerType" value="I">
			<td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeCorporate" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:notEqual>
                  </logic:notEqual>
		
		</tr>
			
			</logic:iterate>
			</logic:present>		
		</table>
</table>
	<button type="button" name="button1" class="topformbutton2" title="Alt+S" accesskey="S" onclick="saveRelationDetails();"><bean:message key="button.save" /></button>
					
</logic:notPresent>
</html:form></BODY></HTML>
</FIELDSET> </div></DIV></DIV>
</logic:notPresent>
<logic:present name="approve">
<DIV id=centercolumn>
<DIV id=revisedcontainer>
<FIELDSET><LEGEND><bean:message key="individual.reference" /></LEGEND>
<HTML>
<HEAD>
<TITLE> <bean:message key="individual.reference" /></TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
</HEAD>
<BODY>
<html:form action="/customerAddressAction" styleId="customerForm">
<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.relationShip" /></legend>
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />	
<html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId }" />
<html:hidden property="addressId" styleClass="text" styleId="addressId" value="${requestScope.addressId }" />
<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2"> 
			<td><B><bean:message key="lbl.customerID" /></b></td>
			<td><B><bean:message key="lbl.customerName" /></b></td>
			<td><B><bean:message key="lbl.primary" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			
		</tr>
			<logic:present name="briefAddrReation">
			<logic:iterate id="subbriefAddrReation" name="briefAddrReation"  indexId="counter">
		<tr class="white1">
			<html:hidden property="relationAddressId" styleId="relationAddressId${counter+1}" value="${subbriefAddrReation.relationAddressId}"/>
			<td > <html:text property="relationCustomerId"  readonly="true" styleId="relationCustomerId${counter+1}" value="${subbriefAddrReation.relationCustomerId}" /></td>
			<td > <html:text property="relationCustomerName"  readonly="true" styleId="relationCustomerName${counter+1}" value="${subbriefAddrReation.relationCustomerName}" /></td>
			<td >${subbriefAddrReation.relationPhone}</td>
			<logic:equal name="subbriefAddrReation"	property="relationCustomerType" value="C">
			<td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" disabled="true"  property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeCorporate" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:equal>
                  <logic:notEqual name="subbriefAddrReation"	property="relationCustomerType" value="C">
			<td><html:select styleClass="text"  styleId="relation${counter+1}" value="${subbriefAddrReation.relation}" disabled="true" property="relation">
                  <html:option value="">Select</html:option>  
        		 <html:optionsCollection name="relationTypeIndividual" label="relationshipS" value="relationCode"/>        				
                  </html:select>
                  </td>
                  </logic:notEqual>
		
		</tr>
			
			</logic:iterate>
			</logic:present>		
		</table>
</FIELDSET> 
</html:form>
</FIELDSET> 
</DIV></DIV>
</logic:present>


<logic:present name="sms">
 <script type="text/javascript">
 
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		
		alert("<bean:message key="msg.notepadSaved" />");
		
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
			alert('<bean:message key="lbl.errorSuccess" />');
	}
	
	</script>
</logic:present>
<script>
	setFramevalues("customerForm");
</script>
</BODY>

</HTML>