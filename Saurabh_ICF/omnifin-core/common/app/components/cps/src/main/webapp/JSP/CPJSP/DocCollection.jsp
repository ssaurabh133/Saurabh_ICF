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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	      
       <script type="text/javascript" src="../../js/popup.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="../../js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="../../js/gcdScript/customerDetail.js"></script>

	</head>
	<body oncontextmenu="return false" onload="enableAnchor();init_fields();">
	
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
   <logic:notPresent name="loanInitDocs">
   <fieldset>
     <logic:present name="headerInfo">
      <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
         <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.dealNo" /></td>
          <td width="220" class="white2" style="width:150px;">${sessionScope.headerInfo[0].dealId}</td>
         <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.date"/></td>
           <td width="220" class="white2" style="width:150px;">${sessionScope.headerInfo[0].dealDate}</td>
          <td  class="white2" style="width:150px;"><bean:message key="lbl.customerName"/> </td>
            <td colspan="3" class="white2" style="width:150px;">${sessionScope.headerInfo[0].dealCustomerName}</td>
           </tr>
          <tr>
          <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.productType"/></td>
          <td width="220" class="white2" style="width:150px;">${sessionScope.headerInfo[1].dealProductCat}</td>
          <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.product"/></td>
          <td width="220" class="white2" style="width:150px;">${sessionScope.headerInfo[1].dealProduct}</td>
          <td width="220" class="white2" style="width:100px;"><bean:message key="lbl.scheme"/></td>
            <td width="220" class="white2" style="width:150px;">${sessionScope.headerInfo[1].dealScheme}</td>
          <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.currentStage"/> </td>
            <td width="220" class="white2" style="width:150px;">Deal Capturing</td>
          </tr>
        </table>   
</logic:present> 
<logic:notPresent name="headerInfo">
	  <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
         <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.dealNo" /></td>
          <td width="220" class="white2" style="width:150px;">${sessionScope.maxId}</td>
         <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.date"/></td>
           <td width="220" class="white2" style="width:150px;">${sessionScope.newinfo[0].dealDate}</td>
          <td  class="white2" style="width:150px;"><bean:message key="lbl.customerName"/> </td>
            <td colspan="3" class="white2" style="width:150px;">${sessionScope.newinfo[0].dealCustomerName}</td>
           </tr>
          <tr>
          <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.productType"/></td>
          <td width="220" class="white2" style="width:150px;">SME</td>
          <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.product"/></td>
          <td width="220" class="white2" style="width:150px;">
		  <logic:present name="loanList">
	          <logic:iterate name="loanList" id="subloanList">
	           	  ${subloanList.countProduct }
	            </logic:iterate>
	            </logic:present>
            </td>
          
          <td width="220" class="white2" style="width:100px;"><bean:message key="lbl.scheme"/></td>
            <td width="220" class="white2" style="width:150px;">
                 <logic:present name="loanList">
	          <logic:iterate name="loanList" id="subloanList">
	           	  ${subloanList.countScheme }
	            </logic:iterate>
	            </logic:present>
           </td>
          <td width="220" class="white2" style="width:150px;"><bean:message key="lbl.currentStage"/> </td>
            <td width="220" class="white2" style="width:150px;">Deal Capturing</td>
          </tr>
        </table> 
