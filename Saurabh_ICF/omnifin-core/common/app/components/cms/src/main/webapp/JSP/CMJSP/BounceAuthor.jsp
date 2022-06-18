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
	<html:form action="/bounceAuthor" method="post">
	<FIELDSET>
<LEGEND> <bean:message key="bounce.detail" /></LEGEND>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
		  <td width="162" class="white2" style="width:150px;"><b>Select 
		    <html:checkbox property="selectValue" value="checkbox" />
		  </b></td>
         <td width="254" class="white2" style="width:150px;"><b><strong><bean:message key="bounce.loan" /></strong></b></td>
          <td width="208" class="white2" style="width:150px;"><strong><b><bean:message key="bounce.no" /></b></strong></td>
		   <td width="208" class="white2" style="width:150px;"><strong><b><bean:message key="bounce.reason" /></b></strong></td>
          <td width="208" class="white2" style="width:150px;"><strong><bean:message key="bounce.batch" /></strong></td>
          <td width="209" class="white2" style="width:150px;"><strong><bean:message key="bounce.date" /></strong></td>
		   <td width="209" class="white2" style="width:150px;"><strong><bean:message key="bounce.remark" /> </strong></td>
            </tr>
        <tr>
		  <td class="white" id=""><label>
		    <input type="checkbox" name="checkbox" value="checkbox" />
		  </label></td>
		  <td class="white" id=""><input class="text" maxlength="100" name="input52" value="zdfvdzvzxv" disabled="disabled"/></td>
		  <td class="white" id=""><input class="text" maxlength="100" name="input222" value="dfxv565" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input3" value="abc" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input" value="zdfvdzvzxv" disabled="disabled"/></td>
		  <td class="white" id=""><input class="text" maxlength="100" name="input62" value="22/10/2011" disabled="disabled"/></td>
		  <td class="white" id=""><input class="text" maxlength="100" name="input" value="zdfvdzvzxv" disabled="disabled"/></td>
            </tr>
        <tr>
          <td class="white" id=""><input type="checkbox" name="checkbox2" value="checkbox" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input53" value="zdfvdzvzxv" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input224" value="dfxv565" disabled="disabled"/></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input32" value="abc" disabled="disabled" /></td>
          <td class="white" id=""><input class="text" maxlength="100" name="input5" value="zdfvdzvzxv" disabled="disabled"/></td>
		  <td class="white" id=""><input class="text" maxlength="100" name="input622" value="22/10/2011" disabled="disabled"/></td>
		  <td class="white" id=""><input class="text" maxlength="100" name="input" value="zdfvdzvzxv" disabled="disabled"/></td>
          </tr>
        </table>   
</table>

<table width="100%" border="0" cellpadding="2" cellspacing="1">
   
  </table>
  </FIELDSET> 
 
  <fieldset>
  <legend><bean:message key="bounce.author"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		<tr>
					
		   <td width="20%"><bean:message key="bounce.comments"/></td>
			 <td width="35%">
			   <html:textarea property="comments" styleClass="text" ></html:textarea>
			</td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="bounce.decision"/></td>
		 <td  id=""><span style="float: left;">
		   <html:select property="decision">
		     <option value="approved">Approved</option>
             <option value="rejected">Rejected</option>
             
		     </html:select>
		 </span></td>
		</tr> 		  
		<tr>
	      <td align="left" colspan="3" class="form2">&nbsp;&nbsp;
	     
	      <button type="button" name="startProcess" id="startProcess"  title="Alt+A" accesskey="A"  class="topformbutton2" > 
<bean:message key="button.approve" /></button>
		 <!-- <input name="button" type="button" class="topformbutton2" value="Approved"/>&nbsp;&nbsp;&nbsp;&nbsp;</td> --> 
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