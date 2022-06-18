<!--Author Name : RICHA-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/RepoBillingApprovalMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">

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

<body onload="enableAnchor();fntab();init_fields();">
<html:form styleId="repoBillingApprovalForm"  method="post"  action="/repoBillingApprovalMakerDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" name="canForward" value="${requestScope.canForward}" id="canForward" />
<fieldset>
<legend><bean:message key="lbl.repoBillingApprovalMaker" /></legend>
  <table width="100%">
<tr>

		<td width="20%"><bean:message key="lbl.loanNo"/><font color="red">*</font></td>
		<td width="35%"  >
			 <logic:notPresent name="editVal">
			<html:text property="searchLoanNo" styleClass="text" styleId="searchLoanNo" maxlength="10" readonly="true"  tabindex="-1" value=""/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value=""/>
			<html:button property="dealButton" style = "" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(579,'repoBillingApprovalForm','searchLoanNo','','', '','','getValuesForRepoBilling','searchCustomerName')" value=" " styleClass="lovbutton"></html:button>
			</logic:notPresent>
			
			<logic:present name="editVal">
			<html:text property="searchLoanNo" styleClass="text" styleId="searchLoanNo" maxlength="10" readonly="true"  tabindex="-1" value="${requestScope.list[0].searchLoanNo}"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${requestScope.list[0].lbxDealNo}"/>
			</logic:present>
	 </td>
	 

	  <td width="17%"><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	   <logic:notPresent name="editVal">
		<td width="28%" ><html:text property="searchCustomerName" tabindex="2"  styleClass="text" styleId="searchCustomerName" readonly="true" maxlength="50" value=""/></td> 
	   </logic:notPresent>
	   
	   <logic:present name="editVal">
		<td width="28%" ><html:text property="searchCustomerName" tabindex="2"  styleClass="text" styleId="searchCustomerName" readonly="true" maxlength="50" value="${requestScope.list[0].searchCustomerName}"/></td> 
	   </logic:present>
	    </tr>
    
      <tr>      
    <td><bean:message key="lbl.billingToBeStopped" /></td>
    <td>
    <logic:present name="editVal">
	
	 <logic:equal value="A" name="billingStatus">
         <input type="checkbox" name=billingStopped id="billingStopped" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="billingStatus">
         <input type="checkbox" name="billingStopped" id="billingStopped"  />
      </logic:notEqual>
	
	</logic:present>
	<logic:present name="save">
	 <input type="checkbox" name=billingStopped id="billingStopped"  />
	</logic:present>
     </td> 
      
       <td><bean:message key="lbl.interestToBeStopped" /></td>
    <td>
    <logic:present name="editVal">
	
	 <logic:equal value="A" name="interestStatus">
         <input type="checkbox" name=interestStopped id="interestStopped" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="interestStatus">
         <input type="checkbox" name="interestStopped" id="interestStopped"  />
      </logic:notEqual>
	
	</logic:present>
	<logic:present name="save">
	 <input type="checkbox" name=interestStopped id="interestStopped"  />
	</logic:present>
    
    
    </tr>
    <tr>
     <td><bean:message key="lbl.SDInterest" /></td>
    <td>
    <logic:present name="editVal">
	
	 <logic:equal value="A" name="sdInterestStatus">
         <input type="checkbox" name=SDInterest id="SDInterest" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="sdInterestStatus">
         <input type="checkbox" name="SDInterest" id="SDInterest"  />
      </logic:notEqual>
	
	</logic:present>
	<logic:present name="save">
	 <input type="checkbox" name=SDInterest id="SDInterest"  />
	</logic:present>
    </tr>
   
	</table>		
	 <table width="100%">
	    <tr ><td width="30%">
	    <logic:present name="save">
	    <button type="button"  title="Alt+A" accesskey="A" onclick="return insertRepoBillingApprovalDetails();" class="topformbutton2"><bean:message key="button.save" /></button>&nbsp;&nbsp;&nbsp;
	    </logic:present>
	    <logic:present name="editVal">
	    <button type="button"  title="Alt+A" accesskey="A" onclick="return updateRepoBillingApprovalDetails();" class="topformbutton2"><bean:message key="button.save" /></button>&nbsp;&nbsp;&nbsp;
	   </logic:present>
				<button type="button" class="topformbutton2" onclick="return forwardRepoBillingApprovalDetails();"><bean:message key="button.forward" /></button>&nbsp;&nbsp;&nbsp;
	          	<logic:present name="editVal">
	          	   	<button type="button" id="delete" class="topformbutton2" onclick="return deleteRepoBillingApproval();" title="Alt+D" accesskey="D"><bean:message key="button.delete" /></button>
					</logic:present>
	    </td> </tr>
	   
	 </table>

</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
	<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=openEditRepoBillingMaker&loanId="+'<%=request.getAttribute("loanId")%>';
	    document.getElementById("repoBillingApprovalForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=openEditRepoBillingMaker&loanId="+'<%=request.getAttribute("loanId")%>';
	    document.getElementById("repoBillingApprovalForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.forwardSuccess" />");
		document.getElementById("repoBillingApprovalForm").action="repoBillingApprovalMakerDispatch.do?method=searchRepoBillingMaker";
	    document.getElementById("repoBillingApprovalForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
</script>
</logic:present>

<logic:present name="deleteMsg">
 <script type="text/javascript">
 
	if('<%=request.getAttribute("deleteMsg")%>'=='Y')
	{
	    alert("<bean:message key="lbl.dataDeleted" />");
		location.href='<%=request.getContextPath()%>/repoBillingApprovalMakerDispatch.do?method=searchRepoBillingMaker';
	}
	else
	{
		alert("<bean:message key="lbl.dataNtDeleted" />");
		location.href='<%=request.getContextPath()%>/repoBillingApprovalMakerDispatch.do?method=searchRepoBillingMaker';
	}
	
  </script>
</logic:present>
  </body>
		</html:html>