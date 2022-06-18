<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Stake Holder Details
 	Documentation    :- 
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/stackHolder.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

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
	<body oncontextmenu="return false" onclick="parent_disable();" onload="enableAnchor();checkChanges('StakeForm');document.getElementById('StakeForm').sex.focus();">
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
				<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>

	<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
	<input type="hidden" id="incDate" name="incDate" value="${incDate }"/>
	<input type="hidden" id="percentage" name="percentage" value="${percentage}"/>
	<logic:notPresent name="approve">

	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/corporatStackHolderPageAction" styleId="StakeForm" method="post" >
	<logic:present name="update">
<fieldset>	  
	<legend><bean:message key="lbl.mgmtdetail" /></legend>
	<input type="hidden" name="currStackHolerId" value="${getStackHolderList[0].holderid}"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>
    <table width="87%" border="0" cellspacing="1" cellpadding="1">
      <tr>
    <td><bean:message key="stake.salut" /> <font color="red">*</font></td>
    <td><html:select property="sex" styleId="sex" styleClass="text" value="${getStackHolderList[0].sex}">
       <html:option value=""><bean:message key="select" /></html:option>
        <logic:present name="salutation">

        <html:optionsCollection name="salutation" label="SALUTATION_ID" value="SALUTATION_DESC" />

        </logic:present>     
       </html:select></td>
       <td> <bean:message key="stake.name" /><font color="red">*</font></td>
       <td ><html:text property="holderName" styleId="holderName" value="${getStackHolderList[0].holderName}" styleClass="text" maxlength="50" onchange="return upperMe('holderName');" /></td>
       </tr>
      <tr>
        <td><bean:message key="stake.type" /><font color="red">*</font></td>
        <td><html:select property="holderType" styleId="holderType" value="${getStackHolderList[0].holderType}" styleClass="text" onchange="return setStackPerc(this.value);">
        <html:option value=""><bean:message key="select" /></html:option>
        <logic:present name="holdertypes">
        <html:optionsCollection name="holdertypes" label="STAKEHOLDER_DESC" value="STAKEHOLDER_ID" />
        </logic:present>        
        </html:select></td>
            
		<td><bean:message key="stake.perc" /></td>
		<td><html:text property="holdingPerc" style="text-align: right" styleId="holdingPerc" value="${getStackHolderList[0].holdingPerc}" styleClass="text" onkeyup="checkNumber(this.value, event, 3,id);return checkRate('holdingPerc');" onkeypress="return numbersonly(event,id,3)"
													onblur="formatNumber(this.value,id);" onfocus="keyUpNumber(this.value, event, 3,id);"></html:text></td>
        </tr>      
        <tr>
        <td><bean:message key="stake.birth" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
        <td ><html:text property="dob" styleId="dob" value="${getStackHolderList[0].dob}" styleClass="text" onchange="checkDate('dob');checkDate1();" /></td>
        <td><bean:message key="stake.exp" /></td>
        <td><html:text maxlength="3" style="text-align: right" property="totalExp" styleId="totalExp" value="${getStackHolderList[0].totalExp}" styleClass="text" onkeypress="return isNumberKey(event);"/></td>
        </tr>
        <!-- Nishant space starts -->
        <tr>
        	<td><bean:message key="pan" /><font color="red">*</font></td>
			<td><html:text property="mgmtPAN" maxlength="10" styleClass="text" styleId="mgmtPAN" value="${getStackHolderList[0].mgmtPAN}" onkeypress="return isAlphNumericKey(event);" onchange="return upperMe('mgmtPAN');"/></td>
        </tr>
        <tr>
        	<td><bean:message key="lbl.addId1" /></td>
			<td><html:text property="addId1" maxlength="20" styleClass="text" styleId="addId1" value="${getStackHolderList[0].addId1}" /></td>
        
        	<td><bean:message key="lbl.addId2" /></td>
			<td><html:text property="addId2" maxlength="20" styleClass="text" styleId="addId2" value="${getStackHolderList[0].addId2}" /></td>
        </tr>
        <!-- Nishant space ends -->
      <tr>
        <td><bean:message key="stake.din" /></td>
        <td><html:text maxlength="25" property="dinNo" styleId="dinNo" value="${getStackHolderList[0].dinNo}" styleClass="text" /></td>
		<td><bean:message key="stake.id" /></td>
		<td><html:text property="idNo" styleId="idNo" value="${getStackHolderList[0].idNo}" styleClass="text" maxlength="25" onkeypress="return isAlphNumericKey(event);"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.prime" /><font color="red">*</font></td>
        <td><html:text property="primaryPhone" style="text-align: right" styleId="primaryPhone" value="${getStackHolderList[0].primaryPhone}" styleClass="text" maxlength="10" onkeypress="return isNumberKey(event);"/></td>
        <td><bean:message key="stake.alt" /></td>
        <td><html:text property="alternatePhone" style="text-align: right" styleId="alternatePhone" value="${getStackHolderList[0].alternatePhone}" maxlength="20" styleClass="text" onkeypress="return isNumberKey(event);"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.pemail" /></td>
        <td><html:text property="primaryEmail" styleId="primaryEmail" value="${getStackHolderList[0].primaryEmail}" maxlength="100" styleClass="text"/></td>
        <td><bean:message key="stake.aemail" /></td>
        <td><html:text property="alternateEmail" maxlength="100" styleId="alternateEmail" value="${getStackHolderList[0].alternateEmail}" styleClass="text"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.web" /></td>
        <td><html:text  maxlength="25" property="website" styleId="website" value="${getStackHolderList[0].website}" styleClass="text" /></td>
        <td><bean:message key="stake.pos" /></td>
        <td><html:select property="position" styleId="position" value="${getStackHolderList[0].position}" styleClass="text">
            <html:option value=""><bean:message key="select" /></html:option>
            <logic:present name="positions">
            <logic:notEmpty name="positions">
            <html:optionsCollection name="positions" label="position_name" value="position_code" />
            </logic:notEmpty>
            </logic:present>
          </html:select>   </td>
     </tr>
     <tr>
       <td><bean:message key="stake.joining" /></td>
       <td><html:text property="doj" styleId="doj" value="${getStackHolderList[0].doj}" styleClass="text" onchange="checkDate('doj');checkDate1();" /></td>
       <td><bean:message key="stake.eligble" /></td>
       <td>
       		 <logic:present name="getStackHolderList">	
         		<logic:iterate name="getStackHolderList" id="getStackHolderList">
          			<logic:equal name="getStackHolderList" property="compute" value="N">
          			<html:checkbox property="compute" styleId="compute" />
          			</logic:equal>
          			<logic:equal name="getStackHolderList" property="compute" value="Y">
          			<input type="checkbox" name="compute" id="compute" checked="checked"/>
          			</logic:equal>
          		</logic:iterate>
          		
          	</logic:present>
          	<logic:notPresent name="getStackHolderList">
         		<html:checkbox property="compute" styleId="compute"/>
         	</logic:notPresent>
          		
      </td>
      </tr>
      <tr>
        <td>
        <logic:notPresent name="getStackHolderList">
    
         <button type="button" name="button1" class="topformbutton2" id="save" onclick="return saveStakeDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
         <button type="button" name="button" class="topformbutton2" onclick="return stakeHolderClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
     
        
         </logic:notPresent>
         <logic:present name="getStackHolderList">
         <button type="button" name="button1" class="topformbutton2" id="save" onclick=" return updateStakeDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
         </logic:present>
        
		</td>
        </tr>
        </table></td>
