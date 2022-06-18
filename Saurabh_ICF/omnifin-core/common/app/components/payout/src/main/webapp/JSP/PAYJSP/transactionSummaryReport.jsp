<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 11 September 2012-->
<!--Purpose  : Transaction Summary Report->
<!--Documentation : -->

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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>

	<title><bean:message key="a3s.noida" />
	</title>

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
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/payoutScript/payReport.js"></script>

	
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

<body
	onload="enableAnchor();document.getElementById('transacttionReport').bpButton.focus();init_fields();">
	<html:form action="/payReportAction" styleId="transacttionReport"
		method="post">
		<html:hidden property="path" styleId="path"
			value="<%=request.getContextPath()%>" />
				<html:hidden property="contextPath" styleId="contextPath"
			value="<%=request.getContextPath()%>" />
	
	<html:hidden  property="parent" styleId="parent" value="N"/>
		<html:errors />

		<fieldset>
			<legend>
				<bean:message key="lbl.transactionSummaryReport" />
			</legend>
			<table width="100%">
            <tr>	<td><bean:message key="lbl.payReportFormat" /></td>
									<td><html:select property="reportType" value="H" styleClass="text">
									<html:optionsCollection name="reportFormatList" value="reportformatid" label="reportformat"/>							
										</html:select> 
									</td>
								</tr>
				<tr>
					<td>
							<bean:message key="lbl.bpName" />
							<span><font color="red"></font>
							</span>
						</td>
						<td>
							<html:text property="bpName" styleClass="text" styleId="bpName" readonly="true" tabindex="-1"	/>
						  <html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(1370,'schemeBpMapAdd','bpName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxBpId" styleId="lbxBpId" value=""/>
						
						</td>
						<td>
								<bean:message key="lbl.payScheme" />
								<span><font color="red"></font>
								</span>
							</td>
							<td>
						  <html:text property="schemeName" styleClass="text" styleId="schemeName"	readonly="true"	 tabindex="-1" />
						   <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1371,'schemeBpMapAdd','schemeName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxSchemeName" styleId="lbxSchemeName"  />
							</td>
				</tr>

				<tr>
					<td>
 <button type="button" class="topformbutton2"   id="show"  onclick="return showTransactionSummaryReport();" title="Alt+S" accesskey="S" ><span class='underLine'>S</span>how</button>
					

	</td>

				</tr>
			</table>
		</fieldset>



	</html:form>
	<logic:present name="sms">
		<script type="text/javascript">

    
	
	
<!--	if('<%=request.getAttribute("sms").toString()%>'=='No')-->
<!--	{-->
<!--		alert("<bean:message key="lbl.noDataFound" />");-->
<!--		-->
<!--	}-->
	
	
	
</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>