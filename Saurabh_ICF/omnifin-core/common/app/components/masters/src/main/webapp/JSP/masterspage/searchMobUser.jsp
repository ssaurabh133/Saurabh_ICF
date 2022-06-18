<!--Author Name : Shreyansh Kumar Sharma
Date of Creation : 27-Oct-2015
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
   <script type="text/javascript">  
   </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
  <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/mobileUserMapping.js"></script>
  <script type="text/javascript">
 function clearInput(){ 
<%if(request.getAttribute("msg1")== "msg1"){%>
document.getElementById('mobileUserId').value='';
document.getElementById('mobileNo').value='';
<%}%>
}


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
  
 
  <body onload="enableAnchor();clearInput();init_fields();">
  	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
  <html:form  styleId="mobileUserMasterSearch" action="/mobileUserMasterSearch">
  <input type="hidden" name="path" id="contextPath" value="<%=request.getContextPath()%>" />
  <fieldset>
<legend><bean:message key="lbl.mobileusermap" /></legend> 

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="60" border="0" cellpadding="4" cellspacing="2">
  
  <tr>
    <td width="13%"><bean:message key="lbl.mobileUserId"/></td>

    <td width="13%"><html:text property="mobileUserId" styleId="mobileUserId" style="text-align: right" maxlength="50" value=""  readonly="true" styleClass="text" />
	<html:button property="lovButton" value=" " styleClass="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(6004,'mobileUserMasterSearch','mobileUserId','','', '','','','mobileUserNameSearch');" />
	</td>
	 <td width="13%"><bean:message key="lbl.mobileUserName" /></td>
    <td width="13%"><html:text property="mobileUserNameSearch" styleId="mobileNo" styleId="mobileUserNameSearch" style="text-align: right" maxlength="50" value=""   styleClass="text"/></td>
	<td width="13%"><html:hidden property="mobileUserNameSearch" styleId="mobileNo"  styleClass="text" /></td>
    </tr>
 <tr> 
     <td><button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="searchUser();" ><bean:message key="button.search" /></button>
     <button type="button" name="button2" id="add" title="Alt+A" accesskey="A" class="topformbutton2" onclick="newAddMobileUser();" ><bean:message key="button.add" /></button></td>
  </tr>
 </table></td>
  </tr></table>
  </fieldset>
  <logic:present name="list">
    <fieldset>
<legend><bean:message key="lbl.userDetails" /></legend>  

   
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/mobileUserMasterSearch.do?method=searchmobileUser" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="idModify" titleKey="lbl.mobileUserId"  sortable="true"  />
	<display:column property="mobileUserNameSearch" titleKey="lbl.mobileUserName"  sortable="true"  />
	<display:column property="mobile" titleKey="lbl.mobileUserMobile"  sortable="true"  />
	<display:column property="imeiNo" titleKey="lbl.mobileUserIMEI"  sortable="true"  />
	<display:column property="userStatus" titleKey="lbl.userStatus"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td class="gridtd">
			<table width="100%" border="0" cellpadding="4" cellspacing="1">
				<tr>
					<td width="220" class="white2" style="width: 220px;">
						<strong><bean:message key="lbl.mobileUserId" /> </strong>
					</td>
					<td width="220" class="white2" style="width: 220px;">
						<strong><bean:message key="lbl.mobileUserName" /> </strong>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.mobileUserMobile" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.mobileUserIMEI" /> <br> </b>
					</td>
					<td width="220" class="white2" style="width: 250px;">
						<b><bean:message key="lbl.userStatus" /> <br> </b>
					</td>
					<tr class="white2" >
	                    	<td colspan="7"><bean:message key="lbl.noData" /></td>
                        </tr>
				</tr>
			</table>
		</td>
	</tr>
</table>
</logic:empty>
  

</fieldset> 
  </logic:present>
  
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
