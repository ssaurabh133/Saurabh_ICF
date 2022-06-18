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

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
	   	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/tradeCheck.js"></script>

     
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
	

	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('tradeMarketForm').textarea.focus();init_fields();">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>

		<%
		
	String tradeCheck=request.getParameter("tradeCheck");
	request.setAttribute("tradeCheck",tradeCheck);
	
	String dealNo=request.getParameter("dealNo");
	request.setAttribute("dealNo",dealNo);
	
	String verId=request.getParameter("verId");
	request.setAttribute("verId",verId);

	String entType=request.getParameter("entType");
	request.setAttribute("entType",entType);
	
	String entID=request.getParameter("entID");
	request.setAttribute("entID",entID);
 %>


<div id=centercolumn>
<div id=revisedcontainer>
<html:form  action="/tradeMarketDetails" method="post" styleId="tradeMarketForm">
<html:javascript formName="TradeCheckCapMarketDynaValidatorForm"/>
<html:hidden property="tradeCheck1" styleId="tradeCheck1" styleClass="text" value="M" />
<html:hidden property="dealNo1" styleId="dealNo1" styleClass="text" value="${dealNo}" />
<html:hidden property="verId" styleId="verId" styleClass="text" value="${verId}" />
<html:hidden property="entityType1" styleId="entityType1" styleClass="text" value="${entType}" />
<html:hidden property="entityId1" styleId="entityId1" styleClass="text" value="${entID}" />
 
  <fieldset>
<legend><bean:message key="lbl.marketCheck"/></legend>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
  <tbody>
  <tr>
    <td>
      <table cellspacing=1 cellpadding=2 width="100%" border=0>
      <logic:notPresent name="list">
       <tr>
                       <td ><bean:message key="lbl.refrenceNo" /></td>
                       <td ><html:text styleClass="text" property="refrenceNO" styleId="refrenceNO" value="" onblur="fnChangeCase('tradeMarketForm','refrenceNO')" maxlength="20"/></td>
                       <td><bean:message key="lbl.tradeAppraiserName"/><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="appraiser" styleId="appraiser" value="" onblur="fnChangeCase('tradeMarketForm','appraiser')" maxlength="50"/></td>
                    </tr>
                 <tr>
                   <td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
                    <td><html:text property="appraisalDate" styleId="appraisalDate" styleClass="text" readonly="true" onchange="validateLeadDateApp();checkDateApp();" /></td>
          			<td><bean:message key="lbl.reportDate" /></td>
					<td><html:text property="reportDate" styleId="reportDate" maxlength="10" styleClass="text" value="${userobject.businessdate }" disabled="true" tabindex="-1"/>
        	  			 <html:hidden property="reportDate" styleClass="text" styleId="reportDate" value="${userobject.businessdate }" />
        	   </td>
                  </tr>
           <tr>
                 	<td><bean:message key="lbl.verificationMode" /></td>
						 <td>
							 <html:select property="verificationMode" styleId="verificationMode" styleClass="text">
								    <option value="F">Field Visit</option>
									<option value="P">Phone</option>
									<option value="W">Website</option>
							</html:select> 
						</td>
       
        <td><bean:message key="lbl.appRemark"/><font color="red">*</font> </td>
       <td><html:textarea property="textarea" styleId="textarea" styleClass="text" cols="70" rows="1" onblur="fnChangeCase('tradeMarketForm','textarea')" value="" ></html:textarea></td>
        </tr>   
        <tr>
            <td><button type="button" name="marketButton" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveMarketDetails('${dealNo}')"><bean:message key="button.save" /></button> </td>
        </tr> 
        </logic:notPresent> 
        
               <!-- TradeCheck for modify screen -->
         <logic:present name="list">
         <html:hidden property="tradeCheckId" styleId="tradeCheckId" value="${list[0].tradeCheckId}"/>
          <tr>
                       <td ><bean:message key="lbl.refrenceNo" /></td>
                       <td ><html:text styleClass="text" property="refrenceNO" styleId="refrenceNO" onblur="fnChangeCase('tradeMarketForm','refrenceNO')" value="${list[0].refrenceNO}" maxlength="20"/></td>
                       <td><bean:message key="lbl.tradeAppraiserName"/><font color="red">*</font></td>
                       <td ><html:text styleClass="text" property="appraiser" styleId="appraiser" onblur="fnChangeCase('tradeMarketForm','appraiser')" value="${list[0].appraiser}" maxlength="50"/></td>
                    </tr>
                 <tr>
                   <td><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
                  <td><html:text property="appraisalDate" styleId="appraisalDate" styleClass="text" readonly="true" onchange="validateLeadDateApp();checkDateApp();" value="${list[0].appraisalDate}"/></td>
                	<td><bean:message key="lbl.reportDate" /></td>
					<td><html:text property="reportDate" styleId="reportDate" maxlength="10" styleClass="text" value="${userobject.businessdate }" disabled="true" tabindex="-1"/>
        	  			 <html:hidden property="reportDate" styleClass="text" styleId="reportDate" value="${userobject.businessdate }" />
        	   </td>
                  </tr>
           <tr>
                 	<td><bean:message key="lbl.verificationMode" /></td>
						 <td>
							 <html:select property="verificationMode" styleId="verificationMode" styleClass="text" value="${list[0].verificationMode}">
								    <html:option value="F">Field Visit</html:option>
									<html:option value="P">Phone</html:option>
									<html:option value="W">Website</html:option>
							</html:select> 
						</td>
				  <td><bean:message key="lbl.appRemark"/><font color="red">*</font> </td>
       <td><html:textarea property="textarea" styleId="textarea" styleClass="text" cols="70" rows="1" onblur="fnChangeCase('tradeMarketForm','textarea')" value="${list[0].textarea}" ></html:textarea></td>		
        </tr><tr>
            <td><button type="button" name="buyerButton" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return modifyTradeMarketDetails()"><bean:message key="button.save" /></button> </td>
        </tr> 
        </logic:present>
        
      </table>
     </td>
     </tr>
     </tbody>
     </table>    
   </fieldset>
   </html:form>
</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
</body>
</html:html>
