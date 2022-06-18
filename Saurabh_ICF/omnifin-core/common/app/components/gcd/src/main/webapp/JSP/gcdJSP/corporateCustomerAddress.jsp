<%--
 	Auther Name :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose :- To provide user interface for Corporate C 
 	Documentation :-  
 --%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   
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
<BODY oncontextmenu="return false;" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('customerForm');">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>

<logic:present name="pageStatuss">
<script type="text/javascript">
//alert("Notok");
   top.opener.location.href="custEntryAction.do";
   <%session.removeAttribute("pageStatuss1");%>
//alert("ok");
    
</script>
</logic:present>

<!--<logic:present name="updateInDeal">-->
<!--<script type="text/javascript">-->
<!--  alert("Notok updateInDeal ");-->
<!--   top.opener.location.href="custEntryAction.do";-->
<%--   <%session.removeAttribute("updateInDeal");%>--%>
<!--  -->
<!--    -->
<!--</script>-->
<!--</logic:present>-->
<logic:present name="updateInLoan">
<script type="text/javascript">
 // alert("Notok updateInDeal ");
   top.opener.location.href="showCustomerDetailBehindAction.do";
   <%session.removeAttribute("updateInLoan");%>
  
    
</script>
</logic:present>



<input type="hidden" id="contextPath" name="contextPath" value="<%=path%>"/>
<input type="hidden" id="functionId" name="functionId" value="${functionId}"/>

<logic:notPresent name="approve">

<DIV id=centercolumn >
<DIV id=revisedcontainer>

<div id="updateAddress">

<HTML>
<HEAD>
<TITLE> <bean:message key="address.contact" /></TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">


</HEAD>

<BODY onload="enableAnchor();document.getElementById('customerForm').addr_type.focus();">

<center><input type="hidden" id="duplicateAdd" value="${requestScope.commAddressCheck}"/></center>
<center><input type="hidden" id="chTest" value="${requestScope.chTest}"/></center>
<html:form action="/customerAddressAction" styleId="customerForm" method="post">
<logic:notPresent name="approve">
<FIELDSET><LEGEND><bean:message key="lbl.copyAddress" /></LEGEND>
<table cellspacing="0" cellpadding="3" border="0" width="100%">
<tr>
	<td width="15%"><bean:message key="lbl.customerID" /><font color="red">*</font></td>
	<td width="11%">
		
		<html:hidden property="addressId" styleClass="text" styleId="addressId" value=""/>
		<html:text property="customerId" styleClass="text" styleId="customerId" readonly="true" value=""/>
		<html:hidden property="adType" styleClass="text" styleId="adType" value=""/>
		<html:hidden property="cmAdd" styleClass="text" styleId="cmAdd" value=""/>
		<html:button property="customerIdButton" styleId="customerIdButton" onclick="openAddressCustomer()" value=" " styleClass="lovbutton"></html:button>
	</td>		
	<td width="15%"><bean:message key="lbl.customerName" /><font color="red">*</font></td>
	<td width="13%"><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="10" value="" readonly="true"/></td>
</tr>
</table>
</FIELDSET>
</logic:notPresent>
<FIELDSET ><LEGEND><bean:message key="address.contact" /></LEGEND>
 <input type="hidden" name="hcommon" id="hcommon" />
