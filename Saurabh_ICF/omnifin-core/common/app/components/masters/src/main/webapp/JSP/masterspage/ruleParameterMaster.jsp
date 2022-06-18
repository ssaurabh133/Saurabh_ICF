<!-- Sanjog -->

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

<title><bean:message key="a3s.noida" /></title>

   		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/ruleScript.js"></script>
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

<body onload="enableAnchor();document.getElementById('ruleParameterSearch').parameterCode.focus();init_fields();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:errors />
<html:form  styleId="ruleParameterSearch" action="/ruleSearchMasters">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />

<fieldset>
<legend><bean:message key="lbl.RPMaster" /></legend> 
	<table width="100%">
	
	<tr>
     <td width="13%"><bean:message key="lbl.parameterCode"/></td>
     <td width="13%"><html:text property="parameterCode" styleId="parameterCode" styleClass="text" maxlength="10" />
      </td>
      
    <td width="13%"><bean:message key="lbl.paramName"/></td>
     <td width="13%"><html:text property="paramName" styleId="paramName" styleClass="text" maxlength="50" />
      </td>
    </tr>
 <tr> 
     <td>
   	  <button type="button"   id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchRule();" ><bean:message key="button.search" /></button>
     <button type="button" name="button2"  id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="return ruleAdd();" ><bean:message key="button.add" /></button></td>
  </tr>
	 </table>
</fieldset>

</br>
<fieldset>
		 <legend><bean:message key="lbl.ruleParameterDetail" /></legend> 
		 
  <logic:present name="list">
  <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/ruleSearchMasters.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="parameterCode" titleKey="lbl.parameterCode"  sortable="true"  />
	<display:column property="paramName" titleKey="lbl.paramName"  sortable="true"  />
	<display:column property="parameterStatus" titleKey="lbl.parameterStatus"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td class="gridtd">
			<table width="100%" border="0" cellpadding="4" cellspacing="1">
				<tr>
					<td width="220" class="white2" style="width: 220px;">
						<strong><bean:message key="lbl.parameterCode" /> </strong>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.paramName" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.parameterStatus" /> <br> </b>
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
			document.getElementById("parameterCode").value="";
			document.getElementById("paramName").value="";
			document.getElementById("ruleParameterSearch").action="ruleSearchMasters.do?method=initialruleMaster";
			document.getElementById("ruleParameterSearch").submit();
		
	}
	
	
	
</script>
</logic:present>	
  </body>
		</html>