<!--Author Name : RICHA-->
<!--Date of Creation : 21-May-2011-->
<!--Purpose  : Information of country Master-->
<!--Documentation : -->

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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/RepoBillingApprovalMaker.js"></script>
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

<body onload="enableAnchor();fntab();init_fields();">
<html:form styleId="repoBillingApprovalForm"  method="post"  action="/repoBillingApprovalAuthorDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" name="canForward" value="${requestScope.canForward}" id="canForward" />
<fieldset>
<legend><bean:message key="lbl.repoBillingApprovalMaker" /></legend>
  <table width="100%">
<tr>

		<td width="20%"><bean:message key="lbl.loanNo"/></td>
		<td width="35%"  >
	
			<html:text property="searchLoanNo" styleClass="text" styleId="searchLoanNo" maxlength="10" readonly="true"  tabindex="-1" value="${sessionScope.sessionrepolist[0].searchLoanNo}"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${sessionScope.sessionrepolist[0].lbxDealNo}"/>
	 </td>
	 

	  <td width="17%"><bean:message key="lbl.customerName"/></td>
	 
		<td width="28%" ><html:text property="searchCustomerName" tabindex="2"  styleClass="text" styleId="searchCustomerName" maxlength="50" value="${sessionScope.sessionrepolist[0].searchCustomerName}"/></td> 
	   </tr>
    
      <tr>
    <td><bean:message key="lbl.billingToBeStopped" /></td>
    <td>
    <logic:iterate name="sessionrepolist" id="billingStatus" length="1">
		  	<logic:equal value="A" name="billingStatus" property="billingStopped">
		<input type="checkbox" name="billingStopped" id="billingStopped"  disabled="true" checked="checked"/>
   </logic:equal>
   	<logic:notEqual value="A" name="billingStatus" property="billingStopped">
		  	<input type="checkbox" name="billingStopped" id="billingStopped" disabled="true" />
		  	</logic:notEqual>
		  	</logic:iterate>
    </td> 
      
       <td><bean:message key="lbl.interestToBeStopped" /></td>
    <td>
    
     <logic:iterate name="sessionrepolist" id="interestStatus" length="1">
		  	<logic:equal value="A" name="interestStatus" property="interestStopped">
		<input type="checkbox" name="interestStopped" id="interestStopped"  disabled="true" checked="checked"/>
   </logic:equal>
   	<logic:notEqual value="A" name="interestStatus" property="interestStopped">
		  	<input type="checkbox" name="interestStopped" id="interestStopped" disabled="true" />
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</td>
   </tr>
<tr>

<td><bean:message key="lbl.SDInterest" /></td>
    <td>
    
     <logic:iterate name="sessionrepolist" id="sdInterestStatus" length="1">
		  	<logic:equal value="A" name="sdInterestStatus" property="SDInterest">
		<input type="checkbox" name="SDInterest" id="SDInterest"  disabled="true" checked="checked"/>
   </logic:equal>
   	<logic:notEqual value="A" name="sdInterestStatus" property="SDInterest">
		  	<input type="checkbox" name="SDInterest" id="SDInterest" disabled="true" />
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</td>
</tr>
	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
	<script type="text/javascript">

    	alert("sms::"+'<%=request.getAttribute("sms").toString()%>');
       alert("loanId::"+ '<%=request.getAttribute("loanId")%>');
   
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=openEditRepoBillingMaker&loanId="+'<%=request.getAttribute("loanId")%>';
	    document.getElementById("repoBillingApprovalForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=openEditRepoBillingMaker&loanId="+'<%=request.getAttribute("loanId")%>';
	    document.getElementById("repoBillingApprovalForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.forwardSuccess" />");
		document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=searchRepoBillingMaker";
	    document.getElementById("repoBillingApprovalForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
</script>
</logic:present>
  </body>
		</html:html>