<logic:present name="update">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
						
				<tr>
					<td><bean:message key="address.type" /><font color="red">*</font></td>
					<td><logic:present name="idividualId"><html:hidden property="hiddenIndividualId" styleId="hiddenIndividualId" value="${sessionScope.idividualId}" /><input type="hidden" name="flag" id="flag" value="individual"/></logic:present>
					<logic:notPresent name="idividualId"><html:hidden property="hiddenIndividualId" styleId="hiddenIndividualId" value="" /><input type="hidden" name="flag" id="flag" value="corporate"/></logic:notPresent>
								
					<html:hidden property="addId" styleId="addId" value="${customerList[0].addId}" />
						<html:select property="addr_type" styleId="addr_type" value="${customerList[0].addr_type}" styleClass="text" >
						   <option value="">
						   <bean:message key="select" /></option>
						   <logic:present name="customerList">
						   <logic:notEmpty name="customerList">
						  	  <html:optionsCollection name="addrList" label="registrationTypeDesc" value="registrationTypeCode" />
								</logic:notEmpty>
								</logic:present>
								<logic:notPresent  name="customerList">
									<html:optionsCollection name="addrList" label="registrationTypeDesc" value="registrationTypeCode" />
								</logic:notPresent>
						</html:select>
					</td>
								
					<td>
					<logic:present name="customerList">
						<html:hidden property="bp_type" styleClass="text" styleId="bp_type" value="${customerList[0].bp_type}" />
					</logic:present>
					<logic:notPresent name="customerList">
						<html:hidden property="bp_type" styleClass="text" styleId="bp_type" value="CS" />
					</logic:notPresent>
					<bean:message key="address.addr.one" /><font color="red">*</font></td>
					<td><html:text property="addr1" styleClass="text" styleId="addr1" maxlength="50" value="${customerList[0].addr1}" onchange="return upperMe('addr1');"/>
					 <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
				      <html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />
					</td>
					
				</tr>	
				<tr>
					<td><bean:message key="address.addr.two" /></td>
					<td><html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" value="${customerList[0].addr2}" onchange="return upperMe('addr2');"/></td>
						
	                <td><bean:message key="address.addr.three" /></td>
					<td><html:text property="addr3" styleClass="text" styleId="addr3" maxlength="50" value="${customerList[0].addr3}" onchange="return upperMe('addr3');"/></td>
					
                     
  				</tr>

				<TR>

				<TD><bean:message key="address.country" /><font color="red">*</font></TD>
           			 <TD>
          			<logic:notPresent name="defaultcountry">
          		
          					<html:text property="country" styleId="country"  maxlength="20" value="${customerList[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				</logic:notPresent>
    				<logic:present name="defaultcountry">
    					<html:text property="country" styleId="country"  maxlength="20" value="${defaultcountry[0].defaultcountryname}" styleClass="text" tabindex="-1" readonly="true"/>
    				</logic:present>
    				<logic:present name="strParentOption">
    					<html:hidden property="txtCountryCode" styleId="txtCountryCode"   value="${sessionScope.strParentOption}" styleClass="text" />
   					</logic:present >
   					<logic:notPresent name="strParentOption">
   					<logic:present name="defaultcountry">
   						<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${defaultcountry[0].defaultcountryid}" styleClass="text" />
   					</logic:present>
   						<logic:notPresent name="defaultcountry">
   					
   						<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${customerList[0].txtCountryCode}" styleClass="text" />
    					</logic:notPresent>
    				</logic:notPresent>
    			<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'customerForm','country','','','','','clearCountryLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button>	
	            
					</TD>					
         
              <TD><bean:message key="address.state" /><font color="red">*</font> </TD>
              <TD>
                <div id="statedetail">
               
                <html:text property="state" styleId="state" styleClass="text" size="20" value="${customerList[0].state}" readonly="true" tabindex="-1"></html:text>
    				<logic:present name="strParentOption">
     					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value="${sessionScope.strParentOption}"></html:hidden>
   					</logic:present >
   					<logic:notPresent name="strParentOption">
   						<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${customerList[0].txtStateCode}"></html:hidden>
    				</logic:notPresent>
    	        	<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'customerForm','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button>
	          
				</div>
			 </TD>
		   </TR>
		<TR>
			<TD><bean:message key="address.dist" /><font color="red">*</font></TD>
          <TD><div id="cityID"><html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${customerList[0].dist}" tabindex="-1"></html:text>
          						<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${customerList[0].txtDistCode}"></html:hidden>
   								<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'customerForm','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button>
                                </div></TD>
		<td><bean:message key="address.Tahsil" /></td>					
                    <td> <div id="tahsildetail"><html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${customerList[0].tahsil}" tabindex="-1"></html:text>
                   <html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			      <html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'customerForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   								</div></td>	
	
					</tr>	
				<tr>
					<td><bean:message key="address.pincode" /><font color="red">*</font></td>
					<td><html:text maxlength="6" property="pincode" styleClass="text" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].pincode}"/></td>				
				
					<td><bean:message key="address.primary" /><font color="red">*</font></td>
					<td><html:text property="primaryPhoneNo" styleId="primaryPhoneNo" styleClass="text" maxlength="10" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].primaryPhoneNo}"/>
					<%-- <logic:notEqual name="MobileVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="MobileVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal> --%>
					</td>	
                  
				</tr>	
				<tr>
					<td><bean:message key="address.alter" /></td>
					<td>
					
					STD No<html:text property="stdNo" maxlength="5" styleId="stdNo" styleClass="text" style="text-align: right;width: 35px;" onkeypress="return isNumberKey(event);" value="${customerList[0].stdNo}"/> 
					<html:text property="alternatePhoneNo" maxlength="10" styleId="alternatePhoneNo" styleClass="text" style="text-align: right;width: 80px;" onkeypress="return isNumberKey(event);" value="${customerList[0].alternatePhoneNo}"/></td>
					<td><bean:message key="address.toll" /></td>
					<td><html:text property="tollfreeNo" maxlength="25" styleId="tollfreeNo" styleClass="text" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].tollfreeNo}"/></td>	
                 </tr>	
				<tr>
					<td><bean:message key="address.fax" /></td>
					<td><html:text property="faxNo" maxlength="25" styleId="faxNo" styleClass="text" style="text-align: right" onkeypress="return isNumberKey(event);" value="${customerList[0].faxNo}"/></td>
					<td><bean:message key="address.land" /></td>
					<td><html:text property="landMark" maxlength="25" styleId="landMark" styleClass="text" value="${customerList[0].landMark}"/></td>	
               </tr>
			   <!-- space start by raj for GSTIN NO -->
			   <tr>
					<td><bean:message key="lbl.gstinno" /></td>
		    		<td><html:text styleClass="text" property="gstIn" styleId="gstIn" value="${customerList[0].gstIn}" maxlength="50" onchange="return upperMe('gstIn');"/></td>
					
					<%-- <td><bean:message key="lbl.relationshipS" /></td>
		                  <td><html:select styleClass="text"  styleId="relation" value="${customerList[0].relation}" property="relation">
                  <html:option value="">Select</html:option>  
<!--        				<logic:present name="relationType">-->
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>   
        		      				
