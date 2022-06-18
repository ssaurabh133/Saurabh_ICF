<!--  
Author Name:- Abhishek Mathur
Date of Creation:- 14/10/2015
Purpose:- Rejected deals would be available at deal capturing stage for re-processing 
-->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<%@page import="com.cp.vo.ReprocessingDealVo"%>
<%@page import="com.login.roleManager.UserObject"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();
%>

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
		  <!-- css and jquery for tooltip -->
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
			<link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
	     <!-- css and jquery for tooltip -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		
     	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
    	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/reprocessingDealCapturing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/rmScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	
	
	<script type="text/javascript">

		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}
   
	</script>
	
			<logic:present name="css">
				<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
			</logic:present>
		   <logic:notPresent name="css">
			    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		   </logic:notPresent>
		
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

<body oncontextmenu="return false" onload="enableAnchor();checkChanges('reprocessingDealCapturingAction');document.getElementById('reprocessingDealCapturingAction').decision.focus();init_fields();">
	<input type="hidden" name="<%= org.apache.struts.taglib.html.Constants.TOKEN_KEY %>" value="<%= session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>" >

     	
	<html:form action="/reprocessingDealCapturingAction" method="post" styleId="reprocessingDealCapturingAction">
	
	<fieldset>
		 
		<logic:equal value="8000309" name="FunctionId">
		 <legend><bean:message key="lbl.reprocessingDealMaker" /></legend>
		 </logic:equal>
		 
		 <logic:notEqual  name="FunctionId" value="8000309">
		 <legend><bean:message key="lbl.reprocessingDealAuthor" /></legend>
		 </logic:notEqual>
		 
		 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		 <input type="hidden" id="businessDate" value="${requestScope.businessDate}" />
		 <input type="hidden" id="makerDate" value="${requestScope.makerDate}" />
		 <input type="hidden" id="checkFlag" value="${requestScope.checkFlag}" />
		 
		 <table width="100%" border="0" cellspacing="1" cellpadding="4">
		 
		 <tr>
			<td width="20%"><bean:message key="lbl.dealNo"/></td>
			<td width="35%" valign="top">
				
				<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
				
				<logic:equal value="8000309" name="FunctionId">
				<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" value="${rejectedDealList[0].dealNo1}" readonly="true" tabindex="-1" />
				</logic:equal>
				<logic:notEqual  name="FunctionId" value="8000309">
				<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" value="${rejectedDealListAuthor[0].dealId}" readonly="true" tabindex="-1" />
				</logic:notEqual>
				
				<html:hidden  property="lbxDealNo" styleId="lbxDealNo" />
				
				<logic:equal value="8000309" name="FunctionId">
				<html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(18003,'reprocessingDealCapturingAction','dealNo','userId','dealNo', 'userId','','','customername');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	           </logic:equal>
	           
	            <logic:notEqual  name="FunctionId" value="8000309">
	            <html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(18004,'reprocessingDealCapturingAction','dealNo','userId','dealNo', 'userId','','','customername');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
	            </logic:notEqual>
	           
	  		</td>
	  		
		  	<td><bean:message key="lbl.customerName"/></td>
		  	<logic:equal value="8000309" name="FunctionId">
			<td width="13%" ><html:text property="customername" styleClass="text" value="${rejectedDealList[0].customername }" styleId="customername" readonly="true" maxlength="50" onchange="upperMe('customerName'); "/></td>
			</logic:equal>
			<logic:notEqual  name="FunctionId" value="8000309">
			<td width="13%" ><html:text property="customername" styleClass="text" value="${rejectedDealListAuthor[0].customername }" styleId="customername" readonly="true" maxlength="50" onchange="upperMe('customerName');"/></td> 
			</logic:notEqual>
			<td width="15%" ></td>
	   
	    </tr>
	    <tr>
	    <td>
	    	<logic:equal value="8000309" name="FunctionId">
    		<button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="RejectedDealSearchForMaker();" ><bean:message key="button.search" /></button>
   			</logic:equal>
   			
   			 <logic:notEqual  name="FunctionId" value="8000309">
   			 <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="RejectedDealSearchAuthor();" ><bean:message key="button.search" /></button>
   			 </logic:notEqual>
   		</td>
	    </tr>
	    
		 </table>
	
	</fieldset>
	
	<!-- Code For Maker -->
	
	
			<logic:present name="rejectedDealList">
			<fieldset>
						<legend><bean:message key="ldl.dealDetails" /></legend>	
						<logic:notEmpty name="rejectedDealList">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td>
						<display:table  id="rejectedDealList"  name="rejectedDealList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${rejectedDealList[0].totalRecordSize}" requestURI="/reprocessingDealCapturingAction.do?method=rejectedDealSearch" >
						<display:setProperty name="paging.banner.placement"  value="bottom"/>
		     			<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		     			<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		     			
		     			<display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
			 			<display:column property="customername" titleKey="lbl.customerName"  sortable="true"  />
			 			<display:column property="userName" titleKey="lbl.maker"  sortable="true"  />
						</display:table>
						</td>
						</tr>
						</table>
						<tr>
							<%-- <td><bean:message key="lbl.decision"/></td>
							 <td >
				   				<html:select property="decision" onchange="" styleId="decision" styleClass="text" onchange="hideAsterik(value)" >
				     			 <html:option value="">Select</html:option> 
				     			<html:option value="X">Reject</html:option>
		             			<html:option value="P">Resend</html:option>
		                  		</html:select>
				     		</td> --%>
				     		<td>
				     			<button type="button" name="save"  class="topformbutton3" value="Save" onclick="return saveAndForward();" id="save" accesskey="V" title="Alt+V" ><bean:message key="button.save&forward" /></button>
				     		</td>
						</tr>
						</logic:notEmpty>
						
						<logic:empty name="rejectedDealList">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
		 						<td class="gridtd">
		 						<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 						<tr class="white2" align="center">
		 						<td ><b><bean:message key="lbl.dealNo" /></b></td>
		  						<td ><b><bean:message key="lbl.customerName" /></b></td>
		  						<td ><b><bean:message key="lbl.maker" /></b></td>
		 						</tr>
		 					
		 						<tr class="white2">
								<td colspan="8"><bean:message key="lbl.noDataFound" /></td>
								</tr>
								<tr>
								</table>
								
						</tr>
		 						
		 						</td>
		 						</tr>
							</table>
							
						</logic:empty>
					</fieldset>
			</logic:present>
	
	<!-- End Maker -->
	
	<!-- Code For Author -->
	
	
			<logic:present name="rejectedDealListAuthor">
			<fieldset>
						<legend><bean:message key="ldl.dealDetails" /></legend>	
						<logic:notEmpty name="rejectedDealListAuthor">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td>
						<display:table  id="rejectedDealListAuthor"  name="rejectedDealListAuthor" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${rejectedDealListAuthor[0].totalRecordSize}" requestURI="/reprocessingDealCapturingAction.do?method=rejectedDealSearchAuthor" >
						<display:setProperty name="paging.banner.placement"  value="bottom"/>
		     			<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		     			<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
		     			
		     			<display:column property="dealNo" titleKey="lbl.dealNo"  sortable="true"  />
			 			<display:column property="customername" titleKey="lbl.customerName"  sortable="true"  />
			 			<display:column property="userName" titleKey="lbl.maker"  sortable="true"  />
						</display:table>
						</td>
						</tr>
						</table>
						<tr>
							<td><bean:message key="lbl.decision"/></td>
							 <td >
				   				<html:select property="decision" onchange="" styleId="decision" styleClass="text" onchange="hideAsterik(value)" >
				     			 <html:option value="">Select</html:option>
				     			 <html:option value="P">Send Back</html:option> 
				     			 <html:option value="X">Reject</html:option>
		             			 <html:option value="A">Approve</html:option>
		                  		</html:select>
				     		</td>
				     		<td>
				     			<button type="button" name="save"  class="topformbutton2" value="Save" onclick="return savedecision(decision);" id="save" accesskey="V" title="Alt+V" ><bean:message key="button.save" /></button>
				     		</td>
						</tr>
						</logic:notEmpty>
						
						<logic:empty name="rejectedDealListAuthor">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
		 						<td class="gridtd">
		 						<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 						<tr class="white2" align="center">
		 						<td ><b><bean:message key="lbl.dealNo" /></b></td>
		  						<td ><b><bean:message key="lbl.customerName" /></b></td>
		  						<td ><b><bean:message key="lbl.maker" /></b></td>
		 						</tr>
		 					
		 						<tr class="white2">
								<td colspan="8"><bean:message key="lbl.noDataFound" /></td>
								</tr>
								<tr>
								</table>
								
						</tr>
		 						
		 						</td>
		 						</tr>
							</table>
							
						</logic:empty>
					</fieldset>
			</logic:present>
			
	
	<!-- End Author -->
