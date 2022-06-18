<!--Author Name : Sachin Singhal -->
<!--Date of Creation : 17-July-2015-->
<!--Purpose  : Partner Type Detail For RSP-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/loanInitiation.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">

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

<body onload="enableDisableFieldForRsp();changeOnFlatOrEffective();checkBusinessPartner();enableAnchor();fntab();init_fields();">
<html:form styleId="partnerTypeDetailsForm"  method="post"  action="/loanDetailCMProcessAction" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	   <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
         
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >RSP Partner Details
	          	<input type="hidden" name="loanAmount" id="loanAmount" value="${loanHeader[0].loanAmount}" />
	          </td>
	          
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>	
<html:hidden property="partnerPercentage1" styleId="partnerPercentage1" value=""  />
<logic:present name="partner">
<logic:present name="RSPMaker">
<fieldset>	
	 <legend><bean:message key="lbl.partnerDetails"/></legend>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
<logic:notPresent name="list">
	<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr>
			<td><bean:message key="lbl.partnerName"/></td>
			<td>
				<html:text property="partnerName" styleId="partnerName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="" />
				<html:button property="partnerName" styleId="partnerName" onclick="openLOVCommon(2080,'partnerTypeDetailsForm','partnerName','','lbxpartnerId', '','','','partnerName','effectiveRate','partnerPercentage1');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
				<html:hidden property="lbxpartnerId" styleId="lbxpartnerId" value=""  />
	        </td>
			<%-- <td width="17%"><bean:message key="lbl.loanAccountNo"/></td>
			<td width="28%"><html:text property="loanNo" styleId="loanNo" styleClass="text" style="text-align: right;" value="${loanHeader[0].loanNo}" readonly="true" /></td> --%>
			
			 <td><bean:message key="lbl.repaySeffectRate"/></td>
		     <td><html:text styleClass="text" property="effectiveRate" styleId="effectiveRate"  style="text-align: right;" onkeypress="return numbersonly(event,id,18);"  onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="sevenDecimalcheckNumber(this.value, event, 8, id);" onfocus="keyUpNumber(this.value, event, 8, id);" value=""/></td>
			
			

		</tr>
								
		<tr>
			<td><bean:message key="lbl.partnerPercentage"/><font color="red">*</font></td>
			<td nowrap="nowrap"><html:text property="partnerPercentage" styleId="partnerPercentage" value="" readonly="true" />
			</td>
			<td><bean:message key="lbl.partnerLoanAmount"/></td>
			<td><html:text property="partnerAmount" value="" onchange="calculatePercentage()" styleId="partnerAmount" onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);"
			  onkeypress="return numbersonly(event,id,18)" onkeyup="checkNumber(this.value, event, 18,id);" styleClass="text" style="text-align: right;" value="" />
			
			</td>
		</tr>
		<tr> 
			<td width="17%"><bean:message key="lbl.servicingPartner"/></td>
			<logic:present name="servicingPartnerflagStatus">
				<td  width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;" checked="checked"   /></td>
		    </logic:present>
		    <logic:notPresent name="servicingPartnerflagStatus">
				<td  width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;"  /></td>
		    </logic:notPresent>

		<td width="17%"><bean:message key="lbl.leadPartner"/></td>
			<logic:present name="leadPartnerFlagStatus">
				<td  width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;" checked="checked"  onclick="enableDisableFieldForRsp();" /></td>
		    </logic:present>
		    <logic:notPresent name="leadPartnerFlagStatus">
				<td width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;" onclick="enableDisableFieldForRsp();" /></td>
		    </logic:notPresent>		
		</tr>
		<tr id="displayField" style="display: none">
          <td><bean:message key="lbl.rateType" /></td>
            <td>
              <html:select property="rateType" styleClass="text" styleId="rateType" onchange="changeOnFlatOrEffective();" >
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          	</td>
			<td width="17%"><bean:message key="lbl.repaySflatRate"/><font color="red">*</font></td>
			<td width="28%">
				<html:text property="partnerRate" styleId="partnerRate" onkeypress="return numbersonly(event,id,18);"  onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="sevenDecimalcheckNumber(this.value, event, 8, id);" onfocus="keyUpNumber(this.value, event, 8, id);"
					   styleClass="text" style="text-align: right;" value=""   />
				<html:hidden property="loanId" styleId="loanId" value="${loanHeader[0].loanId}"  />
			</td>
		    </tr>
		   <tr id="displayField1" style="display: none">
		     <td><bean:message key="lbl.businessType" /></td>
		     <td>
              	<html:select property="businessType" styleClass="text" styleId="businessType" value="${sessionScope.businessType}">
              	<html:option value="CP">CONSORTIUM</html:option>
              	<html:option value="CB">CHANNEL BUSINESS</html:option>
              	</html:select>
          		</td>

		   </tr> 
	</table>
