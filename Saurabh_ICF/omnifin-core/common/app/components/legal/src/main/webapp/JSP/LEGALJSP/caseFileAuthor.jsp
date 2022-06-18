<!-- 
Author Name :- Vinod Kumar Gupta
Date of Creation :18-04-2013
Purpose :-  screen for the Case File Author
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/legalScript/legalCaseFileMaker.js"></script>
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
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
	<body>
	<div id="centercolumn">
	
	<div id="revisedcontainer" >
	
	<html:form action="/paymentAuthorProcessAction" method="POST" styleId="legalCaseFileAuthorForm">
    
   <fieldset>
  
  <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments"></bean:message><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="comments" id="comments" maxlength="1000"></textarea>
			 
			 
			  
			  </div></td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td><span style="float: left;">
		   <html:select property="decision" styleId="decision" onchange="hideAsterik(document.getElementById('decision').value);">
		     <html:option value="CFA">Approved</html:option>
             <html:option value="CFM">Send Back</html:option>
             <html:option value="X">Reject</html:option>
		     </html:select>
		 </span></td>
		</tr> 		  
		<tr>
	        <td> 
	       <button type="button" id="save" class="topformbutton2" onclick="return saveAuthorData('<bean:message key="msg.plsSelReqField" />');" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	       </td> 
	      
		</tr> 
		</table>
		
	      </td>
	</tr>
	
	</table>
	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	  </fieldset>
  </html:form>
  <logic:present name="sms">
<script type="text/javascript">

   
    
    if('<%=request.getAttribute("sms").toString()%>'=='SBA')
	{
		alert("<%=request.getAttribute("decision").toString()%>");
		parent.location="<%=request.getContextPath()%>/legalCaseFileAuthorDispatch.do?method=searchLegalCaseFileAuthor";
		//document.getElementById("legalCaseFileAuthorForm").action="legalCaseFileAuthorDispatch.do?method=searchLegalCaseFileAuthor";
	    //document.getElementById("legalCaseFileAuthorForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	


	
	
</script>
</logic:present>

</div>

</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("legalCaseFileAuthorForm");
</script>
</body>
</html:html>