<!-- 
Author Name :- Manisha Tomar
Date of Creation :18-07-2011
Purpose :-  screen for the Pool Creation
-->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.util.*" %>
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
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/poolCreation.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

</head>
<body onload="enableAnchor();document.getElementById('poolCreationForm').loanButton.focus();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
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
<html:form action="/poolCreationProcessAction"  styleId="poolCreationForm">
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath" />
<fieldset><legend><bean:message key="lbl.poolReport"></bean:message></legend>  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
			<tr>
			 	<td><bean:message key="lbl.ReportType"/><font color="red">*</font></td>
	      		<td><label>
	      			<html:select property="reportType" styleId="reportType" styleClass="text" value="" onchange="changeView()">
	           			<html:option value="R">Pool Report</html:option>
		        		<html:option value="D">Supporting Dump</html:option>		        		
		   			</html:select> 
	        		</label>
	      		</td>
		 		<td id ="poolTypeL" style="display: none;"><bean:message key="lbl.poolType"/><font color="red">*</font></td>
	      		<td id ="poolTypeV" style="display: none;"><label>
	      				<html:select property="poolType" styleId="poolType" styleClass="text" value="">
	      				    <html:option value="">--Select--</html:option>
	           				<html:option value="S">Securitized</html:option>
		        			<html:option value="R">Re-finance</html:option>
		   				</html:select> 
	        		</label>
	      		</td>
	      	</tr>
	        <tr>
	        	<td id ="poolIDL"><bean:message key="lbl.poolID"></bean:message><font color="red">* </font></td>
				<td id ="poolIDV">
		   			<html:text styleClass="text" property="poolID" styleId="poolID" maxlength="20"  value="" readonly="true" tabindex="-1" />
		   			<html:hidden  property="lbxPoolID" styleId="lbxPoolID"/>
           			<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(122,'poolCreationForm','poolID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
           			<input type="hidden" name="hcommon" id="hcommon"/> 
   		   		</td>	
	        	<td id ="instituteIDL" style="display: none;"><bean:message key="lbl.instituteID"/><font color="red">*</font></td>
	        	<td id ="instituteIDV" style="display: none;">
			 		<html:text styleClass="text" property="instituteID" styleId="instituteID" maxlength="100" value="" readonly="true" tabindex="-1"/>   
		     		<html:hidden property="lbxinstituteID" styleId="lbxinstituteID" />
		     		<input type="hidden" name="hcommon" id="hcommon" />
					<html:button property="loanNoButton" styleId="loanNoButton" onclick="openLOVCommon(178,'sourcingForm','instituteID','','', '','','','hcommon');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>  
           		</td>
	        </tr>
            <tr><td><button type="button" name="button"  title="Alt+G" accesskey="G" id="generatePool" class="topformbutton3" onclick="return generatePoolReport();" ><bean:message key="button.generatereport" /></button></td>
	        <td></td><td></td><td></td>
	        </tr>					
	   </table>
	</td>
</tr>
</table>
</fieldset>


</html:form>

		
</div>
</div>
</body>
</html:html>