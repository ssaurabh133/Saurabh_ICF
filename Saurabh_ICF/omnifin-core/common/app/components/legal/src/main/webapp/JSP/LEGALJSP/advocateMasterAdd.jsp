<!--Author Name : Vinod Kumar Gupta-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/advocateMaster.js"></script>
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

$(function() {
		$("#retainershipValidUpto").datepicker({
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
   document.getElementById('advocateCodeButton').focus();
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

<body onload="fntab();">
<html:form styleId="advocateMasterForm"  method="post"  action="/advocateMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>


<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
    </logic:present>
<logic:notPresent name="image">
<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>




<fieldset>
<legend><bean:message key="lbl.advocateMaster" /></legend>
  <table width="100%">
  <tr>
    <td><bean:message key="lbl.advocateCode" /><span><font color="red">*</font></span></td>
    <td>
    <logic:present name="save">
			
			<html:text  property="lbxUserId" styleId="lbxUserId" readonly="true" value="${requestScope.list[0].lbxUserId}"  />
			<html:button property="advocateCodeButton" styleId="advocateCodeButton" onclick="closeLovCallonLov1();openLOVCommon(1515,'advocateMasterForm','advocateCode','','','','','','hmon');" value=" " styleClass="lovbutton"> </html:button>
			<input type="hidden" id="hmon" value="hmon"/> 
			
</logic:present>
<logic:present name="editVal">
<html:text  property="lbxUserId" styleId="lbxUserId" value="${requestScope.list[0].lbxUserId}" styleClass="text" readonly="true" tabindex="-1"/>
			
			<input type="hidden" id="hmon" value="hmon"/> 
			
</logic:present>
</td>
      <td><bean:message key="lbl.advocateDesc" /><span><font color="red">*</font></span></td>
      <td>
      <html:text property="advocateCode" styleId="advocateCode" maxlength="10" value="${requestScope.list[0].advocateCode}" styleClass="text" readonly="true" tabindex="2"/>
     </td>
    
     </tr>
     <tr>
<logic:notPresent name="list">
				 <td>
					 <bean:message key="lbl.advocateTypeFlag.LawFirm" />
					<html:radio property="advocateTypeFlag" styleId ="advocateTypeFlag" onclick="hideLovButton('L');" value="L" /> 
				 </td>
				 <td></td>
				 <td>
					 <bean:message key="lbl.advocateTypeFlag.Individual" />
					<html:radio property="advocateTypeFlag" styleId ="advocateTypeFlag1" value="I" onclick="hideLovButton('I');" />
				</td>
</logic:notPresent>
<logic:present name="list">
	<logic:iterate name="list" id="subList"  length="1">
			<logic:equal name="subList" property="advocateTypeFlag" value="L">
			  
				 <td>
					 <bean:message key="lbl.advocateTypeFlag.LawFirm" />
					 <input type="radio" name="advocateTypeFlag" onclick="hideLovButton('L');" id ="advocateTypeFlag" value="L" checked="checked" disabled>
					
				 </td>
				
				 <td></td>
				 <td>
					 <bean:message key="lbl.advocateTypeFlag.Individual" />
					 <input type="radio" name="advocateTypeFlag" id ="advocateTypeFlag1" value="I" onclick="hideLovButton('I');" disabled >
				</td>
				
		</logic:equal>
			<logic:notEqual name="subList" property="advocateTypeFlag" value="L">
			
				 <td>
					 <bean:message key="lbl.advocateTypeFlag.LawFirm" />
					 <input type="radio" name="advocateTypeFlag" id ="advocateTypeFlag" value="L"disabled>
					
				 </td>
				 <td></td>
				 <td>
					 <bean:message key="lbl.advocateTypeFlag.Individual" />
					 <input type="radio" name="advocateTypeFlag" id ="advocateTypeFlag1" value="I" checked="checked" disabled >
				</td>
				
		</logic:notEqual>
</logic:iterate>
</logic:present>
</tr>
     <tr>
			<td><bean:message key="lbl.lawFirmCode" /><span><font color="red">*</font></span></td>
						<td>
			<html:text property="lawFirmCode" styleId="lawFirmCode" maxlength="10" value="${requestScope.list[0].lawFirmCode}" styleClass="text" readonly="true" tabindex="-1"/>
			<html:hidden  property="lbxlawFirmCode" styleId="lbxlawFirmCode" value="${requestScope.list[0].lbxlawFirmCode}"  />
			<html:button property="lawFirmCodeButton" styleId="lawFirmCodeButton" style="display:none;" onclick="closeLovCallonLov1();openLOVCommon(1505,'advocateMasterForm','lawFirmCode','','','','','clearCountryLovChildMaster','lbxlawFirmCode'); removeRejection();" value=" " styleClass="lovbutton"> </html:button>
			</td>
			
    
    </tr>
    
    <tr>
   
     <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
    
			<td><bean:message key="lbl.lawFirmBranch" /><span><font color="red">*</font></span></td>
			
			<td>
		
		   
		   <html:select property="branchDesc" styleId="branchDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="branchList">
       			 <html:optionsCollection name="branchList" value="branchId" label="branchDesc"/>
      		</logic:present>
     		</html:select>
		   <input type="hidden" name = "lbxBranchIds" id = "lbxBranchIds" value="${requestScope.branchIds}"/>
		       <html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(1508,'advocateMasterForm','branchDesc','','', '','','','lbxBranchIds');" value=" " styleClass="lovbutton"></html:button>	   
			</td>
			<td><bean:message key="lbl.caseTypeCode" /><span><font color="red">*</font></span></td>
			<td>
		<html:select property="caseTypeDesc" styleId="caseTypeDesc" size="5" multiple="multiple" style="width: 120" >
  			<logic:present name="caseTypeList">
       			 <html:optionsCollection name="caseTypeList" value="caseTypeCode" label="caseTypeDesc"/>
      		</logic:present>
     		</html:select>
<!-- openMultiSelectLOVCommon(lovID,strFormName,strFieldName,strParentGroup,strChildGroup,strParentOption,strMessages,strMethod,LovListItemsIds)-->
		     <input type="hidden" name ="lbxCaseTypeCode" id ="lbxCaseTypeCode" value="${requestScope.caseTypeCode}"/>
		       <html:button property="caseTypeButton" styleId="caseTypeButton" onclick="return openMultiSelectLOVCommon(1523,'advocateMasterForm','caseTypeDesc','','', '','','','lbxCaseTypeCode');" value=" " styleClass="lovbutton"></html:button>	   
			</td>
        </tr>
    <tr>
    <td><bean:message key="lbl.address1" /><span><font color="red">*</font></span></td>
			<td><html:text property="address1" styleClass="text" styleId="address1" maxlength="50" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].address1}" /></td>
		<td><bean:message key="lbl.address2" /><span><font color="red">*</font></span></td>
			<td><html:text property="address2" styleClass="text" styleId="address2" maxlength="50" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].address2}" /></td>
    </tr>
     <tr>
    
			<td><bean:message key="lbl.lawFirmCountry" /><span><font color="red">*</font></span></td>
			<td>
			<html:text property="country" styleId="country" maxlength="10" value="${requestScope.list[0].country}" styleClass="text" readonly="true" tabindex="-1"/>
			<html:hidden  property="txtCountryCode" styleId="txtCountryCode" value="${requestScope.list[0].txtCountryCode}"  />
			<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(1510,'advocateMasterForm','country','','','','','clearCountryLovChildMaster','txtCountryCode');" value=" " styleClass="lovbutton"> </html:button>
			</td>
			<td><bean:message key="lbl.lawFirmState" /><span><font color="red">*</font></span></td>
          <td><html:text property="state" styleId="state" styleClass="text" value="${requestScope.list[0].state}" maxlength="10" readonly="true" tabindex="-1"/>
    <html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(1511,'advocateMasterForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChildMaster','hcommon');closeLovCallonLov('country');" value=" " styleClass="lovbutton"> </html:button>
     <html:hidden  property="txtStateCode" styleId="txtStateCode" value="${requestScope.list[0].txtStateCode}"  /></td>
   </tr>
    <tr>
		<td><bean:message key="lbl.lawFirmCity" /><span><font color="red">*</font></span></td>
		<!-- 	<td><html:text property="cityCode" styleClass="text" styleId="cityCode" maxlength="50" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].cityCode}" /></td>   -->
			 <td><html:text property="dist" styleId="dist" styleClass="text" value="${requestScope.list[0].dist}" maxlength="10" readonly="true" tabindex="-1"/>
    <html:button property="cityButton" styleId="cityButton" onclick="openLOVCommon(1512,'advocateMasterForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','','hcommon');closeLovCallonLov('state');" value=" " styleClass="lovbutton"> </html:button>
    <input type="hidden" id="hcommon" name="hcommon" />
    <html:hidden  property="txtDistCode" styleId="txtDistCode" value="${requestScope.list[0].txtDistCode}" />
    </td><td><bean:message key="lbl.lawFirmZipCode" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="zipCode" styleClass="text" styleId="zipCode" maxlength="6" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].zipCode}" onkeypress="return isNumberKey(event);" /></td>
    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmPhone1" /></td>
			
			<td><html:text property="phone1" styleClass="text" styleId="phone1" maxlength="10" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].phone1}" onkeypress="return isNumberKey(event);" /></td>
			
			<td><bean:message key="lbl.lawFirmPhone2" /></td>
			
			<td><html:text property="phone2" styleClass="text" styleId="phone2" maxlength="10" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].phone2}" onkeypress="return isNumberKey(event);"/></td>
    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmPan" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="panNo" styleClass="text" styleId="panNo" maxlength="10" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].panNo}" /></td>
			
			<td><bean:message key="lbl.lawFirmSapCode" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="sapCode" styleClass="text" styleId="sapCode" maxlength="10" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].sapCode}" onkeypress="return isNumberKey(event);" /></td>
    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmBankAccno" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="bankAccNo" styleClass="text" styleId="bankAccNo" maxlength="30" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].bankAccNo}" onkeypress="return isNumberKey(event);"/></td>
			
			<td><bean:message key="lbl.lawFirmMobileNo" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="mobileNo" styleClass="text" styleId="mobileNo" maxlength="10" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].mobileNo}" onkeypress="return isNumberKey(event);"/></td>
    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.lawFirmEmail" /><span><font color="red">*</font></span></td>
			
			<td><html:text property="emailId" styleClass="text" styleId="emailId" maxlength="50" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].emailId}" /></td>
			
			<td><bean:message key="lbl.membershipLicNo" /></td>
			
			<td><html:text property="membershipLicNo" styleClass="text" styleId="membershipLicNo" maxlength="10" onblur="fnChangeCase('advocateMasterForm','advocateDesc')" value="${requestScope.list[0].membershipLicNo}" /></td>
    
    </tr>
    
     <tr>
    
			<td><bean:message key="lbl.retainershipValidUpto" /></td>
			
			<td>
			<html:text property="retainershipValidUpto" styleClass="text" styleId="retainershipValidUpto" maxlength="50"  value="${requestScope.list[0].retainershipValidUpto}" onchange="checkDate('retainershipValidUpto');compareTwoDates('formatD','bDate','retainershipValidUpto','Retainership Valid Upto date should be greater than or equal to Business Date.');" />
			<input type = "hidden" id="bDate" value = "${userobject.businessdate }"/>
			</td>
			
			<td><bean:message key="lbl.dateOfAppointment" /></td>
			
			<td><html:text property="dateOfAppointment" styleClass="text" onchange="checkDate('dateOfAppointment');" styleId="dateOfAppointment" maxlength="50" value="${requestScope.list[0].dateOfAppointment}" /></td>
    
    </tr>
    <tr>
    <td><bean:message key="lbl.classification" /></td>
			
			<td><html:text property="classification" styleClass="text" styleId="classification" maxlength="50" onblur="fnChangeCase('advocateMasterForm','classification')" value="${requestScope.list[0].classification}" /></td>
			
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
     
      
    </td>
    
     <td><bean:message key="lbl.regularRetainerFlag" /></td>
    <td>
     <logic:equal value="Y" name="regularRetainerFlag">
         <input type="checkbox" name=regularRetainerFlag id="regularRetainerFlag" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Y" name="regularRetainerFlag">
         <input type="checkbox" name="regularRetainerFlag" id="regularRetainerFlag"  />
      </logic:notEqual>
      
    </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnUpdateAdvocate('${requestScope.list[0].advocateCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveAdvocateMaster();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
   <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" /> 
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("advocateMasterForm").action="advocateMasterBehind.do";
	    document.getElementById("advocateMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("advocateMasterForm").action="advocateMasterBehind.do";
	    document.getElementById("advocateMasterForm").submit();
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