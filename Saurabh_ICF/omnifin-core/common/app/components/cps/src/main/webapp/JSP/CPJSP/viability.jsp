<!--
 	Author Name      :- Abhimanyu kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for viability Data capturing  
 	Documentation    :- 
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/gcdScript/commonGcdFunctions.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/popup.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

</head>
<body oncontextmenu="return false"
	onunload="closeAllLovCallUnloadBody();"
	onload="enableAnchor();checkChanges('viabilityForm');document.getElementById('viabilityForm').vonroad.focus();getEMIAmount();"
	onclick="parent_disable();">
	<div align="center" class="opacity" style="display: none;"
		id="processingImage">
	</div>
	<div id="centercolumn">
		<div id="revisedcontainer">

			<fieldset>

				<table cellSpacing=0 cellPadding=0 width="100%" border=0>
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr class="white2">
									<td>
										<b><bean:message key="lbl.dealNo" /> </b>
									</td>
									<td>
										${dealHeader[0].dealNo}
									</td>
									<td>
										<b><bean:message key="lbl.date" /> </b>
									</td>
									<td>
										${dealHeader[0].dealDate}
									</td>
									<td>
										<b><bean:message key="lbl.customerName" /> </b>
									</td>
									<td colspan="3">
										${dealHeader[0].dealCustomerName}
									</td>
								</tr>
								<tr class="white2">
									<td>
										<b><bean:message key="lbl.productType" /> </b>
									</td>
									<td>
										${dealHeader[0].dealProductCat}
									</td>
									<td>
										<b><bean:message key="lbl.product" /> </b>
									</td>
									<td>
										${dealHeader[0].dealProduct}
									</td>
									<td>
										<b><bean:message key="lbl.scheme" /> </b>
									</td>
									<td>
										${dealHeader[0].dealScheme}
									</td>
									<td>
										<b><bean:message key="lbl.currentStage" /> </b>
									</td>
									<td>
										Fund Flow Analysis
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</fieldset>
			<html:form action="/viabilitySaveAction" method="post"
				styleId="viabilityForm">
				<input type="hidden" id="contextPath"
					value="<%=request.getContextPath()%>" />
				<input type="hidden" id="dealid"
					value="<%=session.getAttribute("dealId")%>" />
				<fieldset>
					<legend>
						<bean:message key="lbl.viabilitylegend" />
					</legend>
					<table cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td>
								<table cellspacing="1" cellpadding="1" width="100%" border="0">
									<tr>
										<td>
											<bean:message key="lbl.vibnod" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="vonroad"
													onkeypress="return isNumberKey(event);" onchange="calculateVib1();calculateVib5();calculateVib6();calculateVib15();calculateVib16();calculateVib18();" readonly="true"
													maxlength="10" styleId="vonroad" styleClass="text"
													value="${list[0].vonroad}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="vonroad" onchange="calculateVib1();calculateVib5();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeypress="return isNumberKey(event);" maxlength="10"
													styleId="vonroad" styleClass="text"
													value="${list[0].vonroad}" />
											</logic:notPresent>
										</td>
										<td>
											<bean:message key="lbl.vibnokperday" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="vrunday"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);" readonly="true"
													styleId="vrunday" styleClass="text"
													value="${list[0].vrunday}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="vrunday"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib5();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													styleId="vrunday" styleClass="text"
													value="${list[0].vrunday}" />

											</logic:notPresent>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.vibnokpermonth" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="vrunmonth"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="vrunmonth" styleClass="text" readonly="true"
													value="${list[0].vrunmonth}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="vrunmonth"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="vrunmonth" styleClass="text" readonly="true"
													value="${list[0].vrunmonth}" />
											</logic:notPresent>
										</td>
										<td>
											<bean:message key="lbl.vibrpk" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="rpkm" styleId="rpkm" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib5();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleClass="text" value="${list[0].rpkm}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="rpkm" styleId="rpkm"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib5();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleClass="text" value="${list[0].rpkm}" />
											</logic:notPresent>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.vibepd" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="epday" styleId="epday" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib6();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleClass="text" value="${list[0].epday}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="epday" styleId="epday"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib6();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleClass="text" value="${list[0].epday}" />
											</logic:notPresent>
										</td>
										<td>
											<bean:message key="lbl.vibepm" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="epermonth" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="epermonth" styleClass="text"
													value="${list[0].epermonth}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="epermonth" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="epermonth" styleClass="text"
													value="${list[0].epermonth}" />
											</logic:notPresent>
										</td>
									</tr>
									<tr>
										<td style="display: none;">
											<bean:message key="lbl.vibexpensemonthly" />
											<font color="red">*</font>
										</td>
										<td style="display: none;">
											<logic:present name="viewDeal">
												<html:text property="exmonthly" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="exmonthly" styleClass="text"
													value="${list[0].exmonthly}" />
											</logic:present>

											<logic:notPresent name="viewDeal">
												<html:text property="exmonthly"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="exmonthly" styleClass="text"
													value="0" />
											</logic:notPresent>
										</td>
										<td>
											<bean:message key="lbl.vibdriversalary" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="drsalary" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="drsalary" styleClass="text"
													value="${list[0].drsalary}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="drsalary"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="drsalary" styleClass="text"
													value="${list[0].drsalary}" />
											</logic:notPresent>
										</td>
									

										<td>
											<bean:message key="lbl.vibfulecost" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="fcost" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="fcost" styleClass="text" value="${list[0].fcost}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="fcost"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="fcost" styleClass="text" value="${list[0].fcost}" />
											</logic:notPresent>
										</td>
										</tr>
									<tr>
										<td>
											<bean:message key="lbl.vibtyrecost" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="tcost" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="tcost" styleClass="text" value="${list[0].tcost}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="tcost"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="tcost" styleClass="text" value="${list[0].tcost}" />
											</logic:notPresent>
										</td>
									
										<td>
											<bean:message key="lbl.vibpermittax" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="permittax" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="permittax" styleClass="text"
													value="${list[0].permittax}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="permittax"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="permittax" styleClass="text"
													value="${list[0].permittax}" />
											</logic:notPresent>

										</td>
										</tr>

									<tr>
										<td>
											<bean:message key="lbl.vibinsurence" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="insurance" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="insurance" styleClass="text"
													value="${list[0].insurance}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="insurance"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="insurance" styleClass="text"
													value="${list[0].insurance}" />
											</logic:notPresent>
										</td>
								
										<td>
											<bean:message key="lbl.vibmaintences" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="maintenance" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="maintenance" styleClass="text"
													value="${list[0].maintenance}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="maintenance"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="maintenance" styleClass="text"
													value="${list[0].maintenance}" />
											</logic:notPresent>

										</td>
											</tr>
									<tr>
										<td>
											<bean:message key="lbl.vibother" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="others" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="others" styleClass="text"
													value="${list[0].others}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="others"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="others" styleClass="text"
													value="${list[0].others}" />
											</logic:notPresent>
										</td>
								
										<td>
											<bean:message key="lbl.vibtotalex" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="toexpenses" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="toexpenses" styleClass="text"
													value="${list[0].toexpenses}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="toexpenses" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="toexpenses" styleClass="text"
													value="${list[0].toexpenses}" />
											</logic:notPresent>

										</td>
											</tr>
									<tr>
										<td>
											<bean:message key="lbl.vibnetearning" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="nearning" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="nearning" styleClass="text"
													value="${list[0].nearning}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="nearning" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="nearning" styleClass="text"
													value="${list[0].nearning}" />
											</logic:notPresent>
										</td>
								
										<td>
											<bean:message key="lbl.vibemipermnth" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="epmonth" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);calculateVib1();calculateVib6();calculateVib15();calculateVib16();calculateVib18();"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="epmonth" styleClass="text"
													value="${list[0].epmonth}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="epmonth" styleId="epmonth" styleClass="text" value="${list[0].epmonth}" readonly="true"/>
											</logic:notPresent>

										</td>
											</tr>
									<tr>
										<td>
											<bean:message key="lbl.vibnetsaving" />
											<font color="red">*</font>
										</td>
										<td>
											<logic:present name="viewDeal">
												<html:text property="nsaving" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="nsaving" styleClass="text"
													value="${list[0].nsaving}" />
											</logic:present>
											<logic:notPresent name="viewDeal">
												<html:text property="nsaving" readonly="true"
													onkeypress="return fourDecimalnumbersonly(event, id, 18)"
													onblur="twoDecimalNumber(this.value, id);"
													onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);"
													onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"
													styleId="nsaving" styleClass="text"
													value="${list[0].nsaving}" />
											</logic:notPresent>
										</td>
									</tr>
									<logic:present name="viewDeal">
										<tr>
											<td>
												<button style="display: none;" type="button"
													class="topformbutton2" id="Save" title="Alt+V"
													accesskey="V" onclick="saveViabilityDetail();">
													<bean:message key="button.save" />
												</button>
											</td>
										</tr>
									</logic:present>

									<logic:notPresent name="viewDeal">
										<tr>
											<td>
												<button type="button" class="topformbutton2" id="Save"
													title="Alt+V" accesskey="V"
													onclick="saveViabilityDetail();">
													<bean:message key="button.save" />
												</button>
											</td>
										</tr>
									</logic:notPresent>



								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</html:form>
</body>
<logic:present name="save">
	<script type="text/javascript">
		alert("<bean:message key="lbl.dataSave" />");
</script>
</logic:present>
<script type="text/javascript">
	setFramevalues("viabilityForm");
</script>
</html:html>


