<%--
      Author Name-  Kaustuv Ranjan    
      Date of creation -08/04/2011
      Purpose-   Providing User Interface To A Customer/Applicant       
      Documentation-      
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	    <meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval()%>;url= <%=request.getContextPath()%>/logoff.do" />
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/applicant.js"></script>
	
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
  
<body onclick="parent_disable();" oncontextmenu="return false" onload="enableAnchor();checkChanges('applicantForm');document.getElementById('applicantForm').applicantType.focus();" onunload="closeAllWindowCallUnloadBody();closeAllWindowCallUnloadBodyAn();" >   

<html:errors />



<div id=centercolumn>
<div id=revisedcontainer>
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
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>

  </fieldset>

<logic:notPresent name="viewDeal">
<html:form action="/creditProcessing" styleId="applicantForm" method="post">
<html:hidden property="optionIndv" styleId="optionIndv" value="${optionIndv}"/>

<fieldset>
<legend><bean:message key="lbl.deals"/></legend>
<html:hidden property="cat" styleId="cat" />
 
<table cellspacing=0 cellpadding=0 width="100%" border=0>
  
  <tr>
    <td><input type="hidden" id="path" value="<%=request.getContextPath()%>"/>
      <table cellspacing=1 cellpadding=1 width="75%" border=0>
      
       <tr>
       <td ><bean:message key="lbl.applicantType"/><font color="red">*</font></td>
        <td ><html:select property="applicantType" styleId="applicantType"  onchange="return forward();" tabindex='1'>
        
          	<html:option value="0">--Select--</html:option>
          	<logic:present name="applist" >
          	
          	<html:optionsCollection name="applist" label="applicant_desc" value="applicant_code" />

          	</logic:present>
          </html:select>
          </td>
          <td>
        <bean:message key="lbl.existingCustomer"/><font color="red">*</font></td>
       
        <td > <input type="radio" name="existingCustomer" id="existingCustomer1"  value="Y" checked="checked" onclick="return hideCreate();" tabindex='2'/>Yes</td>
        <td><input type="radio" name="existingCustomer" id="existingCustomer2" value="N" onclick="return hideLink();" tabindex='3'/>No </td> 
       </tr>
  <tr>
         <td>
        <bean:message key="lbl.applicantCategory"/><font color="red">*</font></td>
        
       <td><html:radio property="applicantCategory" styleId="applicantCategory" value="I" disabled="true" tabindex='4'>Individual</html:radio>
       
       <html:radio property="applicantCategory" styleId="applicantCategory1" value="C" disabled="true" tabindex='5'>Corporate</html:radio>
       </td> 
        
        </tr>
        
            <tr>
          <td colspan="4">
          
              <button type="button" name="createCust" class="topformbutton3" title="Alt+C" accesskey="C" id="createCust" onclick="createCustomer();callOnInCreateCustomer('applicantType','applicantCategory1','applicantCategory2');"><bean:message key="button.createcust" /></button>
              <button type="button" name="linkCust" class="topformbutton3" title="Alt+L" accesskey="L" id="linkCust" onclick="linkCustomer();callOnLinkOrButtonWindow('applicantType');" ><bean:message key="button.linkcust" /></button>
              </td>
           </tr>
		  </table>
     </td>
     </tr>
   
   </table>
</fieldset> 


<fieldset>	
	
		 <legend><bean:message key="lbl.deals"/></legend>  

  
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
     
     <td width="3%"><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/></td> 
	    
        <td width="10%"><b><bean:message key="lbl.customerID"/></b></td>
		<td ><strong><bean:message key="lbl.customerName"/></strong></td>
        <td ><b><bean:message key="lbl.applicantType"/></b></td>
        <td><b><bean:message key="lbl.applicantCategory"/></b></td>
        <td ><b><bean:message key="lbl.existingCustomer"/></b></td>
          <td><b><bean:message key="lbl.guaranteeAmount"/></b></td>
          <td><b><bean:message key="lbl.cibilScore"/></b></td>
          <td><b><bean:message key="lbl.photoUpload"/></b></td>
