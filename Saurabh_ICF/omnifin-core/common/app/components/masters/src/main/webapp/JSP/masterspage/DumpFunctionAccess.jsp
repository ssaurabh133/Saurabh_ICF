
<!--Author Name : ADITI SHREE -->
<!--Date of Creation :08 JUNE 2012  -->
<!--Purpose  : Information of REPORT FUNCTION ACCESS Search-->
<!--Documentation : -->
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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/dumpFunctionAccess.js"></script>
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
<body onload="enableAnchor();document.getElementById('dumpFunctioAccessForm').roleButton.focus();init_fields();">


<html:form styleId="dumpFunctioAccessForm"  method="post"  action="/reportFunctionAccess" >
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.dumpFunctionAccess" /></legend>
  <table width="100%">
    <tr>
    <td width="13%"><bean:message key="lbl.roleId" /><span><font color="red">*</font></span></td>
       <td width="13%"><html:text property="roleId" styleClass="text"  styleId="roleId" maxlength="10"  readonly="true" value="${requestScope.roleDesc}" />
       <html:button property="roleButton" styleId="roleButton" onclick="openLOVCommon(605,'dumpFunctioAccessForm','roleId','','', '','','','moduleId','lbxModuleId')" value=" " styleClass="lovbutton"></html:button> 
      
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxRoleId" styleId="lbxRoleId" value="${requestScope.roleId}" />
     </td>
      
      <td width="13%"><bean:message key="lbl.moduleId" /><span><font color="red">*</font></span></td>
       <td width="13%"><html:text property="moduleId" styleClass="text" tabindex="-1" styleId="moduleId" maxlength="10" readonly="true" value="${requestScope.moduleDesc}" />
       <html:hidden  property="lbxModuleId" styleId="lbxModuleId" value="${requestScope.moduleId}"/>
       </td>
   </tr>
       
       <tr> 
   
    <td>
     <button type="button" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="fnDumpSearch('<bean:message key="lbl.selectBoth" />','<bean:message key="lbl.selectRoleCode" />','<bean:message key="lbl.selectModuleCode" />');" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
     
    <logic:present name="list">
	    <logic:notEmpty name="list">
	       <button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveDumpAccess('<bean:message key="lbl.selectBoth" />','<bean:message key="lbl.selectRoleCode" />','<bean:message key="lbl.selectModuleCode" />');"  ><bean:message key="button.save" /></button>
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
<logic:present name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
  	<tr class="white2">
  	
    	<td ><input type=checkbox id="allchkd" name=allchkd onClick="allChecked()" /></td> 
    	<td ><b><bean:message key="lbl.DumpName" /></b></td>   
    </tr>  
<logic:iterate name="list" id="sublist" >
  <tr class="white1">
  <logic:equal name="sublist" property="status"	value="A">
   <td ><input type=checkbox id="chkCases" name="chkCases" checked="checked" value="${sublist.reportName}"/></td>
  </logic:equal>
  <logic:equal name="sublist" property="status"	value="X" >
  <td ><input type=checkbox id="chkCases" name="chkCases" value="${sublist.reportName}" /></td>
  </logic:equal>
    <td><b>${sublist.reportDesc}</b></td>
  </tr>
  
  </logic:iterate>
 </table>
</table>
 </logic:present>
</fieldset>    
           
	</html:form>	
<logic:present name="success">
<script type="text/javascript">
	alert("Data Saved Successfully.");
</script>
</logic:present>
<logic:present name="error">
<script type="text/javascript">
	alert("Some error occurs,Please contact administrator.);
</script>
</logic:present>

  </body>
		</html:html>
