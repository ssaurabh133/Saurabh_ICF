<!--Author Name : Apoorva
Date of Creation : 09-May-2011
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

<script type="text/javascript">
function clearInput(){ 
<%if(request.getAttribute("Add")== "Add"){%>
document.getElementById('groupDescription').value='';
document.getElementById('groupExposureLimit').value='';
<%}%>
}

function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('gcdGroupMasterForm').groupExposureLimit.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('gcdGroupMasterForm').groupDescription.focus();
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

<body onload="enableAnchor();clearInput();fntab();init_fields();">

	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<html:form styleId="gcdGroupMasterForm"  method="post"  action="/gcdGroupMasterAction" >
<fieldset>
<legend><bean:message key="lbl.gcdGroupMaster" /></legend>
  <table width="100%">
    
   
	<html:hidden  property="gcdgroupId" styleId="gcdgroupId"  value="${requestScope.list[0].gcdgroupId}" />

      <logic:present name="editVal">
       <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
       <tr>
     
      <td><bean:message key="lbl.groupDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="groupDescription" styleClass="text"  styleId="groupDescription" maxlength="50" onblur="fnChangeCase('gcdGroupMasterForm','groupDescription')" value="${requestScope.list[0].groupDescription}" />
     </td>
     
      <td><bean:message key="lbl.groupExposureLimit" /><span><font color="red">*</font></span></td>
     
      <td><html:text property="groupExposureLimit" style="text-align: right" styleClass="text" styleId="groupExposureLimit" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" value="${requestScope.list[0].groupExposureLimit}" /></td>
     </tr>
     <tr>
      <td><bean:message key="lbl.recStatus" /></td>
      <td>
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="recStatus" id="recStatus"  />
      </logic:notEqual></td>
     </tr>
     </logic:present>
     
   <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/><tr>
      <td><bean:message key="lbl.groupDesc" /><span><font color="red">*</font></span></td>
      <td><html:text property="groupDescription" styleClass="text" styleId="groupDescription" maxlength="50" onblur="fnChangeCase('gcdGroupMasterForm','groupDescription')"/>
     </td>
     
      <td><bean:message key="lbl.groupExposureLimit" /><span><font color="red">*</font></span></td>
      <td><html:text property="groupExposureLimit" styleClass="text" style="text-align: right" styleId="groupExposureLimit" maxlength="18" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"  /></td>
     </tr>
     <tr>
      <td><bean:message key="lbl.recStatus" /></td>
      <td>
         <input type="checkbox" name="recStatus" id="recStatus" checked="checked" />
       </td>
     </tr>
     </logic:notPresent>
    <tr><td>
    
    <logic:present name="editVal">
     <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return gcdGroupModify('${requestScope.list[0].gcdgroupId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="editVal">
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return gcdGroupSave();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("gcdGroupMasterForm").action="gcdGroupMasterSearchBehind.do";
	    document.getElementById("gcdGroupMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("gcdGroupMasterForm").action="gcdGroupMasterSearchBehind.do";
	    document.getElementById("gcdGroupMasterForm").submit();
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='EXIST')
	{
		alert("<bean:message key="lbl.dataExist" />");		
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>