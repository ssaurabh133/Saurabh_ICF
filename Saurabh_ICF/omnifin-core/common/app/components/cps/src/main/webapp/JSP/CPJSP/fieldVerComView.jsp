<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		  
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
  
</head>
	
<body onload="enableAnchor();" >
<html:form styleId="fieldVerificationUpdationForm" action="/fieldVerificationUpdate" method="post" >
<html:javascript formName="FieldVarificationCapturingForm"/>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<fieldset>
<legend><bean:message key="lbl.fieldVerificationCapturing" /></legend> 
	<fieldset>
	<legend><bean:message key="lbl.verificationDetails" /></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
  		<tr>
    		 <td width="13%"><bean:message key="lbl.referenceNo"/></td>
    		 <td width="13%"><html:text property="referenceId" styleId="referenceId"  styleClass="text" value="${requestScope.list[0].referenceId}"readonly="true"/></td>  
    		 <td width="13%"><bean:message key="lbl.appraisalName"/></td>
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${requestScope.list[0].appraisalName}"readonly="true"/></td>
  		</tr> 
  		<tr>
  			<td width="13%"><bean:message key="lbl.varificationType"/></td>
   			<td width="13%"><html:text property="varificationType" styleId="varificationType" maxlength="50" styleClass="text" value="Field Verification" readonly="true"></html:text><br /></td>
			<td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
			<td ><html:text property="appraisalDate"   styleClass="text" styleId="appraisalDate" maxlength="10"  value="${requestScope.list[0].appraisalDate}" readonly="true"/></td> 
 		</tr> 
 		<tr>
    		<td width="13%"><bean:message key="lbl.verificationMode"/></td>
    		<td width="13%"><html:text property="verificationMode"   styleId="appraisalName" styleClass="text" maxlength="15" value="${requestScope.list[0].verificationMode}" readonly="true"></html:text></td>

 		</tr> 
 	</table> 
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.verificationInformation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	   	<tr>
    		<td width="13%"><bean:message key="lbl.personToMeet"/></td>
    		<td width="13%"><html:text property="personToMeet" styleId="personToMeet"  styleClass="text" value="${requestScope.list[0].personToMeet}"readonly="true"/></td>
  		    <td width="13%"><bean:message key="lbl.relationWithApplicant"/></td>
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" maxlength="50" styleClass="text" value="${requestScope.list[0].relationWithApplicant}"readonly="true"/></td>
  	  	</tr> 
 		<tr>
    		<td width="13%"><bean:message key="lbl.phone1"/></td>
    		<td width="13%"><html:text property="phone1" styleId="phone1" style="text-align: right" maxlength="10" styleClass="text" value="${requestScope.list[0].phone1}" readonly="true"/></td>
		    <td width="13%"><bean:message key="lbl.phone2"/></td>
    		<td width="13%"><html:text property="phone2" styleId="phone2" style="text-align: right"maxlength="20" styleClass="text" value="${requestScope.list[0].phone2}" readonly="true"/></td>
  		</tr>
	    <tr>
    		<td width="13%"><bean:message key="lbl.email"/></td>
    		<td width="13%"><html:text property="email" styleId="email" styleClass="text" value="${requestScope.list[0].email}" readonly="true"/></td>
 		</tr>
 	</table>
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.appraisalObservation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
 			<td width="13%"><bean:message key="lbl.officeAddress"/></td>
 			<td  width="13%"><html:text property="officeAddress"   styleClass="text" styleId="officeAddress" maxlength="10"  readonly="true" value="${requestScope.list[0].officeAddress}"/></td>
			<td width="13%"><bean:message key="lbl.officeFacilities"/></td>
			<td  width="13%"><html:text property="officeFacilities"   styleClass="text" styleId="officeFacilities" maxlength="10" value="${requestScope.list[0].officeFacilities}" readonly="true"/></td> 
		</tr>
		<tr>
 			<td width="13%"><bean:message key="lbl.bussBoardScene"/></td>
 			<td  width="13%"><html:text property="bussinessBoardScene"   styleClass="text" styleId="bussinessBoardScene" maxlength="10" value="${requestScope.list[0].bussinessBoardScene}" readonly="true"/></td>
			<td width="13%"><bean:message key="lbl.workEnviroment"/></td>
          	<td  width="13%"><html:text property="workEnvironment"   styleClass="text" styleId="workEnvironment" maxlength="10" value="${requestScope.list[0].workEnvironment}" readonly="true"/></td>
		</tr>
		<tr>
 			<td width="13%"><bean:message key="lbl.bussActivity"/></td>
 			<td  width="13%"><html:text property="bussinessActivity"   styleClass="text" styleId="bussinessActivity" maxlength="10" value="${requestScope.list[0].bussinessActivity}" readonly="true"/></td>
			<td width="13%"><bean:message key="lbl.officeInteriors"/></td>
   			<td  width="13%"><html:text property="officeInteriors"   styleClass="text" styleId="officeInteriors" maxlength="10" value="${requestScope.list[0].officeInteriors}" readonly="true"/></td> 
		</tr>
		<tr> 
   			<td width="13%"><bean:message key="lbl.officeExteriors"/></td>
   			<td  width="13%"><html:text property="officeExteriors"   styleClass="text" styleId="officeExteriors" maxlength="10" value="${requestScope.list[0].officeExteriors}" readonly="true"/></td> 
		</tr>
	</table>	
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.CPVDetail"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">		
		<tr>
 			<td width="13%"><bean:message key="lbl.CPVStatus"/></td>
			<td width="13%">
				<html:text property="CPVStatus" styleClass="text" styleId="CPVStatus" maxlength="100" readonly="true" tabindex="-1" value="${requestScope.list[0].CPVStatus}" />
			</td>  
            <td width="13%"><bean:message key="lbl.CPVNegReason"/></td>
   			<td width="13%" valign="top">
   				<html:text property="CPVNegReasonDesc" styleClass="text" styleId="CPVNegReasonDesc" maxlength="100" readonly="true" tabindex="-1" value="${requestScope.CPVNegReasonDesc}" />
			</td>
   		</tr>
		<tr>
			<td width="13%"><br /><br /></td>
			<td width="13%"><textarea name="remarks" id="remarks" class="text" readonly="readonly">${requestScope.list[0].remarks}</textarea></td>
		</tr>
	</table>
	</fieldset>
	</fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">		
		<tr>
			<td><button type="button" name="close" id="button" class="topformbutton2" title="Alt+X" accesskey="X" onclick="return closeSelfWindow();" ><bean:message key="button.close" /></button></td>
		</tr>
		</table>
	</html:form>
	</body>
	</html:html>