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
		
		 <script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanClosure.js"></script>
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
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('closureAuthorForm');document.getElementById('closureAuthorForm').decision.focus();init_fields();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/author" method="post" styleId="closureAuthorForm">
	 
 
  <fieldset>
  <legend><bean:message key="lbl.author" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="businessDate" value="${requestScope.businessDate}" />
		<input type="hidden" id="makerDate" value="${requestScope.makerDate}" />
		<input type="hidden" id="checkFlag" value="${requestScope.checkFlag}" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<html:hidden property="loanId" styleId="loanId" value="${sessionScope.loanId}"/>
		<html:hidden property="closureStatus" styleId="closureStatus" value="${sessionScope.closureStatus}"/>
		
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" onchange="" styleId="decision" styleClass="text" onchange="hideAsterik(value)" >
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
             
		     </html:select>
		     </td>
		</tr> 
		<tr>
					
		   <td><bean:message key="lbl.comments"/><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td>
			   <html:textarea property="comments" styleClass="text" styleId="comments" value=""></html:textarea>
			</td>
		</tr>		  
		<tr>
	      <td><button type="button" name="save"  class="topformbutton2" 
	      		value="Save" onclick="return saveClosureAuthor();"
	      		id="save" accesskey="V" title="Alt+V" ><bean:message key="button.save" /></button></td>
		</tr>
		</table>
		  
	
	  </fieldset>

  </html:form>
</div>

</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("closureAuthorForm");
</script>
</body>
</html>
<logic:present name="message">
 <script type="text/javascript">
 if(document.getElementById("closureStatus").value=='T')
 {
	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosureAuthor";
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<%=request.getAttribute("status")%>');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosureAuthor";
	}
}

if(document.getElementById("closureStatus").value=='C')
 {
	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosureAuthor";
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<%=request.getAttribute("status")%>');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosureAuthor";
	}
}

if(document.getElementById("closureStatus").value=='X')
 {
	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=cancellationClosureAuthor";
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<%=request.getAttribute("status")%>');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=cancellationClosureAuthor";
	}
}
</script>
</logic:present>
