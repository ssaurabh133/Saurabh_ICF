<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.connect.CommonFunction"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="com.login.roleManager.UserObject"%>
<%
      String path = request.getContextPath();
      String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
      Date currentDate = new Date();
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
       SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-mm-dd");
       UserObject userObject = (UserObject)session.getAttribute("userobject");
     LoggerMsg.info("currentDate.... "+currentDate);
     String initiationDate = userObject.getBusinessdate();
     System.out.println("initiationDate:::::::::::"+initiationDate);
    Date convertedDate = dateFormat.parse(initiationDate); 
    
      System.out.println("convertedDate:::::::::::"+convertedDate);
      String initiationDateDbFormat = dateFormat1.format(convertedDate);
     System.out.println("initiationDateDbFormat:::::::::::"+initiationDateDbFormat);
      
%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		 
		<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=path %>/js/cmScript/manualAdviceMaker.js"></script>
		<script type="text/javascript" src="<%=path %>/js/cpScript/creditProcessing.js"></script>
		 <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
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
	<body onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();TaxAmountView();disableAdviceIdField();checkChanges('manualAdviceCreationForm');">
	
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
    </div>
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	  <input type="hidden" name="loanRecStatus" value="${manualAdviceList[0].loanRecStatus}" id="loanRecStatus" />
	   <input type="hidden" name="minChargeAmount" value="${manualAdviceList[0].minChargeAmount}" id="minChargeAmount" />
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<html:form action="/manualAdviceCreationMaker" styleId="manualAdviceCreationForm" method="post">
	 <input type="hidden" name="hcommon" id="hcommon" />
	 <input type="hidden" name="hcommon1" id="hcommon1" />
	 <input type="hidden" id="adviceDtChngFlag" value="N"/>	 
	 	
	 	 
	<logic:present name="adviceUpdate">
		<logic:notPresent name="manualApprove">
		
		
			<fieldset>	  
	  <legend><bean:message key="lbl.manualAdviceCreation"/></legend>         
	   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
		<input type="hidden" name="manaulId" id="manaulId" value="${manualAdviceList[0].manaulId}"/>
	<tr>
	
          <td><bean:message key="lbl.lan"/><font color="red">*</font></td>
	<td> <html:text property="loanAccountNo" styleClass="text" styleId="loanAccountNo" value="${manualAdviceList[0].loanNo}" readonly="true"/>
	
		
		<logic:present name="strParentOption">
		
     		<input type="hidden" name="lbxLoanNoHID" id="lbxLoanNoHID" size="20" value="${sessionScope.strParentOption}"/>
   		</logic:present >
   		<logic:notPresent name="strParentOption">
   		
   		<input type="hidden" name="lbxProductId" id="lbxProductId" size="20" value="${manualAdviceList[0].lbxProductId}"/>
         <input type="hidden" name="lbxSchemeId" id="lbxSchemeId" size="20" value="${manualAdviceList[0].lbxSchemeId}"/>
   			<input type="hidden" name="lbxLoanNoHID" id="lbxLoanNoHID" size="20" value="${manualAdviceList[0].lbxLoanNoHID}"/>
    	</logic:notPresent>
