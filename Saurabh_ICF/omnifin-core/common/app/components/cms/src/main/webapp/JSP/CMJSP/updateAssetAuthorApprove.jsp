<!--Author Name :Manish Shukla-->
<!--Date of Creation : June 2013-->
<!--Purpose  : Author of Update Asset Author-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.connect.CommonFunction"%>
<%@page import="com.legal.vo.LegalCourtProcessingVo"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/updateAsset.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript">

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

<body onload="enableAnchor();" >
<html:form styleId="updateAssetAuthorFrame"  method="post"  action="/updateAssetAuthorFrame" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
  <legend><bean:message key="lbl.updateAssetAuthorScreen" /></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<input type="hidden" name="contextPath" id="contextPath" value='<%=request.getContextPath() %>' />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr>
		<td><bean:message key="lbl.decision" /><span><font color="red">*</font></span></td>
	 
	 <td>
		<html:select property="authorDecission" styleId="authorDecission" styleClass="text"  value="${rateApprovalList[0].decision}" >
			<html:option value="A">Approved</html:option>
			<html:option value="P">Send Back</html:option>
		 	<html:option value="X">Rejected</html:option>
		</html:select> 
		</td> 
	 
	 
		</tr>
		<tr>
			 <td ><bean:message key="lbl.comments"/><span ><font color="red">*</font></span></td>
			 <td ><html:textarea property="comments" styleId="comments" styleClass="text" value=""></html:textarea>
			 </td>
		</tr>
		</table>
		<button type="button" name="save" id="save"  class="topformbutton2" onclick="return saveUpdateAssetChecker();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
	      </td>
	</tr>
	</table>
	
	  </fieldset> 
	
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<%=request.getAttribute("decision").toString()%>');
	    parent.location="<%=request.getContextPath()%>/updateAssetAuthor.do";
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>