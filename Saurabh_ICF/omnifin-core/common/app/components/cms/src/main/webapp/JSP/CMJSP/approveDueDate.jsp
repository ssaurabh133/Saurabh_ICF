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
		 
		 <script type="text/javascript" 
			src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" 
		 	src="<%=request.getContextPath() %>/js/cmScript/cmRepaySchedule.js"></script>
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
	
	<body onload="enableAnchor();checkChanges('approveDueDateAuthorForm');">

	

	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/dueDateAuthorProcessAction" method="post" styleId="approveDueDateAuthorForm">
 
  <fieldset>
  <legend><bean:message key="lbl.author" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="businessDate" value="${sessionScope.businessDate}" />
		<input type="hidden" id="makerDate" value="${sessionScope.makerDate}" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<html:hidden property="loanId" styleId="loanId" value="${sessionScope.loanId}"/>
		<html:hidden property="reschId" styleId="reschId" value="${sessionScope.reschId}"/>
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td width="35%">
			   <html:textarea property="comments" styleClass="text" styleId="comments" value="${creditApprovalList[0].remarks }"></html:textarea>
			</td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" styleId="decision" styleClass="text">
		     <html:option value="A">Approved</html:option>
		     <html:option value="X">Rejected</html:option>
		     <html:option value="P">Send Back</html:option>
             
		     </html:select>
		     </td>
		</tr> 		  
		<tr>
	      <td>
	     <!--<html:button property="save" onclick="this.disabled='true';saveComment();"  styleClass="topformbutton2" value="Save" />  --> 
	       <button type="button" id="save" class="topformbutton2" name="save"  onclick="return saveDueDateAuthor();"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      
	      </td>
		</tr>
		</table>
		  	
	  </fieldset>

  </html:form>
</div>

</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("approveDueDateAuthorForm");
</script>
</body>
</html>
<logic:present name="message">
 <script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.approveloan" />");
		parent.location='<%=request.getContextPath()%>/repayscheduleSearchBehind.do';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='X')
	{
		alert("<bean:message key="lbl.rejectloan" />");
		parent.location='<%=request.getContextPath()%>/repayscheduleSearchBehind.do';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='P')
	{
		alert("<bean:message key="lbl.revertloan" />");
		parent.location='<%=request.getContextPath()%>/repayscheduleSearchBehind.do';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location='<%=request.getContextPath()%>/repayscheduleSearchBehind.do';
	}
		else
	{
		alert('${message}');
		parent.location='<%=request.getContextPath()%>/repayscheduleSearchBehind.do';
	}
</script>
</logic:present>
