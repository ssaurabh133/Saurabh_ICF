<!-- 
Author Name :- Manisha Tomar
Date of Creation :26-04-2011
Purpose :-  screen for the Payment Maker
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/manualAdviceMaker.js"></script>
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
	<body onload="enableAnchor();checkChanges('manualAdviceSerchForm');document.getElementById('manualAdviceSerchForm').comments.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/manualAdviceSearchProcessAction" method="POST" styleId="manualAdviceSerchForm">
 
   <fieldset>
  
  <input type="hidden" name="loanRecStatus" value="${manualAdviceList[0].loanRecStatus}" id="loanRecStatus" />
  <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments"></bean:message><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="comments" id="comments" ></textarea>
			  <html:hidden property="lbxLoanNoHID" value="${sessionScope.loanID}"/>
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			  </div></td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td  id=""><span style="float: left;">
		   <html:select property="decision" styleId="decision" styleClass="text" value="" onchange="hideAsterik(value)">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
		     </html:select>
		 </span></td>
		</tr> 		  
		<tr>
	        <td> 
	      <button type="button" name="button" title="Alt+V" accesskey="V" id="Save" class="topformbutton2" onclick="return manualAdviceAuthor();"><bean:message key="button.save" /></button> </td>
	
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
	<br/> 
  </html:form>
</div>

</div>
<script>
	setFramevalues("manualAdviceSerchForm");
</script>
</body>
<logic:present name="sms">
<script type="text/javascript">
	if("<%=request.getAttribute("sms").toString()%>"=="A" && '<%=request.getAttribute("deci").toString()%>'!='P' && '<%=request.getAttribute("deci").toString()%>'!='X')
	{
		alert("<bean:message key="msg.approvedSuccessfully" />");
		parent.location="<%=request.getContextPath()%>/manualAdviceSearchBehind.do";
	}
	else if("<%=request.getAttribute("sms").toString()%>"=="A" && "<%=request.getAttribute("deci").toString()%>"=="P")
	{
	  alert("<bean:message key="msg.sendBackSuccessfully" />");
	   parent.location="<%=request.getContextPath()%>/manualAdviceSearchBehind.do";
	  
	}
	else if("<%=request.getAttribute("sms").toString()%>"=="A" && "<%=request.getAttribute("deci").toString()%>"=="X")
	{
	    alert("<bean:message key="msg.Datareject" />");
	   parent.location="<%=request.getContextPath()%>/manualAdviceSearchBehind.do";
	  
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('<%=request.getAttribute("errorMsg")%>');
		
	   parent.location="<%=request.getContextPath()%>/manualAdviceSearchBehind.do";
	  
	}
</script>
</logic:present>
</html:html>


