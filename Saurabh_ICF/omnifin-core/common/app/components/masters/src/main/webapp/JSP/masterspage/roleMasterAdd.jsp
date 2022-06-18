<!--Author Name : Ritu Jindal-->
<!--Date of Creation :18 May 2011 -->
<!--Purpose  : Information of Role Master Add-->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('roleMasterForm').moduleButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('roleMasterForm').roleDesc.focus();
     }
     return true;
}
</script>
</head>

<body onload="enableAnchor();fntab();init_fields();">

<html:javascript formName="RoleMasterAddDynaValidatorForm"/>
<html:errors/>

<html:hidden  property="roleId" styleId="roleId" value="" />

<html:form styleId="roleMasterForm"  method="post"  action="/roleMasterAdd" >

<fieldset>
<legend><bean:message key="lbl.roleMaster" /></legend>
  <table width="100%">
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
    <tr>
    <td><bean:message key="lbl.roleDesc" /><span><font color="red">*</font></span></td>
    <td><html:text property="roleDesc" styleClass="text" styleId="roleDesc" maxlength="50" readonly="true" value="${requestScope.list[0].roleDesc}" onblur="fnChangeCase('roleMasterForm','roleDesc')" /></td>
    
    <td><bean:message key="lbl.moduleId" /><span><font color="red">*</font></span></td>
       <td><html:text property="moduleId" styleClass="text" styleId="moduleId" maxlength="10" readonly="true" value="${requestScope.list[0].moduleId}" />
        <html:button property="moduleButton" styleId="moduleButton" onclick="openLOVCommon(28,'roleMasterForm','moduleId','','', '','','','lbxModule')" value=" " styleClass="lovbutton"></html:button>
    <%-- <img onClick="openLOVCommon(28,'roleMasterForm','moduleId','','', '','','','lbxModule')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxModule" styleId="lbxModule" value="${requestScope.list[0].lbxModule}" />
     </td>
    </tr>
 <tr>
      <td><bean:message key="lbl.active" /></td>
      <logic:equal value="Active" name="status">
         <td><input type="checkbox" name="roleStatus" id="roleStatus" checked="checked" /></td>
       </logic:equal>
       
      <logic:notEqual value="Active" name="status">
         <td><input type="checkbox" name="roleStatus" id="roleStatus"  /></td>
      </logic:notEqual>
      
    </tr>
       </logic:iterate>
    </logic:present>
    
     <logic:notPresent name="editVal">
     <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
    <tr>
    <td><bean:message key="lbl.roleDesc" /><span><font color="red">*</font></span></td>
    <td><html:text property="roleDesc" styleClass="text" styleId="roleDesc" maxlength="50" onblur="fnChangeCase('roleMasterForm','roleDesc')" /></td>
    
    <td><bean:message key="lbl.moduleId" /><span><font color="red">*</font></span></td>
       <td><html:text property="moduleId" styleClass="text" styleId="moduleId" maxlength="10" readonly="true" tabindex="-1" />
       <html:button property="moduleButton" styleId="moduleButton" onclick="openLOVCommon(28,'roleMasterForm','moduleId','','', '','','','lbxModule')" value=" " styleClass="lovbutton"></html:button>
     <%--<img onClick="openLOVCommon(28,'roleMasterForm','moduleId','','', '','','','lbxModule')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxModule" styleId="lbxModule"  />
     </td>
    </tr>
 <tr>
      <td><bean:message key="lbl.active" /></td>
      <logic:equal value="Active" name="status">
         <td><input type="checkbox" name="roleStatus" id="roleStatus" checked="checked" /></td>
       </logic:equal>
       
      <logic:notEqual value="Active" name="status">
         <td><input type="checkbox" name="roleStatus" id="roleStatus"  /></td>
      </logic:notEqual>
      
    </tr>
</logic:notPresent>
    <tr><td>

    <logic:present name="editVal">
      <button type="button" name="save" value="Save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditRole('${requestScope.list[0].roleId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   
   
   <logic:notPresent name="editVal">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" value="Save" title="Alt+V" accesskey="V" id="save" onclick="return saveRole();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("roleMasterForm").action="roleMasterBehind.do";
	    document.getElementById("roleMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("roleMasterForm").action="roleMasterBehind.do";
	    document.getElementById("roleMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}  
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}     
	
	
</script>
</logic:present>
  </body>
		</html:html>