<!--    	<html:button property="bpButton" styleId="loanNoButton" onclick="return openLOVCommon(6,'manualAdviceCreationForm','loanAccountNo','','', '','','','customerName','hcommon','hcommon1');" value=" " styleClass="lovbutton"></html:button>-->
    	
	</td>
	<td><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td><html:text property="customersName" styleClass="text" styleId="customerName" value="${manualAdviceList[0].customersName}" maxlength="100" size="10" readonly="true"/></td> 
          </tr>
			<tr>
	  
          <td><bean:message key="lbl.businessPartnerType"/><font color="red">*</font></td>
         	<td> 
         	
        <html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${manualAdviceList[0].bpTypeDesc}"/> 	
       	<html:hidden property="businessPartnerType" styleClass="text" styleId="businessPartnerType" value="${manualAdviceList[0].businessPartnerType}"/>
        <logic:present name="strParentOption">
     	<input type="hidden" name="lbxBusinessPartnearHID" id="lbxBusinessPartnearHID" size="20" value="${sessionScope.strParentOption}"/>
   		</logic:present >
   		<logic:notPresent name="strParentOption">
   		<input type="hidden" name="lbxBusinessPartnearHID" id="lbxBusinessPartnearHID" size="20" value="${manualAdviceList[0].lbxBusinessPartnearHID}"/>
    	</logic:notPresent>
    	<html:button property="bpButton" styleId="businessPartButton" onclick="openLOVCommon(307,'manualAdviceCreationForm','lbxBusinessPartnearHID','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerType','businessPartnerName','businessPartnerTypeDesc');closeLovCallonLov('loanAccountNo');" value=" " styleClass="lovbutton"></html:button>
    	        	
          </td>
          <td><bean:message key="lbl.businessPartnerName"/><font color="red">*</font></td>
         <td> 
         <html:text property="businessPartnerName" readonly="true" styleClass="text" styleId="businessPartnerName" value="${manualAdviceList[0].bpNameDesc}" />
         </td>
          </tr>	
          
        <tr>
				<td>
					<bean:message key="lbl.manualAdviceDate" />
				</td>
				<td>
						<html:text styleClass="text" property="valueDate"
						styleId="valueDate1" maxlength="10"
						value="${manualAdviceList[0].valueDate}" readonly="true"/>
											
					</td>
					<td></td>
					<td></td>
			</tr>  
          
       <tr>
        <td><bean:message key="lbl.chargeCode"/><font color="red">*</font></td>
		<td>
		<html:text property="chargeCode" styleClass="text" styleId="chargeCode" value="${manualAdviceList[0].chargeCodeDesc}" readonly="true" maxlength="100" size="10"/>
		 <logic:present name="strParentOption">
     	  <input type="hidden" name="lbxCharge" id="lbxCharge" size="20" value="${sessionScope.strParentOption}"/>
   		</logic:present >
   		<logic:notPresent name="strParentOption">
   		  <input type="hidden" name="lbxCharge" id="lbxCharge" size="20" value="${manualAdviceList[0].lbxCharge}"/>
    	</logic:notPresent>
    	 <html:button property="bpButton" styleId="chargeButton" onclick="openLOVCommon(120,'manualAdviceCreationForm','chargeCode','businessPartnerTypeDesc|loanAccountNo','lbxCharge', 'lbxSchemeId|lbxProductId','Select BP Type LOV First|Select Loan Account LOV First','getManualDetails','chargeCode');" value=" " styleClass="lovbutton"></html:button>
<!--    	 <html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(120,'manualAdviceCreationForm','chargeCode','loanAccountNo','lbxCharge', 'lbxLoanNoHID','Select Loan Account LOV First','getManualDetails','chargeCode');" value=" " styleClass="lovbutton"></html:button>-->
    	 
		<input type="hidden" id="chargeId" value=""/>
		</td>
		
		<td><bean:message key="lbl.adviceType"/></td>
		<td>
		<html:select property="adviceType" styleClass="text" styleId="adviceType" value="${manualAdviceList[0].adviceType}">
			<html:option value="P">Payable</html:option>
			<html:option value="R">Receivable</html:option>
		</html:select>
