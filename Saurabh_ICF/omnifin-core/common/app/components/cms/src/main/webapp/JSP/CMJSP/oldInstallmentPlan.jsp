<!-- 
Author Name :- Nishant Rai
Date of Creation :12-08-2013
Purpose :-  screen for the Old Installment Plan
-->
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		     <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript">	
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('installmentPlanForm').fromInstallment1.focus();
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


<body onload="enableAnchor();recoveryPercent();fntab();checkChanges('installmentPlanForm');">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
<input type="hidden" class="text" name="installmentType"  id="installmentType" value="<%=request.getAttribute("installmentType") %>" style="text" />
<input type="hidden" class="text" name="totalInstallment" id="totalInstallment" style="text" value="<%=request.getAttribute("totalInstallment") %>" />
<input type="hidden" class="text" name="rateType"  id="rateType" value="${rateType }" style="text" />
<input type="hidden" class="text" name="loanAmount"  id="loanAmount" value="${loanAmount }" style="text" />

<div id=centercolumn>
<div id=revisedcontainer>


<html:form action="/installmentPlanProcess.do" method="post" styleId="installmentPlanForm">

<br/>
<fieldset>
<legend><bean:message key="lbl.installmentPlan" /></legend>
<table width="50%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td><bean:message key="lbl.recoveryType" /><font color="red">*</font></td>
<td>
<logic:present name="installmentType">
       <logic:notEqual name="installmentType" value="I">
<html:select property="recoveryType" styleId="recoveryType" styleClass="text" onchange="changeRecoveryType();" value="${installmentList[0].recoveryType}" disabled="true">
		<html:option value="P" >PERCENTAGE</html:option>
	    <html:option value="F" >FLAT</html:option>
</html:select>
</logic:notEqual>
</logic:present>
<logic:present name="installmentType">
       <logic:equal name="installmentType" value="I">
<html:select property="recoveryType" styleId="recoveryType" styleClass="text" onchange="changeRecoveryType();" value="F" disabled="true">
		<html:option value="P" >PERCENTAGE</html:option>
	    <html:option value="F" >FLAT</html:option>
</html:select>
</logic:equal>
</logic:present>
</td>
  </tr>
  </table><br />
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="A"/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
        <tr class="white2">
    <td width="3%"><input type="checkbox" disabled="disabled" name="allchkd" id="allchkd" onclick="allChecked();"/></td>
    <logic:present name="installmentType">
       <logic:equal name="installmentType" value="I">
       <td ><bean:message key="msg.cycleDate"></bean:message> </td>
       </logic:equal>
       </logic:present> 
         <td  ><bean:message key="lbl.fromInstallment"></bean:message> </td>
          <td ><bean:message key="lbl.toInstallment"></bean:message></td>
        
          <td ><bean:message key="lbl.recoveryPercen"></bean:message> </td>
           <td ><bean:message key="lbl.principalAmount"></bean:message> </td>
          <td ><bean:message key="lbl.installmentAmount"></bean:message> </td>
                  </tr>
   <logic:present name="viewMode">               
   <logic:present name="installmentList">
   <logic:notEmpty name="installmentList">
                  <logic:present name="installmentType">
       <logic:notEqual name="installmentType" value="I">
   <logic:iterate name="installmentList" id="subInstallmentList" indexId="count">
   
              <tr class="white1">
	          <td><input type="checkbox" disabled="disabled" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
	          <td  ><input type="text" disabled="disabled" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" /></td>
	          <td ><input type="text" disabled="disabled" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" /></td>	      
	          <td ><input type="text" disabled="disabled" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}"  /></td>
	          <td ><input type="text" class="text" disabled="disabled" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          <td ><input type="text" class="text" disabled="disabled" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
              </tr>
          
           </logic:iterate>
           </logic:notEqual>
           </logic:present>
           </logic:notEmpty>
		 </logic:present>
         </logic:present>         
                  
                  
       <logic:present name="installmentList">
        <logic:notPresent name="viewMode">
         <logic:notEmpty name="installmentList">
           <logic:present name="installmentType">
       <logic:notEqual name="installmentType" value="I">
        <logic:iterate name="installmentList" id="subInstallmentList" indexId="count">
   
		   <tr class="white1">
	    <td><input type="checkbox" disabled="disabled" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
	          <td  ><input type="text" disabled="disabled" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100"/></td>
	          <td ><input type="text" disabled="disabled" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" /></td>	      
	          <td ><input type="text" disabled="disabled" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}" /></td>
	          <td ><input type="text" class="text" disabled="disabled" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          <td ><input type="text" class="text" disabled="disabled" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
          </tr>
          
           </logic:iterate>
           </logic:notEqual>
           </logic:present>
           </logic:notEmpty>
            </logic:notPresent>
		   </logic:present>
		 
		  <logic:notPresent name="installmentList">
		  <logic:notPresent name="viewMode">
		    <logic:present name="installmentType">
       <logic:notEqual name="installmentType" value="I">
		   <tr class="white1">
		     <td   ><input type="checkbox" name="chk" id="chk1" value="" disabled="disabled"/></td>
	          <td  ><input type="text" class="text" disabled="disabled" name="fromInstall" id="fromInstallment1" value="" style="text" maxlength="100"/></td>
	          <td ><input type="text" class="text" disabled="disabled" name="toInstall" id="toInstallment1" style="text" value="" maxlength="10"/></td>	      
	          <td ><input type="text" name="recoveryPer" disabled="disabled" id="recoveryPercen1" value=""  class="text"/></td>
	          <td ><input type="text" class="text" disabled="disabled" name="principalAmount" id="principalAmount" value=""  /></td>
	          <td ><input type="text" class="text" disabled="disabled" name="installmentAmount" id="installmentAmount" value=""/></td>
          </tr>
          </logic:notEqual>
          </logic:present>
		     </logic:notPresent>
               </logic:notPresent>
               <%--Richa space starts --%>
               <logic:present name="installmentType">
       <logic:equal name="installmentType" value="I">
                <logic:present name="installmentList">
        <logic:notPresent name="viewMode">
         <logic:notEmpty name="installmentList">
        <logic:iterate name="installmentList" id="subInstallmentList" indexId="count">
   
		  <script>
$(function() {
	$("#dueDate<%=count.intValue()+1%>").datepicker({
	 changeMonth: true,
	 changeYear: true,
	 yearRange: '1950:2070',
     showOn: 'both',
     buttonImage: document.getElementById("CalImage").value,
	  	buttonImageOnly: true,
	  	dateFormat: document.getElementById("formatD").value,
	  	defaultDate: document.getElementById("businessdate").value,
});
 	});
</script>
<tr class="white1">
			         <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
			          <td  ><input type="text" class="text" name="dueDate" id="dueDate<%=count.intValue()+1%>"  maxlength="100" onchange="checkDate('dueDate<%=count.intValue()+1%>')" value="${subInstallmentList.dueDatee}"/></td>
			          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" onkeypress="return isNumberKey(event);"/></td>
			          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" onkeypress="return isNumberKey(event);"/></td>	      
			          <td ><input type="text" class="text" name="recoveryPer" readonly="readonly" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}" /></td>
			          <td ><input type="text" class="text" name="principalAmount"  id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
			          <td ><input type="text" class="text" name="installmentAmount" readonly="readonly" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          </tr>
          
           </logic:iterate>
           </logic:notEmpty>
            </logic:notPresent>
		   </logic:present>
		   </logic:equal>
		   </logic:present>
               <%--Richa space ends --%>
                  </table>   
  		</td>
  		</tr>
   </table>

</fieldset>
</html:form>

</div>

</div>



  </body>
  </html>

