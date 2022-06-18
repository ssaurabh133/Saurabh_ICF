<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 10 jan 2012
 	Purpose          :- To provide user interface for individual financial analysis search
 	Documentation    :- 
 -->
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
		<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>	
		<script type="text/javascript" src="js/cpScript/individualFinancialAnalysis.js"></script>
		<%
			ResourceBundle resource =  ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
		%>
	</head>
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();document.getElementById('individualFinancialAnalysisSearchForm').dealButton.focus();init_fields();" onclick="parent_disable();">
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/individualFinancialSearchBehindAction" method="post" styleId="individualFinancialAnalysisSearchForm">
	
	<fieldset>	  
	  <legend>
	  <bean:message key="lbl.individualFinancialAnalysisSearch"/>

	  </legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%">
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true"  tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(240,'individualFinancialAnalysisSearchForm','dealNo','','', '','','','customername'); return false;" value=" " styleClass="lovbutton"></html:button>
	  </td>
	  <td width="17%"><bean:message key="lbl.customerName"/></td>
		<td width="28%" ><html:text property="customername" tabindex="2"  styleClass="text" styleId="customername" maxlength="50"/></td> 
	   
	    </tr>
	    
		<tr> 
		<td><bean:message key="lbl.product"/></td> 
       
        <td> 
      
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(87,'individualFinancialAnalysisSearchForm','product','userId','scheme','lbxscheme|userId','','','productId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	       </td>
        <input type="hidden" id="productId" name="productId"/> 
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
              <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          		
          		 <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'individualFinancialAnalysisSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');" value=" " styleClass="lovbutton"> </html:button>
                </td>
                <input type="hidden" id="schemeId" name="schemeId"/> 
        <td ></td>
		</tr> 	
			    
		 <tr>
		  <td align="left" class="form2" colspan="4">
		   <button type="button" name="search" id="search" class="topformbutton2" accesskey="S" title="Alt+S" onclick="return searchIndividualFinancialDetail();"><bean:message key="button.search" /></button>
		  </td>
		  </tr>
		</table>
		
	      </td>
	</tr>
	</td>
	
	</table>
	 
	</fieldset>
	
	</html:form>
	</div>

<fieldset>	
		 <legend><bean:message key="lbl.individualFinancialAnalysisDetail"/></legend>  

  
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
			    </tr>
				
			 </table>    </td>
			</tr>
			</table>
    	  </logic:notPresent>
    	
    	  		 <logic:present name="list">
						<logic:notEmpty name="list">
						<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/financialAnalysisSearchBehindAction.do"> 
							<display:setProperty name="paging.banner.placement" value="bottom" />
							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:column property="dealNo" titleKey="lbl.dealNo" sortable="true" />
							<display:column property="customername" titleKey="lbl.customerName" sortable="true" />
							<display:column property="product" titleKey="lbl.product" sortable="true" />
							<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
							
						</display:table>
				</logic:notEmpty>
				</logic:present>
    	
	</tr>
 </table>    </td>
</tr>
</table>

	</fieldset>

</div>

<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
  </script>
</logic:present>
</body>
</html:html>