<!--		<html:text property="adviceType" styleClass="text" styleId="adviceType" value="${manualAdviceList[0].adviceType}" readonly="true"/>-->
		</td>
		
         </tr>	
		
		 <tr>
          <td><bean:message key="lbl.taxApplicable"/></td>
		<td><html:text property="taxApplicable" styleClass="text" styleId="taxApplicable" value="${manualAdviceList[0].taxApplicable}" readonly="true"/></td> 
		<td><bean:message key="lbl.tdsApplicable"/></td>
		<td><html:text property="tdsApplicable" styleClass="text" styleId="tdsApplicable" value="${manualAdviceList[0].tdsApplicable}" readonly="true"/></td>
          </tr>
       <tr>
        <td><bean:message key="lbl.taxRate1"/></td>
		<td><html:text property="taxRate1" styleClass="text" styleId="taxRate1" style="text-align: right;" value="${manualAdviceList[0].taxRate1}" readonly="true"/></td>
      
        <td><bean:message key="lbl.taxRate2"/></td>
		<td><html:text property="taxRate2" styleClass="text" styleId="taxRate2" style="text-align: right;" value="${manualAdviceList[0].taxRate2}" readonly="true"/></td>
         </tr>
		 <tr>
        <td><bean:message key="lbl.tdsRate"/></td>
		<td><html:text property="tdsRate" styleClass="text" styleId="tdsRate" style="text-align: right;" value="${manualAdviceList[0].tdsRate}" readonly="true"/></td>
       	<td><bean:message key="lbl.taxInclusive"/></td>
		<td><html:text property="taxInclusive" styleClass="text" styleId="taxInclusive" value="${manualAdviceList[0].taxInclusive}" readonly="true"/></td>
       
       </tr>
       
		<tr>
			 <td><bean:message key="lbl.chargeAmount"/><font color="red">*</font></td>
			<td><html:text property="chargeAmount" styleClass="text" styleId="chargeAmount" style="text-align: right;" value="${manualAdviceList[0].chargeAmount}" maxlength="18" 
			onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onchange="totAdviceAmount();calculateTax();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			
			
			</tr>
			<tr> 
     		<td><bean:message key="lbl.taxAmount1"/></td>
		<td><html:text property="taxAmount1" styleClass="text" styleId="taxAmount1" style="text-align: right;" value="${manualAdviceList[0].taxAmount1}" maxlength="18" readonly="true" disabled="true" onkeypress="return isNumberKey(event);" 
		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);totAdviceAmount();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
       
		</tr>
		<tr>
		<td><bean:message key="lbl.taxAmount2"/></td>
		<td><html:text property="taxAmount2" styleClass="text" styleId="taxAmount2" style="text-align: right;" value="${manualAdviceList[0].taxAmount2}" maxlength="18" readonly="true" disabled="true" onkeypress="return isNumberKey(event);" 
		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);totAdviceAmount();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      </tr>
		
       <tr>
         <td><bean:message key="lbl.adviceAmount"/></td>
		 <td><html:text property="adviceAmount" styleClass="text" styleId="adviceAmount" style="text-align: right;" value="${manualAdviceList[0].adviceAmount}" readonly="true" maxlength="18" 
		 		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  </tr>
	  <tr>
	  <td><bean:message key="lbl.origionalReversal"/><font color="red">*</font></td>
		<td>
		<html:select property="origionalReversal" styleClass="text" styleId="origionalReversal" onchange="disableAdviceIdField();" value="${manualAdviceList[0].origionalReversal}">
			<html:option  value="">---Select---</html:option>
			<html:option value="O">Original</html:option>
			<html:option value="R">Reversal</html:option>
		</html:select>
<!--		<html:text property="adviceType" styleClass="text" styleId="adviceType" value="" readonly="true"/>-->
		</td>
		<td><bean:message key="lbl.orgAdviceId"/></td>
				<td>
			  		<html:text property="orgAdviceId" styleId="orgAdviceId" styleClass="text" readonly="true" value="${manualAdviceList[0].orgAdviceId}"/>
			   		<html:button property="orgAdviceIdButton" styleId="orgAdviceIdButton" onclick="openLOVCommon(19104,'manualAdviceCreationForm','lbxorgAdviceId','origionalReversal|loanAccountNo','', 'lbxLoanNoHID','Select Original/Reversal First|Select loan account No.','','orgAdviceId');" value=" " styleClass="lovbutton"></html:button>
			     	<html:hidden  property="lbxorgAdviceId" styleId="lbxorgAdviceId" value=" "  />
			    </td>
	  </tr>	  
	   <tr >
		  <td width="25%"> <bean:message key="lbl.makerRemark"/></td>
		  <td  width="25%" >
		  <textarea name="remarks" class="text" id="remarks" maxlength="1000">${manualAdviceList[0].remarks}</textarea>
          </td>
			
			
			<td width="25%"><bean:message key="lbl.authorRemarks" /></td>
			<td width="25%" ><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${manualAdviceList[0].authorRemarks}" /></td>
          </tr>  
	  
