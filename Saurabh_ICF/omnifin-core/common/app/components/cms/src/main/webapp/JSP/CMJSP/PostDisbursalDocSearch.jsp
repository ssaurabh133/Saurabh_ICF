<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.login.roleManager.UserObject"%>
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
		UserObject userobj=(UserObject)session.getAttribute("userobject");
					session.setAttribute("userId",userobj.getUserId());
					
	%>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
			
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>	   
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>


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
<body onload="enableAnchor();approvedOrDraft();document.getElementById('postDisbursalDocSearchForm').dealButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>	
	<logic:present name="postDisbursalDocMakerSearch">
		<div id="centercolumn">

			<div id="revisedcontainer">

				<html:form action="/postDisbursalDocSearch" method="post"
					styleId="postDisbursalDocSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.postDisbursalDocMakerSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="4">

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.dealNo" />
											</td>
											<td nowrap="nowrap">
											<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="dealNo"
													styleId="dealNo" value="" readonly="true" maxlength="20"
													tabindex="-1" />
									 			<html:button property="dealButton" styleId="dealButton"
													onclick="openLOVCommon(157,'postDisbursalDocSearchForm','dealNo','userId','dealNo', '','','clearLoanForDocument','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovbutton"></html:button>
												<html:button style="display:none" property="dealButton" styleId="dealButtonForDraftStatus"
													onclick="openLOVCommon(574,'postDisbursalDocSearchForm','dealNo','userId','dealNo', '','','clearLoanForDocument','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovbutton"></html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxDealNo" styleId="lbxDealNo"
													value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													value="" styleId="customerName" maxlength="50" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="" readonly="true" maxlength="20"
													tabindex="-1" />
												<html:button property="loanButton" styleId="loanButton"
													onclick="openLOVCommon(96,'postDisbursalDocSearchForm','loanNo','userId','loanNo', 'userId','','clearDealForDocument','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovbutton"></html:button>
												<html:button  style="display:none" property="loanButton" styleId="loanButtonForDraftStatus"
													onclick="openLOVCommon(575,'postDisbursalDocSearchForm','loanNo','userId','loanNo', 'userId','','clearDealForDocument','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovbutton"></html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											<td>
												<bean:message key="lbl.docStage" />
											</td>
											<td>

												<html:select property="docStage" styleId="docStage"
													styleClass="text" onchange="vaildateDocStage();"
													value="">
													<option value="">--<bean:message key="lbl.select" />--</option>
													<logic:present name="docStage">
														<html:optionsCollection name="docStage"
															label="stageDescription" value="stageValue" />
													</logic:present>
												</html:select>
											</td>
										</tr>
										<tr>
										<td>
										<bean:message key="lbl.searchByStatus" />
										</td>
										<td>
										   <html:select property="searchStatusPDD" styleClass="text"  styleId="searchStatusPDD" onchange="return approvedOrDraft();">
   										   <html:option value="A">Approved</html:option> 
   										   <html:option value="P">Draft</html:option>  
  										   </html:select>
										</td>
										</tr>
										<tr>
											<td nowrap="nowrap" colspan="4">
												<button type="button" class="topformbutton2" name="search"
													id="search" title="Alt+S" accesskey="S"
													onclick="return searchPostDisbursalDoc('P','<bean:message key="msg.dealLoanSelect"/>');" ><bean:message key="button.search" /></button>
													
											</td>
										</tr>
									</table>

								</td>
							</tr>

						</table>

					</fieldset>

				</html:form>
			</div>

					<!-- Grid For Document Maker -->
