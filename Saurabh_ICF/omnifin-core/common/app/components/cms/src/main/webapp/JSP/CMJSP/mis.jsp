
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>	  	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/underWriter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<script type="text/javascript" src=" <%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>	
	
	
    <script type="text/javascript">
    
    </script>
    </head>
 <body onload="enableAnchor();" >
<%--  <body onclick="parent_disable();" oncontextmenu="return false" onload="enableAnchor();checkChanges('serviceBranchForm');document.getElementById('serviceBranchForm').applicantType.focus();" onunload="closeAllWindowCallUnloadBody();closeAllWindowCallUnloadBodyAn();" > --%>
<html:form styleId="serviceBranchForm"  method="post" action="/MisServiceAction">
<input type="hidden" name="path" id="path"
				value="<%=request.getContextPath()%>" />
<logic:present name="author">
</logic:present>
    <fieldset>
<logic:present name="loanHeader">
<logic:notPresent name="dealHeader">
<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <html:hidden property="loanNo" styleId="loanNo" value="${loanHeader[0].loanNo}" />
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
</logic:notPresent>
</logic:present>
<logic:present name="dealHeader">
<logic:notPresent name="loanHeader">
<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
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
          <td >Under Writer</td>
          
        </tr>
        </table> 
</td>
</tr>
</table>
</logic:notPresent>
</logic:present>
</fieldset>
<html:hidden property="loanId" styleId="loanId" value="${loanId}" />
<fieldset>

		<legend>
			<bean:message key="lbl.misService" />
		</legend>

						<%-- <table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td> --%>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="13%"><bean:message key="lbl.serviceBranch"/></td>
											
											    <td width="13%">
											    <logic:notPresent name="misAuthor">
											    <html:text property="serviceBranch" styleClass="text" styleId="serviceBranch" readonly="true" value="${serviceBranch[0].serviceBranch}" />
											    <html:button property="serviceBranch" styleId="serviceBranch" onclick="openLOVCommon(604,'serviceBranchForm','serviceBranch','branchId','serviceBranch', 'branchId','select service branch','','lbxserviceBranchID')" value=" " styleClass="lovbutton"></html:button>
											    </logic:notPresent>
											    <logic:present name="misAuthor">
											    <html:text property="serviceBranch" styleClass="text" styleId="serviceBranch" readonly="true" value="${serviceBranch[0].serviceBranch}" />
											    </logic:present>
											    <input type="hidden" name="branchId" id="branchId" value="${userobject.branchId}" />
			                                    
								                <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
								                <html:hidden  property="lbxserviceBranchID" styleId="lbxserviceBranchID" value="${serviceBranch[0].lbxserviceBranchID}" />
												<%--
												<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												html:button property="loanButton" styleId="loanButton"
												<input id="serviceBranchButton" class="lovbutton" type="button" onclick="openLOVCommon(604,'bankAccountMasterSearch','serviceBranch','serviceBranch','lbxserviceBranchID', 'lbxserviceBranchID','','','lbl.branchName');closeLovCallonLov('bankSearchCode');" value=" " name="bankBranchSearchButton">
                                                <input id="lbxserviceBranchID" type="hidden" value="" name="lbxserviceBranchID"> --%>
											</td>										
											</tr>
										<tr>
											<td align="left" class="form2" colspan="4">
											<%--<html:button property="save"  styleId="save" value="save" styleClass="topformbutton2"  onclick="saveMISService();"><bean:message key="button.save" /></html:button> --%>
											<logic:notPresent name="misAuthor">
											<button type="button" name="save" class="topformbutton2" title="Alt+S" accesskey="S" id="save" onclick="saveMISService();"><bean:message key="button.save" /></button>
											</logic:notPresent>
												
											</td>
										</tr>
										</table>
										
<%-- 
								</td>	
							</tr>

						</table> --%>

					</fieldset>
	</html:form>
	<logic:present name="sms">
	<script type="text/javascript">

    
	
	
	if('<%=request.getAttribute("sms").toString()%>'=='S')
{
		alert("<bean:message key="msg.DataSaved" />");

}
	if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
			alert("<bean:message key="msg.DataNotSaved" />");

	}
	
	
	
</script>
	</logic:present>
         <%--  </html:form> --%>
          </body>					
</html:html>
					