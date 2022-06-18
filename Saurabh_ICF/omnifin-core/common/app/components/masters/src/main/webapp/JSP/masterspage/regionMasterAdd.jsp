<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 21-May-2011-->
<!--Purpose  : Information of Region Master-->
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
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('regionMaster').regionStatus.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	 document.getElementById('regionMaster').regionDes.focus();
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

<body onload="enableAnchor();fntab();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
 <logic:present name="editVal">
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
<html:form styleId="regionMaster"  method="post"  action="/regionMasterAdd" onsubmit="fnEditRegion('${requestScope.list[0].regionId}');" >
<fieldset>
<legend><bean:message key="lbl.regionMaster" /></legend>
  <table width="100%">
   
   
	<html:hidden  property="regionId" styleId="regionId"  value="${requestScope.list[0].regionId}" />
	<logic:present name="editVal">
	<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
    <logic:iterate id="listObj" name="list">
 <tr>
      <td width="13%"><bean:message key="lbl.regionDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="regionDes" styleClass="text" styleId="regionDes" maxlength="50"  onblur="fnChangeCase('regionMaster','regionDes')" value="${requestScope.list[0].regionDes}"/>
     </td>
     
      <td width="13%"><bean:message key="lbl.status" /></td>
      <td width="13%">
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="regionStatus" id="regionStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="regionStatus" id="regionStatus"  />
      </logic:notEqual></td>

    </tr> </logic:iterate ></logic:present >
    
    <logic:notPresent name="editVal">
    

 <tr>
      <td width="13%"><bean:message key="lbl.regionDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="regionDes" styleClass="text" styleId="regionDes" maxlength="50" onblur="fnChangeCase('regionMaster','regionDes')" />
     </td >
     
      <td width="13%"><bean:message key="lbl.status" /></td>
      <td width="13%">
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="regionStatus" id="regionStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="regionStatus" id="regionStatus"  />
      </logic:notEqual></td>

    </tr></logic:notPresent >
   
    <tr>
   <td>
  
  <logic:present name="editVal">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditRegion('${requestScope.list[0].regionId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
     <logic:present name="editValUpdate">
      <button type="button"  name="save"  id="save" title="Alt+V" accesskey="V" onclick="return fnEditRegion('${requestScope.list[0].regionId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present></td></tr>
    
	</table>		


</fieldset>

</html:form>	

 		
 </logic:present>
 
   <logic:notPresent name="editVal">
 
   		<html:form styleId="regionMaster"  method="post"  action="/regionMasterAdd" onsubmit="return saveRegion();" >
<fieldset>
<legend><bean:message key="lbl.regionMaster" /></legend>
  <table width="100%">
   
   
	<html:hidden  property="regionId" styleId="regionId"  value="${requestScope.list[0].regionId}" />
	<logic:present name="editVal">
    <logic:iterate id="listObj" name="list">
 <tr>
      <td width="13%"><bean:message key="lbl.regionDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="regionDes" styleClass="text" styleId="regionDes" maxlength="50" readonly="true" onblur="fnChangeCase('regionMaster','regionDes')" value="${requestScope.list[0].regionDes}"/>
     </td>
     
      <td width="13%"><bean:message key="lbl.status" /></td>
      <td width="13%">
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="regionStatus" id="regionStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="regionStatus" id="regionStatus"  />
      </logic:notEqual></td>

    </tr> </logic:iterate ></logic:present >
    
    <logic:notPresent name="editVal">
      <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
 <tr>
      <td width="13%"><bean:message key="lbl.regionDesc" /><span><font color="red">*</font></span></td>
      <td width="13%"><html:text property="regionDes" styleClass="text" styleId="regionDes" maxlength="50" onblur="fnChangeCase('regionMaster','regionDes')" />
     </td >
     
      <td width="13%"><bean:message key="lbl.status" /></td>
      <td width="13%">
         <input type="checkbox" name="regionStatus" id="regionStatus" checked="checked" />
      </td>

    </tr></logic:notPresent >
   
    <tr>
    <td>
  <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
   <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveRegion();" class="topformbutton2" ><bean:message key="button.save" /></button>
</logic:present >

<logic:present name="editValUpdate">
     <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditRegion('${requestScope.list[0].regionId}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
 </td></tr>
    
	</table>		
</fieldset>
  	</html:form>	
  	 		
</logic:notPresent>


  
  
          
	
		<logic:present name="sms">
<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("regionMaster").action="regionMasterBehind.do";
	    document.getElementById("regionMaster").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("regionMaster").action="regionMasterBehind.do";
	    document.getElementById("regionMaster").submit();
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