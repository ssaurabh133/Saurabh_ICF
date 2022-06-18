<!-- 
Author Name :- Nazia
Date of Creation :3-04-2013
Purpose :-  screen for the legal decline
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
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/reassignCase.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
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

<body onload="fntab();">
<html:form styleId="reassignCaseForm"  method="post"  action="/legalCaseInitiationMakerDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.ReassignCaseDtl" /></legend>
  <table width="100%">
  
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerLoanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${sessionScope.reassignList[0].loanNo}" /></td>
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${sessionScope.reassignList[0].loanId}" />
		
		
		<td><bean:message key="lbl.legalCaseInitMakerCustName" /></td>
		
		<td><html:text property="customerName" styleClass="text" readonly="true" styleId="customerName" value="${sessionScope.reassignList[0].customerName}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerBranch" /></td>
		
		<td><html:text property="branch" styleClass="text" readonly="true" styleId="branch" value="${sessionScope.reassignList[0].branch}" /></td>
		
		<td><bean:message key="lbl.legalCaseInitMakerProduct" /></td>
		
		<td><html:text property="product" styleClass="text" readonly="true" styleId="product" value="${sessionScope.reassignList[0].product}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerScheme" /></td>
		
		<td><html:text property="scheme" styleClass="text" readonly="true" styleId="scheme" value="${sessionScope.reassignList[0].scheme}" /></td>
			
		
		<td><bean:message key="lbl.legalCaseInitMakerDPD" /></td>
		
		<td><html:text property="dpd" styleClass="text" readonly="true" styleId="dpd" value="${sessionScope.reassignList[0].dpd}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalTotalOutstanding" /></td>
		
		<td><html:text property="totalOutstanding" readonly="true" styleClass="text" styleId="totalOutstanding" value="${sessionScope.reassignList[0].totalOutstanding}" /></td>
		
		
		<td><bean:message key="lbl.legalOtherCharges" /></td>
		
		<td><html:text property="otherCharges" styleClass="text" readonly="true" styleId="otherCharges" value="${sessionScope.reassignList[0].otherCharges}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalPrincipalOutstanding" /></td>
		
		<td><html:text property="principalOutstanding" readonly="true" styleClass="text" styleId="principalOutstanding" value="${sessionScope.reassignList[0].principalOutstanding}" /></td>
		
		
		
		<td><bean:message key="lbl.legalCaseType" /></td>
		<td>
		<html:text property="caseTypeDesc" styleId="caseTypeDesc" value="${sessionScope.reassignList[0].caseTypeDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value="${sessionScope.reassignList[0].lbxCaseTypeCode}"  />
		
		</td>
	  
	</tr>
    
    <tr>
	
		<td><bean:message key="lbl.legalImemo" /></td>
		
		<td><html:text property="initMakerImemo" styleClass="text" styleId="initMakerImemo" value="${sessionScope.reassignList[0].initMakerImemo}" /></td>
		
		
		
		<td><bean:message key="lbl.legalReason" /></td>
		
		<td>
		<html:text property="reasonDesc" styleId="reasonDesc" value="${sessionScope.reassignList[0].reasonDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${sessionScope.reassignList[0].lbxReasonId}"  />
		
		</td> 
		
	  
	</tr>  
    
    <tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerRemarks" /></td>
		
		<td><html:text property="initMakerRemarks" styleClass="text" styleId="initMakerRemarks" value="${sessionScope.reassignList[0].initMakerRemarks}" /></td>
		
	  
	</tr>
    
    <tr><td>
    
  
      
      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
   
  
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("reassignCaseForm").action="reassignCaseDispatch.do?method=searchreassignCase";
	    document.getElementById("reassignCaseForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>