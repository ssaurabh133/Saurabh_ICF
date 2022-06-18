<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
        <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
 		<script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
		  <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/cplead.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>


	
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

<body onload="enableAnchor();diffLead(<%=session.getAttribute("allocationid")%>);document.getElementById('leadButton').focus();">

			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<html:form action="/leadTrackingSearchBehind" styleId="leadTracking" method="post">
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
 <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
<html:hidden property="status" styleId="status" value="L"/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<fieldset>
<legend><bean:message key="lbl.leadtracking"/></legend>
	<table width="100%">
	
	<tr>
      <td width="13%"><bean:message key="lbl.leadno" /></td>
      <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
     <td width="13%"> <html:text property="leadno" styleClass="text" styleId="leadno" maxlength="50" readonly="true" tabindex="-1"/>
     <input type="button" class="lovbutton" id="leadButton" onclick="openLOVCommon(194,'leadTracking','leadno','userId','', 'userId','','','customername')" value=" " tabindex="1" name="dealButton">
     <input type="hidden" name="lbxLeadSearchDetail" id="lbxLeadSearchDetail"  />
     </td>
     <td width="13%"><bean:message key="lbl.customername" /></td>
     <td width="13%"> <html:text property="customername" styleClass="text" styleId="customername" maxlength="50" value="" tabindex="2" />
     <input type="hidden" name="customerDesc" id="customerDesc" />
       </tr>
     <tr>
      	<td width="13%"><bean:message key="lbl.products" /></td>
     	<td width="13%"><html:text property="product" styleId="product" styleClass="text"  readonly="true"  tabindex="-1"></html:text>
     	<input type="button" class="lovbutton" id="dealButton" onclick="openLOVCommon(229,'leadTracking','product','','scheme','lbxscheme','','','lbxProductID')" value=" " tabindex="3" name="dealButton">
		<html:hidden property="lbxProductID" styleId="lbxProductID" styleClass="text" ></html:hidden>
     </td>
     <td width="13%"><bean:message key="lbl.scheme" /><input type="hidden" name="lbxscheme" id="lbxscheme" tabindex="-1"  /></td>
     <td width="13%"> <html:text property="scheme" styleClass="text" styleId="scheme" readonly="true"/>
     <input type="button" class="lovbutton" id="dealButton"	onclick="openLOVCommon(22,'leadTracking','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')" value=" " tabindex="4" name="dealButton">
         <input type="hidden" id="schemeId" name="schemeId"/> 
	</td>
       </tr>
       <tr>
              	<td width="13%"><bean:message key="lbl.leadSource" /></td>
       	<td width="13%">
       	<html:select styleId="leadGenerator" property="leadGenerator" tabindex="5" styleClass="text" value="${leadRMDetails[0].leadGenerator}" onchange="return leadempty(value);">
						
						<html:option value="">---Select---</html:option>
							<html:option value="RM">
								RM / SALES EXEC
							</html:option>
							<html:option value="VENDOR">
								DEALER
							</html:option>
							<html:option value="BRANCH">
								TELECALLER / BRANCH
							</html:option>
							<html:option value="OTHERS">
								OTHERS
							</html:option>
							<html:optionsCollection name="sourceList" label="name" value="id" />
						</html:select></td>
						
						<td width="13%"><bean:message key="lbl.gendate" /></td>
     <td ><html:text property="applicationdate" tabindex="6"  styleClass="text" styleId="applicationdate" maxlength="10" onchange="return checkDate('applicationdate');"/></td>
<!--      <td width="13%"><bean:message key="lbl.rmname" /></td>-->
<!--     <td width="13%"> <html:text property="rmname" styleId="rmname" styleClass="text" tabindex="5" >-->
<!--		      </html:text>-->
<!--     </td>-->
<!--        <td width="13%"><bean:message key="lbl.branch" /></td>-->
<!--     <td width="13%"><html:text property="branch" styleClass="text" styleId="branch" readonly="true" />-->
<!--     <input type="button" class="lovbutton" id="lbxBranch" onclick="openLOVCommon(156,'leadCapturing','branch','','', '','','','lbxBranchId')" value=" " tabindex="6" name="dealButton">-->
<!--     <input type="hidden" name="lbxBranchId" id="lbxBranchId" value=""  />-->
<!--     </td>-->
       </tr>
