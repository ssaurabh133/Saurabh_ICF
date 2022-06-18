<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
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
<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/dealClosure.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

<body onload="enableAnchor();init_fields();" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">

	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
	
	<html:form action="/dealCancellationMaker" styleId="cancellationForm" method="post">
	
	<fieldset><LEGEND><bean:message key="lbl.cancellation" /></LEGEND>
	 
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<logic:notPresent name="list">
		<tr>
			<td><bean:message key="lbl.dealNo" /><span><font color="red">*</font></span></td>
			<td>
				<html:text styleClass="text" property="dealNo" styleId="dealNo" value="" readonly="true" />
				<html:button onclick="openLOVCommon(363,'cancellationForm','dealNo','','', '','','generateDealValuesCancellation','customerName');closeLovCallonLov1();" property="loanAcButton" styleId="loanAcButton" value=" " styleClass="lovButton" />
				<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="" />
			</td>
			<td><bean:message key="lbl.customerName" /></td>
			<td><html:text styleClass="text" styleId="customerName"	tabindex="-1" maxlength="50" readonly="true" property="customerName" /></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.fiedDate" /></td>
			<td><html:text styleClass="text" styleId="dealDate" maxlength="10"	readonly="true" property="dealDate" tabindex="-1" /></td>
			<td><bean:message key="lbl.appFormNo" /></td>
			<td><html:text styleClass="text" styleId="appFormNumber" maxlength="18"	readonly="true" property="appFormNumber" tabindex="-1" style="text-align:left;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.product" /></td>
			<td>
				<html:text styleClass="text" styleId="product" readonly="true" maxlength="50" property="product" tabindex="-1" />
				<html:hidden property="lbxProduct" styleId="lbxProduct" />
			</td>
			<td><bean:message key="lbl.scheme" /></td>
			<td>
				<html:text styleClass="text" styleId="scheme" readonly="true" maxlength="50" property="scheme" tabindex="-1" />
				<html:hidden property="lbxScheme" styleId="lbxScheme" />
			</td>
		</tr>
		<tr>
			<td><bean:message key="lbl.assetCost" /></td>
			<td><html:text styleClass="text" styleId="assetCost" tabindex="-1" readonly="true" maxlength="4" property="assetCost" style="text-align:right;"/></td>
			<td><bean:message key="lbl.loanAmount" /></td>
			<td><html:text styleClass="text" styleId="loanAmount" tabindex="-1" readonly="true" maxlength="1" property="loanAmount" style="text-align:right;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.marginPercentage" /></td>
			<td><html:text styleClass="text" styleId="marginPercentage"	tabindex="-1" readonly="true" maxlength="10" property="marginPercentage" style="text-align:right;"/></td>
			<td><bean:message key="lbl.marginAmount" /></td>
			<td><html:text styleClass="text" styleId="marginAmount" tabindex="-1" readonly="true" maxlength="4" property="marginAmount" style="text-align:right;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.cancellationRate" /></td>
			<td><html:text styleClass="text" styleId="rate" maxlength="3" property="rate" value="" tabindex="-1" readonly="true" style="text-align:left;"/></td>
			<td><bean:message key="lbl.tenure" /></td>
			<td><html:text styleClass="text" styleId="tenure" tabindex="-1" maxlength="18" property="tenure" value="" readonly="true" style="text-align:right;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.dealStatus" /></td>
			<td><html:text styleClass="text" styleId="dealStatus" tabindex="-1" maxlength="10" property="dealStatus" value="" readonly="true" /></td>
			<td><bean:message key="lbl.frequency" /></td>
			<td><html:text styleClass="text" styleId="frequency" tabindex="-1" maxlength="18" property="frequency" value="" readonly="true" style="text-align:left;"/></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.reasonForDealClosure" /><span><font color="red">*</font></span></td>
			<td><html:text styleClass="text" styleId="reasonForClosure" maxlength="500" property="reasonForClosure" value="" /></td>
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true" value="${requestScope.authorRemarks}" /></td>
		</tr>
		
		<tr>
			<td colspan="4">
				<button type="button" name="save" id="save" class="topformbutton2" accesskey="V" title="Alt+V" onclick="return saveNewDealClosure();" ><bean:message key="button.save" /></button>
				<button type="button" name="frowardButton" id="frowardButton" class="topformbutton2" accesskey="F" title="Alt+F" onclick="return updateDealBeforeSave();" ><bean:message key="button.forward" /></button>
			</td>
		</tr>
		</logic:notPresent>
		
		<logic:present name="list">
		 <tr>	   
            <td width="13%"><bean:message key="lbl.dealNo"></bean:message><font color="red">*</font> </td>
		     <td width="13%">
		     <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="${list[0].lbxDealNo}" readonly="true" tabindex="-1"/>
             <html:hidden  property="dealId" styleId="dealId" value="${list[0].dealId}" />
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
			<td><html:text styleClass="text" styleId="reasonForClosure" value="${list[0].reasonForClosure}" maxlength="500" property="reasonForClosure" /></td>
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true" value="${requestScope.authorRemarks}" /></td>
		</tr>
		
		<tr>
			<td colspan="4">
				<button type="button" name="save" id="save" class="topformbutton2" accesskey="V" title="Alt+V" onclick="return saveModfyDeal();" ><bean:message key="button.save" /></button>
				<button type="button" name="saveForward" id="saveForward" class="topformbutton2" accesskey="F" title="Alt+F" onclick="return modfyForwardDeal();" ><bean:message key="button.forward" /></button>
			</td>
		</tr>
		  </logic:present>
		
	</table>
	</fieldset>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</html:form>
	<logic:present name="sms">

		<script type="text/javascript">
			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("<bean:message key="lbl.dataSave" /> ");
				//document.getElementById("cancellationForm").action="<%=request.getContextPath()%>/dealCancellationMaker.do?method=openModifyDeal&deaId="+'${requestScope.dealID}';
	    		//document.getElementById("cancellationForm").submit();
			}
			
			else if('<%=request.getAttribute("sms").toString()%>'=="M")
			{
				alert("<bean:message key="lbl.canForward" />");
				document.getElementById("cancellationForm").action="dealClosureMakerBehindAction.do?method=dealCancellationMakerSearch";
	    		document.getElementById("cancellationForm").submit();
			}
				
				else if('<%=request.getAttribute("sms").toString()%>'=="E")
					{
						alert("<bean:message key="msg.notepadError" />");
					}
				else
					{
						alert("<bean:message key="lbl.dataExist" />");
					}
	</script>
	</logic:present>
</body>
</html:html>

