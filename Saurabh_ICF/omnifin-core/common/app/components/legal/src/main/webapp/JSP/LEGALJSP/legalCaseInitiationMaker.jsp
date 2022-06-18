<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 21-May-2011-->
<!--Purpose  : Information of country Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/legalCaseInitiationMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('caseTypeButton').focus();
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

<body onload="fntab();">
<html:form styleId="legalCaseInitiationMakerForm"  method="post"  action="/legalCaseInitiationMakerDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.legalCaseInitiationMaker" /></legend>
  <table width="100%">
  
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerLoanNo" /></td>
		
		<td><html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${requestScope.list[0].loanNo}" /></td>
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${requestScope.list[0].loanId}" />
		
		
		<td><bean:message key="lbl.legalCaseInitMakerCustName" /></td>
		
		<td><html:text property="customerName" readonly="true" styleId="customerName" value="${requestScope.list[0].customerId}" /></td>
	<html:hidden  property="lbxcustomerId" styleId="lbxcustomerId" value="${requestScope.list[0].lbxcustomerId}"/>
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerBranch" /></td>
		
		<td><html:text property="branch" readonly="true" styleId="branch" value="${requestScope.list[0].branch}" /></td>
		
		<td><bean:message key="lbl.legalCaseInitMakerProduct" /></td>
		
		<td><html:text property="product" readonly="true" styleId="product" value="${requestScope.list[0].product}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerScheme" /></td>
		
		<td><html:text property="scheme" readonly="true" styleId="scheme" value="${requestScope.list[0].scheme}" /></td>
			
		
		<td><bean:message key="lbl.legalCaseInitMakerDPD" /></td>
		
		<td><html:text property="dpd" readonly="true" styleId="dpd" value="${requestScope.list[0].dpd}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalTotalOutstanding" /></td>
		
		<td><html:text property="totalOutstanding" readonly="true" styleClass="text" styleId="totalOutstanding" value="${requestScope.list[0].totalOutstanding}" /></td>
		
		
		<td><bean:message key="lbl.legalOtherCharges" /></td>
		
		<td><html:text property="otherCharges" readonly="true" styleId="otherCharges" value="${requestScope.list[0].otherCharges}" /></td>
		
	  
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.legalPrincipalOutstanding" /></td>
		
		<td><html:text property="principalOutstanding" readonly="true" styleClass="text" styleId="principalOutstanding" value="${requestScope.list[0].principalOutstanding}" /></td>
		
		<td><bean:message key="lbl.legalCaseType" /><span><font color="red">*</font></span></td>
			<td>
		<html:select property="caseTypeDesc" styleId="caseTypeDesc" size="5" multiple="multiple" style="width: 120" >
  			<logic:present name="caseTypeList">
       			 <html:optionsCollection name="caseTypeList" value="caseTypeCode" label="caseTypeDesc"/>
      		</logic:present>
     		</html:select>
<!-- openMultiSelectLOVCommon(lovID,strFormName,strFieldName,strParentGroup,strChildGroup,strParentOption,strMessages,strMethod,LovListItemsIds)-->
		     <input type="hidden" name ="lbxCaseTypeCode" id ="lbxCaseTypeCode" value="${requestScope.caseTypeCode}"/>
		       <html:button property="caseTypeButton" styleId="caseTypeButton" onclick="return openMultiSelectLOVCommon(1524,'legalCaseInitiationMakerForm','caseTypeDesc','','', '','','','lbxCaseTypeCode');" value=" " styleClass="lovbutton"></html:button>	   
			</td>
		
		<%-- <td><bean:message key="lbl.legalCaseType" /><span><font color="red">*</font></td>
		<td>
		<html:text property="caseTypeDesc" styleId="caseTypeDesc" value="${requestScope.list[0].caseTypeDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxCaseTypeCode" styleId="lbxCaseTypeCode" value="${requestScope.list[0].lbxCaseTypeCode}"  />
		<html:button property="caseTypeButton" styleId="caseTypeButton" onclick="closeLovCallonLov1();openLOVCommon(1524,'legalCaseInitiationMakerForm','caseTypeDesc','','reasonDesc','','','','lbxCaseTypeCode');" value=" " styleClass="lovbutton"> </html:button>
		</td> --%>
	  
	</tr>
    
    <tr>
	
		<td><bean:message key="lbl.legalImemo" /></td>
		
		<td><html:text property="initMakerImemo" styleClass="text" styleId="initMakerImemo" value="${requestScope.list[0].initMakerImemo}" /></td>
		
		
		
		<td><bean:message key="lbl.legalReason" /><span><font color="red">*</font></td>
		
	 <td>
		<html:text property="reasonDesc" styleId="reasonDesc" value="${requestScope.list[0].reasonDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${requestScope.list[0].lbxReasonId}"  />
		<html:button property="reasonButton" styleId="reasonButton" onclick="closeLovCallonLov1();openLOVCommon(1520,'legalCaseInitiationMakerForm','reasonDesc','','','','','','lbxReasonId');" value=" " styleClass="lovbutton"> </html:button> 
	<%-- 	<html:button property="lovButton" value=" " styleClass="lovbutton" property="reasonButton" styleId="reasonButton" onclick="closeLovCallonLov('caseTypeDesc');openLOVCommon(16091,'legalCaseInitiationMakerForm','reasonDesc','caseTypeDesc','lbxReasonId', 'lbxCaseTypeCode','Please select Case Type!!!','','lbxReasonId');"/>--%>
		</td>  
		
		
	  
	</tr>  
    
    <tr>
	
		<td><bean:message key="lbl.legalCaseInitMakerRemarks" /><span><font color="red">*</font></span></td>
		
		<td><textarea name="initMakerRemarks" id="initMakerRemarks" maxlength="500" >${requestScope.list[0].initMakerRemarks}</textarea></td>
		
	  
	</tr>
    
    <tr><td>
    
  
      
      <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveLegalCaseInitiationMaker();" class="topformbutton2" ><bean:message key="button.save" /></button>
  
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.CaseInitSucc" />");
		document.getElementById("legalCaseInitiationMakerForm").action="legalCaseInitiationMakerDispatch.do?method=searchLegalCaseInitiationMaker";
	    document.getElementById("legalCaseInitiationMakerForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='LRN')
	{
		alert("For case type (CIVIL,DRT and RO), LRN notice must be sent. ");	
		document.getElementById("legalCaseInitiationMakerForm").action="legalCaseInitiationMakerDispatch.do?method=searchLegalCaseInitiationMaker";
	    document.getElementById("legalCaseInitiationMakerForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='NOTICE138')
	{
		alert("For case type CASE138, NOTICE138 must sent. ");
		document.getElementById("legalCaseInitiationMakerForm").action="legalCaseInitiationMakerDispatch.do?method=searchLegalCaseInitiationMaker";
	    document.getElementById("legalCaseInitiationMakerForm").submit();	
	}	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>