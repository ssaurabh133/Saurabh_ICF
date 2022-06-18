<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       Date currentDate = new Date();

%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

 <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
		<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/cibilReport.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/cibilVerification.js"></script>


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

<body oncontextmenu="return false" onload="enableAnchor();checkChanges('consumermasterform');">
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>

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
	          <td >BUREAU Report</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
<!-- <input type="hidden" name="cibilRepotFlag" id="cibilRepotFlag" value="${cibilRepotFlag}" /> <!-- add by saorabh -->
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<html:form action="/cibilCustomer" styleId="consumermasterform" method="post" enctype="multipart/form-data" >

<html:hidden property="path" styleId="path" value="<%=request.getContextPath()%>"/>






<logic:notPresent name="underWriter">
<logic:notPresent name="contactRecording">

<fieldset>
<legend><bean:message key="lbl.consumerMaster"/></legend>
	<table width="100%">
	
	<tr>
     <td width="13%"><bean:message key="lbl.consumername" /><span><font color="red">*</font></span></td>
     <td width="13%"> <html:text property="consumername" styleClass="text" styleId="consumername" value="${dispData[0].consumername}" maxlength="50" />
     </td>
        <td width="13%"><bean:message key="lbl.dateandtime" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
     <td width="13%"> <html:text property="leadDate"  styleClass="text3" styleId="leadDate" value="${dispData[0].leadDate}"  onchange="return checkDate('leadDate'); "/>
           	    <html:text property="leadTime"  styleId="leadTime" onchange="validateLeadTime();" maxlength="5" value="${dispData[0].leadTime}"  styleClass="text5" />&nbsp;(24hrs Format)
     </td>
       </tr>
     <tr>
      <td width="13%"><bean:message key="lbl.CIBILcodes" /><span><font color="red">*</font></span></td>
<td width="13%"> <html:text property="cibilCodes" styleClass="text" styleId="cibilCodes" value="${dispData[0].cibilCodes}" maxlength="10" />
     </td>
        <td width="13%"><bean:message key="lbl.Totalac" /></td>
     <td width="13%"> <html:text property="totalAc" styleClass="text" styleId="totalAc" maxlength="10" value="${dispData[0].totalAc}" />
     </td>
       </tr>
       <tr>
      <td width="13%"><bean:message key="lbl.Totaloverdueac" /><font color="red">*</font></td>
     <td width="13%"> <html:text property="totalOverdueac" styleClass="text" styleId="totalOverdueac" maxlength="10" value="${dispData[0].totalOverdueac}" />
     </td>
        <td width="13%"><bean:message key="lbl.highersacutionamount" /></td>
     <td width="13%"> <html:text property="higherSanctionAmount" styleClass="text" style="text-align: right" styleId="higherSanctionAmount" value="${dispData[0].higherSanctionAmount}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
     </td>
       </tr>
       <tr>

        <td width="13%"><bean:message key="lbl.overdue" /></td>
     <td width="13%"> <html:text property="overDue" styleClass="text" style="text-align: right" styleId="overDue" value="${dispData[0].overDue}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
      </td>
       <td width="13%"><bean:message key="lbl.currentbalance" /></td>
     <td width="13%"> <html:text property="currentBalance" styleClass="text" style="text-align: right" styleId="currentBalance" value="${dispData[0].currentBalance}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/>
      </td>
       </tr>
       <tr>
      <td width="13%"><bean:message key="lbl.noofenquiry" /></td>
     <td width="13%"> <html:text property="noofEnquiry" styleClass="text" styleId="noofEnquiry" maxlength="10" value="${dispData[0].noofEnquiry}" />
     </td>
       <td width="13%"><bean:message key="lbl.decison"/><span><font color="red">*</font></span></td>
	     <td width="13%"><label>
	    <html:select property="decison" styleId="decison" styleClass="text"  value="${dispData[0].decison}" >	
	           <html:option value="">--- Select ---</html:option>
		        <html:option value="A">Approved</html:option>
		        <html:option value="X">Rejected</html:option>
		      </html:select> 
	    </label></td>
       </tr> 
      
  <tr>
      
        <td width="13%"><bean:message key="lbl.comment" /><span><font color="red">*</font></span></td>
     <td width="13%"> 
     <textarea  class="text" id="comment" 	name="comment" 	onkeypress="textCounter(this,this.form.counter,999);">${dispData[0].comment}</textarea>

     <input	type="hidden" 	name="counter" 	maxlength="4" 	size="4"	value="1000" 	onblur="textCounter(this.form.counter,this,999);" />
     
     </td>
     
      
     
     
       </tr> 

             
       
	
	 </table>
	
	 <logic:present name="cibilRepotFlag">  
	
	 <fieldset>
