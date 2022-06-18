
<!-- 
Author Name :- Manisha Tomar
Date of Creation :20-09-2011
Purpose :-  screen for the Search of all deals
-->
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		
		
	
	<script type="text/javascript">
  			function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
	</head>
	<body onload="enableAnchor();document.getElementById('searchForCPForm').dealNoButton.focus();init_fields();">
	
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
	<html:form action="/searchCPBehindAction" method="post" styleId="searchForCPForm">


		      <fieldset>	  
	    <legend><bean:message key="lbl.search"/></legend>         
	   
	   <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		  
		   <tr>
		   <logic:present name="searchScreenForDeal"> 
            <td><bean:message key="lbl.dealNo"></bean:message> </td>
		     <td>
		      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		     <html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(172,'searchForCPForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
            <!--   <img onclick="openLOVCommon(123,'paymentMakerSearch','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/>-->
        
		  </td>
		  </logic:present>
<!-- Changes Start By Sanjog-->
		  <logic:present name="searchScreenForLead"> 
            <td><bean:message key="lbl.leadNo"></bean:message> </td>
		     <td>
		      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		     <html:text styleClass="text" property="leadNo" styleId="leadNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(359,'searchForCPForm','leadNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
            <!--   <img onclick="openLOVCommon(123,'paymentMakerSearch','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/>-->
        
		  </td>
		  </logic:present>
<!-- Changes End By Sanjog-->
	      <td><bean:message key="lbl.customerName"></bean:message></td>
          <td >
	      <html:text property="customerName"  styleClass="text" styleId="customerName" value="" maxlength="50" readonly="true"></html:text>
		  </td>
		  </tr>
		  
	        <tr>
		    <td>
		    <logic:present name="searchScreenForDeal">
	      <button type="button" class="topformbutton2" id="search" onclick="return onsearchCP('<bean:message key="msg.DealNo"/>');" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	      </logic:present>
	      <logic:present name="searchScreenForLead">
	      <button type="button" class="topformbutton2" id="search" onclick="return onsearchForLead(document.getElementById('leadNo').value);" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	      </logic:present>
	      </td>
		  </tr>
		 </table>	
	     </td>	
	     </tr>
	
	</table>
	 
	</fieldset>	
	
		
	 
          <logic:present name="true">
	
		
     <fieldset>	
		 <legend><bean:message key="lbl.deals"/></legend>  

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
	 
         <td><b><bean:message key="lbl.dealNo"/></b></td>
	     <td><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.product"/></b></td>
         <td><b><bean:message key="lbl.scheme"/></b></td>
	     <td><b><bean:message key="lbl.currentStatus"/></b></td>
	     <td><b><bean:message key="lbl.branch"/></b></td>
	     <td><b><bean:message key="lbl.maker"/></b></td>
	     <td><b><bean:message key="lbl.author"/></b></td>
		
		
		</tr>
	
	  <logic:present name="list">	
		<logic:iterate name="list" id="sublist">
		<tr class="white1">
	    <td><a href="#" onclick="searchDeal(${sublist.lbxDealNo});" />${sublist.dealNo}</td>
	     <td>${sublist.customerName}</td>
	    <td>${sublist.product}</td>	    
	    <td>${sublist.scheme}</td>
	    <td>${sublist.currentStatus}</td>
	    <td>${sublist.dealBranch}</td>
	    <td>${sublist.maker}</td>
	    <td>${sublist.author}</td>
	  
		
		</tr>
 
   </logic:iterate>
   </logic:present>
 </table>
    
    </td>
</tr>
</table>

	</fieldset>

	
		
        </logic:present>
        <!-- Changes Start By Sanjog-->
        <logic:present name="searchScreenForLead">
	
		
     <fieldset>	
		 <legend><bean:message key="lbl.lead.detail"/></legend>  

  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
	 
         <td><b><bean:message key="lbl.leadno"/></b></td>
	     <td><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.product"/></b></td>
         <td><b><bean:message key="lbl.scheme"/></b></td>
	     <td><b><bean:message key="lbl.currentStatus"/></b></td>
		
		
		</tr>
	
	  <logic:present name="list">	
		<logic:iterate name="list" id="sublist">
		<tr class="white1">
	    <td><a href="#" onclick="openLeadView(${sublist.leadno});" />${sublist.leadno}</td>
	     <td>${sublist.customername}</td>
	    <td>${sublist.product}</td>	    
	    <td>${sublist.scheme}</td>
	    <td>
		    <logic:equal name="sublist" property="status" value="X">
		    	Reject
		    </logic:equal>
		     <logic:notEqual name="sublist" property="status" value="X">
		    	${sublist.status}
		    </logic:notEqual>
	    </td>
	  
		
		</tr>
 
   </logic:iterate>
   </logic:present>
 </table>
    
    </td>
</tr>
</table>

	</fieldset>

	
		
        </logic:present>
<!-- Changes End By Sanjog-->

  </html:form>
  <logic:present name="datalist">
		<script type="text/javascript">
			alert("<bean:message key="lbl.noDataFound" />");	
		
		</script>
		</logic:present>	
  
   
</div>



</div>
</body>
</html>