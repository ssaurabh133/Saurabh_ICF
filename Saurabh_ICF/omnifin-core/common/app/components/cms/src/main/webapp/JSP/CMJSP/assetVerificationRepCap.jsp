<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
			<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
	
	<head>
		<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
			
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>	   
	
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
	 	

   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>
   
		<script type="text/javascript">
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
		</script>
	
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
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('sourcingForm').loanButton.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/assetVerificationInit" method="post" styleId="sourcingForm" >
	
	
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
    <fieldset>
 
	<legend><bean:message key="lbl.assetVerificationRepCap"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		 <tr>	
		 	
			<td width="13%"><bean:message key="lbl.LoanNo"/></td>
			<td width="13%">
			
			<html:hidden property="makerID" styleId="makerID" value="${requestScope.makerID}" />
			<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100" value="" readonly="true" tabindex="-1"/>  
			<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(103,'sourcingForm','loanAccNo','makerID','lbxLoanNoHID', 'makerID','','','customerName','hcommon')" value=" " styleClass="lovbutton"></html:button> 
			<%--<img onclick="openLOVCommon(103,'sourcingForm','loanAccNo','','', '','','','hcommon')" SRC="<%= request.getContextPath()%>/images/lov.gif"> --%> 
			<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
			
			<input type="hidden" name="hcommon" id="hcommon" />
			</td>
			        
			<td width="13%"><bean:message key="lbl.customerName"/></td>
			<td width="13%"><html:text styleClass="text" property="customerName" styleId="customerName" maxlength="50" value=""/>
			</td>
  	</tr>
  	
  	
  	
      <tr>
			<td width="13%"><bean:message key="lbl.assetId"></bean:message> </td>
			<td width="13%"> <html:text styleClass="text" property="assetID" styleId="assetID" tabindex="-1" readonly="true"/>
			<html:button property="assetButton" styleId="assetButton" onclick="openLOVCommon(143,'sourcingForm','assetID','loanAccNo','lbxLoanNoHID', 'assetDesc','Please Select Loan No first','','assetDesc')" value=" " styleClass="lovbutton"></html:button>
			<input type="hidden"  name="lbxAssetID" id="lbxAssetID"/>
			<%--<img onclick="openLOVCommon(139,'sourcingForm','assetID','loanNo','lbxLoanNo', 'assetDesc','Please Select Loan Account Lov first','','assetDesc')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%> 
			</td>
			 <td width="13%"><bean:message key="lbl.assetDesc"></bean:message> </td>
			<td width="13%">
			 <html:text property="assetDesc" styleClass="text" styleId="assetDesc" readonly="true" tabindex="-1" value="" maxlength="20"></html:text>
			</td>
	       </tr> 
	       
			<tr>
            <td>
      		<bean:message key="lbl.userName" />
      	    </td>
			<td>
            <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
            <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
     		<html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
     		<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
      		<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'sourcingForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
			</td>
		</tr>
  	
 <tr>     
      <td colspan="4">&nbsp;
      <button type="button" class="topformbutton2" id="SearchButton" title="Alt+S" accesskey="S" onclick="return searchAssetVerRepCap('<bean:message key="lbl.selectAtleast" />');"> <bean:message key="button.search" /></button>      
     </td>
    </tr>
        
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
	
	<logic:present name="dss">
	<script type="text/javascript">
	if('<%=request.getAttribute("dss").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitiation.do";
	document.getElementById('sourcingForm').submit();
	
	}else if('<%=request.getAttribute("dss").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/assetVerificationInitiation.do";
	document.getElementById('sourcingForm').submit();
	} 
	else if('<%=request.getAttribute("dss")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
	
     </script>
	</logic:present>
	
</html:form>
</div>
<br/>	
<fieldset>	
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
     <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="list">
    <tr class="white2">
        <td><strong><bean:message key="lbl.AssetId"/></strong></td>
        <td><strong><bean:message key="lbl.LoanNo"/></strong> </td>
        <td><b><bean:message key="lbl.AssetDesc"/></b></td>
        <td><strong><bean:message key="lbl.InitDate"/></strong> </td>
        <td><b><bean:message key="lbl.AppName"/></b></td>
        <td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	</tr>
<tr class="white2">
<td colspan="6">
<bean:message key="lbl.noDataFound" />
</td>
</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
	 <logic:present name="list">

<html:hidden property="loanAccNo1" styleId="loanAccNo1"  value="${listobj.loanAccNo}"/>
 <html:hidden property="assetID1" styleId="assetID1"  value="${listobj.assetID}"/>
  <html:hidden property="assetDescription1" styleId="assetDescription1"  value="${listobj.assetDescription}"/>
<logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/assetVerificationInit.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="modifyID" titleKey="lbl.AssetId"  sortable="true"  />
    <display:column property="loanAccNo" titleKey="lbl.LoanNo"  sortable="true"  />
	<display:column property="assetDescription" titleKey="lbl.AssetDesc"  sortable="true"  />
	 <display:column property="initDate" titleKey="lbl.InitDate"  sortable="true"  />
	<display:column property="appName" titleKey="lbl.AppName"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
</display:table>
</logic:notEmpty>

<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
         <td><strong><bean:message key="lbl.AssetId"/></strong></td>
        <td><strong><bean:message key="lbl.LoanNo"/></strong> </td>
        <td><b><bean:message key="lbl.AssetDesc"/></b></td>
        <td><strong><bean:message key="lbl.InitDate"/></strong> </td>
        <td><b><bean:message key="lbl.AppName"/></b></td>
         <td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	</tr>
<tr class="white2">
<td colspan="6">
<bean:message key="lbl.noDataFound" />
</td>
</tr>
 </table>    </td>

</table>
</logic:empty>
  </logic:present>

	</fieldset>
    
 

</div>
</body>
</html:html>