<legend><bean:message key="lbl.upload.cibilReport"/></legend>

 
	<table width="70%">
	
	<tr>
      <td ><bean:message key="lbl.fileOne" /></td>
     <td ><html:file property="cibilReportFileOne" styleClass="text" styleId="cibilReportFileOne"  />
     </td>
        
       </tr>
     <tr>
      <td ><bean:message key="lbl.fileTwo" /></td>
     <td > <html:file property="cibilReportFileTwo" styleClass="text" styleId="cibilReportFileTwo"  />
     </td>
       
       </tr>
       <tr>
      <td ><bean:message key="lbl.fileThree" /></td>
     <td > <html:file property="cibilReportFileThree" styleClass="text" styleId="cibilReportFileThree"   />
     </td>
       
       </tr>
            
	
	 </table>

</fieldset>

	<table width="100%"> 
	  <tr>
	 <td>	
	 <logic:notEqual name="functionId" value="500000123">	
	    <button type="button" name="save" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveCIBIL()" ><bean:message key="button.save" /></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 </logic:notEqual>	  
	    <button type="button" name="viewDetails" id="viewDetails" title="Alt+W" accesskey="W" class="topformbutton2" onclick="callOnLinkOrButtonWindow('viewDetails');openDealDetails(${sessionScope.dealId});"><bean:message key="button.viewdealDetail"/></button>
	 </td>
	 	 
	</tr>
	</table>
</fieldset>


	
<fieldset>
		 <legend><bean:message key="lbl.cibilData" /></legend> 
<table width="100%" border="0" cellpadding="0" cellspacing="1" >
<tr>
 <td class="gridtd">
 <table width="100%" border="0" cellpadding="4" cellspacing="1">
 <tr class="white2">
 			<td><input type="checkbox" id="allchk" onclick="allChecked();" /></td>
 			<td><strong><bean:message key="lbl.consumername"/></strong></td>
        	<td><strong><bean:message key="lbl.dateandtime"/></strong></td>
			<td><b><bean:message key="lbl.CIBILcodes"/></b></td>
       		<td><b><bean:message key="lbl.Totalac"/></b></td>
      		<td><strong><bean:message key="lbl.Totaloverdueac"/></strong></td>
      		<td><strong><bean:message key="lbl.highersacutionamount"/></strong></td>
        	<td><strong><bean:message key="lbl.overdue"/></strong></td>
			<td><b><bean:message key="lbl.currentbalance"/></b></td>
       		<td><b><bean:message key="lbl.noofenquiry"/></b></td>
      		<td><strong><bean:message key="lbl.decison"/></strong></td>
      		<td><strong><bean:message key="lbl.comment"/></strong></td>
      		<td><strong><bean:message key="lbl.downLoad"/></strong></td>
      	</tr>
  