</tr>
</table>
</fieldset>

<fieldset>	
		 <legend><bean:message key="lbl.mgmtdetail" /></legend>  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
    <td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="stake.select" /></b></td> 
        <td><b><bean:message key="stake.pos" /></b></td>
		<td ><b><bean:message key="stake.name" /></b></td>
		<td ><b><bean:message key="stake.perc" /></b></td>
        <td><b><bean:message key="stake.joining" /></b></td>

		
	</tr>
				
	                   <logic:present name="briefStake">
	                   
						<logic:iterate id="stakeDetails" name="briefStake">
  
							<tr class="white1"> <td ><input type="checkbox" name="chk" id="chk" value="${stakeDetails.holderid }"/></td>
							<td >${stakeDetails.position }</td>
							<td ><a href="#" id="anchor0" onclick="return modifyStackHolerDetails(${stakeDetails.holderid });">${stakeDetails.holderName }</a></td>
							<td >${stakeDetails.holdingPerc }</td>
							<td >${stakeDetails.doj }</td>
							
							</tr>
						</logic:iterate>
 					</logic:present>
 					<logic:notPresent name="briefStake">
 				
						<logic:iterate id="subBriefStake" name="briefStake">
							<tr class="white1"> <td ><input type="checkbox" name="chk" id="chk" value="${subBriefStake.holderid }"/></td>
							<td >${subBriefStake.position }</td>
							<td ><a href="#" id="anchor0" onclick="return modifyStackHolerDetails(${subBriefStake.holderid });">${subBriefStake.holderName }</a></td>
							<td >${subBriefStake.holdingPerc }</td>
							<td >${subBriefStake.doj }</td>
							
							
							</tr>
						</logic:iterate>
 					</logic:notPresent>
 		
