 <%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page language="java" import="com.connect.CommonFunction"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		  
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/cpScript/documentRelated.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
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
	
<body onload="parentDocWithRCU();enableOrDisableField();enableAnchor();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<logic:present name="verifCPS">
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <% if (CommonFunction.checkNull(session.getAttribute("bpType")).equalsIgnoreCase("BUL")){ %>

				        <tr class="white2">
<td><b>
				Builder Name
				</b>
			</td>
		  
		  <td  >${dealHeader[0].dealCustomerName}</td>
		  
			<td><b><bean:message key="lbl.date"/></b></td>
			<td>${dealHeader[0].dealDate}</td>
			<td><b><bean:message key="lbl.currentStage"/></b></td>
			<logic:notEqual name="functionId" value="10000830">
			<logic:notEqual name="functionId" value="10000831">
			<logic:notEqual name="functionId" value="8000306">
			<logic:notEqual name="functionId" value="8000305">
			<logic:notEqual name="functionId" value="8000303">
			<logic:notEqual name="functionId" value="8000302">
			<logic:notEqual name="functionId" value="3000221">
			<logic:notEqual name="functionId" value="3000226">
			    <td >${requestScope.verification}</td>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
	        </logic:notEqual>
	         <logic:equal name="functionId" value="10000830">  
	          <td >RCU Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="10000831">  
	           <td >RCU Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000305">  
	          <td >Technical Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000306">  
	          <td >Technical Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000302">  
	          <td >Legal Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000303">  
	          <td >Legal Verification Completion</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000221">  
	          <td >Verification Capturing</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000226">  
	          <td >Verification Completion</td>
	          </logic:equal>
		  </tr>
		  <%} else if (CommonFunction.checkNull(session.getAttribute("bpType")).equalsIgnoreCase("PRJ")){%>
		  <tr class="white2">
			<td><b>
				Project Name
				</b>
			</td>
		 
		  <td  >${dealHeader[0].dealCustomerName}</td>
		 
			<td><b><bean:message key="lbl.date"/></b></td>
			<td>${dealHeader[0].dealDate}</td>
			<td><b><bean:message key="lbl.currentStage"/></b></td>
			<logic:notEqual name="functionId" value="10000830">
			<logic:notEqual name="functionId" value="10000831">
			<logic:notEqual name="functionId" value="8000306">
			<logic:notEqual name="functionId" value="8000305">
			<logic:notEqual name="functionId" value="8000303">
			<logic:notEqual name="functionId" value="8000302">
			<logic:notEqual name="functionId" value="3000221">
			<logic:notEqual name="functionId" value="3000226">
			   <td >${requestScope.verification}</td>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
	         </logic:notEqual>
	        <logic:equal name="functionId" value="10000830">  
	          <td >RCU Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="10000831">  
	           <td >RCU Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000305">  
	          <td >Technical Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000306">  
	          <td >Technical Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000302">  
	          <td >Legal Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000303">  
	          <td >Legal Verification Completion</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000221">  
	          <td >Verification Capturing</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000226">  
	          <td >Verification Completion</td>
	          </logic:equal>
		  </tr>
		  <%} else if (CommonFunction.checkNull(session.getAttribute("bpType")).equalsIgnoreCase("DSA")){%>
		  <tr class="white2">
			<td><b>
				DSA Name
				</b>
			</td>
		  <td>${dealHeader[0].dealCustomerName}</td>
			<td><b><bean:message key="lbl.date"/></b></td>
			<td>${dealHeader[0].dealDate}</td>
			<td><b><bean:message key="lbl.currentStage"/></b></td>
			<logic:notEqual name="functionId" value="10000830">
			<logic:notEqual name="functionId" value="10000831">
			<logic:notEqual name="functionId" value="8000306">
			<logic:notEqual name="functionId" value="8000305">
			<logic:notEqual name="functionId" value="8000303">
			<logic:notEqual name="functionId" value="8000302">
			<logic:notEqual name="functionId" value="3000221">
			<logic:notEqual name="functionId" value="3000226">
			   <td >${requestScope.verification}</td>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
	         </logic:notEqual>
	         <logic:equal name="functionId" value="10000830">  
	          <td >RCU Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="10000831">  
	           <td >RCU Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000305">  
	          <td >Technical Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000306">  
	          <td >Technical Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000302">  
	          <td >Legal Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000303">  
	          <td >Legal Verification Completion</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000221">  
	          <td >Verification Capturing</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000226">  
	          <td >Verification Completion</td>
	          </logic:equal>
		  </tr>
		  
		  
		   <%} else if (CommonFunction.checkNull(session.getAttribute("bpType")).equalsIgnoreCase("AG")){%>
		  <tr class="white2">
			<td><b>
				Agency Name
				</b>
			</td>
		  
		 
		  <td >${dealHeader[0].dealCustomerName}</td>
		 
		 
			<td><b><bean:message key="lbl.date"/></b></td>
			<td>${dealHeader[0].dealDate}</td>
			<td><b><bean:message key="lbl.currentStage"/></b></td>
			<logic:notEqual name="functionId" value="10000830">
			<logic:notEqual name="functionId" value="10000831">
			<logic:notEqual name="functionId" value="8000306">
			<logic:notEqual name="functionId" value="8000305">
			<logic:notEqual name="functionId" value="8000303">
			<logic:notEqual name="functionId" value="8000302">
			<logic:notEqual name="functionId" value="3000221">
			<logic:notEqual name="functionId" value="3000226">
			   <td >${requestScope.verification}</td>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
	         </logic:notEqual>
	        <logic:equal name="functionId" value="10000830">  
	          <td >RCU Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="10000831">  
	           <td >RCU Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000305">  
	          <td >Technical Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000306">  
	          <td >Technical Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000302">  
	          <td >Legal Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000303">  
	          <td >Legal Verification Completion</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000221">  
	          <td >Verification Capturing</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000226">  
	          <td >Verification Completion</td>
	          </logic:equal>
		  </tr>
		  
		  
		  
		  <% }else {%>
		  <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <logic:notEqual name="functionId" value="10000830">
			<logic:notEqual name="functionId" value="10000831">
			<logic:notEqual name="functionId" value="8000306">
			<logic:notEqual name="functionId" value="8000305">
			<logic:notEqual name="functionId" value="8000303">
			<logic:notEqual name="functionId" value="8000302">
			<logic:notEqual name="functionId" value="3000221">
			<logic:notEqual name="functionId" value="3000226">
			   <td >${requestScope.verification}</td>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
	         </logic:notEqual>
	            <logic:equal name="functionId" value="10000830">  
	          <td >RCU Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="10000831">  
	           <td >RCU Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000305">  
	          <td >Technical Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000306">  
	          <td >Technical Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000302">  
	          <td >Legal Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000303">  
	          <td >Legal Verification Completion</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000221">  
	          <td >Verification Capturing</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000226">  
	          <td >Verification Completion</td>
	          </logic:equal>
          </tr>
		<%}%>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
