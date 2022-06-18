<!--Author Name : Ravindra-->
<!--Date of Creation : 18-Oct-2011-->
<!--Purpose  : auto Allocation Definition Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/autoAllocationDefinition.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
</head>

<body onload="enableAnchor();document.getElementById('autoAllocationDefinitionForm').npaStageButton.focus();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/autoAllocationDefinitionAction" styleId="autoAllocationDefinitionForm" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.autoAllocationDef"/></legend>

<logic:present name="list">
	<table width="100%">
	
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.loanStage" /><span><font color="red">*</font></span>
      	</td>
      	<td width="13%">
      		
      		<html:text property="npaStage" styleClass="text" styleId="npaStage" maxlength="100" readonly="true" value="${list[0].npaStage}"/>
			<html:hidden property="lbxNPAStageId" styleId="lbxNPAStageId" value="${list[0].lbxNPAStageId}"/>
      	</td>
      	<td width="13%">
      		<bean:message key="lbl.allocationType" /><span><font color="red">*</font></span>
      	</td>
      	<td width="13%">
      	
      			<html:select property="type" styleId="type" styleClass="text"  value="${list[0].type}">
					<html:option value="" >---Select---</html:option>
					<html:option value="D">Date First</html:option>
					<html:option value="C">Charge First</html:option>
				</html:select>
	  	</td>
	</tr>
	
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.repaymentType" /><span><font color="red">*</font></span>
      	</td>
      	<td width="13%">
      		<html:hidden property="repayTypeHid" styleId="repayTypeHid" value="${list[0].repayType}"/>
      		<html:select property="repayType" styleId="repayType" styleClass="text"  value="${list[0].repayType}" disabled="true">
					<html:option value="" >---Select---</html:option>
					<html:option value="I">Installment</html:option>
					<html:option value="N">Non-Installment</html:option>
			</html:select>
	 	</td>
	 	 <td  width="13%"><bean:message key="lbl.active" /></td>
      <td  width="13%">
      	<logic:equal value="Active" name="status">
              <input type="checkbox" name="allocationStatus" id="allocationStatus" checked="checked" />
      </logic:equal>
      
         <logic:notEqual value="Active" name="status">
      	 		  <input type="checkbox" name="allocationStatus" id="allocationStatus"  />
         </logic:notEqual>
     	</td>
      	
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="white2">
    
        <td><b><bean:message key="lbl.component"/></b></td>
        <td><b><bean:message key="lbl.priority"/></b></td>
	</tr>
	<logic:iterate name="list" id="sublist">
	<logic:equal name="sublist" property="repayType" value="I">
	<div id="installmentDiv">
	<table>
	<tr>
	<td><table>
		<tr>
		
		<td><b><bean:message key="lbl.installmentCharges"/><span><font color="red">*</font></span></b></td>
		</tr>
		
		<tr>
			<td><b><bean:message key="lbl.preEmiCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
		<tr>
			<td><b><bean:message key="lbl.otherCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
	</table></td><td width="140px;">&nbsp;</td>
	<td><table>
	<tr>
			
			<td><html:hidden property="installmentChargesID" styleId="installmentChargesID" value="${list[0].installmentChargesID}"/>
			<html:text property="installmentCharges" styleClass="text" styleId="installmentCharges" maxlength="1" value="${list[0].installmentCharges}" /></td>
		</tr>
		<tr>
			
			<td><html:hidden property="preEmiChargesID" styleId="preEmiChargesID" value="${list[0].preEmiChargesID}"/>
			<html:text property="preEmiCharges" styleClass="text" styleId="preEmiCharges" maxlength="1" value="${list[0].preEmiCharges}" /></td>
		</tr>
		<tr>
			
			<td><html:hidden property="otherChargesID" styleId="otherChargesID" value="${list[0].otherChargesID}"/>
			<html:text property="otherCharges" styleClass="text" styleId="otherCharges" maxlength="1" value="${list[0].otherCharges}" /></td>
		</tr>
	</table></td><td>&nbsp;</td>
	</tr>
	
	
	</table>
	
	
	</div>
		
	
	</logic:equal>
	
	<logic:equal name="sublist" property="repayType" value="N">
	<div  id="nonInstallmentDiv">
	<table>
	<tr>
	<td>
	<table>
	<tr>
			<td><b><bean:message key="lbl.principalCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
		<tr>
			<td><b><bean:message key="lbl.interestCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
		<tr>
			<td><b><bean:message key="lbl.otherCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
	</table>
	</td><td width="140px"></td>
	<td>
	
	<table>
	<tr>
			
			<td><html:hidden property="principalChargesID" styleId="principalChargesID" value="${list[0].principalChargesID}"/>
			<html:text property="principalCharges" styleClass="text" styleId="principalCharges" maxlength="1" value="${list[0].principalCharges}" /></td>
		</tr>
		<tr>
			
			<td><html:hidden property="interestChargesID" styleId="interestChargesID" value="${list[0].interestChargesID}"/>
			<html:text property="interestCharges" styleClass="text" styleId="interestCharges" maxlength="1" value="${list[0].interestCharges}" /></td>
		</tr>
		<tr>
			
			<td><html:hidden property="otherChargesNonID" styleId="otherChargesNonID" value="${list[0].otherChargesNonID}"/>
			<html:text property="otherChargesNon" styleClass="text" styleId="otherChargesNon" maxlength="1" value="${list[0].otherChargesNon}" /></td>
		</tr>
		
	</table>
	</td><td>&nbsp;</td>
	</tr>
		
	</table>
	
	</div>
		
	
	</logic:equal>
	</logic:iterate>
	
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	
	
	<tr>
		<td>
			<button type="button"  name="save" id="save" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return updateRecords();"><bean:message key="button.save" /></button>
		</td>
	</tr>
