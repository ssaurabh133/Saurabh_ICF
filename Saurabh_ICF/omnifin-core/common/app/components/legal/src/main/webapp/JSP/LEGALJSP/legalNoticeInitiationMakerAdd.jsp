<!--Author Name : Vinod Kumar Gupta-->
<!--Date of Creation : 09-04-2013-->
<!--Purpose  : For Notice-->
<!--Documentation : -->

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
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

<script type="text/javascript">
function fntab()
{
   document.getElementById('loanNoButton').focus();
   return true;
}

$(function() {
		$("#dateOfConciliation").datepicker({
				changeMonth: true,
			changeYear: true,
            yearRange: '1900:+10',
            showOn: 'both',
				<logic:present name="image">
  	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
          </logic:present>
  		<logic:notPresent name="image">
  			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
   		</logic:notPresent>
				buttonImageOnly: true,
				dateFormat:"<bean:message key="lbl.dateFormat"/>",
				defaultDate:'${userobject.businessdate }'
})
});

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

<body onload="enableAnchor();fntab();">
<html:form styleId="legalNoticeInitiationMakerForm"  method="post"  action="/legalNoticeInitiationMakerAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.legalNoticeInitiationMaker" /></legend>
  <table width="100%">
  	<tr>
  	 
		<td><bean:message key="lbl.loanNo" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="loanNo" styleId="loanNo" value="${requestScope.list[0].loanNo}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxLoanId" styleId="lbxLoanId" value="${requestScope.list[0].lbxLoanId}"  />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(1517,'legalNoticeInitiationMakerForm','loanNo','','lbxLoanId','','','','customerName');clearFnUpdateLegalNoticeInitiationMaker();" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name="customerName" id="customerName"/>
		</td> 
			<td width="13%"><bean:message key="lbl.customerName" /><span><font color="red">*</font></span></td>
		
			
         <td>
            <html:text property="customerId" styleClass="text" styleId="customerId" maxlength="100" readonly="true" value="${requestScope.list[0].customerId}" tabindex="1"/>
			<html:hidden  property="lbxcustomerId" styleId="lbxcustomerId" value="${requestScope.list[0].lbxcustomerId}"/>
			<html:button property="custButton" style="custButton"  onclick="closeLovCallonLov('loanNo');openLOVCommon(17003,'legalNoticeInitiationMakerForm','customerId','loanNo','lbxcustomerId', 'lbxLoanId','Please select Loan No!!!','','customerName')" value=" " styleClass="lovbutton"></html:button>
	   
	        </td> 
			<tr>																				
		<td><bean:message key="lbl.notice" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="noticeDesc" styleId="noticeDesc" value="${requestScope.list[0].noticeDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxNoticeCode" styleId="lbxNoticeCode" value="${requestScope.list[0].lbxNoticeCode}"  />
	<%-- 	<html:button property="noticeButton" styleId="noticeButton" onclick="closeLovCallonLov1();openLOVCommon(1521,'legalNoticeInitiationMakerForm','noticeDesc','','','','','','lbxNoticeCode');" value=" " styleClass="lovbutton"> </html:button> --%>
	<html:button property="noticeButton" styleId="noticeButton" onclick="closeLovCallonLov1();openLOVCommon(1521,'legalNoticeInitiationMakerForm','noticeDesc','','reasonDesc','','','','lbxNoticeCode','noticeDesc')" value=" " styleClass="lovbutton"> </html:button>
		</td> 
    
   
    
   
		<td><bean:message key="lbl.legalReason" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:text property="reasonDesc" styleId="reasonDesc" value="${requestScope.list[0].reasonDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${requestScope.list[0].lbxReasonId}"  />
 		<%--	<html:button property="reasonButton" styleId="reasonButton" onclick="closeLovCallonLov1();openLOVCommon(1520,'legalNoticeInitiationMakerForm','reasonDesc','','','','','','lbxReasonId');" value=" " styleClass="lovbutton"> </html:button>--%>
	<html:button property="lovButton" value=" " styleClass="lovbutton" property="reasonButton" styleId="reasonButton" onclick="closeLovCallonLov('noticeDesc');openLOVCommon(1520,'legalNoticeInitiationMakerForm','reasonDesc','noticeDesc','lbxReasonId', 'lbxNoticeCode','Please select Notice!!!','','lbxReasonId');"/>
	
	</td>   
		</tr>
		<tr>
		<td><bean:message key="lbl.remarks" /></td>
		
		<td><textarea class="text" name="makerRemarks" id="makerRemarks" maxlength="500" >${requestScope.list[0].makerRemarks}</textarea></td>
	   
   
		<td><bean:message key="lbl.noticeAmount" /></td>
		
		<td><html:text property="noticeAmount"  style="text-align: right" styleId="noticeAmount" maxlength="18" value="${requestScope.list[0].noticeAmount}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>  
		 </tr>
    
    <tr>
		<td><bean:message key="lbl.modeOfNotice" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:select property="modeOfNotice" styleId="modeOfNotice" styleClass="text" value="${requestScope.list[0].modeOfNotice}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="EMAIL">Email</html:option>
			<html:option value="POSTAL">Postal</html:option>
		</html:select> 
		</td>  
		   
    
    
		<td><bean:message key="lbl.dateOfConciliation" /></td>
		
		 <td>
		 <html:text property="dateOfConciliation" styleClass="text3" styleId="dateOfConciliation" maxlength="10"  value="${requestScope.list[0].dateOfConciliation}" onchange="checkDate('dateOfConciliation');"/>
		 <html:text property="timeOfConciliation"  styleId="timeOfConciliation" onchange="validateTime('timeOfConciliation');" maxlength="5"  styleClass="text5" value="${requestScope.list[0].timeOfConciliation}" />&nbsp;(24hrs Format)
         </td>
        </tr>
    
    <tr>   
         <td><bean:message key="lbl.venueOfConciliation" /></td>
		 <td><html:text property="venueOfConciliation" styleClass="text" styleId="venueOfConciliation" maxlength="50" value="${requestScope.list[0].venueOfConciliation}" /></td>  
		     
   
		
		<td><bean:message key="lbl.nameOfConciliator" /></td>
		<td><html:text property="nameOfConciliator" styleClass="text" styleId="nameOfConciliator" maxlength="50" value="${requestScope.list[0].nameOfConciliator}" /></td> 
		 </tr>
    
    <tr>
		<td><bean:message key="lbl.designationOfConciliator" /></td>
		<td><html:text property="designationOfConciliator" styleClass="text" styleId="designationOfConciliator" maxlength="50" value="${requestScope.list[0].designationOfConciliator}" /></td>  
		     
    </tr>
    
    </table>
	<table>	
	
	<tr>
	
		<td>
    
	    <logic:present name="editVal">
	      <button type="button" name="update" id="update" title="Alt+V" accesskey="V" onclick="return fnUpdateLegalNoticeInitiationMaker('U');" class="topformbutton2" ><bean:message key="button.save" /></button>
	      <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return fnUpdateLegalNoticeInitiationMaker('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
	   </logic:present>
   
	   <logic:present name="save">
	    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveLegalNoticeInitiationMaker('S');" class="topformbutton2" ><bean:message key="button.save" /></button>
	    <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return saveLegalNoticeInitiationMaker('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
	   </logic:present>
	   
    	<br>
   		 </td> 
   	</tr>

</table>		

</fieldset>

  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
  <input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
  <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" /> 
  <html:hidden property="noticeId" styleId="noticeId" value="${requestScope.list[0].noticeId}"  />         
</html:form>

		
<logic:present name="sms">
<script type="text/javascript">


if('<%=request.getAttribute("sms").toString()%>'=='S')
{
	alert("<bean:message key="lbl.dataSave" />");
	document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMaker.do?method=openEditLegalNoticeInitiationMaker&noticeId="+<%=request.getAttribute("noticeId")%>;
	document.getElementById("legalNoticeInitiationMakerForm").submit();
	
}
else if('<%=request.getAttribute("sms").toString()%>'=='F')
{
	alert("<bean:message key="msg.NoticeForwSucc" />");
	document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerBehind.do";
    document.getElementById("legalNoticeInitiationMakerForm").submit();
	
}
else if('<%=request.getAttribute("sms").toString()%>'=='M')
{
	alert("<bean:message key="lbl.dataModify" />");
	document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMaker.do?method=openEditLegalNoticeInitiationMaker&noticeId="+<%=request.getAttribute("noticeId")%>;
    document.getElementById("legalNoticeInitiationMakerForm").submit();
}
else if('<%=request.getAttribute("sms").toString()%>'=='N')
{
	alert("Before Initiating NOTICE138, LRN notice must be sent for same Loan No.");
		document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerBehind.do";
    document.getElementById("legalNoticeInitiationMakerForm").submit();	
	
}
else if('<%=request.getAttribute("sms").toString()%>'=='E')
{
	alert("Before Initiating NOTICE138, LRN notice must be sent for same Loan No.");	
		document.getElementById("legalNoticeInitiationMakerForm").action="legalNoticeInitiationMakerBehind.do";
    document.getElementById("legalNoticeInitiationMakerForm").submit();
}

	
</script>
</logic:present>
 </body>
		</html:html>