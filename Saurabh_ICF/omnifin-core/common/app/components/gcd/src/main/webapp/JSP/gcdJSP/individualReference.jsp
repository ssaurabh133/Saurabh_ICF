<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   
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
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<%-- <logic:present name="pageStatuss">
<script type="text/javascript">
  // alert("Notok");
   top.opener.location.href="custEntryAction.do";
   //alert("ok");
    
</script>
</logic:present> --%>
<!---->
<%-- <logic:present name="updateInDeal">
<script type="text/javascript">
  //alert("Notok");
   top.opener.location.href="custEntryAction.do";
   <%session.removeAttribute("updateInDeal");%>
  
    
</script>
</logic:present> --%>

<input type="hidden" id="contextPath" name="contextPath" value="<%=path%>"/>
<logic:notPresent name="approve">

<DIV id=centercolumn >
<DIV id=revisedcontainer>

<div id="updateAddress">
<FIELDSET ><LEGEND><bean:message key="individual.reference" /></LEGEND>
<HTML>
<HEAD>
<TITLE> <bean:message key="individual.reference" /></TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
</HEAD>

<BODY oncontextmenu="return false" onload="enableAnchor();document.getElementById('customerForm').addr_type.focus();">

<center><input type="hidden" id="duplicateAdd" value="${requestScope.commAddressCheck}"/></center>

<html:form action="/customerAddressAction" styleId="customerForm" method="post">
 <input type="hidden" name="hcommon" id="hcommon" />
<logic:present name="update">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />
 <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
 <html:hidden property="bp_type" styleClass="text" styleId="bp_type" value="CS" />
	<tr>
        <td><bean:message key="lbl.fname" /><font color="red">*</font></td>
        <td><html:text property="firstName" styleId="firstName" styleClass="text" value="${customerList[0].firstName}" onchange="return upperMe('firstName');" maxlength="50"/></td>
        <td><bean:message key="lbl.mname" /></td>
        <td><html:text property="middleName" styleId="middleName" styleClass="text" value="${customerList[0].middleName}" onchange="return upperMe('middleName');" maxlength="50"/></td>
	</tr>
    <tr>
        <td><bean:message key="lbl.lname" /><font color="red">*</font></td>
        <td><html:text property="lastName" styleId="lastName" styleClass="text" value="${customerList[0].lastName}" onchange="return upperMe('lastName');" maxlength="50"/></td>
        <td><bean:message key="lbl.relationshipS" /><font color="red">*</font></td>
        
<!--        <td><html:text property="relationshipS" styleId="relationshipS" styleClass="text" value="${customerList[0].relationshipS}" onchange="return upperMe('relationshipS');" maxlength="50"/></td>-->
				  <td><html:select styleClass="text"  styleId="relationshipS" value="${customerList[0].relationshipS}" property="relationshipS">
                  <html:option value="">Select</html:option>  
<!--        				<logic:present name="relationType">-->
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
<!--        				</logic:present>-->
                  </html:select>
                  </td>
		
	</tr>
	<tr>
		<td> <bean:message key="lbl.mbnumber" /><font color="red">*</font></td>
		<td><html:text property="primaryRefMbNo" styleId="primaryRefMbNo" styleClass="text" maxlength="10" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].primaryRefMbNo}"/></td>	
        <td><bean:message key="lbl.landlineno" /></td>
		<td><html:text property="alternateRefPhNo" maxlength="20" styleId="alternateRefPhNo" styleClass="text" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].alternateRefPhNo}"/></td>	
	</tr>		
	
	<tr>	
		<td><bean:message key="lbl.knowingSince" /><font color="red">*</font></td>
		<td><html:text property="knowingSince" styleClass="text" styleId="knowingSince" maxlength="3" value="${customerList[0].knowingSince}" onkeypress="return isNumberKey(event);" /></td>					
		<html:hidden property="refId" value="${customerList[0].refId}"/>
		<td><bean:message key="lbl.addr" /></td>
		<td ><textarea name="addRef" id="addRef" class="text" maxlength="150" >${customerList[0].addRef}</textarea></td>
	</tr>	
			
