<!-- 
Author Name :- Kanika Maheshwari
Date of Creation :16-12-2011
Purpose :-  screen for the view payable and reievable
-->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript" 
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" 
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/capsScript/contactRecordingScript.js"></script>
<%
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
	%>
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
<body>
<html:form action="/collButtonAction" styleId="PayablerecievableForm"
		method="post">
			<fieldset>
				<legend>
					<bean:message key="lbl.viewPRContactRecording" />
				</legend>
				<input type="hidden" name="contextPath"
					value="<%=request.getContextPath()%>" id="contextPath" />
					
									<logic:present name="list">
					<fieldset>			
					<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/collButtonAction.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="viewPRDate" titleKey="lbl.date" sortable="true" />
						<display:column property="charge" titleKey="lbl.chargeDescription" sortable="true" />
						<display:column property="balamt" titleKey="lbl.balanceAmount" sortable="true" />
					<display:column property="advicetype" titleKey="lbl.adviceType" sortable="true" />

					</display:table>
				</fieldset>
				</logic:present>
				
							<logic:notPresent name="list">
							<fieldset>		
							<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tbody>
						<tr>
							<td class="gridtd">
								<table width="100%" cellspacing="1" cellpadding="1" border="0">
									<tbody>
								<tr class="white2">

									<td>
										<strong><bean:message key="lbl.date" />
										</strong>
									</td>
									<td>
										<strong><bean:message key="lbl.chargeDescription" />
										</strong>
									</td>
									
									<td>
										<b><bean:message key="lbl.balanceAmount" /> </b>
									</td>
									<td>
										<strong><bean:message key="lbl.adviceType" />
										</strong>
									</td>
								</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table></fieldset>
								</logic:notPresent>
							
			<logic:present name="list">
				<td>
					<!-- <input type="button" value="Close" id="close"
						class="topformbutton2" title="Alt+C" accesskey="C"
						onclick="window.close();" /> -->
						<button type="button" class="topformbutton2"  id="close" onclick="window.close();"
 title="Alt+C" accesskey="C"  ><bean:message key="button.close" /></button>
				</td>
			</logic:present>








		</fieldset>


	</html:form>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</body>
	</html:html>
	