<!--        				</logic:present>-->
                  </html:select>
                  </td>	 --%>		  
                   </tr>
			   <!-- space end by raj for GSTIN NO -->			   
               <tr>
               		<td><bean:message key="address.years" /><font color="red">*</font></td>
					<td>
						Year<html:text property="noYears" maxlength="3" styleId="noYears" value="${customerList[0].noYears}" styleClass="text7" onkeypress="return isNumberKey(event);" style="text-align: right"/>
						Month<html:select  property="noMonths" styleId="noMonths"  value="${customerList[0].noMonths}" styleClass="gcdAddress">
							<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
							<html:option value="10">10</html:option>
							<html:option value="11">11</html:option>
						</html:select>
					</td>
					<td><bean:message key="address.branchDistance" /></td>
					<td><html:text property="distanceBranch" styleId="distanceBranch" maxlength="10" styleClass="text" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" style="text-align: right" value="${customerList[0].distanceBranch}"/></td>	
               </tr>	
				<tr>
					<td><bean:message key="lbl.communication.address"/></td>
					<td>
					
			<logic:present name="customerList">
          		<logic:iterate name="customerList" id="subcustomerList">
          		<logic:notEqual name="subcustomerList" property="communicationAddress" value="Y">
          		  	 <input type="checkbox" name="communicationAddress" id="communicationAddress"   />
          		  	 <input type="hidden" id="communicationAddressCheckBox" name="communicationAddressCheckBox" value="0"/>
          		</logic:notEqual>
          		<logic:equal name="subcustomerList" property="communicationAddress" value="Y">
          		  	 <input type="checkbox" name="communicationAddress" id="communicationAddress" checked="checked" />
          		  	 <input type="hidden" id="communicationAddressCheckBox" name="communicationAddressCheckBox" value="1"/>          		  	 
          		</logic:equal>
          		</logic:iterate>
         	</logic:present>
         	<logic:notPresent name="customerList">
         		 <input type="checkbox" name="communicationAddress" id="communicationAddress"  />
				  <input type="hidden" id="communicationAddressCheckBox" name="communicationAddressCheckBox" value="0"/>
         	</logic:notPresent>
					</td>
					
			<td><bean:message key="lbl.propertyOwnership" /></td>
			<td>
				<html:select styleClass="text" styleId="addDetails" property="addDetails" value="${customerList[0].addDetails}">
				<html:option value="">--Select--</html:option>
				<html:option value="O">Owned</html:option>
				<html:option value="R">Rented</html:option>
				<html:option value="C">Company Provided</html:option>
				</html:select>
			</td>
					
				</tr>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
<TR>
          
          <TD ><span class="white">
          <logic:notEqual name="functionId" value="500000123">
            <logic:present name="customerList">
             	<button type="button" name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick=" return updateAddress('');"><bean:message key="button.save" /></button>
             	<button type="button" name="button2"  class="topformbutton4" title="Alt+R" accesskey="R" onclick="return relationShip();"><bean:message key="button.relationShip" /></button>
             	<%-- <logic:notEqual name="MobileVerificationFlag" value="Y">
                <button type="button" name="button3"  class="topformbutton2" title="Alt+W" accesskey="W" onclick="return verifyMobile();"><bean:message key="button.verify" /></button>
                </logic:notEqual> --%>
             </logic:present>
             <logic:notPresent name="customerList">
             	<button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick=" return insert('');"><bean:message key="button.save" /></button>
            	 <button type="button" name="button2"  class="topformbutton4" title="Alt+R" accesskey="R" onclick="return relationShip();"><bean:message key="button.relationShip" /></button>
            	 <button type="button" name="button2"  class="topformbutton2" title="Alt+C" accesskey="C" onclick="return allClear();"><bean:message key="button.clear" /></button>
            	  
             </logic:notPresent>
          </logic:notEqual>
            </span></TD>
</TR>
</table>
</table>

							<td colspan="4" >
							<FIELDSET><legend><bean:message key="institutionContact.list" /></legend>


	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="4">
							<tr class="white2">
                                <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"><bean:message key="contact.select" /></b></td> 
								<td><B><bean:message key="address.type" /></b></td>
								<td><B><bean:message key="address.addr" /></b></td>
								 <td><B><bean:message key="address.dist" /></b></td>
								 <td ><B><bean:message key="address.state" /></b></td>						
								<td><B><bean:message key="address.country" /></b></td>	
								
							</tr>
							
							<logic:present name="briefAddr">
							<logic:iterate id="subbriefAddr" name="briefAddr">
							<tr class="white1">
							<td ><input type="checkbox" name="chk" id="chk" value="${subbriefAddr.bp_id }"/></td>
								<td ><a href="#" id="anchor0" onclick="return modifyAddress(${subbriefAddr.bp_id });">${subbriefAddr.addr_type }</a></td>
								<td >${subbriefAddr.addr1 }</td>
								<TD>${subbriefAddr.dist }</TD>
								<TD>${subbriefAddr.state }</TD>
								<TD>${subbriefAddr.country }</TD>
							</tr>	
							</logic:iterate>
								</logic:present>		
										</table>
										
								</table>
					<logic:notEqual name="functionId" value="500000123">
					  <button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteAddr();"><bean:message key="button.delete" /></button>
					</logic:notEqual>
</logic:present>

