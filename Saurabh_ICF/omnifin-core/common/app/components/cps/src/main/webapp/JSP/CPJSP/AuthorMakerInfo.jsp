<%@page import="java.util.ResourceBundle"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp"%> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%-- <%@include file="/JSP/OCRJSP/DocumentUploadAuthorBalanceSheet.jsp"%>
<%@include file="/JSP/OCRJSP/DocumentUploadAuthorProfitAndLoss.jsp"%> --%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	    <meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval()%>;url= <%=request.getContextPath()%>/logoff.do" />
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
		
		<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/applicant.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/uploadViewer.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/cpScript/upload.js"></script> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>  --%>
   
   
   		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    	 <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/contentVerification.js"></script>
   
   <%
			ResourceBundle resource =  ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
		%>
   
   
    </head>
	
	
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('authormakerinfo');document.getElementById('authormakerinfo').decision.focus();init_fields();">
	<div id="centercolumn">
	<div id="revisedcontainer">
	
<html:form action="/authormakerinfo" method="post" styleId="authormakerinfo">
	
	<fieldset>
	
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="businessDate" value="${requestScope.businessDate}" />
		<input type="hidden" id="makerDate" value="${requestScope.makerDate}" />
		<input type="hidden" id="checkFlag" value="${requestScope.checkFlag}" />
		<table width="100%" border="0" cellspacing="5" cellpadding="4">
		<input type="hidden" name="dealId" id="dealId" value="${caseId}"/>
	
			<legend><bean:message key="lbl.Docmaker"></bean:message></legend>
			<table width="100%"  border="0" cellspacing="5" cellpadding="2" >
				<tr>
					<td width="20%"><bean:message key="lbl.referenceNo"/></td>
					<td width="35%" valign="top">
					<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
					<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" tabindex="-1" value="${viewList[0].refId}"/>
					<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${sessionScope.caseId}" /> 
	  				</td>
	  				  	
		  			<td width="13%"><bean:message key="lbl.customerName"/></td>
					<td width="13%" ><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="50" readonly="true" value="${viewList[0].customerName}" onchange="upperMe('customerName');"/></td>
	    		</tr>
	    		<tr>
	    		<td width="20%"><bean:message key="lbl.docEntity"/></td>
	    		<td><html:text property="docEntity" styleClass="text" readonly="true" styleId="docEntity" value="${viewList[0].docEntity}" /></td>
	    		
	    		</tr>
			</table>
			
			</table>
</fieldset>

				
	  <fieldset>	
		 <legend><bean:message key="lbl.docInformation"/></legend> 
      	 
      	 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		 <tr>

    	 <td class="gridtd" >
         <table width="100%" border="0" cellspacing="1" cellpadding="3">
	     <tr class="white2">
    	
	       <td><b><center><bean:message key="lbl.fileName"/></center></b></td>
		   <td ><b><center><bean:message key="lbl.docEntity"/></center></b></td>
	       <td ><b><center><bean:message key="lbl.uploadedBy" /></center></b></td>
	       <td ><b><center><bean:message key="lbl.remark" /></center></b></td>
	     </tr>
       
	        <logic:present name="uploadedDocList">
	        <logic:notEmpty name="uploadedDocList">
	        <logic:iterate name="uploadedDocList" id="uploadedDocSubList" indexId="i">
	    	<tr class="white1">
	 
	 		<input type="hidden" name="lbxDealNo" id="lbxDealNo" value="${sessionScope.caseId}"/>
	     	<input type="hidden" name="dealNoArr" id="<%="dealNo"+i %>" value="${uploadedDocSubList.dealId}"/> 
	   		
	    	<input type="hidden" name="docEntity" id="docEntity" value="${uploadedDocSubList.docEntity}"/>
	       	<input type="hidden" name="docId" id="docId" value="${uploadedDocSubList.docId}"/>
	       	<td>${uploadedDocSubList.fileName}
	     	
	     	</td> 
	        <td>${uploadedDocSubList.descriptionValue}</td>
	        <td>${uploadedDocSubList.userName}</td>
	        <logic:notEqual name="uploadedDocSubList" property="docType" value="ANU">
		        <logic:notEqual name="uploadedDocSubList" property="docType" value="CO">
		       	<td><center><b><button type="button" id="remark" class="topformbutton2"  title="Alt+U" accesskey="U" onclick="return captureFromAuthor(${uploadedDocSubList.docId},'${uploadedDocSubList.docType}',${caseId} );" tabindex="4" ><bean:message key="button.remark" /></button></b></center></td>
		       </logic:notEqual>
	       			<logic:equal name="uploadedDocSubList" property="docType" value="CO">
	       			<td></td>
	       		</logic:equal>
	       	</logic:notEqual>
	       	<logic:equal name="uploadedDocSubList" property="docType" value="ANU">
	       	<td></td>
	       	</logic:equal>
	   	    </tr>
	        
	        </logic:iterate>
		  	</logic:notEmpty>
		  	</logic:present>
    	
    	</table>
    	</td>
		</tr>
		</table>
	</fieldset>

 
</html:form>
</div>
</div>

<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("closureAuthorForm");
</script>
</body>
</html>
<logic:present name="message">
 <script type="text/javascript">
 if(document.getElementById("closureStatus").value=='T')
 {
	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosureAuthor";
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<%=request.getAttribute("status")%>');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosureAuthor";
	}
}

if(document.getElementById("closureStatus").value=='C')
 {
	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosureAuthor";
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<%=request.getAttribute("status")%>');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosureAuthor";
	}
}

if(document.getElementById("closureStatus").value=='X')
 {
	if('<%=request.getAttribute("message")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=cancellationClosureAuthor";
	}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<%=request.getAttribute("status")%>');
		parent.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=cancellationClosureAuthor";
	}
}
</script>
</logic:present>
</html:html>