<!--        <tr>-->
<!--		   <td><bean:message key="lbl.remark"/><font color="red">*</font></td>-->
<!--		<td><html:textarea property="remarks" styleClass="text" styleId="remarks" value="${manualAdviceList[0].remarks}" cols="150" /></td> -->
<!--          </tr>	-->
		</table>
		<tr>
		  <td align="left" >
		  
		 <button type="button" name="button1" title="Alt+V" accesskey="V" class="topformbutton2" value="Save" id="save" onclick="return update('Save','');"><bean:message key="button.save" /></button>
		 
		  <button type="button" class="topformbutton2" title="Alt+F" accesskey="F" value="Save & Forward" id="saveFwd"  onclick="return update('Save & Forward','<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>
		 
		  <button type="button" id="delete" class="topformbutton2" onclick="return deleteManualAdvice();" title="Alt+D" accesskey="D"><bean:message key="button.delete" /></button>
		  </td>
    </tr>
	
	</table>
	 
	</fieldset>
		</logic:notPresent>
		<logic:present name="manualApprove">
		
		
	<fieldset>	  
	  <legend><bean:message key="lbl.manualAdviceCreation"/></legend>         
	   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
		<input type="hidden" name="manaulId" id="manaulId" value="${manualAdviceList[0].manaulId}"/>
		
	<tr>
	
          <td><bean:message key="lbl.lan"/><font color="red">*</font></td>
	<td> <html:text property="loanAccountNo" styleClass="text" styleId="loanAccountNo" value="${manualAdviceList[0].loanNo}" disabled="true"/>
	
		
	</td>
	<td><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td><html:text property="customersName" styleClass="text" styleId="customerName" value="${manualAdviceList[0].customersName}" maxlength="100" size="10" disabled="true" /></td> 
          </tr>
			<tr>
	  
          <td><bean:message key="lbl.businessPartnerType"/><font color="red">*</font></td>
         	<td> 
         	  <html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${manualAdviceList[0].bpTypeDesc}"/> 	
       
      
         	<html:hidden property="businessPartnerType" styleClass="text" styleId="businessPartnerType" value="${manualAdviceList[0].businessPartnerType}" disabled="true"/>
         	
          </td>
          <td><bean:message key="lbl.businessPartnerName"/><font color="red">*</font></td>
         <td> 
         <html:text property="businessPartnerName" styleClass="text" styleId="businessPartnerName" value="${manualAdviceList[0].bpNameDesc}" disabled="true"/>
         
          </td>
          </tr>	
          
          <tr>
				<td>
					<bean:message key="lbl.manualAdviceDate" />
				</td>
				<td>
						<html:text styleClass="text" property="valueDate"
						styleId="valueDate1" maxlength="10"
						value="${manualAdviceList[0].valueDate}" readonly="true"/>
											
					</td>
					<td></td>
					<td></td>
			</tr>  
          
          
       <tr>
        <td><bean:message key="lbl.chargeCode"/><font color="red">*</font></td>
		<td>
		<html:text property="chargeCode" styleClass="text" styleId="chargeCode" value="${manualAdviceList[0].chargeCodeDesc}" maxlength="100" size="10" disabled="true"/>
		 
		</td>
		
		<td><bean:message key="lbl.adviceType"/></td>
		<td>
		<html:select property="adviceType" styleClass="text" styleId="adviceType" value="${manualAdviceList[0].adviceType}" disabled="true">
			<html:option value="P">Payable</html:option>
			<html:option value="R">Receivable</html:option>
		</html:select>
