
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/nhbMapping.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>

</head>
	<body onload="enableAnchor();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/searchNhbMapping" method="post" styleId="NhbMappingForm">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<fieldset>	  
	  <legend><bean:message key="lbl.nhbMapping"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 <table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%" valign="top">
				
			<input type="hidden" name="customerName" id="customerName" value="" />
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" value="" />
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
			<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(2013,'NhbMappingForm','dealNo','','dealNo', '','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	        	  
      
	  </td>
	  <td width="8%"><bean:message key="lbl.nhbCategory"/></td>
            <td width="20%">
            
               <html:text property="nhbCategory" styleId="nhbCategory" styleClass="text" readonly="true" tabindex="-1" value=""/>
          		<html:hidden  property="nhbCategoryId" styleId="nhbCategoryId" value="" />
          		
          		 <html:button property="nhbCategoryButton" styleId="nhbCategoryButton" onclick="openLOVCommon(2014,'NhbMappingForm','nhbCategory','dealNo','nhbCategoryId', 'lbxDealNo','Please Select Deal No','','nhbCategoryId');closeLovCallonLov('nhbCategory');" value=" " styleClass="lovbutton"> </html:button>
        
           						    
                </td>
	  
	    </tr>
	  
	    
		
						
		 <tr>
		    
		     <td align="left">
		    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchForNhbMapping();"><bean:message key="button.search" /></button>
		   <button type="button" name="new" id="newbutton" class="topformbutton2" title="Alt+N" accesskey="N" onclick="newNhbMapping();" ><bean:message key="button.new" /></button></td>
		     
		 </tr>
		</table>
		
	      </td>
	  </tr>

	
	</table>
	 
	</fieldset>

	</html:form>


<fieldset>	
		 <legend><bean:message key="lbl.nhbCategoryDetails"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
    <tr class="white2">
	<logic:notPresent name="list">
	
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
        <td><b><bean:message key="lbl.nhbCategory"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
   
</logic:notPresent>
	</tr>
 </table>    </td>
</tr>
</table>
 <logic:present name="list">
 <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/searchNhbMapping.do?method=searchNhbMapping" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="nhbCategory" titleKey="lbl.nhbCategory"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
        <td><b><bean:message key="lbl.nhbCategory"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
	</tr>
	<tr class="white2"><td colspan="7"><bean:message key="lbl.noDataFound"/></td></tr>
	
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>


	</fieldset>


</body>
</html:html>