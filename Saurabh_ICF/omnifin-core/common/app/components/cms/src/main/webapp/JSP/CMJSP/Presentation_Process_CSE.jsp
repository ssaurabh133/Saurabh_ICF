<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

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
	
	<html:form action="/presentaionProcess" method="post">
	  
  
	 
	  
	   <fieldset>	  
	<legend><bean:message key="presentation.process" /></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   <td width="24%"><bean:message key="presentation.date" /> </td>
			 <td width="31%"><html:text property="presentaionDate" disabled="true" styleId="presentaionDate" styleClass="text" style="" value="19/11/2009" maxlength="100"/>
			   &nbsp;</td>
	<td width="17%">&nbsp;</td>
	<td width="28%">&nbsp;</td>
		</tr>
	  <tr>
	    <td><label>
	      <button type="button" name="Submit"  class="topformbutton2"  title="Alt+G" accesskey="G" ><bean:message key="button.generatebatch" /></button>
	      <button type="button" name="Submit2" title="Alt+P" accesskey="P" class="topformbutton4" ><bean:message key="button.generatepayslip" /></button>
	    </label></td>
	    <td nowrap="nowrap" ><label></label></td>
	    <td>&nbsp;</td>
	    </tr>
		</table>
		
	      </td>
	</tr>
	</td>
	
	</table>
	 
	</fieldset>	
	
	
  <tr><td>&nbsp;</td>
	  </tr>
    </html:form>
</div>



</div>
</body>
</html:html>