<%--
      Author Name-  Amit kumar    
      Date of creation -19/10/2012
      Purpose-   Quality Check Info       
      Documentation-      
      
 --%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>	
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
		
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
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
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
<body oncontextmenu="return false" onload="enableAnchor();document.getElementById('commonForm').dealButton.focus();init_fields();" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
	
<logic:present name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
</logic:present>
<logic:notPresent name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
</logic:notPresent>
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="stage" id="stage" value="${sessionScope.stage }" />
<input type="hidden" name="functionId" id="functionId" value="${functionId}" />

<div id="centercolumn">
	<div id="revisedcontainer">
	<html:form action="/qualityCheckAction" method="post" styleId="commonForm">
		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
			<fieldset>	  
  				<legend><bean:message key="lbl.qualityCheckSearch"/></legend>         
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				<td>
		 			<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
							<td><bean:message key="lbl.dealNo"/></td>
							<td>
								<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			<logic:equal name="stage" value="F">
			<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(464,'commonForm','dealNo','userId','dealNo', 'userId','','','customername');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
			</logic:equal>
			<logic:equal name="stage" value="A">
			<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(464,'commonForm','dealNo','userId','dealNo', 'userId','','','customername');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
			</logic:equal>
	  </td>
	  <td><bean:message key="lbl.customerName"/></td>
		<td><html:text property="customername" styleClass="text" styleId="customername" maxlength="50" onchange="upperMe('customerName');"/>
		</td> 
	    </tr>
	  
	    <tr>	    
		 <td><bean:message key="lbl.applicationNo"/></td>
	    <td><html:text property="applicationno" styleClass="text"styleId="applicationno" style="text-align: right" maxlength="20"  onkeyup="return upperMe('applicationno');"/></td>
	    <td><bean:message key="lbl.product"/></td> 
        <td> 
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(87,'commonForm','product','userId','scheme','lbxscheme|userId','','','productId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
			<input type="hidden" id="productId" name="productId"/>      
        </td>
	    </tr>
		
		<tr> 
        	<td><bean:message key="lbl.scheme"/></td>
            <td>
                <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
    			<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');" value=" " styleClass="lovbutton"> </html:button>
				<input type="hidden" id="schemeId" name="schemeId"/>           						    
            </td>
            
       	</tr> 
		 <tr>
		    
		     <td align="left">
		    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return qualityCheckSearch();"><bean:message key="button.search" /></button>
		    </td>
		 </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>

	</html:form>

	</div>

<fieldset>	
		 <legend><bean:message key="lbl.qualityCheckDetails"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="list">
    <tr class="white2">
	
		<td><b><bean:message key="lbl.select" /></b></td>
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
       	<td><b><bean:message key="grd.applicationNo"/></b></td>
      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
    <td><b><bean:message key="lbl.Remarks" /></b></td>
    <td><b><bean:message key="lbl.decision"/> </b></td>
   

	</tr>
	<tr class="white2"><td colspan="9"><bean:message key="lbl.noDataFound"/></td></tr>
	</logic:notPresent>
 </table>    </td>
</tr>
</table>
 <logic:present name="list">
 <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/qualityCheckAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="chk" titleKey="lbl.select"  sortable="false"  />
    <display:column property="lbxDealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customername" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="applicationno" titleKey="grd.applicationNo"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="remarks" titleKey="lbl.Remarks"  sortable="false"  />
	<display:column property="decision" titleKey="lbl.decision" sortable="false" />
	<display:column property="sanctionValidTill" titleKey="lbl.sancValidDate" sortable="false" />
		
</display:table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr>
	
		<td align="left">
		    <button type="button" name="saveFwd" id="saveFwd" tabindex="-1" class="topformbutton3" onclick="return qualityCheckSaveDeal();"><bean:message key="button.savefrwd" /></button>
		</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
</logic:notEmpty>
<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
		<td><b><bean:message key="lbl.select" /></b></td>
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
       	<td><b><bean:message key="grd.applicationNo"/></b></td>
      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
         <td><b><bean:message key="lbl.Remarks" /></b></td>
         <td><b><bean:message key="lbl.decision"/> </b></td>
	</tr>
	<tr class="white2"><td colspan="9"><bean:message key="lbl.noDataFound"/></td></tr>
	
 </table>   
 </td>
</tr>
</table>
</logic:empty>
  </logic:present>
	</fieldset>
</div>

<%-- Div for screen Saver --%>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
	</div>
</body>
</html:html>
<logic:present name="sms">
	<script type="text/javascript">	
		if('<%=request.getAttribute("sms")%>'=='S')
		{
			alert('<bean:message key="msg.DataSaved" />');
			window.location="<%=request.getContextPath()%>/qualityCheckAction.do?method=searchDealForQualityCheck";
		}
		else if('<%=request.getAttribute("sms")%>'=='CE')
		{
			alert('<bean:message key="lbl.someDealConfirmError" />');
			window.location="<%=request.getContextPath()%>/qualityCheckAction.do?method=searchDealForQualityCheck";
		}
		else 
		{
			alert('<bean:message key="msg.DataNotSaved" />');
			window.location="<%=request.getContextPath()%>/qualityCheckAction.do?method=searchDealForQualityCheck";
		}
	</script>
</logic:present>