<!--       <tr>-->
<!---->
<!--        <td width="13%"><bean:message key="lbl.vendor" /></td>-->
<!--     <td width="13%"> <html:text property="vendor" styleId="vendor" styleClass="text" tabindex="7" >-->
<!--		      </html:text>-->
<!--      </td>-->
<!--      <td width="13%"><bean:message key="lbl.gendate" /></td>-->
<!--     <td ><html:text property="applicationdate" tabindex="8"  styleClass="text" styleId="applicationdate" maxlength="10" onchange="return checkDate('applicationdate');"/></td>-->
<!---->
<!--       </tr>-->
  <!--  Amit Ruhela start here -->
				<tr>
					<td><bean:message key="lbl.userNam" /></td>
      				<td>
      				
      				<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId"  />
					  	<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
					  	<html:hidden property="lbxUserId" styleId="lbxUserId"  value=""/>
					  	<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(266,'leadTracking','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"></html:button>
   					  
			    	 </td>
				</tr>
<!--  Amit Ruhela end here -->
	 <tr>
	 <td>	
	    <button type="button" name="Search" title="Alt+S" accesskey="S" id="Search" class="topformbutton2" onclick="return leadTrack()"  tabindex="7"><bean:message key="button.search" /></button> 	 
	</tr>
	 </table>
</fieldset>

</html:form>
<div>

 <fieldset>
		 <legend><bean:message key="lbl.details" /></legend> 
          <table width="100%" border="0" cellpadding="0" cellspacing="1" >
            <tr>
             <td class="gridtd">
                       <table width="100%" border="0" cellpadding="4" cellspacing="1">
                             <tr>
                             <logic:notPresent name="list">
 			                      <td width="220" class="white2" style="width:220px;"><strong><bean:message key="lbl.leadno"/></strong></td>
        	                       <td width="220" class="white2" style="width:220px;"><strong><bean:message key="lbl.gendate"/></strong></td>
			                       <td width="220" class="white2" style="width:250px;"><b><bean:message key="lbl.customername"/><br></b></td>
       		                       <td width="220" class="white2" style="width:250px;"><b><bean:message key="lbl.products"/><br></b></td>
      		                        <td width="220" class="white2" style="width:220px;"><strong><bean:message key="lbl.source"/></strong></td>

<!--  Amit Ruhela did here -->
      	                             <td width="220" class="white2" style="width:220px;"><strong><bean:message key="lbl.userNam"/></strong></td> 
      	                             <td width="220" class="white2" style="width:220px;"><strong><bean:message key="lbl.followupdateForTrack"/></strong></td>  
      	                             <td width="220" class="white2" style="width:220px;"><strong><bean:message key="lbl.leadTrackStatus"/></strong></td>   
      	                             <tr class="white2" >
	    <td colspan="8"><bean:message key="lbl.noDataFound" /></td>
	</tr>
      	                             </logic:notPresent>    	                             
      	                     </tr>
      	                 
                         </table>
                   </td></tr></table>

  

 <logic:present name="list">
 <logic:notEmpty name="list">

  	<display:table id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/leadTrackingSearchBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="leadno" titleKey="lbl.leadno"  sortable="true"  />
	<display:column property="applicationdate" titleKey="lbl.gendate"  sortable="true"  />
	<display:column property="customername" titleKey="lbl.customername"  sortable="true"  />
	<display:column property="product" titleKey="lbl.products"  sortable="true"  />
	<display:column property="leadGenerator" titleKey="lbl.source" sortable="true" />
	<display:column property="reportingToUserId" titleKey="lbl.userNam" sortable="true" />
	<display:column property="loanType" titleKey="lbl.loanType" sortable="true" />
	<display:column property="followupDate" titleKey="lbl.followupdateForTrack" sortable="true" />
	<display:column property="status" titleKey="lbl.leadTrackStatus" sortable="true" />
	
	
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
        <td><b><bean:message key="lbl.loanType"/> </b></td>
        <td><b><bean:message key="lbl.followupdateForTrack"/> </b></td>
        <td><b><bean:message key="lbl.leadTrackStatus"/> </b></td>
	
	</tr>
<tr class="white2" >
	    <td colspan="8">o<bean:message key="lbl.noDataFound" /></td>
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