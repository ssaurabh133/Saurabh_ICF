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
<FIELDSET ><LEGEND>Sarva Suraksha Details</LEGEND>
<HTML>
<HEAD>
<TITLE>Sarva Suraksha Details</TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
</HEAD>

<BODY oncontextmenu="return false" onload="enableAnchor();calculateAgeOnLoad();document.getElementById('customerForm').addr_type.focus();">

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
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

		<div id=centercolumn>
		<div id=revisedcontainer>
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" >
    		
<html:form action="/customerAddressAction" styleId="customerForm" method="post">
 <input type="hidden" name="hcommon" id="hcommon" />
<logic:present name="update">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />
 <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
 <html:hidden property="bp_type" styleClass="text" styleId="bp_type" value="CS" />
 <html:hidden property="refId" styleClass="text" styleId="refId" value="${customerList[0].refId}" />
	<tr>
        <td><bean:message key="lbl.nomineeName" /><font color="red">*</font></td>
        <td><html:text property="nominee" styleId="nominee" styleClass="text" value="${customerList[0].nominee}" onchange="return upperMe('nominee');" maxlength="50"/></td>
        <td><bean:message key="lbl.nomineeRelation" /><font color="red">*</font></td>
         <td><html:select styleClass="text"  styleId="relationshipS" value="${customerList[0].relationshipS}" property="relationshipS">
                  <html:option value="">Select</html:option>  
<!--        				<logic:present name="relationType">-->
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
<!--        				</logic:present>-->
                  </html:select>
                  </td>
	</tr>
    <tr>
     <!--   <td><bean:message key="lbl.nomineeDOB" /><font color="red">*</font></td>
        <td><html:text property="nomineeDOB" styleId="nomineeDOB" styleClass="text" value="${customerList[0].nomineeDOB}"  maxlength="50"/></td>
      -->
		 
		   <td><bean:message key="individual.birthdate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
          <td><html:text property="nomineeDOB" styleId="incorporationDate" styleClass="text" value="${customerList[0].nomineeDOB}" onchange="calculateAge();" /></td>
          <input type="hidden" name="sysDate" id="sysDate" value="${userobject.businessdate}" />
          <input type="hidden" name="approve" id="approve" value="N" />
	</tr>
	

	
	<tr>
        <td><span class="white">
        <logic:present name="customerList">
        
        <button type="button" name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick=" return updateSarvaSuraksha();"><bean:message key="button.save" /></button>
       	</logic:present>
        <logic:notPresent name="customerList">
        
        <button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return insertSarva();"><bean:message key="button.save" /></button>
        
        <button type="button" name="button2"  class="topformbutton2" title="Alt+C" accesskey="C" onclick="return allClearSarva();"><bean:message key="button.clear" /></button>
        </logic:notPresent>
        </span></td>
	</tr>
</table>

<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.SravSuraksha" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2">
	        <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"><bean:message key="contact.select" /></b></td> 
			<td><B><bean:message key="lbl.nomineeName" /></b></td>
			<td><B><bean:message key="lbl.nomineeRelation" /></b></td>
			<td><B><bean:message key="lbl.nomineeDOB" /></b></td>
			
		</tr>
			<logic:present name="briefAddr">
			<logic:iterate id="subbriefAddr" name="briefAddr">
		<tr class="white1">
			<td ><input type="checkbox" name="chk" id="chk" value="${subbriefAddr.refId}"/></td>
			<td ><a href="#" id="anchor0" onclick="return modifySaravSuraksha(${subbriefAddr.refId});">${subbriefAddr.nominee}</a></td>
			<td >${subbriefAddr.relationShipDesc}</td>
			<td>${subbriefAddr.nomineeDOB}</td>
		
		</tr>	
			</logic:iterate>
			</logic:present>		
		</table>
</table>
		<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteSarvSurksha();"><bean:message key="button.delete" /></button>