<logic:notPresent name="viewDeal">
<html:form styleId="fieldVerificationForm" action="/fieldVerificationSave" method="post"  enctype="multipart/form-data" onsubmit="submitFVCDoc()">

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="businessDate" id="businessDate" value="${requestScope.businessDate}" />
<input type="hidden" name="appType" id="appType" value="${requestScope.appType}" />
<html:hidden property="addressType" styleId="addressType" styleClass="text" value="${verifList[0].addressType}" />
<input type="hidden" name="dealDate" id="dealDate" value="${dealHeader[0].dealDate}" />
<fieldset>
<legend><bean:message key="lbl.verificationCapturing" /></legend> 
	<fieldset>
	<legend><bean:message key="lbl.verificationDetails" /></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
  		<html:hidden property="verificationId" styleId="verificationId" styleClass="text" value="${sessionScope.verificationId}" /> 
  		<html:hidden property="entId" styleId="entId" styleClass="text" value="${sessionScope.entityId}" /> 
  		<html:hidden property="fieldVerificationUniqueId" styleId="fieldVerificationUniqueId" styleClass="text" value="${commonList[0].fieldVerificationUniqueId}" /> 
  		
  		<tr>
  			<td width="13%"><bean:message key="lbl.veri.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityType" styleId="entityType" styleClass="text" value="${verifList[0].entityType}" readonly="true"></html:text><br /></td>
   			<td width="13%"><bean:message key="lbl.veri.sub.entityName"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityDesc" styleId="entityDesc" styleClass="text" value="${verifList[0].entityDesc}" readonly="true"></html:text><br /></td>
   			</tr>
   			<tr>
   			
   			<td width="13%"><bean:message key="lbl.veri.sub.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entitySubType" styleId="entitySubType" styleClass="text" value="${verifList[0].entitySubType}" readonly="true"></html:text><br /></td>
			<td width="13%"><bean:message key="lbl.varificationType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationType" styleId="varificationType" styleClass="text" value="${verifList[0].verificationType}" readonly="true"></html:text><br /></td>
			 
 		</tr> 
  		<tr>
  			<td width="13%"><bean:message key="lbl.varificationSubType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationSubType" styleId="varificationSubType" styleClass="text" value="${verifList[0].verificationSubType}" readonly="true"></html:text><br /></td>
			<td width="13%"><bean:message key="lbl.veri.sub.entityAddress"/><font color="red">*</font></td>
			<td width="13%"><textarea  name="entityAddress" id="entityAddress" readonly="true" class="text" >${verifList[0].entityAddress}</textarea></td>
			
 		</tr> 
 		<tr>
 		 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("CONTACT VERIFICATION")){ %>
    	  <td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
			<td width="13%"><html:text  property="managementPhone" styleId="managementPhone" readonly="true" styleClass="text" value="${verifList[0].managementPhone}"></html:text></td>
			 <%} %>
 		</tr>
 		<tr>
 		<logic:present name="appraiserType">
 			<td width="13%"><bean:message key="lbl.veri.agency"/></td>
   			<td width="13%"><html:text property="agencyName" styleId="agencyName" styleClass="text" value="${verifList[0].agencyName}" readonly="true"></html:text><br /></td>
 		</logic:present>
 		</tr>
 		<tr>
 		<td>
 		<logic:present name="verifList">
 			<logic:iterate name="verifList" id="subverifList" length="1">
 			   <logic:equal name="subverifList" property="entityType" value="MARKET">
 					<button type="button" name="details" id="button" class="topformbutton2" title="Alt+V" disabled="disabled" accesskey="V" onclick="detailEntity('${sessionScope.entityId}','${sessionScope.verificationId}');" ><bean:message key="button.details" /></button>
 				</logic:equal>
 				<logic:notEqual name="subverifList" property="entityType" value="MARKET">
 					<button type="button" name="details" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="detailEntity('${sessionScope.entityId}','${sessionScope.verificationId}');" ><bean:message key="button.details" /></button>
 				</logic:notEqual>
 				<logic:present name="bpType">
  		   			 <logic:equal name="bpType" value="PRJ">
  		    			<button type="button" name="details" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="detailproperty('${sessionScope.dealId}');" ><bean:message key="button.ViewProject" /></button>
  		  			 </logic:equal>
  		   		</logic:present>
 			</logic:iterate>
 		</logic:present>
    	</td>

    	 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
    		<td>
    		<button type="button" name="details" id="button" class="topformbutton4" title="Alt+V" accesskey="V" onclick="detailSearch('${sessionScope.dealId}','${sessionScope.verificationId}');" ><bean:message key="button.searchbutton" /></button>
    		</td>
       		<%}} %>
 		</tr> 
 	</table> 
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.verificationInformation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	    <tr>
	    <logic:present name="apprsName">
	    	 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
		<logic:present name="appraiserType">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${verifList[0].appraisalName}" onblur="return caseChangeAN();"/></td>
    	</logic:present>	 
       <logic:notPresent name="appraiserType">
        <td width="15%">
        <html:text property="appraisalName" styleId="appraisalName" value="${verifList[0].appraisalName}" readonly="true" styleClass="text"  />
			<html:hidden  property="lbxUserId" styleId="lbxUserId"  value="${verifList[0].lbxUserId}" />
			<html:button property="internalButton" styleId="internalButton" onclick="openLOVCommon(308,'fieldVerificationForm','appraisalName','','', '','','','internalAppUserId');" value=" " styleClass="lovbutton"> </html:button>
		 	<html:hidden property="internalAppUserId" styleId="internalAppUserId" value="${verifList[0].lbxUseId}" />
		</td>
		
		</logic:notPresent>
		</logic:present>
		<logic:notPresent name="apprsName">
		
		 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
		<logic:present name="appraiserType">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${commonList[0].appraisalName}" onblur="return caseChangeAN();"/></td>
    	</logic:present>	 
       <logic:notPresent name="appraiserType">
        <td width="15%">
        <html:text property="appraisalName" styleId="appraisalName" value="${commonList[0].appraisalName}" readonly="true" styleClass="text"  />
			<html:hidden  property="lbxUserId" styleId="lbxUserId"  value="${commonList[0].lbxUserId}" />
			<html:button property="internalButton" styleId="internalButton" onclick="openLOVCommon(308,'fieldVerificationForm','appraisalName','','', '','','','internalAppUserId');" value=" " styleClass="lovbutton"> </html:button>
		 	<html:hidden property="internalAppUserId" styleId="internalAppUserId" value="${commonList[0].lbxUseId}" />
		</td>
		</logic:notPresent>
		
		</logic:notPresent>
		
    		 <td width="15%"><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
			 <td width="13%"><html:text property="appraisalDate"   styleClass="text" styleId="appraisalDate" maxlength="10" value="${commonList[0].appraisalDate}" onchange="return checkDate('appraisalDate');"/></td>
	    </tr>
	    <tr>
	    	<td width="13%"><bean:message key="lbl.verificationMode"/><span><font color="red">*</font></span></td>
	    	<td width="13%"><html:select property="verificationMode" styleId="verificationMode" styleClass="text"  value="${commonList[0].verificationMode}">
    									<html:option  value="">----Select----</html:option>
           								<html:option value="P">Phone</html:option>
           								<html:option value="W">Website</html:option>
           								<html:option value="F">Field Visit</html:option>
           								
       						</html:select>
       		</td>
       		<logic:present name="functionId">
		<logic:notEqual name="functionId" value="10000830">
    	 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	 <td width="13%"><bean:message key="lbl.personToMeet"/><span><font color="red">*</font></span></td>
    		<td width="13%"><html:text property="personToMeet" styleId="personToMeet" maxlength="50" styleClass="text" value="${commonList[0].personToMeet}" onblur="return caseChangePM();"/></td>
<%} %>
    	</logic:notEqual>
    	</logic:present>
	    </tr>
	    <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    		<tr>
	   	
    <%-- 	<logic:present name="RCUDisabled">
  		    <td width="13%">
  		    <logic:present name="bpType">
  		    <logic:notEqual name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithApplicant"/>
  		    </logic:notEqual>
  		     <logic:equal name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithBuilder"/>
  		    </logic:equal>
  		    </logic:present>
  		    </td>
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" maxlength="50" disabled="true" styleClass="text" value="${commonList[0].relationWithApplicant}" onblur="return caseChangeRA();"/></td>
    		<td width="13%"><bean:message key="lbl.phone1"/></td>
    		<td width="13%"><html:text property="phone1" styleId="phone1" maxlength="10" disabled="true" styleClass="text" value="${commonList[0].phone1}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
    	</logic:present> --%>
    		
    	<logic:present name="functionId">
		<logic:notEqual name="functionId" value="10000830">		
    	
    		 <td width="13%">
  		    <logic:present name="bpType">
  		    <logic:notEqual name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span>
  		    </logic:notEqual>
  		     <logic:equal name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithBuilder"/><span><font color="red">*</font></span>
  		    </logic:equal>
  		    </logic:present>
  		    <logic:notPresent name="bpType">
  		      <bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span>
  		    </logic:notPresent>
  		    </td>
  		    
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" maxlength="50" styleClass="text" value="${commonList[0].relationWithApplicant}" onblur="return caseChangeRA();"/></td>
    		<td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="phone1" styleId="phone1" maxlength="10" styleClass="text" value="${commonList[0].phone1}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
  
  	 </logic:notEqual>
  	 </logic:present>
  	  	</tr> 
  	  	<%} %>
 		<tr>
 		<logic:present name="functionId">
		<logic:notEqual name="functionId" value="10000830">
		 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
		    <td width="13%"><bean:message key="lbl.phone2"/></td>
    		<td width="13%"><html:text property="phone2" styleId="phone2" maxlength="10" styleClass="text" value="${commonList[0].phone2}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
    		
    		<td width="13%"><bean:message key="lbl.email"/></td>
    		<td width="13%"><html:text property="email" styleId="email" maxlength="50" styleClass="text" value="${commonList[0].email}"/></td>
    		<%}} %>
  		</logic:notEqual>
  		</logic:present>
  		</tr>
  		<%-- <tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus" styleId="cpvStatus" styleClass="text" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
    		<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" maxlength="100" class="text" >${commonList[0].remarks}</textarea></td>
  		</tr> --%>
  		
  		
  		 <tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus"  styleId="cpvStatus" styleClass="text" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
			<logic:present name="functionId">
			<logic:equal name="functionId" value="8000305">
				<td width="13%"><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="marketValue" style="text-align: right" styleId="marketValue" onkeypress="return numbersonly(event, id, 12);" onchange="numberFormatting(this.id,'2');"  maxlength="100" styleClass="text" value="${commonList[0].marketValue}"></html:text></td>
  		</logic:equal>
		</logic:present>
		<logic:present name="functionId">
			<logic:equal name="functionId" value="8000306">
				<td width="13%"><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="marketValue" style="text-align: right" styleId="marketValue" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');"  maxlength="100" styleClass="text" value="${commonList[0].marketValue}"></html:text></td>
  		</logic:equal>
		</logic:present>
		</tr>
  	
  		<logic:present name="functionId">
		<logic:equal name="functionId" value="8000305">
			<tr>
				
				<td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 12);" onchange="numberFormatting(this.id,'2');" styleId="govtValue" maxlength="100" styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
				
				<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
				<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 12);" onchange="numberFormatting(this.id,'2');" styleId="distressValue" maxlength="100"   styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
			</tr>
  		</logic:equal>
		</logic:present>
  		
  		<logic:present name="functionId">
		<logic:equal name="functionId" value="8000306">
			<tr>
				
				<td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="govtValue" maxlength="100" styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
				
				<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
				<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="distressValue" maxlength="100"   styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
			</tr>
  		</logic:equal>
		</logic:present>

  		
  		<tr>
    		
		    
    		<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" maxlength="100" class="text"  onblur="textCounter(this,3000);" >${commonList[0].remarks}</textarea></td>
  		</tr>
	 
 	</table>
	</fieldset>
	<logic:present name="functionId">
		<logic:notEqual name="functionId" value="10000830">
	<fieldset>
	<legend><bean:message key="lbl.ver.question"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
    <tr class="white2">
 		<td width="10%"><b><bean:message key="lbl.questionNo"/></b></td>
 		<%--   <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION") && !CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("TECHNICAL VERIFICATION")){ %>
 		<td width="10%"><b><bean:message key="lbl.questionSeqNo"/></b></td>
 		<%} %> --%>
        <td width="13%"><b><bean:message key="lbl.ver.question"/></b></td>
        <td width="50%"><b><bean:message key="lbl.queryRemarks"/></b></td>
        <td width="13%"><b><bean:message key="lbl.notificationMethod"/></b></td>
        <td width="10%"><b><bean:message key="lbl.verifQuestRequired"/></b></td>
   </tr>
	
	