<logic:present name="postDisbursalSearchList">
				<fieldset>
					<legend><bean:message key="lbl.postDisbursalDocMakerRecords" />	</legend>
					
					<logic:present name="loan">
						<logic:notPresent name="postDisbursalSearchList">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="gridtd">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
											<tr class="white2">
												<td><strong><bean:message key="lbl.loanNo" /> </strong></td>
												<td><strong><bean:message key="lbl.customerName" /> </strong></td>
												<td><strong><bean:message key="lbl.product" /> </strong></td>
												<td><b><bean:message key="lbl.scheme" /> </b></td>
												<td><b><bean:message key="lbl.loanAmount" /> </b></td>
												<td><b><bean:message key="lbl.loanApprovalDate" /> </b></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</logic:notPresent>

						<logic:present name="postDisbursalSearchList">
							<logic:notEmpty name="postDisbursalSearchList">
								<display:table id="postDisbursalSearchList" name="postDisbursalSearchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${postDisbursalSearchList[0].totalRecordSize}" requestURI="/postDisbursalDocSearch.do">
									<display:setProperty name="paging.banner.placement" value="bottom" />
									<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
									<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
									<display:column property="loanNo" titleKey="lbl.loanNo" sortable="true" />
									<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
									<display:column property="product" titleKey="lbl.product" sortable="true" />
									<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
									<display:column property="loanAmt" titleKey="lbl.loanAmount" sortable="true" />
									<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate" sortable="true" />
								</display:table>
							</logic:notEmpty>
							<logic:empty name="postDisbursalSearchList">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="gridtd">
			
											<table width="100%" border="0" cellspacing="1" cellpadding="1">
												<tr class="white2">
													<td><strong><bean:message key="lbl.loanNo" /> </strong></td>
													<td><strong><bean:message key="lbl.customerName" /> </strong></td>
													<td><strong><bean:message key="lbl.product" /> </strong></td>
													<td><b><bean:message key="lbl.scheme" /> </b></td>
													<td><b><bean:message key="lbl.loanAmount" /> </b></td>
													<td><b><bean:message key="lbl.loanApprovalDate" /> </b></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<script type="text/javascript">
									alert("<bean:message key="lbl.noDataFound" />");
								</script>
							</logic:empty>
						</logic:present>
					</logic:present>
						
					<logic:present name="deal">
						<logic:notPresent name="postDisbursalSearchList">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="gridtd">
			
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
											<tr class="white2">
												<td><strong><bean:message key="lbl.dealNo" /> </strong></td>
												<td><strong><bean:message key="lbl.customerName" /> </strong></td>
												<td><strong><bean:message key="lbl.product" /> </strong></td>
												<td><b><bean:message key="lbl.scheme" /> </b></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
			</logic:notPresent>

			<logic:present name="postDisbursalSearchList">
				<logic:notEmpty name="postDisbursalSearchList">
					<display:table id="postDisbursalSearchList" name="postDisbursalSearchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${postDisbursalSearchList[0].totalRecordSize}" requestURI="/postDisbursalDocSearch.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="dealNo" titleKey="lbl.dealNo" sortable="true" />
						<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
						<display:column property="product" titleKey="lbl.product" sortable="true" />
						<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
					</display:table>
				</logic:notEmpty>
				<logic:empty name="postDisbursalSearchList">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td>
											<strong><bean:message key="lbl.dealNo" /> </strong>
										</td>
										<td>
											<strong><bean:message key="lbl.customerName" /> </strong>
										</td>
										<td>
											<strong><bean:message key="lbl.product" /> </strong>
										</td>
										<td>
											<b><bean:message key="lbl.scheme" /> </b>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<script type="text/javascript">
						alert("<bean:message key="lbl.noDataFound" />");
					</script>
				</logic:empty>
			</logic:present>		
		</logic:present>
	</fieldset>
</logic:present>
<logic:notPresent name="postDisbursalSearchList">
<logic:present name="dataExist">
	<script type="text/javascript">
	alert("<bean:message key="lbl.existInDraft" />");
	</script>
