<%@ page language="java"%>
<%@ page session="true"%>
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
	<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>  
   <script type="text/javascript" src="<%=path%>/js/cmScript/lovCommonScript.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/waiveOff.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>	
	
	<script type="text/javascript" 	src="<%=request.getContextPath() %>/js/formatnumber.js"></script>
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
<body onload="enableAnchor();fntab();init_fields();" onclick="parent_disable();" onUnload="closeAllLovCallUnloadBody();">
	
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="adviceDtChngFlag" value="N"/>	
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
	<logic:notPresent name="WaiveOffData">
	<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
	<html:form action="/waiveOffMakerDispatchAction" styleId="waiveOffMakerFormCSE">
	<html:javascript formName="WaiveOffMakerSaveDynaValidatorForm" />
	<html:errors/>
	<input type="hidden" id="new" value="new" />
	<fieldset>	  
	  <legend><bean:message key="lbl.waiveOffmaker"/> </legend>         
	   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td >
	
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
            <td width="25%"><bean:message key="lbl.lan"/><font color="red">*</font> </td>
		    <td width="25%" style="padding-left:" nowrap="nowrap" >	
		      <html:text styleClass="text" maxlength="20" property="loanAccountNo" styleId="loanAccountNo" style="" readonly="true" value="" tabindex="-1" /> 
              <html:button property="loanButton" styleId="loanButton" onclick="cleanField('L');openLOVCommon(6,'waiveOffMakerFormCSE','loanAccountNo','','', '','','','customerName','hCommon','hCommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
              <!-- <img onclick="openLOVCommon(6,'waiveOffMakerFormCSE','loanAccountNo','','', '','','','customerName','hCommon','hCommon')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->
              <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
               <html:hidden  property="hCommon" styleId="hCommon" value="" />
                <html:hidden property="waveOffId" styleId="waveOffId" value=""/>
    </td>
		  
		    <td width="25%"><bean:message key="lbl.CustomerName"/>  </td>
		    <td width="25%%"><html:text styleClass="text" readonly="true" property="customerName" style="" styleId="customerName" value="" maxlength="50" tabindex="-1"/></td>
		    </tr>
		  <tr>
            <td > <bean:message key="lbl.businessPartnerType"/><font color="red">*</font>  </td>
		    <td nowrap="nowrap" >
		    					<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="" tabindex="-1"/>
								<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              					<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="" />
              					
              					<html:button property="bpButton" styleId="bpButton" onclick="cleanField('B');openLOVCommon(307,'waiveOffMakerFormCSE','lbxBusinessPartnearHID','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerType','businessPartnerName','businessPartnerTypeDesc');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
              					<!-- <img onclick="openLOVCommon(45,'waiveOffMakerFormCSE','businessPartnerType','loanAccountNo', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerTypeDesc')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->
					</td>
            	         			
            
		    <td nowrap="nowrap"><bean:message key="lbl.businessPartnerName"/></td>	    
		    
		    <td nowrap="nowrap">	
		      <html:text property="businessPartnerName" styleId="businessPartnerName" readonly="true" value="" styleClass="text" maxlength="50" tabindex="-1"/>
				
			 </td>					
   
		  </tr>
			<tr>
				<td>
					<bean:message key="lbl.waiveOffDate" /><font color="red">*</font>
				</td>
				<td>
					
						<html:text styleClass="text" property="valueDate"
						styleId="valueDate" maxlength="10"
						value="<%= initiationDate%>"
						onchange="checkDate('valueDate');checkValueDate();" />
						
						<html:hidden styleClass="text" value="<%= initiationDateDbFormat%>" property="initiationDateDbFormat" styleId="initiationDateDbFormat"/>
					
											
					</td>
					<td><bean:message key="lbl.waiveOffApprovedBy" /></td>
					 <td nowrap="nowrap">  
               		  <html:text property="approvedBy" readonly="true" styleClass="text" styleId="approvedBy" tabindex="-1" />
				 	 <html:button property="approvedByButton" styleId="approvedByButton" onclick="cleanField('C');openLOVCommon(504,'waiveOffMakerFormCSE','approvedBy','valueDate|businessPartnerTypeDesc|loanAccountNo','','initiationDateDbFormat|businessPartnerType|lbxLoanNoHID','Select WaiveOff Date First|Select BP Type LOV First|Select Loan Account LOV First','','lbxapprovedBy');" value=" " styleClass="lovbutton"> </html:button>
				 	 <html:hidden  property="lbxapprovedBy" styleId="lbxapprovedBy" value=""/>
        			 </td>
			</tr>
		  <tr>
            <td nowrap="nowrap"><bean:message key="lbl.chargeCode"/><font color="red">*</font></td>
            <td nowrap="nowrap"><input type="hidden" name="htype" id="htype" value=""/>   
                 <html:text styleClass="text" property="chargeType" styleId="chargeType" maxlength="10" readonly="true" value="" tabindex="-1"/> 
                   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
        				<html:hidden  property="lbxChargeCodeHID" styleId="lbxChargeCodeHID" value="" style="" />  
        					<!-- <img onClick="openLOVCommon(44,'waiveOffMakerFormCSE','chargeDescription','businessPartnerType','lbxChargeCodeHID', 'businessPartnerType','Select Loan Account LOV First','getWaiveOffList','chargeType')" src="<%= request.getContextPath() %>/images/lov.gif"/> -->
        					<html:button property="chargeButton" styleId="chargeButton" onclick="cleanField('C');openLOVCommon(198,'waiveOffMakerFormCSE','chargeDescription','valueDate|businessPartnerTypeDesc|loanAccountNo','lbxChargeCodeHID', 'initiationDateDbFormat|businessPartnerType|lbxLoanNoHID','Select WaiveOff Date First|Select BP Type LOV First|Select Loan Account LOV First','getWaiveOffList','chargeType','htxnAdviceId','htype');closeLovCallonLov('chargeType');" value=" " styleClass="lovbutton"></html:button>
        					 <html:hidden property="htxnAdviceId" styleId="htxnAdviceId" value=""/>
        					
        					 
