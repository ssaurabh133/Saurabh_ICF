<%--
 	Author Name :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose :- To provide user interface for corporate details
 	Documentation :-  
 --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html>

	<head>
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		

		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/popup.js"></script>


		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/gcdScript/search_customer_detail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>

		
		<script type="text/javascript"
			src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript"
			src="<%=path%>/js/gcdScript/commonGcdFunctions.js"></script>
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


	<body onload="hideShowManFieldsForCorporate();enableAnchor();checkChanges('corporateDetailForm');" onunload="closeAllLovCallUnloadBody();" oncontextmenu="return false" onclick="parent_disable();">
	
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
		<input type="hidden" name="formatD" id="formatD"
			value="<bean:message key="lbl.dateFormat"/>" />
						<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<input type="hidden" id="contextPath" name="contextPath"
			value="<%=path%>" />
		<html:hidden property="bypassDedupe" styleId="bypassDedupe" value=""/>
		<input type="hidden" id="emailMandatoryFlag" name="emailMandatoryFlag" value='${emailMandatoryFlag}'/>	
		<logic:notPresent name="approve">

			<div id='centercolumn'>
				<div id=revisedcontainer>
					<html:form action="/corporateDetailProcessAction"
						styleId="corporateDetailForm" method="post">
						<input type="hidden" name="hcommon" id="hcommon" />
						<logic:present name="update">
						     <%-- Update mode in  loan --%>
							<fieldset>
								<legend>
									<bean:message key="corporate.details" />
								</legend>
								<table cellspacing=0 cellpadding=0 width="100%" border=0>
									<tr>
										<td>
											<table cellspacing="1" cellpadding="1" width="100%"
												border="0">
												<logic:present name="detailList">
												<tr>
														<td>
															<bean:message key="corporateCode" />
														</td>
														<td>
															<html:text property="corporateCode"
																styleId="corporateCode" readonly="true" tabindex="-1"
																styleClass="text" value="${detailList[0].corporateCode}" />
														</td>
																<td><bean:message key="lbl.UCIC" /><span></span>
												</td>
												<td><html:text property="ucic"
														styleId="ucic" styleClass="text"
														value="${detailList[0].ucic}" readonly="true"
														tabindex="-1" /></td>
													</tr>
													<%-- Start By Prashant --%>
													<logic:present name="applType">

									               <logic:present name="groupTypeActivated">
													<tr>
									
													<td>
														<bean:message key="lbl.groupType" />
														<font color="red">*</font>
													</td>
														<td>
														 
														<html:select property="groupType" styleId="groupType" onchange="groupSelectInMaster();" styleClass="text" value="${detailList[0].groupType}"> 
															<html:option value="N">New</html:option> 
															<html:option value="E">Existing</html:option> 
														</html:select>
														
														</td>
									          </logic:present>

									          </logic:present>
									          
									          <logic:notPresent name="groupTypeActivated">
									         
									           <html:hidden property="groupType" styleId="groupType"
																	styleClass="text" value="E" />
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
													    <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
														</td>
													</logic:notPresent>	
													
												<logic:present name="applType">
												 	
													<logic:iterate name="detailList" id="subdetailList" length="1">	
													  <logic:equal name="subdetailList" property="groupType" value="N">
													  
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															<div id="groupLov" style="display:none">
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
															</div>	
																															
														<div id="groupText" >
						                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${detailList[0].groupNameText}" maxlength="50"/>
										                </div>
																	
																</td>
														</logic:equal>
														<logic:equal name="subdetailList" property="groupType" value="E">
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															<div id="groupLov" >
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
															</div>	
																															
														<div id="groupText" style="display:none">
						                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${detailList[0].groupNameText}" maxlength="50"/>
										                </div>
																	
																</td>
														</logic:equal>
													</logic:iterate>	

													  		
												</logic:present>
									              
													</tr>
													
													<%-- End By Prashant --%>	
												</logic:present>
												<tr>
													<td>
														<input type="hidden" name="pagestatus" value="Corporate" />
														<bean:message key="corporateName" />
														<font color="red">*</font>
													</td>
													<td>
														<html:text property="corporateName"
															styleId="corporateName"
															value="${detailList[0].corporateName}" styleClass="text"
															onchange="return upperMe('corporateName');"
															maxlength="250" />
													</td>
													<td>
														<bean:message key="customerCategory" />
													</td>
													<td>
														<html:select property="corporateCategory"
															styleId="corporateCategory"
															value="${detailList[0].corporateCategory}"
															styleClass="text">
															<option value="">
																-- Select --
															</option>
															<logic:present name="customerCategory">
															<logic:notEmpty name="customerCategory">
																<html:optionsCollection name="customerCategory"
																	label="customerCategoryDesc"
																	value="customerCategoryCode" />
																	</logic:notEmpty>
															</logic:present>
														</html:select>
													</td>
												</tr>

												<tr>
													<td>
														<bean:message key="incorporationDate" />
														<bean:message key="lbl.dateFormat(dd-mm-yyyy)" /><font color="red">*</font>
													</td>
													<td>
														<html:text property="incorporationDate"
															styleId="incorporationDate" maxlength="10"
															value="${detailList[0].incorporationDate}"
															styleClass="text"
															onchange="checkDate('incorporationDate');yearsInBusiness();" />
													</td>
													<td>
														<bean:message key="constitution" />
														<font color="red">*</font>
													</td>
													<td>
														<html:select property="constitution"
															styleId="constitution"
															value="${detailList[0].constitution}" styleClass="text"
															onchange="appendZero();">
															<html:option value="">--Select--</html:option>
															<logic:present name="constitutionlist">
																<logic:notEmpty name="constitutionlist">
																<html:optionsCollection name="constitutionlist"
																	label="constitution" value="contitutionCode" />
																</logic:notEmpty>
															</logic:present>
														</html:select>
													</td>

												</tr>
												<tr>
													
													<td>
														<bean:message key="registrationNo" /><font color="red">*</font>
														
													</td>
													<td>
														<html:text property="registrationNo"
															styleId="registrationNo" maxlength="25"
															value="${detailList[0].registrationNo}" styleClass="text"
															/>
													</td>
													
													<td>
														<bean:message key="pan" />
														<font color="red" id="hid">*</font> </td>
													<td>
														<html:text property="pan" maxlength="10" styleClass="text"
															styleId="pan" value="${detailList[0].pan}"
															styleClass="text"
															onkeypress="return isAlphNumericKey(event);" onchange="return upperMe('pan');"/>
													</td>
												</tr>

												<tr>
													
													<td>
														<bean:message key="vatRegNo" />
													</td>
													<td>
														<html:text property="vatRegNo" maxlength="25"
															styleId="vatRegNo" value="${detailList[0].vatRegNo}"
															styleClass="text"
															onkeypress="return isAlphNumericKey(event);" />
													</td>
													<td>
														<bean:message key="businessSegment" />
														<font color="red">*</font>
													</td>
													<td>
														<html:select property="businessSegment"
															styleId="businessSegment"
															value="${detailList[0].businessSegment}"
															styleClass="text">
															<html:option value="">--Select--</html:option>
															<logic:present name="businessSegmentList">
																<logic:notEmpty name="businessSegmentList">
																<html:optionsCollection name="businessSegmentList"
																	label="businessSegmentDesc" value="businessSegmentCode" />
																	</logic:notEmpty>
															</logic:present>
														</html:select>
													</td>
												</tr>
												<tr>
													
													<td>
														<bean:message key="industry" />
														<font color="red">*</font>
													</td>
													<td>

														<input type="text" name="industry" id="industry" size="20"
															value="${detailList[0].industry}" class="text"
															readonly="readonly" tabindex="-1"/>
														<logic:present name="strParentOption">
															<input type="hidden" name="lbxIndustry" id="lbxIndustry"
																class="text" value="${sessionScope.strParentOption}" />
														</logic:present>
														<logic:notPresent name="strParentOption">
															<input type="hidden" name="lbxIndustry" id="lbxIndustry"
																class="text" value="${detailList[0].lbxIndustry}" />
														</logic:notPresent>

														<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="industryButton"
															onclick="closeLovCallonLov1();openLOVCommon(10,'corporateDetailForm','industry','','subIndustry', 'lbxSubIndustry','','','hcommon');"/>
													</td>
													
													<td>
														<bean:message key="subIndustry" />
														<font color="red">*</font>
													</td>
													<td>
														<div id="subIndustrydetail">

															<input type="text" name="subIndustry" id="subIndustry"
																size="20" class="text" readonly="readonly"
																value="${detailList[0].subIndustry}" tabindex="-1"/>
															<input type="hidden" name="lbxSubIndustry"
																id="lbxSubIndustry" class="text"
																value="${detailList[0].lbxSubIndustry}" />
															<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="subIndustryButton"
																onclick="closeLovCallonLov('industry');openLOVCommon(11,'corporateDetailForm','subIndustry','industry','lbxSubIndustry', 'lbxIndustry','Please select Industry First!!!','','hcommon');"/>
														</div>

													</td>
												</tr>
												<tr>
													

													<td>
														<bean:message key="institutionEmail" />
													</td>
													<td>
														<html:text maxlength="100" property="institutionEmail"
															styleId="institutionEmail"
															value="${detailList[0].institutionEmail}"
															styleClass="text" />
															
										<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="EmailVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal>
													</td>
													<td>
														<bean:message key="webAddress" />
													</td>
													<td>
														<html:text property="webAddress" styleId="webAddress"
															maxlength="50" value="${detailList[0].webAddress}"
															styleClass="text" />
													</td>

												</tr>
			<!-- Nishant space starts -->	
						<tr>
							<td><bean:message key="lbl.natureOfBus" /></td>
							<td>
								<html:select property="natureOfBus"	styleId="natureOfBus" value="${detailList[0].natureOfBus}" styleClass="text" onchange="getIndutryPercent();">
									<html:option value="">--Select--</html:option>
										<logic:present name="natureOfBusList">
											<logic:notEmpty name="natureOfBusList">
												<html:optionsCollection name="natureOfBusList" label="natureOfBusDesc" value="natureOfBusCode" />
											</logic:notEmpty>
										</logic:present>
								</html:select>
							</td>
							<td><bean:message key="lbl.yearInBusiness" /></td>
							<td>
								<html:text maxlength="4" property="yearOfEstb" styleId="yearOfEstb" readonly="true" styleClass="text" value="${detailList[0].yearOfEstb}"  />
							</td>
						</tr>
						<tr>
							<td><bean:message key="lbl.shopNo" /></td>
							<td>
								<html:text maxlength="50" property="shopEstbNo"	maxlength="50" styleId="shopEstbNo" styleClass="text" value="${detailList[0].shopEstbNo}" />
							</td>
							<td><bean:message key="lbl.salesTax" /></td>
							<td>
								<html:text maxlength="50" property="salesTax"	maxlength="50" styleId="salesTax" styleClass="text" value="${detailList[0].salesTax}" />
							</td>
						</tr>
						<tr>
							<td><bean:message key="lbl.dgftNo" /></td>
							<td>
								<html:text maxlength="50" property="dgftNo"	maxlength="50" styleId="dgftNo" styleClass="text" value="${detailList[0].dgftNo}" />
							</td>
							<td><bean:message key="businessVintage" /></td>
							<td>
								Year<html:text property="noBVYears" maxlength="3" styleId="noBVYears" value="${detailList[0].noBVYears}" styleClass="text7" onkeypress="return isNumberKey(event);" style="text-align: right"/>
								Month<html:select  property="noBVMonths" styleId="noBVMonths"  value="${detailList[0].noBVMonths}" styleClass="gcdAddress">
									<html:option value="0">0</html:option>
									<html:option value="1">1</html:option>
									<html:option value="2">2</html:option>
									<html:option value="3">3</html:option>
									<html:option value="4">4</html:option>
									<html:option value="5">5</html:option>
									<html:option value="6">6</html:option>
									<html:option value="7">7</html:option>
									<html:option value="8">8</html:option>
									<html:option value="9">9</html:option>
									<html:option value="10">10</html:option>
									<html:option value="11">11</html:option>
								</html:select>
							</td>
						</tr>
		<!-- Nishant space end -->			
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.otherRelationShip" />
												
											</td>
											<td nowrap="nowrap">
												 
									              <html:select property="otherRelationShip" styleId="otherRelationShip" styleClass="text" value="${detailList[0].otherRelationShip}" onchange="return activeSupplier(this.value);" >
									              	<html:option value="CS">Customer</html:option>
									              	<html:option value="SU">Supplier</html:option>
									              	<html:option value="MF">Manufacturer</html:option>
									              </html:select>
											</td>
											<td nowrap="nowrap">
											
											<logic:notPresent name="detailList">
												<logic:empty name="detailList">
											
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
												</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/><font color="red">*</font>
												</div>
												</logic:empty>
											</logic:notPresent>
													
											<logic:present name="detailList">
												<logic:notEmpty name="detailList">
													<logic:equal name="otherRelationShip" value='CS'>
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/>
											
													</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/>
											
													</div>
											</logic:equal>
											<logic:notEqual name="otherRelationShip" value='CS'>
												<logic:equal name="otherRelationShip"  value='SU'>
													<div id="supplierLableDiv">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
													<div id="manufactLableDiv" style="display:none">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
												</logic:equal>
												<logic:equal name="otherRelationShip" value='MF'>
													<div id="manufactLableDiv">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
													<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
												</logic:equal>
											</logic:notEqual>
												</logic:notEmpty>
											</logic:present>		
																						
											</td>
											<td nowrap="nowrap">
											<logic:present name="detailList">
												<logic:notEmpty name="detailList">
													<logic:notEqual name="otherRelationShip"  value='CS'>
												<div id="supplierDiv">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'corporateDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
											</logic:notEqual>
											<logic:equal name="otherRelationShip" value='CS'>
												<div id="supplierDiv" style="display:none">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'corporateDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
											</logic:equal>
												</logic:notEmpty>
											</logic:present>
											
											
											<logic:notPresent name="detailList">
												<logic:empty name="detailList">
													<div id="supplierDiv" style="display:none">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'corporateDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
												</logic:empty>
											</logic:notPresent>
											
												
											</td>
										</tr>
												
												<tr>
													
													<td>
														<bean:message key="referredBy" />
													</td>
													<td>
														<html:text property="referredBy" maxlength="25"
															styleId="referredBy" value="${detailList[0].referredBy}"
															styleClass="text"  onkeyup="return upperMe('referredBy');" />
													</td>
													
													<td>
														<bean:message key="lbl.IndustryPercent" />
													</td>
													<td>
														<html:text property="industryPercent" 
															styleId="industryPercent" value="${detailList[0].industryPercent}"
															styleClass="text"  readonly="true" />
													</td>
												</tr>
											<tr>
											<td><bean:message key="lbl.projectedSales" /></td>
											<%-- <td><html:text property="projectedSales" styleClass="text" styleId="projectedSales" value="${detailList[0].projectedSales}" maxlength="100" /></td> --%>
											<td><html:text property="projectedSales" maxlength="25"
															styleId="projectedSales" value="${detailList[0].projectedSales}"
															styleClass="text" />
											
											
											
											<td><bean:message key="lbl.projectedExports" /></td>
											<td><html:text property="projectedExports" styleClass="text" styleId="projectedExports" value="${detailList[0].projectedExports}" maxlength="100" /></td>
											</tr>
											<tr>
											<td><bean:message key="lbl.riskCategory" /><font color="red">*</font></td>
											<td>
														<html:select property="riskCategory"
															styleId="riskCategory"
															value="${detailList[0].riskCategory}"
															styleClass="text">
															<option value="">
																-- Select --
															</option>
															<logic:present name="riskCategoryList">
															<logic:notEmpty name="riskCategoryList">
																<html:optionsCollection name="riskCategoryList"
																	label="riskCategoryDesc"
																	value="riskCategoryCode" />
																	</logic:notEmpty>
															</logic:present>
														</html:select>
													</td>
													<td><bean:message key="lbl.cgtmseIndustry" /></td>
													<td><input type="text" name="cgtmseIndustry" id="cgtmseIndustry" 
															value="${detailList[0].cgtmseIndustry}" class="text"
															readonly="readonly" tabindex="-1"/>
															<html:hidden property="lbxcgtmseIndustry" styleId="lbxcgtmseIndustry"  value="${detailList[0].lbxcgtmseIndustry}" styleClass="text" />
															<logic:equal name="functionId" value="3000206">
															<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="cgtmseIndustryButton"
															onclick="closeLovCallonLov1();openLOVCommon(25052,'corporateDetailForm','cgtmseIndustry','','', '','','','lbxcgtmseIndustry');"/>
														</logic:equal>
															</td>
										</tr>
												<logic:present name="viewBlackList">
												<tr>
													<td>
														<bean:message key="blackListed" />
													</td>
													<td>

														<logic:present name="detailList">
															<logic:iterate name="detailList" id="subdetailList">
																<logic:equal name="subdetailList" property="blackListed"
																	value="N">
																	<html:checkbox property="blackListed"
																		styleId="blackListed" onclick="blacklist();" />
																</logic:equal>
																<logic:equal name="subdetailList" property="blackListed"
																	value="Y">
																	<input type="checkbox" name="blackListed"
																		id="blackListed" checked="checked"
																		onclick="blacklist();" />
																</logic:equal>
															</logic:iterate>
														</logic:present>
														<logic:notPresent name="detailList">
															<html:checkbox property="blackListed"
																styleId="blackListed" onclick="blacklist();" />



														</logic:notPresent>
													</td>

													<td>
														<bean:message key="reasonForBlackListing" />
													</td>

													<td>


														<logic:present name="detailList">
															<logic:iterate name="detailList" id="subdetailList">
																<logic:equal name="subdetailList" property="blackListed"
																	value="Y">
																	<input type="hidden" id="reason" name="reason" style=""
																		value="" readonly="readonly" />
																	<html:text maxlength="100"
																		property="reasonForBlackListed"
																		styleId="reasonForBlackListed" styleClass="text"
																		value="${detailList[0].reasonForBlackListed}"
																		readonly="true" />
																	<html:button property="lovButton" value=" " styleClass="lovbutton"
																		onclick="closeLovCallonLov1();openLOVCommon(138,'corporateDetailForm','reasonForBlackListed','','', '','','','reason');"/>



													</logic:equal>
													<logic:equal name="subdetailList" property="blackListed"
														value="N">

														<input type="hidden" id="reason" name="reason" style=""
															value="" readonly="readonly" />
														<html:text maxlength="100" disabled="true"
															property="reasonForBlackListed"
															styleId="reasonForBlackListed" styleClass="text"
															value="${detailList[0].reasonForBlackListed}"
															readonly="true" />
														<html:button property="lovButton" value=" " styleClass="lovbutton"
															onclick="closeLovCallonLov1();openLOVCommon(138,'corporateDetailForm','reasonForBlackListed','','', '','','','reason');"/>
													</logic:equal>
													</logic:iterate>
													</logic:present>
													
													<logic:notPresent name="detailList">

															<input type="hidden" id="reason" name="reason" style=""
																value="" readonly="readonly" />
															<html:text maxlength="100" maxlength="100"
																property="reasonForBlackListed"
																styleId="reasonForBlackListed" styleClass="text"
																value="" readonly="true" />
															<html:button property="lovButton" value=" " styleClass="lovbutton"
																onclick="closeLovCallonLov1();openLOVCommon(138,'corporateDetailForm','reasonForBlackListed','','', '','','','reason');"/>		
														</logic:notPresent>
												
													
													<input type="hidden" name="customerStatus"
														value="${detailList[0].pagestatus}" />
													</td>

													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
												</tr>
												</logic:present>
												<tr>

													<td align="left">
														<logic:notPresent name="detailList">
															<button type="button" id="save"
																onclick="return saveCorporateDetails();"
																class="topformbutton2" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
														</logic:notPresent>
														<logic:present name="detailList">
														 <logic:equal name="existingCustomer" value="NO"> 
															<button type="button" id="save"
																onclick="return updateCorporateDetails();"
																class="topformbutton2" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
															 <logic:notEqual name="EmailVerificationFlag" value="Y">
															 <button type="button" name="button3"  class="topformbutton2" 
															 title="Alt+W" accesskey="W" onclick="return verifyEmailCorporate();"><bean:message key="button.verifyEmail" /></button>
															 </logic:notEqual>	
														</logic:equal> 
														<logic:equal name="functionId" value="2000310">
														<button type="button" id="save"
																onclick="return updateCorporateDetails();"
																class="topformbutton2" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
														</logic:equal>
															<logic:notPresent name="updateFlag">
															<logic:notPresent name="SRCDealLoan">															
																<button type="button"
																	id="saveAndForward"
																	onclick="location.href='<%=request.getContextPath()%>/corporateDetailAction.do?method=saveAndForwardCorpDetails' "
																	class="topformbutton3" accesskey="F" title="Alt+F"><bean:message key="button.forward" /></button>
																	<logic:notEqual name="EmailVerificationFlag" value="Y">
																	 <button type="button" name="button3"  class="topformbutton2" 
																		 title="Alt+W" accesskey="W" onclick="return verifyEmailCorporate();"><bean:message key="button.verifyEmail" /></button>
																		 </logic:notEqual>
															</logic:notPresent>
															</logic:notPresent>

														</logic:present>
														<logic:notPresent name="detailList">
															<button class="topformbutton2" type="button"
																onclick="corporateClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
														</logic:notPresent>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</fieldset>

						</logic:present>
						<logic:notPresent name="update">
						
							<fieldset>

								<legend>
									<bean:message key="corporate.details" />
								</legend>
								<table cellspacing="0" cellpadding="0" width="100%" border="0">
										<tr>
											<td>
												<table cellspacing="1" cellpadding="1" width="100%" border="0">
                                    
													<tr>
										<%-- Start By Prashant --%>
										<logic:present name="applType">
										 

										
										<logic:present name="groupTypeActivated">
													<td>
														<bean:message key="lbl.groupType" />
														<font color="red">*</font>
													</td>
														<td>
														 
														<html:select property="groupType" styleId="groupType" onchange="groupSelectInMaster();" styleClass="text" value="${detailList[0].groupType}"> 
															<html:option value="N">New</html:option> 
															<html:option value="E">Existing</html:option> 
														</html:select>
														
														</td>
									   </logic:present>

									   </logic:present>
									   <logic:present name="applType">
                                             
												<logic:present name="detailList">
												
													<logic:iterate name="detailList" id="subdetailList" length="1">	
													  <logic:equal name="subdetailList" property="groupType" value="N">
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															<div id="groupLov" style="display:none">
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
															</div>	
																															
														<div id="groupText" >
						                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${detailList[0].groupNameText}" maxlength="50"/>
										                </div>
																	
																</td>
														</logic:equal>
														<logic:equal name="subdetailList" property="groupType" value="E">
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															<div id="groupLov" >
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
															</div>	
																															
														<div id="groupText" style="display:none">
						                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${detailList[0].groupNameText}" maxlength="50"/>
										                </div>
																	
																</td>
														</logic:equal>
													</logic:iterate>
													
														
													
												</logic:present>	
												<logic:notPresent name="detailList">	
													
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
													<logic:present name="groupTypeActivated">	
														<td>
															<div id="groupLov" style="display:none">
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
															</div>	
																												
															<div id="groupText" >
							                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${detailList[0].groupNameText}" maxlength="50"/>
											                </div>
											               </td>
													</logic:present>
													<td>
													<logic:notPresent name="groupTypeActivated">
													<html:hidden property="groupType" styleId="groupType"
																	styleClass="text" value="E" />
														 <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
													</logic:notPresent>		
																</td>
														
												</logic:notPresent>	

												 
										</logic:present>	
										<logic:notPresent name="applType">
										
											<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															<html:hidden property="groupType" styleId="groupType"
																	styleClass="text" value="E" />
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hcommon');"/>
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
																
																															
														
																	
																</td>
										
										</logic:notPresent>	
										
											<%-- End By Prashant --%>		
														
													</tr>

													<tr>
														<td>
															<input type="hidden" name="pagestatus" value="Corporate" />
															<bean:message key="corporateName" />
															<font color="red">*</font>
														</td>
														<td>
															<html:text property="corporateName" maxlength="250"
																styleId="corporateName" styleClass="text"
																onchange="return upperMe('corporateName');"
																value="${detailList[0].corporateName}" />
														</td>
														<td>
															<bean:message key="customerCategory" />
														</td>
														<td>
															<html:select property="corporateCategory"
																styleId="corporateCategory" styleClass="text"
																value="${detailList[0].corporateCategory}">
																<option value="">
																	-- Select --
																</option>
																<logic:present name="customerCategory">
																<logic:notEmpty name="customerCategory">>
																	<html:optionsCollection name="customerCategory"
																		label="customerCategoryDesc"
																		value="customerCategoryCode" />
																		</logic:notEmpty>
																</logic:present>
															</html:select>
														</td>
													</tr>
													<tr>
														<td>
															<bean:message key="incorporationDate" />
														<bean:message key="lbl.dateFormat(dd-mm-yyyy)" /><font color="red">*</font>
														</td>
														<td>
															<html:text property="incorporationDate" maxlength="10"
																styleId="incorporationDate" styleClass="text"
																value="${detailList[0].incorporationDate}"
																onchange="checkDate('incorporationDate');yearsInBusiness();" />
														</td>
														<td>
															<bean:message key="constitution" />
															<font color="red">*</font>
														</td>
														<td>
															<html:select property="constitution"
																styleId="constitution" onchange="appendZero();"
																value="${detailList[0].constitution}" styleClass="text">
																<option value="">
																	--Select--
																</option>
																<logic:present name="constitutionlist">
																	<logic:notEmpty name="constitutionlist">
																	<html:optionsCollection name="constitutionlist"
																		label="constitution" value="contitutionCode" />
																		</logic:notEmpty>

																</logic:present>
															</html:select>
														</td>

													</tr>
													<tr>
														
														<td>
															<bean:message key="registrationNo" /><font color="red">*</font>
															
														</td>
														<td>
															<html:text property="registrationNo"
																styleId="registrationNo" maxlength="25"
																styleClass="text"
																value="${detailList[0].registrationNo}"
																 />
														
														<input type="hidden" name="hiApplType" id="hiApplType"
															value="<%=session.getAttribute("applType")%>" />
														</td>
														
														<td>
															<bean:message key="pan" />
															<font color="red" id="hid">*</font> </td>
														<td>
															<html:text property="pan" styleClass="text"
																maxlength="10" styleId="pan" styleClass="text"
																value="${detailList[0].pan}"
																onkeypress="return isAlphNumericKey(event);" onchange="return upperMe('pan');" />
														</td>
													</tr>

													<tr>
														
														<td>
															<bean:message key="vatRegNo" />
														</td>
														<td>
															<html:text property="vatRegNo" maxlength="25"
																styleId="vatRegNo" styleClass="text"
																value="${detailList[0].vatRegNo}"
																onkeypress="return isAlphNumericKey(event);" />
														</td>
														<td>
															<bean:message key="businessSegment" />
															<font color="red">*</font>
														</td>
														<td>
															<html:select property="businessSegment"
																styleId="businessSegment" styleClass="text"
																value="${detailList[0].businessSegment}">
																<html:option value="">--Select--</html:option>
																<logic:present name="businessSegmentList">
																	<logic:notEmpty name="businessSegmentList">
																	<html:optionsCollection name="businessSegmentList"
																		label="businessSegmentDesc"
																		value="businessSegmentCode" />
																	</logic:notEmpty>
																</logic:present>
															</html:select>
														</td>
													</tr>
													<tr>
														
														<td>
															<bean:message key="industry" />
															<font color="red">*</font>
														</td>
														<td>

															<input type="text" name="industry" id="industry"
																size="20" value="${detailList[0].industry}" class="text"
																readonly="readonly" tabindex="-1"/>
															
														
																<input type="hidden" name="lbxIndustry" id="lbxIndustry"
																	class="text" value="${detailList[0].lbxIndustry}" />
														

															<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="industryButton"
																onclick="closeLovCallonLov1();openLOVCommon(10,'corporateDetailForm','industry','','subIndustry', 'lbxSubIndustry','','','hcommon');"/>
														</td>
														
														<td>
															<bean:message key="subIndustry" />
															<font color="red">*</font>
														</td>
														<td>

															<div id="subIndustrydetail">

																<input type="text" name="subIndustry" id="subIndustry"
																	size="20" class="text" readonly="readonly" tabindex="-1"
																	value="${detailList[0].subIndustry}" />
																<input type="hidden" name="lbxSubIndustry"
																	id="lbxSubIndustry" class="text"
																	value="${detailList[0].lbxSubIndustry}" />
																<html:button property="lovButton" value=" " styleClass="lovbutton"  styleId="subIndustryButton"
																	onclick="closeLovCallonLov('industry');openLOVCommon(11,'corporateDetailForm','subIndustry','industry','lbxSubIndustry', 'lbxIndustry','Please select Industry First!!!','','hcommon');"/>
															</div>

														</td>
													</tr>

													<tr>
														

														<td>
															<bean:message key="institutionEmail" />
														</td>
														<td>
															<html:text maxlength="100" property="institutionEmail"
																styleId="institutionEmail" styleClass="text"
																value="${detailList[0].institutionEmail}" />
											
											<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="EmailVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal>
														</td>
                                                         <td>
															<bean:message key="webAddress" />
														</td>
														<td>
															<html:text maxlength="50" property="webAddress"
																maxlength="50" styleId="webAddress" styleClass="text"
																value="${detailList[0].webAddress}" />
														</td>
													</tr>
			<!-- Nishant space starts -->	
						<tr>
							<td><bean:message key="lbl.natureOfBus" /></td>
							<td>
								<html:select property="natureOfBus"	styleId="natureOfBus" value="${detailList[0].natureOfBus}" styleClass="text" onchange="getIndutryPercent();">
									<html:option value="">--Select--</html:option>
										<logic:present name="natureOfBusList">
											<logic:notEmpty name="natureOfBusList">
												<html:optionsCollection name="natureOfBusList" label="natureOfBusDesc" value="natureOfBusCode" />
											</logic:notEmpty>
										</logic:present>
								</html:select>
							</td>
							<td><bean:message key="lbl.yearInBusiness" /></td>
							<td>
								<html:text maxlength="4" property="yearOfEstb" styleId="yearOfEstb"  readonly="true" styleClass="text" value="${detailList[0].yearOfEstb}"  />
							</td>
						</tr>
						<tr>
							<td><bean:message key="lbl.shopNo" /></td>
							<td>
								<html:text maxlength="50" property="shopEstbNo"	maxlength="50" styleId="shopEstbNo" styleClass="text" value="${detailList[0].shopEstbNo}" />
							</td>
							<td><bean:message key="lbl.salesTax" /></td>
							<td>
								<html:text maxlength="50" property="salesTax"	maxlength="50" styleId="salesTax" styleClass="text" value="${detailList[0].salesTax}" />
							</td>
						</tr>
						<tr>
							<td><bean:message key="lbl.dgftNo" /></td>
							<td>
								<html:text maxlength="50" property="dgftNo"	maxlength="50" styleId="dgftNo" styleClass="text" value="${detailList[0].dgftNo}" />
							</td>
							<td><bean:message key="businessVintage" /></td>
							<td>
								Year<html:text property="noBVYears" maxlength="3" styleId="noBVYears" value="${detailList[0].noBVYears}" styleClass="text7" onkeypress="return isNumberKey(event);" style="text-align: right"/>
								Month<html:select  property="noBVMonths" styleId="noBVMonths"  value="${detailList[0].noBVMonths}" styleClass="gcdAddress">
									<html:option value="0">0</html:option>
									<html:option value="1">1</html:option>
									<html:option value="2">2</html:option>
									<html:option value="3">3</html:option>
									<html:option value="4">4</html:option>
									<html:option value="5">5</html:option>
									<html:option value="6">6</html:option>
									<html:option value="7">7</html:option>
									<html:option value="8">8</html:option>
									<html:option value="9">9</html:option>
									<html:option value="10">10</html:option>
									<html:option value="11">11</html:option>
								</html:select>
							</td>
						</tr>
		<!-- Nishant space end -->
													
										<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.otherRelationShip" />
												
											</td>
											<td nowrap="nowrap">
												 
									              <html:select property="otherRelationShip" styleId="otherRelationShip" styleClass="text" value="${detailList[0].otherRelationShip}" onchange="return activeSupplier(this.value);" >
									              	<html:option value="CS">Customer</html:option>
									              	<html:option value="SU">Supplier</html:option>
									              	<html:option value="MF">Manufacturer</html:option>
									              </html:select>
											</td>
											<td nowrap="nowrap">
											
											<logic:notPresent name="detailList">
												<logic:empty name="detailList">
											
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
												</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/><font color="red">*</font>
												</div>
												</logic:empty>
											</logic:notPresent>
													
											<logic:present name="detailList">
												<logic:notEmpty name="detailList">
													<logic:equal name="otherRelationShip" value='CS'>
														<div id="supplierLableDiv" style="display:none">
															<bean:message key="lbl.supplier"/>
														</div>
														<div id="manufactLableDiv" style="display:none">
															<bean:message key="lbl.manufact"/>
														</div>
													</logic:equal>
											<logic:notEqual name="otherRelationShip" value='CS'>
												<logic:equal name="otherRelationShip"  value='SU'>
													<div id="supplierLableDiv">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
													<div id="manufactLableDiv" style="display:none">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
												</logic:equal>
												<logic:equal name="otherRelationShip" value='MF'>
													<div id="manufactLableDiv">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
													<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
												</logic:equal>
											</logic:notEqual>
												</logic:notEmpty>
											</logic:present>		
																						
											</td>
											<td nowrap="nowrap">
											<logic:present name="detailList">
												<logic:notEmpty name="detailList">
													<logic:notEqual name="otherRelationShip"  value='CS'>
												<div id="supplierDiv">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'corporateDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
											</logic:notEqual>
											<logic:equal name="otherRelationShip" value='CS'>
												<div id="supplierDiv" style="display:none">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'corporateDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
											</logic:equal>
												</logic:notEmpty>
											</logic:present>
											
											
											<logic:notPresent name="detailList">
												<logic:empty name="detailList">
													<div id="supplierDiv" style="display:none">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
					              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(415,'corporateDetailForm','lbxBusinessPartnearHID','otherRelationShip', 'lbxBusinessPartnearHID','otherRelationShip','','','businessPartnerTypeDesc','businessPartnerType','businessPartnerName');" value=" " styleClass="lovbutton"></html:button>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
												</logic:empty>
											</logic:notPresent>
											
												
											</td>
										</tr>
													
													<tr>
														
														<td>
															<bean:message key="referredBy" />
														</td>
														<td>
														
															<html:text property="referredBy" maxlength="25"
																styleId="referredBy" styleClass="text"
																value="${detailList[0].referredBy}" onkeyup="return upperMe('referredBy');"/>
														</td>
														<td>
														<bean:message key="lbl.IndustryPercent" />
													</td>
													<td>
														<html:text property="industryPercent" 
															styleId="industryPercent" value="${detailList[0].industryPercent}"
															styleClass="text"  readonly="true" />
													</td>
													</tr>
											<tr>
											<td><bean:message key="lbl.projectedSales" /></td>
										<%-- 	<td><html:text property="projectedSales" styleClass="text" styleId="projectedSales" value="${detailList[0].projectedSales}" maxlength="100" /></td>
										 --%>
										 <td><html:text property="projectedSales" maxlength="25"
															styleId="projectedSales" value="${detailList[0].projectedSales}"
															styleClass="text" />
																
											<td><bean:message key="lbl.projectedExports" /></td>
											<td><html:text property="projectedExports" styleClass="text" styleId="projectedExports" value="${detailList[0].projectedExports}" maxlength="100" /></td>
											</tr>
											<tr>
											<td><bean:message key="lbl.riskCategory" /><font color="red">*</font></td>
											<td><html:select property="riskCategory" value="${detailList[0].riskCategory}" styleClass="text" styleId="riskCategory">
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
															value="${detailList[0].cgtmseIndustry}" class="text"
															readonly="readonly" tabindex="-1"/>
															<html:hidden property="lbxcgtmseIndustry" styleId="lbxcgtmseIndustry"  value="${detailList[0].lbxcgtmseIndustry}" styleClass="text" />
															<logic:equal name="functionId" value="3000206">
															<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="cgtmseIndustryButton"
															onclick="closeLovCallonLov1();openLOVCommon(25052,'corporateDetailForm','cgtmseIndustry','','', '','','','lbxcgtmseIndustry');"/>
															</logic:equal>
															</td>
										</tr>

													<tr>

														<td align="left" colspan="3">
															<logic:notPresent name="detailList">

																<button  id="save" type="button"
																	onclick="return saveCorporateDetails();"
																	class="topformbutton2" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
															</logic:notPresent>
															<logic:present name="detailList">
																<logic:notPresent name="insert">
																<%-- <logic:equal name="existingCustomer" value="NO"> --%>
																	<button type="button" id="save"
																		onclick="return updateCorporateDetails();"
																		class="topformbutton2" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
																		<logic:notEqual name="EmailVerificationFlag" value="Y">
																		 <button type="button" name="button3"  class="topformbutton2" 
																		 title="Alt+W" accesskey="W" onclick="return verifyEmailCorporate();"><bean:message key="button.verifyEmail" /></button>
																		 </logic:notEqual>	
															<%-- 	</logic:equal> --%>
																	<logic:notPresent name="pageStatuss">
																	<logic:notPresent name="SRCDealLoan">
																		<button type="button"
																			id="saveAndForward"
																			onclick="location.href='<%=request.getContextPath()%>/corporateDetailAction.do?method=saveAndForwardCorpDetails' "
																			class="topformbutton3" accesskey="F" title="Alt+F"><bean:message key="button.forward" /></button>
																			<logic:notEqual name="EmailVerificationFlag" value="Y">
																			 <button type="button" name="button3"  class="topformbutton2" 
																		 title="Alt+W" accesskey="W" onclick="return verifyEmailCorporate();"><bean:message key="button.verifyEmail" /></button>
																		 </logic:notEqual>
																	</logic:notPresent>
																	</logic:notPresent>
																</logic:notPresent>
																<logic:present name="insert">
																	<button  id="save" type="button"
																		onclick="return saveCorporateDetails();"
																		class="topformbutton2" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
																</logic:present>

															</logic:present>
															<logic:notPresent name="detailList">
																<button type="button"
																	class="topformbutton2" onclick="corporateClear();" 
																	accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
															</logic:notPresent>
														</td>

													</tr>
												</table>
											</td>
										</tr>
								</table>
							</fieldset>
						</logic:notPresent>

					</html:form>
				</div>
			</div>

			<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Corporate Details!!!");
	
    </script>


			</logic:present>

		</logic:notPresent>

		<logic:present name="approve">
		
			<logic:present name="back">

				<script type="text/javascript">
   alert("You can't move without saving before Corporate Details!!!");
   </script>
			</logic:present>
			<div id='centercolumn'>
				<div id=revisedcontainer>
					<html:form action="/corporateDetailProcessAction"
						styleId="corporateDetailForm" method="post">

						<fieldset>
							<legend>
								<bean:message key="corporate.details" />
							</legend>
							<table cellspacing="0" cellpadding="0" width="100%" border="0">
									<tr>
										<td>
											<input type="hidden" name="hcommon" id="hcommon" />
											<table cellspacing="1" cellpadding="1" width="100%" border="0">
												<logic:present name="detailList">
													<tr>
														<td>
															<bean:message key="corporateCode" />
														</td>
														<td>
															<html:text property="corporateCode"
																styleId="corporateCode" disabled="true" tabindex="-1"
																styleClass="text" value="${detailList[0].corporateCode}" />
														</td>
														<td><bean:message key="lbl.UCIC" /><span></span>
												</td>
												<td><html:text property="ucic"
														styleId="ucic" styleClass="text"
														value="${detailList[0].ucic}" disabled="true" 
														tabindex="-1" /></td>
													</tr>
													<tr>
														<%-- Start By Prashant --%>
												<logic:present name="groupTypeActivated">		
													<td>
														<bean:message key="lbl.groupType" />
														<font color="red">*</font>
													</td>
														<td>
														 
														<html:select property="groupType" styleId="groupType" onchange="groupSelectInMaster();" styleClass="text" disabled="true" value="${detailList[0].groupType}"> 
															<html:option value="N">New</html:option> 
															<html:option value="E">Existing</html:option> 
														</html:select>
														
														</td>
													</logic:present >
													<logic:present name="viewBlackList">
																<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
														
																															
													
																	
																</td>
													</logic:present>
													<logic:iterate name="detailList" id="subdetailList" length="1">	
													  <logic:equal name="subdetailList" property="groupType" value="N">
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															
														
						                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${detailList[0].groupNameText}" maxlength="50" readonly="readonly"/>
										              
																	
																</td>
														</logic:equal>
														<logic:equal name="subdetailList" property="groupType" value="E">
														<td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<td>
															
															   <input type="text" id="groupName" name="groupName"
																class="text" value="${detailList[0].groupName}"
																readonly="readonly" tabindex="-1"/>
																
																<html:hidden property="hGroupId" styleId="hGroupId"
																	styleClass="text" value="${detailList[0].hGroupId}" />
														
																															
													
																	
																</td>
														</logic:equal>
													</logic:iterate>
													<%-- End By Prashant --%>
													</tr>
												</logic:present>
												<tr>
													<td>
														<input type="hidden" name="pagestatus" value="Corporate" />
														<bean:message key="corporateName" />
														<font color="red">*</font>
													</td>
													<td>
														<html:text maxlength="250" property="corporateName"
															styleId="corporateName" tabindex="-1"
															value="${detailList[0].corporateName}" disabled="true"
															styleClass="text" />
													</td>
													<td>
														<bean:message key="customerCategory" />
													</td>
													<td>
														<html:select property="corporateCategory"
															styleId="corporateCategory" disabled="true"
															value="${detailList[0].corporateCategory}"
															styleClass="text" tabindex="-1">
															<option value="">
																-- Select --
															</option>
															<logic:present name="customerCategory">
															<logic:notEmpty name="customerCategory">
																<html:optionsCollection name="customerCategory"
																	label="customerCategoryDesc"
																	value="customerCategoryCode" />
																	</logic:notEmpty>
															</logic:present>

														</html:select>
													</td>
												</tr>
												<tr>
													<td>
														<bean:message key="incorporationDate" />
														<bean:message key="lbl.dateFormat(dd-mm-yyyy)" /><font color="red">*</font>
													</td>
													<td>
														<input type="text" name="inc_date" id="inc_date"
															disabled="disabled" maxlength="10" tabindex="-1"
															value="${detailList[0].incorporationDate}" class="text" />
													</td>
													<td>
														<bean:message key="constitution" />
														<font color="red">*</font>
													</td>
													<td>
														<html:select property="constitution"
															styleId="constitution" disabled="true" tabindex="-1"
															value="${detailList[0].constitution}" styleClass="text"
															onchange="appendZero();">
															<option value="">
																--Select--
															</option>
															<logic:present name="constitutionlist">
																<logic:notEmpty name="constitutionlist">
																<html:optionsCollection name="constitutionlist"
																	label="constitution" value="contitutionCode" />
																</logic:notEmpty>
															</logic:present>
														</html:select>
													</td>

												</tr>
												<tr>
													
													<td>
														<bean:message key="registrationNo" /><font color="red">*</font>
														
													</td>
													<td>
														<html:text maxlength="25" property="registrationNo"
															styleId="registrationNo" disabled="true" tabindex="-1"
															value="${detailList[0].registrationNo}" styleClass="text"
															/>
													</td>
													<td>
														<bean:message key="pan" />
													</td>
													<td>
														<html:text property="pan" maxlength="10" styleClass="text"
															styleId="pan" disabled="true" tabindex="-1"
															value="${detailList[0].pan}" styleClass="text"
															onkeypress="return isAlphNumericKey(event);" onchange="return upperMe('pan');"/>
													</td>
												</tr>

												<tr>
													
													<td>
														<bean:message key="vatRegNo" />
													</td>
													<td>
														<html:text property="vatRegNo" maxlength="25"
															styleId="vatRegNo" disabled="true" tabindex="-1"
															value="${detailList[0].vatRegNo}" styleClass="text"
															onkeypress="return isAlphNumericKey(event);" />
													</td>
													<td>
														<bean:message key="businessSegment" />
														<font color="red">*</font>
													</td>
													<td>
														<html:select property="businessSegment" tabindex="-1"
															styleId="businessSegment" disabled="true"
															value="${detailList[0].businessSegment}"
															styleClass="text">
															<html:option value="">--Select--</html:option>
															<logic:present name="businessSegmentList">
																<logic:notEmpty name="businessSegmentList">
																<html:optionsCollection name="businessSegmentList"
																	label="businessSegmentDesc" value="businessSegmentCode" />
																</logic:notEmpty>
															</logic:present>
														</html:select>
													</td>
												</tr>
												<tr>
													
													<td>
														<bean:message key="industry" />
														<font color="red">*</font>
													</td>
													<td>

														<input type="text" name="industry" id="industry" size="20"
															value="${detailList[0].industry}" class="text"
															disabled="disabled" tabindex="-1"/>
														<input type="hidden" name="lbxIndustry" id="lbxIndustry"
															class="text" value="${detailList[0].lbxIndustry}" />
													</td>
													<td>
														<bean:message key="subIndustry" />
														<font color="red">*</font>
													</td>
													<td>

														<div id="subIndustrydetail">

															<input type="text" name="subIndustry" id="subIndustry"
																size="20" class="text" disabled="disabled"
																value="${detailList[0].subIndustry}" tabindex="-1"/>
															<input type="hidden" name="lbxSubIndustry"
																id="lbxSubIndustry" class="text"
																value="${detailList[0].lbxSubIndustry}" />


														</div>

													</td>
												</tr>

												<tr>
													

													<td>
														<bean:message key="institutionEmail" />
													</td>
													<td>
														<html:text maxlength="100" property="institutionEmail"
															styleId="institutionEmail" disabled="true"
															value="${detailList[0].institutionEmail}"
															styleClass="text" tabindex="-1"/>
				
													<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="EmailVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal>
													</td>
													<td>
														<bean:message key="webAddress" />
													</td>
													<td>
														<html:text maxlength="50" property="webAddress"
															styleId="webAddress" disabled="true" tabindex="-1"
															value="${detailList[0].webAddress}" styleClass="text" />
													</td>
												</tr>
					<!-- Nishant space starts -->	
						<tr>
							<td><bean:message key="lbl.natureOfBus" /></td>
							<td>
								<html:select property="natureOfBus"	styleId="natureOfBus" disabled="true" value="${detailList[0].natureOfBus}" styleClass="text">
									<html:option value="">--Select--</html:option>
										<logic:present name="natureOfBusList">
											<logic:notEmpty name="natureOfBusList">
												<html:optionsCollection name="natureOfBusList" label="natureOfBusDesc" value="natureOfBusCode" />
											</logic:notEmpty>
										</logic:present>
								</html:select>
							</td>
							<td><bean:message key="lbl.yearInBusiness" /></td>
							<td>
								<html:text maxlength="4" property="yearOfEstb"	styleId="yearOfEstb" readonly="true" styleClass="text" value="${detailList[0].yearOfEstb}" />
							</td>
						</tr>
						<tr>
							<td><bean:message key="lbl.shopNo" /></td>
							<td>
								<html:text maxlength="50" property="shopEstbNo"	maxlength="50" readonly="true" styleId="shopEstbNo" styleClass="text" value="${detailList[0].shopEstbNo}" />
							</td>
							<td><bean:message key="lbl.salesTax" /></td>
							<td>
								<html:text maxlength="50" property="salesTax"	maxlength="50" readonly="true" styleId="salesTax" styleClass="text" value="${detailList[0].salesTax}" />
							</td>
						</tr>
						<tr>
							<td><bean:message key="lbl.dgftNo" /></td>
							<td>
								<html:text maxlength="50" property="dgftNo"	maxlength="50" styleId="dgftNo" readonly="true" styleClass="text" value="${detailList[0].dgftNo}" />
							</td>
							<td><bean:message key="businessVintage" /></td>
							<td>
								Year<html:text property="noBVYears" maxlength="3" styleId="noBVYears" value="${detailList[0].noBVYears}" disabled="true" styleClass="text7" onkeypress="return isNumberKey(event);" style="text-align: right"/>
								Month<html:select  property="noBVMonths" styleId="noBVMonths"  value="${detailList[0].noBVMonths}" disabled="true" styleClass="gcdAddress">
									<html:option value="0">0</html:option>
									<html:option value="1">1</html:option>
									<html:option value="2">2</html:option>
									<html:option value="3">3</html:option>
									<html:option value="4">4</html:option>
									<html:option value="5">5</html:option>
									<html:option value="6">6</html:option>
									<html:option value="7">7</html:option>
									<html:option value="8">8</html:option>
									<html:option value="9">9</html:option>
									<html:option value="10">10</html:option>
									<html:option value="11">11</html:option>
								</html:select>
							</td>
						</tr>
					<!-- Nishant space end -->	
												<tr>
													
													<td>
														<bean:message key="referredBy" />
													</td>
													<td>
														<html:text property="referredBy" maxlength="25"
															styleId="referredBy" disabled="true" tabindex="-1"
															value="${detailList[0].referredBy}" styleClass="text" onkeyup="return upperMe('referredBy');"/>
													</td>
													<td>
														<bean:message key="lbl.IndustryPercent" />
													</td>
													<td>
														<html:text property="industryPercent" 
															styleId="industryPercent" value="${detailList[0].industryPercent}"
															styleClass="text"  disabled="true" tabindex="-1" />
													</td>
												</tr>
												
														<tr>
											<td><bean:message key="lbl.projectedSales" /></td>
											<%-- <td><html:text property="projectedSales" styleClass="text" styleId="projectedSales" value="${detailList[0].projectedSales}" maxlength="100" /></td> --%>
											<td><html:text property="projectedSales" maxlength="25"
															styleId="projectedSales" value="${detailList[0].projectedSales}"
															styleClass="text" />
											
											
											
											<td><bean:message key="lbl.projectedExports" /></td>
											<td><html:text property="projectedExports" styleClass="text" styleId="projectedExports" value="${detailList[0].projectedExports}" maxlength="100" /></td>
											</tr>
											<tr>
											<td><bean:message key="lbl.riskCategory" /><font color="red">*</font></td>
											<td><html:select property="riskCategory" value="${detailList[0].riskCategory}" styleClass="text" styleId="riskCategory">
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
															value="${detailList[0].cgtmseIndustry}" class="text"
															readonly="readonly" tabindex="-1"/>
															<html:hidden property="lbxcgtmseIndustry" styleId="lbxcgtmseIndustry"  value="${detailList[0].lbxcgtmseIndustry}" styleClass="text" />
															
															</td>
										</tr>

												<tr>
													<td>
														<bean:message key="blackListed" />
													</td>
													<td>
														<logic:present name="detailList">
															<logic:iterate name="detailList" id="subdetailList">
																<logic:equal name="subdetailList" property="blackListed"
																	value="N">
																	<html:checkbox property="blackListed"
																		styleId="blackListed" disabled="true" />
																</logic:equal>
																<logic:equal name="subdetailList" property="blackListed"
																	value="Y">
																	<input type="checkbox" name="blackListed"
																		id="blackListed" disabled="disabled" checked="checked" />
																</logic:equal>
															</logic:iterate>
														</logic:present>

														<logic:notPresent name="detailList">
															<html:checkbox property="blackListed"
																styleId="blackListed" disabled="true" />
														</logic:notPresent>
													</td>

													<td>
														<bean:message key="reasonForBlackListing" />
													</td>
													<td>
														<html:text property="reasonForBlackListed" maxlength="100"
															styleId="reasonForBlackListed" styleClass="text"
															disabled="true"
															value="${detailList[0].reasonForBlackListed}" />
														<input type="hidden" name="customerStatus"
															value="${detailList[0].pagestatus}" />
													</td>

												</tr>
												
												<tr>
											<td nowrap="nowrap">
												<bean:message key="lbl.otherRelationShip" />
												
											</td>
											<td nowrap="nowrap">
												 
									              <html:select property="otherRelationShip" styleId="otherRelationShip" styleClass="text" value="${detailList[0].otherRelationShip}" disabled="true" >
									              	<html:option value="CS">Customer</html:option>
									              	<html:option value="SU">Supplier</html:option>
									              	<html:option value="MF">Manufacturer</html:option>
									              </html:select>
											</td>
											<td nowrap="nowrap">
											
											<logic:notPresent name="detailList">
												<logic:empty name="detailList">
											
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
												</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/><font color="red">*</font>
												</div>
												</logic:empty>
											</logic:notPresent>
													
											<logic:present name="detailList">
												<logic:notEmpty name="detailList">
													<logic:equal name="otherRelationShip" value='CS'>
												<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/>
											
													</div>
												<div id="manufactLableDiv" style="display:none">
													<bean:message key="lbl.manufact"/>
											
													</div>
											</logic:equal>
											<logic:notEqual name="otherRelationShip" value='CS'>
												<logic:equal name="otherRelationShip"  value='SU'>
													<div id="supplierLableDiv">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
													<div id="manufactLableDiv" style="display:none">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
												</logic:equal>
												<logic:equal name="otherRelationShip" value='MF'>
													<div id="manufactLableDiv">
														<bean:message key="lbl.manufact"/><font color="red">*</font>
													</div>
													<div id="supplierLableDiv" style="display:none">
													<bean:message key="lbl.supplier"/><font color="red">*</font>
											
													</div>
												</logic:equal>
											</logic:notEqual>
												</logic:notEmpty>
											</logic:present>		
																						
											</td>
											<td nowrap="nowrap">
											<logic:present name="detailList">
												<logic:notEmpty name="detailList">
													<logic:notEqual name="otherRelationShip"  value='CS'>
												<div id="supplierDiv">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
											</logic:notEqual>
											<logic:equal name="otherRelationShip" value='CS'>
												<div id="supplierDiv" style="display:none">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
											</logic:equal>
												</logic:notEmpty>
											</logic:present>
											
											
											<logic:notPresent name="detailList">
												<logic:empty name="detailList">
													<div id="supplierDiv" style="display:none">

													<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${detailList[0].businessPartnerTypeDesc}" tabindex="-1"/>
													<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
					              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              										<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
              										
												</div>
												<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${detailList[0].lbxBusinessPartnearHID}" />
											
												</logic:empty>
											</logic:notPresent>
											
												
											</td>
										</tr>
												
												<tr>
													<td>
														<logic:notPresent name="pageInfo">
															<logic:notPresent name="underWriter">

																<button id="approve" accesskey="A" title="Alt+A" type="button"
																	onclick="return approveCustomer();" class="topformbutton2" > <bean:message key="button.approve" /></button>
															</logic:notPresent>

														</logic:notPresent>
														<logic:equal name="functionId" value="3000296">
													<logic:notEqual name="EmailVerificationFlag" value="Y">		
											<button type="button" name="button3"  class="topformbutton2" 
															 title="Alt+W" accesskey="W" onclick="return verifyEmailCorporate();"><bean:message key="button.verifyEmail" /></button>
													</logic:notEqual>
													</logic:equal>
													</td>
												</tr>
											</table>
										</td>
									</tr>
							</table>
						</fieldset>
					</html:form>
				</div>
			</div>
		</logic:present>
