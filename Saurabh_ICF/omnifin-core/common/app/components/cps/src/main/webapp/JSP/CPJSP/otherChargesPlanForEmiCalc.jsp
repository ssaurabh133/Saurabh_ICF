<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessingForEmiCalc.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		     <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript">	
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('otherChargesPlanForm').fromInstallment1.focus();
     }
     else if(document.getElementById('modifyRecord').value =='A')
     {}
     
     return true;
}
</script >	
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


<body onload="enableAnchor();fntab();checkChanges('otherChargesPlanForm');">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
<input type="hidden" class="text" name="installmentType"  id="installmentType" value="<%=request.getAttribute("installmentType") %>" style="text" />
<input type="hidden" class="text" name="totalInstallment" id="totalInstallment" style="text" value="<%=request.getAttribute("totalInstallment") %>" />
<input type="hidden" class="text" name="rateType"  id="rateType" value="${rateType }" style="text" />
<input type="hidden" class="text" name="loanAmount"  id="loanAmount" value="${loanAmount }" style="text" />
<input type="hidden" name="lbxCharge" id="lbxCharge" size="20" value=""/>

<DIV id=centercolumn>
<DIV id=revisedcontainer>
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
 <html:form action="/otherChargesPlanForEmiCalc" method="post" styleId="otherChargesPlanForm">
<fieldset>
<input type="hidden"  name="psize" id="psize" value=""/>
</fieldset>	
<fieldset>
<legend><bean:message key="lbl.otherChargesPlan" /></legend>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
        <tr class="white2">
    	<td width="3%"><input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked();"/></td> 
         <td  ><bean:message key="lbl.fromInstallment"></bean:message> </td>
          <td ><bean:message key="lbl.toInstallment"></bean:message></td>
          <td ><bean:message key="lbl.type"></bean:message> </td>
          <td ><bean:message key="lbl.amount"></bean:message> </td>
           <td ><bean:message key="lbl.charges"></bean:message> </td>
     </tr>
          
                  
                  
       <logic:present name="otherChrgList">
        <logic:notEmpty name="otherChrgList">
        <logic:iterate name="otherChrgList" id="subOtherChrgList" indexId="count">
         
              <tr class="white1">
		          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
		          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subOtherChrgList.fromInstallment}"  maxlength="100" onkeypress="return numbersonly(event, id, 18)"/></td>
		          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subOtherChrgList.toInstallment}" maxlength="10" onkeypress="return numbersonly(event, id, 18)"/></td>	      
		          <td ><input type="text" class="text" name="type" id="type<%=count.intValue()+1%>" value="${subOtherChrgList.chargeType}"  readonly="readonly"/></td>
		          <td ><input type="text" class="text" name="amount" id="amount<%=count.intValue()+1%>" value="${subOtherChrgList.chargeAmount}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          <td >
	             
	             <input type="hidden" name="chargehiddenFld" id="chargehiddenFld<%=count.intValue()+1%>" size="20" value="${subOtherChrgList.chargeCode}"/>
				<html:text property="chargeDesc" styleClass="text" styleId="chargeDesc<%=count.intValue()+1%>" value="${subOtherChrgList.chargeDesc}" readonly="true" maxlength="100" size="20" tabindex="-1"/>
		 		<html:button property="chargeButton" styleId="chargeButton" onclick="openLOVCommon(453,'otherChargesPlanForm','chargeDesc1','','', '','','','chargehiddenFld1');closeLovCallonLov('lbxCharge');" value=" " styleClass="lovbutton"></html:button>
			  </td>
			  
			   </tr>	
        
           </logic:iterate>
           </logic:notEmpty>
		   </logic:present>
		
		  <logic:notPresent name="otherChrgList">
		   <tr class="white1">
		     <td ><input type="checkbox" name="chk" id="chk1" value=""/></td>
		   
	          <td ><input type="text" class="text" name="fromInstall" id="fromInstallment1" value="" style="text" maxlength="100" onkeypress="return isNumberKey(event);" /></td>
	          <td ><input type="text" class="text" name="toInstall" id="toInstallment1" style="text" value="" maxlength="10" onkeypress="return isNumberKey(event);"/></td>	      
	          <td ><input type="text" name="type" id="type1" value="FLAT"  class="text" readonly="readonly"/></td>
	          <td ><input type="text" class="text" name="amount" id="amount1" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	             <td >
	             
	             <input type="hidden" name="chargehiddenFld" id="chargehiddenFld1" size="20" value=""/>
				<html:text property="chargeDesc" styleClass="text" styleId="chargeDesc1" value="" readonly="true" maxlength="100" size="20" tabindex="-1"/>
		 		<html:button property="chargeButton" styleId="chargeButton" onclick="openLOVCommon(453,'otherChargesPlanForm','chargeDesc1','','', '','','','chargehiddenFld1');closeLovCallonLov('lbxCharge');" value=" " styleClass="lovbutton"></html:button>
			  </td>
          </tr>
               </logic:notPresent>
                  </table>   </td></tr>
  
   </table>
     
   <button type="button" class="topformbutton2"  onclick="return saveOtherChargesForEmiCalc();" title="Alt+V" accesskey="V" id="save"><bean:message key="button.save" /></button>
   <button type="button"  id="deleteRow" tabindex="-1" class="topformbutton2" onclick="removeOtherChargeRowForEmiCalc();" title="Alt+D" accesskey="D"><bean:message key="button.deleterow" /></button>&nbsp;&nbsp;&nbsp;					
   <button type="button" tabindex="-1" class="topformbutton2" onclick="addRowOtherChargePlanForEmiCalc('<bean:message key="msg.LoanAccountNo" />');" title="Alt+A" accesskey="A"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp; 
      

</fieldset>
</html:form>


<logic:present name="sms">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("sms").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		
		}
		else if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
	
		</script>
		</logic:present>
</DIV>
<DIV id=revisedcontainer></DIV>
</DIV>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>

<script>
	setFramevalues("otherChargesPlanForm");
</script>
  </body>
  </HTML>
  


