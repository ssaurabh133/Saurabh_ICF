<!--Author Name : nazia parvez -->
<!--Date of Creation : 11-march-2013-->
<!--Purpose  : Information of Legal notice Master-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/legalScript/noticeTypeMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">
function fntab()
{
   document.getElementById('noticeTypeMasterForm').noticeTypeCode.focus();
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
<html:form styleId="noticeTypeMasterForm"  method="post"  action="/noticeTypeMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<fieldset>
<legend><bean:message key="lbl.noticeTypeMaster" /></legend>
  <table width="100%">
    <tr>
    
     <td><bean:message key="lbl.noticeTypeCode" /><span><font color="red">*</font></span></td>
     <logic:present name="save">
      <td><html:text property="noticeTypeCode" styleClass="text" styleId="noticeTypeCode" maxlength="10" onblur="fnChangeCase('noticeTypeMasterForm','noticeTypeCode')" value="${requestScope.list[0].noticeTypeCode}" /></td>
   
	</logic:present>
	<logic:present name="editVal">
<td><html:text property="noticeTypeCode" styleClass="text" styleId="noticeTypeCode" maxlength="10"  value="${requestScope.list[0].noticeTypeCode}" readonly="true"/></td>
     
     </logic:present>
      <td><bean:message key="lbl.noticeTypeDesc" /><span><font color="red">*</font></span></td>
     
      
      <td><html:text property="noticeTypeDesc" styleClass="text" styleId="noticeTypeDesc" maxlength="50" onblur="fnChangeCase('noticeTypeMasterForm','noticeTypeDesc')" value="${requestScope.list[0].noticeTypeDesc}" />
     </td>
    
    </tr>
    
    
      <tr>
      
      <td><bean:message key="lbl.status" /></td>
      
      <td>
	
	<logic:present name="editVal">
	
	<logic:equal value="A" name="status">
	   <input type="checkbox" name=recStatus id="Status" checked="checked" />
	 </logic:equal>
	<logic:notEqual value="A" name="status">
	   <input type="checkbox" name="recStatus" id="Status"  />
	</logic:notEqual>
	
	</logic:present>
   
  	<logic:present name="save">
      <input type="checkbox" name=recStatus id="Status" checked="checked" />
   </logic:present>
       
    
     </td> 
    </tr><tr><td>
    
    <logic:present name="editVal">
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditNoticeType('${requestScope.list[0].noticeTypeCode}');" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
  	<logic:present name="save">
      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return savenoticeType();" class="topformbutton2" ><bean:message key="button.save" /></button>
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
		document.getElementById("noticeTypeMasterForm").action="noticeTypeMasterBehind.do";
	    document.getElementById("noticeTypeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("noticeTypeMasterForm").action="noticeTypeMasterBehind.do";
	    document.getElementById("noticeTypeMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')

	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
</script>
</logic:present>
  </body>
		</html:html>