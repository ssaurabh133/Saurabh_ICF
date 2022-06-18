<%--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Individual Details
 	Documentation    :- 
 --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.connect.CommonFunction"%>
<%@page import="com.gcd.VO.ShowCustomerDetailVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" import="java.util.Calendar"%>
<%@ page language="java"
	import="java.text.SimpleDateFormat,java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp"%>
<%@include file="/JSP/commonIncludeContent.jsp"%>
<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/popup.js"></script>

<!-- css and jquery for Datepicker -->

<link type="text/css"
	href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

<!-- css and jquery for Datepicker -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gcdScript/customer_address.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gcdScript/search_customer_detail.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gcdScript/commonGcdFunctions.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gcdScript/individual.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<%
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");

	String dateFormat = resource.getString("lbl.dateForDisbursal");
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	String sysDate = formatter.format(currentDate.getTime());
	request.setAttribute("sysDate", sysDate);
%>

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

<!-- Virender Start -->
<script type="text/javascript">
function onPanChange(){


    var pan = document.getElementById("pan").value;
    var proofOfIdentityDocument=document.getElementById("proofOfIdentityDocument").value;

    var proofOfIdentityDocumentNo=document.getElementById("proofOfIdentityDocumentNo").value;
    if(proofOfIdentityDocument=='PAN')
        {
 document.getElementById("proofOfIdentityDocumentNo").value=pan;
        }

}
function onUidChange(){


    var aadhaar = document.getElementById("aadhaar").value;
    var proofOfAddressDocument=document.getElementById("proofOfAddressDocument").value;

    var proofOfAddressDocumentNo=document.getElementById("proofOfAddressDocumentNo").value;
    if(proofOfAddressDocument=='UID')
        {
 document.getElementById("proofOfAddressDocumentNo").value=aadhaar;
        }

}
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
function panEna()
{
	var pan = document.getElementById("pan").value;
	document.getElementById("pan").removeAttribute("disabled","false");
}
</script>
<!-- Virender Code ENd -->


</head>

