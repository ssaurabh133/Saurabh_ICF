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
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js" ></script>
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


<body onload="enableAnchor();" >

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
<input type="hidden" name="psize" id="psize"/>
<div id=centercolumn>
<div id=revisedcontainer>
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
	          <td >Disbursal Schedule</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>

<html:form action="/disbursalSaveProcess.do" method="post" styleId="NoOfDisbForm">
<input type="hidden" name="dealLoanAmount" value="${dealLoanAmount}" id="dealLoanAmount" />
 <logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" name="businessdate" id="businessdate" value='${userobject.businessdate}'/> 
<fieldset>
<logic:notPresent name = "specialConditionDV">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable" >
        <tr class="white2">
       <td width="3%" ><input type="checkbox"  name="allchk" id="allchk" onclick="allChecked();" /></td>
           <td  ><b><bean:message key="lbl.noOfDisbursal"/> </b></td>
          <td ><b><bean:message key="lbl.dateOfDisbursal"/></b></td>

          <td ><b><bean:message key="lbl.descOfDisbursal"/></b></td>
           <td ><b><bean:message key="lbl.amountOfDisbursal"/></b></td>
          </tr>
          <logic:present name="disbList">
            <logic:empty name="disbList">
	           <tr >
	     			<td class="white1"><input type="checkbox"  name="chk" id="chk1" value=""/></td>
	          		 <td  class="white1"><html:text property="noOfDisbursal"  styleId="noOfDisbursal1" styleClass="text" value="1" /></td>
	          		 <td class="white1"><b><html:text property="dateOfDisbursal" styleId="dateOfDisbursal1" styleClass="text" value="" onchange="checkDate('dateOfDisbursal1');"/></b></td>
	         		 <td class="white1"><b><html:text property="descOfDisbursal" styleId="descOfDisbursal1" styleClass="text" value="" maxlength="100"/></b></td>
	          		 <td class="white1" ><b><html:text property="amountOfDisbursal" styleId="amountOfDisbursal1" styleClass="text" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></b></td>
	          </tr>
	       </logic:empty>
	       <logic:notEmpty name="disbList">
	         <logic:iterate name="disbList" id="subDisbList" indexId="counter">
	         	<script type="text/javascript">
						makeChoice('${counter + 1}');
				</script>
	           <tr >
	           
	     			<td class="white1"><input type="checkbox"  name="chk" id="chk${counter+1 }" value=""/></td>
	          		 <td  class="white1"><html:text property="noOfDisbursal" styleId="noOfDisbursal${counter+1 }" styleClass="text" value="${subDisbList.noOfDisb}" /></td>
	          		 <td class="white1"><b><html:text property="dateOfDisbursal" styleId="dateOfDisbursal${counter+1 }" styleClass="text" value="${subDisbList.dateOfDisb}" onchange="checkDate('dateOfDisbursal${counter+1 }');"/></b></td>
	         		 <td class="white1"><b><html:text property="descOfDisbursal" styleId="descOfDisbursal${counter+1 }" styleClass="text" value="${subDisbList.descOfDisb}" maxlength="100"/></b></td>
	          		 <td class="white1" ><b><html:text property="amountOfDisbursal" styleId="amountOfDisbursal${counter+1 }" styleClass="text" value="${subDisbList.amountOfDisb}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></b></td>
	          </tr>
	         </logic:iterate>
	       </logic:notEmpty>
        </logic:present>
        </table>  
   </td>
   </tr> 
  
</table>
	<logic:equal name="functionId" value="3000296"> 	
<button type="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveNoDisb();" ><bean:message key="button.save" /></button>&nbsp;&nbsp;&nbsp;	
 <button type="button" tabindex="-1" class="topformbutton2" onclick="addROWAtDeal('<bean:message key="msg.LoanAccountNo" />');makeChoice();" title="Alt+A" accesskey="A"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp;
 <button type="button"  id="deleteRow" tabindex="-1" class="topformbutton2" onclick="removeRowDisbSche();" title="Alt+D" accesskey="D"><bean:message key="button.deleterow" /></button>				
  </logic:equal>
	<logic:notEqual name="functionId" value="3000296">
						
		</logic:notEqual>

 </logic:notPresent>
 <logic:present name = "specialConditionDV">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable" >
        <tr class="white2">
       <td width="3%" ><input type="checkbox"  name="allchk" id="allchk" disabled="disabled" onclick="allChecked();" /></td>
           <td  ><b><bean:message key="lbl.noOfDisbursal"/> </b></td>
          <td ><b><bean:message key="lbl.dateOfDisbursal"/></b></td>

          <td ><b><bean:message key="lbl.descOfDisbursal"/></b></td>
           <td ><b><bean:message key="lbl.amountOfDisbursal"/></b></td>
          </tr>
          <logic:present name="disbList">
            <logic:empty name="disbList">
	           <tr >
	     			<td class="white1"><input type="checkbox"  name="chk" disabled="disabled" id="chk1" value=""/></td>
	          		 <td  class="white1"><html:text property="noOfDisbursal"  styleId="noOfDisbursal1" readonly="true" styleClass="text" value="1" /></td>
	          		 <td class="white1"><b><html:text property="dateOfDisbursal" styleId="dateOfDisbursal1" disabled="true"  styleClass="text" value="" /></b></td>
	         		 <td class="white1"><b><html:text property="descOfDisbursal" styleId="descOfDisbursal1" readonly="true" styleClass="text" value="" /></b></td>
	          		 <td class="white1" ><b><html:text property="amountOfDisbursal" styleId="amountOfDisbursal1" readonly="true" styleClass="text" value="" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></b></td>
	          </tr>
	       </logic:empty>
	       <logic:notEmpty name="disbList">
	         <logic:iterate name="disbList" id="subDisbList" indexId="counter">
	         	<script type="text/javascript">
						makeChoice('${counter + 1}');
				</script>
	           <tr >
	           
	     			<td class="white1"><input type="checkbox"  name="chk" id="chk${counter+1 }" disabled="disabled" value=""/></td>
	          		 <td  class="white1"><html:text property="noOfDisbursal" styleId="noOfDisbursal${counter+1 }" readonly="true" styleClass="text" value="${subDisbList.noOfDisb}" /></td>
	          		 <td class="white1"><b><html:text property="dateOfDisbursal" styleId="dateOfDisbursal${counter+1 }" disabled="true"  styleClass="text" value="${subDisbList.dateOfDisb}"/></b></td>
	         		 <td class="white1"><b><html:text property="descOfDisbursal" styleId="descOfDisbursal${counter+1 }" readonly="true" styleClass="text" value="${subDisbList.descOfDisb}" /></b></td>
	          		 <td class="white1" ><b><html:text property="amountOfDisbursal" styleId="amountOfDisbursal${counter+1 }" readonly="true" styleClass="text" value="${subDisbList.amountOfDisb}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></b></td>
	          </tr>
	         </logic:iterate>
	       </logic:notEmpty>
        </logic:present>
        </table>  
   </td>
   </tr> 
  
</table>			
 </logic:present>
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
</div>
<div id=revisedcontainer></div>
</div>

  </body>
  </html>