</table>

</td></tr></table>
<button type="button" name="button1" class="topformbutton2" onclick="deleteStake();" accesskey="D" title="Alt+D"><bean:message key="button.delete" /></button>
</fieldset>

</logic:present>

	<logic:notPresent name="update">
	<fieldset>	  
	<legend><bean:message key="lbl.mgmtdetail" /></legend> 
    <center><font color="#FF0000">${requestScope.status}</font></center>
    <input type="hidden" name="currStackHolerId" value="${getStackHolderList[0].holderid}"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td><table width="87%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td><bean:message key="stake.salut" /> <font color="red">*</font></td>
        <td><html:select property="sex" styleId="sex" styleClass="text" value="${getStackHolderList[0].sex}">
           <html:option value=""><bean:message key="select" /></html:option>
        <logic:present name="salutation">

        <html:optionsCollection name="salutation" label="SALUTATION_ID" value="SALUTATION_DESC" />

        </logic:present>  
        </html:select></td>
        <td> <bean:message key="stake.name" /><font color="red">*</font></td>
        <td ><html:text maxlength="50" property="holderName" styleId="holderName" value="${getStackHolderList[0].holderName}" styleClass="text" onchange="return upperMe('holderName');"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.type" /><font color="red">*</font></td>
          <td><html:select property="holderType" styleId="holderType" value="${getStackHolderList[0].holderType}" styleClass="text" onchange="return setStackPerc(this.value);">
            <html:option value=""><bean:message key="select" /></html:option>
            <logic:present name="holdertypes">
             <html:optionsCollection name="holdertypes" label="STAKEHOLDER_DESC" value="STAKEHOLDER_ID" />
            </logic:present>        
            </html:select></td>
              
		<td><bean:message key="stake.perc" /></td>
		<td><html:text maxlength="3" style="text-align: right" property="holdingPerc" styleId="holdingPerc" value="${getStackHolderList[0].holdingPerc}" styleClass="text" onkeypress="return isNumberKey(event);"/></td>
      </tr>      
      <tr>
        <td><bean:message key="stake.birth" /><font color="red">*</font></td>
        <td ><html:text property="dob" styleId="dob" value="${getStackHolderList[0].dob}" styleClass="text" onchange="checkDate('dob');checkDate1();" /></td>
        <td><bean:message key="stake.exp" /></td>
        <td><html:text property="totalExp" style="text-align: right" maxlength="3" styleId="totalExp" value="${getStackHolderList[0].totalExp}" styleClass="text" onkeypress="return isNumberKey(event);"/></td>
      </tr>
      <!-- Nishant space starts -->
        <tr>
        	<td><bean:message key="pan" /><font color="red">*</font></td>
			<td><html:text property="mgmtPAN" maxlength="10" styleClass="text" styleId="mgmtPAN" value="${getStackHolderList[0].mgmtPAN}" onkeypress="return isAlphNumericKey(event);" onchange="return upperMe('mgmtPAN');"/></td>
        </tr>
        <tr>
        	<td><bean:message key="lbl.addId1" /></td>
			<td><html:text property="addId1" maxlength="20" styleClass="text" styleId="addId1" value="${getStackHolderList[0].addId1}" /></td>
        
        	<td><bean:message key="lbl.addId2" /></td>
			<td><html:text property="addId2" maxlength="20" styleClass="text" styleId="addId2" value="${getStackHolderList[0].addId2}" /></td>
        </tr>
        <!-- Nishant space ends -->
      <tr>
        <td><bean:message key="stake.din" /></td>
        <td><html:text maxlength="25" property="dinNo" styleId="dinNo" value="${getStackHolderList[0].dinNo}" styleClass="text"  /></td>
		<td><bean:message key="stake.id" /></td>
		<td><html:text property="idNo" styleId="idNo" value="${getStackHolderList[0].idNo}" styleClass="text" maxlength="25" onkeypress="return isAlphNumericKey(event);"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.prime" /><font color="red">*</font></td>
        <td><html:text property="primaryPhone" style="text-align: right" styleId="primaryPhone" value="${getStackHolderList[0].primaryPhone}" styleClass="text" maxlength="10" onkeypress="return isNumberKey(event);"/></td>
        <td><bean:message key="stake.alt" /></td>
        <td><html:text property="alternatePhone" style="text-align: right" styleId="alternatePhone" value="${getStackHolderList[0].alternatePhone}" maxlength="20" styleClass="text" onkeypress="return isNumberKey(event);"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.pemail" /></td>
        <td><html:text property="primaryEmail" styleId="primaryEmail" value="${getStackHolderList[0].primaryEmail}" maxlength="100"  styleClass="text"/></td>
        <td><bean:message key="stake.aemail" /></td>
        <td><html:text property="alternateEmail" styleId="alternateEmail" value="${getStackHolderList[0].alternateEmail}" styleClass="text" maxlength="100"  /></td>
      </tr>
      <tr>
        <td><bean:message key="stake.web" /></td>
        <td><html:text maxlength="25" property="website" styleId="website" value="${getStackHolderList[0].website}" styleClass="text" /></td>
        <td><bean:message key="stake.pos" /></td>
        <td><html:select property="position" styleId="position" value="${getStackHolderList[0].position}" styleClass="text">
            <html:option value=""><bean:message key="select" /></html:option>
            <logic:present name="positions">
             <html:optionsCollection name="positions" label="position_name" value="position_code" />
            </logic:present>
          </html:select>   </td>
     </tr>
     <tr>
       <td><bean:message key="stake.joining" /></td>
       <td><html:text property="doj" styleId="doj" value="${getStackHolderList[0].doj}" styleClass="text" onchange="checkDate('doj');checkDate1();"/></td>
       <td><bean:message key="stake.eligble" /></td>
       <td>

         	 <logic:notPresent name="getStackHolderList">
         	 	<html:checkbox property="compute" styleId="compute"/>
         	 </logic:notPresent>
       </td>
      </tr>
      <tr>
 <td><span class="white">
 <logic:present name="getStackHolderList">
       
         <button name="button1" type="button" class="topformbutton2" id="save" onclick="return updateStakeDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
         </logic:present>
  <logic:notPresent name="getStackHolderList">

         <button name="button1" type="button" class="topformbutton2" id="save" onclick="return saveStakeDetail();" accesskey="V" title="Alt+V"><bean:message key="button.save" /></button>
 
         </logic:notPresent>
         
 <button name="button" class="topformbutton2" type="button"
  onclick="return stakeHolderClear();" title="Alt+C" accesskey="C"><bean:message key="button.clear" /></button>
 



