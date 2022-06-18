<%--
      Author Name-      Ankit Agarwal	
      Date of creation -06/09/2011
      Purpose-        Trade Market Check info
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       Date currentDate = new Date();

%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
	   	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/tradeCheck.js"></script>

	
	</head>
	

	<body onclick="parent_disable();" onload="enableAnchor();init_fields();">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	


<div id=centercolumn>
<div id=revisedcontainer>
<html:form  action="/tradeMarketDetails" method="post" styleId="tradeMarketForm">
 
  <fieldset>
<legend><bean:message key="lbl.marketCheck"/></legend>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
  <tbody>
  <tr>
    <td>
      <table cellspacing=1 cellpadding=2 width="100%" border=0>
                  <html:hidden property="tradeCheckId" styleId="tradeCheckId" value="${list[0].tradeCheckId}"/>
          <tr>
                       <td ><bean:message key="lbl.refrenceNo" /><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="refrenceNO" readonly="true" styleId="refrenceNO" tabindex="-1" value="${list[0].refrenceNO}" maxlength="20"/></td>
                       <td><bean:message key="lbl.tradeAppraiserName"/><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="appraiser" readonly="true" styleId="appraiser" tabindex="-1" value="${list[0].appraiser}" maxlength="50"/></td>
                    </tr>
                 <tr>
                   <td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
                   <td><html:text property="viewAppraisalDate" styleClass="text" styleId="viewAppraisalDate" tabindex="-1" maxlength="10" tabindex="-1" value="${list[0].appraisalDate}" readonly="true" /></td>
					<td><bean:message key="lbl.reportDate" /></td>
					<td><html:text property="reportDate" styleId="reportDate" maxlength="10" styleClass="text" tabindex="-1" value="${userobject.businessdate }" disabled="true" tabindex="-1"/>
        	  			 <html:hidden property="reportDate" styleClass="text" styleId="reportDate" value="${userobject.businessdate }" />
        	   </td>
                  </tr>
           <tr>
                 	<td><bean:message key="lbl.verificationMode" /></td>
						 <td>
							 <html:select property="verificationMode" styleId="verificationMode" styleClass="text" tabindex="-1" value="${list[0].verificationMode}" disabled="true">
								    <html:option value="F">Field Visit</html:option>
									<html:option value="P">Phone</html:option>
									<html:option value="W">Website</html:option>
							</html:select> 
						</td>
				  <td><bean:message key="lbl.appRemark"/><font color="red">*</font> </td>
       <td><html:textarea property="textarea" styleId="textarea" styleClass="text"  cols="70" rows="1" tabindex="-1" value="${list[0].textarea}" readonly="true"></html:textarea></td>		
        </tr>
       
      </table>
     </td>
     </tr>
     </tbody>
     </table>    
   </fieldset>
   </html:form>
</div>
</div>
</body>
</html:html>