<!--		<html:text property="adviceType" styleClass="text" styleId="adviceType" value="${manualAdviceList[0].adviceType}" disabled="true"/>-->
		</td>
		
         </tr>	
		
		 <tr>
          <td><bean:message key="lbl.taxApplicable"/></td>
		<td><html:text property="taxApplicable" styleClass="text" styleId="taxApplicable" value="${manualAdviceList[0].taxApplicable}" disabled="true"/></td> 
		<td><bean:message key="lbl.tdsApplicable"/></td>
		<td><html:text property="tdsApplicable" styleClass="text" styleId="tdsApplicable" value="${manualAdviceList[0].tdsApplicable}" disabled="true"/></td>
          </tr>
       <tr>
        <td><bean:message key="lbl.taxRate1"/></td>
		<td><html:text property="taxRate1" styleClass="text" styleId="taxRate1" style="text-align: right;" value="${manualAdviceList[0].taxRate1}" disabled="true"/></td>
      
        <td><bean:message key="lbl.taxRate2"/></td>
		<td><html:text property="taxRate2" styleClass="text" styleId="taxRate2" style="text-align: right;" value="${manualAdviceList[0].taxRate2}" disabled="true"/></td>
         </tr>
		 <tr>
        <td><bean:message key="lbl.tdsRate"/></td>
		<td><html:text property="tdsRate" styleClass="text" styleId="tdsRate" style="text-align: right;" value="${manualAdviceList[0].tdsRate}" disabled="true"/></td>
       	<td><bean:message key="lbl.taxInclusive"/></td>
		<td><html:text property="taxInclusive" styleClass="text" styleId="taxInclusive" value="${manualAdviceList[0].taxInclusive}" disabled="true"/></td>
       
       </tr>
       
		<tr>
			 <td><bean:message key="lbl.chargeAmount"/><font color="red">*</font></td>
			<td><html:text property="chargeAmount" styleClass="text" styleId="chargeAmount" style="text-align: right;" value="${manualAdviceList[0].chargeAmount}" maxlength="18" readonly="true" disabled="true"/></td>
			</tr>
			<tr> 
     		<td><bean:message key="lbl.taxAmount1"/></td>
		<td><html:text property="taxAmount1" styleClass="text" styleId="taxAmount1" style="text-align: right;" value="${manualAdviceList[0].taxAmount1}" maxlength="18" readonly="true" disabled="true"/></td> 
       
		</tr>
		<tr>
		<td><bean:message key="lbl.taxAmount2"/></td>
		<td><html:text property="taxAmount2" styleClass="text" styleId="taxAmount2" style="text-align: right;" value="${manualAdviceList[0].taxAmount2}" maxlength="18" disabled="true"/></td> 
      </tr>
		
       <tr>
         <td><bean:message key="lbl.adviceAmount"/></td>
		 <td><html:text property="adviceAmount" styleClass="text" styleId="adviceAmount" style="text-align: right;" value="${manualAdviceList[0].adviceAmount}" readonly="true" maxlength="18" disabled="true" tabindex="-1"/></td>
	  </tr>
	  <tr>
	  <td><bean:message key="lbl.origionalReversal"/><font color="red">*</font></td>
		<td>
		<html:select property="origionalReversal" styleClass="text" styleId="origionalReversal" onchange="disableAdviceIdField();" value="${manualAdviceList[0].origionalReversal}" disabled="true">
			<html:option  value="">---Select---</html:option>
			<html:option value="O">Original</html:option>
			<html:option value="R">Reversal</html:option>
		</html:select>
<!--		<html:text property="adviceType" styleClass="text" styleId="adviceType" value="" readonly="true"/>-->
		</td>
		<td><bean:message key="lbl.orgAdviceId"/></td>
				<td>
			  		<html:text property="orgAdviceId" styleId="orgAdviceId" styleClass="text" readonly="true" value="${manualAdviceList[0].orgAdviceId}"/>
			   		<!-- <html:button property="orgAdviceIdButton" styleId="orgAdviceIdButton" onclick="openLOVCommon(19104,'manualAdviceCreationForm','lbxorgAdviceId','origionalReversal|loanAccountNo','', 'lbxLoanNoHID','Select Original/Reversal First|Select loan account No.','','orgAdviceId');" value=" " styleClass="lovbutton"></html:button> -->
			     	<html:hidden  property="lbxorgAdviceId" styleId="lbxorgAdviceId" value=" "  />
			    </td>
	  </tr>	  
	   <tr >
		  <td width="25%"> <bean:message key="lbl.makerRemark"/></td>
		  <td  width="25%" >
		  <textarea name="remarks" class="text" id="remarks" maxlength="1000" disabled="true">${manualAdviceList[0].remarks}</textarea>
          </td>
			
			
			<td width="25%"><bean:message key="lbl.authorRemarks" /></td>
			<td width="25%" ><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${manualAdviceList[0].authorRemarks}" /></td>
          </tr>  
	  