</span></td>
 </tr>
 
 
 
 </table></td>
</tr>
</table>
</fieldset>
<fieldset>	
		 <legend><bean:message key="lbl.mgmtdetail" /></legend>  

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
        <td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="stake.select" /></b></td> 
        <td  ><b><bean:message key="stake.pos" /></b></td>
		<td ><b><bean:message key="stake.name" /></b></td>
    
        <td ><b><bean:message key="stake.perc" /></b></td>
		    <td ><b><bean:message key="stake.joining" /></b></td>
	</tr>
	 
	                       <logic:present name="briefStake">
							<logic:iterate id="subbriefStake" name="briefStake">

    <tr class="white1"> <td ><input type="checkbox" name="chk" id="chk" value="${subbriefStake.holderid }"/></td>

								<td >${subbriefStake.position }</td>
								<td><a href="#" id="anchor0" onclick="return modifyStackHolerDetails(${subbriefStake.holderid });">${subbriefStake.holderName }</a></td>
										<td >${subbriefStake.holdingPerc }</td>
								<td>${subbriefStake.doj }</td>
						
						
	</tr>
						</logic:iterate>
 						</logic:present>


</table>




</td></tr></table>

 			<button name="button1" type="button"  class="topformbutton2" onclick="deleteStake();" accesskey="D" title="Alt+D"><bean:message key="button.delete" /></button>

