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
	<html>
	
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
	
	<html:form action="/instrumentAuthor" method="post">
	<fieldset>
<legend><bean:message key="instrumentDetails"/> </legend>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
		  <td width="167" class="white2" style="width:150px;"><b><bean:message key="instrument.select"/> 
		    <input type="checkbox" name="checkbox" id="checkbox"  value=""  />
		  </b></td>
         <td width="167" class="white2" style="width:150px;"><b><strong><bean:message key="instrumentDate"/>  </strong></b></td>
          <td width="220" class="white2" style="width:150px;"><strong><b><bean:message key="instrumentNo"/></b></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="installmentAmount"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="pdcAmount"/></strong></td>
            </tr>
        <tr>
		  <td class="white" id=""><label>
		    <input type="checkbox" name="checkbox" value="checkbox" />
		  </label></td>
		  <td class="white" id=""><input class="text" maxlength="100" name="input62" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input222" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input3" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input" value="" disabled="disabled"/></td>
            </tr>
        <tr>
          <td class="white" id=""><input type="checkbox" name="checkbox2" value="checkbox" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input622" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input224" value="" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input32" value="" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input5" value="" disabled="disabled"/></td>
          </tr>
        </table>   
</table>


  </fieldset> 
 
  <fieldset>
  <legend><bean:message key="instrumentAuthor"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
				<td><bean:message key="comments"/></td>	
		  
		    <td class="" id=""><html:textarea property="comments" styleClass="text" styleId="comments" value="" /></td>
			<td><bean:message key="position"/></td>
			<td class="" id="">
          <html:select property="position" styleId="position">
          	<html:option value="">--Select--</html:option>
          	<html:option value="">Approved</html:option>
          	<html:option value="">Rejected</html:option>
          	
          </html:select>
         
          </td>				
		
		</tr>  
		<tr>
	      <td align="left" colspan="3" class="form2">
		  <button type="button" class="topformbutton2"  title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
		  &nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr> 
		</table>
		
	</table>
	
	  </fieldset>

	
	




  </html:form>
</div>



</div>
</body>
</html>