<%--
      Author Name-  Ritu Jindal
      Date of creation -55/09/2011
      Purpose-   Field Varification Info       
      
 --%>

<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
 <%@include file="/JSP/commonIncludeContent.jsp" %> 

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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
	

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
	<style type="text/css">
		select {
	       min-width:80px;
	      }
    </style>
	</head>
	<body  >
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
	          <td >Verification Initiation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	<div id="centercolumn">
	
	<div id="revisedcontainer">

	<html:form action="/fieldVarificationInitiationAction" method="post" styleId="fieldVarificationIniationForm" >
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<%-- 
	<html:hidden property="initiatedCount" styleId="initiatedCount" value="${sessionScope.initiatedCount}"/>
	---%>
		<fieldset>	
		 <legend><bean:message key="lbl.fieldinitiation"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
 
        
        <td ><b><bean:message key="lbl.varificationType"/></b></td>
        <td ><b><bean:message key="lbl.varificationSubType"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityId"/></b></td>
        <td ><b><bean:message key="lbl.veri.entityType"/></b></td>
        <td ><b><bean:message key="lbl.veri.sub.entityType"/></b></td>
         <td ><b><bean:message key="lbl.ver.addressType"/></b></td>
         
        <td ><b><bean:message key="lbl.ver.desc"/></b></td>
        <td ><b><bean:message key="lbl.verifDecision"/></b></td>
        <td><b><bean:message key="lbl.appraiserIN"/></b></td>
     
       	 <td ><b><bean:message key="lbl.internalAppraiser"/></b></td>
   
         <td><b><bean:message key="lbl.externalAppraiser"/></b></td>
         <td><b><bean:message key="lbl.Remarks"/></b></td>
         <td><b><bean:message key="lbl.appraiserDate"/></b></td>
      
        
   
	</tr>
	
