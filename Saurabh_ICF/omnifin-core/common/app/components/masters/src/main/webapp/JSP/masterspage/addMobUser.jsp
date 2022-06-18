<!--Author Name : Shreyansh kumar sharma
Date of Creation : 28-oct-2015
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
        
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/gcdGroupMaster.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/mobileUserMapping.js"></script>
<script type="text/javascript">
function clearInput(){ 
<%if(request.getAttribute("Add")== "Add"){%>
document.getElementById('userId').value='';
document.getElementById('mobile').value='';
<%}%>
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

<body onload="enableAnchor();clearInput();fntab();init_fields();">

	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<html:form styleId="mobileUserMasterSearch"  method="post"  action="/mobileUserMasterSearch" >
<fieldset>

<logic:present name="dataNotSave">
<legend><bean:message key="lbl.mobileUserEdit" /></legend>
</logic:present>


 <logic:notPresent name="dataNotSave">
 <legend><bean:message key="lbl.mobileUserAdd" /></legend>
 </logic:notPresent>
 
  <table width="100%">
    
    <input type="hidden" name="path" id="contextPath" value="<%=request.getContextPath()%>" />


      <logic:present name="editVal">
       <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
       <tr>
     
      <td><bean:message key="lbl.mobileUserId" /><span><font color="red">*</font></span></td>
      <td><html:text property="userId" styleClass="text"  styleId="userId" readonly="true" maxlength="50" value="${requestScope.list[0].userId}" />
     </td>
     
      <td><bean:message key="lbl.mobileUserName" /><span><font color="red">*</font></span></td>
     
      <td><html:text property="mobileUserName" styleClass="text"  styleId="userId" readonly="true" maxlength="50" value="${requestScope.list[0].mobileUserName}" />
      </td>
     </tr>
     <tr>
     <td><bean:message key="lbl.mobileUserIMEI" /><span><font color="red">*</font></span></td>
      <td><html:text property="imeiNo" styleClass="text" style="text-align: right" styleId="imeiNo"  onkeyup="checkNumber(this.value, event, 50,id);" onfocus="keyUpNumber(this.value, event, 50,id);" value="${requestScope.list[0].imeiNo}" /></td>
     	 <td><bean:message key="lbl.mobileUserMobile" /><span><font color="red">*</font></span></td>
      <td><html:text property="mobile" styleClass="text"  styleId="mobile"  onkeyup="checkNumber(this.value, event, 10,id);" onfocus="keyUpNumber(this.value, event, 10,id);" value="${requestScope.list[0].mobile}"/>
	 
	 
     </tr>
	 <tr>
	  <td><bean:message key="lbl.recStatus" /></td>
      <td>
      <logic:equal value="Y" name="status">
         <input type="checkbox" name="userStatus" id="userStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Y" name="status">
         <input type="checkbox" name="userStatus" id="userStatus"  />
      </logic:notEqual></td>

	 </tr>
     </logic:present>
     
   <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   <tr>
      <td><bean:message key="lbl.mobileUserId" /><span><font color="red">*</font></span></td>
      <td><html:text property="userId" styleClass="text" styleId="userId" maxlength="50" readonly="true"/>
     <html:button property="lovButton" value=" " styleClass="lovbutton" onclick="closeLovCallonLov1();openLOVCommon(6006,'mobileUserMasterSearch','userId','','', '','','','mobileUserName');" />
     </td>
     
      <td><bean:message key="lbl.mobileUserName" /><span><font color="red">*</font></span></td>
      <td><html:text property="mobileUserName" styleClass="text" styleId="mobileUserName" maxlength="50" readonly="true"/></td>
     </tr>
     <tr>
     <td><bean:message key="lbl.mobileUserIMEI" /><span><font color="red">*</font></span></td>
      <td><html:text property="imeiNo" styleClass="text" style="text-align: right" styleId="imeiNo"    onkeyup="checkNumber(this.value, event, 50,id);" onfocus="keyUpNumber(this.value, event, 50,id);"  /></td>
     
      <td><bean:message key="lbl.mobileUserMobile" /><span><font color="red">*</font></span></td>
      <td>
  <html:text property="mobile" styleClass="text"  styleId="mobile"  onkeyup="checkNumber(this.value, event, 10,id);" onfocus="keyUpNumber(this.value, event, 10,id);" />
       </td>
     </tr>
	 <tr>
	 <td><bean:message key="lbl.recStatus" /></td>
      <td><input type="checkbox" name="userStatus" id="userStatus" checked="checked" /></td>
	 </tr>
     </logic:notPresent>
	 
    <tr><td>
      
    <logic:present name="dataNotSave">
     <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return mobileUserModify('${requestScope.list[0].userId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="dataNotSave">
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return mobileUserSave();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="msg">
<script type="text/javascript">

if('<%=request.getAttribute("msg").toString()%>'=='S')
{
alert("<bean:message key="lbl.dataSave" />");
document.getElementById("mobileUserMasterSearch").action="MobileUserMappingAction.do";
document.getElementById("mobileUserMasterSearch").submit();
}

else if('<%=request.getAttribute("msg").toString()%>'=='M')
{
alert("<bean:message key="lbl.dataModify" />");
document.getElementById("mobileUserMasterSearch").action="MobileUserMappingAction.do";
document.getElementById("mobileUserMasterSearch").submit();
}
else if('<%=request.getAttribute("msg").toString()%>'=='EXISTSIMIE')
{
alert("<bean:message key="lbl.imieExists" />");		
}
else if('<%=request.getAttribute("msg").toString()%>'=='EXIST')
{
alert("<bean:message key="lbl.userExists" />");		
}
else if('<%=request.getAttribute("msg").toString()%>'=='E')
{
alert("<bean:message key="msg.notepadError" />");

}
else if('<%=request.getAttribute("msg").toString()%>'=='EXSTAT')
{
alert("<bean:message key="lbl.imieActive" />");		
}

</script>
</logic:present>
  </body>
		</html:html>