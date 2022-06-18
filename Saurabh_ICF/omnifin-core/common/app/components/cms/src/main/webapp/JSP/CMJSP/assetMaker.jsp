<!-- 
Author Name :- MRADUL AGARWAL
Date of Creation :03-08-2013
Purpose :-  screen for the Asset Maker
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
	%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		  <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
		
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/asset.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
		  <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
	   <link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
	
		<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif] -->
	
	<script type="text/javascript">
  		
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}   
	</script>
	
	</head>
		<body onload="enableAnchor();">
	 	<div align="center" class="opacity" style="display: none;"  id="processingImage"></div>
		 <input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate%>" />
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<input type="hidden" name="productCategory" id="productCategory" value="<bean:message key="lbl.dateFormat"/>" />
		<html:hidden property="assetMakerListAuthorFlag" styleId="assetMakerListAuthorFlag" value="${requestScope.assetMakerListAuthorFlag}"/>
		<html:hidden property="assetInsuranceID" styleId="assetInsuranceID" value="${requestScope.assetInsuranceID}"/>
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
    </logic:present>
    <logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    </logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	
	<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form action="/assetMakerForSaveAction" method="post" styleId="assetMakerForm">
	
	 <html:javascript formName="AssetMakerDynaValidatorForm"/> 
     <html:errors/>	
	
	<fieldset>
	<legend><bean:message key="lbl.insuranceAssetMaker"></bean:message></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	<tr>
		<td>
			<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath" />
			<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
						
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 		<!-- --------------------------------------- Insurance Asset Maker for New Starts--------------------------------- -->
		
		<logic:notPresent name="assetMakerList">
		<logic:notPresent name="datatoapproveList">
		<html:hidden property="assetInsuranceID" styleId="assetInsuranceID" value="${requestScope.assetInsuranceID}"/>
			<tr>
		   <td><bean:message key="lbl.loanAccount"></bean:message> <font color="red">* </font></td>
		   <td>
		 	 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${assetLoanList[0].loanAccountNumber}${loanNo}" readonly="true" tabindex="-1"/>
	         <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  value="${assetLoanList[0].lbxLoanNoHID}${loanId}" />
	         <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="closeLovCallonLov1();openLOVCommon(2037,'assetMakerForm','loanAccountNumber','','', '','','getOldAssetsInGrid','customerName','productCategory')" value=" " styleClass="lovbutton"></html:button>
      	
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td>
	<input type="hidden" name="lbxAssetHID" id="lbxAssetHID" value="" />
	<html:text property="customerName"  styleClass="text" styleId="customerName" value="${assetLoanList[0].customerName}${cusName }"  maxlength="50" readonly="true" tabindex="-1"></html:text>
	</td>
		</tr>
	       <tr>
	         <td><bean:message key="lbl.veri.entityType"></bean:message><font color="red">*</font></td>
	        <td>
	        	 <input type="hidden" name="entityTypeDesc" id="entityTypeDesc" value=""/>
	        	<html:select property="entityType" styleId="entityType" tabindex='1' styleClass="text" onchange="getEntityType();changeEntity();">  
	        		<html:option value="">--Select--</html:option>    
	        		<html:option value="PRAPPL">APPLICANT</html:option>
          			<html:option value="COAPPL">CO-APPLICANT</html:option>
          			<html:option value="GUARANTOR">GUARANTOR</html:option>
          			<html:option value="ASSET">ASSET</html:option>
          			<html:option value="COLLATERAL">COLLATERAL</html:option>
            	</html:select>
            </td>
          <td><bean:message key="lbl.entity"></bean:message><font color="red">*</font></td>
	        <td>
	        <a rel="tooltip3" id="tool">
		      <html:text styleClass="text" property="entity" styleId="entity" maxlength="50"  onmouseover="applyToolTip(this.id);" value="" readonly="true" tabindex="-1"/>          
		    </a>
		    <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath" />
		      <html:hidden property="lbxEntity" styleId="lbxEntity"/>
            <input type="hidden" name="asstCostLov" id="asstCostLov" value="" />
            <input type="hidden" name="endDateLov" id="endDateLov" value="" />
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openAssetLOV()" value=" " styleClass="lovbutton"></html:button>
		  </td>       
	       </tr>	     
	       <tr>
	       <td><bean:message key="lbl.assetDesc"></bean:message></td>
	       <td>
	       <html:text property="assetDesc"  styleClass="text" styleId="assetDesc" value="" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetNature"></bean:message></td>
	       <td>
	       <html:text property="assetNature"  styleClass="text" styleId="assetNature" value=""  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.assetMake"></bean:message></td>
	       <td>
	       <html:text property="assetMake"  styleClass="text" styleId="assetMake" value="" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetModel"></bean:message></td>
	       <td>
	       <html:text property="assetModel"  styleClass="text" styleId="assetModel" value=""  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.vendorname"></bean:message></td>
	       <td>
	       <html:text property="dealerName"  styleClass="text" styleId="dealerName" value="" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.engineNumber"></bean:message></td>
	       <td>
	       <html:text property="engineNumber"  styleClass="text" styleId="engineNumber" value=""  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.chasisNumber"></bean:message></td>
	       <td>
	       <html:text property="chasisNumber"  styleClass="text" styleId="chasisNumber" value="" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.registrationNumber"></bean:message></td>
	       <td>
	       <html:text property="registrationNumber"  styleClass="text" styleId="registrationNumber" value=""  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>		  
	       <tr>
	       <td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	       <td>
	       <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.policyNo"></bean:message></td>
	       <td>
	       <html:text property="policyNo"  styleClass="text" styleId="policyNo" value="" onkeypress="return checkPolicyNo(event);" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>	
		    <td><bean:message key="lbl.insuranceAgency"></bean:message> <font color="red">* </font></td>
		   <td>
		    <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="50"  value="" readonly="true" tabindex="-1"/>
            <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency"/>
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>
		  </td>	   
	       <td><bean:message key ="lbl.insrDoneBy"></bean:message><font color="red">* </font></td>
		   <td><span style="float:left;">
		   <html:select property="insuranceDoneBy" styleId="insuranceDoneBy" styleClass="text">
	            <option value="">-- Select --</option>	
	                <logic:present name="insuranceDoneByList">		
					<html:optionsCollection name="insuranceDoneByList" label="doneByDesc" value="doneByCode" />
					</logic:present>				             
           </html:select>
		 </span></td>	   
	       </tr>
		  <tr>	 
		  <td><bean:message key="lbl.sumAssured"></bean:message><font color="red">*	</font></td>
	       <td>
	       <html:text property="sumAssured"  styleClass="text" styleId="sumAssured" value="0.0"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
	       </td>    
	       <td><bean:message key="lbl.premiumAmnt"></bean:message><font color="red">*	</font></td>
	       <td>
	       <html:text property="premiumAmnt"  styleClass="text" styleId="premiumAmnt" value="" style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
	       </td>
	      
		   </tr>
	       <tr>	
	        <td><bean:message key ="lbl.startDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td >
	       <html:text property="startDate"  styleClass="text" styleId="startDate" value="" maxlength="50" ></html:text>
	       </td>	   
	        <td><bean:message key ="lbl.endDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td>
	       <html:text property="endDate"  styleClass="text" styleId="endDate" value="" maxlength="50" ></html:text>
	       </td>
	         
	      </tr>	      
	       <tr>	  
	       <td><bean:message key ="lbl.yearNo"></bean:message><font color="red">*</font></td>
	       <td>
	       <logic:present name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="${requestScope.YearNo[0].yearDesc}" />
	        </logic:present>
	        <logic:notPresent name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="0" />
	        </logic:notPresent>
	        
	       <html:select styleId="yearNo" property="yearNo" styleClass="text" value="" onfocus="keyUpNumber(this.value, event, 4,id);">
		         <html:option value="">-Select-</html:option>
		         <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
	        </html:select>
	       </td>   
	       <td><bean:message key="lbl.surrenderValue"/></td>
			 <td>
	       <html:text property="surrenderValue"  styleClass="text" styleId="surrenderValue" value="" maxlength="20" style="text-align: right"  onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
	       </td>		 	
		 	</tr>
	        <tr>
	         <td ><bean:message key="lbl.premiumFrequency"/></td>
           		<td>
           		<html:select property="premiumFrequency" styleId="premiumFrequency" styleClass="text" value="">
	              <html:option value="">--Select--</html:option> 
	             <html:option value="MONTHLY">MONTHLY</html:option>
	             <html:option value="QUARTERLY">QUARTERLY</html:option>
	             <html:option value="HALFYEARLY">HALF YEARLY</html:option>      
	             <html:option value="ANNUALY">ANNUALY</html:option>        
	           </html:select></td>	         
	       <td ><bean:message key="lbl.nominee"/></td>
 		   <td ><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value=""  /></td>
		   		   
	        </tr>
	        <tr>   
	         <td><bean:message key="lbl.relWithNominee"/></td>
		   <td nowrap="nowrap"><html:select styleClass="text"  styleId="relWithNominee" value="" property="relWithNominee">
                  <html:option value="">Select</html:option>  
       			    <html:optionsCollection name="showInsuranceRelWithNominee" label="name" value="id" />
				
                  </html:select>
                  </td> 
	       
	       <td><bean:message key="lbl.adviseFlag" /></td>
	       <td> <html:select styleId="adviseFlag" property="adviseFlag" styleClass="text">
		         <html:option value="">-Select-</html:option>
		         <html:option value="Y">YES</html:option>
		         <html:option value="N">NO</html:option>
	         </html:select>
		  </td>		 
	        </tr>	      
	       <tr>	 	
	         <td><bean:message key="lbl.remark" /><!--  <font color="red">*</font> --></td>
	       <td><textarea name="remarks" class="text" id="remarks" maxlength="500"></textarea>	         
	       </td>	  
	       <td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true"
			value="" /></td>
	       </tr>	  
			</logic:notPresent>
    	   </logic:notPresent>
		 
   
		 <!-- --------------------------------------- Insurance Asset Maker for Saved Data Starts--------------------------------- -->
		
		 <logic:notPresent name="datatoapproveList">
		 <logic:present name="assetMakerList">
		 <logic:notEmpty name="assetMakerList">
		 <html:hidden property="beforeForward" styleId="beforeForward" value="${requestScope.beforeForward}"/>
		 <html:hidden property="assetInsuranceId" styleId="assetInsuranceId" value="${requestScope.assetInsuranceId}"/>
	  
	   <tr>
	  		<td><bean:message key="lbl.loanAccount"></bean:message> <font color="red">* </font></td>
		  	<td>
				<html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${requestScope.assetMakerList[0].loanAccountNumber}" readonly="true" tabindex="-1"/>
		        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  value="${requestScope.assetMakerList[0].lbxLoanNoHID}"/>
		 
		  	</td>
			<td><bean:message key="lbl.customerName"></bean:message></td>
			<td>
			<input type="hidden" name="lbxAssetHID" id="lbxAssetHID" value="" />
				<html:text property="customerName"  styleClass="text" styleId="customerName" value="${requestScope.assetMakerList[0].customerName}"  maxlength="50" readonly="true" tabindex="-1"></html:text>
			
			</td>
		</tr>
		<logic:present name="assetMakerListAuthorFlag">
		 <tr>
	         <td><bean:message key="lbl.veri.entityType"></bean:message><font color="red">*</font></td>
	        <td>
	        	 <input type="hidden" name="entityTypeDesc" id="entityTypeDesc" value="${requestScope.assetMakerList[0].entityTypeDesc}"/>
	        	 <html:text property="entityType"  styleClass="text" styleId="entityType" value="${requestScope.assetMakerList[0].entityType}"  readonly="true" ></html:text>
	       </td>
          <td><bean:message key="lbl.entity"></bean:message><font color="red">*</font></td>
	        <td>
	        <a rel="tooltip3" id="tool">
		    <html:text styleClass="text" property="entity" styleId="entity" maxlength="50"  readonly="true" onmouseover="applyToolTip(id);" tabindex="-1" value="${requestScope.assetMakerList[0].entity}"/></a>
            <html:hidden property="lbxEntity" styleId="lbxEntity" value="${requestScope.assetMakerList[0].lbxEntity}"/>            
          </td>     	     	
	     </tr>
		</logic:present>
		<logic:notPresent name="assetMakerListAuthorFlag">
	    <tr>
	         <td><bean:message key="lbl.veri.entityType"></bean:message><font color="red">*</font></td>
	        <td>
	        	 <input type="hidden" name="entityTypeDesc" id="entityTypeDesc" value="${requestScope.assetMakerList[0].entityTypeDesc}"/>
	        	<html:select property="entityType" styleId="entityType" tabindex='1' value="${requestScope.assetMakerList[0].entityType}" styleClass="text" onchange="getEntityType();changeEntity();">  
	        		<html:option value="">--Select--</html:option>    
	        		<html:option value="PRAPPL">APPLICANT</html:option>
          			<html:option value="COAPPL">CO-APPLICANT</html:option>
          			<html:option value="GUARANTOR">GUARANTOR</html:option>
          			<html:option value="ASSET">ASSET</html:option>
          			<html:option value="COLLATERAL">COLLATERAL</html:option>
            	</html:select>
            </td>
          <td><bean:message key="lbl.entity"></bean:message><font color="red">*</font></td>
	        <td>
	        <a rel="tooltip3" id="tool">
		    <html:text styleClass="text" property="entity" styleId="entity" maxlength="50"  readonly="true" onmouseover="applyToolTip(id);" tabindex="-1" value="${requestScope.assetMakerList[0].entity}"/></a>
            <html:hidden property="lbxEntity" styleId="lbxEntity" value="${requestScope.assetMakerList[0].lbxEntity}"/>            
            <input type="hidden" name="asstCostLov" id="asstCostLov" value="" />
            <input type="hidden" name="endDateLov" id="endDateLov" value="" />
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openAssetLOV()" value=" " styleClass="lovbutton"></html:button>
		  </td>     	     	
	     </tr>
	     </logic:notPresent>
	      <tr>
	       <td><bean:message key="lbl.assetDesc"></bean:message></td>
	       <td>
	       <html:text property="assetDesc"  styleClass="text" styleId="assetDesc" value="${requestScope.assetMakerList[0].assetDesc}" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetNature"></bean:message></td>
	       <td>
	       <html:text property="assetNature"  styleClass="text" styleId="assetNature" value="${requestScope.assetMakerList[0].assetNature}"  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.assetMake"></bean:message></td>
	       <td>
	       <html:text property="assetMake"  styleClass="text" styleId="assetMake" value="${requestScope.assetMakerList[0].assetMake}" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetModel"></bean:message></td>
	       <td>
	       <html:text property="assetModel"  styleClass="text" styleId="assetModel" value="${requestScope.assetMakerList[0].assetModel}"  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.vendorname"></bean:message></td>
	       <td>
	       <html:text property="dealerName"  styleClass="text" styleId="dealerName" value="${requestScope.assetMakerList[0].dealerName}" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.engineNumber"></bean:message></td>
	       <td>
	       <html:text property="engineNumber"  styleClass="text" styleId="engineNumber" value="${requestScope.assetMakerList[0].engineNumber}"  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.chasisNumber"></bean:message></td>
	       <td>
	       <html:text property="chasisNumber"  styleClass="text" styleId="chasisNumber" value="${requestScope.assetMakerList[0].chasisNumber}" maxlength="20" readonly="true" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.registrationNumber"></bean:message></td>
	       <td>
	       <html:text property="registrationNumber"  styleClass="text" styleId="registrationNumber" value="${requestScope.assetMakerList[0].registrationNumber}"  maxlength="100" readonly="true" ></html:text>
	       </td>	        
		   </tr>
	     <tr>
	      <td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	      	 <td>
			     <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="${requestScope.assetMakerList[0].coverNoteNo}" maxlength="20"  ></html:text>
			</td>				     	 
	       	 <td><bean:message key="lbl.policyNo"></bean:message></td>
	      	 <td>	       
	             <html:text property="policyNo"  styleClass="text" styleId="policyNo" value="${requestScope.assetMakerList[0].policyNo}" maxlength="20" ></html:text>     
	         </td>	       
		 </tr>
		 <tr>     
		    <td><bean:message key="lbl.insuranceAgency"></bean:message> <font color="red">* </font></td>	       
		   <td>		   
		        <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${requestScope.assetMakerList[0].insuranceAgency}" readonly="true"/>
	            <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${requestScope.assetMakerList[0].lbxInsuranceAgency}"/>
	            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>
		   </td> 
		     
		   <td><bean:message key ="lbl.insrDoneBy"></bean:message><font color="red">* </font></td>
		   <td>
		   <html:select property="insuranceDoneBy" styleId="insuranceDoneBy" styleClass="text" value="${requestScope.assetMakerList[0].insuranceDoneBy}">
	            <html:option value="">-- Select --</html:option>
				 <logic:present name="insuranceDoneByList">		
					<html:optionsCollection name="insuranceDoneByList" label="doneByDesc" value="doneByCode" />
					</logic:present>	
			</html:select>
		   </td>	
		     
		  </tr>
		  <tr>
	      <td><bean:message key="lbl.sumAssured"></bean:message><font color="red">*</font></td>
	       	<td >
	       	   <html:text property="sumAssured"  styleClass="text" styleId="sumAssured" value="${requestScope.assetMakerList[0].sumAssured}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
	      	 </td>	
	       <td><bean:message key="lbl.premiumAmnt"></bean:message><font color="red">*</font></td>
	       <td >
	       <html:text property="premiumAmnt"  styleClass="text" styleId="premiumAmnt" value="${requestScope.assetMakerList[0].premiumAmnt}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
	       </td>	        
		   </tr>
	       <tr>	
	        <td><bean:message key ="lbl.startDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
	       <td>
	          <html:text property="startDate"  styleClass="text" styleId="startDate" value="${requestScope.assetMakerList[0].startDate}" ></html:text>
	       </td>	 
	        <td><bean:message key ="lbl.endDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*	</font></td>
	       <td >
	         <html:text property="endDate"  styleClass="text" styleId="endDate" value="${requestScope.assetMakerList[0].endDate}" ></html:text>
	       </td>	       
	      </tr>	      
	      <tr>	
	      <logic:present name="assetMakerListAuthorFlag" > 
	       <td><bean:message key ="lbl.yearNo"></bean:message><font color="red">*</font></td>
	       <td>
	        <logic:present name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="${requestScope.YearNo[0].yearDesc}" />
	        </logic:present>
	        <logic:notPresent name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="0" />
	        </logic:notPresent>
	        <html:select styleId="yearNo" property="yearNo" styleClass="text" value="${requestScope.assetMakerList[0].yearNo}">
		         <html:option value="">-Select-</html:option>
		         <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
	        </html:select>
	       </td>
	       </logic:present>    
	       <logic:notPresent name="assetMakerListAuthorFlag" > 
	       <td><bean:message key ="lbl.yearNo"></bean:message><font color="red">*</font></td>
	       <td>
	       <logic:present name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="${requestScope.YearNo[0].yearDesc}" />
	        </logic:present>
	        <logic:notPresent name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="0" />
	        </logic:notPresent>
	         <html:select styleId="yearNo" property="yearNo" styleClass="text" value="${requestScope.assetMakerList[0].yearNo}">
		         <html:option value="">-Select-</html:option>
		         <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
	        </html:select>
	       </td>
	      </logic:notPresent>
	       <td><bean:message key="lbl.surrenderValue"/></td>
			 <td>
	           <html:text property="surrenderValue"  styleClass="text" styleId="surrenderValue" value="${requestScope.assetMakerList[0].surrenderValue}" maxlength="20" style="text-align: right"  onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" ></html:text>
	       </td>		 	 
		 	</tr>
	        <tr>
	        <td ><bean:message key="lbl.premiumFrequency"/></td>
           		<td >
           		<html:select property="premiumFrequency" styleId="premiumFrequency" styleClass="text" value="${requestScope.assetMakerList[0].premiumFrequency}" >
	             <html:option value="">--Select--</html:option> 
	             <html:option value="MONTHLY">MONTHLY</html:option>
	             <html:option value="QUARTERLY">QUARTERLY</html:option>
	             <html:option value="HALFYEARLY">HALF YEARLY</html:option>      
	             <html:option value="ANNUALY">ANNUALY</html:option>        
	           </html:select></td>	         
	       <td ><bean:message key="lbl.nominee"/></td>
 		   <td >
 		      <html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${requestScope.assetMakerList[0].nominee}"  />
 		   </td>
		    
	        </tr>
	        <tr> 
	        <td><bean:message key="lbl.relWithNominee"/></td>
		   <td ><html:select styleClass="text" property="relWithNominee" styleId="relWithNominee" value="${requestScope.assetMakerList[0].relWithNominee}" >
                  <html:option value="">Select</html:option>  
       			 
				  <html:optionsCollection name="showInsuranceRelWithNominee" label="name" value="id" />
				  
                  </html:select>
                  </td>   
	       
	       <td><bean:message key="lbl.adviseFlag" /></td>
	       <td> 
	       <html:select styleId="adviseFlag" property="adviseFlag" styleClass="text" value="${requestScope.assetMakerList[0].adviseFlag}">
		         <html:option value="">-Select-</html:option>
		         <html:option value="Y">YES</html:option>
		         <html:option value="N">NO</html:option>
	        </html:select>
		  </td>		   
	        </tr>
	      <tr>
	     <td><bean:message key="lbl.remark" /></td>
	       <td>
	         <textarea name="remarks" class="text" id="remarks" maxlength="500">${requestScope.assetMakerList[0].remarks}</textarea>	         
	       </td>
	       <td ><bean:message key="lbl.authorRemarks" /></td>
			<td ><html:textarea styleClass="text" property="authorRemarks" readonly="true"
			value="${requestScope.assetMakerList[0].authorRemarks}" /></td>
	       </tr>
	      </logic:notEmpty>
		 </logic:present> 
           </logic:notPresent>		 
   
		<!-- --------------------------------------- Insurance Asset Author Starts--------------------------------- -->

		 <logic:present name="datatoapproveList">
		<logic:notEmpty  name="datatoapproveList">
		<html:hidden property="assetInsuranceID" styleId="assetInsuranceID" value="${requestScope.assetInsuranceID}"/>
            <tr>
		     	<td><bean:message key="lbl.loanAccount"></bean:message> </td>
		  		 <td>
				 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${sessionScope.datatoapproveList[0].loanAccountNumber}" readonly="true" tabindex="-1"/>
		        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}"  />
