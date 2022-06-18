<%--
      Author Name-  Amit Kumar
      Date of creation -21/06/2014
      Purpose-   Dedupe Decision Search      
      
 --%>

<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

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
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		  
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/dedupeReferal.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>
	</head>
	<body  onload="enableAnchor();document.getElementById('dedupeReferalSearchForm').dealButton.focus();">
	<html:form action="/dedupeReferal" method="post" styleId="dedupeReferalSearchForm">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
  <logic:present name="dedupeReferal"> 
    
	<fieldset>	 	 
	  <legend><bean:message key="lbl.dedupeDecision"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 <table width="100%" border="0" cellspacing="1" cellpadding="1">
		 
		 <tr>
		 	<td width="20%"><bean:message key="lbl.dealNo"/></td>
		 	<td>
		 		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
			    <html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			    <html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(2070,'dedupeReferalSearchForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"> </html:button>
		   </td>
		   <td><bean:message key="lbl.corporateName"/></td>
		   <td width="13%">
		        <html:text property="customerName"   styleClass="text" styleId="customerName" maxlength="50" onchange="upperMe('customerName');"/>
		   </td>
		</tr>
	    <tr>
	  
		   <td width="20%"><bean:message key="lbl.product"/></td> 
           <td width="20%">
      		<html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(259,'dedupeReferalSearchForm','product','','','','','clearScheme','productId')" value=" " styleClass="lovbutton"> </html:button>
	        <%-- <img onclick="openLOVCommon(87,'commonForm','product','','scheme','lbxscheme','','','productId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>	         
     		<input type="hidden" id="productId" name="productId"/> 
      
         </td>
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          	   <html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'dedupeReferalSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " styleClass="lovbutton"> </html:button>
               <%-- <img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" />--%>
           	   <input type="hidden" id="schemeId" name="schemeId"/> 				    
             </td>       
       </tr>
     <!--   <tr>
	  
		   <td width="20%"><bean:message key="lbl.userNam"/></td> 
           <td width="20%">
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'dedupeReferalSearchForm','userName','userId','', 'userId','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
         </td>
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
             </td>       
       </tr> -->
			
		 <tr>
		  	<td align="left">
		 	    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="dedupeReferSearch();"><bean:message key="button.search" /></button>
		    </td>
	    </tr>
	</table>
   </td>
</tr>
</table>
	</fieldset>
	 </logic:present> 
 
<fieldset>	
		 <legend><bean:message key="lbl.dedupeR"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>
     <table width="100%" border="0" cellspacing="1" cellpadding="1">
    	<tr>
    	  <logic:notPresent name="list">
    	  	 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			    <td class="gridtd">
			    <table width="100%" border="0" cellspacing="1" cellpadding="1">
			    <tr class="white2">
			        <td ><b><bean:message key="lbl.dealNo"/></b></td>
			         <td><b><bean:message key="lbl.customerName"/></b></td>
			        <td><b><bean:message key="lbl.product"/></b></td>
			       	<td><b><bean:message key="lbl.scheme"/></b></td>
			       	<td><b><bean:message key="lbl.userNam"/></b></td>
			     </tr>
			     <tr class="white2" >
	    <td colspan="6"><bean:message key="lbl.noDataFound" /></td>
	</tr>
			 </table>    </td>
			</tr>
			</table>
    	  </logic:notPresent>
    	  		 <logic:present name="list">
						<logic:notEmpty name="list">
						<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/dedupeReferal.do"> 
							<display:setProperty name="paging.banner.placement" value="bottom" />
							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:column property="dealNo" titleKey="lbl.dealNo" sortable="true"/>
							<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
							<display:column property="product" titleKey="lbl.product" sortable="true" />
							<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
							<display:column property="userName" titleKey="lbl.userNam" sortable="true" />
						</display:table>
				</logic:notEmpty>
				<logic:empty name="list">
						 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			    <td class="gridtd">
			    <table width="100%" border="0" cellspacing="1" cellpadding="1">
			      <tr class="white2">
			         <td ><b><bean:message key="lbl.dealNo"/></b></td>
			         <td><b><bean:message key="lbl.customerName"/></b></td>
			         <td><b><bean:message key="lbl.product"/></b></td>
			         <td><b><bean:message key="lbl.scheme"/></b></td>
			       	 <td><b><bean:message key="lbl.userNam"/></b></td>
			     </tr>
			     <tr class="white2" >
	   				 <td colspan="6"><bean:message key="lbl.noDataFound" /></td>
	             </tr>
			 </table>    </td>
			</tr>
			</table>
				</logic:empty>
				</logic:present>
    	
	</tr>
 </table>    </td>
</tr>
</table>
	</fieldset>
	</html:form>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>

<logic:present name="msg">
	<script type="text/javascript">	

	 if('<%=request.getAttribute("msg")%>'=='S')
	{
	alert("<bean:message key="msg.ForwardNonEmiSuccessfully" />");
	}
	</script>
	</logic:present>
</body>
</html:html>