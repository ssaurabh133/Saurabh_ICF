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
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/disbursalCancellation.js"></script>
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
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('cancellationAuthorForm');document.getElementById('cancellationAuthorForm').decision.focus();init_fields();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/author" method="post" styleId="cancellationAuthorForm">
	 
 
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
		     <html:option value="X">Approved</html:option>
             <html:option value="R">Rejected</html:option>
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
	      		value="Save" onclick="return saveCancelationAuthor();"
	      		id="save" accesskey="V" title="Alt+V" ><bean:message key="button.save" /></button></td>
		</tr>
		<logic:notEmpty name="arrList2">
		<logic:iterate id="arrListobj" name="arrList2" indexId="count">											
			<tr>									
			<td style="display:none" >															
			<input type="text" name="disbursalID" id="disbursalID" value="${arrListobj.disbursalIDNew}" />
			</td>													
			</tr>												
		</logic:iterate>
		</logic:notEmpty>
		</table>
		  
	
	  </fieldset>

  </html:form>
</div>

</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("cancellationAuthorForm");
</script>
</body>
</html>
  	<logic:present name="status">
			<script type="text/javascript">
			if('<%=request.getAttribute("status")%>'!='NONE')
			{
	   			alert('<%=request.getAttribute("status").toString()%>');
			}
		</script>
	</logic:present>
<logic:present name="message">
 <script type="text/javascript">
 

	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		parent.location = "<%=request.getContextPath() %>/disbursalCancellation.do?method=disbCancellationAuthorSearch";	
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location = "<%=request.getContextPath() %>/disbursalCancellation.do?method=disbCancellationAuthorSearch";	
	}
	else if("<%=request.getAttribute("message")%>"=="N")
	{
		alert("<bean:message key="msg.adviceValidation" />");		
	}
	else if("<%=request.getAttribute("message")%>"=="B")
	{
		alert("<bean:message key="msg.billFlag" />");		
	}
	else if("<%=request.getAttribute("message")%>"=="P")
	{
		alert("<bean:message key="msg.recType" />");		
	}
	else if("<%=request.getAttribute("message")%>"=="TA")
	{
		alert("Disbursal has been already Adjusted for the TA Loan No:"+"<%=request.getAttribute("taLoanNO")%>");
	}
</script>
</logic:present>