<tr>
		  <td><bean:message key="individual.email" /></td>
          <td><html:text property="institutionEmail" styleId="institutionEmail" styleClass="text" value="${customerList[0].institutionEmail}" maxlength="100"/></td>
         </tr>
	
	<tr>
        <td><span class="white">
        <logic:notEqual name="functionId" value="500000123">
        <logic:present name="customerList">
        
        <button type="button" name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick=" return updateReference();"><bean:message key="button.save" /></button>
       	</logic:present>
        <logic:notPresent name="customerList">
        
        <button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return insertRef();"><bean:message key="button.save" /></button>
        
        <button type="button" name="button2"  class="topformbutton2" title="Alt+C" accesskey="C" onclick="return allClearRef();"><bean:message key="button.clear" /></button>
        </logic:notPresent>
        </logic:notEqual>
        </span></td>
	</tr>
</table>

<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.referencelist" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2">
	        <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"><bean:message key="contact.select" /></b></td> 
			<td><B><bean:message key="lbl.refname" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			<td><B><bean:message key="lbl.knowingSince" /></b></td>
			<td ><B><bean:message key="lbl.mbnumber" /></b></td>						
			<td><B><bean:message key="lbl.landlineno" /></b></td>	
		</tr>
			<logic:present name="briefAddr">
			<logic:iterate id="subbriefAddr" name="briefAddr">
		<tr class="white1">
			<td ><input type="checkbox" name="chk" id="chk" value="${subbriefAddr.refId}"/></td>
			<td ><a href="#" id="anchor0" onclick="return modifyReference(${subbriefAddr.refId});">${subbriefAddr.refName}</a></td>
			<td >${subbriefAddr.relationShipDesc}</td>
			<td>${subbriefAddr.knowingSince}</td>
			<td>${subbriefAddr.primaryRefMbNo}</td>
			<td>${subbriefAddr.alternateRefPhNo}</td>
		</tr>	
			</logic:iterate>
			</logic:present>		
		</table>
</table>
<logic:notEqual name="functionId" value="500000123">
		<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteRef();"><bean:message key="button.delete" /></button>
</logic:notEqual>
</logic:present>
<logic:notPresent name="update">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />
<html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
	<tr>
        <td><bean:message key="lbl.fname" /><font color="red">*</font></td>
        <td><html:text property="firstName" styleId="firstName" styleClass="text" value="${customerList[0].firstName}" onchange="return upperMe('firstName');" maxlength="50"/></td>
        <td><bean:message key="lbl.mname" /></td>
        <td><html:text property="middleName" styleId="middleName" styleClass="text" value="${customerList[0].middleName}" onchange="return upperMe('middleName');" maxlength="50"/></td>
	</tr>
    <tr>
        <td><bean:message key="lbl.lname" /><font color="red">*</font></td>
        <td><html:text property="lastName" styleId="lastName" styleClass="text" value="${customerList[0].lastName}" onchange="return upperMe('lastName');" maxlength="50"/></td>
        <td><bean:message key="lbl.relationshipS" /><font color="red">*</font></td>
<!--        <td><html:text property="relationshipS" styleId="relationshipS" styleClass="text" value="${customerList[0].relationshipS}" onchange="return upperMe('relationshipS');" maxlength="50"/></td>-->
				  <td><html:select styleClass="text"  styleId="relationshipS" value="${customerList[0].relationshipS}" property="relationshipS">
                  <html:option value="">Select</html:option>  
        				<logic:present name="relationType">
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
        				</logic:present>
                  </html:select>
                  </td>
	</tr>
	<tr>
		<td> <bean:message key="lbl.mbnumber" /><font color="red">*</font></td>
		<td><html:text property="primaryRefMbNo" styleId="primaryRefMbNo" styleClass="text" maxlength="10" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].primaryRefMbNo}"/></td>	
        <td><bean:message key="lbl.landlineno" /></td>
		<td><html:text property="alternateRefPhNo" maxlength="20" styleId="alternateRefPhNo" styleClass="text" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].alternateRefPhNo}"/></td>	
	</tr>		
	
	<tr>	
		<td><bean:message key="lbl.knowingSince" /><font color="red">*</font></td>
		<td><html:text property="knowingSince" styleClass="text" styleId="knowingSince" maxlength="3" value="${customerList[0].knowingSince}" onkeypress="return isNumberKey(event);" /></td>					
		<html:hidden property="refId" value="${customerList[0].refId}"/>
		<td><bean:message key="lbl.addr" /></td>
		<td ><textarea name="addRef" id="addRef" class="text" maxlength="150" >${customerList[0].addRef}</textarea></td>
	</tr>		
	<tr>
		  <td><bean:message key="individual.email" /></td>
          <td><html:text property="institutionEmail" styleId="institutionEmail" styleClass="text" value="${customerList[0].institutionEmail}" maxlength="100"/></td>
         </tr>
	
	<tr>
	
	<tr>
        <td><span class="white">
        <logic:notEqual name="functionId" value="500000123">
        <logic:present name="customerList">
        <button type="button" name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick=" return updateReference();"><bean:message key="button.save" /></button>
        </logic:present>
        <logic:notPresent name="customerList">
        
        <button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return insertRef();"><bean:message key="button.save" /></button>
        
        <button type="button" name="button2"  class="topformbutton2" title="Alt+C" accesskey="C" onclick="return allClearRef();"><bean:message key="button.clear" /></button>
        </logic:notPresent>
        </logic:notEqual>
        </span></td>
	</tr>
