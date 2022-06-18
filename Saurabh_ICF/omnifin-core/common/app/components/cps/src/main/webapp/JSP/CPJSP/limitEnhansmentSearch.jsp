
<!-- 
Author Name :- Ankit Agarwal
Date of Creation :27-02-2012
Purpose :-  screen for loan sensation limit
-->

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		<logic:present name="css">
				<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
			<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/limitEnhancement.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	
		
		
	
	<script type="text/javascript">
  			function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
	</script>
	
	<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
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
	<body onload="enableAnchor();document.getElementById('searchLimitEnhancementForm').dealNoButton.focus();">
	
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	<logic:present name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
	</logic:notPresent>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

<html:form action="/limitEnhansmentBehind" method="post" styleId="searchLimitEnhancementForm">
  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
 <logic:notPresent name="AuthorSearch"> 
	<fieldset><legend><bean:message key="lbl.limEnhMakeSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 <tr>	   
            <td width="13%"><bean:message key="lbl.dealNo"></bean:message> </td>
		     <td width="13%" >
		      <html:text styleClass="text" property="dealNo" styleId="dealNo" value="" maxlength="20" readonly="true" tabindex="-1"/>
             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(291,'searchLimitEnhancementForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
             <html:hidden property="customerName"  styleClass="text" styleId="customerName" value=""  />
		  </td>
		  
	        <td width="13%"><!--<bean:message key="lbl.LoanNo"></bean:message> --></td>
		     <td width="13%" >
<!--		      <html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20" readonly="true" tabindex="-1" value=""/>-->
<!--             <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(292,'searchLimitEnhancementForm','loanNo','dealNo','lbxLoanNo', 'lbxDealNo','Select Deal Number','','customerName')" value=" " styleClass="lovbutton"></html:button>-->
<!--             <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value=""/>-->
		  </td>
		  </tr>
	    <tr>
		  <td>
	    	  <button type="button" class="topformbutton2" id="search" onclick="return searchLimit();" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	    	  <button type="button" class="topformbutton2" id="newButton" title="Alt+N" accesskey="N" onclick="newLimitMaker();" ><bean:message key="button.new" /></button>
	      </td>
		  </tr>
		</table>	
  	 </td>	
   </tr>
	</table>
	</fieldset>	
	
	<fieldset>	
	 <legend><bean:message key="lbl.limitEnhanDetail"/></legend>  

 <logic:present name="list">
<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/limitEnhansmentBehind.do?mehtod=limitMakerSearch" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="oldSenAmount" titleKey="lbl.oldSenAmount"  sortable="true"  style="text-align: right"/>
	<display:column property="addSenAmount" titleKey="lbl.addSenAmount"  sortable="true"  style="text-align: right"/>
	<display:column property="makerId" titleKey="lbl.maker"  sortable="true" style="text-align: right"/>
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
        <td><b><bean:message key="lbl.oldSenAmount"/></b></td>     
        <td><b><bean:message key="lbl.addSenAmount"/></b></td>
        <td><b><bean:message key="lbl.maker"/></b></td>
	</tr>
			   <tr class="white2" >
	        <td colspan="5"><bean:message key="lbl.noDataFound" /></td> 
	        </tr>
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>
	</fieldset>
	</logic:notPresent>	
	
	<!---------------- Limit Enhancement Author Search ------------------------------------------>
	
	<logic:present name="AuthorSearch">
	

	<fieldset><legend><bean:message key="lbl.limEnhAuthSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
		  <td width="13%"><bean:message key="lbl.dealNo"></bean:message> </td>
		     <td width="13%" >
		      <html:text styleClass="text" property="dealNo" styleId="dealNo" value="" maxlength="20" readonly="true" tabindex="-1"/>
             <html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(293,'searchLimitEnhancementForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
             <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
             <html:hidden property="customerName"  styleClass="text" styleId="customerName" value=""  />
		  </td>
		  
	        <td width="13%"><!--<bean:message key="lbl.LoanNo"></bean:message>--> </td>
		     <td width="13%" >
<!--		      <html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20" readonly="true" tabindex="-1" value=""/>-->
<!--             <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(294,'searchLimitEnhancementForm','loanNo','dealNo','lbxLoanNo', 'lbxDealNo','Select Deal Number','','customerName')" value=" " styleClass="lovbutton"></html:button>-->
<!--             <html:hidden  property="lbxLoanNo" styleId="lbxLoanNo" value=""/>-->
		  </td>
		  </tr>
	    <tr>
		  <td>
	    	  <button type="button" class="topformbutton2" id="search" onclick="return searchLimitAuthor();" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button>
	      </td>
		  </tr>
		</table>	
  	 </td>	
   </tr>
	</table>
	</fieldset>	
	
		<fieldset>	
	 <legend><bean:message key="lbl.limitEnhanDetail"/></legend>  

 <logic:present name="list">
<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/limitEnhansmentBehind.do?mehtod=limitAuthorSearch" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="oldSenAmount" titleKey="lbl.oldSenAmount"  sortable="true" style="text-align: right"/>
	<display:column property="addSenAmount" titleKey="lbl.addSenAmount"  sortable="true" style="text-align: right"/>
	<display:column property="makerId" titleKey="lbl.maker"  sortable="true" style="text-align: right"/>
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
        <td><b><bean:message key="lbl.oldSenAmount"/></b></td>     
        <td><b><bean:message key="lbl.addSenAmount"/></b></td>
         <td><b><bean:message key="lbl.maker"/></b></td>
	</tr>
			   <tr class="white2" >
	        <td colspan="5"><bean:message key="lbl.noDataFound" /></td> 
	        </tr>
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>
	</fieldset>
</logic:present>
</html:form>

</body>
</html>