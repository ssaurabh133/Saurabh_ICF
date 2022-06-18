<!--  
Author Name:- Amit Kumar
Date of Creation:- 13/05/2011
Purpose:-  
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/underWriter.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		
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
<body oncontextmenu="return false;" onload="enableAnchor();checkChanges('groupExposureLimitForm');">

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>

		<div id="centercolumn">

			<div id="revisedcontainer">
				<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Under Writer</td>
          </tr>
        </table> 
</td>
</tr>
</table>
<input type="hidden" id="applicantId" name="applicantId" class="text" value="${applicantId}" tabindex="-1"/>
<input type="hidden" id="amountFrom" name="amountFrom" class="text" value="${amountFrom}" tabindex="-1"/>
<input type="hidden" id="amountTo" name="amountTo" class="text" value="${amountTo}" tabindex="-1"/>
<input type="hidden" id="includeExposure" name="includeExposure" class="text" value="${includeExposure}" tabindex="-1"/>
<input type="hidden" id="exposureWithSD" name="exposureWithSD" class="text" value="${exposureWithSD}" tabindex="-1"/>
<input type="hidden" id="exposureAmtTemp" name="exposureAmtTemp" class="text" value="${exposureAmt}" tabindex="-1"/>
<input type="hidden" id="sdChargeTemp" name="sdChargeTemp" class="text" value="${sdCharge}" tabindex="-1"/>
<input type="hidden" id="grossAmountLoanTemp" name="grossAmountLoanTemp" class="text" value="${grossAmountLoan}" tabindex="-1"/>
   
   
   





 			
</fieldset>
				<html:form action="/underwritingGroupExposerProcess" styleId="groupExposureLimitForm"
					method="post">
					
					
<logic:present name="customerTypeCount">
		
	
	<script type="text/javascript">
		if('<%=request.getAttribute("customerTypeCount")%>'=='0')
		{
			alert("<bean:message key="lbl.customerTypeMustBeCorporate" />");
		    location.href='<%=request.getContextPath()%>/underWriting.do?method=getUnderWriterDataFrameset';	
		}
		
		
	</script>
	
</logic:present>
					
					<html:hidden property="contextPath" styleId="contextPath"
						value="<%=request.getContextPath()%>" />
						<logic:notPresent name="strFlagDV">
							
					<fieldset>

						<legend>
							<bean:message key="lbl.groupExposureLimit" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td>
												<bean:message key="lbl.customerID" /><font color="red">*</font>
											</td>
											<td>
												<html:text property="customerId" styleClass="text" styleId="customerId" readonly="true" value="${customerId}"  tabindex="-1"/>
											</td>
									
											<td>
												<bean:message key="lbl.customername" /><font color="red">*</font>
											</td>
											<td>
												<html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" value="${dealHeader[0].dealCustomerName}"  tabindex="-1"/>
											</td>
											
										</tr>
										<tr>
											<td>
													<bean:message key="lbl.groupType" />
													<font color="red">*</font>
													</td>
														<td>
														 
														<html:select property="groupType" styleId="groupType" onchange="groupSelectAtUnderWriter();" styleClass="text" value="${groupExposureList[0].groupType}"> 
															<html:option value="N">New</html:option> 
															<html:option value="E">Existing</html:option> 
														</html:select>
														
														</td>
									
									                    <td>
															<bean:message key="lbl.groupName" />
															<font color="red">*</font>
														</td>
														<logic:present name="groupExposureList">
														    <logic:iterate name="groupExposureList"  id="subgroupExposureList" length="1">
														        <logic:equal name="subgroupExposureList" property="groupType" value="N">
																	<td>
																	<html:hidden property="hcommon" styleId="hcommon"
																				styleClass="text" value="" />
																		<div id="groupLov" style="display:none">
																		   <input type="text" id="groupDescription" name="groupDescription"
																			class="text" value="${groupExposureList[0].groupDescription}"
																			readonly="readonly" tabindex="-1"/>
																			<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																			onclick="closeLovCallonLov1();openLOVCommon(393,'corporateDetailForm','groupDescription','','', '','','getGroupExposure','hcommon');"/>
																			<html:hidden property="hGroupId" styleId="hGroupId"
																				styleClass="text" value="${groupExposureList[0].hGroupId}" />
																		</div>	
																																		
																	<div id="groupText" >
									                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${groupExposureList[0].groupNameText}" maxlength="50"/>
													                </div>
														        </td>
														      </logic:equal>
														      <logic:equal name="subgroupExposureList" property="groupType" value="E">
																	<td>
																	<html:hidden property="hcommon" styleId="hcommon"
																				styleClass="text" value="" />
																		<div id="groupLov" >
																		   <input type="text" id="groupDescription" name="groupDescription"
																			class="text" value="${groupExposureList[0].groupDescription}"
																			readonly="readonly" tabindex="-1"/>
																			<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																			onclick="closeLovCallonLov1();openLOVCommon(393,'corporateDetailForm','groupDescription','','', '','','getGroupExposure','hcommon');"/>
																			<html:hidden property="hGroupId" styleId="hGroupId"
																				styleClass="text" value="${groupExposureList[0].hGroupId}" />
																		</div>	
																																		
																	<div id="groupText" style="display:none">
									                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="${groupExposureList[0].groupNameText}" maxlength="50"/>
													                </div>
														        </td>
														      </logic:equal>
												        </logic:iterate>
											        </logic:present>
											        <logic:notPresent name="groupExposureList">
															<td>
															<html:hidden property="hcommon" styleId="hcommon"
																		styleClass="text" value="" />
																<div id="groupLov" style="display:none">
																   <input type="text" id="groupDescription" name="groupDescription"
																	class="text" value=""
																	readonly="readonly" tabindex="-1"/>
																	<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"
																	onclick="closeLovCallonLov1();openLOVCommon(393,'corporateDetailForm','groupDescription','','', '','','getGroupExposure','hcommon');"/>
																	<html:hidden property="hGroupId" styleId="hGroupId"
																		styleClass="text" value="" />
																</div>	
																																
															<div id="groupText" >
							                                  <input type="text" id="groupNameText" name="groupNameText" class="text" value="" maxlength="50"/>
											                </div>
												        </td>
											        </logic:notPresent>
											
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.exposureLimit" /><font color="red">*</font>
											</td>
											<td>
												<html:text property="groupExposureLimit" styleClass="text" styleId="groupExposureLimit"  value="${groupExposureLimit}" style="text-align: right"  onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);" />
											</td>
				
