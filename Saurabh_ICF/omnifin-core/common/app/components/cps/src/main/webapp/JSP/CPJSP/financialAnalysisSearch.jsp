<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for financial analysis search
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/individualFinancialAnalysis.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	
 <%
			ResourceBundle resource =  ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
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
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();document.getElementById('financialAnalysisSearchForm').dealButton.focus();init_fields();" onclick="parent_disable();">
	 <div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/financialAnalysisSearchBehindAction" method="post" styleId="financialAnalysisSearchForm">
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
		<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  
	  <legend>
	  <bean:message key="lbl.financialAnalysisSearch"/>

	  </legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%">
<!--			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />-->
			<html:text property="dealNo" styleClass="text" value="${vo.dealNo}" styleId="dealNo" maxlength="10" readonly="true"  tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${vo.lbxDealNo}" />
			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(240,'financialAnalysisForm','dealNo','','', '','','','customername')" value=" " styleClass="lovbutton"></html:button>
	  </td>
	  <td width="17%"><bean:message key="lbl.customerName"/></td>
		<td width="28%" ><html:text property="customername" value="${vo.customername}" tabindex="2"  styleClass="text" styleId="customername" maxlength="50"/></td> 
	   
	    </tr>
	    
		<tr> 
		<td><bean:message key="lbl.product"/></td> 
       
        <td> 
      
	        <html:text  property="product" styleId="product" value="${vo.product}" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${vo.lbxProductID}" />
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(87,'financialAnalysisSearchForm','product','userId','scheme','lbxscheme|userId','','','productId');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	       </td>
        <input type="hidden" id="productId" name="productId"/> 
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
              <html:text property="scheme" styleId="scheme" styleClass="text" value="${vo.scheme}" readonly="true" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme"  value="${vo.lbxscheme}"  />
          		 <input type="hidden" id="schemeId" name="schemeId"/> 
          		 <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'financialAnalysisSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');" value=" " styleClass="lovbutton"> </html:button>
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
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'financialAnalysisSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  
   					  </td>
			</tr>    
		 <tr>
		  <td align="left" class="form2" colspan="4">
		   <button type="button" name="search" id="search" class="topformbutton2" accesskey="S" title="Alt+S" onclick="searchFinancialDetail();"><bean:message key="button.search" /></button>
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
		 <legend><bean:message key="lbl.financialAnalysisDetail"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
<!--	<logic:notPresent name="financialDetails">-->
<!--    <td >-->
<!--    </logic:notPresent>-->
<!--    <logic:present name="financialDetails">-->
    <td>
<!--    </logic:present>-->
    
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
				 <td><b><bean:message key="lbl.Type"/></b></td>
			        <td><b><bean:message key="lbl.product"/></b></td>
			       	<td><b><bean:message key="lbl.scheme"/></b></td>
			    <td><b><bean:message key="lbl.userNam"/></b></td>
			    </tr>
			    <tr class="white2">
			    <td colspan="6"> <bean:message key="lbl.noDataFound"/></td>
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
							<display:column property="customerType" titleKey="lbl.Type" sortable="true" />
							<display:column property="product" titleKey="lbl.product" sortable="true" />
							<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
							
							<display:column property="reportingToUserId" titleKey="lbl.userNam" sortable="true" />
							
						</display:table>
				</logic:notEmpty>
				</logic:present>
    	
	</tr>
 </table>    </td>
</tr>
</table>

	</fieldset>

</div>
<!---->
<!--<logic:present name="sms">-->
<!-- <script type="text/javascript">-->
<!--	if('<%=request.getAttribute("sms").toString()%>'=='N')-->
<!--	{-->
<!--		alert("<bean:message key="lbl.noDataFound" />");-->
<!--		-->
<!--	}-->
<!--  </script>-->
<!--</logic:present>-->
</body>
</html:html>