<logic:notPresent name="update">
<table cellspacing="0" cellpadding="3" border="0" width="100%">
						
				<tr>
					<td><bean:message key="address.type" /><font color="red">*</font></td>
					<td><logic:present name="idividualId"><html:hidden property="hiddenIndividualId" styleId="hiddenIndividualId" value="${sessionScope.idividualId}" /><input type="hidden" name="flag" id="flag" value="individual"/></logic:present>
					<logic:notPresent name="idividualId"><html:hidden property="hiddenIndividualId" styleId="hiddenIndividualId" value="" /><input type="hidden" name="flag" id="flag" value="corporate"/></logic:notPresent>
					
					<html:hidden property="addId" styleId="addId" value="${customerList[0].addId}" />
						<html:select property="addr_type" styleId="addr_type" styleClass="text" value="${customerList[0].addr_type}">
						   <option value=""><bean:message key="select" /></option>
						   <logic:notEmpty name="addrList">
							  <html:optionsCollection name="addrList" label="registrationTypeDesc" value="registrationTypeCode" />
						</logic:notEmpty>
						</html:select>
								</td>
								
					<td>
					<html:hidden property="bp_type" styleClass="text" styleId="bp_type" value="CS" />	
						
				<bean:message key="address.addr.one" /><font color="red">*</font></td>
					<td><html:text property="addr1" styleClass="text" styleId="addr1" maxlength="50" value="${customerList[0].addr1}" onchange="return upperMe('addr1');"/>
					
					  <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId }" />
					   <html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId }" />
					</td>
					
					</tr>	
					<TR>
					<td><bean:message key="address.addr.two" /></td>
					<td><html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" value="${customerList[0].addr2}" onchange="return upperMe('addr2');"/></td>
					
					<td><bean:message key="address.addr.three" /></td>
					<td><html:text property="addr3" styleClass="text" styleId="addr3" maxlength="50" value="${customerList[0].addr3}" onchange="return upperMe('addr3');"/></td>	
                    
                    
		</TR>
		<TR>
					
          <TD><bean:message key="address.country" /><font color="red">*</font></TD>
          <TD>
          <logic:present name="defaultcountry">
              
                  <html:text property="country" styleId="country" size="20" value="${defaultcountry[0].defaultcountryname}" styleClass="text" readonly="true" tabindex="-1"/>
    				</logic:present>
    			<logic:notPresent name="defaultcountry">
    			
    			<html:text property="country" styleId="country" size="20" value="${customerList[0].country}" styleClass="text" readonly="true" tabindex="-1"/>
    				
    			</logic:notPresent>
    				<logic:present name="strParentOption">
    					<html:hidden property="txtCountryCode" styleId="txtCountryCode"  value="${sessionScope.strParentOption}" styleClass="text" />
   					</logic:present >
   					<logic:notPresent name="strParentOption">
   						<logic:present name="defaultcountry">
   						
   							<html:hidden property="txtCountryCode" styleId="txtCountryCode"  value="${defaultcountry[0].defaultcountryid}" styleClass="text" />
    				
   						</logic:present>
   					<logic:notPresent name="defaultcountry">
   				
   						<html:hidden property="txtCountryCode" styleId="txtCountryCode"  value="${customerList[0].txtCountryCode}" styleClass="text" />
    				</logic:notPresent>
    				</logic:notPresent>
    				<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'customerForm','country','','','','','clearCountryLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button>                   
  	 </TD>
          <TD><bean:message key="address.state" /><font color="red">*</font> </TD>
          <TD noWrap>
          
          	<div id="statedetail">
          	<html:text property="state" styleId="state" styleClass="text" size="20" value="${customerList[0].state}" readonly="true" tabindex="-1"></html:text>	
    				<logic:present name="strParentOption">
     					<input type="hidden" name="txtStateCode" id="txtStateCode" class="text" value="${sessionScope.strParentOption}"/>
   					</logic:present >
   					<logic:notPresent name="strParentOption">
   						<input type="hidden" name="txtStateCode" id="txtStateCode" class="text" value="${customerList[0].txtStateCode}"/>
    				</logic:notPresent>
    			<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'customerForm','state','country','txtStateCode', 'txtCountryCode','Select Country Lov','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button>
			</div>
					
			</TD>
		  </TR>
	<TR>
	<TD><bean:message key="address.dist" /><font color="red">*</font></TD>
          <TD >
                    						<div id="cityID">
          						
          						<html:text property="dist" styleClass="text" styleId="dist" maxlength="20" value="${customerList[0].dist}" readonly="true" tabindex="-1"/>
          						<html:hidden property="txtDistCode" styleClass="text" styleId="txtDistCode" value="${customerList[0].txtDistCode}" />
   							<html:button property="distButton" styleId="distButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'customerForm','dist','state','txtDistCode', 'txtStateCode','Select State Lov','clearDistrictLovChildNew','hcommon')" value=" " styleClass="lovbutton"> </html:button>
          						</div></TD>
	   <td><bean:message key="address.Tahsil" /></td>
					<td>
					<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${customerList[0].tahsil}" tabindex="-1"></html:text>
                   <html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			      <html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'customerForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first!!!','','hcommon');" value=" " styleClass="lovbutton"> </html:button>
 			      </td>	
				</tr>	
				<tr>
					<td><bean:message key="address.pincode" /><font color="red">*</font></td>
					<td><html:text property="pincode" styleClass="text" styleId="pincode" maxlength="6" onkeypress="return isNumberKey(event);" style="text-align: right" value="${customerList[0].pincode}"/></td>				
				
					<td><bean:message key="address.primary" /><font color="red">*</font></td>
					<td><html:text property="primaryPhoneNo" styleId="primaryPhoneNo" maxlength="10" styleClass="text" onkeypress="return isNumberKey(event);" style="text-align: right" value="${customerList[0].primaryPhoneNo}"/>
				<%-- <logic:notEqual name="MobileVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="MobileVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal> --%>
					</td>	
                 </tr>	
				<tr>
					<td><bean:message key="address.alter" /></td>
					<td>STD No<html:text property="stdNo" maxlength="5" styleId="stdNo" styleClass="text" style="text-align: right;width: 35px;" onkeypress="return isNumberKey(event);" value="${customerList[0].stdNo}"/>
					<html:text property="alternatePhoneNo" maxlength="10" styleId="alternatePhoneNo" styleClass="text" onkeypress="return isNumberKey(event);" style="text-align: right;width: 80px;" value="${customerList[0].alternatePhoneNo}"/></td>
					<td><bean:message key="address.toll" /></td>
					<td><html:text property="tollfreeNo" styleId="tollfreeNo" maxlength="25"  styleClass="text" onkeypress="return isNumberKey(event);" style="text-align: right" value="${customerList[0].tollfreeNo}"/></td>	
                    	
				</tr>
				<tr>
					<td><bean:message key="address.fax" /></td>
					<td><html:text property="faxNo" styleId="faxNo" styleClass="text" maxlength="25" onkeypress="return isNumberKey(event);" style="text-align: right" value="${customerList[0].faxNo}"/></td>
					<td><bean:message key="address.land" /></td>
					<td><html:text property="landMark" styleId="landMark" maxlength="25" styleClass="text" value="${customerList[0].landMark}"/></td>	
                </tr>
			   <!-- space start by raj for GSTIN NO -->
			   <tr>
					<td><bean:message key="lbl.gstinno" /></td>
		    		<td><html:text styleClass="text" property="gstIn" styleId="gstIn" value="${customerList[0].gstIn}" maxlength="50" onchange="return upperMe('gstIn');"/></td>
						
					<%-- <td><bean:message key="lbl.relationshipS" /></td>
		                  <td><html:select styleClass="text"  styleId="relation" value="${customerList[0].relation}" property="relation">
                  <html:option value="">Select</html:option>  