<logic:present name="procval">
	<script type="text/javascript">
	if(('<%=request.getAttribute("procval")%>'!= 'S'))
	{	  
		alert('<%=request.getAttribute("procval").toString()%>');
	}
	</script>
</logic:present>
	<logic:notPresent name="EmailStatus">
		<logic:present name="sms">
			<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.corporateDetailSaved" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateAddress";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
		alert('<bean:message key="lbl.corporateDetailUpdated" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateAddress";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='P')
	{
		alert('<bean:message key="lbl.pendingStatus" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='mobileVerify')
	{
		alert('Please Verify Mobile First.');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='emailVerify')
	{
		alert('Please Verify E-Mail First.');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='MD')
	{
		alert('<bean:message key="lbl.pendingmanagementdetail" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
		alert('<bean:message key="lbl.noRecordForSaveAndForward" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Relation')
	{
		alert("Please capture Relationship Details in Customer Address Details Tab");
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='saveFirst')
	{
		alert('<bean:message key="lbl.firstUpdateThenForward" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateAddress";
			
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Reg')
	{
		alert('<bean:message key="msg.registrationNumber" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
			
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Pan')
	{
		var bypassDedupe = alert('<bean:message key="msg.panNoExist" />');
		if(bypassDedupe)
		{
			document.getElementById('bypassDedupe').value='B';
			document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailProcessAction.do?method=saveCorporateDetails&bypassDedupe=B";
 			document.getElementById('corporateDetailForm').submit();
		}
		else
		{
			//document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
    		//document.getElementById('corporateDetailForm').submit();
    	}
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Pan1')
	{
		var bypassDedupe = alert('<bean:message key="msg.panNoExist" />');
		if(bypassDedupe)
		{
			document.getElementById('bypassDedupe').value='B';
			document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailProcessAction.do?method=updateCorporateDetails&bypassDedupe=B";
 			document.getElementById('corporateDetailForm').submit();
		}
		else
		{
			//document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
    		//document.getElementById('corporateDetailForm').submit();
    	}
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Vat')
	{
		alert('<bean:message key="msg.vatNumber" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='RegAndPan')
	{
		alert('<bean:message key="msg.regAndPanNo" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='RegAndVat')
	{
		alert('<bean:message key="msg.regAndVatNo" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
	    document.getElementById('corporateDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='PanAndVat')
	{
		alert('<bean:message key="msg.panAndVatNo" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
    	document.getElementById('corporateDetailForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='RegPanAndVat')
	{
		alert('<bean:message key="msg.RegPanAndVatNo" />');
		document.getElementById('corporateDetailForm').action="<%=request.getContextPath()%>/corporateDetailAction.do?method=displayCorporateDetails";
    	document.getElementById('corporateDetailForm').submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('<bean:message key="lbl.errorSuccess" />');
		
	}

	
	else if('<%=request.getAttribute("sms").toString()%>'=='exist')
	{
		alert('<bean:message key="msg.existAlready" />');
		window.close();
	}
	</script>
		</logic:present>
	</logic:notPresent>
   	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("corporateDetailForm");
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
