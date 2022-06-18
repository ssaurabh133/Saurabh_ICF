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
		
</head>
	<body onload="enableAnchor();">
	
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/instrumentCapture" method="post">
	  
    
<fieldset>	  
	<legend><bean:message key="instrumentCapturingDetails"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
			 
	<td><bean:message key="lan"/></td>
	  <td><html:text property="lan" styleClass="text" styleId="lan" value="" maxlength="100" size="10" />		
	<button type="button" name="lovlbxCaseType" class="topformbutton2"  onclick="return openLOV();" /><img src="../../images/lov.gif" /></td>
	
	<td><bean:message key="customerName"/>
	<html:text property="customerName" styleClass="text" styleId="customerName" value="" maxlength="100" size="10" /></td>
		</tr>
	  <tr>
	  <td><bean:message key="instrumentType"/></td>
         <td class="" id=""> <html:select property="instrumentType" styleId="instrumentType" >
          	<html:option value="">--Select--</html:option>
          	<html:option value="">PDC</html:option>
          	<html:option value="">ECS</html:option>
          	<html:option value="">DIRECT DEBIT</html:option>
          	<html:option value="">SECURITY PDC</html:option>
          	<html:option value="">PRE-EMI</html:option>
          	
          </html:select>
          </td>
	  	  </tr>
		</table>
		
	</table>
	 
	</fieldset>	
	
	<fieldset>	
		 <legend><bean:message key="pdcDetails"/></legend>  

  
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   
			 <td><bean:message key="totalInstallments"/></td>
	<td><html:text property="totalInstallments" styleClass="text" styleId="totalInstallments" value="" maxlength="100" size="10" /></td>
			   
	
	<td ><bean:message key="installmentAmount"/></td>
	<td><html:text property="installmentAmount" styleClass="text" styleId="installmentAmount" value="" maxlength="100" size="10" /></td>
	
		</tr>
	  <tr>
	    
	    
	<td><bean:message key="fromInstallment"/></td>
	<td><html:text property="fromInstallment" styleClass="text" styleId="fromInstallment" value="" maxlength="100" size="10" /></td>
	    
	     
		<td><bean:message key="toInstallment"/></td>
	<td><html:text property="toInstallment" styleClass="text" styleId="toInstallment" value="" maxlength="100" size="10" /></td>
		
	  </tr>
	  <tr>

	   <td> <bean:message key="startingChequeNo"/></td>
	<td><html:text property="startingChequeNo" styleClass="text" styleId="startingChequeNo" value="" maxlength="100" size="10" /></td>
	  
	  <td><bean:message key="endingChequeNo"/></td>
	<td><html:text property="endingChequeNo" styleClass="text" styleId="endingChequeNo" value="" maxlength="100" size="10" /></td>
		
	  </tr>
	  <tr>
	  
	  <td><bean:message key="bank"/></td>
         <td class="" id=""> <html:select property="bank" styleId="bank" >
          	<html:option value="">--Select--</html:option>
          	<html:option value="">SBI</html:option>
          	<html:option value="">Axis Bank</html:option>
          	<html:option value="">HDFC Bank</html:option>
          	
          	
          </html:select>
          </td>
	    <td><bean:message key="branch"/></td>
         <td class="" id=""> <html:select property="branch" styleId="branch" >
          	<html:option value="">--Select--</html:option>
          	<html:option value="">Noida</html:option>
          	<html:option value="">Gurgaon</html:option>
          	<html:option value="">Delhi</html:option>
          	
          	
          </html:select>
          </td>
	   
	    </tr>
	  <tr>
	  
	  <td><bean:message key="micr"/></td>
         <td class="" id=""> <html:select property="micr" styleId="micr" >
          	<html:option value="">--Select--</html:option>
          	</html:select>
          </td>
	    <td><bean:message key="location"/></td>
	<td><html:text property="location" styleClass="text" styleId="location" value="" maxlength="100" size="10" /></td>
	    
	    </tr>
		 <tr>

	    <td><bean:message key="binNo"/></td>
	<td><html:text property="binNo" styleClass="text" styleId="binNo" value="" maxlength="100" size="10" /></td>
	    
	    <td>&nbsp;</td>
	    <td nowrap="nowrap" >&nbsp;</td>
		 </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td nowrap="nowrap" >&nbsp;</td>
	    <td>&nbsp;</td>
	    <td nowrap="nowrap" ><label>
	      <button type="button" name="Submit" class="topformbutton2" title="Alt+G" accesskey="G" ><bean:message key="button.generatePDC" /></button>
	    </label></td>
	    </tr>
		</table>
		
	     
	
	</table>
	 

  
	</fieldset>
	 
	 
	
	<fieldset>	  
	<legend><bean:message key="pdcSummary"/></legend>         
	   
	
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
         <td width="167" class="white2" style="width:150px;"><strong><bean:message key="instrumentDate"/></strong></td>
          <td width="167" class="white2" style="width:150px;"><b><bean:message key="instrumentNo"/> </b></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="installmentAmount"/> </strong></td>
          <td width="220" class="white2" style="width:150px;"><b><bean:message key="pdcAmount"/> </b></td>
          
          </tr>
        <tr>
          
          <td class="white" id=""><input class="text" maxlength="100" name="input2" value="" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input4" value="" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input42" value="" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input44" value="" /></td>
          </tr>
        <tr>
         
          <td class="white" id="td"><input class="text" maxlength="100" name="input3" value="" /></td>
          <td class="white" id="td"><input class="text" maxlength="100" name="input6" value="" /></td>
          <td class="white" id="td"><input class="text" maxlength="100" name="input43" value="" /></td>
          <td class="white" id="td"><input class="text" maxlength="100" name="input45" value="" /></td>
          </tr>
        </table>   
</table>
 </fieldset> 
<table width="100%" border="0" cellpadding="2" cellspacing="1">
    <tr>
      <td colspan="4"><label>
        <button type="button" name="Submit2"  class="topformbutton2"  title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
         <button type="button" name="Submit22"  class="topformbutton2" title="Alt+F" accesskey="F"> <bean:message key="button.savefrwd" /></button>
      </label></td>
    </tr>
  </table>
 
		
  </html:form>
</div>

</div>
</body>
</html:html>