<!--        				<logic:present name="relationType">-->
        		  <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
<!--        				</logic:present>-->
                  </html:select>
                  </td>	 --%>		
			   </tr>
			   <!-- space end by raj for GSTIN NO -->				
                <tr>
                	<td><bean:message key="address.years" /><font color="red">*</font></td>
					<td>
						Year<html:text property="noYears" maxlength="3" styleId="noYears" value="${customerList[0].noYears}" styleClass="text7" onkeypress="return isNumberKey(event);"/>
						Month<html:select property="noMonths" styleId="noMonths" value="${customerList[0].noMonths}" styleClass="gcdAddress">
							<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
							<html:option value="10">10</html:option>
							<html:option value="11">11</html:option>
						</html:select>
					</td>
					<td><bean:message key="address.branchDistance" /></td>
					<td><html:text property="distanceBranch" styleId="distanceBranch" maxlength="10" styleClass="text" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" style="text-align: right" value="${customerList[0].distanceBranch}"/></td>	
                </tr>
				<tr>
					<td><bean:message key="lbl.communication.address"/></td>
					<td>
					
				<logic:present name="customerList">
          		<logic:iterate name="customerList" id="subcustomerList">
          		<logic:notEqual name="subcustomerList" property="communicationAddress" value="Y">
          		  	 <input type="checkbox" name="communicationAddress" id="communicationAddress" />
					  <input type="hidden" id="communicationAddressCheckBox" name="communicationAddressCheckBox" value="0"/>
          		</logic:notEqual>
          		<logic:equal name="subcustomerList" property="communicationAddress" value="Y">
          		  	<input type="checkbox" name="communicationAddress" id="communicationAddress" checked="checked" />
					<input type="hidden" id="communicationAddressCheckBox" name="communicationAddressCheckBox" value="1"/>
          		</logic:equal>
          		</logic:iterate>
         	</logic:present>
         	<logic:notPresent name="customerList">
         		<input type="checkbox" name="communicationAddress" id="communicationAddress" />
				<input type="hidden" id="communicationAddressCheckBox" name="communicationAddressCheckBox" value="0"/>
         	</logic:notPresent>
					</td>
			<td><bean:message key="lbl.propertyOwnership" /></td>
			<td>
				<html:select styleClass="text" styleId="addDetails" property="addDetails" value="${customerList[0].addDetails}">
				<html:option value="">--Select--</html:option>
				<html:option value="O">Owned</html:option>
				<html:option value="R">Rented</html:option>
				<html:option value="C">Company Provided</html:option>
				</html:select>
			</td>
					<td></td>
					<td></td>
				</tr>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">	
<TR>
          
          <TD noWrap><span class="white">
         <logic:notEqual name="functionId" value="500000123">         
          <logic:present name="customerList">
             	<button type="button" name="button1" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return updateAddress('');"><bean:message key="button.save" /></button>
             	<button type="button" name="button2"  class="topformbutton4" title="Alt+R" accesskey="R" onclick="return relationShip();"><bean:message key="button.relationShip" /></button>
             	<%-- <logic:notEqual name="MobileVerificationFlag" value="Y">
             	<button type="button" name="button3"  class="topformbutton2" title="Alt+W" accesskey="W" onclick="return verifyMobile();"><bean:message key="button.verify" /></button>
             	</logic:notEqual> --%>
             </logic:present>
             <logic:notPresent name="customerList">
                 <button type="button" name="button1" id="save" class="topformbutton2" title="Alt+V" accesskey="V"  onclick=" return insert('');"><bean:message key="button.save" /></button>
                  <button type="button" name="button2"  class="topformbutton4" title="Alt+R" accesskey="R" onclick="return relationShip();"><bean:message key="button.relationShip" /></button>
          <button type="button" name="button2"  class="topformbutton2" title="Alt+C" accesskey="C" onclick="return allClear();"><bean:message key="button.clear" /></button>
         
             </logic:notPresent>
        </logic:notEqual>         
         
         
          </span></TD>