</logic:notPresent>

<logic:present name="list">
	<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr>
			<td><bean:message key="lbl.partnerName"/></td>
			<td nowrap="nowrap">
				<html:text property="partnerName" styleId="partnerName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${list[0].partnerName }" />
				<html:button property="partnerName" styleId="partnerName" onclick="openLOVCommon(2080,'partnerTypeDetailsForm','partnerName','','lbxpartnerId', '','','','partnerName','effectiveRate','partnerPercentage');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
				<html:hidden property="lbxpartnerId" styleId="lbxpartnerId" value="${list[0].lbxpartnerId}"  />
        	</td>
			<%-- <td width="17%"><bean:message key="lbl.loanAccountNo"/></td>
			<td width="28%"><html:text property="loanNo" styleId="loanNo" styleClass="text" style="text-align: right;" value="${loanHeader[0].loanNo}" readonly="true"/> </td>--%>
			
			
			<td><bean:message key="lbl.repaySeffectRate"/></td>
		   <td><html:text styleClass="text" property="effectiveRate" styleId="effectiveRate"  style="text-align: right;" value="${list[0].effectiveRate}" onkeypress="return numbersonly(event,id,18);"  onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="sevenDecimalcheckNumber(this.value, event, 8, id);" onfocus="keyUpNumber(this.value, event, 8, id);"/></td>
			
		</tr>
		
		<tr>
			<td><bean:message key="lbl.partnerPercentage"/><font color="red">*</font></td>
			<td nowrap="nowrap"><html:text property="partnerPercentage" styleId="partnerPercentage" value="${list[0].partnerPercentage}"  readonly="true" />
			</td>
			<td><bean:message key="lbl.partnerLoanAmount"/></td>
			<td><html:text property="partnerAmount" value="" onchange="calculatePercentage()" styleId="partnerAmount" onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);"
			  onkeypress="return numbersonly(event,id,18)" onkeyup="checkNumber(this.value, event, 18,id);" styleClass="text" style="text-align: right;" value="${list[0].partnerAmount}"/>
			
			</td>
		</tr>
		

		<tr> 
			<td width="17%"><bean:message key="lbl.servicingPartner"/></td>
			<logic:present name="servicingPartnerflagStatus">
				<td  width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;" checked="checked"   /></td>
	     	</logic:present>
	     	<logic:notPresent name="servicingPartnerflagStatus">
				<td  width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;"  /></td>
	     	</logic:notPresent>

		<td width="17%"><bean:message key="lbl.leadPartner"/></td>
			<logic:present name="leadPartnerFlagStatus">
				<td  width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;" checked="checked" onclick="enableDisableFieldForRsp();"  /></td>
		    </logic:present>
		    <logic:notPresent name="leadPartnerFlagStatus">
				<td  width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;" onclick="enableDisableFieldForRsp();"  /></td>
		    </logic:notPresent>
		
		</tr>
		<tr id="displayField" style="display: none">
          <td><bean:message key="lbl.rateType" /></td>
            <td>
              <html:select property="rateType" styleClass="text" styleId="rateType" onchange="changeOnFlatOrEffective();" value="${list[0].rateType}">
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          	</td>
			<td width="17%"><bean:message key="lbl.repaySflatRate"/><font color="red">*</font></td>
			<td width="28%">
				<html:text property="partnerRate" styleId="partnerRate" styleClass="text" onkeypress="return numbersonly(event,id,18);"  onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="sevenDecimalcheckNumber(this.value, event, 8, id);" onfocus="keyUpNumber(this.value, event, 8, id);"
					  style="text-align: right;" value="${list[0].partnerRate }"  />
				<html:hidden property="loanId" styleId="loanId" value="${loanHeader[0].loanId}"  />
			</td>
			
			 
		    </tr>
		     <tr id="displayField1" style="display: none">
		    <td><bean:message key="lbl.businessType" /></td>
		     <td>
              	<html:select property="businessType" styleClass="text" styleId="businessType" value="${sessionScope.businessType}">
              	<html:option value="CP">CONSORTIUM</html:option>
              	<html:option value="CB">CHANNEL BUSINESS</html:option>
              	</html:select>
          		</td>
		   </tr> 
	</table>
