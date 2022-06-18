<!--Author Name : nazia parvez -->
<!--Date of Creation : 1-april-2013-->
<!--Purpose  : Information of Repo Confirmation-->
<!--Documentation : -->



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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoConfirmation.js"></script>
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
	<body onload="enableAnchor();">
	

	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/RepoConfirmationDispatch" method="post" styleId="repoConfirmationForm">
 
   <fieldset>
  
  <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>

	  <td width="20%"><bean:message key="lbl.comments" /><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="comments" id="comments" maxlength="1000" ></textarea>
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath"/>
			 
			  </div></td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td  id=""><span style="float: left;">
		   <html:select property="decision" styleId="decision" onchange="hideAsterik(document.getElementById('decision').value)">
		     <html:option value="Y">Approved</html:option>
             <html:option value="N">Send Back</html:option>
              <html:option value="X">Rejected</html:option>
		     </html:select>
		 </span></td>
		</tr> 		 	  
		
	<tr>
	        <td> 
           
	       <button type="button" class="topformbutton2" id="save" onclick="return saveRepoConfirmation();"  title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
  
	       </td>
</tr> 
		</table>
		
	</td></tr></table>
	  </fieldset>

  </html:form>


   <logic:present name="sms">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("sms").toString()%>'=='Y')
		{
	
			
			alert("<bean:message key="msg.repoConfirmedSucc" />");
			parent.location="<%=request.getContextPath()%>/RepoConfirmationDispatch.do?method=searchRepoConfirmation";
			
			
		}
		else if('<%=request.getAttribute("sms").toString()%>'=='N')
		{
	
			alert("<bean:message key="msg.repoConfirmationSendBackSucc" />");
			parent.location="<%=request.getContextPath()%>/RepoConfirmationDispatch.do?method=searchRepoConfirmation";
	
			
		}
		
		else if('<%=request.getAttribute("sms").toString()%>'=='X')
		{
	
			alert("<bean:message key="msg.repoConfirmationRejectedSucc" />");
			parent.location="<%=request.getContextPath()%>/RepoConfirmationDispatch.do?method=searchRepoConfirmation";
	
			
		}
		
		else if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
		}
		
		</script>
		</logic:present>
</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("repoConfirmationForm");
</script>
</body>
</html:html>