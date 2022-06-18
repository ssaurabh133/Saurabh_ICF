<!--Author Name :- Mradul Agarwal-->
<!--Date of Creation : 19_Jan_2013-->
<!--Purpose :-  Stationary Addition Screen-->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="com.logger.LoggerMsg"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>
		<!-- css for Datepicker -->
<link type="text/css" href="development-bundle/demos/demos.css" rel="stylesheet" />
<link type="text/css"
	href="development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<!-- jQuery for Datepicker -->
<script type="text/javascript" src="development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/stationaryMasterScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
</head>
<body onload="enableAnchor();">
	<div align="center" class="opacity" style="display:none" id="processingImage"></div>
		<html:form action="/stationaryIssuanceMasterAtCM" styleId="StationaryMasterForm" >
		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"  />
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<logic:present name="image">
				<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
				<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
				
				
		<fieldset> <legend><bean:message key="lbl.Issuance"/></legend>
		<table width="100%" border="0" cellspacing="2" cellpadding="1">
			<tr>
			
			     <td><bean:message key="lbl.BookNo"/><span><font color="red">*</font></span></td>
							
				<td>
				  
					<html:text property="bookIssue" styleClass="text" styleId="bookIssue" maxlength="100" readonly="true" value=""/>
					<html:hidden property="lbxBookNo" styleId="lbxBookNo" value="" />
					<html:button property="branchButton" styleId="branchButton" tabindex="1" onclick="openLOVCommon(559,'StationaryMasterForm','bookIssue','','', '','','','lbxBookNo')" value=" " styleClass="lovbutton"  ></html:button>
			   </td>
			   				
				<td><bean:message key="lbl.InsType"/><span><font color="red">*</font></span></td>
				<td><html:select property="desc" styleId="desc">						
				<html:option value="">--Select--</html:option>
				<html:option value="R">Receipt Book</html:option>
				<html:option value="C">Cheque Book</html:option>						
				</html:select></td>
			</tr>	
			
			<tr> 
				<td align="left" colspan="2" class="form2">
				<button type="button" name="Search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchAction();">
				<bean:message key="button.search" /></button>					
				</td>
			</tr>
		</table>
	    </fieldset>
	    <br/>

		<fieldset><legend><bean:message key="lbl.Issudtl"/></legend>
		<logic:present name="list">
	    	<logic:notEmpty name="list">
			<display:table  id="list"  name="list" style="width: 100%" class="dataTable" cellspacing="1"  pagesize="5" requestURI="/stationaryIssuanceMasterAtCM.do" >
				<display:setProperty name="paging.banner.placement"  value="bottom"/>
				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				<display:column property="bookIssue" titleKey="lbl.BookNo" sortable="true"/>
				<display:column property="desc" titleKey="lbl.InsType" sortable="true" style="text-align: center"/>
				<display:column property="instruFrom1" titleKey="lbl.InsFrom" sortable="true" />
				<display:column property="instruTo1"  titleKey="lbl.InsTo" sortable="true"  />
				<display:column property="branchid" titleKey="lbl.Issudbranch" sortable="true"/>
				<display:column property="userIssue" titleKey="lbl.Issueduser" sortable="true" />
				<display:column property="issuedate"  titleKey="lbl.Issuddate" sortable="true"  />
				<display:column property="status"  titleKey="lbl.Status" sortable="true"  />
			</display:table>
		</logic:notEmpty>
		<logic:empty name="list">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
           <td class="gridtd">
				<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr class="white2">
			
				<td width="220"  style="width: 220px;">
					<strong><bean:message key="lbl.BookNo" /> </strong>
				</td>									
				<td width="220"  style="width: 220px;">
					<strong><bean:message key="lbl.InsType" /> </strong>
				</td>
				<td width="220"  style="width: 250px;">
					<b><bean:message key="lbl.InsFrom" /> <br> </b>
				</td>
				<td width="220"  style="width: 250px;">
					<b><bean:message key="lbl.InsTo" /> <br> </b>
				</td>																	
				<td width="220"  style="width: 250px;">
					<b><bean:message key="lbl.Issubranch" /> <br> </b>
				</td>
				<td width="220"  style="width: 250px;">
					<b><bean:message key="lbl.Issueuser" /> <br> </b>
				</td>
				<td width="220"  style="width: 250px;">
					<b><bean:message key="lbl.Issudate" /> <br> </b>
				</td>
				<td width="220"  style="width: 250px;">
					<b><bean:message key="lbl.Status" /> <br> </b>
				</td>			
			</tr>
			<tr class="white2" >
	            <td colspan="10"><bean:message key="lbl.noDataFound" /></td>
            </tr>
							
		</table>
      </td>
      </tr>
      </table>
	</logic:empty>
		</logic:present>
		</fieldset>
	</html:form>

	</body>
</html>