<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
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
	</head>
	<body onload="enableAnchor();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/maker" method="post">
	
 <fieldset>
   
      <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
         <td width="220" class="white2" style="width:150px;">Deal  No</td>
          <td width="220" class="white2" style="width:150px;">12345678</td>
         <td width="220" class="white2" style="width:150px;">Date</td>
           <td width="220" class="white2" style="width:150px;">01/03/2011</td>
          <td  class="white2" style="width:150px;">Customer Name</td>
            <td colspan="3" class="white2" style="width:150px;">ABC Corporation</td>
           </tr>
          <tr>
          <td width="220" class="white2" style="width:150px;">Product Type</td>
          <td width="220" class="white2" style="width:150px;">Multiple</td>
          <td width="220" class="white2" style="width:150px;">Product</td>
          <td width="220" class="white2" style="width:150px;">Multiple</td>
          <td width="220" class="white2" style="width:100px;">Scheme</td>
            <td width="220" class="white2" style="width:150px;">Multiple</td>
          <td width="220" class="white2" style="width:150px;">Current Stage</td>
            <td width="220" class="white2" style="width:150px;">Deal Capturing</td>
          </tr>
        </table>    
  


</fieldset>
          
			
	 
	  
	   <fieldset>	  
	<legend><bean:message key="lbl.receiptEntry"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><table width="100%" border="0" cellspacing="1" cellpadding="4">
          <tr>
            <td><bean:message key="lbl.customerName"/></td>
            <td nowrap="nowrap" ><html:text property="customerName"  disabled="true" styleClass="text" 
			styleId="customerName" style="" value="Dsa" maxlength="100"/></td>
            <td><bean:message key="lbl.date"/></td>
            <td width="35%" nowrap="nowrap" ><html:text property="date"  disabled="true" styleClass="text" styleId="date" style="" value="2009/03/01 " maxlength="100"/></td>
          </tr>
          <tr>
            <td width="20%"><bean:message key="lbl.amountMaker"/></td>
            <td ><div style="float:left;">
              <html:text  styleClass="text" property="amountMaker" styleId="amountMaker" value="30289" style="" maxlength="100"/>
            </div></td>
            <td width="35%"><bean:message key="lbl.paymentMode"/></td>
            <td><span style="float:left;">
              <html:select property="paymentMode" styleId="paymentMode" styleClass="text">
              	<html:option value="cash">Cash</html:option>
				<html:option value="check">Cheque</html:option>	
				<html:option value="dd">DD</html:option>
              </html:select>
              </span></td>
          </tr>
          <tr>
            <td width="20%"><bean:message key="lbl.refrenceNumber"/></td>
            <td width="35%"><html:text property="refrenceNumber"  disabled="true" styleClass="text" styleId="refrenceNumber" style="" value="45" maxlength="100"/></td>
            <td><bean:message key="lbl.refrenceDate"/></td>
             <td nowrap="nowrap" ><html:text property="refrenceDate"  disabled="true" styleClass="text" styleId="refrenceDate" style="" value="2009-01-12" maxlength="100"/></td>
          </tr>
          
          <tr>
          
          <td nowrap="nowrap"><button type="button"  class="topformbutton2"  title="Alt+V" accesskey="V" name="save" id="save" ><bean:message key="button.save" /></button>
              <button type="button"  title="Alt+F" accesskey="F" class="topformbutton2" name="forward" id="forward" ><bean:message key="button.savefrwd" /></button></td>
		  </tr>
		  </table>
        

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd"><table width="100%" border="0" cellspacing="1" cellpadding="4">
      <tr>
        <td class="white2" style="width:100px;"><b><bean:message key="lbl.customerName"/></b></td>
		<td class="white2" style="width:220px;"><b><bean:message key="lbl.date"/></b></td>
		<td class="white2" style="width:220px;"><b><bean:message key="lbl.amountMaker"/></b></td>
		<td class="white2" style="width:220px;"><b><bean:message key="lbl.paymentMode"/></b></td>
        <td class="white2" style="width:220px;"><b><bean:message key="lbl.refrenceNumber"/></b></td>
        <td class="white2" style="width:220px;"><b><bean:message key="lbl.refrenceDate"/></b></td>        
      </tr>

      <tr>
        <td class="white" id="">Sohan</td>
        <td class="white" id="">2011/05/15</td>
        <td class="white" id="">1000000</td>
        <td class="white" id="">Cheque</td>
        <td class="white" id="">1223</td>
        <td class="white" id="">2011/05/15</td>        
      </tr>
    </table></td>
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
</html>