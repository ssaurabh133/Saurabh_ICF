<!--Author Name :Kanika Maheshwari-->
<!--Date of Creation :13 October 2011  -->
<!--Purpose  : Information of Allocation master search-->
<!--Documentation : -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

  

<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/allocation.js"></script>
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
<body onload="enableAnchor();init_fields();">


<html:form styleId="AllocationMasterSearchForm"  method="post"  action="/allocationMasterSearch" >


<fieldset>
<legend><bean:message key="lbl.queueAllocationMaster" /></legend>

<table>
<tr> 
  
   <td width="13%"><bean:message key="lbl.iduser" /></td>
    <td width="13%"> <html:text property="userIdsearch" styleClass="text" styleId="userIdsearch" maxlength="10" readonly="true"></html:text>
    <html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(91,'AllocationMasterSearchForm','userIdsearch','','', '','','','lbxUserSearchId')"></html:button> 
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath">
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId"  />
        
  
       
     </td>
    <td width="13%"><bean:message key="lbl.queue" /></td>
     <td width="13%"><html:text property="queueIdSearch" styleClass="text" styleId="queueIdSearch" maxlength="30" readonly="true"   />

      <html:button property="queueButton" styleId="queueButton" onclick="openLOVCommon(189,'AllocationMasterSearchForm','queueIdSearch','','', '','','','lbxQueuesearchId')" value=" " styleClass="lovbutton"></html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxQueuesearchId" styleId="lbxQueuesearchId"  />
     </td>
     </tr>
     <tr><td>
     <select id="queueId" name="queueId" size="5" multiple="multiple" style="width: 120" tabindex="-1" onchange="queueselect();">
		   </select>
     
   <html:button property="userButton" styleId="userButton" onclick="return openMultiSelectLOVCommon(189,'AllocationMasterSearchForm','queueId','','', '','','','lbxQueuesearchId');" value=" " styleClass="lovbutton"></html:button>
	  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxQueuesearchId" styleId="lbxQueuesearchId"  />
	</td></tr>
     <tr> 
    <td>
    <!--  <input type="button"  value="Search" id="save" class="topformbutton2" title="Alt+F" accesskey="F" onclick="this.disabled='true';fnSearch('<bean:message key="lbl.selectAtleast" />');" />   
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <html:button property="add" value="New" styleId="add" styleClass="topformbutton2" title="Alt+N" accesskey="N" onclick="newAdd();"></html:button>-->
    
<button type="button" class="topformbutton2" name="save"  id="save"  onclick="this.disabled='true';fnSearch('<bean:message key="lbl.selectAtleast" />');" 
 title="Alt+S" accesskey="S" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" class="topformbutton2" name="add"  id="add"  onclick="newAdd();" title="Alt+N" accesskey="N" ><bean:message key="button.new" /></button>
    </td>
  </tr>
    
</table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.queueAllocationDetail" /></legend>


<logic:present name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" partialList="true" size="${list[0].totalRecordSize}" cellspacing="1" requestURI="/allocationMasterSearch.do?method=openSearchJsp" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="userId" titleKey="lbl.userId"  sortable="true"  />
	<display:column property="queueId" titleKey="lbl.queue"  sortable="true"  />
		<display:column property="allocationpercentage" titleKey="lbl.allocpercentage"  sortable="true"  />
	<display:column property="queueStatus" titleKey="lbl.active"  sortable="true"  /> 
	
</display:table>
  </logic:present>	
  <logic:notPresent name="list">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tbody><tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tbody><tr align="center" class="white2">

<td><b><bean:message key="lbl.userId" /></b></td>
<td><b><bean:message key="lbl.queue" /></b></td>
<td><b><bean:message key="lbl.allocpercentage" /></b></td>
<td><b><bean:message key="lbl.active" /></b></td>

</tr>
</tbody></table>
</td>
</tr></tbody></table>
</logic:notPresent>
</fieldset>    

  
           
	</html:form>		
		
	<logic:present name="sms">
	
<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>		
</body>
		</html:html>