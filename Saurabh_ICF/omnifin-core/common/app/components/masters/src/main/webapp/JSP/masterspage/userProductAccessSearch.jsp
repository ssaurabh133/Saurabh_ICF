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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/UserProductAccess.js"></script>
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
<body onload="enableAnchor();clearAll();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/userProductAccessBehindAction" styleId="userProductAccess">
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.UserProductAccess"/></legend>
	<table width="100%">
	<tr>
      	<td width="5%"><bean:message key="lbl.userName" /></td>
      	<td width="13%">
      		<html:text property="showUserDescSearch" styleClass="text" styleId="showUserDescSearch" maxlength="100" readonly="true" />
			<html:hidden property="lbxUserId" styleId="lbxUserId" />
			<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(284,'userProductAccess','showUserDescSearch','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
			
		</td>
		
      	<td width="5%"><bean:message key="lbl.product" /></td>     		
      	<td width="13%">
      		<html:text property="showProductDescSearch" styleClass="text" styleId="showProductDescSearch" maxlength="100" readonly="true" />
			<html:hidden property="lbxProductId" styleId="lbxProductId" />
			<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(283,'userProductAccess','showProductDescSearch','','', '','','','lbxProductId')" value=" " styleClass="lovbutton"  ></html:button>
			
		</td>
   </tr>
  
   </table> 
   <table width="100%">
   
   


<tr>

   		<button type="button"  title="Alt+S" accesskey="S" name="search"  id="search" class="topformbutton2" onclick="return searchUserproduct('<bean:message key="msg.plsSelOneCriteria" />');" ><bean:message key="button.search" /></button> &nbsp;
   		<button type="button" title="Alt+A" accesskey="A" name="add" id="add" class="topformbutton2" onclick="return openAddUserProductAcess();" ><bean:message key="button.add" /></button>
</tr>


	</table>
</fieldset>
</html:form>

<logic:present name="list">
<fieldset><legend><bean:message key="lbl.UserProductAccessRecords"/></legend> 
<logic:empty name="list">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
			<tr>
	   			<td class="gridtd">
    			<table width="100%" border="0" cellspacing="1" id="table1" cellpadding="1">
    			<tr class="white2">
    				<td align="center"><b><bean:message key="lbl.userName"/></b></td>
    				<td align="center"><b><bean:message key="lbl.product"/></b></td>
    				<td align="center"><b><bean:message key="lbl.active"/></b></td>
    			</tr>
    			<tr class="white2">
    				<td colspan="6">
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
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="showUserDescSearch" titleKey="lbl.userName"  sortable="true" />
		<display:column property="showProductDescSearch" titleKey="lbl.product"  sortable="true" />
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:notPresent>
<logic:present name="search">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="showUserDescSearch" titleKey="lbl.userName"  sortable="true" />
		<display:column property="showProductDescSearch" titleKey="lbl.product"  sortable="true" />
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:present>
</logic:notEmpty>
</fieldset>
</logic:present>

</body>	
</html:html>