</table>

<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.referencelist" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />		
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2">
	        <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"><bean:message key="contact.select" /></b></td> 
			<td><B><bean:message key="lbl.refname" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			<td><B><bean:message key="lbl.knowingSince" /></b></td>
			<td ><B><bean:message key="lbl.mbnumber" /></b></td>						
			<td><B><bean:message key="lbl.landlineno" /></b></td>	
		</tr>
			<logic:present name="briefAddr">
			<logic:iterate id="subbriefAddr" name="briefAddr">
		<tr class="white1">
			<td ><input type="checkbox" name="chk" id="chk" value="${subbriefAddr.refId}"/></td>
			<td ><a href="#" id="anchor0" onclick="return modifyReference(${subbriefAddr.refId});">${subbriefAddr.refName}</a></td>
			<td >${subbriefAddr.relationShipDesc}</td>
			<td>${subbriefAddr.knowingSince}</td>
			<td>${subbriefAddr.primaryRefMbNo}</td>
			<td>${subbriefAddr.alternateRefPhNo}</td>
		</tr>	
			</logic:iterate>
			</logic:present>		
		</table>
</table>
<logic:notEqual name="functionId" value="500000123">
<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteRef();"><bean:message key="button.delete" /></button>
</logic:notEqual>										
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
<table cellspacing="0" cellpadding="3" border="0" width="100%">
		<tr>
        <td><bean:message key="lbl.fname" /><font color="red">*</font></td>
        <td><html:text property="firstName" styleId="firstName" styleClass="text" value="${customerList[0].firstName}" onchange="return upperMe('firstName');" maxlength="50" disabled="true"/></td>
        <td><bean:message key="lbl.mname" /></td>
        <td><html:text property="middleName" styleId="middleName" styleClass="text" value="${customerList[0].middleName}" onchange="return upperMe('middleName');" maxlength="50" disabled="true"/></td>
	</tr>
    <tr>
        <td><bean:message key="lbl.lname" /><font color="red">*</font></td>
        <td><html:text property="lastName" styleId="lastName" styleClass="text" value="${customerList[0].lastName}" onchange="return upperMe('lastName');" maxlength="50" disabled="true"/></td>
        <td><bean:message key="lbl.relationshipS" /><font color="red">*</font></td>
       <td><html:text property="relationshipS" styleId="relationshipS" styleClass="text" value="${customerList[0].relationShipDesc}" onchange="return upperMe('relationshipS');" maxlength="50" disabled="true"/></td>
