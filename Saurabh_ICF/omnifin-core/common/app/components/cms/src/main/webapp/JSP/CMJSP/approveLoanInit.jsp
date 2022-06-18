<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
         <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
         <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript">
	function saveComment()
	{
	    DisButClass.prototype.DisButMethod();
		if(document.getElementById("comments").value==''&& !(document.getElementById("decision").value=='A')) //Edited by Nishant
		{
			alert("Comments is Required");
			DisButClass.prototype.EnbButMethod();
			
			//document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("comments").focus();
			return false;
		}
		else
		{
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("approveLoanAuthorForm").action = contextPath+"/approveLoanAction.do";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("approveLoanAuthorForm").submit();
		}
	}
	function starStatus()
	{
		var decision=document.getElementById("decision").value;
		if(decision!='A')
		{
			document.getElementById("star").style.display="";
			document.getElementById("wstar").style.display="none";
		}
		else
		{
			document.getElementById("star").style.display="none";
			document.getElementById("wstar").style.display="";
		}
	}
	</script>	
	
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
	
	<body onload="enableAnchor();checkChanges('approveLoanAuthorForm');">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
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
	          <td >Loan Initiation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>	
	
	
	
	<logic:notPresent name="maker">
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/approveLoanAction" method="post" styleId="approveLoanAuthorForm">
 
  <fieldset>
  <legend><bean:message key="lbl.author" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<html:hidden property="loanId" styleId="loanId" value="${sessionScope.loanId}"/>
		
		
		<tr>
					
		   <td width="20%" id="star"  style="display: none"><bean:message key="lbl.comments"/><font color="red">*</font></td>
		   <td width="20%" id="wstar"><bean:message key="lbl.comments"/></td>
			 <td width="35%">
			   <html:textarea property="comments" styleClass="text" styleId="comments" value="${creditApprovalList[0].remarks }"></html:textarea>
			</td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="decision" styleId="decision" styleClass="text" onchange="starStatus()">
		     <html:option value="A">Approved</html:option>
		     <html:option value="X">Rejected</html:option>
		     <html:option value="P">Send Back</html:option>
             
		     </html:select>
		     </td>
		</tr> 		  
		<tr>
	      <td>
	     <!--<html:button property="save" onclick="this.disabled='true';saveComment();"  styleClass="topformbutton2" value="Save" />  --> 
	       <button type="button" class="topformbutton2" name="save"  onclick="return saveComment();"
 title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
	      
	      </td>
		</tr>
		</table>
		  	
	  </fieldset>

  </html:form>
</div>

</div>
</logic:notPresent>
<script>
	setFramevalues("approveLoanAuthorForm");
</script>
</body>
</html>
<logic:present name="countSms">
 <script type="text/javascript">
	if("<%=request.getAttribute("countSms")%>"=="NA")
	{
		alert("There are some document left to receive ");
		
	}
	
	</script>
</logic:present>
<logic:present name="message">
<logic:notPresent name="EDIT">
<script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.approveloan" />");
		parent.location='<%=request.getContextPath()%>/JSP/CMJSP/loanInitAuthorSearch.jsp';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='X')
	{
		alert("<bean:message key="lbl.rejectloan" />");
		parent.location='<%=request.getContextPath()%>/JSP/CMJSP/loanInitAuthorSearch.jsp';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='P')
	{
		alert("<bean:message key="lbl.revertloan" />");
		parent.location='<%=request.getContextPath()%>/JSP/CMJSP/loanInitAuthorSearch.jsp';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location='<%=request.getContextPath()%>/JSP/CMJSP/loanInitAuthorSearch.jsp';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='UT')
	{
		alert("<bean:message key="lbl.utiLizedAmount" />");
		parent.location='<%=request.getContextPath()%>/JSP/CMJSP/loanInitAuthorSearch.jsp';
	}
	else
	{
		alert('${message}');
		parent.location='<%=request.getContextPath()%>/JSP/CMJSP/loanInitAuthorSearch.jsp';
	}
</script>
</logic:notPresent>
<logic:present name="EDIT">
<script type="text/javascript">
if('<%=request.getAttribute("message").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.approveloan" />");
		parent.location='<%=request.getContextPath()%>/editLoanDetailAuthor.do?method=defaultEditLoanAuthor';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='X')
	{
		alert("<bean:message key="lbl.rejectloan" />");
		parent.location='<%=request.getContextPath()%>/editLoanDetailAuthor.do?method=defaultEditLoanAuthor';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='P')
	{
		alert("<bean:message key="lbl.revertloan" />");
		parent.location='<%=request.getContextPath()%>/JSP/CMJSP/loanInitAuthorSearch.jsp';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location='<%=request.getContextPath()%>/editLoanDetailAuthor.do?method=defaultEditLoanAuthor';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='UT')
	{
		alert("<bean:message key="lbl.utiLizedAmount" />");
		parent.location='<%=request.getContextPath()%>/editLoanDetailAuthor.do?method=defaultEditLoanAuthor';
	}
	else
	{
		alert('${message}');
		parent.location='<%=request.getContextPath()%>/editLoanDetailAuthor.do?method=defaultEditLoanAuthor';
	}
  </script>
</logic:present>
</logic:present>