<!--        <tr>-->
<!--		   <td><bean:message key="lbl.remark"/><font color="red">*</font></td>-->
<!--		<td><html:textarea property="remarks" styleClass="text" styleId="remarks" value="${manualAdviceList[0].remarks}" cols="150" disabled="true"/></td> -->
<!--          </tr>	-->
		</table>
		
	</table>
	
	 
	</fieldset>
	</logic:present>
	</logic:present>
	
	
	<logic:notPresent name="adviceUpdate">
	
	<fieldset>	  
	  <legend><bean:message key="lbl.manualAdviceCreation"/></legend>         
	   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
		
	<tr>
	
          <td><bean:message key="lbl.lan"/><font color="red">*</font></td>
	<td> <html:text property="loanAccountNo" styleClass="text" styleId="loanAccountNo" value="${sessionScope.pParentGroup}" readonly="true"/>
	     <input type="hidden" name="lbxLoanNoHID" id="lbxLoanNoHID" size="20" value="${sessionScope.strParentOption}"/>
	     
	     <html:button property="bpButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(6,'manualAdviceCreationForm','loanAccountNo','','', '','','getProductManual','customerName','lbxSchemeId','hcommon1');" value=" " styleClass="lovbutton"></html:button>
	     
         <input type="hidden" name="lbxProductId" id="lbxProductId" size="20" />
         <input type="hidden" name="lbxSchemeId" id="lbxSchemeId" size="20" />
         
        
	</td>
	<td><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td><html:text property="customersName" styleClass="text" styleId="customerName" readonly="true" value="" maxlength="100" size="10" /></td> 
          </tr>
			<tr>
	  
          <td><bean:message key="lbl.businessPartnerType"/><font color="red">*</font></td>
         	<td> 
         	<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value=""/>
								<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              					<html:hidden property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="" />
              					
              					 <html:button property="bpButton" styleId="businessPartButton" onclick="openLOVCommon(307,'manualAdviceCreationForm','lbxBusinessPartnearHID','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','clearChargeCode','businessPartnerType','businessPartnerName','businessPartnerTypeDesc');" value=" " styleClass="lovbutton"></html:button>
              					
				</td>     
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
	<html:text property="businessPartnerName" styleId="businessPartnerName" readonly="true" value="" styleClass="text" maxlength="50" onkeypress="return isCharKey(event);"/>
					    </td>		</tr>
					    
			<tr>
				<td>
					<bean:message key="lbl.manualAdviceDate" /><font color="red">*</font>
				</td>
				<td>
					
						<html:text styleClass="text" property="valueDate"
						styleId="valueDate" maxlength="10"
						value="<%= initiationDate%>"
						onchange="checkDate('valueDate');checkValueDate();" />
						
						<html:hidden styleClass="text" value="<%= initiationDateDbFormat%>" property="initiationDateDbFormat" styleId="initiationDateDbFormat"/>
					
											
					</td>
					<td></td>
					<td></td>
			</tr>		    
					    
					    
					    
		   <tr>
        <td><bean:message key="lbl.chargeCode"/><font color="red">*</font></td>
		<td>
		<html:text property="chargeCode" styleClass="text" styleId="chargeCode" value="${sessionScope.pParentGroup}" readonly="true" maxlength="100" size="10"/>
        <input type="hidden" name="lbxCharge" id="lbxCharge" size="20" value="${sessionScope.strParentOption}"/>

        <html:button property="bpButton" styleId="chargeButton" onclick="openLOVCommon(120,'manualAdviceCreationForm','chargeCode','valueDate|businessPartnerTypeDesc|loanAccountNo','lbxCharge', 'initiationDateDbFormat|lbxSchemeId|lbxProductId','Select Manual Advice Date First|Select BP Type LOV First|Select Loan Account LOV First','getManualDetails','chargeCode');" value=" " styleClass="lovbutton"></html:button>
        
		<input type="hidden" id="chargeId" value=""/>
		</td>
		<td><bean:message key="lbl.adviceType"/></td>
		<td>
		<html:select property="adviceType" styleClass="text" styleId="adviceType" value="">
			<html:option value="P">Payable</html:option>
			<html:option value="R">Receivable</html:option>
		</html:select>