<!--              					      <input type="hidden" name="htxnAdviceId" id="htxnAdviceId" value=""/>        					-->
<!--              					<html:button property="chargeButton" styleId="chargeButton" onclick="return openLOVCommon(44,'waiveOffMakerFormCSE','chargeDescription','businessPartnerType','lbxChargeCodeHID', 'businessPartnerType','Select Loan Account LOV First','getWaiveOffList','chargeType','hCommon','hCommon')" value=" " styleClass="lovbutton"></html:button>-->
    </td>
            
		    <td nowrap="nowrap"><bean:message key="lbl.chargeDescription"/></td>
		    <td nowrap="nowrap" >
		    <html:text styleClass="text" property="chargeDescription" styleId="chargeDescription" style="" readonly="true" value="" maxlength="50" tabindex="-1"/></td>
		    </tr>
		
<!--	Change from here start	-->
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		</table>
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1" >
		<tr class="white2">
			<td width="25%"><bean:message key="lbl.chargeDetails"/></td>
			<td width="25%"><bean:message key="lbl.chargeAmount"/></td>
			<td width="25%"><bean:message key="lbl.waiveOffAmount"/></td>
			<td width="25%"><bean:message key="lbl.EffectiveChargeAmount"/></td>
		</tr>
		<tr class="white1">		  
		    <td nowrap="nowrap"><bean:message key="lbl.chargeAmount"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="chargeAmount" styleId="chargeAmount" value="" style="" maxlength="22" tabindex="-1"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="waivOffAmount" styleId="waivOffAmount" value="" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);newAmount1();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>
		    	<input type="hidden" id="waiveOffAmountNotUsed"/>
		    </td>
		    
		    <td nowrap="nowrap"><html:text  styleClass="text" property="ChargeNewAmount" styleId="ChargeNewAmount" readonly="true" value="" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		
		<tr class="white1">   
		    <td nowrap="nowrap"><bean:message key="lbl.taxAmount1"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="taxAmount1" styleId="taxAmount1" value="" style="" maxlength="22" tabindex="-1"/></td>
		  <td nowrap="nowrap"><html:text  styleClass="text" property="waveAmountForTaxAmt1" styleId="waveAmountForTaxAmt1" value="" style="" maxlength="22" 
		  onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);newAmount2();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="tax1NewAmt" styleId="tax1NewAmt" value="" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		
		<tr class="white1">		  
		    <td nowrap="nowrap"><bean:message key="lbl.taxAmount2"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="taxAmount2" styleId="taxAmount2" value="" style="" maxlength="22" tabindex="-1"/></td>
			<td nowrap="nowrap"><html:text  styleClass="text" property="waveAmountForTaxAmt2" styleId="waveAmountForTaxAmt2" value="" style="" maxlength="22"
			onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);newAmount3();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="tax2NewAmt" styleId="tax2NewAmt" value="" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<tr class="white1">
		 <td nowrap="nowrap"><bean:message key="lbl.AdviceAmount"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="adviceAmount" styleId="adviceAmount" value="" style="" maxlength="22" tabindex="-1"/></td>
		  	   
		   <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="totalWaveOffAmt" styleId="totalWaveOffAmt" value="" style="width:150px !important;" maxlength="22" tabindex="-1"
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="newAdviceAmt" styleId="newAdviceAmt" value="" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		
		
