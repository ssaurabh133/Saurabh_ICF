<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 15-Oct-2011-->
<!--Purpose  : Information of Financial Prameter-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/financialPrameterMaster.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('finPramForm').pramCode.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('finPramForm').pramName.focus();
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


 <body onload="enableAnchor();addField();fntab();init_fields();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/finMasterAdd" styleId="finPramForm" method="POST" >
<html:javascript formName="FinancialPramMasterAddDyanavalidatiorForm" />	
<fieldset>

<legend><bean:message key="lbl.financailPram"/></legend>

  <table width="100%" height="86";">
    

   
    
   <logic:present name="editVal">
      
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
       
       <tr>
     		 <td><bean:message key="lbl.pramCode" /><span><font color="red">*</font></span></td>
   		    <td><html:text property="pramCode" styleClass="text" styleId="pramCode" maxlength="20" onblur="fnChangeCase('finPramForm','pramCode')" value="${requestScope.list[0].pramCode}" readonly="true" /></td>
      
      		 <td><bean:message key="lbl.pramName" /><span><font color="red">*</font></span></td>
     		 <td>
     		<logic:equal value="Y" name="systemDefined">
     		  <html:text property="pramName" styleClass="text" styleId="pramName" maxlength="50" onblur="fnChangeCase('finPramForm','pramName')" value="${requestScope.list[0].pramName}" readonly="true"  />
             </logic:equal>
             <logic:notEqual value="Y" name="systemDefined">
             <html:text property="pramName" styleClass="text" styleId="pramName" maxlength="50" onblur="fnChangeCase('finPramForm','pramName')" value="${requestScope.list[0].pramName}" />
             </logic:notEqual>
     		 </td>
      </tr>
<tr>

 <td><bean:message key="lbl.pramType"/></td>
              <td>
              <logic:equal value="Y" name="systemDefined">
              <html:select property="pramType1" styleId="pramType1" styleClass="text" value="${requestScope.list[0].pramType}" disabled="true">
             		<html:option value="B">BALANCE SHEET</html:option>
               		 <html:option value="P">PROFIT AND LOSS</html:option>
               		 <html:option value="O">OTHERS</html:option>
               		 <html:option value="I">INDIVIDUAL ANALYSIS</html:option>
      		 </html:select>
      		 <input type="hidden" name="pramType"  value="${requestScope.list[0].pramType}"/>
       </logic:equal>
       <logic:notEqual value="Y" name="systemDefined">
       <html:select property="pramType" styleId="pramType" styleClass="text" onchange="changePramType();" value="${requestScope.list[0].pramType}">
               		<html:option value="B">BALANCE SHEET</html:option>
               		 <html:option value="P">PROFIT AND LOSS</html:option>
               		 <html:option value="O">OTHERS</html:option>
               		 <html:option value="I">INDIVIDUAL ANALYSIS</html:option>
      		 </html:select>
       </logic:notEqual>
              
      		 
      		 </td>
      		 
      		 <logic:present name="pramType">
      		  <td id="subTypeLabel"><bean:message key="lbl.subType"/><span><font color="red">*</font></span></td>
              <td id="subTypeValue"><html:select property="subType" styleId="subType" styleClass="text" value="${requestScope.list[0].subType}">
                    <html:option value="">--SELECT--</html:option>
               		<html:option value="A">ASSET</html:option>
               		 <html:option value="L">LIABILITY</html:option>
      		 </html:select></td>
      		 </logic:present>
      		 
      		 <logic:notPresent name="pramType">
      		  <td id="subTypeLabel" style="display: none;"><bean:message key="lbl.subType"/><span><font color="red">*</font></span></td>
              <td id="subTypeValue" style="display: none;"><html:select property="subType" styleId="subType" styleClass="text" >
                    <html:option value="">--SELECT--</html:option>
               		<html:option value="A">ASSET</html:option>
               		 <html:option value="L">LIABILITY</html:option>
      		 </html:select></td>
      		 </logic:notPresent>
      	     		
      		 </tr>
      		 
<tr>

  <td><bean:message key="lbl.sequenceNo"/><span><font color="red">*</font></span></td>
  <td><html:text property="sequenceNo" styleClass="text" styleId="sequenceNo" value="${requestScope.list[0].sequenceNo}" maxlength="10" /></td>

<td><bean:message key="lbl.negativeAllowed"/> </td>
<td>
      <logic:equal value="Active" name="negativeAllowed">
             <input type="checkbox" name="negativeAllowed" id="negativeAllowed" checked="checked"/>
      </logic:equal>
      
      <logic:notEqual value="Active" name="negativeAllowed">
      	 	<input type="checkbox" name="negativeAllowed" id="negativeAllowed" />
      </logic:notEqual>
  </td>
  
  </tr> 
     
