<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
	<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
			
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>	   
		 
	
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
				
			<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<script type="text/javascript" src="<%=path%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
	 	

   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/assetVerificationInitiation.js"></script>
   
		<script type="text/javascript">
	        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=yes,width=1366,height=768' );
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
 
	<legend><bean:message key="lbl.assetVerificationInitiationSearch"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		 <tr>	
		 	
        	<td width="13%"><bean:message key="lbl.loanNo"/></td>
			<td width="13%"><html:text styleClass="text" property="loanNo" styleId="loanNo"	value="" readonly="true" tabindex="-1"/>
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(127,'sourcingForm','loanNo','userId','loanNo', 'userId','','','customerName')" value=" " styleClass="lovbutton"></html:button>
								<%--<img onclick="openLOVCommon(127,'sourcingForm','loanNo','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif"/> --%>
								<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
								<html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value="" />
			</td>
		  <td width="13%"><bean:message key="lbl.customerName"/></td>
			<td width="13%"><html:text styleClass="text" property="customerName" styleId="customerName"	maxlength="50" value=""/>
		</td>
		</tr>     
		     
    <tr>
		     <td width="13%"><bean:message key="lbl.assetId"></bean:message> </td>
			 <td width="13%"> <html:text styleClass="text" property="assetID" styleId="assetID" maxlength="20" tabindex="-1" readonly="true"/>
           <html:button property="assetButton" styleId="assetButton" onclick="openLOVCommon(139,'sourcingForm','assetID','loanNo','lbxLoanNo', 'assetDesc','Please Select Loan No first','','assetDesc')" value=" " styleClass="lovbutton"></html:button>
            <input type="hidden"  name="lbxAssetID" id="lbxAssetID"/>
      
          <%--<img onclick="openLOVCommon(139,'sourcingForm','assetID','loanNo','lbxLoanNo', 'assetDesc','Please Select Loan Account Lov first','','assetDesc')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%> 
		   </td>
		    <td><bean:message key="lbl.assetDesc"></bean:message> </td>
			<td width="13%"><div style="float:left;">
			 <html:text property="assetDesc" styleClass="text" styleId="assetDesc" readonly="true" tabindex="-1" value="" maxlength="20"></html:text>
			  </div></td>
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
		<td>
		<button type="button" name="save" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return assetVerificationInitSearch();" ><bean:message key="button.search" /></button>
		<button type="button" name="save" id="save"  class="topformbutton2" title="Alt+N" accesskey="N" onclick="return newAssetVerificationInit();" ><bean:message key="button.new" /></button>
	</td>
		
		</tr> 	  
		  
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	

</html:form>
<br/>
	</div>
	

<fieldset>	
  
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
  
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="list">
    <tr class="white2">
	 
       <td><strong><bean:message key="lbl.loanNo"/></strong> </td>
 		<td><strong><bean:message key="lbl.customerName"/></strong></td>
        <td><b><bean:message key="lbl.product"/></b></td>       
		<td><strong><bean:message key="lbl.scheme"/></strong></td>
        <td><b><bean:message key="lbl.appraiserType"/></b></td>
        <td><b><bean:message key="lbl.appraiserName"/></b></td>
         <td><b><bean:message key="lbl.userName"/></b></td>
		
	</tr>
		<tr class="white2">
<td colspan="7">
<bean:message key="lbl.noDataFound" />
</td>
</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
	 <logic:present name="list">

<logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/assetVerificationInitSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="verCodeModify" titleKey="lbl.loanNo"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="loanProduct" titleKey="lbl.product"  sortable="true"  />
	<display:column property="loanScheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="appraiserType" titleKey="lbl.appraiserType"  sortable="true"  />
	<display:column property="appraiserName" titleKey="lbl.appraiserName"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
	
</display:table>
</logic:notEmpty>

<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
       <td><strong><bean:message key="lbl.loanNo"/></strong> </td>
 		<td><strong><bean:message key="lbl.customerName"/></strong></td>
        <td><b><bean:message key="lbl.product"/></b></td>       
		<td><strong><bean:message key="lbl.scheme"/></strong></td>
        <td><b><bean:message key="lbl.appraiserType"/></b></td>
        <td><b><bean:message key="lbl.appraiserName"/></b></td>
        <td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	<tr class="white2">
<td colspan="7">
<bean:message key="lbl.noDataFound" />
</td>
</tr>
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>
	</fieldset>
    
 

</div>
</body>
</html:html>