<!--	Change from here End	-->		
		
		  <tr class="white1">		  
		    
		  <td nowrap="nowrap"><bean:message key="lbl.adjustedAmount"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="txnAdjustedAmount" styleId="txnAdjustedAmount" value="" style="" maxlength="22" tabindex="-1"/></td>
		  <td></td>
		  <td></td>
		    </tr>
		    <tr class="white1">
		    	<td><bean:message key="lbl.amountInProcess"/></td>
		  	<td><html:text  styleClass="text" readonly="true" property="amountInProcess" styleId="amountInProcess" value="" style="" maxlength="22" tabindex="-1"/></td>
		   <td></td>
		   <td></td>
		    </tr>
		    <tr class="white1">
			<td nowrap="nowrap"><bean:message key="lbl.balanceAmount"/></td>
		    <td nowrap="nowrap">
		    <html:text styleClass="text" property="balanceAmount" readonly="true" styleId="balanceAmount" value="" style="" maxlength="50" tabindex="-1"/></td>
		   
		  <td nowrap="nowrap">
		   </td>
		  <td nowrap="nowrap">
		   	<input type="text" id="newBalanceAmt" readonly="readonly" maxlength="22"  class="text" value="" 
		  	onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"
		    />
		  </td>
		  <input type="hidden" name="txnAdviceId" id="txnAdviceId" value=""/>
		  <input type="hidden" name="taxRate1" id="taxRate1"/>
		  <input type="hidden" name="taxRate2" id="taxRate2"/>
		  
		  
		  </tr>
		
		  </table>
		  </td>
		  </tr>
		   <tr><td></td></tr>
		
		  <table width="100%" border="0" cellspacing="1" cellpadding="4">
		  <tr >
		  <td width="25%"> <bean:message key="lbl.makerRemark"/></td>
		  <td  width="25%" >
		  <textarea name="remarks" id="remarks" maxlength="1000"></textarea>
          </td>
			
			
			<td width="25%"><bean:message key="lbl.authorRemarks" /></td>
			<td width="25%" ><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="" /></td>
          </tr>      
	<tr>

		  <td colspan="3">
		    <button type="button" name="Save" class="topformbutton2" id="Save"  onclick="return saveWaiveOffCSE()" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>	
		    <button type="button" name="Save &amp; Forward" id="Save &amp; Forward" onclick="return waiveOffMakerCSESaveForword()" class="topformbutton3" title="Alt+F" accesskey="F" ><bean:message key="button.forward" /></button>
		   <button type="button" id="viewPay" class="topformbutton3" onclick="return viewPayableWaiveOff('<bean:message key="msg.LoanAccBPType" />');" title="Alt+P" accesskey="P"><bean:message key="button.viewPayable" /></button>
		   <button type="button" id="viewRec" class="topformbutton3" onclick="return viewReceivableWaiveOff('<bean:message key="msg.LoanAccBPType" />');" title="Alt+R" accesskey="R"><bean:message key="button.viewRecievable" /></button>
		  
		  </td>
            
            
	    </tr>
	
	</table>
	 
	</fieldset>
	
	</html:form>
	</logic:notPresent>
	
		
	<logic:present name="WaiveOffData">
	<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
	
	<html:form action="/waiveOffMakerDispatchAction" styleId="waiveOffMakerFormCSE">
	<html:javascript formName="WaiveOffMakerSaveDynaValidatorForm" />
		<input type="hidden" id="new" value="" />
		<input type="hidden" name="loanRecStatus" value="${WaiveOffData[0].loanRecStatus}" id="loanRecStatus" />
	<html:errors/>
	<fieldset>	  
	  <legend><bean:message key="lbl.waiveOffmaker"/> </legend>         
	   
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
            <td width="25%"><bean:message key="lbl.lan"/> </td>
		    <td width="25%">	
		      <html:text styleClass="text" property="loanAccountNo" maxlength="20" styleId="loanAccountNo" style="" readonly="true" value="${WaiveOffData[0].loanAccountNo}"/> 
               <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${WaiveOffData[0].lbxLoanNoHID}" />
              <html:hidden property="waveOffId" styleId="waveOffId" value="${WaiveOffData[0].waveOffId}"/>
    </td>
		  
		    <td width="25%"><bean:message key="lbl.CustomerName"/> </td>
		    <td width="25%"><html:text styleClass="text" readonly="true" property="customerName" style="" styleId="customerName" value="${WaiveOffData[0].customerName}" maxlength="50"/></td>
		    </tr>
		  <tr>
            <td> <bean:message key="lbl.businessPartnerType"/> </td>
		    <td nowrap="nowrap" >
								<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value="${WaiveOffData[0].businessPartnerType}"/>              					
								<html:hidden styleClass="text"  property="businessPartnerDesc" styleId="businessPartnerDesc" style=""  value="${WaiveOffData[0].businessPartnerDescription}"/>
              					<html:text styleClass="text" maxlength="10" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${WaiveOffData[0].businessPartnerDescription}"/>
              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              				    <html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${WaiveOffData[0].lbxBusinessPartnearHID}" />
					</td>
            	         			
            
		    <td nowrap="nowrap"><bean:message key="lbl.businessPartnerName"/>  </td>	    
		    
		    <td nowrap="nowrap">	
		      <html:text property="businessPartnerName" styleId="businessPartnerName" readonly="true" value="${WaiveOffData[0].businessPartnerName}" styleClass="text" maxlength="50"/>
					<html:hidden  property="lbxBPNID" styleId="lbxBPNID" value="" />				
			 </td>					
   
		  </tr>
		  
		  <tr>
				<td>
					<bean:message key="lbl.waiveOffDate" />
				</td>
				<td>
						<html:text styleClass="text" property="valueDate"
						styleId="valueDate1" maxlength="10"
						value="${WaiveOffData[0].valueDate}" readonly="true"/>
											
					</td>
					<td><bean:message key="lbl.waiveOffApprovedBy" /></td>
					<td nowrap="nowrap">  
               		  <html:text property="approvedBy" readonly="true" styleClass="text" value="${WaiveOffData[0].lbxapprovedBy}" styleId="approvedBy" tabindex="-1" />
				 	   <html:hidden  property="lbxapprovedBy" styleId="lbxapprovedBy" value="${WaiveOffData[0].lbxapprovedBy}"/>
        			 </td>
			</tr>
		  
		  <tr>
            <td nowrap="nowrap"><bean:message key="lbl.chargeCode"/> </td>
            <td nowrap="nowrap">	 
              <html:hidden styleClass="text" property="lbxChargeCodeHID" styleId="lbxChargeCodeHID"  value="${WaiveOffData[0].chargeType}"/> 
             <html:text styleClass="text" maxlength="10" property="chargeType" styleId="chargeType" readonly="true" value="${WaiveOffData[0].chargeType}"/> 
    </td>
		    <td nowrap="nowrap"><bean:message key="lbl.chargeDescription"/> </td>
		    <td nowrap="nowrap" >
		    <html:text styleClass="text" property="chargeDescription" styleId="chargeDescription" style="" readonly="true" value="${WaiveOffData[0].chargeDescription}" maxlength="50"/></td>
		    </tr>
		
		<!--	Change from here start	-->
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		</table>
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="1" >
		<tr class="white2">
			<td width="25%"><bean:message key="lbl.chargeDetails"/></td>
			<td width="25%"><bean:message key="lbl.chargeAmount"/></td>
			<td width="25%"><bean:message key="lbl.waiveOffAmount"/></td>
			<td width="25%"><bean:message key="lbl.EffectiveChargeAmount"/></td>
		</tr>
		<tr class="white1">		  
		    <td nowrap="nowrap"><bean:message key="lbl.chargeAmount"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" property="chargeAmount" styleId="chargeAmount" value="${WaiveOffData[0].chargeAmount}" style="" maxlength="22"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" property="waivOffAmount" styleId="waivOffAmount" style="" value="${WaiveOffData[0].waivOffAmount}" maxlength="22"  
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);newAmount1();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="ChargeNewAmount" readonly="true" styleId="ChargeNewAmount" value="${WaiveOffData[0].newChargeAmt}" style="" maxlength="22"
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);return callNetReceivablePayable();"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		
		<tr class="white1">   
		    <td nowrap="nowrap"><bean:message key="lbl.taxAmount1"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" tabindex="-1" property="taxAmount1" styleId="taxAmount1" value="${WaiveOffData[0].taxAmount1}" style="" maxlength="22"/></td>
		  <td nowrap="nowrap"><html:text  styleClass="text" property="waveAmountForTaxAmt1" styleId="waveAmountForTaxAmt1" value="${WaiveOffData[0].waveAmountForTaxAmt1}" style="" maxlength="22"
		  onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);newAmount2();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="tax1NewAmt" readonly="true" styleId="tax1NewAmt" value="${WaiveOffData[0].newTaxAmt1}" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		
		<tr class="white1">		  
		    <td nowrap="nowrap"><bean:message key="lbl.taxAmount2"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" tabindex="-1" property="taxAmount2" styleId="taxAmount2" value="${WaiveOffData[0].taxAmount2}" style="" maxlength="22"/></td>
			<td nowrap="nowrap"><html:text  styleClass="text" property="waveAmountForTaxAmt2" styleId="waveAmountForTaxAmt2" value="${WaiveOffData[0].waveAmountForTaxAmt2}" style="" maxlength="22" 
			onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);newAmount3();" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="tax2NewAmt" readonly="true" styleId="tax2NewAmt" value="${WaiveOffData[0].newTaxAmt2}" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		<tr class="white1">
		 <td nowrap="nowrap"><bean:message key="lbl.AdviceAmount"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" tabindex="-1" property="adviceAmount" styleId="adviceAmount" value="${WaiveOffData[0].adviceAmount}" style="" maxlength="22"/></td>
		  	   
		   <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" tabindex="-1" property="totalWaveOffAmt" styleId="totalWaveOffAmt" value="${WaiveOffData[0].totalWaiveOffAmt}" style="width:150px !important;" maxlength="22" 
		   
		   onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		    <td nowrap="nowrap"><html:text  styleClass="text" property="newAdviceAmt" readonly="true" styleId="newAdviceAmt" value="${WaiveOffData[0].newAdviceAmt}" style="" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		</tr>
		
		
