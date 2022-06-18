<!-- 
Author Name :- Nazia
Date of Creation :3-04-2013
Purpose :-  screen for the legal decline
-->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalNoticeInitiationMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('legalNoticeInitiationMakerForm').loanNo.focus();
   return true;
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

<body onload="fntab();init_fields();">
<html:form styleId="legalNoticeInitiationMakerForm"  method="post"  action="/legalNoticeInitiationMakerAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.legalNoticeInitiationMaker" /></legend>
  <table width="100%">
    	<tr>
    	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
    	 <input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
		<td width="13%"><bean:message key="lbl.loanNo" /></td>
		
			<td>
			<html:text property="loanNo" styleId="loanNo" value="${sessionScope.declineDispatchList[0].loanNo}" styleClass="text" readonly="true" tabindex="-1"/>
			<html:hidden  property="lbxLoanId" styleId="lbxLoanId" value="${sessionScope.declineDispatchList[0].lbxLoanId}"  />
		
			<input type="hidden" name="customerName" id="customerName"/>
			</td> 
			<td width="13%"><bean:message key="lbl.customerID" /></td>
			<td>
			<html:text property="customerId" styleId="customerId" value="${sessionScope.declineDispatchList[0].customerId}" styleClass="text" readonly="true" tabindex="-1"/>
			<html:hidden  property="lbxcustomerId" styleId="lbxcustomerId" value="${sessionScope.declineDispatchList[0].lbxcustomerId}"  />
			
		</td>	
				 </tr>
    
    	<tr>																				
		<td width="13%"><bean:message key="lbl.notice" /></td>
	
		
		<td>
			
			<html:hidden  property="noticeId" styleId="noticeId" value="${sessionScope.declineDispatchList[0].noticeId}"  />
			<html:text property="noticeDesc" styleId="noticeDesc" value="${sessionScope.declineDispatchList[0].noticeDesc}" styleClass="text" readonly="true" tabindex="-1"/>
			<html:hidden  property="lbxNoticeCode" styleId="lbxNoticeCode" value="${sessionScope.declineDispatchList[0].lbxNoticeCode}"  />
			</td>
		     
   
		<td width="13%"><bean:message key="lbl.legalReason" /></td>
		<td>
		<%-- <html:text property="reasonDesc" styleId="reasonDesc" value="${sessionScope.declineDispatchList[0].reasonDesc}" styleClass="text"  tabindex="-1"/> --%>
		<%-- <html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${sessionScope.declineDispatchList[0].lbxReasonId}"  /> --%>
		<html:text property="reasonDesc" styleId="reasonDesc" value="${sessionScope.declineDispatchList[0].reasonDesc}" styleClass="text"  tabindex="-1"/>
		</td>   
		 </tr>
    
    	<tr>
		<td width="13%"><bean:message key="lbl.remarks" /></td>
		<td width="13%">
		<textarea name="makerRemarks" id="makerRemarks" readonly="readonly">${sessionScope.declineDispatchList[0].makerRemarks}</textarea>
		</td>      
    
		<td width="13%"><bean:message key="lbl.noticeAmount" /></td>
		<td width="13%"><html:text property="noticeAmount" styleClass="text" styleId="noticeAmount" value="${sessionScope.declineDispatchList[0].noticeAmount}" readonly="true"/>
		</td>  
		</tr>
    
    	<tr>
		<td width="13%"><bean:message key="lbl.modeOfNotice" /></td>
		<td>
		<html:select property="modeOfNotice" styleId="modeOfNotice" styleClass="text" value="${sessionScope.declineDispatchList[0].modeOfNotice}" disabled="true" >
			<html:option value="EMAIL">Email</html:option>
			<html:option value="POSTAL">Postal</html:option>
		</html:select> 
		</td>     
    
		<td width="13%"><bean:message key="lbl.dateOfConciliation" /></td>
	
		<td width="13%"><html:text property="dateOfConciliation" styleClass="text3" styleId="dateOfConciliation" value="${sessionScope.declineDispatchList[0].dateOfConciliation}" readonly="true"/>
		<html:text property="timeOfConciliation" styleClass="text5" styleId="timeOfConciliation" value="${sessionScope.declineDispatchList[0].timeOfConciliation}" readonly="true"/>&nbsp;(24hrs Format)
		</td> 
		</tr>
    
    	<tr>
		<td width="13%"><bean:message key="lbl.venueOfConciliation" /></td>
		<td width="13%"><html:text property="venueOfConciliation" styleClass="text" styleId="venueOfConciliation" value="${sessionScope.declineDispatchList[0].venueOfConciliation}" readonly="true"/>
		</td>   
		    
   
		
		
		<td width="13%"><bean:message key="lbl.nameOfConciliator" /></td>
		<td width="13%"><html:text property="nameOfConciliator" styleClass="text" styleId="nameOfConciliator" value="${sessionScope.declineDispatchList[0].nameOfConciliator}" readonly="true"/>
		</td> 
		 </tr>
    
    	<tr>
		<td width="13%"><bean:message key="lbl.designationOfConciliator" /></td>
		<td width="13%"><html:text property="designationOfConciliator" styleClass="text" styleId="designationOfConciliator" value="${sessionScope.declineDispatchList[0].designationOfConciliator}" readonly="true"/>
		</td>  
		
		     
    </tr>
	

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerBehind.do";
	    document.getElementById("legalNoticeInitiationMakerForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerBehind.do";
	    document.getElementById("legalNoticeInitiationMakerForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
//	{
//		alert("<bean:message key="msg.notepadError" />");
//	}
//	else
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>