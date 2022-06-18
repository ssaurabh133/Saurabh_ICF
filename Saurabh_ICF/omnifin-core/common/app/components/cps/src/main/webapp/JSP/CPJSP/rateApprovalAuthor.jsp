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
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/rateApprovalDeal.js"></script>
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
	<body onload="enableAnchor();document.getElementById('rateApprovalAuthor').decision.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/rateApprovalChecker" styleId="rateApprovalAuthor" method="post" > 
 
  <fieldset>
  <legend><bean:message key="lbl.knockOffAuthor" /></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<html:hidden property="loanId" styleId="loanId" value="${sessionScope.loanId }"/>
		<input type="hidden" name="contextPath" id="contextPath" value='<%=request.getContextPath() %>' />
		<input type="hidden" name="rackRate" id="rackRate" value="${resultList[0].rackRate}" />
		<input type="hidden" name="flatRate" id="flatRate" value="${resultList[0].flatRate}" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr>
		 <td nowrap="nowrap"><bean:message key="lbl.decision"/></td>
		 <td nowrap="nowrap">
		 <html:select property="decision" styleId="decision" styleClass="text" value="" onchange="hideAsterik(value)">
		 	<html:option value="A">Approved</html:option>
		 	<html:option value="P">Send Back</html:option>
		 </html:select>
		 </td>
		</tr>
		
		<tr>
					
		   <td nowrap="nowrap"><bean:message key="lbl.comments"/><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td nowrap="nowrap">
			 <html:textarea property="comments" styleId="comments" styleClass="text" value=""></html:textarea>
			   
			 </td>
		</tr>
		</table>
		<button type="button" name="save" id="save"  class="topformbutton2" onclick="return saveRateApprovalAuthor();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
	      </td>
	</tr>
	</table>
	
	  </fieldset> 
</html:form>
</div>
</div>
<script>
	setFramevalues("rateApprovalAuthor");
</script>
</body>
</html:html>

<logic:present name="message">
<script type="text/javascript">

if('<%=request.getAttribute("message")%>'=='S')
{
		alert("<bean:message key="lbl.dataSave" />");
		parent.location="<%=request.getContextPath()%>/rateApprovalChecker.do?method=defaultRateChecker&source=D";

}
	
else if('<%=request.getAttribute("message")%>'=='E')
{
	alert("<bean:message key="msg.notepadError" />");	
}

</script>

</logic:present>