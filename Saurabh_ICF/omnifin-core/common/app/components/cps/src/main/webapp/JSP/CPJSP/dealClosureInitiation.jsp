<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
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
	<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/dealClosure.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
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
<body onload="enableAnchor();document.getElementById('dealCancellationForm').dealButton.focus();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
<html:form action="/dealClosureMakerBehindAction" method="post" styleId="dealCancellationForm">

<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
<logic:notPresent name="AuthorSearch">
<fieldset><legend><bean:message key="lbl.dealCancellation"/></legend> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 	<tr>
				<td><bean:message key="lbl.dealNo"/></td>
				<td>		
					<html:text property="dealNo" styleClass="text" styleId="dealNo" value="" maxlength="100" readonly="true" tabindex="-1" />
					<html:hidden property="lbxDealNo" styleId="lbxDealNo" />
					<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(369,'dealCancellationForm','dealNo','','', '','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
				</td>
				<td><bean:message key="lbl.customerName"/></td>
				<td><html:text property="customerName" styleClass="text" styleId="customerName" value="" maxlength="50" onchange="upperMe('customerName');"/></td> 
		    </tr>    
	   		<tr> 
				<td><bean:message key="lbl.product"/></td>      
       			<td> 
      		        <html:text  property="product" styleId="product" value="" styleClass="text" readonly="true"  tabindex="-1"/>
	        		<html:hidden property="lbxProductID" styleId="lbxProductID" />
	        		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(364,'dealCancellationForm','product','','scheme','','','','productId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	           </td>
	           <input type="hidden" id="productId" name="productId"/>
               <td><bean:message key="lbl.scheme"/></td>
               <td>            
               		<html:text property="scheme" styleId="scheme" value="" styleClass="text" readonly="true" tabindex="-1"/>
          	   		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(365,'dealCancellationForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');" value=" " styleClass="lovbutton"> </html:button>
           	   </td>
           	   <input type="hidden" id="schemeId" name="schemeId"/>
           </tr>
       	   <tr>
		    	<td align="left">
		    		<button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchDealClosure();"><bean:message key="button.search" /></button>
		   			<button type="button" name="new" id="button" class="topformbutton2" title="Alt+N" accesskey="N" onclick="openDealClosure()" ><bean:message key="button.new" /></button>
		   		</td>     
		  </tr>
		 </table>
	</td>
	</tr>
</table>
</fieldset>

<fieldset>	
	 <legend><bean:message key="lbl.dealCanMaker"/></legend>  

 <logic:present name="list">
<logic:notEmpty name="list">
  <display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/dealClosureMakerBehindAction.do?method=dealCancellationMakerSearch" >
     <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="lbxDealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true" style="text-align: right" />
	<display:column property="dealDate" titleKey="lbl.fiedDate"  sortable="true"  />
	<display:column property="makerId" titleKey="lbl.userNam"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
        <td ><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/></b></td>     
        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><b><bean:message key="lbl.fiedDate"/></b></td>
        <td><b><bean:message key="lbl.userNam"/></b></td>
	</tr>
			   <tr class="white2" >
	        <td colspan="7"><bean:message key="lbl.noDataFound" /></td> 
	        </tr>
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>
  </fieldset>
  </logic:notPresent>
	
	
	<!---------------- Deal Cancellation Author Search ------------------------------------------>
	
<logic:present name="AuthorSearch">
<fieldset><legend><bean:message key="lbl.dealCancellation"/></legend> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 	<tr>
				<td><bean:message key="lbl.dealNo"/></td>
				<td>		
					<html:text property="dealNo" styleClass="text" styleId="dealNo" value="" maxlength="100" readonly="true" tabindex="-1" />
					<html:hidden property="lbxDealNo" styleId="lbxDealNo" />
					<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(371,'dealCancellationForm','dealNo','','', '','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
				</td>
				<td><bean:message key="lbl.customerName"/></td>
				<td><html:text property="customerName" styleClass="text" styleId="customerName" value="" maxlength="50" onchange="upperMe('customerName');"/></td> 
		    </tr>    
	   		<tr> 
				<td><bean:message key="lbl.product"/></td>      
       			<td> 
      		        <html:text  property="product" styleId="product" styleClass="text" value="" readonly="true"  tabindex="-1"/>
	        		<html:hidden property="lbxProductID" styleId="lbxProductID" />
	        		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(364,'dealCancellationForm','product','','scheme','','','','productId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	           </td>
	           <input type="hidden" id="productId" name="productId"/>
               <td><bean:message key="lbl.scheme"/></td>
               <td>            
               		<html:text property="scheme" styleId="scheme" styleClass="text" value="" readonly="true" tabindex="-1"/>
          	   		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(365,'dealCancellationForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');" value=" " styleClass="lovbutton"> </html:button>
           	   </td>
           	   <input type="hidden" id="schemeId" name="schemeId"/>
           </tr>
       	   <tr>
		    	<td align="left">
		    		<button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchCancellationAuthor();"><bean:message key="button.search" /></button>
		   		</td>     
		  </tr>
		 </table>
	</td>
	</tr>
</table>
</fieldset>

<fieldset>	
	 <legend><bean:message key="lbl.dealCanAuthor"/></legend>  

 <logic:present name="list">
<logic:notEmpty name="list">
  <display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/dealClosureMakerBehindAction.do?method=dealCancellationMakerSearch" >
     <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="lbxDealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true" style="text-align: right" />
	<display:column property="dealDate" titleKey="lbl.fiedDate"  sortable="true"  />
	<display:column property="makerId" titleKey="lbl.userNam"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
        <td ><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/></b></td>     
        <td><b><bean:message key="lbl.loanAmount"/></b></td>
        <td><b><bean:message key="lbl.fiedDate"/></b></td>
        <td><b><bean:message key="lbl.userNam"/></b></td>
	</tr>
			   <tr class="white2" >
	        <td colspan="7"><bean:message key="lbl.noDataFound" /></td> 
	        </tr>
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>
  </fieldset>
</logic:present>
	
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</html:form>
</body>
</html:html>