<logic:present name="questList">
	<logic:iterate name="questList" id="sublist" indexId="counter">
	<tr class="white1">
	<html:hidden property="questionVerificationUniqueId" styleId="questionVerificationUniqueId" styleClass="text" value="${sublist.questVerifUniqueId}" /> 
    <html:hidden property="questionSeqNo" styleId="questionSeqNo" value="${sublist.questSeqNo}"/>
	<html:hidden property="questionId" styleId="questionId" value="${sublist.questId}"/>
	<html:hidden property="verifQuestRequired" styleId="verifQuestRequired${counter+1}" value="${sublist.verifQuestRequired}"/>
	
	<td width="10%">
		
		${counter+1}
       </td>
       	<td width="10%">
		
			${sublist.questSeqNo}
       </td>
      
	<td width="13%">
		
		${sublist.quest}
       </td>
      
	
     <td>
     
    	 <html:text property="questionRemarks" styleId="questionRemarks${counter+1}" value="${sublist.questRemarks}" size="50%" maxlength="500"/>
		
      </td>
        <td width="13%">
        
         <html:select property="verificationMethod" styleId="verificationMethod${counter+1}" styleClass="text" value="${sublist.questVerifMethod}" >
           		<html:option  value="">----Select----</html:option>
           		<logic:present name="verifMethodList">
           		     <html:optionsCollection name="verifMethodList"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
     
     
       </td>
 	  <td width="10%">
		
			${sublist.verifQuestRequired}
       </td>
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
	</fieldset>
	</logic:notEqual>
	</logic:present>
	<!-- amandeep changes start for RCU DOCS -->
	<logic:present name="functionId">
		<logic:equal name="functionId" value="10000830">
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" onchange="parentDocWithRCU();" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" onblur="parentDocWithRCU();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);">${sublist.RCURemarks}</textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	<logic:equal name="functionId" value="8000241">
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" onchange="parentDocWithRCU();disabledRemarks();" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" onblur="parentDocWithRCU();disabledRemarks();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);" >${sublist.RCURemarks}  </textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	<logic:equal name="functionId" value="8000311">
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" onchange="parentDocWithRCU();disabledRemarks();" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" onblur="parentDocWithRCU();disabledRemarks();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);" >${sublist.RCURemarks}  </textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	</logic:present>
	<!-- amandeep changes ends for RCU DOCS -->
<!--	<fieldset>	  -->
<!--	<legend><bean:message key="lbl.docUploadDetails"/></legend>  -->
<!--	<table width="100%"  border="0" cellspacing="0" cellpadding="0">-->
<!--     <tr>-->
<!--      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">-->
<!--        <html:hidden property="docFileString" styleId="docFileString" value=""/>-->
<!--		<tr>-->
<!--		  <td width="23%" style="width:23%"><bean:message key="lbl.docDescription"/><font color="red">*</font></td>-->
<!--		  <td width="21%"><html:text property="docDescription" styleClass="text" value="" tabindex="1"  maxlength="100" styleId="docDescription" /></td>-->
<!--          <td width="20%"><html:file property="docFile" styleId="docFile" styleClass="text" /></td>-->
<!--         <td> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('fieldVerificationForm','FVC');"><bean:message key="button.upload" /></button></td>-->
<!--          -->
<!--		</tr>-->
<!--		  -->
<!--      </table></td>-->
<!--</tr>-->
<!--</table>    -->
<!---->
<!--	 -->
<!--	 -->
<!--	  </fieldset>-->
	  
	  <div id="UploadDoc"></div>
	  

	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		  	<td >
		    <button type="button" name="save" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveFieldVarCap();" ><bean:message key="button.save" /></button>
		    <button type="button" name="forward" id="forward" class="topformbutton2" title="Alt+F" accesskey="F" onclick="forwardVerificationCap('<bean:message key="msg.confirmationForwardMsg" />');" ><bean:message key="button.forward" /></button>
		    <logic:equal name="functionId" value="10000830">  
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="10000831">  
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000305">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000306">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000302">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000303">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000221">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000226">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
		    <%-- <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVI','${sessionScope.dealId}');"><bean:message key="button.upload" /></button>
		    <button type="button" name="download" id="download" class="topformbutton2" title="Alt+D" accesskey="D" onclick="downloadVarificationForm();" ><bean:message key="button.download" /></button> --%>
		    </td>
		  
		</tr>
	</table>
</fieldset>


<logic:present name="sms">
	<script type="text/javascript">	
		if('<%=request.getAttribute("sms")%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		}
		else if('<%=request.getAttribute("sms")%>'=='N')
		{
			alert("<bean:message key="msg.NonEmiError" />");
		}
    </script>
</logic:present>
<logic:present name="forwardsms">
	<script type="text/javascript">	
		if('<%=request.getAttribute("forwardsms")%>'=='F')
		{
			alert("<bean:message key="msg.ForwardNonEmiSuccessfully" />");
			location.href='<%=request.getContextPath()%>/fieldVarificationCapturingBehindAction.do?method=verificationCapturingCreditProcessing';
		}
		else if('<%=request.getAttribute("forwardsms")%>'=='N')
		{
			alert("<bean:message key="msg.ErrorForwardNonEmiLoan" />");
		}
		else if('<%=request.getAttribute("forwardsms")%>'=='T')
		{
			alert("<bean:message key="msg.ErrorSearchReport" />");
		}
    </script>
</logic:present>
</html:form>
 </logic:notPresent>  
 <logic:present name="viewDeal">
 	<html:form styleId="fieldVerificationForm" action="/fieldVerificationSave" method="post" >

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="businessDate" id="businessDate" value="${requestScope.businessDate}" />
<input type="hidden" name="appType" id="appType" value="${requestScope.appType}" />

<fieldset>
<legend><bean:message key="lbl.verificationCapturing" /></legend> 
	<fieldset>
	<legend><bean:message key="lbl.verificationDetails" /></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
  		<html:hidden property="verificationId" styleId="verificationId" styleClass="text" value="${commonList[0].verificationId}" /> 
  		<html:hidden property="entId" styleId="entId" styleClass="text" value="${sessionScope.entityId}" /> 
  		<html:hidden property="fieldVerificationUniqueId" styleId="fieldVerificationUniqueId" styleClass="text" value="${commonList[0].fieldVerificationUniqueId}" /> 
  		
  		<tr>
  			<td width="13%"><bean:message key="lbl.veri.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityType" styleId="entityType" styleClass="text" value="${commonList[0].entityType}" readonly="true"></html:text><br /></td>
   			<td width="13%"><bean:message key="lbl.veri.sub.entityName"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityDesc" styleId="entityDesc" styleClass="text" value="${commonList[0].entityDesc}" readonly="true"></html:text><br /></td>
   		</tr>	
   		<tr>	
   			<td width="13%"><bean:message key="lbl.veri.sub.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entitySubType" styleId="entitySubType" styleClass="text" value="${commonList[0].entitySubType}" readonly="true"></html:text><br /></td>
			<td width="13%"><bean:message key="lbl.varificationType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationType" styleId="varificationType" styleClass="text" value="${commonList[0].verificationType}" readonly="true"></html:text><br /></td>
			 
 		</tr> 
  		<tr>
  			<td width="13%"><bean:message key="lbl.varificationSubType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationSubType" styleId="varificationSubType" styleClass="text" value="${commonList[0].verificationSubType}" readonly="true"></html:text><br /></td>
			<td width="13%"><bean:message key="lbl.veri.sub.entityAddress"/><font color="red">*</font></td>
			<td width="13%"><textarea  name="entityAddress" id="entityAddress" readonly="true" class="text" >${commonList[0].entityAddress}</textarea></td>
			 
 		</tr> 
 		<tr>
 			 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("CONTACT VERIFICATION")){ %>
    	  <td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
		<td width="13%"><html:text  property="managementPhone" styleId="managementPhone" readonly="true" styleClass="text" value="${commonList[0].managementPhone}"></html:text></td>
			 <%} %>
 		</tr>
 		<%-- 
 		<tr>
    		<td><button type="button" name="details" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="detailEntity('${sessionScope.entityId}','${sessionScope.verificationId}');" ><bean:message key="button.details" /></button></td></td>
 		</tr> 
 		--%>
 			 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
    		<tr>
    		<td>
    		<button type="button" name="details" id="button" class="topformbutton4" title="Alt+V" accesskey="V" onclick="detailSearch('${sessionScope.dealId}','${sessionScope.verificationId}');" ><bean:message key="button.searchbutton" /></button>
    		</td>
    		</tr>
       		<%}} %>
       	
 	</table> 
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.verificationInformation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	    <tr>
	    	 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" readonly="true" value="${commonList[0].appraisalName}" onblur="return caseChangeAN();"/></td>
			 
    		 <td width="13%"><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
			 <td width="13%"><html:text property="appraisalDate"   styleClass="text" styleId="appraisalDate12345" maxlength="10" readonly="true" value="${commonList[0].appraisalDate}" onchange="return checkDate('appraisalDate');"/></td>
	    </tr>
	    <tr>
	    	<td width="13%"><bean:message key="lbl.verificationMode"/><span><font color="red">*</font></span></td>
	    	<td width="13%"><html:select property="verificationMode" styleId="verificationMode" disabled="true" styleClass="text"  value="${commonList[0].verificationMode}">
    									<html:option  value="">----Select----</html:option>
           								<html:option value="P">Phone</html:option>
           								<html:option value="W">Website</html:option>
           								<html:option value="F">Field Visit</html:option>
           								
       						</html:select>
       		</td>

	
		<logic:notEqual name="verificationType" value="RCU REPORT">
		<% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <td width="13%"><bean:message key="lbl.personToMeet"/><span><font color="red">*</font></span></td>
    		<td width="13%"><html:text property="personToMeet" styleId="personToMeet" maxlength="50" readonly="true" styleClass="text" value="${commonList[0].personToMeet}" onblur="return caseChangePM();"/></td>
    		<%} %>
	  </logic:notEqual>
	
	    </tr>
	    <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	 <tr>
  	  	 
		<logic:notEqual name="verificationType" value="RCU REPORT">
  	  	    <td width="13%"><logic:present name="bpType">
  		    <logic:notEqual name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span>
  		    </logic:notEqual>
  		     <logic:equal name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithBuilder"/><span><font color="red">*</font></span>
  		    </logic:equal>
  		    </logic:present>
  		    <logic:notPresent  name="bpType">
  		       <bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span>
  		    </logic:notPresent></td>
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" readonly="true" maxlength="50" styleClass="text" value="${commonList[0].relationWithApplicant}" onblur="return caseChangeRA();"/></td>
    		<td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="phone1" styleId="phone1" maxlength="10" styleClass="text" readonly="true" value="${commonList[0].phone1}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
  	  </logic:notEqual>
  	
  	  	</tr> 
  	  	<%} %>
 		<tr>
  		
		<logic:notEqual name="verificationType" value="RCU REPORT">
		 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
  		 <td width="13%"><bean:message key="lbl.phone2"/></td>
    		<td width="13%"><html:text property="phone2" styleId="phone2" maxlength="10" styleClass="text" readonly="true" value="${commonList[0].phone2}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
    		
		    <td width="13%"><bean:message key="lbl.phone2"/></td>
    		<td width="13%"><html:text property="phone2" styleId="phone2" maxlength="10" styleClass="text" readonly="true" value="${commonList[0].phone2}" style="text-align: right" onkeypress="return numbersonly(event,id,20)" onkeyup="checkNumber(this.value, event, 20,id);"/></td>
    		<td width="13%"><bean:message key="lbl.email"/></td>
    		<td width="13%"><html:text property="email" styleId="email" maxlength="50" styleClass="text" readonly="true" value="${commonList[0].email}"/></td>
    		<%}} %>
  		</logic:notEqual>
  		
  		</tr>
  		<%-- <tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus" styleId="cpvStatus" styleClass="text" disabled="true" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
    		<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" maxlength="100" class="text" >${commonList[0].remarks}</textarea></td>
  		</tr> --%>
  		
  		
  		<tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus" disabled="true" styleId="cpvStatus"  styleClass="text" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
			<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" maxlength="100" readonly="readonly" class="text" >${commonList[0].remarks}</textarea></td>
			
			
			
			
		</tr>
		<logic:equal name="verificationType" value="TECHNICAL VERIFICATION">
  	<tr>
				<td width="13%"><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="marketValue" style="text-align: right" styleId="marketValue" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" readonly="true"  styleClass="text" value="${commonList[0].marketValue}"></html:text></td>
  			
		</tr>
  			</logic:equal>
	
		
				<logic:equal name="verificationType" value="TECHNICAL VERIFICATION">
  		<tr>
    		
		    <td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
           <td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="govtValue" readonly="true" styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
       	
    		<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="distressValue" readonly="true"  styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
  		</tr>
  		</logic:equal>
			
  		
	 
 	</table>
	</fieldset>

		<logic:notEqual name="verificationType" value="RCU REPORT">
	<fieldset>
	<legend><bean:message key="lbl.ver.question"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
 		<td width="10%"><b><bean:message key="lbl.questionNo"/></b></td>
 		<td width="10%"><b><bean:message key="lbl.questionSeqNo"/></b></td>
        <td width="13%"><b><bean:message key="lbl.ver.question"/></b></td>
        <td width="30%"><b><bean:message key="lbl.queryRemarks"/></b></td>
        <td width="13%"><b><bean:message key="lbl.notificationMethod"/></b></td>
        <td width="10%"><b><bean:message key="lbl.verifQuestRequired"/></b></td>
   
	</tr>
	
	
