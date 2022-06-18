
<%@ page language="java"%>
<%@ page session="true"%> 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanInitiation.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jquery.simpletip-1.3.1.js"></script>
	</head>	
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<script type="text/javascript">
	//start:added by indrajeet
	function fnSearchSblGbl(){
		
	     var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value
	     var disbursalId=document.getElementById("disbursalId").value
		 if(lbxLoanNoHID=="")
			{
			 alert('loan no is required');
			 return false;
			}
	     if(disbursalId=="")
			{
	    	 alert('disbursal no is required');	
	    	 return false;
			}
	     if(lbxLoanNoHID !="" && disbursalId !=""){
		 var contextPath=document.getElementById("contextPath").value;
		 document.getElementById("CreditForm").action = contextPath+"/sblGblMakerSearch.do?method=sblGblSearch&lbxLoanNoHID="+lbxLoanNoHID;
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("CreditForm").submit();
	     }
			
	}
	function fnSaveSblGbl(){
		var lbxLoanNoHID=document.getElementById("lbxLoanNoHID").value
		var remarks0=document.getElementById("remarks0").value;
		if(remarks0==''){
			alert('Remarks is required');
		}
		if(remarks0!= "")
		{
		 var contextPath=document.getElementById("contextPath").value;
		 document.getElementById("CreditForm").action = contextPath+"/sblGblMakerSearch.do?method=savesblDetails"+lbxLoanNoHID;
		 document.getElementById("processingImage").style.display = '';
		 document.getElementById("CreditForm").submit();
		}
	}
	
	
	//end:added by indrajeet

</script>
<body onload="enableAnchor();init_fields();">
	<html:form action="/creditListAction"  styleId="CreditForm">
	
	<input type="hidden" name="contextPath" id="contextPath" value="<%=path %>" />
		  
	   <fieldset>	  
	<legend><bean:message key="lbl.sblGblWaiverMaker"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
		
<tr>
		
			</td>
			<td><bean:message key="lbl.loanNumber"/> </td>
			<td>
	        <input type="hidden" name="customerName" id="customerName"/> 
 	        <html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
          	<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  />
  			<html:button property="loanButton" styleId="loanButton" onclick="closeLovCallonLov('loanNo');openLOVCommon(192023,'CreditForm','loanNo','userId','lbxLoanNoHID', 'lbxDealNo','','','customerName');" value=" " styleClass="lovbutton"></html:button>

            	
              
         </td>
         
         
         			
		 <td width="20%">
	    <bean:message key="lbl.disbursalNo"/></td>
	    <td width="35%">
	     <html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1"/>
		 <html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
		 <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		 <input type="hidden" name="disbursalId" id="disbursalId" value="" />
		 <html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(192024,'CreditForm','dealNo','loanNo','lbxDealNo', 'lbxLoanNoHID','Select Loan No LOV','','lbxDealNo');closeLovCallonLov();" value=" " styleClass="lovbutton"></html:button>
		</tr>
		<tr>

	      <td align="left" class="form2" colspan="4">
	      <button type="button" name="search"  title="Alt+S" accesskey="S" id="searchButton" class="topformbutton2" onclick="return fnSearchSblGbl();"><bean:message key="button.search" /></button>
	      </td>
	 
	 
	 
	 
		</tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>	
	
 <fieldset>	
		 <legend><bean:message key="lbl.sblGblWaiverMaker"/></legend>  
		 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <logic:notPresent name="list">
    <tr class="white2">
        <td ><b><bean:message key="lbl.loan"/></b></td>
        <td><b><bean:message key="lbl.disbursalNo"/></b></td>
        <td><b><bean:message key="lbl.sblMaster"/></b></td>
        <td><b><bean:message key="lbl.gblMaster"/></b></td>
        <td><b><bean:message key="lbl.sblLimit"/></b></td>
        <td><b><bean:message key="lbl.gblLimit"/></b></td>
        <%-- <td><b><bean:message key="lbl.sblGblDetail"/></b></td>
		<td><b><bean:message key="lbl.sblGblDetailValue"/> </b></td>
		<td><b><bean:message key="lbl.gap"/> </b></td> --%>
		<td><b><bean:message key="lbl.Remarks"/> </b></td>		
	</tr>
	<tr class="white2">
	<td colspan="10"> <bean:message key="lbl.noDataFound"/> </td>
	</tr>
	</table>    </td>
	</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
 <logic:present name="list">
 
 <input type="hidden" name="disbursalNo" id="disbursalNo" value="${list[0].disbursalNo}" />
 <input type="hidden" name="sblMasterLimit" id="sblMasterLimit" value="${list[0].sblMasterLimit}" />
 <input type="hidden" name="gblMasterLimit" id="gblMasterLimit" value="${list[0].gblMasterLimit}" />
 <input type="hidden" name="custCurrentPos" id="custCurrentPos" value="${list[0].custCurrentPos}" />
 <input type="hidden" name="groupPos" id="groupPos" value="${list[0].groupPos}" />
 <input type="hidden" name="loanId" id="loanId" value="${list[0].loanNo}" />
 <logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${requestScope.totalRecordSize}" requestURI="" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanNo" titleKey="lbl.loan"  sortable="true"  />
    <display:column property="disbursalNo" titleKey="lbl.disbursalNo"  sortable="true"  />
	<display:column property="sblMasterLimit" titleKey="lbl.sblMaster"  sortable="true"  />
	<display:column property="gblMasterLimit" titleKey="lbl.gblMaster"  sortable="true"  />
	<display:column property="custCurrentPos" titleKey="lbl.sblLimit"  sortable="true"  />
	<display:column property="groupPos" titleKey="lbl.gblLimit"  sortable="true"  />
	<%-- <display:column property="diff" titleKey="lbl.gap"  sortable="true"  /> --%>
	<display:column property="remarks" titleKey="lbl.Remarks"  sortable="true"  /> 
</display:table>
<tr>
<td colspan="31" class="white1"><button type="button" name="save&forward" title="Alt+S" accesskey="S" id="button" class="topformbutton3" onclick="return fnSaveSblGbl();"><bean:message key="button.save&forward" /></button></td>
</tr>
</logic:notEmpty>
<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td ><b><bean:message key="lbl.loan"/></b></td>
        <td><b><bean:message key="lbl.disbursalNo"/></b></td>
        <td><b><bean:message key="lbl.sblMaster"/></b></td>
        <td><b><bean:message key="lbl.gblMaster"/></b></td>
        <td><b><bean:message key="lbl.sblLimit"/></b></td>
        <td><b><bean:message key="lbl.gblLimit"/></b></td>
        <%-- <td><b><bean:message key="lbl.sblGblDetail"/></b></td>
		<td><b><bean:message key="lbl.sblGblDetailValue"/> </b></td>
		<td><b><bean:message key="lbl.gap"/> </b></td> --%>
		<td><b><bean:message key="lbl.Remarks"/> </b></td>
	</tr>
	
<tr class="white2">
	<td colspan="10"><bean:message key="lbl.noDataFound" /></td>
</tr>
 </table>    </td></tr>

</table>

</logic:empty>
  </logic:present>
	</fieldset>




  </html:form>
  
</div>
</div>
<logic:present name="sms">
<script type="text/javascript">
//alert("hello");

	 if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert('Data saved successfully');
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert('Data already exists');
	}
	
	</script>
</logic:present>
<script>
	setFramevalues("creditListAction");
</script>
</body>
</html>