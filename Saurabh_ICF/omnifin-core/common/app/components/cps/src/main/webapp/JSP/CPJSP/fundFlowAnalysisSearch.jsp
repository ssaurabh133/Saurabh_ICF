<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for fundflow analysis search
 	Documentation    :- 
 -->
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fundFlowAnalysis.js"></script>
			
	  <%
	  		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

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
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();document.getElementById('fundFlowAnalysisSearchForm').dealButton.focus();init_fields();" onclick="parent_disable();">

	 <div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/fundFlowAnalysisSearch" method="post" styleId="fundFlowAnalysisSearchForm">
	<input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
		<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	<fieldset>	  
	  <legend>
	   <logic:present name="fundFlowAuthor">
	   		<bean:message key="lbl.fundFlowAnalysisAuthorSearch"/>
	   </logic:present>
	   <logic:notPresent name="fundFlowAuthor">
	   		<bean:message key="lbl.fundFlowAnalysisMakerSearch"/>
	   </logic:notPresent>
	  </legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		<td width="20%"><bean:message key="lbl.dealNo"/></td>
		<td width="35%">
			<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
			<% session.setAttribute("fundRecStatus","P"); %>
			<% session.setAttribute("fundRecStatus1","F"); %>
			<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true"  tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
			<logic:present name="fundFlowAuthor">
			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(216,'fundFlowAnalysisSearchForm','dealNo','','', '','','','customername')" value=" " styleClass="lovbutton"></html:button>
			</logic:present>
			<logic:notPresent name="fundFlowAuthor">
			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(213,'fundFlowAnalysisSearchForm','dealNo','','', '','','','customername')" value=" " styleClass="lovbutton"></html:button>
			</logic:notPresent>
	  </td>
	  <td width="17%"><bean:message key="lbl.customerName"/></td>
		<td width="28%" ><html:text property="customername" tabindex="2" styleClass="text" styleId="customername" value="" maxlength="50"/></td> 
		
	    </tr>
	    
		<tr> 
		<td><bean:message key="lbl.product"/></td> 
       
        <td> 
      
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1"/>
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />	        	       
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(302,'fundFlowAnalysisSearchForm','product','userId','scheme','lbxscheme|userId','','','product');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	      
	      </td>
       
        
        <td width="8%"><bean:message key="lbl.scheme"/></td>
            <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme"  />          		 
          		 <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(303,'fundFlowAnalysisSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','scheme');closeLovCallonLov('product');" value=" " styleClass="lovbutton"> </html:button>
                
                 </td>
<!--               changed here-->
		</tr> 	
		<tr>
		<td width="13%">
						<bean:message key="lbl.userNam" />
				      </td>
				      <td width="13%">
						<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId"  />
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'fundFlowAnalysisSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  
   					  </td>
		</tr>
			    
		 <tr>
		  <td align="left" class="form2" colspan="4">
		   <button type="button" name="search" id="search" class="topformbutton2" accesskey="S" title="Alt+S" onclick="return searchFundFlowDetail();"><bean:message key="button.search" /></button>
		   <logic:notPresent name="fundFlowAuthor">
		  	<!-- 	<button type="button" name="new" class="topformbutton2" id="new" accesskey="N" title="Alt+N" onclick="return newfundFlow();"><bean:message key="button.new" /></button> 
		  -->
		  </logic:notPresent>
	      
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
		 <legend><bean:message key="lbl.fundFlowAnalysisDetail"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
<!--	<logic:notPresent name="fundFlowdetails">-->
<!--    <td >-->
<!--    </logic:notPresent>-->
<!--    <logic:present name="fundFlowdetails">-->
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
				
			        <td><b><bean:message key="lbl.product"/></b></td>
			       	<td><b><bean:message key="lbl.scheme"/></b></td>
			       	<td><b><bean:message key="lbl.userNam"/></b></td>
			    </tr>
				<tr class="white2" >
	        <td colspan="5"><bean:message key="lbl.noDataFound" /></td> 
	        </tr>
			 </table>    </td>
			</tr>
			</table>
    	  	</logic:notPresent>	
    
    	 
    	  <logic:present name="list">
    	  <logic:notPresent name="fromSearchPage">
				 <logic:notEmpty name="list">
					<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/fundFlowAnalysisSearchBehind.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="dealNo" titleKey="lbl.dealNo" sortable="true" />
						<display:column property="customername" titleKey="lbl.customerName" sortable="true" />
						<display:column property="product" titleKey="lbl.product" sortable="true" />
						<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
						<display:column property="reportingToUserId" titleKey="lbl.userNam" sortable="true" />
					</display:table>
				</logic:notEmpty>
		
		
				</logic:notPresent>	
	</logic:present>
	
	<logic:present name="fromSearchPage">
				 <logic:notEmpty name="list">
					<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/fundFlowAnalysisSearchAction.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="dealNo" titleKey="lbl.dealNo" sortable="true" />
						<display:column property="customername" titleKey="lbl.customerName" sortable="true" />
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


</body>
</html:html>