<logic:present name="questList">
	<logic:iterate name="questList" id="sublist" indexId="counter">
	<tr class="white1">
	<html:hidden property="questionVerificationUniqueId" styleId="questionVerificationUniqueId" styleClass="text" value="${sublist.questVerifUniqueId}" /> 
    <html:hidden property="questionSeqNo" styleId="questionSeqNo" value="${sublist.questSeqNo}"/>
	<html:hidden property="questionId" styleId="questionId" value="${sublist.questId}"/>
	<td width="10%">
		
		${counter+1}
       </td>
       	<td width="10%">
		
			${sublist.questSeqNo}
       </td>
	<td width="13%">
		
		${sublist.quest}
       </td>
      
	
     <td width="30%">
     
    	<textarea  name="questionRemarks" class="text" id="questionRemarks" style="width: 100%;" readonly="true" >${sublist.questRemarks}</textarea>
      </td>
        <td width="13%">
        
         <html:select property="verificationMethod" styleId="verificationMethod" styleClass="text" disabled="true" value="${sublist.questVerifMethod}" >
           		<html:option  value="">----Select----</html:option>
           		<logic:present name="verifMethodList">
           		     <html:optionsCollection name="verifMethodList"  label="name" value="id"/>
           		</logic:present>
       	 </html:select>
     
 
       </td>
       <td width="10%">
		
			${sublist.verifQuestRequired}
       </td>
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
	</fieldset>
	</logic:notEqual>
	<!-- amandeep changes start for RCU DOCS -->

		<logic:equal name="verificationType" value="RCU REPORT">
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" disabled="true" onchange="parentDocWithRCU();" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" readonly="true" onblur="parentDocWithRCU();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);" >${sublist.RCURemarks}</textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	
	
	<!-- amandeep changes ends for RCU DOCS -->
<%-- <fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="counter">
    <tr  class="white1">
    	
     	<td><a href="#" onclick="downloadFVIFile('${uploadedDocSubList.lbxDocId}','fieldVerificationForm','${uploadedDocSubList.stage}','${count}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="lbxDocId" id="lbxDocId" value="${uploadedDocSubList.lbxDocId}"/>
     		<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubList.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubList.dmsDocUrl}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
    </table>
    </td>
</tr>
</table>
</fieldset>
 --%>

<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		  	<td >
		    <%--  <button type="button" name="download" id="download" class="topformbutton2" title="Alt+D" accesskey="D" onclick="downloadVarificationForm();" ><bean:message key="button.download" /></button> --%>
		    <logic:equal name="functionId" value="10000830">  
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="10000831">  
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000305">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000306">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000302">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000303">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000221">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVI','${sessionScope.dealId}','DC');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="3000226">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	          </logic:equal>
	          
	          <logic:equal name="functionId" value="3000951">  
	          <logic:equal name="verificationType" value="RCU REPORT">
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	           </logic:equal>
	           <logic:notEqual name="verificationType" value="RCU REPORT">
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVI','${sessionScope.dealId}','DC');"><bean:message key="button.download" /></button>
	           </logic:notEqual>
	          </logic:equal>
	          
		    </td>
		  
		</tr>
	</table>

</fieldset>



</html:form>
 	
 </logic:present>
 
</logic:present>
 
 <%-- for cm --%>
 <logic:present name="verifCMS">
 	 <fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <logic:notEqual name="functionId" value="8000242">
			<logic:notEqual name="functionId" value="8000241">
			<logic:notEqual name="functionId" value="8000240">
			<logic:notEqual name="functionId" value="8000239">
			<logic:notEqual name="functionId" value="8000238">
			<logic:notEqual name="functionId" value="8000237">
			<logic:notEqual name="functionId" value="4000134">
			<logic:notEqual name="functionId" value="4000133">
			     <td >${requestScope.verification}</td>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
			</logic:notEqual>
	         </logic:notEqual>
	          <logic:equal name="functionId" value="8000242">  
	          <td >RCU Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000241">  
	           <td >RCU Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000240">  
	          <td >Technical Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000239">  
	          <td >Technical Verification Capturing</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000238">  
	          <td >Legal Verification Completion</td>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000237">  
	          <td >Legal Verification Capturing</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="4000134">  
	          <td >Verification Completion</td>
	          </logic:equal>
	          <logic:equal name="functionId" value="4000133">  
	          <td >Verification Capturing</td>
	          </logic:equal>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
