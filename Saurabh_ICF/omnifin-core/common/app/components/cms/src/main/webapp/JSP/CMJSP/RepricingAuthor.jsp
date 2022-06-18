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
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" 
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	  
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
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('rePricingAuthorForm');document.getElementById('rePricingAuthorForm').decision.focus();init_fields();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/rePricingAuthor" styleId="rePricingAuthorForm" method="post">
	
 
  <fieldset>
  <legend><bean:message key="lbl.rePricingAuthor"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<input type="hidden" name="contextPath" id="contextPath" value='<%=request.getContextPath() %>' />
		<input type="hidden" id="businessDate" value="${requestScope.businessDate}" />
		<input type="hidden" id="makerDate" value="${requestScope.makerDate}" />
		<html:hidden property="loanId" styleId="loanId" styleClass="text" value="${sessionScope.loanId}"/>
		<html:hidden property="reschId" styleId="reschId" styleClass="text" value="${sessionScope.reschId}"/>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr>
		 <td nowrap="nowrap"><bean:message key="lbl.decision"/></td>
		 <td nowrap="nowrap">
		 <html:select property="decision" styleId="decision" styleClass="text">
		 	<html:option value="A">Approved</html:option>
		 	<html:option value="X">Rejected</html:option>
		 	<html:option value="P">Send Back</html:option>
		 </html:select>
		 </td>
		</tr>
		
		<tr>
					
		   <td nowrap="nowrap"><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td nowrap="nowrap">
			 <html:textarea property="comments" styleId="comments" styleClass="text" value=""></html:textarea>
			   
			 </td>
		</tr>
		</table>
		<button type="button" name="save" id="save"  class="topformbutton2" onclick="return saveRepricingAuthor();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      </td>
	</tr>
	</table>
	
	  </fieldset> 
</html:form>
</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("rePricingAuthorForm");
</script>
</body>
</html:html>

<logic:present name="message">
<script type="text/javascript">
if('<%=request.getAttribute("message")%>'=='S')
{
	alert("<bean:message key="msg.DataSaved" />");
	parent.location="<%=request.getContextPath()%>/rePricingSearchBehind.do?method=rePricingAuthorSearch";
}
	
else if('<%=request.getAttribute("message")%>'=='E')
{
	alert('<%=request.getAttribute("status")%>');
	parent.location="<%=request.getContextPath()%>/rePricingSearchBehind.do?method=rePricingAuthorSearch";
}

</script>

</logic:present>