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
       <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/fundFlowAnalysis.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

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
<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('obligationForm');document.getElementById('obligationForm').institutionName.focus();init_fields();hideShowManFields();" onclick="parent_disable();">
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
	<logic:present name="obligationList">
		   			<input type="hidden" id="LSD" name="LSD" value="Y" />
	</logic:present>
	<logic:notPresent name="obligationList">
		   			<input type="hidden" id="LSD" name="LSD" value="N" />
	</logic:notPresent>
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
	<logic:notPresent name="underWriterViewData">
	<html:form action="/saveobligationDetail"  styleId="obligationForm">
	<input type="hidden" id="contextPath" value="<%=path %>" />	
	<logic:notPresent name="fundFlowAuthor">
	<logic:present name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>         
	   
	
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<%-- <tr>
		<td ><bean:message key="lbl.customerType"/><font color="red">*</font></td>   <!-- Sarvesh Added <font color="red">*</font> -->
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${obligationDetail[0].customerType}" onchange="getCustomerName();"  styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/><font color="red">*</font></td>   <!-- Sarvesh Added <font color="red">*</font> -->
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
		</tr> --%>
		
		<!-- Added By Sarvesh -->
	<tr>
		<td width="20%"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td width="35%" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${obligationDetail[0].customerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'obligationForm','customerName','','', '','','','customerType','lbxCustomerRoleType')"
								value=" " name="dealButton"/>
					<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${obligationDetail[0].lbxCustomerId}"/>
					<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${obligationDetail[0].lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="20%"><bean:message key="lbl.entityCustomerName"/><font color="red">*</font></td>
	<td width="35%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		
		
		<tr>
					
		   <td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" value="${obligationDetail[0].institutionName}" maxlength="50" /></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" value="${obligationDetail[0].outstandingLoanAmount}" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts -->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" /></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" onchange="hideShowManFields();">
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
<!--		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>-->
<!--			 <td width="35%">-->
<!--			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>-->
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" >
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" maxlength="50" /></td>
		
		
		<td><bean:message key="lbl.vanillaProgramStatus"/></td>
		   	
		   	
		   			<td nowrap="nowrap" ><html:select  styleClass="text" property="vanillaProgramStatus" styleId="vanillaProgramStatus" value="${obligationDetail[0].vanillaProgramStatus}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="vanillaProgramStatusList" label="description" value="value"/>
	    			</html:select></td>
		
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red" id="hid">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="${obligationDetail[0].emiAmount}" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.remainingTenure"/><font color="red" id="hid1">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="${obligationDetail[0].tenure}" maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/> <font color="red" id="hid2">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" styleId="maturityDate" value="${obligationDetail[0].maturityDate}" onchange="return checkDate('maturityDate');" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" value="${obligationDetail[0].purpose}" maxlength="50" />
		    </label></td>
		 </tr>
		 <!-- Nishant Space Starts on 15-05-2013 -->
		  <tr>
		  <td><bean:message key="lbl.lastStatementDate"/><font color="red">*</font></td>
		   		<logic:present name="obligationList">
		   			<td nowrap="nowrap" ><html:text  styleClass="text" property="lastStatementDate" styleId="lastStatementDate1" readonly="true" value="${obligationDetail[0].lastStatementDate}" maxlength="20" />  </td>
		   		</logic:present>
		   		<logic:notPresent name="obligationList">
		   			<td nowrap="nowrap" ><html:text  styleClass="text" property="lastStatementDate" styleId="lastStatementDate"  value="" onchange="return checkDate('lastStatementDate');" maxlength="20" />  </td>
		   		</logic:notPresent>
		   		
		   		<td><bean:message key="lbl.dcProgramStatue"/></td>
		   	
		   	
		   			<td nowrap="nowrap" ><html:select  styleClass="text" property="status" styleId="status" value="${obligationDetail[0].status}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="statusList" label="description" value="value"/>
	    			</html:select></td>
		   		
		   		
		   		
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth1"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth1" styleId="lastMonth1" value="${obligationDetail[0].lastMonth1}" onchange="return checkDate('lastMonth1');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce1"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce1" styleId="bounce1" styleClass="text" value="${obligationDetail[0].bounce1}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth2"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth2" styleId="lastMonth2" value="${obligationDetail[0].lastMonth2}" onchange="return checkDate('lastMonth2');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce2"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce2" styleId="bounce2" styleClass="text" value="${obligationDetail[0].bounce2}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth3"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth3" styleId="lastMonth3" value="${obligationDetail[0].lastMonth3}" onchange="return checkDate('lastMonth3');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce3"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce3" styleId="bounce3" styleClass="text" value="${obligationDetail[0].bounce3}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth4"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth4" styleId="lastMonth4" value="${obligationDetail[0].lastMonth4}" onchange="return checkDate('lastMonth4');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce4"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce4" styleId="bounce4" styleClass="text" value="${obligationDetail[0].bounce4}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth5"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth5" styleId="lastMonth5" value="${obligationDetail[0].lastMonth5}" onchange="return checkDate('lastMonth5');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce5"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce5" styleId="bounce5" styleClass="text" value="${obligationDetail[0].bounce5}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth6"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth6" styleId="lastMonth6" value="${obligationDetail[0].lastMonth6}" onchange="return checkDate('lastMonth6');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce6"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce6" styleId="bounce6" styleClass="text" value="${obligationDetail[0].bounce6}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth7"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth7" styleId="lastMonth7" value="${obligationDetail[0].lastMonth7}" onchange="return checkDate('lastMonth7');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce7"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce7" styleId="bounce7" styleClass="text" value="${obligationDetail[0].bounce7}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth8"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth8" styleId="lastMonth8" value="${obligationDetail[0].lastMonth8}" onchange="return checkDate('lastMonth8');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce8"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce8" styleId="bounce8" styleClass="text" value="${obligationDetail[0].bounce8}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth9"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth9" styleId="lastMonth9" value="${obligationDetail[0].lastMonth9}" onchange="return checkDate('lastMonth9');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce9"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce9" styleId="bounce9" styleClass="text" value="${obligationDetail[0].bounce9}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth10"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth10" styleId="lastMonth10" value="${obligationDetail[0].lastMonth10}" onchange="return checkDate('lastMonth10');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce10"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce10" styleId="bounce10" styleClass="text" value="${obligationDetail[0].bounce10}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth11"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth11" styleId="lastMonth11" value="${obligationDetail[0].lastMonth11}" onchange="return checkDate('lastMonth11');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce11"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce11" styleId="bounce11" styleClass="text" value="${obligationDetail[0].bounce11}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth12"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth12" styleId="lastMonth12" value="${obligationDetail[0].lastMonth12}" onchange="return checkDate('lastMonth12');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce12"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce12" styleId="bounce12" styleClass="text" value="${obligationDetail[0].bounce12}"  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
<!-- Nishant Space ends on 15-05-2013 -->
		 <tr>
		 <td><bean:message key="lbl.obligationType"/></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="${obligationDetail[0].obligationType}">
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="${obligationDetail[0].financeAmount}"
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 
		<tr>
		 <td ><bean:message key="lbl.mob"/></td>
		<td nowrap="nowrap">
		 <html:text property="mob" styleId="mob" styleClass="text" value="${obligationDetail[0].mob}"  />
	    <%-- <html:option value="">Select</html:option>
	    <html:optionsCollection name="mobList" label="description" value="value"/>
	    </html:select> --%>
         </td>
     
     	<td width="13%"><bean:message key="lbl.loanStartDate" /></td>
     	<td width="13%"> 
     		<html:text  styleClass="text" styleId="loanStartDate" property="loanStartDate" value="${obligationDetail[0].loanStartDate}"	></html:text>
     	</td>
		 </tr>
		 
		 
		 
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${obligationDetail[0].varificationMethod}"  >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" 	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		 <tr>
		  <td align="left" class="form2" colspan="4">
		    <logic:present name="insert">
		   <button type="button" name="Save" class="topformbutton2" title="Alt+V" accesskey="V" id="Save" onclick="return saveObligation();" ><bean:message key="button.save" /></button>
		   </logic:present>
		   
		    <logic:notPresent name="insert">
		    <button type="button" name="Save" class="topformbutton2" title="Alt+V" accesskey="V" id="Save" onclick="return updateObligation(${obligationDetail[0].obligationId});" ><bean:message key="button.save" /></button>
			</logic:notPresent>		    
		     
	        </td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:present>
	 <logic:notPresent name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>      
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<%-- <tr>
		<td ><bean:message key="lbl.customerType"/><font color="red">*</font></td>   <!-- Sarvesh Added <font color="red">*</font> -->
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="" onchange="getCustomerName();"  styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/><font color="red">*</font></td>   <!-- Sarvesh Added <font color="red">*</font> -->
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value=""  styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
		</tr> --%>
		
		<!-- Added By Sarvesh -->
	<tr>
		<td width="20%"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td width="35%" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${obligationDetail[0].customerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'obligationForm','customerName','','', '','','','customerType','lbxCustomerRoleType')"
								value=" " name="dealButton"/>
					<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${obligationDetail[0].lbxCustomerId}"/>
					<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${obligationDetail[0].lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="20%"><bean:message key="lbl.entityCustomerName"/><font color="red">*</font></td>
	<td width="35%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		<td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" value="" maxlength="50" /></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" value="" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts -->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" /></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" onchange="hideShowManFields();">
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
<!--		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>-->
<!--			 <td width="35%">-->
<!--			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>-->
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" >
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" maxlength="50" /></td>
		
				<td><bean:message key="lbl.vanillaProgramStatus"/></td>
		   	
		   	
		   			<td nowrap="nowrap" ><html:select  styleClass="text" property="vanillaProgramStatus" styleId="vanillaProgramStatus" value="${obligationDetail[0].vanillaProgramStatus}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="vanillaProgramStatusList" label="description" value="value"/>
	    			</html:select></td>
		
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red" id="hid">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		
		<td><bean:message key="lbl.remainingTenure"/><font color="red" id="hid1">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="" maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/><font color="red" id="hid2">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" styleId="maturityDate" value="" onchange="return checkDate('maturityDate');" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" value="" maxlength="50" />
		    </label></td>
		 </tr>
<!-- Nishant Space Starts on 15-05-2013 -->
		  <tr>
		   		<td><bean:message key="lbl.lastStatementDate"/><font color="red">*</font></td>
		   		<logic:present name="obligationList">
		   			<td nowrap="nowrap" ><html:text  styleClass="text" property="lastStatementDate" styleId="lastStatementDate1" readonly="true" value="${obligationList[0].lastStatementDate}" maxlength="20" />  </td>
		   		</logic:present>
		   		<logic:notPresent name="obligationList">
		   			<td nowrap="nowrap" ><html:text  styleClass="text" property="lastStatementDate" styleId="lastStatementDate"  value="" onchange="return checkDate('lastStatementDate');" maxlength="20" />  </td>
		   		</logic:notPresent>
		   		
		   		<td><bean:message key="lbl.dcProgramStatue"/></td>
		   		
		   			<td nowrap="nowrap" >
		   			<html:select  styleClass="text" property="status" styleId="status"  value="" > 
		   			 <html:option value="">Select</html:option>
	    			 <html:optionsCollection name="statusList" label="description" value="value"/>
	    			</html:select>
		   			 </td>
		   		
		   		
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth1"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth1" styleId="lastMonth1" value="" onchange="return checkDate('lastMonth1');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce1"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce1" styleId="bounce1" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth2"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth2" styleId="lastMonth2" value="" onchange="return checkDate('lastMonth2');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce2"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce2" styleId="bounce2" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth3"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth3" styleId="lastMonth3" value="" onchange="return checkDate('lastMonth3');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce3"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce3" styleId="bounce3" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth4"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth4" styleId="lastMonth4" value="" onchange="return checkDate('lastMonth4');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce4"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce4" styleId="bounce4" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth5"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth5" styleId="lastMonth5" value="" onchange="return checkDate('lastMonth5');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce5"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce5" styleId="bounce5" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth6"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth6" styleId="lastMonth6" value="" onchange="return checkDate('lastMonth6');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce6"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce6" styleId="bounce6" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth7"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth7" styleId="lastMonth7" value="" onchange="return checkDate('lastMonth7');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce7"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce7" styleId="bounce7" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth8"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth8" styleId="lastMonth8" value="" onchange="return checkDate('lastMonth8');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce8"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce8" styleId="bounce8" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth9"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth9" styleId="lastMonth9" value="" onchange="return checkDate('lastMonth9');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce9"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce9" styleId="bounce9" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth10"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth10" styleId="lastMonth10" value="" onchange="return checkDate('lastMonth10');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce10"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce10" styleId="bounce10" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth11"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth11" styleId="lastMonth11" value="" onchange="return checkDate('lastMonth11');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce11"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce11" styleId="bounce11" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth12"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth12" styleId="lastMonth12" value="" onchange="return checkDate('lastMonth12');" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce12"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce12" styleId="bounce12" styleClass="text" value=""  >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
<!-- Nishant Space ends on 15-05-2013 -->
		 <tr>
		 <td><bean:message key="lbl.obligationType"/></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value=""  >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="" 
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 
		 	 <tr>
		 <td ><bean:message key="lbl.mob"/></td>
		<td nowrap="nowrap">
		 <html:text property="mob" styleId="mob" styleClass="text"  value="" />
	    <%-- <html:option value="">Select</html:option>
	    <html:optionsCollection name="mobList" label="description" value="value"/>
	    </html:select> --%>
         </td>
     
     	<td width="13%"><bean:message key="lbl.loanStartDate" /></td>
     	<td width="13%"> 
     		<html:text  styleClass="text" styleId="loanStartDate" property="loanStartDate"  value=""	></html:text>
     	</td>
		 </tr>
		 
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     	</td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" 	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
     
		 </tr>
		 <tr>
		 
		  <td align="left" class="form2" colspan="4"> 
		   
		   <button type="button" name="Save" title="Alt+V" accesskey="V" class="topformbutton2" id="Save" onclick="return saveObligation();" ><bean:message key="button.save" /></button>
		    <button type="button" class="topformbutton2" onclick="obligatoinClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
	        </td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:notPresent>
	 </logic:notPresent>
	 
	 <logic:present name="fundFlowAuthor">
	 <fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>         
	 		      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<%-- <tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${obligationDetail[0].customerType}" onchange="getCustomerName();" disabled="true" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" disabled="true" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
		</tr> --%>
		
		<!-- Added By Sarvesh -->
	<tr>
		<td width="20%"><bean:message key="lbl.customerType"/><font color="red">*</font></td>
		<td width="35%" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${obligationDetail[0].customerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'obligationForm','customerName','','', '','','','customerType','lbxCustomerRoleType')"
								value=" " name="dealButton"/>
					<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${obligationDetail[0].lbxCustomerId}"/>
					<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${obligationDetail[0].lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="20%"><bean:message key="lbl.customerName"/></td>
	<td width="35%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		
		<tr>
		<td width="20%"><bean:message key="credit.name"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" value="${obligationDetail[0].institutionName}" maxlength="50" readonly="true"/></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" value="${obligationDetail[0].outstandingLoanAmount}" maxlength="22" 
	         readonly="true" style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts for fundFlowAuthor-->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" readonly="true"/></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" disabled="true" onchange="hideShowManFields();">
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
<!--		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>-->
<!--			 <td width="35%">-->
<!--			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" readonly="true" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>-->
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" readonly="true" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" readonly="true" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" disabled="true">
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" readonly="true" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" readonly="true" maxlength="50" /></td>
		
				<td><bean:message key="lbl.vanillaProgramStatus"/></td>
		   	
		   	
		   			<td nowrap="nowrap" ><html:select  styleClass="text" disabled="true" property="vanillaProgramStatus" styleId="vanillaProgramStatus" value="${obligationDetail[0].vanillaProgramStatus}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="vanillaProgramStatusList" label="description" value="value"/>
	    			</html:select></td>
	    			
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="${obligationDetail[0].emiAmount}" maxlength="22" readonly="true"
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.tenure"/></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="${obligationDetail[0].tenure}" maxlength="3" readonly="true"
		    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" styleId="maturityDate1" value="${obligationDetail[0].maturityDate}" maxlength="20" readonly="true"/>  </td>
		   <td><bean:message key="lbl.purpose"/></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" value="${obligationDetail[0].purpose}" maxlength="50" readonly="true"/>
		    </label></td>
		 </tr>
		 		 <tr>
		 <td><bean:message key="lbl.obligationType"/></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="${obligationDetail[0].obligationType}" disabled="true" >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="${obligationDetail[0].financeAmount}" readonly="true"
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 
		 	 <tr>
		 <td ><bean:message key="lbl.mob"/></td>
		<td nowrap="nowrap">
		 <html:text property="mob" styleId="mob" styleClass="text" readonly="true" value="${obligationDetail[0].mob}"  />
	   <%--  <html:option value="">Select</html:option>
	    <html:optionsCollection name="mobList" label="description" value="value"/>
	    </html:select> --%>
         </td>
     
     	<td width="13%"><bean:message key="lbl.loanStartDate" /></td>
     	<td width="13%"> 
     		<html:text  styleClass="text" styleId="loanStartDate" property="loanStartDate" readonly="true" value="${obligationDetail[0].loanStartDate}"	></html:text>
     	</td>
		 </tr>
		 
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${obligationDetail[0].varificationMethod}" disabled="true" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" readonly="readonly"	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:present>
	  <br/>
	<logic:present name="fundFlowDealId">
	
<fieldset>	
<!--		 <legend><bean:message key="stakeObligation.details"/> </legend>  -->

  
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	<logic:present name="fundFlowAuthor">
    		<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="contact.select" /></b>
    	</logic:present>
    	 <logic:notPresent name="fundFlowAuthor">
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	 </logic:notPresent></td> 
        <td ><b><bean:message key="credit.name"/></b> </td>
		<td ><strong><bean:message key="lbl.outstandingLoanAmount"/> </strong></td>
		<td ><b><bean:message key="lbl.customerType"/> </b></td>    <!-- sarvesh Added -->
		 <td ><b><bean:message key="lbl.customerName"/> </b></td>   <!-- sarvesh Added -->
        <td ><b><bean:message key="lbl.emiAmount"/> </b></td>
        <td ><strong><bean:message key="lbl.tenure"/></strong></td>
        <td ><b><bean:message key="lbl.closureDate"/> </b></td>
        <td ><strong><bean:message key="lbl.purpose"/></strong></td>
	</tr>
	
	
		<logic:present name="obligationList">
			<logic:iterate id="subObligationList" name="obligationList">
				<tr class="white1">
					<td >
					<logic:present name="fundFlowAuthor">
    					<input type="checkbox" name="chk" id="chk" value="${subObligationList.obligationId }" disabled="disabled"/>
    				</logic:present>
    	 			<logic:notPresent name="fundFlowAuthor">
    					<input type="checkbox" name="chk" id="chk" value="${subObligationList.obligationId }"/>
    	 			</logic:notPresent>
					
					</td>
					<td ><a href="#" id="anchor0" onclick="return getObligation(${subObligationList.obligationId });">${subObligationList.institutionName }</a></td>
					<td >${subObligationList.outstandingLoanAmount }</td>
					<td>${subObligationList.customerType }</td>   <!-- sarvesh Added -->
					<td>${subObligationList.customerName }</td>   <!-- sarvesh Added -->
					<td>${subObligationList.emiAmount }</td>
					<td>${subObligationList.tenure }</td>
					<td >${subObligationList.maturityDate }</td>
					<td>${subObligationList.purpose }</td>
				</tr>	
			</logic:iterate>
			<input type="hidden" id="dealId" value="${subObligationList.dealId }"/>
		</logic:present>	
	
	
 </table>    </td>
</tr>

</table></fieldset>
	<logic:notPresent name="fundFlowAuthor">
		<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteObligation();"><bean:message key="button.delete" /></button>
	</logic:notPresent>
</logic:present>
	</html:form>
</logic:notPresent>
	
<%--   -------------- -------------------- ---------View mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">
	<html:form action="/saveobligationDetail"  styleId="obligationForm">
	<input type="hidden" id="contextPath" value="<%=path %>" />	
	<logic:notPresent name="fundFlowAuthor">
	<logic:present name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>         
	   
	
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<%-- <tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${obligationDetail[0].customerType}" onchange="getCustomerName();" disabled="true" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" disabled="true" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
		</tr> --%>
		
		
		<!-- Added By Sarvesh -->
	<tr>
		<td width="20%"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td width="35%" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${obligationDetail[0].customerType}" readonly="true" tabindex="-1"/>
  				<!-- <input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'obligationForm','customerName','','', '','','','customerType','lbxCustomerRoleType')"
								value=" " name="dealButton"/> -->
					<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${obligationDetail[0].lbxCustomerId}"/>
					<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${obligationDetail[0].lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="20%"><bean:message key="lbl.customerName"/><font color="red">*</font>></td>
	<td width="35%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
		 <td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" value="${obligationDetail[0].institutionName}" maxlength="50" readonly="true"/></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" value="${obligationDetail[0].outstandingLoanAmount}" readonly="true" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts for View mode-->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" readonly="true"/></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" disabled="true" onchange="hideShowManFields();">
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
<!--		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>-->
<!--			 <td width="35%">-->
<!--			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" readonly="true" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>-->
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" readonly="true" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" readonly="true" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" disabled="true">
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" readonly="true" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" readonly="true" maxlength="50" /></td>
		</tr>
				<td><bean:message key="lbl.vanillaProgramStatus"/></td>
		   	
		   	
		   			<td nowrap="nowrap" ><html:select  styleClass="text" disabled="true" property="vanillaProgramStatus" styleId="vanillaProgramStatus" value="${obligationDetail[0].vanillaProgramStatus}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="vanillaProgramStatusList" label="description" value="value"/>
	    			</html:select></td>
		
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red" id="hid">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="${obligationDetail[0].emiAmount}" readonly="true" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.remainingTenure"/><font color="red" id="hid1">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" readonly="true" value="${obligationDetail[0].tenure}" maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/><font color="red" id="hid2">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" readonly="true" styleId="maturityDate1" value="${obligationDetail[0].maturityDate}" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" readonly="true" value="${obligationDetail[0].purpose}" maxlength="50" />
		    </label></td>
		 </tr>
		 
		 <!-- Nishant Space Starts on 15-05-2013 -->
		  <tr>
		   		<td><bean:message key="lbl.lastStatementDate"/><font color="red">*</font></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastStatementDate" styleId="lastStatementDate1" readonly="true" value="${obligationDetail[0].lastStatementDate}" maxlength="20" />  </td>
		  
		  <td><bean:message key="lbl.dcProgramStatue"/></td>
		   		
		   			<td nowrap="nowrap" >
		   			<html:select  styleClass="text" property="status" styleId="status" disabled="true" value="${obligationList[0].status}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="statusList" label="description" value="value"/>
	    			</html:select>
		   			</td>
		   		
		   	
		  
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth1"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth1" styleId="lastMonth" readonly="true" value="${obligationDetail[0].lastMonth1}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce1"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce1" styleId="bounce1" styleClass="text" value="${obligationDetail[0].bounce1}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth2"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth2" styleId="lastMonth22" readonly="true" value="${obligationDetail[0].lastMonth2}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce2"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce2" styleId="bounce2" styleClass="text" value="${obligationDetail[0].bounce2}" disabled="true" >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth3"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth3" styleId="lastMonth33" readonly="true" value="${obligationDetail[0].lastMonth3}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce3"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce3" styleId="bounce3" styleClass="text" value="${obligationDetail[0].bounce3}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth4"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth4" styleId="lastMonth44" readonly="true" value="${obligationDetail[0].lastMonth4}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce4"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce4" styleId="bounce4" styleClass="text" value="${obligationDetail[0].bounce4}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth5"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth5" styleId="lastMonth55" readonly="true" value="${obligationDetail[0].lastMonth5}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce5"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce5" styleId="bounce5" styleClass="text" value="${obligationDetail[0].bounce5}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth6"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth6" styleId="lastMonth66" readonly="true" value="${obligationDetail[0].lastMonth6}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce6"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce6" styleId="bounce6" styleClass="text" value="${obligationDetail[0].bounce6}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth7"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth7" styleId="lastMonth77" readonly="true" value="${obligationDetail[0].lastMonth7}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce7"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce7" styleId="bounce7" styleClass="text" value="${obligationDetail[0].bounce7}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth8"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth8" styleId="lastMonth88" readonly="true" value="${obligationDetail[0].lastMonth8}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce8"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce8" styleId="bounce8" styleClass="text" value="${obligationDetail[0].bounce8}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth9"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth9" styleId="lastMonth99" readonly="true" value="${obligationDetail[0].lastMonth9}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce9"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce9" styleId="bounce9" styleClass="text" value="${obligationDetail[0].bounce9}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth10"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth10" styleId="lastMonth100" readonly="true" value="${obligationDetail[0].lastMonth10}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce10"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce10" styleId="bounce10" styleClass="text" value="${obligationDetail[0].bounce10}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth11"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth11" styleId="lastMonth111" readonly="true" value="${obligationDetail[0].lastMonth11}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce11"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce11" styleId="bounce11" styleClass="text" value="${obligationDetail[0].bounce11}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth12"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth12" styleId="lastMonth122" readonly="true" value="${obligationDetail[0].lastMonth12}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce12"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce12" styleId="bounce12" styleClass="text" value="${obligationDetail[0].bounce12}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
<!-- Nishant Space ends on 15-05-2013 -->
		 
		 <tr>
		 <td><bean:message key="lbl.obligationType"/></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="${obligationDetail[0].obligationType}" disabled="true" >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="${obligationDetail[0].financeAmount}" readonly="true"
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 
		 	 <tr>
		 <td ><bean:message key="lbl.mob"/></td>
		<td nowrap="nowrap">
		 <<html:text property="mob" styleId="mob" styleClass="text" readonly="true" value="${obligationDetail[0].mob}" />
	   <%--  <html:option value="">Select</html:option>
	    <html:optionsCollection name="mobList" label="description" value="value"/>
	    </html:select> --%>
         </td>
     
     	<td width="13%"><bean:message key="lbl.loanStartDate" /></td>
     	<td width="13%"> 
     		<html:text  styleClass="text" styleId="loanStartDate" property="loanStartDate" readonly="true" value="${obligationDetail[0].loanStartDate}"	></html:text>
     	</td>
		 </tr>
		 
		 
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${obligationDetail[0].varificationMethod}" disabled="true" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" 	readonly="readonly" onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		 <tr>
	
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:present>
	 <logic:notPresent name="obligationDetail">
	<fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>      
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<%-- <tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="" onchange="getCustomerName();" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value=""  styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
		</tr> --%>
		
		<!-- Added By Sarvesh -->
	<tr>
		<td width="20%"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td width="35%" nowrap="nowrap">

		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${obligationDetail[0].customerType}" readonly="true" tabindex="-1"/>
  			<!-- 	<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'obligationForm','customerName','','', '','','','customerType','lbxCustomerRoleType')"
								value=" " name="dealButton"/> -->
					<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${obligationDetail[0].lbxCustomerId}"/>
					<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${obligationDetail[0].lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="20%"><bean:message key="lbl.entityCustomerName"/><font color="red">*</font></td>
	<td width="35%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
					
		   <td width="20%"><bean:message key="credit.name"/><font color="red">*</font> </td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName" value="" readonly="true" maxlength="50" /></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount" readonly="true" value="" maxlength="22" 
	         style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts for fundFlowAuthor-->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" readonly="true"/></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" disabled="true" onchange="hideShowManFields();">
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
<!--		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>-->
<!--			 <td width="35%">-->
<!--			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" readonly="true" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>-->
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" readonly="true" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" readonly="true" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" disabled="true">
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" readonly="true" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" readonly="true" maxlength="50" /></td>
				<td><bean:message key="lbl.vanillaProgramStatus"/></td>
		   	
		   	
		   			<td nowrap="nowrap" ><html:select  styleClass="text" disabled="true" property="vanillaProgramStatus" styleId="vanillaProgramStatus" value="${obligationDetail[0].vanillaProgramStatus}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="vanillaProgramStatusList" label="description" value="value"/>
	    			</html:select></td>
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/><font color="red" id="hid">*</font> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" readonly ="true" maxlength="22" 
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.remainingTenure"/><font color="red" id="hid1">*</font></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value=""  readonly ="true"  maxlength="3" 
		    style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/><font color="red" id="hid2">*</font></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" styleId="maturityDate1"  readonly ="true"  value="" maxlength="20" />  </td>
		   <td><bean:message key="lbl.purpose"/></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" value=""  readonly ="true"  maxlength="50" />
		    </label></td>
		 </tr>
		 
		  <!-- Nishant Space Starts on 15-05-2013 -->
		  <tr>
		   		<td><bean:message key="lbl.lastStatementDate"/><font color="red">*</font></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastStatementDate" styleId="lastStatementDate1" readonly="true" value="${obligationDetail[0].lastStatementDate}" maxlength="20" />  </td>
		 
		 <td><bean:message key="lbl.dcProgramStatue"/></td>
		   		
		   			<td nowrap="nowrap" >
		   			<html:select  styleClass="text" property="status" styleId="status" disabled="true" value="${obligationList[0].status}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="statusList" label="description" value="value"/>
	    			</html:select>
		   			</td>
		 
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth1"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth1" styleId="lastMonth" readonly="true" value="${obligationDetail[0].lastMonth1}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce1"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce1" styleId="bounce1" styleClass="text" value="${obligationDetail[0].bounce1}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth2"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth2" styleId="lastMonth22" readonly="true" value="${obligationDetail[0].lastMonth2}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce2"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce2" styleId="bounce2" styleClass="text" value="${obligationDetail[0].bounce2}" disabled="true" >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth3"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth3" styleId="lastMonth33" readonly="true" value="${obligationDetail[0].lastMonth3}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce3"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce3" styleId="bounce3" styleClass="text" value="${obligationDetail[0].bounce3}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth4"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth4" styleId="lastMonth44" readonly="true" value="${obligationDetail[0].lastMonth4}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce4"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce4" styleId="bounce4" styleClass="text" value="${obligationDetail[0].bounce4}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth5"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth5" styleId="lastMonth55" readonly="true" value="${obligationDetail[0].lastMonth5}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce5"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce5" styleId="bounce5" styleClass="text" value="${obligationDetail[0].bounce5}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth6"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth6" styleId="lastMonth66" readonly="true" value="${obligationDetail[0].lastMonth6}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce6"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce6" styleId="bounce6" styleClass="text" value="${obligationDetail[0].bounce6}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth7"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth7" styleId="lastMonth77" readonly="true" value="${obligationDetail[0].lastMonth7}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce7"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce7" styleId="bounce7" styleClass="text" value="${obligationDetail[0].bounce7}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth8"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth8" styleId="lastMonth88" readonly="true" value="${obligationDetail[0].lastMonth8}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce8"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce8" styleId="bounce8" styleClass="text" value="${obligationDetail[0].bounce8}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth9"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth9" styleId="lastMonth99" readonly="true" value="${obligationDetail[0].lastMonth9}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce9"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce9" styleId="bounce9" styleClass="text" value="${obligationDetail[0].bounce9}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth10"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth10" styleId="lastMonth100" readonly="true" value="${obligationDetail[0].lastMonth10}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce10"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce10" styleId="bounce10" styleClass="text" value="${obligationDetail[0].bounce10}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth11"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth11" styleId="lastMonth111" readonly="true" value="${obligationDetail[0].lastMonth11}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce11"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce11" styleId="bounce11" styleClass="text" value="${obligationDetail[0].bounce11}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth12"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth12" styleId="lastMonth122" readonly="true" value="${obligationDetail[0].lastMonth12}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce12"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce12" styleId="bounce12" styleClass="text" value="${obligationDetail[0].bounce12}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
<!-- Nishant Space ends on 15-05-2013 -->
		 
		 		 <tr>
		 <td><bean:message key="lbl.obligationType"/></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" disabled="true" value=""  >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" readonly="true" value="" 
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 
		 	 <tr>
		 <td ><bean:message key="lbl.mob"/></td>
		<td nowrap="nowrap">
		 <html:text property="mob" styleId="mob" styleClass="text" disabled="true" value=""  />
	   <%--  <html:option value="">Select</html:option>
	    <html:optionsCollection name="mobList" label="description" value="value"/>
	    </html:select> --%>
         </td>
     
     	<td width="13%"><bean:message key="lbl.loanStartDate" /></td>
     	<td width="13%"> 
     		<html:text  styleClass="text" styleId="loanStartDate" property="loanStartDate" readonly="true" value=""	></html:text>
     	</td>
		 </tr>
		 
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" disabled="true" styleClass="text" value=""  >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     	<td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" readonly="readonly"	name="comment" 	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		 <tr>
		
		  </tr>
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:notPresent>
	 </logic:notPresent>
	 
	 <logic:present name="fundFlowAuthor">
	 <fieldset>
	
		  <legend><bean:message key="lbl.obligation"/></legend>         
	 		      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<%-- <tr>
		<td ><bean:message key="lbl.customerType"/></td>
		<td  nowrap="nowrap">
		<html:select property="customerType" styleId="customerType" value="${obligationDetail[0].customerType}" onchange="getCustomerName();" disabled="true" styleClass="text">
		<html:optionsCollection name="customerTypeList" value="parameCode" label="paramName"/>
		</html:select>
			
		     </td>
			<td ><bean:message key="lbl.customerName"/></td>
			<td >
			<div id="customerNameDiv">
			<html:select property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" disabled="true" styleClass="text">
			<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
			</html:select>
			</div>
			</td>
		</tr>
		 --%>
	<!-- Added By Sarvesh -->
		<tr>
		<td width="20%"><bean:message key="lbl.entityCustomerType"/><font color="red">*</font></td>
		<td width="35%"nowrap="nowrap">
					
		<html:text property="customerType" styleId="customerType" styleClass="text" maxlength="50" value="${obligationDetail[0].customerType}" readonly="true" tabindex="-1"/>
  				<input type="button" class="lovbutton" id="customerTypeButton"
								onClick="openLOVCommon(6091,'obligationForm','customerName','','', '','','','customerType','lbxCustomerRoleType')"
								value=" " name="dealButton"/>
					<input type="hidden" name="lbxCustomerId" id="lbxCustomerId" value="${obligationDetail[0].lbxCustomerId}"/>
					<input type="hidden" name="lbxCustomerRoleType" id="lbxCustomerRoleType" value="${obligationDetail[0].lbxCustomerRoleType}"/>
								
								
     </td>
	<td width="20%"><bean:message key="lbl.entityCustomerName"/><font color="red">*</font></td>
	<td width="35%">
	<html:text  styleClass="text" property="customerName" styleId="customerName" value="${obligationDetail[0].customerName}" maxlength="50" readonly="true" tabindex="-1"/>
    </td>
		</tr>
		
		<tr>
					
		   <td width="20%"><bean:message key="credit.name"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="institutionName" styleId="institutionName"  readonly ="true"  value="${obligationDetail[0].institutionName}" maxlength="50" readonly="true"/></td>
	         <td><bean:message key="lbl.outstandingLoanAmount"/></td>
	         <td><html:text  styleClass="text" property="outstandingLoanAmount" styleId="outstandingLoanAmount"  readonly ="true"  value="${obligationDetail[0].outstandingLoanAmount}" maxlength="22" 
	         readonly="true" style="text-align: right;"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<!-- Nishant space starts for fundFlowAuthor-->
		<tr>		
		   <td width="20%"><bean:message key="lbl.LoanNo"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanNo" styleId="loanNo" value="${obligationDetail[0].loanNo}" maxlength="50" readonly="true"/></td>
	         <td><bean:message key="lbl.typeOfLoan"/></td>
	         <td nowrap="nowrap">
				 <html:select property="typeOfLoan" styleId="typeOfLoan" styleClass="text" value="${obligationDetail[0].typeOfLoan}" disabled="true" onchange="hideShowManFields();">
			    <html:option value="">Select</html:option>
			    <html:optionsCollection name="typeOfLoanList" label="typeOfLoanValueId" value="typeOfLoanValue"/>
			    </html:select>
	     	</td>
		</tr>
		
		<tr>		
<!--		   <td width="20%"><bean:message key="lbl.loanAmount"/></td>-->
<!--			 <td width="35%">-->
<!--			<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${obligationDetail[0].loanAmount}" readonly="true" maxlength="22" style="text-align: right;" onkeypress="return numbersonly(event,id,22)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 22,id);"	onfocus="keyUpNumber(this.value, event, 22,id);" /></td>-->
	         <td><bean:message key="lbl.loanMonth"/></td>
	         <td><html:text  styleClass="text" property="loanMonth" styleId="loanMonth" value="${obligationDetail[0].loanMonth}" maxlength="7" readonly="true" onblur="checkFormate(this.value,this.id)"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.tenure"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="loanTenure" styleId="loanTenure" value="${obligationDetail[0].loanTenure}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.emiPaid"/></td>
	         <td><html:text  styleClass="text" property="emiPaid" styleId="emiPaid" value="${obligationDetail[0].emiPaid}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPending"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPending" styleId="emiPending" value="${obligationDetail[0].emiPending}" readonly="true" maxlength="10" style="text-align: right;" onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 10,id);"	onfocus="keyUpNumber(this.value, event, 10,id);" /></td>
	         <td><bean:message key="lbl.loanStatus"/></td>
	         <td><html:text  styleClass="text" property="loanStatus" styleId="loanStatus" value="${obligationDetail[0].loanStatus}" readonly="true" maxlength="50"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.obligationConsider"/></td>
			 <td nowrap="nowrap">
				 <html:select property="obligationConsider" styleId="obligationConsider" styleClass="text" value="${obligationDetail[0].obligationConsider}" disabled="true">
				    <html:option value="">Select</html:option>
				    <html:option value="Y">Yes</html:option>
				    <html:option value="N">No</html:option>
			    </html:select>
	     	</td>
	         <td><bean:message key="lbl.trackPer"/></td>
	         <td><html:text  styleClass="text" property="trackPer" styleId="trackPer" value="${obligationDetail[0].trackPer}" readonly="true" maxlength="100"/></td>
		</tr>
		
		<tr>		
		   <td width="20%"><bean:message key="lbl.emiPaidFrom"/></td>
			 <td width="35%">
			<html:text  styleClass="text" property="emiPaidFrom" styleId="emiPaidFrom" value="${obligationDetail[0].emiPaidFrom}" readonly="true" maxlength="50" /></td>
		
			<td><bean:message key="lbl.vanillaProgramStatus"/></td>
		   	
		   	
		   			<td nowrap="nowrap" ><html:select  styleClass="text" disabled="true" property="vanillaProgramStatus" styleId="vanillaProgramStatus" value="${obligationDetail[0].vanillaProgramStatus}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="vanillaProgramStatusList" label="description" value="value"/>
	    			</html:select></td>
		</tr>
		<!-- Nishant space ends -->
	  <tr>
	    <td><bean:message key="lbl.emiAmount"/> </td>
	    <td><html:text  styleClass="text" property="emiAmount" styleId="emiAmount" value="${obligationDetail[0].emiAmount}"  readonly ="true"  maxlength="22" readonly="true"
	    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.tenure"/></td>
		    <td nowrap="nowrap" ><html:text  styleClass="text" property="tenure" styleId="tenure" value="${obligationDetail[0].tenure}"  readonly ="true"  maxlength="3" readonly="true"
		    style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>  </td>
	  </tr>
		 <tr>
		   <td><bean:message key="lbl.sdMaturityDate"/></td>
		   <td nowrap="nowrap" ><html:text  styleClass="text" property="maturityDate" styleId="maturityDate1" value="${obligationDetail[0].maturityDate}"  readonly ="true"  maxlength="20" readonly="true"/>  </td>
		   <td><bean:message key="lbl.purpose"/></td>
		    <td nowrap="nowrap" ><label>
		    <html:text  styleClass="text" property="purpose" styleId="purpose" value="${obligationDetail[0].purpose}" maxlength="50" readonly="true"/>
		    </label></td>
		 </tr>
		 
		 <!-- Nishant Space Starts on 15-05-2013 -->
		  <tr>
		   		<td><bean:message key="lbl.lastStatementDate"/><font color="red">*</font></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastStatementDate" styleId="lastStatementDate1" readonly="true" value="${obligationDetail[0].lastStatementDate}" maxlength="20" />  </td>
		  <td><bean:message key="lbl.dcProgramStatue"/></td>
		   		
		   			<td nowrap="nowrap" >
		   			<html:select  styleClass="text" property="status" styleId="status" disabled="true" value="${obligationList[0].status}" >  
		   			 	 <html:option value="">Select</html:option>
	    				<html:optionsCollection name="statusList" label="description" value="value"/>
	    			</html:select>
		   			</td>
		  
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth1"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth1" styleId="lastMonth" readonly="true" value="${obligationDetail[0].lastMonth1}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce1"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce1" styleId="bounce1" styleClass="text" value="${obligationDetail[0].bounce1}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth2"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth2" styleId="lastMonth22" readonly="true" value="${obligationDetail[0].lastMonth2}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce2"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce2" styleId="bounce2" styleClass="text" value="${obligationDetail[0].bounce2}" disabled="true" >
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth3"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth3" styleId="lastMonth33" readonly="true" value="${obligationDetail[0].lastMonth3}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce3"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce3" styleId="bounce3" styleClass="text" value="${obligationDetail[0].bounce3}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth4"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth4" styleId="lastMonth44" readonly="true" value="${obligationDetail[0].lastMonth4}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce4"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce4" styleId="bounce4" styleClass="text" value="${obligationDetail[0].bounce4}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth5"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth5" styleId="lastMonth55" readonly="true" value="${obligationDetail[0].lastMonth5}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce5"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce5" styleId="bounce5" styleClass="text" value="${obligationDetail[0].bounce5}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth6"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth6" styleId="lastMonth66" readonly="true" value="${obligationDetail[0].lastMonth6}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce6"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce6" styleId="bounce6" styleClass="text" value="${obligationDetail[0].bounce6}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth7"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth7" styleId="lastMonth77" readonly="true" value="${obligationDetail[0].lastMonth7}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce7"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce7" styleId="bounce7" styleClass="text" value="${obligationDetail[0].bounce7}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth8"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth8" styleId="lastMonth88" readonly="true" value="${obligationDetail[0].lastMonth8}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce8"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce8" styleId="bounce8" styleClass="text" value="${obligationDetail[0].bounce8}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth9"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth9" styleId="lastMonth99" readonly="true" value="${obligationDetail[0].lastMonth9}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce9"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce9" styleId="bounce9" styleClass="text" value="${obligationDetail[0].bounce9}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth10"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth10" styleId="lastMonth100" readonly="true" value="${obligationDetail[0].lastMonth10}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce10"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce10" styleId="bounce10" styleClass="text" value="${obligationDetail[0].bounce10}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth11"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth11" styleId="lastMonth111" readonly="true" value="${obligationDetail[0].lastMonth11}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce11"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce11" styleId="bounce11" styleClass="text" value="${obligationDetail[0].bounce11}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
		  <tr>
		   		<td><bean:message key="lbl.lastMonth12"/></td>
		   		<td nowrap="nowrap" ><html:text  styleClass="text" property="lastMonth12" styleId="lastMonth122" readonly="true" value="${obligationDetail[0].lastMonth12}" maxlength="20" />  </td>
		   		<td><bean:message key="lbl.bounce12"/></td>
		    	<td nowrap="nowrap" >
		    		<html:select property="bounce12" styleId="bounce12" styleClass="text" value="${obligationDetail[0].bounce12}"  disabled="true">
					     <html:option value="">Select</html:option>
					     <html:option value="Y">Yes</html:option>
					     <html:option value="N">No</html:option>
				     </html:select>
		    	</td>
		  </tr>
<!-- Nishant Space ends on 15-05-2013 -->
		 
		 		 <tr>
		 <td><bean:message key="lbl.obligationType"/></td>
		 <td>
		 
		 <html:select property="obligationType" styleId="obligationType" styleClass="text" value="${obligationDetail[0].obligationType}" disabled="true" >
	     <html:option value="">Select</html:option>
	     <html:optionsCollection name="obligationTypeList" label="paramName" value="parameCode"/>
	     </html:select>
		 </td>
		 <td><bean:message key="lbl.financeAmount"/></td>
		 <td><html:text  styleClass="text" property="financeAmount" styleId="financeAmount" value="${obligationDetail[0].financeAmount}" readonly="true"
            style="text-align: right;" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" />  </td>
		 </tr>
		 
		 <tr>
		 <td ><bean:message key="lbl.mob"/></td>
		<td nowrap="nowrap">
		 <html:text property="mob" styleId="mob" styleClass="text" readonly="true" value="${obligationDetail[0].mob}"  />
	   <%--  <html:option value="">Select</html:option>
	    <html:optionsCollection name="mobList" label="description" value="value"/>
	    </html:select> --%>
         </td>
     
     	<td width="13%"><bean:message key="lbl.loanStartDate" /></td>
     	<td width="13%"> 
     		<html:text  styleClass="text" styleId="loanStartDate" property="loanStartDate" readonly="true" value="${obligationDetail[0].loanStartDate}"	></html:text>
     	</td>
		 </tr>
		 
		 <tr>
		 <td ><bean:message key="lbl.varificationmethod"/></td>
		<td nowrap="nowrap">
		 <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${obligationDetail[0].varificationMethod}" disabled="true" >
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
     </td>
     <td width="13%"><bean:message key="lbl.comment" /></td>
     	<td width="13%"> 
     		<textarea  class="text" id="comment" 	name="comment" readonly="readonly" 	onkeypress="textCounter(this,this.form.counter,999);">${obligationDetail[0].comment}</textarea>
     	</td>
		 </tr>
		
		</table>
		
	      </td>
	</tr>
	</table>
	</fieldset>
	 </logic:present>
	  <br/>
	<logic:present name="fundFlowDealId">
	
<fieldset>	
<!--		 <legend><bean:message key="stakeObligation.details"/> </legend>  -->

  
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	<logic:present name="fundFlowAuthor">
    		<b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/><bean:message key="contact.select" /></b>
    	</logic:present>
    	 <logic:notPresent name="fundFlowAuthor">
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	 </logic:notPresent></td> 
        <td ><b><bean:message key="credit.name"/></b> </td>
		<td ><strong><bean:message key="lbl.outstandingLoanAmount"/> </strong></td>
		<td ><b><bean:message key="lbl.customerType"/> </b></td>        <!-- sarvesh added -->
		 <td ><b><bean:message key="lbl.customerName"/> </b></td>       <!-- sarvesh added -->
        <td ><b><bean:message key="lbl.emiAmount"/> </b></td>
        <td ><strong><bean:message key="lbl.tenure"/></strong></td>
        <td ><b><bean:message key="lbl.closureDate"/> </b></td>
        <td ><strong><bean:message key="lbl.purpose"/></strong></td>
	</tr>
	
	
		<logic:present name="obligationList">
			<logic:iterate id="subObligationList" name="obligationList">
				<tr class="white1">
					<td >
					<logic:present name="fundFlowAuthor">
    					<input type="checkbox" name="chk" id="chk" value="${subObligationList.obligationId }" disabled="disabled"/>
    				</logic:present>
    	 			<logic:notPresent name="fundFlowAuthor">
    					<input type="checkbox" name="chk" id="chk" value="${subObligationList.obligationId }"/>
    	 			</logic:notPresent>
					
					</td>
					<td ><a href="#" id="anchor0" onclick="return getObligation(${subObligationList.obligationId });">${subObligationList.institutionName }</a></td>
					<td >${subObligationList.outstandingLoanAmount }</td>
					<td>${subObligationList.customerType }</td>    <!-- sarvesh added -->
					<td>${subObligationList.customerName }</td>    <!-- sarvesh added -->
					<td>${subObligationList.emiAmount }</td>
					<td>${subObligationList.tenure }</td>
					<td >${subObligationList.maturityDate }</td>
					<td>${subObligationList.purpose }</td>
				</tr>	
			</logic:iterate>
			<input type="hidden" id="dealId" value="${subObligationList.dealId }"/>
		</logic:present>	
	
	
 </table>    </td>
</tr>

</table></fieldset>

</logic:present>
	</html:form>
</logic:present>
	
</div></div>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		document.getElementById("maturityDate").focus();
		alert("<bean:message key="lbl.maturityDateLessBussDate" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Del')
	{
		alert("<bean:message key="lbl.dataDeleted" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataNtDeleted" />");
		
	}else if('<%=request.getAttribute("sms").toString()%>'=='A')
	{
		alert("<bean:message key="lbl.dataExist" />");
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	}
	
	</script>
	</logic:present>
	<script>
	setFramevalues("obligationForm");
</script>

</body>
</html:html>