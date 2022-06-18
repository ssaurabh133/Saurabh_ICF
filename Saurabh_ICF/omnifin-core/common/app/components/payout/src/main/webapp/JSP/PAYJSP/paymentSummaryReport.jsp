<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 18 September 2012-->
<!--Purpose  : Payment Summary Report->
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


	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/payoutScript/payReport.js"></script>
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/payoutScript/schemeMaster.js"></script>
<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

		<!-- css for Datepicker -->
<link type="text/css" href="development-bundle/demos/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<!-- jQuery for Datepicker -->

<script type="text/javascript" src="development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="development-bundle/ui/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	
	
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
		 <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<html:hidden  property="parent" styleId="parent" value="N"/>
		<html:errors />

		<fieldset>
			<legend>
				<bean:message key="lbl.paymentSummaryReport" />
			</legend>
			<table width="100%">
            <tr>	<td><bean:message key="lbl.payReportFormat" /></td>
									<td><html:select property="reportType" value="H" styleClass="text"><html:optionsCollection name="reportFormatList" value="reportformatid" label="reportformat"/>							
										</html:select> 
									</td>
								</tr>
				<tr>
					<td>
							<bean:message key="lbl.payFromDate" />
							<span><font color="red"></font>
							</span>
						</td>
						<td>
						  <html:text property="fromDate" styleClass="text" styleId="fromdate" onchange="checkDate('fromDate');"/>
						 
						</td>
						<td>
								<bean:message key="lbl.payToDate" />
								<span><font color="red"></font>
								</span>
							</td>
							<td>
						  <html:text property="toDate" styleClass="text" styleId="todate" 	onchange="checkDate('todate');" />
						  
							</td>
				</tr>
				<tr>
				<td><bean:message key="lbl.paySumParameter" />
							<span><font color="red"></font>
							</span></td>
				<td><html:select styleId="paySumParameter" property="paySumParameter" styleClass="text">
				<html:option value="Pending">Pending</html:option>
				<html:option value="Paid">Paid</html:option>
				</html:select></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				</tr>

				<tr>
					<td>
 <button type="button" class="topformbutton2"   id="show"  onclick="return showPaymentSummaryReport();" title="Alt+S" accesskey="S" ><span class='underLine'>S</span>how</button>
					

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