<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
	<html>
	
	<head>
		
		 
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/buyerSupplier.js"></script>

		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

         <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
   
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
   

       <title> <bean:message key="lbl.others" /></title>
	</head>
<body oncontextmenu="return false" onload="enableAnchor();document.getElementById('otherForm').otherName.focus();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<fieldset>   
    <table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
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
	          <td >Buyer Supplier</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 </fieldset>	

<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>

<div id=centercolumn >
<div id=revisedcontainer>

<div id="updateAddress">

   <fieldset ><legend><bean:message key="lbl.others" /></legend>


<html:form action="/otherRelationProcessAction" styleId="otherForm" method="post">

<logic:present name="update">
<input type="hidden" id="otherUniqueId" name="otherUniqueId" value="${otherDetailList[0].otherUniqueId}"/>
<table cellspacing="0" cellpadding="3" border="0" width="100%">


	<tr>
        <td><bean:message key="lbl.othername" /><font color="red">*</font></td>
        <td><html:text property="otherName" styleId="otherName" styleClass="text" value="${otherDetailList[0].otherName}" onchange="return upperMe('otherName');" maxlength="50"/></td>
         <td><bean:message key="lbl.relationshipS" /><font color="red">*</font></td>
        

				  <td><html:select styleClass="text"  styleId="relationships" value="${otherDetailList[0].relationships}" property="relationships">
                  <html:option value=""><bean:message key="lbl.select" /></html:option>  

        		      <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				

                  </html:select>
                  </td>
    </tr>

	<tr>
		<td> <bean:message key="lbl.mbnumber" /><font color="red">*</font></td>
		<td><html:text property="primaryOtherMbNo" styleId="primaryOtherMbNo" styleClass="text" maxlength="10" style="text-align: right" onkeypress="return isNumberKey(event);" value="${otherDetailList[0].primaryOtherMbNo}"/></td>	
        <td><bean:message key="lbl.landlineno" /></td>
		<td><html:text property="alternateOtherPhNo" maxlength="15" styleId="alternateOtherPhNo" styleClass="text" style="text-align: right" onkeypress="return isNumberKey(event);" value="${otherDetailList[0].alternateOtherPhNo}"/></td>	
	</tr>		
	
	<tr>	
		<td><bean:message key="lbl.vintageOfRelationship" /><font color="red">*</font></td>
		<td><html:text property="knowingSince" styleClass="text" styleId="knowingSince" maxlength="3" value="${otherDetailList[0].knowingSince}" onkeypress="return isNumberKey(event);" /></td>					
		<td><bean:message key="lbl.addr" /></td>
		<td ><textarea name="addRef" id="addRef" class="text" maxlength="150" >${otherDetailList[0].addRef}</textarea></td>
	</tr>	

	
	<tr>
        <td class="white">
    
        
        <button type="button" name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick=" return updateOtherRelation();"><bean:message key="button.save" /></button>
   
    
       </td>
	</tr>
</table>

<td colspan="4" >
<fieldset><legend><bean:message key="lbl.others" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2">
	        <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allOtherChecked();"><bean:message key="contact.select" /></b></td> 
			<td><B><bean:message key="lbl.othername" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			<td><B><bean:message key="lbl.vintageOfRelationship" /></b></td>
			<td ><B><bean:message key="lbl.mbnumber" /></b></td>						
			<td><B><bean:message key="lbl.landlineno" /></b></td>	
		</tr>
			<logic:present name="otherList">
			<logic:iterate id="subotherList" name="otherList">
		<tr class="white1">
			<td ><input type="checkbox" name="chk" id="chk" value="${subotherList.otherUniqueId}"/></td>
			<td ><a href="#" id="anchor0" onclick="return modifyReference(${subotherList.otherUniqueId});">${subotherList.otherName}</a></td>
			<td >${subotherList.relationships}</td>
			<td>${subotherList.knowingSince}</td>
			<td>${subotherList.primaryOtherMbNo}</td>
			<td>${subotherList.alternateOtherPhNo}</td>
		</tr>	
			</logic:iterate>
			</logic:present>		
		</table>
</table>
</fieldset>
		<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteOtherRelation();"><bean:message key="button.delete" /></button>

</logic:present>

<logic:notPresent name="update">

