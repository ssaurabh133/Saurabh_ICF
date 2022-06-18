<!--Purpose  : Information of Vehicle Approval Search-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/vehicalApprovalScript.js"></script>
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

<body onload="enableAnchor();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/vehicleApprovalGridDispatchAction" styleId="vehicleApprovalGrid" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>

<html:errors/>

<fieldset id=sublist>
<legend><bean:message key="lbl.vehicleApprovalGrid"/></legend>
	<table width="100%">
	
	<tr>
	   <td width="220" >
	   <bean:message key="lbl.product" /> <br>
	     </td>
	  <td>
       <html:text property="product" styleId="product" tabindex="-1" styleClass="text" value="${requestScope.list[0].product}" readonly="true" ></html:text>
       <html:hidden property="lbxProductID" styleId="lbxProductID"  value="" />
       <html:button property="productButton"  styleId="productButton" onclick="openLOVCommon(17,'vehicleApprovalGrid','product','','scheme','lbxScheme','','','lbxProductID')" value=" "  styleClass="lovbutton"></html:button>
	   </td> 
     	
     	<td width="220" >
		<bean:message key="lbl.scheme" /> <br> 
	      </td>
       <td>
          <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value="${requestScope.list[0].scheme}" tabindex="-1"/>
          <html:hidden  property="lbxScheme" styleId="lbxScheme" value="" />
          <html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(25,'vehicleApprovalGrid','scheme','product','lbxScheme', 'lbxProductID','Select Product LOV','','lbxScheme')" value=" " styleClass="lovbutton"></html:button>  								    
      </td> 
    </tr>
       <!-- asdsadad -->
     </table>
     
       <table width="100%">
		<tbody>
		<tr>
	 
	 <td>  <button type="button" name="search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="searchVehicleApprovalGrid();" ><bean:message key="button.search" /></button>
       <button type="button"  name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="newAddVehicalApprovalGrid();" ><bean:message key="button.add" /></button> </td>

</tr>
		</tbody>
		</table>
       <!-- sdsff -->
 
	 
</fieldset>
<br/>
<fieldset>
		 <legend><bean:message key="lbl.vehicleApprovalGrid"/></legend> 

  <logic:present name="list">
 	<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/vehicleApprovalGridBehindAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="vehicleApprovalID" titleKey="lbl.vehicleApprovalID"  sortable="true" />
    <display:column property="lbxProductID" titleKey="lbl.product"  sortable="true" />
	<display:column property="lbxScheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="manufactId" titleKey="lbl.manufact"  sortable="true"  />
	<display:column property="branchAmt" titleKey="lbl.branchAmount"  sortable="true"  />
	<display:column property="nationalAmt" titleKey="lbl.nationalAmount"  sortable="true"  />
	<display:column property="hoAmt" titleKey="lbl.HOAmount"  sortable="true"  />
	
	
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td class="gridtd">
			<table width="100%" border="0" cellpadding="3" cellspacing="1">
				<tr>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.vehicleApprovalID" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.product" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.scheme" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.manufact" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.branchAmount" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.nationalAmount" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.HOAmount" /> <br> </b>
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
  </body>
		</html:html>