<body oncontextmenu="return false" onclick="parent_disable();"
	onload="panEna();groupSelectInMasterNew();calculateAgeOnLoad();hideShowManFieldsForIndividual();enableAnchor();checkChanges('individualDetailForm');document.getElementById('individualDetailForm').firstName.focus();">
	<div align="center" class="opacity" style="display: none"
		id="processingImage"></div>
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="source" id="source" value="${source}" />

	<logic:present name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/images/theme1/calendar.gif' />
	</logic:notPresent>
	<input type="hidden" id="businessdate"
		value='${userobject.businessdate}' />

	<input type="hidden" id="contextPath" name="contextPath"
		value="<%=request.getContextPath()%>" />
	<input type="hidden" id="emailMandatoryFlag" name="emailMandatoryFlag"
		value='${emailMandatoryFlag}' />
	<input type="hidden" name="hcommon" id="hcommon" />
	<html:hidden property="bypassDedupe" styleId="bypassDedupe" value="" />
	<logic:notPresent name="approve">

		<center>
			<font color="#FF0000">${requestScope.status}</font>
		</center>
		<div id="centercolumn">
			<div id="revisedcontainer">
				<html:form action="/idividualDetailActionPage"
					styleId="individualDetailForm" method="post">

					<fieldset>
						<legend>
							<bean:message key="individual.details" />
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
							<tr>
								<td>
									<table cellspacing=1 cellpadding=2 width="100%" border=0>
										<logic:present name="individualInfo">


											<tr>

												<td><bean:message key="individual.code" /><span></span>
												</td>
												<td><html:text property="corporateCode"
														styleId="corporateCode" styleClass="text"
														value="${individualInfo[0].corporateCode}" readonly="true"
														tabindex="-1" /></td>
														
														<td><bean:message key="lbl.UCIC" /><span></span>
												</td>
												<td><html:text property="ucic"
														styleId="ucic" styleClass="text"
														value="${individualInfo[0].ucic}" readonly="true"
														tabindex="-1" /></td>
											</tr>

										</logic:present>
										<!-- amandeep -->
										<!-- amandeep starts -->

										<logic:present name="applType">

											<logic:present name="groupTypeActivated">


												<td><bean:message key="lbl.groupType" /></td>
												<td><html:select property="groupType"
														styleId="groupType" onchange="groupSelectInMasterNew();"
														styleClass="text" value="${individualInfo[0].groupType}">
														<html:option value="N">New</html:option>
														<html:option value="E">Existing</html:option>
													</html:select></td>

												<td><bean:message key="lbl.groupName" /></td>
												<td>
													<div id="groupLov" style="display: none">
														<input type="text" id="groupName" name="groupName"
															class="text" value="${individualInfo[0].groupName}"
															readonly="readonly" tabindex="-1" />
														<html:button property="lovButton" value=" "
															styleClass="lovbutton" styleId="groupButton"
															onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
														<html:hidden property="hGroupId" styleId="hGroupId"
															styleClass="text" value="${individualInfo[0].hGroupId}" />

													</div>

													<div id="groupText">
														<input type="text" id="groupNameText" name="groupName"
															class="text" value="${individualInfo[0].groupName}"
															maxlength="50" />


													</div>

												</td>

											</logic:present>

										</logic:present>

										<logic:notPresent name="groupTypeActivated">

											<html:hidden property="groupType" styleId="groupType"
												styleClass="text" value="E" />
											<td><bean:message key="lbl.groupName" /></td>
											<td><input type="text" id="groupName" name="groupName"
												class="text" value="${individualInfo[0].groupName}"
												readonly="readonly" tabindex="-1" /> <html:button
													property="lovButton" value=" " styleClass="lovbutton"
													styleId="groupButton"
													onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
												<html:hidden property="hGroupId" styleId="hGroupId"
													styleClass="text" value="${individualInfo[0].hGroupId}" />
											</td>
										</logic:notPresent>








										<!-- amandeep -->

										<tr>
											<td><bean:message key="lbl.customerNamePrifx" /><font
												color="red">*</font></td>
											<td><html:select property="customerNamePrifx"
													styleId="customerNamePrifx" styleClass="text"
													value="${individualInfo[0].customerNamePrifx}">
													<html:option value="">-- Select --</html:option>
													<html:option value="MR">MR</html:option>
													<html:option value="MISS">MISS</html:option>
													<html:option value="MRS">MRS</html:option>
													<html:option value="DR">DR</html:option>
												</html:select></td>

											<td><bean:message key="lbl.citizenship" /></td>
											<td><html:text property="citizenship" styleClass="text"
													styleId="citizenship"
													value="${individualInfo[0].citizenship}" maxlength="2" /></td>
										</tr>
										<tr>
											<td><input type="hidden" name="pagestatus"
												value="Individual" />
											<bean:message key="individual.name" /><font color="red">*</font></td>
											<td><html:text property="firstName" styleId="firstName"
													styleClass="text" value="${individualInfo[0].firstName}"
													onchange="return upperMe('firstName');" maxlength="50" /></td>
											<td><bean:message key="individual.middle" /></td>
											<td><html:text property="middleName"
													styleId="middleName" styleClass="text"
													value="${individualInfo[0].middleName}"
													onchange="return upperMe('middleName');" maxlength="50" /></td>
										</tr>
										<tr>
											<td><bean:message key="individual.last" /><font
												color="red">*</font></td>
											<td><html:text property="lastName" styleId="lastName"
													styleClass="text" value="${individualInfo[0].lastName}"
													onchange="return upperMe('lastName');" maxlength="50" /></td>

											<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
											<td><html:select property="genderIndividual"
													value="${individualInfo[0].genderIndividual}"
													styleClass="text" styleId="genderIndividual">
													<option value="">-- Select --</option>
													<logic:present name="GenderCategory">
														<logic:notEmpty name="GenderCategory">
															<html:optionsCollection name="GenderCategory"
																label="genderDesc" value="genderCode" />
														</logic:notEmpty>
													</logic:present>
												</html:select></td>
										</tr>
										<tr>
											<td><bean:message key="individual.category" /><font
												color="red">*</font></td>
											</td>
											<td><html:select property="corporateCategory"
													value="${individualInfo[0].corporateCategory}"
													styleClass="text" styleId="corporateCategory">
													<option value="">-- Select --</option>
													<logic:present name="individualCategory">
														<logic:notEmpty name="individualCategory">
															<html:optionsCollection name="individualCategory"
																label="customerCategoryDesc"
																value="customerCategoryCode" />
														</logic:notEmpty>
													</logic:present>
												</html:select></td>

											<td><bean:message key="constitution" /><font
												color="red">*</font></td>
											<td><html:select property="constitution"
													styleId="constitution"
													value="${individualInfo[0].constitution}" styleClass="text">
													<option value="">--Select--</option>
													<logic:present name="constitutionlist">
														<logic:notEmpty name="constitutionlist">
															<html:optionsCollection name="constitutionlist"
																label="constitution" value="contitutionCode" />
														</logic:notEmpty>
													</logic:present>
												</html:select></td>
										</tr>
										<tr>
											<td><bean:message key="fatherHusband" /><font
												color="red">*</font></td>
											<td><html:text property="fatherHusband"
													styleId="fatherHusband" styleClass="text"
													value="${individualInfo[0].fatherHusband}"
													onchange="return upperMe('fatherHusband');" maxlength="50" /></td>


											<td><bean:message key="individual.birthdate" />
												<bean:message key="lbl.dateFormat(dd-mm-yyyy)" /><font
												color="red">*</font></td>
											<td><html:text property="incorporationDate"
													styleId="incorporationDate" styleClass="text"
													value="${individualInfo[0].incorporationDate}"
													onchange="calculateAge();" /></td>
											<input type="hidden" name="sysDate" id="sysDate"
												value="${userobject.businessdate}" />
											<input type="hidden" name="approve" id="approve" value="N" />

										</tr>
										<tr>
											<td><bean:message key="currentAge" /></td>
											<td><html:text property="currentAge"
													styleId="currentAge" styleClass="text" readonly="true" /></td>
											<input type="hidden" name="hiApplType" id="hiApplType"
												value="<%=session.getAttribute("applType")%>" />


											<td><bean:message key="casteCategory" /></td>
											<td><html:select property="casteCategory"
													value="${individualInfo[0].casteCategory}"
													styleClass="text" styleId="casteCategory">
													<option value="">-- Select --</option>
													<logic:present name="CastCategory">
														<logic:notEmpty name="CastCategory">
															<html:optionsCollection name="CastCategory"
																label="castCategoryDesc" value="castCategoryCode" />
														</logic:notEmpty>
													</logic:present>
												</html:select></td>

										</tr>

										<tr>
											<td><bean:message key="maritalStatus" /><font
												color="red">*</font></td>
											<td><html:select property="maritalStatus"
													styleId="maritalStatus" styleClass="text"
													value="${individualInfo[0].maritalStatus}">
													<html:option value="">-- Select --</html:option>
													<html:option value="M">Married</html:option>
													<html:option value="U">Un-Married</html:option>
													<html:option value="O">OTHER</html:option>
												</html:select></td>

											<logic:equal name="emailMandatoryFlag" value="Y">
												<td><bean:message key="individual.email" /><font
													color="red">*</font></td>
											</logic:equal>
											<logic:notEqual name="emailMandatoryFlag" value="Y">
												<td><bean:message key="individual.email" /></td>
											</logic:notEqual>
											<td><html:text property="institutionEmail"
													styleId="institutionEmail" styleClass="text"
													value="${individualInfo[0].institutionEmail}"
													maxlength="100" />
													<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="EmailVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal>
													</td>
										</tr>

										<tr>

											<td><bean:message key="industry" /></td>
											<td><input type="text" name="industry" id="industry"
												size="20" value="${individualInfo[0].industry}" class="text"
												readonly="readonly" tabindex="-1" /> <logic:present
													name="strParentOption">
													<input type="hidden" name="lbxIndustry" id="lbxIndustry"
														class="text" value="${sessionScope.strParentOption}" />
												</logic:present> <logic:notPresent name="strParentOption">
													<input type="hidden" name="lbxIndustry" id="lbxIndustry"
														class="text" value="${individualInfo[0].lbxIndustry}" />
												</logic:notPresent> <html:button property="lovButton" value=" "
													styleClass="lovbutton" styleId="industryButton"
													onclick="closeLovCallonLov1();openLOVCommon(10,'individualDetailForm','industry','','subIndustry', 'lbxSubIndustry','','','hcommon');" />
											</td>

											<td><bean:message key="subIndustry" /></td>
											<td>
												<div id="subIndustrydetail">

													<input type="text" name="subIndustry" id="subIndustry"
														size="20" class="text" readonly="readonly"
														value="${individualInfo[0].subIndustry}" tabindex="-1" />
													<input type="hidden" name="lbxSubIndustry"
														id="lbxSubIndustry" class="text"
														value="${individualInfo[0].lbxSubIndustry}" />
													<html:button property="lovButton" value=" "
														styleClass="lovbutton" styleId="subIndustryButton"
														onclick="closeLovCallonLov('industry');openLOVCommon(11,'individualDetailForm','subIndustry','industry','lbxSubIndustry', 'lbxIndustry','Please select Industry First!!!','','hcommon');" />
												</div>

											</td>
										</tr>
										<tr>
											<logic:present name="viewBlackList">
												<td><bean:message key="individual.blackListed" /></td>
												<td><logic:iterate name="individualInfo"
														id="individualInfoList">
														<logic:equal name="individualInfoList"
															property="blackListed" value="N">
															<html:checkbox property="blackListed"
																styleId="blackListed" onclick="blacklist();" />
														</logic:equal>
														<logic:equal name="individualInfoList"
															property="blackListed" value="Y">
															<input type="checkbox" name="blackListed"
																id="blackListed" checked="checked"
																onclick="blacklist();" />
														</logic:equal>
													</logic:iterate></td>
											</logic:present>


											<logic:present name="viewBlackList">
												<td><bean:message
														key="individual.reasonForBlackListing" /></td>
												<td><logic:iterate name="individualInfo"
														id="individualInfoList">
														<logic:equal name="individualInfoList"
															property="blackListed" value="N">
															<input type="hidden" id="reason" name="reason" style=""
																value="" readonly="readonly" />
															<html:text property="reasonForBlackListed"
																styleId="reasonForBlackListed" value="" maxlength="100"
																styleClass="text" readonly="true" tabindex="-1" />
															<html:button
																onclick="return openLOVCommon(138,'individualDetailForm','reasonForBlackListed','','', '','','','reason');"
																property="reasonButton" styleId="reasonButton"
																styleClass="lovbutton" value=" "></html:button>
														</logic:equal>
														<logic:equal name="individualInfoList"
															property="blackListed" value="Y">

															<input type="hidden" id="reason" name="reason" style=""
																value="" readonly="readonly" />
															<html:text property="reasonForBlackListed"
																styleId="reasonForBlackListed" maxlength="100"
																styleClass="text"
																value="${individualInfo[0].reasonForBlackListed}"
																readonly="true" />
															<html:button property="reasonButton"
																styleId="reasonButton" styleClass="lovbutton" value=""
																onclick="return openLOVCommon(138,'individualDetailForm','reasonForBlackListed','','', '','','','reason');"></html:button>
														</logic:equal>
													</logic:iterate></td>
											</logic:present>
											<logic:present name="deal">
												<logic:notPresent name="type">
													<td><bean:message key="lbl.relationshipWithCust" /></td>
													<td><html:text property="relationShip"
															styleId="relationShip" styleClass="text"
															value="${individualInfo[0].relationShip}" maxlength="50" /></td>
												</logic:notPresent>
											</logic:present>

											<td><input type="hidden" name="customerStatus"
												value="${individualInfo[0].pagestatus}" /></td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td><bean:message key="individual.pan" /><font color="red">*</font></td>
											<td><html:text property="pan" styleClass="text"
													styleId="pan" styleClass="text"
													onkeypress="return isAlphNumericKey(event);"
													onchange="return upperMe('pan');"
													onkeyup="onPanChange()"
													value="${individualInfo[0].pan}" maxlength="10" /></td>

											<td><bean:message key="aadhaar" /></td>
											<td><html:text property="aadhaar" styleClass="text"
													styleId="aadhaar" onkeypress="return isNumber(event);"
													onkeyup="onUidChange();" readonly="true"
													value="${individualInfo[0].aadhaar}" maxlength="12" /></td>
										</tr>
										<!-- Abhishek Start For CKYC -->
										<tr>
											<td><bean:message key="lbl.education" /></td>
											<td><html:select styleId="eduDetailInd"
													property="eduDetailInd" styleClass="text"
													value="${individualInfo[0].eduDetailInd}">
													<html:option value="">---Select---</html:option>
													<logic:present name="eduDetail">
														<logic:notEmpty name="eduDetail">
															<html:optionsCollection name="eduDetail"
																label="eduDetailDesc" value="eduDetailCode" />
														</logic:notEmpty>
													</logic:present>
												</html:select></td>

											<%-- <td><bean:message key="lbl.motherPrifx" /><font color="red">*</font></td>
										       <td>
										       <html:select property="motherPrifx" styleId="motherPrifx" styleClass="text" value="${individualInfo[0].motherPrifx}">
											   <html:option value="Mrs">MRS</html:option>
											   </html:select>
											   </td> --%>

											<td><bean:message key="lbl.motherFName" /><font
												color="red">*</font></td>
											<td><html:text property="motherFName" styleClass="text"
													styleId="motherFName"
													value="${individualInfo[0].motherFName}" maxlength="50" /></td>
										</tr>

										<tr>
											<td><bean:message key="lbl.motherMName" /></td>
											<td><html:text property="motherMName"
													styleId="motherMName" styleClass="text"
													value="${individualInfo[0].motherMName}" maxlength="50" /></td>

											<td><bean:message key="lbl.motherLName" /><font
												color="red">*</font></td>
											<td><html:text property="motherLName"
													styleId="motherLName" styleClass="text"
													value="${individualInfo[0].motherLName}" maxlength="50" /></td>
										</tr>

										<%--   <tr>
										   <td><bean:message key="lbl.maritalStatus" /></td>
									       <td>
									       <html:select property="maritalStatus" styleId="maritalStatus" styleClass="text" value="${individualInfo[0].maritalStatus}">
										   <html:option value="">--Select--</html:option>
										   <html:option value="M">MARRIED</html:option>
										   <html:option value="U">UN MARRIED</html:option>
										   <html:option value="O">OTHER</html:option>
										   </html:select>
										   </td>
																				
								           <td><bean:message key="lbl.citizenship" /></td>
								           <td><html:text property="citizenship" styleClass="text" styleId="citizenship" value="${individualInfo[0].citizenship}" maxlength="2"/></td>
								       </tr> --%>

										<tr>
											<td><bean:message key="lbl.residentialStatus" /><font
												color="red">*</font></td>
											<td><html:select property="residentialStatus"
													styleId="residentialStatus" styleClass="text"
													value="${individualInfo[0].residentialStatus}">
													<html:option value="">--Select--</html:option>
													<html:option value="RI">RESIDENT INDIVIDUAL</html:option>
													<html:option value="NRI">NON RESIDENT INDIAN</html:option>
													<html:option value="FN">FOREIGN NATIONAL</html:option>
													<html:option value="PIO">PERSON OF INDIAN ORIGIN</html:option>
												</html:select></td>

											<td><bean:message key="lbl.occpation" /><font
												color="red">*</font></td>
											<td><html:select property="occpation"
													styleId="occpation" styleClass="text"
													value="${individualInfo[0].occpation}">
													<html:option value="">--Select--</html:option>
													<html:option value="S-02">SERVICE PRIVATE SECTOR</html:option>
													<html:option value="S-01">SERVICE PUBLIC SECTOR</html:option>
													<html:option value="S-03">SERVICE GOVERNMENT SECTOR</html:option>
													<html:option value="O-01">OTHERS PROFESSIONAL</html:option>
													<html:option value="O-02">OTHER SEFL EMPLOYED</html:option>
													<html:option value="O-03">OTHER RETIRED</html:option>
													<html:option value="O-04">OTHER HOUSE WIFE</html:option>
													<html:option value="O-05">OTHER STUDENT</html:option>
													<html:option value="B-01">BUSINESS</html:option>
													<html:option value="X-01">NOT CATEGORISED</html:option>
												</html:select></td>
										</tr>

										<tr>

											<td><bean:message key="lbl.pesTaxPrsJuriOutSideInd" /></td>
											<td><html:select property="pesTaxPrsJuriOutSideInd"
													styleId="pesTaxPrsJuriOutSideInd" styleClass="text"
													value="${individualInfo[0].pesTaxPrsJuriOutSideInd}">
													<html:option value="">--Select--</html:option>
													<html:option value="Y">Yes</html:option>
													<html:option value="N">No</html:option>
												</html:select></td>

											<td><bean:message key="lbl.accountType" /></td>
											<td><html:select property="accountType"
													styleId="accountType" styleClass="text"
													value="${individualInfo[0].accountType}">
													<html:option value="">--Select--</html:option>
													<html:option value="NORMAL">NORMAL</html:option>
													<html:option value="SIMPLIFIED ID">SIMPLIFIED ID</html:option>
													<html:option value="SMALL">SMALL</html:option>
													<html:option value="E-KYC">E-KYC</html:option>
												</html:select></td>
										</tr>

										<%-- <tr>
										  <td><bean:message key="lbl.prfIdentityDocNo" /></td>
								          <td><html:text property="prfIdentityDocNo" styleClass="text" styleId="prfIdentityDocNo" value="${individualInfo[0].prfIdentityDocNo}" maxlength="20"/></td>
																				
								          <td><bean:message key="lbl.prfIdentityDocExpDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
         								  <td><html:text property="prfIdentityDocExpDate" styleId="prfIdentityDocExpDate" styleClass="text" value="${individualInfo[0].prfIdentityDocExpDate}" /></td> 
								       </tr>
								       
								       <tr>
										  <td><bean:message key="lbl.prfAddressDocNo" /></td>
								          <td><html:text property="prfAddressDocNo" styleClass="text" styleId="prfAddressDocNo" value="${individualInfo[0].prfAddressDocNo}" maxlength="50"/></td>
																				
								          <td><bean:message key="lbl.prfAddressDocExpDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
         								  <td><html:text property="prfAddressDocExpDate" styleId="prfAddressDocExpDate" styleClass="text" value="${individualInfo[0].prfAddressDocExpDate}" /></td> 
								       </tr> --%>


										<%-- <tr>
									   	 <td><bean:message key="lbl.accountType" /></td>
									       <td>
									       <html:select property="accountType" styleId="accountType" styleClass="text" value="${individualInfo[0].accountType}">
									       <html:option value="">--Select--</html:option>
										   <html:option value="NORMAL">NORMAL</html:option>
										   <html:option value="SIMPLIFIED ID">SIMPLIFIED ID</html:option>
										   <html:option value="SMALL">SMALL</html:option>
										   <html:option value="E-KYC">E-KYC</html:option>
										   </html:select>
										   </td>
								       </tr> --%>

										<tr>
											<td><bean:message key="lbl.proofOfIdentityDocument" /><font
												color="red">*</font></td>
											<td><html:select property="proofOfIdentityDocument"
													styleId="proofOfIdentityDocument" styleClass="text"
													onchange="enableCkycDoc('Identity');"
													onchange="panUIDFill('Identity');"
													onblur="proofAddressValid();"
													value="${individualInfo[0].proofOfIdentityDocument}">
													<html:option value="">--Select--</html:option>
													<html:option value="PP">PASSPORT</html:option>
													<html:option value="VIC">VOTER ID</html:option>
													<<html:option value="PAN">PAN CARD</html:option>
										    
													<html:option value="DL">DRIVING LICENCE</html:option>
													<html:option value="UID">UID ADHAAR</html:option>
										 
													<html:option value="NREGA">NREGA JOB CARD</html:option>
													<html:option value="OT">OTHER</html:option>
													<html:option value="UB2">Identity card with applicant photograph issued by Central State Government Departments</html:option>
													<html:option value="PPO2">Letter issued by a gazetted officer</html:option>
												</html:select></td>

											<td><bean:message key="lbl.proofOfIdentityDocumentNo" /><font
												color="red">*</font></td>
											<td><html:text property="proofOfIdentityDocumentNo"
													styleClass="text" styleId="proofOfIdentityDocumentNo"
													
													onkeypress="panUIDFill('Identity');"
													onkeypress="return isAlphNumericKey(event);"
													onchange="return upperMe('proofOfIdentityDocumentNo');"
													disabled="false"
													value="${individualInfo[0].proofOfIdentityDocumentNo}"
													maxlength="16" /></td>
										</tr>
										<tr>
											<td><bean:message
													key="lbl.proofOfIdentityDocumentExpDate" /><font
												color="red">*</font></td>
											<td><html:text property="proofOfIdentityDocumentExpDate"
													styleClass="text" styleId="proofOfIdentityDocumentExpDate"
													value="${individualInfo[0].proofOfIdentityDocumentExpDate}"
													maxlength="12" /></td>
										</tr>

										<tr>
											<td><bean:message key="lbl.proofOfAddressDocument" /><font
												color="red">*</font></td>
											<td><html:select property="proofOfAddressDocument"
													styleId="proofOfAddressDocument" styleClass="text"
													onchange="enableCkycDoc('Address');"
													onchange="panUIDFill('Address');"
													onblur="proofAddressValid();"
													value="${individualInfo[0].proofOfAddressDocument}">
													<html:option value="">--Select--</html:option>
													<html:option value="PP">PASSPORT</html:option>
													<html:option value="VIC">VOTER ID</html:option>
													<%-- <html:option value="PAN">PAN CARD</html:option>
										    --%>
													<html:option value="DL">DRIVING LICENCE</html:option>
													  <html:option value="UID">UID ADHAAR</html:option>
										  
													<html:option value="NREGA">NREGA JOB CARD</html:option>
													<html:option value="OT">OTHER</html:option>
													<html:option value="UB2">Identity card with applicant photograph issued by Central State Government Departments</html:option>
													<html:option value="PPO2">Letter issued by a gazetted officer</html:option>
													<html:option value="MTR">Prop Muncipal Tax Recipt</html:option>
													<html:option value="SB">Saving Acc Statement bank post office</html:option>
													<html:option value="PPO">Pension Payment Order</html:option>
													<html:option value="LOA">Letter of allotment of accommodation from employer issued by State or Central Government departments</html:option>
													<html:option value="IFE">Documents issued by Government departments of foreign jurisdiction</html:option>
												</html:select></td>

											<td><bean:message key="lbl.proofOfAddressDocumentNo" /><font
												color="red">*</font></td>
											<td><html:text property="proofOfAddressDocumentNo"
													styleClass="text" styleId="proofOfAddressDocumentNo"
													onkeypress="panUIDFill('Address');"
													
													onkeypress="return isAlphNumericKey(event);"
													onchange="return upperMe('proofOfAddressDocumentNo');"
													disabled="false"
													value="${individualInfo[0].proofOfAddressDocumentNo}"
													maxlength="16" /></td>
										</tr>
										<tr>
											<td><bean:message
													key="lbl.proofOfAddressDocumentExpDate" /><font
												color="red">*</font></td>
											<td><html:text property="proofOfAddressDocumentExpDate"
													styleClass="text" styleId="proofOfAddressDocumentExpDate"
													value="${individualInfo[0].proofOfAddressDocumentExpDate}"
													maxlength="12" /></td>
										</tr>
										<tr>
											<td><bean:message key="lbl.relatedPersonType" /></td>
											<td><html:select property="relatedPersonType"
													styleId="relatedPersonType" styleClass="text"
													value="${individualInfo[0].relatedPersonType}">
													<html:option value="">--Select--</html:option>
													<html:option value="GOM">GUARDIAN OF MINOR</html:option>
													<html:option value="ASSI">ASSIGNEE</html:option>
													<html:option value="AUTHR">AUTHORIZED REPRESENTATIVE</html:option>
												</html:select></td>

											<td><bean:message key="lbl.relatedPersonPrifix" /></td>
											<td><html:select property="relatedPersonPrifix"
													styleId="relatedPersonPrifix" styleClass="text"
													value="${individualInfo[0].relatedPersonPrifix}">
													<html:option value="">--Select--</html:option>
													<html:option value="Mr">MR</html:option>
													<html:option value="Miss">MISS</html:option>
													<html:option value="Mrs">MRS</html:option>
													<html:option value="DR">DR</html:option>
												</html:select></td>
										</tr>

										<tr>
											<td><bean:message key="lbl.relatedPersonFName" /></td>
											<td><html:text property="relatedPersonFName"
													styleClass="text" styleId="relatedPersonFName"
													value="${individualInfo[0].relatedPersonFName}"
													maxlength="50" /></td>

											<td><bean:message key="lbl.relatedPersonMName" /></td>
											<td><html:text property="relatedPersonMName"
													styleId="relatedPersonMName" styleClass="text"
													value="${individualInfo[0].relatedPersonMName}"
													maxlength="50" /></td>
										</tr>

										<tr>
											<td><bean:message key="lbl.relatedPersonLName" /></td>
											<td><html:text property="relatedPersonLName"
													styleClass="text" styleId="relatedPersonLName"
													value="${individualInfo[0].relatedPersonLName}"
													maxlength="50" /></td>

											<td><bean:message key="lbl.relatedPersonCkycNo" /></td>
											<td><html:text property="relatedPersonCkycNo"
													styleId="relatedPersonCkycNo" styleClass="text"
													value="${individualInfo[0].relatedPersonCkycNo}"
													maxlength="50" /></td>
										</tr>

										<tr>
											<td><bean:message key="lbl.relatedPersonPrfIdntyDoc" /></td>
											<td><html:select property="relatedPersonPrfIdntyDoc"
													styleId="relatedPersonPrfIdntyDoc" styleClass="text" onchange="proofOfRelatedPerson();"
													value="${individualInfo[0].relatedPersonPrfIdntyDoc}">
													<html:option value="">--Select--</html:option>
													<html:option value="PP">PASSPORT</html:option>
													<html:option value="VIC">VOTER ID</html:option>
													<html:option value="PAN">PAN CARD</html:option>
													<html:option value="DL">DRIVING LICENCE</html:option>
													<html:option value="UID">UID ADHAAR</html:option>
													<html:option value="NREGA">NREGA JOB CARD</html:option>
													<html:option value="OT">OTHER</html:option>
													<html:option value="UB2">Identity card with applicants photograph issued by Central State Government Departments</html:option>
													<html:option value="PPO2">Letter issued by a gazetted officer</html:option>
												</html:select></td>

											<td><bean:message key="lbl.relatedPersonPrfIdntyDocNo" /></td>
											<td><html:text property="relatedPersonPrfIdntyDocNo"
													styleClass="text" styleId="relatedPersonPrfIdntyDocNo"
													value="${individualInfo[0].relatedPersonPrfIdntyDocNo}"
													maxlength="50" /></td>
										</tr>

										<tr>
											<td><bean:message
													key="lbl.relatedPersonPrfIdntyDocExpDate" />
												<bean:message key="lbl.dateFormat(dd-mm-yyyy)" /></td>
											<td><html:text
													property="relatedPersonPrfIdntyDocExpDate"
													styleId="relatedPersonPrfIdntyDocExpDate" styleClass="text"
													value="${individualInfo[0].relatedPersonPrfIdntyDocExpDate}" /></td>

											<td><bean:message key="lbl.cKYC" /></td>
											<td><html:text property="cKYC" styleClass="text"
													styleId="cKYC" value="${individualInfo[0].cKYC}"
													maxlength="20" /></td>
										</tr>
										<tr>
											<td nowrap="nowrap"><bean:message
													key="lbl.otherRelationShip" /></td>
											<td nowrap="nowrap"><html:select
													property="otherRelationShip" styleId="otherRelationShip"
													styleClass="text"
													value="${individualInfo[0].otherRelationShip}"
													onchange="return activeSupplier(this.value);">
													<html:option value="CS">Customer</html:option>
													<html:option value="SU">Supplier</html:option>
													<html:option value="MF">Manufacturer</html:option>
												</html:select></td>
											<td nowrap="nowrap"><logic:notPresent
													name="individualInfo">
													<logic:empty name="individualInfo">

														<div id="supplierLableDiv" style="display: none">
															<bean:message key="lbl.supplier" />
															<font color="red">*</font>
														</div>
														<div id="manufactLableDiv" style="display: none">
															<bean:message key="lbl.manufact" />
															<font color="red">*</font>
														</div>
													</logic:empty>
												</logic:notPresent> <logic:present name="individualInfo">
													<logic:notEmpty name="individualInfo">
														<logic:equal name="otherRelationShip" value='CS'>

															<div id="supplierLableDiv" style="display: none">
																<bean:message key="lbl.supplier" />

															</div>
															<div id="manufactLableDiv" style="display: none">
																<bean:message key="lbl.manufact" />

															</div>
														</logic:equal>
														<logic:notEqual name="otherRelationShip" value='CS'>
															<logic:equal name="otherRelationShip" value='SU'>

																<div id="supplierLableDiv">
																	<bean:message key="lbl.supplier" />
																	<font color="red">*</font>

																</div>
																<div id="manufactLableDiv" style="display: none">
																	<bean:message key="lbl.manufact" />
																	<font color="red">*</font>
																</div>
															</logic:equal>
															<logic:equal name="otherRelationShip" value='MF'>

																<div id="manufactLableDiv">
																	<bean:message key="lbl.manufact" />
																	<font color="red">*</font>
																</div>
																<div id="supplierLableDiv" style="display: none">
																	<bean:message key="lbl.supplier" />
																	<font color="red">*</font>

																</div>
															</logic:equal>
														</logic:notEqual>
													</logic:notEmpty>
												</logic:present></td>
											<td nowrap="nowrap"><logic:present name="individualInfo">
													<logic:notEmpty name="individualInfo">
														<logic:notEqual name="otherRelationShip" value='CS'>
															<div id="supplierDiv">

																<html:text styleClass="text" maxlength="10"
																	property="businessPartnerTypeDesc"
																	styleId="businessPartnerTypeDesc" style=""
																	readonly="true"
																	value="${individualInfo[0].businessPartnerTypeDesc}"
																	tabindex="-1" />
																<html:hidden styleClass="text"
																	property="businessPartnerType"
																	styleId="businessPartnerType" style="" value="" />
																<input type="hidden" name="contextPath"
																	value="<%=request.getContextPath()%>" id="contextPath" />
																<html:button property="bpButton" styleId="bpButton"
																	onclick="openLOVCommon(415,'individualDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');"
																	value=" " styleClass="lovbutton"></html:button>
																<html:hidden property="businessPartnerName"
																	styleId="businessPartnerName" value=""
																	styleClass="text" />

															</div>
															<html:hidden property="lbxBusinessPartnearHID"
																styleId="lbxBusinessPartnearHID"
																value="${individualInfo[0].lbxBusinessPartnearHID}" />

														</logic:notEqual>
														<logic:equal name="otherRelationShip" value='CS'>
															<div id="supplierDiv" style="display: none">

																<html:text styleClass="text" maxlength="10"
																	property="businessPartnerTypeDesc"
																	styleId="businessPartnerTypeDesc" style=""
																	readonly="true"
																	value="${individualInfo[0].businessPartnerTypeDesc}"
																	tabindex="-1" />
																<html:hidden styleClass="text"
																	property="businessPartnerType"
																	styleId="businessPartnerType" style="" value="" />
																<input type="hidden" name="contextPath"
																	value="<%=request.getContextPath()%>" id="contextPath" />
																<html:button property="bpButton" styleId="bpButton"
																	onclick="openLOVCommon(415,'individualDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');"
																	value=" " styleClass="lovbutton"></html:button>
																<html:hidden property="businessPartnerName"
																	styleId="businessPartnerName" value=""
																	styleClass="text" />

															</div>
															<html:hidden property="lbxBusinessPartnearHID"
																styleId="lbxBusinessPartnearHID"
																value="${individualInfo[0].lbxBusinessPartnearHID}" />

														</logic:equal>
													</logic:notEmpty>
												</logic:present> <logic:notPresent name="individualInfo">
													<logic:empty name="individualInfo">
														<div id="supplierDiv" style="display: none">

															<html:text styleClass="text" maxlength="10"
																property="businessPartnerTypeDesc"
																styleId="businessPartnerTypeDesc" style=""
																readonly="true"
																value="${individualInfo[0].businessPartnerTypeDesc}"
																tabindex="-1" />
															<html:hidden styleClass="text"
																property="businessPartnerType"
																styleId="businessPartnerType" style="" value="" />
															<input type="hidden" name="contextPath"
																value="<%=request.getContextPath()%>" id="contextPath" />
															<html:button property="bpButton" styleId="bpButton"
																onclick="openLOVCommon(415,'individualDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');"
																value=" " styleClass="lovbutton"></html:button>
															<html:hidden property="businessPartnerName"
																styleId="businessPartnerName" value="" styleClass="text" />

														</div>
														<html:hidden property="lbxBusinessPartnearHID"
															styleId="lbxBusinessPartnearHID"
															value="${individualInfo[0].lbxBusinessPartnearHID}" />

													</logic:empty>
												</logic:notPresent></td>
										</tr>
										
										<tr>
											<td><bean:message key="lbl.minorityCommunity" /></td>
										<td><html:select property="minorityCommunity"
													styleId="minorityCommunity" styleClass="text"
													value="${individualInfo[0].minorityCommunity}">
													<html:option value="">--Select--</html:option>
													<html:option value="Y">Yes</html:option>
													<html:option value="N">No</html:option>
												</html:select></td>
									
											<td><bean:message key="lbl.handiCapped" /></td>
											<td><html:select property="handiCapped"
													styleId="handiCapped" styleClass="text"
													value="${individualInfo[0].handiCapped}">
													<html:option value="">--Select--</html:option>
													<html:option value="Y">Yes</html:option>
													<html:option value="N">No</html:option>
												</html:select></td>
										</tr>
										<tr>
											<td><bean:message key="lbl.riskCategory" /><font color="red">*</font></td>
											<td><html:select property="riskCategory" value="${individualInfo[0].riskCategory}" styleClass="text" styleId="riskCategory">
													<option value="">-- Select --</option>
													<logic:present name="riskCategoryList">
														<logic:notEmpty name="riskCategoryList">
															<html:optionsCollection name="riskCategoryList"
																label="riskCategoryDesc" value="riskCategoryCode" />
														</logic:notEmpty>
													</logic:present>
												</html:select></td>
												<td><bean:message key="lbl.cgtmseIndustry" /></td>
													<td><input type="text" name="cgtmseIndustry" id="cgtmseIndustry" 
															value="${individualInfo[0].cgtmseIndustry}" class="text"
															readonly="readonly" tabindex="-1"/>
															<html:hidden property="lbxcgtmseIndustry" styleId="lbxcgtmseIndustry"  value="${individualInfo[0].lbxcgtmseIndustry}" styleClass="text" />
															
															<logic:equal name="functionId" value="3000206">
															<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="cgtmseIndustryButton"
															onclick="closeLovCallonLov1();openLOVCommon(25052,'corporateDetailForm','cgtmseIndustry','','', '','','','lbxcgtmseIndustry');"/>
															</logic:equal>
															</td>
										</tr>
										<tr style="display: none">
											<%-- <td><bean:message key="aadhaar" /><font color="red">*</font></td>
								          <td><html:text property="aadhaar" styleClass="text" styleId="aadhaar"  onkeypress="return isNumber(event);" value="${individualInfo[0].aadhaar}" maxlength="12"/></td>
								 --%>
											<td><bean:message key="other" /></td>
											<td><html:text property="other" styleId="other"
													styleClass="text" value="${individualInfo[0].other}"
													maxlength="20" /></td>

										</tr>
										<tr style="display: none">
											<td><bean:message key="voterId" /></td>
											<td><html:text property="voterId" styleClass="text"
													styleId="voterId" value="${individualInfo[0].voterId}"
													maxlength="20" /></td>

											<td><bean:message key="passport" /></td>
											<td><html:text property="passport" styleId="passport"
													styleClass="text" value="${individualInfo[0].passport}"
													maxlength="20" /></td>
										</tr>


										<tr style="display: none">
											<td><bean:message key="drivingLicense" /></td>
											<td><html:text property="drivingLicense"
													styleId="drivingLicense" styleClass="text"
													value="${individualInfo[0].drivingLicense}" maxlength="20" /></td>
										</tr>
										<!-- Abhishek End For CKYC -->

										<tr>
											<td align="left" colspan="3"><logic:notPresent
													name="panError">
													<logic:notPresent name="individualInfo">
														<button class="topformbutton2" id="save" title="Alt+V"
															type="button" accesskey="V"
															onclick=" saveIndividualDetails();">
															<bean:message key="button.save" />
														</button>
														<logic:notPresent name="pageStatuss">
															<logic:present name="update">
																<logic:notPresent name="SRCDealLoan">
																	<button id="saveAndForward" type="button"
																		onclick=" location.href='<%=request.getContextPath()%>/individualDetailAction.do?method=saveAndForwardIndivDetails' "
																		class="topformbutton3" accesskey="F" title="Alt+F">
																		<bean:message key="button.forward" />
																	</button>
																</logic:notPresent>
															</logic:present>
														</logic:notPresent>
													</logic:notPresent>

													<logic:present name="individualInfo">
														<logic:present name="update">
															<logic:equal name="existingCustomer" value="NO">
															<button id="save" type="button" class="topformbutton2"
																title="Alt+V" accesskey="V"
																onclick="return saveIndividualDetails();">
																<bean:message key="button.save" />		
															</button>
															<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<button type="button" name="button3"  class="topformbutton2" 
															 title="Alt+W" accesskey="W" onclick="return verifyEmail();"><bean:message key="button.verifyEmail" /></button>
													</logic:notEqual>
														
																
															</logic:equal>
															<logic:equal name="functionId" value="2000310">
															<button id="save" type="button" class="topformbutton2"
																title="Alt+V" accesskey="V"
																onclick="return saveIndividualDetails();">
																<bean:message key="button.save" />		
															</button>
															<logic:notEqual name="EmailVerificationFlag" value="Y">
																<button type="button" name="button3"  class="topformbutton2" 
															 title="Alt+W" accesskey="W" onclick="return verifyEmail();"><bean:message key="button.verifyEmail" /></button>	
															 </logic:notEqual>
															</logic:equal>
														</logic:present>
														<logic:notPresent name="update">
														<logic:equal name="existingCustomer" value="NO">
															<button id="save" class="topformbutton2" type="button"
																title="Alt+V" accesskey="V"
																onclick="return saveIndividualDetails();">
																<bean:message key="button.save" />
															</button>
														</logic:equal>
														</logic:notPresent>
														<logic:notPresent name="updateFlag">
															<logic:notPresent name="SRCDealLoan">
																<button id="saveAndForward" type="button"
																	onclick="location.href='<%=request.getContextPath()%>/individualDetailAction.do?method=saveAndForwardIndivDetails'"
																	class="topformbutton3" accesskey="F" title="Alt+F">
																	<bean:message key="button.forward" />
																</button>
															</logic:notPresent>
														</logic:notPresent>
													</logic:present>
													<logic:notPresent name="individualInfo">
														<button title="Alt+C" accesskey="C" type="button"
															class="topformbutton2" onclick="corporateClear();">
															<bean:message key="button.clear" />
														</button>
													</logic:notPresent>
												</logic:notPresent> <logic:present name="panError">
												<%-- <logic:equal name="existingCustomer" value="NO"> --%>
													<button id="save" class="topformbutton2" type="button"
														title="Alt+V" accesskey="V"
														onclick="return saveIndividualDetails();">
														<bean:message key="button.save" />
													</button>
													<%-- </logic:equal> --%>
													<button title="Alt+C" accesskey="C" type="button"
														class="topformbutton2" onclick="corporateClear();">
														<bean:message key="button.clear" />
													</button>
												</logic:present></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</div>
		</div>
		<logic:present name="back">

			<script type="text/javascript">
 alert("You can't move without saving before Individual Details!!!");
