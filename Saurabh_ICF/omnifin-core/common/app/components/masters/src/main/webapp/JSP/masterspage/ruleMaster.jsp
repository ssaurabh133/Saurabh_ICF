<!--Author Name : Ravindra Kumar -->
<!--Date of Creation : -->
<!--Purpose  : provide interface to add new Rule master -->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/ruleMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/ratioDefinition.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/capsScript/actionCodeScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript">
function clearInput(){ 
<%if(request.getAttribute("Add")== "Add"){%>
document.getElementById('ruleCode').value='';
document.getElementById('ruleName').value='';
<%}%>
}
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('ruleMasterForm').ruleName.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('ruleMasterForm').ruleCode.focus();
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

<body onload="enableAnchor();clearInput();fntab();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="ruleMasterForm"  method="post"  action="/saveRuleMaster" >
<html:javascript formName="RuleMasterAddDynaValidatorForm"/>
<logic:notPresent name="editVal">
<fieldset>
<legend><bean:message key="lbl.ruleMaster" /></legend>
 
  <table width="100%">
  
   
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
 
 	 <tr>
   
    <td width="13%"><bean:message key="lbl.ruleCode" /><font color="red">*</font> </td>
      <td width="13%"><html:text property="ruleCode" styleClass="text" styleId="ruleCode" onblur="fnChangeCase('ruleMasterForm','ruleCode')" value="${requestScope.ruleDetail[0].ruleCode}"  maxlength="10" /></td>
      
      <td width="13%"><bean:message key="lbl.ruleName" /><font color="red">*</font></td>
     <td width="13%"><html:text property="ruleName" styleClass="text" styleId="ruleName" value="${requestScope.ruleDetail[0].ruleName}"  maxlength="50" />
    </td> </tr>
    
     <tr>
   
    <td width="13%"><bean:message key="lbl.ruleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="ruleType" styleId="ruleType" styleClass="text" value="${requestScope.ruleDetail[0].ruleType}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="ruleList">
          	  		<html:optionsCollection name="ruleList" label="ruleDescription" value="ruleValue" />
          	  	</logic:present>
          	  
          </html:select>
        </td>
        
    <td width="13%"><bean:message key="lbl.stageForRule" /><font color="red">*</font></td>
    <td>
    <html:select property="stageForRule" styleId="stageForRule" styleClass="text" value="" >
    <html:option value="">--Select--</html:option>
    <html:option value="DC">DEAL CAPTURING</html:option>
    <html:option value="LIM">LOAN INITIATION MAKER</html:option>
    </html:select></td>
    </tr>
    <tr>
      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleType" styleId="subRuleType" styleClass="text" value="${requestScope.ruleDetail[0].subRuleType}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td> 
      <td width="13%"><bean:message key="lbl.active" /></td>
      <td width="13%">
      <logic:present name="ruleDetail">
    
       		<logic:iterate name="ruleDetail" id="subruleDetail">
          		<logic:equal name="subruleDetail" property="ruleStatus" value="X">
          		  	<input type="checkbox" name="ruleStatus" id="ruleStatus" />
          		</logic:equal>
          		<logic:equal name="subruleDetail" property="ruleStatus" value="A">
          		  	<input type="checkbox" name="ruleStatus" id="ruleStatus" checked="checked" />
          		</logic:equal>
       		</logic:iterate>
      </logic:present>
         	<logic:notPresent name="ruleDetail">
         		<input type="checkbox" name="ruleStatus" id="ruleStatus" checked="checked"/>
         	</logic:notPresent>
      
<!--            <input type="checkbox" name="ratioStatus" id="ratioStatus"  />-->
      </td>
     </tr>
    
    
    
    </table></fieldset>
    <br/>
  	<fieldset>
<legend><bean:message key="lbl.ruleFormula" /></legend>
  <table width="100%">
  	
   <tr>
   
   <td  width="13%"><bean:message key="lbl.parameterCode" /></td>
   <td  width="13%">
   
		 <select name="parameterCode" id="parameterCode" class="text" >
          	  <option value=""   >--<bean:message key="lbl.select" />--</option>
          	  	<logic:present name="paramList">
          	  	
          	  	<logic:iterate name="paramList" id="paramlistobj">
          	  		
          	  		<option value="${paramlistobj.parameCode}" onclick="ruleMasterMakeExpression('parameterCode');">${paramlistobj.parameCode}</option>
          	  		</logic:iterate>
          	  	</logic:present>
          	  
          </select>
          
          <input type="hidden" id="allParamCodes" name="allParamCodes" value="${requestScope.ruleDetail[0].allParamCodes}"/>
  	</td>
    <td width="13%"></td>
	<td width="13%"></td>
	</tr>

    <tr>

    	<td width="13%" colspan="4"><bean:message key="lbl.expression" /><span><font color="red">*</font></span>
   <html:textarea property="expression" styleId="expression" styleClass="text" rows="5" style="margin-left:194px !important;width:700px !important;" cols="50" value="${requestScope.ruleDetail[0].expression}"></html:textarea>

<!--    	<td width="13%"><bean:message key="lbl.expression" /><span><font color="red">*</font></span></td>-->
<!--    <td ><html:textarea property="expression" styleId="expression" styleClass="text" rows="5"  cols="200" value="${requestScope.ruleDetail[0].expression}"></html:textarea>-->

	</td>
    </tr>
    
   <tr>
    <td width="13%">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />

   	<button  type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRuleMaster();" class="topformbutton2" ><bean:message key="button.save" /></button>
    <button type="button"  class="topformbutton2" onclick="clearRatioExpression();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
   
   </td></tr>

	</table>		
	</fieldset>
	</logic:notPresent>
	<logic:present name="editVal">
	<fieldset>
