<!--Author Name : Ankit Agarwal-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalCaseInitiationMaker.js"></script>
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

<body>
<html:form styleId="assignRejectAddForm"  method="post"  action="/assignRejectCaseDispatchAction" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.caseTypeMaster" /></legend>
  <table width="100%">
  
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerLoanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${sessionScope.assignRejectList[0].loanNo}" /></td>
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${sessionScope.assignRejectList[0].loanId}" />
		
		
		<td><bean:message key="lbl.legalCaseInitMakerCustName" /></td>
		
		<td><html:text property="customerName" styleClass="text" readonly="true" styleId="customerName" value="${sessionScope.assignRejectList[0].customerId}" readonly="true" /></td>
		<html:hidden  property="lbxcustomerId" styleId="lbxcustomerId" value="${requestScope.assignRejectList[0].lbxcustomerId}"/>
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerBranch" /></td>
		
		<td><html:text property="branch" styleClass="text" readonly="true" styleId="branch" value="${sessionScope.assignRejectList[0].branch}"/></td>
		
		<td><bean:message key="lbl.legalCaseInitMakerProduct" /></td>
		
		<td><html:text property="product" styleClass="text" readonly="true" styleId="product" value="${sessionScope.assignRejectList[0].product}"/></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerScheme" /></td>
		
		<td><html:text property="scheme" styleClass="text" readonly="true" styleId="scheme" value="${sessionScope.assignRejectList[0].scheme}" /></td>
			
		
		<td><bean:message key="lbl.legalCaseInitMakerDPD" /></td>
		
		<td><html:text property="dpd" styleClass="text" readonly="true" styleId="dpd" value="${sessionScope.assignRejectList[0].dpd}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalTotalOutstanding" /></td>
		
		<td><html:text property="totalOutsatanding" readonly="true" styleClass="text" styleId="totalOutsatanding" value="${sessionScope.assignRejectList[0].totalOutsatanding}" /></td>
		
		
		<td><bean:message key="lbl.legalOtherCharges" /></td>
		
		<td><html:text property="otherCharges" styleClass="text" readonly="true" styleId="otherCharges" value="${sessionScope.assignRejectList[0].otherCharges}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalPrincipalOutstanding" /></td>
		
		<td><html:text property="pos" readonly="true" styleClass="text" styleId="pos" value="${sessionScope.assignRejectList[0].pos}" /></td>
		
		
		
		<td><bean:message key="lbl.legalCaseType" /></td>
		<td>
		<html:text property="caseType"  styleId="caseType" value="${sessionScope.assignRejectList[0].caseType}" styleClass="text" readonly="true" tabindex="-1" />
		<html:hidden  property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value="${sessionScope.assignRejectList[0].lbxCaseTypeCode}"  />
		
		</td>
	  
	</tr>
    
    <tr>
	
		<td><bean:message key="lbl.legalImemo" /></td>
		
		<td><html:text property="iMemoBy" styleClass="text" styleId="iMemoBy" value="${sessionScope.assignRejectList[0].iMemoBy}" readonly="true"/></td>
		
		
		
		<td><bean:message key="lbl.legalReason" /></td>
		
		<td>
		<html:text property="reason" styleId="reason" value="${sessionScope.assignRejectList[0].reason}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${sessionScope.assignRejectList[0].lbxReasonId}"  />
		
		</td> 
		
	  
	</tr>  
    
    <tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerRemarks" /></td>
		
		<td><html:text property="remark" styleClass="text" styleId="remark" value="${sessionScope.assignRejectList[0].remark}" readonly="true" /></td>
		
	  
	</tr>
    
    <tr><td>
    
  
  
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		
  </body>
		</html:html>