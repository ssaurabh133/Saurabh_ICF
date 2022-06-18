<!-- 
Author Name :- Richa
Date of Creation :15-0-4-2013
Purpose :-  screen for Case Marking Checker
-->

<%@ page language="java"%>
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
		 
		 

	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/RepoBillingApprovalMaker.js"></script>
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
	<body onload="enableAnchor();">

	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/repoBillingApprovalAuthorDispatch" method="post" styleId="repoBillingApprovalAuthorForm">
 
   <fieldset>
  
  <legend><bean:message key="lbl.repoBillingApprovalAuthor"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		
		<logic:present name="list">
<logic:notEmpty name="list">
	 
	<logic:iterate name="list" id="sublist" indexId="counter">
	<input type="hidden" name="caseId"  id="caseId<%=counter.intValue()+1%>" value="${sublist.caseId}" />
	</logic:iterate>
		</logic:notEmpty>
		</logic:present>
		
		<tr>
			 <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${sessionScope.sessionrepolist[0].lbxDealNo}"/>
			 <html:hidden  property="interestStopped" styleId="interestStopped" value="${sessionScope.sessionrepolist[0].interestStopped}"/>
			 <html:hidden  property="billingStopped" styleId="billingStopped" value="${sessionScope.sessionrepolist[0].billingStopped}"/>
			 <html:hidden  property="SDInterest" styleId="SDInterest" value="${sessionScope.sessionrepolist[0].SDInterest}"/>
			 	
		   <td width="20%"><bean:message key="lbl.comments" /><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
			 <td width="35%"><div style="float:left;">
			
			  <textarea name="comments" id="comments" maxlength="1000" ></textarea>
			  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
			  </div></td>
		
		<td width="17%">&nbsp;</td>
		
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"></bean:message></td>
		 <td  id=""><span style="float: left;">
		   <html:select property="decision" styleId="decision" onchange="hideAsterik(document.getElementById('decision').value)">
		     <html:option value="A">Approved</html:option>
             <html:option value="X">Rejected</html:option>
             <html:option value="P">Send Back</html:option>
		     </html:select>
		 </span></td>
		</tr> 		  
		<tr>
	        <td> 
           
	       <button type="button" class="topformbutton2" id="save" onclick="return saveRepoBillingApprovalChecker('<bean:message key="msg.plsSelReqField" />');"  title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	       
	       
	       </td>

		
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>

  </html:form>


</div>

</div>

<logic:present name="sms">
 <script type="text/javascript"><!--
 

	 if('<%=request.getAttribute("sms")%>'=='S')
	{
		if('<%=request.getAttribute("decision")%>'=='A')
			{
			alert("<bean:message key="msg.CaseAppSucc" />");
			
			parent.location='<%=request.getContextPath()%>/repoBillingApprovalAuthorDispatch.do?method=search';
	    	
			}
			if('<%=request.getAttribute("decision")%>'=='X')
			{
			alert("<bean:message key="msg.CaseReject" />");
			parent.location='<%=request.getContextPath()%>/repoBillingApprovalAuthorDispatch.do?method=search';
	    
			}
			if('<%=request.getAttribute("decision")%>'=='P')
			{
			alert("<bean:message key="msg.CaseSendBck" />");
			parent.location='<%=request.getContextPath()%>/repoBillingApprovalAuthorDispatch.do?method=search';
	    	
		   }
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert("<bean:message key="lbl.NonDataSave" />");
		//document.getElementById("caseMarkingMakerForm").action="caseMarkingMakerDispatch.do?method=search ";
	    //document.getElementById("caseMarkingMakerForm").submit();
	}
	
  </script>
</logic:present>
		<logic:present name="datalist">
 <script type="text/javascript"><!--
	
		alert("<bean:message key="lbl.dataAlreadyMarked" />");
	
  </script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("caseCheckerAuthorForm");
</script>
</body>
</html:html>