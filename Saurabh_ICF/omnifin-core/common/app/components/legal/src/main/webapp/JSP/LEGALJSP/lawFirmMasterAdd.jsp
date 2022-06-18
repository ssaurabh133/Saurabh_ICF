<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 21-May-2011-->
<!--Purpose  : Information of country Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/lawFirmMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript">
  $(function() {
		$("#dateOfAppointment").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1900:+10',
            showOn: 'both',
				<logic:present name="image">
  	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
          </logic:present>
  		<logic:notPresent name="image">
  			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
   		</logic:notPresent>
				buttonImageOnly: true,
				dateFormat:"<bean:message key="lbl.dateFormat"/>",
				defaultDate:'${userobject.businessdate }'
})
});

</script>


<script type="text/javascript">
function fntab()
{
   document.getElementById('lawFirmMasterForm').lawFirmCode.focus(); 
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

<body onload="fntab();init_fields();">
<html:form styleId="lawFirmMasterForm"  method="post"  action="/lawFirmMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>


<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
    </logic:present>
<logic:notPresent name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>




<fieldset>
<legend><bean:message key="lbl.lawFirmMaster" /></legend>
  <table width="100%">
  
  
  
    <tr>
    
     <td><bean:message key="lbl.lawFirmCode" /><span><font color="red">*</font></span></td>
     
      <logic:present name="editVal">
      <td><html:text property="lawFirmCode" readonly="true" styleClass="text" styleId="lawFirmCode" maxlength="10" onblur="fnChangeCase('lawFirmMasterForm','lawFirmCode')" value="${requestScope.list[0].lawFirmCode}" /></td>
   	  </logic:present>
   	  
   	   <logic:notPresent name="editVal">
      <td><html:text property="lawFirmCode" styleClass="text" styleId="lawFirmCode" maxlength="10" onblur="fnChangeCase('lawFirmMasterForm','lawFirmCode')" value="${requestScope.list[0].lawFirmCode}" /></td>
   	  </logic:notPresent>
	

      <td><bean:message key="lbl.lawFirmDesc" /><span><font color="red">*</font></span></td>
     
      
      <td><html:text property="lawFirmDesc" styleClass="text" styleId="lawFirmDesc" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','lawFirmDesc')" value="${requestScope.list[0].lawFirmDesc}" />
     </td>
     
  
    </tr>
    
    <tr>
     <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
    
			<td><bean:message key="lbl.lawFirmBranch" /><span><font color="red">*</font></span></td>
			
			<td>
		<!--  	<select id="branchDesc" name="branchDesc" size="5" multiple="multiple" style="width: 120" tabindex="-1">
		   </select> -->
		   
		   <html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="branchList">
       			 <html:optionsCollection name="branchList" value="branchId" label="branchDesc"/>
      		</logic:present>
     		</html:select>
		   
		   <!--   <html:text  property="lbxBranchIds" styleId="lbxBranchIds" value="" />  -->
		     <input type="hidden" name = "lbxBranchIds" id = "lbxBranchIds" value="${requestScope.branchIds}"/>
		       <html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(1508,'lawFirmMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>	   
			</td>
		  <!--  	<td><html:text property="branchId" styleClass="text" styleId="branchId" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','lawFirmDesc')" value="${requestScope.list[0].branchId}" /></td>  -->
			
			<td><bean:message key="lbl.constitutionOfFirm" /><span><font color="red">*</font></span></td>
			
		<!-- 	<td><html:text property="constitutionOfFirm" styleClass="text" styleId="constitutionOfFirm" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','lawFirmDesc')" value="${requestScope.list[0].constitutionOfFirm}" /></td>  -->
		
		<td>
		
		 <html:select property="constitutionOfFirm" styleId="constitutionOfFirm" styleClass="text" value="${requestScope.list[0].constitutionOfFirm}" >
                 <html:option value="INDIVIDUAL">Individual</html:option>
                <html:option value="PROPRIETORSHIP">Proprietorship</html:option>
                <html:option value="PARTNERSHIP">Partnership</html:option>
                <html:option value="COMPANY">Company</html:option>
                <html:option value="LLP">LLP</html:option>
       </html:select>
       
       </td>
    
    </tr>
     <tr>
    
			<td><bean:message key="lbl.address1" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="address1" styleClass="text" styleId="address1" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','address1')" value="${requestScope.list[0].address1}" /></td>
			
			<td><bean:message key="lbl.address2" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="address2" styleClass="text" styleId="address2" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','address2')" value="${requestScope.list[0].address2}" /></td>
    
    </tr>
    
    <tr>
    
			<td><bean:message key="lbl.lawFirmCountry" /><span><font color="red">*</font></span></td>
			
		<!-- 	<td><html:text property="countryCode" styleClass="text" styleId="countryCode" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','lawFirmDesc')" value="${requestScope.list[0].countryCode}" /></td>  -->
			
			<td>
			<html:text property="country" styleId="country" maxlength="10" value="${requestScope.list[0].country}" styleClass="text" readonly="true" tabindex="-1"/>
			<html:hidden  property="txtCountryCode" styleId="txtCountryCode" value="${requestScope.list[0].txtCountryCode}"  />
			<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(1510,'lawFirmMasterForm','country','','','','','clearCountryLovChildMaster','txtCountryCode');" value=" " styleClass="lovbutton"> </html:button>
			</td>
			
			
			
			
			
			
			
			
			<td><bean:message key="lbl.lawFirmState" /><span><font color="red">*</font></span></td>
			
		<!-- 	<td><html:text property="stateCode" styleClass="text" styleId="stateCode" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','lawFirmDesc')" value="${requestScope.list[0].stateCode}" /></td>   -->
    
    
    
    <td><html:text property="state" styleId="state" styleClass="text" value="${requestScope.list[0].state}" maxlength="10" readonly="true" tabindex="-1"/>
    <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(1511,'lawFirmMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChildMaster','hcommon');closeLovCallonLov('country');" value=" " styleClass="lovbutton"> </html:button>
    
    <html:hidden  property="txtStateCode" styleId="txtStateCode" value="${requestScope.list[0].txtStateCode}"  /></td>
    
    
    
    
    
    </tr>
    
    <input type="hidden" name="hcommon" id="hcommon" value="" /> 
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmCity" /><span><font color="red">*</font></span></td>
			
		<!-- 	<td><html:text property="cityCode" styleClass="text" styleId="cityCode" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','lawFirmDesc')" value="${requestScope.list[0].cityCode}" /></td>   -->
			
			    <td><html:text property="dist" styleId="dist" styleClass="text" value="${requestScope.list[0].dist}" maxlength="10" readonly="true" tabindex="-1"/>
    <html:button property="cityButton" styleId="cityButton" onclick="openLOVCommon(1512,'lawFirmMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');closeLovCallonLov('state');" value=" " styleClass="lovbutton"> </html:button>
    
    <html:hidden  property="txtDistCode" styleId="txtDistCode" onkeypress="return isNumberKey(event);" style="text-align: right"
    value="${requestScope.list[0].txtDistCode}" /></td>
			
			
			
			
			
			<td><bean:message key="lbl.lawFirmZipCode" /><span><font color="red">*</font></span></td>
			
			
      <td><html:text property="zipCode" styleId="zipCode" maxlength="6" styleClass="text" 
    	onkeypress="return isNumberKey(event);" style="text-align: left" value="${requestScope.list[0].zipCode}"/></td>
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmPhone1" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="phone1" styleClass="text" styleId="phone1" maxlength="10" onblur="fnChangeCase('lawFirmMasterForm','phone1')" value="${requestScope.list[0].phone1}" onkeypress="return isNumberKey(event);"/></td>
			
			<td><bean:message key="lbl.lawFirmPhone2" /></td>
			
   		 <td><html:text property="phone2" styleId="phone2" maxlength="10" styleClass="text" 
    	
    	onkeypress="return isNumberKey(event);" style="text-align: left" value="${requestScope.list[0].phone2}"/></td>

    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmPan" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="panNo" styleClass="text" styleId="panNo" maxlength="10" onblur="fnChangeCase('lawFirmMasterForm','panNo')" value="${requestScope.list[0].panNo}" /></td>
			
			<td><bean:message key="lbl.lawFirmSapCode" /></td>
			
			<td><html:text property="sapCode" styleClass="text" styleId="sapCode" maxlength="10" 
			onkeypress="return isNumberKey(event);" style="text-align: left" value="${requestScope.list[0].sapCode}"/> 
		</td>
			
    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmBankAccno" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="bankAccNo" styleClass="text" styleId="bankAccNo" maxlength="30" 
			onblur="fnChangeCase('lawFirmMasterForm','bankAccNo')" value="${requestScope.list[0].bankAccNo}" /></td>
			
			<td><bean:message key="lbl.lawFirmMobileNo" /><span><font color="red">*</font></span></td>
			
			
			
     <td><html:text property="mobileNo" styleId="mobileNo" maxlength="10" styleClass="text" 
     onkeypress="return isNumberKey(event);" style="text-align: left" value="${requestScope.list[0].mobileNo}"/></td>

     

    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmEmail" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="emailId" styleClass="text" styleId="emailId" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','emailId')" value="${requestScope.list[0].emailId}" /></td>
			
			<td><bean:message key="lbl.dateOfAppointment" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="dateOfAppointment"  onchange="checkDate('dateOfAppointment');" styleClass="text" styleId="dateOfAppointment" maxlength="50" onblur="fnChangeCase('lawFirmMasterForm','dateOfAppointment')" value="${requestScope.list[0].dateOfAppointment}" /></td>
    
    </tr>
    
      <tr>
      
    <td><bean:message key="lbl.status" /></td>
    
      <td>
      
	<logic:present name="editVal">
	
	<logic:equal value="A" name="status">
	<input type="checkbox" name=recStatus id="recStatus" checked="checked" />
	</logic:equal>
	
	<logic:notEqual value="A" name="status">
	<input type="checkbox" name="recStatus" id="recStatus"  />
	</logic:notEqual>
	
	</logic:present>
	
	<logic:present name="save">
	
	<input type="checkbox" name=recStatus id="recStatus" checked="checked" />
	
	</logic:present>
    
      
    </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnUpdateLawFirm('${requestScope.list[0].lawFirmCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveLawFirm();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("lawFirmMasterForm").action="lawFirmMasterBehind.do";
	    document.getElementById("lawFirmMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("lawFirmMasterForm").action="lawFirmMasterBehind.do";
	    document.getElementById("lawFirmMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='PAN')
	{
		alert("<bean:message key="msg.panNumber" />");	
	}
	
	
</script>
</logic:present>
  </body>
		</html:html>