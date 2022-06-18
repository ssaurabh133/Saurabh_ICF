<!--Author Name : Apoorva Joshi
Date of Creation : 26-May-2011
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.masters.vo.GenericMasterVo"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
 
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>	     
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/genericMasterScript.js"></script>		
   <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
  
   <script type="text/javascript">
 
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('genericMasterForm').description.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('genericMasterForm').genericButton.focus();
     }
     return true;
     

}


//-->
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
  
  <body onload="enableAnchor();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
  <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
  <html:javascript formName="GenericMasterAddDynaValidatorForm"/>
<html:errors/>
  <html:form styleId="genericMasterForm" action="/genericAction">
  
  <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
  <fieldset>
<legend><bean:message key="lbl.genericMaster"/></legend>   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    <table width="100%" height="86" border="0" cellpadding="0" cellspacing="0">
  <tr>
     <td><bean:message key="lbl.genericDescription"/><span><font color="red">*</font></span></td>
   <logic:present name="modify">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
     <td>
      <html:text property="genericKey" styleId="genericKey"  styleClass="text" maxlength="20" onblur="fnChangeCase('genericMasterForm','genericKey')" value="${requestScope.list[0].genericKey}" readonly="true"/>
      <html:hidden property="lbxGenericId" styleId="lbxGenericId"  value="${requestScope.list[0].lbxGenericId}" />
    </td>
    </logic:present>
    <logic:notPresent name="modify">
    <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
     <td>
  
		<html:text property="genericKey" styleId="genericKey" readonly="true" styleClass="text" maxlength="50" tabindex="-1"  />
        <html:button property="genericButton" styleId="genericButton" onclick="openLOVCommon(136,'genericMasterForm','genericKey','','', '','','','hcommon','parentValue','');closeLovCallonLov1();" value=" " styleClass="lovbutton"> </html:button>
		<html:hidden property="lbxGenericId" styleId="lbxGenericId" />
		<input type ="hidden" name="hcommon" id="hcommon" />
	</td>
     
    </logic:notPresent>
    
     <td><bean:message key="lbl.parentValue"/></td>
   <logic:present name="modify">
     <td>
      <html:text property="parentValue" styleId="parentValue" tabindex="-1" styleClass="text" readonly="true"  value="${requestScope.list[0].parentValue}" readonly="true"/>
    </td>
    </logic:present>
    <logic:notPresent name="modify">
     <td>
      <html:text property="parentValue" styleId="parentValue" styleClass="text" readonly="true" tabindex="-1" />
    </td>
    </logic:notPresent>
    
   
   <td><bean:message key="lbl.value"/><span><font color="red">*</font></span></td>
   
   <logic:present name="modify">
     <td>
      <html:text property="genericval" styleId="genericval"  styleClass="text" maxlength="10" onblur="gmChangeCase(id)" value="${requestScope.list[0].genericval}" readonly="true" />
    </td>
    </logic:present>
    
    <logic:notPresent name="modify">
	    <td>
	
	      <html:text property="genericval" styleId="genericval" styleClass="text" maxlength="10" onkeyup="checkAlphaNum(id);" onblur="gmChangeCase(id)"/>
	

	    </td>
    </logic:notPresent>
    </tr>
    
    <tr>
    <td><bean:message key="lbl.description"/><span><font color="red">*</font></span></td>
   <logic:present name="modify">
     <td>
      <html:text property="description" styleId="description" maxlength="50" styleClass="text" onblur="gmChangeCase(id)" value="${requestScope.list[0].description}"/>
    </td>
    </logic:present>
    <logic:notPresent name="modify">
     <td>
      <html:text property="description" styleId="description" styleClass="text" maxlength="50" onblur="gmChangeCase(id)" />
    </td>
    </logic:notPresent>
    <td>
     	<bean:message key="lbl.status"/> </td>
        <td>
         <logic:notPresent name="modify">
         <input type="checkbox" name="status" id="recStatus" checked="checked" />
         </logic:notPresent>
         
        <logic:present name="modify">
     			 <logic:equal value="A" name="status">
              			<input type="checkbox" name="status" id="recStatus" checked="checked" />
      			</logic:equal>
      
         <logic:notEqual value="A" name="status">
      	 		  <input type="checkbox" name="status" id="recStatus"  />
         </logic:notEqual>
         </logic:present>
         </td>
              
    </tr>
      
  <tr>
	 <logic:present name="modify">
	 <% 
	   ArrayList ob=(ArrayList)request.getAttribute("list");
	 	int si=ob.size();
	 	GenericMasterVo vo=(GenericMasterVo)ob.get(0);
	%>
	  <td align="left" >
	  <button type="button" name="button" id="save" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return saveModifyDetails(
	 	'<%=org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(vo.getGenericKey()) %>','<%=org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(vo.getParentValue()) %>','<%=org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(vo.getGenericval()) %>');" ><bean:message key="button.save" /></button></td>
	
	 </logic:present>
	 
	 <logic:notPresent name="modify">
	 <td align="left" >
	 <button type="button" name="button" id="save" title="Alt+V" accesskey="V" class="topformbutton2" onclick="return genericSave();" ><bean:message key="button.save" /></button></td>
	 </logic:notPresent>		   	
 </tr>
 </table></td>
  </tr></table></fieldset>
</html:form>
   		<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("genericMasterForm").action="genericMasterBehind.do";
	    document.getElementById("genericMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("genericMasterForm").action="genericMasterBehind.do";
	    document.getElementById("genericMasterForm").submit();
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}

</script>
</logic:present>
  </body>
</html:html>
