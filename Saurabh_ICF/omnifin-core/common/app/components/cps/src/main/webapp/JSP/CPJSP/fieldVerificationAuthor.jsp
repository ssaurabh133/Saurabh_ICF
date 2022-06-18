<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>

		
		<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->

	</head>
	<body onload="enableAnchor();document.getElementById('closureAuthorForm').decision.focus();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/author" method="post" styleId="fieldVerificationAuthorForm">
	 
 
  <fieldset>
  <legend><bean:message key="lbl.author" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<html:hidden property="loanId" styleId="loanId" value="${sessionScope.loanId}"/>
		<html:hidden property="closureStatus" styleId="closureStatus" value="${sessionScope.closureStatus}"/>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td ><html:select property="decision" onchange="" styleId="decision" styleClass="text">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
           </html:select></td>
		</tr> 
		
		<tr>
		  <td><bean:message key="lbl.comments"/><font color="red">*</font></td>
		  <td><html:textarea property="comments" styleClass="text" styleId="comments" value=""></html:textarea></td>
		</tr>		  
		
		<tr>
	      <td><button type="button" name="save"  class="topformbutton2" onclick="saveClosureAuthor();" id="save" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button></td>
		</tr>
		</table>
		  
	
	  </fieldset>

  </html:form>
</div>

</div>
</body>
</html>