</logic:notPresent>
 
  
</fieldset>
</logic:notPresent>
<html:form styleId="form1" action="/docCollection" method="post">
	<fieldset><legend>Document Collection</legend>  
	<logic:present name="loanInitDocs">
	   <font color="red">LoanId: ${sessionScope.loanId }</font>
   </logic:present>
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
         
          <td width="181" class="white2" style="width:100px;"><strong><center><bean:message key="lbl.documentName"/></center></strong></td>
          <td width="109" class="white2" style="width:60px;"><b><bean:message key="lbl.originalOrPhotocopy"/></b></td>
          <td width="115" class="white2" style="width:100px;"><b><center><bean:message key="lbl.mandatoryOrNonMandatory"/></center></b></td>
          <td width="154" class="white2" style="width:100px;"><b><center><bean:message key="lbl.status"/></center></b></td>
          <td width="92" class="white2" style="width:80px;"><strong><center><bean:message key="lbl.receivedDate"/></center></strong></td>
             <td width="92" class="white2" style="width:80px;"><strong><center><bean:message key="lbl.deferredTillDate"/></center></strong></td>
               <td width="92" class="white2" style="width:80px;"><strong><center><bean:message key="lbl.expiryDate"/></center></strong></td>
                 <td width="169" class="white2" style="width:100px;"><strong><center><bean:message key="lbl.remarks"/></center></strong></td>
        
          </tr>
        <tr>
         
          <td height="30" class="white" id="">
          	<html:text property="documentName" styleClass="text" styleId="documentName" value="PAN Card" maxlength="120" />
          </td>
          <td class="white" id=""><center>
            Photocopy
          </center></td>
          <td class="white" id=""><center>Mandatory</center></td>
          <td class="white" id="">
          <html:select property="status" styleId="status" >
          	<html:option value="">--Select--</html:option>
          	<html:option value="">Deffered</html:option>
          	<html:option value="">Recieved</html:option>
          	<html:option value="">Pending</html:option>
          	<html:option value="">Waived</html:option>
          </html:select>
          </td>
           <td class="white" id=""><html:text property="receivedDate" styleId="receivedDate" value="" maxlength="100" size="10" /></td>
             <td class="white" id=""><html:text property="deferredTillDate" styleId="deferredTillDate" maxlength="100" size="10" /></td>
           <td class="white" id=""><html:text property="expiryDate" styleId="expiryDate" maxlength="100" size="10" /></td>
             <td class="white" id=""><html:text property="remarks" styleId="remarks" maxlength="100" size="10" /></td>
          
          </tr>
          
          <tr>
         
          <td height="30" class="white" id="">
          	<html:text property="documentName" styleClass="text" styleId="documentName" value="Balance Sheet" maxlength="120" />
          </td>
          <td class="white" id=""><center>
            Photocopy
          </center></td>
          <td class="white" id=""><center>Non-Mandatory</center></td>
          <td class="white" id="">
          <html:select property="status" styleId="status" >
          	<html:option value="">--Select--</html:option>
          	<html:option value="">Deffered</html:option>
          	<html:option value="">Recieved</html:option>
          	<html:option value="">Pending</html:option>
          	<html:option value="">Waived</html:option>
          </html:select>
          </td>
           <td class="white" id=""><html:text property="receivedDate" styleId="receivedDate" value="" maxlength="100" size="10" /></td>
             <td class="white" id=""><html:text property="deferredTillDate" styleId="deferredTillDate" maxlength="100" size="10" /></td>
           <td class="white" id=""><html:text property="expiryDate" styleId="expiryDate" maxlength="100" size="10" /></td>
             <td class="white" id=""><html:text property="remarks" styleId="remarks" maxlength="100" size="10" /></td>
          
          </tr>
          
          <tr>
         
          <td height="30" class="white" id="">
          	<html:text property="documentName" styleClass="text" styleId="documentName" value="Sales Tax Certificate" maxlength="120" />
          </td>
          <td class="white" id=""><center>
            Original
          </center></td>
          <td class="white" id=""><center>Mandatory</center></td>
          <td class="white" id="">
          <html:select property="status" styleId="status" >
          	<html:option value="">--Select--</html:option>
          	<html:option value="">Deffered</html:option>
          	<html:option value="">Recieved</html:option>
          	<html:option value="">Pending</html:option>
          	<html:option value="">Waived</html:option>
          </html:select>
          </td>
           <td class="white" id=""><html:text property="receivedDate" styleId="receivedDate" value="" maxlength="100" size="10" /></td>
             <td class="white" id=""><html:text property="deferredTillDate" styleId="deferredTillDate" maxlength="100" size="10" /></td>
           <td class="white" id=""><html:text property="expiryDate" styleId="expiryDate" maxlength="100" size="10" /></td>
             <td class="white" id=""><html:text property="remarks" styleId="remarks" maxlength="100" size="10" /></td>
          
          </tr>
          
 
        </table>    </td>
  </tr>
</table>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
	
	<tr>
  
 <td align="left">

 <button type="button" name="save" class="topformbutton2" id="save" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>

  </tr>
  
	</table>
	
	  </fieldset>
	<br/> 
	
	
	</fieldset>


<br/> 
	</fieldset>





  </html:form>
  

</div>



</div>
</body>
</html:html>