</script>

		</logic:present>
	</logic:notPresent>

	<logic:present name="approve">

		<logic:present name="back">

			<script type="text/javascript">
  alert("You can't move without saving before Individual Details!!!");
</script>

		</logic:present>
		<div id='centercolumn'>
			<div id=revisedcontainer>
				<html:form action="/idividualDetailActionPage"
					styleId="individualDetailForm" method="post">
					<fieldset>
						<legend>
							<bean:message key="individual.details" />
						</legend>
						<table cellspacing=0 cellpadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td><font color="red"><html:errors /></font></td>
								</tr>
								<tr>
									<td>
										<table cellspacing=1 cellpadding=2 width="100%" border=0>
											<logic:present name="individualInfo">

												<tr>
													<td><bean:message key="individual.code" /><span></span>
													</td>
													<td><html:text property="corporateCode"
															styleId="corporateCode" styleClass="text"
															value="${individualInfo[0].corporateCode}"
															disabled="true" /></td>
															<td><bean:message key="lbl.UCIC" /><span></span>
												</td>
												<td><html:text property="ucic"
														styleId="ucic" styleClass="text"
														value="${individualInfo[0].ucic}" disabled="true"
														tabindex="-1" /></td>
												</tr>
											</logic:present>

											<!-- amandeep starts -->

											<!-- amandeep starts -->

											<logic:present name="applType">

												<logic:present name="groupTypeActivated">


													<td><bean:message key="lbl.groupType" /></td>
													<td><html:select property="groupType"
															styleId="groupType" onchange="groupSelectInMasterNew();"
															disabled="true" styleClass="text"
															value="${individualInfo[0].groupType}">
															<html:option value="N">New</html:option>
															<html:option value="E">Existing</html:option>
														</html:select></td>

													<td><bean:message key="lbl.groupName" /></td>
													<td>
														<div id="groupLov" style="display: none">
															<input type="text" id="groupName" name="groupName"
																class="text" readonly="readonly"
																value="${individualInfo[0].groupName}"
																readonly="readonly" tabindex="-1" />
															<html:hidden property="hGroupId" styleId="hGroupId"
																styleClass="text" value="${individualInfo[0].hGroupId}" />

														</div>

														<div id="groupText">
															<input type="text" id="groupNameText" name="groupName"
																class="text" readonly="readonly"
																value="${individualInfo[0].groupName}" maxlength="50" />


														</div>

													</td>

												</logic:present>

											</logic:present>

											<logic:notPresent name="groupTypeActivated">

												<html:hidden property="groupType" styleId="groupType"
													styleClass="text" value="E" />
												<td><bean:message key="lbl.groupName" /></td>
												<td><input type="text" id="groupName" name="groupName"
													class="text" value="${individualInfo[0].groupName}"
													readonly="readonly" tabindex="-1" /> <html:hidden
														property="hGroupId" styleId="hGroupId" styleClass="text"
														value="${individualInfo[0].hGroupId}" /></td>
											</logic:notPresent>








											<!-- amandeep -->
											<tr>
												<td><bean:message key="lbl.customerNamePrifx" /><font
													color="red">*</font></td>
												<td><html:select property="customerNamePrifx"
														styleId="customerNamePrifx" styleClass="text"
														value="${individualInfo[0].customerNamePrifx}">
														<html:option value="">-- Select --</html:option>
														<html:option value="MR">MR</html:option>
														<html:option value="MISS">MISS</html:option>
														<html:option value="MRS">MRS</html:option>
														<html:option value="DR">DR</html:option>
													</html:select></td>
											</tr>
											<tr>
												<td><input type="hidden" name="cusType" id="cusType"
													value="${individualInfo[0].cusType}" /> <input
													type="hidden" name="pagestatus" value="Individual" />
												<bean:message key="individual.name" /><font color="red">*</font></td>
												<td><html:text property="firstName" styleId="firstName"
														disabled="true" styleClass="text"
														value="${individualInfo[0].firstName}" /></td>

												<td><bean:message key="individual.middle" /></td>
												<td><html:text property="middleName"
														styleId="middleName" disabled="true" styleClass="text"
														value="${individualInfo[0].middleName}" /></td>
											</tr>
											<tr>
												<td><bean:message key="individual.last" /><font
													color="red">*</font></td>
												<td><html:text property="lastName" styleId="lastName"
														disabled="true" styleClass="text"
														value="${individualInfo[0].lastName}" /></td>

												<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
												<td><html:select property="genderIndividual"
														disabled="true"
														value="${individualInfo[0].genderIndividual}"
														styleClass="text" styleId="genderIndividual">
														<option value="">-- Select --</option>
														<logic:present name="GenderCategory">
															<logic:notEmpty name="GenderCategory">
																<html:optionsCollection name="GenderCategory"
																	label="genderDesc" value="genderCode" />
															</logic:notEmpty>
														</logic:present>
													</html:select></td>
											</tr>
											<tr>
												<td><bean:message key="individual.category" /><font
													color="red">*</font></td>
												</td>
												<td><html:select property="corporateCategory"
														disabled="true"
														value="${individualInfo[0].corporateCategory}"
														styleClass="text">
														<option value="">-- Select --</option>
														<logic:present name="individualCategory">

															<html:optionsCollection name="individualCategory"
																label="customerCategoryDesc"
																value="customerCategoryCode" />
														</logic:present>

													</html:select></td>

												<td><bean:message key="constitution" /><font
													color="red">*</font></td>
												<td><html:select property="constitution"
														styleId="constitution" disabled="true"
														value="${individualInfo[0].constitution}"
														styleClass="text">
														<option value="">--Select--</option>
														<logic:present name="constitutionlist">
															<logic:notEmpty name="constitutionlist">
																<html:optionsCollection name="constitutionlist"
																	label="constitution" value="contitutionCode" />
															</logic:notEmpty>
														</logic:present>
													</html:select></td>

											</tr>
											<tr>
												<td><bean:message key="fatherHusband" /><font
													color="red">*</font></td>
												<td><html:text property="fatherHusband"
														styleId="fatherHusband" readonly="true" styleClass="text"
														value="${individualInfo[0].fatherHusband}"
														onchange="return upperMe('fatherHusband');" maxlength="50" /></td>

												<td><bean:message key="individual.birthdate" /><font
													color="red">*</font></td>
												<td><input type="hidden" name="approve" id="approve"
													value="Y" /> <input type="hidden" name="sysDate"
													id="sysDate" value="${userobject.businessdate}" /> <input
													type="text" name="incorporation_Date" readonly="readonly"
													id="incorporation_Date" class="text"
													value="${individualInfo[0].incorporationDate}" /></td>
											</tr>
											<tr>
												<td><bean:message key="currentAge" /></td>
												<td><html:text property="currentAge"
														styleId="currentAge" styleClass="text" readonly="true" /></td>


												<td><bean:message key="casteCategory" /></td>
												<td><html:select property="casteCategory"
														value="${individualInfo[0].casteCategory}" disabled="true"
														styleClass="text" styleId="casteCategory">
														<option value="">-- Select --</option>
														<logic:present name="CastCategory">
															<logic:notEmpty name="CastCategory">
																<html:optionsCollection name="CastCategory"
																	label="castCategoryDesc" value="castCategoryCode" />
															</logic:notEmpty>
														</logic:present>
													</html:select></td>

											</tr>
											<tr>
												<td><bean:message key="maritalStatus" /><font
													color="red">*</font></td>
												<td><html:select property="maritalStatus"
														styleId="maritalStatus" styleClass="text" disabled="true"
														value="${individualInfo[0].maritalStatus}">
														<html:option value="">-- Select --</html:option>
														<html:option value="M">Married</html:option>
														<html:option value="U">Un-Married</html:option>
														<html:option value="O">OTHER</html:option>
													</html:select></td>

												<logic:equal name="emailMandatoryFlag" value="Y">
													<td><bean:message key="individual.email" /><font
														color="red">*</font></td>
												</logic:equal>
												<logic:notEqual name="emailMandatoryFlag" value="Y">
													<td><bean:message key="individual.email" /></td>
												</logic:notEqual>
												<td><html:text property="institutionEmail"
														styleId="institutionEmail" disabled="true"
														styleClass="text"
														value="${individualInfo[0].institutionEmail}" />
														
												<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="EmailVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal>
														</td>
											</tr>
											<tr>

												<td><bean:message key="individual.pan" /><font color="red">*</font></td>
												<td><html:text property="pan" styleClass="text"
														disabled="true" styleId="pan" styleClass="text"
														onkeypress="return isAlphNumericKey(event);"
														onchange="return upperMe('pan');"
														onkeyup="onPanChange()"
														value="${individualInfo[0].pan}" /></td>

												<td><bean:message key="aadhaar" /></td>
												<td><html:text property="aadhaar" styleClass="text"
														styleId="aadhaar" onkeypress="return isNumber(event);"
														readonly="true" value="${individualInfo[0].aadhaar}"
														maxlength="12" /></td>
											</tr>
											<tr>

												<td><bean:message key="industry" /></td>
												<td><input type="text" name="industry" id="industry"
													size="20" value="${individualInfo[0].industry}"
													class="text" readonly="readonly" tabindex="-1" /> <logic:present
														name="strParentOption">
														<input type="hidden" name="lbxIndustry" id="lbxIndustry"
															class="text" value="${sessionScope.strParentOption}" />
													</logic:present> <logic:notPresent name="strParentOption">
														<input type="hidden" name="lbxIndustry" id="lbxIndustry"
															class="text" value="${individualInfo[0].lbxIndustry}" />
													</logic:notPresent> <!-- <html:button property="lovButton" value=" " styleClass="lovbutton" styleId="industryButton"
															onclick="closeLovCallonLov1();openLOVCommon(10,'individualDetailForm','industry','','subIndustry', 'lbxSubIndustry','','','hcommon');"/> -->
												</td>

												<td><bean:message key="subIndustry" /></td>
												<td>
													<div id="subIndustrydetail">

														<input type="text" name="subIndustry" id="subIndustry"
															size="20" class="text" readonly="readonly"
															value="${individualInfo[0].subIndustry}" tabindex="-1" />
														<input type="hidden" name="lbxSubIndustry"
															id="lbxSubIndustry" class="text"
															value="${individualInfo[0].lbxSubIndustry}" />
														<!-- <html:button property="lovButton" value=" " styleClass="lovbutton" styleId="subIndustryButton"
																onclick="closeLovCallonLov('industry');openLOVCommon(11,'individualDetailForm','subIndustry','industry','lbxSubIndustry', 'lbxIndustry','Please select Industry First!!!','','hcommon');"/> -->
													</div>

												</td>
											</tr>


											<tr>

												<td><bean:message key="individual.blackListed" /></td>
												<td><logic:present name="individualInfo">

														<logic:iterate name="individualInfo"
															id="subindividualInfo">

															<logic:equal name="subindividualInfo"
																property="blackListed" value="N">
																<html:checkbox property="blackListed"
																	styleId="blackListed" disabled="true" />
															</logic:equal>
															<logic:equal name="subindividualInfo"
																property="blackListed" value="Y">
																<input type="checkbox" name="blackListed"
																	id="blackListed" checked="checked" disabled="disabled" />
															</logic:equal>
														</logic:iterate>

													</logic:present> <logic:notPresent name="individualInfo">
														<html:checkbox property="blackListed"
															styleId="blackListed" disabled="true" />
													</logic:notPresent></td>
												<td><bean:message
														key="individual.reasonForBlackListing" /></td>
												<td><html:text property="reasonForBlackListed"
														styleId="reasonForBlackListed" disabled="true"
														styleClass="text"
														value="${individualInfo[0].reasonForBlackListed}" /></td>


												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>

											<tr>
												<logic:present name="deal">
													<logic:notPresent name="type">
														<td><bean:message key="lbl.relationshipWithCust" /></td>
														<td><html:text property="relationShip"
																styleId="relationShip" styleClass="text"
																value="${individualInfo[0].relationShip}" maxlength="50"
																readonly="true" /></td>
													</logic:notPresent>
												</logic:present>
												<td><bean:message key="individual.referredBy" /></td>
												<td><html:text property="referredBy"
														styleId="referredBy" disabled="true" styleClass="text"
														value="${individualInfo[0].referredBy}" /></td>
											</tr>




											<!-- Abhishek Start For CKYC -->
											<tr>
												<%--  <td><bean:message key="lbl.motherPrifx" /><font color="red">*</font></td>
									       <td>
									       <html:select property="motherPrifx" styleId="motherPrifx" styleClass="text" value="${individualInfo[0].motherPrifx}">
										   <html:option value="Mrs">MRS</html:option>
										   </html:select>
										   </td> --%>

												<td><bean:message key="lbl.education" /></td>
												<td><html:select styleId="eduDetailInd"
														property="eduDetailInd" styleClass="text" disabled="true"
														value="${individualInfo[0].eduDetailInd}">
														<html:option value="">---Select---</html:option>
														<logic:present name="eduDetail">
															<logic:notEmpty name="eduDetail">
																<html:optionsCollection name="eduDetail"
																	label="eduDetailDesc" value="eduDetailCode" />
															</logic:notEmpty>
														</logic:present>
													</html:select></td>

												<td><bean:message key="lbl.motherFName" /></td>
												<td><html:text property="motherFName" styleClass="text"
														styleId="motherFName"
														value="${individualInfo[0].motherFName}" maxlength="50" /></td>
											</tr>

											<tr>
												<td><bean:message key="lbl.motherMName" /></td>
												<td><html:text property="motherMName"
														styleId="motherMName" styleClass="text"
														value="${individualInfo[0].motherMName}" maxlength="50" /></td>

												<td><bean:message key="lbl.motherLName" /></td>
												<td><html:text property="motherLName"
														styleId="motherLName" styleClass="text"
														value="${individualInfo[0].motherLName}" maxlength="50" /></td>
											</tr>

											<tr>
												<%-- <td><bean:message key="lbl.maritalStatus" /></td>
									       <td>
									       <html:select property="maritalStatus" styleId="maritalStatus" styleClass="text" value="${individualInfo[0].maritalStatus}">
										   <html:option value="">--Select--</html:option>
										   <html:option value="M">MARRIED</html:option>
										   <html:option value="U">UN MARRIED</html:option>
										   <html:option value="O">OTHER</html:option>
										   </html:select>
										   </td> --%>

												<td><bean:message key="lbl.citizenship" /></td>
												<td><html:text property="citizenship" styleClass="text"
														styleId="citizenship"
														value="${individualInfo[0].citizenship}" maxlength="2" /></td>
											</tr>

											<tr>
												<td><bean:message key="lbl.residentialStatus" /></td>
												<td><html:select property="residentialStatus"
														styleId="residentialStatus" styleClass="text"
														value="${individualInfo[0].residentialStatus}">
														<html:option value="">--Select--</html:option>
														<html:option value="RI">RESIDENT INDIVIDUAL</html:option>
														<html:option value="NRI">NON RESIDENT INDIAN</html:option>
														<html:option value="FN">FOREIGN NATIONAL</html:option>
														<html:option value="PIO">PERSON OF INDIAN ORIGIN</html:option>
													</html:select></td>

												<td><bean:message key="lbl.occpation" /></td>
												<td><html:select property="occpation"
														styleId="occpation" styleClass="text"
														value="${individualInfo[0].occpation}">
														<html:option value="">--Select--</html:option>
														<html:option value="SPRIS">SERVICE PRIVATE SECTOR</html:option>
														<html:option value="SPUBS">SERVICE PUBLIC SECTOR</html:option>
														<html:option value="SGOVS">SERVICE GOVERNMENT SECTOR</html:option>
														<html:option value="OPRO">OTHERS PROFESSIONAL</html:option>
														<html:option value="OSEO">OTHER SEFL EMPLOYED</html:option>
														<html:option value="ORET">OTHER RETIRED</html:option>
														<html:option value="OHW">OTHER HOUSE WIFE</html:option>
														<html:option value="OSTU">OTHER STUDENT</html:option>
														<html:option value="BUSI">BUSINESS</html:option>
														<html:option value="XNOT">NOT CATEGORISED</html:option>
													</html:select></td>
											</tr>

											<tr>
												<td><bean:message key="lbl.pesTaxPrsJuriOutSideInd" /></td>
												<td><html:select property="pesTaxPrsJuriOutSideInd"
														styleId="pesTaxPrsJuriOutSideInd" styleClass="text"
														value="${individualInfo[0].pesTaxPrsJuriOutSideInd}">
														<html:option value="">--Select--</html:option>
														<html:option value="Y">Yes</html:option>
														<html:option value="N">No</html:option>
													</html:select></td>

												<td><bean:message key="lbl.accountType" /></td>
												<td><html:select property="accountType"
														styleId="accountType" styleClass="text"
														value="${individualInfo[0].accountType}">
														<html:option value="">--Select--</html:option>
														<html:option value="NORMAL">NORMAL</html:option>
														<html:option value="SIMPLIFIED ID">SIMPLIFIED ID</html:option>
														<html:option value="SMALL">SMALL</html:option>
														<html:option value="E-KYC">E-KYC</html:option>
													</html:select></td>
											</tr>

											<%--  <tr>
										  <td><bean:message key="lbl.prfIdentityDocNo" /></td>
								          <td><html:text property="prfIdentityDocNo" styleClass="text" styleId="prfIdentityDocNo" value="${individualInfo[0].prfIdentityDocNo}" maxlength="20"/></td>
																				
								          <td><bean:message key="lbl.prfIdentityDocExpDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
         								  <td><html:text property="prfIdentityDocExpDate" styleId="prfIdentityDocExpDate" styleClass="text" value="${individualInfo[0].prfIdentityDocExpDate}" /></td> 
								       </tr>
								       
								       <tr>
										  <td><bean:message key="lbl.prfAddressDocNo" /></td>
								          <td><html:text property="prfAddressDocNo" styleClass="text" styleId="prfAddressDocNo" value="${individualInfo[0].prfAddressDocNo}" maxlength="50"/></td>
																				
								          <td><bean:message key="lbl.prfAddressDocExpDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
         								  <td><html:text property="prfAddressDocExpDate" styleId="prfAddressDocExpDate" styleClass="text" value="${individualInfo[0].prfAddressDocExpDate}" /></td> 
								       </tr> --%>


											<%-- <tr>
									   	 <td><bean:message key="lbl.accountType" /></td>
									       <td>
									       <html:select property="accountType" styleId="accountType" styleClass="text" value="${individualInfo[0].accountType}">
									       <html:option value="">--Select--</html:option>
										   <html:option value="NORMAL">NORMAL</html:option>
										   <html:option value="SIMPLIFIED ID">SIMPLIFIED ID</html:option>
										   <html:option value="SMALL">SMALL</html:option>
										   <html:option value="E-KYC">E-KYC</html:option>
										   </html:select>
										   </td>
								       </tr> --%>

											<tr>
												<td><bean:message key="lbl.proofOfIdentityDocument" /><font
													color="red">*</font></td>
												<td><html:select property="proofOfIdentityDocument"
														styleId="proofOfIdentityDocument" styleClass="text"
														value="${individualInfo[0].proofOfIdentityDocument}">
														<html:option value="">--Select--</html:option>
														<html:option value="PP">PASSPORT</html:option>
														<html:option value="VIC">VOTER ID</html:option>
														 <html:option value="PAN">PAN CARD</html:option>
										    
														<html:option value="DL">DRIVING LICENCE</html:option>
														 <html:option value="UID">UID ADHAAR</html:option>
										    
														<html:option value="NREGA">NREGA JOB CARD</html:option>
														<html:option value="OT">OTHER</html:option>
														<html:option value="UB2">Identity card with applicant photograph issued by Central State Government Departments</html:option>
														<html:option value="PPO2">Letter issued by a gazetted officer</html:option>
													</html:select></td>

												<td><bean:message key="lbl.proofOfIdentityDocumentNo" /><font
													color="red">*</font></td>
												<td><html:text property="proofOfIdentityDocumentNo"
														styleClass="text" styleId="proofOfIdentityDocumentNo"
														value="${individualInfo[0].proofOfIdentityDocumentNo}"
														maxlength="16" /></td>
											</tr>
											<tr>
												<td><bean:message
														key="lbl.proofOfIdentityDocumentExpDate" /><font
													color="red">*</font></td>
												<td><html:text
														property="proofOfIdentityDocumentExpDate"
														styleClass="text" styleId="proofOfIdentityDocumentExpDate"
														value="${individualInfo[0].proofOfIdentityDocumentExpDate}"
														maxlength="12" /></td>
											</tr>




											<tr>
												<td><bean:message key="lbl.proofOfAddressDocument" /><font
													color="red">*</font></td>
												<td><html:select property="proofOfAddressDocument"
														styleId="proofOfAddressDocument" styleClass="text"
														value="${individualInfo[0].proofOfAddressDocument}">
														<html:option value="">--Select--</html:option>
														<html:option value="PP">PASSPORT</html:option>
														<html:option value="VIC">VOTER ID</html:option>
														<%-- <html:option value="PAN">PAN CARD</html:option>
										    --%>
														<html:option value="DL">DRIVING LICENCE</html:option>
														 <html:option value="UID">UID ADHAAR</html:option>
										   
														<html:option value="NREGA">NREGA JOB CARD</html:option>
														<html:option value="OT">OTHER</html:option>
														<html:option value="UB2">Identity card with applicant photograph issued by Central State Government Departments</html:option>
														<html:option value="PPO2">Letter issued by a gazetted officer</html:option>
														<html:option value="MTR">Prop Muncipal Tax Recipt</html:option>
														<html:option value="SB">Saving Acc Statement bank post office</html:option>
														<html:option value="PPO">Pension Payment Order</html:option>
														<html:option value="LOA">Letter of allotment of accommodation from employer issued by State or Central Government departments</html:option>
														<html:option value="IFE">Documents issued by Government departments of foreign jurisdiction</html:option>
													</html:select></td>

												<td><bean:message key="lbl.proofOfAddressDocumentNo" /><font
													color="red">*</font></td>
												<td><html:text property="proofOfAddressDocumentNo"
														styleClass="text" styleId="proofOfAddressDocumentNo"
														value="${individualInfo[0].proofOfAddressDocumentNo}"
														maxlength="16" /></td>
											</tr>
											<tr>
												<td><bean:message
														key="lbl.proofOfAddressDocumentExpDate" /><font
													color="red">*</font></td>
												<td><html:text property="proofOfAddressDocumentExpDate"
														styleClass="text" styleId="proofOfAddressDocumentExpDate"
														value="${individualInfo[0].proofOfAddressDocumentExpDate}"
														maxlength="12" /></td>
											</tr>
											<tr>
												<td><bean:message key="lbl.relatedPersonType" /></td>
												<td><html:select property="relatedPersonType"
														styleId="relatedPersonType" styleClass="text"
														value="${individualInfo[0].relatedPersonType}">
														<html:option value="">--Select--</html:option>
														<html:option value="GOM">GUARDIAN OF MINOR</html:option>
														<html:option value="ASSI">ASSIGNEE</html:option>
														<html:option value="AUTHR">AUTHORIZED REPRESENTATIVE</html:option>
													</html:select></td>

												<td><bean:message key="lbl.relatedPersonPrifix" /></td>
												<td><html:select property="relatedPersonPrifix"
														styleId="relatedPersonPrifix" styleClass="text"
														value="${individualInfo[0].relatedPersonPrifix}">
														<html:option value="">--Select--</html:option>
														<html:option value="Mr">MR</html:option>
														<html:option value="Miss">MISS</html:option>
														<html:option value="Mrs">MRS</html:option>
														<html:option value="DR">DR</html:option>
													</html:select></td>
											</tr>

											<tr>
												<td><bean:message key="lbl.relatedPersonFName" /></td>
												<td><html:text property="relatedPersonFName"
														styleClass="text" styleId="relatedPersonFName"
														value="${individualInfo[0].relatedPersonFName}"
														maxlength="50" /></td>

												<td><bean:message key="lbl.relatedPersonMName" /></td>
												<td><html:text property="relatedPersonMName"
														styleId="relatedPersonMName" styleClass="text"
														value="${individualInfo[0].relatedPersonMName}"
														maxlength="50" /></td>
											</tr>

											<tr>
												<td><bean:message key="lbl.relatedPersonLName" /></td>
												<td><html:text property="relatedPersonLName"
														styleClass="text" styleId="relatedPersonLName"
														value="${individualInfo[0].relatedPersonLName}"
														maxlength="50" /></td>

												<td><bean:message key="lbl.relatedPersonCkycNo" /></td>
												<td><html:text property="relatedPersonCkycNo"
														styleId="relatedPersonCkycNo" styleClass="text"
														value="${individualInfo[0].relatedPersonCkycNo}"
														maxlength="50" /></td>
											</tr>

											<tr>
												<td><bean:message key="lbl.relatedPersonPrfIdntyDoc" /></td>
												<td><html:select property="relatedPersonPrfIdntyDoc"
														styleId="relatedPersonPrfIdntyDoc" styleClass="text" onchange="proofOfRelatedPerson();"
														value="${individualInfo[0].relatedPersonPrfIdntyDoc}">
														<html:option value="">--Select--</html:option>
														<html:option value="PP">PASSPORT</html:option>
														<html:option value="VIC">VOTER ID</html:option>
														<html:option value="PAN">PAN CARD</html:option>
														<html:option value="DL">DRIVING LICENCE</html:option>
														<html:option value="UID">UID ADHAAR</html:option>
														<html:option value="NREGA">NREGA JOB CARD</html:option>
														<html:option value="OT">OTHER</html:option>
														<html:option value="UB2">Identity card with applicants photograph issued by Central State Government Departments</html:option>
														<html:option value="PPO2">Letter issued by a gazetted officer</html:option>
													</html:select></td>

												<td><bean:message key="lbl.relatedPersonPrfIdntyDocNo" /></td>
												<td><html:text property="relatedPersonPrfIdntyDocNo"
														styleClass="text" styleId="relatedPersonPrfIdntyDocNo"
														value="${individualInfo[0].relatedPersonPrfIdntyDocNo}"
														maxlength="50" /></td>
											</tr>

											<tr>
												<td><bean:message
														key="lbl.relatedPersonPrfIdntyDocExpDate" />
													<bean:message key="lbl.dateFormat(dd-mm-yyyy)" /></td>
												<td><html:text
														property="relatedPersonPrfIdntyDocExpDate"
														styleId="relatedPersonPrfIdntyDocExpDate"
														styleClass="text"
														value="${individualInfo[0].relatedPersonPrfIdntyDocExpDate}" /></td>

												<td><bean:message key="lbl.cKYC" /></td>
												<td><html:text property="cKYC" styleClass="text"
														styleId="cKYC" value="${individualInfo[0].cKYC}"
														maxlength="20" /></td>
											</tr>

											<tr>
												<td nowrap="nowrap"><bean:message
														key="lbl.otherRelationShip" /></td>
												<td nowrap="nowrap"><html:select
														property="otherRelationShip" styleId="otherRelationShip"
														styleClass="text"
														value="${individualInfo[0].otherRelationShip}"
														disabled="true">
														<html:option value="CS">Customer</html:option>
														<html:option value="SU">Supplier</html:option>
														<html:option value="MF">Manufacturer</html:option>
													</html:select></td>
												<td nowrap="nowrap"><logic:notPresent
														name="individualInfo">
														<logic:empty name="individualInfo">

															<div id="supplierLableDiv" style="display: none">
																<bean:message key="lbl.supplier" />
																<font color="red">*</font>
															</div>
															<div id="manufactLableDiv" style="display: none">
																<bean:message key="lbl.manufact" />
																<font color="red">*</font>
															</div>
														</logic:empty>
													</logic:notPresent> <logic:present name="individualInfo">
														<logic:notEmpty name="individualInfo">
															<logic:equal name="otherRelationShip" value='CS'>
																<div id="supplierLableDiv" style="display: none">
																	<bean:message key="lbl.supplier" />

																</div>
																<div id="manufactLableDiv" style="display: none">
																	<bean:message key="lbl.manufact" />

																</div>
															</logic:equal>
															<logic:notEqual name="otherRelationShip" value='CS'>
																<logic:equal name="otherRelationShip" value='SU'>
																	<div id="supplierLableDiv">
																		<bean:message key="lbl.supplier" />
																		<font color="red">*</font>

																	</div>
																	<div id="manufactLableDiv" style="display: none">
																		<bean:message key="lbl.manufact" />
																		<font color="red">*</font>
																	</div>
																</logic:equal>
																<logic:equal name="otherRelationShip" value='MF'>
																	<div id="manufactLableDiv">
																		<bean:message key="lbl.manufact" />
																		<font color="red">*</font>
																	</div>
																	<div id="supplierLableDiv" style="display: none">
																		<bean:message key="lbl.supplier" />
																		<font color="red">*</font>

																	</div>
																</logic:equal>
															</logic:notEqual>
														</logic:notEmpty>
													</logic:present></td>
												<td nowrap="nowrap"><logic:present
														name="individualInfo">
														<logic:notEmpty name="individualInfo">
															<logic:notEqual name="otherRelationShip" value='CS'>
																<div id="supplierDiv">

																	<html:text styleClass="text" maxlength="10"
																		property="businessPartnerTypeDesc"
																		styleId="businessPartnerTypeDesc" style=""
																		readonly="true"
																		value="${individualInfo[0].businessPartnerTypeDesc}"
																		tabindex="-1" />
																	<html:hidden styleClass="text"
																		property="businessPartnerType"
																		styleId="businessPartnerType" style="" value="" />
																	<input type="hidden" name="contextPath"
																		value="<%=request.getContextPath()%>"
																		id="contextPath" />
																	<!--					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'individualDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>-->
																	<html:hidden property="businessPartnerName"
																		styleId="businessPartnerName" value=""
																		styleClass="text" />

																</div>
																<html:hidden property="lbxBusinessPartnearHID"
																	styleId="lbxBusinessPartnearHID"
																	value="${individualInfo[0].lbxBusinessPartnearHID}" />

															</logic:notEqual>
															<logic:equal name="otherRelationShip" value='CS'>
																<div id="supplierDiv" style="display: none">

																	<html:text styleClass="text" maxlength="10"
																		property="businessPartnerTypeDesc"
																		styleId="businessPartnerTypeDesc" style=""
																		readonly="true"
																		value="${individualInfo[0].businessPartnerTypeDesc}"
																		tabindex="-1" />
																	<html:hidden styleClass="text"
																		property="businessPartnerType"
																		styleId="businessPartnerType" style="" value="" />
																	<input type="hidden" name="contextPath"
																		value="<%=request.getContextPath()%>"
																		id="contextPath" />
																	<!--					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'individualDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>-->
																	<html:hidden property="businessPartnerName"
																		styleId="businessPartnerName" value=""
																		styleClass="text" />

																</div>
																<html:hidden property="lbxBusinessPartnearHID"
																	styleId="lbxBusinessPartnearHID"
																	value="${individualInfo[0].lbxBusinessPartnearHID}" />

															</logic:equal>
														</logic:notEmpty>
													</logic:present> <logic:notPresent name="individualInfo">
														<logic:empty name="individualInfo">
															<div id="supplierDiv" style="display: none">

																<html:text styleClass="text" maxlength="10"
																	property="businessPartnerTypeDesc"
																	styleId="businessPartnerTypeDesc" style=""
																	readonly="true"
																	value="${individualInfo[0].businessPartnerTypeDesc}"
																	tabindex="-1" />
																<html:hidden styleClass="text"
																	property="businessPartnerType"
																	styleId="businessPartnerType" style="" value="" />
																<input type="hidden" name="contextPath"
																	value="<%=request.getContextPath()%>" id="contextPath" />
																<html:button property="bpButton" styleId="bpButton"
																	onclick="openLOVCommon(415,'individualDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');"
																	value=" " styleClass="lovbutton"></html:button>
																<html:hidden property="businessPartnerName"
																	styleId="businessPartnerName" value=""
																	styleClass="text" />

															</div>
															<html:hidden property="lbxBusinessPartnearHID"
																styleId="lbxBusinessPartnearHID"
																value="${individualInfo[0].lbxBusinessPartnearHID}" />

														</logic:empty>
													</logic:notPresent></td>
											</tr>
											
											<tr>
											<td><bean:message key="lbl.minorityCommunity" /></td>
											<td><html:select property="minorityCommunity"
													styleId="minorityCommunity" styleClass="text"
													value="${individualInfo[0].minorityCommunity}">
													<html:option value="">--Select--</html:option>
													<html:option value="Y">Yes</html:option>
													<html:option value="N">No</html:option>
												</html:select></td>
									
											<td><bean:message key="lbl.handiCapped" /></td>
											<td><html:select property="handiCapped"
													styleId="handiCapped" styleClass="text"
													value="${individualInfo[0].handiCapped}">
													<html:option value="">--Select--</html:option>
													<html:option value="Y">Yes</html:option>
													<html:option value="N">No</html:option>
												</html:select></td>
										
										</tr>
												<tr>
											<td><bean:message key="lbl.riskCategory" /></td>
											<td><html:select property="riskCategory" value="${individualInfo[0].riskCategory}" styleClass="text" styleId="riskCategory">
													<option value="">-- Select --</option>
													<logic:present name="riskCategoryList">
														<logic:notEmpty name="riskCategoryList">
															<html:optionsCollection name="riskCategoryList"
																label="riskCategoryDesc" value="riskCategoryCode" />
														</logic:notEmpty>
													</logic:present>
												</html:select></td>
												<td><bean:message key="lbl.cgtmseIndustry" /></td>
													<td><input type="text" name="cgtmseIndustry" id="cgtmseIndustry" 
															value="${individualInfo[0].cgtmseIndustry}" class="text"
															readonly="readonly" tabindex="-1"/>
															<html:hidden property="lbxcgtmseIndustry" styleId="lbxcgtmseIndustry"  value="${individualInfo[0].lbxcgtmseIndustry}" styleClass="text" />
															
															</td>
										</tr>
											<tr style="display: none">

												<td><bean:message key="other" /></td>
												<td><html:text property="other" styleId="other"
														styleClass="text" readonly="true"
														value="${individualInfo[0].other}" maxlength="20" /></td>
											</tr>

											<tr style="display: none">

												<td><bean:message key="voterId" /></td>
												<td><html:text property="voterId" styleClass="text"
														styleId="voterId" readonly="true"
														value="${individualInfo[0].voterId}" maxlength="20" /></td>

												<td><bean:message key="passport" /></td>
												<td><html:text property="passport" styleId="passport"
														readonly="true" styleClass="text"
														value="${individualInfo[0].passport}" maxlength="20" /></td>

											</tr>


											<!-- Abhishek End For CKYC -->

											<tr>

												<td><logic:notPresent name="pageStatuss">
														<logic:notPresent name="underWriter">
															<button class="topformbutton2" type="button"
																onclick="return approveCustomer();" title="Alt+A"
																accesskey="A">
																<bean:message key="button.approve" />
															</button>
														</logic:notPresent>

													</logic:notPresent>
													<logic:equal name="functionId" value="3000296">
													<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<button type="button" name="button3"  class="topformbutton2" 
															 title="Alt+W" accesskey="W" onclick="return verifyEmail();"><bean:message key="button.verifyEmail" /></button>
													</logic:notEqual>
													</logic:equal>
													</td>
											</tr>
										</table>
									</td>
								</tr>

							</tbody>
						</table>

					</fieldset>
				</html:form>
			</div>
		</div>
	</logic:present>