</logic:present>
	 </td></tr>
	 
	 		 <tr>
				<td >
				 	<button type="button" name="Save"  title="Alt+V" accesskey="V" class="topformbutton2" id="Save" onclick="savePartnerDetailsForRsp();" ><bean:message key="button.save" /></button>	
				 	<button type="button" name="Forward"  title="Alt+F" accesskey="F" class="topformbutton2" id="Forward" onclick="forwordPartnerDetailsForRsp();" ><bean:message key="button.forward" /></button>	
				</td>
			</tr>
		</table>
	</fieldset>	
	<br/>
	  <fieldset>	
		 <legend><bean:message key="lbl.BusinessPartnerDtl"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.partnerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.loanAccountNo"/></b></center></td>
        <td><center><b><bean:message key="lbl.rateType"/></b></center></td>
        <td><center><b><bean:message key="lbl.repaySflatRate"/></b></center></td>
        <td><center><b><bean:message key="lbl.repaySeffectRate"/></b></center></td>
        <td><center><b><bean:message key="lbl.partnerPercentage"/></b></center></td>
         <td><center><b><bean:message key="lbl.partnerLoanAmount"/></b></center></td>
        <td><center><b><bean:message key="lbl.servicingPartner"/></b></center></td>
         <td><center><b><bean:message key="lbl.leadPartner"/></b></center></td>
	</tr>
	 <tr>
		
	
		<logic:notEmpty name="partnerDetails">
		<logic:iterate name="partnerDetails" id="objist" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="chk" id="chk" value="${objist.id }"/>
    	 </td>
		<td ><a href="#" id="anchor0" onclick="return getPartnerDtlForRsp(${objist.id});">${objist.partnerName}</a></td>
		<td >${objist.loanNo}</td>
		<td>${objist.rateTypeDesc}</td>
		<td>${objist.partnerRate }</td>
		<td>${objist.effectiveRate }</td>
		<td>${objist.partnerPercentage }</td>
		<td>${objist.partnerAmount }</td>
		<td>${objist.servicingPartnerFlag }</td>
		<td>${objist.leadPartnerFlag }</td>
		</tr>	
	   	</logic:iterate>
		</logic:notEmpty>
		</tr> 
	
 </table>    </td>
</tr>
<logic:present name="partnerDetails">
   <tr><td >
  	  <button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deletePartnerDetailForRsp();"><bean:message key="button.delete" /></button>
	        </td></tr>
</logic:present>
</table>

	</fieldset>
	</logic:present>
	</logic:present>
	
	<!-- for view part -->
	<logic:present name="RSPAuthor">
	
	
<fieldset>	
	 <legend><bean:message key="lbl.partnerDetails"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
		<logic:notPresent name="list">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td ><bean:message key="lbl.partnerName"/></td>
		<td >
		<html:text property="partnerName" styleId="partnerName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value=""  readonly="true" />
		
        </td>
	<%-- <td width="17%"><bean:message key="lbl.loanAccountNo"/></td>
	<td width="28%"><html:text property="loanNo" styleId="loanNo" styleClass="text" style="text-align: right;" value="${loanHeader[0].loanNo}"  readonly="true" /></td> --%>
