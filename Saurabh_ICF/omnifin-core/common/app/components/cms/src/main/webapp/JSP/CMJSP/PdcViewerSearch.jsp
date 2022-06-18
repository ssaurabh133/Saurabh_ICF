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
	<html>
	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		

		<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/pdcViewer.js"></script>
		
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

	<body onload="enableAnchor();" onunload="closeAllLovCallUnloadBody();" oncontextmenu="return false">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/pdcViewerSearch" method="post" styleId="PdcViewerForm">
	
	<input type="hidden" name="contextPath" id="contextPath" value="<%=path %>" />

					<fieldset>
						<legend>
							<bean:message key="lbl.pdcViewer" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>

											<td width="20%">

													<bean:message key="lbl.loanNumber" /><font color="red">*</font>
												</td>
												<td>
													<input type="hidden" name="hCommon" id="hCommon" />
													<html:text property="loanNo" styleId="loanNo"
														styleClass="text" readonly="true" tabindex="-1" />
													<html:hidden property="lbxLoanNo" styleId="lbxLoanNo"
														 />

													<html:button property="loanButton" styleId="loanButton"
														onclick="openLOVCommon(318,'PdcViewerForm','loanNo','','lbxLoanNo', '','','calculatePDC','customer');"
														value=" " styleClass="lovbutton"></html:button>
														<input type="hidden" id="customer"/>


												</td>
												<td><bean:message key="lbl.endofdaystatus" /></td>
												<td><html:select property="status" styleId="status" styleClass="text">
												<html:option value="">--Select--</html:option>
													<html:option value="P">Pending</html:option>
													<html:option value="A">Approved</html:option>
													<html:option value="H">Hold</html:option>
													<html:option value="X">Delete</html:option>
													<html:option value="R">Released</html:option>
													<html:option value="B">Bounced</html:option>
													<html:option value="F">Forwarded</html:option>
													<html:option value="L">Cancel</html:option>
												</html:select></td>
										</tr>
										<tr>
											<td width="20%"><bean:message key="lbl.PDCPresented" /><font color="red">*</font></td>
											<td><html:text property="presented" styleId="presented" styleClass="text" readonly="true" tabindex="-1" value="${presented}" style="text-align: right"/></td>
											<td width="20%"><bean:message key="lbl.PDCtoPresented" /><font color="red">*</font></td>
											<td><html:text property="toBePresented" styleId="toBePresented" styleClass="text" readonly="true" tabindex="-1" value="${toBePresented}" style="text-align: right"/></td>	
									    </tr>
										
										<tr>
										<td>
										    <button type="button" name="Search"  id="Search" title="Alt+S" accesskey="S" 
							                class="topformbutton2" onclick="return searchPdcviewer();" ><bean:message key="button.search" /></button>
										</td>
										</tr>	

									</table>

									</fieldset>


								


										

						<logic:present name="pdcList">
							<fieldset>
										<legend>
											<bean:message key="lbl.pdcDetails" />
										</legend>
							<logic:notEmpty name="pdcList">
		<display:table id="pdcList" name="pdcList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${pdcList[0].totalRecordSize}" requestURI="/pdcViewerSearch.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				
						<display:column property="bptye" titleKey="address.bptype" sortable="true" />
						<display:column property="inst_mode" titleKey="lbl.installmentMode" sortable="true" />
						<display:column property="inst_no" titleKey="lbl.installmentNo" sortable="true" />
						<display:column property="purpose" titleKey="lbl.purpose" sortable="true" />
						<display:column property="bank_name" titleKey="lbl.bankName" sortable="true" />
						<display:column property="branch_name" titleKey="lbl.branchName" sortable="true" />
						<display:column property="micr_code" titleKey="lbl.micrCode" sortable="true" />
						<display:column property="ifsc_code" titleKey="lbl.ifscCode" sortable="true" />
						<display:column property="bank_ac" titleKey="lbl.bankAccount" sortable="true" />
						<display:column property="location" titleKey="lbl.location" sortable="true" />
						<display:column property="instr_no" titleKey="lbl.no" sortable="true" />
						<display:column property="instr_date" titleKey="lbl.instrumentDate" sortable="true" />
						<display:column property="instr_amt" titleKey="lbl.instrumetAmount" sortable="true" />
						<display:column property="totalEMI" titleKey="lbl.totalEMI" sortable="true" />
						<display:column property="clearingType" titleKey="lbl.clearingType" sortable="true" />
						<display:column property="submitBy" titleKey="lbl.submitBy" sortable="true" />
						<display:column property="pdcSubmitCustomerName" titleKey="lbl.nameOfPDCSubmitBy" sortable="true" />
						<display:column property="maker" titleKey="lbl.maker" sortable="true" />
						<display:column property="makerRemarks" titleKey="lbl.makerRemarks" sortable="true" />
						<display:column property="makerDate" titleKey="lbl.makerDate" sortable="true" />
						<display:column property="author" titleKey="lbl.author" sortable="true" />
						<display:column property="remarks" titleKey="lbl.authorRemarks" sortable="true" />
						<display:column property="authorDate" titleKey="lbl.authorDate" sortable="true" />
								</display:table>
							</logic:notEmpty>


							<logic:empty name="pdcList">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="gridtd">
											<table width="100%" border="0" cellspacing="1"
												cellpadding="1">
												<tr class="white2">
													
																<td>
																	<b><bean:message key="address.bptype" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.installmentMode" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.installmentNo" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.purpose" /> </b>
																</td>
															
																<td>
																	<b><bean:message key="lbl.bankName" />
																	</b>
																</td>
<td>
																	<b><bean:message key="lbl.branchName" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.micrCode" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.ifscCode" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.bankAccount" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.location" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.no" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.instrumentDate" /> </b>
																</td>
																
																<td>
																	<b><bean:message key="lbl.instrumetAmount" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.totalEMI" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.clearingType" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.submitBy" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.nameOfPDCSubmitBy" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.maker" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.makerRemarks" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.makerDate" /> </b>
																</td>
																<td>
																	<b><bean:message key="lbl.author" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.authorRemarks" />
																	</b>
																</td>
																<td>
																	<b><bean:message key="lbl.authorDate" /> </b>
																</td>
												</tr>
												<tr class="white2">
													<td colspan="23">
														<bean:message key="lbl.noDataFound" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>

							</logic:empty>
							</fieldset>
						</logic:present>
				</html:form>
  
</div>
<logic:present name="sms">
<script type="text/javascript">
//alert("hello");

	if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert('<bean:message key="msg.sanctionValid" />');
	}
	
	</script>
</logic:present>


</div>
</body>
</html>