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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    
    	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	  
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
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('editDealAuthorForm');document.getElementById('editDealAuthorForm').decision.focus();init_fields();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/dealReassignmentAuthor" styleId="dealReassignAuthorForm" method="post">
	
 
  <fieldset>
  <legend><bean:message key="lbl.reassignDealAuthor"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<input type="hidden" name="contextPath" id="contextPath" value='<%=request.getContextPath() %>' />
		<input type="hidden" id="businessDate" value="${requestScope.businessDate}" />
		<input type="hidden" id="makerDate" value="${requestScope.makerDate}" />
		<html:hidden property="lbxDealNo" styleId="lbxDealNo" styleClass="text" value="${sessionScope.dealId}"/>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr>
		 <td nowrap="nowrap"><bean:message key="lbl.decision"/></td>
		 <td nowrap="nowrap">
		 <html:select property="decision" styleId="decision" styleClass="text">
		 	<html:option value="A">Approved</html:option>
		 	<html:option value="X">Rejected</html:option>
		  </html:select>
		 </td>
		</tr>
		
		<tr>
					
		   <td nowrap="nowrap"><bean:message key="lbl.comments"/></td>
			 <td nowrap="nowrap">
			 <html:textarea property="authorRemarks" styleId="authorRemarks" styleClass="text" value=""></html:textarea>
			   
			 </td>
		</tr>
		</table>
		<button type="button" name="save" id="save"  class="topformbutton2" onclick="return saveReassignDealAuthor();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      </td>
	</tr>
	</table>
	
	  </fieldset> 
</html:form>
</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("editDealAuthorForm");
</script>
</body>
</html:html>

<logic:present name="message">
<script type="text/javascript">
if('<%=request.getAttribute("message")%>'=='S')
{
	alert("<bean:message key="msg.DataSaved" />");
	parent.location="<%=request.getContextPath()%>/dealReassignmentSearch.do?method=dealReassignmentMakerSearch";
}
	
else if('<%=request.getAttribute("message")%>'=='E')
{
	alert('<%=request.getAttribute("status")%>');
	parent.location="<%=request.getContextPath()%>/dealReassignmentSearch.do?method=dealReassignmentMakerSearch";
}

</script>

</logic:present>