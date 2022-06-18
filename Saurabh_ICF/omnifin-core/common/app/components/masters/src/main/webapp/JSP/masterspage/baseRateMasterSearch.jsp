<!--Author Name : Ritu Jindal-->
<!--Date of Creation :9 May 2011 -->
<!--Purpose  : Information of Base Rate Master Search-->
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

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>
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

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/baseRateScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

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

<body onload="enableAnchor();document.getElementById('baseRateMasterSearchForm').baseRateTypeSearch.focus();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>

<div id=centercolumn>
<div id=revisedcontainer>
<html:form action="/baseRateMasterSearch" styleId="baseRateMasterSearchForm" method="post" > 	
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.bplrMaster"/></legend>
	<table width="100%">
	<tr>
	<td ><bean:message key="lbl.bplrbaseRateType" /></td>
<!--    <td ><html:text property="baseRateTypeSearch" styleClass="text" styleId="baseRateTypeSearch" maxlength="10"  /></td>-->
    <td>
		<html:select property="baseRateTypeSearch" styleId="baseRateTypeSearch" styleClass="text">
			<logic:present name="baseRateTypeList" > 
				<html:option value="">--Select--</html:option>         	
				    <html:optionsCollection name="baseRateTypeList" label="bRTypeDesc" value="bRTypeCode" />
			</logic:present>
	    </html:select>
    </td>
   
 	<td ><bean:message key="lbl.effectiveFromDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
    <td ><html:text property="effectiveFromDateSearch"  styleId="effectiveFromDateSearch" styleClass="text"  onchange="return checkDate('effectiveFromDateSearch');" maxlength="10"/></td>
  

	<td><bean:message key="lbl.bplrbaseRateDesc" /></td>
    <td ><html:text property="baseRateDescSearch" styleClass="text" styleId="baseRateDescSearch" maxlength="50"  /></td>
  </tr>

 <tr>
	  <td>
 	  <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fnSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
   <button type="button"  name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="return newAdd();"><bean:message key="button.add" /></button></td>

	</tr>
	 </table>
</fieldset>

<br/>
<fieldset>
		 <legend><bean:message key="lbl.baseRatedetail"/></legend> 

 <logic:present name="list">
 <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/baseRateMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="baseRateTypeModify" titleKey="lbl.bplrbaseRateType"  sortable="true"  />
	<display:column property="effectiveFromDate" titleKey="lbl.effectiveFromDate"  sortable="true"  />
	<display:column property="baseRateDesc" titleKey="lbl.bplrbaseRateDesc"  sortable="true"  />
	<display:column property="baseRate" titleKey="lbl.bplrbaseRate"  sortable="true"  />
	<display:column property="baseRateStatus" titleKey="lbl.active"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td class="gridtd">
			<table width="100%" border="0" cellpadding="4" cellspacing="1">
				<tr>
					<td width="220" class="white2" style="width: 220px;">
						<strong><bean:message key="lbl.baseRateType" /> </strong>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.effectiveFromDate" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.baseRateDesc" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.baseRate" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.repaymentType" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.active" /> <br> </b>
					</td>
					<tr class="white2" >
	                    	<td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                        </tr>
				</tr>
			</table>
		</td>
	</tr>
</table>
</logic:empty>
  </logic:present>

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
</div>
</div>	
  </body>
		</html:html>