<!--		        <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="closeLovCallonLov1();openLOVCommon(2037,'assetMakerForm','loanAccountNumber','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>-->
		        <input type="hidden" value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	
		 		</td>
				<td><bean:message key="lbl.customerName"></bean:message></td>
				<td>
				<html:text property="customerName"  styleClass="text" styleId="customerName" value="${sessionScope.datatoapproveList[0].customerName}"  maxlength="50" readonly="true" tabindex="-1"></html:text>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
				</td>
		</tr>
	       <tr>
	         <td><bean:message key="lbl.veri.entityType"></bean:message></td>
	        <td>
	          <html:hidden property="entityTypeDesc"  styleClass="text" styleId="entityTypeDesc" value="${sessionScope.datatoapproveList[0].entityTypeDesc}" ></html:hidden>	        
	       <html:text property="entityType"  styleClass="text" styleId="entityType" value="${sessionScope.datatoapproveList[0].entityType}" maxlength="20" style="text-align: right" readonly="true" ></html:text>
	       </td>
	       <td><bean:message key="lbl.entity"></bean:message></td>
	        <td>
	        <a rel="tooltip3" id="tool">
		    <html:text styleClass="text" property="entity" styleId="entity" maxlength="50"  value="${sessionScope.datatoapproveList[0].entity}" onmouseover="applyToolTip(id);" readonly="true"   tabindex="-1"/>
            </a></td> 	      
	      </tr>
	       <tr>
	       <td><bean:message key="lbl.assetDesc"></bean:message></td>
	       <td>
	       <html:text property="assetDesc"  styleClass="text" styleId="assetDesc" value="${sessionScope.datatoapproveList[0].assetDesc}"  readonly="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetNature"></bean:message></td>
	       <td>
	       <html:text property="assetNature"  styleClass="text" styleId="assetNature" value="${sessionScope.datatoapproveList[0].assetNature}"  readonly="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.assetMake"></bean:message></td>
	       <td>
	       <html:text property="assetMake"  styleClass="text" styleId="assetMake" value="${sessionScope.datatoapproveList[0].assetMake}"  readonly="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetModel"></bean:message></td>
	       <td>
	       <html:text property="assetModel"  styleClass="text" styleId="assetModel" value="${sessionScope.datatoapproveList[0].assetModel}"  readonly="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.vendorname"></bean:message></td>
	       <td>
	       <html:text property="dealerName"  styleClass="text" styleId="dealerName" value="${sessionScope.datatoapproveList[0].dealerName}" readonly="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.engineNumber"></bean:message></td>
	       <td>
	       <html:text property="engineNumber"  styleClass="text" styleId="engineNumber" value="${sessionScope.datatoapproveList[0].engineNumber}"  readonly="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.chasisNumber"></bean:message></td>
	       <td>
	       <html:text property="chasisNumber"  styleClass="text" styleId="chasisNumber" value="${sessionScope.datatoapproveList[0].chasisNumber}" readonly="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.registrationNumber"></bean:message></td>
	       <td>
	       <html:text property="registrationNumber"  styleClass="text" styleId="registrationNumber" value="${sessionScope.datatoapproveList[0].registrationNumber}"  readonly="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
	      <tr>
	      <td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	       <td>
	       <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="${sessionScope.datatoapproveList[0].coverNoteNo}" readonly="true"></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.policyNo"></bean:message></td>
	       <td >
	       <html:text property="policyNo"  styleClass="text" styleId="policyNo" value="${sessionScope.datatoapproveList[0].policyNo}" readonly="true"></html:text>
	       </td>
		   </tr>		   
		   <tr>    		   
	        <td><bean:message key="lbl.insuranceAgency"></bean:message></td>
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${sessionScope.datatoapproveList[0].insuranceAgency}" readonly="true"/>
          <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${sessionScope.datatoapproveList[0].lbxInsuranceAgency}" />
		   </td>	  		   
		   <td><bean:message key ="lbl.insrDoneBy"></bean:message></td>
		   <td>
	       <html:text property="insuranceDoneBy"  styleClass="text" styleId="insuranceDoneBy" value="${sessionScope.datatoapproveList[0].insuranceDoneBy}" readonly="true"></html:text>
	       </td> 		  	
		   </tr>		   
		  <tr>	
		   <td><bean:message key="lbl.sumAssured"></bean:message></td>
	       <td >
	       <html:text property="sumAssured"  styleClass="text" styleId="sumAssured" value="${sessionScope.datatoapproveList[0].sumAssured}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  readonly="true"></html:text>
	       </td>       
	       <td><bean:message key="lbl.premiumAmnt"></bean:message></td>
	       <td>
	       <html:text property="premiumAmnt"  styleClass="text" styleId="premiumAmnt" value="${sessionScope.datatoapproveList[0].premiumAmnt}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)"  readonly="true"></html:text>
	       </td>	       
		   </tr>		   
	       <tr>
	       <td><bean:message key ="lbl.startDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td >
	       <html:text property="startDate"  styleClass="text" styleId="remarks" value="${sessionScope.datatoapproveList[0].startDate}" maxlength="50" readonly="true"></html:text>
	       </td>	   
	        <td><bean:message key ="lbl.endDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td>
	       <html:text property="endDate"  styleClass="text" styleId="remarks" value="${sessionScope.datatoapproveList[0].endDate}" maxlength="50" readonly="true"></html:text>
	       </td>	       
	      </tr>
	    <tr>	
	    <td><bean:message key ="lbl.yearNo"></bean:message><font color="red">*</font></td>
	       <td >
	        <logic:present name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="${requestScope.YearNo[0].yearDesc}" />
	        </logic:present>
	        <logic:notPresent name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="0" />
	        </logic:notPresent>
	      <html:select styleId="yearNo" property="yearNo" styleClass="text" value="${sessionScope.datatoapproveList[0].yearNo}" disabled="true">
		         <html:option value="">-Select-</html:option>
		         <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
	        </html:select>
	       </td>     
	       <td><bean:message key="lbl.surrenderValue"/></td>
			 <td>
	       <html:text property="surrenderValue"  styleClass="text" styleId="surrenderValue" value="${sessionScope.datatoapproveList[0].surrenderValue}" maxlength="20" style="text-align: right" readonly="true" ></html:text>
	       </td>		 	 
		 	</tr>
	        <tr>
	        <td style="width:23%"><bean:message key="lbl.premiumFrequency"/></td>
           	<td>
	       <html:text property="premiumFrequency"  styleClass="text" styleId="premiumFrequency" value="${sessionScope.datatoapproveList[0].premiumFrequency}" maxlength="20" style="text-align: right" readonly="true" ></html:text>
	       </td>	         
	       <td style="width:23%"><bean:message key="lbl.nominee"/></td>
 		   <td style="width:26%"><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${sessionScope.datatoapproveList[0].nominee}" readonly="true" /></td>
		    </tr>
	        <tr>   
	         <td><bean:message key="lbl.relWithNominee"/></td>
		   	<td>
	       <html:text property="relWithNominee"  styleClass="text" styleId="relWithNominee" value="${sessionScope.datatoapproveList[0].relWithNominee}" maxlength="20" style="text-align: right"  readonly="true" ></html:text>
	       </td>
	       <td><bean:message key="lbl.adviseFlag" /></td>
	      	<td>
	       <html:text property="adviseFlag"  styleClass="text" styleId="adviseFlag" value="${sessionScope.datatoapproveList[0].adviseFlag}" maxlength="20" style="text-align: right" readonly="true" ></html:text>
	       </td>		   
	        </tr>	      
	       <tr>	
	       <td><bean:message key="lbl.remark" /></td>
	       <td><textarea name="remarks" class="text" id="remarks" maxlength="500"  readonly="readonly">${sessionScope.datatoapproveList[0].remarks}</textarea>	         
	       </td>	  
	       <td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true" 
			value="${sessionScope.datatoapproveList[0].authorRemarks}" /></td>
	       </tr>
	       <tr>	   
	         <td>
	       		<button type="button"  class="topformbutton4" id="cancel" onclick="return assetInsuranceViewer();" >Asset Insurance Viewer</button>
	       	  </td>        
	       </tr>	       
	     </logic:notEmpty>
		  
		 </logic:present>
		
		 
		 <!-- Sarvesh Changes for Direct Update when author has been done-->
		 
		 <%-- <logic:present name="onAssetInsurancePolicyViewer">
		 <logic:notEmpty  name="onAssetInsurancePolicyViewer"> 
		<html:hidden property="assetInsuranceID" styleId="assetInsuranceID" value="${requestScope.assetInsuranceID}"/>
            <tr>
		     	<td><bean:message key="lbl.loanAccount"></bean:message> </td>
		  		 <td>
				 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${onAssetInsurancePolicyViewer[0].loanAccountNumber}" readonly="true" tabindex="-1"/>
		        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${onAssetInsurancePolicyViewer[0].lbxLoanNoHID}"  />
		        <input type="hidden" value="${onAssetInsurancePolicyViewer[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	
		 		</td>
				<td><bean:message key="lbl.customerName"></bean:message></td>
				<td>
				<html:text property="customerName"  styleClass="text" styleId="customerName" value="${onAssetInsurancePolicyViewer[0].customerName}"  maxlength="50" readonly="true" disabled="true" tabindex="-1"></html:text>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
				</td>
		</tr>
	       <tr>
	         <td><bean:message key="lbl.veri.entityType"></bean:message></td>
	        <td>
			<input type="hidden" name="entityTypeDesc" id="entityTypeDesc" value=""/>
	        	<html:select property="entityType" styleId="entityType" tabindex='1' styleClass="text" value="${onAssetInsurancePolicyViewer[0].entityType}" onchange="getEntityType();">  
	        		<html:option value="">--Select--</html:option>    
	        		<html:option value="PRAPPL">APPLICANT</html:option>
          			<html:option value="COAPPL">CO-APPLICANT</html:option>
          			<html:option value="GUARANTOR">GUARANTOR</html:option>
          			<html:option value="ASSET">ASSET</html:option>
          			<html:option value="COLLATERAL">COLLATERAL</html:option>
            	</html:select>
	       <!-- <html:text property="entityType"  styleClass="text" styleId="entityType" value="${onAssetInsurancePolicyViewer[0].entityType}" maxlength="20" style="text-align: right" disabled="true" ></html:text> -->
	       </td>
	       <td><bean:message key="lbl.entity"></bean:message></td>
	        <td>
	        <a rel="tooltip3" id="tool">
		    <html:text styleClass="text" property="entity" styleId="entity" maxlength="50"  value="${onAssetInsurancePolicyViewer[0].entity}" onmouseover="applyToolTip(id);" readonly="true" tabindex="-1"/>
           </a>
		   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath" />
		      <html:hidden property="lbxEntity" styleId="lbxEntity"/>
            <input type="hidden" name="asstCostLov" id="asstCostLov" value="" />
            <input type="hidden" name="endDateLov" id="endDateLov" value="" />
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openAssetLOV()" value=" " styleClass="lovbutton"></html:button>  
		   </td> 	      
	      </tr>
	      <tr>
	       <td><bean:message key="lbl.assetDesc"></bean:message></td>
	       <td>
	       <html:text property="assetDesc"  styleClass="text" styleId="assetDesc" value="${onAssetInsurancePolicyViewer[0].assetDesc}"  disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetNature"></bean:message></td>
	       <td>
	       <html:text property="assetNature"  styleClass="text" styleId="assetNature" value="${onAssetInsurancePolicyViewer[0].assetNature}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.assetMake"></bean:message></td>
	       <td>
	       <html:text property="assetMake"  styleClass="text" styleId="assetMake" value="${onAssetInsurancePolicyViewer[0].assetMake}"  disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.assetModel"></bean:message></td>
	       <td>
	       <html:text property="assetModel"  styleClass="text" styleId="assetModel" value="${onAssetInsurancePolicyViewer[0].assetModel}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.vendorname"></bean:message></td>
	       <td>
	       <html:text property="dealerName"  styleClass="text" styleId="dealerName" value="${onAssetInsurancePolicyViewer[0].dealerName}" disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.engineNumber"></bean:message></td>
	       <td>
	       <html:text property="engineNumber"  styleClass="text" styleId="engineNumber" value="${onAssetInsurancePolicyViewer[0].engineNumber}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
		   <tr>
	       <td><bean:message key="lbl.chasisNumber"></bean:message></td>
	       <td>
	       <html:text property="chasisNumber"  styleClass="text" styleId="chasisNumber" value="${onAssetInsurancePolicyViewer[0].chasisNumber}" disabled="true" maxlength="20" ></html:text>
	      </td>	      
	       <td><bean:message key="lbl.registrationNumber"></bean:message></td>
	       <td>
	       <html:text property="registrationNumber"  styleClass="text" styleId="registrationNumber" value="${onAssetInsurancePolicyViewer[0].registrationNumber}"  disabled="true" maxlength="100" ></html:text>
	       </td>	        
		   </tr>
	      <tr>
	      <td><bean:message key="lbl.coverNoteNo"></bean:message></td>
	       <td>
	       <html:text property="coverNoteNo"  styleClass="text" styleId="coverNoteNo" value="${onAssetInsurancePolicyViewer[0].coverNoteNo}" disabled="false"></html:text>
	      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	      </td>
	       <td><bean:message key="lbl.policyNo"></bean:message></td>
	       <td >
	       <html:text property="policyNo"  styleClass="text" styleId="policyNo" value="${onAssetInsurancePolicyViewer[0].policyNo}" onkeypress="return checkPolicyNo(event);" disabled="false"></html:text>
	       </td>
		   </tr>		   
		   <tr>    		   
	        <td><bean:message key="lbl.insuranceAgency"></bean:message></td>
		   <td>
		   <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="20"  value="${onAssetInsurancePolicyViewer[0].insuranceAgency}" disabled="false"/>
          <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency" value="${onAssetInsurancePolicyViewer[0].lbxInsuranceAgency}" />
		  <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>
		   </td>	  		   
		   <td><bean:message key ="lbl.insrDoneBy"></bean:message></td>
		   <td><span style="float:left;">