</table>
</logic:present>

<logic:notPresent name="list">
	<table width="100%">
	
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.loanStage" /><span><font color="red">*</font></span>
      	</td>
      	<td width="13%">
      		
      		<html:text property="npaStage" styleClass="text" styleId="npaStage" maxlength="100" readonly="true" value=""/>
			<html:hidden property="lbxNPAStageId" styleId="lbxNPAStageId" value=""/>
			<html:hidden property="hid" styleId="hid" value=""/>
			<html:button property="npaStageButton" styleId="npaStageButton" tabindex="1" onclick="openLOVCommon(465,'autoAllocationDefinitionSearch','npaStage','','', '','','','hid')" value=" " styleClass="lovbutton"  ></html:button>
      	</td>
      	<td width="13%">
      		<bean:message key="lbl.allocationType" /><span><font color="red">*</font></span>
      	</td>
      	<td width="13%">
      			<html:select property="type" styleId="type" styleClass="text"  value="">
					<html:option value="" >---Select---</html:option>
					<html:option value="D">Date First</html:option>
					<html:option value="C">Charge First</html:option>
					
				</html:select>
	  	</td>
	</tr>
	
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.repaymentType" /><span><font color="red">*</font></span>
      	</td>
      	<td width="13%">
      		
      		<html:select property="repayType" styleId="repayType" styleClass="text"  value="" onchange="return activeDiv();">
					<html:option value="" >---Select---</html:option>
					<html:option value="I">Installment</html:option>
					<html:option value="N">Non-Installment</html:option>
			</html:select>
	 	</td>
	 	<td width="13%"><bean:message key="lbl.active" /></td>
      	<td width="13%">
           <input type="checkbox" name="allocationStatus" id="allocationStatus"  checked="checked"/>
     	</td>
      	
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="white2">
    
        <td><b><bean:message key="lbl.component"/></b></td>
        <td><b><bean:message key="lbl.priority"/></b></td>
	</tr>
	<tr  id="installmentDiv" style="display: none;">
	<td>
	
	<table>
		<tr>
			<td><b><bean:message key="lbl.installmentCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
		<tr>
			<td><b><bean:message key="lbl.preEmiCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
		<tr>
			<td><b><bean:message key="lbl.otherCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
	</table>
	</td><td><table>
	<tr>
			<td><html:text property="installmentCharges" styleClass="text" styleId="installmentCharges" maxlength="1" value="" /></td>
		</tr>
		<tr>
			
			<td><html:text property="preEmiCharges" styleClass="text" styleId="preEmiCharges" maxlength="1" value="" /></td>
		</tr>
		<tr>
			
			<td><html:text property="otherCharges" styleClass="text" styleId="otherCharges" maxlength="1" value="" /></td>
		</tr>
	</table></td>
	</tr>
		<tr  id="nonInstallmentDiv" style="display: none;">
		<td>
		<table>
		<tr>
			<td><b><bean:message key="lbl.principalCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
		<tr>
			<td><b><bean:message key="lbl.interestCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
		<tr>
			<td><b><bean:message key="lbl.otherCharges"/><span><font color="red">*</font></span></b></td>
			
		</tr>
	</table>
	</td><td><table>
	<tr>
			
			<td><html:text property="principalCharges" styleClass="text" styleId="principalCharges" maxlength="1" value="" /></td>
		</tr>
		<tr>
			
			<td><html:text property="interestCharges" styleClass="text" styleId="interestCharges" maxlength="1" value="" /></td>
		</tr>
		<tr>
			
			<td><html:text property="otherChargesNon" styleClass="text" styleId="otherChargesNon" maxlength="1" value="" /></td>
		</tr>
	</table></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>
			<button type="button"  name="save" id="save" class="topformbutton2"  title="Alt+V" accesskey="V" onclick="return saveRecords();"><bean:message key="button.save" /></button>
		</td>
	</tr>
</table>
</logic:notPresent>
</fieldset>
</html:form>

<logic:present name="ERROR">
	<script type="text/javascript">
		alert("<bean:message key="msg.notepadError" />");
		document.getElementById("autoAllocationDefinitionForm").action="autoAllocationDefinitionSearchBehind.do";
	    document.getElementById("autoAllocationDefinitionForm").submit();
	</script>
</logic:present>
<logic:present name="EXIST">
	<script type="text/javascript">
		alert("<bean:message key="lbl.npaAndRepayTypeExist" />");
	</script>
</logic:present>
<logic:present name="SAVE">
	<script type="text/javascript">
		alert("<bean:message key="lbl.dataSave" />");		
		document.getElementById("autoAllocationDefinitionForm").action="autoAllocationDefinitionSearchBehind.do";
	    document.getElementById("autoAllocationDefinitionForm").submit();
	</script>
</logic:present>
<logic:present name="UPDATE">
	<script type="text/javascript">
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("autoAllocationDefinitionForm").action="autoAllocationDefinitionSearchBehind.do";
	    document.getElementById("autoAllocationDefinitionForm").submit();
	</script>
</logic:present>

</body>	
</html:html>
	