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
		<input type="hidden" id=investmentRatio value='${investmentRatio}'/>		<!-- seema -->
		<input type="hidden" id=instituteID1 value='${poolIdSavedDataList[0].lbxinstituteID}'/>	<!-- seema -->
		
	<div id="centercolumn">
		<div id="revisedcontainer">
			<html:form action="/Tab2ProcessAction" method="post" styleId="sourcingForm">
				<logic:present name="updateList">
					<logic:notEmpty name="updateList">
				<fieldset>
					<legend>Multiple Investor Details</legend> 
						<html:hidden property="hid" value="${updateList[0].id}"/>        
	   					<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath() %>"/>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
									 <td><bean:message key="lbl.poolID"/><font color="red">*</font></td>
									 <td>
								        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${updateList[0].poolID}" readonly="true" tabindex="-1"/>   
							         </td>
								</tr>
								<tr>
									<td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${updateList[0].lbxinstituteID}" readonly="true" tabindex="-1"/>   
		     							<%-- <html:hidden property="lbxinstituteID" value="${poolIdSavedDataList[0].lbxinstituteID}" styleId="lbxinstituteID" />	<!-- seema --> --%>
		     							<html:hidden property="lbxinstituteID" value="${updateList[0].lbxinstituteID}" styleId="lbxinstituteID" />
									     <input type="hidden" name="hcommon" id="hcommon" />
										 <%-- <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button> --%>   
           							 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();instituteIdTemp();" value=" " styleClass="lovbutton"></html:button>	<!-- seema -->
           							</td>
									<td><bean:message key="lbl.investmentRatio"/><font color="red">*</font></td>
	          						<td>
	          						<%-- <html:text styleClass="text" styleId="investmentRatio" value='${investmentRatio}' property="investmentRatio" onclick="return totalInvestmentRatio()" />	<!-- seema --> --%>
	          							<html:text styleClass="text" styleId="investmentRatio" value="${updateList[0].investmentRatio}" property="investmentRatio"  onchange="return totalInvestmentRatio();"  onclick="return totalInvestmentRatio100();"/>   	<!-- seema -->
	         						<%-- <html:text styleClass="text" styleId="investmentRatio" value="${updateList[0].investmentRatio}" property="investmentRatio" onkeypress="return isNumberKey(event)" /> --%>	
	         						</td>
								</tr>
								
								<tr>
									<td><bean:message key="lbl.interestRate"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text" value="${updateList[0].interestRate}" styleId="interestRate" property="interestRate" onkeypress="return isNumberKey(event)"></html:text>
									</td>
									<td><bean:message key="lbl.distributionPriority"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text" styleId="distributionPriority" value="${updateList[0].distributionPriority}" property="distributionPriority"></html:text>
									</td>
								</tr>
								
								<tr>
								<logic:notPresent name="authorPoolIdSavedList">
									<td colspan="4">
									<button type="button" name="button" onclick="return updateTab2()" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.save" /></button>
									<button type="button" name="button" onclick="return deleteTab2()" class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.Delete" /></button>
									</td>
								</logic:notPresent>
								</tr>
							</table>
				</fieldset>	
				</logic:notEmpty>
				</logic:present>
				
				<logic:notPresent name="updateList">
				<fieldset>
					<legend>Multiple Investor Details</legend>         
	   					<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath() %>"/>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
									 <td><bean:message key="lbl.poolID"/><font color="red">*</font></td>
									 <td>
								        <html:text styleClass="text" property="poolID" styleId="poolID" maxlength="100" value="${requestScope.poolNo}" readonly="true" tabindex="-1"/>   
							         </td>
								</tr>
								<tr>
									<td><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
									<td>
										<%-- <html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="" readonly="true" tabindex="-1"/> --%>   
		     						<html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="${updateList[0].lbxinstituteID}" readonly="true" tabindex="-1"/>
		     							
		     							<html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
									     <input type="hidden" name="hcommon" id="hcommon" />
										<%--  <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button> --%>   
           							 <html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();instituteIdTemp();" value=" " styleClass="lovbutton"></html:button>	<!-- seema -->
           							</td>
									<td><bean:message key="lbl.investmentRatio"/><font color="red">*</font></td>
	          						<td>
	          						<html:text styleClass="text" styleId="investmentRatio" value='${investmentRatio}' property="investmentRatio" onchange="return totalInvestmentRatio();"  onclick="return totalInvestmentRatio100();"/>	<!-- seema -->
	          							<%-- <html:text styleClass="text" styleId="investmentRatio" property="investmentRatio" onkeypress="return isNumberKey(event)" onclick="return totalInvestmentRatio()"/>	<!-- seema --> --%>
	         						<%-- <html:text styleClass="text" styleId="investmentRatio" property="investmentRatio" onkeypress="return isNumberKey(event)" />	<!-- seema --> --%>
	         						</td>
								</tr>
								
								<tr>
									<td><bean:message key="lbl.interestRate"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text"  styleId="interestRate" property="interestRate" onkeypress="return isNumberKey(event)"></html:text>
									</td>
									<td><bean:message key="lbl.distributionPriority"/><font color="red">*</font></td>
									<td>
										<html:text styleClass="text" styleId="distributionPriority" property="distributionPriority"></html:text>
									</td>
								</tr>
								
								<tr>
								<logic:notPresent name="authorPoolIdSavedList">
									<td colspan="4"><button type="button" name="button" onclick="return saveTab2(),totalInvestmentRatio100()"  class="topformbutton2" title="Alt+S" accesskey="S"><bean:message key="button.save" /></button></td>
								</logic:notPresent>
								</tr>
							</table>
				</fieldset>
				</logic:notPresent>
				
				
				<fieldset>
					<legend>Multiple Investor Details</legend>  
					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
			    			<td class="gridtd"> 
								<table width="100%" border="0" cellspacing="1" cellpadding="1" >
									<logic:present name="gridList">
									<logic:notEmpty name="gridList">	
										<display:table  id="gridList"  name="gridList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${gridList[0].totalRecord}" requestURI="/tab1BehindAction.do?method=tab2OpenAction" >
										    <display:setProperty name="paging.banner.placement"  value="bottom"/>
										    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
										    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
										    
										    <display:column property="srNo" titleKey="lbl.srNo"  sortable="true"  />
											<display:column property="poolID" titleKey="lbl.poolID"  sortable="true"  />
											<display:column property="lbxinstituteID" titleKey="lbl.instituteID"  sortable="true"  />
											<display:column property="investmentRatio" titleKey="lbl.investmentRatio"  sortable="true"  />
											<display:column property="interestRate" titleKey="lbl.interestRate"  sortable="true"  />
											<display:column property="distributionPriority" titleKey="lbl.distributionPriority"  sortable="true"  />
										</display:table>
										
									</logic:notEmpty>
									<logic:empty name="gridList">
										<tr class="white2">
										<td><STRONG><bean:message key="lbl.srNo"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.poolID"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.instituteID"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.investmentRatio"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.interestRate"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.distributionPriority"/></STRONG></td>
									</tr>
										<tr class="white1">
											<td colspan="6">No Data Found</td>
										</tr>
									</logic:empty>
									</logic:present>
									<logic:notPresent name="gridList">
									<tr class="white2">
										<td><STRONG><bean:message key="lbl.srNo"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.poolID"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.instituteID"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.investmentRatio"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.interestRate"/></STRONG></td>
										<td><STRONG><bean:message key="lbl.distributionPriority"/></STRONG></td>
									</tr>
										<tr class="white1">
											<td colspan="6">No Data Found</td>
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
			window.location='<%=request.getContextPath()%>/tab1BehindAction.do?method=tab2OpenAction';	
			
		</script>
</logic:present>
</body>
</html>