<!-- <html:text property="insuranceDoneBy"  styleClass="text" styleId="insuranceDoneBy" value="${onAssetInsurancePolicyViewer[0].insuranceDoneBy}" disabled="true"></html:text>   -->
		   <html:select property="insuranceDoneBy" styleId="insuranceDoneBy" tabindex='1' styleClass="text" value="${onAssetInsurancePolicyViewer[0].insuranceDoneBy}">  
	        		<html:option value="">--Select--</html:option>    
	        		<html:option value="B">BORROWER</html:option>
          			<html:option value="D">DEALER</html:option>
          			<html:option value="I">IN HOUSE</html:option>
          			
            	</html:select>
		   
		   
		   <!-- <html:select property="insuranceDoneBy" styleId="insuranceDoneBy" styleClass="text" value="${onAssetInsurancePolicyViewer[0].insuranceDoneBy}">
		   <option value="">-- Select --</option>	
	                <logic:present name="insuranceDoneByList">		
					<html:optionsCollection name="insuranceDoneByList" label="doneByDesc" value="doneByCode" />
					</logic:present>				             
           </html:select> -->
	        
	       </span></td> 		  	
		   </tr>		   
		  <tr>	
		   <td><bean:message key="lbl.sumAssured"></bean:message></td>
	       <td >
	       <html:text property="sumAssured"  styleClass="text" styleId="sumAssured" value="${onAssetInsurancePolicyViewer[0].sumAssured}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  disabled="false"></html:text>
	       </td>       
	       <td><bean:message key="lbl.premiumAmnt"></bean:message></td>
	       <td>
	       <html:text property="premiumAmnt"  styleClass="text" styleId="premiumAmnt" value="${onAssetInsurancePolicyViewer[0].premiumAmnt}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)"  disabled="false"></html:text>
	       </td>	       
		   </tr>		   
	       <tr>
	       <td><bean:message key ="lbl.startDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td >
	       <html:text property="startDate"  styleClass="text" styleId="startDate" value="${onAssetInsurancePolicyViewer[0].startDate}" maxlength="50" disabled="false"></html:text>
	       <!-- <img class="ui-datepicker-trigger" src="/OmniFin/images/theme1/calendar.gif" alt="..." title="...">  -->
		   </td>	   
	        <td><bean:message key ="lbl.endDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	       <td>
	       <html:text property="endDate"  styleClass="text" styleId="endDate" value="${onAssetInsurancePolicyViewer[0].endDate}" maxlength="50" disabled="false"></html:text>
	       <!-- <img class="ui-datepicker-trigger" src="/OmniFin/images/theme1/calendar.gif" alt="..." title="...">  -->
		   </td>	       
	      </tr>
	    <tr>	
	    <td><bean:message key ="lbl.yearNo"></bean:message></td>
	       <td >
		   <logic:present name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="${onAssetInsurancePolicyViewer[0].yearNo}" />
	        </logic:present>
	        <logic:notPresent name="YearNo">
	        <input type="hidden" name="yearDesc" id="yearDesc" value="0" />
	        </logic:notPresent>
		   
		   <html:select styleId="yearNo" property="yearNo" styleClass="text" value="" value="${onAssetInsurancePolicyViewer[0].yearNo}" onfocus="keyUpNumber(this.value, event, 4,id);">
		         <html:option value="">-Select-</html:option>
		         <html:option value="1">1</html:option>
		         <html:option value="2">2</html:option>
		         <html:option value="3">3</html:option>
		         <html:option value="4">4</html:option>
		         <html:option value="5">5</html:option>
		         <html:option value="6">6</html:option>
		         <html:option value="7">7</html:option>
		         <html:option value="8">8</html:option>
		         <html:option value="9">9</html:option>
	        </html:select>
	       <!-- <html:text property="yearNo"  styleClass="text" styleId="yearNo" value="${onAssetInsurancePolicyViewer[0].yearNo}" maxlength="04" disabled="true" ></html:text>  -->
	       </td>     
	       <td><bean:message key="lbl.surrenderValue"/></td>
			 <td>
	       <html:text property="surrenderValue"  styleClass="text" styleId="surrenderValue" value="${onAssetInsurancePolicyViewer[0].surrenderValue}" maxlength="20" style="text-align: right" disabled="false" ></html:text>
	       </td>		 	 
		 	</tr>
	        <tr>
	        <td style="width:23%"><bean:message key="lbl.premiumFrequency"/></td>
           	<td>
			<html:select property="premiumFrequency" styleId="premiumFrequency" value="${onAssetInsurancePolicyViewer[0].premiumFrequency}" styleClass="text" value="">
	              <html:option value="">--Select--</html:option> 
	             <html:option value="MONTHLY">MONTHLY</html:option>
	             <html:option value="QUARTERLY">QUARTERLY</html:option>
	             <html:option value="HALFYEARLY">HALF YEARLY</html:option>      
	             <html:option value="ANNUALY">ANNUALY</html:option>        
	           </html:select>
	       <!-- <html:text property="premiumFrequency"  styleClass="text" styleId="premiumFrequency" value="${onAssetInsurancePolicyViewer[0].premiumFrequency}" maxlength="20" style="text-align: right" disabled="true" ></html:text>  -->
	       </td>	         
	       <td style="width:23%"><bean:message key="lbl.nominee"/></td>
 		   <td style="width:26%"><html:text styleClass="text" styleId="nominee" property="nominee" maxlength="100" value="${onAssetInsurancePolicyViewer[0].nominee}" disabled="false" /></td>
		    </tr>
	        <tr>   
	         <td><bean:message key="lbl.relWithNominee"/></td>
		   	<td nowrap="nowrap"><html:select styleClass="text"  styleId="relWithNominee" value="${onAssetInsurancePolicyViewer[0].relWithNominee}" property="relWithNominee">
                  <html:option value="">Select</html:option>  
       			    <html:optionsCollection name="showInsuranceRelWithNominee" label="name" value="id" />
				
                  </html:select>
                  </td> 
			
			<!--<td>
			
	       <html:text property="relWithNominee"  styleClass="text" styleId="relWithNominee" value="${onAssetInsurancePolicyViewer[0].relWithNominee}" maxlength="20" style="text-align: right"  disabled="false" ></html:text>
	       </td>  -->
	       <td><bean:message key="lbl.adviseFlag" /></td>
	      	<td>
			<html:select styleId="adviseFlag" property="adviseFlag" value="${onAssetInsurancePolicyViewer[0].adviseFlag}" styleClass="text">
		         <html:option value="">-Select-</html:option>
		         <html:option value="Y">YES</html:option>
		         <html:option value="N">NO</html:option>
	         </html:select>
	      <!-- <html:text property="adviseFlag"  styleClass="text" styleId="adviseFlag" value="${onAssetInsurancePolicyViewer[0].adviseFlag}" maxlength="20" style="text-align: right" disabled="true" ></html:text>-->
	       </td>		   
	        </tr>	      
	       <tr>	
	       <td><bean:message key="lbl.remark" /></td>
	       <td><textarea name="remarks" class="text" id="remarks" maxlength="500" property="relWithNominee" >${onAssetInsurancePolicyViewer[0].remarks}</textarea>	         
	       </td>	  
	       <td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true" disabled="true"
			value="${onAssetInsurancePolicyViewer[0].authorRemarks}" /></td>
	       </tr>	       
	      </logic:notEmpty> 
		 </logic:present> --%>
		 
		 
		 
		 
		 <!--  Sarvesh Work ended -->












		 
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="1">
		<logic:present name="assetMakerListAuthorFlag"></logic:present>
		<tr>
		  <logic:notPresent name="datatoapproveList">
		  <logic:notPresent name="errorAtSave">
	      <logic:present name="assetMakerList">
	      <logic:present name="assetMakerGridBeforSave">
	      	 <td>
	      	<logic:present name="updat">
	      <button type="button"  class="topformbutton2" id="save" onclick="return onSaveUpdateAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	      <button type="button"  class="topformbutton2" id="saveForward" onclick="return onSaveForwardUpdateAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />','<bean:message key="msg.confirmationForwardMsg" />');fnAmnt('<bean:message key="msg.ntGrSum" />');" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	      <button type="button"  class="topformbutton2" id="cancel" onclick="return onCancelAssetMaker();" title="Alt+C" accesskey="C"><bean:message key="button.cancel" /></button>
	      	</logic:present> 
	      	 
	      	<logic:notPresent name="updat">
	      	   <button type="button"  class="topformbutton2" id="save" onclick="return onSaveofAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');fnAmnt('<bean:message key="msg.ntGrSum" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
		      <button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"  class="topformbutton2" onclick="return saveBeforeForward();"><bean:message key="button.forward" /></button>
		    </logic:notPresent> 	      
	      </td>
	      </logic:present>
	      <logic:notPresent name="assetMakerGridBeforSave">
	      <logic:present name="assetMakerListAuthorFlag">
	      <td>
	       <button type="button"  class="topformbutton2" id="save" onclick="return onSaveUpdateAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	        <button type="button" class="topformbutton2" id="saveForward" onclick="return onSaveForwardUpdateAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');fnAmnt('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.confirmationForwardMsg" />');" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	        </td>
	       </logic:present>
	       <logic:notPresent name="assetMakerListAuthorFlag">
	      	<td>
	      <button type="button"  class="topformbutton2" id="save" onclick="return onSaveUpdateAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	       <button type="button" class="topformbutton2" id="saveForward" onclick="return onSaveForwardUpdateAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');fnAmnt('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.confirmationForwardMsg" />');" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	       <button type="button"  class="topformbutton2" id="delete" onclick="return onCancelAssetMaker();" title="Alt+D" accesskey="C"><bean:message key="button.delete" /></button>
	       </td>
	       </logic:notPresent>
		   
	       </logic:notPresent>
	       
		</logic:present>
		</logic:notPresent>
		<logic:present name="errorAtSave">
			<td>
		       <button type="button" class="topformbutton2" id="save" onclick="return onSaveofAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');fnAmnt('<bean:message key="msg.ntGrSum" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
		       <button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"  class="topformbutton2" onclick="return saveBeforeForward();"><bean:message key="button.forward" /></button>
		    </td>
		</logic:present>
	     <logic:notPresent name="assetMakerList">
	     <td>
		  <button type="button" class="topformbutton2" id="save" onclick="return onSaveofAssetMaker('<bean:message key="msg.ntGrSum" />','<bean:message key="msg.EndDateLessStart" />');fnAmnt('<bean:message key="msg.ntGrSum" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>  
	       <button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"  class="topformbutton2" onclick="return saveBeforeForward();"><bean:message key="button.forward" /></button>
	    </td>
	      
	      </logic:notPresent>
	       </logic:notPresent>
	</tr>
	
	</table>
	</td>
	</tr>
	 </table>
	</fieldset>	
	
	<logic:present name="assetInsuranceViewerGrid">
		<fieldset>
			<legend>
				<bean:message key="lbl.insuranceDetails" />
			</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="gridtd">
							<input type="hidden" name="psize" id="psize" />
							<input type="hidden" name="pcheck" id="pcheck" />
							<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
								<tr class="white2">
									<td><b><bean:message key="lbl.insYearNo"/></b></td>
									<td><b><bean:message key="lbl.insuranceAgency"/></b></td>
							        <td><b><bean:message key="lbl.veri.entityType"/></b></td>
									<td><b><bean:message key="lbl.entity"/></b></td>								      
								    <td><b><bean:message key="lbl.startDate"/></b></td>
									<td><b><bean:message key="lbl.endDate"/></b></td>  
									<td><b><bean:message key="lbl.insrDoneBy"/></b></td>
									<td><b><bean:message key="lbl.policyNo"/></b></td>  
								    <td><b><bean:message key="lbl.coverNoteNo"/></b></td>									
									<td><b><bean:message key="lbl.premiumAmnt"/></b></td>	
									<td><b><bean:message key="lbl.sumAssured"/></b></td>
									<td><b><bean:message key="lbl.status"/></b></td>										
								</tr>
									<logic:iterate name="assetInsuranceViewerGrid"
										id="subAssetInsuranceViewer" indexId="i">
										<tr class="white1" style="width: 25px;">
											
											<td>${subAssetInsuranceViewer.yearNo }
											</td>
											<td>${subAssetInsuranceViewer.insuranceAgency }
											</td>
											<td>${subAssetInsuranceViewer.entityType }
											</td>
											<td>${subAssetInsuranceViewer.entity }
											</td>
											<td>${subAssetInsuranceViewer.startDate }
											</td>
											<td>${subAssetInsuranceViewer.endDate }
											</td>
											<td>${subAssetInsuranceViewer.insuranceDoneBy }
											</td>
											<td>${subAssetInsuranceViewer.policyNo }
											</td>
											<td>${subAssetInsuranceViewer.coverNoteNo }
											</td>
											<td>${subAssetInsuranceViewer.premiumAmnt }
											</td>
											<td>${subAssetInsuranceViewer.sumAssured }
											</td>
											<td>${subAssetInsuranceViewer.recStatus }
											</td>
										</tr>
								</logic:iterate>		
							</table>
						</td>
					</tr>				
				</table>	
		</fieldset>
	</logic:present>	
     
  </html:form>
  <logic:present name="warningMsg">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("warningMsg").toString()%>'=='IPE')
		{
			alert("<bean:message key="lbl.combinationExist" />");
		
		}
		
		</script>
		</logic:present>
  
		<logic:present name="msg">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='comAlEx')
		{
			alert("Policy No. or Cover Note No. Already Exists.");
		
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='insCheck')
		{
			alert("Insurance already Captured for selected year.");
		
		}
		else if('<%=request.getAttribute("msg")%>'=="sameYearWarning")
		{
		//	var forwardLoanID=document.getElementById("forwardLoanID").value;
		//  var forwardInstrumentID=document.getElementById("forwardInstrumentID").value;
			if(confirm("Insurance for selected entity for selected year already exists.Do you want to proceed")){
		  
			 var basePath=document.getElementById("contextPath").value;		 
			 document.getElementById("assetMakerForm").action=basePath+"/assetMakerForSaveAction.do?method=onSaveAssetMaker&afterWarning=WARN";
	         document.getElementById("assetMakerForm").submit();
	         }
		}
		else if('<%=request.getAttribute("msg")%>'=="sameYearWarningUpdate")
		{
		//	var forwardLoanID=document.getElementById("forwardLoanID").value;
		//  var forwardInstrumentID=document.getElementById("forwardInstrumentID").value;
			if(confirm("Insurance for selected entity for selected year already exists.Do you want to proceed")){
		  
			 var basePath=document.getElementById("contextPath").value;		 
			 document.getElementById("assetMakerForm").action=basePath+"/assetMakerForSaveAction.do?method=updateonSaveAssetMaker&afterWarning=WARN";
	         document.getElementById("assetMakerForm").submit();
	         }
		}
		else if('<%=request.getAttribute("msg")%>'=="sameYearWarningForward")
		{
		//	var forwardLoanID=document.getElementById("forwardLoanID").value;
		//  var forwardInstrumentID=document.getElementById("forwardInstrumentID").value;
			if(confirm("Insurance for selected entity for selected year already exists.Do you want to proceed")){
		  
			 var basePath=document.getElementById("contextPath").value;		 
			 document.getElementById("assetMakerForm").action=basePath+"/assetMakerForSaveAction.do?method=onSaveForwardofAssetMaker&afterWarning=WARN";
	         document.getElementById("assetMakerForm").submit();
	         }
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='SF')
		{
			alert("<bean:message key="lbl.canForward" />");
			location.href="assetMakerSearch.do";
		}		
		else if('<%=request.getAttribute("msg").toString()%>'=='PO')
		{
			alert("<bean:message key="lbl.pendingforPolicy" />");
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='D')
		{
			alert("<bean:message key="lbl.assetDeleted" />");
			location.href="assetMakerSearch.do";
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='entityTypeCheck')
		{
			alert("Please check entity type field value and Save again.");
		
		}		
		</script>
		</logic:present>	
</div>
</div>
<script>
	setFramevalues("assetMakerForm");
</script>
</body>
</html:html>