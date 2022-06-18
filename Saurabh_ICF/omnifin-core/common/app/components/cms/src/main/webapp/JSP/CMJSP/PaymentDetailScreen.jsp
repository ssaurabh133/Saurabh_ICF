<%--
      Author Name-  Prashant kumar    
      Date of creation -12/17/2011
      Purpose-   InitLoan Info       
      Documentation-      
      
 --%>


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
   
   function view_payable()
   {
   var url="Viewpayable.jsp";
   newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
	}
   
   
   
      
	</script>
	</head>
	<body onload="enableAnchor();" onunload="closeAllLovCallUnloadBody();">
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
	
	<html:form action="/paymentDetailProcess" method="POST">
	
	   <fieldset>	  
	<legend><bean:message key="lbl.payment"></bean:message></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td width="20%"><bean:message key="lbl.loanaccountno"></bean:message> </td>
			 <td width="35%">
	<html:text property="loanAccount" styleClass="text" styleId="loanAccount" value="5256" maxlength="100"></html:text>
	</td>
	<td width="20%"><bean:message key="lbl.customername"></bean:message></td>
	<td width="35%">
	<html:text property="customerName" styleClass="text" styleId="customerName" value="Sohan" maxlength="100"></html:text>
	</td>
		</tr>
	  <tr>
		<td>
		<bean:message key ="lbl.paymentDate"></bean:message>   
	 </td>
			<td nowrap="true" >
			<html:text property="paymentDate" styleClass="text" styleId="paymentDate" value="10-03-2011" maxlength="100"></html:text> 
	</td>
		   <td>		   
	<bean:message key ="lbl.chargeBPT"></bean:message>	   
	 </td>
			<td nowrap="true" ><span style="float:left;">			
			<html:select property="bpType" styleId="bpType">
			<html:option value="select">--Select--</html:option>			  
              </html:select>
			</span></td>     
	 </tr>
	<tr>  
		 <td><bean:message key="lbl.chargeBPN"></bean:message> </td>
		 <td nowrap="true" ><span style="float:left;">
		 <html:select property="bpName" styleId="bpName">	
		 <html:option value="select1">--Select--</html:option>	  
          </html:select>
		 </span></td>
			
			<td><bean:message key ="lbl.paymentMode"></bean:message> </td>
		 <td nowrap="true" ><span style="float:left;">
		 <html:select property="paymentMode" styleId="paymentMode">
		 <html:option value="cash">--Cash--</html:option> 
		 <html:option value="cheque">--Cheque--</html:option> 
		 <html:option value="dd">--DD--</html:option> 
		 <html:option value="neft">--NEFT--</html:option> 
		 <html:option value="rtgs">--RTGS--</html:option>	              
           </html:select>
		 </span></td>
		</tr>
		<tr>
					
		   <td width="20%"><bean:message key="lbl.instrumentNumber"></bean:message> </td>
			 <td width="35%"><div style="float:left;">
			 <html:text property="instrumentNo" styleClass="text" styleId="instrumentNo" value="6012" maxlength="100"></html:text>
			 </div></td>
	    <td><bean:message key="lbl.instrumentDate"></bean:message> </td>
	    <td><html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" value="10-03-2011" maxlength="100"></html:text>
	  </td>
		</tr>
		<tr>
					
		   <td width="20%">
		   <bean:message key="lbl.BankName"></bean:message>
	</td>
			 <td width="35%"><div style="float:left;">
			 <html:text property="bankName" styleClass="text" styleId="bankName" value="ABC" maxlength="100"></html:text>			  
			 </div></td>
	<td><bean:message key="lbl.branchMICRCode"></bean:message></td>
	<td>
	<html:text property="micrCode" styleClass="text" styleId="micrCode" value="1234" maxlength="100"></html:text>
	</td>
		</tr>
		<tr>
					
		   <td width="20%">
		   <bean:message key="lbl.ifsCode"></bean:message>
	 </td>
			 <td width="35%"><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="7810" maxlength="100"></html:text>		
	         </div></td>
	         <td><bean:message key="lbl.remark"></bean:message> </td>
	         <td>
	         <html:textarea property="remarks" styleClass="text" styleId="remarks"></html:textarea>	         
	        </td>
		</tr> 
		
		
		<tr>
	  
	      <td align="left" class="form2" colspan="3">
	      <button type="button" name="button2"  class="topformbutton2"  title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>&nbsp;
		  <button type="button" name="button2"  class="topformbutton2" title="Alt+F" accesskey="F" ><bean:message key="button.savefrwd" /></button>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <td align="left" class="form2" colspan="3">
          <button type="button" name="button29" title="Alt+P" accesskey="P" class="topformbutton2"  onclick="return view_payable();"><bean:message key="button.viewPayable" /></button>
          </td>
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