<logic:notPresent name="viewDeal">
<html:form styleId="fieldVerificationForm" action="/fieldVerificationSave" method="post"  enctype="multipart/form-data" onsubmit="submitFVCDoc()">

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="businessDate" id="businessDate" value="${requestScope.businessDate}" />
<input type="hidden" name="appType" id="appType" value="${requestScope.appType}" />
<input type="hidden" name="viewMode" id="viewMode" value="" />
<fieldset>
<legend><bean:message key="lbl.verificationCapturing" /></legend> 
	<fieldset>
	<legend><bean:message key="lbl.verificationDetails" /></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
  		<html:hidden property="verificationId" styleId="verificationId" styleClass="text" value="${sessionScope.verificationId}" /> 
  		<html:hidden property="entId" styleId="entId" styleClass="text" value="${sessionScope.entityId}" /> 
  		<html:hidden property="fieldVerificationUniqueId" styleId="fieldVerificationUniqueId" styleClass="text" value="${commonList[0].fieldVerificationUniqueId}" /> 
  		
  		<tr>
  			<td width="13%"><bean:message key="lbl.veri.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityType" styleId="entityType" styleClass="text" value="${verifList[0].entityType}" readonly="true"></html:text><br /></td>
   			<td width="13%"><bean:message key="lbl.veri.sub.entityName"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityDesc" styleId="entityDesc" styleClass="text" value="${verifList[0].entityDesc}" readonly="true"></html:text><br /></td>
   		</tr>
   		<tr>	
   		
   		
   			<td width="13%"><bean:message key="lbl.veri.sub.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entitySubType" styleId="entitySubType" styleClass="text" value="${verifList[0].entitySubType}" readonly="true"></html:text><br /></td>
			<td width="13%"><bean:message key="lbl.varificationType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationType" styleId="varificationType" styleClass="text" value="${verifList[0].verificationType}" readonly="true"></html:text><br /></td>
			 
 		</tr> 
  		<tr>
  			<td width="13%"><bean:message key="lbl.varificationSubType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationSubType" styleId="varificationSubType" styleClass="text" value="${verifList[0].verificationSubType}" readonly="true"></html:text><br /></td>
			<td width="13%"><bean:message key="lbl.veri.sub.entityAddress"/><font color="red">*</font></td>
			<td width="13%"><textarea  name="entityAddress" id="entityAddress" readonly="true" class="text" >${verifList[0].entityAddress}</textarea></td>
			 
 		</tr> 
 		<tr>
 			 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("CONTACT VERIFICATION")){ %>
    	  <td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
			<td width="13%"><html:text  property="managementPhone" styleId="managementPhone" readonly="true" styleClass="text" value="${verifList[0].managementPhone}"></html:text></td>
			 <%} %>
 		</tr>
 		<tr>
 		<logic:present name="appraiserType">
 			<td width="13%"><bean:message key="lbl.veri.agency"/></td>
   			<td width="13%"><html:text property="agencyName" styleId="agencyName" styleClass="text" value="${verifList[0].agencyName}" readonly="true"></html:text><br /></td>
 		</logic:present>
 		</tr>
 		<tr>
 		<logic:present name="verifList">
 			<logic:iterate name="verifList" id="subverifList" length="1">
 			   <logic:equal name="subverifList" property="entityType" value="MARKET">
 					<td><button type="button" name="details" id="button" class="topformbutton2" title="Alt+V" disabled="disabled" accesskey="V" onclick="detailEntityAtCM('${sessionScope.entityId}','${sessionScope.verificationId}');" ><bean:message key="button.details" /></button></td></td>
 				</logic:equal>
 				<logic:notEqual name="subverifList" property="entityType" value="MARKET">
 					<td><button type="button" name="details" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="detailEntityAtCM('${sessionScope.entityId}','${sessionScope.verificationId}');" ><bean:message key="button.details" /></button></td></td>
 				</logic:notEqual>
 			</logic:iterate>
 		</logic:present>
    		 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
    		
    		<td>
    		<button type="button" name="details" id="button" class="topformbutton4" title="Alt+V" accesskey="V" onclick="detailSearch('${sessionScope.dealId}','${sessionScope.verificationId}');" ><bean:message key="button.searchbutton" /></button>
    		</td>
    		
       		<%}} %>
    		
       		
 		</tr> 
 	</table> 
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.verificationInformation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	    <tr>
	    	<logic:present name="apprsName">
	    	 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
		<logic:present name="appraiserType">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${verifList[0].appraisalName}" onblur="return caseChangeAN();"/></td>
    	</logic:present>	 
       <logic:notPresent name="appraiserType">
        <td width="15%">
        <html:text property="appraisalName" styleId="appraisalName" value="${verifList[0].appraisalName}" readonly="true" styleClass="text"  />
			<html:hidden  property="lbxUserId" styleId="lbxUserId"  value="${verifList[0].lbxUserId}" />
			<html:button property="internalButton" styleId="internalButton" onclick="openLOVCommon(308,'fieldVerificationForm','appraisalName','','', '','','','internalAppUserId');" value=" " styleClass="lovbutton"> </html:button>
		 	<html:hidden property="internalAppUserId" styleId="internalAppUserId" value="${verifList[0].lbxUseId}" />
		</td>
		
		</logic:notPresent>
		</logic:present>
		<logic:notPresent name="apprsName">
		
		 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
		<logic:present name="appraiserType">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${commonList[0].appraisalName}" onblur="return caseChangeAN();"/></td>
    	</logic:present>	 
       <logic:notPresent name="appraiserType">
        <td width="15%">
        <html:text property="appraisalName" styleId="appraisalName" value="${commonList[0].appraisalName}" readonly="true" styleClass="text"  />
			<html:hidden  property="lbxUserId" styleId="lbxUserId"  value="${commonList[0].lbxUserId}" />
			<html:button property="internalButton" styleId="internalButton" onclick="openLOVCommon(308,'fieldVerificationForm','appraisalName','','', '','','','internalAppUserId');" value=" " styleClass="lovbutton"> </html:button>
		 	<html:hidden property="internalAppUserId" styleId="internalAppUserId" value="${commonList[0].lbxUseId}" />
		</td>
		</logic:notPresent>
		
		</logic:notPresent>
    		 <td width="13%"><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
			 <td width="13%"><html:text property="appraisalDate"   styleClass="text" styleId="appraisalDate" maxlength="10" value="${commonList[0].appraisalDate}" onchange="return checkDate('appraisalDate');"/></td>
	    </tr>
	    <tr>
	    	<td width="13%"><bean:message key="lbl.verificationMode"/><span><font color="red">*</font></span></td>
	    	<td width="13%"><html:select property="verificationMode" styleId="verificationMode" styleClass="text"  value="${commonList[0].verificationMode}">
    									<html:option  value="">----Select----</html:option>
           								<html:option value="P">Phone</html:option>
           								<html:option value="W">Website</html:option>
           								<html:option value="F">Field Visit</html:option>
           								
       						</html:select>
       		</td>
       		<logic:present name="functionId">
		<logic:notEqual name="functionId" value="8000241">
       		 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  
       		<td width="13%"><bean:message key="lbl.personToMeet"/><span><font color="red">*</font></span></td>
    		<td width="13%"><html:text property="personToMeet" styleId="personToMeet" maxlength="50" styleClass="text" value="${commonList[0].personToMeet}" onblur="return caseChangePM();"/></td>
    		<%} %>
    		</logic:notEqual>
    		</logic:present>
	    </tr>
	    
<!-------------------------------------ADD by sachin------------------------------------	    -->

	    <tr id="seenAtAssetOrCollateral" style="display:none">			
		   <td width="13%"><bean:message key="lbl.assetLocation"/><font color="red">*</font>
		   </td>
		   <td width="13%">
		   <html:text styleClass="text" property="assetLocation" styleId="assetLocation"   maxlength="100" value="${commonList[0].assetLocation}" />
		   </td>
		   <td width="13%">
			<bean:message key="lbl.assetCondition"/><font color="red">*</font> 
		   </td>
	   	   <td width="13%"> 
	   	  	 <html:text styleClass="text" property="assetCondition" maxlength="100" styleId="assetCondition" value="${commonList[0].assetCondition}" />   
		 </td>
	</tr>
		
	  <tr id="seenAtAstOrCol" style="display:none">
	   
		<td width="13%"><bean:message key="lbl.invoiceCollected"/></td>
		<td width="13%" >
		<html:select property="invoiceCollected" styleClass="text" styleId="invoiceCollected" value="${commonList[0].invoiceCollected}" onchange="InvoColl()">
	            <html:option value="Y">Yes</html:option>
		        <html:option value="N">No</html:option>
		      </html:select> </td>
			
		   <td width="13%"><bean:message key="lbl.invoiceNumber"/></td>
			 <td width="13%">
			 <html:text styleClass="text" property="invoiceNumber" styleId="invoiceNumber"  maxlength="20" value="${commonList[0].invoiceNumber}" /> </td>
			 
		</tr>
		
<!-------------------------------------ADD by sachin------------------------------------	    -->		
	    <logic:present name="functionId">
		<logic:notEqual name="functionId" value="8000241">
	    <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
    		
	   	<tr>
    		
  		   <td width="13%">
  		    <bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span>
  		</td>
  		 
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" maxlength="50" styleClass="text" value="${commonList[0].relationWithApplicant}" onblur="return caseChangeRA();"/></td>
    		<td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="phone1" styleId="phone1" maxlength="10" styleClass="text" value="${commonList[0].phone1}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
  	  	</tr> 
  	  	<%}} %>
  	  	</logic:notEqual>
  	  	</logic:present>
 		<tr>
 		<logic:present name="functionId">
		<logic:notEqual name="functionId" value="8000241">
    		 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
    		
		    <td width="13%"><bean:message key="lbl.phone2"/></td>
    		<td width="13%"><html:text property="phone2" styleId="phone2" maxlength="10" styleClass="text" value="${commonList[0].phone2}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
    		
    		<td width="13%"><bean:message key="lbl.email"/></td>
    		<td width="13%"><html:text property="email" styleId="email" maxlength="50" styleClass="text" value="${commonList[0].email}"/></td>
    		<%}} %>
    		</logic:notEqual>
    		</logic:present>
  		</tr>
  		
  		<%-- <tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus" styleId="cpvStatus" styleClass="text" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
    		<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" maxlength="100" class="text" >${commonList[0].remarks}</textarea></td>
  		</tr> --%>
	 
	 <tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus"  styleId="cpvStatus" styleClass="text" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
			<logic:present name="functionId">
			<logic:equal name="functionId" value="8000239">
				<td width="13%"><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="marketValue" style="text-align: right" styleId="marketValue" onkeypress="return numbersonly(event, id, 12);" onchange="numberFormatting(this.id,'2');"  styleClass="text" value="${commonList[0].marketValue}"></html:text></td>
  			
			</logic:equal>
			<logic:equal name="functionId" value="8000240">
				<td width="13%"><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="marketValue" style="text-align: right" styleId="marketValue" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" readonly="true"  styleClass="text" value="${commonList[0].marketValue}"></html:text></td>
  			
			</logic:equal>
			</logic:present>
			
			
		</tr>
  	
  		<logic:present name="functionId">
		<logic:equal name="functionId" value="8000239">
  		<tr>
    		
		    <td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
           <td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 12);" onchange="numberFormatting(this.id,'2');" styleId="govtValue"  styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
      
    		<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 12);" onchange="numberFormatting(this.id,'2');" styleId="distressValue"   styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
  		</tr>
  		</logic:equal>
		
				<logic:equal name="functionId" value="8000240">
  		<tr>
    		
		    <td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
           <td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="govtValue" readonly="true" styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
       	
    		<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="distressValue" readonly="true"  styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
  		</tr>
  		</logic:equal>
			</logic:present>
  		
	 
  	
  	<tr>
  	<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" maxlength="100" class="text" >${commonList[0].remarks}</textarea></td>
  	</tr>
  		<logic:present name="functionId">
		<logic:equal name="functionId" value="8000305">
			<tr>
				
				<td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="govtValue" maxlength="100" styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
				
				<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
				<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="distressValue" maxlength="100"   styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
			</tr>
	 </logic:equal>
	 <logic:equal name="functionId" value="8000306">
			<tr>
				
				<td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
				<td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="govtValue" maxlength="100" styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
				
				<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
				<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="distressValue" maxlength="100"   styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
			</tr>
	 </logic:equal>
	</logic:present>
 	</table>
	</fieldset>
	<logic:notEqual name="verificationType" value="RCU REPORT">
	<fieldset>
	<legend><bean:message key="lbl.ver.question"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
    <tr class="white2">
 		<td width="10%"><b><bean:message key="lbl.questionNo"/></b></td>
 		<%-- <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION") && !CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("TECHNICAL VERIFICATION")){ %>
 		
 		<td width="10%"><b><bean:message key="lbl.questionSeqNo"/></b></td>
 		<%} %> --%>
        <td width="13%"><b><bean:message key="lbl.ver.question"/></b></td>
        <td width="50%"><b><bean:message key="lbl.queryRemarks"/></b></td>
        <td width="13%"><b><bean:message key="lbl.notificationMethod"/></b></td>
        <td width="10%"><b><bean:message key="lbl.verifQuestRequired"/></b></td>
   
	</tr>
	
	
