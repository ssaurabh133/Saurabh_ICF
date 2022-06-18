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
<link type="text/css" href="development-bundle/demos/demos.css" rel="stylesheet" />
<link type="text/css" href="development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />

		<script type="text/javascript" src="<%=path%>/js/cmScript/lovCommonScript.js"></script>
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	


		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fleetDetails.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
			<script type="text/javascript">
 	
		
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
<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="showHideLovOnLoad();enableAnchor();checkChanges('fleetDetailForm');document.getElementById('fleetDetailForm').financialInst.focus();" onclick="parent_disable();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
		<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Fund Flow Analysis</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	
	<html:form action="/saveFleetProcessAction"  styleId="fleetDetailForm">
	<input type="hidden" id="contextPath" value="<%=path %>" />	

	
 <logic:notPresent name="fleetAuthor">
 
  <html:hidden  styleClass="text" property="dealFleetId" styleId="dealFleetId"  value="${allFleetList[0].dealFleetId}" />
  
	<fieldset>
	
		  <legend><bean:message key="lbl.fleetDetail"/></legend>         
	   
	
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<!-- Nishant space starts -->
		<tr>
			<td width="20%"><bean:message key="lbl.financialInst"/><font color="red">*</font></td>
		    <td width="35%">
		       <html:select property="financialInst" styleId="financialInst" styleClass="text" onchange="getFinancialInstName();showHideLOV();" value="${allFleetList[0].financialInst}" >
	          	  	<html:option value="">---Select----</html:option>
	             		<logic:present name="financialInstList">
	               			<html:optionsCollection name="financialInstList" label="name" value="id" /> 
	             		</logic:present>
          	  </html:select>
        	</td>
        	<td><bean:message key="lbl.financialInstName"/></td>
			<td>
				<html:text  styleClass="text" property="financialInstName" styleId="financialInstName" maxlength="50"  value="${allFleetList[0].financialInstName}" />
			</td>
		</tr>
		<tr>
			<td width="20%"><bean:message key="lbl.loanAccountNo"/></td>
			<td width="35%">
				<html:text  styleClass="text" property="loanNo" styleId="loanNo" maxlength="50"  value="${allFleetList[0].loanNo}" />
				<input type="button" class="lovbutton" id="loanNoButton" 	onclick="openLOVCommon(573,'fleetDetailForm','loanNo','','','','','getFleetData','custName','appType','assetDesc');" value=" " tabindex="1" name="dealButton" />
				<html:hidden property="lbxLoanNoHid" styleId="lbxLoanNoHid" value="${allFleetList[0].lbxLoanNoHid}"/>
				<input type="hidden" id="assetDesc" name="assetDesc" value="" />
				<input type="hidden" id="custName" name="custName" value="" />
				<input type="hidden" id="appType" name="appType" value="" />
			</td>
			<td><bean:message key="lbl.seasoning"/><font color="red">*</font></td>
			<td>
				<html:text  styleClass="text" property="seasoning" styleId="seasoning"  maxlength="50" style="text-align: right" onkeypress="return isNumberKey(event);" value="${allFleetList[0].seasoning}" />
			</td>
		</tr>
		<tr>
			<td width="20%"><bean:message key="lbl.hpnStatus"/></td>
		    <td width="35%">
		       <html:select property="hpnStatus" styleId="hpnStatus" styleClass="text"  value="${allFleetList[0].hpnStatus}" >
	          	  	<html:option value="Y" >Yes</html:option>
	          	  	<html:option value="N" >No</html:option>
          	  </html:select>
        	</td>
        	<td><bean:message key="lbl.currentPos"/></td>
			<td>
				<html:text  styleClass="text" property="currentPos" styleId="currentPos" maxlength="50" style="text-align: right" onkeypress="return isNumberKey(event);" value="${allFleetList[0].currentPos}" />
			</td>
		</tr>
		<!-- Nishant space ends -->
		<tr>
					
		   <td width="20%"><bean:message key="lbl.vehicleOwner"/> <font color="red">*</font></td>
			 <td width="35%">
			 <html:text  styleClass="text" property="vehicleOwner" styleId="vehicleOwner" maxlength="50"  value="${allFleetList[0].vehicleOwner}" /></td>
	         <td><bean:message key="lbl.relationshipS"/></td>
	         <td><html:text  styleClass="text" property="relationship" styleId="relationship" maxlength="50"  value="${allFleetList[0].relationship}"/></td>
		</tr>
	  <tr>
	    <td><bean:message key="lbl.vehicleNo"/><font color="red">*</font> </td>
	    <td>
	    	<html:text  styleClass="text" property="vehicleNo" styleId="vehicleNo" maxlength="25" value="${allFleetList[0].vehicleNo}"/>
	    </td>
		<td><bean:message key="lbl.vehicleModel"/><font color="red">*</font></td>
		    <td >
		       <html:text  styleClass="text" property="vehicleModel" styleId="vehicleModel"  maxlength="50" value="${allFleetList[0].vehicleModel}"/> 
		    </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.mfgYear"/>(YYYY)</td>
		   <td  ><html:text  styleClass="text" property="mfgYear" styleId="mfgYear" onblur="checkFormateYear(this.value,this.id)" maxlength="7" value="${allFleetList[0].mfgYear}"/>  </td>
		   <td><bean:message key="lbl.docCollected"/></td>
		    <td  >
		       <html:select property="docCollected" styleId="docCollected" styleClass="text"  value="${allFleetList[0].docCollected}" >
	          	  	<html:option value="RC" >RC</html:option>
	          	  	<html:option value="RC & TRACK" >RC & TRACK</html:option>
	          	  	<html:option value="TRACK" >TRACK</html:option>
                    <html:option value="NC" >Not Taken</html:option>
          	  </html:select>
        	        	
        	</td>
		  
		 </tr>
		 <tr>
			  <td>
			    <button type="button" name="saveButton" class="topformbutton2" title="Alt+V" accesskey="V" id="saveButton" onclick="return saveFleetDetails('fleetDetailForm');" ><bean:message key="button.save" /></button>	    
			    <button type="button" name="fleetButton" id="fleetButton" title="Alt+F" accesskey="F" class="topformbutton2" onclick="return openFleetPopUp();"><bean:message key="button.fleetDetails"/></button>
		      </td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	
	</logic:notPresent>
	 
	 <logic:present name="fleetAuthor">
	 <fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>         
	 		      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<!-- Nishant space starts -->
		<tr>
			<td width="20%"><bean:message key="lbl.financialInst"/><font color="red">*</font></td>
		    <td width="35%">
		       <html:select property="financialInst" styleId="financialInst" styleClass="text" disabled="true" onchange="getFinancialInstName();showHideLOV();" value="${allFleetList[0].financialInst}" >
	          	  	<html:option value="">---Select----</html:option>
	             		<logic:present name="financialInstList">
	               			<html:optionsCollection name="financialInstList" label="name" value="id" /> 
	             		</logic:present>
          	  </html:select>
        	</td>
        	<td><bean:message key="lbl.financialInstName"/></td>
			<td>
				<html:text  styleClass="text" property="financialInstName" styleId="financialInstName" maxlength="50" readonly="true"  value="${allFleetList[0].financialInstName}" />
			</td>
		</tr>
		<tr>
			<td width="20%"><bean:message key="lbl.loanAccountNo"/></td>
			<td width="35%">
				<html:text  styleClass="text" property="loanNo" styleId="loanNo" maxlength="50" readonly="true"  value="${allFleetList[0].loanNo}" />
				<html:hidden property="lbxLoanNoHid" styleId="lbxLoanNoHid" value="${allFleetList[0].lbxLoanNoHid}"/>
				<input type="hidden" id="assetDesc" name="assetDesc" value="" />
				<input type="hidden" id="custName" name="custName" value="" />
				<input type="hidden" id="appType" name="appType" value="" />
			</td>
			<td><bean:message key="lbl.seasoning"/><font color="red">*</font></td>
			<td>
				<html:text  styleClass="text" property="seasoning" styleId="seasoning" readonly="true" maxlength="50"  value="${allFleetList[0].seasoning}" />
			</td>
		</tr>
		<tr>
			<td width="20%"><bean:message key="lbl.hpnStatus"/></td>
		    <td width="35%">
		       <html:select property="hpnStatus" styleId="hpnStatus" styleClass="text" disabled="true" value="${allFleetList[0].hpnStatus}" >
	          	  	<html:option value="Y" >Yes</html:option>
	          	  	<html:option value="N" >No</html:option>
          	  </html:select>
        	</td>
        	<td><bean:message key="lbl.currentPos"/></td>
			<td>
				<html:text  styleClass="text" property="currentPos" styleId="currentPos" maxlength="50" readonly="true" value="${allFleetList[0].currentPos}" />
			</td>
		</tr>
		<!-- Nishant space ends -->
		<tr>
					
		   <td width="20%"><bean:message key="lbl.vehicleOwner"/><font color="red">*</font></td>
			 <td width="35%">
			 <html:text  styleClass="text" property="vehicleOwner" styleId="vehicleOwner" maxlength="50"  value="${allFleetList[0].vehicleOwner}" readonly="true"/></td>
	         <td><bean:message key="lbl.relationshipS"/> </td>
	         <td><html:text  styleClass="text" property="relationship" styleId="relationship" maxlength="50"  value="${allFleetList[0].relationship}" readonly="true"/></td>
		</tr>
	  <tr>
	    <td><bean:message key="lbl.vehicleNo"/><font color="red">*</font> </td>
	    <td>
	    	<html:text  styleClass="text" property="vehicleNo" styleId="vehicleNo" maxlength="25" value="${allFleetList[0].vehicleNo}" readonly="true"/>
	    </td>
		<td><bean:message key="lbl.vehicleModel"/><font color="red">*</font></td>
		    <td >
		       <html:text  styleClass="text" property="vehicleModel" styleId="vehicleModel"  maxlength="50" value="${allFleetList[0].vehicleModel}" readonly="true"/> 
		    </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.mfgYear"/>(YYYY)</td>
		   <td  ><html:text  styleClass="text" property="mfgYear" styleId="mfgYearView" onchange="return checkDate('mfgYear');" maxlength="10" value="${allFleetList[0].mfgYear}" readonly="true"/>  </td>
		   <td><bean:message key="lbl.docCollected"/></td>
		    <td  >
		       <html:select property="docCollected" styleId="docCollected" styleClass="text"  value="${allFleetList[0].docCollected}" disabled="true">
	          	  	<html:option value="RC" >RC</html:option>
	          	  	<html:option value="RC & TRACK" >RC & TRACK</html:option>
	          	  	<html:option value="TRACK" >TRACK</html:option>
                    <html:option value="NC" >Not Taken</html:option>
          	  </html:select>
        	        	
        	</td>
		  
		 </tr>
		<tr>
			  <td>
			    <button type="button" name="fleetButton" id="fleetButton" title="Alt+F" accesskey="F" class="topformbutton2" onclick="return openFleetPopUp();"><bean:message key="button.fleetDetails"/></button>
		      </td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:present>
	  <br/>
	
	
