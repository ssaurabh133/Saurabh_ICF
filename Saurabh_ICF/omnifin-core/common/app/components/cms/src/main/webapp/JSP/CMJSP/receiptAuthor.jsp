<!-- 
Author Name :- Manisha Tomar
Date of Creation :24-05-2011
Purpose :-  screen for the Receipt Author
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>    
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
	<body onload="enableAnchor();checkChanges('receiptAuthorForm');" onunload="closeAllWindowCallUnloadBody();">
	<input type="hidden" name="loanRecStatus" value="${requestScope.loanRecStatus}" id="loanRecStatus" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/receiptAuthorProcessAction" method="post" styleId="receiptAuthorForm">
 
   <fieldset>
  
  <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments" /><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="comments" id="comments" maxlength="1000" ></textarea>
			  <html:hidden property="lbxLoanNoHID"  styleId="lbxLoanNoHID" value="${sessionScope.loanID}"/>
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			   <html:hidden property="instrumentID" styleId="instrumentID" value="${sessionScope.instrumentID}" />
			     <html:hidden property="businessPartnerType" styleId="businessPartnerType" value="${sessionScope.businessPartnerType}" />
			  </div></td>
		
		<td width="17%">&nbsp;</td>
		
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td  id=""><span style="float: left;">
		   <html:select property="decision" styleId="decision" onchange="hideAsterik(value)">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
		     </html:select>
		 </span></td>
		</tr> 		  
		<tr>
	        <td> 
           
	       <button type="button" class="topformbutton2" id="save" onclick="return onSaveReceiptAuthor('<bean:message key="msg.CommReqField" />');"  title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	       <button type="button" id="allocatedReceivable" class="topformbutton4" onclick="return onAllocatedReceivable();" title="Alt+R" accesskey="R" ><bean:message key="button.allocdreceivable" /></button>
	       
	       </td>

		
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>

  </html:form>

<logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='NONE')
	{
	   	alert('<%=request.getAttribute("procval")%>');
		
	}
	</script>
</logic:present>
   <logic:present name="msg">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("msg")%>'=='S')
		{
	
			if('<%=request.getAttribute("messval")%>'=='A')
			{
			alert("<bean:message key="msg.approvedSuccessfully" />");
			parent.location="<%=request.getContextPath()%>/receiptAuthorProcessAction.do?method=receiptAuthorSearchDetail";
			}
			if('<%=request.getAttribute("messval")%>'=='X')
			{
			alert("<bean:message key="msg.Datareject" />");
			parent.location="<%=request.getContextPath()%>/receiptAuthorProcessAction.do?method=receiptAuthorSearchDetail";
			}
			if('<%=request.getAttribute("messval")%>'=='P')
			{
			alert("<bean:message key="msg.sendBackSuccessfully" />");
		   	parent.location="<%=request.getContextPath()%>/receiptAuthorProcessAction.do?method=receiptAuthorSearchDetail";
		   }
		}
	
		else if('<%=request.getAttribute("msg")%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			parent.location="<%=request.getContextPath()%>/receiptAuthorProcessAction.do?method=receiptAuthorSearchDetail";
		}
		
		</script>
		</logic:present>
</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("receiptAuthorForm");
</script>
</body>
</html:html>