<!--		<html:text property="adviceType" styleClass="text" styleId="adviceType" value="" readonly="true"/>-->
		</td>
		
         </tr>	
		
		 <tr>
          <td><bean:message key="lbl.taxApplicable"/></td>
		<td><html:text property="taxApplicable" styleClass="text" styleId="taxApplicable" value="" readonly="true"/></td> 
		<td><bean:message key="lbl.tdsApplicable"/></td>
		<td><html:text property="tdsApplicable" styleClass="text" styleId="tdsApplicable" value="" readonly="true"/></td>
          </tr>
       <tr>
        <td><bean:message key="lbl.taxRate1"/></td>
		<td><html:text property="taxRate1" styleClass="text" styleId="taxRate1" style="text-align: right;" value="" readonly="true"/></td>
      
        <td><bean:message key="lbl.taxRate2"/></td>
		<td><html:text property="taxRate2" styleClass="text" styleId="taxRate2" style="text-align: right;" readonly="true"/></td>
         </tr>
		 <tr>
        <td><bean:message key="lbl.tdsRate"/></td>
		<td><html:text property="tdsRate" styleClass="text" styleId="tdsRate" style="text-align: right;" value="" readonly="true"/></td>
       	<td><bean:message key="lbl.taxInclusive"/></td>
		<td><html:text property="taxInclusive" styleClass="text" styleId="taxInclusive" value="" readonly="true"/></td>
       
       </tr>
       
		<tr>
			 <td><bean:message key="lbl.chargeAmount"/><font color="red">*</font></td>
			<td><html:text property="chargeAmount" styleClass="text" styleId="chargeAmount" style="text-align: right;" maxlength="18" 
			onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onchange="totAdviceAmount();calculateTax();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
			
			
			</tr>
			<tr> 
     		<td><bean:message key="lbl.taxAmount1"/></td>
		<td><html:text property="taxAmount1" styleClass="text" styleId="taxAmount1" style="text-align: right;" value="" maxlength="18" readonly="true" disabled="true"
		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);totAdviceAmount();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
       
		</tr>
		<tr>
		<td><bean:message key="lbl.taxAmount2"/></td>
		<td><html:text property="taxAmount2" styleClass="text" styleId="taxAmount2" style="text-align: right;" value="" maxlength="18" readonly="true" disabled="true"
		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);totAdviceAmount();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      </tr>
		
       <tr>
         <td><bean:message key="lbl.adviceAmount"/></td>
		 <td><html:text property="adviceAmount" styleClass="text" styleId="adviceAmount" style="text-align: right;" value="0" readonly="true" disabled="true" maxlength="18" 
		 		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  </tr>
	  <tr>
	  <td><bean:message key="lbl.origionalReversal"/><font color="red">*</font></td>
		<td>
		<html:select property="origionalReversal" styleClass="text" styleId="origionalReversal" onchange="disableAdviceIdField();" value="${manualAdviceList[0].origionalReversal}">
			<html:option  value="">---Select---</html:option>
			<html:option value="O">Original</html:option>
			<html:option value="R">Reversal</html:option>
		</html:select>