</logic:present>
</logic:notPresent>
</div>
</logic:present>


	<!-- Post disbursal Document Author Search -->

	<logic:present name="postDisbursalDocAuthorSearch">
		<div id="centercolumn">

			<div id="revisedcontainer">

				<html:form action="/postDisbursalDocSearch" method="post"
					styleId="postDisbursalDocSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.postDisbursalDocAuthorSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="4">

										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.dealNo" />
											</td>
											<td nowrap="nowrap">
											<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="dealNo"
													styleId="dealNo" value="" readonly="true" maxlength="20"
													tabindex="-1" />
												<html:button property="dealButton" styleId="dealButton"
													onclick="openLOVCommon(158,'postDisbursalDocSearchForm','dealNo','userId','dealNo', 'userId','','clearLoanForDocument','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovbutton"></html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxDealNo" styleId="lbxDealNo"
													value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													value="" styleId="customerName" maxlength="50" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.loanNo" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="" readonly="true" maxlength="20"
													tabindex="-1" />
												<html:button property="loanButton" styleId="loanButton"
													onclick="openLOVCommon(104,'postDisbursalDocSearchForm','loanNo','userId','loanNo', 'userId','','clearDealForDocument','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovbutton"></html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											<td>
												<bean:message key="lbl.docStage" />
											</td>
											<td>

												<html:select property="docStage" styleId="docStage"
													styleClass="text" onchange="vaildateDocStage();"
													value="">
													<option value="">--<bean:message key="lbl.select" />--</option>
													<logic:present name="docStage">
														<html:optionsCollection name="docStage"
															label="stageDescription" value="stageValue" />
													</logic:present>
												</html:select>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap" colspan="4">
												<button type="button" class="topformbutton2" name="search"
													id="search"  title="Alt+S" accesskey="S"
													onclick="return searchPostDisbursalDoc('F','<bean:message key="msg.dealLoanSelect"/>');" ><bean:message key="button.search" /></button>
													
											</td>
										</tr>
									</table>

								</td>
							</tr>

						</table>

					</fieldset>

				</html:form>
			</div>

			<logic:present name="postDisbursalSearchList">

				<fieldset>
					<legend>
						<bean:message key="lbl.postDisbursalDocAuthorRecords" />
					</legend>
					<logic:present name="loan">
						<logic:notPresent name="postDisbursalSearchList">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="gridtd">
			
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
											<tr class="white2">
												<td>
													<strong><bean:message key="lbl.loanNo" /> </strong>
												</td>
												<td>
													<strong><bean:message key="lbl.customerName" /> </strong>
												</td>
												<td>
													<strong><bean:message key="lbl.product" /> </strong>
												</td>
												<td>
													<b><bean:message key="lbl.scheme" /> </b>
												</td>
												<td>
													<b><bean:message key="lbl.loanAmount" /> </b>
												</td>
												<td>
													<b><bean:message key="lbl.loanApprovalDate" /> </b>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</logic:notPresent>

						<logic:present name="postDisbursalSearchList">
							<logic:notEmpty name="postDisbursalSearchList">
								<display:table id="postDisbursalSearchList" name="postDisbursalSearchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${postDisbursalSearchList[0].totalRecordSize}" requestURI="/postDisbursalDocSearch.do">
									<display:setProperty name="paging.banner.placement" value="bottom" />
									<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
									<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
									<display:column property="loanNo" titleKey="lbl.loanNo" sortable="true" />
									<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
									<display:column property="product" titleKey="lbl.product" sortable="true" />
									<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
									<display:column property="loanAmt" titleKey="lbl.loanAmount" sortable="true" />
									<display:column property="loanApprovalDate" titleKey="lbl.loanApprovalDate" sortable="true" />
								</display:table>
							</logic:notEmpty>
							<logic:empty name="postDisbursalSearchList">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="gridtd">
			
											<table width="100%" border="0" cellspacing="1" cellpadding="1">
												<tr class="white2">
													<td>
														<strong><bean:message key="lbl.loanNo" /> </strong>
													</td>
													<td>
														<strong><bean:message key="lbl.customerName" /> </strong>
													</td>
													<td>
														<strong><bean:message key="lbl.product" /> </strong>
													</td>
													<td>
														<b><bean:message key="lbl.scheme" /> </b>
													</td>
													<td>
														<b><bean:message key="lbl.loanAmount" /> </b>
													</td>
													<td>
														<b><bean:message key="lbl.loanApprovalDate" /> </b>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<script type="text/javascript">
									alert("<bean:message key="lbl.noDataFound" />");
								</script>
							</logic:empty>
						</logic:present>
					</logic:present>
						
					<logic:present name="deal">
						<logic:notPresent name="postDisbursalSearchList">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="gridtd">
			
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
											<tr class="white2">
												<td>
													<strong><bean:message key="lbl.dealNo" /> </strong>
												</td>
												<td>
													<strong><bean:message key="lbl.customerName" /> </strong>
												</td>
												<td>
													<strong><bean:message key="lbl.product" /> </strong>
												</td>
												<td>
													<b><bean:message key="lbl.scheme" /> </b>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
			</logic:notPresent>

			<logic:present name="postDisbursalSearchList">
				<logic:notEmpty name="postDisbursalSearchList">
					<display:table id="postDisbursalSearchList" name="postDisbursalSearchList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${postDisbursalSearchList[0].totalRecordSize}" requestURI="/postDisbursalDocSearch.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="dealNo" titleKey="lbl.dealNo" sortable="true" />
						<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
						<display:column property="product" titleKey="lbl.product" sortable="true" />
						<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
					</display:table>
				</logic:notEmpty>
				<logic:empty name="postDisbursalSearchList">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td>
											<strong><bean:message key="lbl.dealNo" /> </strong>
										</td>
										<td>
											<strong><bean:message key="lbl.customerName" /> </strong>
										</td>
										<td>
											<strong><bean:message key="lbl.product" /> </strong>
										</td>
										<td>
											<b><bean:message key="lbl.scheme" /> </b>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<script type="text/javascript">
						alert("<bean:message key="lbl.noDataFound" />");
					</script>
				</logic:empty>
			</logic:present>		
		</logic:present>
				</fieldset>
			</logic:present>
		</div>
	</logic:present>
</body>
</html:html>


<logic:present name="message">
	<script type="text/javascript">
if('<%=request.getAttribute("message").toString()%>' == 'N') {
		alert('<bean:message key="msg.DataNotFound"/>');
	}
	else if('<%=request.getAttribute("message")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	
	}
</script>
</logic:present>