<!--Author Name : Ritu Jindal-->
<!--Date of Creation : -->
<!--Purpose  : Information of Agency Master Add-->
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


<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/agencyScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function clearInput(){ 
<%if(request.getAttribute("Add")== "Add"){%>
document.getElementById('agencyCode').value='';
document.getElementById('agencyDesc').value='';
<%}%>
}
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('agencyMasterForm').agencyType.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('agencyMasterForm').agencyCode.focus();
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

<body onload="enableAnchor();clearInput();fntab();init_fields();userMapping();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="agencyMasterForm"  method="post"  action="/agencyMasterAdd" >
<fieldset>
<legend><bean:message key="lbl.agencyMaster" /></legend>
  <table width="100%">
  
   <logic:present name="editVal">
  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
   <logic:iterate id="listObj" name="list">
   <tr>
   <td><bean:message key="lbl.agencyCode" /><span><font color="red">*</font></span></td>
   <td><html:text property="agencyCode" styleClass="text" styleId="agencyCode" maxlength="10" readonly="true" value="${listObj.agencyCode}" /></td>
  
   <td><bean:message key="lbl.agencyType" /><font color="red">*</font></td>
	<td> 
	  <html:select property="agencyType" styleId="agencyType" styleClass="text" onchange="userMapping();" value="${listObj.agencyType}">
		<option value="">--<bean:message key="lbl.select" />--</option>
			 <logic:present name="agencyType">
			 <logic:notEmpty name="agencyType" >
			   <html:optionsCollection name="agencyType" label="agencyDescription" value="agencyValue" />
			 </logic:notEmpty>
			 </logic:present>
		</html:select>
	</td>
	</tr><tr>					
	<td><bean:message key="lbl.agencyDesc" /><span><font color="red">*</font></span></td>
    <td><html:text property="agencyDesc" styleClass="text" styleId="agencyDesc" maxlength="50" onblur="fnChangeCase('agencyMasterForm','agencyDesc')" value="${listObj.agencyDesc}"/></td>
   
      
    <td><bean:message key="lbl.active" /></td>
      <td>
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="agencyStatus" id="agencyStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="agencyStatus" id="agencyStatus"  />
      </logic:notEqual></td>
      
    </tr>
    <logic:notEqual name="listObj" property="agencyType" value="EA">
		<tr>
		      <td id="usrNameMapp" style="display: none;">
		        	<bean:message key="lbl.userMapp" /></td>
		        <td id="userMapping" style="display: none;">
		         	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		         	<html:hidden  property="lbxUserIds" styleId="lbxUserIds" value="${requestScope.lbxUserIds}" />
		         	 <html:select property="userName" styleId="userName" size="5" multiple="multiple" style="width: 120" >
		                 <logic:present name="userNameList">
		                
		                  <logic:notEmpty name="userNameList" >
		        		       <html:optionsCollection name="userNameList" value="lbxUserIds" label="userName"/>
		        		</logic:notEmpty>
		        	</logic:present>
		           
				</html:select>
				     <html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(264,'agencyMasterForm','userName','','', '','','','lbxUserIds');" value=" " styleClass="lovbutton"></html:button>	   
		        <%--<img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"></td> --%>
		     </td>
	
	     </tr>
   </logic:notEqual>
   <logic:equal name="listObj" property="agencyType" value="EA">
 
   	<tr>
	      <td id="usrNameMapp" >
	        	<bean:message key="lbl.userMapp" /></td>
	        <td id="userMapping" >
	         	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	         	<html:hidden  property="lbxUserIds" styleId="lbxUserIds" value="${requestScope.lbxUserIds}" />
	         	 <html:select property="userName" styleId="userName" size="5" multiple="multiple" style="width: 120" >
	                 <logic:present name="userNameList">
	                
	                  <logic:notEmpty name="userNameList" >
	        		       <html:optionsCollection name="userNameList" value="lbxUserIds" label="userName"/>
	        		</logic:notEmpty>
	        	</logic:present>
	           
			</html:select>
			     <html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(264,'agencyMasterForm','userName','','', '','','','lbxUserIds');" value=" " styleClass="lovbutton"></html:button>	   
	        <%--<img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"></td> --%>
	     </td>

     </tr>
   
   </logic:equal>


    
   </logic:iterate>
   </logic:present>
   
   <logic:notPresent name="editVal">
 <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
   <tr>
   <td><bean:message key="lbl.agencyCode" /><span><font color="red">*</font></span></td>
   <td><html:text property="agencyCode" styleClass="text"  styleId="agencyCode" maxlength="10" onblur="fnChangeCase('agencyMasterForm','agencyCode')" /></td>
  
    <td><bean:message key="lbl.agencyType" /><font color="red">*</font></td>
	<td> 
	  <html:select property="agencyType" styleId="agencyType" onchange="userMapping();" styleClass="text" value="${requestScope.list[0].agencyType}">
		<option value="">--<bean:message key="lbl.select" />--</option>
			 <logic:present name="agencyType">
			 <html:optionsCollection name="agencyType" label="agencyDescription" value="agencyValue" />
			 </logic:present>
		</html:select>
	</td>
	</tr><tr>	
    <td><bean:message key="lbl.agencyDesc" /><span><font color="red">*</font></span></td>
    <td><html:text property="agencyDesc" styleClass="text"  styleId="agencyDesc" onblur="fnChangeCase('agencyMasterForm','agencyDesc')" maxlength="50" /></td>
      
    <td ><bean:message key="lbl.active" /></td>
      <td>
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="agencyStatus" id="agencyStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
           <input type="checkbox" name="agencyStatus" id="agencyStatus"  />
      </logic:notEqual></td>
      
    </tr>
     <tr>
      <td id="usrNameMapp" style="display: none;">
        	<bean:message key="lbl.userMapp" /></td>
        <td id="userMapping" style="display: none">
           <html:select property="userName" styleId="userName" size="5" multiple="multiple" style="width: 120" >
		</html:select>
		    <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		     <html:hidden  property="lbxUserIds" styleId="lbxUserIds" value=""/>
		     <html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(264,'agencyMasterForm','userName','','', '','','','lbxUserIds');" value=" " styleClass="lovbutton"></html:button>	   
        <%--<img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(12,'userBranchMasterForm','branchDesc','','', '','','');"></td> --%>
</td>

</tr>

   </logic:notPresent>
   
    <tr>
    <td>
    <logic:present name="editVal">
      <button name="save"  type="button" id="save" title="Alt+V" accesskey="V" onclick="return fnEditAgency('${requestScope.list[0].agencyCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:notPresent name="editVal">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button name="save"  type="button" id="save" title="Alt+V" accesskey="V" onclick="return saveAgency();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:notPresent>
   </td></tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("agencyMasterForm").action="agencyMasterBehind.do";
	    document.getElementById("agencyMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("agencyMasterForm").action="<%=request.getContextPath()%>/agencyMasterBehind.do";
	    document.getElementById("agencyMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='EX')
	{
		alert("<bean:message key="lbl.agencyCodeExist" />");
	}
	else
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	

	
	
</script>
</logic:present>
  </body>
		</html:html>