<logic:notPresent name="EmailStatus">
	<logic:present name="sms">
		<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='I')
	{
		alert('<bean:message key="lbl.IndividualDetailSaved" />');
		document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualAddress";
	    document.getElementById('individualDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
		alert("<bean:message key="lbl.IndividualDetailUpdated" />");
		document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualAddress";
	    document.getElementById('individualDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='P')
	{
		alert('<bean:message key="lbl.pendingStatus" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayIndividualAddress";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='mobileVerify')
	{
		alert('Please Verify Mobile First.');
		document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/individualDetailAction.do?method=BackIndividualDetails";
	    document.getElementById('individualDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='emailVerify')
	{
		alert('Please Verify E-Mail First.');
		document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/individualDetailAction.do?method=BackIndividualDetails";
	    document.getElementById('individualDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
			document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualAddress";
		
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='name')
	{
		 var byPassNameCheck = confirm('Name and DOB already exist. Do you want to continue?');
	    	/* document.getElementById('pan').disabled=false;
	    	document.getElementById('aadhaar').disabled=false; */
			if(byPassNameCheck)
			{
				
				document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/idividualDetailActionPage.do?byPassNameCheck=C";
	  			document.getElementById('individualDetailForm').submit();
			}
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Pan')
	{
	    if('${panCondition}'=='Y')
	    {

	    	var bypassDedupe = confirm('Pan No. already exist. Do you want to continue?');
	    	/* document.getElementById('pan').disabled=false;
	    	document.getElementById('aadhaar').disabled=false; */
			if(bypassDedupe)
			{
				document.getElementById('bypassDedupe').value='B';
				document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/idividualDetailActionPage.do?bypassDedupe=B";
 	  			document.getElementById('individualDetailForm').submit();
			}
	    }	   
	    else
	    {
		     alert('<bean:message key="msg.panNumber" />');
		     /* document.getElementById('pan').disabled=false;
		     document.getElementById('aadhaar').disabled=false; */
		}
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='noRecord')
	{
			alert('<bean:message key="lbl.noRecordForSaveAndForward" />');
			document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/individualDetailAction.do?method=BackIndividualDetails";
	    document.getElementById('individualDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='pending')
	{
			alert('<bean:message key="lbl.pendingStatus" />');
			document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/individualDetailAction.do?method=BackIndividualDetails";
	   	 document.getElementById('individualDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='saveFirst')
	{
		alert('<bean:message key="lbl.firstUpdateThenForward" />');
		document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/individualDetailAction.do?method=BackIndividualDetails";
	    document.getElementById('individualDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Relation')
	{
		alert("Please capture Relationship Details in Customer Address Details Tab");
		document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/individualDetailAction.do?method=BackIndividualDetails";
	    document.getElementById('individualDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='exist')
	{
			alert('<bean:message key="msg.existAlready" />');
		 window.close();
	}
	</script>
	</logic:present>
	<logic:present name="sms1">
		<script type="text/javascript">
	if('<%=request.getAttribute("sms1").toString()%>'=='UID')
	{
		alert('<bean:message key="msg.UIDexist" />');
		/* document.getElementById('aadhaar').disabled=false;
		document.getElementById('pan').disabled=false; */
	}
	if('<%=request.getAttribute("sms1").toString()%>'=='voter')
	{
		alert('Voter ID Number already exist.');
		
	}
	if('<%=request.getAttribute("sms1").toString()%>'=='passport')
	{
		alert('PassPort Number already exist.');
		
	}
	if('<%=request.getAttribute("sms1").toString()%>'=='dl')
	{
		alert('Driving License Number already exist.');
		
	}
	</script>
	</logic:present>
	<logic:present name="sms2">
		<script type="text/javascript">
	 if('<%=request.getAttribute("sms2").toString()%>'=='name')
	{
		 var byPassNameCheck = confirm('Name and DOB already exist. Do you want to continue?');
	    	/* document.getElementById('pan').disabled=false;
	    	document.getElementById('aadhaar').disabled=false; */
			if(byPassNameCheck)
			{
				
				document.getElementById('individualDetailForm').action="<%=request.getContextPath()%>/idividualDetailActionPage.do?byPassNameCheck=C";
 	  			document.getElementById('individualDetailForm').submit();
			}
	}
	 </script>
	</logic:present>
	</logic:notPresent>
	<script>
	setFramevalues("individualDetailForm");
</script>
<logic:present name="EmailStatus">
<script type="text/javascript">
	if('<%=request.getAttribute("EmailStatus").toString()%>'=='Y')
	{
		alert('E-Mail Send Successfully.');
		 
	}
	else if('<%=request.getAttribute("EmailStatus").toString()%>'=='N')
	{
		alert('E-Mail Not Send Successfully.');
	}
</script>
</logic:present>
</body>
</html>