<!--Author Name : Richa Bansal-->
<!--Date of Creation : 21-Apr-2013-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoMarkingAuthor.js"></script>
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

<body onload="enableAnchor();">
<html:form styleId="repoMarkingMakerForm"  method="post"  action="/repoMarkingAuthorDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.repoMarkingMaker" /></legend>
  <table width="100%">
  
	<tr>
	
		<td><bean:message key="lbl.loanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${sessionScope.repoMakingAuthorList[0].loanNo}" /></td>
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${sessionScope.repoMakingAuthorList[0].loanId}" />
		
		
		<td><bean:message key="lbl.branch" /></td>
		
		<td><html:text property="branch" readonly="true" styleClass="text" styleId="branch" value="${sessionScope.repoMakingAuthorList[0].branch}" /></td>
	  
	</tr>
	
	<tr>
	<td><bean:message key="lbl.product" /></td>
		
		<td><html:text property="product" readonly="true" styleId="product" styleClass="text" value="${sessionScope.repoMakingAuthorList[0].product}" /></td>

		<td><bean:message key="lbl.scheme" /></td>
		
		<td><html:text property="scheme" readonly="true" styleId="scheme" styleClass="text" value="${sessionScope.repoMakingAuthorList[0].scheme}" /></td>
		
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.loanAmount" /></td>
		
		<td><html:text property="loanAmount" readonly="true" styleClass="text" styleId="loanAmount" value="${sessionScope.repoMakingAuthorList[0].loanAmount}" /></td>
		<td><bean:message key="lbl.balancePrincipal" /></td>
		
		<td><html:text property="balancePrincipal" readonly="true" styleClass="text" styleId="balancePrincipal" value="${sessionScope.repoMakingAuthorList[0].balancePrincipal}" /></td>
		
</tr>
	
	<tr>
	
	
		<td><bean:message key="lbl.overduePrincipal" /></td>
		
		<td><html:text property="overduePrincipal" readonly="true" styleClass="text" styleId="overduePrincipal" value="${sessionScope.repoMakingAuthorList[0].overduePrincipal}" /></td>
	 <td><bean:message key="lbl.overdueAmount" /></td>
		
		<td><html:text property="overdueAmount" readonly="true" styleClass="text" styleId="overdueAmount" value="${sessionScope.repoMakingAuthorList[0].overdueAmount}" /></td>
	</tr>
    
    <tr>
   
		

		<td><bean:message key="lbl.repoMarkingMakerDPD" /></td>
		
		<td><html:text property="dpd" readonly="true" styleClass="text" styleId="dpd" value="${sessionScope.repoMakingAuthorList[0].dpd}" /></td>
  
  <td><bean:message key="lbl.repoReason" /></td>
		
		<td>
		<html:text property="reasonDesc" styleId="reasonDesc" value="${sessionScope.repoMakingAuthorList[0].reasonDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${sessionScope.repoMakingAuthorList[0].lbxReasonId}"  />
		
		</td> 
	</tr>  
    
    <tr>

		<td><bean:message key="lbl.repoMarkingMakerRemarks" /></td>
		
		<td><textarea name="remarks" id="remarks" readonly="readonly" class="text" maxlength="500" >${sessionScope.repoMakingAuthorList[0].remarks}</textarea></td>
		
		<td><bean:message key="lbl.agency" /></td>
		
		<td><html:text property="agency" readonly="true" styleId="agency" value="${sessionScope.repoMakingAuthorList[0].agency}" styleClass="text"  tabindex="-1"/></td>
		
	  
	</tr>
	</table>		


</fieldset>
  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
   <input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
           
	</html:form>		

  </body>
		</html:html>