<logic:present name="questList">
	<logic:iterate name="questList" id="sublist" indexId="counter">
	<tr class="white1">
	<html:hidden property="questionVerificationUniqueId" styleId="questionVerificationUniqueId" styleClass="text" value="${sublist.questVerifUniqueId}" /> 
    <html:hidden property="questionSeqNo" styleId="questionSeqNo" value="${sublist.questSeqNo}"/>
	<html:hidden property="questionId" styleId="questionId" value="${sublist.questId}"/>
	<html:hidden property="verifQuestRequired" styleId="verifQuestRequired${counter+1}" value="${sublist.verifQuestRequired}"/>
	
	<td width="10%">
		
		${counter+1}
       </td>
       	<td width="10%">
		
			${sublist.questSeqNo}
       </td>
	<td width="13%">
		
		${sublist.quest}
       </td>
      
	
     <td>
     
    	<textarea  name="questionRemarks" class="text" id="questionRemarks${counter+1}" style="width: 100%;" onkeyup="return imposeMaxLength(this, 5000);"  >${sublist.questRemarks}</textarea>
      </td>
        <td width="13%">
        
         <html:select property="verificationMethod" styleId="verificationMethod${counter+1}" styleClass="text" value="${sublist.questVerifMethod}" >
           		<html:option  value="">----Select----</html:option>
           		<logic:present name="verifMethodList">
           		     <html:optionsCollection name="verifMethodList"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
     
     
       </td>
 	  <td width="10%">
		
			${sublist.verifQuestRequired}
       </td>
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
	</fieldset>
	</logic:notEqual>
	<logic:present name="functionId">
		<logic:equal name="functionId" value="10000830">
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" onchange="parentDocWithRCU();" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" onblur="parentDocWithRCU();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);" >${sublist.RCURemarks}</textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	<logic:equal name="functionId" value="8000241">
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" onchange="parentDocWithRCU();" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" onblur="parentDocWithRCU();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);" >${sublist.RCURemarks}</textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	<logic:equal name="functionId" value="8000311">
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" onchange="parentDocWithRCU();" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" onblur="parentDocWithRCU();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);" >${sublist.RCURemarks}</textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	</logic:present>
<!--	<fieldset>	  -->
<!--	<legend><bean:message key="lbl.docUploadDetails"/></legend>  -->
<!--	<table width="100%"  border="0" cellspacing="0" cellpadding="0">-->
<!--     <tr>-->
<!--      <td><table width="100%" border="0" cellspacing="1" cellpadding="4">-->
<!--        <html:hidden property="docFileString" styleId="docFileString" value=""/>-->
<!--		<tr>-->
<!--		  <td width="23%" style="width:23%"><bean:message key="lbl.docDescription"/><font color="red">*</font></td>-->
<!--		  <td width="21%"><html:text property="docDescription" styleClass="text" value="" tabindex="1"  maxlength="100" styleId="docDescription" /></td>-->
<!--          <td width="20%"><html:file property="docFile" styleId="docFile" styleClass="text" /></td>-->
<!--         <td> <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('fieldVerificationForm','FVC');"><bean:message key="button.upload" /></button></td>-->
<!--          -->
<!--		</tr>-->
<!--		  -->
<!--      </table></td>-->
<!--</tr>-->
<!--</table>    -->
<!---->
<!--	 -->
<!--	 -->
<!--	  </fieldset>-->
	  
	  <div id="UploadDoc"></div>
	  

	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		  	<td >
			    <button type="button" name="save" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveFieldVarCapAtCM();" ><bean:message key="button.save" /></button>
			    <button type="button" name="forward" id="forward" class="topformbutton2" title="Alt+F" accesskey="F" onclick="forwardVerificationCapAtCM('<bean:message key="msg.confirmationForwardMsg" />');" ><bean:message key="button.forward" /></button>
			    <logic:equal name="functionId" value="8000242">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000241">  
	             <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000240">  
	            <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000239">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000238">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000237">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="4000134">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="4000133">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
			    <%-- <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVILM','${sessionScope.dealId}');"><bean:message key="button.upload" /></button>
			    <button type="button" name="download" id="download" class="topformbutton2" title="Alt+D" accesskey="D" onclick="downloadVarificationFormAtCM();" ><bean:message key="button.download" /></button> --%>
		    </td>
		  
		</tr>
	</table>
</fieldset>


<logic:present name="sms">
	<script type="text/javascript">	
		if('<%=request.getAttribute("sms")%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		}
		else if('<%=request.getAttribute("sms")%>'=='N')
		{
			alert("<bean:message key="msg.NonEmiError" />");
		}
    </script>
</logic:present>
<logic:present name="forwardsms">
	<script type="text/javascript">	
		if('<%=request.getAttribute("forwardsms")%>'=='F')
		{
			alert("<bean:message key="msg.ForwardNonEmiSuccessfully" />");
			location.href='<%=request.getContextPath()%>/fieldVarificationCapturingBehindAction.do?method=verificationCapturingCreditManagement';
		}
		else if('<%=request.getAttribute("forwardsms")%>'=='N')
		{
			alert("<bean:message key="msg.ErrorForwardNonEmiLoan" />");
		}
		else if('<%=request.getAttribute("forwardsms")%>'=='T')
		{
			alert("<bean:message key="msg.ErrorSearchReport" />");
		}
    </script>
</logic:present>
</html:form>
 </logic:notPresent>  
 <logic:present name="viewDeal">
 	<html:form styleId="fieldVerificationForm" action="/fieldVerificationSave" method="post" >

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="businessDate" id="businessDate" value="${requestScope.businessDate}" />
<input type="hidden" name="appType" id="appType" value="${requestScope.appType}" />
 	<input type="hidden" name="viewMode" id="viewMode" value="VIEWMODE" />

<fieldset>
<legend><bean:message key="lbl.verificationCapturing" /></legend> 
	<fieldset>
	<legend><bean:message key="lbl.verificationDetails" /></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
  		<html:hidden property="verificationId" styleId="verificationId" styleClass="text" value="${sessionScope.verificationId}" /> 
  		<html:hidden property="entId" styleId="entId" styleClass="text" value="${sessionScope.entityId}" /> 
  		<html:hidden property="fieldVerificationUniqueId" styleId="fieldVerificationUniqueId" styleClass="text" value="${commonList[0].fieldVerificationUniqueId}" /> 
  		
  		<tr>
  			<td width="13%"><bean:message key="lbl.veri.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityType" styleId="entityType" styleClass="text" value="${commonList[0].entityType}" readonly="true"></html:text><br /></td>
   			<td width="13%"><bean:message key="lbl.veri.sub.entityName"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entityDesc" styleId="entityDesc" styleClass="text" value="${commonList[0].entityDesc}" readonly="true"></html:text><br /></td>
   		</tr>	
   		<tr>	
   			
   			<td width="13%"><bean:message key="lbl.veri.sub.entityType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="entitySubType" styleId="entitySubType" styleClass="text" value="${commonList[0].entitySubType}" readonly="true"></html:text><br /></td>
			<td width="13%"><bean:message key="lbl.veri.sub.entityAddress"/><font color="red">*</font></td>
			<td width="13%"><textarea  name="entityAddress" id="entityAddress" readonly="true" class="text" >${commonList[0].entityAddress}</textarea></td>
			 
 		</tr> 
 		<tr>
 		
 		
			<logic:notEqual name="verificationType" value="RCU REPORT">
 			 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("CONTACT VERIFICATION")){ %>
    	  <td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
			<td width="13%"><html:text  property="managementPhone" styleId="managementPhone" readonly="true" styleClass="text" value="${commonList[0].managementPhone}"></html:text></td>
			 <%} %>
			 </logic:notEqual>
			
 		</tr>
  		<tr>
  			<td width="13%"><bean:message key="lbl.varificationType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationType" styleId="varificationType" styleClass="text" value="${commonList[0].verificationType}" readonly="true"></html:text><br /></td>
   			<td width="13%"><bean:message key="lbl.varificationSubType"/><font color="red">*</font></td>
   			<td width="13%"><html:text property="varificationSubType" styleId="varificationSubType" styleClass="text" value="${commonList[0].verificationSubType}" readonly="true"></html:text><br /></td>
			 
 		</tr> 
 		<%-- 
 		<tr>
    		<td><button type="button" name="details" id="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="detailEntity('${sessionScope.entityId}','${sessionScope.verificationId}');" ><bean:message key="button.details" /></button></td></td>
 		</tr> 
 		--%>
 			 <% if (CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
    		<tr>
    		<td>
    		<button type="button" name="details" id="button" class="topformbutton4" title="Alt+V" accesskey="V" onclick="detailSearch('${sessionScope.dealId}','${sessionScope.verificationId}');" ><bean:message key="button.searchbutton" /></button>
    		</td>
    		</tr>
       		<%}} %>
 	</table> 
	</fieldset>
	<fieldset>
	<legend><bean:message key="lbl.verificationInformation"/></legend>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	    <tr>
	    	<logic:present name="apprsName">
	    	 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
		<logic:present name="appraiserType">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${verifList[0].appraisalName}" onblur="return caseChangeAN();"/></td>
    	</logic:present>	 
       <logic:notPresent name="appraiserType">
        <td width="15%">
        <html:text property="appraisalName" styleId="appraisalName" value="${verifList[0].appraisalName}" readonly="true" styleClass="text"  />
			<html:hidden  property="lbxUserId" styleId="lbxUserId"  value="${verifList[0].lbxUserId}" />
