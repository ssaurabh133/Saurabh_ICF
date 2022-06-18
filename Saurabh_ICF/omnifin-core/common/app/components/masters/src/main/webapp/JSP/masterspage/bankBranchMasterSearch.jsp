<!--Author Name : Yogesh
Date of Creation : 28-April-2011
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
  	    <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
 
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/bankBranchMaster.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript">  
   </script>

<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
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
  
  <body onload="enableAnchor();document.getElementById('bankBranchMasterSearch').bankBranchSearchCode.focus();init_fields();">
  <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
  <html:errors />
  <html:form  styleId="bankBranchMasterSearch" action="/bankbranchMasterSearch">
  <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
  <fieldset>
<legend><bean:message key="lbl.bankBranchMaster" /></legend>
${requestScope.msg}
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="65" border="0" cellpadding="4" cellspacing="2">
  
  <tr>
     <td width="13%"><bean:message key="lbl.BankName"/></td>
     <td width="13%"><html:text property="bankBranchSearchCode" styleId="bankBranchSearchCode"  styleClass="text" maxlength="50" />
	</td>
	
	<td width="13%"><bean:message key="lbl.bankBranchName"/></td>
     <td width="13%"><html:text property="bankBranchSearchName" styleId="bankBranchSearchName"  styleClass="text" maxlength="50" />
	</td>
    </tr>
        <tr>
     <td width="13%"><bean:message key="lbl.branchMICRCode"/></td>
     <td width="13%"><html:text property="branchMICRCode" styleId="branchMICRCode"  styleClass="text" maxlength="50" />
	</td>
	
	<td width="13%"><bean:message key="lbl.branchIFCSCode"/></td>
     <td width="13%"><html:text property="branchIFCSCode" styleId="branchIFCSCode"  styleClass="text" maxlength="50" />
	</td>
    </tr>
 <tr> 
     <td>
     	  <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fnSearch('<bean:message key="lbl.selectAtleast" />','<bean:message key="lbl.selectBranch" />');" ><bean:message key="button.search" /></button>
     <button type="button" name="button2" id="add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="return newAdd();" ><bean:message key="button.add" /></button></td>
  </tr>
 </table></td>
  </tr></table></fieldset>
  </br>
  <fieldset>
<legend><bean:message key="lbl.bankBranchDetail" /></legend> 
	<logic:present name="list">
	<logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/bankBranchSearchBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="bankBranchCodeModify" titleKey="lbl.bankBranchCode"  sortable="true"  />
	<display:column property="bank" titleKey="lbl.bankName"  sortable="true"  />
	<display:column property="bankBranchName" titleKey="lbl.bankBranchName"  sortable="true"  />
	<display:column property="branchMICRCode" titleKey="lbl.branchMICRCode"  sortable="true"  />
	<display:column property="branchIFCSCode" titleKey="lbl.branchIFCSCode"  sortable="true"  />
	<display:column property="bankBranchStatus" titleKey="lbl.active"  sortable="true"  />
  
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td class="gridtd">
			<table width="100%" border="0" cellpadding="4" cellspacing="1">
				<tr>
					<td width="220" class="white2" style="width: 220px;">
						<strong><bean:message key="lbl.bankBranchCode" /> </strong>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.bankBranchName" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.branchMICRCode" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.branchIFCSCode" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.active" /> <br> </b>
					</td>
					<tr class="white2" >
	                    	<td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                        </tr>
				</tr>
			</table>
		</td>
	</tr>
</table>
</logic:empty>
  </logic:present>
</fieldset>
</html:form>
   <logic:present name="sms">
<script type="text/javascript">

   
	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
	
	
</script>
</logic:present>	
  </body>
</html>
