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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/dealClosure.js"></script>
			
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

	<fieldset><legend><bean:message key="lbl.dealCanMake"/></legend>         
	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
	   
	<table width="100%"  border="0" cellspacing="1" cellpadding="1">
	 <tr>	   
            <td width="13%"><bean:message key="lbl.dealNo"></bean:message><font color="red">*</font> </td>
		     <td width="13%">
		     <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="${list[0].lbxDealNo}" readonly="true" tabindex="-1"/>
<!--             <html:hidden  property="dealId" styleId="dealId" value="${list[0].dealId}" />-->
		  </td>
		  
	     <td width="13%"><bean:message key="lbl.customerName" /></td>
			<td width="13%"><html:text styleClass="text" styleId="customerName" value="${list[0].customerName}" tabindex="-1" maxlength="50" readonly="true" property="customerName" /></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.fiedDate" /></td>
			<td><html:text styleClass="text" styleId="dealDate" value="${list[0].dealDate}" maxlength="10"	readonly="true" property="dealDate" tabindex="-1" /></td>
			<td><bean:message key="lbl.appFormNo" /></td>
			<td><html:text styleClass="text" styleId="appFormNumber" value="${list[0].appFormNumber}" maxlength="18"	readonly="true" property="appFormNumber" tabindex="-1" style="text-align:left;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.product" /></td>
			<td>
				<html:text styleClass="text" styleId="product" value="${list[0].lbxProduct}" readonly="true" maxlength="50" property="product" tabindex="-1" />
				<html:hidden property="lbxProduct" styleId="lbxProduct" />
			</td>
			<td><bean:message key="lbl.scheme" /></td>
			<td>
				<html:text styleClass="text" styleId="scheme" value="${list[0].lbxScheme }" readonly="true" maxlength="50" property="scheme" tabindex="-1" />
				<html:hidden property="lbxScheme" styleId="lbxScheme" />
			</td>
		</tr>
		<tr>
			<td><bean:message key="lbl.assetCost" /></td>
			<td><html:text styleClass="text" styleId="assetCost" value="${list[0].assetCost}" tabindex="-1" readonly="true" maxlength="4" property="assetCost" style="text-align:right;"/></td>
			<td><bean:message key="lbl.loanAmount" /></td>
			<td><html:text styleClass="text" styleId="loanAmount" value="${list[0].loanAmount}" tabindex="-1" readonly="true" maxlength="1" property="loanAmount" style="text-align:right;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.marginPercentage" /></td>
			<td><html:text styleClass="text" styleId="marginPercentage" value="${list[0].marginPercentage}" tabindex="-1" readonly="true" maxlength="10" property="marginPercentage" style="text-align:right;"/></td>
			<td><bean:message key="lbl.marginAmount" /></td>
			<td><html:text styleClass="text" styleId="marginAmount" value="${list[0].marginAmount}" tabindex="-1" readonly="true" maxlength="4" property="marginAmount" style="text-align:right;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.cancellationRate" /></td>
			<td><html:text styleClass="text" styleId="rate" value="${list[0].rate}" maxlength="3" property="rate"  tabindex="-1" readonly="true" style="text-align:left;"/></td>
			<td><bean:message key="lbl.tenure" /></td>
			<td><html:text styleClass="text" styleId="tenure" value="${list[0].tenure}" tabindex="-1" maxlength="18" property="tenure"  readonly="true" style="text-align:right;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.dealStatus" /></td>
			<td><html:text styleClass="text" styleId="dealStatus" value="${list[0].dealStatus }" tabindex="-1" maxlength="10" property="dealStatus"  readonly="true" /></td>
			<td><bean:message key="lbl.frequency" /></td>
			<td><html:text styleClass="text" styleId="frequency" value="${list[0].frequency}" tabindex="-1" maxlength="18" property="frequency"  readonly="true" style="text-align:left;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.reasonForDealClosure" /><span><font color="red">*</font></span></td>
			<td><html:text styleClass="text" styleId="reasonForClosure" value="${list[0].reasonForClosure}" readonly="true" maxlength="1000" property="reasonForClosure" /></td>
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true" value="${requestScope.authorRemarks}" /></td>
		</tr>
		</table>
		 </td>	
   </tr>
	</table>	
	</fieldset>	
	
</html:form>
<script>
	setFramevalues("dealMakerAuthorForm");
</script>
</body>
</html>