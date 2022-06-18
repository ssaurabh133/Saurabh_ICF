<%@page import="java.util.ResourceBundle"%>
<%@page import="com.cp.vo.UnderwritingDocUploadVo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		 
		 
		 
		 <script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanClosure.js"></script> --%>
    	 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    	 <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/uploadAuthor.js"></script>
    	 <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency-1.3.0.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.formatCurrency.all.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/individualFinancialAnalysis.js"></script> 
    	 

		


	</head>
	<body oncontextmenu="return false" onload="enableAnchor();checkChanges('closureAuthorForm');document.getElementById('closureAuthorForm').decision.focus();init_fields();">
	<input type="hidden" name="<%= org.apache.struts.taglib.html.Constants.TOKEN_KEY %>" value="<%= session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>" >

      <%
			ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
			int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
			request.setAttribute("no",no);

		%>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/authorForOcr" method="post" styleId="closureAuthorForm">
	
	
	 
 <fieldset>
  <legend><bean:message key="lbl.author" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<input type="hidden" id="businessDate" value="${requestScope.businessDate}" />
		<input type="hidden" id="makerDate" value="${requestScope.makerDate}" />
		<input type="hidden" id="checkFlag" value="${requestScope.checkFlag}" />
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<html:hidden property="closureStatus" styleId="closureStatus" value="${sessionScope.closureStatus}"/>
		<html:hidden property="dealId" styleId="dealId" value="${sessionScope.caseId}"/>
		 
  		 
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" onchange="" styleId="decision" styleClass="text" onchange="hideAsterik(value)" ><font color="red">*</font>
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
             
		     </html:select>
		     </td>
		     
		      <td><bean:message key="lbl.stage" /></td>
		 <td >
		   <html:select property="stage" onchange="" styleId="stage" styleClass="text" disabled="true" >
		    <option value="">--Select--</option>
		    <logic:present name="stagemovement" >
				<logic:notEmpty name="stagemovement" >	    
   					<html:optionsCollection name="stagemovement" label="stageDesc" value="stageCode"  />
				</logic:notEmpty>	
   				</logic:present>
             
		     </html:select>
		     </td>
		</tr> 
		<tr>
					
		   <td><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td>
			   <html:textarea property="comments" styleClass="text" styleId="comments" value=""></html:textarea>
			</td>
		</tr>
				  
		<tr>
	      <td><button type="button" name="save"  class="topformbutton2" value="Save" onclick="return saveClosureAuthor(decision);" id="save" accesskey="V" title="Alt+V" ><bean:message key="button.save" /></button></td>
		</tr>
		
		
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

<logic:present name="msg">

 <script type="text/javascript">
	
 if('<%=request.getAttribute("msg").toString()%>'=='G')
	{
		
	 	alert('<bean:message key="msg.dataforwarded" />');
	 	
	 	parent.location.href ="<%=request.getContextPath()%>/documentUploadAuthor.do?";
		
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='L')
	{
	 	alert('<bean:message key="msg.datasendBack" />');
	 	parent.location.href ="<%=request.getContextPath()%>/documentUploadAuthor.do?";
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='X')
	{
	 	alert('<bean:message key="msg.datareject" />');
	 	parent.location.href ="<%=request.getContextPath()%>/documentUploadAuthor.do?";
	}
 else if('<%=request.getAttribute("msg").toString()%>'=='F')
	{
	 	//alert('<bean:message key="msg.checkdata" />');
	 	
	 	alert('${ansNotMatch}');
	 	parent.location.href ="<%=request.getContextPath()%>/documentUploadAuthor.do?";
	}
 
 
 
</script>
</logic:present>

<logic:present name="back">

 <script type="text/javascript">
	 	alert('<bean:message key="msg.captureRemarks" />');
	 	parent.location.href ="<%=request.getContextPath()%>/authorForOcr.do?method=getDocumentForOCR&dealId="+'${caseId}';
</script>
</logic:present>
<logic:present name="checkStageM">

 <script type="text/javascript">
	if('<%=request.getAttribute("checkStageM").toString()%>'=='checkStageM')
	{
		alert('${checkStageM}');
	}
</script>
</logic:present>

