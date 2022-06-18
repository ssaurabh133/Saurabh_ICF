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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/usedVehicalPricingScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
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

<body onload="enableAnchor();document.getElementById('vehiclePricing').usedVehicleMakeSearch.focus();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/usedVehiclePricingAdd" styleId="vehiclePricing" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>

<html:errors/>

<fieldset id=sublist>
<legend><bean:message key="lbl.usedVehicalPricing"/></legend>
	<table width="100%">
	
	<tr>
      <td width="13%"><bean:message key="lbl.usedVehicleMake" /><span><font color="red">*</font></span></td>
   <td> <html:text property="usedVehicleMakeSearch" styleId="usedVehicleMakeSearch" styleClass="text"  readonly="true" tabindex="-1" value=""/>
      <html:hidden property="makeModelId" styleId="makeModelId" value="" />
    <html:button property="vehicleMakeButton" styleId="vehicleMakeButton" 
    onclick="openLOVCommon(2063,'vehiclePricing','usedVehicleMakeSearch','','','','','','usedVehicleModelSearch');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>	
      </td>
      
      <td width="13%"><bean:message key="lbl.usedVehicleModel" /><span><font color="red">*</font></span></td>
     <td> <html:text property="usedVehicleModelSearch"  styleId="usedVehicleModelSearch" maxlength="50" value="" readonly="true" />        
      </td>
       </tr>
       <!-- asdsadad -->
       </table>
       <table width="100%">
		<tbody>
		<tr>
	 
	 <td>  <button type="button" name="search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="searchUsedVehiclePricing();" ><bean:message key="button.search" /></button>
       <button type="button"  name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="newAddVehicalPrising();" ><bean:message key="button.add" /></button> </td>

</tr>
		</tbody>
		</table>
       <!-- sdsff -->
 
	 
</fieldset>
<br/>
<fieldset>
		 <legend><bean:message key="lbl.usedVehicalDetails"/></legend> 

  <logic:present name="list">
 	<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/usedVehiclePricingBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="makeModelId" titleKey="lbl.makeModelID"  sortable="true" />
	<display:column property="usedVehicleMakeSearch" titleKey="lbl.usedVehicleMake"  sortable="true"  />
	<display:column property="usedVehicleModelSearch" titleKey="lbl.usedVehicleModel"  sortable="true"  />
	<display:column property="usedVehicleState" titleKey="lbl.usedVehicleState"  sortable="true"  />
	<display:column property="usedVehicleBranch" titleKey="lbl.usedVehicleBranch"  sortable="true"  />
	
	
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td class="gridtd">
			<table width="100%" border="0" cellpadding="3" cellspacing="1">
				<tr>
					
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.makeModelID" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.usedVehicleMake" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.usedVehicleModel" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.usedVehicleState" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.usedVehicleBranch" /> <br> </b>
					</td>
					
						<tr class="white2" >
	                    	<td colspan="5"><bean:message key="lbl.noDataFound" /></td>
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


    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		var contextPath =document.getElementById('contextPath').value ;
		document.getElementById("vehiclePricing").action=contextPath+"/usedVehiclePricingBehind.do";
	    document.getElementById("vehiclePricing").submit();
	}
	
	
	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
	
	
</script>
</logic:present>		
  </body>
		</html:html>