<logic:present name="verificationList">
	<logic:iterate name="verificationList" id="sublist" indexId="counter">
	<tr class="white1">

	<html:hidden property="entityId" styleId="entityId${counter+1}" value="${sublist.entId}"/>
	<td width="10%">
		<html:hidden property="verType" styleId="verType" value="${sublist.verificationType}"/>
		${sublist.verificationType}
       </td>
      
		<td width="10%">
		<html:hidden property="verSubType" styleId="verSubType" value="${sublist.verificationSubType}"/>
		${sublist.verificationSubType}
            
       </td>
      <td width="5%">
    		${sublist.entId}
       </td>

     <td width="7%">
     <html:hidden property="entType" styleId="entType" value="${sublist.entityType}"/>
		${sublist.entityType}
       </td>
        <td width="7%">
         <html:hidden property="entSubType" styleId="entSubType" value="${sublist.entitySubType}"/>
		${sublist.entitySubTypeDesc}
       </td>
       <td width="7%">
         <html:hidden property="lbxAddressType" styleId="lbxAddressType" value="${sublist.addressType}"/>
		${sublist.addressTypeDesc}
       </td>
      <td width="10%">
        <html:hidden property="entityDescription" styleId="entityDescription" value="${sublist.entityDesc}"/>
		${sublist.entityDesc}
       </td>
		
		<td width="7%">
		   <html:hidden property="action" styleId="action${counter+1}" value="I"/>
		    <html:select property="verfDecisionArray" styleId="verfDecisionArray${counter+1}"  styleClass="text7" value="${sublist.verfDecision}" onchange="disableDecision('${counter+1}');" >
                <html:option value="">--Select--</html:option>
                <html:option value="P">Positive</html:option>
                <html:option value="N">Negative</html:option>
              
            </html:select>
	
		
		</td>
	 	
		<td width="7%">
		<logic:equal name="sublist" property="appraiserType" value="BOTH">
				
			<html:select property="appraiser" styleId="appraiserType${counter+1}"  styleClass="text7" value="" onchange="disableAppraisal('${counter+1}');">
                <html:option value="INTERNAL">INTERNAL</html:option>
                <html:option value="EXTERNAL">EXTERNAL</html:option>
           </html:select>
		</logic:equal>
		<logic:notEqual name="sublist" property="appraiserType" value="BOTH">
		    
			<input type="hidden" name="appraiser" id="appraiserType${counter+1}"  value="${sublist.appraiserType}"/>
		    ${sublist.appraiserType}
		</logic:notEqual>
		

	</td>
	

        <td width="10%"><html:text property="internalAppraiserArr" styleId="internalAppraiser${counter+1}" value="${sublist.internalAppraiser}" readonly="true" styleClass="text6"  />
			<html:hidden  property="lbxUserId" styleId="lbxUserId"  value="${sublist.lbxUserId}" />
			<html:button property="internalButton" styleId="internalButton${counter+1}" disabled="true" onclick="openLOVCommon(308,'fieldVarificationIniationForm','internalAppraiser${counter+1}','','', '','','','internalAppUserId${counter+1}');" value=" " styleClass="lovbutton"> </html:button>
		 	<html:hidden property="internalAppUserId" styleId="internalAppUserId${counter+1}" value="${sublist.lbxUseId}" />
		</td>	
		 <td width="10%"><html:text property="externalAppraiserArr" styleId="externalAppraiser${counter+1}" value="${sublist.externalAppraiser}" readonly="true" styleClass="text6"  />
			<html:hidden  property="lbxextApprHID" styleId="lbxextApprHID"  value="${sublist.lbxextApprHID}" />
			<html:button property="externalButton" styleId="externalButton${counter+1}"  disabled="true" onclick="openLOVCommon(312,'fieldVarificationIniationForm','externalAppraiser${counter+1}','','', '','','','externalAppUserId${counter+1}');" value=" " styleClass="lovbutton"> </html:button>
			<html:hidden property="externalAppUserId" styleId="externalAppUserId${counter+1}" value="${sublist.lbxetApprHID}" />
		</td>
		
		<td width="10%">
		  <html:text property="appraiserRemarksArray" maxlength="50" styleId="appraiserRemarks${counter+1}" value="${sublist.appraiserRemarks}"  styleClass="text6"  />
		</td>
		<td width="10%">
		  <script type="text/javascript">
		  	$(function() {
				$("#appraiserDate${counter+1}").datepicker({
						changeMonth: true,
					changeYear: true,
			        yearRange: '1900:+10',
			        showOn: 'both',
			        buttonImage: document.getElementById("CalImage").value,
			        buttonImageOnly: true,
			        dateFormat: document.getElementById("formatD").value,
					defaultDate: document.getElementById("businessdate").value
			        
				});
			});
		  </script>
		  <html:text property="appraiserDateArray" styleId="appraiserDate${counter+1}" value="${sublist.appraiserDate}" maxlength="10" styleClass="text6"  onchange="return checkDate('appraiserDate${counter+1}');"/>
		</td>
		

	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>

<tr>
		  	<td colspan="2">
		  
		  		<button type="button" name="save" id="saveButton" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveCompletionModule();" ><bean:message key="button.save" /></button> 

		    </td> 
		 </tr>
</table>

	</fieldset>

<logic:present name="sms">
<script type="text/javascript">
	if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert("<bean:message key="lbl.dataInitiated" />");
		//parent.location="<%-- request.getContextPath() --%>//fieldVarificationBehindAction.do";
		
	}else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
	alert('<bean:message key="msg.ForwardSuccessfully"/>');
	
	
	}else if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
	alert("You can't Forward without saving all Verification Type");
		<logic:present name="checkStageM">
		     alert('${checkStageM}');
		</logic:present>
	
	}
	
	else
	{
		alert("data not saved");
		
	}
 </script>
</logic:present>


<logic:present name="dublicate">
<script type="text/javascript">
	
	alert("<bean:message key="lbl.dublicateInitiated" />");
			
	
</script>
</logic:present>
<logic:present name="alreadyInitiated">
<script type="text/javascript">
	
	alert("<bean:message key="lbl.alreadyInitiated" />");
			
	
</script>
</logic:present>

</html:form>

	</div>


</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

</body>
</html:html>