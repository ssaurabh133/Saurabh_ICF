<!-- 
Author Name :- Manisha Tomar
Date of Creation :26-04-2011
Purpose :-  screen for the Pool Id Author 
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
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/sczScript/poolIDMakerAuthor.js"></script>
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
	<body oncontextmenu="false" onload="enableAnchor();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	<div id="revisedcontainer" >
	
	<html:form action="/poolIdAuthorProcessAction" method="POST" styleId="poolAuthorForm">
       <fieldset>
    <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments"></bean:message><font color="red">*</font></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="comments" id="comments" maxlength="1000"></textarea>
			<!--    <html:text property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${sessionScope.loanID}"/>-->
			   <html:hidden property="poolID" styleId="poolID" value="${poolID}"/>
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			
			  </div></td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td><span style="float: left;">
		   <html:select property="decision" styleId="decision">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
		     </html:select>
		 </span></td>
		</tr> 		  
		<tr>
	        <td> 
	       <button type="button" id="save" class="topformbutton2" onclick="onSaveOfPoolIdAuthor('<bean:message key="msg.CommReqField" />');" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	    
	      
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
  </html:form>
  <logic:present name="msg">
		<script type="text/javascript">
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		   parent.location="<%=request.getContextPath()%>/poolIdBehindAuthor.do";
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='E')
		{
			alert('${massage}');
			parent.location="<%=request.getContextPath()%>/poolIdBehindAuthor.do";
		}
		
		</script>
		</logic:present>
</div>



</div>
</body>
</html:html>