<table cellspacing="0" cellpadding="3" border="0" width="100%">

	<tr>
        <td><bean:message key="lbl.othername" /><font color="red">*</font></td>
        <td><html:text property="otherName" styleId="otherName" styleClass="text" value="" onchange="return upperMe('otherName');" maxlength="50"/></td>
        <td><bean:message key="lbl.relationshipS" /><font color="red">*</font></td>

				  <td><html:select styleClass="text" value=""  styleId="relationships" property="relationships">
                  <html:option value=""><bean:message key="lbl.select" /></html:option>  
        				<logic:present name="relationType">
        		 <html:optionsCollection name="relationType" label="relationshipS" value="relationCode"/>        				
        				</logic:present>
                  </html:select>
                  </td>
    </tr>
 
	<tr>
		<td> <bean:message key="lbl.mbnumber" /><font color="red">*</font></td>
		<td><html:text property="primaryOtherMbNo" styleId="primaryOtherMbNo" value="" styleClass="text" maxlength="10" style="text-align: right" onkeypress="return isNumberKey(event);" /></td>	
        <td><bean:message key="lbl.landlineno" /></td>
		<td><html:text property="alternateOtherPhNo" maxlength="15" value="" styleId="alternateOtherPhNo" styleClass="text" style="text-align: right" onkeypress="return isNumberKey(event);" /></td>	
	</tr>		
	
	<tr>	
		<td><bean:message key="lbl.vintageOfRelationship" /><font color="red">*</font></td>
		<td><html:text property="knowingSince" styleClass="text" styleId="knowingSince" value="" maxlength="3"  onkeypress="return isNumberKey(event);" /></td>					
		<td><bean:message key="lbl.addr" /></td>
		<td ><textarea name="addRef" id="addRef" class="text" maxlength="150" ></textarea></td>
	</tr>		
	
	
	<tr>
        <td class="white">
      
        
        <button type="button"  name="button1" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return insertOtherRelation();"><bean:message key="button.save" /></button>
        
       
         </td>
	</tr>
</table>

<td colspan="4" >
<fieldset><legend><bean:message key="lbl.others" /></legend>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
		<tr>
		<td class="gridtd">
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<tr class="white2">
	        <td ><B><input type="checkbox" name="allchk" id="allchk" onclick="allOtherChecked();"><bean:message key="contact.select" /></b></td> 
			<td><B><bean:message key="lbl.othername" /></b></td>
			<td><B><bean:message key="lbl.relationshipS" /></b></td>
			<td><B><bean:message key="lbl.vintageOfRelationship" /></b></td>
			<td ><B><bean:message key="lbl.mbnumber" /></b></td>						
			<td><B><bean:message key="lbl.landlineno" /></b></td>	
		</tr>
			<logic:present name="otherList">
			<logic:iterate id="subotherList" name="otherList">
		<tr class="white1">
			<td ><input type="checkbox" name="chk" id="chk" value="${subotherList.otherUniqueId}"/></td>
			<td ><a href="#" id="anchor0" onclick="return modifyOtherRelation(${subotherList.otherUniqueId});">${subotherList.otherName}</a></td>
			<td >${subotherList.relationships}</td>
			<td>${subotherList.knowingSince}</td>
			<td>${subotherList.primaryOtherMbNo}</td>
			<td>${subotherList.alternateOtherPhNo}</td>
		</tr>	
			</logic:iterate>
			</logic:present>		
		</table>
</table>
</fieldset>
<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteOtherRelation();"><bean:message key="button.delete" /></button>
					
</logic:notPresent>
</html:form>
</fieldset>
</div></div></div>
<logic:present name="msg">
	<script type="text/javascript">
	if('<%=request.getAttribute("msg")%>'=='S')
	{
	   
		alert('<bean:message key="lbl.dataSavedSucc" />');
		
	}
	else
	{
		alert('<bean:message key="lbl.dataNtSavedSucc" />');
	}
	</script>
</logic:present>
<logic:present name="deleteMsg">
	<script type="text/javascript">
	if('<%=request.getAttribute("deleteMsg")%>'=='S')
	{
	   
		alert('<bean:message key="lbl.dataDeleted" />');
		
	}
	else
	{
		alert('<bean:message key="lbl.dataNtDeleted" />');
	}
	</script>
</logic:present>
<logic:present name="dublicateName">
	<script type="text/javascript">
	if('<%=request.getAttribute("dublicateName")%>'=='dublicateName')
	{
	   
		alert('<bean:message key="lbl.dublicateFirstReference" />');
		
	}
	</script>
</logic:present>

</body>

</html>