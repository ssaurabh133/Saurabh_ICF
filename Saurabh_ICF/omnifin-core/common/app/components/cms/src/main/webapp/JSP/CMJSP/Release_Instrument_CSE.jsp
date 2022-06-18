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
		<script type="text/javascript" src="<%=path%>/js/commonScript/calendar.js"></script>
		
	
	<script type="text/javascript">
  		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
	</head>
	<body onload="enableAnchor();init_fields();">
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
	
	<html:form action="/releaseinstrumentMaker" method="post">
	  
  
	 
	  
	   <fieldset>	  
	<legend><bean:message key="releaseInstrumentDetails"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><table width="100%" border="0" cellspacing="1" cellpadding="4">
          <tr>
           <td><bean:message key="instrumentNo"/></td>
	<td><html:text property="instrumentNo" styleClass="text" styleId="instrumentNo" value="" maxlength="100" size="10" /></td>
          
         <td><bean:message key="binNo"/></td>
	<td><html:text property="binNo" styleClass="text" styleId="binNo" value="" maxlength="100" size="10" /></td> </tr> 
          
        <tr>  <td><bean:message key="lan"/></td>
	  <td><html:text property="lan" styleClass="text" styleId="lan" value="" maxlength="100" size="10" />		
	<button type="button" name="lovlbxCaseType" class="topformbutton2"  onclick="return openLOV();" /><img src="../../images/lov.gif" /></td>
	
	<td><bean:message key="customerName"/></td>
	<td><html:text property="customerName" styleClass="text" styleId="customerName" value="" maxlength="100" size="10" /></td>
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
         
	   <td><bean:message key="releaseReason"/></td>
	<td><html:text property="releaseReason" styleClass="text" styleId="releaseReason" value="" maxlength="100" size="10" /></td>
           
          </tr>
         
        </table>
	
	</table>
	 
	</fieldset>	
	
	<FIELDSET>
<LEGEND><bean:message key="releaseInstrumentSummary"/></LEGEND>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
		 
          <td width="167" class="white2" style="width:150px;"><b><bean:message key="instrumentNo"/> </b></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="binNo"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="lan"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="customerName"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="instrumentType"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="releaseDate"/></strong></td>
            <td width="220" class="white2" style="width:150px;"><strong><bean:message key="releaseReason"/></strong></td>
			<td width="120" class="white2" style="width:150px;"><strong><b>
			  <input type="checkbox" name="checkbox" value="checkbox" />
            </b>Select</strong></td>
        </tr>
        <tr>
		 
		  <td class="white" id=""><input class="text" maxlength="100" name="input2" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input22" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input222" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input3" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input" value="" disabled="disabled" /></td>
           <td class="white" id=""><input class="text" maxlength="100" name="input6" value="" disabled="disabled"/></td>
            <td class="white" id=""><input class="text" maxlength="100" name="input7" value="" disabled="disabled"/></td>
			 <td class="white" id=""><label>
		    <input type="checkbox" name="checkbox" value="checkbox" />
		  </label></td>
        </tr>
        <tr>
          
          <td class="white" id=""><input class="text" maxlength="100" name="input23" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input223" value= disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input224" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input32" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input4" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input62" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input5" value="" disabled="disabled"/></td>
		  <td class="white" id=""><input type="checkbox" name="checkbox2" value="checkbox" /></td>
        </tr>
		
        </table>  
</table>

  </FIELDSET> 
  <tr><td>
			 <button type="button" name="Submit2" class="topformbutton2" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
			 <button type="button" title="Alt+F" accesskey="F" name="Submit22" class="topformbutton2" ><bean:message key="button.savefrwd" /></button>
</td>
			</tr>
    </html:form>
</div>



</div>
</body>
</html:html>