<%-- 			<html:button property="internalButton" styleId="internalButton" onclick="openLOVCommon(308,'fieldVerificationForm','appraisalName','','', '','','','internalAppUserId');" value=" " styleClass="lovbutton"> </html:button> --%>
	 	<html:hidden property="internalAppUserId" styleId="internalAppUserId" value="${verifList[0].lbxUseId}" />
		</td>
		
		</logic:notPresent>
		</logic:present>
		<logic:notPresent name="apprsName">
		
		 <td width="13%"><bean:message key="lbl.appraisalName"/><span><font color="red">*</font></span></td>
		<logic:present name="appraiserType">
    		 <td width="13%"><html:text property="appraisalName" styleId="appraisalName" maxlength="50" styleClass="text" value="${commonList[0].appraisalName}" onblur="return caseChangeAN();"/></td>
    	</logic:present>	 
       <logic:notPresent name="appraiserType">
        <td width="15%">
        <html:text property="appraisalName" styleId="appraisalName" value="${commonList[0].appraisalName}" readonly="true" styleClass="text"  />
			<html:hidden  property="lbxUserId" styleId="lbxUserId"  value="${commonList[0].lbxUserId}" />
<%-- 			<html:button property="internalButton" styleId="internalButton"   onclick="openLOVCommon(308,'fieldVerificationForm','appraisalName','','', '','','','internalAppUserId');" disabled="true" value=" " styleClass="lovbutton"> </html:button> --%>
		 	<html:hidden property="internalAppUserId" styleId="internalAppUserId" value="${commonList[0].lbxUseId}" />
		</td>
		
		</logic:notPresent>
		
		</logic:notPresent>
    		 <td width="13%"><bean:message key="lbl.appraisalDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>
			 <td width="13%"><html:text property="appraisalDate"   styleClass="text" styleId="appraisalDate12345" maxlength="10" readonly="true" value="${commonList[0].appraisalDate}" onchange="return checkDate('appraisalDate');"/></td>
	    </tr>
	    <tr>
	    	<td width="13%"><bean:message key="lbl.verificationMode"/><span><font color="red">*</font></span></td>
	    	<td width="13%"><html:select property="verificationMode" styleId="verificationMode" disabled="true" styleClass="text"  value="${commonList[0].verificationMode}">
    									<html:option  value="">----Select----</html:option>
           								<html:option value="P">Phone</html:option>
           								<html:option value="W">Website</html:option>
           								<html:option value="F">Field Visit</html:option>
           								
       						</html:select>
       		</td>
       		
			<logic:notEqual name="verificationType" value="RCU REPORT">
       		 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <td width="13%"><bean:message key="lbl.personToMeet"/><span><font color="red">*</font></span></td>
    		<td width="13%"><html:text property="personToMeet" styleId="personToMeet" maxlength="50" readonly="true" styleClass="text" value="${commonList[0].personToMeet}" onblur="return caseChangePM();"/></td>
    		<%} %>
    		</logic:notEqual>
    		
	    </tr>
	    
	    <!-------------------------------------ADD by sachin------------------------------------	    -->
	
	    <tr id="seenAtAssetOrCollateral" style="display:none">			
		   <td width="13%"><bean:message key="lbl.assetLocation"/><font color="red">*</font>
		   </td>
		   <td width="13%">
		   <html:text styleClass="text" property="assetLocation" styleId="assetLocation"  disabled="true"  maxlength="100" value="${commonList[0].assetLocation}" />
		   </td>
		   <td width="13%">
			<bean:message key="lbl.assetCondition"/><font color="red">*</font> 
		   </td>
	   	   <td width="13%"> 
	   	  	 <html:text styleClass="text" property="assetCondition" maxlength="100" styleId="assetCondition"  disabled="true" value="${commonList[0].assetCondition}" />   
		 </td>
	</tr>
		
	  <tr id="seenAtAstOrCol" style="display:none">
	   
		<td width="13%"><bean:message key="lbl.invoiceCollected"/></td>
		<td width="13%" >
		<html:select property="invoiceCollected" styleClass="text" styleId="invoiceCollected" disabled="true" value="${commonList[0].invoiceCollected}" onchange="InvoColl()">
	            <html:option value="Y">Yes</html:option>
		        <html:option value="N">No</html:option>
		      </html:select> </td>
			
		   <td width="13%"><bean:message key="lbl.invoiceNumber"/></td>
			 <td width="13%">
			 <html:text styleClass="text" property="invoiceNumber" styleId="invoiceNumber" disabled="true"  maxlength="20" value="${commonList[0].invoiceNumber}" /> </td>
			 
		</tr>
		
<!-------------------------------------ADD by sachin------------------------------------	    -->	

			<logic:notEqual name="verificationType" value="RCU REPORT">
	   		 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
	   	<tr>
    		
  		    <td width="13%"><logic:present name="bpType">
  		    <logic:notEqual name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span>
  		    </logic:notEqual>
  		     <logic:equal name="bpType" value="BUL">
  		    <bean:message key="lbl.relationWithBuilder"/><span><font color="red">*</font></span>
  		    </logic:equal>
  		    </logic:present>
  		    <logic:notPresent name="bpType"> 
  		      <bean:message key="lbl.relationWithApplicant"/><span><font color="red">*</font></span>
  		      </logic:notPresent>
  		    </td>
    		<td width="13%"><html:text property="relationWithApplicant" styleId="relationWithApplicant" readonly="true" maxlength="50" styleClass="text" value="${commonList[0].relationWithApplicant}" onblur="return caseChangeRA();"/></td>
    		<td width="13%"><bean:message key="lbl.phone1"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="phone1" styleId="phone1" maxlength="10" styleClass="text" readonly="true" value="${commonList[0].phone1}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
  	  	</tr> 
  	  	<%}} %>
  	  	</logic:notEqual>
  	  
 		<tr>
 		
		<logic:notEqual name="verificationType" value="RCU REPORT">
 		
    			 <% if (!CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("LEGAL VERIFICATION")){ %>
    	  <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION")){ %>
		    <td width="13%"><bean:message key="lbl.phone2"/></td>
    		<td width="13%"><html:text property="phone2" styleId="phone2" maxlength="10" styleClass="text" readonly="true" value="${commonList[0].phone2}" style="text-align: right" onkeypress="return numbersonly(event,id,10)" onkeyup="checkNumber(this.value, event, 10,id);"/></td>
    		
    		<td width="13%"><bean:message key="lbl.email"/></td>
    		<td width="13%"><html:text property="email" styleId="email" maxlength="50" styleClass="text" readonly="true" value="${commonList[0].email}"/></td>
    		<%}} %>
    		</logic:notEqual>
    		
  		</tr>
  		<%-- <tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus" styleId="cpvStatus" styleClass="text" disabled="true" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
    		<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" disabled="disabled"  class="text" >${commonList[0].remarks}</textarea></td>
  		</tr> --%>
  		
  		 	<tr>
  	<td width="13%"><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
    		<td width="13%"><textarea  name="remarks" id="remarks" maxlength="100" class="text"  disabled="disabled" >${commonList[0].remarks}</textarea></td>
  	</tr>
  		<tr>
    		
		    <td width="13%"><bean:message key="lbl.CPVStatus"/><span><font color="red">*</font></span></td>
            <td width="13%"><html:select property="cpvStatus" disabled="true" styleId="cpvStatus" disabled="true" styleClass="text" value="${commonList[0].cpvStatus}" >
           								<html:option  value="">----Select----</html:option>
           								<html:option value="P">Positive</html:option>
           								<html:option value="N">Negative</html:option>
           								<html:option value="R">Refer to Credit</html:option>
       						</html:select>
       		</td> 
		
		
	<logic:equal name="verificationType" value="TECHNICAL VERIFICATION">  	
    		<td width="13%"><bean:message key="lbl.marketValue"/><font color="red">*</font></td>
    		<td width="13%"><html:text  property="marketValue" style="text-align: right" styleId="marketValue" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');"  readonly="true" styleClass="text" value="${commonList[0].marketValue}"></html:text></td>
  		</logic:equal>
		
		</tr>
  	
		
		
			
		<logic:equal name="verificationType" value="TECHNICAL VERIFICATION">  		
  		<tr>
    		
		    <td width="13%"><bean:message key="lbl.govtValue"/><font color="red">*</font></td>
           <td width="13%"><html:text  property="govtValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="govtValue" readonly="true" styleClass="text" value="${commonList[0].govtValue}"></html:text></td>
       		
    		<td width="13%"><bean:message key="lbl.distressValue"/><font color="red">*</font></td>
    		<td width="13%"><html:text property="distressValue" style="text-align: right" onkeypress="return numbersonly(event, id, 22);" onchange="numberFormatting(this.id,'2');" styleId="distressValue" readonly="true"   styleClass="text" value="${commonList[0].distressValue}" ></html:text></td>
  		</tr>
  		</logic:equal>
		
		
	 
 	</table>
	</fieldset>
	<logic:notEqual  name="verificationType" value="RCU REPORT">
	<fieldset>
	<legend><bean:message key="lbl.ver.question"/></legend>
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table id="gridtable" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
 		<td width="10%"><b><bean:message key="lbl.questionNo"/></b></td>
 		<%-- <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION") && !CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("TECHNICAL VERIFICATION")){ %>
 		
 		<td width="10%"><b><bean:message key="lbl.questionSeqNo"/></b></td>
 		<%} %> --%>
        <td width="13%"><b><bean:message key="lbl.ver.question"/></b></td>
        <td width="30%"><b><bean:message key="lbl.queryRemarks"/></b></td>
        <td width="13%"><b><bean:message key="lbl.notificationMethod"/></b></td>
        <td width="10%"><b><bean:message key="lbl.verifQuestRequired"/></b></td>
   
	</tr>
	
	