</logic:present>
<logic:notPresent name="update">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />
<html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
	<tr>
        <td><bean:message key="lbl.nomineeName" /><font color="red">*</font></td>
        <td><html:text property="nominee" styleId="nominee" styleClass="text" value="${customerList[0].nominee}" onchange="return upperMe('nominee');" maxlength="50"/></td>
        <td><bean:message key="lbl.nomineeRelation" /><font color="red">*</font></td>
       <td><html:select styleClass="text"  styleId="relationshipS" value="${customerList[0].relationshipS}" property="relationshipS">
                  <html:option value="">Select</html:option>  
        				<logic:present name="relationType">
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
        				</logic:present>
                  </html:select>
                  </td>
	</tr>
    <tr>
       <td><bean:message key="individual.birthdate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
      <td><html:text property="nomineeDOB" styleId="incorporationDate" styleClass="text" value="${customerList[0].nomineeDOB}" onchange="calculateAge();" /></td>
          <input type="hidden" name="sysDate" id="sysDate" value="${userobject.businessdate}" />
          <input type="hidden" name="approve" id="approve" value="N" />
	</tr>
		
	
			
	
	
	<tr>
        <td><span class="white">
        <logic:present name="customerList">
        <button type="button" name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick=" return updateSarvaSuraksha();"><bean:message key="button.save" /></button>
        </logic:present>
        <logic:notPresent name="customerList">
        
        <button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return insertSarva();"><bean:message key="button.save" /></button>
        
        <button type="button" name="button2"  class="topformbutton2" title="Alt+C" accesskey="C" onclick="return allClearSarva();"><bean:message key="button.clear" /></button>
        </logic:notPresent>
        </span></td>
	</tr>
</table>

<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.SravSuraksha" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />		
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2">
	        <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"><bean:message key="contact.select" /></b></td> 
			<td><B><bean:message key="lbl.nomineeName" /></b></td>
			<td><B><bean:message key="lbl.nomineeRelation" /></b></td>
			<td><B><bean:message key="lbl.nomineeDOB" /></b></td>
		
		</tr>
			<logic:present name="briefAddr">
			<logic:iterate id="subbriefAddr" name="briefAddr">
		<tr class="white1">
			<td ><input type="checkbox" name="chk" id="chk" value="${subbriefAddr.refId}"/></td>
			<td ><a href="#" id="anchor0" onclick="return modifySaravSuraksha(${subbriefAddr.refId});">${subbriefAddr.nominee}</a></td>
			<td >${subbriefAddr.relationShipDesc}</td>
			<td>${subbriefAddr.nomineeDOB}</td>
		
		</tr>	
			</logic:iterate>
			</logic:present>		
		</table>
</table>
<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteSarvSurksha();"><bean:message key="button.delete" /></button>
					
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
        <td><bean:message key="lbl.nomineeName" /><font color="red">*</font></td>
        <td><html:text property="nominee" styleId="nominee" styleClass="text" value="${customerList[0].nominee}" onchange="return upperMe('nominee');" maxlength="50" disabled="true"/></td>
        <td><bean:message key="lbl.nomineeRelation" /><font color="red">*</font></td>
         <td><html:text property="relationshipS" styleId="relationshipS" styleClass="text" value="${customerList[0].relationShipDesc}" onchange="return upperMe('relationshipS');" maxlength="50" disabled="true"/></td>
	</tr>
    <tr>
       <td><bean:message key="individual.birthdate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
        <td><html:text property="nomineeDOB" styleId="incorporationDate" styleClass="text" value="${customerList[0].nomineeDOB}" disabled="true" onchange="calculateAge();" /></td>
          <input type="hidden" name="sysDate" id="sysDate" value="${userobject.businessdate}" />
          <input type="hidden" name="approve" id="approve" value="N" />
	</tr>
		
	
</table>
<td colspan="4" >
<FIELDSET><legend><bean:message key="individual.SravSuraksha" /></legend>
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
			<td><B><bean:message key="lbl.nomineeName" /></b></td>
			<td><B><bean:message key="lbl.nomineeRelation" /></b></td>
			<td><B><bean:message key="lbl.nomineeDOB" /></b></td>
	
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
			<td ><a href="#" id="anchor0" onclick="return modifySaravSuraksha(${subbriefAddr.refId});">${subbriefAddr.nominee}</a></td>
			<td >${subbriefAddr.relationShipDesc}</td>
			<td>${subbriefAddr.nomineeDOB}</td>
		
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
 alert("You can't move without saving before Individual/Corporate Details!!!");
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
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displaySarvSurakshaDetails";
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		
		alert("<bean:message key="lbl.IndividualCustAdress" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateSarvaSurksha";
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='CI')
	{
		
		
		alert("<bean:message key="lbl.IndividualAddressUpdated" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displaySarvSurakshaDetails";
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
		
		
		alert("<bean:message key="lbl.IndividualAddressUpdated" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateSarvaSurksha";
	    document.getElementById('customerForm').submit();
		
	}

	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
			alert('<bean:message key="lbl.errorSuccess" />');
			document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displaySarvSurakshaDetails";
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