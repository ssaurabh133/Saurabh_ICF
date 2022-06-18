<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

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
		  <!-- css and jquery for Datepicker -->
			<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
		    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		 <!-- css and jquery for Datepicker -->
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/sczScript/poolIDMakerAuthor.js"></script>
		  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>


		<%	
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
			request.setAttribute("no",no); 
		%>	

	 	<%
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>		
	<script type="text/javascript">
		function popitup(url) {
			newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
			if (window.focus) {newwindow.focus()}
			return false;
		} 
	</script>
</head>
<body onload="enableAnchor();document.getElementById('manualAdviceSerchForm').loanButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
		<logic:present name="image">
    	   	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
    	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">
		<div id="revisedcontainer">
			<html:form action="/Tab1ProcessAction" styleId="sourcingForm" method="post">
				<logic:present name="updateList">
					<logic:notEmpty name="updateList">
				<fieldset>
					<legend>Credit Enhancement Details</legend> 
					<html:hidden property="hid" value="${updateList[0].id}"/>         
	   					<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath() %>"/>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									 <td><bean:message key="lbl.poolID"/><font color="red">*</font></td>
									 <td>
								        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${updateList[0].poolID}" readonly="true" tabindex="-1"/>   
							         </td>
									<td><bean:message key="lbl.creditEnhanceType"/><font color="red">*</font></td>
									<td>
										<html:select property="creditEnhanceType" value="${updateList[0].creditEnhanceType}" styleId="creditEnhanceType" styleClass="text">
											<html:option value="">--Select--</html:option>
											<html:option value="FD">FD</html:option>
											<html:option value="CG">CG</html:option>
											<html:option value="BG">BG</html:option>
										</html:select>
									</td>
								</tr>
								
								<tr>
									<td><bean:message key="lbl.creditEnhanceDocRefNo"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text" styleId="creditEnhanceDocRefNo" value="${updateList[0].creditEnhanceDocRefNo}" maxlength="60" property="creditEnhanceDocRefNo"></html:text>
									</td>
									<td><bean:message key="lbl.creditEnhanceAmount"/><font color="red">*</font></td>
									<td>
									<!--  	<html:text styleClass="text" styleId="creditEnhanceAmount" value="${updateList[0].creditEnhanceAmount}" property="creditEnhanceAmount"></html:text> -->
										<html:text styleClass="text" styleId="creditEnhanceAmount" value="${updateList[0].creditEnhanceAmount}" property="creditEnhanceAmount" maxlength="15" onkeypress="return isNumberKey(event);"
													 style="text-align:right;"/>
									</td>
								</tr>
								
								<tr>
								<logic:notPresent name="authorPoolIdSavedList">
									<td colspan="4">
										<button type="button" name="button" onclick="updateTab1()" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.save" /></button>
										<button type="button" name="button" onclick="deleteTab1()" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.Delete" /></button>
									</td>
								</logic:notPresent>
								</tr>
							</table>
				</fieldset>
				</logic:notEmpty>
				</logic:present>
				
				<logic:notPresent name="updateList">
				<fieldset>
					<legend>Credit Enhancement Details</legend>         
	   					<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath() %>"/>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									 <td><bean:message key="lbl.poolID"/><font color="red">*</font></td>
									 <td>
								        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${requestScope.poolNo}" readonly="true" tabindex="-1"/>   
							         </td>
									<td><bean:message key="lbl.creditEnhanceType"/><font color="red">*</font></td>
									<td>
										<html:select property="creditEnhanceType" styleId="creditEnhanceType" styleClass="text">
											<html:option value="">--Select--</html:option>
											<html:option value="FD">FD</html:option>
											<html:option value="CG">CG</html:option>
											<html:option value="BG">BG</html:option>
										</html:select>
									</td>
								</tr>
								
								<tr>
									<td><bean:message key="lbl.creditEnhanceDocRefNo"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text" styleId="creditEnhanceDocRefNo" maxlength="60" property="creditEnhanceDocRefNo"></html:text>
									</td>
									<td><bean:message key="lbl.creditEnhanceAmount"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text" styleId="creditEnhanceAmount" property="creditEnhanceAmount" maxlength="15"
													onkeypress="return isNumberKey(event);"
													style="text-align:right;"/>
									</td>
								</tr>
								
								<tr>
									<logic:notPresent name="authorPoolIdSavedList">
										<td colspan="4"><button type="button" name="button" onclick="saveTab1()" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.save" /></button></td>	
									</logic:notPresent>
								</tr>
							</table>
				</fieldset>
				</logic:notPresent>
				
				<fieldset>
					<legend>Credit Enhancement Details</legend>  
					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
			    			<td class="gridtd"> 
								<table width="100%" border="0" cellspacing="1" cellpadding="1" >
									<logic:present name="gridList">
									<logic:notEmpty name="gridList">	
										<display:table  id="gridList"  name="gridList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${gridList[0].totalRecord}" requestURI="/tab1BehindAction.do" >
										    <display:setProperty name="paging.banner.placement"  value="bottom"/>
										    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
										    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
										    
										    <display:column property="srNo" titleKey="lbl.srNo"  sortable="true"  />
											<display:column property="poolID" titleKey="lbl.poolID"  sortable="true"  />
											<display:column property="creditEnhanceType" titleKey="lbl.creditEnhanceType"  sortable="true"  />
											<display:column property="creditEnhanceDocRefNo" titleKey="lbl.creditEnhanceDocRefNo"  sortable="true"  />
											<display:column property="creditEnhanceAmount" titleKey="lbl.creditEnhanceAmount"  sortable="true"  />
										</display:table>
									</logic:notEmpty>
									
									<logic:empty name="gridList">
										<tr class="white2">
											<td><STRONG><bean:message key="lbl.srNo"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.poolID"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.creditEnhanceType"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.creditEnhanceDocRefNo"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.creditEnhanceAmount"/></STRONG></td>
										</tr>
										<tr class="white1">
											<td colspan="7">No Data Found</td>
										</tr>
									</logic:empty>
									</logic:present>
									
									<logic:notPresent name="gridList">
										<tr class="white2">
											<td><STRONG><bean:message key="lbl.srNo"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.poolID"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.creditEnhanceType"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.creditEnhanceDocRefNo"/></STRONG></td>
											<td><STRONG><bean:message key="lbl.creditEnhanceAmount"/></STRONG></td>
										</tr>
										<tr class="white1">
											<td colspan="7">No Data Found</td>
										</tr>
									</logic:notPresent>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</html:form>
		</div>
	</div>

<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='N')
	{
		// alert('<bean:message key="lbl.noDataFound" />');
	}
    else if('<%=request.getAttribute("msg")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
</script>
</logic:present>
<logic:present name="mssg">
		<script type="text/javascript">	
			alert("<%=request.getAttribute("mssg")%>");
			window.location='<%=request.getContextPath()%>/tab1BehindAction.do?method=tab1OpenAction';	
			
		</script>
</logic:present>
</body>
</html>