 
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
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/underWriter.js"></script>
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/masterScript/irrCalculation.js"></script>
		
	  <%
			ResourceBundle resource =  ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
			
			com.login.roleManager.UserObject userobj=(com.login.roleManager.UserObject)session.getAttribute("userobject");
		
		if(userobj!=null){
			
			session.setAttribute("userId", userobj.getUserId());
		}
		%>
		
<!--[if IE 8]>
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
	<body onload="enableAnchor();" oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();"  >
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/irrCalculation" method="post" styleId="irrCalculationSearchForm">
	<input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
	
	<fieldset>	  
	  <legend>
	  <bean:message key="lbl.irrCalculationSearch"/>

	  </legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr> 
		<td><bean:message key="lbl.product"/></td> 
       
        <td> 
          
	        <html:text  property="product" styleId="product" styleClass="text" readonly="true"  tabindex="-1" value = "" />
	        <html:hidden  property="lbxProductID" styleId="lbxProductID" />
	        <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(6003,'irrCalculationSearchForm','product','','','','','','productId')" value=" " styleClass="lovbutton"> </html:button>
	        <%-- <img onclick="openLOVCommon(87,'commonForm','product','','scheme','lbxscheme','','','productId')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%>	         
     		<input type="hidden" id="productId" name="productId"/>
	       </td>
        
        
      <td width="8%"><bean:message key="lbl.scheme"/></td>
            
		     <td width="20%">
            
               <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" tabindex="-1" value = ""/>
          	   <html:hidden  property="lbxscheme" styleId="lbxscheme"  />
          	   <html:button property="productButton" styleId="productButton" onclick="openLOVCommon(22,'irrCalculationSearchForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " styleClass="lovbutton"> </html:button>
               <%-- <img onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" src="<%= request.getContextPath()%>/images/lov.gif" />--%>
           	   <input type="hidden" id="schemeId" name="schemeId"/> 	 
           	   		    
             </td>
		</tr> 
		
		
	    
		<%-- 	
			    <tr>
			    <td width="13%">
						<bean:message key="lbl.userNam" />
				      </td>
				      <td width="13%">
						<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId"  />
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'queryResponseSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  
   					  </td>
			    </tr>
		 --%>
		 <tr>
		    <td align="left" class="form2" colspan="4">
		    <button type="button" name="searchButton" id="searchButton" class="topformbutton2" accesskey="S" title="Alt+S" onclick="searchIrrCal();"><bean:message key="button.search" /></button>
		    <button id="add" class="topformbutton2" tabindex="8" onclick="return newAddIrrCal1()" accesskey="A" title="Alt+A" name="add" type="button"><bean:message key="button.add" /></button>
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
		 <legend><bean:message key="lbl.irrCalSearchDetails"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>

    <td>

    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
  
	<tr>
    	  <logic:present name="list">
    	<logic:empty name="list">
    	  			
    	  	 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
			    <td class="gridtd">
			    
			    <table width="100%" border="0" cellspacing="1" cellpadding="1">
			   
			    <tr class="white2">
				
			        				
			        <td><b><bean:message key="lbl.product"/></b></td>
			       	<td><b><bean:message key="lbl.scheme"/></b></td>
			       	<td><b><bean:message key="lbl.irrType"/></b></td>
			       	<td><b><bean:message key="lbl.irrChargeCode"/></b></td>
			     </tr>
			     <tr class="white2" >
	    <td colspan="6"><bean:message key="lbl.noDataFound" /></td>
	</tr>
	
				
			 </table>
			 </td>    
			</tr>
			</table>
			</logic:empty>
    	  </logic:present>
 
    	
    	  		 <logic:present name="list">
						<logic:notEmpty name="list">
						<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/irrCalculation.do?method=irrCalculationSearch"> 
							<display:setProperty name="paging.banner.placement" value="bottom" />
							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							
							<display:column property="irrChargeCodeModify" titleKey="lbl.irrID" sortable="true" />
							<display:column property="product" titleKey="lbl.product" sortable="true" />
							<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
							<display:column property="irrType" titleKey="lbl.irrType" sortable="true" />
							<display:column property="irrChargeCode" titleKey="lbl.irrChargeCode" sortable="true" />
							
							
						</display:table>
				</logic:notEmpty>
				</logic:present>
    	
	</tr>
 </table> 
 </td>   
</tr>
</table>

</fieldset>

</div>

</body>
</html:html>