<tr>
     <td><bean:message key="lbl.AutoCalculated"/> </td>
  <td>
      <logic:equal value="Y" name="autoCalculated">
             <input type="checkbox" name="autoCalculated" id="autoCalculated" checked="checked" onclick="addField();"/>
      </logic:equal>
      
      <logic:notEqual value="Y" name="autoCalculated">
      	 	<input type="checkbox" name="autoCalculated" id="autoCalculated"  onclick="addField();"/>
      </logic:notEqual>
  </td>
 
   <td><bean:message key="lbl.status"/> </td>
  <td>
      <logic:equal value="Active" name="status">
             <input type="checkbox" name="pramStatus" id="pramStatus" checked="checked"/>
      </logic:equal>
      
      <logic:notEqual value="Active" name="status">
      	 	<input type="checkbox" name="pramStatus" id="pramStatus" />
      </logic:notEqual>
  </td>
  </tr> 
  
   	 </logic:present>
    
    <logic:notPresent name="editVal">
 
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
       <tr>
    		<td><bean:message key="lbl.pramCode" /><span><font color="red">*</font></span></td>
   		    <td><html:text property="pramCode" styleClass="text" styleId="pramCode" maxlength="20"  onblur="fnChangeCase('finPramForm','pramCode'); varifyValue();" /></td>
      
      		 <td><bean:message key="lbl.pramName" /><span><font color="red">*</font></span></td>
     		 <td><html:text property="pramName" styleClass="text" styleId="pramName" maxlength="50" onblur="fnChangeCase('finPramForm','pramName')" /></td>
      </tr> 

<tr>

 <td><bean:message key="lbl.pramType"/></td>
              <td><html:select property="pramType" styleId="pramType" onchange="changePramType();" styleClass="text" >
               		<html:option value="B">BALANCE SHEET</html:option>
               		 <html:option value="P">PROFIT AND LOSS</html:option>
               		 <html:option value="O">OTHERS</html:option>
               		 <html:option value="I">INDIVIDUAL ANALYSIS</html:option>
      		 </html:select></td>

      		 
      		 <td id="subTypeLabel"><bean:message key="lbl.subType"/><span><font color="red">*</font></span></td>
              <td id="subTypeValue"><html:select property="subType" styleId="subType" styleClass="text" >
                    <html:option value="">--SELECT--</html:option>
               		<html:option value="A">ASSET</html:option>
               		 <html:option value="L">LIABILITY</html:option>
      		 </html:select></td>
      		 
</tr>  
  
<tr>
  <td><bean:message key="lbl.sequenceNo"/><span><font color="red">*</font></span></td>
  <td><html:text property="sequenceNo" styleClass="text" styleId="sequenceNo" maxlength="10" /></td>
  
<td><bean:message key="lbl.negativeAllowed"/> </td>
<td>
      <logic:equal value="Active" name="negativeAllowed">
             <input type="checkbox" name="negativeAllowed" id="negativeAllowed" checked="checked"/>
      </logic:equal>
      
      <logic:notEqual value="Active" name="negativeAllowed">
      	 	<input type="checkbox" name="negativeAllowed" id="negativeAllowed" />
      </logic:notEqual>
  </td>
  </tr>
  
  <tr>
     <td><bean:message key="lbl.AutoCalculated"/> </td>
  <td>
      <logic:equal value="Y" name="autoCalculated">
             <input type="checkbox" name="autoCalculated" id="autoCalculated" checked="checked" onclick="addField();" />
      </logic:equal>
      
      <logic:notEqual value="Y" name="autoCalculated">
      	 	<input type="checkbox" name="autoCalculated" id="autoCalculated" onclick="addField();" />
      </logic:notEqual>
  </td>

   <td><bean:message key="lbl.status"/> </td>
  <td>
             <logic:equal value="Active" name="status">
             <input type="checkbox" name="pramStatus" id="pramStatus" checked="checked"/>
      </logic:equal>
      
      <logic:notEqual value="Active" name="status">
      	 	<input type="checkbox" name="pramStatus" id="pramStatus" />
      </logic:notEqual>
  </td>
  </tr>    		       
    </logic:notPresent> 
    



  <tr id="saveButton"><td>
    <br>
    <logic:present name="editVal">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnFinEdit();" class="topformbutton2"  ><bean:message key="button.save" /></button>
   </logic:present>
    
   <logic:notPresent name="editVal">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnFinSave();" class="topformbutton2"  ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> </tr>
  
		
	</table>		

</fieldset>
<!--sachin work start-->
<div id="financialCapturing" style="display: none;">
 <logic:present name="editVal">