<fieldset>	
<!--		 <legend><bean:message key="stakeObligation.details"/> </legend>  -->

  
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	<logic:present name="fleetAuthor">
    		<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="contact.select" /></b>
    	</logic:present>
    	 <logic:notPresent name="fleetAuthor">
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	 </logic:notPresent></td> 
        <td ><b><bean:message key="lbl.vehicleOwner"/></b> </td>
		<td ><strong><bean:message key="lbl.relationshipS"/> </strong></td>
        <td ><b><bean:message key="lbl.vehicleNo"/> </b></td>
        <td ><strong><bean:message key="lbl.vehicleModel"/></strong></td>
        <td ><b><bean:message key="lbl.mfgYear"/> </b></td>
        <td ><strong><bean:message key="lbl.docCollected"/></strong></td>
	</tr>
	
	
		<logic:present name="fleetList">
			<logic:iterate id="subfleetList" name="fleetList">
				<tr class="white1">
					<td >
					<logic:present name="fleetAuthor">
    					<input type="checkbox" name="chk" id="chk" value="${subfleetList.dealFleetId }" disabled="disabled"/>
    				</logic:present>
    	 			<logic:notPresent name="fleetAuthor">
    					<input type="checkbox" name="chk" id="chk" value="${subfleetList.dealFleetId }"/>
    	 			</logic:notPresent>
					
					</td>
					<td >
					
					<logic:present name="fleetAuthor">
    					<a href="#" id="anchor0" onclick="return getAllAuthorFleetDetails(${subfleetList.dealFleetId });">${subfleetList.vehicleOwner }</a>
    				</logic:present>
    	 			<logic:notPresent name="fleetAuthor">
    					<a href="#" id="anchor0" onclick="return getAllFleetDetails(${subfleetList.dealFleetId });">${subfleetList.vehicleOwner }</a>
    	 			</logic:notPresent>
					
					  
					
					</td>
					<td >${subfleetList.relationship }</td>
					<td>${subfleetList.vehicleNo }</td>
					<td>${subfleetList.vehicleModel }</td>
					<td >${subfleetList.mfgYear }</td>
					<td>${subfleetList.docCollected }</td>
				</tr>	
			</logic:iterate>
			
		</logic:present>	
	
	
 </table>    </td>
</tr>

</table></fieldset>
	<logic:notPresent name="fleetAuthor">
		<button type="button" name="deleteButton" id="deleteButton" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteFleetDetails();"><bean:message key="button.delete" /></button>
	</logic:notPresent>

	</html:form>
</div></div>
<logic:present name="msg">
 <script type="text/javascript">
	if('<%=request.getAttribute("msg")%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		
	}
	else 
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	}
	
	</script>
	</logic:present>
	
	<logic:present name="deleteStatus">
 <script type="text/javascript">
	if('<%=request.getAttribute("deleteStatus")%>'=='S')
	{
		alert("<bean:message key="lbl.datadeleteSucc" />");
		
	}
	else 
	{
		alert("<bean:message key="lbl.dataNtdeleteSucc" />");
	}
	
	</script>
	</logic:present>
	
	
	<script>
	setFramevalues("fleetDetailForm");
</script>

</body>
</html:html>