<legend><bean:message key="lbl.ruleMaster" /></legend>
 
  <table width="100%">
  
   
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
 
 	 <tr>
       <td><bean:message key="lbl.ruleCode" /><font color="red">*</font></td>
      <td><html:text property="ruleCode" styleClass="text" styleId="ruleCode"   readonly="true" value="${ruleData[0].ruleCode}"  maxlength="10" /></td>
      
      <td><bean:message key="lbl.ruleName" /><font color="red">*</font></td>
     <td><html:text property="ruleName" styleClass="text" styleId="ruleName"  onblur="fnChangeCase('ruleMasterForm','ruleCode')" value="${ruleData[0].ruleName}"  maxlength="50" />
    </td>
     </tr>
    
     <tr>
   
    <td><bean:message key="lbl.ruleType" /><font color="red">*</font></td>
      <td>
		<html:select property="ruleType" styleId="ruleType" styleClass="text" value="${ruleData[0].ruleType}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="ruleList">
          	  		<html:optionsCollection name="ruleList" label="ruleDescription" value="ruleValue" />
          	  	</logic:present>
          	 
          </html:select></td>
          
          <td><bean:message key="lbl.stageForRule" /><font color="red">*</font></td>
				<td>
				<html:select property="stageForRule" styleId="stageForRule" styleClass="text" value="${ruleData[0].stageForRule}" >
				<html:option value="">--Select--</html:option>
				<html:option value="DC">DEAL CAPTURING</html:option>
				<html:option value="LIM">LOAN INITIATION MAKER</html:option>
				</html:select></td>
          
          
</tr>
      <tr>
      <td width="13%"><bean:message key="lbl.subRuleType" /><font color="red">*</font></td>
      <td width="13%">
		<html:select property="subRuleType" styleId="subRuleType" styleClass="text" value="${ruleData[0].subRuleType}">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="subRuleList">
          	  		<html:optionsCollection name="subRuleList" label="subRuleDescription" value="subRuleValue" />
          	  	</logic:present>
          	  
          </html:select>
      </td>
      <td><bean:message key="lbl.active" /></td>
      <td>
      <logic:present name="ruleData">
    
       		<logic:iterate name="ruleData" id="subruleData">
          		<logic:equal name="subruleData" property="ruleStatus" value="X">
          		  	<input type="checkbox" name="ruleStatus" id="ruleStatus" />
          		</logic:equal>
          		<logic:equal name="subruleData" property="ruleStatus" value="A">
          		  	<input type="checkbox" name="ruleStatus" id="ruleStatus" checked="checked" />
          		</logic:equal>
       		</logic:iterate>
      </logic:present>
         	<logic:notPresent name="ruleData">
         		<input type="checkbox" name="ruleStatus" id="ruleStatus"  checked="checked"/>
         	</logic:notPresent>
      
      </td>
      
<!--      <td></td>-->
<!--     <td></td>-->
     </tr>
     
    </table></fieldset>
    <br/>
  	<fieldset>
<legend><bean:message key="lbl.ruleFormula" /></legend>
  <table width="100%">
  	
   <tr>
   
   <td><bean:message key="lbl.parameterCode" /></td>
   <td><input type="hidden" id="flag" value="true"/>
    <html:select property="parameterCode" styleId="parameterCode" styleClass="text" value="${ruleData[0].parameterCode}" onchange="clearExpression();">
          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>
          	  	<logic:present name="paramList">
          	  		<logic:iterate name="paramList" id="paramlistobj">
          	  		
          	  		<option value="${paramlistobj.parameCode}" onclick="ruleMasterMakeExpression('parameterCode');">${paramlistobj.parameCode}</option>
          	  		</logic:iterate>
          	  	</logic:present>
          	  
     </html:select>
		<input type="hidden" id="allParamCodes" name="allParamCodes" value="${ruleData[0].allParamCodes}"/>
  	</td>
    <td></td>
	<td> </td>
	</tr>
	
    <tr>
    	<td colspan="4"><bean:message key="lbl.expression" /><span><font color="red">*</font></span>
    <html:textarea property="expression" styleId="expression" styleClass="text" rows="5" cols="50" value="${ruleData[0].expression}"  style="margin-left:194px !important;width:700px !important;"></html:textarea>
	</td>
    </tr>
    
   <tr>
    <td>
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return updateRule('${ruleData[0].ruleCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
    <button type="button"  class="topformbutton2" onclick="clearRatioExpression();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
	
   </td></tr>

	</table>		
	</fieldset>
	</logic:present>	 
	</html:form>		
	
	<logic:present name="error">
      <script type="text/javascript">
		alert("<%=request.getAttribute("error")%>");
	</script>
</logic:present>
	<logic:present name="sms">
      <script type="text/javascript">
     
    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("ruleMasterForm").action="ruleMasterSearchBehindAction.do";
	    document.getElementById("ruleMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
			document.getElementById("ruleMasterForm").action="ruleMasterSearchBehindAction.do";
	    document.getElementById("ruleMasterForm").submit();
	}
  	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='EX')
	{
		alert("<bean:message key="lbl.ruleCodeExist" />");
		
	}
	else{
		alert("<bean:message key="msg.notepadError" />");
	}
</script>
</logic:present>
  </body>
		</html:html>