<%--
      Author Name-  Ritu Jindal
      Date of creation -05/09/2011
      Purpose-   Field Varification Info       
      
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
com.login.roleManager.UserObject userobj=(com.login.roleManager.UserObject)session.getAttribute("userobject");
		String branchId = "";
		if (userobj != null) {
			branchId = userobj.getBranchId();
			session.setAttribute("branchId",branchId);
		}
%>
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
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
	<body  onload="enableAnchor();document.getElementById('fieldVarificationComSearchForm').dealButton.focus();">	
	
	<logic:present name="verifCPS">
				<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/fieldVarificationAction" method="post" styleId="fieldVarificationComSearchForm">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  
	  <legend><bean:message key="lbl.ContactVerificationCompletion"/></legend>	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 	<tr>
		 		<td width="20%"><bean:message key="lbl.dealNo"/></td>
				<td width="35%" valign="top">
					<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
					<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
					<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(206,'fieldVarificationComSearchForm','dealNo','','','','','','customerName')" value=" " styleClass="lovbutton"> </html:button>
	        		<%-- <img onclick="openLOVCommon(21,'commonForm','dealNo','','', '','','','customername')" src="<%= request.getContextPath()%>/images/lov.gif"  />--%> 	  
       			</td>	 
	    		<td><bean:message key="lbl.customerName"/></td>
				<td width="13%" ><html:text property="customerName"   styleClass="text" styleId="customerName" maxlength="50" onchange="upperMe('customerName');"/></td> 
		   </tr>
		   <tr>
				<td><bean:message key="lbl.initiationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
				<td ><html:text property="initiationDate"   styleClass="text" styleId="initiationDate" maxlength="10" onchange="return checkDate('initiationDate');"/>
				</td> 	  
				<td width="20%"><bean:message key="lbl.product"/></td> 
       			<td width="20%">
      				<html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        		<html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(207,'fieldVarificationComSearchForm','product','','scheme','','','','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
	        	</td>
       	   </tr>
		   <tr>
		       <td width="8%"><bean:message key="lbl.scheme"/></td>
            	<td width="20%">
            
               		<html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          	   		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(208,'fieldVarificationComSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " styleClass="lovbutton"> </html:button>
               		<%-- <img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" />--%>
           	   		<input type="hidden" id="schemeId" name="schemeId"/> 				    
               </td> 
            </tr> 				
		   <tr>
		  	<td align="left">
		 	    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return newSearchComCapture();"><bean:message key="button.search" /></button>
			    
		    </td>		     
		 	</tr>
			</table>
		
	      </td>
	</tr>	
	</table>	 
	</fieldset>
	</html:form>

	

<fieldset>	
    
<legend><bean:message key="lbl.completion"/></legend> 
<logic:present name="list">
<logic:notEmpty name="list">
<logic:notPresent name="search"> 
    <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/fieldVarificationCompletionBehindAction.do" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
		<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
		<display:column property="initiationDate" titleKey="lbl.initiationDate"  sortable="true"  style="text-align: center"/>
		<display:column property="product" titleKey="lbl.product"  sortable="true"  />
		<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
		<display:column property="status" titleKey="lbl.dealStatus"  sortable="true"  />
	</display:table>
</logic:notPresent>
<logic:present name="search">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/fieldVarificationComAction.do?method=searchFieldComCapture" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		<display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
		<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
		<display:column property="initiationDate" titleKey="lbl.initiationDate"  sortable="true"  style="text-align: center"/>
		<display:column property="product" titleKey="lbl.product"  sortable="true"  />
		<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
		<display:column property="status" titleKey="lbl.dealStatus"  sortable="true"  />
	</display:table>
</logic:present>		
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">   
    <tr class="white2">
        <td align="center"><b><bean:message key="lbl.dealNo"/></b></td>
        <td align="center"><b><bean:message key="lbl.customerName"/></b></td>
        <td align="center"><b><bean:message key="lbl.initiationDate"/></b></td>     
        <td align="center"><b><bean:message key="lbl.product"/></b></td>
        <td align="center"><b><bean:message key="lbl.scheme"/> </b></td>
         <td align="center"><b><bean:message key="lbl.dealStatus"/> </b></td>
       </tr>
		<tr class="white2" >
	        <td colspan="6"><bean:message key="lbl.noDataFound" /></td>
	</tr>	
 </table>    </td>
</tr>
</table>
</logic:empty>
</logic:present>
</fieldset>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
</logic:present>
<%-- For Cm --%>

<logic:present name="verifCMS">
	
				<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/fieldVarificationAction" method="post" styleId="fieldVarificationComSearchForm">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  
	  <legend><bean:message key="lbl.ContactVerificationCompletion"/></legend>	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		 	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 	<tr>
		 		<td width="20%"><bean:message key="lbl.loanNumber"/></td>
				<td width="35%" valign="top">
					 <input type="hidden" name="hCommon" id="hCommon"/>   
	                 <html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
          	         <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  />
  			         <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1374,'CreditForm','loanNo','','lbxLoanNoHID', '','','','customerName');" value=" " styleClass="lovbutton"></html:button>	  
	        		<%-- <img onclick="openLOVCommon(21,'commonForm','dealNo','','', '','','','customername')" src="<%= request.getContextPath()%>/images/lov.gif"  />--%> 	  
       			</td>	 
	    		<td><bean:message key="lbl.customerName"/></td>
				<td width="13%" ><html:text property="customerName"   styleClass="text" styleId="customerName" maxlength="50" onchange="upperMe('customerName');"/></td> 
		   </tr>
		
		   <tr>
		       	<td width="20%"><bean:message key="lbl.product"/></td> 
       			<td width="20%">
      				<html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        		<html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(207,'fieldVarificationComSearchForm','product','','scheme','','','','lbxProductID')" value=" " styleClass="lovbutton"> </html:button>
	        	</td>
		       <td width="8%"><bean:message key="lbl.scheme"/></td>
            	<td width="20%">
            
               		<html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          	   		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   		<html:button property="productButton" styleId="productButton" onclick="openLOVCommon(208,'fieldVarificationComSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " styleClass="lovbutton"> </html:button>
               		<%-- <img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" />--%>
           	   		<input type="hidden" id="schemeId" name="schemeId"/> 				    
               </td> 
            </tr> 				
		   <tr>
		  	<td align="left">
		 	    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return newSearchCompletionCaptureAtCM();"><bean:message key="button.search" /></button>
			    
		    </td>		     
		 	</tr>
			</table>
		
	      </td>
	</tr>	
	</table>	 
	</fieldset>
	</html:form>

	

<fieldset>	
    
<legend><bean:message key="lbl.completion"/></legend> 
<logic:present name="list">
<logic:notEmpty name="list">
<logic:notPresent name="search"> 
    <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/fieldVarificationCompletionBehindAction.do" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		
		<display:column property="loanNo" titleKey="lbl.loanNumber"  sortable="true"  />
		<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
		<display:column property="product" titleKey="lbl.product"  sortable="true"  />
		<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
		<display:column property="status" titleKey="lbl.dealStatus"  sortable="true"  />
	</display:table>
</logic:notPresent>
<logic:present name="search">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/fieldVarificationComAction.do?method=searchFieldComCapture" >
    	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		
		<display:column property="loanNo" titleKey="lbl.loanNumber"  sortable="true"  />
		<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
		<display:column property="product" titleKey="lbl.product"  sortable="true"  />
		<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
		<display:column property="status" titleKey="lbl.dealStatus"  sortable="true"  />
	</display:table>
</logic:present>		
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">   
    <tr class="white2">
        <td align="center"><b><bean:message key="lbl.loanNumber"/></b></td>
        <td align="center"><b><bean:message key="lbl.customerName"/></b></td>
         <td align="center"><b><bean:message key="lbl.product"/></b></td>
        <td align="center"><b><bean:message key="lbl.scheme"/> </b></td>
         <td align="center"><b><bean:message key="lbl.dealStatus"/> </b></td>
       </tr>
		<tr class="white2" >
	        <td colspan="6"><bean:message key="lbl.noDataFound" /></td>
	</tr>	
 </table>    </td>
</tr>
</table>
</logic:empty>
</logic:present>
</fieldset>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
</logic:present>
</body>
</html:html>