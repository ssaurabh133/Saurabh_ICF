<%--
      Author Name-  Manisha Tomar    
      Date of creation -19/09/2011
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
	
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		

		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		
		
	</head>

	<body onunload="closeAllWindowCallUnloadBody();" onclick="parent_disable();" onload="enableAnchor();init_fields();" oncontextmenu="return false;">

	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="exist" id="exist" value="E" />
	<html:form action="/existAssetsInCMBehindAction" styleId="ExistAssetForm" method="post">
	<div id="centercolumn">
	
	<div id="revisedcontainer">	
	
	

<fieldset>	
		 <legend><bean:message key="lbl.existDetail"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
    <tr class="white2">
    <html:hidden property="openType" styleId="openType" value="${requestScope.openType}" />
		 <input type="hidden" id="loanId" value="${loanHeader[0].loanId}"/>
		
         <td  ><input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked();"/></td>
        		
        			  <td ><strong><bean:message key="lbl.assetCollateralSecurityType" /></strong></td>
        	    	 <td><strong><bean:message key="lbl.assetCollateralDescription" /></strong></td>
        	         <td ><strong><bean:message key="lbl.Assets/Collateral" /></strong></td>
		             <td><strong><bean:message key="lbl.assetCollateralValue" /></strong></td>
	     </tr>
	     	       <logic:present name="existassetList">
	     	       <logic:notEmpty name="existassetList">
	     	       <logic:iterate id="showCollateralDetails1" name="existassetList" >		                
	               <tr class="white1"> 	             
  				  <td >
  				  
  				  <input type="checkbox" name="chk" id="chk"  value="${showCollateralDetails1.assetsId}" /></td>	  				          
	                  <td >${showCollateralDetails1.assetNature}
	                  <input type="hidden" name="assetNature" id="assetNature" value="${showCollateralDetails1.assetNature}" /></td>	                      
	                  <td >${showCollateralDetails1.colltype1}
	                  <input type="hidden" name="colltype1" id="colltype1" value="${showCollateralDetails1.colltype1}" /></td>
	                  <td >${showCollateralDetails1.assetsCollateralDesc}
	                  <input type="hidden" name="assetsCollateralDesc" id="assetsCollateralDesc" value="${showCollateralDetails1.assetsCollateralDesc}" /></td>
	                  <td >${showCollateralDetails1.assetsCollateralValue}
	                  <input type="hidden" name="assetsCollateralValue" id="assetsCollateralValue" value="${showCollateralDetails1.assetsCollateralValue}" /></td>	               
	             
	               </tr>	 
                    </logic:iterate>
                    </logic:notEmpty>
                    </logic:present>  
	
	     
	
 </table>
    </td></tr>	
</table>
<button type="button" class="topformbutton2" name="button"  id="button"  onclick="saveAssetCollateralInCM();" title="Alt+A" accesskey="A" >
<bean:message key="button.add" /></button>
<!-- <input type="button" name="button" id="button" class="topformbutton2" value="Add" title="Alt+D" accesskey="D" onclick="saveAssetCollateralInCM();"/>
 -->
	</fieldset>

</div>
</html:form>
<logic:present name="sms">

  <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
	   
		alert('<bean:message key="lbl.recordSuccess" />');
		opener.location.href="collaterlInCMBehindAction.do";
        javascript:window.close();
	}
	else
	{
		 alert('<bean:message key="lbl.recordError" />');
		 opener.location.href="collaterlInCMBehindAction.do";
         javascript:window.close();
	}	
</script>


</logic:present>

</body>
</html:html>