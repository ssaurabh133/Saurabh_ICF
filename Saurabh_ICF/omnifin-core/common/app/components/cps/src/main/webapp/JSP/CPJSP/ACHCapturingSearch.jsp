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
		
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="search" content="" />
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
	</head>
	<body oncontextmenu="return false"
		onunload=""
		onload="enableAnchor();">
		
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

		<html:form action="/achCapturingAction"	styleId="achCapturingSearchForm" method="post">
			<input type="hidden" id="contextPath" value="<%=path%>" />
			<fieldset>
				<legend>
					<bean:message key="lbl.achCapturingSearch" />
				</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
								 <logic:notEqual name="functionId" value="3000362">
									<td>
										<bean:message key="lbl.loanNo"/>
									</td>
									<td>
										<html:text property="txtDealNo" styleId="txtDealNo" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
						  				<input type="button" class="lovbutton" id="btnDealNo"
											onClick="openLOVCommon(17018,'achCapturingSearchForm','txtDealNo','','', '','','','txtCustomerName','hidDealNo')"
											value=" " name="dealButton"/>
										<input type="hidden" name="hidDealNo" id="hidDealNo" value=""/>
									</td>
									</logic:notEqual>
									<logic:equal name="functionId" value="3000362">
									<td>
										<bean:message key="lbl.DealNo"/>
									</td>
									<td>
										<html:text property="txtDealNo" styleId="txtDealNo" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
						  				<input type="button" class="lovbutton" id="btnDealNo"
											onClick="openLOVCommon(17019,'achCapturingSearchForm','txtDealNo','','', '','','','txtCustomerName','hidDealNo')"
											value=" " name="dealButton"/>
										<input type="hidden" name="hidDealNo" id="hidDealNo" value=""/>
									</td>
									</logic:equal>
									<td>
										<bean:message key="lbl.customerName"/>
									</td>
									<td>
										<html:text property="txtCustomerName" styleId="txtCustomerName" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td>
						<button type="button" name="Search" class="topformbutton2" id="Search" title="Alt+S" accesskey="S" onclick="return searchACHData();">
							<bean:message key="button.search" />
						</button>
						
						<button type="button" name="New" class="topformbutton2" id="New" title="Alt+N" accesskey="N" onclick="return newACHData();">
							<bean:message key="button.new" />
						</button>
					</td>
				</tr>
			</table>
			</fieldset>
			
			
		</html:form>
		
		
		<fieldset>	
			<legend><bean:message key="lbl.achCapturingSearchRecords"/></legend>  
	        
	 		<logic:present name="fetchSavedRecordList">
	 			<logic:notEmpty name="fetchSavedRecordList">
		  			<display:table  id="fetchSavedRecordList"  name="fetchSavedRecordList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${fetchSavedRecordList[0].totalRecordCount}" requestURI="/achCapturingAction.do" >
		    			<display:setProperty name="paging.banner.placement"  value="bottom"/>
		    			<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		    			<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					   <logic:notEqual name="functionId" value="3000362">
					    <display:column property="txtDealNo" titleKey="lbl.loanNo"  sortable="true"  />
					    </logic:notEqual>
					     <logic:equal name="functionId" value="3000362">
					    <display:column property="txtDealNo" titleKey="lbl.DealNo"  sortable="true"  />
					    </logic:equal>
						<display:column property="txtCustomerName" titleKey="lbl.customerName"  sortable="true"  />
						<display:column property="makerId" titleKey="lbl.userNam"  sortable="true"  />
					</display:table>
				</logic:notEmpty>
				<logic:empty name="fetchSavedRecordList">
	 				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
	    					<td class="gridtd">
	    						<table width="100%" border="0" cellspacing="1" cellpadding="1">
	    							<tr class="white2">
	    							 <logic:notEqual name="functionId" value="3000362">
	        							<td><b><bean:message key="lbl.loanNo"/></b></td>
	        							</logic:notEqual>
	        							<logic:equal name="functionId" value="3000362">
	        							<td><b><bean:message key="lbl.DealNo"/></b></td>
	        							</logic:equal>
	         							<td><b><bean:message key="lbl.customerName"/></b></td>
	        							<td><b><bean:message key="lbl.userNam"/> </b></td>
									</tr>
									<tr class="white2"><td colspan="8"><bean:message key="lbl.noDataFound"/></td></tr>
								</table>
							</td>
						</tr>
					</table>
				</logic:empty>
	  		</logic:present>
	  	</fieldset>
	</body>
</html:html>
	
