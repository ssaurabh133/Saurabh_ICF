<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 08 Agust 2012-->
<!--Purpose  : Payment Receipt Search->
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
		src="<%=request.getContextPath()%>/js/payoutScript/paymentReceipt.js"></script>

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

<body
	onload="enableAnchor();document.getElementById('paymentReceiptSearch').searchBpName.focus();init_fields();">
	<html:form action="/paymentReceiptBehind" styleId="paymentReceiptSearch"
		method="post">
		<html:hidden property="path" styleId="path"
			value="<%=request.getContextPath()%>" />
		<html:errors />

		<fieldset>
			<legend>
				<bean:message key="lbl.payPaymentReceiptWaive" />
			</legend>
			<table width="100%">

				<tr>
					<td width="13%">
						<bean:message key="lbl.bpName" />
					</td>
					<td width="13%">
						<html:text property="searchBpName" styleClass="text"
							styleId="searchBpName" maxlength="50" />
					</td>
					<td width="13%">
						<bean:message key="lbl.payPaymentType" />
					</td>
					<td width="13%">
						<html:select property="searchPaymentType" styleClass="text" styleId="searchPaymentType" >
						<html:option value="">Select</html:option>
						<html:option value="P">Payment</html:option>
						<html:option value="R">Receipt </html:option>
						<html:option value="W">Waive Off</html:option>
						
						</html:select>
					</td>
					
				</tr>

				<tr>
					<td>
 <button type="button" class="topformbutton2"   id="search"  onclick="return fnSearchCode('<bean:message key="lbl.selectAtLeast" />');" title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
					
<button type="button" class="topformbutton2" name="add"  id="add"  onclick="return addPaymentReceipt();" title="Alt+A" accesskey="A" ><bean:message key="button.add" /></button>
	</td>

				</tr>
			</table>
		</fieldset>

		<br />
		<fieldset>
			<legend>
				<bean:message key="lbl.payPaymentReceiptWaiveDtl" />
			</legend>

			<logic:present name="list">
				<display:table id="list" name="list" style="width: 100%"
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${list[0].totalRecordSize}"
					requestURI="/paymentReceiptBehind.do">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:column property="paymentId" titleKey="lbl.bpName"
						sortable="true" />
					<display:column property="paymentType"
						titleKey="lbl.payPaymentType" sortable="true" />
					<display:column property="paymentMode"
						titleKey="lbl.payPaymentMode" sortable="true" />
						<display:column property="paymentDate"
						titleKey="lbl.payPaymentDate" sortable="true" />
						<display:column property="paymentAmount"
						titleKey="lbl.payPaymentAmount" sortable="true" />
					<display:column property="makerRemark" style="text-align: center" titleKey="lbl.payRemark"
						sortable="true" />

				</display:table>
			</logic:present>

			<logic:notPresent name="list">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.bpName" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payPaymentType" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payPaymentMode" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payPaymentDate" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payPaymentAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payRemark" /> <br> </b>
									</td>
									<tr class="white2" >
	                                  <td colspan="7">&nbsp; <bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							</tr>
			            </table>
						</td>
					</tr>
				</table>
			
			</logic:notPresent>

				
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