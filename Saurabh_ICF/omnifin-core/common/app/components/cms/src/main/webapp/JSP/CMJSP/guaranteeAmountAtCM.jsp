
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

        <script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="js/cpScript/applicant.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
			
	</head> 
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();document.getElementById('guaranteeAmountForm').guaranteeAmount.focus();" >
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path%>" />
		
			<div id='centercolumn'>
				<div id=revisedcontainer>
					<html:form action="/updateGuaranteeAmount" styleId="guaranteeAmountForm">
						<html:javascript formName="GuaranteeAmountDynaValidatorForm" />
						<table width="100%"  border="0" cellspacing="0" cellpadding="1">
							
							<tr>
							<td>
							<table cellspacing="1" cellpadding="1" width="100%"	border="0">
							<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							</tr>
													<tr>
													<td width="26%"><bean:message key="lbl.guaranteeAmount" /><font color="red">*</font></td>
													<td width="13%">
														<html:text property="guaranteeAmount" styleId="guaranteeAmount" maxlength="22"
															value="${requestScope.amount}" styleClass="text" style="text-align: right"
															onkeypress="return numbersonly(event, id, 18);" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
															
													</td>
													<td><input type="hidden" id="id" name="id" value="${requestScope.id}" /></td>
													<td></td>
												</tr>
												
												 <tr>
												<td colspan="10" >
												<button type="button" name="Save" class="topformbutton2"  title="Alt+V" accesskey="V" id="Save" onclick="saveGuaranteeAmountAtCM();" ><bean:message key="button.save" /></button>
												</td>
												<td></td>
													<td></td>
													<td></td>
												</tr>
							</table>
							</td>
							</tr></table>
					</html:form>
				</div>
			</div>

		<logic:present name="msg">
			<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.guaranteeAmountSaved" />');
		 top.opener.location.href="showCustomerDetailBehindAction.do";
         javascript:window.close();
		
	}
	else
	{
		alert('<bean:message key="msg.notepadError" />');
		
		top.opener.location.href="showCustomerDetailBehindAction.do";
         javascript:window.close();
	}

	</script>
		</logic:present>

	</body>
</html:html>