</html:form>


<logic:present name="msg">
 <script type="text/javascript">
 if('<%=request.getAttribute("msg")%>'=='dealForward')
	{
	 alert('<bean:message key="msg.DealForward" />');
	 location="<%=request.getContextPath()%>/dealReprocessingBehindAction.do";
		
	}
 
 else if('<%=request.getAttribute("msg")%>'=='dataNotSaved')
	{
	 alert('<bean:message key="msg.dealNotSave" />');
	 location="<%=request.getContextPath()%>/dealReprocessingBehindAction.do";
	}
 else if('<%=request.getAttribute("msg")%>'=='datasaved')
	{
	 alert('<bean:message key="msg.decisiondealSave" />');
	 location="<%=request.getContextPath()%>/dealReprocessingBehindAction.do";
	}
 else if('<%=request.getAttribute("msg")%>'=='dataNotSaved')
	{
	 alert('<bean:message key="msg.dealNotSave" />');
	 location="<%=request.getContextPath()%>/dealReprocessingBehindAction.do";
	}
 else if('<%=request.getAttribute("msg")%>'=='rejectedDealList')
	{
	 alert('<bean:message key="msg.nodatafoundforsearch" />');
	 location="<%=request.getContextPath()%>/dealReprocessingBehindAction.do";
	}
 else if('<%=request.getAttribute("msg")%>'=='showprocmsg')
	 {
	 alert("<%=request.getAttribute("sms1")%>");
	 location="<%=request.getContextPath()%>/dealReprocessingBehindAction.do";
	 }
 
 
 
</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</body>

</html:html>