<!--Author Name : Ritu Jindal-->
<!--Date of Creation : -->
<!--Purpose  : Information of Industry Master Add-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/industryScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('industryMasterForm').industryStatus.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('industryMasterForm').industryDesc.focus();
     }
     return true;
}
function sevenDecimalnumbersonlyForMarkup(e, san, Max){
	//alert(e.charCode+" e.keyCode: "+e.keyCode);
	var dynaVal = san;
	document.getElementById(dynaVal).maxLength = Max+8;
	var unicode=e.charCode? e.charCode : e.keyCode
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9 && unicode != 45)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}
function checkRate(val)

{
	

	var rate = document.getElementById(val).value;
	 
//alert("Passed Value: "+rate);
if(rate=='')
	{
		rate=0;	
	}
	    var intRate = parseFloat(rate);
	
	    if(intRate>=0 && intRate<=100)
	      {
		    return true;
	       }
	        else
	           {
		        alert("Please Enter the value b/w 0 to 100");
		        document.getElementById(val).value="";
		        return false;
	         }
	
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
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<logic:present name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
	<html:form styleId="industryMasterForm"  method="post"  action="/industryMasterAdd" onsubmit="return fnEditIndustry('${requestScope.list[0].industryId}');">
<fieldset>
<legend><bean:message key="lbl.industryMaster" /></legend>
  <table width="100%">
    <tr>
    <html:hidden  property="industryId" styleId="industryId"  value="${requestScope.list[0].industryId}" />
   <logic:present name="editVal">
      <td width="13%"><bean:message key="lbl.industryDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="industryDesc" styleClass="text" styleId="industryDesc" readonly="true" maxlength="50" value="${requestScope.list[0].industryDesc}" />
    </logic:present>
    
    <logic:notPresent name="editVal">
      <td width="13%"><bean:message key="lbl.industryDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="industryDesc" styleClass="text" styleId="industryDesc" maxlength="50" onblur="fnChangeCase('industryMasterForm','industryDesc')"/>
     </logic:notPresent>
     
     
    
      <td width="13%"><bean:message key="lbl.active" /></td>
    <logic:equal value="Active" name="status">
      <td width="13%"> <input type="checkbox" name="industryStatus" id="industryStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <td width="13%"><input type="checkbox" name="industryStatus" id="industryStatus"  /></td>
      </logic:notEqual>
      
    </tr>
    <tr>
    <td><bean:message key="lbl.ManufacturerPercent" /></td>
	<td><html:text property="manufacturer" styleClass="text" style="text-align: right" value="${requestScope.list[0].manufacturer}"  styleId="manufacturer" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('manufacturer');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  />
	</td>
     <td><bean:message key="lbl.WholesalerPercent" /></td>
	<td><html:text property="trader" styleClass="text" style="text-align: right" value="${requestScope.list[0].trader}"  styleId="trader" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('trader');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  />
	</td>
    </tr>
    <tr>
     <td><bean:message key="lbl.ServicePercent" /></td>
	<td><html:text property="service" styleClass="text" style="text-align: right" value="${requestScope.list[0].service}"  styleId="service" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('service');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  />
	</td>
    </tr>
    <tr><td >
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditIndustry('${requestScope.list[0].industryId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
    
   
    
  </td> </tr>

	</table>		


</fieldset>
  
       
	</html:form>
	
</logic:present>



<logic:notPresent name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
<html:form styleId="industryMasterForm"  method="post"  action="/industryMasterAdd" onsubmit="return saveIndustry();">
<fieldset>
<legend><bean:message key="lbl.industryMaster" /></legend>
  <table width="100%">
    <tr>
    <html:hidden  property="industryId" styleId="industryId"  value="${requestScope.list[0].industryId}" />
   <logic:present name="editVal">
      <td width="13%"><bean:message key="lbl.industryDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="industryDesc" styleClass="text" styleId="industryDesc" readonly="true" maxlength="50" value="${requestScope.list[0].industryDesc}" />
    </logic:present>
    
    <logic:notPresent name="editVal">
      <td width="13%"><bean:message key="lbl.industryDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="industryDesc" styleClass="text" styleId="industryDesc" maxlength="50" onblur="fnChangeCase('industryMasterForm','industryDesc')"/>
     </logic:notPresent>
    
      <td width="13%"><bean:message key="lbl.active" /></td>
    <logic:equal value="Active" name="status">
      <td width="13%"> <input type="checkbox" name="industryStatus" id="industryStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <td width="13%"><input type="checkbox" name="industryStatus" id="industryStatus"  /></td>
      </logic:notEqual>
      
    </tr>
    <tr>
    <td><bean:message key="lbl.ManufacturerPercent" /></td>
	<td><html:text property="manufacturer" styleClass="text" style="text-align: right" value="${requestScope.list[0].manufacturer}"  styleId="manufacturer" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('manufacturer');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  />
	</td>
     <td><bean:message key="lbl.WholesalerPercent" /></td>
	<td><html:text property="trader" styleClass="text" style="text-align: right" value="${requestScope.list[0].trader}"  styleId="trader" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('trader');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  />
	</td>
    </tr>
    <tr>
     <td><bean:message key="lbl.ServicePercent" /></td>
	<td><html:text property="service" styleClass="text" style="text-align: right" value="${requestScope.list[0].service}"  styleId="service" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('service');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  />
	</td>
    </tr>
    <tr><td >
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
   
   
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveIndustry();" class="topformbutton2" ><bean:message key="button.save" /></button>
    
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>
	   </logic:notPresent>	
	<logic:present name="sms">
<script type="text/javascript">

    
	
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("industryMasterForm").action="industryMasterBehind.do";
	    document.getElementById("industryMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("industryMasterForm").action="industryMasterBehind.do";
	    document.getElementById("industryMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='EX')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	
	
</script>
</logic:present>		
			
		
  </body>
		</html:html>