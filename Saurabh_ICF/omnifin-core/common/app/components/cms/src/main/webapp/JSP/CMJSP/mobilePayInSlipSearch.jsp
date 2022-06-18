<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userScript.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/mobilePayInSlip.js"></script>
	<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);

%>
</head>
<body onload="enableAnchor();init_fields();">
<div align="center" class="opacity" style="display: none" id="processingImage"></div>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
	</logic:notPresent>
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<html:form styleId="mobilePayInSlip"  method="post"  action="/mobliePayInSlipAction" >
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>

<html:errors/>
<fieldset>
<legend><bean:message key="lbl.mobilePayInSlip" /></legend>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td>
<table border="0" cellpadding="1" cellspacing="1" width="100%">
	<tr>
		<td><bean:message key="lbl.FromDate"/><span><font color="red">*</font></span></td>
		<td>
			<html:text property="payFromDate"  styleId="payFromDate" styleClass="text" value="" maxlength="10" onchange="checkDate('payFromDate');"/>
		</td>									
		<td ><bean:message key="lbl.ToDate"/><span><font color="red">*</font></span></td>
		<td>
			<html:text property="payToDate"  styleId="payToDate" styleClass="text" value="" maxlength="10" onchange="checkDate('payToDate');"/>
			<%-- <html:text property="payToDate" styleClass="text"  value="" styleId="payToDate" onchange="checkDate(value);"/> --%>
		</td>					
	</tr>
	<tr>

		<td><bean:message key="lbl.createdBy" /></td>
		<td><html:text property="userName" styleClass="text" styleId="userName" maxlength="50" value=""/>
			<html:hidden property="userId" styleClass="text" styleId="userId" value="" />
			<input type="hidden" name="lbxUserId" id="lbxUserId" value=""/>
			<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(20132,'mobilePayInSlip','userName','','lbxUserId', '','','','userId')" value=" " styleClass="lovbutton"></html:button>
		</td>
		<%-- <td><bean:message key="lbl.allBranch"/></td>
		<td ><html:checkbox property="allBranches" styleClass="text"styleId="allBranches" /></td> --%>
		
	</tr>
	
	<tr> 
	<td>
		<button type="button"  id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="mobilePayInSearch();" ><bean:message key="button.search" /></button>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
</fieldset>
<br/>
<fieldset>
<legend><bean:message key="lbl.mobilePayInSlipDtl" /></legend>

	<logic:present name="list">
	<logic:notEmpty name="list">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/mobliePayInSlipAction.do" >
	<display:setProperty name="paging.banner.placement"  value="bottom"/>
	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="payInSlipNo" titleKey="lbl.payInSlipNo"  sortable="true"  />
	<%-- <display:column property="instrumentID" titleKey="lbl.instrumentID"  sortable="true"  /> --%>
	<display:column property="recipetMode" titleKey="lbl.recipetMode"  sortable="true"  />
	<display:column property="makerDate" titleKey="lbl.uploadedDate"  sortable="true"  />
	<display:column property="makerName" titleKey="lbl.uploadedBy"  sortable="true"  />
	<display:column property="imageLink" titleKey="lbl.view"  sortable="true" />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.payInSlipNo" /> </strong>
									</td>
									
									<%-- <td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.instrumentID" /> <br> </b>
									</td> --%>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.recipetMode" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.uploadedDate" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.uploadedBy" /> <br> </b>
									</td>
								</tr>
								<tr class="white2" >
									<td colspan="7"><bean:message key="lbl.noDataFound" /></td>
								</tr>
						</table>
						</td>
					</tr>
				</table>
</logic:empty>
  </logic:present>
</fieldset>   

	</html:form>		
	<logic:present name="sms">
<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
	
	}
	
</script>
</logic:present>		
  </body>
		</html:html>