<!--				  <td><html:select styleClass="text"  styleId="relationshipS" value="${customerList[0].relationshipS}" property="relationshipS" disabled="true">-->
<!--                  <html:option value="">Select</html:option>  -->
<!--        				<logic:present name="relationType">-->
<!--        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				-->
<!--        				</logic:present>-->
<!--                  </html:select>-->
                  </td>
	</tr>
	<tr>
		<td> <bean:message key="lbl.mbnumber" /><font color="red">*</font></td>
		<td><html:text property="primaryRefMbNo" styleId="primaryRefMbNo" styleClass="text" maxlength="10" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].primaryRefMbNo}" disabled="true"/></td>	
        <td><bean:message key="lbl.landlineno" /></td>
		<td><html:text property="alternateRefPhNo" maxlength="20" styleId="alternateRefPhNo" styleClass="text" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].alternateRefPhNo}" disabled="true"/></td>	
	</tr>		
	
	<tr>	
		<td><bean:message key="lbl.knowingSince" /><font color="red">*</font></td>
		<td><html:text property="knowingSince" styleClass="text" styleId="knowingSince" maxlength="3" value="${customerList[0].knowingSince}"  onkeypress="return isNumberKey(event);" disabled="true"/></td>					
		<html:hidden property="refId" value="${customerList[0].refId}"/>
		<td><bean:message key="lbl.addr" /></td>
		<td ><textarea name="addRef" id="addRef" class="text" maxlength="150" disabled="disabled" >${customerList[0].addRef}</textarea></td>
	</tr>	
	<tr>
		  <td><bean:message key="individual.email" /></td>
          <td><html:text property="institutionEmail" styleId="institutionEmail" styleClass="text" value="${customerList[0].institutionEmail}" maxlength="100" disabled="true" /></td>
         </tr>
	
	<tr>
</table>
<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.referencelist" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2">
	       	<td ><B>
            <logic:present name="underWriter">
            <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled" tabindex="-1"><bean:message key="contact.select" />
            </logic:present>
            <logic:notPresent name="underWriter">
            <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"><bean:message key="contact.select" />
            </logic:notPresent>
            </b></td>  
			<td><B><bean:message key="lbl.refname" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			<td><B><bean:message key="lbl.knowingSince" /></b></td>
			<td ><B><bean:message key="lbl.mbnumber" /></b></td>						
			<td><B><bean:message key="lbl.landlineno" /></b></td>	
		</tr>
			<logic:present name="briefAddr">
			<logic:iterate id="subbriefAddr" name="briefAddr">
		<tr class="white1">
			<td >
			<logic:present name="underWriter">
			<input type="checkbox" name="chk" id="chk" value="${subbriefAddr.refId}" disabled="disabled"/>
			</logic:present>
			<logic:notPresent name="underWriter">
			<input type="checkbox" name="chk" id="chk" value="${subbriefAddr.refId}"/>
			</logic:notPresent>
			</td>
			<td ><a href="#" id="anchor0" onclick="return modifyReference(${subbriefAddr.refId});">${subbriefAddr.refName}</a></td>
			<td >${subbriefAddr.relationShipDesc}</td>
			<td>${subbriefAddr.knowingSince}</td>
			<td>${subbriefAddr.primaryRefMbNo}</td>
			<td>${subbriefAddr.alternateRefPhNo}</td>
		</tr>	
			</logic:iterate>
			</logic:present>		
		</table>
</table>
</FIELDSET> 
</html:form>
</FIELDSET> 
</DIV></DIV>
</logic:present>

<logic:present name="back">
<script type="text/javascript">
 alert("You can't move without saving before Individual Details!!!");
</script>
</logic:present>

<logic:present name="closeSuccess">
<script type="text/javascript">
 alert("Deleted Successfully!!!");
</script>
</logic:present>
<logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='S')
	{
	   
		alert('<%=request.getAttribute("procval").toString()%>');
		
	}
	</script>
</logic:present>
<logic:present name="sms">
 <script type="text/javascript">
 if('<%=request.getAttribute("sms").toString()%>'=='I')
	{
		
		alert("<bean:message key="lbl.IndividualCustAdress" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualReference";
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		
		alert("<bean:message key="lbl.IndividualCustAdress" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateReference";
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='CI')
	{
		
		
		alert("<bean:message key="lbl.IndividualAddressUpdated" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualReference";
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
		
		
		alert("<bean:message key="lbl.IndividualAddressUpdated" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateReference";
	    document.getElementById('customerForm').submit();
		
	}

	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
			alert('<bean:message key="lbl.errorSuccess" />');
			document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualReference";
	}
	
	</script>
</logic:present>
<logic:present name="dublicateName">
	<script type="text/javascript">
	if('<%=request.getAttribute("dublicateName")%>'=='dublicateName')
	{
	   
		alert('<bean:message key="lbl.dublicateFirstReference" />');
		
	}
	</script>
</logic:present>
<script>
	setFramevalues("customerForm");
</script>
</BODY>

</HTML>