<td><bean:message key="lbl.repaySeffectRate"/></td>
		     <td><html:text styleClass="text" property="effectiveRate" styleId="effectiveRate" disabled="true" style="text-align: right;" onkeypress="return numbersonly(event,id,18);"  onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="sevenDecimalcheckNumber(this.value, event, 8, id);" onfocus="keyUpNumber(this.value, event, 8, id);"/></td>
		</tr>
		
		<tr>


	<td><bean:message key="lbl.partnerPercentage"/><font color="red">*</font></td>
    <td nowrap="nowrap"><html:text property="partnerPercentage" styleId="partnerPercentage"  readonly="true" />
			</td>
			<td><bean:message key="lbl.partnerLoanAmount"/></td>
			<td><html:text property="partnerAmount" value="" onchange="calculatePercentage()" styleId="partnerAmount" onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);"
			  onkeypress="return numbersonly(event,id,18)" onkeyup="checkNumber(this.value, event, 18,id);" styleClass="text" style="text-align: right;"   readonly="true"/>
			
			</td>
	</tr>
	
	
	
	<tr>
	
	<td width="17%"><bean:message key="lbl.servicingPartner"/></font></td>
		<logic:present name="servicingPartnerflagStatus">
		<td width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;" checked="checked"   />
     </td>
     </logic:present>
     	<logic:notPresent name="servicingPartnerflagStatus">
		<td width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;"  />
     </td>
     </logic:notPresent>


		<td width="17%"><bean:message key="lbl.leadPartner"/></td>
			<logic:present name="leadPartnerFlagStatus">
				<td  width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;" checked="checked"   /></td>
		    </logic:present>
		    <logic:notPresent name="leadPartnerFlagStatus">
				<td  width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;"  /></td>
		    </logic:notPresent>
		
		</tr>	
		
			<tr>
          <td><bean:message key="lbl.rateType" /></td>
            <td>
              <html:select property="rateType" styleClass="text" styleId="rateType" disabled="true" >
			  <html:option value="">---Select---</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          	</td>
				<td width="17%"><bean:message key="lbl.repaySflatRate"/><font color="red">*</font></td>
		<td width="28%"><html:text property="partnerRate" styleId="partnerRate" styleClass="text" style="text-align: right;" value="" />
	<html:hidden property="loanId" styleId="loanId" value="${loanHeader[0].loanId}"    />
	</td>
			
			 
		    </tr>
		 <tr id="displayField1" style="display: none">
		    <td><bean:message key="lbl.businessType" /></td>
		     <td>
              	<html:select property="businessType" styleClass="text" styleId="businessType" value="${sessionScope.businessType}">
              	<html:option value="CP">CONSORTIUM</html:option>
              	<html:option value="CB">OWN FUNDED</html:option>
              	</html:select>
          		</td>
		   </tr> 
	  	  </table>
</logic:notPresent>
<logic:present name="list">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td><bean:message key="lbl.partnerName"/></td>
		<td nowrap="nowrap">
		<html:text property="partnerName" styleId="partnerName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${list[0].partnerName }"  readonly="true"/>
		
        </td>
	<%-- <td width="17%"><bean:message key="lbl.loanAccountNo"/></td>
	<td width="28%"><html:text property="loanNo" styleId="loanNo" styleClass="text" style="text-align: right;" value="${loanHeader[0].loanNo}"  readonly="true"/> </td>--%>
	<html:hidden property="loanId" styleId="loanId" value="${loanHeader[0].loanId}"   />
<td><bean:message key="lbl.repaySeffectRate"/></td>
		     <td><html:text styleClass="text" property="effectiveRate" styleId="effectiveRate" disabled="true" style="text-align: right;" onkeypress="return numbersonly(event,id,18);"  onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="sevenDecimalcheckNumber(this.value, event, 8, id);" onfocus="keyUpNumber(this.value, event, 8, id);"/></td>
		</tr>
		
		<tr>
		<%-- <td colspan="2"><bean:message key="lbl.repaySflatRate"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap"><html:text property="partnerRate" styleId="partnerRate" styleClass="text" style="text-align: right;" value="${list[0].partnerRate }"  readonly="true" />
     </td> --%>

	<td><bean:message key="lbl.partnerPercentage"/><font color="red">*</font></td>
    <td nowrap="nowrap"><html:text property="partnerPercentage" styleId="partnerPercentage" value="${list[0].partnerPercentage}"  readonly="true" />
			</td>
			<td><bean:message key="lbl.partnerLoanAmount"/></td>
			<td><html:text property="partnerAmount" value="" onchange="calculatePercentage()" styleId="partnerAmount" onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);"
			  onkeypress="return numbersonly(event,id,18)" onkeyup="checkNumber(this.value, event, 18,id);" styleClass="text" style="text-align: right;" value="${list[0].partnerAmount}"   readonly="true"/>
			
			</td>
	</tr>
	<tr>
	<td width="17%"><bean:message key="lbl.servicingPartner"/></font></td>
		<logic:present name="servicingPartnerflagStatus">
		<td width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;" checked="checked"   />
     </td>
     </logic:present>
     	<logic:notPresent name="servicingPartnerflagStatus">
		<td width="28%"><input type ="checkbox" name="servicingPartnerFlag" id="servicingPartnerFlag"  style="text-align: right;"  />
     </td>
     </logic:notPresent>
		
		<td width="17%"><bean:message key="lbl.leadPartner"/></td>
			<logic:present name="leadPartnerFlagStatus">
				<td  width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;" checked="checked"   /></td>
		    </logic:present>
		    <logic:notPresent name="leadPartnerFlagStatus">
				<td  width="28%"><input type ="checkbox" name="leadPartnerFlag" id="leadPartnerFlag"  style="text-align: right;"  /></td>
		    </logic:notPresent>
		
		</tr>	
		
					<tr>
          <td><bean:message key="lbl.rateType" /></td>
            <td>
              <html:select property="rateType" styleClass="text" styleId="rateType" disabled="true" >
			  <html:option value="">---Select---</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          	</td>
				<td width="17%"><bean:message key="lbl.repaySflatRate"/><font color="red">*</font></td>
		<td width="28%"><html:text property="partnerRate" styleId="partnerRate" styleClass="text" style="text-align: right;" value="${list[0].partnerRate }"   />
	</td>
			
			 
		    </tr>
		     <tr id="displayField1" style="display: none">
		     <td><bean:message key="lbl.businessType" /></td>
		     <td>
              	<html:select property="businessType" styleClass="text" styleId="businessType" value="${sessionScope.businessType}">
              	<html:option value="CP">CONSORTIUM</html:option>
              	<html:option value="CB">OWN FUNDED</html:option>
              	</html:select>
          		</td>
		   </tr> 
	  	  </table>
