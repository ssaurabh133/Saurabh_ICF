<!--Author Name : Richa Bansal-->
<!--Date of Creation : 11-March-2013-->
<!--Purpose  : Information of court type Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/courtTypeMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('courtTypeCode').focus();
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

<body onload="fntab();">
<html:form styleId="courtTypeMasterForm"  method="post"  action="/courtTypeMasterAdd" >
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.courtTypeMaster" /></legend>
  <table width="100%">
    <tr>
     <logic:present name="editVal">
      <td> <bean:message key="lbl.courtTypeCode" /><span><font color="red">*</font></span></td>
        <td><html:text property="courtTypeCode" styleClass="text" styleId="courtTypeCode" maxlength="10" onblur="fnChangeCase('courtTypeMasterForm','courtTypeCode')" value="${requestScope.list[0].courtTypeCode}" readonly="true"/></td>
      </logic:present>
   
   <logic:present name="save">
      <td> <bean:message key="lbl.courtTypeCode" /><span><font color="red">*</font></span></td>
        <td><html:text property="courtTypeCode" styleClass="text" styleId="courtTypeCode" maxlength="10" onblur="fnChangeCase('courtTypeMasterForm','courtTypeCode')" value="${requestScope.list[0].courtTypeCode}"/></td>
   </logic:present>
 
      <td><bean:message key="lbl.courtTypeDesc" /><span><font color="red">*</font></span></td>
     
      
      <td><html:text property="courtTypeDesc" styleClass="text" styleId="courtTypeDesc" maxlength="50" onblur="fnChangeCase('courtTypeMasterForm','courtTypeDesc')" value="${requestScope.list[0].courtTypeDesc}" />
     </td>
    </tr>
    
      <tr>
      
    <td><bean:message key="lbl.status" /></td>
      <td>
      
      <logic:present name="editVal">
      
        <logic:equal value="A" name="status">
         <input type="checkbox" name=recStatus id="countryStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="A" name="status">
         <input type="checkbox" name="recStatus" id="countryStatus"  />
      </logic:notEqual>
      
   </logic:present>
   
   <logic:present name="save">
   
     <input type="checkbox" name=recStatus id="countryStatus" checked="checked" />
    
   </logic:present>
      
     
    
    
      </td>
    </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick=" fnEditCourtType('${requestScope.list[0].courtTypeCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return saveCourtType();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> </tr>

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("courtTypeMasterForm").action="courtTypeMasterBehind.do";
	    document.getElementById("courtTypeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("courtTypeMasterForm").action="courtTypeMasterBehind.do";
	    document.getElementById("courtTypeMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>