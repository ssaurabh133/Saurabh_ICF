<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 16-may-2011-->
<!--Purpose  : Information of Department Master1-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

</script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('departmentMaster').departmentStatus.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('departmentMaster').departmentDes.focus();
     }
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


 <body onload="enableAnchor();fntab();init_fields();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
  		<html:form action="/departmentMasterAdd" styleId="departmentMaster" method="POST"  onsubmit="return fnEditDepartment();">

<fieldset>

<legend><bean:message key="lbl.deptMaster"/></legend>
 <html:hidden  property="departmentId" styleId="departmentId"  value="${requestScope.list[0].departmentId}" />
  <table width="100%">
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    <logic:iterate id="listObj" name="list">
      <tr>
    
    <td width="13%"><bean:message key="lbl.deptDes"/><span><font color="red">*</font></span></td>
    <td width="13%"><label><html:text property="departmentDes" styleClass="text" styleId="departmentDes" readonly="true" maxlength="50" onblur="fnChangeCase('departmentMaster','departmentDes')" value="${requestScope.list[0].departmentDes}" /></label></td>
   
    
      
 <td width="13%">
     <bean:message key="lbl.status"/></td>
         <td width="13%">
         		 <logic:equal value="Active" name="status">
         	  		   <input type="checkbox" name="departmentStatus" id="departmentStatus" checked="checked" />
      		</logic:equal>
      
       		  <logic:notEqual value="Active" name="status">
      	 			  <input type="checkbox" name="departmentStatus" id="departmentStatus"  />
       		  </logic:notEqual></td> 

  </tr> </logic:iterate></logic:present>
  
   <logic:notPresent name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
      <tr>
    
    <td width="13%"><bean:message key="lbl.deptDes"/><span><font color="red">*</font></span></td>
    <td width="13%"><label><html:text property="departmentDes" styleClass="text" styleId="departmentDes"  maxlength="50" onblur="fnChangeCase('departmentMaster','departmentDes')" /></label></td>
   
    
      
 <td width="13%">
     <bean:message key="lbl.status"/></td>
         <td width="13%">
         		 <logic:equal value="Active" name="status">
         	  		   <input type="checkbox" name="departmentStatus" id="departmentStatus" checked="checked" />
      		</logic:equal>
      
       		  <logic:notEqual value="Active" name="status">
      	 			  <input type="checkbox" name="departmentStatus" id="departmentStatus"  />
       		  </logic:notEqual></td> 

  </tr></logic:notPresent>
   
  
  <tr><td>
    
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditDepartment();" class="topformbutton2" ><bean:message key="button.save" /></button>
  
  
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    
    <br></td> </tr>
  
		
	</table>		

</fieldset></html:form>
  </logic:present>
 
 
  <logic:notPresent name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
<html:form action="/departmentMasterAdd" styleId="departmentMaster" method="POST"  onsubmit="return fndeptSave();">


<fieldset>

<legend><bean:message key="lbl.deptMaster"/></legend>
 <html:hidden  property="departmentId" styleId="departmentId"  value="${requestScope.list[0].departmentId}" />
  <table width="100%">
  <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    <logic:iterate id="listObj" name="list">
      <tr>
    
    <td width="13%"><bean:message key="lbl.deptDes"/><span><font color="red">*</font></span></td>
    <td width="13%"><label><html:text property="departmentDes" styleClass="text" styleId="departmentDes" readonly="true" maxlength="50" onblur="fnChangeCase('departmentMaster','departmentDes')" value="${requestScope.list[0].departmentDes}" /></label></td>
   
    
      
 <td width="13%">
     <bean:message key="lbl.status"/></td>
         <td width="13%">
         		 <logic:equal value="Active" name="status">
         	  		   <input type="checkbox" name="departmentStatus" id="departmentStatus" checked="checked" />
      		</logic:equal>
      
       		  <logic:notEqual value="Active" name="status">
      	 			  <input type="checkbox" name="departmentStatus" id="departmentStatus"  />
       		  </logic:notEqual></td> 

  </tr> </logic:iterate></logic:present>
  
   <logic:notPresent name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
      <tr>
    
    <td width="13%"><bean:message key="lbl.deptDes"/><span><font color="red">*</font></span></td>
    <td width="13%"><label><html:text property="departmentDes" styleClass="text" styleId="departmentDes"  maxlength="50" onblur="fnChangeCase('departmentMaster','departmentDes')" /></label></td>
   
    
      
 <td width="13%">
     <bean:message key="lbl.status"/></td>
         <td width="13%">
         	  <input type="checkbox" name="departmentStatus" id="departmentStatus" checked="checked" />
         </td> 

  </tr></logic:notPresent>
   
  
  <tr><td>
     
  
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fndeptSave();" class="topformbutton2" ><bean:message key="button.save" /></button>
    <br></td> </tr>
  
		
	</table>		

</fieldset></html:form>
</logic:notPresent>
<logic:present name="sms">
		<script type="text/javascript">

     if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("departmentMaster").action="departmentMasterBehind.do";
	    document.getElementById("departmentMaster").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("departmentMaster").action="departmentMasterBehind.do";
	    document.getElementById("departmentMaster").submit();
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
