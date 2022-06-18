<!--Author Name : Richa Bansal-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/repoScript/repoMarkingMaker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('loanNoButton').focus();
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

<body onload="enableAnchor();fntab();">
<html:form styleId="repoMarkingMakerForm"  method="post"  action="/repoMarkingMakerDispatch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.repoMarkingMaker" /></legend>
  <table width="100%">
  
	<tr>
	
		<td><bean:message key="lbl.loanNo" /><span><font color="red">*</font></span></td>
		<td>
		 <logic:present name="editVal"> 
		<html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${requestScope.list[0].loanNo}" />
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${requestScope.list[0].loanId}" />
		</logic:present>
		<logic:present name="save"> 
		<html:text property="loanNo" readonly="true" styleClass="text" styleId="loanNo" value="${requestScope.list[0].loanNo}" />
		<html:hidden property="loanId" styleClass="text" styleId="loanId" value="${requestScope.list[0].loanId}" />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(1603,'repoMarkingMakerForm','loanNo','','loanId','','','getRepoMarkingDetail','customerName');" value=" " styleClass="lovbutton"> </html:button>
		<input type="hidden" name="customerName" id="customerName"/>
		</logic:present>
		</td>
		<td><bean:message key="lbl.branch" /></td>
		
		<td><html:text property="branch" styleClass="text" readonly="true" styleId="branch" value="${requestScope.list[0].branch}" /></td>
	  	<html:hidden  property="lbxBranch" styleId="lbxBranch" value="${requestScope.list[0].lbxBranch}"  />
	</tr>
	
	<tr>
	<td><bean:message key="lbl.product" /></td>
		
		<td><html:text property="product" styleClass="text" readonly="true" styleId="product" value="${requestScope.list[0].product}" /></td>
	<html:hidden  property="lbxProduct" styleId="lbxProduct" value="${requestScope.list[0].lbxProduct}"  />
		<td><bean:message key="lbl.scheme" /></td>
		
		<td><html:text property="scheme" styleClass="text" readonly="true" styleId="scheme" value="${requestScope.list[0].scheme}" /></td>
			<html:hidden  property="lbxScheme" styleClass="text" styleId="lbxScheme" value="${requestScope.list[0].lbxScheme}"  />
	</tr>
	
	<tr>
	
		<td><bean:message key="lbl.loanAmount" /></td>
		
		<td><html:text property="loanAmount" readonly="true" styleClass="text" styleId="loanAmount" value="${requestScope.list[0].loanAmount}" /></td>
		<td><bean:message key="lbl.balancePrincipal" /></td>
		
		<td><html:text property="balancePrincipal" styleClass="text" readonly="true" styleId="balancePrincipal" value="${requestScope.list[0].balancePrincipal}" /></td>
		
</tr>
	
	<tr>
	
	
		<td><bean:message key="lbl.overduePrincipal" /></td>
		
		<td><html:text property="overduePrincipal" readonly="true" styleClass="text" styleId="overduePrincipal" value="${requestScope.list[0].overduePrincipal}" /></td>
	
	 <td><bean:message key="lbl.overdueAmount" /></td>
		
		<td>
	
		<html:text property="overdueAmount" readonly="true" styleClass="text" styleId="overdueAmount" value="${requestScope.list[0].overdueAmount}" />
		
		</td>
	</tr>
    
    <tr>
   
		

		<td><bean:message key="lbl.repoMarkingMakerDPD" /></td>
		
		<td>
		
		<html:text property="dpd" styleClass="text" readonly="true" styleId="dpd" value="${requestScope.list[0].dpd}" />
		
		</td>
  
  <td><bean:message key="lbl.repoReason" /><span><font color="red">*</font></span></td>
		
		<td>
		
		<html:text property="reasonDesc" styleId="reasonDesc" value="${requestScope.list[0].reasonDesc}" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxReasonId" styleId="lbxReasonId" value="${requestScope.list[0].lbxReasonId}"  />
		<html:button property="reasonButton" styleId="reasonButton" onclick="closeLovCallonLov1();openLOVCommon(1601,'repoMarkingMakerForm','reasonDesc','','','','','','lbxReasonId');" value=" " styleClass="lovbutton"> </html:button>
		
		</td> 
		
	</tr>  
    
    <tr>

		<td><bean:message key="lbl.repoMarkingMakerRemarks" /><span><font color="red">*</font></span></td>
		
		<td><textarea name="remarks" class="text" id="remarks" maxlength="500" >${requestScope.list[0].remarks}</textarea></td>
		
		
		
		<td><bean:message key="lbl.agency" /><span><font color="red">*</font></span></td>
		
		<td><html:text property="agency" styleId="agency" value="${requestScope.list[0].agency}" styleClass="text"  tabindex="-1"/>
		<html:hidden  property="lbxAgencyId" styleId="lbxAgencyId" value="${requestScope.list[0].lbxAgencyId}"  />
		<html:button property="agencyButton" styleId="agencyButton" onclick="closeLovCallonLov1();openLOVCommon(1605,'repoMarkingMakerForm','agency','','','','','','lbxAgencyId');" value=" " styleClass="lovbutton"> </html:button>
		</td>
	</tr>
	
	</table>
	<table>
    
    <tr>	<td>
    
	    <logic:present name="editVal">
	      <button type="button" name="update" id="update" title="Alt+V" accesskey="V" onclick="return fnUpdateRepoMarkingMaker('U');" class="topformbutton2" ><bean:message key="button.save" /></button>
	      <button type="button" name="forward" id="forward" title="Alt+F" accesskey="F" onclick="return fnUpdateRepoMarkingMaker('F');" class="topformbutton2" ><bean:message key="button.forward" /></button>
	   </logic:present>
   
	   <logic:present name="save">
	    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRepoMarkingMaker();" class="topformbutton2" ><bean:message key="button.save" /></button>
	   </logic:present>
	   
    	<br>
   		 </td>  </tr>

	</table>		


</fieldset>
  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
   <input type="hidden" name ="updateForwardFlag" id="updateForwardFlag" />
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.repoMarkedSucc" />");
		document.getElementById("repoMarkingMakerForm").action="repoMarkingMakerDispatch.do?method=openRepoMarkingMaker&loanId="+<%=request.getAttribute("loanId")%>;
	    document.getElementById("repoMarkingMakerForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="msg.markedRepoForwSucc" />");
		document.getElementById("repoMarkingMakerForm").action="repoMarkingMakerDispatch.do?method=searchRepoMarkingMaker";
	    document.getElementById("repoMarkingMakerForm").submit();
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	
</script>
</logic:present>
  </body>
		</html:html>