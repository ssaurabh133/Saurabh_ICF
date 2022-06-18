<!--Author Name : Ritu Jindal
Date of Creation : 07-July-2011
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
<html:html>
  <head>
    
   		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/npaStageScript.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
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
  
  <body onload="enableAnchor();document.getElementById('npaStageMasterSearch').npaSearchStage.focus();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
  <html:form action="/npaStageMasterSearch" styleId="npaStageMasterSearch" >
  <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
  <fieldset>
<legend><bean:message key="lbl.npaStageMaster" /></legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="65" border="0" cellpadding="4" cellspacing="2">
  
  <tr>
     <td width="13%"><bean:message key="lbl.npaStage"/></td>
     <td width="13%"><html:text property="npaSearchStage" styleId="npaSearchStage"  styleClass="text" maxlength="10" />
	</td>
	
	<td width="13%"><bean:message key="lbl.sequenceNo"/></td>
     <td width="13%"><html:text property="sequenceNoSearch" style="text-align: right" styleId="sequenceNoSearch"  styleClass="text" maxlength="3" />
	</td>
    </tr>
 <tr> 
     <td>
     	  <button type="button"  id="save" title="Alt+S" accesskey="S" class="topformbutton2" onclick="return fnSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
            <button type="button"  name="button2" id="add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="return newAdd();"><bean:message key="button.add" /></button></td>
  </tr>
 </table></td>
  </tr></table></fieldset>
  <br/>
  <fieldset>
<legend><bean:message key="lbl.npaStageDetail" /></legend> 
	<logic:present name="list">
	<logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/npaStageMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="npaStageModify" titleKey="lbl.npaStage"  sortable="true"  />
	<display:column property="sequenceNo" titleKey="lbl.sequenceNo"  sortable="true"  />
	<display:column property="npaCriteriaModify" titleKey="lbl.npaCriteria"  sortable="true"  />
	<display:column property="npaCriteriaValue" titleKey="lbl.npaCriteriaValue"  sortable="true"  />
	<display:column property="moveToNextModify" titleKey="lbl.moveToNext"  sortable="true"  />
	<display:column property="moveToPreviousModify" titleKey="lbl.moveToPrevious"  sortable="true"  />
	<display:column property="billingFlagStatus" titleKey="lbl.billingFlag"  sortable="true"  />
	<display:column property="accrualFlagStatus" titleKey="lbl.AccrualFlag"  sortable="true"  />
	<display:column property="proDesc" titleKey="lbl.productId"  sortable="true"  />
	<display:column property="npaStageStatus" titleKey="lbl.status"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.npaStage" /> </strong>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.sequenceNo" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.npaCriteria" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.npaCriteriaValue" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.moveToNext" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.moveToPrevious" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.billingFlag" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.AccrualFlag" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.status" /> <br> </b>
									</td>
							</tr>					
			    </table>
							<tr class="white2" >
	                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
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
</html:html>