<!--         <td class="gridheader"><b><bean:message key="lbl.customerStatus"/></b></td>-->
	</tr>
	           
	                        <logic:present name="roleList">
	                        
							<logic:iterate id="subroleList" name="roleList">
	
	
					<tr class="white1">
							
			 				<td   ><input type="checkbox" name="chk" id="chk" value="${subroleList.roleId }"/></td>
							<td   >
							<a href="#" id="anchor0" onclick=" linkDeal('${subroleList.customerId }', '${subroleList.applicantCategory }','${subroleList.applicantType }','${subroleList.existingCustomer }');">
							${subroleList.customerId }</a></td>
	      			        <td>${subroleList.customerName }
	      			       	 <html:hidden property="cust_name" styleId="cust_name" value="${subroleList.customerName }"/>
	      			        </td>
	      			        
							<td  > <html:hidden property="applType" styleId="${subroleList.customerId }" value="${subroleList.applicantType }"/>${subroleList.applicantType }
							</td>
							<td  >${subroleList.applicantCategory }
							<html:hidden property="cust_type" styleId="cust_type" value="${subroleList.applicantCategory }"/>
							<html:hidden property="updateFlag" styleId="updateFlag" value="${subroleList.flagForUpdate }"/>
							</td>
							<td   >${subroleList.existingCustomer }</td>
							<td ><logic:equal name="subroleList" property="applicantType"
																	value="GUARANTOR">
																	<a href="#" id="anchor0" onclick="linkGuaranteeAmount('${subroleList.roleId }','${subroleList.guaranteeAmount }');">
							
							${subroleList.guaranteeAmount }</a>
																	
																</logic:equal>
							</td>
							<td>${subroleList.cibilScore }</td>
							<td ><logic:equal name="subroleList" property="applicantCategory"
																	value="INDIVIDUAL">
																	<a href="#" id="anchor0" onclick="linkPhotoUpload('${subroleList.customerId }','');">Photo Upload</a>
							
																</logic:equal>
							</td>
							

			       </tr>				
			 
	</logic:iterate>
	</logic:present> 
	
		</table>
		</td></tr>
</table>

 <button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" value="Delet" onclick="deleteApplicant('N');"><bean:message key="button.delete" /></button>

        
</fieldset>

</html:form>
</logic:notPresent>

<logic:present name="viewDeal">
	<html:form action="/creditProcessing" styleId="applicantForm" method="post">

<fieldset>
<legend><bean:message key="lbl.deals"/></legend>
<html:hidden property="cat" styleId="cat" />
 
<table cellspacing=0 cellpadding=0 width="100%" border=0>
  
  <tr>
    <td><input type="hidden" id="path" value="<%=request.getContextPath()%>"/>
      <table cellspacing=1 cellpadding=1 width="100%" border=0>
      
       <tr>
       <td width="16%"><bean:message key="lbl.applicantType"/><font color="red">*</font></td>
        <td width="26%"><html:select property="applicantType" styleId="applicantType"  onchange="return forward();" disabled="true">
        
          	<html:option value="0">--Select--</html:option>
          	<logic:present name="applist" >
          	
          	<html:optionsCollection name="applist" label="applicant_desc" value="applicant_code" />

          	</logic:present>
          </html:select>
          </td>
          <td width="13%">
        <bean:message key="lbl.existingCustomer"/><font color="red">*</font></td>
       
        <td width="45%"> <input type="radio" name="existingCustomer" id="existingCustomer"  value="Y" checked="checked" onclick="return hideCreate();" disabled="disabled"/>Yes
       
        <input type="radio" name="existingCustomer" id="existingCustomer" value="N" onclick="return hideLink();" disabled="disabled"/>No
      </td> 
       
       </tr>
  <tr>
         <td>
        <bean:message key="lbl.applicantCategory"/><font color="red">*</font></td>
        
       <td><html:radio property="applicantCategory" styleId="applicantCategory" value="I" disabled="true">Individual</html:radio>
       
       <html:radio property="applicantCategory" styleId="applicantCategory1" value="C" disabled="true">Corporate</html:radio>
       </td> 
        
        </tr>
        
            <tr>
          <td colspan="4">
          
             
              </td>
           </tr>
		  </table>
     </td>
     </tr>
   
   </table>
