<!--Author Name : Ritu Jindal-->
<!--Date of Creation : 25 May 2011-->
<!--Purpose  : Information of USER ACCESS Master Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userAccessScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('userAccessMasterForm').modulButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('userAccessMasterForm').userbutton.focus();
     }
     return true;
}
</script>
</head>

<body onload="enableAnchor();fntab();init_fields();">
<html:javascript formName="UserAccessMasterAddDynaValidatorForm"/>
<html:errors/>
<html:form styleId="userAccessMasterForm"  method="post"  action="/userAccessMasterAdd" >
 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<fieldset>
<legend><bean:message key="lbl.userAccessMaster" /></legend>
  <table width="100%">
  <logic:present name="editVal">
  
   <input type="hidden" id="userAccessId" name="userAccessId" value="${userAccessId }"/>
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
   <tr>
   <td><bean:message key="lbl.userId" /><span><font color="red">*</font></span></td>
   <td><html:text property="userId" styleClass="text" styleId="userId" readonly="true" value="${requestScope.list[0].userId}" /></td>  
    
   <td><bean:message key="lbl.moduleId" /><span><font color="red">*</font></span></td>
       <td ><html:text property="moduleId" styleClass="text" styleId="moduleId" tabindex="-1" readonly="true" value="${requestScope.list[0].moduleId}" onclick="return fnval()"/>
        <html:button property="modulButton" styleId="modulButton" onclick="openLOVCommon(28,'userAccessMasterForm','moduleId','','roleId','lbxRoleId','','','lbxModule')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onClick="openLOVCommon(28,'userAccessMasterForm','moduleId','','roleId','lbxRoleId','','','lbxModule')" src="<%= request.getContextPath()%>/images/lov.gif"> --%> 
       <html:hidden  property="lbxModule" styleId="lbxModule" value="${requestScope.list[0].lbxModule}" />
     </td>
   </tr> 
   
   <tr>
    <td><bean:message key="lbl.roleId" /><span><font color="red">*</font></span></td>
      <td ><html:text property="roleId" styleClass="text" styleId="roleId" tabindex="-1" maxlength="10" readonly="true" value="${requestScope.list[0].roleId}"/>
 	  <html:button property="roleButton" styleId="roleButton" onclick="openLOVCommon(51,'userAccessMasterForm','roleId','moduleId','lbxRoleId', 'lbxModule','Please Select Module Code','','lbxRoleId')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onClick="openLOVCommon(51,'userAccessMasterForm','roleId','moduleId','lbxRoleId', 'lbxModule','Please Select Module Code','','lbxRoleId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%> 
          <html:hidden  property="lbxRoleId" styleId="lbxRoleId"  value="${requestScope.list[0].lbxRoleId}"/>
    
     </td>
  
      <td><bean:message key="lbl.active" /></td>
      <logic:equal value="Active" name="userAccessStatus">
         <td><input type="checkbox" name="userAccessStatus" id="userAccessStatus" checked="checked" /></td>
       </logic:equal>
    
      <logic:notEqual value="Active" name="userAccessStatus">
       <td><input type="checkbox" name="userAccessStatus" id="userAccessStatus"  /></td> 
      </logic:notEqual>
      
    </tr>
   </logic:iterate>
   </logic:present>
   
   <logic:notPresent name="editVal">
   
<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   
   <tr>
   <td><bean:message key="lbl.userId" /><span><font color="red">*</font></span></td>
       <td ><html:text property="userId" styleClass="text" styleId="userId"  readonly="true" />
        <html:button property="userbutton" styleId="userbutton" tabindex="-1" onclick="openLOVCommon(33,'userAccessMasterForm','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onClick="openLOVCommon(33,'userAccessMasterForm','userId','','', '','','','lbxUserId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%> 
       <html:hidden  property="lbxUserId" styleId="lbxUserId"  />
     </td>    
   <td><bean:message key="lbl.moduleId" /><span><font color="red">*</font></span></td>
       <td ><html:text property="moduleId" styleClass="text" tabindex="-1" styleId="moduleId"  readonly="true"  onclick="return fnval()"/>
 		<html:button property="modulButton" styleId="modulButton" onclick="openLOVCommon(28,'userAccessMasterForm','moduleId','','roleId','lbxRoleId','','','lbxModule')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onClick="openLOVCommon(28,'userAccessMasterForm','moduleId','','roleId','lbxRoleId','','','lbxModule')" src="<%= request.getContextPath()%>/images/lov.gif"> --%> 
       <html:hidden  property="lbxModule" styleId="lbxModule"  />
     </td>
   </tr> 
   
   <tr>
    <td><bean:message key="lbl.roleId" /><span><font color="red">*</font></span></td>
      <td ><html:text property="roleId" styleClass="text"  tabindex="-1" styleId="roleId" maxlength="10" readonly="true" />
      <html:button property="roleButton" styleId="roleButton" onclick="openLOVCommon(51,'userAccessMasterForm','roleId','moduleId','lbxRoleId', 'lbxModule','Please Select Module Code','','lbxRoleId')" value=" " styleClass="lovbutton"></html:button>
       <%--<img onClick="openLOVCommon(51,'userAccessMasterForm','roleId','moduleId','lbxRoleId', 'lbxModule','Please Select Module Code','','lbxRoleId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%> 
      <html:hidden  property="lbxRoleId" styleId="lbxRoleId" />

     </td>
  
      <td><bean:message key="lbl.active" /></td>
      <logic:equal value="Active" name="userAccessStatus">
         <td><input type="checkbox" name="userAccessStatus" id="userAccessStatus" checked="checked" /></td>
       </logic:equal>
    
      <logic:notEqual value="Active" name="userAccessStatus">
       <td><input type="checkbox" name="userAccessStatus" id="userAccessStatus"  /></td> 
      </logic:notEqual>
      
    </tr>
  
   </logic:notPresent>
    <tr>
    
    <tr><td>
    
    <br>
    <logic:present name="editVal">
      <button type="button"  name="save"  id="save" title="Alt+V" accesskey="V" onclick="return fnEditUserAccess('${requestScope.list[0].lbxUserId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="editVal">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save"  id="save" title="Alt+V" accesskey="V" onclick="return saveUserAccess();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>
	<logic:present name="sms">
<script type="text/javascript">
   if('<%=request.getAttribute("sms").toString()%>'=='X')
	{
		alert("<bean:message key="msg.PlzActiveModule" />");
		document.getElementById("userAccessMasterForm").action="userAccessMasterBehind.do";
	    document.getElementById("userAccessMasterForm").submit();
	}
   
   else if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("userAccessMasterForm").action="userAccessMasterBehind.do";
	    document.getElementById("userAccessMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("userAccessMasterForm").action="userAccessMasterBehind.do";
	    document.getElementById("userAccessMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}  
	else
	{
		alert("<bean:message key="msg.notepadError" />");		
		
	}  
    
	

	
</script>
</logic:present>		
		
  </body>
		</html:html>