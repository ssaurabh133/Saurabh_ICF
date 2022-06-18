<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	
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
	<body onload="enableAnchor();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/authorPOD" method="post" styleId="PODAuthorForm">
	 
 
  <fieldset>
  <legend><bean:message key="lbl.author" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td width="35%">
			   <html:textarea property="comments" styleClass="text" styleId="comments" value=""></html:textarea>
			</td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" onchange="" styleId="decision" styleClass="text">
		     <html:option value="A">Approved</html:option>
             <html:option value="P">Send Back</html:option>
             <html:option value="X">Rejected</html:option>
             
		     </html:select>
		     </td>
		</tr> 		  
		<tr>
	      <td><button type="button" name="save" id="save" class="topformbutton2" accesskey="V" title="Alt+V" onclick="return savePODdocAuthor();"><bean:message key="button.save" /></button></td>
		</tr>
		</table>
		  
	
	  </fieldset>

  </html:form>
</div>

</div>
</body>
</html>
<logic:present name="message">
<script type="text/javascript">

if('<%=request.getAttribute("message").toString()%>'=='S')
{
	alert("<bean:message key="msg.DataSaved" />");
	parent.location="<%=request.getContextPath()%>/postDisbursalDocSearchBehind.do?method=postDisbursalDocAuthorSearch";
}
	
else if('<%=request.getAttribute("message").toString()%>'=='E')
{
	alert("DOCUMENT_COLLECTION_AUTHOR---UNKNOWN EXCEPTION IN SQL EXECUTION, CONTACT SYSTEM ADMINISTRATOR...");
	parent.location="<%=request.getContextPath()%>/postDisbursalDocSearchBehind.do?method=postDisbursalDocAuthorSearch";
}

</script>

</logic:present>
