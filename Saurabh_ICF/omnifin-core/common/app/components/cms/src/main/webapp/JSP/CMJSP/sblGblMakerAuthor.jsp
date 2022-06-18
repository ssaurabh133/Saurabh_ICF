<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		<logic:present name="css">
				<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
			<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
			
<%
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	request.setAttribute("no",no);
%>	

		
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
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
	<body onload="enableAnchor();checkChanges('dealMakerAuthorForm');">
	
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
 	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<html:form action="/dealCancellationMaker" method="post" styleId="dealMakerAuthorForm">

	<fieldset><legend><bean:message key="lbl.sblGblWaiverMaker"/></legend>         
	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
	   
	<table width="100%"  border="0" cellspacing="1" cellpadding="1">
	 <tr>	   
            <td width="13%"><bean:message key="lbl.loan"></bean:message><font color="red">*</font> </td>
		     <td width="13%">
		     <html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="${list[0].loanNo}" readonly="true" tabindex="-1"/>
<!--             <html:hidden  property="dealId" styleId="dealId" value="${list[0].dealId}" />-->
		  </td>
		  
	     <td width="13%"><bean:message key="lbl.disbursalNo" /></td>
			<td width="13%"><html:text styleClass="text" styleId="disbursalNo" value="${list[0].disbursalNo}" tabindex="-1" maxlength="50" readonly="true" property="disbursalNo" /></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.sblMaster" /></td>
			<td>
				<html:text styleClass="text" styleId="sblMasterLimit" value="${list[0].sblMasterLimit}" readonly="true" maxlength="50" property="sblMasterLimit" tabindex="-1" />
			</td>
			<td><bean:message key="lbl.sblLimit" /></td>
			<td>
				<html:text styleClass="text" styleId="custCurrentPos" value="${list[0].custCurrentPos }" readonly="true" maxlength="50" property="custCurrentPos" tabindex="-1" />
			</td>
		</tr>
		<tr>
			<td><bean:message key="lbl.gblMaster" /></td>
			<td>
				<html:text styleClass="text" styleId="gblMasterLimit" value="${list[0].gblMasterLimit}" readonly="true" maxlength="50" property="gblMasterLimit" tabindex="-1" />
			</td>
			<td><bean:message key="lbl.gblLimit" /></td>
			<td>
				<html:text styleClass="text" styleId="groupPos" value="${list[0].groupPos }" readonly="true" maxlength="50" property="groupPos" tabindex="-1" />
			</td>
		</tr>
		<tr>
			<%-- <td><bean:message key="lbl.gap" /></td>
			<td><html:text styleClass="text" styleId="diff" value="${list[0].diff}" tabindex="-1" readonly="true" maxlength="4" property="diff" /></td> --%>
			<td><bean:message key="lbl.Remarks" /></td>
			<td><html:text styleClass="text" styleId="remarks" value="${list[0].remarks}" tabindex="-1" readonly="true" maxlength="1" property="remarks"/></td>
		</tr>
		
		</table>
		 </td>	
   </tr>
	</table>	
	</fieldset>	
	
</html:form>
<script>
	setFramevalues("dealCancellationMaker");
</script>
</body>
</html>