</fieldset> 


<fieldset>	
	
		 <legend><bean:message key="lbl.deals"/></legend>  

  
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
   
     
     <td width="3%" ><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" disabled="disabled"/></td> 
	    
        <td ><b><bean:message key="lbl.customerID"/></b></td>
		<td ><strong><bean:message key="lbl.customerName"/></strong></td>
        <td ><b><bean:message key="lbl.applicantType"/></b></td>
        <td ><b><bean:message key="lbl.applicantCategory"/></b></td>
        <td><b><bean:message key="lbl.existingCustomer"/></b></td>
       <td><b><bean:message key="lbl.guaranteeAmount"/></b></td>
        <td><b><bean:message key="lbl.cibilScore"/></b></td>
         <td><b><bean:message key="lbl.photoUpload"/></b></td>
	</tr>
	           
	                        <logic:present name="roleList">
	                        
							<logic:iterate id="subroleList" name="roleList">
	
	
					<tr class="white1">
							
			 				<td   ><input disabled="disabled" type="checkbox" name="chk" id="chk" value="${subroleList.roleId }"/></td>
							<td  >
							<a href="#" id="anchor0" onclick="return linkDealinCMnew('${subroleList.customerId }', '${subroleList.applicantCategory }','${subroleList.applicantType }','${subroleList.existingCustomer }');">
							
							${subroleList.customerId }</a></td>
							
	      			        <td  >${subroleList.customerName }
	      			        <html:hidden property="cust_name" styleId="cust_name" value="${subroleList.customerName }"/>
	      			       </td>
	      			        
							<td  ><html:hidden property="applType" styleId="applType" value="${subroleList.applicantType }"/>${subroleList.applicantType }
							</td>
							<td >${subroleList.applicantCategory }
							<html:hidden property="cust_type" styleId="cust_type" value="${subroleList.applicantCategory }"/>
							<html:hidden property="updateFlag" styleId="updateFlag" value="${subroleList.flagForUpdate }"/>
							</td>
							<td >${subroleList.existingCustomer }</td>	
							
							<td ><logic:equal name="subroleList" property="applicantType"
																	value="GUARANTOR">

																	 ${subroleList.guaranteeAmount }
																</logic:equal>
							</td>
							<td>${subroleList.cibilScore }</td>	
						<td ><logic:equal name="subroleList" property="applicantCategory"
																	value="INDIVIDUAL">
						<a href="#" id="anchor0" onclick="return linkPhotoUpload('${subroleList.customerId }','ViewMode');">Photo Upload</a>
							
																</logic:equal>
							</td>
								
							

			       </tr>				
			 
	</logic:iterate>
	</logic:present> 
	
		</table>
		</td></tr>
</table>
 
 

        
</fieldset>

</html:form>
</logic:present>
</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<script>
	setFramevalues("applicantForm");
</script>
<logic:present name="customerNameAddressVerif">
<logic:notEmpty name="customerNameAddressVerif">
	<script type="text/javascript">
	
		if(confirm("<%=request.getAttribute("customerNameAddressVerif")%>"+" <bean:message key="lbl.verifCapturingWarning" />"))
		{
			deleteApplicant('Y');
		}
			
	</script>
</logic:notEmpty>
</logic:present>	
<logic:present name="msg">
	<script type="text/javascript">
	
		
		if('<%=request.getAttribute("msg")%>'=='S')
		{
				alert("<bean:message key="lbl.dataDeleted" />");
	
		}
		else if('<%=request.getAttribute("msg")%>'=='IMD')
		{
				alert("<bean:message key="lbl.imdExist" />");
	
		}
		else
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
		}
			
	</script>
</logic:present>


  </body>
  
  
  </html:html>

  
 