</fieldset>
 </logic:notPresent>

  </html:form>
</div>
</div>
</logic:notPresent>

<logic:present name="approve">

<div id="centercolumn">
<div id="revisedcontainer">

<html:form action="/corporatStackHolderPageAction" styleId="StakeForm" method="post">
<fieldset>	 

	<legend><bean:message key="lbl.mgmtdetail" /></legend> 
	<logic:present name="corporateId">
    
    <html:hidden property="corporateCode" styleId="corporateCode" styleClass="text" value="${sessionScope.corporateId}" />
    </logic:present>
    <center><font color="#FF0000">${requestScope.status}</font></center>
	<input type="hidden" name="currStackHolerId" value="${getStackHolderList[0].holderid}"/>   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="87%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td><bean:message key="stake.salut" /> <font color="red">*</font></td>
        <td><html:select property="sex" styleId="sex" styleClass="text" value="${getStackHolderList[0].sex}" disabled="true" tabindex="-1">
            <html:option value=""><bean:message key="select" /></html:option>
        <logic:present name="salutation">

        <html:optionsCollection name="salutation" label="SALUTATION_ID" value="SALUTATION_DESC" />

        </logic:present>  
        </html:select></td>
        <td> <bean:message key="stake.name" /><font color="red">*</font></td>
        <td ><html:text maxlength="50" property="holderName" styleId="holderName" value="${getStackHolderList[0].holderName}" styleClass="text" disabled="true" tabindex="-1"/></td>
      </tr>
      <tr>
      
        <td><bean:message key="stake.type" /><font color="red">*</font></td>
          <td><html:select property="holderType" styleId="holderType" value="${getStackHolderList[0].holderType}" styleClass="text" disabled="true" tabindex="-1">
            <html:option value=""><bean:message key="select" /></html:option>
            <logic:present name="holdertypes">
             <html:optionsCollection name="holdertypes" label="STAKEHOLDER_DESC" value="STAKEHOLDER_ID" />
            </logic:present>        
            </html:select></td>
            
		<td><bean:message key="stake.perc" /></td>
		<td><html:text maxlength="3" property="holdingPerc" styleId="holdingPerc" value="${getStackHolderList[0].holdingPerc}" styleClass="text" onkeypress="return isNumberKey(event);" disabled="true"/></td>
      </tr>      
      <tr>
        <td><bean:message key="stake.birth" /><font color="red">*</font></td>
        <td ><html:text property="dob" styleId="dob1" value="${getStackHolderList[0].dob}" styleClass="text" disabled="true" onchange="checkDate('dob');checkDate1();" tabindex="-1"/></td>
        <td><bean:message key="stake.exp" /></td>
        <td><html:text maxlength="3"  property="totalExp" styleId="totalExp" value="${getStackHolderList[0].totalExp}" styleClass="text" onkeypress="return isNumberKey(event);" disabled="true" tabindex="-1"/></td>
      </tr>
      <!-- Nishant space starts -->
        <tr>
        	<td><bean:message key="pan" /><font color="red">*</font></td>
			<td><html:text property="mgmtPAN" maxlength="10" styleClass="text" styleId="mgmtPAN" value="${getStackHolderList[0].mgmtPAN}" disabled="true" onkeypress="return isAlphNumericKey(event);" onchange="return upperMe('mgmtPAN');"/></td>
        </tr>
        <tr>
        	<td><bean:message key="lbl.addId1" /></td>
			<td><html:text property="addId1" maxlength="20" styleClass="text" styleId="addId1" value="${getStackHolderList[0].addId1}" disabled="true"/></td>
        
        	<td><bean:message key="lbl.addId2" /></td>
			<td><html:text property="addId2" maxlength="20" styleClass="text" styleId="addId2" value="${getStackHolderList[0].addId2}" disabled="true"/></td>
        </tr>
      <!-- Nishant space ends -->
      <tr>
        <td><bean:message key="stake.din" /></td>
        <td><html:text property="dinNo" maxlength="25" styleId="dinNo" value="${getStackHolderList[0].dinNo}" styleClass="text"  disabled="true" tabindex="-1"/></td>
		<td><bean:message key="stake.id" /></td>
		<td><html:text property="idNo" styleId="idNo" value="${getStackHolderList[0].idNo}" styleClass="text" maxlength="25" onkeypress="return isAlphNumericKey(event);" disabled="true" tabindex="-1"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.prime" /><font color="red">*</font></td>
        <td><html:text property="primaryPhone" styleId="primaryPhone" value="${getStackHolderList[0].primaryPhone}" styleClass="text" maxlength="10" onkeypress="return isNumberKey(event);" disabled="true" tabindex="-1"/></td>
        <td><bean:message key="stake.alt" /></td>
        <td><html:text property="alternatePhone" styleId="alternatePhone" value="${getStackHolderList[0].alternatePhone}" maxlength="20" styleClass="text" onkeypress="return isNumberKey(event);" disabled="true" tabindex="-1"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.pemail" /></td>
        <td><html:text maxlength="100" property="primaryEmail" styleId="primaryEmail" value="${getStackHolderList[0].primaryEmail}" styleClass="text" disabled="true" tabindex="-1"/></td>
        <td><bean:message key="stake.aemail" /></td>
        <td><html:text property="alternateEmail" styleId="alternateEmail" value="${getStackHolderList[0].alternateEmail}" styleClass="text" disabled="true" maxlength="100" tabindex="-1"/></td>
      </tr>
      <tr>
        <td><bean:message key="stake.web" /></td>
        <td><html:text maxlength="25" property="website" styleId="website" value="${getStackHolderList[0].website}" styleClass="text" disabled="true" tabindex="-1"/></td>
        <td><bean:message key="stake.pos" /></td>
        <td><html:select property="position" styleId="position" value="${getStackHolderList[0].position}" styleClass="text" disabled="true" tabindex="-1">
            <html:option value=""><bean:message key="select" /></html:option>
            <logic:present name="positions">
             <html:optionsCollection name="positions" label="position_name" value="position_code" />
            </logic:present>
          </html:select>   </td>
     </tr>
     <tr>
       <td><bean:message key="stake.joining" /></td>
       <td>
   
       <html:text property="doj" styleId="doj1"  value="${getStackHolderList[0].doj}" styleClass="text" onchange="return checkDate1(doj);" disabled="true" tabindex="-1"/></td>
     
       <td><bean:message key="stake.eligble" /></td>
       <td>
       		 <logic:present name="getStackHolderList">	
         		<logic:iterate name="getStackHolderList" id="getStackHolderList">
          			<logic:equal name="getStackHolderList" property="compute" value="N" >
          			  	<html:checkbox property="compute" styleId="compute" disabled="true"/>
          			</logic:equal>
          			<logic:equal name="getStackHolderList" property="compute" value="Y">
          			  	<input type="checkbox" name="compute" id="compute" checked="checked" disabled="disabled"/>
          			</logic:equal>
          		</logic:iterate>
          		
          	</logic:present>
          	<logic:notPresent name="getStackHolderList">
         		<html:checkbox property="compute" styleId="compute" value="N" disabled="true"/>
         	</logic:notPresent>
          		
      </td>
      </tr>
      
 </table></td>