<!--	Change from here End	-->		
		
		  <tr class="white1">		  
		    
		  <td nowrap="nowrap"><bean:message key="lbl.adjustedAmount"/>  </td>
		    <td nowrap="nowrap"><html:text  styleClass="text" readonly="true" tabindex="-1" property="txnAdjustedAmount" styleId="txnAdjustedAmount" value="${WaiveOffData[0].txnAdjustedAmount}" style="" maxlength="22"/></td>
		  	<td></td>
		  	<td></td>
		     </tr>
		     <tr class="white1">
		     
		     <td><bean:message key="lbl.amountInProcess"/></td>
		  	<td><html:text  styleClass="text" readonly="true" property="amountInProcess" styleId="amountInProcess" value="${WaiveOffData[0].amountInProcess}" style="" maxlength="22" tabindex="-1"/></td>
		    <td></td>
		    <td></td>
		     </tr>
		    <tr class="white1">
			<td nowrap="nowrap"><bean:message key="lbl.balanceAmount"/></td>
		    <td nowrap="nowrap">
		    <html:text styleClass="text" property="balanceAmount" readonly="true" tabindex="-1" styleId="balanceAmount" value="${WaiveOffData[0].balanceAmount}" style="" maxlength="50"/></td>
		   
		  <td>
<!--		  <bean:message key="lbl.waiveOffAmount"/><font color="red">*</font>-->
		   </td> 
		  <td>
				<input type="text" class="text" id="newBalanceAmt" readonly="readonly" tabindex="-1" value="${WaiveOffData[0].newAdviceAmt}" maxlength="22" 
		    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>
		  </td>
		   <input type="hidden" name="txnAdviceId" id="txnAdviceId" />
		   
		   <logic:present name="txnId">
