<!--Author Name : Ravindra Kumar -->
<!--Date of Creation : -->
<!--Purpose  : provide interface to add new Ratio Definition -->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/ratioDefinition.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/capsScript/actionCodeScript.js"></script>
<script type="text/javascript">
function clearInput(){ 
<%if(request.getAttribute("Add")== "Add"){%>
document.getElementById('ratioCode').value='';
document.getElementById('ratioName').value='';
<%}%>
}
function fntab()
{
    if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('ratioDefinitionMasterForm').ratioName.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('ratioDefinitionMasterForm').ratioCode.focus();
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
<html:form styleId="ratioDefinitionMasterForm"  method="post"  action="/saveRatioDefinition" >
<html:javascript formName="RatioDefinitionMasterAddDynaValidatorForm"/>
<logic:notPresent name="editVal">
<fieldset>
<legend><bean:message key="lbl.ratioDefinitionMaster" /></legend>
 
  <table width="100%" >
  
   
<!-- <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>-->
 
 	 <tr>
   
    <td ><bean:message key="lbl.ratioCode" /><font color="red">*</font></td>
      <td ><html:text property="ratioCode" styleClass="text" styleId="ratioCode" onblur="fnChangeCase('ratioDefinitionMasterForm','ratioCode')" value="${requestScope.ratioDetail[0].ratioCode}"  maxlength="10" /></td>
      
      <td><bean:message key="lbl.ratioName" /><font color="red">*</font></td>
     <td><html:text property="ratioName" styleClass="text" styleId="ratioName" value="${requestScope.ratioDetail[0].ratioName}"  maxlength="50" />
    </td> </tr>
    <tr>	
    
     <td ><bean:message key="lbl.active" /></td>
      <td>
      		
      		 <logic:present name="ratioDetail">
      		
          		<logic:iterate name="ratioDetail" id="subratioDetail">
          		
          		<logic:equal name="subratioDetail" property="ratioStatus" value="X">
          		
          		  	<input type="checkbox" name="ratioStatus" id="ratioStatus" />
          		</logic:equal>
          		<logic:equal name="subratioDetail" property="ratioStatus" value="A">
          		
          		  	<input type="checkbox" name="ratioStatus" id="ratioStatus" checked="checked" />
          		</logic:equal>
          		</logic:iterate>
         	</logic:present>
         	<logic:notPresent name="ratioDetail">
         	
         		<input type="checkbox" name="ratioStatus" id="ratioStatus"  checked="checked"/>
         	</logic:notPresent>
      </td>
    
    
    </tr>
    </table></fieldset>
    <br/>
  	<fieldset>
<legend><bean:message key="lbl.ratioFormula" /></legend>
  <table width="100%" cellspacing="2" cellpadding="2" >
  	
   <tr>
   
   <td ><bean:message key="lbl.parameterCode" /><span><font color="red">*</font></span></td>
   <td >
   
<!--		 <html:select property="parameterCode" styleId="parameterCode" styleClass="text" value="${requestScope.ratioDetail[0].parameterCode}"  onchange="return makeExpression('parameterCode');">-->
<!--          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>-->
<!--          	  	<logic:present name="paramList">-->
<!--          	  		<html:optionsCollection name="paramList" label="parameCode" value="parameCode" />-->
<!--          	  	</logic:present>-->
<!--          	  -->
<!--          </html:select>-->
          
           <select name="parameterCode" id="parameterCode" class="text" value="${requestScope.ratioDetail[0].parameterCode}"  onchange="makeExpression('parameterCode');" >
          	  <option value=""   >--<bean:message key="lbl.select" />--</option>
          	  	<logic:present name="paramList">
          	  	
          	  	<logic:iterate name="paramList" id="paramlistobj">
          	  		
          	  		<option value="${paramlistobj.parameCode}">${paramlistobj.parameCode}</option>
          	  		</logic:iterate>
          	  	</logic:present>
          	  
          </select>
          
          <input type="hidden" id="allParamCodes" name="allParamCodes" value="${requestScope.ratioDetail[0].allParamCodes}"  />
  	</td>
    <td><bean:message key="lbl.ratioCode" /></td>
    <td>
    <html:select property="ratioCodeList" styleId="ratioCodeList" styleClass="text" onchange="getRatioFarmula();clearExpression();" value="">
    <html:option value="">Select</html:option>
    <html:optionsCollection name="ratioList" label="ratioName" value="ratioCode"/>
    </html:select>
    <input type="hidden" id="ratioCodeHid" name="ratioCodeHid" value=""  />
    </td>
	</tr>
	<tr>	
	<td><bean:message key="lbl.operator" /><font color="red">*</font></td>
	<td> 
	 <html:select property="operator" styleId="operator" styleClass="text" value="${requestScope.ratioDetail[0].operator}"  onchange="makeExpression('operator');" >
		<option value="">--<bean:message key="lbl.select" />--</option>
			<option value="+">+</option>
			<option value="-">-</option>
			<option value="/">/</option>
			<option value="%">%</option>
			<option value="*">*</option>
			<option value="(">(</option>
			<option value=")">)</option>
			
		</html:select>
	</td>
    <td><bean:message key="lbl.constant" /><span></span></td>
    <td><html:text property="constant" styleClass="text"  styleId="constant" maxlength="20" value="${requestScope.ratioDetail[0].constant}" onkeypress="return numbersonly(event,id,18)" onchange="return makeExpression('constant');"/></td>
  </tr>
    <tr>
    	<td><bean:message key="lbl.expression" /><span><font color="red">*</font></span></td>
    	<td  colspan="3" align="left">
    <html:textarea property="expression" styleId="expression" styleClass="text" rows="5" style="margin-left:1px !important;width:700px !important;"  cols="200" value="${requestScope.ratioDetail[0].expression}"></html:textarea>
	</td>
    </tr>
    
   <tr>
    <td colspan="4">
       
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
   <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRatioDefinition();" class="topformbutton2" ><bean:message key="button.save" /></button>
    <button type="button" class="topformbutton2" onclick="clearRatioExpression();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>

   </td></tr>

	</table>		
	</fieldset>
	</logic:notPresent>
	<logic:present name="editVal">
	<fieldset>
<legend><bean:message key="lbl.ratioDefinitionMaster" /></legend>
 
  <table width="100%">
  
   
<!-- <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>-->
 
 	 <tr>
   
    <td ><bean:message key="lbl.ratioCode" /><font color="red">*</font></td>
      <td ><html:text property="ratioCode" styleClass="text" styleId="ratioCode" readonly="true" value="${ratioData[0].ratioCode}"  maxlength="10" /></td>
      
      <td ><bean:message key="lbl.ratioName" /><font color="red">*</font></td>
     <td ><html:text property="ratioName" styleClass="text" styleId="ratioName" value="${ratioData[0].ratioName}"  maxlength="50" />
    </td> </tr>
    
    <tr>	
     <td ><bean:message key="lbl.active" /></td>
      <td>
      <logic:present name="ratioData">
    
          		<logic:iterate name="ratioData" id="subratioData">
          	
          		<logic:equal name="subratioData" property="ratioStatus" value="X">
          		
          		  	<input type="checkbox" name="ratioStatus" id="ratioStatus" />
          		</logic:equal>
          		<logic:equal name="subratioData" property="ratioStatus" value="A">
          		
          		  	<input type="checkbox" name="ratioStatus" id="ratioStatus" checked="checked" />
          		</logic:equal>
          		</logic:iterate>
         	</logic:present>
         	<logic:notPresent name="ratioData">
         	
         		<input type="checkbox" name="ratioStatus" id="ratioStatus" />
         	</logic:notPresent>
      
<!--            <input type="checkbox" name="ratioStatus" id="ratioStatus"  />-->
      </td>
    
    </tr>
    </table></fieldset>
    <br/>
  	<fieldset>
<legend><bean:message key="lbl.ratioFormula" /></legend>
  <table width="100%">
  	
   <tr>
   
   <td ><bean:message key="lbl.parameterCode" /><span><font color="red">*</font></span></td>
   <td ><input type="hidden" id="flag" value="true"/>
   
<!--   <html:select property="parameterCode" styleId="parameterCode" styleClass="text" value="${ratioData[0].parameterCode}" onchange="clearExpression();makeExpression('parameterCode');">-->
<!--          	  	<html:option value="" >--<bean:message key="lbl.select" />--</html:option>-->
<!--          	  	<logic:present name="paramList">-->
<!--          	  		<html:optionsCollection name="paramList" label="parameCode" value="parameCode" />-->
<!--          	  	</logic:present>-->
<!--          	  -->
<!--          </html:select>-->
          
          
           <select name="parameterCode" id="parameterCode" class="text" onchange="clearExpression();makeExpression('parameterCode');" >
          	  <option  value="${ratioData[0].parameterCode}">--<bean:message key="lbl.select" />--</option>
          	  	<logic:present name="paramList">
          	  	
          	  	<logic:iterate name="paramList" id="paramlistobj">
          	  		
          	  		<option value="${paramlistobj.parameCode}" >${paramlistobj.parameCode}</option>
          	  	</logic:iterate>
          	  	
          	  	</logic:present>
          	  
          </select>
          
	<input type="hidden" id="allParamCodes" name="allParamCodes" value="${ratioData[0].allParamCodes}"/>
  	</td>
  	<td><bean:message key="lbl.ratioCode" /></td>
    <td>
    <html:select property="ratioCodeList" styleId="ratioCodeList" styleClass="text"  onchange="getRatioFarmula();clearExpression();" value="">
    <html:option value="">Select</html:option>
    <html:optionsCollection name="ratioList" label="ratioName" value="ratioCode"/>
    </html:select>
     <input type="hidden" id="ratioCodeHid" name="ratioCodeHid" value=""  />
    </td>
    
	</tr>
	<tr>
   <td><bean:message key="lbl.operator" /><font color="red">*</font></td>
	<td> 
	 <html:select property="operator" styleId="operator" styleClass="text" value="${ratioData[0].operator}" onchange="clearExpression();makeExpression('operator');" >
		<option value="">--<bean:message key="lbl.select" />--</option>
			<option value="+">+</option>
			<option value="-">-</option>
			<option value="/">/</option>
			<option value="%">%</option>
			<option value="*">*</option>
			<option value="(">(</option>
			<option value=")">)</option>
			
		</html:select>
	</td>	
    <td><bean:message key="lbl.constant" /></td>
    <td><html:text property="constant" styleClass="text"  styleId="constant" maxlength="20" value="${ratioData[0].constant}" onkeypress="return numbersonly(event,id,18)" onchange="return makeExpression('constant');"/></td>
  </tr>
    <tr>
    	<td ><bean:message key="lbl.expression" /><span><font color="red">*</font></span></td>
    <td colspan="3" align="left">
    <html:textarea property="expression" styleId="expression" styleClass="text" rows="5" style="margin-left:1px !important;width:700px !important;" cols="200"  value="${ratioData[0].expression}"></html:textarea>
	</td>
    </tr>
    
   <tr>
     <td colspan="4">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
  <button type="button" id="save" title="Alt+V" accesskey="V" onclick="updateRatioDefinition('${ratioData[0].ratioCode}');" class="topformbutton2" > <bean:message key="button.save" /></button>
    <button type="button" class="topformbutton2" onclick="clearRatioExpression();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>

   </td></tr>

	</table>		
	</fieldset>
	</logic:present>
	</html:form>		
	<logic:present name="sms">
      <script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("ratioDefinitionMasterForm").action="ratioDefinitionMasterBehindAction.do";
	    document.getElementById("ratioDefinitionMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("ratioDefinitionMasterForm").action="ratioDefinitionMasterBehindAction.do";
	    document.getElementById("ratioDefinitionMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='V')
	{
		alert("<bean:message key="lbl.notValidExpression" />");
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.ratioCodeExist" />");
		
	}
	
</script>
</logic:present>
  </body>
		</html:html>