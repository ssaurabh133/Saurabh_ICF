<!--Author Name : Anil Kumar Yadav  -->
<!--Date of Creation : 06-Sept-2012-->
<!--Purpose  : Manufacturer Supplier Mapping Master -->
<!--Documentation : -->


<%@ page language="java"%>
<%@ page session="true"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/manufaturerSupplierMapping.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
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

<body onload="enableAnchor();document.getElementById('ManufacturerSupplierMappingForm').manufacturerId.focus();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/mfrSupplMappingBehindAction" styleId="ManufacturerSupplierMappingForm" method="post" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.manufacturerSupplierMapping"/></legend>
	<table width="100%">
	<tr>
    
     <td width="13%"><bean:message key="lbl.manufacturers" /></td>
     <td width="13%"><html:text property="manufacturerId" styleClass="text" styleId="manufacturerId" readonly="true" tabindex="-1" value=""/>
       <html:button property="manufacturerIdButton" styleId="manufacturerIdButton" onclick="openLOVCommon(411,'ManufacturerSupplierMappingAddForm','manufacturerId','','', '','','','manufacturerDesc');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxmachineManufact" styleId="lbxmachineManufact"  />
     </td>
    
      <td width="13%"><bean:message key="lbl.manufacturerDesc"/></td>
       <td width="13%"><html:text property="manufacturerDesc" styleClass="text" value="" styleId="manufacturerDesc" maxlength="50"/>
    
         </tr>
   </table> 
   <table width="100%">
   <tr>
   <td>
   		<button type="button"  name="search" id="search" class="topformbutton2" onclick="searchMfrSupplMappingRecords();"><bean:message key="button.search" /></button>
   		<button type="button"  name="add" id="add" class="topformbutton2" onclick="openMfrSupplMappingScreen();"><bean:message key="button.add" /></button>
   </td>
   	</tr>
	</table>
</fieldset>
</html:form>

<logic:present name="list">
<fieldset><legend><bean:message key="lbl.MfrSupplMappingRecords"/></legend> 
<logic:empty name="list">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
			<tr>
	   			<td class="gridtd">
    			<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
    			<tr class="white2">
    				<td align="center"><b><bean:message key="lbl.mfrSuppMappingID"/></b></td>											
    				<td align="center"><b><bean:message key="lbl.manufacturerDesc"/></b></td>  				
    				<td align="center"><b><bean:message key="lbl.active"/></b></td>
    			</tr>
    			<tr class="white2">
    				<td colspan="9">&nbsp;
    					<bean:message key="lbl.noDataFound" />
    				</td>
    			</tr>
     			</table>
    	 		</td>
    	 	</tr>
    	 	</table>
    		</td>
     	</tr>
     </table>
</logic:empty>
<logic:notEmpty name="list"> 
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/mfrSupplMappingBehindAction.do" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	
	    <display:column property="mappingId" titleKey="lbl.mfrSuppMappingID"  sortable="true" />
		<display:column property="manufacturerDesc" titleKey="lbl.manufacturerDesc"  sortable="true"  />
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:notEmpty>
</fieldset>
</logic:present>


</body>	
</html:html>
	