</tr>
</table>
 
    
</fieldset>
<div class="">
    <span class="">&nbsp;</span>
	 
  <br/>  
<fieldset >	
		 <legend><bean:message key="lbl.mgmtdetail" /></legend>  

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr >
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
        <td ><b> 
         <logic:notPresent name="underWriter">
        <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/>  <bean:message key="stake.select" />
        </logic:notPresent>
         <logic:present name="underWriter">
         <input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/>  <bean:message key="stake.select" />
         </logic:present>
        </b></td> 
        <td ><b><bean:message key="stake.pos" /></b></td>
		<td ><b><bean:message key="stake.name" /></b></td>      
        <td><b><bean:message key="stake.perc" /></b></td>
		  <td><b><bean:message key="stake.joining" /></b></td>
	</tr>

	                       <logic:present name="briefStake">
	                       
							<logic:iterate id="substakeDetails" name="briefStake">


    <tr class="white1"> <td >
    <logic:notPresent name="underWriter">
    <input type="checkbox" name="chk" id="chk" value="${substakeDetails.holderid }" disabled="disabled"/>
    </logic:notPresent>
    <logic:present name="underWriter">
    <input type="checkbox" name="chk" id="chk" value="${substakeDetails.holderid }" disabled="disabled"/>
    </logic:present>
    
    </td>

								<td >${substakeDetails.position }</td>
								<td><a href="#" id="anchor0" onclick="return modifyStackHolerDetails(${substakeDetails.holderid });">${substakeDetails.holderName }</a></td>
						
								<td >${substakeDetails.holdingPerc }</td>
										<td >${substakeDetails.doj }</td>
	</tr>
