<%--
      Author Name-  Prashant kumar    
      Date of creation -12/05/2011
      Purpose-   InitLoan Info       
      Documentation-      
      
 --%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		   <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/customerService/customerServiceScript.js"></script>

    <!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->
	</head>
	
	<body oncontextmenu="return false" >
	
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	        <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	        <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">

<form action="summaryService" method="post" name="SummaryServiceForm">

 <fieldset>	
	     <legend><bean:message key="lbl.loanSummary"/></legend>    
		    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
   			  <td class="gridtd">    
    		    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    		       <tr class="white2" >        
        			 <td><strong><bean:message key="lbl.loanAccountNo" /></strong></td>
		             <td><strong><bean:message key="lbl.customername" /></strong></td>
                     <td><strong><bean:message key="lbl.loanStatus" /></strong></td>
                     <td ><strong><bean:message key="lbl.DisbursalStatus" /></strong></td>
                     
		             <td><strong><bean:message key="lbl.firstEMI" /></strong></td>
                     <td ><strong><bean:message key="lbl.lastEMI" /></strong></td>
                     <td><strong><bean:message key="lbl.role" /></strong></td>
		             <td><strong><bean:message key="lbl.loanBalPrinc" /></strong></td>
                     <td><strong><bean:message key="lbl.loanOverduePrin" /></strong></td>
                     
                     <td ><strong><bean:message key="lbl.overdueCharges" /></strong></td>
		             <td><strong><bean:message key="lbl.loanDPD" /></strong></td>
                     <td ><strong><bean:message key="lbl.loanNPAFlag" /></strong></td>
                     
                     <td><strong><bean:message key="lbl.repoFlag" /></strong></td>
                     <td><strong><bean:message key="lbl.legalFlag" /></strong></td>
                     <td><strong><bean:message key="lbl.soaLink" /></strong></td>
                    
	               </tr>
	               <logic:present name="customerExposureList">
	                 <logic:iterate id="subCustomerExposureList" name="customerExposureList">	 
		               <tr class="white1"> 
                           
                          <td >${subCustomerExposureList.loanNo }</td>
		                  <td >${subCustomerExposureList.customerName }</td>
		                  <td >${subCustomerExposureList.loanStatus }</td>
		                  <td >${subCustomerExposureList.disbursalStatus }</td> 
		                  <td>${subCustomerExposureList.firstEmi }</td> 
		                  <td >${subCustomerExposureList.lastEmi }</td>
		                  <td >${subCustomerExposureList.customerRole }</td>
		                  <td >${subCustomerExposureList.loanBalancePrincipal }</td>
		                  <td >${subCustomerExposureList.loanOverdueAmount }</td> 	
		                  <td>${subCustomerExposureList.overdueCharges }</td> 
		                  <td >${subCustomerExposureList.loanDpd }</td>
		                  <td >${subCustomerExposureList.npaFlag }</td>
		                  <td >${subCustomerExposureList.repoFlag }</td>
		                  <td >${subCustomerExposureList.legalFlag }</td> 	
		                  <td>${subCustomerExposureList.soaLink }</td> 
		                  

		               </tr>	 
                    </logic:iterate>
                    </logic:present>      
	        
                </table>
                </td>
                </tr>
                </table>
        </fieldset>
          
   </form>

</div>
</div>
</body>
</html:html>