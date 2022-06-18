<!--Author Name : Neeraj Kumar Tripathi  -->
<!--Date of Creation : 05-Jan-2012-->
<!--Purpose  : Make Model Master -->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/makeModelMaster.js"></script>
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

<body onload="enableAnchor();document.getElementById('MakeModelMaster').productCategory.focus();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/MakeModelMasterSearch" styleId="MakeModelMaster" method="post" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.makeModelMaster"/></legend>
	<table width="100%">
	<tr>
                                    <!--changed by abhimanyu on 18/07/2012	-->

	<td width="5%"><bean:message key="lbl.productCategory" /></td>
    <td width="5%">
                   <html:select styleClass="text" styleId="productCategory" value="" property="productCategory">  
                    <html:option value="">Select</html:option>  
        				<logic:present name="ProductCategory">
        				<html:optionsCollection name="ProductCategory" label="categoryDesc" value="category"/>        				
        				</logic:present>
                   </html:select> 
    </td>
    
      	<td width="5%"><bean:message key="lbl.make" /></td>     		
      	<td width="20%">
      		<html:text property="make" styleClass="text" value="" styleId="make" maxlength="25" />
      	</td>
   </tr>
   <tr>
   		<td width="5%"><bean:message key="lbl.model" /></td>     		
      	<td width="20%">
      		<html:text property="model" styleClass="text" value="" styleId="model" maxlength="25" />
      	</td>
      	
      	<td width="5%"><bean:message key="lbl.usesType" /></td>
      	<td width="5%"> 
      	<html:text property="usesType" tabindex="-1" styleClass="text" value="" readonly="true" styleId="usesType" maxlength="100" /> 
      	<html:hidden property="usesTypeId" styleId="usesTypeId" value=""/>
        <html:button property="usesTypeButton" styleId="usesTypeButton" tabindex="" onclick="openLOVCommon(373,'MakeModelMasterAdd','usesTypeId','','', '','','','usesType','productType')" value=" " styleClass="lovbutton"  ></html:button>
        <html:hidden property="txtUsesType" styleId="txtUsesType" value=""/>
        <html:hidden property="productType" styleId="productType" value=""/> 
   		</td>
   </tr>
   </table> 
   <table width="100%">
   <tr>
   		<button type="button"  name="search" id="search" class="topformbutton2" onclick="searchManufacturerRecords();"><bean:message key="button.search" /></button>
   		<button type="button"  name="add" id="add" class="topformbutton2" onclick="return newMakeModelMaster();"><bean:message key="button.add" /></button>
   	</tr>
	</table>
</fieldset>
</html:form>

<logic:present name="list">
<fieldset><legend><bean:message key="lbl.makeModelMasterRecords"/></legend> 
<logic:empty name="list">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
			<tr>
	   			<td class="gridtd">
    			<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
    			<tr class="white2">
    				<td align="center"><b><bean:message key="lbl.makeModelID"/></b></td>
					<td align="center"><b><bean:message key="lbl.productCategory"/></b></td>									
    				<td align="center"><b><bean:message key="lbl.make"/></b></td>
    				<td align="center"><b><bean:message key="lbl.model"/></b></td>
    				<td align="center"><b><bean:message key="lbl.LTV"/></b></td>    				
    				<td align="center"><b><bean:message key="lbl.type"/></b></td>
    				<td align="center"><b><bean:message key="lbl.usesTypeDes"/></b></td>    				
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
<logic:notPresent name="search">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/MakeModelMasterBehindAction.do" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="makeModelId" titleKey="lbl.makeModelID"  sortable="true" />
		<display:column property="productCategory" titleKey="lbl.productCategory"  sortable="true"  />				
		<display:column property="make" titleKey="lbl.make"  sortable="true"  />
		<display:column property="model" titleKey="lbl.model"  sortable="true"  />		
		<display:column property="ltv" titleKey="lbl.LTV"  sortable="true"  style="text-align: right" />		
		<display:column property="type" titleKey="lbl.type"  sortable="true"  />
		<display:column property="usesType" titleKey="lbl.usesTypeDes"  sortable="true"  />		
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
				
	</display:table>
</logic:notPresent>
<logic:present name="search">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/searchMakeModelMaster.do?method=searchMakeModelRecords" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	
	    <display:column property="makeModelId" titleKey="lbl.makeModelID"  sortable="true" />
		<display:column property="productCategory" titleKey="lbl.productCategory"  sortable="true"  />		
		<display:column property="make" titleKey="lbl.make"  sortable="true"  />
		<display:column property="model" titleKey="lbl.model"  sortable="true"  />		
		<display:column property="ltv" titleKey="lbl.LTV"  sortable="true"  style="text-align: right" />		
		<display:column property="type" titleKey="lbl.type"  sortable="true"  />
		<display:column property="usesType" titleKey="lbl.usesTypeDes"  sortable="true"  />		
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:present>
</logic:notEmpty>
</fieldset>
</logic:present>


</body>	
</html:html>
	