</logic:iterate>
 </logic:present>


</table>
</td></tr></table>
<logic:notPresent name="approve">
 <logic:notPresent name="underWriter">
		<button name="button1" class="topformbutton2" type="button"  onclick="deleteStake();" accesskey="D" title="Alt+D"><bean:message key="button.delete" /></button>
</logic:notPresent>
</logic:notPresent>
	</fieldset>
<br/> 
	</div>   
</html:form>
</div>
</div>
</logic:present>

<!-- 
<logic:present name="procval">
	<script type="text/javascript">
	if(('<%=request.getAttribute("procval")%>'!= '') || ('<%=request.getAttribute("procval")%>'!='S'))
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
		
	}
	</script>
</logic:present>
 -->
<logic:present name="sms">

 <script type="text/javascript">
 
  //  alert("check 1 .....");	
  
   if('<%=request.getAttribute("sms").toString()%>' == 'both')
	{
		alert('<bean:message key="lbl.dinAndIdNoExist" />');
		document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	    document.getElementById('StakeForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'dinno')
	{
		alert('<bean:message key="lbl.dinexist" />');
		document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	    document.getElementById('StakeForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'panNo')
	{
		alert('<bean:message key="msg.panNumber" />');
		document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	    document.getElementById('StakeForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'idno')
	{
		alert('<bean:message key="lbl.identificationexist" />');
		document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	    document.getElementById('StakeForm').submit();
		
	}
 if('<%=request.getAttribute("sms").toString()%>' == 'S')
	{
		alert('<bean:message key="lbl.corporateStageHolder" />');
		document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	    document.getElementById('StakeForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'U')
	{
		alert('<bean:message key="lbl.corporateStakeHolderUpdated" />');
		document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	    document.getElementById('StakeForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'Doj')
	{
		alert('<bean:message key="lbl.dojLessThanDoi" />');
		document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	    document.getElementById('StakeForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>' == 'E'){
		alert('<bean:message key="lbl.errorSuccess" />');
	}
	else{
	document.getElementById('StakeForm').action="<%=request.getContextPath() %>/corporateStackHolderAction.do";
	}
	</script>
</logic:present>
<script>
	setFramevalues("StakeForm");
</script>
</body>
</html:html>