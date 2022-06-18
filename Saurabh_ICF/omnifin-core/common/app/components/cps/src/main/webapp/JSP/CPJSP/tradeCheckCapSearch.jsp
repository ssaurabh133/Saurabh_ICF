<%--
      Author Name-  Ankit Agarwal	
      Date of creation -5/09/2011
      Purpose-   Trade Check Capturing Info       
      
 --%>

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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/tradeCheck.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>

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
<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
	</head>
	<body  onload="enableAnchor();document.getElementById('tradeCheckCapForm').dealButton.focus();init_fields();">
				<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />

	<div id="centercolumn">
	<div id="revisedcontainer">

<html:form action="/tradeCheckCapSearch" method="post" styleId="tradeCheckCapForm">

	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  
	  <legend><bean:message key="lbl.tradeCheckSearch"/></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		 <table width="100%" border="0" cellspacing="1" cellpadding="1">	
		<tr>
		<td width="20%"><bean:message key="lbl.tradDealNo"/></td>
		<td width="35%" valign="top">
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" value="${searchParams[0].dealNo}" tabindex="-1" />
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${searchParams[0].lbxDealNo}"/>
			<html:button property="dealButton" styleId="dealButton" onclick="openLOVCommon(162,'tradeCheckCapForm','dealNo','','', '','','','customername')" value=" " styleClass="lovbutton"> </html:button> 	  
       </td>
	    <td><bean:message key="lbl.tradCustName"/></td>
		<td width="13%" ><html:text property="customername" styleClass="text" styleId="customername" onblur="fnChangeCase('tradeCheckCapForm','customername')" value="${searchParams[0].customername}" maxlength="50" onchange="upperMe('customerName');"/></td> 
		</tr>
	 
	 <tr>
		<td><bean:message key="lbl.initiationDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
		<td><html:text property="applicationdate" styleClass="text" styleId="applicationdate" maxlength="10" onchange="return checkDate('applicationdate');" value="${searchParams[0].applicationdate}"/>
		</td> 
		<td width="20%"><bean:message key="lbl.product"/></td> 
      	 <td width="20%">
      		<html:text  property="product" styleId="product" styleClass="text" readonly="true" tabindex="-1" value="${searchParams[0].product}"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${searchParams[0].lbxProductID}"/>
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(214,'tradeCheckCapForm','product','','scheme','lbxscheme','','','productId')" value=" " styleClass="lovbutton"> </html:button>	         
     		<input type="hidden" id="productId" name="productId" /> 
        </td>
       </tr>
		
		<tr> 
            <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1" value="${searchParams[0].scheme}"/>
          	   <html:hidden  property="lbxscheme" styleId="lbxscheme" value="${searchParams[0].lbxscheme}" />
          	   <html:button property="productButton" styleId="schemeButton" onclick="openLOVCommon(22,'tradeCheckCapForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " styleClass="lovbutton"> </html:button>
           	   <input type="hidden" id="schemeId" name="schemeId"/> 				    
             </td>
             <td width="13%">
						<bean:message key="lbl.userNam" />
				      </td>
				      <td width="13%">
						<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId"  />
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'tradeCheckCapForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  
   					  </td>
		</tr> 	
				
		 <tr>
		  	<td align="left">
		    <button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return newTradeCheckCapSearch('F');"><bean:message key="button.search" /></button>
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
	 <legend><bean:message key="lbl.tradeDetail"/></legend>  

 <logic:present name="list">
<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/tradeCheckCapturingBehindAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="lbxDealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="customername" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="applicationdate" titleKey="lbl.initiationDate"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
	<display:column property="reportingToUserId" titleKey="lbl.userNam"  sortable="true"  />
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
        <td><b><bean:message key="lbl.initiationDate"/></b></td>     
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/> </b></td>
        <td><b><bean:message key="lbl.userNam"/> </b></td>
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
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
</body>
</html:html>