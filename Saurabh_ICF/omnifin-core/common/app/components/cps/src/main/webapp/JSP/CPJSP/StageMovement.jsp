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
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/contentstyle.css"/>
		 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/subpage.css"/>
	   
		 
		 <!-- css for Datepicker -->
		<link type="text/css" href="../../development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="../../development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="../../development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="../../development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="../../development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="../../development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		
		<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
	
	<script type="text/javascript">
  	
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
    <script type="text/javascript">
	
		function search()
		{
			var url="clerify_customer.html";
		    window.open(url,'name','height=400,width=1000,top=200,left=250');
	        return true;
		}
		
    </script>
	</head>
	<body onload="enableAnchor();init_fields();">
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

     
<fieldset>	
		 <legend>Stage Movement</legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr>
        <td width="220" class="white2" style="width:60px;"><strong><bean:message key="lbl.serialNumber"/> </strong></td>
		<td width="220" class="white2" style="width:250px;"><strong><bean:message key="lbl.stage"/>Stage</strong></td>
        <td width="220" class="white2" style="width:220px;"><b><bean:message key="lbl.fileIn"/></b></td>
        <td width="220" class="white2" style="width:220px;"><strong><bean:message key="lbl.fileOut"/></strong></td>
                
	</tr>
	<tr>
     <td class="white" id="">1</td>
	 <td class="white" id="">Deal Capturing</td>
     <td class="white" id="">03/02/2011 11:00AM</td>
	 <td class="white" id="">02/02/2011 01:00PM</td>
	      
	</tr>
	
	<tr>
     <td class="white" id="">2</td>
	 <td class="white" id="">Trade Checking</td>
     <td class="white" id="">05/02/2011 02:00PM</td>
	 <td class="white" id="">02/02/2011 01:00PM</td>
	 </tr>
 </table>    </td>
</tr>
</table>

	</fieldset>

</div>
<form action="" id="ok">
	
</form>

<logic:present name="status">

 <script type="text/javascript">
 
	if('<%=request.getAttribute("status").toString()%>'=='CS')
	{
		alert("<bean:message key="lbl.custMove" />");
		location.href="custEntryAction.do";
	//	document.forms[0].action="custEntryAction.do";
	    //document.forms[0].submit();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='LD')
	{
		alert("<bean:message key="lbl.loanMove" />");
		location.href="loanDetailBehindAction.do";
		//document.forms[0].action="loanDetailBehindAction.do";
	   // document.forms[0].submit();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='AC')
	{
		alert("<bean:message key="lbl.assetMove" />");
		location.href="collateralDetailBehindAction.do";
		//document.forms[0].action="collateralDetailBehindAction.do";
		//alert(document.forms[0].action);
	   // document.forms[0].submit();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='CH')
	{
		alert("<bean:message key="lbl.chargeMove" />");
		location.href="chargeBehindAction.do";
		//document.forms[0].action="chargeBehindAction.do";
	    //document.forms[0].submit();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='DC')
	{
		alert("<bean:message key="lbl.docsMove" />");
		location.href="docsCollectionBehindAction.do";
		//document.forms[0].action="docsCollectionBehindAction.do";
	    //document.forms[0].submit();
	}	
</script>
</logic:present>

<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.stageSuccess" />");
	    parent.location='<%=request.getContextPath()%>/JSP/CPJSP/commonDeal.jsp';
 
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.stageError" />");
		   parent.location='<%=request.getContextPath()%>/JSP/CPJSP/commonDeal.jsp';
		
	}
	</script>
</logic:present>

</body>
</html:html>