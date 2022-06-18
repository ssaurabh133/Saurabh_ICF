<!--Author Name : Ritu Jndal-->
<!--Date of Creation : 07-july-2011-->


<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
boolean strReadonly = false;
boolean strDisable = false;
String strDisabled = "";
String regular = CommonFunction.checkNull(request.getAttribute("regular"));

if(!regular.equalsIgnoreCase("")){
strReadonly = true;
strDisable = true;
strDisabled = "Disabled";
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/npaStageScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('npaStageMaster').sequenceNo.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('npaStageMaster').npaStage.focus();
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
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<html:form action="/npaStageMasterAdd" styleId="npaStageMaster" method="POST" >
<html:errors/>

<fieldset>

<legend><bean:message key="lbl.npaStageMaster"/></legend>

  <table width="100%" height="86";">
    
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
  <tr>
  			<td><bean:message key="lbl.productId" /><span><font color="red">*</font></span></td>
  		 <td>	
  		 		<html:select property="productId" styleId="productId" styleClass="text" value="${requestScope.list[0].productId}" style="width: 160px;" disabled="true" >
						<html:option value="">--Select--</html:option>
						 <logic:present name="product">
							<html:optionsCollection name="product" label="proDesc" value="productId" />
						</logic:present>
				</html:select>
		</td>
  </tr>
  <tr>
    		 <td><bean:message key="lbl.npaStage" /><span><font color="red">*</font></span></td>
     		 <td><html:text property="npaStage" styleClass="text" styleId="npaStage" maxlength="10" tabindex="-1" readonly="true" value="${requestScope.list[0].npaStage}" onblur="fnChangeCase('npaStagemaster','npaStage')"/></td>
   	 
   	         <td><bean:message key="lbl.sequenceNo"/><span><font color="red">*</font></span></td>
             <td><label><html:text property="sequenceNo" styleClass="text" style="text-align: right" styleId="sequenceNo" maxlength="3" readonly="true" value="${requestScope.list[0].sequenceNo}" /></label></td>
      </tr>
      <tr>
      
             <td><bean:message key="lbl.npaCriteria" /><span><font color="red">*</font></span></td>
		     <td><html:select property="npaCriteria" styleId="npaCriteria" disabled="<%=strDisable%>"  styleClass="text" value="${requestScope.list[0].npaCriteria}">
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="D">DPD</html:option>
		      	      <html:option value="I">Installments</html:option>
	        </html:select></td>
	        
	         <td><bean:message key="lbl.npaCriteriaValue" /><span><font color="red">*</font></span></td>
     		 <td><html:text property="npaCriteriaValue" styleClass="text" styleId="npaCriteriaValue" style="text-align: right" readonly="<%=strReadonly%>" maxlength="5"  value="${requestScope.list[0].npaCriteriaValue}" /></td>
   	 
   	 </tr>
   	 <tr>
   	        <td><bean:message key="lbl.moveToNext" /><span><font color="red">*</font></span></td>
		     <td><html:select property="moveToNext" styleId="moveToNext" styleClass="text" disabled="<%=strDisable%>"  value="${requestScope.list[0].moveToNext}">
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="M">Manual</html:option>
		      	      <html:option value="A">Auto</html:option>
	        </html:select></td>
	        
	        <td><bean:message key="lbl.moveToPrevious" /><span><font color="red">*</font></span></td>
		     <td><html:select property="moveToPrevious" styleId="moveToPrevious" styleClass="text" disabled="<%=strDisable%>"  value="${requestScope.list[0].moveToPrevious}">
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="M">Manual</html:option>
		      	      <html:option value="A">Auto</html:option>
	        </html:select></td>
	    
	    </tr>
	    <tr>    
	  <td>
      <bean:message key="lbl.billingFlag"/> </td>
      <td>
      <logic:equal value="Yes" name="billingStatus">
              <input type="checkbox" name="billingFlagStatus" <%=strDisabled%>  id="billingFlagStatus" checked="checked" />
      </logic:equal>
      
      <logic:notEqual value="Yes" name="billingStatus">
      	 	 <input type="checkbox" name="billingFlagStatus" <%=strDisabled%>  id="billingFlagStatus"  />
      </logic:notEqual></td>
      
      <td>
      <bean:message key="lbl.AccrualFlag"/> </td>
      <td>
      <logic:equal value="Yes" name="AccrualStatus">
              <input type="checkbox" name="accrualFlagStatus" <%=strDisabled%>  id="accrualFlagStatus" checked="checked" />
      </logic:equal>
      
      <logic:notEqual value="Yes" name="AccrualStatus">
      	 	 <input type="checkbox" name="accrualFlagStatus" <%=strDisabled%> id="accrualFlagStatus"  />
      </logic:notEqual></td>
      
      </tr>
      <tr>
       <td>
      <bean:message key="lbl.sdAccrualFlag"/></td>
      <td>
      <logic:equal value="Yes" name="SdAccrualStatus">
              <input type="checkbox" name="sdAccrualFlag" <%=strDisabled%>  id="sdAccrualFlag" checked="checked" />
              
      </logic:equal>
      
      <logic:notEqual value="Yes" name="SdAccrualStatus">
      	 	 <input type="checkbox" name="sdAccrualFlag" <%=strDisabled%>  id="sdAccrualFlag"  />
      </logic:notEqual></td>
     <td>
     <bean:message key="lbl.status"/> </td>
     <td>
     <logic:equal name="status" value="Active"  >
              <input type="checkbox" name="npaStageStatus" <%=strDisabled%> id="npaStageStatus" checked="checked" />
     </logic:equal>
      
     <logic:notEqual value="Active" name="status">
      	 	 <input type="checkbox" name="npaStageStatus"  <%=strDisabled%> id="npaStageStatus"  />
      </logic:notEqual></td>
   	  </tr>      
   	 </logic:present>
   	 
  
   
   	 <logic:notPresent name="editVal">
   	 <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   	 
   	  <tr>
  			<td><bean:message key="lbl.productId" /><span><font color="red">*</font></span></td>
  		 <td>	<html:select property="productId" styleId="productId" styleClass="text" value="${productId}" style="width: 160px;">
						<html:option value="">--Select--</html:option>
						 <logic:present name="product">
							<html:optionsCollection name="product" label="proDesc" value="productId" />
						</logic:present>
					</html:select>
					</td>
  </tr>
      <tr>
    		 <td><bean:message key="lbl.npaStage" /><span><font color="red">*</font></span></td>
     		 <td><html:text property="npaStage" styleClass="text" styleId="npaStage" maxlength="10" onchange="fnChangeCase('npaStageMaster','npaStage')"/></td>
   	 
   	         <td><bean:message key="lbl.sequenceNo"/><span><font color="red">*</font></span></td>
             <td><label><html:text property="sequenceNo" style="text-align: right" styleClass="text" styleId="sequenceNo" maxlength="3"  /></label></td>
      </tr>
      <tr>
      
             <td><bean:message key="lbl.npaCriteria" /><span><font color="red">*</font></span></td>
		     <td><html:select property="npaCriteria" styleId="npaCriteria" styleClass="text" >
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="D">DPD</html:option>
		      	      <html:option value="I">Installments</html:option>
	        </html:select></td>
	        
	         <td><bean:message key="lbl.npaCriteriaValue" /><span><font color="red">*</font></span></td>
     		 <td><html:text property="npaCriteriaValue" style="text-align: right" styleClass="text" styleId="npaCriteriaValue" maxlength="5"  /></td>
   	 
   	 </tr>
   	 <tr>
   	        <td><bean:message key="lbl.moveToNext" /><span><font color="red">*</font></span></td>
		     <td><html:select property="moveToNext" styleId="moveToNext" styleClass="text" >
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="M">Manual</html:option>
		      	      <html:option value="A">Auto</html:option>
	        </html:select></td>
	        
	        <td><bean:message key="lbl.moveToPrevious" /><span><font color="red">*</font></span></td>
		     <td><html:select property="moveToPrevious" styleId="moveToPrevious" styleClass="text" >
              	     <html:option  value="">--Select--</html:option>
              	      <html:option  value="M">Manual</html:option>
		      	      <html:option value="A">Auto</html:option>
	        </html:select></td>
	    
	    </tr>
	    <tr>    
	  <td>
      <bean:message key="lbl.billingFlag"/> </td>
      <td>
      <logic:equal value="Yes" name="billingStatus">
              <input type="checkbox" name="billingFlagStatus" id="billingFlagStatus" checked="checked" />
      </logic:equal>
      
      <logic:notEqual value="Yes" name="billingStatus">
      	 	 <input type="checkbox" name="billingFlagStatus" id="billingFlagStatus"  />
      </logic:notEqual></td>
      
      <td>
      <bean:message key="lbl.AccrualFlag"/> </td>
      <td>
      <logic:equal value="Yes" name="AccrualStatus">
              <input type="checkbox" name="accrualFlagStatus" id="accrualFlagStatus" checked="checked" />
      </logic:equal>
      
      <logic:notEqual value="Yes" name="AccrualStatus ">
      	 	 <input type="checkbox" name="accrualFlagStatus" id="accrualFlagStatus"  />
      </logic:notEqual></td>
      
      </tr>
      <tr>
      <td>
      <bean:message key="lbl.sdAccrualFlag"/></td>
      <td>
      <logic:equal value="Yes" name="SdAccrualStatus">
              <input type="checkbox" name="sdAccrualFlag" id="sdAccrualFlag" checked="checked" />
      </logic:equal>
      
      <logic:notEqual value="Yes" name="SdAccrualStatus">
      	 	 <input type="checkbox" name="sdAccrualFlag" id="sdAccrualFlag"  />
      </logic:notEqual></td>
     <td>
     <bean:message key="lbl.status"/> </td>
     <td>
              <input type="checkbox" name="npaStageStatus" id="npaStageStatus" checked="checked" />
     </td>
   	  </tr>      
   	 </logic:notPresent>
    
  <tr><td>
    <br>
    <logic:present name="editVal">

      <button type="button" name="save" title="Alt+V" accesskey="V" id="save" onclick="return fnEdit();" class="topformbutton2" ><bean:message key="button.save" /></button>

   </logic:present>
    
     <logic:present name="editValUpdate">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEdit();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnSave();" class="topformbutton2"><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>
  
		
	</table>		

</fieldset></html:form>

<logic:present name="REGULAR">   
	<script type="text/javascript">
		alert("<bean:message key="lbl.npaRegular" />");
	</script>
</logic:present>
<logic:present name="ERROR">
	<script type="text/javascript">
		alert("<bean:message key="msg.notepadError" />");
		document.getElementById("npaStageMaster").action="npaStageMasterBehind.do";
	    document.getElementById("npaStageMaster").submit();
	</script>
</logic:present>
<logic:present name="EXIST">
	<script type="text/javascript">
		alert("<bean:message key="lbl.npadataExist" />");
	</script>
</logic:present>
<logic:present name="SAVE">
	<script type="text/javascript">
		alert("<bean:message key="lbl.dataSave" />");		
		document.getElementById("npaStageMaster").action="npaStageMasterBehind.do";
	    document.getElementById("npaStageMaster").submit();
	</script>
</logic:present>
<logic:present name="UPDATE">
	<script type="text/javascript">
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("npaStageMaster").action="npaStageMasterBehind.do";
	    document.getElementById("npaStageMaster").submit();
	</script>
</logic:present>

</body>
</html:html>