<!--											<td>-->
<!--												<bean:message key="lbl.groupExposureLimit" />-->
<!--											</td>-->
<!--											<td>-->
<!--												<html:text property="currentExposureLimit" styleClass="text" styleId="currentExposureLimit"  readonly="true" value="${groupExposure}" style="text-align: right" />-->
<!--											</td>-->
											
										</tr>
										
										<tr>
											<td><bean:message key="lbl.exposureAmt"/></td>
											<td><html:text property="exposureAmt" styleClass="text" styleId="exposureAmt"  readonly="true" value="${exposureAmt}" style="text-align: right" /></td>
											<td><bean:message key="lbl.customerTotalExposure" /></td>
											<td><html:text property="customerTotalExposure" styleClass="text" styleId="customerTotalExposure"  readonly="true" value="${customerTotalExposure}"  style="text-align: right"/></td>
										</tr>
										<tr>
											<td><bean:message key="lbl.balancePrincipal"/></td>
											<td><html:text property="loanBalansePrincipal" styleClass="text" styleId="loanBalansePrincipal"  readonly="true" value="${loanBalansePrincipal}" style="text-align: right" /></td>
											<td><bean:message key="lbl.overduePrincipal"/></td>
											<td><html:text property="loanOverduePrincipal" styleClass="text" styleId="loanOverduePrincipal"  readonly="true" value="${loanOverduePrincipal}" style="text-align: right" /></td>
										</tr>
										<tr>
											<td><bean:message key="lbl.sdAdviceAmt"/></td>
											<td><html:text property="sdAdviceAmount" styleClass="text" styleId="sdAdviceAmount"  readonly="true" value="${sdAdviceAmount}" style="text-align: right" /></td>
											<td><bean:message key="lbl.dealSDCharge"/></td>
											<td><html:text property="sdCharge" styleClass="text" styleId="sdCharge"  readonly="true" value="${sdCharge}" style="text-align: right" /></td>
										</tr>
										<tr>
											<td><bean:message key="lbl.grossAmountLoan"/></td>
											<td><html:text property="grossAmountLoan" styleClass="text" styleId="grossAmountLoan"  readonly="true" value="${grossAmountLoan}" style="text-align: right" /></td>
										</tr>
										<tr>
											<td><bean:message key="lbl.productExposure" /></td>
											<td><html:text property="productExposure" styleClass="text" styleId="productExposure" readonly="true" readonly="true" value="${productExposure}" style="text-align: right"/></td>
											<td><bean:message key="lbl.schemeExposure" /></td>
											<td><html:text property="exposureScheme" styleClass="text" styleId="exposureScheme" readonly="true"  value="${exposureScheme}"  tabindex="-1" style="text-align: right"/></td>
										</tr>									
										<tr>
											<td><bean:message key="lbl.customerCurrent.exposure" /></td>
											<td><html:text property="exposureCurrentScheme" styleClass="text" styleId="exposureCurrentScheme" readonly="true"  value="${exposureCurrentScheme}"  tabindex="-1" style="text-align: right"/></td>
										</tr>
										<tr>
											<td><bean:message key="lbl.industryExposure" /></td>
											<td><html:text property="industryExposure" styleClass="text" styleId="industryExposure" readonly="true" value="${industryExposure}"  tabindex="-1" style="text-align: right"/></td>
											<td><bean:message key="lbl.subIndustryExposure" /></td>
											<td><html:text property="subIndustryExposure" styleClass="text" styleId="subIndustryExposure"  readonly="true" value="${subIndustryExposure}" style="text-align: right" /></td>
										</tr>
										
										<tr>
											<td><bean:message key="lbl.SBL" /></td>
											<td><html:text property="sbl" styleClass="text" styleId="sbl" disabled="true" style="background-color:#cfd1d3; color:black;" value="${sbl}"  tabindex="-1" style="text-align: right"/></td>
											<td><bean:message key="lbl.GBL" /></td>
											<td><html:text property="gbl" styleClass="text" styleId="gbl"  disabled="true" style="background-color:#cfd1d3; color:black;" value="${gbl}" style="text-align: right" /></td>
										</tr>
										
										<tr>
											<td><bean:message key="lbl.CustomerPos" /></td>
											<td><html:text property="custCurrentPos" styleClass="text" styleId="custCurrentPos" disabled="true" style="background-color:#cfd1d3; color:black;" value="${custCurrentPos}"  tabindex="-1" style="text-align: right"/></td>
											<td><bean:message key="lbl.GroupPos" /></td>
											<td><html:text property="groupPos" styleClass="text" styleId="groupPos"  disabled="true" style="background-color:#cfd1d3; color:black;" value="${groupPos}" style="text-align: right" /></td>
										</tr>
													
					                 <tr>
											<td align="left" class="form2" colspan="4">
												<div align="left">
													<button type="button" name="saveGroup" id="saveGroup"
														class="topformbutton2" title="Alt+V" accesskey="V"
														onclick="return saveGroupExposure();"><bean:message key="button.save" /></button>
												</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>

						</table>

					</fieldset>
					</logic:notPresent>
	<fieldset>	
	
		 <legend><bean:message key="lbl.deals"/></legend>  

  
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        
        <td ><b><bean:message key="lbl.customerID"/></b></td>
		<td ><strong><bean:message key="lbl.customerName"/></strong></td>
		<td ><b><bean:message key="lbl.applicantCategory"/></b></td>
        <td ><b><bean:message key="lbl.groupType"/></b></td>
        <td><b><bean:message key="lbl.groupName"/></b></td>
        <td ><b><bean:message key="lbl.exposureLimit"/></b></td>
        
   

	</tr>
	           
	                        <logic:present name="expoGrid">
	                        
							<logic:iterate id="subexpoGrid" name="expoGrid">
	
	
					<tr class="white1">
							
			 				<logic:notPresent name="strFlagDV">
							<td>
							<a href="#" id="anchor0" onclick="fetchExposureLimitByCustomerId('${subexpoGrid.customerId }');">
							${subexpoGrid.customerId }</a></td>
	      			        <td>${subexpoGrid.customerName }</td>
	      			        </logic:notPresent>
	      			        <logic:present name="strFlagDV">
	      			        <td>${subexpoGrid.customerId }</td>
	      			        <td>${subexpoGrid.customerName }</td>
	      			        </logic:present>
	      			        <logic:equal name="subexpoGrid" property="applicantCat" value="PRAPPL">
	      			        	 <td>Applicant</td>
	      			        </logic:equal>
	      			        <logic:equal name="subexpoGrid" property="applicantCat" value="COAPPL">
	      			        	 <td>Co-Applicant</td>
	      			        </logic:equal>
	      			        <logic:equal name="subexpoGrid" property="applicantCat" value="GUARANTOR">
	      			        	 <td>Guarantor</td>
	      			        </logic:equal>
	      			        
	      			       
	      			        <td >${subexpoGrid.groupType }</td>
	      			      	<td>${subexpoGrid.groupNameText }</td>
							<td>${subexpoGrid.groupExposureLimit }</td>
													

			       </tr>				
			 
	</logic:iterate>
	</logic:present> 
	
		</table>
		</td></tr>
</table>

        
</fieldset>

				</html:form>
			</div>
			

	
<logic:present name="sms">
		

	
	<script type="text/javascript">
		if('<%=request.getAttribute("sms")%>'=='S')
		{
			alert("<bean:message key="msg.manualAdviceMakerSaved" />");
		    
			
		}
		else if('<%=request.getAttribute("sms")%>'=='X')
		{
			alert("<bean:message key="msg.manualAdviceMakerUnsuccessful" />");
			
		}
		
	</script>
	
</logic:present>





	
	<script type="text/javascript">
		setFramevalues('groupExposureLimitForm');
	</script>
</body>
</html:html>