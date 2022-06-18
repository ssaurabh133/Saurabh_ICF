
<!--Author Name : Ritu Jindal-->
<!--Date of Creation :12May 2011  -->
<!--Purpose  : Information of Charge Master Search-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/chargeScript.js"></script>
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
<body onload="enableAnchor();document.getElementById('chargeMasterSearchForm').chargeSearchButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="chargeMasterSearchForm"  method="post"  action="/chargeMasterSearch" >

<html:errors/>
<fieldset>
<legend><bean:message key="lbl.chargeMaster" /></legend>

<table border="0" cellpadding="4" cellspacing="2" width="100%">
<tr> 
  
     <td width="13%"><bean:message key="lbl.chargeCode" /></td>
     <td width="13%"><html:text property="chargeSearchCode" styleClass="text" styleId="chargeSearchCode" readonly="true" tabindex="-1"/>
       <html:button property="chargeSearchButton" styleId="chargeSearchButton" onclick="openLOVCommon(98,'chargeMasterSearchForm','chargeSearchDes','','', '','','','chargeSearchCode');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxChargeSearch" styleId="lbxChargeSearch"  />
     </td>
    
      <td width="13%"><bean:message key="lbl.chargeDescription"/></td>
       <td width="13%"><html:text property="chargeSearchDes" styleClass="text" styleId="chargeSearchDes" maxlength="10" readonly="true"/>
    
     </tr>
    
    <tr>
     <td ><bean:message key="product.desc" /></td>
      <td><html:text property="productSearchId" styleClass="text" styleId="productSearchId" readonly="true" tabindex="-1"/>
      <html:button property="productSearchButton" styleId="productSearchButton" onclick="openLOVCommon(99,'chargeMasterSearchForm','productSearchId','','','','','','lbxProductSearchID');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
     <html:hidden  property="lbxProductSearchID" styleId="lbxProductSearchID"  />
     </td>
    <td><bean:message key="scheme.desc" /></td>
      <td><html:text property="schemeSearchId" styleClass="text" styleId="schemeSearchId" maxlength="10" readonly="true" tabindex="-1" />
     <html:button property="schemeSearchButton" styleId="schemeSearchButton" onclick="openLOVCommon(100,'chargeMasterSearchForm','schemeSearchId','productSearchId','lbxSchemeSearch','lbxProductSearchID','Please Select Product LOV First','','lbxSchemeSearch');closeLovCallonLov('productSearchId');" value=" " styleClass="lovbutton"> </html:button>
     <html:hidden  property="lbxSchemeSearch" styleId="lbxSchemeSearch" />
     </td>
     </tr>
     
    <tr> 
    <td>
     <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fnSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="return newAdd();"><bean:message key="button.add" /></button></td>
  </tr>
</table>
</fieldset>
</br>
<fieldset>
<legend><bean:message key="lbl.chargeDetail" /></legend>


 <logic:present name="list">
 <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/chargeMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="lbxChargeModify" titleKey="lbl.charge_description"  sortable="true"  />
	<display:column property="productId" titleKey="lbl.product"  sortable="true"  />
	<display:column property="schemeId" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="chargeStatus" titleKey="lbl.active"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td class="gridtd">
			<table width="100%" border="0" cellpadding="4" cellspacing="1">
				<tr>
					<td width="220" class="white2" style="width: 220px;">
						<strong><bean:message key="lbl.chargeCode" /> </strong>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.product" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.scheme" /> <br> </b>
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
  </body>
		</html:html>