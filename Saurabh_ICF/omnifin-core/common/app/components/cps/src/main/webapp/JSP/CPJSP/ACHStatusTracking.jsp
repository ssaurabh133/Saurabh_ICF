<%--
 	Author Name      :- Himanshu Verma
 	Date of Creation :- 07 Dec 2015
 	Purpose          :- ACH Capturing Search Screen 
 --%>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@ page language="java" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/achCapturing.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

	<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
	
		
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="maker" content="" />
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		  
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
		<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>
		<script>
		$(function() {
			$("#dteSentDate").datepicker({
				changeMonth: true,
			changeYear: true,
			yearRange: '1900:+10',
			showOn: 'both',
			buttonImage: document.getElementById("CalImage").value,
			buttonImageOnly: true,
			dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
			
			});
		});
		
		$(function() {
			$("#dteReceivedDate").datepicker({
				changeMonth: true,
			changeYear: true,
			yearRange: '1900:+10',
			showOn: 'both',
			buttonImage: document.getElementById("CalImage").value,
			buttonImageOnly: true,
			dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("businessdate").value
			
			});
		});
		</script>
		
	</head>
	<body oncontextmenu="return false"
		onunload=""
		onload="enableAnchor();enableDisableFields();">
		
		<logic:present name="image">
   	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
   		<logic:notPresent name="image">
   			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
   		</logic:notPresent>
   		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<div align="center" class="opacity" style="display: none;"
			id="processingImage">
		</div>

		<html:form action="/achCapturingAction"	styleId="achTrackingDataForm" method="post">
			<input type="hidden" id="contextPath" value="<%=path%>" />
			<html:hidden property="hidAchCapturingId" styleId="hidAchCapturingId" value="${achCapturingId}" />
			<fieldset>
			<logic:notPresent name="view">
				<legend>
					<bean:message key="lbl.achStatusTracking" />
				</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td>
										<bean:message key="lbl.achStatus"/><font color="red">*</font>
									</td>
									<td>
									<%-- 	<html:text property="selACHStatus" styleId="selACHStatus" styleClass="text" maxlength="50" value="" />--%>
										<html:select property="selACHStatus" styleId="selACHStatus" onchange="enableDisableFields();"
											styleClass="text" value="" style="width:90px !important; min-width:85px !important;" >
											<html:option value="">--Select--</html:option>
											<logic:present name="ACHStatusList">
												<html:optionsCollection name="ACHStatusList"
													label="selACHStatusDescription"
													value="selACHStatusValue" />
											</logic:present>
										</html:select>
									</td>
									
									<td>
										<bean:message key="lbl.achRemarks"/><font color="red">*</font>
									</td>
									<td>
										<html:text property="txtRemarks" styleId="txtRemarks" styleClass="text" maxlength="50" value="${alAchTrackingList[0].txtRemarks}"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.achVendorName"/>
									</td>
									<td>
							<%-- 		<html:text property="selVendorName" styleId="selVendorName" styleClass="text" maxlength="50" value="" />--%>
										<html:text property="txtselVendorName" styleId="txtselVendorName" styleClass="text" maxlength="50" value="${alAchTrackingList[0].txtselVendorName}" readonly="true" tabindex="-1"/>
						  				<input type="button" class="lovbutton" id="btnVendor"
											onClick="openLOVCommon(16103,'achTrackingDataForm','txtselVendorName','','', '','','','hidselVendorName','hidselVendorName')"
											value=" " name="btnVendor"/>
										<input type="hidden" name="hidselVendorName" id="hidselVendorName" value="${alAchTrackingList[0].hidselVendorName}"/>
									</td>
									
									<td>
										<bean:message key="lbl.achSentDate"/>
									</td>
									<td>
										<html:text property="dteSentDate" styleId="dteSentDate" styleClass="text" maxlength="10" value="${alAchTrackingList[0].dteSentDate}" onchange="return checkDate('dteSentDate');"/>
										<html:text property="dteSentDateNonEditable" styleId="dteSentDateNonEditable" styleClass="text" maxlength="10" value="${alAchTrackingList[0].dteSentDate}" readonly="true"  style="display:none"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.UMRN"/>
									</td>
									<td>
										<html:text property="txtUMRN" styleId="txtUMRN" styleClass="text" maxlength="20" value="" />
									</td>
									<td>
										<bean:message key="lbl.achLotNo"/>
									</td>
									<td>
										<html:text property="txtLotNo" styleId="txtLotNo" styleClass="text" maxlength="50" value="${alAchTrackingList[0].txtLotNo}" />
									</td>
									
								</tr>
								<tr>
								<td>
										<bean:message key="lbl.receivedDate"/>
									</td>
									<td>
										<html:text property="dteReceivedDate" styleId="dteReceivedDate" styleClass="text" maxlength="10" value="" onchange="return checkDate('dteReceivedDate');"/>
										<html:text property="dteReceivedDateNonEditable" styleId="dteReceivedDateNonEditable" styleClass="text" maxlength="10" value="" readonly="true" style="display:none"/>
									</td>
									<td>
										<bean:message key="lbl.achReceivedStatus"/>
									</td>
									<td>
										<%-- html:text property="selACHReceivedStatus" styleId="selACHReceivedStatus" styleClass="text" maxlength="50" value="" />--%>
										
										<html:select property="selACHReceivedStatus" styleId="selACHReceivedStatus"
											styleClass="text" value="" style="width:90px !important; min-width:85px !important;">
											<html:option value="">--Select--</html:option>
											<logic:present name="ACHReceivedStatusList">
												<html:optionsCollection name="ACHReceivedStatusList"
													label="selACHReceivedStatusDescription"
													value="selACHReceivedStatusValue" />
											</logic:present>
										</html:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td>
						<button type="button" name="Save" class="topformbutton2" id="Save" title="Alt+S" accesskey="S" onclick="return saveACHTrackingData();">
							<bean:message key="button.save" />
						</button>
					</td>
				</tr>
			</table>
			</logic:notPresent>
			
			
			<logic:present name="view">
				<legend>
					<bean:message key="lbl.achStatusTracking" />
				</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td>
										<bean:message key="lbl.achStatus"/><font color="red">*</font>
									</td>
									<td>
									<%-- 	<html:text property="selACHStatus" styleId="selACHStatus" styleClass="text" maxlength="50" value="" />--%>
										<html:select property="selACHStatus" styleId="selACHStatus" onchange="enableDisableFields();"
											styleClass="text" value="" style="width:90px !important; min-width:85px !important;" disabled="true">
											<html:option value="">--Select--</html:option>
											<logic:present name="ACHStatusList">
												<html:optionsCollection name="ACHStatusList"
													label="selACHStatusDescription"
													value="selACHStatusValue" />
											</logic:present>
										</html:select>
									</td>
									
									<td>
										<bean:message key="lbl.achRemarks"/><font color="red">*</font>
									</td>
									<td>
										<html:text property="txtRemarks" styleId="txtRemarks" styleClass="text" maxlength="50" value="${alAchTrackingList[0].txtRemarks}" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.achVendorName"/>
									</td>
									<td>
							<%-- 		<html:text property="selVendorName" styleId="selVendorName" styleClass="text" maxlength="50" value="" />--%>
										<html:text property="txtselVendorName" styleId="txtselVendorName" styleClass="text" maxlength="50" value="${alAchTrackingList[0].txtselVendorName}" readonly="true" tabindex="-1" disabled="true"/>
						  				<input type="button" class="lovbutton" id="btnVendor"
											onClick="openLOVCommon(16103,'achTrackingDataForm','txtselVendorName','','', '','','','hidselVendorName','hidselVendorName')"
											value=" " name="btnVendor" style="display: none;"/>
										<input type="hidden" name="hidselVendorName" id="hidselVendorName" value="${alAchTrackingList[0].hidselVendorName}"/>
									</td>
									
									<td>
										<bean:message key="lbl.achSentDate"/>
									</td>
									<td>
										<html:text property="dteSentDate" styleId="dteSentDateInView" styleClass="text" maxlength="10" value="${alAchTrackingList[0].dteSentDate}" onchange="return checkDate('dteSentDate');" disabled="true"  />
										<html:text property="dteSentDateNonEditable" styleId="dteSentDateNonEditable" styleClass="text" maxlength="10" value="${alAchTrackingList[0].dteSentDate}" readonly="true"  style="display:none"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.UMRN"/>
									</td>
									<td>
										<html:text property="txtUMRN" styleId="txtUMRN" styleClass="text" maxlength="20" value="" disabled="true"/>
									</td>
									<td>
										<bean:message key="lbl.achLotNo"/>
									</td>
									<td>
										<html:text property="txtLotNo" styleId="txtLotNo" styleClass="text" maxlength="50" value="${alAchTrackingList[0].txtLotNo}" disabled="true"/>
									</td>
									
								</tr>
								<tr>
								<td>
										<bean:message key="lbl.receivedDate"/>
									</td>
									<td>
										<html:text property="dteReceivedDate" styleId="dteReceivedDateInView" styleClass="text" maxlength="10" value="" onchange="return checkDate('dteReceivedDate');" disabled="true"/>
										<html:text property="dteReceivedDateNonEditable" styleId="dteReceivedDateNonEditable" styleClass="text" maxlength="10" value="" readonly="true" style="display:none"/>
									</td>
									<td>
										<bean:message key="lbl.achReceivedStatus"/>
									</td>
									<td>
										<%-- html:text property="selACHReceivedStatus" styleId="selACHReceivedStatus" styleClass="text" maxlength="50" value="" />--%>
										
										<html:select property="selACHReceivedStatus" styleId="selACHReceivedStatus"
											styleClass="text" value="" style="width:90px !important; min-width:85px !important;" disabled="true">
											<html:option value="">--Select--</html:option>
											<logic:present name="ACHReceivedStatusList">
												<html:optionsCollection name="ACHReceivedStatusList"
													label="selACHReceivedStatusDescription"
													value="selACHReceivedStatusValue" />
											</logic:present>
										</html:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
	
			</logic:present>
			
			
			
			
			</fieldset>
			
			
			
			
			<fieldset>	
			<legend><bean:message key="lbl.achStatusTrackingRecords"/></legend>  
	 		<logic:present name="alAchTrackingList">
	 			<logic:notEmpty name="alAchTrackingList">
		  			<display:table  id="alAchTrackingList"  name="alAchTrackingList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true"  size="${alAchTrackingList[0].totalRecordCount}" requestURI="" >
		    			<display:setProperty name="paging.banner.placement"  value="bottom"/>
		    			<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		    			<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="selACHStatus" titleKey="lbl.achStatus"  sortable="false"  />
						<display:column property="txtRemarks" titleKey="lbl.achRemarks"  sortable="false"  />
						<display:column property="txtselVendorName" titleKey="lbl.achVendorName"  sortable="false"  />
						<display:column property="dteSentDate" titleKey="lbl.achSentDate"  sortable="false"  />
						<display:column property="txtLotNo" titleKey="lbl.achLotNo"  sortable="false"  />
						<display:column property="dteReceivedDate" titleKey="lbl.receivedDate"  sortable="false"  />
						<display:column property="selACHReceivedStatus" titleKey="lbl.achReceivedStatus"  sortable="false"  />
						<display:column property="txtUMRN" titleKey="lbl.UMRN"  sortable="false"  />
					</display:table>
				</logic:notEmpty>
				<logic:empty name="alAchTrackingList">
	 				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
	    					<td class="gridtd">
	    						<table width="100%" border="0" cellspacing="1" cellpadding="1">
	    							<tr class="white2">
	         							<td><b><bean:message key="lbl.achStatus"/></b></td>
	        							<td><b><bean:message key="lbl.achRemarks"/> </b></td>
	        							<td><b><bean:message key="lbl.achVendorName"/> </b></td>
	        							<td><b><bean:message key="lbl.achSentDate"/> </b></td>
	        							<td><b><bean:message key="lbl.achLotNo"/> </b></td>
	        							<td><b><bean:message key="lbl.receivedDate"/> </b></td>
	        							<td><b><bean:message key="lbl.achReceivedStatus"/> </b></td>
	        							<td><b><bean:message key="lbl.UMRN"/> </b></td>
									</tr>
									<tr class="white2"><td colspan="8"><bean:message key="lbl.noDataFound"/></td></tr>
								</table>
							</td>
						</tr>
					</table>
				</logic:empty>
	  		</logic:present>
	  	</fieldset>
		</html:form>
		<logic:present name="message" >
		<script>
		if('<%=session.getAttribute("message").toString()%>'=='S')
		{
			alert("<bean:message key="msg.DataSaved" />");
		}
		if('<%=session.getAttribute("message").toString()%>'=='B')
		{
			alert("<bean:message key="msg.sentfirst" />");
		}
		 if('<%=session.getAttribute("message").toString()%>'=='Back')
		{
			alert("Please capture ACH details first");
			parent.location='<%=request.getContextPath()%>/achCapturingAction.do?method=newACHRecord';
		}
		</script>
		</logic:present>
	</body>
</html:html>