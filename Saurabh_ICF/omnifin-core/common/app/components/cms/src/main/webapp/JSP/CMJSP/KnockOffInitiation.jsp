<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>

	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>

	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
	%>
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=path%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=path%>/js/cmScript/knockOffMaker.js"></script>



	<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('knockOffSearchForm').loanButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('knockOffSearchForm').loanButton1.focus();
     }
   
     return true;
}		
    </script>
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
<body onload="enableAnchor();fntab();" oncontextmenu="return false"
	onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
	<logic:present name="knockOffMakerSearch">
		<input type="hidden" id="modifyRecord" name="modifyRecord" value="M" />
		<div id="revisedcontainer">
			<html:form action="/knockOffSearch" styleId="knockOffSearchForm">

				<fieldset>
					<legend>
						<bean:message key="lbl.knockOffMakerSearch" />
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>

										<td>
											<bean:message key="lbl.loanNo" />
										</td>
										<td width="35%">
											<input type="hidden" name="userId" id="userId"
												value="${userobject.userId}" />
											<html:text styleClass="text" property="loanNo"
												readonly="true" styleId="loanNo" maxlength="20" value=""
												tabindex="-1" />
											<html:button property="loanButton" styleId="loanButton"
												onclick="openLOVCommon(112,'knockOffSearchForm','loanNo','userId','loanNo', 'userId','','','customerName');closeLovCallonLov1();"
												value=" " styleClass="lovbutton"></html:button>
											<input type="hidden" name="contextPath"
												value="<%=request.getContextPath()%>" id="contextPath" />
											<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
												value="" />
										</td>


										<td>
											<bean:message key="lbl.CustomerName" />
										</td>
										<td>
											<html:text property="customerName" styleId="customerName"
												readonly="" value="" styleClass="text" maxlength="50" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.businessPartnerType" />
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="businessPartnerType"
												styleId="businessPartnerType" style="" maxlength="10"
												readonly="true" value="" tabindex="-1" />
											<html:hidden property="lbxBPNID" styleId="lbxBPNID" value="" />
											<html:hidden  property="hBPType" styleId="hBPType" value="" />
											<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(45,'knockOffSearchForm','hBPType','loanNo', 'lbxBPNID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerType');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
										</td>


										<td nowrap="nowrap">
											<bean:message key="lbl.businessPartnerName" />
										</td>

										<td nowrap="nowrap">
											<html:text property="businessPartnerName"
												styleId="businessPartnerName" readonly="true" value=""
												styleClass="text" maxlength="50"
												tabindex="-1" />
											<html:hidden property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID"
												 value=""/>
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
									    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'knockOffSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
									 </td>
								    </tr>
															
									<tr>
										<td colspan="3">
											<button type="button" 
												onclick="return searchKnockOff('P','<bean:message key="msg.EnterAtleastOneValue" />');"
												class="topformbutton2" id="search" 
												accesskey="S" title="Alt+S" ><bean:message key="button.search" /></button>
											<button type="button" name="new"
												onclick="return openNewKnockOff();"
												class="topformbutton2" id="new" 
												accesskey="N" title="Alt+N" ><bean:message key="button.new" /></button>
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
				<bean:message key="lbl.knockOffMakerRecord" />
			</legend>

		<logic:present name="knockOffSearchList">
			<logic:notEmpty name="list">
				<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/knockOffSearchBehindAction.do?method=knockOffMakerSearch" >
    				<display:setProperty name="paging.banner.placement"  value="bottom"/>
    				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				    <display:column property="loanNo" titleKey="lbl.loanNo"  sortable="true"  />
				    <display:column property="customerName" titleKey="lbl.CustomerName"  sortable="true"  />
					<display:column property="businessPartnerType" titleKey="lbl.businessPartnerType"  sortable="true"  />
					<display:column property="businessPartnerName" titleKey="lbl.businessPartnerName"  sortable="true"  />
					<display:column property="knockOffAmount" titleKey="lbl.knockOffAmount"  sortable="true"  />
					<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
				</display:table>				
			</logic:notEmpty>
				<logic:empty name="list">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td>
											<strong><bean:message key="lbl.loanNo" /> </strong>
										</td>
										<td>
											<strong><bean:message key="lbl.CustomerName" /> </strong>
										</td>
										<td>
											<strong> <bean:message key="lbl.businessPartnerType" />
											</strong>
										</td>
										<td>
											<strong><bean:message key="lbl.businessPartnerName" />
											</strong>
										</td>
										<td>
											<strong><bean:message key="lbl.knockOffAmount" />
											</strong>
										</td>
										<td>
											<strong><bean:message key="lbl.userName" />
											</strong>
										</td>
									</tr>
						<tr class="white2">
						<td colspan="7"> 
						<bean:message key="lbl.noDataFound" />
						</td>
						</tr>
								</table>
							</td>
						</tr>
				
					</table>
				</logic:empty>
			</logic:present>

		</fieldset>

	</logic:present>





	<!-- ------------------------Knock off Author Search--------------- -->



	<logic:present name="knockOffAuthorSearch">
		<input type="hidden" id="modifyRecord" name="modifyRecord" value="I" />
		<div id="revisedcontainer">
			<html:form action="/knockOffSearch" styleId="knockOffSearchForm">

				<fieldset>
					<legend>
						<bean:message key="lbl.knockOffAuthorSearch" />
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>

										<td>
											<bean:message key="lbl.loanNo" />
										</td>
										<td width="35%">
											<input type="hidden" name="userId" id="userId"
												value="${userobject.userId}" />
											<html:text styleClass="text" property="loanNo"
												readonly="true" styleId="loanNo" maxlength="20" value="" />
											<html:button property="loanButton1" styleId="loanButton1"
												onclick="openLOVCommon(113,'knockOffSearchForm','loanNo','userId','loanNo', 'userId','','','customerName');closeLovCallonLov1();"
												value=" " styleClass="lovbutton"></html:button>
											<input type="hidden" name="contextPath"
												value="<%=request.getContextPath()%>" id="contextPath" />
											<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
												value="" />
											<input type="hidden" name="hCommon" id="hCommon" value="" />
										</td>


										<td>
											<bean:message key="lbl.CustomerName" />
										</td>
										<td>
											<html:text property="customerName" styleId="customerName"
												readonly="" value="" styleClass="text" maxlength="50" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.businessPartnerType" />
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="businessPartnerType"
												styleId="businessPartnerType" style="" maxlength="10"
												readonly="true" value="" tabindex="-1" />
											<html:hidden property="lbxBPNID" styleId="lbxBPNID" value="" />
											<html:hidden  property="hBPType" styleId="hBPType" value="" />
											<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(45,'knockOffSearchForm','hBPType','loanNo', 'lbxBPNID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerType');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
										</td>


										<td nowrap="nowrap">
											<bean:message key="lbl.businessPartnerName" />
										</td>

										<td nowrap="nowrap">
											<html:text property="businessPartnerName"
												styleId="businessPartnerName" readonly="true" value=""
												styleClass="text" maxlength="50"
												tabindex="-1" />
											<html:hidden property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID"
												 value=""/>
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
										    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'knockOffSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
										 </td>
								    </tr>

									<tr>
										<td colspan="3">
											<button type="button" name="search"
												onclick="return searchKnockOff('F','<bean:message key="msg.EnterAtleastOneValue" />');"
												class="topformbutton2" id="search" 
												accesskey="S" title="Alt+S" ><bean:message key="button.search" /></button>
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
				<bean:message key="lbl.knockOffAuthorRecord" />
			</legend>

		<logic:present name="knockOffSearchList">
			<logic:notEmpty name="list">
				<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/knockOffSearchBehindAction.do?method=knockOffAuthorSearch" >
    				<display:setProperty name="paging.banner.placement"  value="bottom"/>
    				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				    <display:column property="loanNo" titleKey="lbl.loanNo"  sortable="true"  />
				    <display:column property="customerName" titleKey="lbl.CustomerName"  sortable="true"  />
					<display:column property="businessPartnerType" titleKey="lbl.businessPartnerType"  sortable="true"  />
					<display:column property="businessPartnerName" titleKey="lbl.businessPartnerName"  sortable="true"  />
					<display:column property="knockOffAmount" titleKey="lbl.knockOffAmount"  sortable="true"  />
					<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
				</display:table>				
			</logic:notEmpty>
				<logic:empty name="list">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td>
											<strong><bean:message key="lbl.loanNo" /> </strong>
										</td>
										<td>
											<strong><bean:message key="lbl.CustomerName" /> </strong>
										</td>
										<td>
											<strong> <bean:message key="lbl.businessPartnerType" />
											</strong>
										</td>
										<td>
											<strong><bean:message key="lbl.businessPartnerName" />
											</strong>
										</td>
										<td>
											<strong><bean:message key="lbl.knockOffAmount" />
											</strong>
										</td>
										<td>
											<strong><bean:message key="lbl.userName" />
											</strong>
										</td>
									</tr>
											<tr class="white2">
							<td colspan="7"> 
							<bean:message key="lbl.noDataFound" />
							</td>
							</tr>
								</table>
							</td>
						</tr>
					
					</table>
				</logic:empty>
			</logic:present>

		</fieldset>

	</logic:present>


</body>
</html:html>

<logic:present name="sms">
	<script type="text/javascript">	
		if('<%=request.getAttribute("sms").toString()%>'=='S')
		{
			alert('<bean:message key="msg.DataSaved" />');
		}
		else if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
			alert('<bean:message key="msg.DataNotSaved" />');
		}
		else if('<%=request.getAttribute("sms")%>'=='Locked')
	    {
		   alert('<%=request.getAttribute("recordId")%>
	' + ' ' + '<bean:message key="lbl.recordIsInUse" />');
	}
</script>
</logic:present>