<%--
      Author Name-  prashant kumar    
      Date of creation -12/04/2011
      Purpose-          
      Documentation-     
      
 --%>


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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
	
		 
		 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		
	

	</head>
	<body onunload="closeAllWindowCallUnloadBody();" onclick="parent_disable();" onload="enableAnchor();document.getElementById('ExistAssetForm').dealNoButton.focus();init_fields();" oncontextmenu="return false;">
	
	
	
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="exist" id="exist" value="E" />
	<html:form action="/existAssetsAction" styleId="ExistAssetForm" method="post">
	<div id="centercolumn">
	
	<div id="revisedcontainer">	
	
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>
	<fieldset>
	<logic:equal name="openType" value="D">	  
	  <legend><bean:message key="lbl.dealNo"/></legend>         
 	</logic:equal>
 	<logic:equal name="openType" value="L">	  
	  <legend><bean:message key="lbl.LoanNo"/></legend>         
 	</logic:equal>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<html:hidden property="openType" styleId="openType" value="${sessionScope.openType}" />
		<html:hidden property="dealCustomerId" styleId="dealCustomerId" value="${sessionScope.custID}" />
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
	<logic:equal name="openType" value="D">
		 <td><bean:message key="lbl.DealNo"/></td>
         <td>
       		 <html:text property="lbxDealNo" styleClass="text" styleId="lbxDealNo" maxlength="10" readonly="true" value="" tabindex="-1"/>
			 <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value=""/>
			 <html:button property="loanNoButton" style="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(169,'ExistAssetForm','lbxDealNo','dealCustomerId','lbxLoanNoHID', 'dealCustomerId','','','customerName');" value=" " styleClass="lovbutton"></html:button>
         <%-- <img onclick="openLOVCommon(46,'ExistAssetForm','loanNo','','', '','','clearDeal','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>	 	  
         </td>
    </logic:equal>
    <logic:equal name="openType" value="L">
    	 <td><bean:message key="lbl.LoanNo"/></td>
         <td>
       		 <html:text property="lbxDealNo" styleClass="text" styleId="lbxDealNo" maxlength="10" readonly="true" value="" tabindex="-1"/>
			 <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value=""/>
			 <html:button property="loanNoButton" style="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(470,'ExistAssetForm','lbxDealNo','dealCustomerId','lbxLoanNoHID', 'dealCustomerId','','','customerName');" value=" " styleClass="lovbutton"></html:button>
         <%-- <img onclick="openLOVCommon(46,'ExistAssetForm','loanNo','','', '','','clearDeal','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>	 	  
         </td>
    </logic:equal>
            <td><bean:message key="lbl.customerName"/></td>
           <td ><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="50" size="10" value="" /></td>
           
		</tr>
		
	 <tr>
	  
	       <td><bean:message key="lbl.collateralType"/></td>
	 <logic:equal name="openType" value="D">
           <td>
            <html:text property="collateralId" styleClass="text" styleId="collateralId" maxlength="10" readonly="true" value="" tabindex="1" style="float:left; margin:0px 5px 0px 0px;"/>
            <html:hidden  property="lbxCollateralId" styleId="lbxCollateralId" value=""/>

              <html:button property="collateralButton" style="collateralButton" onclick="closeLovCallonLov('lbxDealNo');openLOVCommon(108,'ExistAssetForm','collateralId','lbxDealNo','lbxCollateralId', 'lbxLoanNoHID','Please Select Deal No','','assetDesc');" value=" " styleClass="lovbutton"></html:button>
         <%-- <img onclick="openLOVCommon(108,'ExistAssetForm','collateralId','loanNo','lbxCollateralId', 'lbxLoanNoHID','','','common')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" /> --%>	
            </td>
     </logic:equal>
     <logic:equal name="openType" value="L">
     	<td>
            <html:text property="collateralId" styleClass="text" styleId="collateralId" maxlength="10" readonly="true" value="" tabindex="1" style="float:left; margin:0px 5px 0px 0px;"/>
            <html:hidden  property="lbxCollateralId" styleId="lbxCollateralId" value=""/>

              <html:button property="collateralButton" style="collateralButton" onclick="closeLovCallonLov('lbxDealNo');openLOVCommon(471,'ExistAssetForm','collateralId','lbxDealNo','lbxCollateralId', 'lbxLoanNoHID','Please Select Loan No','','assetDesc');" value=" " styleClass="lovbutton"></html:button>
         <%-- <img onclick="openLOVCommon(108,'ExistAssetForm','collateralId','loanNo','lbxCollateralId', 'lbxLoanNoHID','','','common')" src="<%= request.getContextPath()%>/images/lov.gif" style="float:left;" /> --%>	
        </td>
     </logic:equal>
             <td><bean:message key="lbl.collateralDescription"/></td>
           <td ><html:text property="assetDesc" styleClass="text" styleId="assetDesc" maxlength="50" size="10" value="" /></td>
           
          
     </tr>
											 
		<tr>
		  <td colspan="3" ><button type="button" name="button" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S"  onclick="return searchAsset();" ><bean:message key="button.search" /></button></td>
		  </tr>
		
		</table>
		
	   </td></tr>
	
	</table>
	 
	</fieldset>
	

	</div>

<fieldset>	
		 <legend><bean:message key="lbl.existDetail"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
         <td  ><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/></td>
        			   <td ><strong><bean:message key="lbl.collateralClass" /></strong></td>
        			  <td ><strong><bean:message key="lbl.collateralType" /></strong></td>
        	    	 <td><strong><bean:message key="lbl.collateralDescription" /></strong></td>
		             <td><strong><bean:message key="lbl.collateralValue" /></strong></td>
	     </tr>
	   <logic:present name="existasset">
	               
	                 <logic:iterate id="showCollateralDetails1" name="existasset" >		                
	               <tr class="white1"> 	             
  				  <td >
  				  <input type="checkbox" name="chk" id="chk"  value="${showCollateralDetails1.lbxCollateralId}" /></td>
  				     <td >${showCollateralDetails1.assetClass}</td>	  				          
	                 <td >${showCollateralDetails1.assetType}</td>
	                  <td >${showCollateralDetails1.assetDesc}</td>
	                  <td >${showCollateralDetails1.assetValue}</td>	               
	             
	               </tr>	 
                    </logic:iterate>
                    </logic:present>  
	
	
 </table>
    </td></tr>	
</table>
<button type="button" name="button" id="button" class="topformbutton2" title="Alt+D" accesskey="D" onclick="saveAssetCollateral();"><bean:message key="button.add" /></button>

	</fieldset>

</div>
</html:form>
<logic:present name="sms">

  <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
	   
		alert('<bean:message key="lbl.recordSuccess" />');
		 opener.location.href="collateralDetailBehindAction.do";
         javascript:window.close();
	}
	else
	{
		 alert('<bean:message key="lbl.recordError" />');
		 opener.location.href="collateralDetailBehindAction.do";
         javascript:window.close();
	}	
</script>


</logic:present>

<logic:present name="searchResult">
  <script type="text/javascript">
	if('<%=request.getAttribute("searchResult").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotFound" />");
	}	
</script>
</logic:present>
</body>
</html:html>