</logic:present>
	 </td></tr>
  
		</table>
	</fieldset>	
	<br/>
	  <fieldset>	
		 <legend><bean:message key="lbl.BusinessPartnerDtl"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><input type="checkbox" name="allchk" id="allchk" /><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.partnerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.loanAccountNo"/></b></center></td>
         <td><center><b><bean:message key="lbl.rateType"/></b></center></td>
        <td><center><b><bean:message key="lbl.repaySflatRate"/></b></center></td>
        <td><center><b><bean:message key="lbl.repaySeffectRate"/></b></center></td>
        <td><center><b><bean:message key="lbl.partnerPercentage"/></b></center></td>
         <td><center><b><bean:message key="lbl.partnerLoanAmount"/></b></center></td>
         <td><center><b><bean:message key="lbl.servicingPartner"/></b></center></td>
         <td><center><b><bean:message key="lbl.leadPartner"/></b></center></td>
         
	</tr>
	 <tr>
		
	
		<logic:notEmpty name="partnerDetails">
		<logic:iterate name="partnerDetails" id="objist" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="chk" id="chk" value="${objist.id }"/>
    	 </td>
		<td >${objist.partnerName}</td>
		<td >${objist.loanNo}</td>
		<td>${objist.rateTypeDesc}</td>
		<td>${objist.partnerRate }</td>
		<td>${objist.effectiveRate }</td>
		<td>${objist.partnerPercentage}</td>
		<td>${objist.partnerAmount}</td>
		<td>${objist.servicingPartnerFlag }</td>
		<td>${objist.leadPartnerFlag }</td>
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		</tr> 
	
 </table>    </td>
</tr>
</table>

	</fieldset>
	
	
	</logic:present>
	<!-- for view part ends here-->
</html:form>

  
           
		<logic:present name="msg">
<script type="text/javascript">

    if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='NS')
	{
		alert("<bean:message key="lbl.NonDataSave" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='NM')
	{
		alert("<bean:message key="msg.updationUnsuccessful" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='Del')
	{
		alert("<bean:message key="lbl.dataDeleted" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataNtDeleted" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='EX')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='prevent')
	{
		alert("<bean:message key="msg.prevent" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='contributionAmount')
	{
		alert("<bean:message key="msg.totalContribution" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='leadPartnerExist')
	{
		alert("<bean:message key="msg.leadPartnerExist" />");	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.canForward" />");	
 		parent.location.href="<%=request.getContextPath()%>/repaymentServiceAction.do?method=openSearchPageForRepaymentService";
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.cantForwardSucc" />");	
	}
	else if('<%=request.getAttribute("msg")%>'=='al')
	{	
		alert("<bean:message key="msg.servicePartner" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='ONEMUSTLEADPARTNER')
	{	
		alert("<bean:message key="msg.oneMustLeadPartner" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='RATENOTMATCH')
	{	
		alert("<bean:message key="msg.rateNotMatch" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='checkAllParticipationAmt')
	{	
		alert("<bean:message key="msg.checkAllParticipationAmt" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='SAVEALLSTAGE')
	{	
		alert("<bean:message key="msg.saveAllStage" />");
	}
	else if('<%=request.getAttribute("msg")%>'=='saveOneRow')
	{	
		alert("<bean:message key="msg.saveOneRow" />");
	}
   
	
	
</script>
</logic:present>
  </body>
		</html:html>