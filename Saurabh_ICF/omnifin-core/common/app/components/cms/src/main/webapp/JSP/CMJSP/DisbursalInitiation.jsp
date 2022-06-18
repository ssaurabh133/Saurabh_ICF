<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>

	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
	%>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/disbursalJS.js"></script>
	<script type="text/javascript" 
		src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
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
<body onclick="parent_disable();" onload="enableAnchor();generateDisbursalVoucherReport();document.getElementById('loanNoButton').focus();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>

<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="forwardedLoanId" id="forwardedLoanId" value="${forwardedLoanId}"/>

<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<logic:present name="disbursalMaker">
	<div id="centercolumn">

		<div id="revisedcontainer">

			<html:form action="/disbursalSearch" method="post"
				styleId="disbursalSearchForm">

				<fieldset>
					<legend>
						<bean:message key="lbl.disbursalInitiationMakerSearch"/>
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanNo"/>
										</td>
										<td nowrap="nowrap">
										<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
											<html:text styleClass="text" property="loanNo" value=""
												styleId="loanNo"  readonly="true" maxlength="20" tabindex="-1"/>
											<html:button property="loanNoButton" styleId="loanNoButton"  value=" "
											onclick="closeLovCallonLov1();openLOVCommon(54,'disbursalSearchForm','loanNo','userId','product', 'userId','','clearSchemeValue','customerName')" styleClass="lovbutton"> </html:button>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
								<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  />
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.customerName"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="customerName"
												styleId="customerName" maxlength="50" value=""/>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanAmt"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="loanAmt"
												styleId="loanAmt"  onkeypress="return numbersonly(event,id,18)" 
												onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
												onfocus="keyUpNumber(this.value, event, 18,id);" />
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanApprovalDate"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="loanApprovalDate" 
												styleId="loanApprovalDate" maxlength="10" onchange="checkDate('loanApprovalDate');"/>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.product"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="product"
												styleId="product"  maxlength="20" readonly="true" tabindex="-1"/>
											<html:button property="productButton" styleId="productButton" value=" " onclick="openLOVCommon(55,'disbursalSearchForm','product','loanNo','lbxProductID', 'lbxLoanNoHID','Select Loan No. First','','lbxProductID');closeLovCallonLov('loanNo');" styleClass="lovbutton"> </html:button>
								<html:hidden  property="lbxProductID" styleId="lbxProductID"  />
								
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.scheme"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="scheme" 
												styleId="scheme" readonly="true" tabindex="-1"/>
											<html:button property="schemeButton" styleId="schemeButton" value=" " onclick="openLOVCommon(56,'disbursalSearchForm','scheme','loanNo','lbxscheme', 'lbxLoanNoHID','Select Loan No. First','','lbxscheme');closeLovCallonLov('loanNo');" styleClass="lovbutton"> </html:button>
								<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
								
										</td>
									</tr>
									
		<tr>
            <td>
      		<bean:message key="lbl.userName" />
      	    </td>
      	    <td>
            <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
            <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
     		<html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
     		<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
      		<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'disbursalSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
			
		</tr>
									<tr>
										<td nowrap="nowrap" colspan="4">
											<!--<html:button styleClass="topformbutton2" property="search" accesskey="S" title="Alt+S"
												styleId="search" value="Search" onclick="this.disabled=true;searchDisbursal('P');"/>
											<html:button property="new" styleId="new" value="New" accesskey="N" title="Alt+N"
												styleClass="topformbutton2" onclick="this.disabled=true;newDisbursal();"></html:button>-->
												<button type="button" class="topformbutton2" name="search"  id="search"  onclick="return searchDisbursal('P');"
														 title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
                                       <!--	<button type="button" class="topformbutton2" name="new"  id="new"  onclick="return newDisbursal();" 
														title="Alt+N" accesskey="N" ><bean:message key="button.new" /></button>  -->
									  <button type="button" class="topformbutton2" name="new"  id="new"  onclick="return newDisbursalPayment();" 
														title="Alt+N" accesskey="N" ><bean:message key="button.new" /></button>
										</td>
									</tr>
								</table>

							</td>
						</tr>

					</table>

				</fieldset>

			</html:form>
		</div>
		
	

		<fieldset>
			<legend>
				<bean:message key="lbl.disbursalInitiationMakerRecords"/>
			</legend>

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="disbursalSearchList">
    <tr class="white2">
	 
		<td><strong><bean:message key="lbl.loanNo"/></strong></td>
		<td><b><bean:message key="lbl.customerName"/></b></td>
		<td><strong><bean:message key="lbl.loanAmt"/></strong></td>
		<td><b><bean:message key="lbl.loanApprovalDate"/></b></td>
		<td><strong><bean:message key="lbl.product"/></strong></td>
		<td><b><bean:message key="lbl.scheme"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>
		
	</tr>
	<tr class="white2">
	<td colspan="7">
	<bean:message key="lbl.noDataFound"/>
	</td>
	</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
	 <logic:present name="disbursalSearchList">

