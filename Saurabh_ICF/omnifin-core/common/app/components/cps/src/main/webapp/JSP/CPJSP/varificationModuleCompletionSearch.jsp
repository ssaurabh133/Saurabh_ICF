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
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		  
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);

%>
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>

		<!--[if gte IE 5]>
	<style type="text/css">
	
	.white1 {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
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
	<body  onload="enableAnchor();document.getElementById('fieldVarificationSearchForm').dealButton.focus();">
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

	<html:form action="/fieldVarificationAction" method="post" styleId="fieldVarificationSearchForm">
	
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
        <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  
	  <legend><bean:message key="lbl.fieldVerSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
		 <table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%" valign="top">
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" />
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(197,'fieldVarificationSearchForm','dealNo','','lbxDealNo', '','','','customerName')" value=" " styleClass="lovbutton"> </html:button>
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
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(220,'fieldVarificationSearchForm','product','userId','scheme','lbxscheme|userId','','clearSchemeValue','productId')" value=" " styleClass="lovbutton"> </html:button>
	        <%-- <img onclick="openLOVCommon(87,'commonForm','product','','scheme','lbxscheme','','','productId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>	         
     		<input type="hidden" id="productId" name="productId"/> 
      
        </td>
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          	   <html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'fieldVarificationSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " styleClass="lovbutton"> </html:button>
               <%-- <img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" />--%>
           	   <input type="hidden" id="schemeId" name="schemeId"/> 				    
             </td>
       </tr>
		<tr> 
        
        
             <td width="13%">
						<bean:message key="lbl.userNam" />
				      </td>
				      <td width="13%">
						<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId"  />
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'fieldVarificationSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>

   					  </td>
            
		</tr> 	
				
		 <tr>
		  	<td align="left">
		 	    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return newSearchInitaition();"><bean:message key="button.search" /></button>
		    </td>
		     
		 </tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 
	</fieldset>

<fieldset>	
 <legend><bean:message key="lbl.fieldVerDetail"/></legend>  

	 <logic:present name="dealdetails">
<logic:notEmpty name="dealdetails">
   <display:table  id="dealdetails"  name="dealdetails" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${dealdetails[0].totalRecordSize}" partialList="true" requestURI="/fieldVarificationBehindAction.do?method=verificationInitCreditProcessing" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userNam" sortable="true"/>
</display:table>
</logic:notEmpty>
<logic:empty name="dealdetails">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
         <td><b><bean:message key="lbl.userNam"/> </b></td>
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
	
</html:form>

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
</body>
</html:html>