</TR></table>
</table>

							<td colspan="4" >
							<FIELDSET><legend><bean:message key="institutionContact.list" /></legend>


	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="4">
							<tr class="white2">
                                <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"><bean:message key="contact.select" /></b></td> 
								<td ><B><bean:message key="address.type" /></b></td>
								<td ><B><bean:message key="address.addr" /></b></td>
								 <td ><B><bean:message key="address.dist" /></b></td>
								 <td ><B><bean:message key="address.state" /></b></td>						
								<td ><B><bean:message key="address.country" /></b></td>	
 							</tr>
							<logic:present name="briefAddr">
							<logic:iterate id="subbriefAddr" name="briefAddr">
							<tr class="white1">
							<td ><input type="checkbox" name="chk" id="chk" value="${subbriefAddr.bp_id }"/></td>
								<td ><a href="#" id="anchor0" onclick="return modifyAddress(${subbriefAddr.bp_id });">${subbriefAddr.addr_type }</a></td>
								<td >${subbriefAddr.addr1 }</td>
								<TD>${subbriefAddr.dist }</TD>
								<TD>${subbriefAddr.state }</TD>
								<TD>${subbriefAddr.country }</TD>
							</tr>	
							</logic:iterate>
								</logic:present>		
										</table>
										
								</table>

						<logic:notEqual name="functionId" value="500000123">
					    <button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteAddr();"><bean:message key="button.delete" /></button>
					    </logic:notEqual>
			
		
	
</logic:notPresent>
</FIELDSET>
</html:form></BODY></HTML>
	  
	  </div>
</DIV></DIV>

</logic:notPresent>

<logic:present name="approve">

	<DIV id=centercolumn>
<DIV id=revisedcontainer>

<FIELDSET><LEGEND><bean:message key="address.contact" /></LEGEND>
<HTML>
<HEAD>
<TITLE> <bean:message key="address.contact" /></TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">


</HEAD>

<BODY>
<html:form action="/customerAddressAction" styleId="customerForm">

<table cellspacing="0" cellpadding="3" border="0" width="100%">
						
				<tr>
					<td><bean:message key="address.type" /><font color="red">*</font></td>
					<td><logic:present name="idividualId"><input type="hidden" name="hiddenIndividualId" id="hiddenIndividualId" value="${sessionScope.idividualId}"/><input type="hidden" name="flag" id="flag" value="individual"/></logic:present>
					<logic:notPresent name="idividualId"><input type="hidden" name="hiddenIndividualId" id="hiddenIndividualId" value=""/><input type="hidden" name="flag" id="flag" value="corporate"/></logic:notPresent>
								
					<html:hidden property="addId" styleId="addId" value="${customerList[0].addId}"/>
						<html:select property="addr_type" styleId="addr_type" value="${customerList[0].addr_type}" disabled="true" styleClass="text">
						   <option value=""><bean:message key="select" /></option>
						   <logic:present name="customerList">
						   <logic:notEmpty  name="addrList" >
						  	  <html:optionsCollection name="addrList" label="registrationTypeDesc" value="registrationTypeCode" />
								</logic:notEmpty>
								</logic:present>
									</html:select>
								</td>
								
					<td>
					
						<html:hidden property="bp_type" styleClass="text" styleId="bp_type" value="${customerList[0].bp_type}" />	
				
				<bean:message key="address.addr.one" /><font color="red">*</font></td>
					<td><html:text property="addr1" styleClass="text" styleId="addr1" maxlength="50" value="${customerList[0].addr1}" disabled="true" tabindex="-1"/>
					
				
					  <html:hidden property="bp_id" styleClass="text" styleId="bp_id" value="${sessionScope.corporateId}" />
				      <html:hidden property="bp_id1" styleClass="text" styleId="bp_id1" value="${sessionScope.idividualId}" />
					</td>
					
					</tr>	
					<TR>
					<td><bean:message key="address.addr.two" /></td>
					<td><html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" value="${customerList[0].addr2}" disabled="true" tabindex="-1"/></td>	
                    <td><bean:message key="address.addr.three" /></td>
					<td><html:text property="addr3" styleClass="text" styleId="addr3" maxlength="50" value="${customerList[0].addr3}" disabled="true" tabindex="-1"/></td>
                                    
				</TR>

			<TR>

			  <TD><bean:message key="address.country" /><font color="red">*</font></TD>
          			<TD>
  						<html:text property="country" styleClass="text" styleId="country" size="20" value="${customerList[0].country}" styleClass="text" disabled="true" tabindex="-1" />
  						<html:hidden property="txtCountryCode" styleClass="text" styleId="txtCountryCode" value="${customerList[0].txtCountryCode}" />
					</TD>
					
         
          <TD><bean:message key="address.state" /><font color="red">*</font> </TD>
          <TD noWrap>
          
          				<div id="statedetail">
          				
          				<html:text property="state" styleClass="text" styleId="state" maxlength="20" value="${customerList[0].state}" readonly="true" tabindex="-1"/>
          				<html:hidden property="txtStateCode" styleClass="text" styleId="txtStateCode" value="${customerList[0].txtStateCode}" />
          		     			
									</div>
									
									</TD>
		  </TR>
