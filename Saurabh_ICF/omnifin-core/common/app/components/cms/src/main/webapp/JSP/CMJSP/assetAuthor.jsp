<!-- 
Author Name :- MRADUL AGARWAL
Date of Creation :03-08-2013
Purpose :-  screen for the Asset Maker
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
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/asset.js"></script>
	<script type="text/javascript" 
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	
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
<body onload="enableAnchor();checkChanges('assetAuthorForm');document.getElementById('assetAuthorForm').comments.focus();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">

		<div id="revisedcontainer">

			<html:form action="/assetAuthorProcessAction" method="POST"
				styleId="assetAuthorForm">




				<fieldset>


					<legend>
						<bean:message key="lbl.assetAuthor"></bean:message>
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="4">

									<tr>

										<td width="20%">
											<bean:message key="lbl.comments"></bean:message>
											<font color="red">*</font>
										</td>
										<td width="35%">
											<div style="float: left;">
												<html:textarea property="comments" styleClass="text"
													styleId="comments"></html:textarea>
												<html:hidden property="assetID"
													value="${sessionScope.assetID}" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />


											</div>
										</td>

										<td width="17%">
											&nbsp;
										</td>
										<td width="28%">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.decision"></bean:message>
											<font color="red">*</font>
										</td>
										<td>
											<span style="float: left;"> <html:select
													property="decision" styleId="decision" styleClass="text">

													<html:option value="A">Approved</html:option>
													<html:option value="X">Rejected</html:option>
													<html:option value="P">Send Back</html:option>

												</html:select> </span>
										</td>
									</tr>
									<tr>
										<td>
											<!--  <input type="button" value="Save" id="save" class="topformbutton2" onclick="this.disabled=true;return onSaveOfAuthor('<bean:message key="msg.plsSelReqField" />');" title="Alt+V" accesskey="V"/>-->
										
										
										<button type="button" class="topformbutton2"  id="save" onclick="return onSaveOfAuthor('<bean:message key="msg.CommReqField" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
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
			if('<%=request.getAttribute("procval")%>'!='S')
			{
	   			alert('<%=request.getAttribute("procval").toString()%>');
			}
		</script>
			</logic:present>

			<logic:present name="msg">
				<script type="text/javascript">
		
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
			parent.location="<%=request.getContextPath()%>/assetAuthorProcessAction.do?method=authorSearchDetail";
		
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			parent.location="<%=request.getContextPath()%>/assetAuthorProcessAction.do?method=authorSearchDetail";
		
		}
		
		
		</script>
			</logic:present>

		</div>



	</div>
	<script>
	setFramevalues("assetAuthorForm");
</script>
</body>
</html:html>