<logic:present name="list">
<logic:iterate name="list" id="sublist" >

  <tr  class="white1">
  			<td><input type="checkbox" name="chk" id="chk" value="${sublist.civilId}" /></td>
 			<td>${sublist.consumername}</td>
			<td>${sublist.dateandtime}</td>
			<td>${sublist.cibilCodes}</td>
			<td>${sublist.totalAc}</td>
			<td>${sublist.totalOverdueac}</td>
			<td>${sublist.higherSanctionAmount}</td>
			<td>${sublist.overDue}</td>
			<td>${sublist.currentBalance}</td>
			<td>${sublist.noofEnquiry}</td>
			<td>${sublist.decison}</td>
			<td>${sublist.comment}</td>
		<td>
			<logic:present name="uploadedDocList">
            <logic:iterate name="uploadedDocList" id="uploadDoclist" >
			  <logic:equal name="uploadDoclist" property="dealId" value="${sublist.civilId}">
				   <a href="#" onclick="downLoadCibilReport('${uploadDoclist.fileName}',${sublist.civilId});">${uploadDoclist.fileName}</a><br/>
			 </logic:equal>
			
			</logic:iterate>
			</logic:present>
		</td>
			
	 </tr>
 
  </logic:iterate>
   
  </logic:present>

</table>
</td></tr></table>
<logic:notEqual name="functionId" value="500000123">	
<logic:present name="list">
  <logic:notEmpty name="list">
    <button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return deleteCibilReport();"><bean:message key="button.delete" /></button>
   </logic:notEmpty>
</logic:present>
</logic:notEqual>
</fieldset>    

</logic:present> 
</logic:notPresent>
</logic:notPresent>

<%-- -------------------------------------------- view mode in under writer-------------------------------------------------------------- --%>
<logic:present name="underWriter" >


	<fieldset>
		 <legend><bean:message key="lbl.cibilData" /></legend> 
<table width="100%" border="0" cellpadding="0" cellspacing="1" >
<tr>
 <td class="gridtd">
 <table width="100%" border="0" cellpadding="4" cellspacing="1">
 <tr class="white2">
 			<td><input type="checkbox" id="allchk" onclick="allChecked();" disabled="disabled"/></td>
 			<td><strong><bean:message key="lbl.consumername"/></strong></td>
        	<td><strong><bean:message key="lbl.dateandtime"/></strong></td>
			<td><b><bean:message key="lbl.CIBILcodes"/></b></td>
       		<td><b><bean:message key="lbl.Totalac"/></b></td>
      		<td><strong><bean:message key="lbl.Totaloverdueac"/></strong></td>
      		<td><strong><bean:message key="lbl.highersacutionamount"/></strong></td>
        	<td><strong><bean:message key="lbl.overdue"/></strong></td>
			<td><b><bean:message key="lbl.currentbalance"/></b></td>
       		<td><b><bean:message key="lbl.noofenquiry"/></b></td>
      		<td><strong><bean:message key="lbl.decison"/></strong></td>
      		<td><strong><bean:message key="lbl.comment"/></strong></td>
      		<td><strong><bean:message key="lbl.downLoad"/></strong></td>
      	</tr>
  
<logic:present name="list">
<logic:iterate name="list" id="sublist" >

  <tr  class="white1">
  			<td><input type="checkbox" name="chk" id="chk" value="${sublist.civilId}" disabled="disabled"/></td>
 			<td>${sublist.consumername}</td>
			<td>${sublist.dateandtime}</td>
			<td>${sublist.cibilCodes}</td>
			<td>${sublist.totalAc}</td>
			<td>${sublist.totalOverdueac}</td>
			<td>${sublist.higherSanctionAmount}</td>
			<td>${sublist.overDue}</td>
			<td>${sublist.currentBalance}</td>
			<td>${sublist.noofEnquiry}</td>
			<td>${sublist.decison}</td>
			<td>${sublist.comment}</td>
			<td>
			<logic:present name="uploadedDocList">
            <logic:iterate name="uploadedDocList" id="uploadDoclist" >
			  <logic:equal name="uploadDoclist" property="dealId" value="${sublist.civilId}">
				   <a href="#" onclick="downLoadCibilReport('${uploadDoclist.fileName}',${sublist.civilId});">${uploadDoclist.fileName}</a><br/>
			 </logic:equal>
			
			</logic:iterate>
			</logic:present>
		</td>
	
	 </tr>
 
  </logic:iterate>
   
  </logic:present>