<logic:notEmpty name="disbursalSearchList"> 
  <display:table  id="disbursalSearchList"  name="disbursalSearchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${disbursalSearchList[0].totalRecordSize}" requestURI="/disbursalSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="modifyNo" titleKey="lbl.loanNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="loanAmt" titleKey="lbl.loanAmt"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
		<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
		<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>

<logic:empty name="disbursalSearchList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td><strong><bean:message key="lbl.loanNo"/></strong></td>
		<td><b><bean:message key="lbl.customerName"/></b></td>
		<td><strong><bean:message key="lbl.loanAmt"/></strong></td>
		<td><b><bean:message key="lbl.loanApprovalDate"/></b></td>
		<td><strong><bean:message key="lbl.product"/></strong></td>
		<td><b><bean:message key="lbl.scheme"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>
	</tr>

</tr>
<tr class="white2">
<td colspan="7">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
 </table>    </td>

</table>

</logic:empty>
  </logic:present>
</fieldset>
		
	</logic:present>


	</div>

	
	<logic:present name="disbursalAuthor">
		<div id="centercolumn">

		<div id="revisedcontainer">

			<html:form action="/disbursalSearch" method="post"
				styleId="disbursalSearchForm">

				<fieldset>
					<legend>
						<bean:message key="lbl.disbursalInitiationAuthorSearch"/>
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanNo"/>
										</td>
										<td nowrap="nowrap">
										<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
											<html:text styleClass="text" property="loanNo"
												styleId="loanNo" value="" readonly="true" maxlength="20" tabindex="-1"/>
											<html:button property="loanNoButton" styleId="loanNoButton" value=" " 
											onclick="closeLovCallonLov1();openLOVCommon(67,'disbursalSearchForm','loanNo','userId','product', 'userId','','clearSchemeValue','customerName')" styleClass="lovbutton"> </html:button>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
								<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.customerName"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="customerName" value=""
												styleId="customerName" maxlength="50" />
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanAmt"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="loanAmt"
												styleId="loanAmt" value="" onkeypress="return numbersonly(event,id,18)" 
												onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
												onfocus="keyUpNumber(this.value, event, 18,id);" />
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanApprovalDate"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="loanApprovalDate" value=""
												styleId="loanApprovalDate" maxlength="5" onchange="checkDate('loanApprovalDate');"/>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.product"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="product"
												styleId="product" value="" maxlength="20" readonly="true"/>
											<html:button property="productButton" styleId="productButton" value=" " onclick="openLOVCommon(55,'disbursalSearchForm','product','loanNo','lbxProductID', 'lbxLoanNoHID','Select Loan No. First','','hid');closeLovCallonLov('loanNo');" styleClass="lovbutton"> </html:button>
								<html:hidden  property="lbxProductID" styleId="lbxProductID" value="" />
								<html:hidden  property="hid" styleId="hid" value="" />
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.scheme"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="scheme" value=""
												styleId="scheme" readonly="true" tabindex="-1"/>
											<html:button property="schemeButton" styleId="schemeButton" value=" " onclick="openLOVCommon(56,'disbursalSearchForm','scheme','loanNo','lbxscheme', 'lbxLoanNoHID','Select Loan No. First','','hid');closeLovCallonLov('loanNo');" styleClass="lovbutton"> </html:button>
								<html:hidden  property="lbxscheme" styleId="lbxscheme" value="" />
								<html:hidden  property="hid" styleId="hid" value="" />
										</td>
									</tr>
									
		<tr>
            <td>
      		<bean:message key="lbl.userName" />
      	    </td>
      	    <td>
            <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
            <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
     		<html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
     		<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
      		<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'disbursalSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
		</tr>
			<tr>
				<td><bean:message key="lbl.allBranch"/></td>
		    	<td><html:checkbox property="allBranches" styleClass="text"styleId="allBranches"/></td>
		    	<td><bean:message key="lbl.selectiveBranch"/></td>
		    	<td>
		            <html:text property="selectiveBranch" styleClass="text" styleId="selectiveBranch" maxlength="100" readonly="true" value=""/>
		     		<html:hidden property="lbxBranchId" styleClass="text" styleId="lbxBranchId" value=""/>
		      		<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(505,'CreditForm','lbxBranchId','','', '','','','selectiveBranch')" value=" " styleClass="lovbutton"  ></html:button>
				</td>
			</tr>						
									
									<tr>
										<td nowrap="nowrap" colspan="4">
											<!--  <html:button styleClass="topformbutton2" property="search" accesskey="S" title="Alt+S"
												styleId="search" value="Search" onclick="this.disabled=true;searchDisbursal('F');"/>-->
												<button type="button" class="topformbutton2" name="search"  id="search"  onclick="return searchDisbursal('F');"
 title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
										</td>
									</tr>
								</table>

							</td>
						</tr>

					</table>

				</fieldset>

			</html:form>
		</div>
		
	

		<fieldset>
			<legend>
				<bean:message key="lbl.disbursalInitiationAuthorRecords"/>
			</legend>


			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="gridtd">

						<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="disbursalSearchList">
    <tr class="white2">
	 
		<td><strong><bean:message key="lbl.loanNo"/></strong></td>
		<td><b><bean:message key="lbl.customerName"/></b></td>
		<td><strong><bean:message key="lbl.loanAmt"/></strong></td>
		<td><b><bean:message key="lbl.loanApprovalDate"/></b></td>
		<td><strong><bean:message key="lbl.product"/></strong></td>
		<td><b><bean:message key="lbl.scheme"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>
		
	</tr>
		<tr class="white2">
