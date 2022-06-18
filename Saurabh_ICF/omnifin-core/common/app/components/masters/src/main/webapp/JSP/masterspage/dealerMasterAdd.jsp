<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 13-May-2011-->
<!--Purpose  : Information of Dealer Master Ad-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('dealerMaster').dealerButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('dealerMaster').dealerDes.focus();
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


 <body onload="enableAnchor();empanelledHandel();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/dealerMasterAdd" styleId="dealerMaster" method="POST" >
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
 <html:errors/>


<fieldset>

<legend><bean:message key="lbl.dealerMaster"/></legend>

<table width="100%" height="86">
     <html:hidden  property="dealerId" styleId="dealerId"  value="${requestScope.list[0].dealerId}" />
       <html:hidden  property="tahsil" styleId="tahsil"  value="" />
       <html:hidden  property="tahsilDesc" styleId="tahsilDesc"  value="" />
     
<logic:present name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    <logic:iterate id="listObj" name="list">
    <tr>
     
          
          <td><bean:message key="lbl.dealerDes"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="dealerDes" styleClass="text" styleId="dealerDes" maxlength="50"  onblur="fnChangeCase('dealerMaster','dealerDes')"  readonly="true" value="${requestScope.list[0].dealerDes}" /></label></td>
      
    	 <td><bean:message key="lbl.dealerTypeDesc"/><span><font color="red">*</font></span></td>
		  <td><html:text property="dealerType" readonly="true" styleClass="text" styleId="dealerType" value="${requestScope.list[0].lbxdealerType}" tabindex="-1"/>
	 	 <html:button property="dealerButton" styleId="dealerButton" onclick="openLOVCommon(35,'dealerMaster','dealerType','','', '','','showEmpannel','lbxdealerType');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	 	 <%-- <img onClick="openLOVCommon(35,'dealerMaster','dealerType','','', '','','','lbxdealerType')" src="<%= request.getContextPath()%>/images/lov.gif"></td>--%>
		 <html:hidden  property="lbxdealerType" styleId="lbxdealerType"  value="${requestScope.list[0].dealerType}"/>
	 </tr>
     <!--Surendra Code 	 -->
      <tr>
							<td>
								<bean:message key="lbl.bank" />								
							</td>
							<td>
								<html:text property="bankCode" styleId="bankCode" styleClass="text" readonly="true" value="${list[0].bankCode}" tabindex="-1" />
                                <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'dealerMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(7,'bankAccountMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID')" src="<%=request.getContextPath()%>/images/lov.gif"> --%>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
								<html:hidden property="lbxBankID" styleId="lbxBankID" value="${list[0].lbxBankID}"/>
							</td>

							<td>
								<bean:message key="lbl.bankBranchName" />								
							</td>
							<td>
								<html:text property="bankBranchName" styleId="bankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${list[0].bankBranchName}"/>
                                <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(109,'dealerMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','micrCode','ifscCode','lbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(109,'bankAccountMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','lbxBranchID','ifscCode','micrCode')" src="<%=request.getContextPath()%>/images/lov.gif"> --%> 
								<html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${list[0].lbxBranchID}" />
							</td>				
<!--							<td>-->
<!--								<bean:message key="lbl.ifscCode" />-->
<!--							-->
<!--							</td>-->
							<td>
								<html:hidden property="ifscCode" styleId="ifscCode"
									styleClass="text"   />
							</td><!--							<td>-->
<!--								<bean:message key="lbl.micrCode" />-->
<!--								-->
<!--							</td>-->
							<td>
								<html:hidden property="micrCode" styleId="micrCode"
									styleClass="text"  />
							</td>
						</tr>
						
      
      <tr>
      
							<td>
								<bean:message key="lbl.accountNo" />								
							
							</td>
							<td>
								<html:text property="accountNo" styleId="accountNo"  value="${list[0].accountNo}" tabindex="-1" onblur="fnChangeCase('dealerMaster','accountNo');this.value=removeSpaces1(this.value);"
									styleClass="text" maxlength="20" />
							</td>

			<td>
				<bean:message key="lbl.addressLine1" />	<span><font color="red">*</font></span>		
			</td>
			<td >
				<html:text property="addressDealer1" styleClass="text"
				styleId="addressDealer1" maxlength="50"
				value="${list[0].addressDealer1}"
				onblur="fnChangeCase('dealerMaster','addressDealer1')" />
			</td>
	</tr>
	       
      <!--Surendra Code 	 --> 

<!--     start by sachin  add some field for dsn dealer master  -->
	<tr>
			<td>
				<bean:message key="lbl.two" />
			</td>
			<td >
				<html:text property="addressDealer2" styleClass="text"
				styleId="addressDealer2" maxlength="50"
				value="${list[0].addressDealer2}"
				onblur="fnChangeCase('dealerMaster','addressDealer2')" />
			</td>	
			<td>
				<bean:message key="lbl.three" />
			</td>
			<td>
				<html:text property="addressDealer3" styleClass="text"
				styleId="addressDealer3" maxlength="50"
				value="${list[0].addressDealer3}"
				onblur="fnChangeCase('dealerMaster','addressDealer3')" />
			</td>
	</tr>
	<tr>		
		
			<td>
				<bean:message key="lbl.country" /><span><font color="red">*</font></span>	
			</td>
			<td>
				<html:text property="country" styleId="country" styleClass="text"
				tabindex="-1" readonly="true" value="${list[0].country}" />
				<input type="button" class="lovbutton" id="countryButton"
				onclick="openLOVCommon(255,'dealerMaster','txtCountryCode','','','','','clearCountryLovChild','country')"
				value="" name="countryButton" />
				<input type="hidden" name="txtCountryCode" id="txtCountryCode"
				value="${list[0].txtCountryCode}" />
						

						

			</td>
			
			

			<td >
			<bean:message key="lbl.state" /><span><font color="red">*</font></span>
			</td>
			<td>
			<html:text property="state" styleId="state" styleClass="text"
			tabindex="-1" readonly="true" value="${list[0].state}" />
			<input type="button" class="lovbutton" id="stateButton"
			onclick="openLOVCommon(256,'dealerMaster','txtStateCode','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','state')"
			value="" name="stateButton"/>
			<input type="hidden" name="txtStateCode" id="txtStateCode"
			value="${list[0].txtStateCode}" />
			</td>
	</tr>
	<tr>
			
			<td>
			<bean:message key="lbl.dist" /><span><font color="red">*</font></span>
			</td>
			<td>
			<html:text property="dist" styleId="dist" styleClass="text"
			tabindex="-1" readonly="true" value="${list[0].dist}" />
			<input type="button" class="lovbutton" id="distButton"
			onclick="openLOVCommon(257,'dealerMaster','txtDistCode','state','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChild','dist')"
			value=" " name="distButton"/>
			<input type="hidden" name="txtDistCode" id="txtDistCode"
			value="${list[0].txtDistCode}" />
			</td>
			<td>
			<bean:message key="lbl.pincode" />	<span><font color="red">*</font></span>		
			</td>
			<td>
			<html:text property="pincode" styleClass="text" styleId="pincode"
			maxlength="6" value="${list[0].pincode}"
			style="text-align:right;"
			onkeypress="return numericOnly(event,6);" />
			</td>		
	</tr>
	<tr>	
			<td>
				<bean:message key="lbl.contractPerson" />
			</td>
			<td >
				<html:text property="contractPerson" styleClass="text"
				styleId="contractPerson" maxlength="50"
				value="${list[0].contractPerson}"
				onblur="fnChangeCase('dealerMaster','contractPerson')" />
			</td>			
			<td>
			<bean:message key="lbl.mobileoff" />	
			</td>
			<td>
			<html:text property="phoneOff" styleClass="text"
			styleId="phoneOff" maxlength="10"
			value="${list[0].phoneOff}"
			style="text-align:right;"
			onkeypress="numericOnly(event,10);" />
			</td>
	</tr>
				<tr>			
					<td >
						<bean:message key="lbl.alter" />
					</td>
					<td>
						<html:text property="phoneRes" styleClass="text"
							styleId="phoneRes" maxlength="20"
							value="${list[0].phoneRes}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,20);" />
					</td>
					<td>
						<bean:message key="lbl.email" />
					</td>
					<td>
						<html:text property="email" styleClass="text" styleId="email"
							maxlength="100" value="${list[0].email}"/>
					</td>
</tr>
				<tr>
					<td>
						<bean:message key="lbl.registrationNo" />
					</td>
					<td>
						<html:text property="registrationNo" styleClass="text"
							styleId="registrationNo" maxlength="100"
							value="${list[0].registrationNo}" />
					</td>
	<%-- Changes by Kumar Aman Started --%>				
					
					<td>
						<bean:message key="pan" />
						
					</td>
					<td>
						<html:text property="pan" styleClass="text"
						maxlength="10" styleId="pan" styleClass="text"
						value="${list[0].pan}"
						onkeypress=" isAlphNumericKey1(event);" onchange="return upperMe('pan');" />
					</td>
					
					</tr>
		<tr>	
		
<%-- Changes by Kumar Aman Ended --%>				
			<td id="empanel1" ><bean:message key="lbl.empanelled"/></td>
            <td id="empanel2" >
          		<logic:equal value="Y" name="checked">
              		<input type="checkbox" name="empanelledStatus" id="empanelledStatus" checked="checked" />
      			</logic:equal>
      		    <logic:notEqual value="Y" name="checked">
     		  		<input type="checkbox" name="empanelledStatus" id="empanelledStatus"  />
      			</logic:notEqual>         		
      	   </td> 
      	</tr>


<!--end by sachin-->
      
	<tr>
	<td><bean:message key="lbl.userNam" /></td>
	 <td>
			<html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="${requestScope.userIds}" />
   			<html:select property="userDesc" styleId="userDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="levelNameList">
       			 <html:optionsCollection name="levelNameList" value="userId" label="userDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="levelButton" styleId="levelButtonEdit" onclick="return openMultiSelectLOVCommon(368,'dealerMaster','userDesc','','', '','','','lbxUserSearchId');" value=" " styleClass="lovbutton"></html:button>
   	</td>
 
	 <td>
     <bean:message key="lbl.status"/> </td>
     
     <td>
      <logic:equal value="Active" name="status">
              <input type="checkbox" name="dealerStatus" id="dealerStatus" checked="checked" />
      </logic:equal>
      
      <logic:notEqual value="Active" name="status">
     		  <input type="checkbox" name="dealerStatus" id="dealerStatus"  />
      </logic:notEqual></td>   
  </tr> </logic:iterate></logic:present>
  
  <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/> 
    <tr>
     
          
    	  <td><bean:message key="lbl.dealerDes"/><span><font color="red">*</font></span></td>
    	  <td><label><html:text property="dealerDes" styleClass="text" styleId="dealerDes" maxlength="50"  onblur="fnChangeCase('dealerMaster','dealerDes')"  /></label></td>
      
    	 <td><bean:message key="lbl.dealerTypeDesc"/><span><font color="red">*</font></span></td>
		  <td><html:text property="dealerType" readonly="true" styleClass="text" styleId="dealerType" tabindex="-1" />
	 	 <html:button property="dealerButton" styleId="dealerButton" onclick="openLOVCommon(35,'dealerMaster','dealerType','','', '','','showEmpannel','lbxdealerType');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	 	<%--  <img onClick="openLOVCommon(35,'dealerMaster','dealerType','','', '','','','lbxdealerType')" src="<%= request.getContextPath()%>/images/lov.gif"></td>--%>
		 <html:hidden  property="lbxdealerType" styleId="lbxdealerType" />
	 </tr>
<!--Surendra Code 	 -->
      <tr>
							<td>
								<bean:message key="lbl.bank" />					
							</td>
							<td>
								<html:text property="bankCode" styleId="bankCode" styleClass="text" readonly="true" />
                                <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'dealerMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(7,'bankAccountMaster','bankCode','','bankBranchName', 'lbxBranchID','','','lbxBankID')" src="<%=request.getContextPath()%>/images/lov.gif"> --%>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
								<html:hidden property="lbxBankID" styleId="lbxBankID" />
							</td>

							<td>
								<bean:message key="lbl.bankBranchName" />								
							</td>
							<td>
								<html:text property="bankBranchName" styleId="bankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1"/>
                                <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(109,'dealerMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','micrCode','ifscCode','lbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
								<%-- <img onClick="openLOVCommon(109,'bankAccountMaster','bankBranchName','bankCode','lbxBranchID', 'lbxBankID','Please Select Bank','','lbxBranchID','ifscCode','micrCode')" src="<%=request.getContextPath()%>/images/lov.gif"> --%> 
								<html:hidden property="lbxBranchID" styleId="lbxBranchID" />
							</td>				
<!--							<td>-->
<!--								<bean:message key="lbl.ifscCode" />-->
<!--							-->
<!--							</td>-->
							<td>
								<html:hidden property="ifscCode" styleId="ifscCode"
									styleClass="text"   />
							</td><!--							<td>-->
<!--								<bean:message key="lbl.micrCode" />-->
<!--								-->
<!--							</td>-->
							<td>
								<html:hidden property="micrCode" styleId="micrCode"
									styleClass="text"  />
							</td>
						</tr>
						
      
      <tr>
      
							<td>
								<bean:message key="lbl.accountNo" />							
								
							</td>
							<td>
								<html:text property="accountNo" styleId="accountNo" onblur="fnChangeCase('dealerMaster','accountNo');this.value=removeSpaces1(this.value);"
									styleClass="text" maxlength="20" />
							</td>
      
			<td>
				<bean:message key="lbl.addressLine1" />	<span><font color="red">*</font></span>			
			</td>
			<td >
				<html:text property="addressDealer1" styleClass="text"
				styleId="addressDealer1" maxlength="50"
				value="${list[0].addressDealer1}"
				onblur="fnChangeCase('dealerMaster','addressDealer1')" />
			</td>
	</tr>
	       
      <!--Surendra Code 	 --> 

<!--     start by sachin  add some field for dsn dealer master  -->
	<tr>
			<td>
				<bean:message key="lbl.two" />
			</td>
			<td >
				<html:text property="addressDealer2" styleClass="text"
				styleId="addressDealer2" maxlength="50"
				value="${list[0].addressDealer2}"
				onblur="fnChangeCase('dealerMaster','addressDealer2')" />
			</td>	
			<td>
				<bean:message key="lbl.three" />
			</td>
			<td>
				<html:text property="addressDealer3" styleClass="text"
				styleId="addressDealer3" maxlength="50"
				value="${list[0].addressDealer3}"
				onblur="fnChangeCase('dealerMaster','addressDealer3')" />
			</td>
	</tr>
	<tr>		
		
			<td>
				<bean:message key="lbl.country" /><span><font color="red">*</font></span>
			</td>
			<td>
				<html:text property="country" styleId="country" styleClass="text"
				tabindex="-1" readonly="true" value="${list[0].country}" />
				<input type="button" class="lovbutton" id="countryButton"
				onclick="openLOVCommon(255,'dealerMaster','txtCountryCode','','','','','clearCountryLovChild','country')"
				value="" name="countryButton" />
				<input type="hidden" name="txtCountryCode" id="txtCountryCode"
				value="${list[0].txtCountryCode}" />
						

						

			</td>

			<td>
			<bean:message key="lbl.state" /><span><font color="red">*</font></span>	
			</td>
			<td>
			<html:text property="state" styleId="state" styleClass="text"
			tabindex="-1" readonly="true" value="${list[0].state}" />
			<input type="button" class="lovbutton" id="stateButton"
			onclick="openLOVCommon(256,'dealerMaster','txtStateCode','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','state')"
			value="" name="stateButton"/>
			<input type="hidden" name="txtStateCode" id="txtStateCode"
			value="${list[0].txtStateCode}" />
			</td>
	</tr>
	<tr>
			
			<td>
			<bean:message key="lbl.dist" /><span><font color="red">*</font></span>	
			</td>
			<td>
			<html:text property="dist" styleId="dist" styleClass="text"
			tabindex="-1" readonly="true" value="${list[0].dist}" />
			<input type="button" class="lovbutton" id="distButton"
			onclick="openLOVCommon(257,'dealerMaster','txtDistCode','state','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChild','dist')"
			value=" " name="distButton"/>
			<input type="hidden" name="txtDistCode" id="txtDistCode"
			value="${list[0].txtDistCode}" />
			</td>
			<td>
			<bean:message key="lbl.pincode" /><span><font color="red">*</font></span>				
			</td>
			<td>
			<html:text property="pincode" styleClass="text" styleId="pincode"
			maxlength="6" value="${list[0].pincode}"
			style="text-align:right;"
			onkeypress="return numericOnly(event,6);" />
			</td>		
	</tr>
	<tr>	
			<td>
				<bean:message key="lbl.contractPerson" />
			</td>
			<td >
				<html:text property="contractPerson" styleClass="text"
				styleId="contractPerson" maxlength="50"
				value="${list[0].contractPerson}"
				onblur="fnChangeCase('dealerMaster','contractPerson')" />
			</td>			
			<td>
			<bean:message key="lbl.mobileoff" />	
			</td>
			<td>
			<html:text property="phoneOff" styleClass="text"
			styleId="phoneOff" maxlength="10"
			value="${list[0].phoneOff}"
			style="text-align:right;"
			onkeypress="return numericOnly(event,10);" />
			</td>
	</tr>
				<tr>			
					<td >
						<bean:message key="lbl.alter" />
					</td>
					<td >
						<html:text property="phoneRes" styleClass="text"
							styleId="phoneRes" maxlength="20"
							value="${list[0].phoneRes}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,20);" />
					</td>
					<td>
						<bean:message key="lbl.email" />
					</td>
					<td>
						<html:text property="email" styleClass="text" styleId="email"
							maxlength="100" value="${list[0].email}"/>
					</td>
</tr>
				<tr>
					<td>
						<bean:message key="lbl.registrationNo" />
					</td>
					<td>
						<html:text property="registrationNo" styleClass="text"
							styleId="registrationNo" maxlength="100"
							value="${list[0].registrationNo}" />
					</td>
				<%-- Changes by Kumar Aman Started --%>				
					
					<td>
						<bean:message key="pan" />
						
					</td>
					<td>
						<html:text property="pan" styleClass="text"
						maxlength="10" styleId="pan" styleClass="text"
						value="${list[0].pan}"
						onkeypress=" isAlphNumericKey1(event);" onchange="return upperMe('pan');" />
					</td>
					
					</tr>
		<tr>	
		
<%-- Changes by Kumar Aman Ended --%>
					<td id="empanel1" style="display:none"><bean:message key="lbl.empanelled"/></td>
          			<td id="empanel2" style="display:none">      
              			<input type="checkbox" name="empanelledStatus" id="empanelledStatus"  />
      	  			</td> 
				</tr>


<!--end by sachin-->
    <tr>
	 <td><bean:message key="lbl.userNam" /></td>
 
        <td>
			<html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="${requestScope.userId}" />
   			<html:select property="userDesc" styleId="userDesc" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="levelNameList">
       			 <html:optionsCollection name="userNameList" value="userID" label="userDesc"/>
      		</logic:present>
     		</html:select>
      		<html:button property="levelButton" styleId="levelButtonEdit" onclick="return openMultiSelectLOVCommon(367,'dealerMaster','userDesc','','', '','','','lbxUserSearchId');" value=" " styleClass="lovbutton"></html:button>
   		</td>
   		<td>
 
     <bean:message key="lbl.status"/> </td>
     
     <td>      
              <input type="checkbox" name="dealerStatus" id="dealerStatus" checked="checked" />
      </td>   
  </tr> </logic:notPresent>
   
  
  <tr><td>
     <logic:present name="editVal">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditDealer();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
      
   <logic:present name="save">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnSaveDealer();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>
  
		
	</table>		

</fieldset></html:form>

<logic:present name="sms">
		<script type="text/javascript">


    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("dealerMaster").action="dealerMasterBehind.do?source=J";
	    document.getElementById("dealerMaster").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("dealerMaster").action="dealerMasterBehind.do?source=J";
	    document.getElementById("dealerMaster").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	} 
	
	</script>
</logic:present>
</body>
</html:html>
