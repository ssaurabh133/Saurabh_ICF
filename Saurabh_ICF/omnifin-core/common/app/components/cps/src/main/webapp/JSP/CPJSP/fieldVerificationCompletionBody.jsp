<%--
      Author Name-  Neeraj Tripathi
      Date of creation -12/10/2011
      Purpose-          
      Documentation-     
      
 --%>
 
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       Date currentDate = new Date();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
	<!-- css for Datepicker -->
	<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/demos/demos.css" rel="stylesheet" />
	<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
    <link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/jquery.ui.theme.css" rel="stylesheet" />
    <link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.theme.css" rel="stylesheet" />  
    
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
          
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
  	   <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/fieldVerificationScript.js"></script>
  	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  
     
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>


	</head>

<body onload="enableAnchor();checkChanges('fieldVerificationBody');"  onunload="closeAllWindowCallUnloadBody();">
<form name="fieldVerificationBody" id="fieldVerificationBody">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

    <fieldset>
	<table cellspacing=0 cellpadding=0 width="100%" border=0>
  	<tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
        </tr>
        <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >CONTACT VERIFICATION COMPLETION</td>
          </tr>
        </table> 
	</td>
	</tr>
	</table>
 	</fieldset> 

   <fieldset>	
	<legend><bean:message key="lbl.customer_detail"/></legend>  
 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
		 <tr>
		    <td class="gridtd">
        		<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
    			<tr class="white2">
	        	 	<td align="center"><b><bean:message key="lbl.customer_id"/></b></td>
        		 	<td align="center"><b><bean:message key="lbl.customer_name"/></b></td>
			 		<td align="center"><b><bean:message key="lbl.applicant_type"/></b></td>
			 		<td align="center"><b><bean:message key="lbl.verification_sub_type"/></b></td>
			 	</tr>
						
	<logic:present name="customer_detail">
	  <logic:notEmpty name="customer_detail">
			<logic:iterate name="customer_detail" id="subdealdetails" >
			   <tr class="white1">	        
		          	 <td>${subdealdetails.customer_id}</td>          	 
		         	<td><a href="#" onclick="popUpComJsp('${subdealdetails.customer_id}','${subdealdetails.verificationSubType}');">${subdealdetails.customer_name}</a></td>
		         	 <td align="center">${subdealdetails.applicant_type}</td>
		         	 <td align="center">${subdealdetails.verificationSubType}</td>
		      </tr>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="customer_detail">
			<script type="text/javascript">
			
			             alert('No Data Found');
			             self.close(); 
		                 
		    </script>
		</logic:empty>
	</logic:present>
					
	</table>
	</td>
	</tr> 
 </table>
 </td>
 </tr>
 </table>
 </fieldset>
 </form>
 <script>
	setFramevalues("fieldVerificationBody");
</script>

</body>
</html:html>