<logic:present name="questList">
	<logic:iterate name="questList" id="sublist" indexId="counter">
	<tr class="white1">
	<html:hidden property="questionVerificationUniqueId" styleId="questionVerificationUniqueId" styleClass="text" value="${sublist.questVerifUniqueId}" /> 
    <html:hidden property="questionSeqNo" styleId="questionSeqNo" value="${sublist.questSeqNo}"/>
	<html:hidden property="questionId" styleId="questionId" value="${sublist.questId}"/>
	<td width="10%">
		
		${counter+1}
       </td>
      <%--  <% if (!CommonFunction.checkNull(request.getAttribute("verificationSubType")).equalsIgnoreCase("SEARCH REPORT VERIFICATION") && !CommonFunction.checkNull(request.getAttribute("verificationType")).equalsIgnoreCase("TECHNICAL VERIFICATION")){ %>
 		
       	<td width="10%">
		
			${sublist.questSeqNo}
       </td>
       <%} %> --%>
	<td width="13%">
		
		${sublist.quest}
       </td>
      
	
     <td>
     
    	 <textarea cols="100" name="questionRemarks" class="text" id="questionRemarks" readonly="true" style="width: 100%;">${sublist.questRemarks}</textarea>
      </td>
        <td width="13%">
        
         <html:select property="verificationMethod" styleId="verificationMethod" styleClass="text" disabled="true" value="${sublist.questVerifMethod}" >
           		<html:option  value="">----Select----</html:option>
           		<logic:present name="verifMethodList">
           		     <html:optionsCollection name="verifMethodList"  label="name" value="id"/>
           		</logic:present>
       	 </html:select>
     
 
       </td>
       <td width="10%">
		
			${sublist.verifQuestRequired}
       </td>
 
	</tr>
	
	</logic:iterate>
	</logic:present>	
		

 </table>    </td>
</tr>


</table>
	</fieldset>
	</logic:notEqual>
	
	<logic:equal name="verificationType" value="RCU REPORT">
	
	<fieldset>
	
	<html:hidden property="hidRcuStatusString" styleId="hidRcuStatusString" value=""/>
	<html:hidden property="hidRcuCommentString" styleId="hidRcuCommentString" value=""/>
	<html:hidden property="hidDOCIDString" styleId="hidDOCIDString" value=""/>
	<html:hidden property="hidChildIDString" styleId="hidChildIDString" value=""/>
	<legend><bean:message key="lbl.rcu.docs"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTableRCU">
    <tr class="white2">
        <td width="13%"><b><bean:message key="lbl.docEntity"/></b></td>
        <td width="10%"><b><bean:message key="lbl.customer_name"/></b></td>
        <td width="13%"><b><bean:message key="lbl.docStage"/></b></td>
        <td width="10%"><b><bean:message key="lbl.parentDocname"/></b></td>
   		<td width="10%"><b><bean:message key="lbl.childDocname"/></b></td>
   		<td width="5%"><b><bean:message key="lbl.rcu.status"/></b></td>
   		<td width="70%"><b><bean:message key="lbl.Remarks"/></b></td>
	</tr>
<logic:present name="RCUDocs">
	<logic:iterate name="RCUDocs" id="sublist" indexId="counter">
	<tr class="white1">
	
	<html:hidden property="docEntityType" styleId="docEntityType" value="${sublist.docEntityType}"/>
	<html:hidden property="customerName" styleId="customerName${counter+1}" value="${sublist.customerName}"/>
	<html:hidden property="docStage" styleId="docStage${counter+1}" value="${sublist.docStage}"/>
	<html:hidden property="parentDocName" styleId="parentDocName${counter+1}" value="${sublist.parentDocName}"/>
	<html:hidden property="childDocName" styleId="childDocName${counter+1}" value="${sublist.childDocName}"/>
       <td width="10%">
			${sublist.docEntityType}
       </td>
        <td width="10%">
			${sublist.customerName}
       </td>
        <td width="10%">
			${sublist.docStage}
       </td>
        <td width="10%">
			${sublist.parentDocName}
       </td>
        <td width="10%">
			${sublist.childDocName}
       </td>
        <td width="5%">
         <html:select property="RCUStatus" styleId="RCUStatus${counter+1}" styleClass="text" onchange="parentDocWithRCU();" disabled="true" value="${sublist.RCUStatus}" >
           		<logic:present name="verifMethodListRCU">
           		     <html:optionsCollection name="verifMethodListRCU"  label="name" value="id"/>
           		</logic:present>
       	</html:select>
		<html:hidden property= "parentDocID" styleId="parentDocID${counter+1}" value="${sublist.parentDocID}" />
		<html:hidden property= "childDocID" styleId="childDocID${counter+1}" value="${sublist.childDocID}" />
       </td>
         <td width="10%">
    	 <textarea name="RCURemarks" class="text" id="RCURemarks${counter+1}" style="width: 100%;" disabled="disabled" onblur="parentDocWithRCU();" maxlength="5000" onkeyup="return imposeMaxLength(this, 5000);" >${sublist.RCURemarks}</textarea>
    	 <html:hidden property="parentDocIDD" styleId="parentDocIDD" styleClass="text" value="${sublist.parentDocID}" /> 
    	  <html:hidden property="childDocIDD" styleId="childDocIDD" styleClass="text" value="${sublist.childDocID}" /> 
      </td>
	</tr>
	</logic:iterate>
	</logic:present>	
 </table>    </td>
</tr>
	</table>
	</fieldset>
	</logic:equal>
	
	
	
</fieldset>
<%-- <fieldset>	

		 <legend><bean:message key="lbl.docInformation"/></legend> 

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	 
        <td><b><bean:message key="lbl.fileName"/></b></td>
		<td ><b><bean:message key="lbl.docDescription"/></b></td>
        <td ><b><bean:message key="lbl.uploadedBy" /></b></td>
        </tr>
        <logic:present name="uploadedDocList">
        <logic:notEmpty name="uploadedDocList">
        <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="count">    	
    <tr  class="white1">

     	<td><a href="#" onclick="downloadFVIFile('${uploadedDocSubList.lbxDocId}','fieldVerificationForm','${uploadedDocSubList.stage}','${count}');">${uploadedDocSubList.fileName}</a>
     		<input type="hidden" name="lbxDocId" id="lbxDocId" value="${uploadedDocSubList.lbxDocId}"/>
     		<input type="hidden" name="dmsDocId" id="dmsDocId_${count}" value="${uploadedDocSubList.dmsDocId}"/>
			<input type="hidden" name="dmsDocUrl" id="dmsDocUrl_${count}" value="${uploadedDocSubList.dmsDocUrl}"/>
     	</td>
        <td>${uploadedDocSubList.docDescription}</td>
        <td>${uploadedDocSubList.userName}</td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
        </logic:present>
    </table>
    </td>
</tr>
</table>
</fieldset> --%>
<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
		  	<td >
		  	<logic:equal name="functionId" value="8000242">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000241">  
	             <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000240">  
	            <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000239">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','TVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000238">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	           <logic:equal name="functionId" value="8000237">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','LVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="4000134">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVILM','${sessionScope.dealId}','LIM');"><bean:message key="lbl.download" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="4000133">  
	          <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.upload" /></button>
	          </logic:equal>
	          <logic:equal name="functionId" value="4001231">  
	          <logic:equal name="verificationType" value="RCU REPORT">
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','RVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.download" /></button>
	           </logic:equal>
	           <logic:notEqual name="verificationType" value="RCU REPORT">
	           <button type="button" name="upload" class="topformbutton2" id="upload" title="Alt+U" accesskey="U" onclick="submitFVCDoc('${commonList[0].fieldVerificationUniqueId}','FVILM','${sessionScope.dealId}','LIM');"><bean:message key="button.download" /></button>
	           </logic:notEqual>
	          </logic:equal>
		    <%--  <button type="button" name="download" id="download" class="topformbutton2" title="Alt+D" accesskey="D" onclick="downloadVarificationFormAtCM();" ><bean:message key="button.download" /></button> --%>
		    </td>
		  
		</tr>
	</table>

</html:form>
 	
 </logic:present>
 </logic:present>
 
  </body>
</html:html>