<!--		<html:text property="adviceType" styleClass="text" styleId="adviceType" value="" readonly="true"/>-->
		</td>
		<td><bean:message key="lbl.orgAdviceId"/></td>
				<td>
			  		<html:text property="orgAdviceId" styleId="orgAdviceId" styleClass="text" readonly="true" value="${manualAdviceList[0].orgAdviceId}"/>
			   		<html:button property="orgAdviceIdButton" styleId="orgAdviceIdButton" onclick="openLOVCommon(19104,'manualAdviceCreationForm','lbxorgAdviceId','origionalReversal|loanAccountNo','', 'lbxLoanNoHID','Select Original/Reversal First|Select loan account No.','','orgAdviceId');" value=" " styleClass="lovbutton"></html:button>
			     	<html:hidden  property="lbxorgAdviceId" styleId="lbxorgAdviceId" value=" "  />
			    </td>
	  </tr>	  
	   <tr >
		  <td width="25%"> <bean:message key="lbl.makerRemark"/></td>
		  <td  width="25%" >
		  <textarea name="remarks" class="text" id="remarks" maxlength="1000"></textarea>
          </td>
			
			
			<td width="25%"><bean:message key="lbl.authorRemarks" /></td>
			<td width="25%" ><html:textarea styleClass="text" property="authorRemarks" disabled="true" value="" /></td>
          </tr>     
<!--        <tr>-->
<!--		   <td><bean:message key="lbl.remark"/><font color="red">*</font></td>-->
<!--			<td><html:textarea property="remarks" styleClass="text" styleId="remarks" value="" cols="150" /></td> -->
<!--          </tr>	-->
		</table>
		
	<tr>

		  <td align="left" >

		   <button type="button" name="button1" class="topformbutton2" value="Save" id="save" title="Alt+V" accesskey="V"  onclick="return insert('Save');"><bean:message key="button.save" /></button>

		
		  <button type="button" class="topformbutton2" title="Alt+F" value="Save & Forward" id="saveFwd" accesskey="F" onclick="return insert('Save & Forward');"><bean:message key="button.forward" /></button>
		
		  
		</td>
    </tr>
	
	</table>
	 
	</fieldset>
	</logic:notPresent>
	</html:form>
	</div>

</div>
<logic:present name="sms">
 <script type="text/javascript">
	if("<%=request.getAttribute("sms").toString()%>"=="S")
	{
		alert("<bean:message key="msg.manualAdviceMakerSaved" />");
		location.href="<%=request.getContextPath()%>/manualAdviceCreationBehindAction.do";
		//document.getElementById('manualAdviceCreationForm').action=
	   // document.getElementById('manualAdviceCreationForm').submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
	   alert("<bean:message key="msg.manualAdviceMakerUnsuccessful" />");
	   location.href="<%=request.getContextPath()%>/manualAdviceCreationBehindAction.do";
	   //document.getElementById('manualAdviceCreationForm').action=
	  // document.getElementById('manualAdviceCreationForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
	   alert("<bean:message key="msg.manualAdviceMakerUpdated" />");
	   location.href="<%=request.getContextPath()%>/manualAdviceCreationBehindAction.do";
	  // document.getElementById('manualAdviceCreationForm').submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='UE')
	{
	   alert("<bean:message key="msg.updationUnsuccessful" />");
	   location.href="<%=request.getContextPath()%>/manualAdviceCreationBehindAction.do";
	  // document.getElementById('manualAdviceCreationForm').submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='SF')
	{
	   alert("<bean:message key="msg.ForwardSuccessfully" />");
	   location.href="<%=request.getContextPath()%>/manualAdviceSearchBehind.do";
	   //document.getElementById('manualAdviceCreationForm').submit();
	
	}else if('<%=request.getAttribute("sms").toString()%>'=="DS")
		{
			alert("<bean:message key="lbl.dataDeleted" />");
			window.location="<%=request.getContextPath()%>/manualAdviceSearchBehind.do";
		}else if('<%=request.getAttribute("sms").toString()%>'=='DN')
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
			
		}
	
	</script>
</logic:present>

<script>
	setFramevalues("manualAdviceCreationForm");
</script>
</body>
</html:html>