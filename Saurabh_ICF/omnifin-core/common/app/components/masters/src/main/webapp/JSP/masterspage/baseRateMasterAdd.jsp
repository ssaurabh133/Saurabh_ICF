<!--Author Name : Ritu Jindal-->
<!--Date of Creation :9 may 2011 -->
<!--Purpose  : Information of Base Rate Master Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<script type="text/javascript" src="js/formatnumber.js"/></script>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>

 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/baseRateScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('baseRateMasterForm').baseRateDesc.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('baseRateMasterForm').baseRateType.focus();
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
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>

<div id=centercolumn>
<div id=revisedcontainer>
<html:errors/>



<html:form styleId="baseRateMasterForm"  method="post"  action="/baseRateMasterAdd" >

<fieldset>
<legend><bean:message key="lbl.bplrMaster" /></legend>
  <table width="100%">
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
   <tr>
   <td><bean:message key ="lbl.bplrbaseRateType"/><span><font color="red">*</font></span></td>
	<td><html:text property="baseRateType" styleClass="text" styleId="baseRateType" readonly="true" maxlength="10" value="${listObj.baseRateTypeSearch}"/></td>

	<td><bean:message key ="lbl.effectiveFromDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
    <td><html:text property="effectiveFromDate"  styleClass="text" readonly="true" value="${listObj.effectiveFromDate}" /></td>
  </tr>
  <tr>
	
   <td><bean:message key="lbl.bplrbaseRate" /><span><font color="red">*</font></span></td>
<!--    <td style="width:26%"><html:text styleClass="text"  property="averagePurchaseSales" maxlength="18"  styleId="averagePurchaseSales"  value="${buyerList[0].averagePurchaseSales}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>-->
   
    <td><html:text property="baseRate" styleClass="text" styleId="baseRate" readonly="true" value="${listObj.baseRate}" style="text-align: right" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
    
   <td><bean:message key="lbl.bplrbaseRateDesc" /><span><font color="red">*</font></span></td>
   <td><html:text property="baseRateDesc" styleClass="text" styleId="baseRateDesc" onblur="fnChangeCase('baseRateMasterForm','baseRateDesc')" maxlength="50" value="${listObj.baseRateDesc}"/></td>
    </tr>
   <tr> 

   <td><bean:message key="lbl.active" /></td>
      <td>
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="baseRateStatus" id="baseRateStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="baseRateStatus" id="baseRateStatus"  />
      </logic:notEqual></td>
   
   </tr>
    </logic:iterate>
   </logic:present>
   
    <logic:notPresent name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   <tr>
   <td><bean:message key ="lbl.bplrbaseRateType"></bean:message><span><font color="red">*</font></span></td>
<!--		 <td><html:text property="baseRateType" styleClass="text" styleId="baseRateType"  maxlength="10" ></html:text></td>-->
    <td>
		<html:select property="baseRateType" styleId="baseRateType" styleClass="text">
			<logic:present name="baseRateTypeList" > 
				<html:option value="">--Select--</html:option>         	
				    <html:optionsCollection name="baseRateTypeList" label="bRTypeDesc" value="bRTypeCode" />
			</logic:present>
	    </html:select>
    </td>
		  
		 <td><bean:message key ="lbl.effectiveFromDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
         <td><html:text property="effectiveFromDate" styleId="effectiveFromDate" onchange="return checkDate('effectiveFromDate');" styleClass="text" maxlength="10"/>
          </td>
   </tr>
   <tr>
   <td><bean:message key="lbl.bplrbaseRate" /><span><font color="red">*</font></span></td>
    <td><html:text property="baseRate" styleClass="text" styleId="baseRate" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
   
    <td><bean:message key="lbl.bplrbaseRateDesc" /><span><font color="red">*</font></span></td>
     <td><html:text property="baseRateDesc" styleClass="text" styleId="baseRateDesc" maxlength="50" onblur="fnChangeCase('baseRateMasterForm','baseRateDesc')" /></td>
   
   </tr>
   <tr>
   <td><bean:message key="lbl.active" /></td>
      <td>
         <input type="checkbox" name="baseRateStatus" id="baseRateStatus" checked="checked" />
      </td>
   
   </tr>
    
   </logic:notPresent>
   
    <tr><td>
    
    <br>
    <logic:present name="editVal">
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditBaseRate('${requestScope.list[0].baseRateType}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="editVal">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveBaseRate();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

   			if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("baseRateMasterForm").action="baseRateMasterBehind.do";
	    document.getElementById("baseRateMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("baseRateMasterForm").action="baseRateMasterBehind.do";
	    document.getElementById("baseRateMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
		
	} 
	
	else
	{
		
		alert("<bean:message key="msg.notepadError" />");
		
	} 
	
</script>
</logic:present>
</div>
</div>
  </body>
		</html:html>