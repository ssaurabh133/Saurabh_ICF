<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/waiveOff.js"></script>
   
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
   
		
	
	<script type="text/javascript">
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
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
	<body onload="enableAnchor();checkChanges('WaiveOffAuthorPage');document.getElementById('WaiveOffAuthorPage').remarks.focus();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/waiveOffAuthorDispatchAction" styleId="WaiveOffAuthorPage">
 
  <fieldset>
  <legend><bean:message key="lbl.WaiveoffAuthor"/></legend>
	<input type="hidden" name="loanRecStatus" value="${AuthorData[0].loanRecStatus}" id="loanRecStatus" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					<html:hidden property="waveOffId" styleId="waveOffId" value="${sessionScope.waveOffId}"/>
					<html:hidden property="newBalanceAmt" styleId="newBalanceAmt" value="${AuthorData[0].newBalanceAmount}"/>
		   <td width="20%"><bean:message key="lbl.remarks"/><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td width="35%">
			   <textarea name="remarks" id="remarks" maxlength="1000"></textarea>
			     <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			</td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" styleId="decision" styleClass="text" onchange="hideAsterik(value)">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
		     </html:select>
		     </td>
		</tr> 		  
		<tr>
	      <td align="left" class="form2">

	      <button type="button" name="Save"  onclick="return saveWaiveOffCSEAuthor();" id="Save" class="topformbutton2" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

	      </td>
		</tr>
		</table>
	
	  </fieldset>

  </html:form>
  <logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.saveWaiveOff" />');
		parent.location="<%=request.getContextPath()%>/WaiveOffAuthorBehindAction.do";
		
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert('<%=request.getAttribute("errorMsg")%> ');
		parent.location="<%=request.getContextPath()%>/WaiveOffAuthorBehindAction.do";
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='R')
	{
		alert('<bean:message key="msg.rejectWaveOff" />');
		parent.location="<%=request.getContextPath()%>/WaiveOffAuthorBehindAction.do";
	}

</script>
</logic:present>
</div>

</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("WaiveOffAuthorPage");
</script>
</body>
</html>