<TR><TD><bean:message key="address.dist" /><font color="red">*</font></TD>
          <TD noWrap>
          
          						<div id="cityID">
          						
          						<html:text property="dist" styleClass="text" styleId="dist" maxlength="20" value="${customerList[0].dist}" readonly="true" tabindex="-1"/>
          						<html:hidden property="txtDistCode" styleClass="text" styleId="txtDistCode" value="${customerList[0].txtDistCode}" />
          					 </div></TD>
 <td><bean:message key="address.Tahsil" /></td>
					<td><html:text property="tahsil" styleId="tahsil" maxlength="20" readonly="true" styleClass="text" value="${customerList[0].tahsil}"/></td>
		 </tr>	
				<tr>
					<td><bean:message key="address.pincode" /></td>
					<td><html:text property="pincode" styleClass="text" styleId="pincode" maxlength="6" onkeypress="return isNumberKey(event);" value="${customerList[0].pincode}" disabled="true" tabindex="-1"/></td>				
				
					<td><bean:message key="address.primary" /><font color="red">*</font></td>
					<td><html:text property="primaryPhoneNo" maxlength="10" styleId="primaryPhoneNo" styleClass="text" onkeypress="return isNumberKey(event);" value="${customerList[0].primaryPhoneNo}" disabled="true" tabindex="-1"/>
					<%-- <logic:notEqual name="MobileVerificationFlag" value="Y">		
											<img src="<%=request.getContextPath()%>/images/theme1/delete.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
													</logic:notEqual>
														<logic:equal name="MobileVerificationFlag" value="Y">
											<img src="<%=request.getContextPath()%>/images/theme1/ic_validated.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
											</logic:equal> --%>
					</td>	
                   
				</tr>	
				<tr>
						
                    <td><bean:message key="address.alter" /></td>
					<td>STD No<html:text property="stdNo" maxlength="5" styleId="stdNo" styleClass="text" style="width: 35px;" onkeypress="return isNumberKey(event);" disabled="true" value="${customerList[0].stdNo}"/>
					<html:text property="alternatePhoneNo" maxlength="10" styleId="alternatePhoneNo" styleClass="text" style="width: 80px;" onkeypress="return isNumberKey(event);" value="${customerList[0].alternatePhoneNo}" disabled="true" tabindex="-1"/></td>
               <td><bean:message key="address.toll" /></td>
					<td><html:text property="tollfreeNo" maxlength="25" styleId="tollfreeNo" styleClass="text" onkeypress="return isNumberKey(event);" value="${customerList[0].tollfreeNo}" disabled="true" tabindex="-1"/></td>
                </tr>	
				<tr>
					<td><bean:message key="address.land" /></td>
					<td><html:text property="landMark" styleId="landMark" maxlength="25" styleClass="text" value="${customerList[0].landMark}" disabled="true"/></td>	
                    <td><bean:message key="address.fax" /></td>
					<td><html:text property="faxNo" styleId="faxNo" maxlength="25" styleClass="text" onkeypress="return isNumberKey(event);" value="${customerList[0].faxNo}" disabled="true" tabindex="-1"/></td>
                </tr>
			   <!-- space start by raj for GSTIN NO -->
			   <tr>
					<td><bean:message key="lbl.gstinno" /></td>
		    		<td><html:text styleClass="text" property="gstIn" styleId="gstIn" value="${customerList[0].gstIn}" maxlength="50" readonly="true" /></td>
						
					<%-- <td><bean:message key="lbl.relationshipS" /></td>
		                  <td><html:select styleClass="text"  styleId="relation" value="${customerList[0].relation}" property="relation" disabled="true">
                  <html:option value="">Select</html:option>  
<!--        				<logic:present name="relationType">-->
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
<!--        				</logic:present>-->
                  </html:select>
                  </td>	 --%>		
			   </tr>
			   <!-- space end by raj for GSTIN NO -->				
                <tr>
                	<td><bean:message key="address.years" /><font color="red">*</font></td>
					<td>
						Year<html:text property="noYears" maxlength="3" styleId="noYears" value="${customerList[0].noYears}" styleClass="text7" onkeypress="return isNumberKey(event);" disabled="true" tabindex="-1"/>
						Month<html:select property="noMonths" styleId="noMonths" value="${customerList[0].noMonths}" disabled="true" tabindex="-1" styleClass="gcdAddress">
							<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
							<html:option value="10">10</html:option>
							<html:option value="11">11</html:option>
						</html:select>
					</td>
					<td><bean:message key="address.branchDistance" /></td>
					<td><html:text property="distanceBranch" styleId="distanceBranch" readonly="true" maxlength="10" styleClass="text" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" style="text-align: right" value="${customerList[0].distanceBranch}"/></td>		
                </tr>	
				<tr>
					<td><bean:message key="lbl.communication.address"/></td>
					<td>
					<logic:present name="customerList">
					
          			<logic:iterate name="customerList" id="subcustomerList">
          			<logic:notEqual name="subcustomerList" property="communicationAddress" value="Y">
          		  	<input type="checkbox" name="communicationAddress" id="communicationAddress" disabled="disabled" tabindex="-1"/>
          		</logic:notEqual>
          		<logic:equal name="subcustomerList" property="communicationAddress" value="Y">
          		  	<input type="checkbox" name="communicationAddress" id="communicationAddress" checked="checked" disabled="disabled" tabindex="-1"/>
          		</logic:equal>
          		</logic:iterate>
         	</logic:present>
         	<logic:notPresent name="customerList">
         		<input type="checkbox" name="communicationAddress" id="communicationAddress" disabled="disabled" tabindex="-1"/>
         	</logic:notPresent></td>
         	<td><bean:message key="lbl.propertyOwnership" /></td>
			<td>
				<html:select styleClass="text" styleId="addDetails" property="addDetails" disabled="true" value="${customerList[0].addDetails}">
				<html:option value="">--Select--</html:option>
				<html:option value="O">Owned</html:option>
				<html:option value="R">Rented</html:option>
				<html:option value="C">Company Provided</html:option>
				</html:select>
			</td>
					<td></td>
					<td></td>
				</tr>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">	
<TR>
          
          <TD noWrap><span class="white">
            <button type="button" name="button2"  class="topformbutton4" title="Alt+R" accesskey="R" onclick="return relationShip();"><bean:message key="button.relationShip" /></button>
         <%--  <logic:equal name="functionId" value="3000296">
            <logic:notEqual name="MobileVerificationFlag" value="Y">
              <button type="button" name="button3"  class="topformbutton2" title="Alt+W" accesskey="W" onclick="return verifyMobile();"><bean:message key="button.verify" /></button>
                </logic:notEqual>
               </logic:equal>   --%>    
          </span></TD>
          
         
