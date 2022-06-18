<!--Author Name : Ritu Jindal-->
<!--Date of Creation :19 May 2011 -->
<!--Purpose  : Information of Role Access Master Search-->
<!--Documentation : --> 

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%int count=0 ;
count=4;
 %>
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleAccessScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function allChecked()
{

	var c = document.getElementById("allchkd").checked;

	var ch=document.getElementsByName('chkCases');

 	var zx=0;

	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
	
}
</script>
</head>
<body onload="enableAnchor();document.getElementById('roleAccessMasterForm').roleButton.focus();init_fields();">


<html:form styleId="roleAccessMasterForm"  method="post"  action="/roleAccessMaster" >
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.roleAccessMaster" /></legend>
  <table width="100%">
    <tr>
    <td width="13%"><bean:message key="lbl.roleDesc" /><span><font color="red">*</font></span></td>
       <td width="13%"><html:text property="roleId" styleClass="text"  styleId="roleId" maxlength="10"  readonly="true" value="${requestScope.roleDesc}" />
       <html:button property="roleButton" styleId="roleButton" onclick="openLOVCommon(29,'roleAccessMasterForm','roleId','','', '','','','moduleId','lbxModuleId')" value=" " styleClass="lovbutton"></html:button> 
      
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxRoleId" styleId="lbxRoleId" value="${requestScope.roleId}" />
     </td>
      
      <td width="13%"><bean:message key="lbl.moduleDesc" /><span><font color="red">*</font></span></td>
       <td width="13%"><html:text property="moduleId" styleClass="text" tabindex="-1" styleId="moduleId" maxlength="10" readonly="true" value="${requestScope.moduleDesc}" />
       <html:hidden  property="lbxModuleId" styleId="lbxModuleId" value="${requestScope.moduleId}"/>
       </td>
   </tr>
       
       <tr> 
   
    <td>
     <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="fnSearch('<bean:message key="lbl.selectBoth" />','<bean:message key="lbl.selectRoleCode" />','<bean:message key="lbl.selectModuleCode" />');" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
     
    <logic:present name="list">
	    <logic:notEmpty name="list">
	       <button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveRoleAccess('<bean:message key="lbl.selectBoth" />','<bean:message key="lbl.selectRoleCode" />','<bean:message key="lbl.selectModuleCode" />');"  ><bean:message key="button.save" /></button>
	    </logic:notEmpty>
	    <logic:empty name="list">
	    	  <button type="button"  name="save" id="save1"  title="Alt+V" accesskey="V" disabled="disabled" ><bean:message key="button.save" /></button>
	    </logic:empty>
   </logic:present>
   <logic:notPresent name="list">
   		  <button type="button" name="save" id="save1"  title="Alt+V" accesskey="V" disabled="disabled" ><bean:message key="button.save" /></button>
   </logic:notPresent>
   
   </td>
  </tr>
</table>		
</fieldset>
<fieldset>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
  <tr class="white2">
    <td ><input type=checkbox id="allchkd" name=allchkd onClick="allChecked()" /></td> 
   <%--  <td ><b><bean:message key="lbl.functionId" /></b></td>--%>
    <td ><b><bean:message key="lbl.functionDesc" /></b></td>
   
  </tr>
  
  <logic:present name="list">
<logic:iterate name="list" id="sublist" >

  <tr class="white1">
  <td>
  <logic:equal value="0" property="parentId" name="sublist" >
  <logic:equal value="" property="funNameforCheckBox" name="sublist">
   <input type="checkbox" name="chkCases" style="display: none"  id="chkCases" value="${sublist.funName }"/>
  </logic:equal>
 <logic:notEqual value="" property="funNameforCheckBox" name="sublist">
  <input type="checkbox" name="chkCases" style="display: none"  id="chkCases" checked="checked" value="${sublist.funName }"/>
 </logic:notEqual>
  </logic:equal>
  <logic:notEqual value="0" property="parentId" name="sublist">
  <logic:equal value="" property="funNameforCheckBox" name="sublist">
   <input type="checkbox" name="chkCases"  id="chkCases" value="${sublist.funName }"/>
  </logic:equal>
 <logic:notEqual value="" property="funNameforCheckBox" name="sublist">
  <input type="checkbox" name="chkCases"   id="chkCases" checked="checked" value="${sublist.funName }"/>
 </logic:notEqual>
  </logic:notEqual>
  
  
  </td>
 <%--  <td>${sublist.funName }</td> --%>
  <td>
  <logic:equal value="0" property="parentId" name="sublist" >
      <b>${sublist.funDesc }</b>
  </logic:equal>
  <logic:notEqual value="0" property="parentId" name="sublist" >
       ${sublist.funDesc }
  </logic:notEqual>
 
  </td>
  
  </tr>
  
  </logic:iterate>
  </logic:present>
 
 <logic:present name="pageDetails" >
      <tr>
                    <td colspan="8" align="center" class="gridheader"><logic:iterate
                        id="varpage" name="pageDetails">
                        <html:link action="/roleAccessMasterBehind.do" paramName="varpage"
                            paramId="page">
                            <bean:write name="varpage" />
                        </html:link>
                    </logic:iterate></td>
                </tr>
            </logic:present>
 
</table>
</table>
</fieldset>    
           
	</html:form>	

	<logic:present name="sms">
<script type="text/javascript">

	if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.noDataSave" />");
		document.getElementById("roleAccessMasterForm").action="roleAccessMasterBehind.do";
	    document.getElementById("roleAccessMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("roleAccessMasterForm").action="roleAccessMasterBehind.do";
	    document.getElementById("roleAccessMasterForm").submit();
	}
	else  if('<%=request.getAttribute("sms").toString()%>'=='E')
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