<fieldset>
<legend><bean:message key="lbl.financialFormula" /></legend>
  <table width="100%" cellspacing="2" cellpadding="2" >
  	
   <tr>
   
   <td ><bean:message key="lbl.parameterCode" /><span><font color="red">*</font></span></td>
   <td ><input type="hidden" id="flag" value="true"/>
   
       
           <select name="finParameterCode" id="finParameterCode" class="text" value=""  onchange="clearExpression();makeExpression('finParameterCode');" >
          	  <option  value="${ratioData[0].parameterCode}">--<bean:message key="lbl.select" />--</option>
          	  	<logic:present name="paramList">
          	  	
          	  	<logic:iterate name="paramList" id="paramlistobj">
          	  		
          	  		<option value="${paramlistobj.parameCode}" >${paramlistobj.parameCode}</option>
          	  	</logic:iterate>
          	  	
          	  	</logic:present>
          	  
          </select>
          

  	</td>

	</tr>
	<tr>	
	<td><bean:message key="lbl.operator" /><font color="red">*</font></td>
	<td> 
	 <html:select property="finOperator" styleId="finOperator" styleClass="text" value="${requestScope.list[0].finOperator}"  onchange="makeExpression('finOperator');" >
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
    <td><html:text property="finConstant" styleClass="text"  styleId="finConstant" maxlength="20" value="${requestScope.list[0].finConstant}" onkeypress="return numbersonly(event,id,18)" onchange="return makeExpression('finConstant');"/></td>
  </tr>
    <tr>
    	<td><bean:message key="lbl.expression" /><span><font color="red">*</font></span></td>
    	<td  colspan="3" align="left">
    <html:textarea property="finExpression" styleId="finExpression" styleClass="text" rows="5" style="margin-left:1px !important;width:700px !important;"  cols="200" value="${requestScope.list[0].finExpression}"></html:textarea>
	</td>
    </tr>
   
   <tr><td colspan="4">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnFinEdit();" class="topformbutton2"  ><bean:message key="button.save" /></button>
      <button type="button" class="topformbutton2" onclick="clearRatioExpression();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
	</td> </tr> 
   <tr>

	</table>		
	</fieldset>

</logic:present>
 <logic:notPresent name="editVal">

<fieldset>
<legend><bean:message key="lbl.financialFormula" /></legend>
  <table width="100%" cellspacing="2" cellpadding="2" >
  	
   <tr>
   
   <td ><bean:message key="lbl.parameterCode" /><span><font color="red">*</font></span></td>
   <td ><input type="hidden" id="flag" value="true"/>
   
       
           <select name="finParameterCode" id="finParameterCode" class="text" value=""  onchange="makeExpression('finParameterCode');" >
          	  <option  value="${requestScope.list[0].parameterCode}">--<bean:message key="lbl.select" />--</option>
          	  	<logic:present name="paramList">
          	  	
          	  	<logic:iterate name="paramList" id="paramlistobj">
          	  		
          	  		<option value="${paramlistobj.parameCode}" >${paramlistobj.parameCode}</option>
          	  	</logic:iterate>
          	  	
          	  	</logic:present>
          	  
          </select>
          
          <input type="hidden" id="finAllParamCodes" name="finAllParamCodes" value="${requestScope.list[0].finAllParamCodes}"  />
  	</td>

	</tr>
	<tr>	
	<td><bean:message key="lbl.operator" /><font color="red">*</font></td>
	<td> 
	 <html:select property="finOperator" styleId="finOperator" styleClass="text" value="${requestScope.list[0].finOperator}"  onchange="makeExpression('finOperator');" >
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
    <td><html:text property="finConstant" styleClass="text"  styleId="finConstant" maxlength="20" value="${requestScope.list[0].finConstant}" onkeypress="return numbersonly(event,id,18)" onchange="return makeExpression('finConstant');"/></td>
  </tr>
    <tr>
    	<td><bean:message key="lbl.expression" /><span><font color="red">*</font></span></td>
    	<td  colspan="3" align="left">
    <html:textarea property="finExpression" styleId="finExpression" styleClass="text" rows="5" style="margin-left:1px !important;width:700px !important;"  cols="200" value="${requestScope.list[0].finExpression}"></html:textarea>
	</td>
    </tr>   
   <tr><td colspan="4">
    
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick=" fnFinSave();" class="topformbutton2"  ><bean:message key="button.save" /></button>
    <button type="button" class="topformbutton2" onclick="clearRatioExpression();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>

	</td> </tr> 
   <tr>

	</table>		
	</fieldset>

</logic:notPresent>
</div>
</html:form>

<!--sachin work end -->


<logic:present name="sms">
		<script type="text/javascript">

			if('<%=request.getAttribute("sms").toString()%>'=="S")
			{
				alert("Data Saved Successfully");
				document.getElementById("finPramForm").action="financialPramBehind.do";
	    		document.getElementById("finPramForm").submit();
			}
			else if('<%=request.getAttribute("sms").toString()%>'=='M')
	        {
		        alert("<bean:message key="lbl.dataModify" />");
		        document.getElementById("finPramForm").action="financialPramBehind.do";
	            document.getElementById("finPramForm").submit();
	        }else if('<%=request.getAttribute("sms").toString()%>'=='A')
	        {
		        alert("Data Already Exists");
		       
	        }else if('<%=request.getAttribute("sms").toString()%>'=='AS')
	        {
		        alert("Sequence No Already Exists");
		       
	        }
	        //add by sachin
	        else if('<%=request.getAttribute("sms").toString()%>'=='V')
			{
			alert("<bean:message key="lbl.notValidExpression" />");
			}
	        //end by sachin
	        else if('<%=request.getAttribute("sms").toString()%>'=='E')
					{
						
						 alert("<bean:message key="msg.notepadError" />");
					}
	    	else
					{
						alert("Data already Exist");
					}
	</script>
</logic:present>
</body>
</html:html>