</TR></table>
							<td colspan="4">
			<logic:notPresent name="verificationType">
							<FIELDSET><legend><bean:message key="institutionContact.list" /></legend>


	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="4">
							<tr class="white2">
                                <td ><B>
                                <logic:present name="underWriter">
                                <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled" tabindex="-1"><bean:message key="contact.select" />
                                </logic:present>
                                <logic:notPresent name="underWriter">
                                <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"><bean:message key="contact.select" />
                                </logic:notPresent>
                                </b></td> 
								<td ><B><bean:message key="address.type" /></b></td>
								<td ><B><bean:message key="address.addr" /></b></td>
								 <td ><B><bean:message key="address.dist" /></b></td>
								 <td ><B><bean:message key="address.state" /></b></td>						
								<td ><B><bean:message key="address.country" /></b></td>	
								
							</tr>
							
							<logic:present name="addressInfo">
							<logic:iterate id="subaddressInfo" name="addressInfo">
							<tr class="white1">
							<td >
							<logic:present name="underWriter">
							<input type="checkbox" name="chk" id="chk" value="${subaddressInfo.addId }" disabled="disabled"/>
							</logic:present>
							<logic:notPresent name="underWriter">
							<input type="checkbox" name="chk" id="chk" value="${subaddressInfo.addId }" disabled="disabled"/>
							</logic:notPresent>
							
							</td>
								<td ><a href="#" id="anchor0" onclick="return modifyAddress(${subaddressInfo.addId });">${subaddressInfo.addr_type }</a></td>
								<td >${subaddressInfo.addr1 }</td>
								<TD>${subaddressInfo.dist }</TD>
								<TD>${subaddressInfo.state }</TD>
								<TD>${subaddressInfo.country }</TD>
							</tr>	
							</logic:iterate>
								</logic:present>		
										</table>
										
								</table>
								
	
</FIELDSET> 
	</logic:notPresent>
	
	
</table>
</html:form>
</FIELDSET> 
</DIV></DIV>

</logic:present>
<logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='S')
	{
	   
		alert('<%=request.getAttribute("procval").toString()%>');
		
	}
	</script>
</logic:present>
<logic:present name="sms">
 <script type="text/javascript">
	if("<%=request.getAttribute("sms").toString()%>"=="S")
	{
		alert("<bean:message key="lbl.corporateCustAdress" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateAddress";
		var dupAdd = document.getElementById("duplicateAdd").value;
	 		if(dupAdd != ''){
	 			alert(dupAdd);
	 		}
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='I')
	{
		
		alert("<bean:message key="lbl.IndividualCustAdress" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualAddress";
	    		var dupAdd = document.getElementById("duplicateAdd").value;
	 		if(dupAdd != ''){
	 			alert(dupAdd);
	 		}
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='CU')
	{
		
		alert("<bean:message key="lbl.CorporateAddressUpdated" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateAddress";
	        		var dupAdd = document.getElementById("duplicateAdd").value;
	 		if(dupAdd != ''){
	 			alert(dupAdd);
	 		}
	    document.getElementById('customerForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='CI')
	{
		
		
		alert("<bean:message key="lbl.IndividualAddressUpdated" />");
		document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayIndividualAddress";
	       		var dupAdd = document.getElementById("duplicateAdd").value;
	 		if(dupAdd != ''){
	 			alert(dupAdd);
	 		}
	    document.getElementById('customerForm').submit();
		
	}

	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
			alert('<bean:message key="lbl.errorSuccess" />');
			document.getElementById('customerForm').action="<%=request.getContextPath()%>/corporateAddressAction.do?method=displayCorporateAddress";
	}
	
	</script>
</logic:present>
<logic:present name="mobileNoCheck">
	<script type="text/javascript">
	if('<%=request.getAttribute("mobileNoCheck")%>'=='Y')
	{
	   
		   var bypassDedupe = confirm('<bean:message key="msg.mobileNoExist" />');
			if(bypassDedupe)
			{
		       
				 updateAddress('PASS');
			}
		
	}
	</script>
</logic:present>
<logic:present name="mobileNoCheckInsert">
	<script type="text/javascript">
	if('<%=request.getAttribute("mobileNoCheckInsert")%>'=='Y')
	{
	   
		   var bypassDedupe = confirm('<bean:message key="msg.mobileNoExist" />');
			if(bypassDedupe)
			{
		     
				 insert('PASS');
			}
		
	}
	</script>
</logic:present>
<logic:present name="mobileNoCheckRelationFlag">
	<script type="text/javascript">
	if('<%=request.getAttribute("mobileNoCheckRelationFlag")%>'=='Y')
	{
	   
		 alert('<bean:message key="msg.mobileNoExistRelationShipFlag" />');
	}
	</script>
</logic:present>
<logic:present name="mobileNoCheckInsertRelationFlag">
	<script type="text/javascript">
	if('<%=request.getAttribute("mobileNoCheckInsertRelationFlag")%>'=='Y')
	{
	   
	 alert('<bean:message key="msg.mobileNoExistRelationShipFlag" />');
		
	}
	</script>
</logic:present>
<logic:present name="gstNoExist">
	<script type="text/javascript">
	if('<%=request.getAttribute("gstNoExist")%>'=='Y')
	{
	   
	 alert('GST No already exists');
		
	}
	</script>
</logic:present>

<script>
	setFramevalues("customerForm");
</script>
</BODY>

</HTML>


