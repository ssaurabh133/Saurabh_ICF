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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>


</head>
	
<body onload="enableAnchor();document.getElementById('fieldVarificationCapSearchForm').referenceId.focus();">
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
<input type="hidden" name="businessDate" id="businessDate" value="${requestScope.businessDate}" />
<fieldset>
<legend><bean:message key="lbl.fieldVerificationCapturing" /></legend> 
	<fieldset>
	<legend><bean:message key="lbl.verificationDetails" /></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
  		<tr>
    		 <td width="13%"><bean:message key="lbl.referenceNo"/><span><font color="red">*</font></span></td>
    		 <logic:notPresent name="view">
    		 <td width="13%"><html:text property="referenceId" styleId="referenceId" maxlength="20" styleClass="text" value="${requestScope.list[0].referenceId}"/></td>  
    		 </logic:notPresent>
    		 <logic:present name="view">
    		 <td width="13%"><html:text property="referenceId" styleId="referenceId" maxlength="20" styleClass="text" value="${requestScope.list[0].referenceId}"readonly="true"/></td>  
    		 </logic:present>
    		 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
    		 <logic:notPresent name="view">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${requestScope.list[0].appraisalName}" onblur="return caseChangeAN();"/></td>
    		 </logic:notPresent>
    		 <logic:present name="view">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${requestScope.list[0].appraisalName}"readonly="true"/></td>
    		 </logic:present>
    		 
  		</tr> 
  		<tr>
  			<td width="13%"><bean:message key="lbl.varificationType"/><font color="red">*</font></td>
  			<td width="13%"><html:text property="varificationType" styleId="varificationType" maxlength="50" styleClass="text" value="Field Verification" readonly="true"></html:text><br /></td>
   			  
   			<td width="13%"><bean:message key="lbl.varificationSubType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationSubType" styleId="varificationSubType" maxlength="50" styleClass="text" value="${requestScope.list[0].varificationSubType}" readonly="true"></html:text><br /></td>
			 
 		</tr> 
 		<tr>
    		<td width="13%"><bean:message key="lbl.verificationMode"/><span><font color="red">*</font></span></td>
    		<logic:notPresent name="view">
    		<td width="13%"><html:select property="verificationMode" styleId="verificationMode" styleClass="text" value="${requestScope.list[0].verificationMode}">
    									<html:option  value="">----Select----</html:option>
           								<html:option value="P">Phone</html:option>
           								<html:option value="W">Website</html:option>
           								<html:option value="F">Field Visit</html:option>
           								
       						</html:select>
       		</td></logic:notPresent>
       		<logic:present name="view">
       		<td width="13%"><html:text property="verificationMode"   styleId="appraisalName" styleClass="text" value="${requestScope.list[0].verificationMode}" readonly="true"></html:text></td>
    		 </logic:present>
       		<td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
       		<logic:notPresent name="view">
			<td ><html:text property="appraisalDate"   styleClass="text" styleId="appraisalDate" maxlength="10" onchange="return checkDate('appraisalDate');" value="${requestScope.list[0].appraisalDate}"/></td>
			</logic:notPresent>
			<logic:present name="view">
			<td ><html:text property="appDate"   styleClass="text" styleId="" maxlength="10"  value="${requestScope.list[0].appraisalDate}" readonly="true"/></td> 
    		 </logic:present>

 		</tr> 
 	</table> 
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.verificationInformation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	   	<tr>
    		<td width="13%"><bean:message key="lbl.personToMeet"/><span><font color="red">*</font></span></td>
    		<logic:notPresent name="view">
    		<td width="13%"><html:text property="personToMeet" styleId="personToMeet" maxlength="50" styleClass="text" value="${requestScope.list[0].personToMeet}" onblur="return caseChangePM();"/></td>
    		</logic:notPresent>
    		<logic:present name="view">
    		<td width="13%"><html:text property="personToMeet" styleId="personToMeet" maxlength="50" styleClass="text" value="${requestScope.list[0].personToMeet}"readonly="true"/></td>
    		 </logic:present>
  		    <td width="13%"><bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span></td>
  		    <logic:notPresent name="view">
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" maxlength="50" styleClass="text" value="${requestScope.list[0].relationWithApplicant}" onblur="return caseChangeRA();"/></td>
    		</logic:notPresent>
    		<logic:present name="view">
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" maxlength="50" styleClass="text" value="${requestScope.list[0].relationWithApplicant}"readonly="true"/></td>
    		 </logic:present>
  	  	</tr> 
 		<tr>
    		<td width="13%"><bean:message key="lbl.phone1"/><span><font color="red">*</font></span></td>
    		<logic:notPresent name="view">
    		<td width="13%"><html:text property="phone1" styleId="phone1" maxlength="10" styleClass="text" value="${requestScope.list[0].phone1}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
    		</logic:notPresent>
    		<logic:present name="view">
    		<td width="13%"><html:text property="phone1" styleId="phone1" maxlength="10" styleClass="text" value="${requestScope.list[0].phone1}" readonly="true" style="text-align: right"/></td>
    		</logic:present>
		    <td width="13%"><bean:message key="lbl.phone2"/></td>
		    <logic:notPresent name="view">
    		<td width="13%"><html:text property="phone2" styleId="phone2" maxlength="20" styleClass="text" value="${requestScope.list[0].phone2}" style="text-align: right" onkeypress="return numbersonly(event,id,20)" onkeyup="checkNumber(this.value, event, 20,id);"/></td>
    		</logic:notPresent>
    		<logic:present name="view">
    		<td width="13%"><html:text property="phone2" styleId="phone2"  style="text-align: right" maxlength="20" styleClass="text" value="${requestScope.list[0].phone2}" readonly="true"/></td>
    		 </logic:present>
  		</tr>
	    <tr>
    		<td width="13%"><bean:message key="lbl.email"/></span></td>
    		<logic:notPresent name="view">
    		<td width="13%"><html:text property="email" styleId="email" maxlength="100" styleClass="text" value="${requestScope.list[0].email}"/></td>
    		</logic:notPresent>
    		<logic:present name="view">
    		<td width="13%"><html:text property="email" styleId="email" maxlength="100" styleClass="text" value="${requestScope.list[0].email}" readonly="true"/></td>
    		 </logic:present>
 		</tr>
 	</table>
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.appraisalObservation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
 			<td width="13%"><bean:message key="lbl.officeAddress"/><span><font color="red">*</font></span></td>
 			<logic:notPresent name="view">
			<td width="13%"><html:select property="officeAddress" styleId="officeAddress" styleClass="text" value="${requestScope.list[0].officeAddress}">
           								<html:option  value="">----Select----</html:option>
           								<html:option value="Y">Confirmed</html:option>
           								<html:option value="N">Not-Confirmed</html:option>
       						</html:select>
       		
       		</td>  </logic:notPresent>
       		<logic:present name="view">
       		<td  width="13%"><html:text property="officeAddress"   styleClass="text" styleId="officeAddress" maxlength="10"  readonly="true" value="${requestScope.list[0].officeAddress}"/></td>
    		 </logic:present>
            <td width="13%"><bean:message key="lbl.officeFacilities"/></td>
            <logic:notPresent name="view">
			<td  width="13%"><html:text property="officeFacilities"   styleClass="text" styleId="officeFacilities" maxlength="10" value="${requestScope.list[0].officeFacilities}"/></td>
			</logic:notPresent> 
			<logic:present name="view">
			<td  width="13%"><html:text property="officeFacilities"   styleClass="text" styleId="officeFacilities" maxlength="10" value="${requestScope.list[0].officeFacilities}" readonly="true"/></td> 
    		 </logic:present>
		</tr>
		<tr>
 			<td width="13%"><bean:message key="lbl.bussBoardScene"/></td>
 			<logic:notPresent name="view">
			<td width="13%"><html:select property="bussinessBoardScene" styleId="bussinessBoardScene" styleClass="text" value="${requestScope.list[0].bussinessBoardScene}">
           								<html:option  value="">----Select----</html:option>
           								<html:option value="Y">Yes</html:option>
           								<html:option value="N">No</html:option>
       						</html:select></td>  
       		</logic:notPresent>
       		<logic:present name="view">
       		<td  width="13%"><html:text property="bussinessBoardScene"   styleClass="text" styleId="bussinessBoardScene" maxlength="10" value="${requestScope.list[0].bussinessBoardScene}" readonly="true"/></td>
    		 </logic:present>
          	<td width="13%"><bean:message key="lbl.workEnviroment"/></td>
          	<logic:notPresent name="view">
		  	<td width="13%"><html:select property="workEnvironment" styleId="workEnvironment" styleClass="text" value="${requestScope.list[0].workEnvironment}">
          							 <html:option  value="">----Select----</html:option>
           							 <html:option value="M">Make Shift</html:option>
           							 <html:option value="P">Permanent</html:option>
       					  </html:select>
       	 	</td>  
       	 	</logic:notPresent>
       	 	<logic:present name="view">
       	 	<td  width="13%"><html:text property="workEnvironment"   styleClass="text" styleId="workEnvironment" maxlength="10" value="${requestScope.list[0].workEnvironment}" readonly="true"/></td>
    		 </logic:present>
		</tr>
		<tr>
 			<td width="13%"><bean:message key="lbl.bussActivity"/></td>
 			<logic:notPresent name="view">
			<td width="13%"><html:select property="bussinessActivity" styleId="bussinessActivity" styleClass="text" value="${requestScope.list[0].bussinessActivity}" >
          								 <html:option  value="">----Select----</html:option>
           								 <html:option value="H">High</html:option>
           								 <html:option value="M">Medium</html:option>
           								 <html:option value="L">Low</html:option>
       						</html:select>
       		</td> 
       		</logic:notPresent>  
       		<logic:present name="view">
       		<td  width="13%"><html:text property="bussinessActivity"   styleClass="text" styleId="bussinessActivity" maxlength="10" value="${requestScope.list[0].bussinessActivity}" readonly="true"/></td>
    		 </logic:present>    
   			<td width="13%"><bean:message key="lbl.officeInteriors"/></td>
   			<logic:notPresent name="view">
   			<td  width="13%"><html:text property="officeInteriors"   styleClass="text" styleId="officeInteriors" maxlength="10" value="${requestScope.list[0].officeInteriors}"/></td>
   			</logic:notPresent> 
   			<logic:present name="view">
   			<td  width="13%"><html:text property="officeInteriors"   styleClass="text" styleId="officeInteriors" maxlength="10" value="${requestScope.list[0].officeInteriors}" readonly="true"/></td> 
    		 </logic:present>
		</tr>
		<tr> 
   			<td width="13%"><bean:message key="lbl.officeExteriors"/></td>
   			<logic:notPresent name="view">
   			<td  width="13%"><html:text property="officeExteriors"   styleClass="text" styleId="officeExteriors" maxlength="10" value="${requestScope.list[0].officeExteriors}"/></td>
   			</logic:notPresent> 
   			<logic:present name="view">
   			<td  width="13%"><html:text property="officeExteriors"   styleClass="text" styleId="officeExteriors" maxlength="10" value="${requestScope.list[0].officeExteriors}" readonly="true"/></td> 
    		 </logic:present>
		</tr>
	</table>	
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.CPVDetail"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">		
		<tr>
 			<td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
 			<logic:notPresent name="view">
			<td width="13%"><html:select property="CPVStatus" styleId="CPVStatus" styleClass="text" value="${requestScope.list[0].CPVStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
       						</html:select>
       		</td> 
       		</logic:notPresent>
       		<logic:present name="view">
       		<td width="13%"><html:text property="CPVStatus" styleClass="text" styleId="CPVStatus" maxlength="100" readonly="true" tabindex="-1" value="${requestScope.list[0].CPVStatus}" /></td>
    		 </logic:present> 
            <td width="13%"><bean:message key="lbl.CPVNegReason"/><span><font color="red">*</font></span></td>
            <logic:notPresent name="view">
   			<td width="13%" valign="top">
   				<html:text property="CPVNegReasonDesc" styleClass="text" styleId="CPVNegReasonDesc" maxlength="100" readonly="true" tabindex="-1" value="${requestScope.CPVNegReasonDesc}"/>
				<html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${requestScope.lbxReasonId}"/>
				<input type="hidden" name="abc" id="abc"/>
				<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(190,'fieldVerificationForm','CPVNegReasonDesc','','', '','','','abc')" value=" " styleClass="lovbutton"> </html:button>
	        </td>
	        </logic:notPresent>
	        <logic:present name="view">
	        <td width="13%"><html:text property="CPVNegReasonDesc" styleClass="text" styleId="CPVNegReasonDesc" maxlength="100" readonly="true" tabindex="-1" value="${requestScope.CPVNegReasonDesc}" /></td>
    		 </logic:present>
   		</tr>
		<tr>
			<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
			<logic:notPresent name="view">
			<td width="13%"><textarea name="remarks" id="remarks" class="text">${requestScope.list[0].remarks}</textarea></td>
			</logic:notPresent>
			<logic:present name="view">
			<td width="13%"><textarea name="remarks" id="remarks" class="text" readonly="readonly">${requestScope.list[0].remarks}</textarea></td>
    		 </logic:present>
		</tr>
	</table>
	</fieldset>
<logic:notPresent name="view">
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
			
		  	<td align="left">
		    <button type="button" name="save" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return updateFieldVarCap();" ><bean:message key="button.save" /></button></td>
		</tr>
	</table>
</logic:notPresent>
<logic:present name="view">
    		 </logic:present>
</fieldset>
</html:form>

<logic:present name="save">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataSave" />");
		window.opener.location.href="fieldVarificationAction.do?method=addFieldCapture2";
        self.close();
	</script>
</logic:present>
<logic:present name="update">
	<script type="text/javascript">	
		alert("<bean:message key="lbl.dataUpdate" />");
		window.close();
    </script>
</logic:present>
   
  </body>
</html:html>