</table>
</td></tr></table>
<!--<logic:present name="list">-->
<!--  <logic:notEmpty name="list">-->
<!--    <button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return deleteCibilReport();"><bean:message key="button.delete" /></button>-->
<!--   </logic:notEmpty>-->
<!--</logic:present>-->
</fieldset>    



</logic:present>

<%-- -------------------------------------------- view mode in Contact Recording-------------------------------------------------------------- --%>
<logic:present name="contactRecording">
	<fieldset>
		 <legend><bean:message key="lbl.cibilData" /></legend> 
<table width="100%" border="0" cellpadding="0" cellspacing="1" >
<tr>
 <td class="gridtd">
 <table width="100%" border="0" cellpadding="4" cellspacing="1">
 <tr class="white2">
 			<td><input type="checkbox" id="allchk" onclick="allChecked();" disabled="disabled"/></td>
 			<td><strong><bean:message key="lbl.consumername"/></strong></td>
        	<td><strong><bean:message key="lbl.dateandtime"/></strong></td>
			<td><b><bean:message key="lbl.CIBILcodes"/></b></td>
       		<td><b><bean:message key="lbl.Totalac"/></b></td>
      		<td><strong><bean:message key="lbl.Totaloverdueac"/></strong></td>
      		<td><strong><bean:message key="lbl.highersacutionamount"/></strong></td>
        	<td><strong><bean:message key="lbl.overdue"/></strong></td>
			<td><b><bean:message key="lbl.currentbalance"/></b></td>
       		<td><b><bean:message key="lbl.noofenquiry"/></b></td>
      		<td><strong><bean:message key="lbl.decison"/></strong></td>
      		<td><strong><bean:message key="lbl.comment"/></strong></td>
      		<td><strong><bean:message key="lbl.downLoad"/></strong></td>
      	</tr>
  
<logic:present name="list">
<logic:iterate name="list" id="sublist" >

  <tr  class="white1">
  			<td><input type="checkbox" name="chk" id="chk" value="${sublist.civilId}" disabled="disabled"/></td>
 			<td>${sublist.consumername}</td>
			<td>${sublist.dateandtime}</td>
			<td>${sublist.cibilCodes}</td>
			<td>${sublist.totalAc}</td>
			<td>${sublist.totalOverdueac}</td>
			<td>${sublist.higherSanctionAmount}</td>
			<td>${sublist.overDue}</td>
			<td>${sublist.currentBalance}</td>
			<td>${sublist.noofEnquiry}</td>
			<td>${sublist.decison}</td>
			<td>${sublist.comment}</td>
			<td>
			<logic:present name="uploadedDocList">
            <logic:iterate name="uploadedDocList" id="uploadDoclist" >
			  <logic:equal name="uploadDoclist" property="dealId" value="${sublist.civilId}">
				   <a href="#" onclick="downLoadCibilReport('${uploadDoclist.fileName}',${sublist.civilId});">${uploadDoclist.fileName}</a><br/>
			 </logic:equal>
			
			</logic:iterate>
			</logic:present>
		</td>
	
	 </tr>
 
  </logic:iterate>
   
  </logic:present>

</table>
</td></tr></table>
<!--<logic:present name="list">-->
<!--  <logic:notEmpty name="list">-->
<!--    <button type="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="return deleteCibilReport();"><bean:message key="button.delete" /></button>-->
<!--   </logic:notEmpty>-->
<!--</logic:present>-->
</fieldset>    

</logic:present>

<logic:present name="message">
<script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='U')
	{
		alert('<bean:message key="msg.upperLimit" />');
		document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/cibilCustomer.do";
	    document.getElementById('sourcingForm').submit();
	}
</script>
</logic:present>

