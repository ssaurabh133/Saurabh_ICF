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
		 
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanInitiation.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
         <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
         <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript">
	function saveComment()
	{
	    DisButClass.prototype.DisButMethod();
		if(document.getElementById("remarks").value==''&& !(document.getElementById("recStatus").value=='A')) //Edited by Nishant
		{
			alert("remarks is Required");
			DisButClass.prototype.EnbButMethod();
			
			//document.getElementById("save").removeAttribute("disabled","true");
			document.getElementById("remarks").focus();
			return false;
		}
		else
		{
			var contextPath=document.getElementById("contextPath").value;
		    document.getElementById("approveLoanRSPAuthorForm").action = contextPath+"/repaymentServiceActionForPartnerDetail.do?method=rspServiceApproval";
		    document.getElementById("processingImage").style.display = '';
		    document.getElementById("approveLoanRSPAuthorForm").submit();
		}
	}
	function starStatus()
	{
		var decision=document.getElementById("recStatus").value;
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
	
	<body onload="enableAnchor();checkChanges('approveLoanRSPAuthorForm');">
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
	          <td >RSP Repayment Schedule Capturing Approval</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>	
	
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/repaymentServiceActionForPartnerDetail" method="post" styleId="approveLoanRSPAuthorForm">
 
  <fieldset>
  <legend><bean:message key="lbl.author" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		<html:hidden property="loanId" styleId="loanId" value="${sessionScope.loanId}"/>
		
		
		<tr>
					
		   <td width="20%" id="star"  style="display: none"><bean:message key="lbl.remarks"/><font color="red">*</font></td>
		   <td width="20%" id="wstar"><bean:message key="lbl.remarks"/></td>
			 <td width="35%">
			   <html:textarea property="remarks" styleClass="text" styleId="remarks" value=""></html:textarea>
			</td>
		
		<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td >
		   <html:select property="recStatus" styleId="recStatus" styleClass="text" onchange="starStatus()">
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
<script>
	setFramevalues("approveLoanRSPAuthorForm");
</script>
</body>
</html>

<logic:present name="message">
<script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='S')
	{
		alert("<bean:message key="msg.approvedSuccessfully" />");
		parent.location='<%=request.getContextPath()%>/repaymentServiceAction.do?method=openSearchPageForRepaymentService';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='X')
	{
		alert("<bean:message key="msg.Datareject" />");
		parent.location='<%=request.getContextPath()%>/repaymentServiceAction.do?method=openSearchPageForRepaymentService';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='P')
	{
		alert("<bean:message key="msg.sendBackSuccessfully" />");
		parent.location='<%=request.getContextPath()%>/repaymentServiceAction.do?method=openSearchPageForRepaymentService';
	}
	else if('<%=request.getAttribute("message").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location='<%=request.getContextPath()%>/repaymentServiceAction.do?method=openSearchPageForRepaymentService';
	}
	else
	{
		alert('<%=request.getAttribute("message").toString()%>');
	}


</script>

</logic:present>

