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
	<body onload="enableAnchor();">
	
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
	
	<html:form action="/generateReport" method="post">
	  
  
	 
	  
	   <fieldset>	  
	<legend><bean:message key="generate.report.detail" /> </legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><table width="100%" border="0" cellspacing="1" cellpadding="4">
          <tr>
            <td width="20%"><bean:message key="generate.report.date" /></td>
            <td width="35%"><html:text property="generateDate" styleClass="text"  styleId="generateDate" value="12/03/2010" />
              &nbsp;</td>
            <td width="17%"><bean:message key="generate.report.instrument" /> </td>
            <td width="28%"><html:select property="instrument_type">
                <option value="PDC">PDC</option>
                <option value="ECS">ECS</option>
                <option value="DIRECT DEBIT">DIRECT DEBIT</option>
                <option value="PRE-EMI">PRE-EMI</option>
            </html:select></td>
          </tr>
          <tr>
            <td><label>
              <button type="button" title="Alt+G" accesskey="G" name="Submit" class="topformbutton3" ><bean:message key="button.generate" /></button>
            </label></td>
            <td nowrap="nowrap" >&nbsp;</td>
            <td>&nbsp;</td>
            <td nowrap="nowrap" >&nbsp;</td>
          </tr>
         
        </table>
	</table>
	 
	</fieldset>	
	
	
</html:form>
</div>



</div>
</body>
</html:html>