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
		
	
	
	<script type="text/javascript">

		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
	</head>
	<body onload="enableAnchor();init_fields();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
	<html:form action="/holdinstrumentMaker" method="post">
	  
  
	 
	  
	   <fieldset>	  
	<legend><bean:message key="holdInstrumentDetails"/></legend>         
	   
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
          <td><bean:message key="instrumentNo"/>
	<html:text property="instrumentNo" styleClass="text" styleId="instrumentNo" value="" maxlength="100" size="10" /></td>
		  </tr>
	  <tr>
	  
	  <td><bean:message key="installmentDate"/></td>
	<td><html:text property="installmentDate" styleClass="text" styleId="installmentDate" value="" maxlength="100" size="10" /></td>
	   
	    
	    </tr>
		<tr>
		<td nowrap="nowrap" ><label>
	    <button type="button"  name="Submit2" class="topformbutton2"  title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
	    </label></td>
		</tr>
		</table>
		
	    
	
	</table>
	 
	</fieldset>	
	<FIELDSET>
<LEGEND><bean:message key="holdInstrumentSummary"/></LEGEND>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
		
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="installmentDate"/> </strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="instrumentType"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="instrumentNo"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="installmentAmount"/> </strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="holdReason"/></strong></td>
		  <td width="120" class="white2" style="width:150px;"><strong>
		    <label>
		    <input type="checkbox" name="checkbox3" value="checkbox" />
            </label>
Select
		    <label></label>
		  </strong></td>
            </tr>
        <tr>
        
		   <td class="white" id=""><input class="text" maxlength="100" name="input22" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input4" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input3" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input32" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input" value="" disabled="disabled"/></td>
		    <td class="white" id=""><input type="checkbox" name="checkbox" value="checkbox" /></td>
            </tr>
			<tr>
     
			   <td class="white" id=""><input class="text" maxlength="100" name="input22" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input4" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input3" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input32" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input" value="" disabled="disabled"/></td>
		           <td class="white" id=""><input type="checkbox" name="checkbox" value="checkbox" /></td>
            </tr>
			<tr>
           
			   <td class="white" id=""><input class="text" maxlength="100" name="input22" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input4" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input3" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input32" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input" value="" disabled="disabled"/></td>
		     <td class="white" id=""><input type="checkbox" name="checkbox22" value="checkbox" /></td>
            </tr>
			
        </table>   
  
</table>

  </FIELDSET> 
  <tr><td >
			 <button type="button" name="Submit2" class="topformbutton2" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
			 <button type="button" name="Submit22" class="topformbutton2" value="Save &amp; Forward" title="Alt+F" accesskey="F"><bean:message key="button.savefrwd" /></button>
</td>
			</tr>
    </html:form>
</div>



</div>
</body>
</html:html>