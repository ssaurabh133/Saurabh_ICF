<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 11 JULY 2012-->
<!--Purpose  : Scheme Master Add->
<!--Documentation : -->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

	<title><bean:message key="a3s.noida" />
	</title>

	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/payoutScript/schemeMaster.js"></script>
<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

	<link type="text/css" 	href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
		<!-- css for Datepicker -->
<link type="text/css" href="development-bundle/demos/demos.css"
	rel="stylesheet" />
<link type="text/css"
	href="development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<!-- jQuery for Datepicker -->

<script type="text/javascript" src="development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="development-bundle/ui/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('schemeMasterAdd').schemeName.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('schemeMasterAdd').schemeName.focus();
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

<body onload="enableAnchor();fntab();init_fields();">


	<html:form styleId="schemeMasterAdd" method="post"		action="/schemeMasterDispatch" >
	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	  <input type="hidden" name="hcommon"  id="hcommon"/>
	 <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
     <input type="hidden" id="psize" name="psize"/>
		<fieldset>
			<legend>
				<bean:message key="lbl.schemeMaster" />
			</legend>
			<table cellpadding="0" cellspacing="1"  width="100%">


				<logic:present name="editVal">
				<logic:notEmpty name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="M" />
					<logic:iterate id="listObj" name="list">
						<tr>
						<td>
							<bean:message key="lbl.schemeName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="schemeName" styleClass="text" styleId="schemeName" readonly="true"
							 value="${listObj.schemeName}"	maxlength="50" onblur="fnChangeCase('schemeMasterAdd','schemeName')" />
						</td>

						<td>
							<bean:message key="lbl.payNarration" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						<html:textarea property="narration" styleClass="text" value="${listObj.narration}" styleId="narration"></html:textarea>
						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.payEffectiveDate" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:text property="effectiveDate" styleClass="text" styleId="effectiveDate" value="${listObj.effectiveDate}" onchange="checkDate('effectiveDate');validRepayDateInScheme();" />
							</td>

							<td>
								
							</td>
							<td>
							
							</td>

					</tr>
						<tr>
					    <td><bean:message key="lbl.schemeParameter" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    <html:select property="schemeParameter" styleId="schemeParameter" styleClass="text" value="${listObj.schemeParameter}" onchange="chengeParameterDiv()">
					    <html:option value="CW">Case Wise</html:option>
					    <html:option value="PW">Percentage Wise</html:option>
					    <html:option value="SCW">Slab Case Wise</html:option>
					    <html:option value="SPW">Slab Percentage Wise</html:option>
					     <html:option value="SFA">Slab For Amount</html:option>
					    </html:select>
					    </td>
					    <td>
					    <logic:present name="slab">
					    <logic:equal name="slab" value="Y">
					    <span id="slabOnSpan"><bean:message key="lbl.slabOn" /></span>
					    </logic:equal>
					     <logic:notEqual name="slab" value="Y">
					     <span id="slabOnSpan" style="display:none"><bean:message key="lbl.slabOn" /></span>
					    </logic:notEqual>
					    </logic:present>
					    </td>
					    <td>
					   <logic:present name="slab">
					    <logic:equal name="slab" value="Y">
					  <html:select property="slabOn" styleId="slabOn" styleClass="text" value="${listObj.slabOn}" >
					    <html:option value="C">Case</html:option>
					    <html:option value="P">Amount</html:option>
					    </html:select>
					     </logic:equal>
					     <logic:notEqual name="slab" value="Y">
					    <html:select property="slabOn" styleId="slabOn" styleClass="text" value="${listObj.slabOn}"  style="display:none">
					    <html:option value="C">Case</html:option>
					    <html:option value="P">Amount</html:option>
					   </html:select>
					     </logic:notEqual>
					    </logic:present>
					    </td>	
						</tr>
				  <tr>
			
				 <td><bean:message key="lbl.schemeCity" />
					<span><font color="red">*</font></span></td>
					<td>
					<input type="text" name="cpDist" id="cpDist" size="20" value="${listObj.cpDistCode}"    class="text" readonly="readonly"  tabindex="-1"> 
				<input type="hidden" name="cpDistCode" id="cpDistCode"  value="${listObj.districtId}" class="text"> 
				<html:button property="distButton" styleId="stateButton" onclick="openLOVCommon(1369,'schemeSearch','cpDist','','cpDistCode', '','','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				<input type="hidden" name="hcommon" id="hcommon"  class="text">
				<html:hidden property="contextPath" styleId="contextPath"		value="<%=request.getContextPath()%>" />
					</td>
					<td><bean:message key="lbl.tat" />
								<span><font color="red">*</font></span></td>
				<td>
				<html:text property="tat" styleClass="text" styleId="tat" value="${listObj.tat}" onkeypress="return isNumberKey(event);" maxlength="50" />
				</td>
				
				 </tr>
				 	<tr>
				 <td><bean:message key="lbl.payTaxApp" />
								<span><font color="red">*</font>
								</span></td>
				
				  <td>
				  <logic:equal value="A" name="taxApp">
					<input type="checkbox" name="serviceTaxApp" id="serviceTaxApp"
						checked="checked" />
					</logic:equal>
					<logic:notEqual value="A" name="taxApp">
					<input type="checkbox" name="serviceTaxApp" id="serviceTaxApp" />
					</logic:notEqual>
				  </td>
					 <td><bean:message key="lbl.payTdsApp" />
								<span><font color="red">*</font>
								</span></td> 			 
				    <td>
				    <logic:equal value="A" name="tdsApp">
					<input type="checkbox" name="tdsApp" id="tdsApp"
						checked="checked" />
					</logic:equal>
					<logic:notEqual value="A" name="tdsApp">
					<input type="checkbox" name="tdsApp" id="tdsApp" />
					</logic:notEqual>
				    
				    </td>
				 </tr>
						<tr>
							<td>
								<bean:message key="lbl.payRecStatus" />
							</td>
							<td>
								<logic:equal value="Active" name="status">
									<input type="checkbox" name="recStatus" id="recStatus"
										checked="checked" />
								</logic:equal>
								<logic:notEqual value="Active" name="status">
									<input type="checkbox" name="recStatus" id="recStatus" />
								</logic:notEqual>
							</td>

						</tr>
						
                    <tr>
						 <td colspan="4" width="100%" >
						 <fieldset><legend><bean:message key="lbl.schemeParameterDtl" /></legend>
						 <logic:equal value="CW" name="listObj" property="schemeParameter">
						  <div id="divCW" style="display:block"  >
						 <table  width="100%">
						 <tr>
						 <td width="24%" class="white1"><bean:message key="lbl.commissionPerCaseRs" />
								<span><font color="red">*</font>
								</span></td>
					    <logic:present name="listDtl" >
						 <logic:iterate id="listDtlObj" name="listDtl" >
						 <td width="25%" class="white1">
						 <html:text property="commissionPerCaseR" styleClass="text" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event,18,this.id);" onblur="formatNumber(this.value,this.id);" styleId="commissionPerCaseR" value="${listDtlObj.commissionPerCaseR}" maxlength="10" /></td>
						</logic:iterate>
						</logic:present>
						
						 <td width="25%" class="white1">&nbsp;</td>
						 <td width="26%" class="white1">&nbsp;</td>
						 </tr>
						 </table>
						 </div>
						 </logic:equal>
						 <logic:notEqual value="CW" name="listObj" property="schemeParameter">
						  <div id="divCW" style="display:none"  >
						 <table  width="100%">
						 <tr>
						 <td width="24%" class="white1"><bean:message key="lbl.commissionPerCaseRs" />
								<span><font color="red">*</font>
								</span></td>
						<td width="25%" class="white1"><html:text property="commissionPerCaseR" styleClass="text" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event,18,this.id);" onblur="formatNumber(this.value,this.id);" styleId="commissionPerCaseR" maxlength="10" /></td>
						 <td width="25%" class="white1">&nbsp;</td>
						 <td width="26%" class="white1">&nbsp;</td>
						 </tr>
						 </table>
						 </div>
						 </logic:notEqual>
						 <logic:equal value="PW" name="listObj" property="schemeParameter">
						  <div id="divPW" style="display:block">
						 <table width="100%">
						 <tr>
						 <td width="24%" class="white1"><bean:message key="lbl.commissionPerCasePer" />
								<span><font color="red">*</font>
								</span></td>
						<logic:present name="listDtl" >
						 <logic:iterate id="listDtlObj" name="listDtl" >
						 <td width="25%" class="white1">
						 <html:text property="commissionPerCaseP" onkeypress="return numbersonly(event,this.id,3);" onkeyup="checkNumber(this.value, event,3,this.id);" onblur="formatNumber(this.value,this.id);" value="${listDtlObj.commissionPerCaseP}" styleClass="text" styleId="commissionPerCaseP" maxlength="10" /></td>
						 </logic:iterate>
						 </logic:present>
						 <td width="25%" class="white1">&nbsp;</td>
						 <td width="26%" class="white1">&nbsp;</td>
						 </tr>
						 </table>
						 </div >
						 </logic:equal>
						  <logic:notEqual value="PW" name="listObj" property="schemeParameter">
						   <div id="divPW" style="display:none">
						 <table width="100%">
						 <tr>
						 <td width="24%" class="white1"><bean:message key="lbl.commissionPerCasePer" />
								<span><font color="red">*</font>
								</span></td>
						 <td width="25%" class="white1"><html:text property="commissionPerCaseP" styleClass="text" onkeypress="return numbersonly(event,this.id,3);" onkeyup="checkNumber(this.value, event,3,this.id);" onblur="formatNumber(this.value,this.id);" styleId="commissionPerCaseP" maxlength="10" /></td>
						 <td width="25%" class="white1">&nbsp;</td>
						 <td width="26%" class="white1">&nbsp;</td>
						 </tr>
						 </table>
						 </div >
						  </logic:notEqual>
						  
						  <logic:equal value="SCW" name="listObj" property="schemeParameter">
						   <div id="divSCW" style="display:block" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSCW" >
						 <tr>
						 <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.commissionPerCaseRs" /></td>
						 
						 </tr>
						 <logic:present name="listDtl" >
						 <logic:iterate id="listDtlObj" name="listDtl" indexId="countSCW">
						
						 <tr>
						 <td class="white1"> <input type="checkbox" id="<%=countSCW.intValue()+1 %>"/></td>
						 <td class="white1"><input type="text" name="caseFrom" onkeypress="return numbersonly(event,this.id,18);" id="caseFrom<%=countSCW.intValue()+1 %>" value="${listDtlObj.caseFromStr}" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="caseTo" onkeypress="return numbersonly(event,this.id,18);" id="caseTo<%=countSCW.intValue()+1 %>" value="${listDtlObj.caseToStr}" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"   class="text"/></td>
						 <td class="white1"><input type="text" name="commissionR" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" id="commissionR<%=countSCW.intValue()+1 %>" value="${listDtlObj.commissionRStr}" class="text"/></td>
						
						 </tr>
						  </logic:iterate>
						 </logic:present>
						 <tr>
						 <td colspan="4" class="white1">
						  <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addrowSCW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSCW();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						 </logic:equal>
						  <logic:notEqual value="SCW" name="listObj" property="schemeParameter">
						   <div id="divSCW" style="display:none" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSCW" >
						 <tr>
						 <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.commissionPerCaseRs" /></td>
						 
						 </tr>
						 <tr>
						 <td class="white1"> <input type="checkbox"/></td>
						 <td class="white1"><input type="text" name="caseFrom" id="caseFrom1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="caseTo" id="caseTo1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="commissionR" id="commissionR1"  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						
						 </tr>
						 <tr>
						 <td colspan="4" class="white1">
						  <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addrowSCW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSCW();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						  </logic:notEqual>
						  
						  <logic:equal value="SPW" name="listObj" property="schemeParameter">
						   <div id="divSPW" style="display:block" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSPW">
						 <tr>
						  <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="26%" class="white2"><bean:message key="lbl.commissionPerCasePer" /></td>
						
						 </tr>
						 <logic:present name="listDtl" >
						 <logic:iterate id="listDtlObj" name="listDtl" indexId="countSPW">
						 <tr>
						  <td class="white1"> <input type="checkbox" id="<%=countSPW.intValue()+1 %>"/></td>
						<td class="white1"><input type="text" name="caseFromP" id="caseFromP<%=countSPW.intValue()+1 %>"  value="${listDtlObj.caseFromStr}" onkeypress="return numbersonly(event,this.id,18);"  onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="caseToP" id="caseToP<%=countSPW.intValue()+1 %>" value="${listDtlObj.caseToStr}"  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"   class="text"/></td>
						 <td class="white1"><input type="text" name="commissionP" id="commissionP<%=countSPW.intValue()+1 %>"  value="${listDtlObj.commissionPStr}" onkeypress="return numbersonly(event,this.id,3);" onkeyup="checkNumber(this.value, event,3,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						  </tr>
						  </logic:iterate>
						  </logic:present>
						 <tr>
						 <td colspan="4" class="white1">
						 <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addRowSPW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						 <button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSPW();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						 </logic:equal>
						 
						  <logic:notEqual value="SPW" name="listObj" property="schemeParameter">
						   <div id="divSPW" style="display:none" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSPW">
						 <tr>
						  <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="26%" class="white2"><bean:message key="lbl.commissionPerCasePer" /></td>
						
						 </tr>
						 <tr>
						  <td class="white1"> <input type="checkbox"/></td>
						<td class="white1"><input type="text" name="caseFromP" id="caseFromP1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						 <td class="white1"><input type="text" name="caseToP" id="caseToP1" onkeypress="return numbersonly(event,this.id,18);"  onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="commissionP" id="commissionP1" onkeypress="return numbersonly(event,this.id,3);" onkeyup="checkNumber(this.value, event, 3,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						  </tr>
						 <tr>
						 <td colspan="4" class="white1">
						 <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addRowSPW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						 <button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSPW();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						  </logic:notEqual>
							
							 <logic:equal value="SFA" name="listObj" property="schemeParameter">
						   <div id="divSFA" style="display:block" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSFA" >
						 <tr>
						 <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.commissionAmount" /></td>
						 
						 </tr>
						 <logic:present name="listDtl" >
						 <logic:iterate id="listDtlObj" name="listDtl" indexId="countSCW">
						
						 <tr>
						 <td class="white1"> <input type="checkbox" id="<%=countSCW.intValue()+1 %>"/></td>
						 <td class="white1"><input type="text" name="caseFromA" onkeypress="return numbersonly(event,this.id,18);" id="caseFromA<%=countSCW.intValue()+1 %>" value="${listDtlObj.caseFromStr}" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="caseToA" onkeypress="return numbersonly(event,this.id,18);" id="caseToA<%=countSCW.intValue()+1 %>" value="${listDtlObj.caseToStr}" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"   class="text"/></td>
						 <td class="white1"><input type="text" name="commissionA" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" id="commissionA<%=countSCW.intValue()+1 %>" value="${listDtlObj.commissionRStr}" class="text"/></td>
						
						 </tr>
						  </logic:iterate>
						 </logic:present>
						 <tr>
						 <td colspan="4" class="white1">
						  <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addrowSFA();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSFA();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						 </logic:equal>
						  <logic:notEqual value="SFA" name="listObj" property="schemeParameter">
						   <div id="divSFA" style="display:none" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSFA" >
						 <tr>
						 <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.commissionAmount" /></td>
						 
						 </tr>
						 <tr>
						 <td class="white1"> <input type="checkbox"/></td>
						 <td class="white1"><input type="text" name="caseFromA" id="caseFromA1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="caseToA" id="caseToA1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  class="text"/></td>
						 <td class="white1"><input type="text" name="commissionA" id="commissionA1"  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						
						 </tr>
						 <tr>
						 <td colspan="4" class="white1">
						  <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addrowSFA();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSFA();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						  </logic:notEqual>				
						 </fieldset>
						 </td> </tr>
					</logic:iterate>
					</logic:notEmpty>
				</logic:present>

				<logic:notPresent name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="I" />
					<tr>
						<td>
							<bean:message key="lbl.schemeName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="schemeName" styleClass="text" styleId="schemeName"
								maxlength="50" onblur="fnChangeCase('schemeMasterAdd','schemeName')" />
						</td>

						<td>
							<bean:message key="lbl.payNarration" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						<html:textarea property="narration" styleClass="text" styleId="narration"></html:textarea>
						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.payEffectiveDate" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:text property="effectiveDate" styleClass="text" styleId="effectiveDate" maxlength="50"  onchange="checkDate('effectiveDate');validRepayDateInScheme();"/>
							</td>

							<td>
								
							</td>
							<td>
							
							</td>

					</tr>
						<tr>
					    <td><bean:message key="lbl.schemeParameter" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    <html:select property="schemeParameter" styleId="schemeParameter" styleClass="text" onchange="chengeParameterDiv()">
					    <html:option value="CW">Case Wise</html:option>
					    <html:option value="PW">Percentage Wise</html:option>
					    <html:option value="SCW">Slab Case Wise</html:option>
					    <html:option value="SPW">Slab Percentage Wise</html:option>
					    <html:option value="SFA">Slab For Amount</html:option>
					  
					    </html:select>
					    </td>
					    <td><span id="slabOnSpan" style="display:none"><bean:message key="lbl.slabOn" /></span></td>
					    <td>
					  <html:select property="slabOn" styleId="slabOn" styleClass="text" onchange="chengeParameterDiv()" style="display:none" disabled="true">
					    <html:option value="C">Case</html:option>
					    <html:option value="P">Amount</html:option>
					    </html:select>
					    </td>	
						</tr>
				  <tr>
			
				 <td><bean:message key="lbl.schemeCity" />
					<span><font color="red">*</font></span></td>
					<td>
					<input type="text" name="cpDist" id="cpDist" size="20"    class="text" readonly="readonly"  tabindex="-1"> 
				<input type="hidden" name="cpDistCode" id="cpDistCode"  class="text"> 
				<html:button property="distButton" styleId="stateButton" onclick="openLOVCommon(1369,'schemeSearch','cpDist','','cpDistCode', '','','','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
				<input type="hidden" name="hcommon" id="hcommon"  class="text">
				<html:hidden property="contextPath" styleId="contextPath"		value="<%=request.getContextPath()%>" />
					</td>
					<td><bean:message key="lbl.tat" />
								<span><font color="red">*</font></span></td>
				<td>
				<html:text property="tat" styleClass="text" styleId="tat"  onkeypress="return isNumberKey(event);"  maxlength="50" />
				</td>
				
				 </tr>
				 	<tr>
				 <td><bean:message key="lbl.payTaxApp" />
								<span><font color="red">*</font>
								</span></td>
				
				  <td><input type="checkbox" name="serviceTaxApp" id="serviceTaxApp" /></td>
					 <td><bean:message key="lbl.payTdsApp" />
								<span><font color="red">*</font>
								</span></td> 			 
				    <td><input type="checkbox" name="tdsApp" id="tdsApp" /></td>
				 </tr>				
					<tr>
						<td>
							<bean:message key="lbl.payRecStatus" />
						</td>
						<td>
							<input type="checkbox" name="recStatus" id="recStatus"
								checked="checked" />
						</td>

					</tr>
					
                    <tr>
						 <td colspan="4" width="100%" >
						 <fieldset><legend><bean:message key="lbl.schemeParameterDtl" /></legend>
						 <div id="divCW" style="display:block"  >
						 <table  width="100%">
						 <tr>
						 <td width="24%" class="white1"><bean:message key="lbl.commissionPerCaseRs" />
								<span><font color="red">*</font>
								</span></td>
						<td width="25%" class="white1">
						<html:text property="commissionPerCaseR" styleClass="text"  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"  styleId="commissionPerCaseR"  /></td>
						 <td width="25%" class="white1">&nbsp;</td>
						 <td width="26%" class="white1">&nbsp;</td>
						 </tr>
						 </table>
						 </div>
						 <div id="divPW" style="display:none">
						 <table width="100%">
						 <tr>
						 <td width="24%" class="white1"><bean:message key="lbl.commissionPerCasePer" />
								<span><font color="red">*</font>
								</span></td>
						 <td width="25%" class="white1">
						 <html:text property="commissionPerCaseP" styleClass="text" styleId="commissionPerCaseP" onkeypress="return numbersonly(event,this.id,3);" onkeyup="checkNumber(this.value, event,3,this.id);"  /></td>
						 <td width="25%" class="white1">&nbsp;</td>
						 <td width="26%" class="white1">&nbsp;</td>
						 </tr>
						 </table>
						 </div >
						 <div id="divSCW" style="display:none" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSCW" >
						 <tr>
						 <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.commissionPerCaseRs" /></td>
						 
						 </tr>
						 <tr>
						 <td class="white1"> <input type="checkbox"/></td>
						 <td class="white1"><input type="text" name="caseFrom" id="caseFrom1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						 <td class="white1"><input type="text" name="caseTo" id="caseTo1" onkeypress="return numbersonly(event,this.id,18);"  onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"class="text"/></td>
						 <td class="white1"><input type="text" name="commissionR" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" id="commissionR1" class="text"/></td>
						
						 </tr>
						 <tr>
						 <td colspan="4" class="white1">
						  <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addrowSCW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSCW();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						 <div id="divSPW" style="display:none" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSPW">
						 <tr>
						  <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="26%" class="white2"><bean:message key="lbl.commissionPerCasePer" /></td>
						
						 </tr>
						 <tr>
						  <td class="white1"> <input type="checkbox"/></td>
						<td class="white1"><input type="text" name="caseFromP" id="caseFromP1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						 <td class="white1"><input type="text" name="caseToP" id="caseToP1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						 <td class="white1"><input type="text" name="commissionP" id="commissionP1" onkeypress="return numbersonly(event,this.id,3);" onkeyup="checkNumber(this.value, event, 3,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						  </tr>
						 <tr>
						 <td colspan="4" class="white1">
						 <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addRowSPW();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						 <button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSPW();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						 <div id="divSFA" style="display:none" class="gridtd" >
						 <table width="100%" cellspacing="1" cellpadding="1" id="gridTableSFA" >
						 <tr>
						 <td width="24%" class="white2"><bean:message key="lbl.select" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabFrom" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.slabTo" /></td>
						 <td width="25%" class="white2"><bean:message key="lbl.commissionAmount" /></td>
						 
						 </tr>
						 <tr>
						 <td class="white1"> <input type="checkbox"/></td>
						 <td class="white1"><input type="text" name="caseFromA" id="caseFromA1" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" class="text"/></td>
						 <td class="white1"><input type="text" name="caseToA" id="caseToA1" onkeypress="return numbersonly(event,this.id,18);"  onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);"class="text"/></td>
						 <td class="white1"><input type="text" name="commissionA" onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" id="commissionA1" class="text"/></td>
						
						 </tr>
						 <tr>
						 <td colspan="4" class="white1">
						  <button type="button" class="topformbutton2" name="addRow"  id="addRow"  onclick="return addrowSFA();" title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
						<button type="button" class="topformbutton2" name="deleteRow"  id="deleteRow"  onclick="return removeRowSFA();" title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>
						 </td>
						 </tr>
						 </table>
						 </div>
						 </fieldset>
						 </td> </tr>
				</logic:notPresent>

				<tr>
					<td>
						
						<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveScheme();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

					</td>
				</tr>
				
 
 


			</table>


		</fieldset>


	</html:form>

	<logic:present name="sms">
		<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("schemeMasterAdd").action="schemeMasterBehind.do";
	    document.getElementById("schemeMasterAdd").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("schemeMasterAdd").action="schemeMasterBehind.do";
	    document.getElementById("schemeMasterAdd").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	
</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>