<!--		   		 <input type="hidden" name="htxnAdviceId" id="htxnAdviceId" value="${requestScope.txnId}"/>-->
		   		  <html:hidden property="htxnAdviceId" styleId="htxnAdviceId" value="${requestScope.txnId}"/>
		   </logic:present>
		   <logic:notPresent name="txnId">
		   <html:hidden property="htxnAdviceId" styleId="htxnAdviceId" value="${WaiveOffData[0].txnAdviceId}"/>  
<!--		  	 	<input type="hidden" name="htxnAdviceId" id="htxnAdviceId"  value="${WaiveOffData[0].txnAdviceId}"/>-->
		   </logic:notPresent>
		  <html:hidden property="taxRate1" styleId="taxRate1" value="${WaiveOffData[0].taxRate1}"/>    
		  <html:hidden property="taxRate2" styleId="taxRate2" value="${WaiveOffData[0].taxRate2}"/>     
<!--		  <input type="hidden" name="taxRate1" id="taxRate1" value="${WaiveOffData[0].taxRate1}"/>-->
<!--		  <input type="hidden" name="taxRate2" id="taxRate2" value="${WaiveOffData[0].taxRate2}"/>-->
		  
		  </tr>
		  </table>
		  </td>
		  </tr>
		  
		 <tr><td></td></tr>
		
		  <table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr >
		  <td width="25%"> <bean:message key="lbl.makerRemark"/></td>
		  <td width="25%">
		  <textarea name="remarks" id="remarks" maxlength="1000" >${WaiveOffData[0].remarks}</textarea>   
          </td>
			
			<td width="25%"><bean:message key="lbl.authorRemarks" /></td>
			<td width="25%"><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${WaiveOffData[0].authorRemarks}" /></td>
          </tr>      
	<tr>

		  <td colspan="3">
		  
		   <button type="button" name="Save" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="return saveWaiveOffCSE();" ><bean:message key="button.save" /></button>
		   <button type="button" name="Save &amp; Forward" id="Forward" class="topformbutton3"  onclick="return waiveOffMakerCSESaveForword();" title="Alt+F" accesskey="F" ><bean:message key="button.forward" /></button>
           <button type="button" id="viewPay" class="topformbutton3" onclick="return viewPayableWaiveOff('<bean:message key="msg.LoanAccBPType" />');" title="Alt+P" accesskey="P"><bean:message key="button.viewPayable" /></button>   		 
		   <button type="button" id="viewRec" class="topformbutton3" onclick="return viewReceivableWaiveOff('<bean:message key="msg.LoanAccBPType" />');" title="Alt+R" accesskey="R"><bean:message key="button.viewRecievable" /></button>
		   <button type="button" id="delete" class="topformbutton2" onclick="return deleteWaiveOff();" title="Alt+D" accesskey="D"><bean:message key="button.delete" /></button>
		   </td>
            
            
	    </tr>
	
	</table>

		 
</td></tr></table>
	 
	</fieldset>
	
	</html:form>
	</logic:present>
	</div>

</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</body>

</html:html>
<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.saveWaiveOff" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='P')
	{
		alert('<bean:message key="lbl.errorCntEdit" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='U')
	{
		alert('<bean:message key="lbl.updateWaiveOff" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='N')
	{
		alert('<bean:message key="lbl.alreadyWaiveOff" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='SF')
	{
		alert('<bean:message key="lbl.saveAndForwardWaiveOff" />');
		document.getElementById('waiveOffMakerFormCSE').action="<%=request.getContextPath()%>/waiveOffMakerBehindAction.do";
	    document.getElementById('waiveOffMakerFormCSE').submit();
		
	}else if('<%=request.getAttribute("msg").toString()%>'=="DS")
		{
			alert("<bean:message key="lbl.dataDeleted" />");
			window.location="<%=request.getContextPath()%>/waiveOffMakerBehindAction.do";
		}else if('<%=request.getAttribute("msg").toString()%>'=='DN')
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
			
		}
	else
	{
	alert('<bean:message key="lbl.errorWaiveOff" />');
	
	}

</script>
</logic:present>