<logic:present name="msg">
	<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S'){
	alert('<bean:message key="lbl.datadeleteSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/cibilCustomer.do";
	document.getElementById('sourcingForm').submit();
	
	}else if('<%=request.getAttribute("msg").toString()%>'=='E'){
	alert('<bean:message key="lbl.dataNtdeleteSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/cibilCustomer.do";
	document.getElementById('sourcingForm').submit();
	} 
	else if('<%=request.getAttribute("msg").toString()%>'=='M'){
	alert('<bean:message key="lbl.dataSave"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/cibilCustomer.do";
	document.getElementById('sourcingForm').submit();
	
	}else if('<%=request.getAttribute("msg").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNotSave"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/cibilCustomer.do";
	document.getElementById('sourcingForm').submit();
	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='UD'){
	alert('<bean:message key="lbl.uniqueUpload"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/cibilCustomer.do";
	document.getElementById('sourcingForm').submit();
	
	}
	
	
     </script>
	</logic:present>



</html:form>	
<script>
	setFramevalues("consumermasterform");
</script>

  </body>
  
  
  <!-- Parvez Starts -->
  
<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=path%>/js/cpScript/cibilVerification.js"></script>
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

<body onload="enableAnchor();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="cibilVerification"  method="post"  action="/viewCibilDispatchAction" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<!-- <fieldset>
<legend><bean:message key="lbl.cibilView" /></legend>  -->
 
 <!--   <table width="100%">
    <tr>
    	<td><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
  	<td>
				<html:hidden styleClass="text" property="dealID" styleId="dealID" value="${gridList[0].dealID}"/>
	 			<html:text styleClass="text" maxlength="10" property="dealNO" styleId="dealNO" readonly="true" value="${gridList[0].dealNO}" tabindex="-1"/>
				<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(2010,'cibilVerificationDispatchAction','dealNO','','','','','','customerName');" value=" " styleClass="lovbutton"></html:button>
   		</td>  
   	
   		<td><bean:message key="lbl.customerName"/></td>
		<td><html:text styleClass="text" maxlength="10" property="customerName" styleId="customerName" style="" readonly="true" value="${gridList[0].customerName}" maxlength="100"/></td>
   </tr>
 
   <tr>
   		<td>
   		<button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return searchCibilDeal();"><bean:message key="button.search" /></button>
   	
   		</td>
   		
   </tr> 
 </table>		-->

<!--  </fieldset> -->
<logic:present name="cibilRepotFlag">

<logic:present name="roleList">	
<logic:notEmpty name="roleList">
<fieldset><legend><bean:message key="lbl.deals"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td class="gridtd">
		   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		<tr class="white2">
    	  	<td align="center"><b>Select</b></td> 
	 	 			<!-- <td align="center"><b><bean:message key="lbl.dealNo"/></b></td> 
	 	 		<td align="center"><b><bean:message key="lbl.cibilId"/></b></td>
	 	 		-->
	 	 		 
	 	 		
	 	 		
	 	 		
	 	 		<td align="center"><b><bean:message key="lbl.customerId"/></b></td>
	 	 		<td align="center"><b><bean:message key="lbl.customerName"/></b></td>
	 	 		
         		<td align="center"><b><bean:message key="lbl.Type"/></b></td>
         		
         		
         		<td align="center"><b><bean:message key="lbl.customerRole"/></b></td>
         		<td align="center"><b><bean:message key="lbl.cibilScore"/></b></td>
         		<td align="center"><b><bean:message key="lbl.crifHighMarkScore"/></b></td>
         		<td align="center"><b><bean:message key="lbl.experianScore"/></b></td>
         		<td align="center"><b><bean:message key="lbl.cibilStatus"/></b></td>
         <!--  		<td align="center"><b><bean:message key="lbl.gender"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberName"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.memberId"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.inquiryPurpus"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.refranceNo"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.dob"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address1"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address2"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.address3"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.addressType"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.addressDetail"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.dist"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.state"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.pincode"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.country"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.panNo"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.No"/></b></td>
	     		<td align="center"><b><bean:message key="voterId"/></b></td>
	     		<td align="center"><b><bean:message key="passport"/></b></td>
	     		<td align="center"><b><bean:message key="drivingLicense"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.primary"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.alter"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.toll"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.fax"/></b></td> -->
		    </tr>
			<%-- <logic:iterate name="gridList" id="sublist"> --%>
			 <logic:iterate name="roleList" id="sublist" indexId="index">
			<input type="hidden"  name="cibilDone"  value="${sublist.cibilDone}" id="cibilDone<%=index.intValue()%>"/>
					<input type="hidden"  name="dealId"  value="${sessionScope.dealId}" id="dealId<%=index.intValue()%>"/>
			<input type="hidden"  name="customerId"  value="${sublist.customerId}" id="customerId<%=index.intValue()%>"/>
			<tr class="white1">
				<td align="center"><input type="radio" name="radioId" id="radioId<%=index.intValue()%>"  value="<%=index.intValue()%>" /></td>
	     		
	      	  <td>${sublist.customerId}</td>
	     		<td>${sublist.customerName}</td>
	     		<td>${sublist.applicantCategory}</td>
	     		<td>${sublist.applicantType}</td>
	     		<td>${sublist.cibilScore}</td>
				<td>${sublist.crifHighMarkScore}</td>
				<td>${sublist.experianScore}</td>
	    		   
	    		
				<td>${sublist.cibilResponse}</td>
	    	<%-- 	<td>${sublist.customerType}</td>
	    		   
	    		<td>${sublist.customerRole}</td> 
	    		
	     	<input type="hidden" name="cibilDone" id="cibilDone" value="${sublist.cibilDone}" /> --%>
	    		<input type="hidden" name="lbxCibilId" id="lbxCibilId" value="${sublist.lbxCibilId}" />		 
	    		   	
	     	</tr> 
   			</logic:iterate>
   		 	<td colspan="32" class="white1"><button type="button" name="viewGenerate" title="Alt+S" accesskey="S" id="button" class="topformbutton3" onclick=" viewCibilDone();"><bean:message key="button.viewCibilReport" /></button></td>
   		 </table>
   	</td>
   </tr>
   </table>
        <fieldset>
<legend><bean:message key="lbl.cibilVerificationHst" /></legend>
<table width="100%">
    <tr>
    	<td>Report Name<span><font color="red">*</font></span></td>
    	<td>	
    			<input type="hidden"  name="dealID"  value="${sessionScope.dealId}" id="dealID"/>
				<html:hidden styleClass="text" property="LinkId" styleId="LinkId" value=""/>
				<html:text styleClass="text" maxlength="10" property="reportName" styleId="reportName" readonly="true" value="" tabindex="-1"/>
				<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(192031,'cibilVerification','LinkId','','','','','','reportName');" value=" " styleClass="lovbutton"></html:button>
   		</td>  
   	 </tr>
   	 <tr>
   	 <td>
   		<button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return generateHistoricalData();">ViewGenerate</button>
   		</td>
   	 </tr>
      
 </table>
</fieldset>
</fieldset>
</logic:notEmpty>

<logic:empty name="roleList">
<fieldset><legend><bean:message key="lbl.deals"/></legend>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td class="gridtd">
		   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		<tr class="white2">
    	 		<td align="center"><b>Select</b></td>
    	   	  <td align="center"><b><bean:message key="lbl.customerId"/></b></td>
	 	 		<td align="center"><b><bean:message key="lbl.customerName"/></b></td>
	 	 		
	 	 	     <td align="center"><b><bean:message key="lbl.Type"/></b></td>
         	    <td align="center"><b><bean:message key="lbl.customerRole"/></b></td>
	     		<td align="center"><b><bean:message key="lbl.cibilStatus"/></b></td>
		    </tr>
   		 </table>
   	</td>
   </tr>
   </table>
</fieldset>
</logic:empty>
</logic:present>

</logic:present>
</html:form>
  <!-- Parvez Ends -->
		</html:html>