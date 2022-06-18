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

   function view_receopts()
   {
   url ="Viewreceipt.jsp";
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
	
	<html:form action="/receipt" method="POST">
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>                    
			
	 
	  
	   <fieldset>	  
	<legend><bean:message key="receipts"></bean:message></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%"><bean:message key="loan_account_number"></bean:message></td>
			 <td width="35%"><div style="float:left;">
	<html:text property ="loan_account_number" styleClass="text" styleId="loan_account_number" value="123456" maxlength="100"></html:text></div>		
	</td>
	<td width="20%"><bean:message key="customer_name"></bean:message> </td>
	<td width="35%">
	<html:text property ="customer_name" styleClass="text" styleId="customer_name" value="XYZ" maxlength="100"></html:text>
	</td>
		</tr>
	  <tr>
		<td>
		<bean:message key="receipt_date"></bean:message>   
	 </td>
			<td nowrap="true" > 
		<html:text property ="receipt_date" styleClass="text" styleId="receipt_date" value="10-03-2011" maxlength="100"></html:text>	
	</td>
		   <td>
		   
		   <bean:message key="business_partner_type"></bean:message>
	       </td>
			<td nowrap="true" ><span style="float:left;">
			  <html:select property="business_partnertype">
                <html:option value="select">--Select--</html:option>
              </html:select>
			</span></td>     
	 </tr>
	<tr>  
		 <td><bean:message key="business_partner_name"></bean:message> </td>
		 <td nowrap="true"><span style="float:left;">
		   <html:select property="business_partner_name">
             <html:option value="select1">--Select--</html:option>
           </html:select>
		 </span></td>
			
			<td><bean:message key="receipt_mode"></bean:message> </td>
		    <td nowrap="true" ><span style="float:left;">
		   <html:select property="receipt_mode">
             <html:option value="cash">--Cash--</html:option>
			 <html:option value="cheque">--Cheque--</html:option>
			 <html:option value="dd">--DD--</html:option>
			 <html:option value="neft">--NEFT--</html:option>
			 <html:option value="rtgs">--RTGS--</html:option>
           </html:select>
		 </span></td>
		</tr>
		<tr>
					
		   <td width="20%"><bean:message key ="instrument_number"></bean:message> </td>
			 <td width="35%"><div style="float:left;">
		    <html:text property ="instrument_number" styleClass="text" styleId="instrument_number" value="451" maxlength="100"></html:text></div>
	     </td>
	   <td><bean:message key ="instrument_date"></bean:message></td>
	<td>
	<html:text property ="instrument_date" styleClass="text" styleId="instrument_date" value="12-03-2011" maxlength="100"></html:text></td>
		</tr>
		<tr>
					
		   <td width="20%">
		   
	<bean:message key="bank_branch"></bean:message></td>
			 <td width="35%"><div style="float:left;">
	<html:text property ="bank_branch" styleClass="text" styleId="bank_branch" value="ABC" maxlength="100"></html:text>	
	</div></td>
	<td><bean:message key="micr"></bean:message></td>
	<td>
	<html:text property ="micr" styleClass="text" styleId="micr" value="1010" maxlength="100"></html:text>	
	</td>
		</tr>
		<tr>
					
		   <td width="20%">
	<bean:message key="ifs_code"></bean:message></td>
			 <td width="35%"><div style="float:left;">
	<html:text property ="ifs_code" styleClass="text" styleId="ifs_code" value="7845" maxlength="100"></html:text>			
	</div></td>
	         <td><bean:message key="remarks"></bean:message></td>
	         <td>
	           <html:textarea property="remarks" styleClass="text" styleId="remarks"></html:textarea>      
	       		
	       </td>
		</tr> 
	
		
		
		<tr>
	  
	      <td align="left" class="form2" colspan="3">
	      <button type="button" name="savebutton" class="topformbutton2" id="savebutton"  title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>&nbsp;
	      <button type="button" name="savebutton" class="topformbutton2" id="savebutton" title="Alt+F" accesskey="F" ><bean:message key="button.savefrwd" /></button></td>
	     
          <td align="right" class="form2" colspan="3">
           <button type="button" name="viewreceivable"  title="Alt+R" accesskey="R" class="topformbutton2" id="viewreceivable"  onclick="return view_receopts();"><bean:message key="button.viewRecievable" /></button></td>
		  
	
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