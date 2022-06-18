<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Fund flow Author
 	Documentation    :- 
 -->
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		

	</head>
	<body onload="enableAnchor();document.getElementById('fundFlowAuthorForm').comments.focus();init_fields();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/fundFlowAuthor" method="POST" styleId="fundFlowAuthorForm">
 
   <fieldset>
  
  <legend><bean:message key="lbl.author"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.comments"></bean:message><font color="red">*</font></td>
			 <td width="35%"><div style="float:left;">
			  <textarea name="comments" id="comments" ></textarea>
			  <html:hidden property="dealId" value="${sessionScope.fundFlowDealId}"/>
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			  </div></td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td  id=""><span style="float: left;">
		   <html:select property="decision" styleId="decision" styleClass="text" value="">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
		     </html:select>
		 </span></td>
		</tr> 		  
		<tr>
	        <td> 
	      <button type="button" name="button" class="topformbutton2" onclick="return fundFlowAuthor();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button> </td>
	
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
</body>
<logic:present name="sms">
<script type="text/javascript">
	if("<%=request.getAttribute("sms").toString()%>"=="A" && '<%=request.getAttribute("deci").toString()%>'!='P' && '<%=request.getAttribute("deci").toString()%>'!='X')
	{
		alert("<bean:message key="msg.approvedSuccessfully" />");
		parent.location="<%=request.getContextPath()%>/fundFlowAnalysisSearchBehind.do";
	}
	else if("<%=request.getAttribute("sms").toString()%>"=="A" && "<%=request.getAttribute("deci").toString()%>"=="P")
	{
	  alert("<bean:message key="msg.sendBackSuccessfully" />");
	   parent.location="<%=request.getContextPath()%>/fundFlowAnalysisSearchBehind.do";
	  
	}
	else if("<%=request.getAttribute("sms").toString()%>"=="A" && "<%=request.getAttribute("deci").toString()%>"=="X")
	{
	    alert("<bean:message key="msg.Datareject" />");
	   parent.location="<%=request.getContextPath()%>/fundFlowAnalysisSearchBehind.do";
	  
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('<%=request.getAttribute("msg.approvedUnsuccessfully")%>');
		
	    parent.location="<%=request.getContextPath()%>/fundFlowAnalysisSearchBehind.do";
	  
	}
</script>
</logic:present>
</html:html>