<td colspan="7">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
	 <logic:present name="disbursalSearchList">

<logic:notEmpty name="disbursalSearchList"> 
  <display:table  id="disbursalSearchList"  name="disbursalSearchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${disbursalSearchList[0].totalRecordSize}" requestURI="/disbursalSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="modifyNo" titleKey="lbl.loanNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="loanAmt" titleKey="lbl.loanAmt"  sortable="true"  />
	<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"/>
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"/>
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"/>
</display:table>
</logic:notEmpty>

<logic:empty name="disbursalSearchList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td><strong><bean:message key="lbl.loanNo"/></strong></td>
		<td><b><bean:message key="lbl.customerName"/></b></td>
		<td><strong><bean:message key="lbl.loanAmt"/></strong></td>
		<td><b><bean:message key="lbl.loanApprovalDate"/></b></td>
		<td><strong><bean:message key="lbl.product"/></strong></td>
		<td><b><bean:message key="lbl.scheme"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	<tr class="white2">
<td colspan="7">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
 </table>    </td>
</tr>

</table>

</logic:empty>
  </logic:present>

		</fieldset>
	</logic:present>


	</div>
	
</body>
</html:html>


<logic:present name="message">
<script type="text/javascript">
if('<%=request.getAttribute("message").toString()%>'=='S')
{
	alert('<bean:message key="msg.DataSaved" />');
}
else if('<%=request.getAttribute("message").toString()%>'=='E')
{
	alert('<bean:message key="msg.DataNotSaved" />');
}

 else if('<%=request.getAttribute("message")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
	
</script>
</logic:present>